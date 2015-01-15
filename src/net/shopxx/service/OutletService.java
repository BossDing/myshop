/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Outlet;

/**
 * 服务网点
 * @author Mr.Zhang
 *
 */
public interface OutletService extends BaseService<Outlet, Long> {
	List<Outlet> findOutlets(String areaName,String categoryName,String entCode);
}