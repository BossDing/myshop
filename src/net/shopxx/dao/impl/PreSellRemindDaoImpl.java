package net.shopxx.dao.impl;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.stereotype.Repository;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.PreSellRemindDao;
import net.shopxx.entity.PreSellRemind;

/**
 * Dao - 预售提醒
 * @author hfh
 *
 */
@Repository("preSellRemindDaoImpl")
public class PreSellRemindDaoImpl extends BaseDaoImpl<PreSellRemind,Long > implements
		PreSellRemindDao {
	
}
