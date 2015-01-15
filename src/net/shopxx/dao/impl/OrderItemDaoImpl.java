/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import net.shopxx.dao.OrderItemDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;
import org.springframework.stereotype.Repository;

/**
 * Dao - 订单项
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

	public Long countUnreview(Order order){
		Long count = 0L;
		if(order != null){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrderItem> criteriaQuery = criteriaBuilder.createQuery(OrderItem.class);
		Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		//restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("order"), order));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("reviewStatus"), false));
		criteriaQuery.where(restrictions);
		count = super.count(criteriaQuery, null);   
		}
		return count;
	}
	
	public List<OrderItem> toOrderItem(Product product , Member member){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrderItem> criteriaQuery = criteriaBuilder.createQuery(OrderItem.class);
		Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		//restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
		if(product != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		if(member != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.join("order").<Order>get("member"), member));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("reviewStatus"), false));
		criteriaQuery.where(restrictions);
		List<net.shopxx.Order> orders = new ArrayList<net.shopxx.Order>();
		return super.findList(criteriaQuery, null, 1, null, orders);
	}
}