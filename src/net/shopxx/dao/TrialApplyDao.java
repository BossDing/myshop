package net.shopxx.dao;

import java.util.List;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.Trial;
import net.shopxx.entity.TrialApply;

/**
 * Dao - 试用申请
 * 
 * @author lzy
 * @version 1.0
 */
public interface TrialApplyDao extends BaseDao<TrialApply, Long>{
	List<TrialApply> findList(long trialId, Integer count, List<Filter> filters, List<Order> orders);
	
	boolean findCountCreateBy(Trial trial,String createBy);
	
	Long findCreateByMaxId(String createBy);
	
	/**
	 * 查找个人使用申请
	 * 
	 * @param member
	 *            会员
	 */
	List<TrialApply> findListByMember(Member member);
}
