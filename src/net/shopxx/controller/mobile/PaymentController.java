/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.mobile;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopxx.Setting;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.PaymentStatus;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Payment;
import net.shopxx.entity.Payment.Method;
import net.shopxx.entity.Payment.Status;
import net.shopxx.entity.Payment.Type;
import net.shopxx.entity.PaymentMethod;
import net.shopxx.entity.Sn;
import net.shopxx.plugin.PaymentPlugin;
import net.shopxx.plugin.PaymentPlugin.NotifyMethod;
import net.shopxx.plugin.wap_unionpay.util.MypayUtil;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderService;
import net.shopxx.service.PaymentService;
import net.shopxx.service.PluginService;
import net.shopxx.service.SnService;
import net.shopxx.util.SettingUtils;
import net.shopxx.weixin.WeixinPayConfig;
import net.shopxx.weixin.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.wxap.util.Sha1Util;

/**
 * Controller - 支付
 * 
 * @author Guoxianlong
 * @date 2014/05/07
 */
@Controller("mobilePaymentController")
@RequestMapping("/mobile/payment")
public class PaymentController extends BaseController {

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	
	/**
	 * 提交m
	 */
	@RequestMapping(value = "/toSubmit", method = RequestMethod.GET)
	public String toSubmit(String sn ,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Member member = memberService.getCurrent();
		if (member == null) {
			return ERROR_VIEW;
		}
		Payment payment = new Payment();
		Order order = orderService.findBySn(sn);
		if (order == null || !member.equals(order.getMember()) || order.isExpired() || order.isLocked(null)) {
			return ERROR_VIEW;
		}
		if (order.getPaymentMethod() == null || order.getPaymentMethod().getMethod() != PaymentMethod.Method.online) {
			return ERROR_VIEW;
		}
		if (order.getPaymentStatus() != PaymentStatus.unpaid && order.getPaymentStatus() != PaymentStatus.partialPayment) {
			return ERROR_VIEW;
		}
		if (order.getAmountPayable().compareTo(new BigDecimal(0)) <= 0) {
			return ERROR_VIEW;
		}
		payment.setSn(snService.generate(Sn.Type.payment));
		payment.setType(Type.payment);
		payment.setMethod(Method.online);
		payment.setStatus(Status.wait);
		payment.setPaymentMethod(order.getPaymentMethodName() + Payment.PAYMENT_METHOD_SEPARATOR + "手机支付宝支付");
		payment.setAmount(order.getAmountPayable());
		payment.setFee(new BigDecimal(0));
//		payment.setPaymentPluginId(paymentPluginId);
//		payment.setExpire(paymentPlugin.getTimeout() != null ? DateUtils.addMinutes(new Date(), paymentPlugin.getTimeout()) : null);
		payment.setOrder(order);
		paymentService.save(payment);
		model.addAttribute("sn", sn);
		model.addAttribute("sum_amount", order.getAmountPayable());
		model.addAttribute("paymentSn", payment.getSn());
		return "mobile/member/payment/submit";
	}

