/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.MemberPointsDao;
import net.shopxx.entity.MemberPoints;
import net.shopxx.entity.MemberRank;

import org.springframework.stereotype.Repository;

/**
 * Dao - 会员邀请项
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("memberPointsDaoImpl")
public class MemberPointsDaoImpl extends BaseDaoImpl<MemberPoints, Long> implements MemberPointsDao {

	public List<MemberPoints> findList() {
		String jpql = "select memberpoints from MemberPoints memberpoints";
		return entityManager.createQuery(jpql, MemberPoints.class).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
	
	public boolean nameExists(String name) {
		if (name == null) {
			return false;
		}
		String jpql = "select count(*) from MemberPoints memberpoints where lower(memberpoints.rulename) = lower(:rulename)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("rulename", name).getSingleResult();
		return count > 0;
	}

	@Override
	public MemberPoints findByRuleName(String rulename) {
		if (rulename == null) {
			return null;
		}
		String jpql = "select memberpoints from MemberPoints memberpoints where memberpoints.rulename = :rulename";
		return entityManager.createQuery(jpql, MemberPoints.class).setFlushMode(FlushModeType.COMMIT).setParameter("rulename", rulename).setMaxResults(1).getSingleResult();
	}

}