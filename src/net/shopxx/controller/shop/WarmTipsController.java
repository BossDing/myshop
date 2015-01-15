package net.shopxx.controller.shop;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.controller.admin.BaseController;
import net.shopxx.entity.Area;
import net.shopxx.entity.WarmTips;
import net.shopxx.service.AreaService;
import net.shopxx.service.WarmTipsService;

import org.springframework.stereotype.Controller;
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
@Controller("shopWarmTipsController")
@RequestMapping("/shop/warmtips")
public class WarmTipsController extends BaseController{
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "warmTipsServiceImpl")
	private WarmTipsService warmTipsService;
	
	/**
	 * 根据地区与时间查询出，提醒信息
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/getTips", method = RequestMethod.POST)
	public @ResponseBody
	byte[] getShopWarmTipsDesc(String areaName,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException{
		Area area = areaService.findByAreaName(areaName);
		
		String str = "Hi！欢迎来到沁园官方商城";
		List<WarmTips> warmTips = warmTipsService.findByArea(area);
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("HH");
		int hours = Integer.valueOf(sf.format(date));
		if(warmTips != null){
			for(WarmTips tip:warmTips){
				int topTime = tip.getTimeTopLine().intValue();
				int bottomTime = tip.getTimeBottomLine().intValue();
				if(topTime < bottomTime){
					if(topTime <= hours && bottomTime >  hours){
						str = tip.getWarmTipsDesc();
					}
				}else if(bottomTime < topTime){
					if(topTime > hours && hours < bottomTime){
						str = tip.getWarmTipsDesc();
					}
				}
			}
		}
		
		return  str.getBytes("UTF-8");
	}
}
