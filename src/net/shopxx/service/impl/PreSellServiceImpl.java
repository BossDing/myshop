package net.shopxx.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.dao.PreSellDao;
import net.shopxx.entity.PreSellRole;
import net.shopxx.service.PreSellService;


/**
 * Service - 预售
 * 
 * @author WeiHuaLin
 * @version 1.0
 */
@Service("preSellServiceImpl")
public class PreSellServiceImpl extends BaseServiceImpl<PreSellRole, Long> implements PreSellService{

	@Resource(name = "preSellDaoImpl")
	public void setBaseDao(PreSellDao preSellDao) {
		super.setBaseDao(preSellDao);
	}
	
	
	@Override
	@Transactional
	@CacheEvict(value = { "preSellRole", "product" }, allEntries = true)
	public void save(PreSellRole preSellRole) {
		super.save(preSellRole);
	}  

	@Override
	@Transactional
	@CacheEvict(value = { "preSellRole", "product" }, allEntries = true)
	public PreSellRole update(PreSellRole preSellRole) {
		return super.update(preSellRole);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "preSellRole", "product" }, allEntries = true)
	public PreSellRole update(PreSellRole preSellRole, String... ignoreProperties) {
		return super.update(preSellRole, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "preSellRole", "product" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "preSellRole", "product" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "preSellRole", "product" }, allEntries = true)
	public void delete(PreSellRole preSellRole) {
		super.delete(preSellRole);
	}
}
