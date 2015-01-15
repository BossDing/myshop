package net.shopxx.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.StoreDao;
import net.shopxx.entity.Store;

import org.springframework.stereotype.Repository;

/**
 * Dao - 店铺
 * 
 * @author: Guoxianlong
 * @date: Oct 11, 2014  11:29:29 AM
 */
@Repository("storeDaoImpl")
public class StoreDaoImpl extends BaseDaoImpl<Store, Long> implements StoreDao {

	@Override
	public Page<Store> findPage(String areaName, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Store> criteriaQuery = criteriaBuilder.createQuery(Store.class);
		Root<Store> root = criteriaQuery.from(Store.class);
		criteriaQuery.select(root);  
		criteriaQuery.where(criteriaBuilder.like(root.get("areaName").as(String.class), "%"+areaName+"%"));
		return super.findPage(criteriaQuery, pageable);  
	}

	@Override
	public Boolean isStoreNameExists(String storeName) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Store> criteriaQuery = criteriaBuilder.createQuery(Store.class);
		Root<Store> root = criteriaQuery.from(Store.class);
		criteriaQuery.select(root);  
		criteriaQuery.where(criteriaBuilder.equal(root.get("name").as(String.class), storeName));
		Long rows = super.count(criteriaQuery, null);
		return rows == 0 ? false : true;
	}
	
}
