/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.Date;

import javax.persistence.FlushModeType;

import net.shopxx.dao.CartDao;
import net.shopxx.entity.Cart;
import net.shopxx.entity.Member;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 购物车
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("cartDaoImpl")
public class CartDaoImpl extends BaseDaoImpl<Cart, Long> implements CartDao {

	public void evictExpired() {
		String jpql = "delete from Cart cart where cart.modifyDate <= :expire";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("expire", DateUtils.addSeconds(new Date(), -Cart.TIMEOUT)).executeUpdate();
	}

	@Override
	public Cart getCart(Member member) {
		Cart cart = null;
		if(member == null) {
			return null;
		}
		String jpql = "select cart from Cart cart where cart.member = :member";
		try {
			cart = entityManager.createQuery(jpql, Cart.class)
					.setFlushMode(FlushModeType.COMMIT).setParameter("member",
							member).getSingleResult();
		} catch (Exception e) {
			cart = null;
		}
		return cart;
	}

}