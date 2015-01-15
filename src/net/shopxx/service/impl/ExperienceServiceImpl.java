package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.ExperienceDao;
import net.shopxx.entity.Experience;
import net.shopxx.service.ExperienceService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 体验中心
 * 
 * @author DongXinXing
 * @version 1.0
 */
@Service("experienceServiceImpl")
public class ExperienceServiceImpl extends BaseServiceImpl<Experience, Long> implements ExperienceService {

	@Resource(name = "experienceDaoImpl")
	private ExperienceDao experienceDao;  

	@Resource(name = "experienceDaoImpl")
	public void setBaseDao(ExperienceDao experienceDao) {
		super.setBaseDao(experienceDao);
	}

	@Transactional(readOnly = true)
	public List<Experience> findList(Integer count, List<Filter> filters, List<Order> orders) {
		return experienceDao.findList(count, filters, orders);
	}   	
	
	@Override
	@Transactional
	public void save(Experience experience) {
		super.save(experience);
	}  

	@Override    
	@Transactional
	@CacheEvict(value = { "experience", "product" }, allEntries = true)
	public Experience update(Experience experience) {
		return super.update(experience);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "experience", "product" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override   
	@Transactional
	@CacheEvict(value = { "experience", "product" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "experience", "product" }, allEntries = true)
	public void delete(Experience experience) {
		super.delete(experience);
	}  
	@Transactional(readOnly = true)
	public Page<Experience> findPage(String areaName, Pageable pageable){
		return experienceDao.findPage(areaName, pageable);
	}

	@Override
	public List<Experience> findAll(String searchWord, String fullName,String province,String city) {
		return experienceDao.findAll(searchWord, fullName,province,city);
	}
}