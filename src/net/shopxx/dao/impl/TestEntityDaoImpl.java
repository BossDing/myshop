package net.shopxx.dao.impl;

import net.shopxx.dao.TestEntityDao;
import net.shopxx.entity.TestEntity;

import org.springframework.stereotype.Repository;

@Repository("testEntityDaoImpl")
public class TestEntityDaoImpl extends BaseDaoImpl<TestEntity, Long> implements
		TestEntityDao {
	
}
