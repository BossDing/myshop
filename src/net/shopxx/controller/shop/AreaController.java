/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import net.shopxx.entity.Area;
import net.shopxx.service.AreaService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Controller - 地区
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("areaController")
@RequestMapping("/area")
public class AreaController extends BaseController {

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
  
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Long parentId, ModelMap model) {
		model.addAttribute("parent", areaService.find(parentId));
		return "/admin/area/add";
	}

 
	@RequestMapping(value = "/listbyparent", method = RequestMethod.POST)
	public @ResponseBody
	List<Map<String, Object>> listbyparent(String areaId) {    
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(areaId)) {
			List<Area> areas = areaService.findListByParent(areaId); 
			for (Area area : areas) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", area.getId());
				map.put("name", area.getName());
				data.add(map);
			}
		}     
		return data;   
	}
	
	/**
	 * 根据地区ID查询地区全名
	 * @param areaId 
	 * 			地区ID
	 * @return 
	 * 			Area实体类
	 * @date 
	 * 			2014/11/06
	 */
	@RequestMapping(value = "/searchArea", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> searchArea(String areaId){
		Long id = Long.parseLong(areaId);
		Area area = areaService.find(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fullName", area.getFullName());
		return map;
	}
}