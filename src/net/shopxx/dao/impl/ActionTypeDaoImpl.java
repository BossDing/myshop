package net.shopxx.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import net.shopxx.dao.ActionTypeDao;
import net.shopxx.entity.ActionType;
import net.shopxx.entity.Member;

/**
 * daoImpl - 活动分类
 * 
 * @author lzy
 * @version 1.0
 */
@Repository("actionTypeDaoImpl")
public class ActionTypeDaoImpl extends BaseDaoImpl<ActionType, Long> implements
		ActionTypeDao {

	@Override
	public ActionType findByActionName(String actionName) {
		if (actionName == null) {
			return null;
		}
		try {
			String jpql = "select actionType from ActionType actionType where lower(actionType.actionName) = lower(:actionName)";
			return entityManager.createQuery(jpql, ActionType.class).setFlushMode(FlushModeType.COMMIT).setParameter("actionName", actionName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
