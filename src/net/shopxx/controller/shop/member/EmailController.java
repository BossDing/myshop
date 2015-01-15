package net.shopxx.controller.shop.member;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

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
 * Controller - 邮箱
 * 
 * @author hfh
 * @version 1.0
 */
@Controller("shopMemberEmailController")
@RequestMapping("/member/email")
public class EmailController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	
	
	/**
	 * 修改邮箱
	 * @returnStringhfh
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public String updateEmail(Model model) {
		Member member = memberService.getCurrent();
		model.addAttribute("member", member);
		return "shop/member/email/edit";
	}
	
	/**
	 * 发送邮箱验证码
	 */
	@RequestMapping(value = "/sendCheckCode", method = RequestMethod.GET)
	public @ResponseBody
	Message sendCheckCode(String email, Model model) {
//		Message draftMessage = messageService.find(draftMessageId);
//		if (draftMessage != null && draftMessage.getIsDraft() && memberService.getCurrent().equals(draftMessage.getSender())) {
//			model.addAttribute("draftMessage", draftMessage);
//		}
		System.out.println(email);
		List<Member> members = memberService.findListByEmail(email);
		Member member = members.get(0);
		System.out.println(members.size());
		System.out.println(members.get(0).getUsername());
		Setting setting = SettingUtils.get();
		SafeKey safeKey = new SafeKey();
		safeKey.setValue(UUID.randomUUID().toString() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
		safeKey.setExpire(setting.getSafeKeyExpiryTime() != 0 ? DateUtils.addMinutes(new Date(), setting.getSafeKeyExpiryTime()) : null);
		member.setSafeKey(safeKey);
		System.out.println("safeKey===="+safeKey);
		System.out.println("a");
		memberService.update(member);
		mailService.sendUpdateEMail(member.getEmail(), member.getUsername(), safeKey);
		System.out.println("ab");
		return Message.success("shop.password.mailSuccess");
	}
	
	
	/**
	 * 检查E-mail是否存在
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkEmail(String email) {
		System.out.println("newEmail="+email);
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		if (memberService.emailExists(email)) {
			return false;
		} else {
			return true;
		}
	}
}
