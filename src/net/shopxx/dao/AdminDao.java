/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Admin;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Store;

/**
 * Dao - 管理员
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface AdminDao extends BaseDao<Admin, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByUsername(String username);
	
	/**
	 * 根据店铺查找管理员
	 * @param store
	 * 		店铺
	 * @return 
	 * 		管理员
	 * @author wmd
	 * @date 2014/11/25
	 */
	public Admin findAdminForDeleteStore(Store store);

}