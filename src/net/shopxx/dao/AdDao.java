/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Ad;
import net.shopxx.entity.Store;

/**
 * Dao - 广告
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface AdDao extends BaseDao<Ad, Long> {
	
	/**
	 * @Description 根据广告位名称查询广告
	 * @author Guoxianlong
	 * 
	 * @param adPositionName 广告位名称
	 * @param store 店铺
	 * @create_date Sep 2, 2014 11:15:20 PM
	 */
	public List<Ad> findAdByAdPositionName(String adPositionName, Store store);
	
	/**
	 * @author Guoxianlong
	 */
	public Page<Ad> findPage(Pageable pageable) ;
	
	/**
	 * 根据店铺查找广告
	 * @param store
	 * 		店铺
	 * @return
	 * 		广告集合
	 * @author wmd
	 * @date 2014/11/24
	 */
	public List<Ad> findAdForDeleteStore(Store store);
}