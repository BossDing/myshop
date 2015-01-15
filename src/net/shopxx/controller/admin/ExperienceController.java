package net.shopxx.controller.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.FileInfo.FileType;
import net.shopxx.entity.Experience;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductImage;
import net.shopxx.service.AreaService;
import net.shopxx.service.ExperienceService;
import net.shopxx.service.FileService;
import net.shopxx.service.ProductImageService;
import net.shopxx.service.ProductService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
@Controller("adminExperienceController")
@RequestMapping("/admin/experience")
public class ExperienceController extends BaseController {

	@Resource(name = "experienceServiceImpl")
	private ExperienceService experienceService;  
	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
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
		model.addAttribute("areas", areaService.findRoots());
		return "/admin/experience/add";
	}        
    
	/**
	 * 保存      
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Experience experience, Long areaId,Long[] productIds,RedirectAttributes redirectAttributes) {
		experience.setArea(areaService.find(areaId));
		for (Product product : productService.findList(productIds)) {
			if (!product.getIsGift()) {
				experience.getProducts().add(product);    
			}
		}		   
		for (Iterator<ProductImage> iterator = experience.getProductImages().iterator(); iterator.hasNext();) {
			ProductImage productImage = iterator.next();
			if (productImage == null || productImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (productImage.getFile() != null && !productImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, productImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:add.jhtml";
				}
			}
		}
		for (ProductImage productImage : experience.getProductImages()) {
			productImageService.build(productImage);
		}  
		Collections.sort(experience.getProductImages());
		
		if (!isValid(experience)) {  
			return ERROR_VIEW;
		}
		
		experience.setAreaName(areaService.find(areaId).getFullName());
		experienceService.save(experience);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}      
  
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("experience", experienceService.find(id));
		return "/admin/experience/edit";
	}

	/**   
	 * 更新    
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Experience experience,Long areaId,Long[] productIds,RedirectAttributes redirectAttributes) {
		experience.setArea(areaService.find(areaId));
		for (Product product : productService.findList(productIds)) {
			if (!product.getIsGift()) {
				experience.getProducts().add(product);
			}
		}
		for (Iterator<ProductImage> iterator = experience.getProductImages().iterator(); iterator.hasNext();) {
			ProductImage productImage = iterator.next();
			if (productImage == null || productImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (productImage.getFile() != null && !productImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, productImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));  
					return "redirect:edit.jhtml?id=" + experience.getId();
				}
			}
		}
		for (ProductImage productImage : experience.getProductImages()) {
			productImageService.build(productImage);   
		}  
		Collections.sort(experience.getProductImages());  
		experience.setAreaName(areaService.find(areaId).getFullName());
		experienceService.update(experience);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**   
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", experienceService.findPage(pageable));
		return "/admin/experience/list";    
	}      
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		experienceService.delete(ids);   
		return SUCCESS_MESSAGE;
	}

}