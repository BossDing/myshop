package net.shopxx.dao;

import net.shopxx.entity.ActionType;

/**
 * Dao - 活动分类
 * 
 * @author lzy
 * @version 1.0
 */
public interface ActionTypeDao extends BaseDao<ActionType, Long> {
	
	ActionType findByActionName(String actionName);
}
