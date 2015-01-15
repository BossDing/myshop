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
import net.shopxx.entity.Ad;
import net.shopxx.entity.Store;
import net.shopxx.entity.Ad.Type;
import net.shopxx.service.AdPositionService;
import net.shopxx.service.AdService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 广告
 * 
 * @author SHOP++ Team
 * @version 3.0
 */

@Controller("adminAdController")
@RequestMapping("/admin/ad")
public class AdController extends BaseController {

	@Resource(name = "adServiceImpl")
	private AdService adService;
	@Resource(name = "adPositionServiceImpl")
	private AdPositionService adPositionService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("types", Type.values());
//		model.addAttribute("adPositions", adPositionService.findPage(null).getContent());
		
		Store store = WebUtils.getStore();
		String entcode = WebUtils.getXentcode();
		List<Filter> filters = new ArrayList<Filter>();
		if (store == null) {
			if (entcode.equals("macrogw")) {
				filters.add(Filter.isNull("stores"));
			} else {
				filters = null;
			}
		} else {
			filters.add(new Filter("stores", Filter.Operator.eq, store));
		}
		model.addAttribute("adPositions", adPositionService.findList(null, filters, null));
		return "/admin/ad/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Ad ad, Long adPositionId, RedirectAttributes redirectAttributes) {
		ad.setAdPosition(adPositionService.find(adPositionId));
		if (!isValid(ad)) {
			return ERROR_VIEW;
		}
		if (ad.getBeginDate() != null && ad.getEndDate() != null && ad.getBeginDate().after(ad.getEndDate())) {
			return ERROR_VIEW;
		}
		if (ad.getType() == Type.text) {
			ad.setPath(null);
		} else {
			ad.setContent(null);
		}
		Store store = WebUtils.getStore();
		if(store != null) {
			ad.setStore(store);
		}
		adService.save(ad);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("types", Type.values());
		model.addAttribute("ad", adService.find(id));
//		model.addAttribute("adPositions", adPositionService.findPage(null).getContent());
		
		Store store = WebUtils.getStore();
		String entcode = WebUtils.getXentcode();
		List<Filter> filters = new ArrayList<Filter>();
		if (store == null) {
			if (entcode.equals("macrogw")) {
				filters.add(Filter.isNull("stores"));
			} else {
				filters = null;
			}
		} else {
			filters.add(new Filter("stores", Filter.Operator.eq, store));
		}
		model.addAttribute("adPositions", adPositionService.findList(null, filters, null));
		
		return "/admin/ad/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Ad ad, Long adPositionId, RedirectAttributes redirectAttributes) {
		ad.setAdPosition(adPositionService.find(adPositionId));
		if (!isValid(ad)) {
			return ERROR_VIEW;
		}
		if (ad.getBeginDate() != null && ad.getEndDate() != null && ad.getBeginDate().after(ad.getEndDate())) {
			return ERROR_VIEW;
		}
		if (ad.getType() == Type.text) {
			ad.setPath(null);
		} else {
			ad.setContent(null);
		}
		Store store = WebUtils.getStore();
		if(store != null) {
			ad.setStore(store);
		}
		adService.update(ad);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 * 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		Store store = WebUtils.getStore();
		if(store != null) {
			model.addAttribute("isStore", true);
		}
		model.addAttribute("page", adService.findPage(pageable));
		return "/admin/ad/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		adService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}