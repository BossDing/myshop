/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.dao.NavigationDao;
import net.shopxx.entity.Navigation;
import net.shopxx.entity.Store;
import net.shopxx.entity.Navigation.Position;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Repository;

/**
 * Dao - 导航
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("navigationDaoImpl")
public class NavigationDaoImpl extends BaseDaoImpl<Navigation, Long> implements NavigationDao {

	public List<Navigation> findList(Position position) {
		Store store = WebUtils.getStore();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Navigation> criteriaQuery = criteriaBuilder.createQuery(Navigation.class);
		Root<Navigation> root = criteriaQuery.from(Navigation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (position != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("position"), position));
		}
		if (store != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
	
	public List<Navigation> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion, Store store) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Navigation> criteriaQuery = criteriaBuilder.createQuery(Navigation.class);
		Root<Navigation> root = criteriaQuery.from(Navigation.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (store != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		} else {
			restrictions = criteriaBuilder.and(restrictions, root.get("store").isNull());
		}
		criteriaQuery.where(restrictions);
		return findList(criteriaQuery, null, count, filters, orders);
	}
	public List<Navigation> findListForDeleteStore(Store store){
		String jpql = "from Navigation ng where ng.store = :store";
		return entityManager.createQuery(jpql, Navigation.class).setParameter("store", store).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
	

}