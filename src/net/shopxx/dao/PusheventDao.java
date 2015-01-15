/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import net.shopxx.entity.Pushevent;

/**
 * Dao - 微信用户操作记录
 * 
 * @author shenlong
 * @version 3.0
 */
public interface PusheventDao extends BaseDao<Pushevent, Long> {

	/**
	 * 根据微信号查找记录
	 * 
	 * @param fromusername
	 *            微信号(忽略大小写)
	 * @return Pushevent 若不存在则返回null
	 */
	Pushevent findByFromusername(String fromusername);
	
	/**
	 * 根据微信号删除记录
	 * 
	 * @param fromusername
	 *            微信号(忽略大小写)
	 */
	void deleteByFromusername(String fromusername);
}