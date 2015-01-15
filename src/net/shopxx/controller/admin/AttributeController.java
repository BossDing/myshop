/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Parameter;
import net.shopxx.entity.Product;
import net.shopxx.entity.BaseEntity.Save;
import net.shopxx.service.AttributeService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 属性
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminAttributeController")
@RequestMapping("/admin/attribute")
public class AttributeController extends BaseController {

	@Resource(name = "attributeServiceImpl")
	private AttributeService attributeService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("attributeValuePropertyCount", Product.ATTRIBUTE_VALUE_PROPERTY_COUNT);
		return "/admin/attribute/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Attribute attribute, Long productCategoryId, RedirectAttributes redirectAttributes) {
		for (Iterator<String> iterator = attribute.getOptions().iterator(); iterator.hasNext();) {
			String option = iterator.next();
			if (StringUtils.isEmpty(option)) {
				iterator.remove();
			}
		}
		List<Product> products = productService.findList(productCategoryService.find(productCategoryId), null, null, null, null);
		ArrayList<String> arr = new ArrayList<String>();
		for (Product product : products) {
			Map<Parameter, String> parameterValue = product.getParameterValue();
			Set<Parameter> keySet = parameterValue.keySet();
			int index = 0;
			for(Parameter parameter:keySet){
			    if(attribute.getName().equals(parameter.getName())){
			    	arr.add(index, parameterValue.get(parameter));
			    	index++;
			    }
			}
		} 
		List<String> list = new ArrayList<String>();
        for(int i=0; i<arr.size(); i++){
            String str = arr.get(i);  
            if(!list.contains(str)){   
                list.add(str);
            }
        }
        for(int i=0;i<list.size();i++){
        	attribute.getOptions().add(i,list.get(i));
        }
		attribute.setProductCategory(productCategoryService.find(productCategoryId));
		if (!isValid(attribute, Save.class)) {
			//return ERROR_VIEW;  
		}
		if (attribute.getProductCategory().getAttributes().size() >= Product.ATTRIBUTE_VALUE_PROPERTY_COUNT) {
			addFlashMessage(redirectAttributes, Message.error("admin.attribute.addCountNotAllowed", Product.ATTRIBUTE_VALUE_PROPERTY_COUNT));
		} else {
			attribute.setPropertyIndex(null);
			attributeService.save(attribute);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		}
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("attributeValuePropertyCount", Product.ATTRIBUTE_VALUE_PROPERTY_COUNT);
		model.addAttribute("attribute", attributeService.find(id));
		return "/admin/attribute/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Attribute attribute, RedirectAttributes redirectAttributes) {
		for (Iterator<String> iterator = attribute.getOptions().iterator(); iterator.hasNext();) {
			String option = iterator.next();
			if (StringUtils.isEmpty(option)) {
				iterator.remove();
			}
		}
		if (!isValid(attribute)) {
			return ERROR_VIEW;
		}
		attributeService.update(attribute, "propertyIndex", "productCategory");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", attributeService.findPage(pageable));
		return "/admin/attribute/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		attributeService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}