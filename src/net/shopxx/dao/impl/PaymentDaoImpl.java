/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import net.shopxx.Filter;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.PaymentDao;
import net.shopxx.entity.Payment;
import net.shopxx.entity.Store;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Repository;

/**
 * Dao - 收款单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("paymentDaoImpl")
public class PaymentDaoImpl extends BaseDaoImpl<Payment, Long> implements PaymentDao {

	public Payment findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select payment from Payment payment where lower(payment.sn) = lower(:sn)";
		Store store = WebUtils.getStore();
		if (store != null) jpql += " and payment.store = :store";
		try {
			 TypedQuery<Payment> query = entityManager.createQuery(jpql, Payment.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn);
			 if (store != null) query.setParameter("store", store);
			 return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Page<Payment> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Payment> criteriaQuery = criteriaBuilder.createQuery(Payment.class);
		criteriaQuery.select(criteriaQuery.from(Payment.class));
		Store store = WebUtils.getStore();
		if (store != null) {
			List<Filter> filters = new ArrayList<Filter>();
			Filter f = new Filter();
			f.setOperator(Filter.Operator.eq);
			f.setProperty("store");
			f.setValue(store);
			filters.add(f);
			return findPage(criteriaQuery, filters, null, pageable);
		} else {
			return findPage(criteriaQuery, pageable);
		}
	}
	
	public List<Payment> findListForDeleteStore(Store store){
		String jpql = "from Payment pm where pm.store = :store";
		return entityManager.createQuery(jpql, Payment.class).
			setParameter("store", store).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
}