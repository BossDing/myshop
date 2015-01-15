package net.shopxx.controller.shop;

import javax.annotation.Resource;

import net.shopxx.Pageable;
import net.shopxx.service.SiteServiceService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("siteServiceController")
@RequestMapping("/service")
public class SiteServiceController extends BaseController{

	@Resource(name="siteServiceServiceImpl")
	  private SiteServiceService siteServiceService;
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String city,Pageable pageable, ModelMap model) {
		model.addAttribute("page", siteServiceService.findPage(city, pageable));
		return "/shop/service/list";
	}
	
	
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String bbb() {
		return "/shop/include/map";
	}
}
