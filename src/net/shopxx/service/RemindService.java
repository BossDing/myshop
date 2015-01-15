/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Ad;
import net.shopxx.entity.GroupRemind;

/**
 * Service - 调度
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface RemindService extends BaseService<GroupRemind, Long> {
    
	List<GroupRemind> findlist();
}