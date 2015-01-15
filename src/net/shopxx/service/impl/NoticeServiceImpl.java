package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.NoticeDao;
import net.shopxx.entity.Notice;
import net.shopxx.service.NoticeService;

import org.springframework.stereotype.Service;

@Service("noticeServiceImpl")
public class NoticeServiceImpl extends BaseServiceImpl<Notice,Long> implements NoticeService {
	
	@Resource(name = "noticeDaoImpl")
	private NoticeDao noticeDao;

	@Resource(name = "noticeDaoImpl")
	public void setBaseDao(NoticeDao noticeDao) {
		super.setBaseDao(noticeDao);
	}
	
	public Page<Notice> findPublishedPage(Pageable pageable){
		return noticeDao.findPublishedPage(pageable);
	}
}
