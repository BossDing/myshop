/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import net.shopxx.dao.AdDao;
import net.shopxx.dao.GroupDao;
import net.shopxx.entity.Ad;
import net.shopxx.entity.GroupPurchase;

import org.springframework.stereotype.Repository;

/**
 * Dao - 广告
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("groupDaoImpl")
public class GroupDaoImpl extends BaseDaoImpl<GroupPurchase, Long> implements GroupDao{

}