	/**
	 * 提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(Type type, String paymentPluginId, String sn, BigDecimal amount, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Member member = memberService.getCurrent();
		if (member == null) {
			return ERROR_VIEW;
		}
		PaymentPlugin paymentPlugin = pluginService.getPaymentPlugin(paymentPluginId);
		if (paymentPlugin == null || !paymentPlugin.getIsEnabled()) {
			return ERROR_VIEW;
		}
		Payment payment = new Payment();
		String description = null;
		if (type == Type.payment) {
			Order order = orderService.findBySn(sn);
			if (order == null || !member.equals(order.getMember()) || order.isExpired() || order.isLocked(null)) {
				return ERROR_VIEW;
			}
			if (order.getPaymentMethod() == null || order.getPaymentMethod().getMethod() != PaymentMethod.Method.online) {
				return ERROR_VIEW;
			}
			if (order.getPaymentStatus() != PaymentStatus.unpaid && order.getPaymentStatus() != PaymentStatus.partialPayment) {
				return ERROR_VIEW;
			}
			if (order.getAmountPayable().compareTo(new BigDecimal(0)) <= 0) {
				return ERROR_VIEW;
			}
			payment.setSn(snService.generate(Sn.Type.payment));
			payment.setType(Type.payment);
			payment.setMethod(Method.online);
			payment.setStatus(Status.wait);
			payment.setPaymentMethod(order.getPaymentMethodName() + Payment.PAYMENT_METHOD_SEPARATOR + paymentPlugin.getPaymentName());
			payment.setFee(paymentPlugin.calculateFee(order.getAmountPayable()));
			payment.setAmount(paymentPlugin.calculateAmount(order.getAmountPayable()));
			payment.setPaymentPluginId(paymentPluginId);
			payment.setExpire(paymentPlugin.getTimeout() != null ? DateUtils.addMinutes(new Date(), paymentPlugin.getTimeout()) : null);
			payment.setOrder(order);
			paymentService.save(payment);
			description = order.getName();
		} else if (type == Type.recharge) {
			Setting setting = SettingUtils.get();
			if (amount == null || amount.compareTo(new BigDecimal(0)) <= 0 || amount.precision() > 15 || amount.scale() > setting.getPriceScale()) {
				return ERROR_VIEW;
			}
			payment.setSn(snService.generate(Sn.Type.payment));
			payment.setType(Type.recharge);
			payment.setMethod(Method.online);
			payment.setStatus(Status.wait);
			payment.setPaymentMethod(paymentPlugin.getPaymentName());
			payment.setFee(paymentPlugin.calculateFee(amount));
			payment.setAmount(paymentPlugin.calculateAmount(amount));
			payment.setPaymentPluginId(paymentPluginId);
			payment.setExpire(paymentPlugin.getTimeout() != null ? DateUtils.addMinutes(new Date(), paymentPlugin.getTimeout()) : null);
			payment.setMember(member);
			paymentService.save(payment);
			description = message("shop.member.deposit.recharge");
		} else {
			return ERROR_VIEW;
		}
		model.addAttribute("requestUrl", paymentPlugin.getRequestUrl());
		model.addAttribute("requestMethod", paymentPlugin.getRequestMethod());
		model.addAttribute("requestCharset", paymentPlugin.getRequestCharset());
		model.addAttribute("parameterMap", paymentPlugin.getParameterMap(payment.getSn(), description, request));
		if (StringUtils.isNotEmpty(paymentPlugin.getRequestCharset())) {
			response.setContentType("text/html; charset=" + paymentPlugin.getRequestCharset());
		}
		return "shop/payment/submit";
	}

	/**
	 * 通知
	 */
	@RequestMapping("/notify/{notifyMethod}/{sn}")
	public String notify(@PathVariable NotifyMethod notifyMethod, @PathVariable String sn, HttpServletRequest request, ModelMap model) {
		Payment payment = paymentService.findBySn(sn);
		if (payment != null) {
			PaymentPlugin paymentPlugin = pluginService.getPaymentPlugin(payment.getPaymentPluginId());
			if (paymentPlugin != null) {
				if (paymentPlugin.verifyNotify(sn, notifyMethod, request)) {
					paymentService.handle(payment);
				}
				model.addAttribute("notifyMessage", paymentPlugin.getNotifyMessage(sn, notifyMethod, request));
			}
			model.addAttribute("payment", payment);
		}
		return "shop/payment/notify";
	}
	
