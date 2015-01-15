/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.mobile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shopxx.Message;
import net.shopxx.entity.Cart;
import net.shopxx.entity.CartItem;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.entity.Store;
import net.shopxx.service.CartItemService;
import net.shopxx.service.CartService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
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
@Controller("mobileCartController")
@RequestMapping("/mobile/cart")
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
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody
	Message add(Long id, Integer quantity, HttpServletRequest request, HttpServletResponse response) {
		if (quantity == null || quantity < 1) {
			return ERROR_MESSAGE;
		}
		Product product = productService.find(id);
		if (product == null) {
			return Message.warn("shop.cart.productNotExsit");
		}
		if (!product.getIsMarketable()) {
			return Message.warn("shop.cart.productNotMarketable");
		}
		if (product.getIsGift()) {
			return Message.warn("shop.cart.notForSale");
		}

		Cart cart = cartService.getCurrent();
		Member member = memberService.getCurrent();

		if (cart == null) {
			cart = new Cart();
			cart.setKey(UUID.randomUUID().toString() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
			cart.setMember(member);
			cartService.save(cart);
		}

		if (Cart.MAX_PRODUCT_COUNT != null && cart.getCartItems().size() >= Cart.MAX_PRODUCT_COUNT) {
			return Message.warn("shop.cart.addCountNotAllowed", Cart.MAX_PRODUCT_COUNT);
		}

		if (cart.contains(product)) {
			CartItem cartItem = cart.getCartItem(product);
			if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() + quantity > CartItem.MAX_QUANTITY) {
				return Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY);
			}
			if (product.getStock() != null && cartItem.getQuantity() + quantity > product.getAvailableStock()) {
				return Message.warn("shop.cart.productLowStock");
			}
			cartItem.add(quantity);
			cartItemService.update(cartItem);
		} else {
			if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
				return Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY);
			}
			if (product.getStock() != null && quantity > product.getAvailableStock()) {
				return Message.warn("shop.cart.productLowStock");
			}
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItemService.save(cartItem);
			cart.getCartItems().add(cartItem);
		}

		if (member == null) {
			WebUtils.addCookie(request, response, Cart.ID_COOKIE_NAME, cart.getId().toString(), Cart.TIMEOUT);
			WebUtils.addCookie(request, response, Cart.KEY_COOKIE_NAME, cart.getKey(), Cart.TIMEOUT);
		}
		return Message.success("shop.cart.addSuccess", cart.getQuantity(), currency(cart.getEffectivePrice(), true, false));
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		WebUtils.getOpenidtoLoginForWechat(request, response, session, memberService, cartService);
		Cart cart = cartService.getCurrent();
		model.addAttribute("cart", cart);
		return "mobile/cart/list";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> edit(Long id, Integer quantity) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (quantity == null || quantity < 1) {
			data.put("message", ERROR_MESSAGE);
			return data;
		}
		Cart cart = cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			data.put("message", Message.error("shop.cart.notEmpty"));
			return data;
		}
		CartItem cartItem = cartItemService.find(id);
		Set<CartItem> cartItems = cart.getCartItems();
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			data.put("message", Message.error("shop.cart.cartItemNotExsit"));
			return data;
		}
		if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
			data.put("message", Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY));
			return data;
		}
		Product product = cartItem.getProduct();
		if (product.getStock() != null && quantity > product.getAvailableStock()) {
			data.put("message", Message.warn("shop.cart.productLowStock"));
			return data;
		}
		cartItem.setQuantity(quantity);
		cartItemService.update(cartItem);

		data.put("message", SUCCESS_MESSAGE);
		data.put("subtotal", cartItem.getSubtotal());
		data.put("isLowStock", cartItem.getIsLowStock());
		data.put("quantity", cart.getQuantity());
		data.put("effectivePoint", cart.getEffectivePoint());
		data.put("effectivePrice", cart.getEffectivePrice());
		data.put("promotions", cart.getPromotions());
		data.put("giftItems", cart.getGiftItems());
		return data;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> delete(Long id) {
		Map<String, Object> data = new HashMap<String, Object>();
		Cart cart = cartService.getCurrent();
		if (cart == null || cart.isEmpty()) {
			data.put("message", Message.error("shop.cart.notEmpty"));
			return data;
		}
		CartItem cartItem = cartItemService.find(id);
		Set<CartItem> cartItems = cart.getCartItems();
		if (cartItem == null || cartItems == null || !cartItems.contains(cartItem)) {
			data.put("message", Message.error("shop.cart.cartItemNotExsit"));
			return data;
		}
		cartItems.remove(cartItem);
		cartItemService.delete(cartItem);

		data.put("message", SUCCESS_MESSAGE);
		data.put("quantity", cart.getQuantity());
		data.put("effectivePoint", cart.getEffectivePoint());
		data.put("effectivePrice", cart.getEffectivePrice());
		data.put("promotions", cart.getPromotions());
		data.put("isLowStock", cart.getIsLowStock());
		return data;
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
	
	/**
	 * 查询购物车里是否存在店铺商品
	 * @author wmd
	 * @date 2014/12/1
	 */
	@RequestMapping(value = "/queryCartIsExistStore", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> queryCartIsExistStore(Long id) {
		Map<String, Object> data = new HashMap<String, Object>();
		Cart cart = cartService.getCurrent();
		Product p = productService.find(id);
		Store st = p.getStore(); 
		if(cart != null) {
			Set<CartItem> cartItems = cart.getCartItems();
			if(cartItems.size() != 0) {
				for(CartItem cartItem : cartItems) {
					Product product = cartItem.getProduct();
					Store store = product.getStore();
					if(st == null) {
						if(store != null) {
							data.put("message", Message.error("购物车已存在店铺商品, 添加失败!"));
							return data;
						} else {
							data.put("message", SUCCESS_MESSAGE);
							return data;
						}
					} else if(st != null) {
						if(store == null) {
							data.put("message", Message.error("购物车存在非店铺商品, 添加失败!"));
							return data;
						}
						if(st == store) {
							data.put("message", SUCCESS_MESSAGE);
							return data;
						}
					}
				}
			}
		}
		data.put("message", SUCCESS_MESSAGE);
		return data;
	}
}