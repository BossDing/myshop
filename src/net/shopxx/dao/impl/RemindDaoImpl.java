/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import net.shopxx.dao.AdDao;
import net.shopxx.dao.RemindDao;
import net.shopxx.entity.Ad;
import net.shopxx.entity.Area;
import net.shopxx.entity.GroupRemind;

import org.springframework.stereotype.Repository;

/**
 * Dao - 调度
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("remindDaoImpl")
public class RemindDaoImpl extends BaseDaoImpl<GroupRemind, Long> implements RemindDao {

	@Override
	public List<GroupRemind> findlist() {
		System.out.println("-------impl-------");
		String jpql = "select groupgemind from GroupRemind GroupRemind";
		TypedQuery<GroupRemind> query = entityManager.createQuery(jpql, GroupRemind.class).setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}

}