package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.PreSellRoleDao;
import net.shopxx.dao.TrialDao;
import net.shopxx.entity.PreSellRole;
import net.shopxx.service.PreSellRoleService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("preSellRoleServiceImpl")
public class PreSellRoleServiceImpl extends BaseServiceImpl<PreSellRole, Long> implements
		PreSellRoleService {
	@Resource(name = "preSellRoleDaoImpl")
	private PreSellRoleDao preSellRoleDao;
	
	@Resource(name = "preSellRoleDaoImpl")
	public void setBaseDao(PreSellRoleDao preSellRoleDao) {
		super.setBaseDao(preSellRoleDao);
	}

	
//	@Transactional(readOnly = true)
//	public PreSellRole find(Long id) {
//		return preSellRoleDao.find(id);
//	}	
	
	@Override
	public PreSellRole find(Long id) {
		return preSellRoleDao.find(id);
	}

	@Transactional(readOnly = true)
	public List<PreSellRole> findList(Boolean hasBegun, Boolean hasEnded,
			Integer count, List<Filter> filters, List<Order> orders) {
		// TODO Auto-generated method stub
		return preSellRoleDao.findList(hasBegun, hasEnded, count, filters, orders);
	}


//	@Transactional(readOnly = true)
//	public Page<PreSellRole> findPage(Pageable pageable1) {
//		return preSellRoleDao.findPage(pageable1);
//	}
	
}
