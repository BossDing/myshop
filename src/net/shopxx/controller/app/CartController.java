/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.print.attribute.standard.Finishings;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.shopxx.Message;
import net.shopxx.entity.Cart;
import net.shopxx.entity.CartItem;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.CartItemService;
import net.shopxx.service.CartService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.util.TwUtil;
import net.shopxx.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 购物车 
 * 
 * @author SHOP++ Team
 * @version 3.0
 */

/*
 * @Controller用于申明该类为一个控制器，其值必须唯一
 * @RequestMapping用于配置响应请求的URL前缀
 * @RequestMapping value用于配置响应请求的URL后缀
 * @RequestMapping method用于配置响应请求的方式(post / get)
 * String username用于接收请求参数值
 * model.addAttribute("name",  username);用于将变量传递至模板
 * return "shop/hello/view";表示使用 "/WEB-INF/template/shop/hello/view.ftl"模板输出
 * 
 */
@Controller("APPCartController")
@RequestMapping("/m/cart")
public class CartController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;
	
	
	/**
	 * 获取购物车信息
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public @ResponseBody ModelMap list(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> cartList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Cart cart = cartService.getCart(member);
			if (cart == null || cart.isEmpty()) {
				model.put("datas", cartList);
				model.put("success",2);
				return model;
			}
			if(cart != null || !cart.isEmpty()) {
				try {
					for (CartItem ci : cart.getCartItems()) {
						HashMap<String, Object> cartMap = new HashMap<String, Object>();
						cartMap.put("productid", ci.getProduct().getId());
						cartMap.put("itemid", ci.getId());
						cartMap.put("image", ci.getProduct().getProductImages().size()==0? " " : ci.getProduct().getProductImages().get(0).getThumbnail());
						cartMap.put("fullname", ci.getProduct().getFullName());
						cartMap.put("price", ci.getProduct().getPrice());
						cartMap.put("quantity", ci.getQuantity());
						cartMap.put("subtotal", ci.getSubtotal());
						cartMap.put("stock", ci.getProduct().getStock());
						cartList.add(cartMap);
					}
				} catch (Exception e) {
				}
			}
			model.put("totalprice", cart.getEffectivePrice());
			model.put("datas", cartList);
			model.put("length", cartList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 加入购物车
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public @ResponseBody ModelMap add(Long userid, Long id, Integer quantity, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			id = Long.parseLong((String) obj.get("productid"));
			quantity = Integer.parseInt((String) obj.get("quantity"));
			
			Member member = memberService.find(userid);
			Product product = productService.find(id);
			Cart cart = cartService.getCart(member);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			if (product == null) {
				model.put("error", "操作错误, 该商品不存在");
				model.put("success", 1);
				return model;
			}
			if (!product.getIsMarketable()) {
				model.put("error", "操作错误, 该商品已经下架");
				model.put("success", 1);
				return model;
			}
			if (product.getIsGift()) {
				model.put("error", "操作错误, 该商品为非卖品");
				model.put("success", 1);
				return model;
			}
			if (cart == null) {
				cart = new Cart();
				cart.setKey(UUID.randomUUID().toString() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
				cart.setMember(member);
				cartService.save(cart);
			}
			if (Cart.MAX_PRODUCT_COUNT != null && cart.getCartItems().size() >= Cart.MAX_PRODUCT_COUNT) {
				model.put("error", "操作错误, 最多允许添加"+Cart.MAX_PRODUCT_COUNT+"种商品到购物车里");
				model.put("success", 1);
				return model;
			}
			if (cart.contains(product)) {
				CartItem cartItem = cart.getCartItem(product);
				if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() + quantity > CartItem.MAX_QUANTITY) {
					model.put("error", "操作错误, 商品数量不允许超过"+CartItem.MAX_QUANTITY);
					model.put("success", 1);
					return model;
				}
				if (product.getStock() != null && cartItem.getQuantity() + quantity > product.getAvailableStock()) {
					model.put("error", "操作错误, 该商品库存不足 非常抱歉!");
					model.put("success", 1);
					return model;
				}
				cartItem.add(quantity);
				cartItemService.update(cartItem);
			}  else {
				if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
					model.put("error", "操作错误, 商品数量不允许超过"+CartItem.MAX_QUANTITY);
					model.put("success", 1);
					return model;
				}
				if (product.getStock() != null && quantity > product.getAvailableStock()) {
					model.put("error", "操作错误, 该商品库存不足 非常抱歉!");
					model.put("success", 1);
					return model;
				}
				CartItem cartItem = new CartItem();
				cartItem.setQuantity(quantity);
				cartItem.setProduct(product);
				cartItem.setCart(cart);
				cartItemService.save(cartItem);
				cart.getCartItems().add(cartItem);
			}
			model.put("success", 2);
		} catch (Exception e) {
//			System.out.println("1");
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public @ResponseBody ModelMap delete(Long userid, Long itemid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			itemid = Long.parseLong((String) obj.get("itemid"));
			
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Cart cart = cartService.getCart(member);
			
			CartItem cartItem = cartItemService.find(itemid);
			Set<CartItem> cartItems = cart.getCartItems();
			if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
				model.put("success", 1);
				model.put("error", "购物车项不存在");
				return model;
			}
			cartItems.remove(cartItem);
			cartItemService.delete(cartItem);

			model.put("quantity", cart.getQuantity());
			model.put("effectivePoint", cart.getEffectivePoint());
			model.put("effectivePrice", cart.getEffectivePrice());
			model.put("promotions", cart.getPromotions());
			model.put("isLowStock", cart.getIsLowStock());
			
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	public @ResponseBody ModelMap edit(Long userid, Long itemid, Integer quantity, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			itemid = Long.parseLong((String) obj.get("itemid"));
			quantity = Integer.parseInt((String) obj.get("quantity"));
			
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Cart cart = cartService.getCart(member);
			CartItem cartItem = cartItemService.find(itemid);
			Set<CartItem> cartItems = cart.getCartItems();
			if (cart == null || cart.isEmpty()) {
				model.put("success", 1);
				model.put("error", "操作错误, 购物车为空！");
				return model;
			}			
			if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
				model.put("success", 1);
				model.put("error", "购物车项不存在");
				return model;
			}
			if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
				model.put("error", "操作错误, 商品数量不允许超过"+CartItem.MAX_QUANTITY);
				model.put("success", 1);
				return model;
			}
			Product product = cartItem.getProduct();
			if (product.getStock() != null && quantity > product.getAvailableStock()) {
				model.put("error", "操作错误, 该商品库存不足 非常抱歉!");
				model.put("success", 1);
				return model;
			}
			cartItem.setQuantity(quantity);
			cartItemService.update(cartItem);
			
			model.put("subtotal", cartItem.getSubtotal());
			model.put("isLowStock", cartItem.getIsLowStock());
			model.put("quantity", cart.getQuantity());
			model.put("effectivePoint", cart.getEffectivePoint());
			model.put("effectivePrice", cart.getEffectivePrice());
			model.put("promotions", cart.getPromotions());
			model.put("giftItems", cart.getGiftItems());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 购物车检测
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public @ResponseBody
	Boolean check() {
		Cart cart = cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 清空
	 */
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public @ResponseBody
	Message clear() {
		Cart cart = cartService.getCurrent();
		cartService.delete(cart);
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 查询购物车数量,及购物车明细
	 */
	@RequestMapping(value = "/queryCartCount", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> queryCartCount() {
		Cart cart = cartService.getCurrent();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", SUCCESS_MESSAGE);
		data.put("quantity", cart.getQuantity());
		data.put("cartall", cart.getCartItems());
		return data;     
	}

}