	/**
	 * 支付宝支付成功之后的回调处理(操作订单表) 更改订单信息
	 * author: Guoxianlong
	 * date: 2014/5/28
	 * 
	 * @param trade_no 		支付宝交易号
	 * @param gmt_payment	支付宝支付时间
	 * @param orderCode		商户订单号
	 * @param result		支付宝支付状态
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String verify(String trade_no, String gmt_payment, String orderCode, String result, HttpServletRequest request, ModelMap model) throws ParseException {
		if("success".equals(result)) {
			String[] str = orderCode.split("_");
			String orderSn = str[0];
			String paymentSn = str[1];
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date modifyDate = sdf.parse(gmt_payment);
			Order order = orderService.findBySn(orderSn);
			order.setModifyDate(modifyDate);
			order.setOrderStatus(OrderStatus.confirmed);//支付成功，修改订单状态   wmd 2014/12/3
			order.setPaymentStatus(PaymentStatus.paid);
			List<OrderItem> orderItems = order.getOrderItems();
			BigDecimal totalPrice = new BigDecimal(0);
			for(int i=0;i<orderItems.size();i++){
				Integer quantity = orderItems.get(i).getQuantity();
				BigDecimal t = new BigDecimal(quantity);
				BigDecimal price = orderItems.get(i).getProduct().getPrice();
				totalPrice = totalPrice.add(price.multiply(t));
			}
			order.setAmountPaid(totalPrice);
			orderService.update(order);

			Payment payment = paymentService.findBySn(paymentSn);
			payment.setStatus(Status.success);
			payment.setPaymentDate(new Date());
			paymentService.update(payment);
			return "mobile/member/index";
		}
		return null;
	}
	
	@RequestMapping(value = "/toVerify", method = RequestMethod.GET)
	public String toVerify(Long argName, String ordertime , String ordersn ,ModelMap model) throws ParseException{
		if(argName == 0){
			if(MypayUtil.query(ordertime, ordersn)){
				Order order = orderService.findBySn(ordersn);
				if(!Order.OrderStatus.completed.equals(order.getOrderStatus())){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					Date modifyDate = sdf.parse(ordertime);
					order.setModifyDate(modifyDate);
					//order.setOrderStatus(OrderStatus.completed);
					order.setPaymentStatus(PaymentStatus.paid);
					order.setAmountPaid(new BigDecimal(0));
					orderService.update(order);
					return "redirect:/mobile/member/index.jhtml";
				}
			}
			return "redirect:/mobile/index.jhtml";
		}
		return "redirect:/mobile/index.jhtml";
	}
	
	/**
	 * @Description 微信支付成功之后的回调处理(操作订单表) 更改订单信息
	 * @author Guoxianlong
	 * @create_date Oct 8, 201410:09:14 AM
	 */
	@RequestMapping(value = "/weixinVerify", method = RequestMethod.POST)
	public String weixinVerify(HttpServletRequest request, ModelMap model) throws ParseException {
//		System.out.println("=======weixinVerify=======");
		String result = (String) request.getAttribute("result");
		String sn = (String) request.getAttribute("out_trade_no");
		String transaction_id = (String) request.getAttribute("transaction_id");
		String total_fee = (String) request.getAttribute("total_fee");
		//System.out.println("===result: "+result+"、sn: "+sn+"、transaction_id: "+transaction_id+"、total_fee: "+total_fee);
		
		Order order = orderService.findBySn(sn);
		Payment payment = new Payment();
		if (order == null || order.isExpired() || order.isLocked(null)) {
			return ERROR_VIEW;
		}
		if (order.getPaymentMethod() == null || order.getPaymentMethod().getMethod() != PaymentMethod.Method.online) {
			return ERROR_VIEW;
		}
		if (order.getPaymentStatus() != PaymentStatus.unpaid && order.getPaymentStatus() != PaymentStatus.partialPayment) {
			return ERROR_VIEW;
		}
		if (order.getAmountPayable().compareTo(new BigDecimal(0)) <= 0) {
			return ERROR_VIEW;
		}
		String openid = getOpenId(result);
		payment.setOpenid(openid);
		payment.setTransaction_id(transaction_id);
		payment.setSn(snService.generate(Sn.Type.payment));
		payment.setType(Type.payment);
		payment.setMethod(Method.online);
		payment.setStatus(Status.success);
		payment.setPaymentMethod(order.getPaymentMethodName() + Payment.PAYMENT_METHOD_SEPARATOR + "微信支付");
		payment.setAmount(order.getAmountPayable());
		payment.setFee(new BigDecimal(0));
		payment.setOrder(order);
		payment.setPaymentDate(new Date());
		paymentService.save(payment);
		
		order.setModifyDate(new Date());
		order.setOrderStatus(OrderStatus.confirmed);
		order.setPaymentStatus(PaymentStatus.paid);
		/** TODO 以下方法的处理不是很合理，需要改进  **/
		List<OrderItem> orderItems = order.getOrderItems();
		BigDecimal totalPrice = new BigDecimal(0);
		for(int i=0;i<orderItems.size();i++){
			Integer quantity = orderItems.get(i).getQuantity();
			BigDecimal t = new BigDecimal(quantity);
			BigDecimal price = orderItems.get(i).getProduct().getPrice();
			totalPrice = totalPrice.add(price.multiply(t));
		}
		order.setAmountPaid(totalPrice);
		/**  **/
		orderService.update(order);
		
		for(int i = 0; i< 5; i++) {
			String res = delivery(openid, transaction_id, sn);
			Map<String, Object> map = pareObject(res);
			String errcode = (String)map.get("errcode");
			String errmsg = (String)map.get("errmsg");
//			System.out.println("====errcode: "+errcode);
//			System.out.println("====errmsg: "+errmsg);
			if("0".equals(errcode) && "ok".equals(errmsg)) {
				break;
			}
		}
		
		return "mobile/member/index";
	}
	
