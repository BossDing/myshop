/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import net.shopxx.entity.Cart;
import net.shopxx.entity.Member;

/**
 * Dao - 购物车
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface CartDao extends BaseDao<Cart, Long> {

	/**
	 * 清除过期购物车
	 */
	void evictExpired();
	
	/**
	 * @Description 根据用户查询购物车
	 * @author Guoxianlong
	 * @create_date Sep 11, 20145:32:17 PM
	 */
	Cart getCart(Member member);
}