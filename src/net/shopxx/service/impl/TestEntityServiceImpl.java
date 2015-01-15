package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.TestEntityDao;
import net.shopxx.dao.impl.TestEntityDaoImpl;
import net.shopxx.entity.TestEntity;
import net.shopxx.service.TestEntityService;

import org.springframework.stereotype.Service;

@Service("testEntityServiceImpl")
public class TestEntityServiceImpl extends BaseServiceImpl<TestEntity, Long>
		implements TestEntityService {
	@Resource(name = "testEntityDaoImpl")
	private TestEntityDaoImpl testEntityDao;
	
	@Resource(name = "testEntityDaoImpl")
	public void setBaseDao(TestEntityDao testEntityDao) {
		super.setBaseDao(testEntityDao);
	}
}
