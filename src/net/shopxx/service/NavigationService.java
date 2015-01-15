/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.entity.Navigation;
import net.shopxx.entity.Store;
import net.shopxx.entity.Navigation.Position;

/**
 * Service - 导航
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface NavigationService extends BaseService<Navigation, Long> {

	/**
	 * 查找导航
	 * 
	 * @param position
	 *            位置
	 * @return 导航
	 */
	List<Navigation> findList(Position position);

	/**
	 * 查找导航(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param cacheRegion
	 *            缓存区域
	 * @return 导航(缓存)
	 */
	List<Navigation> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion);
	
	/**
	 * @author Guoxianlong
	 * @date Oct 20, 2014 4:45:45 PM
	 */
	public List<Navigation> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion, Store store) ;
	
	/**
	 * @author Guoxianlong
	 * @date Oct 20, 2014 4:45:45 PM
	 */
	public List<Navigation> findList(Integer count, List<Filter> filters, List<Order> orders,  Store store) ;
	
	/**
	 * 根据店铺查找导航
	 * @param store 
	 * 		店铺
	 * @return  
	 * 		导航集合
	 * @author wmd
	 * @date 2014/11/24
	 */
	public List<Navigation> findListForDeleteStore(Store store);

}