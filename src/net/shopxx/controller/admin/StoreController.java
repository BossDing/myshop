/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Message;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Ad;
import net.shopxx.entity.AdPosition;
import net.shopxx.entity.Admin;
import net.shopxx.entity.Navigation;
import net.shopxx.entity.Order;
import net.shopxx.entity.Payment;
import net.shopxx.entity.PluginConfig;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Store;
import net.shopxx.service.AdPositionService;
import net.shopxx.service.AdService;
import net.shopxx.service.AdminService;
import net.shopxx.service.AreaService;
import net.shopxx.service.NavigationService;
import net.shopxx.service.OrderService;
import net.shopxx.service.PaymentService;
import net.shopxx.service.PluginConfigService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.StoreService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 店铺
 * 
 * @author: Guoxianlong
 * @date: Oct 11, 2014  11:21:20 AM
 */
@Controller("adminStoreController")
@RequestMapping("/admin/store")
public class StoreController extends BaseController {

	@Resource(name = "storeServiceImpl")
	private StoreService storeService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	/** 导航*/
	@Resource(name = "navigationServiceImpl")
	private NavigationService navigationService;
	
	/** 广告*/
	@Resource(name = "adServiceImpl")
	private AdService adService;
	
	/** 广告位*/
	@Resource(name = "adPositionServiceImpl")
	private AdPositionService adPositionService;
	
	/** 订单*/
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	
	/** 收款单*/
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	
	/** 商品*/
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	/** 分类*/
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	/** 账号*/
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	/** 支付宝插件*/
	@Resource(name = "pluginConfigServiceImpl")
	private PluginConfigService pluginConfigService;
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		Store store = WebUtils.getStore();
		Page<Store> page = null;
		Object hasStore = store == null ? false : true;
		if (store == null) {
			page = storeService.findPage(pageable);
		} else {
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(new Filter("id", Filter.Operator.eq, store.getId()));
			page = storeService.findPage(filters, null, pageable);
		}
		model.addAttribute("page", page);
		model.addAttribute("hasStore",  hasStore);
		return "/admin/store/list";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/store/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Store store, Long areaId, RedirectAttributes redirectAttributes) {
		store.setArea(areaService.find(areaId));
		store.setAreaName(areaService.find(areaId).getFullName());
		store.setCreateDate(new Date()); // 设置创建日期
		store.setCheckStatus(Store.CheckStatus.wait); // 设置申请状态：待审核
		storeService.save(store);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("store",storeService.find(id));
		
		/** 判断编辑店铺的是管理员还是店铺人员 
		 * 	wmd 2014/12/2
		 */
		Store store = WebUtils.getStore();
		if(store != null){
			model.addAttribute("isStore", true);
		}
		return "/admin/store/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Store store, Long areaId, RedirectAttributes redirectAttributes) {
		store.setArea(areaService.find(areaId));
		store.setAreaName(areaService.find(areaId).getFullName());
		
		Store pStore = storeService.find(store.getId());
		store.setCreateDate(pStore.getCreateDate());
		
		if (store.getIsEnabled()){
			store.setCheckStatus(Store.CheckStatus.success); // 设置申请状态：通过
		} else {
			store.setCheckStatus(pStore.getCheckStatus());
		}
		store.setModifyDate(new Date());
		storeService.update(store);
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids.length >= storeService.count()) {
			return Message.error("admin.common.deleteAllNotAllowed");
		}
		/** 遍历ids集合，逐个删除对应的信息*/
		for(Long id: ids){
			Store store = storeService.find(id);
			
			/** 删除广告*/
			List<Ad> ads = adService.findAdForDeleteStore(store);
			if(ads.size() > 0){
				for(Ad ad: ads){
					adService.delete(ad);
				}
			}
			
			/** 删除广告位*/
			List<AdPosition> adps = adPositionService.findListForDeleteStore(store);
			if(adps.size() > 0){
				for(AdPosition adp: adps){
					adPositionService.delete(adp);
				}
			}
			
			/** 删除导航*/
			List<Navigation> ngs = navigationService.findListForDeleteStore(store);
			if(ngs.size() > 0){
				for(Navigation ng: ngs){
					navigationService.delete(ng);
				}
			}
			
			/** 删除订单*/
			List<Order> ods = orderService.findListForDeleteStore(store);
			if(ods.size() > 0){
				for(Order od: ods){
					orderService.delete(od);
				}
			}
			
			/** 删除收款单*/
			List<Payment> pms = paymentService.findListForDeleteStore(store);
			if(pms.size() > 0){
				for(Payment pm: pms){
					paymentService.delete(pm);
				}
			}
			
			/** 删除分类*/
			List<ProductCategory> pcs = productCategoryService.findListForDeleteStore(store);
			if(pcs.size() > 0){
				for(ProductCategory pc: pcs){
					productCategoryService.delete(pc);
				}
			}
			
			/** 删除商品*/
			List<Product> pds = productService.findListForDeleteStore(store);
			if(pds.size() > 0){
				for(Product pd: pds){
					productService.delete(pd);
				}
			}
			
			/** 删除账号*/
			Admin admin = adminService.findAdminForDeleteStore(store);
			if(admin != null){
				adminService.delete(admin);
			}
			
			/** 删除支付宝插件*/
			List<PluginConfig> pcfs = pluginConfigService.findListForDeleteStore(store);
			if(pcfs.size() > 0){
				for(PluginConfig pcf: pcfs){
					pluginConfigService.delete(pcf);
				}
			}
			
			/** 删除店铺*/
			storeService.delete(store);
			
		}
		storeService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 审核
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody
	Message check(Long id, String status) {
		Store store = storeService.find(id);
		if (store == null) {
			return Message.error("操作失败，店铺不存在！");
		}
		if (store.getCheckStatus().equals(Store.CheckStatus.wait)){
			storeService.check(id, status);
		} else {
			return Message.warn("该店铺已审核，请勿重复操作");
		}
		return SUCCESS_MESSAGE;
	}
	
}