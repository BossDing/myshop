/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberAttribute;
import net.shopxx.entity.MemberPoints;
import net.shopxx.entity.PointsWater;

/**
 * Dao - 积分流水
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface PointsWaterDao extends BaseDao<PointsWater, Long> {
	/**
	 * 查询当前用户积分流水
	 * 
	 * @return 当前用户积分流水
	 */
	List<PointsWater> findList(Member member);

	/**
	 * 查询当前用户积分流水
	 * 
	 * @return 当前用户积分流水
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
	 * 用于“人人营销”查询累计送出积分
	 * @return
	 */
	long getSumPoints();
	/**
	 * 用于“人人营销”查询参与推荐人数
	 * @return
	 */
	long getCountMembers();
}