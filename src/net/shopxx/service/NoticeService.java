package net.shopxx.service;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Notice;

public interface NoticeService extends BaseService<Notice,Long> {

	Page<Notice> findPublishedPage(Pageable pageable);
}
