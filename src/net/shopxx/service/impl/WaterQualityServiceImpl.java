package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.dao.WaterQualityDao;
import net.shopxx.entity.Area;
import net.shopxx.entity.WaterQualityData;
import net.shopxx.service.WaterQualityService;

/**
 * Service - 水质设置
 * 
 * @author WeiHuaLin
 * @version 1.0
 */
@Service("waterQualityServiceImpl")
public class WaterQualityServiceImpl extends BaseServiceImpl<WaterQualityData,Long> implements WaterQualityService{
	
	

	@Resource(name = "waterQualityDaoImpl")
	private WaterQualityDao waterQualityDao;
	
	
	@Resource(name = "waterQualityDaoImpl")
	public void setBaseDao(WaterQualityDao waterQualityDao) {
		super.setBaseDao(waterQualityDao);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = { "waterQualityData" }, allEntries = true)
	public void save(WaterQualityData waterQualityData) {
		super.save(waterQualityData);
	}  

	@Override
	@Transactional
	@CacheEvict(value = { "waterQualityData" }, allEntries = true)
	public WaterQualityData update(WaterQualityData waterQualityData) {
		return super.update(waterQualityData);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "waterQualityData" }, allEntries = true)
	public void toUpdateWaterQualityData(Area area,Long gt_tds,Long ltqt_tds,Double gt_cl,Double ltqt_cl,String itemSpec) {
		waterQualityDao.UpdateWaterQualityData(area, gt_tds, ltqt_tds, gt_cl, ltqt_cl, itemSpec);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "waterQualityData" }, allEntries = true)
	public WaterQualityData update(WaterQualityData waterQualityData, String... ignoreProperties) {
		return super.update(waterQualityData, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "waterQualityData" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "waterQualityData" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "waterQualityData" }, allEntries = true)
	public void delete(WaterQualityData waterQualityData) {
		super.delete(waterQualityData);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "waterQualityData" }, allEntries = true)
	public List<WaterQualityData> findData(Area area, String districtName) {
		return waterQualityDao.findWaterData(area,districtName);
	}

}
