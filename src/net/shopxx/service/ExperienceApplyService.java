package net.shopxx.service;

import java.util.List;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Experience;
import net.shopxx.entity.ExperienceApply;


public interface ExperienceApplyService extends BaseService<ExperienceApply, Long> {

	/**
	 * 查找体验中心
	 * 
	 * @param count
	 *            数量
	 * @param filters   
	 *            筛选   
	 * @param orders
	 *            排序   
	 */
	List<ExperienceApply> findList(Integer count, List<Filter> filters, List<Order> orders);
	
	/**
	 * 按地区查找体验中心
	 * 
	 * @param areaName
	 *            地区
	 * @param pageable
	 *            分页信息
	 * @return 体验中心
	 */
	//Page<ExperienceApply> findPage(String areaName , Pageable pageable);
}
