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
import net.shopxx.dao.ReturnsDao;
import net.shopxx.entity.Returns;
import net.shopxx.service.ReturnsService;

import org.springframework.stereotype.Service;

/**
 * Service - 退货单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("returnsServiceImpl")
public class ReturnsServiceImpl extends BaseServiceImpl<Returns, Long> implements ReturnsService {

	@Resource(name = "returnsDaoImpl")
	private ReturnsDao returnsDao;
	
	@Resource(name = "returnsDaoImpl")
	public void setBaseDao(ReturnsDao returnsDao) {
		super.setBaseDao(returnsDao);
	}

	public Page<Returns> findPage(String orderNo, String returnOrExchangeNo,
			Integer afterSaleStatus, String logisticsNo, Date beginDate,
			Date endDate, Pageable pageable) {
		// TODO Auto-generated method stub
		return returnsDao.findPage(orderNo,returnOrExchangeNo,afterSaleStatus,logisticsNo,beginDate,endDate,pageable);
	}

}