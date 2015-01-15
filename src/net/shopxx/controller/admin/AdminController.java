/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Admin;
import net.shopxx.entity.BaseEntity.Save;
import net.shopxx.entity.Role;
import net.shopxx.service.AdminService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.RoleService;
import net.shopxx.service.StoreService;
import net.shopxx.util.WebUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 管理员
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminAdminController")
@RequestMapping("/admin/admin")
public class AdminController extends BaseController {

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;
	@Resource(name = "storeServiceImpl")
	private StoreService storeService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	/**
	 * 检查用户名是否存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (adminService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("productcategorys",productCategoryService.findRootsByEntcode(null, WebUtils.getXentcode()));
		return "/admin/admin/add";
	}
    
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Admin admin, Long[] roleIds,Long[] productcategoryids, Long storeId, RedirectAttributes redirectAttributes) {
		admin.setRoles(new HashSet<Role>(roleService.findList(roleIds)));
		if (!isValid(admin, Save.class)) {
			return ERROR_VIEW;
		}
		if (adminService.usernameExists(admin.getUsername())) {
			return ERROR_VIEW;
		}
		List<String> productcategorys = new ArrayList<String>();
		if (productcategoryids != null) {
			for(int i=0;i<productcategoryids.length;i++){
				productcategorys.add(i, productcategoryids[i].toString());
			}
		}
		admin.setProductcategory(productcategorys);
		admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		admin.setIsLocked(false);
		admin.setLoginFailureCount(0);
		admin.setLockedDate(null);
		admin.setLoginDate(null);
		admin.setLoginIp(null);
		admin.setOrders(null);
		admin.setEntcode(WebUtils.getXentcode());
		if (storeId != null) {
			admin.setStore(storeService.find(storeId));	//店铺
		}
		adminService.save(admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("admin", adminService.find(id));
		StringBuffer check = new StringBuffer();
		for(int i=0;i<adminService.find(id).getProductcategory().size();i++){
			check.append(adminService.find(id).getProductcategory().get(i).toString());
		}  
		model.addAttribute("check", check.toString());
		model.addAttribute("productcategorys", productCategoryService.findRootsByEntcode(null, WebUtils.getXentcode()));
		return "/admin/admin/edit";
	}   
   
	/**    
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Admin admin, Long[] roleIds, Long[] productcategoryids,Long storeId, RedirectAttributes redirectAttributes) {
		admin.setRoles(new HashSet<Role>(roleService.findList(roleIds)));
		List<String> productcategorys = new ArrayList<String>();
		if (productcategoryids != null) {
			for(int i=0;i<productcategoryids.length;i++){
				productcategorys.add(i, productcategoryids[i].toString());
			}
		}
		admin.setProductcategory(productcategorys);
		if (!isValid(admin)) {
			return ERROR_VIEW;
		}
		Admin pAdmin = adminService.find(admin.getId());
		if (pAdmin == null) {
			return ERROR_VIEW;
		}
		if (storeId == null) {
			admin.setStore(pAdmin.getStore());
		} else {
			admin.setStore(storeService.find(storeId));	//店铺
		}
		if (StringUtils.isNotEmpty(admin.getPassword())) {
			admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		} else {
			admin.setPassword(pAdmin.getPassword());
		}
		if (pAdmin.getIsLocked() && !admin.getIsLocked()) {
			admin.setLoginFailureCount(0);
			admin.setLockedDate(null);
		} else {
			admin.setIsLocked(pAdmin.getIsLocked());
			admin.setLoginFailureCount(pAdmin.getLoginFailureCount());
			admin.setLockedDate(pAdmin.getLockedDate());
		}
		adminService.update(admin, "username", "loginDate", "loginIp", "orders", "entcode");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", adminService.findPage(pageable));
		return "/admin/admin/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids.length >= adminService.count()) {
			return Message.error("admin.common.deleteAllNotAllowed");
		}
		adminService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}