/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.dao.OrderItemDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;
import net.shopxx.service.OrderItemService;
import net.shopxx.service.OrderService;

import org.springframework.stereotype.Service;

/**
 * Service - 订单项
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long> implements OrderItemService {

	@Resource(name = "orderItemDaoImpl")
	public void setBaseDao(OrderItemDao orderItemDao) {
		super.setBaseDao(orderItemDao);
	}
	
	@Resource(name = "orderItemDaoImpl")
	private OrderItemDao orderItemDao;
	/**
	@Resource(name = "vImpl")
	private OrderItemDao orderItemDao;
	**/
	public Long countUnreview(Order order){
		return orderItemDao.countUnreview(order);
	}
	
	public List<OrderItem> toOrderItem(Product product , Member member){
		return orderItemDao.toOrderItem(product ,member);
	}
	

}