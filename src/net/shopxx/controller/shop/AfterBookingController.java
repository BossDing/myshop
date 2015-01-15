/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.entity.AfterBooking;
import net.shopxx.entity.AfterBooking.Type;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.AfterBookingService;
import net.shopxx.service.AreaService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 文章
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("afterBookingController")
@RequestMapping("/afterBooking")
public class AfterBookingController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 20;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "afterBookingServiceImpl")
	private AfterBookingService afterBookingService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public String list(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		return "/shop/article/list";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public  @ResponseBody 
	Message save(AfterBooking afterBooking,Long areaId,String productIds, HttpServletRequest request, RedirectAttributes redirectAttributes){
		Long[] ps = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		if(null!=productIds&&!"".equalsIgnoreCase(productIds.trim())){
			String[] ss = productIds.split("-");
			ps= new Long[ss.length];
			for(int i=0;i<ss.length;i++){
				ps[i] = Long.parseLong(ss[i]);
			}
		}
		afterBooking.setArea(areaService.find(areaId));
		List<Product> products = productService.findList(ps);
		if(null!=products){
			afterBooking.setProducts(new HashSet<Product>(products));
		}
		Member member = memberService.getCurrent();
		if(member == null){
			return Message.error("用户还没有登录");
			//return ERROR_VIEW;
		}
		if(null!=member.getAfterBookings()&&member.getAfterBookings().size()>0){
			afterBooking.setSn(sdf.format(new Date())+member.getAfterBookings().size());
		}else{
			afterBooking.setSn(sdf.format(new Date())+0);
		}
		afterBooking.setMember(member);
		String entCode = afterBooking.getEntCode();
		if(null==entCode||"".equals(entCode.trim())||entCode.length()<1){
			afterBooking.setEntCode("macro");
		}
		try {
			if(afterBooking.getType()==Type.install){
				afterBooking.setFaultType(0);
				afterBooking.setFaultDate(new Date());
			}else if(afterBooking.getType()==Type.repair){
			}
			afterBookingService.save(afterBooking);
			} catch (Exception e) {
				e.printStackTrace();		
		}
		System.out.println("保存成功，返回success");
		return Message.success("保存成功");
	}
	
	/**
	 * 文章详情
	 */
	@RequestMapping(value = "/queryArticle/{id}", method = RequestMethod.GET)
	public String queryArticle(@PathVariable Long id, ModelMap model) {
		return "/shop/article/guide" ;
	}
}