package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import net.shopxx.dao.PreSellApplyDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.PreSellApply;
import net.shopxx.entity.PreSellRole;
import net.shopxx.entity.TrialApply;

import org.springframework.stereotype.Repository;

@Repository("preSellApplyDaoImpl")
public class PreSellApplyDaoImpl extends BaseDaoImpl<PreSellApply, Long> implements PreSellApplyDao{
	
	
	public boolean findCountByCreateBy(PreSellRole preSellRole, String createBy) {
		if(preSellRole == null){
			return false;
		}
		String jpql = "select count(*) from PreSellApply preSellApply where lower(preSellApply.preSellRole.id) = lower(:preSellRoleId) and lower(preSellApply.createdBy) = lower(:createBy)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
						.setParameter("preSellRoleId", preSellRole.getId()).setParameter("createBy", createBy).getSingleResult();
		return count > 0;
	}

	@Override
	public List<PreSellApply> findListByMember(Member member) {
		// TODO Auto-generated method stub
		if(member != null){
			String jpql = "select preSellApply from PreSellApply preSellApply where lower(preSellApply.member) = lower(:member)";
			return entityManager.createQuery(jpql, PreSellApply.class).setFlushMode(FlushModeType.COMMIT).setParameter("member", member).getResultList();
		}else {
			return null;
		}
	}
	 
}
