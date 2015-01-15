/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;

/**
 * Dao - 订单项
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface OrderItemDao extends BaseDao<OrderItem, Long> {

	Long countUnreview(Order order);
	
	List<OrderItem> toOrderItem(Product product , Member member);
}