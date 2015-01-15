/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app.member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.entity.PointsWater;
import net.shopxx.service.MemberService;
import net.shopxx.service.PointsWaterService;
import net.shopxx.util.TwUtil;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("APPMemberPointsController")
@RequestMapping("/m/member/points")
public class PointsController extends BaseController {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "pointsWaterServiceImpl")
	private PointsWaterService pointsWaterService;
	
	/**
	 * 列表(我的优惠券)
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public @ResponseBody ModelMap list(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> pointsList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));

			Pageable pageable = new Pageable(pageNumber, pageSize);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			
			
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 我的积分列表
	 */
	@RequestMapping(value = "/points",method = RequestMethod.POST)
	public @ResponseBody ModelMap points(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> pointsList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));

			Pageable pageable = new Pageable(pageNumber, pageSize);
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			
			for(PointsWater pw: pointsWaterService.findPage(member,pageable).getContent()) {
				HashMap<String, Object> pointsMap = new HashMap<String, Object>();
				pointsMap.put("id", pw.getId());
				pointsMap.put("date", (new SimpleDateFormat("yyyy-MM-dd")).format(pw.getCreateDate()));
				pointsMap.put("points_stat", pw.getPoints_stat());
				pointsMap.put("points", pw.getPoints());
				pointsList.add(pointsMap);
			}
			
			model.put("datas", pointsList);
			model.put("length", pointsList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}

//	/**
//	 * 信息
//	 */
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String list(Integer pageNumber, Integer pageSize, ModelMap model) {
//		Pageable pageable = new Pageable(pageNumber, pageSize);
//		Member member = memberService.getCurrent();
//		model.addAttribute("pageNumber", pageNumber);
//		model.addAttribute("pageSize", pageSize);
//		model.addAttribute("member",member);	
//		model.addAttribute("pointsWaters",pointsWaterService.findPage(member,pageable));
//		return "shop/member/points/list";
//	}
}