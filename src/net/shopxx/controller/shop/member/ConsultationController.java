/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop.member;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.Setting.ConsultationAuthority;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Consultation;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.ConsultationService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.util.SettingUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心 - 咨询
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopMemberConsultationController")
@RequestMapping("/member/consultation")
public class ConsultationController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "consultationServiceImpl")
	private ConsultationService consultationService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNumber, ModelMap model) {
		Member member = memberService.getCurrent();
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", consultationService.findPage(member, null, null, pageable));
		return "shop/member/consultation/list";
	}
	
	@RequestMapping(value = "/addConsultation", method = RequestMethod.GET)
	public String addConsultation(ModelMap model) {
		//Setting setting = SettingUtils.get();
		System.out.println("How are you!");
		return "/shop/consultation/addMessage";
	}
	
	/**
	 *保存
	 */
	@RequestMapping(value = "/saveX", method = RequestMethod.POST)
	public @ResponseBody
	Message saveX(String theme ,String accessory ,String consultationType ,Long id, String content, HttpServletRequest request) {
		System.out.println("--ee1--");
		Setting setting = SettingUtils.get();
		if (!setting.getIsConsultationEnabled()) {
			return Message.error("shop.consultation.disabled");
		}
		if (!isValid(Consultation.class, "theme", theme)) {
			return ERROR_MESSAGE;
		}
		if (!isValid(Consultation.class, "content", content)) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (setting.getConsultationAuthority() != ConsultationAuthority.anyone && member == null) {
			return Message.error("shop.consultation.accessDenied");
		}
		Product product = null;
		if(id != null){
			product = productService.find(id);
			if (product == null) {
				return ERROR_MESSAGE;
			}
		}
		Consultation consultation = new Consultation();
		consultation.setContent(content);
		consultation.setTheme(theme);
		consultation.setAccessory(accessory);
		consultation.setConsultationType(consultationType);
		consultation.setIp(request.getRemoteAddr());
		consultation.setMember(member);
		consultation.setProduct(product);
		if (setting.getIsConsultationCheck()) {
			System.out.println("--ee2--");
			consultation.setIsShow(false);
			System.out.println("--ee3--");
				consultationService.save(consultation);
			System.out.println("--ee4--");
			return Message.success("shop.consultation.check");
		} else {
			System.out.println("--ee5--");
			consultation.setIsShow(true);
			System.out.println("--ee7--");
			consultationService.save(consultation);
			System.out.println("--ee8--");
			return Message.success("shop.consultation.success");
		}
	}

}