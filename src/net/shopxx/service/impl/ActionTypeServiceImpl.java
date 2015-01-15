package net.shopxx.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shopxx.dao.ActionTypeDao;
import net.shopxx.entity.ActionType;
import net.shopxx.service.ActionTypeService;

/**
 * Service - 活动分类
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("actionTypeServiceImpl")
public class ActionTypeServiceImpl extends BaseServiceImpl<ActionType, Long> implements
		ActionTypeService {
	
	@Resource(name = "actionTypeDaoImpl")
	private ActionTypeDao actionTypeDao;

	@Override
	public ActionType findByActionName(String actionName) {
		return actionTypeDao.findByActionName(actionName);
	}
	
	@Override
	public List<ActionType> findAll() {
		return actionTypeDao.findList(null,null,null,null);
	}
	
	@Override
	public ActionType find(Long id) {
		return actionTypeDao.find(id);
	}

}
