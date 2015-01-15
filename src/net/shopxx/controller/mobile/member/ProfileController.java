package net.shopxx.controller.mobile.member;

/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shopxx.CommonAttributes;
import net.shopxx.Message;
import net.shopxx.Setting;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Area;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberAttribute;
import net.shopxx.entity.Member.Gender;
import net.shopxx.entity.MemberAttribute.Type;
import net.shopxx.service.AreaService;
import net.shopxx.service.CartService;
import net.shopxx.service.MemberAttributeService;
import net.shopxx.service.MemberService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心 - 个人资料
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("mobileMemberProfileController")
@RequestMapping("/mobile/member/profile")
public class ProfileController extends BaseController {

	/** "会员"属性名称 */
	private static final String MEMBER_ATTRIBUTE_NAME = "member";
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;

	/**
	 * 检查E-mail是否唯一
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		Member member = memberService.getCurrent();
		if (memberService.emailUnique(member.getEmail(), email)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		Member member = memberService.getCurrent();//获取当前会员信息
		model.addAttribute("genders", Gender.values());
		model.addAttribute(MEMBER_ATTRIBUTE_NAME,member);
		model.addAttribute("memberAttributes", memberAttributeService.findList());
		return "mobile/member/profile/edit";
	}
	
	@RequestMapping(value = "/mybalance", method = RequestMethod.GET)
	public String mybalance(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		WebUtils.getOpenidtoLoginForWechat(request, response, session, memberService, cartService);
		Member member = memberService.getCurrent();//获取当前会员信息
		model.addAttribute(MEMBER_ATTRIBUTE_NAME,member);
		return "mobile/member/balance/mybalance";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	public  @ResponseBody
	Message update(String email, HttpServletRequest request, HttpServletResponse response) {
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
		if (!isValid(Member.class, "email", email)) {
//			out.println("邮箱验证失败！");
//			out.flush(); 
//			out.close(); 
//			return;
			return Message.error("邮箱验证失败！");
		}
		Setting setting = SettingUtils.get();
		Member member = memberService.getCurrent();
		if (!setting.getIsDuplicateEmail() && !memberService.emailUnique(member.getEmail(), email)) {
//			out.println("此邮箱已被注册！");
//			out.flush(); 
//			out.close(); 
//			return ;
			return Message.error("此邮箱已被注册！");
		}
		member.setEmail(email);
		List<MemberAttribute> memberAttributes = memberAttributeService.findList();
		for (MemberAttribute memberAttribute : memberAttributes) {
			String parameter = request.getParameter("memberAttribute_" + memberAttribute.getId());
			if (memberAttribute.getType() == Type.name || memberAttribute.getType() == Type.address || memberAttribute.getType() == Type.zipCode || memberAttribute.getType() == Type.phone || memberAttribute.getType() == Type.mobile || memberAttribute.getType() == Type.text || memberAttribute.getType() == Type.select) {
				if (memberAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
//					out.println("地址不能为空！");
//					out.flush(); 
//					out.close(); 
//					return ;
					return Message.error("邮箱验证失败！");
				}
				member.setAttributeValue(memberAttribute, parameter);
			} else if (memberAttribute.getType() == Type.gender) {
				Gender gender = StringUtils.isNotEmpty(parameter) ? Gender.valueOf(parameter) : null;
				if (memberAttribute.getIsRequired() && gender == null) {
//					out.println("性别不能为空！");
//					out.flush(); 
//					out.close(); 
//					return ;
					return Message.error("性别不能为空！");
				}
				member.setGender(gender);
			} else if (memberAttribute.getType() == Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter) ? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (memberAttribute.getIsRequired() && birth == null) {
//						out.println("出生日期不能为空！");
//						out.flush(); 
//						out.close(); 
//						return ;
						return Message.error("出生日期不能为空！");
					}
					member.setBirth(birth);
				} catch (ParseException e) {
//					out.println("出生日期格式解析出错！");
//					out.flush(); 
//					out.close(); 
//					return ;
					return Message.error("出生日期格式解析出错！");
				}
			} else if (memberAttribute.getType() == Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					member.setArea(area);
				} else if (memberAttribute.getIsRequired()) {
//					out.println("地区不能为空！");
//					out.flush(); 
//					out.close(); 
//					return ;
					return Message.error("地区不能为空！");
				}
			} else if (memberAttribute.getType() == Type.checkbox) {
				String[] parameterValues = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (memberAttribute.getIsRequired() && (options == null || options.isEmpty())) {
//					out.println("多选框出错！");
//					out.flush(); 
//					out.close(); 
//					return ;
					return Message.error("多选框出错！");
				}
				member.setAttributeValue(memberAttribute, options);
			}
		}
		memberService.update(member);
//		out.println("操作成功！");
//		out.flush(); 
//		out.close(); 
		return Message.success("修改成功！");
	}

}