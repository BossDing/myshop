/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.Message;

/**
 * Service - 消息
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface MessageService extends BaseService<Message, Long> {

	/**
	 * 查找消息分页
	 * 
	 * @param member
	 *            会员,null表示管理员
	 * @param pageable
	 *            分页信息
	 * @return 消息分页
	 */
	Page<Message> findPage(Member member, Pageable pageable);

	/**
	 * 查找草稿分页
	 * 
	 * @param sender
	 *            发件人,null表示管理员
	 * @param pageable
	 *            分页信息
	 * @return 草稿分页
	 */
	Page<Message> findDraftPage(Member sender, Pageable pageable);

	/**
	 * 查找消息数量
	 * 
	 * @param member
	 *            会员，null表示管理员
	 * @param read
	 *            是否已读
	 * @return 消息数量，不包含草稿
	 */
	Long count(Member member, Boolean read);

	/**
	 * 删除消息
	 * 
	 * @param ids
	 *            Long
	 * @param member
	 *            执行人,null表示管理员
	 */
	void delete(Long[] ids, Member member);
	
	Page<Message> findSenderPage(Member sender, Pageable pageable);
	
	Page<Message> findReceiverPage(Member receiver, Pageable pageable);
	
	/**
	 * 删除信息（适用于删除自己给自己发的信息）
	 * @param id
	 * @param member
	 * @param remark
	 * 			可为空,"inbox"时删除收件箱,"outbox"删除发件箱
	 */
	void removeX(Long[] id, Member member , String remark);
	
	

}