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
import net.shopxx.entity.Area;
import net.shopxx.entity.Article;
import net.shopxx.entity.Member;
import net.shopxx.entity.Outlet;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Tag;
import net.shopxx.service.AreaService;
import net.shopxx.service.MemberService;
import net.shopxx.service.OutletService;
import net.shopxx.service.ProductCategoryService;

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
@Controller("adminOutletController")
@RequestMapping("/admin/outlet")
public class OutletController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 20;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "outletServiceImpl")
	private OutletService outletService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", outletService.findPage(pageable));
		return "/admin/outlet/list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("outlet", outletService.find(id));
		return "/admin/outlet/edit";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/outlet/add";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Outlet outlet,Long areaId,Long[] productCategoryIds, RedirectAttributes redirectAttributes) {
		Area area = areaService.find(areaId);
		List<ProductCategory> productCategories =productCategoryService.findList(productCategoryIds);
		StringBuffer sbf = new StringBuffer();
		for(ProductCategory pc : productCategories){
			sbf.append(pc.getName()+",");
		}
		outlet.setArea(area);
		outlet.setProductCategorys(new HashSet<ProductCategory>(productCategories));
		outlet.setAreaName(area.getFullName());
		outlet.setCategoryNames(sbf.toString());
		if (!isValid(outlet)) {
			return ERROR_VIEW;
		}
		try {
			outletService.save(outlet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Outlet outlet,Long areaId,Long[] productCategoryIds, RedirectAttributes redirectAttributes) {
		outlet.setArea(areaService.find(areaId));
		outlet.setProductCategorys(new HashSet<ProductCategory>(productCategoryService.findList(productCategoryIds)));
		if (!isValid(outlet)) {
			return ERROR_VIEW;
		}
		try {
			outletService.update(outlet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		outletService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	
}