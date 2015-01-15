 package net.shopxx.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.dao.TrialDao;
import net.shopxx.entity.Trial;

import net.shopxx.service.TrialService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 试用
 * 
 * @author DongXinXing
 * @version 1.0
 */
@Service("trialServiceImpl")
public class TrialServiceImpl extends BaseServiceImpl<Trial, Long> implements TrialService {

	@Resource(name = "trialDaoImpl")
	private TrialDao trialDao;

	@Resource(name = "trialDaoImpl")
	public void setBaseDao(TrialDao trialDao) {
		super.setBaseDao(trialDao);
	}

	@Transactional(readOnly = true)
	public List<Trial> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders) {
		return trialDao.findList(hasBegun, hasEnded, count, filters, orders);
	}   

	@Transactional(readOnly = true)
	@Cacheable("trial")
	public List<Trial> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders, String cacheRegion) {
		return trialDao.findList(hasBegun, hasEnded, count, filters, orders);
	}
	
	

	@Override
	@Transactional
	@CacheEvict(value = { "trial", "product" }, allEntries = true)
	public void save(Trial trial) {
		super.save(trial);
	}  

	@Override
	@Transactional
	@CacheEvict(value = { "trial", "product" }, allEntries = true)
	public Trial update(Trial trial) {
		return super.update(trial);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "trial", "product" }, allEntries = true)
	public Trial update(Trial trial, String... ignoreProperties) {
		return super.update(trial, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "trial", "product"}, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "trial", "product" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}  

	@Override
	@Transactional
	@CacheEvict(value = { "trial", "product" }, allEntries = true)
	public void delete(Trial trial) {
		super.delete(trial);
	}  

	public List<Trial> findOrderByEndDateAndDate(Date date) {
		return trialDao.findOrderByEndDateAndDate(date);
	}

}