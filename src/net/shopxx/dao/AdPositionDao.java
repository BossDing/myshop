/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;


import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.AdPosition;
import net.shopxx.entity.Navigation;
import net.shopxx.entity.Store;


/**
 * Dao - 广告位
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface AdPositionDao extends BaseDao<AdPosition, Long> {

	/**
	 * 查找广告
	 * 
	 * @param count  
	 *            数量
	 * @return 顶级地区
	 */
	AdPosition findByName(String Name);  
	
	/**
	 * 根据名称查找广告
	 * @author guoxl
	 * @param name
	 * @param store
	 * @return
	 */
	public AdPosition findNameByStore(String name, Store store);
	
	/**
	 * 
	 * @author Guoxianlong
	 */
	public Page<AdPosition> findPage(Pageable pageable);
	
	/**
	 * 根据店铺查找广告位
	 * @param store 
	 * 		店铺
	 * @return  
	 * 		广告位
	 * @author wmd
	 * @date 2014/11/25
	 */
	public List<AdPosition> findListForDeleteStore(Store store);
	

}   