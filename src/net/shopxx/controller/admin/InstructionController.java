package net.shopxx.controller.admin;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Instruction;
import net.shopxx.service.InstructionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("adminInstructionController")
@RequestMapping("admin/instruction")
public class InstructionController extends BaseController {
	
	@Resource(name = "instructionServiceImpl")
	private InstructionService instructionService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model){
		try {
			model.addAttribute("page", instructionService.findPage(pageable));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/admin/instruction/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(){
		return "/admin/instruction/add";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Instruction instruction, RedirectAttributes redirectAttributes){
		System.out.println("instruction---save");
		instructionService.save(instruction);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model){
		model.addAttribute("instruction", instructionService.find(id));
		return "/admin/instruction/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Instruction instruction, RedirectAttributes redirectAttributes){
		System.out.println("instruction---update");
		instructionService.update(instruction);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids){
		instructionService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}
