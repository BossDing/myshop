package net.shopxx.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.dao.WarmTipsDao;
import net.shopxx.entity.Area;
import net.shopxx.entity.WarmTips;
import net.shopxx.service.WarmTipsService;

/**
 * Service - 温馨提示
 * 
 * @author hfh
 * @version 1.0
 */
@Service("warmTipsServiceImpl")
public class WarmTipsServiceImpl extends BaseServiceImpl<WarmTips, Long> implements
		WarmTipsService {
	@Resource(name = "warmTipsDaoImpl")
	private WarmTipsDao warmTipsDao;
	
	@Resource(name = "warmTipsDaoImpl")
	public void setBaseDao(WarmTipsDao warmTipsDao) {
		super.setBaseDao(warmTipsDao);
	}
	
//	@Override
//	@Transactional
//	public WarmTips update(WarmTips warmTips) {
//		return super.update(warmTips);
//	}
	
	@Override
	public List<WarmTips> findByArea(Area area) {
		return warmTipsDao.findByArea(area);
	}
	
}
