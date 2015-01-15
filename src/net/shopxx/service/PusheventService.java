package net.shopxx.service;

import net.shopxx.entity.Pushevent;

/**
 * Service - 微信用户操作
 * 
 * @author shenlong
 * @version 3.0
 */
public interface PusheventService extends BaseService<Pushevent, Long>{

	/**
	 * 根据微信号查找记录
	 * 
	 * @param fromusername
	 *            微信号(忽略大小写)
	 * @return Pushevent 若不存在则返回null
	 */
	Pushevent findByFromusername(String fromusername);
}