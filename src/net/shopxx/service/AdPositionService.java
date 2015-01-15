/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.AdPosition;
import net.shopxx.entity.Store;

/**
 * Service - 广告位
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface AdPositionService extends BaseService<AdPosition, Long> {

	/**
	 * 查找广告位(缓存)
	 * 
	 * @param id
	 *            ID
	 * @param cacheRegion
	 *            缓存区域
	 * @return 广告位(缓存)
	 */
	AdPosition find(Long id, String cacheRegion);
	/**
	 * 查找广告位
	 * 
	 * @param name
	 *   
	 * @return 广告位
	 */
	AdPosition findByName(String Name) ;
	
	/**
	 * @author Guoxianlong
	 */
	public Page<AdPosition> findPage(Pageable pageable);
	
	/**
	 * 根据名称查找广告
	 * @author guoxl
	 * @param name
	 * @param store
	 * @return
	 */
	public AdPosition findNameByStore(String name, Store store);
	
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