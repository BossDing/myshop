/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.entity.Store;
import net.shopxx.plugin.PaymentPlugin;
import net.shopxx.service.PluginService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 支付插件
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminPaymentPluginController")
@RequestMapping("/admin/payment_plugin")
public class PaymentPluginController extends BaseController {

	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<PaymentPlugin> paymentPlugins = null;
		Store store = WebUtils.getStore();
		if(store != null) {
			paymentPlugins = pluginService.getPaymentPlugins(store);
			model.addAttribute("isStore", true);
		} else {
			 paymentPlugins = pluginService.getPaymentPlugins();
		}
		model.addAttribute("paymentPlugins", paymentPlugins);
		return "/admin/payment_plugin/list";
	}

}