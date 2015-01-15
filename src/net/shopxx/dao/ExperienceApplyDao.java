package net.shopxx.dao;

import java.util.List;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.entity.Experience;
import net.shopxx.entity.ExperienceApply;

/**
 * Dao - 体验中心
 * 
 * @author DongXinXing
 * @version 1.0
 */
public interface ExperienceApplyDao extends BaseDao<ExperienceApply, Long> {

	/**
	 * 查找试用
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 */
	List<ExperienceApply> findList(Integer count, List<Filter> filters, List<Order> orders);

}