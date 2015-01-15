/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.mobile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Pageable;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Store;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.StoreService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 商品分类
 * 
 * @author Guoxianlong
 * @date 2014/5/4
 * @version 3.0
 */
@Controller("mobileProductCategoryController")
@RequestMapping("/mobile/product_category")
public class ProductCategoryController extends BaseController {

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	/**
	 * 首页
	 */
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String index(ModelMap model) {
//		model.addAttribute("rootProductCategories", productCategoryService.findRoots());
		model.addAttribute("rootProductCategories", productCategoryService.findRootsByEntcode(null, "macro"));
		return "/mobile/product_category/index";
	}

}