	/**
	 * 解析微信返回回来的xml数据 得到openid
	 * @author Guoxianlong
	 * @date Oct 17, 2014 10:02:57 AM
	 */
	public String getOpenId(String strXml) {
		Document doc = null;
		try {
            doc = DocumentHelper.parseText(strXml); // 将字符串转为XML
            Element rootE = doc.getRootElement(); // 获取根节点
            String openid = rootE.elementText("OpenId");
//            System.out.println("====openid: "+openid);
            return openid;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(doc != null) doc.clearContent();
        }
        return null;
	}
	
	/**
	 * 发货处理 - 用户微信支付完成后，需手动向微信发送一个 发货的消息 ，否则 24小时后 系统将做超时处理
	 * @author Guoxianlong
	 * @date Oct 17, 2014 10:07:07 AM
	 */
	public String delivery(String openid, String transaction_id, String sn) {
		String  respStr = null;
		String timestamp = Sha1Util.getTimeStamp();
		//设置支付参数
		SortedMap<String, String> signParams = new TreeMap<String, String>();
		signParams.put("appid", WeixinPayConfig.APP_ID);
		signParams.put("appkey", WeixinPayConfig.APP_KEY);
		signParams.put("openid", openid);
		signParams.put("transid", transaction_id);
		signParams.put("out_trade_no", sn);
		signParams.put("deliver_timestamp",timestamp);
		signParams.put("deliver_status","1");
		signParams.put("deliver_msg","OK");
		//生成支付签名，要采用URLENCODER的原始值进行SHA1算法！
		String sign = null;
		try {
			sign = Sha1Util.createSHA1Sign(signParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String authStr = WeixinUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token","grant_type=client_credential&appid="+WeixinPayConfig.APP_ID+"&secret="+WeixinPayConfig.APP_SECRET);
		Map authMap = WeixinUtil.parseResp(authStr);
		if(null != authMap){
			String token = (String)authMap.get("access_token");
//			System.out.println("====token:"+token);
//			System.out.println("===sign:"+sign);

			Map paramMap = new Hashtable(); 
			paramMap.put("appid",WeixinPayConfig.APP_ID);
			signParams.put("appkey", WeixinPayConfig.APP_KEY);
			paramMap.put("openid", openid);
			paramMap.put("transid", transaction_id);
			paramMap.put("out_trade_no", sn);
			paramMap.put("deliver_timestamp",timestamp);
			paramMap.put("deliver_status","1");
			paramMap.put("deliver_msg","OK");
			paramMap.put("app_signature",sign);
			paramMap.put("sign_method","sha1");

			respStr = WeixinUtil.postData("https://api.weixin.qq.com/pay/delivernotify?access_token="+token,paramMap);
		}
		return respStr;
	}
	
	/**
	 * 向微信发送了 发货的消息之后 的返回数据转成map数据
	 * @author Guoxianlong
	 * @date Oct 17, 2014 2:07:09 PM
	 */
	public static Map<String, Object> pareObject(String str) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<HashMap<String, Object>> tmpList;
			JSONObject jObj = JSONObject.fromObject(str);
			Iterator<String> it = jObj.keys();
			String key;
			Object obj;

			while (it.hasNext()) {
				key = it.next();
				obj = jObj.get(key);
				if (obj instanceof JSONArray) {
					tmpList = parsArray((JSONArray) obj);
					if (tmpList != null && tmpList.size() != 0) {
						map.put(key.toLowerCase(), tmpList);
					}
				}
				else {
					map.put(key.toLowerCase(), jObj.get(key).toString());
				}
			}
			return map;
	}
	
	public static List<HashMap<String, Object>> parsArray(JSONArray arry) {
		ArrayList<HashMap<String, Object>> listCont = null;
		HashMap<String, Object> hm;
		JSONObject jobj;
		int size = 0;
		if (arry != null) {
			size = arry.size();
		}
		if (size != 0) {
			listCont = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < size; i++) {
				jobj = arry.getJSONObject(i);
				if (jobj != null && jobj.size() != 0) {
					hm = new HashMap<String, Object>();
					hm.putAll(pareObject(jobj.toString()));
					listCont.add(hm);
				}
			}
		}
		return listCont;
	}

}