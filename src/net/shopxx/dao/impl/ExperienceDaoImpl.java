package net.shopxx.dao.impl;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.ExperienceDao;
import net.shopxx.entity.Experience;


import org.springframework.stereotype.Repository;

/**
 * Dao - 体验中心
 * 
 * @author DongXinXing
 * @version 1.0    
 */
@Repository("experienceDaoImpl")
public class ExperienceDaoImpl extends BaseDaoImpl<Experience, Long> implements ExperienceDao {
  
	public List<Experience> findList(Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Experience> criteriaQuery = criteriaBuilder.createQuery(Experience.class);
		Root<Experience> root = criteriaQuery.from(Experience.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public Page<Experience> findPage(String areaName, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Experience> criteriaQuery = criteriaBuilder.createQuery(Experience.class);
		Root<Experience> root = criteriaQuery.from(Experience.class);
		criteriaQuery.select(root);  
		criteriaQuery.where(criteriaBuilder.like(root.get("areaName").as(String.class), "%"+areaName+"%"));
		return super.findPage(criteriaQuery, pageable);  
	}

	@Override
	public List<Experience> findAll(String searchWord, String fullName,String province, String city) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Experience> criteriaQuery = criteriaBuilder.createQuery(Experience.class);
		Root<Experience> root = criteriaQuery.from(Experience.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(searchWord!=null){
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.like(root.get("name").as(String.class), "%"+searchWord+"%"));
		}
		if(province!=null){
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.like(root.get("areaName").as(String.class), "%"+province+"%"));
		}
		if(city!=null){
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.like(root.get("areaName").as(String.class), "%"+city+"%"));
		}
		if(fullName!=null){
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.like(root.get("areaName").as(String.class), "%"+fullName+"%"));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}
	  
	
	
	
}