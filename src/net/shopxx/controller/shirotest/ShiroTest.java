package net.shopxx.controller.shirotest;

import net.shopxx.controller.admin.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shiro")
public class ShiroTest  extends BaseController{
	
	@RequestMapping("/listShiro")
	public String shiroList(){
		System.out.println("shiroList");
		return "/shiro/list";
	}
	@RequestMapping("/addShiro")
	public String shiroAdd(){
		System.out.println("shiroAdd");
		return "/shiro/add";
	}
	@RequestMapping("/updateShiro")
	public String shiroUpdate(){
		System.out.println("shiroUpdate");
		return "/shiro/update";
	}
	@RequestMapping("/deleteShiro")
	public String shiroDelete(){
		System.out.println("shiroUpdate");
		return "/shiro/update";
	}
	
	
}
