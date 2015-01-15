/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Page;
import net.shopxx.Pageable;
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
@Controller("adminAfterBookingController")
@RequestMapping("/admin/after_booking")
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
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long label, Pageable pageable, ModelMap model) {
//		System.out.println("afterBooking ----list");
//		System.out.println("label:"+label);
		try {
			model.addAttribute("page", afterBookingService.findPage(label, pageable));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/admin/service/list";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list2", method = RequestMethod.GET)
	public String list2(Long label, Pageable pageable, ModelMap model) {
//		System.out.println("afterBooking ----list2");
//		System.out.println("label:"+label);
		try {
			model.addAttribute("page", afterBookingService.findPage(label, pageable));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/admin/service/list2";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("afterBooking", afterBookingService.find(id));
		return "/admin/service/edit";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/service/add";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(AfterBooking afterBooking ,RedirectAttributes redirectAttributes) {
//		System.out.println("afterBooking--update");
//		System.out.println("productIdslength:"+productIds.length);
//		System.out.println("productIds:"+productIds);
//		System.out.println("state:"+afterBooking.getState());
		if (!isValid(afterBooking)) {
			return ERROR_VIEW;
		}
//		List<Product> products = productService.findList(productIds);
//		System.out.println("products.size():"+products.size());
//		if(null!=products){
//			afterBooking.setProducts(new HashSet<Product>(products));
//		}
//		System.out.println("type:"+afterBooking.getType());
//		System.out.println("id:"+afterBooking.getId());
		try {
			afterBookingService.update(afterBooking);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		if(Type.repair.equals(afterBooking.getType())){
			return "redirect:list.jhtml";
		}else{
			return "redirect:list2.jhtml";
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		afterBookingService.delete(ids);
		return SUCCESS_MESSAGE;
	}   
}