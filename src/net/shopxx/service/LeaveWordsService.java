package net.shopxx.service;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.LeaveWords;

public interface LeaveWordsService extends BaseService<LeaveWords, Long> {

	Page<LeaveWords> findPage(Long label ,Pageable pageable);
}
