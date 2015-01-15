package net.shopxx.dao.impl;


import java.util.List;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.shopxx.dao.WaterQualityDao;
import net.shopxx.entity.Area;
import net.shopxx.entity.WaterQualityData;


/**
 * Dao - 水质设置
 * 
 * @author WeiHuaLin
 * @version 1.0
 */
@Repository("waterQualityDaoImpl")
public class WaterQualityDaoImpl extends BaseDaoImpl<WaterQualityData,Long> implements WaterQualityDao {

	@Override
	public void UpdateWaterQualityData(Area area, Long gt_tds, Long ltqt_tds,
			Double gt_cl, Double ltqt_cl, String itemSpec) {
		//获取省ID，市ID，区ID
		Long provinceId=null, cityId=null, districtId=null;
		if(area!=null){
			if(area.getParent()!=null){
				Area parent = area.getParent();
				if(parent.getParent()!=null){
					Area root = parent.getParent();
					districtId = area.getId();
					cityId = parent.getId();
					provinceId = root.getId();
				}else{
					if(area.getChildren().size()!=0){
						provinceId = parent.getId();
						cityId = area.getId();
					}else{
						districtId = area.getId();
						cityId = parent.getId();
						provinceId = parent.getId();
					}
				}
			}else{
				provinceId = area.getId();
			}
		}
		//查找水质资料
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WaterQualityData> criteriaQuery = criteriaBuilder.createQuery(WaterQualityData.class);
		Root<WaterQualityData> root = criteriaQuery.from(WaterQualityData.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(provinceId!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Long>get("provinceId"),provinceId));
		}
		if(cityId!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Long>get("cityId"),cityId));
		}	
		if(districtId!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Long>get("districtId"),districtId));
		}
		if(gt_tds!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Long>get("tds"),gt_tds));
		}
		if(ltqt_tds!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Long>get("tds"),ltqt_tds));
		}
		if(gt_cl!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt(root.<Double>get("chlorine"),gt_cl));
		}
		if(ltqt_cl!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Double>get("chlorine"),ltqt_cl));
		}
		criteriaQuery.where(restrictions);
		List<WaterQualityData> list = findList(criteriaQuery, null, null, null, null);
		
		//更新水质资料并保存
		for(WaterQualityData w:list){
			w.setItemSpec(itemSpec);
			super.merge(w);
		}
	}

	@Override
	public List<WaterQualityData> findWaterData(Area area, String districtName) {
		//获取省ID，市ID，区ID
		Long provinceId=null, cityId=null, districtId=null;
		if(area!=null){
			if(area.getParent()!=null){
				Area parent = area.getParent();
				if(parent.getParent()!=null){
					Area root = parent.getParent();
					districtId = area.getId();
					cityId = parent.getId();
					provinceId = root.getId();
				}else{
					if(area.getChildren().size()!=0){
						provinceId = parent.getId();
						cityId = area.getId();
					}else{
						districtId = area.getId();
						cityId = parent.getId();
						provinceId = parent.getId();
					}
				}
			}else{
				provinceId = area.getId();
			}
		}
		//查找水质资料
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WaterQualityData> criteriaQuery = criteriaBuilder.createQuery(WaterQualityData.class);
		Root<WaterQualityData> root = criteriaQuery.from(WaterQualityData.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(provinceId!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Long>get("provinceId"),provinceId));
		}
		if(cityId!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Long>get("cityId"),cityId));
		}	
		if(districtId!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Long>get("districtId"),districtId));
		}
		if(districtName!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.<String>get("communityName"),districtName));
		}
		criteriaQuery.where(restrictions);
		List<WaterQualityData> list = findList(criteriaQuery, null, null, null, null);
		return list;
	}
	
}
