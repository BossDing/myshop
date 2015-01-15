package net.shopxx.controller.dp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.shopxx.entity.Store;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * Controller - 店铺首页
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("DPIndexController")
@RequestMapping("/")
public class IndexController {
	
	@Resource(name = "storeServiceImpl")
	private StoreService storeService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String index(@PathVariable Long id, ModelMap model, Long productCategoryId, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Store store = storeService.find(id);// 根据店铺id，获取店铺信息
		if(store == null || !store.getIsEnabled()) {
			return "redirect:store/list.jhtml";
		}
		model.addAttribute("productCategories", productCategoryService.findChildrenForStoreForFront(null, null, store));// 商城及该店铺的分类
		model.addAttribute("store", store);
		return "dp/index";
	}
}
