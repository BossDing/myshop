/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.app;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.shopxx.Setting;
import net.shopxx.plugin.StoragePlugin;
import net.shopxx.service.PluginService;
import net.shopxx.util.FreemarkerUtils;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.TwUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Controller - 文件处理
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("AppFileController")
@RequestMapping("/m/file")
public class FileController extends BaseController {
	
	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;

	/**
	 * 图片上传
	 */
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public @ResponseBody ModelMap upload(HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		String uploadPath;
		String iconUrl = null;
		try {
			model = new ModelMap();
			Setting setting = SettingUtils.get();
//			uploadPath = setting.getMobileImageUploadPath();
			uploadPath = setting.getImageUploadPath();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreemarkerUtils.process(uploadPath, model);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("iconfile");
			
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			System.out.println("destPath: "+destPath);
			
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
				if (!tempFile.getParentFile().exists()) {
					tempFile.getParentFile().mkdirs();
				}
				multipartFile.transferTo(tempFile);
				try {
					storagePlugin.upload(destPath, tempFile, multipartFile.getContentType());
				} finally {
					FileUtils.deleteQuietly(tempFile);
				}
				iconUrl = storagePlugin.getUrl(destPath);
				System.out.println("path: "+storagePlugin.getUrl(destPath));
			}
			model.put("success", 2);
			model.put("iconurl", iconUrl);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("sucess", "1");
			model.put("message", "error");
			model.put("error", e.getMessage());
		}
		return model;
	}
}