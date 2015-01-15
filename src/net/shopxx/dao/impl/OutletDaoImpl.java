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
import net.shopxx.dao.OutletDao;
import net.shopxx.entity.Experience;
import net.shopxx.entity.Outlet;

import org.springframework.stereotype.Repository;

/**
 * 服务网点
 * @author Mr.Zhang
 *
 */
@Repository("outletDaoImpl")
public class OutletDaoImpl extends BaseDaoImpl<Outlet, Long> implements OutletDao{
	
	/**查询服务网点*/
	public List<Outlet> findOutlets(String areaName,String categoryName,String entCode) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Outlet> criteriaQuery = criteriaBuilder.createQuery(Outlet.class);
		Root<Outlet> root = criteriaQuery.from(Outlet.class); 
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(areaName!=null){
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.like(root.get("areaName").as(String.class), "%"+areaName+"%"));
		}
		if(categoryName!=null){
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.like(root.get("categoryNames").as(String.class), "%"+categoryName+"%"));
		}
		if(entCode!=null){
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.like(root.get("entCode").as(String.class), "%"+entCode+"%"));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
}