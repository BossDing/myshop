package net.shopxx.controller.admin;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.FileInfo.FileType;
import net.shopxx.entity.Experience;
import net.shopxx.entity.ExperienceApply;
import net.shopxx.entity.ProductImage;
import net.shopxx.service.AreaService;
import net.shopxx.service.ExperienceApplyService;
import net.shopxx.service.ExperienceService;
import net.shopxx.service.FileService;
import net.shopxx.service.ProductImageService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 体验申请中心
 *      
 * @author LiuFeng
 * @version 1.0
 */
@Controller("adminExperienceApplyController")
@RequestMapping("/admin/experience_apply")
public class ExperienceApplyController extends BaseController {


	@Resource(name = "experienceApplyServiceImpl")
	private ExperienceApplyService experienceApplyService;
	@Resource(name = "experienceServiceImpl")
	private ExperienceService experienceService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {   
		model.addAttribute("experienceApply", experienceApplyService.findList());
		return "/admin/experience_apply/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ExperienceApply experienceApply, Long experienceId,RedirectAttributes redirectAttributes) {
		experienceApply.setExperiences(experienceService.find(experienceId));
		
		if (!isValid(experienceApply)) {  
			return ERROR_VIEW;
		}
		experienceApplyService.save(experienceApply);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		System.out.print("保存");
		return "redirect:list.jhtml";
	}      
  
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("experienceApply", experienceApplyService.find(id));
		System.out.print("编辑");
		return "/admin/experience_apply/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ExperienceApply experienceApply,Long experienceId,RedirectAttributes redirectAttributes) {
		experienceApply.setExperiences(experienceService.find(experienceId));
		experienceApplyService.update(experienceApply);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**   
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		System.out.print("1");
		model.addAttribute("page", experienceApplyService.findPage(pageable));
		System.out.print("2");
		return "/admin/experience_apply/list";
	}      
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		experienceApplyService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}