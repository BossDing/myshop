/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Area;
import net.shopxx.entity.WaterQualityData;

/**
 * Dao - 地区
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface AreaDao extends BaseDao<Area, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @param count  
	 *            数量
	 * @return 顶级地区
	 */
	List<Area> findRoots(Integer count);

	/**
	 * 设置水质资料地区ID
	 * @param list
	 * @return 水质资料集合
	 */
	List<WaterQualityData> setIds(List<WaterQualityData> list);
	
	List<Area> findList(String areaId);
	
	/**
	 * 根据地区名称获取当前地区顶级对象
	 */
	   
	Area findByAreaName(String areaName);
	
	/**
	 * 根据上级的确查找下级地区
	 * 
	 * @param areaId
	 *            上级id
	 * @return 下级地区
	 */
	List<Area> findListByParent(String areaId);
	
	/**
	 * @Description 根据名称查询出详细的地址
	 * @author Guoxianlong
	 * @param areaName
	 */
	public List<Area> findPageByAreaName(String areaName);
}