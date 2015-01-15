package net.shopxx.controller.mobile;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.shopxx.Message;
import net.shopxx.Principal;
import net.shopxx.Setting;
import net.shopxx.Setting.AccountLockType;
import net.shopxx.entity.Cart;
import net.shopxx.entity.Member;
import net.shopxx.entity.BaseEntity.Save;
import net.shopxx.service.CartService;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.MemberService;
import net.shopxx.service.RSAService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.WebUtils;
import net.shopxx.util.macro.weixin.WeixinUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller -- 微信会员卡系统
 * @author Chengd
 *
 */
@Controller("mobileWechatVipController")
@RequestMapping("/mobile/weixin_vip")
public class WechatVipController extends BaseController {
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	

	
	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (memberService.usernameDisabled(username) || memberService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 微信会员系统
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
//			System.out.println("WechatVipController--here ... ");
			List<Member> members = null;
			String wechatVipId = null;
			String openid = WeixinUtil.getOpenid(request, response);
//			System.out.println("controller--openid:" + openid);
			Member member = memberService.getCurrent(); //获取当前登录会员
			if (StringUtils.isEmpty(openid)) {
				model.addAttribute("errorMessage", "请在微信客户端打开链接");
				return "/mobile/weixin_vip/weixin_error";
			}
			boolean isSame = false;
			if (member != null) {
				isSame = openid.equals(member.getOpenid());
			}
			if (member == null || !isSame) {
				members = memberService.findListByWechatOpenid(openid);
			}
			if (member != null && !isSame) {
				logout(request, response, session);
				member = null;
			}
			int size = 0;
			if (members != null) {
				size = members.size();
			}
			if (size == 1) {
				member = members.get(0);
			} else if(size > 1) {
				model.addAttribute("errorMessage", "您的微信号绑定了多个商城账号");
				return "/mobile/weixin_vip/weixin_error";
			}
			if (member != null && !isSame) {
				/**
				 * 自动登录
				 */
				Message msg = autoLogin(member, openid, request, response, session);
				if (!msg.getType().equals(Message.Type.success)) {
					model.addAttribute("errorMessage", msg.getContent());
					return "/mobile/weixin_vip/weixin_error";
				}
			}
			if (member != null) {
				wechatVipId = member.getWechatVipId();
			}
			if (wechatVipId == null) {
				wechatVipId = createRandom(14);
				if (member != null) {
					member.setWechatVipId(wechatVipId);
					memberService.update(member);
				}
			}
			JSONObject jsonObj = WeixinUtil.getWeixinUserInfo(request, response);
			if (jsonObj.get("errcode") != null) {
				model.addAttribute("errorMessage", jsonObj.getString("errmsg"));
				return "/mobile/weixin_vip/weixin_error";
			}
			String[] strArray = wechatVipId.split("|");
			StringBuilder tmp = new StringBuilder(); 
			int len = strArray.length;
			for (int i = 0; i < len; i++) {
				if (i == 4 || i == 10) {
					tmp.append(" " + strArray[i]);
				}
				tmp.append(strArray[i]);
			}
			String nickname = jsonObj.getString("nickname");
			String headImageUrl = jsonObj.getString("headimgurl");
			model.addAttribute("member", member);
			model.addAttribute("wechatVipId", tmp.toString());
			model.addAttribute("nickname", nickname);
			model.addAttribute("headimgurl", headImageUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/mobile/weixin_vip/index";
	}
	
	/**
	 * 注册提交
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody
	Message submit(String username, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String password = rsaService.decryptParameter("enPassword", request);
		rsaService.removePrivateKey(request);

		Setting setting = SettingUtils.get();
		if (!setting.getIsRegisterEnabled()) {
			return Message.error("shop.register.disabled");
		}
		if (!isValid(Member.class, "username", username, Save.class) || !isValid(Member.class, "password", password, Save.class)) {
			return Message.error("shop.common.invalid");
		}
		if (username.length() < setting.getUsernameMinLength() || username.length() > setting.getUsernameMaxLength()) {
			return Message.error("shop.common.invalid");
		}
		if (password.length() < setting.getPasswordMinLength() || password.length() > setting.getPasswordMaxLength()) {
			return Message.error("shop.common.invalid");
		}
		if (memberService.usernameDisabled(username) || memberService.usernameExists(username)) {
			return Message.error("shop.register.disabledExist");
		}
		String openid = WeixinUtil.getOpenid(request, response);
		Member member = new Member();
		member.setUsername(username.toLowerCase());
		member.setPassword(DigestUtils.md5Hex(password));
		member.setPoint(setting.getRegisterPoint());
		member.setAmount(new BigDecimal(0));
		member.setBalance(new BigDecimal(0));
		member.setIsEnabled(true);
		member.setIsLocked(false);
		member.setLoginFailureCount(0);
		member.setLockedDate(null);
		member.setRegisterIp(request.getRemoteAddr());
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setSafeKey(null);
		member.setMemberRank(memberRankService.findDefault());
		member.setFavoriteProducts(null);
		member.setOpenid(openid);
		memberService.save(member);
		Cart cart = cartService.getCurrent();
		if (cart != null && cart.getMember() == null) {
			cartService.merge(member, cart);
			WebUtils.removeCookie(request, response, Cart.ID_COOKIE_NAME);
			WebUtils.removeCookie(request, response, Cart.KEY_COOKIE_NAME);
		}
		Map<String, Object> attributes = new HashMap<String, Object>();
		Enumeration<?> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			attributes.put(key, session.getAttribute(key));
		}
		session.invalidate();
		session = request.getSession();
		for (Entry<String, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}
		session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), member.getUsername()));
		WebUtils.addCookie(request, response, Member.USERNAME_COOKIE_NAME, member.getUsername());
		
		return Message.success("shop.register.success");
	}
	
	/**
	 * 更新信息
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	Message update(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String password = rsaService.decryptParameter("enPassword", request);
		rsaService.removePrivateKey(request);

		Setting setting = SettingUtils.get();
		if (!isValid(Member.class, "password", password, Save.class)) {
			return Message.error("shop.common.invalid");
		}
		if (password.length() < setting.getPasswordMinLength() || password.length() > setting.getPasswordMaxLength()) {
			return Message.error("shop.common.invalid");
		}
		Member member = memberService.getCurrent();
		String openid = WeixinUtil.getOpenid(request, response);
		if (openid == null) {
			return Message.error("请用微信客户端访问");
		}
		if (!openid.equals(member.getOpenid())) {
			return Message.error("微信唯一识别号与商城账号之间存在异常");
		}
		if (!member.getIsEnabled()) {
			return Message.error("此帐号已无效");
		}
		if (member.getIsLocked()) {
			return Message.error("此帐号已被锁定");
		}
		member.setPassword(DigestUtils.md5Hex(password));
		memberService.update(member);
		return Message.success("密码修改成功");
	}
	
	/**
	 * 生成指定长度随机数
	 * @param count 指定长度
	 * @return
	 */
	private String createRandom(int count) {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		while (count > 0) {
			sb.append(r.nextInt(10));
			count --;
		}
		return sb.toString();
	}
	
	/**
	 * 自动登录
	 * @param username
	 * @param openid
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	private Message autoLogin (Member member, String openid, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		Setting setting = SettingUtils.get();
		if (member == null) {
			return Message.error("无法识别的商城账号");
		}
		if (!member.getIsEnabled()) {
			return Message.error("您的商城账号已无效");
		}
		if (member.getIsLocked()) {
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.member)) {
				int loginFailureLockTime = setting.getAccountLockTime();
				if (loginFailureLockTime == 0) {
					return Message.error("商城账号已锁定");
				}
				Date lockedDate = member.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				if (new Date().after(unlockDate)) {
//					member.setLoginFailureCount(0);
					member.setIsLocked(false);
					member.setLockedDate(null);
//					memberService.update(member);
				} else {
					return Message.error("商城账号已锁定");
				}
			} else {
//				member.setLoginFailureCount(0);
				member.setIsLocked(false);
				member.setLockedDate(null);
//				memberService.update(member);
			}
		}

		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setLoginFailureCount(0);
		memberService.update(member);

		Cart cart = cartService.getCurrent();
		if (cart != null) {
			if (cart.getMember() == null) {
				cartService.merge(member, cart);
				WebUtils.removeCookie(request, response, Cart.ID_COOKIE_NAME);
				WebUtils.removeCookie(request, response, Cart.KEY_COOKIE_NAME);
			}
		}

		Map<String, Object> attributes = new HashMap<String, Object>();
		Enumeration<?> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			attributes.put(key, session.getAttribute(key));
		}
		session.invalidate();
		session = request.getSession();
		for (Entry<String, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}

		session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), member.getUsername()));
		WebUtils.addCookie(request, response, Member.USERNAME_COOKIE_NAME, member.getUsername());
		
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 注销
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.removeAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
		WebUtils.removeCookie(request, response, Member.USERNAME_COOKIE_NAME);
	}
}
