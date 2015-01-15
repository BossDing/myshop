/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.Setting;
import net.shopxx.Setting.CaptchaType;
import net.shopxx.Setting.ConsultationAuthority;
import net.shopxx.entity.Consultation;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.CaptchaService;
import net.shopxx.service.ConsultationService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.util.SettingUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 咨询
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopConsultationController")
@RequestMapping("/consultation")
public class ConsultationController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "consultationServiceImpl")
	private ConsultationService consultationService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;

	/**
	 * 发表
	 */
	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public String add(@PathVariable Long id, ModelMap model) {
		Setting setting = SettingUtils.get();
		if (!setting.getIsConsultationEnabled()) {
			throw new ResourceNotFoundException();
		}
		Product product = productService.find(id);
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("product", product);
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/shop/consultation/add";
	}

	/**
	 * 内容
	 */
	@RequestMapping(value = "/content/{id}", method = RequestMethod.GET)
	public String content(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		Setting setting = SettingUtils.get();
		if (!setting.getIsConsultationEnabled()) {
			throw new ResourceNotFoundException();
		}
		Product product = productService.find(id);
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("product", product);
		model.addAttribute("page", consultationService.findPage(null, product, true, pageable));
		return "/shop/consultation/content";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(String captchaId, String captcha, Long id, String content, HttpServletRequest request) {
		if (!captchaService.isValid(CaptchaType.consultation, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		Setting setting = SettingUtils.get();
		if (!setting.getIsConsultationEnabled()) {
			return Message.error("shop.consultation.disabled");
		}
		if (!isValid(Consultation.class, "content", content)) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (setting.getConsultationAuthority() != ConsultationAuthority.anyone && member == null) {
			return Message.error("shop.consultation.accessDenied");
		}
		Product product = productService.find(id);
		if (product == null) {
			return ERROR_MESSAGE;
		}
		Consultation consultation = new Consultation();
		consultation.setContent(content);
		consultation.setIp(request.getRemoteAddr());
		consultation.setMember(member);
		consultation.setProduct(product);
		if (setting.getIsConsultationCheck()) {
			consultation.setIsShow(false);
			consultationService.save(consultation);
			return Message.success("shop.consultation.check");
		} else {
			consultation.setIsShow(true);
			consultationService.save(consultation);
			return Message.success("shop.consultation.success");
		}
	}
	
	/**
	 * 发表
	 */
	@RequestMapping(value = "/addMessage/{id}", method = RequestMethod.GET)
	public String addMessage(@PathVariable Long id, ModelMap model) {
		Setting setting = SettingUtils.get();
		/*
		if (!setting.getIsConsultationEnabled()) {
			throw new ResourceNotFoundException();
		}
		*/
		Product product = productService.find(id);
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("product", product);
		//model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/shop/consultation/addMessage";
	}
	
	@RequestMapping(value = "/addConsultation", method = RequestMethod.GET)
	public String addConsultation(ModelMap model) {
		//Setting setting = SettingUtils.get();
		System.out.println("How are you!");
		return "/shop/consultation/addMessage";
	}
	
	@RequestMapping(value = "/saveMessage", method = RequestMethod.POST)
	public @ResponseBody
	Message saveMessage(Consultation consultation, String accessoryX ,Long id, HttpServletRequest request) {
		if(consultation == null){
		}
		if(accessoryX != null){
			consultation.setAccessory(accessoryX);
		}
		System.out.println("--22--1:"+consultation.getTheme());
		System.out.println("--22--2:"+consultation.getContent());
		System.out.println("--22--3:"+consultation.getAccessory());
		System.out.println("--22--4:"+consultation.getConsultationType());
		if(id == null){
			
		}
		Setting setting = SettingUtils.get();
		if (!setting.getIsConsultationEnabled()) {
			return Message.error("shop.consultation.disabled");
		}
		Member member = memberService.getCurrent();
		if (setting.getConsultationAuthority() != ConsultationAuthority.anyone && member == null) {
			return Message.error("shop.consultation.accessDenied");
		}
		Product product = productService.find(id);
		if (product == null) {
			return ERROR_MESSAGE;
		}
		Date date = new Date();
		consultation.setCreateDate(date);
		consultation.setModifyDate(date);
		String ip = request.getRemoteAddr();
		consultation.setIp(ip);
		consultation.setMember(member);
		consultation.setProduct(product);
		if (setting.getIsConsultationCheck()) {
			consultation.setIsShow(false);
			//consultationService.saveX(consultation);
			consultationService.save(consultation);
			System.out.println("--11--");   
			return Message.success("shop.consultation.check");
		} else {
			consultation.setIsShow(true);
			//consultationService.saveX(consultation);
			consultationService.save(consultation);
			return Message.success("shop.consultation.success");
		}
	}
	
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