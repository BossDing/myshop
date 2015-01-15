package net.shopxx.controller.app.member;

/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.print.attribute.standard.Finishings;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import net.shopxx.CommonAttributes;
import net.shopxx.Message;
import net.shopxx.Setting;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Area;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberAttribute;
import net.shopxx.entity.Member.Gender;
import net.shopxx.entity.MemberAttribute.Type;
import net.shopxx.service.AreaService;
import net.shopxx.service.CartService;
import net.shopxx.service.MemberAttributeService;
import net.shopxx.service.MemberService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.TwUtil;
import net.shopxx.util.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 会员中心 - 个人资料 
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("APPMemberProfileController")
@RequestMapping("/m/member/profile")
public class ProfileController extends BaseController {

	/** "会员"属性名称 */
	private static final String MEMBER_ATTRIBUTE_NAME = "member";
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;

	/**
	 * 检查E-mail是否唯一
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		Member member = memberService.getCurrent();
		if (memberService.emailUnique(member.getEmail(), email)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取用户资料
	 */
	@RequestMapping(value = "/getInfo",method = RequestMethod.POST)
	public @ResponseBody ModelMap getInfo(Long userid, HttpServletResponse response, HttpServletRequest request) {
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
			model.put("username", member.getUsername());
			model.put("email", member.getEmail());
			model.put("mobile", member.getMobile());
			model.put("name", member.getName());
			model.put("gender", member.getGender());
			model.put("birth", member.getBirth() == null ? "" : (new SimpleDateFormat("yyyy-MM-dd")).format(member.getBirth()));
			model.put("areaid", member.getArea() == null ? "" : member.getArea().getId());
			model.put("areaname", member.getArea() == null ? "" : member.getArea().getFullName());
			model.put("address", member.getAddress());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取用户资料
	 */
	@RequestMapping(value = "/getInvoice",method = RequestMethod.POST)
	public @ResponseBody ModelMap getInvoice(Long userid, HttpServletResponse response, HttpServletRequest request) {
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
			model.put("name", member.getDangweimingchen());
			model.put("id", member.getNashuishibiehao());
			model.put("address", member.getZhucedizhi());
			model.put("phone", member.getZhucedianhua());
			model.put("bank", member.getKaihuyh());
			model.put("account", member.getZengzhishuiyhzh());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 保存增值税发票
	 */
	@RequestMapping(value = "/saveinvoice", method = RequestMethod.POST)
	public @ResponseBody ModelMap saveinvoice(Long userid, HttpServletRequest request, RedirectAttributes redirectAttributes) {
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
			String name = null;
			String id = null;
			String address = null;
			String phone = null;
			String bank = null;
			String account = null;
			
			try {
				name = (String) obj.get("name");
				id = (String) obj.get("id");
				address = (String) obj.get("address");
				phone = (String) obj.get("phone");
				bank = (String) obj.get("bank");
				account = (String) obj.get("account");
			} catch (Exception e) {
				
			} finally {
				member.setDangweimingchen(name);
				member.setNashuishibiehao(id);
				member.setZhucedizhi(address);
				member.setZhucedianhua(phone);
				member.setKaihuyh(bank);
				member.setZengzhishuiyhzh(account);
			}
			
			memberService.update(member);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	
	/**
	 * 修改用户个人资料
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	public @ResponseBody ModelMap edit(Long userid, HttpServletResponse response, HttpServletRequest request) {
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
			String email = null;
			String mobile = null;
			String name = null;
			String gender = null;
			String birth = null;
			Long areaid = null;
			String address = null;
			try {
				email = (String) obj.get("email");
				mobile = (String) obj.get("mobile");
				name = (String) obj.get("name");
				gender = (String) obj.get("gender");
				birth = (String) obj.get("birth");
				areaid = Long.parseLong((String) obj.get("areaid"));
				address = (String) obj.get("address");
			} catch(Exception e) {
				
			} finally {
				if(StringUtils.isNotEmpty(email)) {
					member.setEmail(email);
				}
				if(StringUtils.isNotEmpty(mobile)) {
					member.setMobile(mobile);
				}
				if(StringUtils.isNotEmpty(name)) {
					member.setName(name);
				}
				if(StringUtils.isNotEmpty(gender)) {
					member.setGender(Gender.valueOf(gender));
				}
				if(StringUtils.isNotEmpty(birth)) {
					member.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse(birth));
				}
				if(areaid != null) {
					member.setArea(areaService.find(areaid));
				}
				if(StringUtils.isNotEmpty(address)) {
					member.setAddress(address);
				}
			}
			memberService.update(member);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	
	@RequestMapping(value = "/mybalance", method = RequestMethod.GET)
	public String mybalance(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		WebUtils.getOpenidtoLoginForWechat(request, response, session, memberService, cartService);
		Member member = memberService.getCurrent();//获取当前会员信息
		model.addAttribute(MEMBER_ATTRIBUTE_NAME,member);
		return "mobile/member/balance/mybalance";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	public void update(String email, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (!isValid(Member.class, "email", email)) {
			out.println("邮箱验证失败！");
			out.flush(); 
			out.close(); 
			return;
		}
		Setting setting = SettingUtils.get();
		Member member = memberService.getCurrent();
		if (!setting.getIsDuplicateEmail() && !memberService.emailUnique(member.getEmail(), email)) {
			out.println("此邮箱已被注册！");
			out.flush(); 
			out.close(); 
			return ;
		}
		member.setEmail(email);
		List<MemberAttribute> memberAttributes = memberAttributeService.findList();
		for (MemberAttribute memberAttribute : memberAttributes) {
			String parameter = request.getParameter("memberAttribute_" + memberAttribute.getId());
			if (memberAttribute.getType() == Type.name || memberAttribute.getType() == Type.address || memberAttribute.getType() == Type.zipCode || memberAttribute.getType() == Type.phone || memberAttribute.getType() == Type.mobile || memberAttribute.getType() == Type.text || memberAttribute.getType() == Type.select) {
				if (memberAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					out.println("地址不能为空！");
					out.flush(); 
					out.close(); 
					return ;
				}
				member.setAttributeValue(memberAttribute, parameter);
			} else if (memberAttribute.getType() == Type.gender) {
				Gender gender = StringUtils.isNotEmpty(parameter) ? Gender.valueOf(parameter) : null;
				if (memberAttribute.getIsRequired() && gender == null) {
					out.println("性别不能为空！");
					out.flush(); 
					out.close(); 
					return ;
				}
				member.setGender(gender);
			} else if (memberAttribute.getType() == Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter) ? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (memberAttribute.getIsRequired() && birth == null) {
						out.println("出生日期不能为空！");
						out.flush(); 
						out.close(); 
						return ;
					}
					member.setBirth(birth);
				} catch (ParseException e) {
					out.println("出生日期格式解析出错！");
					out.flush(); 
					out.close(); 
					return ;
				}
			} else if (memberAttribute.getType() == Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					member.setArea(area);
				} else if (memberAttribute.getIsRequired()) {
					out.println("地区不能为空！");
					out.flush(); 
					out.close(); 
					return ;
				}
			} else if (memberAttribute.getType() == Type.checkbox) {
				String[] parameterValues = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (memberAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					out.println("多选框出错！");
					out.flush(); 
					out.close(); 
					return ;
				}
				member.setAttributeValue(memberAttribute, options);
			}
		}
		memberService.update(member);
		out.println("操作成功！");
		out.flush(); 
		out.close(); 
	}

}