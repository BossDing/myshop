package net.shopxx.service;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Store;

/**
 * Service - 店铺
 * 
 * @author: Guoxianlong
 * @date: Oct 11, 2014  11:25:17 AM
 */
public interface StoreService extends BaseService<Store, Long> {
	
	/**
	 * 申请开店
	 * @param Store 店铺
	 */
	void applyStore(Store store);
	
	/**
	 * 审核
	 * @param id store的ID
	 * @param status 审核操作 (T:通过或F:未通过)
	 */
	void check(Long id, String status);
	
	/**
	 * 按地区查找店铺
	 * 
	 * @param areaName
	 *            地区
	 * @param pageable
	 *            分页信息
	 * @return 店铺
	 */
	Page<Store> findPage(String areaName , Pageable pageable);
}
