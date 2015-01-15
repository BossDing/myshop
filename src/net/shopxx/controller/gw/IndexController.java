package net.shopxx.controller.gw;

/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */

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
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.BrandService;
import net.shopxx.service.CartService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.TagService;
import net.shopxx.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * Controller - 微商城首页
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("GWIndexController")
@RequestMapping("/gw")
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

	/*
	 * @RequestMapping value用于配置响应请求的URL后缀
	 * 
	 * @RequestMapping method用于配置响应请求的方式(post / get) String username用于接收请求参数值
	 * model.addAttribute("username", username);用于将变量传递至模板 return
	 * "shop/hello/view";表示使用 "/WEB-INF/template/shop/hello/view.ftl"模板输出
	 */  

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Integer pageNumber, Integer pageSize, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//		System.out.println("IndexController idnex()");
		WebUtils.getOpenidtoLoginForWechat(request, response, session, memberService, cartService);
		// 查找最新推荐
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		tagIds = new Long[5];
		tagIds[0] = Long.parseLong(Integer.toString(2));
		List<Tag> tags = tagService.findList(tagIds);  
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("newpage", productService.findPage(null, brand, promotion, tags, null, startPrice, endPrice, true, true, null, false, null, null, orderType, pageable));
		
		// 查找热卖推荐
		tagIds = new Long[5];
		tagIds[0] = Long.parseLong(Integer.toString(1));
		List<Tag> tags2 = tagService.findList(tagIds);
		model.addAttribute("hotpage", productService.findPage(null, brand, promotion, tags2, null, startPrice, endPrice, true, true, null, false, null, null, orderType, pageable));
		Member member = memberService.getCurrent();
		model.addAttribute("member", member);
		
		// 指向ftl文件
		return "gw/index";
	}

}