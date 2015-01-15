/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Controller("shopCartController")
@RequestMapping("/cart")
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
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody
	Message add(Long id, Integer quantity,String ids,String quantitys, Long preSellRoleId, BigDecimal purchasePrice, HttpServletRequest request, HttpServletResponse response) {
		Cart cart = cartService.getCurrent();
		Member member = memberService.getCurrent();
		if(ids != null && ids.length()>0 && quantitys != null){
			String ss[]=ids.split(",");
			String qtys[]=quantitys.split(",");
			 for(int i=0; i<ss.length;i++){
				 Integer quantity1 = Integer.parseInt(qtys[i]);
				 Product product = productService.find(Long.parseLong(ss[i]));
					if (product == null) {
						return Message.warn("shop.cart.productNotExsit");
					}
					if (!product.getIsMarketable()) {
						return Message.warn("shop.cart.productNotMarketable");
					}
					if (product.getIsGift()) {
						return Message.warn("shop.cart.notForSale");
					}		
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
						if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() + quantity1 > CartItem.MAX_QUANTITY) {
							return Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY);
						}
						if (product.getStock() != null && cartItem.getQuantity() + quantity1 > product.getAvailableStock()) {
							return Message.warn("shop.cart.productLowStock");
						}
						cartItem.add(quantity1);
						if(purchasePrice!=null){
							cartItem.setTempPrice2(purchasePrice);
//							System.out.println("团购后:"+cartItem.getTempPrice2());
						}
						cartItemService.update(cartItem);
					} else {
						if (CartItem.MAX_QUANTITY != null && quantity1 > CartItem.MAX_QUANTITY) {
							return Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY);
						}
						if (product.getStock() != null && quantity1 > product.getAvailableStock()) {
							return Message.warn("shop.cart.productLowStock");
						}
						CartItem cartItem = new CartItem();
						cartItem.setQuantity(quantity1);
						cartItem.setProduct(product);
						cartItem.setCart(cart);
						if(purchasePrice!=null){
							cartItem.setTempPrice2(purchasePrice);
						}
						cartItemService.save(cartItem);
						cart.getCartItems().add(cartItem);
					}
					if (member == null) {
						WebUtils.addCookie(request, response, Cart.ID_COOKIE_NAME, cart.getId().toString(), Cart.TIMEOUT);
						WebUtils.addCookie(request, response, Cart.KEY_COOKIE_NAME, cart.getKey(), Cart.TIMEOUT);
					}
			 }
		}else if(ids == null && id ==null){
			return Message.warn("请勾选您想购买的商品");
		}else if(id != null){
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
			//PreSellRole preSellRole = preSellRoleService.find(preSellRoleId);	
			if (cart == null) {
				cart = new Cart();
				cart.setKey(UUID.randomUUID().toString() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
				cart.setMember(member);
				cartService.save(cart);
			}
			if (Cart.MAX_PRODUCT_COUNT != null && cart.getCartItems().size() >= Cart.MAX_PRODUCT_COUNT) {
				return Message.warn("shop.cart.addCountNotAllowed", Cart.MAX_PRODUCT_COUNT);
			}
//			System.out.println("团购价="+purchasePrice);
			if (cart.contains(product)) {
					CartItem cartItem = cart.getCartItem(product);
					if (CartItem.MAX_QUANTITY != null && cartItem.getQuantity() + quantity > CartItem.MAX_QUANTITY) {
						return Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY);
					}
					/*if (product.getStock() != null && cartItem.getQuantity() + quantity > product.getAvailableStock()) {
						return Message.warn("shop.cart.productLowStock");
					}*/
					cartItem.add(quantity);
					//hfh 2014/9/19  团购价不为空，设置购物项的临时商品价格为该团购价
					if(purchasePrice!=null){
							cartItem.setTempPrice2(purchasePrice);
//							System.out.println("团购后:"+cartItem.getTempPrice2());
					}
					cartItemService.update(cartItem);
			}else {
				if (CartItem.MAX_QUANTITY != null && quantity > CartItem.MAX_QUANTITY) {
					return Message.warn("shop.cart.maxCartItemQuantity", CartItem.MAX_QUANTITY);
				}
				/*if (product.getStock() != null && quantity > product.getAvailableStock()) {
					return Message.warn("shop.cart.productLowStock");
				}*/
				CartItem cartItem = new CartItem();
				cartItem.setQuantity(quantity);
				cartItem.setProduct(product);
			//	cartItem.setPreSellRole(preSellRole);
				cartItem.setCart(cart);
				//hfh 2014/9/19  团购价不为空，设置购物项的临时商品价格为该团购价
				if(purchasePrice!=null){
//					cartItems = cart.getCartItems();
//					for (CartItem cartItem : cartItems) {
//						if(id == cartItem.getProduct().getId()){
							cartItem.setTempPrice2(purchasePrice);
//						}
//					}
				}
				cartItemService.save(cartItem);
				cart.getCartItems().add(cartItem);
			}
			if (member == null) {
				WebUtils.addCookie(request, response, Cart.ID_COOKIE_NAME, cart.getId().toString(), Cart.TIMEOUT);
				WebUtils.addCookie(request, response, Cart.KEY_COOKIE_NAME, cart.getKey(), Cart.TIMEOUT);
			}
		}
		
		
		return Message.success("shop.cart.addSuccess", cart.getQuantity(), currency(cart.getEffectivePrice(), true, false));
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("cart", cartService.getCurrent());
		return "/shop/cart/list";
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
		data.put("productEffectivePrice", cartItem.getProductEffectivePrice());
		//hfh 2014/9/19 临时价格为团购价
//		data.put("tempPrice2", cartItem.getTempPrice2());
		data.put("saveprice", cartItem.getSavingPrice());
		data.put("price", cartItem.getPrice());
		data.put("qty", cartItem.getQuantity());
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