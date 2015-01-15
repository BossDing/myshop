package net.shopxx.dao;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Store;

/**
 * Dao - 管理员
 * 
 * @author: Guoxianlong
 * @date: Oct 11, 2014  11:28:33 AM
 */
public interface StoreDao extends BaseDao<Store, Long> {
	
	
	/**
	 * 按地区查找体验中心
	 * 
	 * @param areaName
	 *            地区      
	 * @param pageable
	 *            分页信息
	 * @return 收藏商品分页
	 */
	Page<Store> findPage(String areaName, Pageable pageable);
	
	/**
	 * 店铺名称存在性
	 * @param storeName
	 * @return
	 */
	Boolean isStoreNameExists (String storeName);
}
