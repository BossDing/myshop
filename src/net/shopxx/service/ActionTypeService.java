package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.ActionType;

/**
 * Service - 活动分类
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface ActionTypeService extends BaseService<ActionType, Long> {
	
	ActionType findByActionName(String actionName);
	
	@Override
	List<ActionType> findAll();
	
	@Override
	ActionType find(Long id);
}
