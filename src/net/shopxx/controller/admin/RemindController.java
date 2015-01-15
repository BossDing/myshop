/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Ad;
import net.shopxx.entity.GroupRemind;
import net.shopxx.entity.Ad.Type;
import net.shopxx.service.AdPositionService;
import net.shopxx.service.AdService;
import net.shopxx.service.GroupService;
import net.shopxx.service.ProductService;
import net.shopxx.service.RemindService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 调度
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("remindController")
@RequestMapping("/admin/remind")
public class RemindController extends BaseController {

	@Resource(name = "remindServiceImpl")
	private RemindService remindService;
	@Resource(name = "groupServiceImpl")
	private GroupService groupService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		System.out.println("-----remindlist------");
		model.addAttribute("page", remindService.findPage(pageable));
		return "/admin/remind/list";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		System.out.println("---------edit-----------");
		model.addAttribute("remind", remindService.find(id));
		model.addAttribute("group", groupService.findAll());
		return "/admin/remind/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(GroupRemind remind, Long grid, RedirectAttributes redirectAttributes) {
		System.out.println("-----update-----"+remind.getContectperson()+"  "+grid+" "+remind.getId());
		remind.setGroupPurchase(groupService.find(grid));
		remindService.update(remind);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除  
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		System.out.println("---------del----------");
		remindService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}