/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app.member;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.Setting.ReviewAuthority;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Cart;
import net.shopxx.entity.CartItem;
import net.shopxx.entity.Coupon;
import net.shopxx.entity.CouponCode;
import net.shopxx.entity.Deposit;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.PaymentStatus;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Payment;
import net.shopxx.entity.Payment.Status;
import net.shopxx.entity.PaymentMethod;
import net.shopxx.entity.Product;
import net.shopxx.entity.Receiver;
import net.shopxx.entity.Review;
import net.shopxx.entity.Shipping;
import net.shopxx.entity.ShippingMethod;
import net.shopxx.entity.Sn;
import net.shopxx.plugin.PaymentPlugin;
import net.shopxx.service.AreaService;
import net.shopxx.service.CartService;
import net.shopxx.service.CouponCodeService;
import net.shopxx.service.DepositService;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderService;
import net.shopxx.service.PaymentMethodService;
import net.shopxx.service.PaymentService;
import net.shopxx.service.PluginService;
import net.shopxx.service.ProductService;
import net.shopxx.service.ReceiverService;
import net.shopxx.service.ReviewService;
import net.shopxx.service.ShippingMethodService;
import net.shopxx.service.ShippingService;
import net.shopxx.service.SnService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.TwUtil;
import net.shopxx.util.app.alipay.config.AlipayConfig;
import net.shopxx.util.app.alipay.util.AlipaySingleQuery;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 订单
 * @author: Guoxianlong
 * @date: Sep 4, 2014  8:42:01 AM
 */
