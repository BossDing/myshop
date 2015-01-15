/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.Setting.AccountLockType;
import net.shopxx.entity.Member;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderService;
import net.shopxx.service.ProductNotifyService;
import net.shopxx.service.ProductService;
import net.shopxx.service.ReceiverService;
import net.shopxx.service.ReviewService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.TwUtil;
import net.shopxx.util.app.alipay.config.AlipayConfig;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("APPLoginController")
@RequestMapping("/m/login")
public class LoginController extends BaseController {
	
	private static final int PAGE_SIZE = 20;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "productNotifyServiceImpl")
	private ProductNotifyService productNotifyService;
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	@Resource(name = "reviewServiceImpl")
	private ReviewService reviewService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
 
	/**
	 * 登录检测
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public @ResponseBody
	Boolean check() {
		return memberService.isAuthenticated();
	}
	
	/**
	 * 切换登录
	 * @throws Exception 
	 */
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public @ResponseBody
	ModelMap index(Long userid, Integer pageNumber, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap model = new ModelMap();
		try {
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
			
			Long waitingPaymentOrderCount = orderService.waitingPaymentCount(member);
			Long waitingShippingOrderCount = orderService.waitingShippingCount(member);
			Long productNotifyCount = productNotifyService.count(member, null, null, null);
			int receiveCount = receiverService.findPage(member, pageable).getContent().size();
			int favoriteCount = productService.findPage(member, pageable).getContent().size();

			model.put("balance", member.getBalance().floatValue());							//余额
			model.put("amount", member.getAmount().floatValue());							//已消费金额
			model.put("point", member.getPoint().toString());								//积分
			
			model.put("waitingPaymentOrderCount", waitingPaymentOrderCount);	//等待支付订单数量 
			model.put("waitingShippingOrderCount", waitingShippingOrderCount);	//等待发货订单数量 
			model.put("productNotifyCount", productNotifyCount);				//到货通知数量
			model.put("reviewCount", 0);								//待评价数量
			model.put("receiveCount", receiveCount);							//收货地址数量
			model.put("favoriteCount", favoriteCount);							//商品收藏数量
			model.put("success", 2);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * 登录提交
	 * @throws Exception 
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody
	ModelMap submit(String username, String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelMap model = new ModelMap();
		try {
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			username = (String) obj.get("username");
			password = (String) obj.get("password");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
//			return Message.error("shop.common.invalid");
			model.put("error", "参数错误");
			model.put("success", 1);
			return model;
		}
		Member member;
		Setting setting = SettingUtils.get();
		if (setting.getIsEmailLogin() && username.contains("@")) {
			List<Member> members = memberService.findListByEmail(username);
			if (members.isEmpty()) {
				member = null;
			} else if (members.size() == 1) {
				member = members.get(0);
			} else {
//				return Message.error("shop.login.unsupportedAccount");
				model.put("success", 1);
				return model;
			}
		} else {
			member = memberService.findByUsername(username);
		}
		if (member == null) {
//			return Message.error("shop.login.unknownAccount");
			model.put("error", "此账号不存在");
			model.put("success", 1);
			return model;
		}
		if (!member.getIsEnabled()) {
//			return Message.error("shop.login.disabledAccount");
			model.put("error", "此账号已被禁用");
			model.put("success", 1);
			return model;
		}
		if (member.getIsLocked()) {
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.member)) {
				int loginFailureLockTime = setting.getAccountLockTime();
				if (loginFailureLockTime == 0) {
//					return Message.error("shop.login.lockedAccount");
					model.put("error", "此账号已被锁定");
					model.put("success", 1);
					return model;
				}
				Date lockedDate = member.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				if (new Date().after(unlockDate)) {
					member.setLoginFailureCount(0);
					member.setIsLocked(false);
					member.setLockedDate(null);
					memberService.update(member);
				} else {
//					return Message.error("shop.login.lockedAccount");
					model.put("error", "此账号已被锁定");
					model.put("success", 1);
					return model;
				}
			} else {
				member.setLoginFailureCount(0);
				member.setIsLocked(false);
				member.setLockedDate(null);
				memberService.update(member);
			}
		}
		
		if (!DigestUtils.md5Hex(password).equals(member.getPassword())) {
			int loginFailureCount = member.getLoginFailureCount() + 1;
			if (loginFailureCount >= setting.getAccountLockCount()) {
				member.setIsLocked(true);
				member.setLockedDate(new Date());
			}
			member.setLoginFailureCount(loginFailureCount);
			memberService.update(member);
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.member)) {
//				return Message.error("shop.login.accountLockCount", setting.getAccountLockCount());
				model.put("error", "此账号已被锁定");
				model.put("success",1);
				return model;
			} else {
//				return Message.error("shop.login.incorrectCredentials");
				model.put("error", "用户名或密码错误");
				model.put("success", 1);
				return model;
			}
		}
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setLoginFailureCount(0);
		memberService.update(member);
		
		System.out.println("app submit : userid : "+TwUtil.encryptStr(member.getId().toString()));
		model.put("userid", TwUtil.encryptStr(member.getId().toString()));	//userid
		model.put("username", member.getUsername());						//用户名
		model.put("mobile", member.getMobile());							//手机号
		model.put("memberRank", member.getMemberRank().getName());			//会员等级
		model.put("default_partner", TwUtil.encodeHex(AlipayConfig.partner.getBytes()));
		model.put("default_seller", TwUtil.encodeHex(AlipayConfig.seller.getBytes()));
		model.put("private", TwUtil.encodeHex(AlipayConfig.key.getBytes()));
		model.put("success", 2);
		return model;
	}

}