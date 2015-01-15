package net.shopxx.controller.shop;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.entity.Experience;
import net.shopxx.entity.ExperienceApply;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.AreaService;
import net.shopxx.service.ExperienceApplyService;
import net.shopxx.service.ExperienceService;
import net.shopxx.service.FileService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller - 体验中心
 *         
 * @author DongXinXing
 * @version 1.0
 */
@Controller("shopExperienceApplyController")
@RequestMapping("/experience_apply")
public class ExperienceApplyController extends BaseController {

	@Resource(name = "experienceApplyServiceImpl")
	private ExperienceApplyService experienceApplyService;
	@Resource(name = "experienceServiceImpl")
	private ExperienceService experienceService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(ExperienceApply experienceApply,Long experienceId,RedirectAttributes redirectAttributes) {
	
		experienceApply.setExperiences(experienceService.find(experienceId));
		experienceApply.setAddress(experienceService.find(experienceId).getAddress());
		experienceApply.setName(experienceService.find(experienceId).getName());
		experienceApplyService.save(experienceApply);
	
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
	

		return Message.success("success");
	}    

}