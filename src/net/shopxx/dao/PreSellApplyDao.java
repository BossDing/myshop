package net.shopxx.dao;


import java.util.List;

import net.shopxx.entity.Member;
import net.shopxx.entity.PreSellApply;
import net.shopxx.entity.PreSellRole;
import net.shopxx.entity.TrialApply;

/**
 * Dao - 预约申请
 * 
 * @author hfh
 * 
 * @version 1.0
 */
public interface PreSellApplyDao extends BaseDao<PreSellApply, Long> {
	
	boolean findCountByCreateBy(PreSellRole preSellRole,String createBy);
	
	List<PreSellApply> findListByMember(Member member);
}
