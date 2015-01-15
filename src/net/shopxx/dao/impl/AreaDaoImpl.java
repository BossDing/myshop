/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.AreaDao;
import net.shopxx.entity.Area;
import net.shopxx.entity.WaterQualityData;

import org.springframework.stereotype.Repository;

/**
 * Dao - 地区
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {

	public List<Area> findRoots(Integer count) {
		String jpql = "select area from Area area where area.parent is null order by area.order asc";
		TypedQuery<Area> query = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}
   
	@Override
	public List<WaterQualityData> setIds(List<WaterQualityData> list) {
		List<WaterQualityData> waters=new ArrayList<WaterQualityData>();
		String provinceName=null, cityName=null, districtName=null;
		for(WaterQualityData water:list){
			//获取省名，市名，区名
			provinceName=water.getProvinceName();
			cityName=water.getCityName();
			districtName=water.getDistrictName();
			System.out.println(cityName+"+"+provinceName+"+"+districtName);
			if(provinceName.equals(cityName)){
				//设置水质资料地区ID
				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Area> criteriaQuery = criteriaBuilder.createQuery(Area.class);
				Root<Area> root = criteriaQuery.from(Area.class);
				criteriaQuery.select(root);
				Predicate restrictions = criteriaBuilder.conjunction();
				if(districtName!=null){
					String str= cityName.trim()+districtName.trim();
					System.out.println(str);
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("name"),districtName));
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("fullName"),str));
				}
				
				criteriaQuery.where(restrictions);
				//List<Area> areas = findList(criteriaQuery, null, null, null, null);
				Area area =entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
				water.setDistrictId(area.getId());
				water.setCityId(area.getParent().getId());
				water.setProvinceId(area.getParent().getId());
				waters.add(water);
			}else{
				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Area> criteriaQuery = criteriaBuilder.createQuery(Area.class);
				Root<Area> root = criteriaQuery.from(Area.class);
				criteriaQuery.select(root);
				Predicate restrictions = criteriaBuilder.conjunction();
				if(districtName!=null){
					String str= provinceName+cityName.trim()+districtName.trim();
					System.out.println(str);
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("name"),districtName));
					restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("fullName"),str));
				}
				
				criteriaQuery.where(restrictions);
				List<Area> areas = findList(criteriaQuery, null, null, null, null);
				water.setDistrictId(areas.get(0).getId());
				water.setCityId(areas.get(0).getParent().getId());
				water.setProvinceId(areas.get(0).getParent().getParent().getId());
				waters.add(water);
			}
		}
		return waters;
	}
	
	public List<Area> findList(String areaId){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Area> criteriaQuery = criteriaBuilder.createQuery(Area.class);
		Root<Area> root = criteriaQuery.from(Area.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(areaId !=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("name"), areaId));
//			criteriaQuery.where(criteriaBuilder.equal(root.get("servicePcity"), pcity));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public Area findByAreaName(String areaName) {  
		
		if(areaName == null || areaName == ""){
			return null;
		}
		String jpql = "select area from Area area where lower(area.name) like :areaName||'%'"; 
		Area area = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("areaName", areaName).getSingleResult();
		return area;
	}
	
	/**
	 * @Description 根据名称查询出详细的地址
	 * @author Guoxianlong
	 */
	public List<Area> findPageByAreaName(String areaName) {
		String jpql = "select area from Area area where area.name like :areaName "; 
		return entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT).setParameter("areaName", "%"+areaName+"%").getResultList();
	}

	/**
	 * 根据上级的确查找下级地区
	 * 
	 * @param areId
	 *            上级id
	 * @return 下级地区
	 */
	public List<Area> findListByParent(String areaId){
		Area area = new Area();
		Long arealong = new Long(areaId);
		area.setId(arealong);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Area> criteriaQuery = criteriaBuilder.createQuery(Area.class);
		Root<Area> root = criteriaQuery.from(Area.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(areaId !=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("parent"), area));
		} 
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
}