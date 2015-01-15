/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;


import java.util.Date;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Refunds;

/**
 * Dao - 退款单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface RefundsDao extends BaseDao<Refunds, Long> {
	
	/**
	 * 
	 * @param orderNo
	 * @param returnOrExchangeNo
	 * @param afterSaleStatus
	 * @param logisticsNo
	 * @param begindate
	 * @param enddate
	 * @param pageable
	 * @returnPage<Refunds>hfh
	 */
	Page<Refunds> findPage(String orderNo, String returnOrExchangeNo,
			Integer afterSaleStatus, String logisticsNo, Date beginDate,
			Date endDate, Pageable pageable);
	
}