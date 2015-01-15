package net.shopxx.dao;

import java.util.Date;
import java.util.List;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.entity.Trial;

/**
 * Dao - 促销
 * 
 * @author DongXinXing
 * @version 1.0
 */
public interface TrialDao extends BaseDao<Trial, Long> {

	/**
	 * 查找试用
	 * 
	 * @param hasBegun  
	 *            是否已开始
	 * @param hasEnded
	 *            是否已结束  
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 促销
	 */
	List<Trial> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders);

	List<Trial> findOrderByEndDateAndDate(Date date);
}