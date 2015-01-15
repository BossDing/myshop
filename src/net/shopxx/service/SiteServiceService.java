package net.shopxx.service;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.SiteService;

public interface SiteServiceService extends BaseService<SiteService, Long>{

	Page<SiteService> findPage(String pcity, Pageable pageable); 
	List<SiteService> findList(String province);
}
