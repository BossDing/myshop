package net.shopxx.service;

import java.util.List;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.Trial;
import net.shopxx.entity.TrialApply;

public interface TrialApplyService extends BaseService<TrialApply, Long> {

	/**
	 * 保存试用申请
	 * 
	 * @param apply
	 *        申请详情
	 */
	void save(TrialApply apply);
	
	List<TrialApply> findList(long trialid, Integer count, List<Filter> filters, List<Order> orders);
	
	/**
	 * 判断该会员是否已经申请了该试用
	 * @param trialId 这个试用
	 * @param trialId 当前用户
	 */
	
	boolean findCountByCreateBy(Trial trial,String createBy);
	
	/**
	 * 根据创建人，查询出刚刚申请的Id
	 * @param trialId 当前用户
	 */
	
	Long findCreateByMaxId(String createBy);
	
	
	/**
	 * 查找个人试用申请
	 * 
	 * @param member
	 *            会员
	 */
	List<TrialApply> findListByMember(Member member);
	
	public Page<TrialApply> findPage(Pageable pageable);
	
}
