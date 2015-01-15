package net.shopxx.service;

import net.shopxx.entity.Extension;
import net.shopxx.entity.Member;
import net.shopxx.service.BaseService;

public interface ExtensionService extends BaseService<Extension, Long> {

	/**
	 * 
	 * @param membervoidhfh
	 */

	Extension findByMember(String username);

}
