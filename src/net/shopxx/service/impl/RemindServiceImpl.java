/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.dao.AdDao;
import net.shopxx.dao.AdPositionDao;
import net.shopxx.dao.RemindDao;
import net.shopxx.entity.Ad;
import net.shopxx.entity.GroupRemind;
import net.shopxx.service.AdService;
import net.shopxx.service.RemindService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 广告
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("remindServiceImpl")
public class RemindServiceImpl extends BaseServiceImpl<GroupRemind, Long> implements RemindService {
    

	@Resource(name = "remindDaoImpl")
	private RemindDao remindDao;
	
	@Resource(name = "remindDaoImpl")
	public void setBaseDao(RemindDao remindDao) {
		super.setBaseDao(remindDao);
	}

	@Override
	public List<GroupRemind> findlist() {
		System.out.println("-------sermpl------");
		return remindDao.findlist();
	} 
}