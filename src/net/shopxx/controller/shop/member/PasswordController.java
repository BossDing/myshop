/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop.member;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Setting;
import net.shopxx.Setting.CaptchaType;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.service.CaptchaService;
import net.shopxx.service.MemberService;
import net.shopxx.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员中心 - 密码
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopMemberPasswordController")
@RequestMapping("/member/password")
public class PasswordController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	/**
	 * 验证当前密码
	 */
	@RequestMapping(value = "/check_current_password", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkCurrentPassword(String currentPassword) {
		if (StringUtils.isEmpty(currentPassword)) {
			return false;
		}
		Member member = memberService.getCurrent();
		if (StringUtils.equals(DigestUtils.md5Hex(currentPassword), member.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit() {
		return "shop/member/password/edit";
	}

	/**
	 * 跳转到修改密码页面，验证个人信息
	 * @param model
	 * @returnStringhfh
	 */
	@RequestMapping(value="/checkInfo",method=RequestMethod.GET)
	public String checkInfo(Model model){
		Member member = memberService.getCurrent();
		model.addAttribute("member", member);
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "shop/member/password/updatePwd";
	}   
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	Message update(String captchaId, String captcha,String currentPassword, String password, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if (!captchaService.isValid(CaptchaType.resetPassword, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(currentPassword)) {
			return Message.error("shop.passwrod.nullinvalid");
		}
		Setting setting = SettingUtils.get();
		if (password.length() < setting.getPasswordMinLength() || password.length() > setting.getPasswordMaxLength()) {
			return Message.error("shop.passwrod.lengthvalid");
		}
		Member member = memberService.getCurrent();
		if (!StringUtils.equals(DigestUtils.md5Hex(currentPassword), member.getPassword())) {
			return Message.error("shop.password.valid");
		}
		member.setPassword(DigestUtils.md5Hex(password));
		memberService.update(member);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE); 
		return Message.success("shop.password.success");  
	}

}