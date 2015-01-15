package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.PusheventDao;
import net.shopxx.entity.Pushevent;
import net.shopxx.service.PusheventService;

import org.springframework.stereotype.Service;

/**
 * Dao - 微信事件
 * 
 * @author shenlong
 * @version 3.0
 */
@Service("pusheventServiceImpl")
public class PusheventServiceImpl extends BaseServiceImpl<Pushevent, Long> implements PusheventService {

	@Resource(name = "pusheventDaoImpl")
	private PusheventDao pusheventDao;
	
	@Resource(name = "pusheventDaoImpl")
	public void setBaseDao(PusheventDao pusheventDao) {
	    super.setBaseDao(pusheventDao);
	} 
	
	
	public Pushevent findByFromusername(String fromusername) {
		return pusheventDao.findByFromusername(fromusername);
	}
	
}
