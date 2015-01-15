package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import net.shopxx.dao.TrialReportDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.TrialApply;
import net.shopxx.entity.TrialReport;

/**
 * daoImpl - 试用报告
 * 
 * @author lzy
 * @version 1.0
 */
@Repository("trialReportDaoImpl")
public class TrialReportDaoImpl extends BaseDaoImpl<TrialReport, Long> implements TrialReportDao {
	
	public List<TrialReport> findListByMember(Member member) {
		if (member == null) {
			return null;
		}
		String jpql = "select trialReport from TrialReport trialReport where lower(trialReport.member) = lower(:member)";
		return entityManager.createQuery(jpql, TrialReport.class).setFlushMode(FlushModeType.COMMIT).setParameter("member", member).getResultList();
	}
}
