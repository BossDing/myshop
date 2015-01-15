/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.AfterBookingDao;
import net.shopxx.entity.AfterBooking;
import net.shopxx.entity.LeaveWords;
import net.shopxx.entity.AfterBooking.Type;
import net.shopxx.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 广告
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("afterBookingDaoImpl")
public class AfterBookingDaoImpl extends BaseDaoImpl<AfterBooking, Long> implements AfterBookingDao{
	public Page<AfterBooking> findPage(Long label ,Pageable pageable){
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<AfterBooking> criteriaQuery = criteriaBuilder.createQuery(AfterBooking.class);
			Root<AfterBooking> root = criteriaQuery.from(AfterBooking.class);
			criteriaQuery.select(root);
			Predicate restrictions = criteriaBuilder.conjunction();
//		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forLeaveWords")));
			if(label == 1){
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), Type.repair));
			}else if(label == 0){
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), Type.install));
			}
			String entcode = WebUtils.getXentcode();
			if (StringUtils.isNotEmpty(entcode)) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
						.equal(root.get("entCode"), entcode));
			}
			criteriaQuery.where(restrictions);
			return super.findPage(criteriaQuery, pageable);
	}
}