package net.shopxx.dao;

import net.shopxx.entity.Extension;
import net.shopxx.entity.Member;


public interface ExtensionDao  extends BaseDao<Extension, Long>{


	Extension findByMember(String username);
	
}
