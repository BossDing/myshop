/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.List;
import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.AdDao;
import net.shopxx.entity.Ad;
import net.shopxx.entity.Store;
import net.shopxx.util.WebUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 广告
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("adDaoImpl")
public class AdDaoImpl extends BaseDaoImpl<Ad, Long> implements AdDao {
	
	public List<Ad> findAdByAdPositionName(String adPositionName, Store store) {
		String jpql = " from Ad a where a.adPosition in ( select ap.id from AdPosition ap where ap.name = '"+adPositionName+"' ) ";
		if(store != null) {
			jpql += " and a.store = :store";
		} else {
			jpql += " and a.store is null";
		}
		TypedQuery<Ad> query = entityManager.createQuery(jpql, Ad.class).setFlushMode(FlushModeType.COMMIT);
		if(store != null) {
			query.setParameter("store", store);
		}
		return query.getResultList();
	}
	
	public Page<Ad> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Ad> criteriaQuery = criteriaBuilder.createQuery(Ad.class);
		Root<Ad> root = criteriaQuery.from(Ad.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Store store = WebUtils.getStore();
		if(store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("store"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	
	public List<Ad> findAdForDeleteStore(Store store){
		String jpql = "from Ad ad where ad.store = :store";
		TypedQuery<Ad> query = entityManager.createQuery(jpql, Ad.class).setParameter("store", store).setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}
}