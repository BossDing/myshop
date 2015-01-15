/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import net.shopxx.dao.PluginConfigDao;
import net.shopxx.entity.PluginConfig;
import net.shopxx.entity.Store;
import net.shopxx.plugin.PaymentPlugin;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Repository;

/**
 * Dao - 插件配置
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("pluginConfigDaoImpl")
public class PluginConfigDaoImpl extends BaseDaoImpl<PluginConfig, Long> implements PluginConfigDao {

	public boolean pluginIdExists(String pluginId) {
		if (pluginId == null) {
			return false;
		}
		String jpql = "select count(*) from PluginConfig pluginConfig where pluginConfig.pluginId = :pluginId";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("pluginId", pluginId).getSingleResult();
		return count > 0;
	}

	public PluginConfig findByPluginId(String pluginId) {
		Store store = WebUtils.getStore();
		if (pluginId == null) {
			return null;
		}
		try {
			String jpql = "select pluginConfig from PluginConfig pluginConfig where pluginConfig.pluginId = :pluginId";
			if(store != null) {
				jpql += " and pluginConfig.store = :store";
			} else {
				jpql += " and pluginConfig.store is null";
			}
			TypedQuery<PluginConfig> query = entityManager.createQuery(jpql, PluginConfig.class).setFlushMode(FlushModeType.COMMIT);
			if(store != null) {
				query.setParameter("store", store);
			}
			query.setParameter("pluginId", pluginId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public PluginConfig findByPluginId(String pluginId, Store store) {
		if (pluginId == null) {
			return null;
		}
		try {
			String jpql = "select pluginConfig from PluginConfig pluginConfig where pluginConfig.pluginId = :pluginId";
			if(store != null) {
				jpql += " and pluginConfig.store = :store";
			} else {
				jpql += " and pluginConfig.store is null";
			}
			
			TypedQuery<PluginConfig> query = entityManager.createQuery(jpql, PluginConfig.class).setFlushMode(FlushModeType.COMMIT);
			if(store != null) {
				query.setParameter("store", store);
			}
			query.setParameter("pluginId", pluginId);
			return query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<PluginConfig> findListForDeleteStore(Store store){
		String jpql = "from PluginConfig pc where pc.store = :store";
		return entityManager.createQuery(jpql, PluginConfig.class).
			setParameter("store", store).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
}