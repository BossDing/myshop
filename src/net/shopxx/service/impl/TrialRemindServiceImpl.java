package net.shopxx.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.dao.TrialRemindDao;
import net.shopxx.entity.TrialRemind;
import net.shopxx.service.TrialRemindService;

/**
 * Service - 试用提醒
 * 
 * @author WeiHuaLin
 * @version 1.0
 */
@Service("trialRemindServiceImpl")
public class TrialRemindServiceImpl extends BaseServiceImpl<TrialRemind,Long> implements TrialRemindService{

	@Resource(name = "trialRemindDaoImpl")
	public void setBaseDao(TrialRemindDao trialRemindDao) {
		super.setBaseDao(trialRemindDao);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = { "trialRemind", "trial" }, allEntries = true)
	public void save(TrialRemind trialRemind) {
		super.save(trialRemind);
	}  

	@Override
	@Transactional
	@CacheEvict(value = { "trialRemind", "trial" }, allEntries = true)
	public TrialRemind update(TrialRemind trialRemind) {
		return super.update(trialRemind);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "trialRemind", "trial" }, allEntries = true)
	public TrialRemind update(TrialRemind trialRemind, String... ignoreProperties) {
		return super.update(trialRemind, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "trialRemind", "trial" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "trialRemind", "trial" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "trialRemind", "trial" }, allEntries = true)
	public void delete(TrialRemind trialRemind) {
		super.delete(trialRemind);
	}

}
