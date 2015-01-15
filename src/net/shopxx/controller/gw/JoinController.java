package net.shopxx.controller.gw;

/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */


import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Setting.CaptchaType;
import net.shopxx.entity.LeaveWords;
import net.shopxx.service.CaptchaService;
import net.shopxx.service.LeaveWordsService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Controller - 加盟中心
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("gwJoinController")
@RequestMapping("/gw/join")
public class JoinController extends BaseController {
	@Resource(name = "leaveWordsServiceImpl")
	private LeaveWordsService leaveWordsService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	/**			
	 * 加盟申请
	 */
	@RequestMapping(value = "/apply", method = RequestMethod.GET)
	public String apply(HttpServletRequest request, ModelMap model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/gw/join/apply";
	}
	/**				
	 * 加盟介绍		
	 */
	@RequestMapping(value = "/intruduction", method = RequestMethod.GET)
	public String intruduction(HttpServletRequest request, ModelMap model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/gw/join/intruduction";
	}
	/**				
	 * 加盟介绍		       
	 */
	@RequestMapping(value = "/fireplace", method = RequestMethod.GET)
	public String fireplace(HttpServletRequest request, ModelMap model) {
		return "/gw/join/fireplace";
	}
	/**
	 * 加盟介绍		
	 */
	@RequestMapping(value = "/hec", method = RequestMethod.GET)
	public String hec(HttpServletRequest request, ModelMap model) {
		return "/gw/join/hec";
	}
	/**
	 * 加盟介绍					
	 */
	@RequestMapping(value = "/kitchen", method = RequestMethod.GET)
	public String kitchen(HttpServletRequest request, ModelMap model) {
		return "/gw/join/kitchen";
	}
	/**
	 *保存
	 */
	@RequestMapping(value = "/savemessage", method = RequestMethod.POST)
	public @ResponseBody
	Message savemessage(String captchaId, String captcha,LeaveWords leaveWords, HttpServletRequest request) {
		if (!captchaService.isValid(CaptchaType.consultation, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		leaveWords.setEntcode(WebUtils.getXentcode());
		leaveWords.setConsultationType("7");
		leaveWordsService.save(leaveWords);
		return Message.success("shop.consultation.success");
		
	}
	/**
	 *保存
	 */
	@RequestMapping(value = "/saveapply", method = RequestMethod.POST)
	public @ResponseBody
	Message saveapply(String captchaId, String captcha,LeaveWords leaveWords, HttpServletRequest request) {
		if (!captchaService.isValid(CaptchaType.consultation, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		leaveWords.setEntcode(WebUtils.getXentcode());
		leaveWords.setConsultationType("8");
		leaveWordsService.save(leaveWords);
		return Message.success("shop.consultation.success");
		
	}
	
}