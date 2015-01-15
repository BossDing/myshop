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
import net.shopxx.entity.Trial;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.ProductService;
import net.shopxx.service.TrialService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 试用
 *      
 * @author DongXinXing
 * @version 1.0
 */
@Controller("adminTrialController")
@RequestMapping("/admin/trial")
public class TrialController extends BaseController {

	@Resource(name = "trialServiceImpl")
	private TrialService trialService;  
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
		String reg = "[a-zA-Z]";
		boolean isCract = q.matches(reg);
		String Q = q;
		if(isCract){  
			Q = q.toUpperCase();   
		}   
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(Q)) {
			List<Product> products = productService.search(Q, false, 20);
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
		return "/admin/trial/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Trial trial, Long[] memberRankIds, Long[] productCategoryIds, Long[] brandIds, Long[] couponIds, Long[] productIds, RedirectAttributes redirectAttributes) {
		trial.setMemberRanks(new HashSet<MemberRank>(memberRankService.findList(memberRankIds)));
		for (Product product : productService.findList(productIds)) {
			if (!product.getIsGift()) {
				trial.getProducts().add(product);
			}
		}
		if (!isValid(trial)) {
			return ERROR_VIEW;
		}
		if (trial.getBeginDate() != null && trial.getEndDate() != null && trial.getBeginDate().after(trial.getEndDate())) {
			return ERROR_VIEW;
		}
		trialService.save(trial);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}      
  
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("trial", trialService.find(id));
		model.addAttribute("memberRanks", memberRankService.findAll());
		return "/admin/trial/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Trial trial, Long[] memberRankIds, Long[] productCategoryIds, Long[] brandIds, Long[] couponIds, Long[] productIds, RedirectAttributes redirectAttributes) {
		trial.setMemberRanks(new HashSet<MemberRank>(memberRankService.findList(memberRankIds)));
		for (Product product : productService.findList(productIds)) {
			if (!product.getIsGift()) {
				trial.getProducts().add(product);
			}
		}
		if (trial.getBeginDate() != null && trial.getEndDate() != null && trial.getBeginDate().after(trial.getEndDate())) {
			return ERROR_VIEW;
		}
		trialService.update(trial);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", trialService.findPage(pageable));
		return "/admin/trial/list";    
	}      
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		trialService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}