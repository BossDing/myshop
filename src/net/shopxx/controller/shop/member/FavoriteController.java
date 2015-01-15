/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop.member;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Cart;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心 - 商品收藏
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopMemberFavoriteController")
@RequestMapping("/member/favorite")
public class FavoriteController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody
	Message add(Long id) {
		Product product = productService.find(id);
		if (product == null) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (member.getFavoriteProducts().contains(product)) {
			return Message.warn("shop.member.favorite.exist");
		}
		if (Member.MAX_FAVORITE_COUNT != null && member.getFavoriteProducts().size() >= Member.MAX_FAVORITE_COUNT) {
			return Message.warn("shop.member.favorite.addCountNotAllowed", Member.MAX_FAVORITE_COUNT);
		}
		member.getFavoriteProducts().add(product);
		memberService.update(member);
		return Message.success("shop.member.favorite.success");
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNumber, ModelMap model) {
		Member member = memberService.getCurrent();
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", productService.findPage(member, pageable));
		System.out.println("favorite-list");
		System.out.println(productService.findPage(member, pageable).getContent().size());
		return "shop/member/favorite/list";
	}

//	/**
//	 * 删除
//	 */
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public @ResponseBody
//	Message delete(Long id) {
//		Product product = productService.find(id);
//		if (product == null) {
//			return ERROR_MESSAGE;
//		}
//		Member member = memberService.getCurrent();
//		if (!member.getFavoriteProducts().contains(product)) {
//			return ERROR_MESSAGE;
//		}
//		member.getFavoriteProducts().remove(product);
//		memberService.update(member);
//		return SUCCESS_MESSAGE;
//	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id, String ids) {
		if(id != null){
			Product product = productService.find(id);
			if (product == null) {
				return ERROR_MESSAGE;
			}
			Member member = memberService.getCurrent();
			if (!member.getFavoriteProducts().contains(product)) {
				return ERROR_MESSAGE;
			}
			member.getFavoriteProducts().remove(product);
			memberService.update(member);
		}else {
			if(ids != null && ids.length() >0){
				String ss[]=ids.split(",");
				Member member = memberService.getCurrent();
				for(int i=0; i<ss.length;i++){
					Product product = productService.find(Long.parseLong(ss[i]));
					if (product == null) {
						return ERROR_MESSAGE;
					}
					if (!member.getFavoriteProducts().contains(product)) {
						return ERROR_MESSAGE;
					}
					member.getFavoriteProducts().remove(product);
				}
				memberService.update(member);
			}else{
				return Message.warn("请勾选您想删除的商品");
			}
		}
		
		return SUCCESS_MESSAGE;
	}
	
	@RequestMapping(value = "/queryFavoriteCount", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> queryFavoriteCount() {
		Member member = memberService.getCurrent();
		Pageable pageable = new Pageable();
		int count = productService.findPage(member, pageable).getContent().size();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", SUCCESS_MESSAGE);
		data.put("favoriteCount", count);
		return data;     
	}
}