package net.shopxx.dao;

import java.util.List;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Experience;

/**
 * Dao - 体验中心
 * 
 * @author DongXinXing
 * @version 1.0
 */
public interface ExperienceDao extends BaseDao<Experience, Long> {

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
	List<Experience> findList(Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 按地区查找体验中心
	 * 
	 * @param areaName
	 *            地区      
	 * @param pageable
	 *            分页信息
	 * @return 收藏商品分页
	 */
	Page<Experience> findPage(String areaName, Pageable pageable);
	
	/**
	 * 通过关键字和地区查找体验店
	 * @param searchWord
	 * @param fullName
	 * @return
	 */
	List<Experience> findAll(String searchWord,String fullName,String province,String city);
}     