package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Area;
import net.shopxx.entity.WarmTips;


/**
 * Service - 温馨提示
 * 
 * @author hfh
 * @version 1.0
 */
public interface WarmTipsService extends BaseService<WarmTips, Long>{
	
	List<WarmTips> findByArea(Area area);
}
