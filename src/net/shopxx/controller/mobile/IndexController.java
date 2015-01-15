/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.mobile;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shopxx.Pageable;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Member;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Store;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.BrandService;
import net.shopxx.service.CartService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.StoreService;
import net.shopxx.service.TagService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * Controller - 微商城首页
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("IndexController")
@RequestMapping("/mobile")
public class IndexController extends BaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "storeServiceImpl")
	private StoreService storeService;

	/*
	 * @RequestMapping value用于配置响应请求的URL后缀
	 * 
	 * @RequestMapping method用于配置响应请求的方式(post / get) String username用于接收请求参数值
	 * model.addAttribute("username", username);用于将变量传递至模板 return
	 * "shop/hello/view";表示使用 "/WEB-INF/template/shop/hello/view.ftl"模板输出
	 */  

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, BigDecimal endPrice, Integer pageNumber, Integer pageSize, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		System.out.println("IndexController idnex()");
		WebUtils.getOpenidtoLoginForWechat(request, response, session, memberService, cartService);
		// 查找精品推荐
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		Pageable pageable = new Pageable(pageNumber, 6);
		
		tagIds = new Long[1];
		tagIds[0] = Long.parseLong(Integer.toString(2));
		List<Tag> tags = tagService.findList(tagIds);
		model.addAttribute("newpage", productService.findPage(null, brand, promotion, tags, null, startPrice, endPrice, true, true, null, false, null, null, OrderType.priceAsc, pageable));
		
		// 查找热卖推荐
		tagIds = new Long[1];
		tagIds[0] = Long.parseLong(Integer.toString(1));
		List<Tag> tags2 = tagService.findList(tagIds);
		model.addAttribute("hotpage", productService.findPage(null, brand, promotion, tags2, null, startPrice, endPrice, true, true, null, false, null, null, OrderType.priceAsc, pageable));
		
		
		Member member = memberService.getCurrent();
		model.addAttribute("member", member);
		
		// 指向ftl文件
		return "mobile/index";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String index(@PathVariable Long id, ModelMap model, Long productCategoryId, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Store store = storeService.find(id);// 根据店铺id，获取店铺信息
		if(store == null || !store.getIsEnabled()) {
			return "redirect:store/list.jhtml";
		}
//		model.addAttribute("productCategories", productCategoryService.findChildrenForStoreForFront(null, null, store));// 商城及该店铺的分类
		model.addAttribute("store", store);
		return "/mobile/dp/index";
	}

}