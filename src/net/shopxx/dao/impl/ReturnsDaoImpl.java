/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.ReturnsDao;
import net.shopxx.entity.Returns;

import org.springframework.stereotype.Repository;

/**
 * Dao - 退货单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("returnsDaoImpl")
public class ReturnsDaoImpl extends BaseDaoImpl<Returns, Long> implements ReturnsDao {

	public Page<Returns> findPage(String orderNo, String returnOrExchangeNo,
			Integer afterSaleStatus, String logisticsNo, Date beginDate,
			Date endDate, Pageable pageable) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Returns> criteriaQuery = criteriaBuilder.createQuery(Returns.class);
		Root<Returns> root = criteriaQuery.from(Returns.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(beginDate != null){
			restrictions = criteriaBuilder.and(restrictions, root.get("createDate").isNotNull(), 
						criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if(endDate != null){
			restrictions = criteriaBuilder.and(restrictions, root.get("createDate").isNotNull(),
					criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		if(orderNo != null){
			System.out.println("orderNo="+orderNo);
			System.out.println("order_no="+root.get("order").get("sn"));
			restrictions = criteriaBuilder.and(restrictions, root.get("order").get("sn").isNotNull(),
					criteriaBuilder.equal(root.<String> get("order").get("sn"), orderNo));
		}
		if(returnOrExchangeNo != null){
			System.out.println("returnOrExchangeNo="+returnOrExchangeNo);
			System.out.println("sn="+root.get("sn"));
			restrictions = criteriaBuilder.and(restrictions, root.get("sn").isNotNull(),
					criteriaBuilder.equal(root.<String> get("sn"), returnOrExchangeNo));
		}
		if(afterSaleStatus != null && afterSaleStatus != 0){
			System.out.println("afterSaleStatus="+afterSaleStatus);
			System.out.println("returnsStatus="+root.get("returnsStatus"));
			restrictions = criteriaBuilder.and(restrictions, root.get("returnsStatus").isNotNull(),
					criteriaBuilder.equal(root.<String> get("returnsStatus"), afterSaleStatus));
		}
//		if(logisticsNo != null){
//			System.out.println("logisticsNo="+logisticsNo);
//			System.out.println("refundsStatus="+root.get("refundsStatus"));
//			restrictions = criteriaBuilder.and(restrictions, root.get("refundsStatus").isNotNull(),
//					criteriaBuilder.equal(root.<String> get("refundsStatus"), logisticsNo));
//		}
		
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}