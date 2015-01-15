package net.shopxx.controller.shop;

import net.shopxx.entity.ActionType;
import net.shopxx.entity.Member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 商品
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopBannerController")
@RequestMapping("/banner")
public class BannerController extends BaseController {
	
	@RequestMapping(value = "/content",method = RequestMethod.GET)
	public String content(ModelMap model) {
		return "/shop/banner/content";
	}
}
