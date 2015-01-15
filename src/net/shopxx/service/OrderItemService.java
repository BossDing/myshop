/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;

/**
 * Service - 订单项
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface OrderItemService extends BaseService<OrderItem, Long> {
	
	/**
	 * 
	 * @param order
	 * 			订单
	 * @return
	 * 		返回订单中的未评价订单项数量
	 */
	Long countUnreview(Order order);
	
	/**
	 * 
	 * @param product
	 * @param member
	 * @return
	 * 		放回一个当前用户对应的一个最新的订单项集合（长度为一）
	 */
	List<OrderItem> toOrderItem(Product product , Member member);
}