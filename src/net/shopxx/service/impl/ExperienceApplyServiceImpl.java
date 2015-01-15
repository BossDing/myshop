package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.ExperienceApplyDao;
import net.shopxx.dao.ExperienceDao;
import net.shopxx.entity.Experience;
import net.shopxx.entity.ExperienceApply;
import net.shopxx.service.ExperienceApplyService;
import net.shopxx.service.ExperienceService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 体验申请中心
 * 
 * @author DongXinXing
 * @version 1.0
 */
@Service("experienceApplyServiceImpl")
public class ExperienceApplyServiceImpl extends BaseServiceImpl<ExperienceApply, Long> implements ExperienceApplyService {

	@Resource(name = "experienceApplyDaoImpl")
	private ExperienceApplyDao experienceApplyDao;  

	@Resource(name = "experienceApplyDaoImpl")
	public void setBaseDao(ExperienceApplyDao experienceApplyDao) {
		super.setBaseDao(experienceApplyDao);
	}

	@Transactional(readOnly = true)
	public List<ExperienceApply> findList(Integer count, List<Filter> filters, List<Order> orders) {
		return experienceApplyDao.findList(count, filters, orders);
	}   	
	
	@Override
	@Transactional
	public void save(ExperienceApply experienceApply) {
		super.save(experienceApply);
	}  

	@Override    
	@Transactional
	@CacheEvict(value = { "experienceApply", "product" }, allEntries = true)
	public ExperienceApply update(ExperienceApply experienceApply) {
		return super.update(experienceApply);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "experienceApply", "product" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override   
	@Transactional
	@CacheEvict(value = { "experienceApply", "product" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "experienceApply", "product" }, allEntries = true)
	public void delete(ExperienceApply experienceApply) {
		super.delete(experienceApply);
	}  
	//@Transactional(readOnly = true)
	//public Page<ExperienceApply> findPage(ExperienceApply experienceApply, Pageable pageable){
	//	return experienceApplyDao.findPage(experienceApply, pageable);
	//}
}