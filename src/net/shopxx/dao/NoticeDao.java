package net.shopxx.dao;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Notice;

public interface NoticeDao extends BaseDao<Notice,Long> {

	Page<Notice> findPublishedPage(Pageable pageable);
}
