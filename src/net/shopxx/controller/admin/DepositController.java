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
import net.shopxx.Pageable;
import net.shopxx.entity.Member;
import net.shopxx.entity.Store;
import net.shopxx.service.DepositService;
import net.shopxx.service.MemberService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 预存款
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminDepositController")
@RequestMapping("/admin/deposit")
public class DepositController extends BaseController {

	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long memberId, Pageable pageable, ModelMap model) {
		Member member = memberService.find(memberId);
		Store store = WebUtils.getStore();
		if (member != null) {
			model.addAttribute("member", member);
			model.addAttribute("page", depositService.findPage(member, pageable));
		} else {
			if (store == null) {
				model.addAttribute("page", depositService.findPage(pageable));
			} else {
				List<Filter> filters = new ArrayList<Filter>();
				Filter filter = new Filter("store", Filter.Operator.eq, store);
				filters.add(filter);
				model.addAttribute("page", depositService.findPage(filters, null, pageable));
			}
		}
		return "/admin/deposit/list";
	}

}