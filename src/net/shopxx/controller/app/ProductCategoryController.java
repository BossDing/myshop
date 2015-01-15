/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.util.TwUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 商品分类
 * @author: Guoxianlong
 * @date: Sep 2, 2014 10:58:27 AM
 */
@Controller("APPProductCategoryController")
@RequestMapping("/m/product_category")
public class ProductCategoryController extends BaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	/**
	 * 首页
	 */
	@RequestMapping(value = "/index",method = RequestMethod.POST)
	public @ResponseBody ModelMap index(HttpServletResponse response) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> productCategoryList = new ArrayList<HashMap<String, Object>>();
			
			for(ProductCategory pc : productCategoryService.findRoots()) {
				HashMap<String, Object> productCategorysMap = new HashMap<String, Object>();
				productCategorysMap.put("id", pc.getId());
				productCategorysMap.put("name", pc.getName());
				productCategorysMap.put("image", pc.getImage());
				productCategoryList.add(productCategorysMap);
			}
			model.put("productCategories", productCategoryList);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 查询子分类或者子分类下商品
	 */
	@RequestMapping(value = "/select",method = RequestMethod.POST)
	public @ResponseBody ModelMap select(HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			Long parentId = Long.parseLong((String)obj.get("id"));
			
			List<ProductCategory> list =  productCategoryService.findChildrenByParent(parentId);
			
			ArrayList<HashMap<String, Object>> productCategoryList = new ArrayList<HashMap<String, Object>>();
			for(ProductCategory pc : list) {
				HashMap<String, Object> productCategorysMap = new HashMap<String, Object>();
				productCategorysMap.put("id", pc.getId());
				productCategorysMap.put("name", pc.getName());
				productCategorysMap.put("image", pc.getImage());
				productCategoryList.add(productCategorysMap);
			}

			ArrayList<HashMap<String, Object>> products = new ArrayList<HashMap<String, Object>>();
			ProductCategory productCategory = productCategoryService.find(parentId);
			List<Product> productList = productService.findList(productCategory, null, null, null, null);
			if(productList.size() != 0) {
				for(Product product: productList) {
					HashMap<String, Object> productMap = new HashMap<String, Object>();
					productMap.put("id", product.getId());
					productMap.put("fullname", product.getFullName());
					productMap.put("price", product.getPrice());
					productMap.put("stock", product.getStock());
					productMap.put("image", product.getThumbnail()==null?"":product.getThumbnail());
					productMap.put("marketprice", product.getMarketPrice());
					products.add(productMap);
				}
			}
			
			model.put("products", products);
			model.put("productCategories", productCategoryList);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}

}