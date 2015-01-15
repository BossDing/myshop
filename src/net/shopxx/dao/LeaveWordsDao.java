package net.shopxx.dao;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.LeaveWords;

public interface LeaveWordsDao extends BaseDao<LeaveWords,Long> {

	Page<LeaveWords> findPage(Long laber ,Pageable pageable);
}
