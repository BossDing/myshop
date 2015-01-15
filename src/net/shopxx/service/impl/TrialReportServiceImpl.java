package net.shopxx.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.TrialReportDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.TrialReport;
import net.shopxx.service.TrialReportService;

/**
 * Service - 试用报告
 * 
 * @author WeiHuaLin
 * @version 1.0
 */
@Service("trialReportServiceImpl")
public class TrialReportServiceImpl extends BaseServiceImpl<TrialReport, Long> implements TrialReportService{
	
	@Resource(name = "trialReportDaoImpl")
	private TrialReportDao trialReportDao;
	
	@Resource(name = "trialReportDaoImpl")
	public void setBaseDao(TrialReportDao trialReportDao) {
		super.setBaseDao(trialReportDao);
	}
	
	public Page<TrialReport> findPage(Pageable pageable) {
		return trialReportDao.findPage(pageable);
	}

	public TrialReport find(Long id) {
		return trialReportDao.find(id);
	}
		
	@Transactional(readOnly = true)
	public List<TrialReport> findListByMember(Member member) {
		return trialReportDao.findListByMember(member);
	}
	
	@Transactional
	public void save(TrialReport trialReport){
		System.out.println("你好地方");
		super.save(trialReport);
	}
}
