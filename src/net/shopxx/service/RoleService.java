/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import net.shopxx.entity.Role;

/**
 * Service - 角色
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface RoleService extends BaseService<Role, Long> {

	/**
	 * 根据角色名查找角色
	 * @author cgd 2014-10-22
	 * @param name 角色名称
	 * @return Role 对应角色
	 */
	Role findByName(String name);
}