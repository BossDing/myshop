package net.shopxx.dao.impl;


import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.TrialApplyDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Trial;
import net.shopxx.entity.TrialApply;

/**
 * Dao - 试用申请
 * 
 * @author lzy
 * @version 1.0
 */
@Repository("trialApplyDaoImpl")
public class TrialApplyDaoImpl extends BaseDaoImpl<TrialApply, Long> implements TrialApplyDao {
	
	
	public List<TrialApply> findList(long trialId, Integer count,
			List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TrialApply> criteriaQuery = criteriaBuilder.createQuery(TrialApply.class);
		Root<TrialApply> root = criteriaQuery.from(TrialApply.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.equal(root.get("trial_id"), trialId);
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public boolean findCountCreateBy(Trial trial, String createBy) {
		if( trial == null){
			return false;
		}
		String jpql = "select count(*) from TrialApply trialApply where lower(trialApply.trial.id) = lower(:trialId) and lower(trialApply.createdBy) = lower(:createBy)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("trialId", trial.getId()).setParameter("createBy", createBy).getSingleResult();
		return count>0;
	}

	public Long findCreateByMaxId(String createBy) {
		if(createBy == null || createBy ==""){
			return 0L;
		}
		
		String jpql = "select max(trialApply.id) from TrialApply trialApply where lower(trialApply.createdBy) = lower(:createBy)";
		Long trialApplyId = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("createBy", createBy).getSingleResult();
		return trialApplyId;
	}
	
	public List<TrialApply> findListByMember(Member member) {
		if (member == null) {
			return null;
		}
		String jpql = "select trialApply from TrialApply trialApply where lower(trialApply.member) = lower(:member)";
		return entityManager.createQuery(jpql, TrialApply.class).setFlushMode(FlushModeType.COMMIT).setParameter("member", member).getResultList();
	}
}
