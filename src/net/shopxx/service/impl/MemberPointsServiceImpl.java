/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.MemberAttributeDao;
import net.shopxx.dao.MemberPointsDao;
import net.shopxx.entity.MemberAttribute;
import net.shopxx.entity.MemberPoints;
import net.shopxx.service.MemberAttributeService;
import net.shopxx.service.MemberPointsService;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 会员积分规则
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("memberPointsServiceImpl")
public class MemberPointsServiceImpl extends BaseServiceImpl<MemberPoints, Long> implements MemberPointsService {

	@Resource(name = "memberPointsDaoImpl")
	private MemberPointsDao memberPointsDao;

	@Resource(name = "memberPointsDaoImpl")
	public void setBaseDao(MemberPointsDao memberPointsDao) {
		super.setBaseDao(memberPointsDao);
	}

	@Transactional(readOnly = true)
	public List<MemberPoints> findList() {
		return memberPointsDao.findList();
	}


	@Override
	@Transactional
	public void save(MemberPoints memberPoints) {
		super.save(memberPoints);
	}
	
	

	@Override
	@Transactional
	public MemberPoints update(MemberPoints memberPoints) {
		return super.update(memberPoints);
	}

	@Override
	@Transactional
	public MemberPoints update(MemberPoints memberPoints, String... ignoreProperties) {
		return super.update(memberPoints, ignoreProperties);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	public void delete(MemberPoints memberPoints) {
		super.delete(memberPoints);
	}

	@Transactional(readOnly = true)
	public boolean nameUnique(String previousName, String currentName) {
		if (StringUtils.equalsIgnoreCase(previousName, currentName)) {
			return true;
		} else {
			if (memberPointsDao.nameExists(currentName)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public MemberPoints findByRuleName(String rulename) {
		return memberPointsDao.findByRuleName(rulename);
	}
}