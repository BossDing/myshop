/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Store;
import net.shopxx.plugin.PaymentPlugin;
import net.shopxx.plugin.StoragePlugin;

/**
 * Service - 插件
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface PluginService {

	/**
	 * 获取支付插件
	 * 
	 * @return 支付插件
	 */
	List<PaymentPlugin> getPaymentPlugins();
	
	/**
	 * 店铺后台获取支付插件
	 * @author Guoxianlong
	 * @param store
	 * @return 支付插件
	 */
	List<PaymentPlugin> getPaymentPlugins(Store store);

	/**
	 * 获取存储插件
	 * 
	 * @return 存储插件
	 */
	List<StoragePlugin> getStoragePlugins();

	/**
	 * 获取支付插件
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @return 支付插件
	 */
	List<PaymentPlugin> getPaymentPlugins(boolean isEnabled);
	
	/**
	 * 获取存储插件
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @return 存储插件
	 */
	List<StoragePlugin> getStoragePlugins(boolean isEnabled);

	/**
	 * 获取支付插件
	 * 
	 * @param id
	 *            ID
	 * @return 支付插件
	 */
	PaymentPlugin getPaymentPlugin(String id);

	/**
	 * 获取支付插件
	 * 
	 * @param id
	 *            ID
	 * @param store
	 * 			  店铺
	 * 
	 * @return 支付插件
	 */
	PaymentPlugin getPaymentPlugin(String id, Store store);
	/**
	 * 获取存储插件
	 * 
	 * @param id
	 *            ID
	 * @return 存储插件
	 */
	StoragePlugin getStoragePlugin(String id);

}