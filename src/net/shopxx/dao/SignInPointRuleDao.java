package net.shopxx.dao;

import net.shopxx.entity.SignInPointRule;

public interface SignInPointRuleDao extends BaseDao<SignInPointRule, Long>{
	/**
	 * 判断连续签到次数是否已存在。
	 * @param serialSignInTimes
	 * @return
	 */
	boolean serialSignInTimesExists(Integer serialSignInTimes);
	/**
	 * 查找连续签到次数应该奖励多少积分。
	 * @param serialSignInTimes
	 * @return
	 */
	Integer getPointOfSerialSignInTimes(Integer serialSignInTimes);
	/**
	 * 找出该连续签到次数奖励的积分范围开区间（大于比它小的连续签到次数的积分，小于比它大的连续签到次数的积分）
	 * @param serialSignInTimes
	 * @return
	 */
	String rangeOfPointForSerialSignInTimes(Integer previousSerialSignInTimes,Integer serialSignInTimes);
}
