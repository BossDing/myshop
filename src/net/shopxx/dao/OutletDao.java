/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Outlet;

/**
 * 服务网点
 * @author Mr.Zhang
 *
 */
public interface OutletDao extends BaseDao<Outlet,Long> {
	/** 查询服务网点*/
	List<Outlet> findOutlets(String areaName,String categoryName,String entCode);
	
}