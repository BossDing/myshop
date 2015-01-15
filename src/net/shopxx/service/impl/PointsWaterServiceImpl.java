/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.PointsWaterDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.PointsWater;
import net.shopxx.service.PointsWaterService;
import org.springframework.stereotype.Service;

/**
 * Service - 积分流水
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("pointsWaterServiceImpl")
public class PointsWaterServiceImpl extends BaseServiceImpl<PointsWater, Long> implements PointsWaterService {
	@Resource(name = "pointsWaterDaoImpl")
	private PointsWaterDao pointsWaterDao;
	
	@Resource(name = "pointsWaterDaoImpl")
	public void setBaseDao(PointsWaterDao pointsWaterDao) {
		super.setBaseDao(pointsWaterDao);
	}
	
	public List<PointsWater> findList(Member member) {
		return pointsWaterDao.findList(member);
	}

	public Page<PointsWater> findPage(Member member,Pageable pageable){
		return pointsWaterDao.findPage(member,pageable);
		
	}
	
	public boolean isPointsWaterExist(Long member_id, Long b_member_id,
			Integer points_stat, String rulename,String ip) {
		return pointsWaterDao.isPointsWaterExist(member_id, b_member_id, points_stat, rulename, ip);
	}

	
	public long getSumPoints() {
		return pointsWaterDao.getSumPoints();
	}

	
	public long getCountMembers() {
		return pointsWaterDao.getCountMembers();
	}
}