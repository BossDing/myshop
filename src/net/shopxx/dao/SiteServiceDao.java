package net.shopxx.dao;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.SiteService;

public interface SiteServiceDao extends BaseDao<SiteService, Long>{
	
	Page<SiteService> findPage(String pcity, Pageable pageable);

	List<SiteService> findList(String province);
}
