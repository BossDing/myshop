/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Store;
import net.shopxx.service.ShippingService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 发货单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminShippingController")
@RequestMapping("/admin/shipping")
public class ShippingController extends BaseController {

	@Resource(name = "shippingServiceImpl")
	private ShippingService shippingService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("shipping", shippingService.find(id));
		return "/admin/shipping/view";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		Store store = WebUtils.getStore();
		if (store == null) {
			model.addAttribute("page", shippingService.findPage(pageable));
		} else {
			List<Filter> filters = new ArrayList<Filter>();
			Filter filter = new Filter("store", Filter.Operator.eq, store);
			filters.add(filter);
			model.addAttribute("page", shippingService.findPage(filters, null, pageable));
		}
		return "/admin/shipping/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		shippingService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}