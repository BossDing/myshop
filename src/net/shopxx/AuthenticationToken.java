/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登录令牌
 * 
 * @author SHOP++ Team
 * @version 3.0
 * 用户令牌
 * 在 Shiro 术语中，令牌 指的是一个键，可用它登录到一个系统。
 * 最基本和常用的令牌是 UsernamePasswordToken，用以指定用户的用户名和密码。
 * 
 * 
 * UsernamePasswordToken 类实现了 AuthenticationToken 接口，它提供了一种获得凭证和用户的主体（帐户身份）的方式。
 * UsernamePasswordToken 适用于大多数应用程序， 并且您还可以在需要的时候扩展 AuthenticationToken 
 * 接口来将您自己获得凭证的方式包括进来。例如，可以扩展这个接口来提供您应用程序用来验证用户身份的一个关键文件的内容。
 * 
 */
public class AuthenticationToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 5898441540965086534L;

	/** 验证码ID */
	private String captchaId;

	/** 验证码 */
	private String captcha;

	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param captchaId
	 *            验证码ID
	 * @param captcha
	 *            验证码
	 * @param rememberMe
	 *            记住我
	 * @param host
	 *            IP
	 */
	public AuthenticationToken(String username, String password, String captchaId, String captcha, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
		this.captchaId = captchaId;
		this.captcha = captcha;
	}

	/**
	 * 获取验证码ID
	 * 
	 * @return 验证码ID
	 */
	public String getCaptchaId() {
		return captchaId;
	}

	/**
	 * 设置验证码ID
	 * 
	 * @param captchaId
	 *            验证码ID
	 */
	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}

	/**
	 * 获取验证码
	 * 
	 * @return 验证码
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * 设置验证码
	 * 
	 * @param captcha
	 *            验证码
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}