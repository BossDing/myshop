package net.shopxx.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import net.shopxx.dao.SignInPointRuleDao;
import net.shopxx.entity.SignInPointRule;
import net.shopxx.service.SignInPointRuleService;

@Service("signInPointRuleServiceImpl")
public class SignInPointRuleServiceImpl extends BaseServiceImpl<SignInPointRule, Long> implements SignInPointRuleService{
	@Resource(name="signInPointRuleDaoImpl")
	private SignInPointRuleDao signInPointRuleDao;
	@Resource(name="signInPointRuleDaoImpl")
	public void setBaseDao(SignInPointRuleDao signInPointRuleDao) {
		super.setBaseDao(signInPointRuleDao);
	}

	@Override
	public boolean serialSignInTimesUnique(Integer previousSerialSignInTimes,
			Integer serialSignInTimes) {
		if (previousSerialSignInTimes!=null&&previousSerialSignInTimes.equals(serialSignInTimes)) {
			return true;
		} else {
			if (signInPointRuleDao.serialSignInTimesExists(serialSignInTimes)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public Integer getPointOfSerialSignInTimes(Integer serialSignInTimes) {
		return signInPointRuleDao.getPointOfSerialSignInTimes(serialSignInTimes);
	}

	@Override
	public String rangeOfPointForSerialSignInTimes(Integer previousSerialSignInTimes,Integer serialSignInTimes) {
		return signInPointRuleDao.rangeOfPointForSerialSignInTimes(previousSerialSignInTimes,serialSignInTimes);
	}
}
