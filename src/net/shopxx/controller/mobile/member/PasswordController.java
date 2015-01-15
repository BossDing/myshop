/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.mobile.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopxx.Message;
import net.shopxx.Setting;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.service.MemberService;
import net.shopxx.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心 - 密码
 * 创建日期：2014-05-08
 * @author chengandou
 * @version 3.0
 */
@Controller("mobileMemberPasswordController")
@RequestMapping("/mobile/member/password")
public class PasswordController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

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
		return "mobile/member/password/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	Message update(String currentPassword, String password, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("updatePassword");
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//		} catch (IOException e) {
//			e.printStackTrace();
////			return;
//		}
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(currentPassword)) {
//			out.print("新密码不能为空！");
//			out.flush(); 
//			out.close(); 
			return Message.error("新密码不能为空！");
		}
		if (!isValid(Member.class, "password", password)) {
//			out.print("新密码验证失败！");
//			out.flush(); 
//			out.close(); 
			return Message.error("新密码验证失败！");
		}
		Setting setting = SettingUtils.get();
		if (password.length() < setting.getPasswordMinLength() || password.length() > setting.getPasswordMaxLength()) {
//			out.print("新密码格式不正确！");
//			out.flush(); 
//			out.close(); 
			return Message.error("新密码格式不正确！");
		}
		Member member = memberService.getCurrent();
		if (!StringUtils.equals(DigestUtils.md5Hex(currentPassword), member.getPassword())) {
//			out.print("新密码格式不正确！");
//			out.flush(); 
//			out.close(); 
			return Message.error("新密码格式不正确！");
		}
		member.setPassword(DigestUtils.md5Hex(password));
		memberService.update(member);
//		out.print("操作成功！");
//		out.flush(); 
//		out.close();
		return Message.success("操作成功！");
	}

}