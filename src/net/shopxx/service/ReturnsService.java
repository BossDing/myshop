/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.Date;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Returns;

/**
 * Service - 退货单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface ReturnsService extends BaseService<Returns, Long> {
	
	/**
	 * 
	 * @param orderNo
	 * @param returnOrExchangeNo
	 * @param afterSaleStatus
	 * @param logisticsNo
	 * @param beginDate
	 * @param endDate
	 * @param pageable
	 * @returnPage<Returns>hfh
	 */
	Page<Returns> findPage(String orderNo, String returnOrExchangeNo,
			Integer afterSaleStatus, String logisticsNo, Date beginDate,
			Date endDate, Pageable pageable);

}