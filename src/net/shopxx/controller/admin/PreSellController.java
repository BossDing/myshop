package net.shopxx.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.MemberRank;
import net.shopxx.entity.Product;
import net.shopxx.entity.PreSellRole;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.PreSellService;
import net.shopxx.service.ProductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 预售
 *      
 * @author WeiHuaLin
 * @version 1.0
 */
@Controller("preSellController")
@RequestMapping("/admin/presell")
public class PreSellController extends BaseController{
	@Resource(name = "preSellServiceImpl")
	private PreSellService preSellService;  
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 商品选择
	 */
	@RequestMapping(value = "/product_select", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> productSelect(String q) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<Product> products = productService.search(q, false, 20);
			for (Product product : products) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", product.getId());
				map.put("sn", product.getSn());
				map.put("fullName", product.getFullName());
				map.put("path", product.getPath());
				data.add(map);
			}
		}
		return data;
	}


	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("memberRanks", memberRankService.findAll());
		return "/admin/presell/add";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", preSellService.findPage(pageable));
		return "/admin/presell/list";    
	}      
	
	/**
	 * 预售申请管理
	 */
	@RequestMapping(value = "/applyList", method = RequestMethod.GET)
	public String applyList(Pageable pageable, ModelMap model) {
		model.addAttribute("page", preSellService.findPage(pageable));
		return "/admin/presell/applyList";    
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(PreSellRole preSellRole, Long[] memberRankIds, Long[] productCategoryIds, Long[] brandIds, Long[] couponIds, Long[] productIds, RedirectAttributes redirectAttributes) {
		preSellRole.setMemberRanks(new HashSet<MemberRank>(memberRankService.findList(memberRankIds)));
		for (Product product : productService.findList(productIds)) {
			if (!product.getIsGift()) {
				preSellRole.getProducts().add(product);
			}
		}
		if (!isValid(preSellRole)) {
			return ERROR_VIEW;
		}
		if (preSellRole.getBeginDate() != null && preSellRole.getEndDate() != null && preSellRole.getBeginDate().after(preSellRole.getEndDate())) {
			return ERROR_VIEW;
		}
		preSellService.save(preSellRole);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}      
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		preSellService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		System.out.println(preSellService.find(id).getName());
		model.addAttribute("preSellRole", preSellService.find(id));
		model.addAttribute("memberRanks", memberRankService.findAll());
		return "/admin/presell/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(PreSellRole preSellRole, Long[] memberRankIds, Long[] productCategoryIds, Long[] brandIds, Long[] couponIds, Long[] productIds, RedirectAttributes redirectAttributes) {
		preSellRole.setMemberRanks(new HashSet<MemberRank>(memberRankService.findList(memberRankIds)));
		for (Product product : productService.findList(productIds)) {
			if (!product.getIsGift()) {
				preSellRole.getProducts().add(product);
			}
		}
		if (preSellRole.getBeginDate() != null && preSellRole.getEndDate() != null && preSellRole.getBeginDate().after(preSellRole.getEndDate())) {
			return ERROR_VIEW;
		}
		System.out.println(preSellRole.getName());
		preSellService.update(preSellRole);
		System.out.println("=====================");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
}
