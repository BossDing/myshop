/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.mobile.member;

import javax.annotation.Resource;
import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.entity.Receiver;
import net.shopxx.service.AreaService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ReceiverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员中心 - 收货地址
 * 创建日期：2014-05-08
 * @author chengandou
 * @version 3.0
 */
@Controller("mobileMemberReceiverController")
@RequestMapping("/mobile/member/receiver")
public class ReceiverController extends BaseController {
	
	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNumber, ModelMap model, Integer istocart) {
		Member member = memberService.getCurrent();
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("page", receiverService.findPage(member, pageable));
		return "mobile/member/receiver/list";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(RedirectAttributes redirectAttributes) {
		Member member = memberService.getCurrent();
		if (Receiver.MAX_RECEIVER_COUNT != null && member.getReceivers().size() >= Receiver.MAX_RECEIVER_COUNT) {
			addFlashMessage(redirectAttributes, Message.warn("shop.member.receiver.addCountNotAllowed", Receiver.MAX_RECEIVER_COUNT));
			return "redirect:list.jhtml";
		}
		return "mobile/member/receiver/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(Receiver receiver, Long areaId) {
		receiver.setArea(areaService.find(areaId));
		if (receiver.getConsignee() == null) {
			return Message.warn("收货人输入有误！");
		}else if(areaId == null){
			return Message.warn("地区输入有误！");
		}else if(receiver.getAddress() == null){
			return Message.warn("地址输入有误！");
		}else if(receiver.getZipCode() == null){
			return Message.warn("邮编输入有误！");
		}else if(receiver.getPhone() == null){
			return Message.warn("电话输入有误！");
		}
		Member member = memberService.getCurrent();
		if (Receiver.MAX_RECEIVER_COUNT != null && member.getReceivers().size() >= Receiver.MAX_RECEIVER_COUNT) {
			return Message.warn("shop.member.receiver.addCountNotAllowed", Receiver.MAX_RECEIVER_COUNT);
		}
		receiver.setMember(member);
		receiverService.save(receiver);
		return Message.success("操作成功！");
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model, RedirectAttributes redirectAttributes) {
		Receiver receiver = receiverService.find(id);
		if (receiver == null) {
			addFlashMessage(redirectAttributes, Message.warn("收货地址不能为空！"));
			return "mobile/member/receiver/edit";
		}
		Member member = memberService.getCurrent();
		if (!member.equals(receiver.getMember())) {
			addFlashMessage(redirectAttributes, Message.warn("收货人不匹配！"));
			return "mobile/member/receiver/edit";
		}
		model.addAttribute("receiver", receiver);
		return "mobile/member/receiver/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Receiver receiver, Long id, Long areaId) {
		receiver.setArea(areaService.find(areaId));
		if (!isValid(receiver)) {
			return ERROR_MESSAGE;
		}
		Receiver pReceiver = receiverService.find(id);
		if (pReceiver == null) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (!member.equals(pReceiver.getMember())) {
			return ERROR_MESSAGE;
		}
		receiverService.update(receiver, "member");
		return SUCCESS_MESSAGE;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		Receiver receiver = receiverService.find(id);
		if (receiver == null) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (!member.equals(receiver.getMember())) {
			return ERROR_MESSAGE;
		}
		receiverService.delete(id);
		return SUCCESS_MESSAGE;
	}

}