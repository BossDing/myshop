/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Area;
import net.shopxx.entity.Member;
import net.shopxx.entity.Receiver;
import net.shopxx.service.AreaService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ReceiverService;
import net.shopxx.util.TwUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 会员中心 - 收货地址
 * 创建日期：2014-05-08
 * @author chengandou
 * @version 3.0
 */
@Controller("APPMemberReceiverController")
@RequestMapping("/m/member/receiver")
public class ReceiverController extends BaseController {
	
	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	
	/**
	 * 获取用户的收获地址
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public @ResponseBody ModelMap list(Integer pageNumber, Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> receiverList = new ArrayList<HashMap<String, Object>>();
			Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
			
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			for(Receiver  receiver: receiverService.findPage(member, pageable).getContent()) {
				HashMap<String, Object> receiverMap = new HashMap<String, Object>();
				receiverMap.put("id", receiver.getId());
				receiverMap.put("consignee", receiver.getConsignee());
				receiverMap.put("phone", receiver.getPhone());
				receiverMap.put("areaid", receiver.getArea().getId());
				receiverMap.put("areaname", receiver.getAreaName());
				receiverMap.put("address", receiver.getAddress());
				receiverMap.put("zipcode", receiver.getZipCode());
				receiverMap.put("isdefault", receiver.getIsDefault());
				receiverList.add(receiverMap);
			}
			
			model.put("datas", receiverList);
			model.put("length", receiverList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 查询地区(根据用户搜索的关键字查询)
	 */
	@RequestMapping(value = "/searchArea",method = RequestMethod.POST)
	public @ResponseBody ModelMap searchArea(String areaName, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			ArrayList<HashMap<String, Object>> areaList = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			areaName = (String) obj.get("area");
//			System.out.println("searchArea------>areaName: "+areaName);
			
			for(Area area: areaService.findPageByAreaName(areaName)) {
				HashMap<String, Object> areaMap = new HashMap<String, Object>();
				areaMap.put("id", area.getId());
				areaMap.put("fullname", area.getFullName());
				areaList.add(areaMap);
			}
			model.put("datas", areaList);
			model.put("length", areaList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public @ResponseBody ModelMap save(Long userid, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);

			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Long areaId = Long.parseLong((String) obj.get("areaid"));
			String address = (String) obj.get("address");
			String consignee = (String) obj.get("consignee");
			String zipCode = (String) obj.get("zipcode");
			String phone = (String) obj.get("phone");
			Boolean isDefault = Boolean.valueOf((String) obj.get("isdefault"));
			
//			System.out.println("areaId:"+areaId+"、address:"+address+"、consignee:"+consignee+"、zipCode:"+zipCode+"、phone:"+phone+"、isDefault:"+isDefault);
			Receiver receiver = new Receiver();
			receiver.setMember(member);
			receiver.setArea(areaService.find(areaId));
			receiver.setAddress(address);
			receiver.setConsignee(consignee);
			receiver.setZipCode(zipCode);
			receiver.setPhone(phone);
			receiver.setIsDefault(isDefault);
			receiverService.save(receiver);
			
			model.put("id", receiver.getId());
			model.put("areaid", receiver.getArea().getId());
			model.put("areaid", receiver.getArea().getId());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 编辑收货地址
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	public @ResponseBody ModelMap edit(Long userid, Long id, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);

			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			id = Long.parseLong((String) obj.get("id"));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Long areaId = null;
			String address = null;
			String consignee = null;
			String zipCode = null;
			String phone = null;
			Boolean isDefault = null;
			Receiver receiver = receiverService.find(id);
			try {
				areaId = Long.parseLong((String) obj.get("areaid"));
				address = (String) obj.get("address");
				consignee = (String) obj.get("consignee");
				zipCode = (String) obj.get("zipcode");
				phone = (String) obj.get("phone");
				isDefault = Boolean.valueOf((String) obj.get("isdefault"));
			} catch (Exception e) {
				
			} finally {
//				System.out.println("areaId:"+areaId+"、address:"+address+"、consignee:"+consignee+"、zipCode:"+zipCode+"、phone:"+phone+"、isDefault:"+isDefault);
				if(areaId != null) {
					receiver.setArea(areaService.find(areaId));
				}
				if(address != null) {
					receiver.setAddress(address);
				}
				if(consignee != null) {
					receiver.setConsignee(consignee);
				}
				if(zipCode != null) {
					receiver.setZipCode(zipCode);
				}
				if(phone != null) {
					receiver.setPhone(phone);
				}
				if(isDefault != null) {
					receiver.setIsDefault(isDefault);
				}
			}
			receiverService.update(receiver);
			
			model.put("id", receiver.getId());
			model.put("areaid", receiver.getArea().getId());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}

	/**
	 * 删除收货地址
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public @ResponseBody ModelMap delete(Long userid, Long id, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);

			userid = Long.parseLong(TwUtil.decryptStr((String) obj.get("userid")));
			id = Long.parseLong((String) obj.get("id"));
			Member member = memberService.find(userid);
			if(member == null) {
				model.put("error", "无效的用户");
				model.put("success", 1);
				return model;
			}
			Receiver receiver = receiverService.find(id);
			if (!member.equals(receiver.getMember())) {
				model.put("success", 1);
				model.put("error", "删除收货地址出错，请联系客服人员 谢谢！");
				return model;
			}
			receiverService.delete(id);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	

}