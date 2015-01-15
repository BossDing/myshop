/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.MemberAttribute;
import net.shopxx.entity.MemberPoints;

/**
 * Dao - 会员邀请项
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface MemberPointsDao extends BaseDao<MemberPoints, Long> {


	/**
	 * 查找已启用会员注册项
	 * 
	 * @return 已启用会员注册项
	 */
	List<MemberPoints> findList();
	
	/**
	 * 判断名称是否存在
	 * 
	 * @param name
	 *            名称(忽略大小写)
	 * @return 名称是否存在
	 */
	boolean nameExists(String name);
	/**
	 * 根据积分规则名称查找实体对象。
	 * 
	 * @param rulename
	 *            消费金额
	 * @return 返回对应规则名称的积分规则实体对象，若不存在则返回null
	 */
	MemberPoints findByRuleName(String rulename);
}