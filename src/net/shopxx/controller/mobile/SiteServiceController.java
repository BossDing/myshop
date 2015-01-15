package net.shopxx.controller.mobile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Pageable;
import net.shopxx.entity.SiteService;
import net.shopxx.service.SiteServiceService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("mobileServiceController")
@RequestMapping({"/mobile/member/service"})
public class SiteServiceController extends BaseController
{
	@Resource(name="siteServiceServiceImpl")
	  private SiteServiceService siteServiceService;

  @RequestMapping(value={"/list"}, method = RequestMethod.GET)
  public String list(String city, Pageable pageable, ModelMap model){
	  List<SiteService> siteServices = siteServiceService.findPage(city, pageable).getContent();
	  List<String> provinces = new ArrayList<String>();
	  for(int i=0;i<siteServices.size();i++){	  
		  if(!provinces.contains(siteServices.get(i).getServiceProvince())){
			  provinces.add(siteServices.get(i).getServiceProvince());
		  }
		  
	  }
	  model.addAttribute("provinces", provinces);
	  model.addAttribute("siteServices", siteServices);
	  return "/mobile/service/list";
  }

  @RequestMapping(value={"/content"}, method = RequestMethod.GET)
  public String content(String province , ModelMap model){
	  System.out.println("--tt11--:"+province);
	  List<SiteService > siteServices = siteServiceService.findList(province);
	  System.out.println("--tt2--:"+siteServices.get(0).getServiceName());
	  model.addAttribute("siteServices", siteServices);
	  return "/mobile/service/content";
  }
}