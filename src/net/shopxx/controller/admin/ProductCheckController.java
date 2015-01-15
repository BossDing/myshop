/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.ParameterGroup;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Store;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.entity.Tag.Type;
import net.shopxx.service.AreaService;
import net.shopxx.service.BrandService;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.SpecificationService;
import net.shopxx.service.TagService;
import net.shopxx.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 商品审核
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminProductCheckController")
@RequestMapping("/admin/product_check")
public class ProductCheckController extends BaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	
	/**
	 * 审核商品
	 * 
	 * @param id
	 * @param checkStatus
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody
	Message checkStoreProduct(Long id, String checkStatus) {

		if (StringUtils.isEmpty(checkStatus)) {
			return Message.error("数据异常，请重新操作");
		}

		Product product = productService.find(id);

		if (!product.getCheckStatus().equals(Product.CheckStatus.waitting)) {
			return Message.error("数据异常，请重新操作");
		}

		if ("F".equals(checkStatus.trim())) {
			product.setCheckStatus(Product.CheckStatus.failure);
		} else if ("T".equals(checkStatus.trim())) {
			product.setCheckStatus(Product.CheckStatus.success);
			product.setIsMarketable(true);//审核通过，默认上架
		} else {
			return Message.error("数据异常，请重新操作");
		}

		productService.save(product);

		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 需要审核的商品列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String checkList(Long productCategoryId, Long brandId, Long promotionId,
			Long tagId, Boolean isMarketable, Boolean isList, Boolean isTop,
			Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert,
			Pageable pageable, ModelMap model) {
		try {
			ProductCategory productCategory = productCategoryService
					.find(productCategoryId);
			Brand brand = brandService.find(brandId);
			Promotion promotion = promotionService.find(promotionId);
			List<Tag> tags = tagService.findList(tagId);
			model.addAttribute("productCategoryTree", productCategoryService
					.findTree());
			model.addAttribute("brands", brandService.findAll());
			model.addAttribute("promotions", promotionService.findAll());
			model.addAttribute("tags", tagService.findList(Type.product));
			model.addAttribute("productCategoryId", productCategoryId);
			model.addAttribute("brandId", brandId);
			model.addAttribute("promotionId", promotionId);
			model.addAttribute("tagId", tagId);
			model.addAttribute("isMarketable", isMarketable);
			model.addAttribute("isList", isList);
			model.addAttribute("isTop", isTop);
			model.addAttribute("isGift", isGift);
			model.addAttribute("isOutOfStock", isOutOfStock);
			model.addAttribute("isStockAlert", isStockAlert);
			model.addAttribute("store", WebUtils.getStore());
			//查询待审核商品
			model.addAttribute("page", productService.findPageByEntcodeForCheck(
					productCategory, brand, promotion, tags, null, null, null,
					isMarketable, isList, isTop, isGift, isOutOfStock,
					isStockAlert, OrderType.dateDesc, pageable));
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return "/admin/product_check/list";
	}
	

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		Product p = productService.find(id);
		Store store = WebUtils.getStore();
		model.addAttribute("store", store);
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		model.addAttribute("attributes", attributes(p.getProductCategory()
				.getId()));
		model.addAttribute("parameterGroups", parameterGroups(p
				.getProductCategory().getId()));
		model.addAttribute("product", p);
		model.addAttribute("areas", areaService.findRoots());
		return "/admin/product_check/view";
	}
	
	/**
	 * 获取属性
	 */
	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	public @ResponseBody
	Set<Attribute> attributes(Long id) {
		Set<Attribute> set = null;
		ProductCategory productCategory = productCategoryService.find(id);
		if (productCategory.getAttributes() == null
				|| productCategory.getAttributes().size() <= 0) {
			List<ProductCategory> list = productCategoryService
					.findParents(productCategory);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ProductCategory p = (ProductCategory) iterator.next();
				if (p.getAttributes() != null && p.getAttributes().size() > 0) {
					set = p.getAttributes();
				}
			}
		} else {
			set = productCategory.getAttributes();
		}
		return set;
	}
	

	/**
	 * 获取参数组
	 */
	@RequestMapping(value = "/parameter_groups", method = RequestMethod.GET)
	public @ResponseBody
	Set<ParameterGroup> parameterGroups(Long id) {
		Set<ParameterGroup> set = null;
		ProductCategory productCategory = productCategoryService.find(id);
		if (productCategory.getParameterGroups() == null
				|| productCategory.getParameterGroups().size() <= 0) {
			List<ProductCategory> list = productCategoryService
					.findParents(productCategory);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ProductCategory p = (ProductCategory) iterator.next();
				if (p.getAttributes() != null
						&& p.getParameterGroups().size() > 0) {
					set = p.getParameterGroups();
				}
			}
		} else {
			set = productCategory.getParameterGroups();
		}
		return set;
	}
}