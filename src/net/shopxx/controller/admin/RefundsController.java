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
import net.shopxx.entity.Refunds;
import net.shopxx.entity.Store;
import net.shopxx.service.RefundsService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 退款单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminRefundsController")
@RequestMapping("/admin/refunds")
public class RefundsController extends BaseController {

	@Resource(name = "refundsServiceImpl")
	private RefundsService refundsService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		Refunds refunds = refundsService.find(id);
		Store store = WebUtils.getStore();
		if (store != null) {
			if (!store.getId().equals(refunds.getStore().getId())) { //店铺管理员只能查自家的退款单
				refunds = null;
			}
		}
		model.addAttribute("refunds", refunds);
		return "/admin/refunds/view";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		Store store = WebUtils.getStore();
		if (store == null) {
			model.addAttribute("page", refundsService.findPage(pageable));
		} else {
			List<Filter> filters = new ArrayList<Filter>();
			Filter filter = new Filter("store", Filter.Operator.eq, store);
			filters.add(filter);
			model.addAttribute("page", refundsService.findPage(filters, null, pageable));
		}
		return "/admin/refunds/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		Store store = WebUtils.getStore();
		List<Long> ids2 = new ArrayList<Long>();
		if (ids != null) {
			for (Long id : ids) {
				Refunds refunds = refundsService.find(id);
				if (store != null) {
					if (store.getId().equals(refunds.getStore().getId())) { //店铺管理员只能删除自家的退款单
						ids2.add(id);
					}
				}
			}
			if (store != null)
				refundsService.delete((Long[]) ids2.toArray());
			else
				refundsService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}