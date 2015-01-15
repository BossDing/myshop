package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Member;
import net.shopxx.entity.TrialApply;
import net.shopxx.entity.TrialReport;

/**
 * Dao - 试用报告
 * 
 * @author lzy
 * @version 1.0
 */
public interface TrialReportDao extends BaseDao<TrialReport, Long>{
	
	/**
	 * 查找个人试用报告
	 * 
	 * @param member
	 *            会员
	 */
	List<TrialReport> findListByMember(Member member);

}
