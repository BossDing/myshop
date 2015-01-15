package net.shopxx.dao.impl;


import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import net.shopxx.dao.WarmTipsDao;
import net.shopxx.entity.Area;
import net.shopxx.entity.WarmTips;

/**
 * Dao - 温馨提示
 * 
 * @author hfh
 * @version 1.0
 */
@Repository("warmTipsDaoImpl")
public class WarmTipsDaoImpl extends BaseDaoImpl<WarmTips, Long> implements WarmTipsDao {
	
	@Override
	public List<WarmTips> findByArea(Area area) {
		if(area == null){
			return null;
		}
		String jpql = "select warmTips from WarmTips warmTips where lower(warmTips.area.id) = lower(:area)";
		List<WarmTips> warmTips = entityManager.createQuery(jpql, WarmTips.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("area", area.getId()).getResultList();
		return warmTips;
	}
}
