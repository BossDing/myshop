package net.shopxx.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Area;
import net.shopxx.entity.MemberRank;
import net.shopxx.entity.Product;
import net.shopxx.entity.PreSellRole;
import net.shopxx.entity.WarmTips;
import net.shopxx.service.AreaService;
import net.shopxx.service.BaseService;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.PreSellService;
import net.shopxx.service.ProductService;
import net.shopxx.service.WarmTipsService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 温馨提示
 *      
 * @author hfh
 * @version 1.0
 */
@Controller("warmTipsController")
@RequestMapping("/admin/warmtips")
public class WarmTipsController extends BaseController{
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "warmTipsServiceImpl")
	private WarmTipsService warmTipsService;
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
//		model.addAttribute("memberRanks", memberRankService.findAll());
		List<Area> areas = new ArrayList<Area>();
		areas = areaService.findRoots();
		model.addAttribute("areas", areas);
		return "/admin/warmtips/add";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("warmtips", warmTipsService.find(id));
		model.addAttribute("areas", areaService.findRoots());
		return "/admin/warmtips/edit";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", warmTipsService.findPage(pageable));
		return "/admin/warmtips/list";
	}
	
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(WarmTips warmTips, Long areaId,
			RedirectAttributes redirectAttributes) {
		warmTips.setArea(areaService.find(areaId));
		if (!isValid(warmTips)) {
			return ERROR_VIEW;
		}
		warmTips.setTimeTopLine(Integer.parseInt(warmTips.getTimeTopLine().toString()));
		warmTips.setTimeBottomLine(Integer.parseInt(warmTips.getTimeBottomLine().toString()));
//		if (warmTips.getTimeTopLine() != null && warmTips.getTimeBottomLine() != null 
//				&& Integer.parseInt(warmTips.getTimeTopLine().toString()) > Integer.parseInt(warmTips.getTimeBottomLine().toString())) {
//			return ERROR_VIEW;
//		}
		warmTipsService.save(warmTips);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST) 
	public String update(WarmTips warmTips, Long areaId, RedirectAttributes redirectAttributes) {
		if (!isValid(warmTips)) {
			return ERROR_VIEW;
		}
//		if (warmTips.getTimeTopLine() != null && warmTips.getTimeBottomLine() != null 
//				&& warmTips.getTimeTopLine().after(warmTips.getTimeBottomLine())) {
//			return ERROR_VIEW;
//		}
//		if (warmTips.getTimeTopLine() != null && warmTips.getTimeBottomLine() != null 
//				&& Integer.parseInt(warmTips.getTimeTopLine().toString()) > Integer.parseInt(warmTips.getTimeBottomLine().toString())) {
//			return ERROR_VIEW;
//		}
//		warmTips.setTimeTopLine(Integer.parseInt(warmTips.getTimeTopLine().toString()));
//		warmTips.setTimeBottomLine(Integer.parseInt(warmTips.getTimeBottomLine().toString()));
		warmTips.setTimeTopLine(warmTips.getTimeTopLine().intValue());
		warmTips.setTimeBottomLine(warmTips.getTimeBottomLine().intValue());
		warmTips.setArea(areaService.find(areaId));
		warmTipsService.update(warmTips);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		warmTipsService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
}
