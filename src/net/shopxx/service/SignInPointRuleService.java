package net.shopxx.service;

import net.shopxx.entity.SignInPointRule;

public interface SignInPointRuleService extends BaseService<SignInPointRule, Long>{
	/**
	 * 连续签到次数是否唯一。
	 * @param previousSerialSignInTimes
	 * @param serialSignInTimes
	 * @return
	 */
	boolean serialSignInTimesUnique(Integer previousSerialSignInTimes,Integer serialSignInTimes);
	/**
	 * 查找连续签到次数应该奖励多少积分。
	 * @param serialSignInTimes
	 * @return
	 */
	Integer getPointOfSerialSignInTimes(Integer serialSignInTimes);
	/**
	 * 找出该连续签到次数奖励的积分范围开区间（大于比它小的连续签到次数的积分，小于比它大的连续签到次数的积分）。
	 * @param serialSignInTimes
	 * @return
	 */
	String rangeOfPointForSerialSignInTimes(Integer previousSerialSignInTimes,Integer serialSignInTimes);
}
