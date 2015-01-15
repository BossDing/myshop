package net.shopxx.controller.mobile.member;

import javax.annotation.Resource;

import net.shopxx.entity.Member;
import net.shopxx.service.DeliveryCenterService;
import net.shopxx.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * (中山公用)
 * 文件名称: PickUpAdressController
 * 系统名称: shopxx
 * 模块名称: 提货点
 * 创建人: Chengandou
 * 创建日期: 2014-07-22
 */
@Controller("mobileDeliveryCentersController")
@RequestMapping("/mobile/member/delivery")
public class DeliveryCentersController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "deliveryCenterServiceImpl")
	private DeliveryCenterService deliverycenterservice;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String toPickUpAdressPage(ModelMap model) {
		Member member = memberService.getCurrent();
		model.addAttribute("member", member);
		model.addAttribute("deliverycenters", deliverycenterservice.findAll());
		return "mobile/member/delivery/list";
	}
}
