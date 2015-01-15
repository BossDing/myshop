/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Cart;
import net.shopxx.entity.Coupon;
import net.shopxx.entity.CouponCode;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.Payment;
import net.shopxx.entity.PaymentMethod;
import net.shopxx.entity.PointsWater;
import net.shopxx.entity.Product;
import net.shopxx.entity.Receiver;
import net.shopxx.entity.Shipping;
import net.shopxx.entity.ShippingMethod;
import net.shopxx.entity.Sn;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.PaymentStatus;
import net.shopxx.entity.Payment.Method;
import net.shopxx.entity.Payment.Status;
import net.shopxx.entity.Payment.Type;
import net.shopxx.plugin.PaymentPlugin;
import net.shopxx.service.AreaService;
import net.shopxx.service.CartService;
import net.shopxx.service.CouponCodeService;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderService;
import net.shopxx.service.PaymentMethodService;
import net.shopxx.service.PaymentService;
import net.shopxx.service.PluginService;
import net.shopxx.service.PointsWaterService;
import net.shopxx.service.ProductService;
import net.shopxx.service.ReceiverService;
import net.shopxx.service.ShippingMethodService;
import net.shopxx.service.ShippingService;
import net.shopxx.service.SnService;
import net.shopxx.util.SettingUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心 - 订单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopMemberOrderController")
@RequestMapping("/member/order")
public class OrderController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 2;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
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
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	@Resource(name = "pointsWaterServiceImpl")
	private PointsWaterService pointsWaterService;
	
	/**
	 * 保存收货地址
	 */
	@RequestMapping(value = "/save_receiver", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveReceiver(Receiver receiver, Long areaId) {
		Map<String, Object> data = new HashMap<String, Object>();
		receiver.setArea(areaService.find(areaId));
		if (!isValid(receiver)) {
			data.put("message", ERROR_MESSAGE);
			return data;
		}
		Member member = memberService.getCurrent();
		if (Receiver.MAX_RECEIVER_COUNT != null && member.getReceivers().size() >= Receiver.MAX_RECEIVER_COUNT) {
			data.put("message", Message.error("shop.order.addReceiverCountNotAllowed", Receiver.MAX_RECEIVER_COUNT));
			return data;
		}
		receiver.setMember(member);
		receiverService.save(receiver);
		data.put("message", SUCCESS_MESSAGE);
		data.put("receiver", receiver);
		return data;
	}

	/**
	 * 订单锁定
	 */
	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	public @ResponseBody
	boolean lock(String sn) {
		Order order = orderService.findBySn(sn);
		if (order != null && memberService.getCurrent().equals(order.getMember()) && !order.isExpired() && !order.isLocked(null) && order.getPaymentMethod() != null && order.getPaymentMethod().getMethod() == PaymentMethod.Method.online && (order.getPaymentStatus() == PaymentStatus.unpaid || order.getPaymentStatus() == PaymentStatus.partialPayment)) {
			order.setLockExpire(DateUtils.addSeconds(new Date(), 20));
			order.setOperator(null);
			orderService.update(order);
			return true;
		}
		return false;
	}

	/**
	 * 检查支付
	 */
	@RequestMapping(value = "/check_payment", method = RequestMethod.POST)
	public @ResponseBody
	boolean checkPayment(String sn) {
		Order order = orderService.findBySn(sn);
		if (order != null && memberService.getCurrent().equals(order.getMember()) && order.getPaymentStatus() == PaymentStatus.paid) {
			return true;
		}
		return false;
	}

	/**
	 * 优惠券信息
	 */
	@RequestMapping(value = "/coupon_info", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> couponInfo(String code) {
		Map<String, Object> data = new HashMap<String, Object>();
		Cart cart = cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			data.put("message", Message.warn("shop.order.cartNotEmpty"));
			return data;
		}
		if (!cart.isCouponAllowed()) {
			data.put("message", Message.warn("shop.order.couponNotAllowed"));
			return data;
		}
		CouponCode couponCode = couponCodeService.findByCode(code);
		if (couponCode != null && couponCode.getCoupon() != null) {
			Coupon coupon = couponCode.getCoupon();
			if (!coupon.getIsEnabled()) {
				data.put("message", Message.warn("shop.order.couponDisabled"));
				return data;
			}
			if (!coupon.hasBegun()) {
				data.put("message", Message.warn("shop.order.couponNotBegin"));
				return data;
			}
			if (coupon.hasExpired()) {
				data.put("message", Message.warn("shop.order.couponHasExpired"));
				return data;
			}
			if (!cart.isValid(coupon)) {
				data.put("message", Message.warn("shop.order.couponInvalid"));
				return data;
			}
			if (couponCode.getIsUsed()) {
				data.put("message", Message.warn("shop.order.couponCodeUsed"));
				return data;
			}
			data.put("message", SUCCESS_MESSAGE);
			data.put("couponName", coupon.getName());
			return data;
		} else {
			data.put("message", Message.warn("shop.order.couponCodeNotExist"));
			return data;
		}
	}

	/**
	 * 信息
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(ModelMap model) {
		Cart cart = cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			return "redirect:/cart/list.jhtml";
		}
		if (!isValid(cart)) {
			return ERROR_VIEW;
		}
		Order order = orderService.build(cart, null, null, null, null, false, null, false, null);
		model.addAttribute("order", order);
		model.addAttribute("cartToken", cart.getToken());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		Integer count = 4;
//		System.out.println(products.size());
//		for (Product product : products) {
//			System.out.println(product.getName());
//			System.out.println(product.getSales());
//		}
		model.addAttribute("hotproducts", productService.findHotProductList(count));
		return "/shop/member/order/info";
	}
	/**
	 * 订单信息-积分购物
	 */
	@RequestMapping(value ="/inJf/{args}", method = RequestMethod.GET)
	public String inJf(@PathVariable String args,ModelMap model) {
		Long id =0l;
		int quantity =0;
		if(""!=args&&args.split("a").length>0){
			String [] argArray =args.split("a");
				id = Long.parseLong(argArray[0]);
				quantity = Integer.parseInt(argArray[1]);
		}
		Product product =  productService.find(id);
		if (product == null) {
			return "redirect:/product/list.jhtml?tagIds=405";
		}
//		if (!isValid(product)) {
//			return ERROR_VIEW;
//		}
		Member member = memberService.getCurrent();
		//HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37).append(getKey());
		Order order = orderService.buildPointOrder(product,quantity,member,null, null, null, null, false, null, false, null);
		model.addAttribute("product",product);
		model.addAttribute("order", order);
		//model.addAttribute("cartToken", cart.getToken());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		return "/shop/member/order/inJf";
	}

	/**
	 * 计算
	 */
	@RequestMapping(value = "/calculate", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> calculate(Long paymentMethodId, Long shippingMethodId, String code, @RequestParam(defaultValue = "false") Boolean isInvoice, String invoiceTitle, @RequestParam(defaultValue = "false") Boolean useBalance, String memo) {
		Map<String, Object> data = new HashMap<String, Object>();
		Cart cart = cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			data.put("message", Message.error("shop.order.cartNotEmpty"));
			return data;
		}
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		CouponCode couponCode = couponCodeService.findByCode(code);
		Order order = orderService.build(cart, null, paymentMethod, shippingMethod, couponCode, isInvoice, invoiceTitle, useBalance, memo);

		data.put("message", SUCCESS_MESSAGE);
		data.put("quantity", order.getQuantity());
		data.put("price", order.getPrice());
		data.put("freight", order.getFreight());
		data.put("promotionDiscount", order.getPromotionDiscount());
		data.put("couponDiscount", order.getCouponDiscount());
		data.put("tax", order.getTax());
		data.put("amountPayable", order.getAmountPayable());
		return data;
	}
	/**
	 * 计算
	 */
	@RequestMapping(value = "/calculateJf", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> calculateJf(Long productId,int quantity,Long paymentMethodId, Long shippingMethodId,  @RequestParam(defaultValue = "false") Boolean isInvoice, String invoiceTitle, @RequestParam(defaultValue = "false") Boolean useBalance, String memo) {
		Map<String, Object> data = new HashMap<String, Object>();
		if(null==productId){
			data.put("message", Message.error("未选购商品"));
			return data;
		}
		Product product =  productService.find(productId);
		if(null==product){
			data.put("message", Message.error("未选购商品"));
			return data;
		}
		Member member = memberService.getCurrent();
		
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		//CouponCode couponCode = couponCodeService.findByCode(code);
		Order order = orderService.buildPointOrder(product,quantity,member, null, paymentMethod, shippingMethod, null, isInvoice, invoiceTitle, useBalance, memo);
		data.put("message", SUCCESS_MESSAGE);
		data.put("quantity", order.getQuantity());
		//data.put("price", order.getPrice());
		data.put("lowPrice", order.getLowPrice());
		data.put("freight", order.getFreight());
		data.put("promotionDiscount", order.getPromotionDiscount());
		data.put("couponDiscount", order.getCouponDiscount());
		data.put("tax", order.getTax());
		data.put("amountPayable", order.getAmountPayable());
		return data;
	}
	/**
	 * 创建
	 */
	@RequestMapping(value = "/createJf", method = RequestMethod.POST)
	public @ResponseBody
	Message createJf(Long productId,int quantity,Long receiverId, Long paymentMethodId, Long shippingMethodId,  @RequestParam(defaultValue = "false") Boolean isInvoice, String invoiceTitle, @RequestParam(defaultValue = "false") Boolean useBalance, String memo) {	
		Product product =  productService.find(productId);
		Member member = memberService.getCurrent();
		Receiver receiver = receiverService.find(receiverId);
		if (receiver == null) {
			return Message.error("shop.order.receiverNotExsit");
		}
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		if (paymentMethod == null) {
			return Message.error("shop.order.paymentMethodNotExsit");
		}
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		if (shippingMethod == null) {
			return Message.error("shop.order.shippingMethodNotExsit");
		}
		if (!paymentMethod.getShippingMethods().contains(shippingMethod)) {
			return Message.error("shop.order.deliveryUnsupported");
		}
		//CouponCode couponCode = couponCodeService.findByCode(code);
		Order order = orderService.createJf(product,quantity, member,receiver, paymentMethod, shippingMethod, null, isInvoice, invoiceTitle, useBalance, memo, null);
		return Message.success(order.getSn());
	}

	/**
	 * 创建
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	Message create(String cartToken, Long receiverId, Long paymentMethodId, Long shippingMethodId, String code, @RequestParam(defaultValue = "false") Boolean isInvoice, String invoiceTitle, @RequestParam(defaultValue = "false") Boolean useBalance, String memo) {
		Cart cart = cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			return Message.warn("shop.order.cartNotEmpty");
		}
		if (!StringUtils.equals(cart.getToken(), cartToken)) {
			return Message.warn("shop.order.cartHasChanged");
		}
		if (cart.getIsLowStock()) {
			return Message.warn("shop.order.cartLowStock");
		}
		Receiver receiver = receiverService.find(receiverId);
		if (receiver == null) {
			return Message.error("shop.order.receiverNotExsit");
		}
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		if (paymentMethod == null) {
			return Message.error("shop.order.paymentMethodNotExsit");
		}
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		if (shippingMethod == null) {
			return Message.error("shop.order.shippingMethodNotExsit");
		}
		if (!paymentMethod.getShippingMethods().contains(shippingMethod)) {
			return Message.error("shop.order.deliveryUnsupported");
		}
		CouponCode couponCode = couponCodeService.findByCode(code);
		Order order = orderService.create(cart, receiver, paymentMethod, shippingMethod, couponCode, isInvoice, invoiceTitle, useBalance, memo, null);
		return Message.success(order.getSn());
	}

	/**
	 * 支付
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String payment(String sn, ModelMap model) {
		Order order = orderService.findBySn(sn);
		if (order == null || !memberService.getCurrent().equals(order.getMember()) || order.isExpired() || order.getPaymentMethod() == null) {
			return ERROR_VIEW;
		}
		if (order.getPaymentMethod().getMethod() == PaymentMethod.Method.online) {
			List<PaymentPlugin> paymentPlugins = pluginService.getPaymentPlugins(true);
			if (!paymentPlugins.isEmpty()) {
				PaymentPlugin defaultPaymentPlugin = paymentPlugins.get(0);
				if (order.getPaymentStatus() == PaymentStatus.unpaid || order.getPaymentStatus() == PaymentStatus.partialPayment) {
					model.addAttribute("fee", defaultPaymentPlugin.calculateFee(order.getAmountPayable()));
					model.addAttribute("amount", defaultPaymentPlugin.calculateAmount(order.getAmountPayable()));
				}
				model.addAttribute("defaultPaymentPlugin", defaultPaymentPlugin);
				model.addAttribute("paymentPlugins", paymentPlugins);
			}
		}
		if(order.getStore() != null) {
			model.addAttribute("isStore", true);
		}
		model.addAttribute("order", order);
		return "/shop/member/order/payment";
	}
					
	/**完全积分兑换 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody Message submit(Type type,String sn,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Order order = orderService.findBySn(sn);
		Member member = memberService.getCurrent();
		if (order == null || !member.equals(order.getMember()) || order.isExpired() || order.isLocked(null)) {
			return Message.warn("shop.order.cartLowStock");
		}
		if (order.getPaymentMethod() == null || order.getPaymentMethod().getMethod() != PaymentMethod.Method.online) {
			return Message.warn("shop.order.cartLowStock");
		}
		if (order.getPaymentStatus() != PaymentStatus.unpaid && order.getPaymentStatus() != PaymentStatus.partialPayment) {
			return Message.warn("shop.order.cartLowStock");
		}
		if (order.getLowPoints()< 0) {
			return Message.warn("shop.order.cartLowStock");
		}
		if(null!=order&&order.getType().equals("1")){
			member.setPoint(member.getPoint()-order.getLowPoints());
			memberService.update(member);
			order.setLowPointsPaid(order.getLowPoints());
			order.setOrderStatus(OrderStatus.confirmed);
			order.setPaymentStatus(PaymentStatus.paid);
			orderService.update(order);
			
			if(order.getLowPrice().compareTo(new BigDecimal(0)) == 0){
			Payment payment = new Payment();
			payment.setSn(snService.generate(Sn.Type.payment));
			payment.setType(Type.payment);
			payment.setMethod(Method.online);
			payment.setStatus(Status.success);
			//payment.setPaymentMethod(order.getPaymentMethodName() + Payment.PAYMENT_METHOD_SEPARATOR + paymentPlugin.getPaymentName());
			payment.setPaymentMethod("积分支付");
			payment.setFee(new BigDecimal(0));
			payment.setAmount(new BigDecimal(0));
			payment.setPaymentPluginId(null);
			payment.setExpire(null);
			payment.setOrder(order);
			paymentService.save(payment);
			
			PointsWater pointWater = new PointsWater();
			pointWater.setCreateDate(order.getModifyDate());
			pointWater.setRulename("积分兑换");
			pointWater.setMember_id(member.getId());
			pointWater.setPoints_stat(2);
			pointWater.setPoints(Integer.valueOf(order.getLowPointsPaid().toString()));
			pointWater.setOrder(order);
			pointsWaterService.save(pointWater);
			
			}
		}	
		return Message.success("支付成功！");
	}
	/**
	 * 计算支付金额
	 */
	@RequestMapping(value = "/calculate_amount", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> calculateAmount(String paymentPluginId, String sn) {
		Map<String, Object> data = new HashMap<String, Object>();
		Order order = orderService.findBySn(sn);
		PaymentPlugin paymentPlugin = pluginService.getPaymentPlugin(paymentPluginId);
		if (order == null || !memberService.getCurrent().equals(order.getMember()) || order.isExpired() || order.isLocked(null) || order.getPaymentMethod() == null || order.getPaymentMethod().getMethod() == PaymentMethod.Method.offline || paymentPlugin == null || !paymentPlugin.getIsEnabled()) {
			data.put("message", ERROR_MESSAGE);
			return data;
		}
		data.put("message", SUCCESS_MESSAGE);   
		data.put("fee", paymentPlugin.calculateFee(order.getAmountPayable()));
		data.put("amount", paymentPlugin.calculateAmount(order.getAmountPayable()));
		return data;
	}   

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)  
	public String list(String searchValue ,Integer pageNumber ,String status ,ModelMap model) {
		Member member = memberService.getCurrent();
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("allOrderCount", orderService.AllCount(member));
		model.addAttribute("waitingPaymentOrderCount", orderService.waitingPaymentCount(member));
		model.addAttribute("waitingShippingOrderCount", orderService.waitingShippingCount(member));
		model.addAttribute("waitinggetOrderCount", orderService.waitingGetCount(member));
		model.addAttribute("waitingconmentOrderCount", orderService.waitingConmentCount(member));	
		if(searchValue!=null && !searchValue.equals("")){
			model.addAttribute("page", orderService.findPage(searchValue, member, pageable));    
			model.addAttribute("searchValue", searchValue);  
		}else{
			if("unpaid".equals(status)){
			model.addAttribute("page", orderService.findUnpaidPage(member, pageable)); 
			}else if("unshipped".equals(status)){
				model.addAttribute("page", orderService.waitingShipping(member, pageable)); 
			}else if("unreceive".equals(status)){
				model.addAttribute("page", orderService.waitingReceiving(member, pageable)); 
			}else if("unEvaluation".equals(status)){
				model.addAttribute("page", orderService.waitingConmentPage(member, pageable));
			}else{
				model.addAttribute("page", orderService.findPage(member, pageable)); 
			}
		} 
		model.addAttribute("status", status); 
		return "shop/member/order/list";
	}  
	/**
	 * 列表
	 */
	@RequestMapping(value = "/invoice", method = RequestMethod.GET)  
	public String invoice(String searchValue,Integer pageNumber, ModelMap model) {
		Member member = memberService.getCurrent();
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("allOrderCount", orderService.AllCount(member));
		model.addAttribute("waitingPaymentOrderCount", orderService.waitingPaymentCount(member));
		model.addAttribute("waitingShippingOrderCount", orderService.waitingShippingCount(member));
		model.addAttribute("waitinggetOrderCount", orderService.waitingGetCount(member));
		model.addAttribute("waitingconmentOrderCount", orderService.waitingConmentCount(member));	
		if(searchValue!=null && !searchValue.equals("")){
			model.addAttribute("page", orderService.findPage(searchValue, member, pageable));    
			model.addAttribute("searchValue", searchValue);  
		}else{
			model.addAttribute("page", orderService.findPage(member, pageable));    
		} 
		return "shop/member/invoice/list";         
	} 
	/**   
	 * 查看   
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(String sn, ModelMap model) {
		Order order = orderService.findBySn(sn);
		if (order == null) {
			return ERROR_VIEW;
		}
		Member member = memberService.getCurrent();
		if (!member.getOrders().contains(order)) {
			return ERROR_VIEW;
		}
		model.addAttribute("order", order);
		return "shop/member/order/view";
	}

	/**
	 * 取消
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public @ResponseBody
	Message cancel(String sn) {
		Order order = orderService.findBySn(sn);
		if (order != null && memberService.getCurrent().equals(order.getMember()) && !order.isExpired() && order.getOrderStatus() == OrderStatus.unconfirmed && order.getPaymentStatus() == PaymentStatus.unpaid) {
			if (order.isLocked(null)) {
				return Message.warn("shop.member.order.locked");
			}
			orderService.cancel(order, null);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

	/**
	 * 物流动态
	 */
	@RequestMapping(value = "/delivery_query", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> deliveryQuery(String sn) {
		Map<String, Object> data = new HashMap<String, Object>();
		Shipping shipping = shippingService.findBySn(sn);
		Setting setting = SettingUtils.get();
		if (shipping != null && shipping.getOrder() != null && memberService.getCurrent().equals(shipping.getOrder().getMember()) && StringUtils.isNotEmpty(setting.getKuaidi100Key()) && StringUtils.isNotEmpty(shipping.getDeliveryCorpCode()) && StringUtils.isNotEmpty(shipping.getTrackingNo())) {
			data = shippingService.query(shipping);
		}
		return data;
	}
	
	@RequestMapping(value = "/queryShippedCount", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> queryShippedCount() {
		Member member = memberService.getCurrent();
		Long count = orderService.waitingGetCount(member);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", SUCCESS_MESSAGE);
		data.put("shippedCount", count);
		return data;     
	}

}