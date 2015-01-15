/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.PointsWaterDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberPoints;
import net.shopxx.entity.PointsWater;

import org.springframework.stereotype.Repository;

/**
 * Dao - 积分流水
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("pointsWaterDaoImpl")
public class PointsWaterDaoImpl extends BaseDaoImpl<PointsWater, Long> implements PointsWaterDao {
	
	public List<PointsWater> findList(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PointsWater> criteriaQuery = criteriaBuilder.createQuery(PointsWater.class);
		Root<PointsWater> root = criteriaQuery.from(PointsWater.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member_id"), member.getId()));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

	/**
	 * 查询当前用户积分流水
	 * 
	 * @return 当前用户积分流水
	 */
	public Page<PointsWater> findPage(Member member,Pageable pageable){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PointsWater> criteriaQuery = criteriaBuilder.createQuery(PointsWater.class);
		Root<PointsWater> root = criteriaQuery.from(PointsWater.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member_id"), member.getId()));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		return super.findPage(criteriaQuery, pageable);
	}
	
	public boolean isPointsWaterExist(Long member_id, Long b_member_id,
			Integer points_stat, String rulename,String ip) {
		StringBuilder jpqlBuilder = new StringBuilder();
		jpqlBuilder.append("select count(*) from PointsWater pointswater where 1=1");
		if (member_id!=null&&member_id>=0) {
			jpqlBuilder.append(" and pointswater.member_id=:member_id");
		}
		if (b_member_id!=null&&b_member_id>=0) {
			jpqlBuilder.append(" and pointswater.b_member_id=:b_member_id");
		}
		if (points_stat!=null&&points_stat>=0) {
			jpqlBuilder.append(" and pointswater.points_stat=:points_stat");
		}
		if (rulename!=null&&rulename.length()>0) {
			jpqlBuilder.append(" and pointswater.rulename=:rulename");
		}
		if (ip!=null&&ip.length()>0) {
			jpqlBuilder.append(" and pointswater.ip=:ip");
		}
		TypedQuery<Long> typedQuery=entityManager.createQuery(jpqlBuilder.toString(),Long.class).setFlushMode(FlushModeType.COMMIT);
		if (member_id!=null&&member_id>=0) {
			typedQuery.setParameter("member_id",member_id);
		}
		if (b_member_id!=null&&b_member_id>=0) {
			typedQuery.setParameter("b_member_id",b_member_id);
		}
		if (points_stat!=null&&points_stat>=0) {
			typedQuery.setParameter("points_stat",points_stat);
		}
		if (rulename!=null&&rulename.length()>0) {
			typedQuery.setParameter("rulename",rulename);
		}
		if (ip!=null&&ip.length()>0) {
			typedQuery.setParameter("ip",ip);
		}
		Long count=typedQuery.getSingleResult();
		return count>0;
	}

	
	public long getSumPoints() {
		String jpql = "select sum(pointswater.points) from PointsWater pointswater where pointswater.rulename='推荐注册' or pointswater.rulename='推荐购买'";
		return entityManager.createQuery(jpql, Long.class).getSingleResult();
	}

	
	public long getCountMembers() {
		String jpql = "select count(distinct member_id) from PointsWater where rulename='推荐注册' or rulename='推荐购买'";
		return entityManager.createQuery(jpql, Long.class).getSingleResult();
	}	

}