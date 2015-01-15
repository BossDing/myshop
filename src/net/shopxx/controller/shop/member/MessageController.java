/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop.member;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.entity.Message;
import net.shopxx.service.MemberService;
import net.shopxx.service.MessageService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员中心 - 消息
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopMemberMessageController")
@RequestMapping("/member/message")
public class MessageController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "messageServiceImpl")
	MessageService messageService;
	@Resource(name = "memberServiceImpl")
	MemberService memberService;

	/**
	 * 检查用户名是否合法
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		System.out.println("校验用户名");
		if (!StringUtils.equalsIgnoreCase(username, memberService.getCurrentUsername()) && memberService.usernameExists(username)) {
			System.out.println("返回true");
			return true;
		}
		System.out.println("返回false");
		return false;
	}

	/**
	 * 发送
	 */
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public String send(Long draftMessageId, Model model) {
		Message draftMessage = messageService.find(draftMessageId);
		if (draftMessage != null && draftMessage.getIsDraft() && memberService.getCurrent().equals(draftMessage.getSender())) {
			model.addAttribute("draftMessage", draftMessage);
		}
		return "/shop/member/message/send";
	}

	/**
	 * 发送
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String send(String username, String title, String content, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//		if (!isValid(Message.class, "content", content)) {
//			return ERROR_VIEW;
//		}
		Member member = memberService.getCurrent();
	//	Message draftMessage = messageService.find(draftMessageId);
//		if (draftMessage != null && draftMessage.getIsDraft() && member.equals(draftMessage.getSender())) {
//			messageService.delete(draftMessage);
//		}
		Member receiver = null;
		if (StringUtils.isNotEmpty(username)) {
			receiver = memberService.findByUsername(username);
			/*
			if (member.equals(receiver)) {
				return ERROR_VIEW;
			}
			*/
		}
		Message message = new Message();
		message.setTitle(title);
		message.setContent(content);
		message.setIp(request.getRemoteAddr());
		message.setIsDraft(false);
		message.setSenderRead(true);
		message.setReceiverRead(false);
		message.setSenderDelete(false);
		message.setReceiverDelete(false);
		message.setSender(member);
		message.setReceiver(receiver);
		try {
			messageService.save(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (message.getIsDraft()) {
			addFlashMessage(redirectAttributes, net.shopxx.Message.success("shop.member.message.saveDraftSuccess"));
			return "redirect:draft.jhtml";
		} else {
			addFlashMessage(redirectAttributes, net.shopxx.Message.success("shop.member.message.sendSuccess"));
			return "redirect:list.jhtml";
		}
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, Model model) {
		Message message = messageService.find(id);
		if (message == null || message.getIsDraft() || message.getForMessage() != null) {
			return ERROR_VIEW;
		}
		Member member = memberService.getCurrent();
		if ((!member.equals(message.getSender()) && !member.equals(message.getReceiver())) || (member.equals(message.getReceiver()) && message.getReceiverDelete()) || (member.equals(message.getSender()) && message.getSenderDelete())) {
			return ERROR_VIEW;
		}
		if (member.equals(message.getReceiver())) {
			message.setReceiverRead(true);
		} else {
			message.setSenderRead(true);
		}
		messageService.update(message);
		model.addAttribute("memberMessage", message);
		return "/shop/member/message/view";
	}

	/**
	 * 回复
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(Long id, String content, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!isValid(Message.class, "content", content)) {
			return ERROR_VIEW;
		}
		Message forMessage = messageService.find(id);
		if (forMessage == null || forMessage.getIsDraft() || forMessage.getForMessage() != null) {
			return ERROR_VIEW;
		}
		Member member = memberService.getCurrent();
		if ((!member.equals(forMessage.getSender()) && !member.equals(forMessage.getReceiver())) || (member.equals(forMessage.getReceiver()) && forMessage.getReceiverDelete()) || (member.equals(forMessage.getSender()) && forMessage.getSenderDelete())) {
			return ERROR_VIEW;
		}
		Message message = new Message();
		message.setTitle("reply: " + forMessage.getTitle());
		message.setContent(content);
		message.setIp(request.getRemoteAddr());
		message.setIsDraft(false);
		message.setSenderRead(true);
		message.setReceiverRead(false);
		message.setSenderDelete(false);
		message.setReceiverDelete(false);
		message.setSender(member);
		message.setReceiver(member.equals(forMessage.getReceiver()) ? forMessage.getSender() : forMessage.getReceiver());
		if ((member.equals(forMessage.getReceiver()) && !forMessage.getSenderDelete()) || (member.equals(forMessage.getSender()) && !forMessage.getReceiverDelete())) {
			message.setForMessage(forMessage);
		}
		messageService.save(message);

		if (member.equals(forMessage.getSender())) {
			forMessage.setSenderRead(true);
			forMessage.setReceiverRead(false);
		} else {
			forMessage.setSenderRead(false);
			forMessage.setReceiverRead(true);
		}
		messageService.update(forMessage);

		if ((member.equals(forMessage.getReceiver()) && !forMessage.getSenderDelete()) || (member.equals(forMessage.getSender()) && !forMessage.getReceiverDelete())) {
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
			return "redirect:view.jhtml?id=" + forMessage.getId();
		} else {
			addFlashMessage(redirectAttributes, net.shopxx.Message.success("shop.member.message.replySuccess"));
			return "redirect:list.jhtml";
		}
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Integer pageNumber, Pageable pageable ,Model model) {
//		int page_size =3;
//		Pageable pageable = new Pageable(pageNumber, page_size);
		Member member = memberService.getCurrent();
		model.addAttribute("receiverPage", messageService.findReceiverPage(member, pageable));
		model.addAttribute("senderPage", messageService.findSenderPage(member, pageable));
		return "/shop/member/message/list";
	}

	/**
	 * 草稿箱
	 */
	@RequestMapping(value = "/draft", method = RequestMethod.GET)
	public String draft(Integer pageNumber, Model model) {
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		Member member = memberService.getCurrent();
		model.addAttribute("page", messageService.findDraftPage(member, pageable));
		return "/shop/member/message/draft";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody
	net.shopxx.Message delete(Long[] box1 ,Pageable pageable ,Long[] box2) {
		Member member = memberService.getCurrent();
		Long[] ids = new Long[]{};
		if(box1 != null){
			if(box1.length > 0){
			ids = box1;
			messageService.removeX(ids, member ,"inbox");
			}
		}
		if(box2 != null){
			if(box2.length>0){
			ids = box2;
			messageService.removeX(ids, member,"outbox");
			}
		}
		int data = 0;
		if(box1 != null){
			if(box1.length > 0){
			data = messageService.findReceiverPage(member, pageable).getContent().size();
			}
		}
		if(box2 != null){
			if(box2.length>0){
				data = messageService.findSenderPage(member, pageable).getContent().size();
			}
		}
		return net.shopxx.Message.success("成功删除"+ids.length+"条记录", data);
	}

}