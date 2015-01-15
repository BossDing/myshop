package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Area;
import net.shopxx.entity.WaterQualityData;

/**
 * Service - 水质设置
 * 
 * @author WeiHuaLin
 * @version 1.0
 */
public interface WaterQualityService extends BaseService<WaterQualityData,Long>{
	
	/**
	 * 更新水质推荐机器
	 * @param area
	 * @param gt_tds
	 * @param ltqt_tds
	 * @param gt_cl
	 * @param ltqt_cl
	 * @param itemSpec
	 */
	void toUpdateWaterQualityData(Area area,Long gt_tds,Long ltqt_tds,Double gt_cl,Double ltqt_cl,String itemSpec);
	/**
	 * 查找小区水质资料
	 * @param area
	 * @param districtName
	 * @return
	 */
	List<WaterQualityData> findData(Area area,String districtName);
	
	
}
