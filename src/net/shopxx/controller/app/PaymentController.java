/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shopxx.Setting;
import net.shopxx.entity.Deposit;
import net.shopxx.entity.Member;
import net.shopxx.entity.Payment;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.PaymentStatus;
import net.shopxx.entity.Payment.Method;
import net.shopxx.entity.Payment.Status;
import net.shopxx.entity.Payment.Type;
import net.shopxx.entity.Sn;
import net.shopxx.service.DepositService;
import net.shopxx.service.MemberService;
import net.shopxx.service.PaymentService;
import net.shopxx.service.SnService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.TwUtil;
import net.shopxx.util.app.alipay.config.AlipayConfig;
import net.shopxx.util.app.alipay.util.AlipaySingleQuery;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 支付
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("APPPaymentController")
@RequestMapping("/m/payment")
public class PaymentController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;

	/**
	 * 余额充值
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public @ResponseBody ModelMap recharge(Long userid, BigDecimal amount,
			HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request
					.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj
					.get("userid")));
			Member member = memberService.find(userid);
			if (member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			amount = new BigDecimal((String) obj.get("amount"));

			Payment payment = new Payment();
			Setting setting = SettingUtils.get();
			if (amount == null || amount.compareTo(new BigDecimal(0)) <= 0
					|| amount.precision() > 15
					|| amount.scale() > setting.getPriceScale()) {

			}
			payment.setSn(snService.generate(Sn.Type.payment));
			payment.setType(Type.recharge);
			payment.setMethod(Method.online);
			payment.setStatus(Status.wait);
			payment.setPaymentMethod("支付宝即时交易");
			payment.setFee(new BigDecimal(0));
			payment.setAmount(amount);
			payment.setPaymentPluginId(null);
			payment.setExpire(null);
			payment.setMember(member);
			paymentService.save(payment);

			model.put("success", 2);
			model.put("sn", payment.getSn());
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}

	/**
	 * 提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody ModelMap submit(Long userid, String sn,
			HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request
					.getInputStream());
			userid = Long.parseLong(TwUtil.decryptStr((String) map
					.get("userid")));
			Member member = memberService.find(userid);
			if (member == null) {
				throw new Exception("无效的用户");
			}
			sn = (String) map.get("sn");
			Payment payment = paymentService.findBySn(sn);
			if (payment == null) {
				throw new Exception("付款单为空");
			}
			if (payment.getType() != Payment.Type.recharge) {
				throw new Exception("此付款单无法用于充值");
			}
			if (payment.getStatus() == Payment.Status.success) {
				throw new Exception("此付款单已使用过，充值无效");
			}
			if (payment.getStatus() == Payment.Status.failure) {
				throw new Exception("此付款单已试用过，充值无效");
			}
			String result = AlipaySingleQuery.getSingleQueryString("", sn
					+ "-001");
			Map<String, String> resultMap = AlipaySingleQuery
					.parseXMLStrToMap(result);
			if ("F".equals(resultMap.get("is_success"))) {// 支付宝请求失败
				throw new Exception(resultMap.get("error"));// 获取支付宝异常状态吗，并抛出
			}
			String tradeStatus = resultMap.get("trade_status");
			if (!"TRADE_FINISHED".equals(tradeStatus) && !"TRADE_SUCCESS".equals(tradeStatus))
				throw new Exception("您未支付成功，请查看支付宝交易记录");
			// 检查收款支付宝账号
			String seller = resultMap.get("seller_email").toString();
			if (!AlipayConfig.seller.equals(seller)) {
				throw new Exception("收款支付宝账号异常");
			}
			BigDecimal amountPayable = payment.getAmount();// 获取付款金额
			BigDecimal totalFee = new BigDecimal(resultMap.get("total_fee"));// 支付宝交易总金额
			boolean isError = false;
			if (amountPayable.compareTo(totalFee) != 0) {// 付款金额与交易总金额
				isError = true;
			}
			String tradeNo = resultMap.get("trade_no").toString();
			// 检查此支付宝交易号是否使用过
			if (depositService.findByTradeNo(tradeNo) != null) {
				throw new Exception("此笔交易已处理，请勿重复操作");
			}
			/**
			 * 交易金额转入用户余额
			 */
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date gmtPayment = sdf.parse(resultMap.get("gmt_payment"));
			payment.setMember(member);
			payment.setPayer(resultMap.get("buyer_email"));// 付款人
			payment.setAccount(seller);// 收款账号
			payment.setPaymentDate(gmtPayment);// 支付日期
			payment.setModifyDate(new Date());
			payment.setStatus(Payment.Status.success);
			payment.setMemo("APP预存款充值");
			paymentService.handle(payment);
			if (isError)
				throw new Exception("注意，您支付的实际金额与充值面额有差异");

			model.put("success", 2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}

	/**
	 * 生成预存款--会员充值
	 * 
	 * @author cgd 2014/10/13
	 * @param member
	 * @param credit
	 * @return
	 */
	private Deposit createDeposit(Member member, Payment payment, BigDecimal credit,
			String tradeNo) {
		Deposit deposit = new Deposit();
		deposit.setMember(member);
		deposit.setPayment(payment);
		deposit.setCreateDate(new Date());
		deposit.setType(Deposit.Type.memberRecharge);// 会员充值
		deposit.setCredit(credit);// 收入
		deposit.setBalance(member.getBalance());// 当前余额
		deposit.setTradeNo(tradeNo);// 支付宝交易号
		deposit.setMemo("APP预存款充值");// 备注
		return deposit;
	}
}