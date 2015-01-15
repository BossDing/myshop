package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.SiteServiceDao;
import net.shopxx.entity.SiteService;
import net.shopxx.service.SiteServiceService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("siteServiceServiceImpl")
public class SiteServiceServiceImpl extends BaseServiceImpl<SiteService, Long> implements
	SiteServiceService{

	@Resource(name = "siteServiceDaoImpl")
	private SiteServiceDao siteServiceDao;
	
	@Transactional(readOnly = true)
	public Page<SiteService> findPage(String pcity, Pageable pageable) {

		return siteServiceDao.findPage(pcity, pageable);
	}

	public List<SiteService> findList(String province){
		
		return siteServiceDao.findList(province);
	}
	
}
