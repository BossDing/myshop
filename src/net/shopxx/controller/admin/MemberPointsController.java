/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.MemberPoints;
import net.shopxx.service.MemberPointsService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员积分规则
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminMemberPointsController")
@RequestMapping("/admin/member_points")
public class MemberPointsController extends BaseController {

	@Resource(name = "memberPointsServiceImpl")
	private MemberPointsService memberPointsService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", memberPointsService.findPage(pageable));
		return "/admin/member_points/list";
	}
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/member_points/add";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(MemberPoints memberpoints, RedirectAttributes redirectAttributes) {
		if (!isValid(memberpoints)) {
			return ERROR_VIEW;
		}
		if (!memberPointsService.nameUnique(new String(),memberpoints.getRulename())) {
			return ERROR_VIEW;
		}
		memberPointsService.save(memberpoints);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	/**
	 * 校验规则名称是否唯一
	 */
	@RequestMapping(value = "/check_rulename", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkRuleName(String previousName,String rulename) {
		if (StringUtils.isEmpty(rulename)) {
			return false;
		}

		if (memberPointsService.nameUnique(previousName, rulename)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			memberPointsService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("memberPoints", memberPointsService.find(id));
		return "/admin/member_points/edit";
	}
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MemberPoints memberPoints, RedirectAttributes redirectAttributes) {
		if (!isValid(memberPoints)) {
			return ERROR_VIEW;
		}
		MemberPoints pMemberPoints = memberPointsService.find(memberPoints.getId());
		if (pMemberPoints == null) {
			return ERROR_VIEW;
		}
		if (!memberPointsService.nameUnique(pMemberPoints.getRulename(), memberPoints.getRulename())) {
			return ERROR_VIEW;
		}
		memberPointsService.update(memberPoints);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
}