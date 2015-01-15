/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.shopxx.Setting;
import net.shopxx.util.SettingUtils;

/**
 * Entity - 购物车项
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Entity
@Table(name = "xx_cart_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_cart_item_sequence")
public class CartItem extends BaseEntity {

	private static final long serialVersionUID = 2979296789363163144L;

	/** 最大数量 */
	public static final Integer MAX_QUANTITY = 10000;

	/** 数量 */
	private Integer quantity;

	/** 商品 */
	private Product product;

	/** 购物车 */
	private Cart cart;

	/** 临时商品价格 */
	private BigDecimal tempPrice;
	
	/** 临时价格(目前团购价用到) */
	private BigDecimal tempPrice2;

	public BigDecimal getTempPrice2() {
		return tempPrice2;
	}

	public void setTempPrice2(BigDecimal tempPrice2) {
		this.tempPrice2 = tempPrice2;
	}

	/** 临时赠送积分 */
	private Long tempPoint;

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置数量
	 * 
	 * @param quantity
	 *            数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取购物车
	 * 
	 * @return 购物车
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Cart getCart() {
		return cart;
	}

	/**
	 * 设置购物车
	 * 
	 * @param cart
	 *            购物车
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/**
	 * 获取临时商品价格
	 * 
	 * @return 临时商品价格
	 */
	@Transient
	public BigDecimal getTempPrice() {
		if (tempPrice == null) {
			return getSubtotal();
		}
		return tempPrice;
	}

	/**
	 * 设置临时商品价格
	 * 
	 * @param tempPrice
	 *            临时商品价格
	 */
	@Transient
	public void setTempPrice(BigDecimal tempPrice) {
		this.tempPrice = tempPrice;
	}

	/**
	 * 获取临时赠送积分
	 * 
	 * @return 临时赠送积分
	 */
	@Transient
	public Long getTempPoint() {
		if (tempPoint == null) {
			return getPoint();
		}
		return tempPoint;
	}

	/**
	 * 设置临时赠送积分
	 * 
	 * @param tempPoint
	 *            临时赠送积分
	 */
	@Transient
	public void setTempPoint(Long tempPoint) {
		this.tempPoint = tempPoint;
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Transient
	public long getPoint() {
		if (getProduct() != null && getProduct().getPoint() != null && getQuantity() != null) {
			return getProduct().getPoint() * getQuantity();
		} else {
			return 0L;
		}
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		if (getProduct() != null && getProduct().getWeight() != null && getQuantity() != null) {
			return getProduct().getWeight() * getQuantity();
		} else {
			return 0;
		}
	}

	/**
	 * 获取价格
	 * 
	 * @return 价格
	 */
	@Transient
	public BigDecimal getPrice() {
		Setting setting = SettingUtils.get();
		if(getTempPrice2() != null){
//			System.out.println("one");
			return setting.setScale(this.tempPrice2);
		}else if (getProduct() != null && getProduct().getPrice() != null) {
//			System.out.println("one-one");
			if (getCart() != null && getCart().getMember() != null && getCart().getMember().getMemberRank() != null) {
				MemberRank memberRank = getCart().getMember().getMemberRank();
				Map<MemberRank, BigDecimal> memberPrice = getProduct().getMemberPrice();
				if (memberPrice != null && !memberPrice.isEmpty()) {
					if (memberPrice.containsKey(memberRank)) {
						return setting.setScale(memberPrice.get(memberRank));
					}
				}
				if (memberRank.getScale() != null) {
					return setting.setScale(getProduct().getPrice().multiply(new BigDecimal(memberRank.getScale())));
				}
			}
			return setting.setScale(getProduct().getPrice());
		} else {
//			System.out.println("one-one-one");
			return new BigDecimal(0);
		}
	}
	
	/**
	 * 获取节省价格
	 * @returnBigDecimal	hfh
	 *2014/09/28
	 */
	@Transient
	public BigDecimal getSavingPrice() {
		Setting setting = SettingUtils.get();
		return setting.setScale(getProduct().getMarketPrice().subtract(getPrice()));
	}
	

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@Transient
	public BigDecimal getSubtotal() {
		if (getQuantity() != null) {
//			if(getTempPrice() != null){
//				return getTempPrice().multiply(new BigDecimal(getQuantity()));
//			}else {
				return getPrice().multiply(new BigDecimal(getQuantity()));
//			}
		} else {
			return new BigDecimal(0);
		}
	}
	
	/**
	 * 获取节省金额
	 * @returnBigDecimal	hfh
	 *  2014/09/28
	 */
	@Transient
	public BigDecimal getSavingMoney(){
		if(getQuantity() != null && getProduct() != null && getProduct().getPrice() != null){
//			System.out.println("总共节省："+getProduct().getMarketPrice().subtract(getPrice()).multiply(new BigDecimal(getQuantity())));
			return getProduct().getMarketPrice().subtract(getPrice()).multiply(new BigDecimal(getQuantity()));
		}else{
			return new BigDecimal(0);
		}
	}
	

	/**
	 * 获取是否库存不足
	 * 
	 * @return 是否库存不足
	 */
	@Transient
	public boolean getIsLowStock() {
		if (getQuantity() != null && getProduct() != null && getProduct().getStock() != null && getQuantity() > getProduct().getAvailableStock()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 增加商品数量
	 * 
	 * @param quantity
	 *            数量
	 */
	@Transient
	public void add(int quantity) {
		if (quantity > 0) {
			if (getQuantity() != null) {
				setQuantity(getQuantity() + quantity);
			} else {
				setQuantity(quantity);
			}
		}
	}
	
	/**
	 * 判断促销是否有效
	 * 
	 * @param promotion
	 *            促销
	 * @return 促销是否有效
	 */
	@Transient
	private boolean isValid(Promotion promotion) {
		if (promotion == null || !promotion.hasBegun() || promotion.hasEnded()) {
			return false;
		}
		if (promotion.getMemberRanks() == null || promotion.getMemberRanks().isEmpty() || getCart().getMember() == null || getCart().getMember().getMemberRank() == null || !promotion.getMemberRanks().contains(getCart().getMember().getMemberRank())) {
			return false;
		}
		Integer quantity = getQuantity();
		if ((promotion.getMinimumQuantity() != null && promotion.getMinimumQuantity() > quantity) || (promotion.getMaximumQuantity() != null && promotion.getMaximumQuantity() < quantity)) {
			return false;
		}
		BigDecimal price = getSubtotal();
		if ((promotion.getMinimumPrice() != null && promotion.getMinimumPrice().compareTo(price) > 0) || (promotion.getMaximumPrice() != null && promotion.getMaximumPrice().compareTo(price) < 0)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取折扣
	 * 
	 * @return 折扣
	 */
	@Transient
	public BigDecimal getDiscount() {
		BigDecimal originalPrice = new BigDecimal(0);
		BigDecimal currentPrice = new BigDecimal(0);
		if(getProduct().getProductCategory().getPromotions() != null){
			for (Promotion promotion : getProduct().getProductCategory().getPromotions()) {
				if (promotion != null) {
					if(isValid(promotion)){
						int promotionQuantity = 1;
						BigDecimal originalPromotionPrice = getPrice();
						BigDecimal currentPromotionPrice = promotion.calculatePrice(promotionQuantity, originalPromotionPrice);
						originalPrice = originalPrice.add(originalPromotionPrice);
						currentPrice = currentPrice.add(currentPromotionPrice);
					}
				}
			}
		}
		if(getProduct().getPromotions() != null){
			for (Promotion promotion : getProduct().getPromotions()) {
				if (promotion != null) {
					if(isValid(promotion)){
						int promotionQuantity = 1;
						BigDecimal originalPromotionPrice = getPrice();
						BigDecimal currentPromotionPrice = promotion.calculatePrice(promotionQuantity, originalPromotionPrice);
						originalPrice = originalPrice.add(originalPromotionPrice);
						currentPrice = currentPrice.add(currentPromotionPrice);
					}
				}
			}
		}
		Setting setting = SettingUtils.get();
		BigDecimal discount = setting.setScale(originalPrice.subtract(currentPrice));
		return discount.compareTo(new BigDecimal(0)) > 0 ? discount : new BigDecimal(0);
	}

	/**
	 * 获取有效商品价格
	 * 
	 * @return 有效商品价格
	 */
	@Transient
	public BigDecimal getProductEffectivePrice() {
		BigDecimal effectivePrice = getPrice();
		if(getProduct().getProductCategory().getPromotions() != null){
			for(Promotion promotion : getProduct().getProductCategory().getPromotions()){
				if (promotion != null) {
					if(isValid(promotion)){
						effectivePrice = getPrice().subtract(getDiscount());
					}else{
						effectivePrice = getPrice();
					}
				}else{
					effectivePrice = getPrice();
				}
			}
		}
		if(getProduct().getPromotions() != null){
			for(Promotion promotion : getProduct().getPromotions()){
				if (promotion != null) {
					if(isValid(promotion)){
						effectivePrice = getPrice().subtract(getDiscount());
					}else{
						effectivePrice = getPrice();
					}
				}else{
					effectivePrice = getPrice();
				}
			}
		}
		if(getProduct().getProductCategory().getPromotions() == null && getProduct().getPromotions() == null){
			effectivePrice = getPrice();
		}
		return effectivePrice.compareTo(new BigDecimal(0)) > 0 ? effectivePrice : new BigDecimal(0);
	}

}