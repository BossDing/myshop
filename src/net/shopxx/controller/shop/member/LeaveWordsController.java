package net.shopxx.controller.shop.member;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Setting;
import net.shopxx.Setting.ConsultationAuthority;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Consultation;
import net.shopxx.entity.LeaveWords;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.LeaveWordsService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * Controller - 买家留言
 * 
 * @author xx
 * @version 1.0   
 */
@Controller("shopLeaveWordsController")
@RequestMapping("/member/leaveWords")
public class LeaveWordsController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "leaveWordsServiceImpl")
	private LeaveWordsService leaveWordsService;
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addConsultation(ModelMap model) {
		//Setting setting = SettingUtils.get();
		return "/shop/member/leaveWords/add";
	}
	
	/**
	 *保存
	 */
	@RequestMapping(value = "/saveXX", method = RequestMethod.POST)
	public @ResponseBody
	Message saveXX(LeaveWords leaveWords, HttpServletRequest request) {
		Member member = memberService.getCurrent();
		leaveWords.setMember(member);
		leaveWords.setEntcode(WebUtils.getXentcode());
		leaveWordsService.save(leaveWords);
		return Message.success("shop.consultation.success");
	}
	
	/**
	 *保存
	 */
	@RequestMapping(value = "/saveX", method = RequestMethod.POST)
	public @ResponseBody
	Message saveX(String theme ,String accessory ,String consultationType ,Long id, String content, HttpServletRequest request) {
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
		LeaveWords leaveWords = new LeaveWords();
		leaveWords.setContent(content);
		leaveWords.setTheme(theme);
		leaveWords.setAccessory(accessory);
		leaveWords.setConsultationType(consultationType);
		leaveWords.setMember(member);
		leaveWordsService.save(leaveWords);
		return Message.success("shop.consultation.success");
		/**
		if (setting.getIsConsultationCheck()) {
			System.out.println("--ee2--");
			consultation.setIsShow(false);
			System.out.println("--ee3--");
				leaveWordsService.save(consultation);
			System.out.println("--ee4--");
			return Message.success("shop.consultation.check");
		} else {
			System.out.println("--ee5--");
			consultation.setIsShow(true);
			System.out.println("--ee7--");
			leaveWordsService.save(consultation);
			System.out.println("--ee8--");
			return Message.success("shop.consultation.success");
		}
		*/
	}
}
