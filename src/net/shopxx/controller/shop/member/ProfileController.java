/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop.member;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.CommonAttributes;
import net.shopxx.Message;
import net.shopxx.Setting;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Area;
import net.shopxx.entity.Member;
import net.shopxx.entity.Member.Gender;
import net.shopxx.entity.MemberAttribute;
import net.shopxx.entity.MemberAttribute.Type;
import net.shopxx.service.AreaService;
import net.shopxx.service.MemberAttributeService;
import net.shopxx.service.MemberService;
import net.shopxx.util.SettingUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员中心 - 个人资料
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopMemberProfileController")
@RequestMapping("/member/profile")
public class ProfileController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

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
		Member member = memberService.getCurrent();
		model.addAttribute("member", member);
		return "shop/member/profile/index";
	}
	
	/**
	 * 编辑增值税发票
	 */
	@RequestMapping(value = "/invoice", method = RequestMethod.GET)
	public String invoice(ModelMap model) {

		Member member = memberService.getCurrent();
		model.addAttribute("member", member);
		return "shop/member/invoice/list";
	}
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(String email, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!isValid(Member.class, "email", email)) {
			return ERROR_VIEW;
		}
		Setting setting = SettingUtils.get();
		Member member = memberService.getCurrent();
		if (!setting.getIsDuplicateEmail() && !memberService.emailUnique(member.getEmail(), email)) {
			return ERROR_VIEW;
		}
		member.setEmail(email);
		List<MemberAttribute> memberAttributes = memberAttributeService.findList();
		for (MemberAttribute memberAttribute : memberAttributes) {
			String parameter = request.getParameter("memberAttribute_" + memberAttribute.getId());
			if (memberAttribute.getType() == Type.name || memberAttribute.getType() == Type.address || memberAttribute.getType() == Type.zipCode || memberAttribute.getType() == Type.phone || memberAttribute.getType() == Type.mobile || memberAttribute.getType() == Type.text || memberAttribute.getType() == Type.select) {
				if (memberAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, parameter);
			} else if (memberAttribute.getType() == Type.gender) {
				Gender gender = StringUtils.isNotEmpty(parameter) ? Gender.valueOf(parameter) : null;
				if (memberAttribute.getIsRequired() && gender == null) {
					return ERROR_VIEW;
				}
				member.setGender(gender);
			} else if (memberAttribute.getType() == Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter) ? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (memberAttribute.getIsRequired() && birth == null) {
						return ERROR_VIEW;
					}
					member.setBirth(birth);
				} catch (ParseException e) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					member.setArea(area);
				} else if (memberAttribute.getIsRequired()) {
					return ERROR_VIEW;
				}
			} else if (memberAttribute.getType() == Type.checkbox) {
				String[] parameterValues = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (memberAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					return ERROR_VIEW;
				}
				member.setAttributeValue(memberAttribute, options);
			}
		}
		memberService.update(member);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:edit.jhtml";
	}
	
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(String username,String name,String email,String zipCode,String mobile,String phone,String birth,Long areaId,String address, Gender gender,String qq,String wangwang,String zhifubao,String yinghanzhanghao ,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Member member = memberService.getCurrent();
		member.setUsername(username);
		member.setName(name);
		member.setEmail(email);
		member.setZipCode(zipCode);
		member.setMobile(mobile);
		member.setPhone(phone);
		member.setGender(gender);     
		member.setArea(areaService.find(areaId));
		member.setAddress(address);
		if(birth!=null&&!birth.equals("")){
			Date birth_date = null;
			try {
				birth_date = StringUtils.isNotEmpty(birth) ? DateUtils.parseDate(birth, CommonAttributes.DATE_PATTERNS) : null;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			member.setBirth(birth_date);  
		}
		member.setQq(qq);
		member.setWangwang(wangwang);
		member.setZhifubao(zhifubao);
		member.setYinghanzhanghao(yinghanzhanghao);
		memberService.update(member);    
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		//return "redirect:edit.jhtml";
		return Message.success("shop.register.success");
	}
	
	/**
	 * 保存增值税发票
	 */
	@RequestMapping(value = "/saveinvoice", method = RequestMethod.POST)
	public @ResponseBody
	Message saveinvoice(String dangweimingchen,String nashuishibiehao,String zhucedizhi,String zhucedianhua,String kaihuyh,String zengzhishuiyhzh,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Member member = memberService.getCurrent();
		member.setDangweimingchen(dangweimingchen);
		member.setNashuishibiehao(nashuishibiehao);
		member.setZhucedizhi(zhucedizhi);
		member.setZhucedianhua(zhucedianhua);
		member.setKaihuyh(kaihuyh);
		member.setZengzhishuiyhzh(zengzhishuiyhzh);
		memberService.update(member);    
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return Message.success("shop.register.success");
	}

}