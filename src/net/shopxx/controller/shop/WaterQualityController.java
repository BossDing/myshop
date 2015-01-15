package net.shopxx.controller.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import javax.annotation.Resource;

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
 * @author WeiHuaLin
 * @version 1.0
 */
@Controller("waterQualityDataController")
@RequestMapping("/waterquality")
public class WaterQualityController extends BaseController{
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "waterQualityServiceImpl")
	private WaterQualityService waterQualityService; 
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	/**
	 * 水质查询
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check(ModelMap model) {
		return "/shop/waterquality/check";    
	}
	
	/**
	 * 测试水质
	 */
	@RequestMapping(value = "/testWater", method = RequestMethod.POST)
	public @ResponseBody
	Map<String,Object> updateItmSpec(Long areaId, String districtName) {
		Map<String,Object> data=new HashMap<String,Object>();
		List<WaterQualityData> wdatas=null;
		List<Product> products=null;
		if(areaId!=null){
			wdatas=waterQualityService.findData(areaService.find(areaId), districtName);
			
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
		return data;
	}

	
}
