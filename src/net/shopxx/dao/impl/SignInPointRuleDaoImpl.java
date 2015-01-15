package net.shopxx.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.shopxx.dao.SignInPointRuleDao;
import net.shopxx.entity.SignInPointRule;

@Repository("signInPointRuleDaoImpl")
public class SignInPointRuleDaoImpl extends BaseDaoImpl<SignInPointRule, Long>
		implements SignInPointRuleDao {

	@Override
	public boolean serialSignInTimesExists(Integer serialSignInTimes) {
		String jpql = "select count(*) from SignInPointRule signInPointRule where signInPointRule.serialSignInTimes = :serialSignInTimes";
		Long count = entityManager.createQuery(jpql, Long.class)
				.setFlushMode(FlushModeType.COMMIT)
				.setParameter("serialSignInTimes", serialSignInTimes)
				.getSingleResult();
		return count > 0;
	}

	@Override
	public Integer getPointOfSerialSignInTimes(Integer serialSignInTimes) {
		if (serialSignInTimes == null) {
			serialSignInTimes = 0;
		}
		String jpql = "select max(point) from SignInPointRule signInPointRule where signInPointRule.serialSignInTimes <= :serialSignInTimes";
		Integer point = entityManager.createQuery(jpql, Integer.class).setFlushMode(FlushModeType.COMMIT).setParameter("serialSignInTimes", serialSignInTimes).getSingleResult();
		if (point==null) {
			point=0;
		}
		return point;
	}

	@Override
	public String rangeOfPointForSerialSignInTimes(Integer previousSerialSignInTimes,Integer serialSignInTimes) {
		if (serialSignInTimes == null) {
			serialSignInTimes = 0;
		}
		Integer[] dataArr=new Integer[2];
		//范围最小值。
		String jpql = "select max(point) from SignInPointRule signInPointRule where signInPointRule.serialSignInTimes < :serialSignInTimes";
		if(previousSerialSignInTimes!=null){
			jpql+=" and signInPointRule.serialSignInTimes<>"+previousSerialSignInTimes;
		}
		dataArr[0] = entityManager.createQuery(jpql, Integer.class).setFlushMode(FlushModeType.COMMIT).setParameter("serialSignInTimes", serialSignInTimes).getSingleResult();
		//范围最大值。
		String jpql2 = "select min(point) from SignInPointRule signInPointRule where signInPointRule.serialSignInTimes > :serialSignInTimes";
		if(previousSerialSignInTimes!=null){
			jpql2+=" and signInPointRule.serialSignInTimes<>"+previousSerialSignInTimes;
		}
		dataArr[1]= entityManager.createQuery(jpql2, Integer.class).setFlushMode(FlushModeType.COMMIT).setParameter("serialSignInTimes", serialSignInTimes).getSingleResult();
		return StringUtils.join(dataArr,',');
	}
}
