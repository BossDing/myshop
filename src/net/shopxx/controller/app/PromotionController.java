/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shopxx.Pageable;
import net.shopxx.entity.Product;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.util.TwUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 促销活动
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("APPPromotionController")
@RequestMapping("/m/promotion")
public class PromotionController extends BaseController {

	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 获取活动首页数据
	 */
	@RequestMapping(value = "/index",method = RequestMethod.POST)
	public @ResponseBody ModelMap index(HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			if(promotionService.findAll().size() == 0) {
				model.put("promotions", productList);
				model.put("success", 2);
				return model;
			}
			for(Promotion pn : promotionService.findAll()) {
				HashMap<String, Object> pnMap = new HashMap<String, Object>();
				pnMap.put("id", pn.getId());
				pnMap.put("name", pn.getName());
				pnMap.put("image", pn.getImgpath()==null?"":pn.getImgpath());
				productList.add(pnMap);
			}
			model.put("promotions", productList);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取活动信息里面的商品
	 */
	@RequestMapping(value = "/getPromotionProducts",method = RequestMethod.POST)
	public @ResponseBody ModelMap getPromotionProducts(Long brandId, Long[] tagIds, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			
			JSONObject obj = TwUtil.maptoJsin(map);
			Long id = Long.parseLong((String)obj.get("id"));

			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));
			Pageable pageable = new Pageable(pageNumber, pageSize);
			Promotion promotion = promotionService.find(id);
			if(productService.findPage(null, null, promotion, null, null, startPrice, endPrice, true, true, null, false, null, null, orderType, pageable).getContent().size() == 0) {
				model.put("datas", productList);
				model.put("success", 2);
				return model;
			}
			for(Product p : productService.findPage(null, null, promotion, null, null, startPrice, endPrice, true, true, null, false, null, null, orderType, pageable).getContent()) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("id", p.getId());
				productMap.put("name", p.getName());
				productMap.put("image", p.getThumbnail());
				productMap.put("price", p.getPrice());
				productMap.put("marketprice", p.getMarketPrice());
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

}