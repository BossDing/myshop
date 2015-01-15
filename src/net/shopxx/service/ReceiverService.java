/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.Receiver;

/**
 * Service - 收货地址
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface ReceiverService extends BaseService<Receiver, Long> {

	/**
	 * 查找默认收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 默认收货地址，若不存在则返回最新收货地址
	 */
	Receiver findDefault(Member member);

	/**
	 * 查找收货地址分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 收货地址分页
	 */
	Page<Receiver> findPage(Member member, Pageable pageable);
	
	/**
	 * 查询一个收货地址(手机web端)
	 * @author	Guoxianlong
	 * @param member	会员
	 * @param id	收货地址id
	 * @since 2014/05/13
	 * @return 先查找默认收货地址，若不存在则返回最新的"一个"收货地址
	 */
	List<Receiver> findOneForMobile(Member member, Long id);

}