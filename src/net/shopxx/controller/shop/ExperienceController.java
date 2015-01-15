package net.shopxx.controller.shop;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.entity.Area;
import net.shopxx.entity.Experience;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.AreaService;
import net.shopxx.service.ExperienceService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller - 体验中心
 *         
 * @author DongXinXing
 * @version 1.0
 */
@Controller("shopExperienceController")
@RequestMapping("/experience")
public class ExperienceController extends BaseController {

	@Resource(name = "experienceServiceImpl")
	private ExperienceService experienceService;  
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	/**
	 * 列表
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String searchWord,String city,Long areaId_new,Long provinceId,OrderType orderType,Integer pageNumber, Integer pageSize, HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
		if(pageNumber == null ||pageNumber.equals(new Integer(0))){
		   pageNumber = new Integer(1);
		}  
		if(pageSize == null ||pageSize.equals(new Integer(0))){
			pageSize = new Integer(9);
		}
		System.out.println(city);
		if(areaId_new == null&&city!=null){
			if(!city.equals("")){
				Area area = areaService.findByAreaName(city);
				if(area!=null){
					areaId_new = area.getId();  
					provinceId = area.getParent().getId(); 
					System.out.println(areaId_new);
					System.out.println(provinceId);
				}    
			}   
		}    
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("orderType", orderType);   
		model.addAttribute("pageNumber", pageNumber);         
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("areaId_new", areaId_new);
		model.addAttribute("provinceId", provinceId);
		model.addAttribute("searchWord", searchWord);   
		pageable.setSearchValue(searchWord);         
		pageable.setSearchProperty("name");   
		       
		if(areaId_new == null){  
			model.addAttribute("page", experienceService.findPage(pageable));
		}else{
			model.addAttribute("treePath", areaService.find(areaId_new).getTreePath());
			model.addAttribute("page", experienceService.findPage(areaService.find(areaId_new).getName(),pageable));  
		}   
		return "/shop/experience/list";      
	}
	/**
	 * 地图  
	 */
	@RequestMapping(value = "/map/{id}", method = RequestMethod.GET)  
	public String map(@PathVariable Long id, ModelMap model) {
		Experience experience = experienceService.find(id);
		if (experience == null) {
			throw new ResourceNotFoundException();
		}   
		model.addAttribute("experience", experience);
		return "/shop/experience/map";
	}

	/**
	 * 体验中心详情页面          
	 */
	@RequestMapping(value = "/content/{id}", method = RequestMethod.GET)  
	public String content(@PathVariable Long id, ModelMap model) {
		Experience experience = experienceService.find(id);
		if (experience == null) {
			throw new ResourceNotFoundException();
		}   
		model.addAttribute("experience", experience);
		return "/shop/experience/content";
	}
}