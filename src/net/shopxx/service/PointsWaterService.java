/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.PointsWater;

/**
 * Service - 积分流水
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface PointsWaterService extends BaseService<PointsWater, Long> {
	/**
	 * 查找当前用户的积分流水。
	 * 
	 * @param member
	 *            当前登录的用户。
	 * @return 前用户的积分流水。
	 */
	List<PointsWater> findList(Member member);
	
	/**
	 * 查找当前用户的积分流水。
	 * 
	 * @param member
	 *            当前登录的用户。
	 * @return 前用户的积分流水。
	 */
	Page<PointsWater> findPage(Member member,Pageable pageable);

	/**
	 * 判断是否存在该流水积分。
	 * 
	 * @param member_id
	 *            被赠送的用户id
	 * @param b_member_id
	 *            赠送用户的id
	 * @param points_stat
	 *            积分状态
	 * @param note
	 *            备注
	 * @return 是否存在改积分流水。
	 */
	boolean isPointsWaterExist(Long member_id, Long b_member_id,
			Integer points_stat, String rulename,String ip);
	/**
	 * 用于“人人营销”查询获取累计送出积分
	 * @return
	 */
	long getSumPoints();
	/**
	 * 用于“人人营销”查询参与推荐人数
	 * @return
	 */
	long getCountMembers();
}