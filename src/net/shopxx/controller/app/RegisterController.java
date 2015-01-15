package net.shopxx.controller.app;
/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.shopxx.Setting;
import net.shopxx.entity.Member;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.MemberService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.TwUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 
 * @author: Guoxianlong
 * @date: Sep 16, 2014  10:38:01 AM
 */
@Controller("APPRegisterController")
@RequestMapping("/m/register")
public class RegisterController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;

	/**
	 * 注册提交
	 */
	@RequestMapping(value = "/submit",method = RequestMethod.POST)
	public @ResponseBody ModelMap submit(String username, String password, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			username = (String) obj.get("username");
			password = (String) obj.get("password");
			
			Setting setting = SettingUtils.get();
			if (!setting.getIsRegisterEnabled()) {
				model.put("success", 1);
				model.put("error", "您好, 会员注册功能已关闭 非常抱歉!");
				return model;
			}
			if (memberService.usernameDisabled(username) || memberService.usernameExists(username)) {
				model.put("success", 1);
				model.put("error", "用户名被禁用活或已被注册, 请重新注册用户 谢谢！");
				return model;
			}
			Member member = new Member();
			member.setUsername(username.toLowerCase());
			member.setPassword(DigestUtils.md5Hex(password));
			member.setPoint(setting.getRegisterPoint());
			member.setAmount(new BigDecimal(0));
			member.setBalance(new BigDecimal(0));
			member.setIsEnabled(true);
			member.setIsLocked(false);
			member.setLoginFailureCount(0);
			member.setLockedDate(null);
			member.setLoginIp(request.getRemoteAddr());
			member.setRegisterIp(request.getRemoteAddr());
			member.setLoginDate(new Date());
			member.setCreateDate(new Date());
			member.setModifyDate(new Date());
			member.setSafeKey(null);
			member.setMemberRank(memberRankService.findDefault());
			member.setFavoriteProducts(null);
			memberService.save(member);
			
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	

}