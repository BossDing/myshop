/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import net.shopxx.entity.Role;

/**
 * Dao - 角色
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface RoleDao extends BaseDao<Role, Long> {

	/**
	 * 根据角色名称查找角色
	 * @param name 角色名称
	 * @return 对应角色
	 */
	Role findByName(String name);
}