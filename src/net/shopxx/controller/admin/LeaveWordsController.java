package net.shopxx.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Consultation;
import net.shopxx.entity.LeaveWords;
import net.shopxx.service.LeaveWordsService;
import net.shopxx.util.Mail;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("adminLeaveWordsController")
@RequestMapping("/admin/leaveWords")
public class LeaveWordsController extends BaseController {
	@Resource(name = "leaveWordsServiceImpl")
	private LeaveWordsService leaveWordsService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long label ,ModelMap model ,Pageable pageable) {
//		System.out.println("进入leaveWordsController----list");
//		System.out.println("label:"+label);
		try {
			model.addAttribute("page", leaveWordsService.findPage(label ,pageable));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/admin/leaveWords/list";
	}
	@RequestMapping(value = "/listjion", method = RequestMethod.GET)
	public String listjion(Long label ,ModelMap model ,Pageable pageable) {
//		System.out.println("进入leaveWordsController----list2");
//		System.out.println("label:"+label);
		model.addAttribute("page", leaveWordsService.findPage(label ,pageable));
		return "/admin/leaveWords/listjoin";
	}
	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id ,ModelMap model) {
		//Setting setting = SettingUtils.get();
		LeaveWords leaveWords = leaveWordsService.find(id);
		leaveWords.setIsRead(true);
		leaveWordsService.update(leaveWords);
		model.addAttribute("leaveWords", leaveWords);
		return "/admin/leaveWords/view";
	}
	/**
	 * 查看			  
	 */
	@RequestMapping(value = "/viewjoin", method = RequestMethod.GET)
	public String viewjoin(Long id ,ModelMap model) {
		//Setting setting = SettingUtils.get();
		LeaveWords leaveWords = leaveWordsService.find(id);
		leaveWords.setIsRead(true);
		leaveWordsService.update(leaveWords);
		model.addAttribute("leaveWords", leaveWords);
		return "/admin/leaveWords/viewjoin";
	}    
	
	/**
	 * 回复
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(Long id ,ModelMap model) {
		//Setting setting = SettingUtils.get();
		LeaveWords leaveWords = leaveWordsService.find(id);
		leaveWords.setIsRead(true);
		leaveWordsService.update(leaveWords);
		model.addAttribute("leaveWords", leaveWords);
		return "/admin/leaveWords/reply";
	}
	
	/**
	 * 回复
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public @ResponseBody Message reply(Long id, String content, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//		System.out.println("进入reply--post");
//		System.out.println("content:"+content);
//		System.out.println("email:"+email);
		/*if (!isValid(Consultation.class, "content", content)) {  
			return ERROR_VIEW;
		}*/
		LeaveWords leaveWords = leaveWordsService.find(id);
//		System.out.println("---"+leaveWords);
		/*if (leaveWords == null) {
			return ERROR_VIEW;
		}*/
//		System.out.println("emai:"+leaveWords.getEmail());
		
		String smtp = "smtp.163.com";// smtp服务器
		String from = "macroTest@163.com";// 邮件显示名称
		String to = leaveWords.getEmail();// 收件人的邮件地址，必须是真实地址
		String copyto = "";// 抄送人邮件地址
		String subject = "万家乐官方网站留言回复";// 邮件标题
//		String content = "你好！";// 邮件内容
		String username = "macroTest";// 发件人真实的账户名
//		String username = "万家乐官方网站留言回复";// 发件人真实的账户名
		String password = "gztw10086";// 发件人密码
		Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password);
		
		
		LeaveWords replyLeaveWords = new LeaveWords();
		replyLeaveWords.setContent(content);
		replyLeaveWords.setForLeaveWords(leaveWords);
		leaveWordsService.save(replyLeaveWords);
		return Message.success("已将回复内容成功发送到对方邮箱！");
		/*addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:reply.jhtml?id=" + id;*/
	}
	
	/**
	 * 删除  
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
//		System.out.println("-----leaveWordsService----del----------");
		leaveWordsService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteReply", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
//		System.out.println("-----leaveWordsService----deleteReply----------");
//		System.out.println("id:"+id);
		leaveWordsService.delete(id);
		return SUCCESS_MESSAGE;
	}
}
