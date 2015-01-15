/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.math.BigDecimal;
import java.util.List;

import net.shopxx.entity.Admin;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberPoints;
import net.shopxx.entity.MemberRank;

/**
 * Service - 会员积分规则
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface MemberPointsService extends BaseService<MemberPoints, Long> {

	/**
	 * 查找会员注册项
	 * 
	 * @return 会员注册项，仅包含已启用会员注册项
	 */
	List<MemberPoints> findList();

	/**
	 * 判断名称是否唯一
	 * 
	 * @param previousName
	 *            修改前名称(忽略大小写)
	 * @param currentName
	 *            当前名称(忽略大小写)
	 * @return 名称是否唯一
	 */
	boolean nameUnique(String previousName, String currentName);
	/**
	 * 根据积分规则名称查找实体对象。
	 * 
	 * @param rulename
	 *            积分规则名称
	 * @return 返回对应规则名称的积分规则实体对象，若不存在则返回null
	 */
	MemberPoints findByRuleName(String rulename);
}