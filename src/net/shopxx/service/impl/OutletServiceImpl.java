package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.shopxx.dao.OutletDao;
import net.shopxx.entity.Outlet;
import net.shopxx.service.OutletService;

/**
 * 服务网点
 * @author Mr.Zhang
 *
 */
@Service("outletServiceImpl")
public class OutletServiceImpl extends BaseServiceImpl<Outlet, Long> implements OutletService {
	@Resource(name = "outletDaoImpl")
	private OutletDao outletDao;
	
	@Resource(name = "outletDaoImpl")
	public void setBaseDao(OutletDao outletDao) {
		super.setBaseDao(outletDao);
	}
	
	@Transactional(readOnly = true)
	public List<Outlet> findOutlets(String areaName,String categoryName,String entCode) {
		return outletDao.findOutlets(areaName, categoryName, entCode);
	}

}
