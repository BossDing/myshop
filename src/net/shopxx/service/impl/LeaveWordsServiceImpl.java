package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.LeaveWordsDao;
import net.shopxx.entity.LeaveWords;
import net.shopxx.service.LeaveWordsService;

import org.springframework.stereotype.Service;

@Service("leaveWordsServiceImpl")
public class LeaveWordsServiceImpl extends BaseServiceImpl<LeaveWords, Long> implements LeaveWordsService {

	@Resource(name="leaveWordsDaoImpl")
	private LeaveWordsDao leaveWordsDao;

	@Resource(name = "leaveWordsDaoImpl")
	public void setBaseDao(LeaveWordsDao leaveWordsDao) {
		super.setBaseDao(leaveWordsDao);
	}
	
	public Page<LeaveWords> findPage(Long label ,Pageable pageable){
		
		return leaveWordsDao.findPage(label ,pageable);
	}

}
