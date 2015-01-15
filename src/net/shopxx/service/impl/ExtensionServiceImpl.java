package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.ExperienceDao;
import net.shopxx.dao.ExtensionDao;
import net.shopxx.dao.GroupDao;
import net.shopxx.entity.Extension;
import net.shopxx.entity.GroupPurchase;
import net.shopxx.entity.Member;
import net.shopxx.service.ExtensionService;

@Service("extensionServiceImpl")
public class ExtensionServiceImpl  extends BaseServiceImpl<Extension, Long>  implements ExtensionService {

	@Resource(name = "extensionDaoImpl")
	private ExtensionDao extensionDao;  
	
	@Resource(name = "extensionDaoImpl")
	public void setBaseDao(ExtensionDao extensionDao) {
		super.setBaseDao(extensionDao);
	}

	public Extension findByMember(String username) {
		// TODO Auto-generated method stub
		return extensionDao.findByMember(username);
	}
	
	
}
