/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Order;
import net.shopxx.entity.Payment;
import net.shopxx.entity.Store;

/**
 * Dao - 收款单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface PaymentDao extends BaseDao<Payment, Long> {

	/**
	 * 根据编号查找收款单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 收款单，若不存在则返回null
	 */
	Payment findBySn(String sn);
	
	/**
	 * 查找实体对象分页(增加店铺过滤)
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<Payment> findPage(Pageable pageable);
	
	/**
	 * 根据店铺查找收款单
	 * @param store
	 * 		店铺
	 * @return 
	 * 		收款单集合
	 * @author wmd
	 * @date 2014/11/25
	 */
	public List<Payment> findListForDeleteStore(Store store);
}