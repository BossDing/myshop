package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Area;
import net.shopxx.entity.WaterQualityData;

/**
 * Dao - 水质设置
 * 
 * @author WeiHuaLin
 * @version 1.0
 */
public interface WaterQualityDao extends BaseDao<WaterQualityData,Long>{
	
	/**
	 * 更新推荐机型
	 * @param area 地区
	 * @param gt_tds 大于tds
	 * @param ltqt_tds 小于等于tds
	 * @param gt_cl 大于 余氯
	 * @param ltqt_cl 小于等于 余氯
	 * @param itemSpec 机型
	 */
	void UpdateWaterQualityData(Area area,Long gt_tds,Long ltqt_tds,Double gt_cl,Double ltqt_cl,String itemSpec);
	/**
	 * 查找小区水质资料
	 * @param area
	 * @param districtName
	 * @return
	 */
	List<WaterQualityData> findWaterData(Area area,String districtName);
}
