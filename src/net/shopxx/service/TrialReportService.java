package net.shopxx.service;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.TrialReport;

public interface TrialReportService extends BaseService<TrialReport, Long> {

	/**
	 * 查询试用报告
	 * 
	 * @param report
	 *        试用报告详情
	 */
	TrialReport find(Long id);
	
	
	/**
	 * 查找个人试用报告
	 * 
	 * @param member
	 *            会员
	 */
	List<TrialReport> findListByMember(Member member);
	
	public Page<TrialReport> findPage(Pageable pageable);
	
	void save(TrialReport trialReport);
	
}
