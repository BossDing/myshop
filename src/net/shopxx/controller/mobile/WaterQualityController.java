package net.shopxx.controller.mobile;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.entity.Area;
import net.shopxx.entity.Product;
import net.shopxx.entity.WaterQualityData;
import net.shopxx.service.AreaService;
import net.shopxx.service.ProductService;
import net.shopxx.service.WaterQualityService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 水质设置
 *      
 * @author xk
 * @version 1.0
 */
@Controller("mobileWaterQualityController")
@RequestMapping("/mobile/member/waterquality")
public class WaterQualityController {
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "waterQualityServiceImpl")
	private WaterQualityService waterQualityService; 
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	Area area;
	/**
	 * 水质查询
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check(ModelMap model) {
		List<Area> roots = areaService.findRoots();
		model.addAttribute("roots", roots);
		return "/mobile/waterquality/check";    
	}
	
	/**
	 * 水质报告
	 */
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String report(String areaId, String so_tex,Long thisIndex, ModelMap model) {
		List<Area> areas1 = areaService.findList(areaId);
		Map<String,Object> data=new HashMap<String,Object>();
		List<WaterQualityData> wdatas=null;
		List<Product> products=null;
		if(thisIndex!=3L){
			area = areas1.get(0);
			if(area!=null){
				wdatas=waterQualityService.findData(area, so_tex);				
			}
		}else{
			if(so_tex != null){
			wdatas=waterQualityService.findData(area, so_tex);
			}else{
				wdatas=waterQualityService.findData(area, areaId);
			}
		}
		if(wdatas.size()!=0){
			products=productService.findByItemSpec(wdatas.get(0).getItemSpec());
			if(products.size()!=0){
				data.put("products", products);
			}
			data.put("tds", wdatas.get(0).getTds());
			data.put("cl", wdatas.get(0).getChlorine());
			data.put("itmSpec",wdatas.get(0).getItemSpec());
			
		}
		model.addAttribute("data", data);
		return "/mobile/waterquality/report";    
	}
	
	/**
	 * 获得下级地区的集合
	 */
	@RequestMapping(value = "/areaList", method = RequestMethod.GET)
	public @ResponseBody
	byte[] areaList(String areaId , Long nextIndex , ModelMap model) {
		String xHtml="";
		byte[] kHtml = null;
		List<Area> areas1 = areaService.findList(areaId);
		area = areas1.get(0);
		Set<Area> areas = area.getChildren();
		List<Area> xAreas = new ArrayList<Area>();
		System.out.println("--nextIndex--:"+nextIndex);
		for(Area att:areas){
			xAreas.add(att);
		}
		if(nextIndex!=3L){
				if(xAreas.size()!=0){
				for(int i=0;i<xAreas.size();i++){
					int t=i+2;
					String areaName = xAreas.get(i).getName();
					xHtml = xHtml+"<option value="+t+">"+areaName+"</option>";
				}
				xHtml = xHtml.trim();			
				try {
					kHtml =null;
					kHtml = xHtml.getBytes("utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}else if(nextIndex==3L){
			List<WaterQualityData> waterDatas = waterQualityService.findData(area, null);
			if(waterDatas.size() == 0){
			}
			if(waterDatas.size()!=0){
				for(int i=0;i<waterDatas.size();i++){
					int t=i+2;
					String waterData = waterDatas.get(i).getCommunityName();
					xHtml = xHtml+"<option value="+t+">"+waterData+"</option>";
					
				}
				try {
					kHtml = xHtml.getBytes("utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return kHtml;    
	}
}
