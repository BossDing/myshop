/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.shopxx.entity.Member;

/**
 * Dao - 会员
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);
	
	/**
	 * 判断mobile是否存在
	 * 
	 * @param mobile
	 *            
	 * @return mobile是否存在
	 */
	boolean mobileExists(String mobile);

	/**
	 * 根据用户名查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByUsername(String username);

	
	/**
	 * 根据微信号查找会员
	 * 
	 * @param microno
	 *            微信号(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByMicrono(String microno);
	
	/**
	 * 根据用户名、手机查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findLoginByUsername(String username);
	
	/**
	 * 根据E-mail查找会员
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	List<Member> findListByEmail(String email);
	/**
	 * 根据微信识别号openid查找会员
	 * 
	 * @param openid
	 *            openid(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	List<Member> findListByWechatOpenid(String openid);	
	/**
	 * 查找会员消费信息
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param count
	 *            数量
	 * @return 会员消费信息
	 */
	List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count);

	/**
	 * 修改用户积分
	 * 
	 */
	boolean updatPoint(Long id,BigDecimal point);
}