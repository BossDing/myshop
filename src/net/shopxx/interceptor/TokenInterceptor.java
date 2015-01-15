/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.interceptor;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shopxx.util.WebUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor - 令牌
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	/** "令牌"属性名称 */
	private static final String TOKEN_ATTRIBUTE_NAME = "token";

	/** "令牌"Cookie名称 */
	private static final String TOKEN_COOKIE_NAME = "token";

	/** "令牌"参数名称 */
	private static final String TOKEN_PARAMETER_NAME = "token";

	/** 错误消息 */
	private static final String ERROR_MESSAGE = "Bad or missing token!";
	
	private static final String FILTER_URL = "http://m.macro.com.cn:80/mobile/payment/weixinVerify.jhtml";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = WebUtils.getCookie(request, TOKEN_COOKIE_NAME);
		if (request.getMethod().equalsIgnoreCase("POST")) {
			String requestUrl = String.valueOf(request.getRequestURL());
//			System.out.println("=========requestUrl: "+requestUrl);
			if(FILTER_URL.equals(requestUrl)) {
				return true; 
			}
			String requestType = request.getHeader("X-Requested-With");
			
			if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
				if (token != null && token.equals(request.getHeader(TOKEN_PARAMETER_NAME))) {
					return true;
				} else {
					response.addHeader("tokenStatus", "accessDenied");
				}
			} else {
				if (token != null && token.equals(request.getParameter(TOKEN_PARAMETER_NAME))) {
					return true;
				}
			}
			
			// 判断客户端类型
			String userAgen = request.getHeader("User-Agent");
			//手机客户端验证
			if (userAgen!=null && userAgen.indexOf("macro") >= 0 && (userAgen.indexOf("ios;") > 0 || userAgen.indexOf("android;") > 0)) {
				return true;
			}
			
			if (token == null) {
				token = UUID.randomUUID().toString();
				WebUtils.addCookie(request, response, TOKEN_COOKIE_NAME, token);
			}
			return true;
//			response.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE);
//			return false;
		} else {
			if (token == null) {
				token = UUID.randomUUID().toString();
				WebUtils.addCookie(request, response, TOKEN_COOKIE_NAME, token);
			}
			request.setAttribute(TOKEN_ATTRIBUTE_NAME, token);
			return true;
		}
	}

}