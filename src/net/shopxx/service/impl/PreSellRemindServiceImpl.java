package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.PreSellRemindDao;
import net.shopxx.entity.PreSellRemind;
import net.shopxx.service.PreSellRemindService;

/**
 * 
 * @author hfh
 *
 */
@Service("preSellRemindServiceImpl")
public class PreSellRemindServiceImpl extends BaseServiceImpl<PreSellRemind, Long> implements
		PreSellRemindService {

	@Resource(name = "preSellRemindDaoImpl")
	public void setBaseDao(PreSellRemindDao preSellRemindDao) {
		super.setBaseDao(preSellRemindDao);
	}
	
	@Override
	@Transactional
	public void save(PreSellRemind preSellRemind) {
		System.out.println("进入saveRemindService");
		super.save(preSellRemind);
	}  
//	preSellRemindDaoImpl

}
