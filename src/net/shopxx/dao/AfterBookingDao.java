/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.AfterBooking;

/**
 * Dao - 售后预约
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface AfterBookingDao extends BaseDao<AfterBooking,Long> {
	Page<AfterBooking> findPage(Long laber ,Pageable pageable);
}