/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app.member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Coupon;
import net.shopxx.entity.CouponCode;
import net.shopxx.entity.Member;
import net.shopxx.service.CouponCodeService;
import net.shopxx.service.CouponService;
import net.shopxx.service.MemberService;
import net.shopxx.util.TwUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心 - 优惠码
 * 
 * @author SHOP++ Team 
 * @version 3.0
 */
@Controller("APPMemberCouponCodeController")
@RequestMapping("/m/member/coupon_code")
public class CouponCodeController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	
	/**
	 * 列表(我的优惠券)
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public @ResponseBody ModelMap list(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> couponcodeList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			int flag = Integer.parseInt((String) obj.get("flag"));
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));

			Pageable pageable = new Pageable(pageNumber, pageSize);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			if(flag == 1) { //全部
				for(CouponCode couponcode : couponCodeService.findPage(member, pageable).getContent()) {
					HashMap<String, Object> couponcodeMap = new HashMap<String, Object>();
					couponcodeMap.put("id", couponcode.getId());
					couponcodeMap.put("name", couponcode.getCoupon().getName());
					couponcodeMap.put("enddate", (new SimpleDateFormat("yyyy-MM-dd")).format(couponcode.getCoupon().getEndDate()));
					couponcodeMap.put("isused", couponcode.getIsUsed() == true ? 1 : 0);
					couponcodeMap.put("hasexpired", couponcode.getCoupon().hasExpired() == true ? 1 : 0);
					couponcodeMap.put("introduction", couponcode.getCoupon().getIntroduction());
					couponcodeList.add(couponcodeMap);
				}
			} else if(flag == 2) {	//未使用
				for(CouponCode couponcode : couponCodeService.findPage(member, pageable).getContent()) {
					if(!couponcode.getIsUsed() && !couponcode.getCoupon().hasExpired()) {
						HashMap<String, Object> couponcodeMap = new HashMap<String, Object>();
						couponcodeMap.put("id", couponcode.getId());
						couponcodeMap.put("name", couponcode.getCoupon().getName());
						couponcodeMap.put("enddate", (new SimpleDateFormat("yyyy-MM-dd")).format(couponcode.getCoupon().getEndDate()));
						couponcodeMap.put("isused", couponcode.getIsUsed() == true ? 1 : 0);
						couponcodeMap.put("hasexpired", couponcode.getCoupon().hasExpired() == true ? 1 : 0);
						couponcodeMap.put("introduction", couponcode.getCoupon().getIntroduction());
						couponcodeList.add(couponcodeMap);
					}
				}
			} else if(flag == 3) {
				for(CouponCode couponcode : couponCodeService.findPage(member, pageable).getContent()) {
					if(couponcode.getIsUsed()) {
						HashMap<String, Object> couponcodeMap = new HashMap<String, Object>();
						couponcodeMap.put("id", couponcode.getId());
						couponcodeMap.put("name", couponcode.getCoupon().getName());
						couponcodeMap.put("enddate", (new SimpleDateFormat("yyyy-MM-dd")).format(couponcode.getCoupon().getEndDate()));
						couponcodeMap.put("isused", couponcode.getIsUsed() == true ? 1 : 0);
						couponcodeMap.put("hasexpired", couponcode.getCoupon().hasExpired() == true ? 1 : 0);
						couponcodeMap.put("introduction", couponcode.getCoupon().getIntroduction());
						couponcodeList.add(couponcodeMap);
					}
				}
				
			} else if(flag == 4) {
				for(CouponCode couponcode : couponCodeService.findPage(member, pageable).getContent()) {
					if(couponcode.getCoupon().hasExpired()) {
						HashMap<String, Object> couponcodeMap = new HashMap<String, Object>();
						couponcodeMap.put("id", couponcode.getId());
						couponcodeMap.put("name", couponcode.getCoupon().getName());
						couponcodeMap.put("enddate", (new SimpleDateFormat("yyyy-MM-dd")).format(couponcode.getCoupon().getEndDate()));
						couponcodeMap.put("isused", couponcode.getIsUsed() == true ? 1 : 0);
						couponcodeMap.put("hasexpired", couponcode.getCoupon().hasExpired() == true ? 1 : 0);
						couponcodeMap.put("introduction", couponcode.getCoupon().getIntroduction());
						couponcodeList.add(couponcodeMap);
					}
				}
			}
			
			model.put("datas", couponcodeList);
			model.put("length", couponcodeList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 列表可使用的兑换券 
	 */
	@RequestMapping(value = "/exchangelist",method = RequestMethod.POST)
	public @ResponseBody ModelMap exchangelist(HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> couponList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));

			Pageable pageable = new Pageable(pageNumber, pageSize);
			
			for(Coupon coupon : couponService.findPage(true, true, false, pageable).getContent()) {
				if(!coupon.hasExpired()) {
					HashMap<String, Object> couponMap = new HashMap<String, Object>();
					couponMap.put("id", coupon.getId());
					couponMap.put("name", coupon.getName());
					couponMap.put("enddate", (new SimpleDateFormat("yyyy-MM-dd")).format(coupon.getEndDate()));
					couponMap.put("introduction", coupon.getIntroduction());
					couponMap.put("point", coupon.getPoint());
					couponList.add(couponMap);
				}
			}
			
			model.put("datas", couponList);
			model.put("length", couponList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 列表(我的优惠券)
	 */
	@RequestMapping(value = "/exchange",method = RequestMethod.POST)
	public @ResponseBody ModelMap exchange(Long userid, Long id, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			id = Long.parseLong((String) obj.get("id"));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Coupon coupon = couponService.find(id);
			if (coupon == null || !coupon.getIsEnabled() || !coupon.getIsExchange() || coupon.hasExpired()) {
				model.put("error", "优惠券有误,无法兑换!");
				model.put("success", 1);
				return model;
			}
			if (member.getPoint() < coupon.getPoint()) {
				model.put("error", "您的积分不足, 无法兑换该优惠券!");
				model.put("success", 1);
				return model;
			}
			couponCodeService.exchange(coupon, member);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
}