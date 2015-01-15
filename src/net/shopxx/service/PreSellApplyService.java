package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Member;
import net.shopxx.entity.PreSellApply;
import net.shopxx.entity.PreSellRole;

public interface PreSellApplyService extends BaseService<PreSellApply,Long> {
	/**
	 * 保存预约申请
	 * 
	 * @param preSellApply
	 *        申请详情
	 */
	void save(PreSellApply preSellApply);
	
	/**
	 * 判断该会员是否已经申请了该预售
	 * @param preSellRole
	 * @param createBy
	 * @returnbooleanhfh
	 */
	boolean findCountByCreateBy(PreSellRole preSellRole,String createBy);
	
	/**
	 * 个人中心-查找我的预约
	 * 
	 * @param member
	 * @returnList<PreSellApply>hfh
	 */
	List<PreSellApply> findListByMember(Member member);
	
}
