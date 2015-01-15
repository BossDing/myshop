package net.shopxx.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Extension;
import net.shopxx.entity.GroupPurchase;
import net.shopxx.entity.LeaveWords;
import net.shopxx.entity.Member;
import net.shopxx.service.ExtensionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("adminExtensionController")
@RequestMapping("/admin/extension")
public class ExtensionController extends BaseController {
	
	@Resource(name = "extensionServiceImpl")
	private ExtensionService extensionService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model){
		model.addAttribute("page", extensionService.findPage(pageable));
		return "/admin/extension/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/extension/add";
	}
	
	/**
	 * 验证服务卡号是否已存在
	 */
	@RequestMapping(value = "/checkCardNo", method = RequestMethod.POST)
	public @ResponseBody
	Message checkCardNo(Extension extension, HttpServletRequest request) {
		if(extension.getCardNo()==null){
			return Message.warn("请填写服务卡号");
		}
		Extension extension1 = extensionService.findByMember(extension.getCardNo());
		if(extension1 != null){
			return Message.warn("此服务卡号已存在");
		}
		return Message.success("服务卡号可以使用");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Extension extension, RedirectAttributes redirectAttributes){
		try {
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String no = sf.format(date);
			extension.setSn(no);
			System.out.println(extension.getSn());
			extensionService.save(extension);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("extension", extensionService.find(id));
		return "/admin/extension/edit"; 
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Extension extension,RedirectAttributes redirectAttributes) {
		try {
			extensionService.update(extension);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		extensionService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}
