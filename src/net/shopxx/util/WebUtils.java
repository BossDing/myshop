/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.shopxx.Principal;
import net.shopxx.Setting;
import net.shopxx.entity.Cart;
import net.shopxx.entity.Member;
import net.shopxx.entity.Store;
import net.shopxx.homawechat.tool.WeixinUtil;
import net.shopxx.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import net.shopxx.service.CartService;

/**
 * Utils - Web
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public final class WebUtils {

	/**
	 * 不可实例化
	 */
	private WebUtils() {
	}

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            有效期(单位: 秒)
	 * @param path
	 *            路径
	 * @param domain
	 *            域
	 * @param secure
	 *            是否启用加密
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, value);
			if (maxAge != null) {
				cookie.setMaxAge(maxAge);
			}
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			if (secure != null) {
				cookie.setSecure(secure);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            有效期(单位: 秒)
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge) {
		Setting setting = SettingUtils.get();
		addCookie(request, response, name, value, maxAge, setting.getCookiePath(), setting.getCookieDomain(), null);
	}

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
		Setting setting = SettingUtils.get();
		addCookie(request, response, name, value, null, setting.getCookiePath(), setting.getCookieDomain(), null);
	}

	/**
	 * 获取cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie名称
	 * @return 若不存在则返回null
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Assert.hasText(name);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				name = URLEncoder.encode(name, "UTF-8");
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 移除cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param path
	 *            路径
	 * @param domain
	 *            域
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, String domain) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			Cookie cookie = new Cookie(name, null);
			cookie.setMaxAge(0);
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Setting setting = SettingUtils.get();
		removeCookie(request, response, name, setting.getCookiePath(), setting.getCookieDomain());
	}

	/**
	 * 获取参数
	 * 
	 * @param queryString
	 *            查询字符串
	 * @param encoding
	 *            编码格式
	 * @param name
	 *            参数名称
	 * @return 参数
	 */
	public static String getParameter(String queryString, String encoding, String name) {
		String[] parameterValues = getParameterMap(queryString, encoding).get(name);
		return parameterValues != null && parameterValues.length > 0 ? parameterValues[0] : null;
	}

	/**
	 * 获取参数
	 * 
	 * @param queryString
	 *            查询字符串
	 * @param encoding
	 *            编码格式
	 * @param name
	 *            参数名称
	 * @return 参数
	 */
	public static String[] getParameterValues(String queryString, String encoding, String name) {
		return getParameterMap(queryString, encoding).get(name);
	}

	/**
	 * 获取参数
	 * 
	 * @param queryString
	 *            查询字符串
	 * @param encoding
	 *            编码格式
	 * @return 参数
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, String[]> getParameterMap(String queryString, String encoding) {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		Charset charset = Charset.forName(encoding);
		try {
		if (StringUtils.isNotEmpty(queryString)) {
			byte[] bytes;
			
				bytes = queryString.getBytes(charset.toString());
			
			if (bytes != null && bytes.length > 0) {
				int ix = 0;
				int ox = 0;
				String key = null;
				String value = null;
				while (ix < bytes.length) {
					byte c = bytes[ix++];
					switch ((char) c) {
					case '&':
						value = new String(bytes, 0, ox, charset.toString());
						if (key != null) {
							putMapEntry(parameterMap, key, value);
							key = null;
						}
						ox = 0;
						break;
					case '=':
						if (key == null) {
							key = new String(bytes, 0, ox, charset.toString());
							ox = 0;
						} else {
							bytes[ox++] = c;
						}
						break;
					case '+':
						bytes[ox++] = (byte) ' ';
						break;
					case '%':
						bytes[ox++] = (byte) ((convertHexDigit(bytes[ix++]) << 4) + convertHexDigit(bytes[ix++]));
						break;
					default:
						bytes[ox++] = c;
					}
				}
				if (key != null) {
					value = new String(bytes, 0, ox, charset.toString());
					putMapEntry(parameterMap, key, value);
				}
			}
		}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parameterMap;
	}

	private static void putMapEntry(Map<String, String[]> map, String name, String value) {
		String[] newValues = null;
		String[] oldValues = map.get(name);
		if (oldValues == null) {
			newValues = new String[] { value };
		} else {
			newValues = new String[oldValues.length + 1];
			System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
			newValues[oldValues.length] = value;
		}
		map.put(name, newValues);
	}

	private static byte convertHexDigit(byte b) {
		if ((b >= '0') && (b <= '9')) {
			return (byte) (b - '0');
		}
		if ((b >= 'a') && (b <= 'f')) {
			return (byte) (b - 'a' + 10);
		}
		if ((b >= 'A') && (b <= 'F')) {
			return (byte) (b - 'A' + 10);
		}
		throw new IllegalArgumentException();
	}
	
	public static String getXentcode(){
		String entcode = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				Principal principal = (Principal) subject.getPrincipal();
				if (principal != null) {
//					return principal.getUsername().equals("gwadmin") ? "macrogw" : "macro";
					entcode = principal.getEntcode();
//					System.out.println("ENTCOE:" + entcode);
					if (StringUtils.isNotEmpty(entcode))
						return entcode;
				}
			}
		} catch (Exception e) { 
//			System.out.println("从身份信息中获取Xentcode异常");
		}
		try {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			if (requestAttributes != null) {
				HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
				String serverName = request.getServerName();
				String path = request.getRequestURL().toString();
//				System.out.println("path:"+path);
//				System.out.println("serverName:"+serverName);
	//			if(path.indexOf("test.etwowin.com/gw")>=0){
	//				entcode = "macrogw";
	//			}else if(path.indexOf("test.etwowin.com")>=0){
	//				entcode = "macro";
	//			}else if(path.indexOf("macro.itwowin.com/gw")>=0){
	//				entcode = "macrogw";
	//			}else if(path.indexOf("macro.itwowin.com")>=0){
	//				entcode = "macro";
	//			}else if(path.indexOf("shop.chinamacro.cn/gw")>=0){
	//				entcode = "macrogw";
	//			}else if(path.indexOf("shop.chinamacro.cn")>=0){
	//				entcode = "macro";
	//			}else if(path.indexOf("www.chinamacro.cn")>=0){
	//				entcode = "macrogw";
	//			}else if(path.indexOf("192.168.101.1")>=0){
	//				entcode = "macrogw";
	//			}
//				System.out.println(serverName.length()+8);
//				System.out.println(serverName.length()+12);
				String port = path.substring(serverName.length()+8	,serverName.length()+12);
//				System.out.println("port:"+port);
				if(path.indexOf("/gw/")>=0){
					entcode = "macrogw";
				}else if(path.indexOf("emacro")>=0){
					entcode = "macromk";
				}else if(serverName.indexOf("www.chinamacro.cn")>=0){
					entcode = "macrogw";
				}else if(("9090").equals(port)){
					entcode = "macromk";
				}else{
					entcode = "macro";
				}
//				System.out.println("entcode:"+entcode);
			}
		} catch (Exception e) { 
//			System.out.println("从域名或URL中获取Xentcode异常");
		}
		return entcode;
	}

	public static void getOpenidtoLoginForWechat(HttpServletRequest request, HttpServletResponse response, HttpSession session, MemberService memberService, CartService cartService) {
		String openid = "";
		Setting setting = SettingUtils.get();
		openid = WeixinUtil.getOpenid(request, response);
//		System.out.println("----------------openid : "+openid);
		Member member = null;
		if(openid != null && openid.length() > 0) {
			WebUtils.addCookie(request, response, "cookie_openid", openid, 1 * 24 * 60 * 60);
			List<Member> members = memberService.findListByWechatOpenid(openid);
			if (members.isEmpty()) {
				member = null;
			} else if (members.size() == 1) {
				member = members.get(0);
				int loginFailureCount = member.getLoginFailureCount() + 1;
				if (loginFailureCount >= setting.getAccountLockCount()) {
					member.setIsLocked(true);
					member.setLockedDate(new Date());
				}
				member.setLoginFailureCount(loginFailureCount);
				memberService.update(member);
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
			} 
		}
	}
	
	public static String getPercent(Long p1,Long p2)  {
		if(p2 == 0){
			return "0.0%";
		}
        String retStr;
        double  p3  =  (double)p1/p2;
        NumberFormat nf  =  NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits( 1 );
        retStr  =  nf.format(p3);
        return  retStr;
    }
	
	/**
	 * 从session里的"身份信息"里获取当前用户所属的店铺
	 * @author Guoxianlong
	 * @create_date Oct 13, 2014 9:37:43 AM
	 */
	public static Store getStore() {
		Store store = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				Principal principal = (Principal) subject.getPrincipal();
				if (principal != null) {
					store = principal.getStore();
				}
			}
			if (store == null) {
				RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
				if (requestAttributes != null) {
					HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
					store = (Store) request.getSession().getAttribute("store");
				}
			}
		} catch (Exception e) { 
//			System.out.println("获取store异常");
		}
		return store;
	}
	
}