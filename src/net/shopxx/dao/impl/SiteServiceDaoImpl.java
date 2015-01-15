package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.SiteServiceDao;
import net.shopxx.entity.SiteService;


@Repository("siteServiceDaoImpl")
public class SiteServiceDaoImpl extends BaseDaoImpl<SiteService, Long> implements SiteServiceDao{

	public Page<SiteService> findPage(String pcity, Pageable pageable) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SiteService> criteriaQuery = criteriaBuilder.createQuery(SiteService.class);
		Root<SiteService> root = criteriaQuery.from(SiteService.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(pcity !=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("servicePcity"), pcity));
//			criteriaQuery.where(criteriaBuilder.equal(root.get("servicePcity"), pcity));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	
	}

	public List<SiteService> findList(String province){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SiteService> criteriaQuery = criteriaBuilder.createQuery(SiteService.class);
		Root<SiteService> root = criteriaQuery.from(SiteService.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(province !=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("serviceProvince"), province));
//			criteriaQuery.where(criteriaBuilder.equal(root.get("servicePcity"), pcity));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}
}
