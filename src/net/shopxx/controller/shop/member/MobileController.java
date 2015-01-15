package net.shopxx.controller.shop.member;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.entity.SafeKey;
import net.shopxx.service.MailService;
import net.shopxx.service.MemberService;
import net.shopxx.util.SettingUtils;
import net.shopxx.Message;
import net.shopxx.Setting;

/**
 * Controller - 修改手机
 * 
 * @author hfh
 * @version 1.0
 */
@Controller("shopMemberMobileController")
@RequestMapping("/member/mobile")
public class MobileController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	
	
	/**
	 * 修改手机
	 * @param model
	 * @returnStringhfh
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public String updateMobile(Model model) {
		Member member = memberService.getCurrent();
		model.addAttribute("member", member);
		return "shop/member/mobile/edit";
	}
	
	/**
	 * 修改手机-发送手机验证码
	 * @param mobile
	 * @param code
	 * @param request
	 * @param model
	 * @returnMessagehfh
	 */
	@RequestMapping(value = "/sendCode", method = RequestMethod.GET)
	public @ResponseBody
	Message sendCode(String mobile,String code, HttpServletRequest request,Model model) {
//		Message draftMessage = messageService.find(draftMessageId);
//		if (draftMessage != null && draftMessage.getIsDraft() && memberService.getCurrent().equals(draftMessage.getSender())) {
//			model.addAttribute("draftMessage", draftMessage);
//		}
		System.out.println("====="+code+"++++");
		request.getSession().setAttribute("vCode", code);
		List<Member> members = memberService.findListByMobile(mobile);
		Member member = members.get(0);
		System.out.println(members.size());
		System.out.println(members.get(0).getUsername());
		System.out.println("a");
//		mailService.sendUpdateEMail(member.getMobile(), member.getUsername(), safeKey);
		System.out.println("ab");
		return Message.success("shop.password.mailSuccess");
	}
	
	/**
	 * 绑定新手机-发送手机验证码
	 * @param mobile
	 * @param newcode
	 * @param request
	 * @param model
	 * @returnMessagehfh
	 */
	@RequestMapping(value = "/sendNewCode", method = RequestMethod.GET)
	public @ResponseBody
	Message sendNewCode(String mobile,String newcode, HttpServletRequest request,Model model) {
//		Message draftMessage = messageService.find(draftMessageId);
//		if (draftMessage != null && draftMessage.getIsDraft() && memberService.getCurrent().equals(draftMessage.getSender())) {
//			model.addAttribute("draftMessage", draftMessage);
//		}
		System.out.println("====="+newcode+"++++");
		request.getSession().setAttribute("newcode", newcode);
		List<Member> members = memberService.findListByMobile(mobile);
		Member member = members.get(0);
		System.out.println(members.size());
		System.out.println(members.get(0).getUsername());
		System.out.println("a");
//		mailService.sendUpdateEMail(member.getMobile(), member.getUsername(), safeKey);
		System.out.println("ab");
		return Message.success("shop.password.mailSuccess");
	}
	
	
	/**
	 * 校验修改手机验证码
	 * @param mobile
	 * @param usercode
	 * @param request
	 * @param model
	 * @returnbooleanhfh
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkCode(String mobile,String usercode,HttpServletRequest request, Model model) {
		String rightvCode = (String) request.getSession().getAttribute("vCode");
		if(usercode.equals(rightvCode)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 校验绑定新手机验证码
	 * @param mobile
	 * @param usercode
	 * @param request
	 * @param model
	 * @returnbooleanhfh
	 */
	@RequestMapping(value = "/checkNewCode", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkNewCode(String mobile,String usercode,HttpServletRequest request, Model model) {
		String newcode = (String) request.getSession().getAttribute("newcode");
		if(usercode.equals(newcode)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 绑定新手机
	 * @param mobile
	 * @param newmobile
	 * @param request
	 * @param modelvoidhfh
	 */
	@RequestMapping(value = "/saveNewMobile", method = RequestMethod.GET)
	public @ResponseBody
	void saveNewMobile(String mobile,String newmobile,HttpServletRequest request, Model model) {
		List<Member> members = memberService.findListByMobile(mobile);
		Member member = members.get(0);
		member.setMobile(newmobile);
		memberService.update(member);
	}
	
	
	/**
	 * 检查mobile是否存在
	 */
	@RequestMapping(value = "/check_mobile", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		if (memberService.mobileExists(mobile)) {
			return false;
		} else {
			return true;
		}
	}
	
}
