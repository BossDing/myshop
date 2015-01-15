/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service.impl;


import java.util.Date;

import javax.annotation.Resource;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.RefundsDao;
import net.shopxx.entity.Refunds;
import net.shopxx.service.RefundsService;

import org.springframework.stereotype.Service;

/**
 * Service - 退款单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("refundsServiceImpl")
public class RefundsServiceImpl extends BaseServiceImpl<Refunds, Long> implements RefundsService {

	@Resource(name = "refundsDaoImpl")
	private RefundsDao refundsDao;
	
	@Resource(name = "refundsDaoImpl")
	public void setBaseDao(RefundsDao refundsDao) {
		super.setBaseDao(refundsDao);
	}

	public Page<Refunds> findPage(String orderNo, String returnOrExchangeNo,
			Integer afterSaleStatus, String logisticsNo, Date beginDate,
			Date endDate, Pageable pageable) {
		// TODO Auto-generated method stub
		return refundsDao.findPage(orderNo,returnOrExchangeNo,afterSaleStatus,logisticsNo,beginDate,endDate,pageable);
	}

}