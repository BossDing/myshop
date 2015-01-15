/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Setting.AccountLockType;
import net.shopxx.Setting.CaptchaType;
import net.shopxx.entity.Admin;
import net.shopxx.entity.Store;
import net.shopxx.service.AdminService;
import net.shopxx.service.CaptchaService;
import net.shopxx.service.StoreService;
import net.shopxx.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
/**
 * http://www.ibm.com/developerworks/cn/java/j-lo-shiro/
 * 
 *  Spring Security VS  Spring Shiro
 *  Spring Security 也是一款优秀的安全控制组件
 *  简单性，Shiro 在使用上较 Spring Security 更简单，更容易理解。
 *  灵活性，Shiro 可运行在 Web、EJB、IoC、Google App Engine 等任何应用环境，却不依赖这些环境。
 *  	而 Spring Security 只能与 Spring 一起集成使用。
 *  可插拔，Shiro 干净的 API 和设计模式使它可以方便地与许多的其它框架和应用进行集成。	
 *  	Shiro 可以与诸如 Spring、Grails、Wicket、Tapestry、Mule、Apache Camel、Vaadin 这类第三方框架无缝集成。
 *  	Spring Security 在这方面就显得有些捉衿见肘。
 *  
 */
/**
 * 权限认证
 * Realm 可以理解为读取用户信息、角色及权限的 DAO
 * 
 * Realm类似于Oracle系统中经常使用的role
 * @author SHOP++ Team
 * @version 3.0
 */
public class AuthenticationRealm extends AuthorizingRealm {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "storeServiceImpl")
	private StoreService storeService;

	/**
	 * 获取 认证者 信息(验证当前登录的Subject)
	 * 
	 * 这个组件负责收集 principals 和 credentials，并将它们提交给应用系统。如果提交的 credentials 跟应用系统中提供的 credentials 吻合，
	 * 就能够继续访问，否则需要重新提交 principals 和 credentials，或者直接终止访问。
	 * @see 该方法在LoginController.login()
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		//自定义 登录令牌
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		String captchaId = authenticationToken.getCaptchaId();
		String captcha = authenticationToken.getCaptcha();
		String ip = authenticationToken.getHost();
		if (!captchaService.isValid(CaptchaType.adminLogin, captchaId, captcha)) {
			throw new UnsupportedTokenException();//验证码 验证失败
		}
		if (username != null && password != null) {
			Admin admin = adminService.findByUsername(username);
			if (admin == null) {
				throw new UnknownAccountException();///没有找到账户
			}
			if (!admin.getIsEnabled()) {
				throw new DisabledAccountException();//没有启用
			}
			Setting setting = SettingUtils.get();
			if (admin.getIsLocked()) {
				if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
					int loginFailureLockTime = setting.getAccountLockTime();
					if (loginFailureLockTime == 0) {
						throw new LockedAccountException();//锁定账户账户
					}
					Date lockedDate = admin.getLockedDate();
					Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					if (new Date().after(unlockDate)) {
						admin.setLoginFailureCount(0);
						admin.setIsLocked(false);
						admin.setLockedDate(null);
						adminService.update(admin);
					} else {
						throw new LockedAccountException();//用户被锁定	
					}
				} else {
					admin.setLoginFailureCount(0);
					admin.setIsLocked(false);
					admin.setLockedDate(null);
					adminService.update(admin);
				}
			}
			if (!DigestUtils.md5Hex(password).equals(admin.getPassword())) {
				int loginFailureCount = admin.getLoginFailureCount() + 1;
				if (loginFailureCount >= setting.getAccountLockCount()) {
					admin.setIsLocked(true);
					admin.setLockedDate(new Date());
				}
				admin.setLoginFailureCount(loginFailureCount);
				adminService.update(admin);
				throw new IncorrectCredentialsException();//密码不正确
			}
			admin.setLoginIp(ip);
			admin.setLoginDate(new Date());
			admin.setLoginFailureCount(0);
			adminService.update(admin);
//			return new SimpleAuthenticationInfo(new Principal(admin.getId(), username), password, getName());
			/**
			 * 添加企业号及店铺
			 */
			Store store = admin.getStore();//如果有店铺，此时仅有店铺id(延迟加载机制)
			if (store != null) {
				/**
				 * 在这个类中，无法通过store.get店铺的任何非id属性来获得store的全部信息
				 */
				store = storeService.find(store.getId());
			}
			return new SimpleAuthenticationInfo(new Principal(admin.getId(), username, admin.getEntcode(), store), password, getName());
		}
		throw new UnknownAccountException();
		//返回null时， 也会放回 UnknownAccountException 异常
	}

	/**
	 * 获取 授权者 信息(为当前登录的Subject授予角色和权限)
	 * 
	 * 身份份验证通过后，由这个组件对登录人员进行访问控制的筛查
	 * 
	 * @see 该方法在需授权的资源被访问时 
	 * @see 如果每次访问需要授权的资源时都会执行该方法，这表示AuthorizationCache 未启用 (本项目启用了)
	 * @param principals
	 *            principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			List<String> authorities = adminService.findAuthorities(principal.getId());
			if (authorities != null) {
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermissions(authorities);//将权限添加到authorizationInfo
				return authorizationInfo;
			}
		}
		//return null，用户访问需要的页面会自动跳转到 unauthorizedUrl 指定的页面
		return null;
	}

}