package net.shopxx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.AdPositionDao;
import net.shopxx.dao.AfterBookingDao;
import net.shopxx.dao.BaseDao;
import net.shopxx.dao.LeaveWordsDao;
import net.shopxx.entity.AfterBooking;
import net.shopxx.entity.LeaveWords;
import net.shopxx.service.AfterBookingService;

/**
 * Service - 活动分类
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("afterBookingServiceImpl")
public class AfterBookingServiceImpl extends BaseServiceImpl<AfterBooking, Long> implements AfterBookingService {
	
	@Resource(name="afterBookingDaoImpl")
	private AfterBookingDao afterBookingDao;
	
	@Resource(name = "afterBookingDaoImpl")
	public void setBaseDao(AfterBookingDao afterBookingDao) {
		super.setBaseDao(afterBookingDao);
	}
	
	public Page<AfterBooking> findPage(Long label ,Pageable pageable){
		
		return afterBookingDao.findPage(label ,pageable);
	}
}
