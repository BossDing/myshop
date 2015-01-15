package net.shopxx.controller.admin;

import java.util.Date;
import java.util.HashSet;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Article;
import net.shopxx.entity.Notice;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Tag.Type;
import net.shopxx.service.NoticeService;

@Controller("adminNoticeController")
@RequestMapping("/admin/notice")
public class NoticeController extends BaseController {
	@Resource(name="noticeServiceImpl")
	private NoticeService noticeService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)  
	public String list(Pageable pageable ,ModelMap model){
		model.addAttribute("page", noticeService.findPage(pageable));
		return "admin/notice/list";
	}  
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)  
	public String add(ModelMap model){
		return "admin/notice/add";
	}  
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)  
	public String save(Notice notice ,boolean isPublication ,RedirectAttributes redirectAttributes){
		if(notice == null){
			return ERROR_VIEW;
		}
		if(isPublication == true){
			notice.setNoticeDate(new Date());
		}
		try {
			noticeService.save(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("notice", noticeService.find(id));
		return "/admin/notice/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Notice notice, boolean isPublication,RedirectAttributes redirectAttributes) {
		if (!isValid(notice)) {
			return ERROR_VIEW;
		}
		if(isPublication == true){
			notice.setIsPublication(isPublication);
		if(notice.getNoticeDate() == null){
			notice.setNoticeDate(new Date());
		}
		}
		noticeService.update(notice);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		noticeService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}

