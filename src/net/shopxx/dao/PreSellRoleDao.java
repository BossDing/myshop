package net.shopxx.dao;

import java.util.List;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.PreSellRole;
import net.shopxx.entity.Trial;

/**
 * Dao - 预售
 * 
 * @author hfh
 * @version 1.0
 */
public interface PreSellRoleDao extends BaseDao<PreSellRole, Long> {

//	Page<PreSellRole> findPage(Pageable pageable1);

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
	List<PreSellRole> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders);

}
