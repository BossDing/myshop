/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.AdDao;
import net.shopxx.dao.GroupDao;
import net.shopxx.entity.Ad;
import net.shopxx.entity.GroupPurchase;
import net.shopxx.service.AdService;
import net.shopxx.service.GroupService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 团购 
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("groupServiceImpl")
public class GroupServiceImpl extends BaseServiceImpl<GroupPurchase, Long> implements GroupService {

	@Resource(name = "groupDaoImpl")
	public void setBaseDao(GroupDao groupDao) {
		super.setBaseDao(groupDao);
	}

	

}