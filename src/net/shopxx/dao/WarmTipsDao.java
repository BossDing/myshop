package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Area;
import net.shopxx.entity.WarmTips;

/**
 * Dao - 温馨提示
 * 
 * @author hfh
 * @version 1.0
 */
public interface WarmTipsDao extends BaseDao<WarmTips, Long>{
	
	/**
	 * 根据地区查找
	 */
	
	List<WarmTips> findByArea(Area area);
}
