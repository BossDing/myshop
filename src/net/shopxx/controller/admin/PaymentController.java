/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Payment;
import net.shopxx.entity.Store;
import net.shopxx.service.PaymentService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 收款单
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminPaymentController")
@RequestMapping("/admin/payment")
public class PaymentController extends BaseController {

	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		Payment payment = paymentService.find(id);
		Store store = WebUtils.getStore();
		if (store != null) {
			if (!store.getId().equals(payment.getStore().getId())) { //店铺管理员只能查自家的收款单
				payment = null;
			}
		}
		model.addAttribute("payment", payment);
		return "/admin/payment/view";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", paymentService.findPage(pageable));
		return "/admin/payment/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		Store store = WebUtils.getStore();
		List<Long> ids2 = new ArrayList<Long>();
		if (ids != null) {
			for (Long id : ids) {
				Payment payment = paymentService.find(id);
				if (payment != null && payment.getExpire() != null && !payment.hasExpired()) {
					return Message.error("admin.payment.deleteUnexpiredNotAllowed");
				}
				if (store != null) {
					if (store.getId().equals(payment.getStore().getId())) { //店铺管理员只能删除自家的收款单
						ids2.add(id);
					}
				}
			}
			if (store != null)
				paymentService.delete((Long[]) ids2.toArray());
			else
				paymentService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}