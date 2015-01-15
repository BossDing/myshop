package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.PointsWaterDao;
import net.shopxx.dao.TrialApplyDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Trial;
import net.shopxx.entity.TrialApply;
import net.shopxx.service.TrialApplyService;

/**
 * Service - 试用申请
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("trialApplyServiceImpl")
public class TrialApplyServiceImpl extends BaseServiceImpl<TrialApply, Long> implements TrialApplyService {

	@Resource(name = "trialApplyDaoImpl")
	private TrialApplyDao trialApplyDao;
	
	@Resource(name = "trialApplyDaoImpl")
	public void setBaseDao(TrialApplyDao trialApplyDao) {
		super.setBaseDao(trialApplyDao);
	}
	
	public void save(TrialApply apply) {
		trialApplyDao.persist(apply);
	}

	public List<TrialApply> findList(long trialid, Integer count, List<Filter> filters, List<Order> orders) {
		return trialApplyDao.findList(trialid, count, filters, orders);
		}
	
	public TrialApply find(Long id) {
		return trialApplyDao.find(id);
	}

	public boolean findCountByCreateBy(Trial trial, String createBy) {
		return trialApplyDao.findCountCreateBy(trial,createBy);
	}

	public Long findCreateByMaxId(String createBy) {
		return trialApplyDao.findCreateByMaxId(createBy);
	}
	
	@Transactional(readOnly = true)
	public List<TrialApply> findListByMember(Member member) {
		return trialApplyDao.findListByMember(member);
	}

	@Override
	public Page<TrialApply> findPage(Pageable pageable) {
		return trialApplyDao.findPage(pageable);
	}
}
