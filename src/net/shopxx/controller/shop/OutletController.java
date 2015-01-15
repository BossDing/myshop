/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Area;
import net.shopxx.entity.Outlet;
import net.shopxx.service.AreaService;
import net.shopxx.service.MemberService;
import net.shopxx.service.OutletService;
import net.shopxx.service.ProductCategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 文章
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("outletController")
@RequestMapping("/outlet")
public class OutletController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 20;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "outletServiceImpl")
	private OutletService outletService;
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST )
	public  @ResponseBody 
			List<String> list(Long areaId,String productCategoryName,String entCode,HttpServletRequest request, RedirectAttributes redirectAttributes){
		Area area = areaService.find(areaId);
		List<String> outlets = new ArrayList<String>();
		if(null!=area){
			List<Outlet> outs = outletService.findOutlets(area.getName(),productCategoryName,entCode);
			if(null!=outs) {
				for(Outlet s : outs){
					String sf = s.getId()+","+s.getName()+","+s.getPhone()+","+s.getAreaName()+","+s.getAddress();
					outlets.add(sf);
				 }
			}
			return outlets;
		} 
		return null;
	}
}