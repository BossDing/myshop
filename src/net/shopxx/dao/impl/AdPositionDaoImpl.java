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

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.AdPositionDao;
import net.shopxx.entity.AdPosition;
import net.shopxx.entity.Navigation;
import net.shopxx.entity.Store;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Repository;

/**
 * Dao - 广告位
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("adPositionDaoImpl")
public class AdPositionDaoImpl extends BaseDaoImpl<AdPosition, Long> implements
		AdPositionDao {
	//TODO CriteriaBuilder 这是什么意思
	@Override
	public AdPosition findByName(String Name) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdPosition> criteriaQuery = criteriaBuilder
				.createQuery(AdPosition.class);
		Root<AdPosition> root = criteriaQuery.from(AdPosition.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (Name != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("name"), Name));
		}
		criteriaQuery.where(restrictions);
		List<AdPosition> adpositionlist = super.findList(criteriaQuery, null,
				null, null, null);
		AdPosition adPosition = new AdPosition();
		if (adpositionlist.size() > 0) {
			adPosition = adpositionlist.get(0);
		}
		return adPosition;
	}

	public AdPosition findNameByStore(String name, Store store) {
		String jpql = "select ap from AdPosition ap where ap.name = :name and ap.stores = :store";
		return entityManager.createQuery(jpql, AdPosition.class)
				.setFlushMode(FlushModeType.COMMIT).setParameter("name", name)
				.setParameter("store", store).getSingleResult();
	}

	public Page<AdPosition> findPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdPosition> criteriaQuery = criteriaBuilder
				.createQuery(AdPosition.class);
		Root<AdPosition> root = criteriaQuery.from(AdPosition.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("stores"), store));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	
	public List<AdPosition> findListForDeleteStore(Store store){
		String jpql = "from AdPosition adp where adp.stores = :store";
		return entityManager.createQuery(jpql, AdPosition.class).setParameter("store", store).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

}