@Controller("APPMemberOrderController")
@RequestMapping("/m/member/order")
public class OrderController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "shippingServiceImpl")
	private ShippingService shippingService;
	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;
	@Resource(name = "reviewServiceImpl")
	private ReviewService reviewService;
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	
	/**
	 * 获取信息
	 */
	@RequestMapping(value = "/info",method = RequestMethod.POST)
	public @ResponseBody ModelMap info(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		int totalQuantity = 0;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> receiverList = new ArrayList<HashMap<String, Object>>();
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			ArrayList<HashMap<String, Object>> paymentmethodList = new ArrayList<HashMap<String, Object>>();
			ArrayList<HashMap<String, Object>> shippingmethodList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			System.out.println("userid: "+(String) obj.get("userid"));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Cart cart = cartService.getCart(member);
			if (cart == null || cart.isEmpty()) {
				model.put("success", 1);
				model.put("error", "温馨提示: 购物车已经空了! ");
				return model;
			}
			HashMap<String, Object> receiverMap = new HashMap<String, Object>();
			if(receiverService.findOneForMobile(member, new Long(0)).size() > 0) {
				for(Receiver receiver: receiverService.findOneForMobile(member, new Long(0))) {
					receiverMap.put("id", receiver.getId());
					receiverMap.put("consignee", receiver.getConsignee());
					receiverMap.put("address", receiver.getArea().getFullName()+receiver.getAddress());
					receiverMap.put("phone", receiver.getPhone());
				}
			}
			receiverList.add(receiverMap);
			for(CartItem item : cart.getCartItems()) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("image", item.getProduct().getThumbnail());
				productMap.put("fullname", item.getProduct().getFullName());
				productMap.put("price", item.getProduct().getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				productMap.put("quantity", item.getQuantity());
				totalQuantity =+ item.getQuantity();
				productList.add(productMap);
			}

			for(PaymentMethod pm : paymentMethodService.findAll()) {
				HashMap<String, Object> PaymentMethodMap = new HashMap<String, Object>();
				PaymentMethodMap.put("id", pm.getId());
				PaymentMethodMap.put("name", pm.getName());
				paymentmethodList.add(PaymentMethodMap);
			}
			for(ShippingMethod sm: shippingMethodService.findAll()) {
				HashMap<String, Object> ShippingMethodMap = new HashMap<String, Object>();
				ShippingMethodMap.put("id", sm.getId());
				ShippingMethodMap.put("name", sm.getName());
				shippingmethodList.add(ShippingMethodMap);
			}
			for(CouponCode code : couponCodeService.findPage(member, new Pageable(1, 20)).getContent()) {
				
			}
			
			Order order = orderService.build(cart, null, null, null, null, false, null, false, null);
			
			model.put("producttotalprice", order.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 				//商品总价
			model.put("freight", order.getFreight().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 						//运费
			model.put("promotiondiscount", order.getPromotionDiscount().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 	//促销折扣
			model.put("totalQuantity", totalQuantity);																		//商品的总量
			model.put("balance", member.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());						//我的余额
			model.put("amountpayable", order.getAmountPayable().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 			//应付金额
			model.put("point", order.getPoint());																			//赠送积分
			
			model.put("products", productList);																				//商品数据
			model.put("receivers", receiverList);																			//收货地址
			model.put("paymentmethods", paymentmethodList);																	//支付方式
			model.put("shippingmethods", shippingmethodList);																//配送方式
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 创建订单
	 */
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	public @ResponseBody ModelMap create(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Cart cart = cartService.getCart(member);
			
			Long receiverId = null;
			Long paymentMethodId = null;
			Long shippingMethodId = null;
			String code = null;
			Boolean isInvoice = false;
			String invoiceTitle = null;
			Boolean useBalance = false;
			String memo = null;
			
			try {
				receiverId = Long.parseLong((String) obj.get("receiverid"));
				paymentMethodId = Long.parseLong((String) obj.get("paymentmethodid"));
				shippingMethodId = Long.parseLong((String) obj.get("shippingmethodid"));
				code = (String) obj.get("code");
				isInvoice = Boolean.parseBoolean((String) obj.get("isinvoice"));
				invoiceTitle = (String) obj.get("invoicetitle");
				useBalance = Boolean.parseBoolean((String) obj.get("usebalance"));
				memo = (String) obj.get("memo");
			} catch(Exception e) {
				
			} finally {
				if (cart == null || cart.isEmpty()) {
					model.put("success", 1);
					model.put("error", "操作错误, 购物车为空！");
					return model;
				}
				if(receiverId == null) {
					model.put("error", "请选择收货地址 谢谢！");
					model.put("success", 1);
					return model;
				}
				if(paymentMethodId == null) {
					model.put("error", "请选择支付方式 谢谢！");
					model.put("success", 1);
					return model;
				}
				if(shippingMethodId == null) {
					model.put("error", "请选择配送方式 谢谢！");
					model.put("success", 1);
					return model;
				}
			}
			Receiver receiver = receiverService.find(receiverId);
			PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
			ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
			CouponCode couponCode = couponCodeService.findByCode(code);
			Order order = orderService.create(cart, receiver, paymentMethod, shippingMethod, couponCode, isInvoice, invoiceTitle, useBalance, memo, null);
			
			model.put("sn", order.getSn());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	
	/**
	 * 获取所有的订单 (收银台)
	 */
	@RequestMapping(value = "/getAll",method = RequestMethod.POST)
	public @ResponseBody ModelMap getAll(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> orderList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));
			
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			int flag = Integer.parseInt((String) obj.get("flag"));

			Pageable pageable = new Pageable(pageNumber, pageSize);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			if(flag == 1) {
				for(Order o : orderService.findPage(member, pageable).getContent()) {
					HashMap<String, Object> orderMap = new HashMap<String, Object>();
					orderMap.put("id", o.getId());
					orderMap.put("sn", o.getSn());
					orderMap.put("fullname", o.getOrderItems().get(0).getFullName());
//					orderMap.put("paymentstatus", o.getPaymentStatus().toString());
//					orderMap.put("orderstatus1", o.getOrderStatus().toString());
//					orderMap.put("shippingstatus", o.getShippingStatus().toString());
					orderMap.put("orderstatus", TwUtil.getOrderStatus(o, o.getOrderStatus().toString(), o.getPaymentStatus().toString(), o.getShippingStatus().toString()));
					orderMap.put("image", o.getOrderItems().get(0).getThumbnail());
					orderList.add(orderMap);
				}
			} else if(flag == 2) {
				for(Order o : orderService.findUnpaidPage(member, pageable).getContent()) {
					if(!o.isExpired()) {	//过滤掉已过期的订单
						HashMap<String, Object> orderMap = new HashMap<String, Object>();
						orderMap.put("id", o.getId());
						orderMap.put("sn", o.getSn());
						orderMap.put("fullname", o.getOrderItems().get(0).getFullName());
						orderMap.put("orderstatus", TwUtil.getOrderStatus(o, o.getOrderStatus().toString(), o.getPaymentStatus().toString(), o.getShippingStatus().toString()));
						orderMap.put("image", o.getOrderItems().get(0).getThumbnail());
						orderList.add(orderMap);
					}
				}
			} else if(flag == 3) {
				for(Order o : orderService.waitingShipping(member, pageable).getContent()) {
					if(!o.isExpired()) {	//过滤掉已过期的订单
						HashMap<String, Object> orderMap = new HashMap<String, Object>();
						orderMap.put("id", o.getId());
						orderMap.put("sn", o.getSn());
						orderMap.put("fullname", o.getOrderItems().get(0).getFullName());
						orderMap.put("orderstatus", TwUtil.getOrderStatus(o, o.getOrderStatus().toString(), o.getPaymentStatus().toString(), o.getShippingStatus().toString()));
						orderMap.put("image", o.getOrderItems().get(0).getThumbnail());
						
						orderList.add(orderMap);
					}
				}
			} else if(flag == 4) {
				for(Order o : orderService.waitingReceiving(member, pageable).getContent()) {
					if(!o.isExpired()) {	//过滤掉已过期的订单
						HashMap<String, Object> orderMap = new HashMap<String, Object>();
						orderMap.put("id", o.getId());
						orderMap.put("sn", o.getSn());
						orderMap.put("fullname", o.getOrderItems().get(0).getFullName());
						orderMap.put("orderstatus", TwUtil.getOrderStatus(o, o.getOrderStatus().toString(), o.getPaymentStatus().toString(), o.getShippingStatus().toString()));
						orderMap.put("image", o.getOrderItems().get(0).getThumbnail());
						orderList.add(orderMap);
					}
				}
			} else if(flag == 5) {
				for(Order o : orderService.findPage(member, pageable).getContent()) {
					int status = TwUtil.getOrderStatus(o, o.getOrderStatus().toString(), o.getPaymentStatus().toString(), o.getShippingStatus().toString());
					if(1 == status) {
						HashMap<String, Object> orderMap = new HashMap<String, Object>();
						orderMap.put("id", o.getId());
						orderMap.put("sn", o.getSn());
						orderMap.put("fullname", o.getOrderItems().get(0).getFullName());
						orderMap.put("orderstatus", TwUtil.getOrderStatus(o, o.getOrderStatus().toString(), o.getPaymentStatus().toString(), o.getShippingStatus().toString()));
						orderMap.put("image", o.getOrderItems().get(0).getThumbnail());
						orderList.add(orderMap);
					}
				}
			}
			
			model.put("datas", orderList);
			model.put("length", orderList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取待付款订单
	 */
	@RequestMapping(value = "/waitingPaymentOrder",method = RequestMethod.POST)
	public @ResponseBody ModelMap waitingPaymentOrder(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> orderList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			
			Pageable pageable = new Pageable(pageNumber, pageSize);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			for(Order o : orderService.findUnpaidPage(member, pageable).getContent()) {
				if(!o.isExpired()) {	//过滤掉已过期的订单
					HashMap<String, Object> orderMap = new HashMap<String, Object>();
					orderMap.put("id", o.getId());
					orderMap.put("sn", o.getSn());
					orderMap.put("fullname", o.getOrderItems().get(0).getFullName());
					orderMap.put("paymentstatus", o.getPaymentStatus().toString());
					orderMap.put("orderstatus1", o.getOrderStatus().toString());
					orderMap.put("shippingstatus", o.getShippingStatus().toString());
					orderMap.put("orderstatus", TwUtil.getOrderStatus(o, o.getOrderStatus().toString(), o.getPaymentStatus().toString(), o.getShippingStatus().toString()));
					orderMap.put("image", o.getOrderItems().get(0).getThumbnail());
					
					orderList.add(orderMap);
				}
			}
			model.put("datas", orderList);
			model.put("length", orderList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	
	/**
	 * 获取待发货的订单
	 */
	@RequestMapping(value = "/waitingShippingOrder",method = RequestMethod.POST)
	public @ResponseBody ModelMap waitingShippingOrder(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> orderList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			
			Pageable pageable = new Pageable(pageNumber, pageSize);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			for(Order o : orderService.waitingShipping(member, pageable).getContent()) {
				if(!o.isExpired()) {	//过滤掉已过期的订单
					HashMap<String, Object> orderMap = new HashMap<String, Object>();
					orderMap.put("id", o.getId());
					orderMap.put("sn", o.getSn());
					orderMap.put("fullname", o.getOrderItems().get(0).getFullName());
					orderMap.put("paymentstatus", o.getPaymentStatus().toString());
					orderMap.put("orderstatus1", o.getOrderStatus().toString());
					orderMap.put("shippingstatus", o.getShippingStatus().toString());
					orderMap.put("orderstatus", TwUtil.getOrderStatus(o, o.getOrderStatus().toString(), o.getPaymentStatus().toString(), o.getShippingStatus().toString()));
					orderMap.put("image", o.getOrderItems().get(0).getThumbnail());
					
					orderList.add(orderMap);
				}
			}
			model.put("datas", orderList);
			model.put("length", orderList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取待收货的订单
	 */
	@RequestMapping(value = "/waitingReceivingOrder",method = RequestMethod.POST)
	public @ResponseBody ModelMap waitingReceivingOrder(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> orderList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			
			Pageable pageable = new Pageable(pageNumber, pageSize);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			for(Order o : orderService.waitingReceiving(member, pageable).getContent()) {
				if(!o.isExpired()) {	//过滤掉已过期的订单
					HashMap<String, Object> orderMap = new HashMap<String, Object>();
					orderMap.put("id", o.getId());
					orderMap.put("sn", o.getSn());
					orderMap.put("fullname", o.getOrderItems().get(0).getFullName());
					orderMap.put("paymentstatus", o.getPaymentStatus().toString());
					orderMap.put("orderstatus1", o.getOrderStatus().toString());
					orderMap.put("shippingstatus", o.getShippingStatus().toString());
					orderMap.put("orderstatus", TwUtil.getOrderStatus(o, o.getOrderStatus().toString(), o.getPaymentStatus().toString(), o.getShippingStatus().toString()));
					orderMap.put("image", o.getOrderItems().get(0).getThumbnail());
					
					orderList.add(orderMap);
				}
			}
			model.put("datas", orderList);
			model.put("length", orderList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取订单详细信息
	 */
	@RequestMapping(value = "/getOrderInfo",method = RequestMethod.POST)
	public @ResponseBody ModelMap getOrderInfo(Long userid, String sn, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			sn = (String) obj.get("sn");
			
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Order order = orderService.findBySn(sn);
			if (order == null) {
				model.put("error", "订单为空");
				model.put("success", 1);
				return model;
			}
			
			for(OrderItem item : order.getOrderItems()) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("id", item.getProduct().getId());
				productMap.put("image", item.getProduct().getThumbnail());
				productMap.put("fullname", item.getProduct().getFullName());
				productMap.put("price", item.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				productMap.put("quantity", item.getQuantity());
				
				productList.add(productMap);
			}
			
			model.put("products", productList);
			model.put("length", productList.size());
			model.put("balance", member.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());		//我的余额
			model.put("orderstatus", TwUtil.getStringOrderStatus(order, order.getOrderStatus().toString(), order.getPaymentStatus().toString(), order.getShippingStatus().toString()));
			model.put("status", TwUtil.getOrderStatus(order, order.getOrderStatus().toString(), order.getPaymentStatus().toString(), order.getShippingStatus().toString()));
			model.put("producttotalprice", order.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 			//订单里的商品总价(订单金额)
			model.put("freight", order.getFreight().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 			//运费
			model.put("promotiondiscount", order.getPromotionDiscount().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 			//促销折扣
			model.put("amountpaid", order.getAmountPaid().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 			//已付金额
			model.put("amountpayable", order.getAmountPayable().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 			//应付金额
			model.put("consignee", order.getConsignee()); 		//收货人
			model.put("phone", order.getPhone()); 					//收货人手机号
			model.put("address", order.getAreaName()+order.getAddress()); 		//收货地址
			model.put("sn", order.getSn()); 		//订单号
			model.put("modifydate", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(order.getModifyDate())); 		//成交时间
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取数据去支付界面
	 */
	@RequestMapping(value = "/getInfoToPayment",method = RequestMethod.POST)
	public @ResponseBody ModelMap getInfoToPayment(Long userid, String sn, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			sn = (String) obj.get("sn");
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Order order = orderService.findBySn(sn);
			if (order == null) {
				model.put("error", "订单为空");
				model.put("success", 1);
				return model;
			}
			model.put("balance", member.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());		//我的余额
			model.put("amountpayable", order.getAmountPayable().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); 			//应付金额
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	/**
	 * 取消订单
	 */
	@RequestMapping(value = "/cancelOrder",method = RequestMethod.POST)
	public @ResponseBody ModelMap cancelOrder(Long userid, String sn, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			sn = (String) obj.get("sn");
			
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Order order = orderService.findBySn(sn);
			if (order == null) {
				model.put("error", "订单为空");
				model.put("success", 1);
				return model;
			}
			if (order != null && member.equals(order.getMember()) && !order.isExpired() && order.getOrderStatus() == OrderStatus.unconfirmed && order.getPaymentStatus() == PaymentStatus.unpaid) {
				if (order.isLocked(null)) {
					model.put("error", "该订单已被锁定,请稍候再试 谢谢!");
					model.put("success", 1);
					return model;
				}
				orderService.cancel(order, null);
			}
			model.put("orderstatus", TwUtil.getStringOrderStatus(order, order.getOrderStatus().toString(), order.getPaymentStatus().toString(), order.getShippingStatus().toString()));
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 余额支付或支付宝支付
	 */
	@RequestMapping(value = "/payment",method = RequestMethod.POST)
	public @ResponseBody ModelMap payment(Long userid, String sn, Boolean useBalance, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			sn = (String) obj.get("sn");
			useBalance = Boolean.valueOf((String) obj.get("usebalance"));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Order order = orderService.findBySn(sn);
			if (order == null) {
				model.put("error", "订单为空");
				model.put("success", 1);
				return model;
			}
			if(useBalance){//余额支付
				orderService.paymentOrderUseBalance(member, order, useBalance, null);
			}else{//支付宝支付
				alipayVerify("", order, member);
			}
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取订单里的商品 - 带评论数据
	 */
	@RequestMapping(value = "/getproductreview",method = RequestMethod.POST)
	public @ResponseBody ModelMap getProductReview(Long userid, String sn, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			sn = (String) obj.get("sn");
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));
			
			Pageable pageable = new Pageable(pageNumber, pageSize);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Order order = orderService.findBySn(sn);
			if (order == null) {
				model.put("error", "订单为空");
				model.put("success", 1);
				return model;
			}
			
			for(OrderItem item : order.getOrderItems()) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("productid", item.getProduct().getId());
				productMap.put("image", item.getProduct().getThumbnail());
				productMap.put("fullname", item.getProduct().getFullName());
				productMap.put("price", item.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				if(reviewService.findPage(member, item.getProduct(), null, true, pageable).getContent().size() > 0) {
					for(Review review : reviewService.findPage(member, item.getProduct(), null, true, pageable).getContent()) {
						productMap.put("reviewid", review.getId());
						productMap.put("score", review.getScore());
						productMap.put("content", review.getContent());
					}
				}
				productList.add(productMap);
			}
			model.put("datas", productList);
			model.put("length", productList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 保存评论数据 - 批量保存
	 */
	@RequestMapping(value = "/savereview",method = RequestMethod.POST)
	public @ResponseBody ModelMap savereview(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		Review review = null;
		try {
			model = new ModelMap();
//			ArrayList<HashMap<String, Object>> reviewList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Setting setting = SettingUtils.get();
			if (!setting.getIsReviewEnabled()) {
				model.put("error", "评论不可用!");
				model.put("success", 1);
				return model;
			}

			List<HashMap<String, Object>> datas = TwUtil.parsArray(obj.getJSONArray("datas"));
			for(HashMap<String, Object> hm : datas) {
//				HashMap<String, Object> reviewMap = new HashMap<String, Object>();
				review = new Review();
				Product product = productService.find(Long.parseLong((String) hm.get("productid")));
				if (setting.getReviewAuthority() == ReviewAuthority.purchased) {
					if (!productService.isPurchased(member, product)) {
						model.put("error", "评论不可用!");
						model.put("success", 1);
						return model;
					}
					if (reviewService.isReviewed(member, product)) {
						model.put("error", "您已评论过该商品, 不能重复评论!");
						model.put("success", 1);
						return model;
					}
				}
				review.setScore(Integer.parseInt((String)hm.get("score")));
				review.setContent((String) hm.get("content"));
				review.setIp(request.getRemoteAddr());
				review.setMember(member);
				review.setProduct(product);
				if (setting.getIsReviewCheck()) {
					review.setIsShow(false);
					reviewService.save(review);
				} else {
					review.setIsShow(true);
					reviewService.save(review);
				}
//				reviewMap.put("productid", product.getId());
//				reviewMap.put("image", product.getThumbnail());
//				reviewMap.put("fullname", product.getFullName());
//				reviewMap.put("price", product.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
//				reviewMap.put("reviewid", review.getId());
//				reviewMap.put("score", review.getScore());
//				reviewMap.put("content", review.getContent());
//				reviewList.add(reviewMap);
			}
			
//			model.put("datas", reviewList);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	

	/**
	 * 支付宝支付成功之后的回调处理
	 * @author: cgd
	 * date: 2014/10/08
	 * @param tradeNo 	支付宝交易号
	 * @param order	订单
	 * @param member 会员
	 * @throws Exception 
	 */
	public void alipayVerify(String tradeNo, Order order, Member member) throws Exception {
		if (!member.equals(order.getMember())) throw new Exception("订单异常，请联系管理员");
		if(order.isExpired()) throw new Exception("订单已过期，请联系管理员");
		if(order.isLocked(null)) throw new Exception("订单已锁定，请联系管理员");
		if (order.getPaymentStatus() == PaymentStatus.paid) {
			throw new Exception("此订单已支付，请联系管理员");
		}
		if (order.getAmountPayable().compareTo(new BigDecimal(0)) <= 0) {
			throw new Exception("此订单应付金额为0，请联系管理员");
		}
		String order_no = order.getSn();
		String result = AlipaySingleQuery.getSingleQueryString(tradeNo, order_no + "-001");
		Map<String, String> resultMap = AlipaySingleQuery.parseXMLStrToMap(result);
		if("F".equals(resultMap.get("is_success"))){//支付宝请求失败
			throw new Exception(resultMap.get("error"));//获取支付宝异常状态吗，并抛出
		}
		String tradeStatus = resultMap.get("trade_status");
		if (!"TRADE_FINISHED".equals(tradeStatus) && !"TRADE_SUCCESS".equals(tradeStatus))
			throw new Exception("您未支付成功，请查看支付宝交易记录");
		//检查收款支付宝账号
		String seller = resultMap.get("seller_email").toString();
		if(!AlipayConfig.seller.equals(seller)){
			throw new Exception("收款支付宝账号异常");
		}
		BigDecimal amountPayable = order.getAmountPayable();//获取应付金额
		BigDecimal totalFee = new BigDecimal(resultMap.get("total_fee"));//支付宝交易总金额
		if(amountPayable.compareTo(totalFee) != 0){//对比订单应付金额与交易总金额
			String tradeNo2 = resultMap.get("trade_no").toString();
			//检查此支付宝交易号是否使用过
			if(depositService.findByTradeNo(tradeNo2) != null){
				throw new Exception("此笔交易已处理，请勿重复操作");
			}
			/**
			 * 交易金额转入用户余额
			 */
			Deposit deposit = createDeposit(member, totalFee, tradeNo2);
			member.setBalance(deposit.getBalance());
			depositService.save(deposit);//更新预存款
			memberService.update(member);//更新会员
			throw new Exception("支付失败，请核对交易金额(所支付的金额将转入会员余额中)");
		}
		order.setAmountPaid(order.getAmountPaid().add(amountPayable));//设置已付金额 = 已付金额 + 此次的交易金额
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date modifyDate = sdf.parse(resultMap.get("gmt_payment"));
		order.setModifyDate(modifyDate);//设置修改时间
		order.setOrderStatus(OrderStatus.confirmed);//设置订单状态
		order.setPaymentStatus(PaymentStatus.paid);//设置付款状态
		orderService.update(order);//更新订单

		Payment payment = createPayment(order);
		payment.setMember(member);
		payment.setAmount(totalFee);
		payment.setPayer(resultMap.get("buyer_email"));//付款人
		payment.setAccount(seller);//收款账号
		
		paymentService.save(payment);
		
	}

	/**
	 * 生成付款单
	 * @author cgd 2014-10-11
	 * @param order
	 * @return Payment
	 */
	private Payment createPayment(Order order) {
		Payment payment = new Payment();
		payment.setSn(snService.generate(Sn.Type.payment));
		payment.setFee(order.getFee());
		payment.setStatus(Status.success);
		payment.setType(Payment.Type.payment);
		payment.setMethod(Payment.Method.online);//支付方式
		payment.setPaymentDate(order.getModifyDate());//付款时间
		payment.setPaymentMethod(order.getPaymentMethodName() + Payment.PAYMENT_METHOD_SEPARATOR + "APP支付宝支付");
		payment.setCreateDate(new Date());
//		payment.setPaymentPluginId(paymentPluginId);
//		payment.setExpire(paymentPlugin.getTimeout() != null ? DateUtils.addMinutes(new Date(), paymentPlugin.getTimeout()) : null);
		payment.setOrder(order);
		return payment;
	}
	
	/**
	 * 生成预存款
	 * @param member
	 * @param credit
	 * @return
	 */
	private Deposit createDeposit(Member member, BigDecimal credit, String tradeNo){
		Deposit deposit = new Deposit();
		deposit.setMember(member);
		deposit.setCreateDate(new Date());
		deposit.setType(Deposit.Type.adminRefunds);//后台退款
		deposit.setCredit(credit);//收入
		deposit.setBalance(member.getBalance().add(credit));//当前余额
		deposit.setTradeNo(tradeNo);//支付宝交易号
		deposit.setMemo("支付宝交易金额与订单应付金额不一致");//备注
		return deposit;
	}
	
	/**
	 * 确认收货
	 */
	@RequestMapping(value = "/confirmOrder",method = RequestMethod.POST)
	public @ResponseBody ModelMap confirmOrder(Long userid, String sn, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			sn = (String) obj.get("sn");
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Order order = orderService.findBySn(sn);
			if (order == null) {
				model.put("error", "订单为空");
				model.put("success", 1);
				return model;
			}
			
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
}