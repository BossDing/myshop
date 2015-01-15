/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop.member;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Cart;
import net.shopxx.entity.Coupon;
import net.shopxx.entity.CouponCode;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.PaymentStatus;
import net.shopxx.entity.PaymentMethod;
import net.shopxx.entity.PointsWater;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Receiver;
import net.shopxx.entity.Shipping;
import net.shopxx.entity.ShippingMethod;
import net.shopxx.plugin.PaymentPlugin;
import net.shopxx.service.AreaService;
import net.shopxx.service.CartService;
import net.shopxx.service.CouponCodeService;
import net.shopxx.service.DeliveryCenterService;
import net.shopxx.service.MemberPointsService;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderService;
import net.shopxx.service.PaymentMethodService;
import net.shopxx.service.PluginService;
import net.shopxx.service.PointsWaterService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.ReceiverService;
import net.shopxx.service.ShippingMethodService;
import net.shopxx.service.ShippingService;
import net.shopxx.util.SettingUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心 - 邀请链接
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopMemberPointsController")
@RequestMapping("/member/points")
public class PointsController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "pointsWaterServiceImpl")
	private PointsWaterService pointsWaterService;

	/**
	 * 信息
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNumber, Integer pageSize, ModelMap model) {
		if ((pageNumber == null) || (pageNumber.equals(new Integer(0)))) {
	        pageNumber = new Integer(1);
	      }
	      if ((pageSize == null) || (pageSize.equals(new Integer(0)))) {
	        pageSize = new Integer(4);
	      }
	      model.addAttribute("pageNumber", pageNumber);
	      model.addAttribute("pageSize", pageSize);
	      Pageable pageable = new Pageable(pageNumber, pageSize);
	      Member member = memberService.getCurrent();
	      model.addAttribute("page",pointsWaterService.findPage(member,pageable));
	      model.addAttribute("member",member);	
		  return "shop/member/points/list";
	}
}