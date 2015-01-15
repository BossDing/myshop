/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.PluginConfig;
import net.shopxx.entity.Store;

/**
 * Service - 插件配置
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface PluginConfigService extends BaseService<PluginConfig, Long> {

	/**
	 * 判断插件ID是否存在
	 * 
	 * @param pluginId
	 *            插件ID
	 * @return 插件ID是否存在
	 */
	boolean pluginIdExists(String pluginId);

	/**
	 * 根据插件ID查找插件配置
	 * 
	 * @param pluginId
	 *            插件ID
	 * @return 插件配置，若不存在则返回null
	 */
	PluginConfig findByPluginId(String pluginId);
	
	/**
	 * 根据插件ID查找插件配置
	 * 
	 * @param pluginId
	 *            插件ID
	 * @param store
	 *            店铺ID
	 * @return 插件配置，若不存在则返回null
	 */
	PluginConfig findByPluginId(String pluginId, Store store);
	
	/**
	 * 根据店铺查找插件配置
	 * @param store
	 * 		店铺ID
	 * @return
	 * 		插件集合
	 * @author wmd
	 * @date 2014/11/26
	 */
	public List<PluginConfig> findListForDeleteStore(Store store);


}