/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import net.shopxx.dao.PusheventDao;
import net.shopxx.entity.Pushevent;

import org.springframework.stereotype.Repository;

/**
 * Dao - 微信用户操作记录
 * 
 * @author shenlong
 * @version 3.0
 */
@Repository("pusheventDaoImpl")
public class PusheventDaoImpl extends BaseDaoImpl<Pushevent, Long> implements PusheventDao {

	
	public Pushevent findByFromusername(String fromusername) {
		if (fromusername == null) {
			return null;
		}
		try {
			String jpql = "select pushevents from Pushevent pushevents where lower(pushevents.fromusername) = lower(:fromusername)";
			return entityManager.createQuery(jpql, Pushevent.class).setFlushMode(FlushModeType.COMMIT).setParameter("fromusername", fromusername).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	public void deleteByFromusername(String fromusername) {
		
	}

}