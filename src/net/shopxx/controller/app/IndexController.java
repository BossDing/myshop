/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shopxx.Pageable;
import net.shopxx.entity.Ad;
import net.shopxx.entity.Product;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.AdService;
import net.shopxx.service.ProductService;
import net.shopxx.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Controller - app首页商品显示
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("APPIndexController")
@RequestMapping("/m")
public class IndexController extends BaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "adServiceImpl")
	private AdService adService;

	/*
	 * @RequestMapping value用于配置响应请求的URL后缀
	 * 
	 * @RequestMapping method用于配置响应请求的方式(post / get) String username用于接收请求参数值
	 * model.addAttribute("username", username);用于将变量传递至模板 return
	 * "shop/hello/view";表示使用 "/WEB-INF/template/shop/hello/view.ftl"模板输出
	 */  

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public @ResponseBody ModelMap index(Long[] tagIds, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Integer pageNumber, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			
			ArrayList<HashMap<String, Object>> adList = new ArrayList<HashMap<String, Object>>();
			ArrayList<HashMap<String, Object>> hotList = new ArrayList<HashMap<String, Object>>();
			ArrayList<HashMap<String, Object>> comList = new ArrayList<HashMap<String, Object>>();
			
			//查询广告图
			for(Ad ad : adService.findAdByAdPositionName("app - 首页 - 广告", null)) {
				HashMap<String, Object> adMap = new HashMap<String, Object>();
				adMap.put("image", ad.getPath());
				adList.add(adMap);
			}
			
			// 查询热卖推荐
			tagIds = new Long[5];
			tagIds[0] = Long.parseLong(Integer.toString(1));
			List<Tag> tags2 = tagService.findList(tagIds);
			Pageable pageable = new Pageable(0, 5);
			
			for(Product product : productService.findPage(null, null, null, tags2, null, startPrice, endPrice, true,
					true, null, false, null, null, OrderType.topDesc, pageable).getContent()) {
				HashMap<String, Object> hotMap = new HashMap<String, Object>();
				hotMap.put("id", product.getId());
				hotMap.put("image", product.getMedium());
				hotMap.put("price", product.getPrice());
				hotMap.put("marketprice", product.getMarketPrice());
				hotMap.put("fullname", product.getFullName());
				hotList.add(hotMap);
			}
			
			//查询精品推荐
			tagIds = new Long[5];
			tagIds[0] = Long.parseLong(Integer.toString(2));
			List<Tag> tags = tagService.findList(tagIds);
			
			for(Product product : productService.findPage(null, null, null, tags, null, startPrice, endPrice, true,
					true, null, false, null, null, OrderType.topDesc, pageable).getContent()) {
				HashMap<String, Object> comMap = new HashMap<String, Object>();
				comMap.put("id", product.getId());
				comMap.put("image", product.getMedium());
				comMap.put("price", product.getPrice());
				comMap.put("marketprice", product.getMarketPrice());
				comMap.put("fullname", product.getFullName());
				comList.add(comMap);
			}

			model.put("index-banner", adList);
			model.put("hotProducts", hotList);
			model.put("commendationProducts", comList);
			model.put("hot.length", hotList.size());
			model.put("com.length", comList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}

}