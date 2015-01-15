/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import net.shopxx.Setting;
import net.shopxx.entity.Admin;
import net.shopxx.entity.ProductNotify;
import net.shopxx.entity.SafeKey;
import net.shopxx.entity.Store;
import net.shopxx.service.MailService;
import net.shopxx.service.TemplateService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.SpringUtils;

import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Service - 邮件
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("mailServiceImpl")
public class MailServiceImpl implements MailService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "javaMailSender")
	private JavaMailSenderImpl javaMailSender;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;

	/**
	 * 添加邮件发送任务
	 * 
	 * @param mimeMessage
	 *            MimeMessage
	 */
	private void addSendTask(final MimeMessage mimeMessage) {
		try {
			taskExecutor.execute(new Runnable() {
				public void run() {
					javaMailSender.send(mimeMessage);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Assert.hasText(smtpFromMail);
		Assert.hasText(smtpHost);
		Assert.notNull(smtpPort);
		Assert.hasText(smtpUsername);
		Assert.hasText(smtpPassword);
		Assert.hasText(toMail);
		Assert.hasText(subject);
		Assert.hasText(templatePath);
		try {
//			System.out.println("进入shop MailServiceImpl send");
//			System.out.println("----配置信息----");
//			System.out.println("smtpFromMail："+smtpFromMail);
//			System.out.println("smtpHost："+smtpHost);
//			System.out.println("smtpPort："+smtpPort);
//			System.out.println("smtpUsername："+smtpUsername);
//			System.out.println("smtpPassword："+smtpPassword);
//			System.out.println("--------接收信息---------");
//			System.out.println("toMail："+toMail);
//			System.out.println("subject："+subject);
//			System.out.println("templatePath："+templatePath);
//			System.out.println("async："+async);
			Setting setting = SettingUtils.get();
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			javaMailSender.setHost(smtpHost);
			javaMailSender.setPort(smtpPort);
			javaMailSender.setUsername(smtpUsername);
			javaMailSender.setPassword(smtpPassword);
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(setting.getSiteName()) + " <" + smtpFromMail + ">");
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setText(text, true);
//			System.out.println("模板内容："+text);
			if (async) {
				addSendTask(mimeMessage);
//				System.out.println("异步发送，退出shop MailServiceImpl send");
			} else {
				javaMailSender.send(mimeMessage);
//				System.out.println("同步发送，退出shop MailServiceImpl send");
			}
		} catch (TemplateException e) {
//			System.out.println("异常:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("异常:" + e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
//			System.out.println("异常:" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void send(String toMail, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Setting setting = SettingUtils.get();
		send(setting.getSmtpFromMail(), setting.getSmtpHost(), setting.getSmtpPort(), setting.getSmtpUsername(), setting.getSmtpPassword(), toMail, subject, templatePath, model, async);
	}

	public void send(String toMail, String subject, String templatePath, Map<String, Object> model) {
//		System.out.println("2");
		Setting setting = SettingUtils.get();
		send(setting.getSmtpFromMail(), setting.getSmtpHost(), setting.getSmtpPort(), setting.getSmtpUsername(), setting.getSmtpPassword(), toMail, subject, templatePath, model, true);
//		System.out.println("22");
	}

	public void send(String toMail, String subject, String templatePath) {
		Setting setting = SettingUtils.get();
		send(setting.getSmtpFromMail(), setting.getSmtpHost(), setting.getSmtpPort(), setting.getSmtpUsername(), setting.getSmtpPassword(), toMail, subject, templatePath, null, true);
	}

	public void sendTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail) {
		Setting setting = SettingUtils.get();
		String subject = SpringUtils.getMessage("admin.setting.testMailSubject", setting.getSiteName());
		net.shopxx.Template testMailTemplate = templateService.get("testMail");
		send(smtpFromMail, smtpHost, smtpPort, smtpUsername, smtpPassword, toMail, subject, testMailTemplate.getTemplatePath(), null, false);
	}

	public void sendFindPasswordMail(String toMail, String username, SafeKey safeKey) {
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", username);
		model.put("safeKey", safeKey);
		String subject = SpringUtils.getMessage("shop.password.mailSubject", setting.getSiteName());
		net.shopxx.Template findPasswordMailTemplate = templateService.get("findPasswordMail");
		send(toMail, subject, findPasswordMailTemplate.getTemplatePath(), model);
	} 
	
	public void sendUpdateEMail(String toMail, String username, SafeKey safeKey) {
		System.out.println("1");
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", username);
		model.put("safeKey", safeKey);
		String subject = SpringUtils.getMessage("shop.password.mailSubject", setting.getSiteName());
		net.shopxx.Template findPasswordMailTemplate = templateService.get("UpdateEMail");
		send(toMail, subject, findPasswordMailTemplate.getTemplatePath(), model);
		System.out.println("n");
	} 

	public void sendProductNotifyMail(ProductNotify productNotify) {
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productNotify", productNotify);
		String subject = SpringUtils.getMessage("admin.productNotify.mailSubject", setting.getSiteName());
		net.shopxx.Template productNotifyMailTemplate = templateService.get("productNotifyMail");
		send(productNotify.getEmail(), subject, productNotifyMailTemplate.getTemplatePath(), model);
	}

	@Override
	public void sendApplyStoreFailureNofyMail(Store store) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("status", false);
		model.put("store", store);
		net.shopxx.Template template = templateService.get("applyStoreNotify");
		String subject = template.getName();
		send(store.getEmail(), subject, template.getTemplatePath(), model, true);
	}

	@Override
	public void sendApplyStoreSuccessNofyMail(Admin admin, String password) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("status", true);
		model.put("admin", admin);
		model.put("password", password);
		net.shopxx.Template template = templateService.get("applyStoreNotify");
		String subject = template.getName();
		send(admin.getEmail(), subject, template.getTemplatePath(), model, true);
	}

}