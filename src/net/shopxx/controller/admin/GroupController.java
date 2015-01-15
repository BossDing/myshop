/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Ad;
import net.shopxx.entity.GroupPurchase;
import net.shopxx.entity.Product;
import net.shopxx.entity.Ad.Type;
import net.shopxx.service.GroupService;
import net.shopxx.service.ProductService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 团购
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("groupController")
@RequestMapping("/admin/group")
public class GroupController extends BaseController {
 
	@Resource(name = "groupServiceImpl")
	private GroupService groupService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	
	/**
	 * 商品选择
	 */
	@RequestMapping(value = "/product_select", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> productSelect(String q) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<Product> products = productService.search(q, false, 20);
			for (Product product : products) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", product.getId());
				map.put("sn", product.getSn());
				map.put("fullName", product.getFullName());
				map.put("path", product.getPath());
				data.add(map);
			}
		}
		return data;
	}
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		System.out.println("========list========");
		try {
//			System.out.println(groupService.findPage(pageable).getContent().get(0).getName());
			model.addAttribute("page", groupService.findPage(pageable));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/admin/group/list";
	}
	
	/**
	 *去添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		System.out.println("====group====add========");
//		model.addAttribute("types",net.shopxx.entity.GroupPurchase.Type.values());
		return "/admin/group/add";
	}
	 
	/** 
	 * 保存
	 */ 
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public String save(GroupPurchase group,String sn, RedirectAttributes redirectAttributes) {
//		System.out.println("=====group======save===========");
//		Product productt=new Product();  
//		if(sn!=null && !sn.equals("")){ 
//		    productt=productService.findBySn(sn.trim());
//		} 
//		if (!isValid(group)) { 
//			return ERROR_VIEW; 
//		}
//		if ((group.getPrbegindate() != null && group.getPrenddate() != null && group.getPrbegindate().after(group.getPrenddate()))||(group.getBegindate()!=null && group.getEnddate()!=null && group.getBegindate().after(group.getEnddate()))||(group.getPrenddate().after(group.getBegindate()))) {
//			return ERROR_VIEW;
//		}
//		if(productt!=null){
//			group.setProduct(productt);
//		}
//		try {
//			groupService.save(group);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
//		return "redirect:list.jhtml";
//	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(GroupPurchase group,Long[] productIds, RedirectAttributes redirectAttributes){
		System.out.println("group---save--");
		//hfh 2014/9/17 根据参团人数更改团购价
		try {
			if(group.getBuycount()>=group.getWantcount1()&&group.getBuycount()<group.getWantcount2()){
				group.setPurchasePrice(group.getPurchasePrice1());
			}else if(group.getBuycount()>=group.getWantcount2()&&group.getBuycount()<group.getWantcount3()){
				group.setPurchasePrice(group.getPurchasePrice2());
			}else if(group.getBuycount()>=group.getWantcount3()&&group.getBuycount()<group.getWantcount4()){
				group.setPurchasePrice(group.getPurchasePrice3());
			}else if(group.getBuycount()>=group.getWantcount4()&&group.getBuycount()<group.getWantcount5()){
				group.setPurchasePrice(group.getPurchasePrice4());
			}else if(group.getBuycount()>=group.getWantcount5()){
				group.setPurchasePrice(group.getPurchasePrice5());
			}else{
				group.setPurchasePrice(group.getPurchasePrice1());
			}
			System.out.println("admin--save--purchasePrice="+group.getPurchasePrice());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
//		Product productt=new Product();  
//		System.out.println("sn="+sn);
		System.out.println("productIds="+productIds);
//		if(sn!=null && !sn.equals("")){ 
//		    productt=productService.findBySn(sn.trim());
//		} 
		for (Product product : productService.findList(productIds)) {
			if (!product.getIsGift()) {
				group.getProducts().add(product);
			}
		}
		if (!isValid(group)) { 
			return ERROR_VIEW; 
		}
		if ((group.getPrbegindate() != null && group.getPrenddate() != null && group.getPrbegindate().after(group.getPrenddate()))||(group.getBegindate()!=null && group.getEnddate()!=null && group.getBegindate().after(group.getEnddate()))||(group.getPrenddate().after(group.getBegindate()))) {
			return ERROR_VIEW;
		}
//		if(productt!=null){
//			group.setProduct(productt);
//		}
		try {
			groupService.save(group);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		System.out.println("=====group======edit===========");
		System.out.println("-----编辑-------"+id);
//		model.addAttribute("types",net.shopxx.entity.GroupPurchase.Type.values());
		model.addAttribute("group", groupService.find(id));
		return "/admin/group/edit"; 
	}
    
	/**
	 * 更新 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(GroupPurchase group,String sn, Long[] productIds,RedirectAttributes redirectAttributes) {
		System.out.println("------group----update-----------");
		//hfh 2014/9/17 根据参团人数更改团购价
		if(group.getBuycount()>=group.getWantcount1()&&group.getBuycount()<group.getWantcount2()){
			group.setPurchasePrice(group.getPurchasePrice1());
		}else if(group.getBuycount()>=group.getWantcount2()&&group.getBuycount()<group.getWantcount3()){
			group.setPurchasePrice(group.getPurchasePrice2());
		}else if(group.getBuycount()>=group.getWantcount3()&&group.getBuycount()<group.getWantcount4()){
			group.setPurchasePrice(group.getPurchasePrice3());
		}else if(group.getBuycount()>=group.getWantcount4()&&group.getBuycount()<group.getWantcount5()){
			group.setPurchasePrice(group.getPurchasePrice4());
		}else if(group.getBuycount()>=group.getWantcount5()){
			group.setPurchasePrice(group.getPurchasePrice5());
		}else{
			group.setPurchasePrice(group.getPurchasePrice1());
		}
		System.out.println("admin--update--purchasePrice="+group.getPurchasePrice());
		group.setProduct(productService.findBySn(sn));
		for (Product product : productService.findList(productIds)) {
			if (!product.getIsGift()) {
				group.getProducts().add(product);
			}
		}
		if (!isValid(group)) {
			return ERROR_VIEW;
		}
		if (!isValid(group)) { 
			return ERROR_VIEW; 
		}
		if ((group.getPrbegindate() != null && group.getPrenddate() != null 
				&& group.getPrbegindate().after(group.getPrenddate()))
				||(group.getBegindate()!=null && group.getEnddate()!=null 
						&& group.getBegindate().after(group.getEnddate()))||(group.getPrenddate().after(group.getBegindate()))) {
			return ERROR_VIEW;
		}
		try {
			groupService.update(group);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除  
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		System.out.println("-----group----del----------");
		groupService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}