/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.util.TwUtil;

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
@Controller("APPMemberFavoriteController")
@RequestMapping("/m/member/favorite")
public class FavoriteController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	/**
	 * 获取用户的收藏信息
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public @ResponseBody ModelMap list(Integer pageNumber, Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> favoriteList = new ArrayList<HashMap<String, Object>>();
			Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
			
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			System.out.println("userid : "+userid);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			for(Product product : productService.findPage(member, pageable).getContent()) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("id", product.getId());
				productMap.put("fullname", product.getFullName());
				productMap.put("price", product.getPrice());
				productMap.put("marketPrice", product.getMarketPrice());
				favoriteList.add(productMap);
			}
			
			model.put("datas", favoriteList);
			model.put("length", favoriteList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}

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
	 * 查询此商品收藏与否
	 */
	@RequestMapping(value = "/isExist", method = RequestMethod.POST)
	public @ResponseBody
	Message isExist(Long id) {
		Product product = productService.find(id);
		if (product == null) {
			return Message.error("收藏对象已不存在！");
		}
		Member member = memberService.getCurrent();
		if (member.getFavoriteProducts().contains(product)) {
			return Message.warn("shop.member.favorite.exist");
		}
		return Message.success("shop.member.favorite.success");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
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
		return SUCCESS_MESSAGE;
	}

}