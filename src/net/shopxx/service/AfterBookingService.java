/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.AfterBooking;

/**
 * Service - 属性
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface AfterBookingService extends BaseService<AfterBooking, Long> {
	Page<AfterBooking> findPage(Long label ,Pageable pageable);
}