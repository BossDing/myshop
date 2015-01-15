package net.shopxx.controller.gw;

/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.entity.ActionType;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Member;
import net.shopxx.entity.ParameterGroup;
import net.shopxx.entity.Product;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Tag;
import net.shopxx.service.ActionTypeService;
import net.shopxx.service.BrandService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.SearchService;
import net.shopxx.service.TagService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 商品
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("gwProductController")
@RequestMapping("/gw/product")
public class ProductController extends BaseController {
	
	private static final int DEFAULT_PAGE_SIZE = 12;

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
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;
	@Resource(name = "actionTypeServiceImpl")
	private ActionTypeService actionTypeService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	/**
	 * 浏览记录
	 */
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public @ResponseBody
	List<Product> history(Long[] ids) {
		return productService.findList(ids);
	}
	
	
	
	/**
	 * 点击商品属性获取结果
	 */
	@RequestMapping(value = "/slist/{productCategoryId}", method = RequestMethod.GET)
	public String slist(@PathVariable Long productCategoryId, Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, 
														BigDecimal endPrice, OrderType orderType,Boolean isOutOfStock, Integer pageNumber, Integer pageSize, 
														HttpServletRequest request, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		
		List<ProductCategory> childrenpCategory = productCategoryService.findChildrenByParent(productCategoryId);
		if (productCategory == null) {
			throw new ResourceNotFoundException();  
		}
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);
		Map<Attribute, String> attributeValue = new HashMap<Attribute, String>();
		if (productCategory != null) {
			Set<Attribute> attributes = productCategory.getAttributes();
			for (Attribute attribute : attributes) {
				String value = request.getParameter("attribute_" + attribute.getId());
				if (StringUtils.isNotEmpty(value) && attribute.getOptions().contains(value)) {
					attributeValue.put(attribute, value);
				}
			}
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("productCategory", productCategory);
		model.addAttribute("childrenpCategory", childrenpCategory);
		model.addAttribute("brand", brand);
		model.addAttribute("promotion", promotion);
		model.addAttribute("tags", tags);
		model.addAttribute("isOutOfStock", isOutOfStock);
		model.addAttribute("attributeValue", attributeValue);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", 
				productService.findPage(productCategory, brand, promotion, 
														tags, attributeValue, startPrice, endPrice,
														true, true, null, false, isOutOfStock, null, orderType, pageable));
		return "/gw/product/slist";
	}
	
	
	
	/**
	 * 官网二级分类列表
	 */
	@RequestMapping(value = "/plist/{productCategoryId}", method = RequestMethod.GET)
	public String plist(@PathVariable Long productCategoryId, Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, 
														BigDecimal endPrice, OrderType orderType,Boolean isOutOfStock, Integer pageNumber, Integer pageSize, 
														HttpServletRequest request, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		
		List<ProductCategory> childrenpCategory = productCategoryService.findChildrenByParent(productCategoryId);
		if (productCategory == null) {
			throw new ResourceNotFoundException();  
		}
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);
		Map<Attribute, String> attributeValue = new HashMap<Attribute, String>();
		if (productCategory != null) {
//			System.out.println("treePath=========="+productCategory.getTreePath());
			if(productCategory.getTreePath().length()>=10){
				String treePath = productCategory.getTreePath().substring(6, 10);
//				System.out.println("treePath=========="+productCategory.getTreePath().substring(6, 10));
//				if(treePath.equals("1080")||treePath.equals("1082")){
					model.addAttribute("treePath", treePath);
//				}
			}
			Set<Attribute> attributes = productCategory.getAttributes();
			for (Attribute attribute : attributes) {
				String value = request.getParameter("attribute_" + attribute.getId());
				if (StringUtils.isNotEmpty(value) && attribute.getOptions().contains(value)) {
					attributeValue.put(attribute, value);
				}
			}
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("productCategory", productCategory);
		model.addAttribute("childrenpCategory", childrenpCategory);
		model.addAttribute("brand", brand);
		model.addAttribute("promotion", promotion);
		model.addAttribute("tags", tags);
		model.addAttribute("isOutOfStock", isOutOfStock);
		model.addAttribute("attributeValue", attributeValue);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", 
				productService.findPageGW(productCategory, brand, promotion, 
														tags, attributeValue, startPrice, endPrice,
														true, true, null, false, isOutOfStock, null, orderType, pageable));
//		System.out.println("plist电热水器="+productService.findPage(productCategory, brand, promotion, 
//														tags, attributeValue, startPrice, endPrice,
//														true, true, null, false, isOutOfStock, null, orderType, pageable).getTotal());
		return "/gw/product/plist";
	}
	
	/**
	 * 官网三级分类列表
	 */
	@RequestMapping(value = "/plist2/{productCategoryId}", method = RequestMethod.GET)
	public String plist2(@PathVariable Long productCategoryId, Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, 
														BigDecimal endPrice, OrderType orderType,Boolean isOutOfStock, Integer pageNumber, Integer pageSize, 
														HttpServletRequest request, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		
		List<ProductCategory> childrenpCategory = productCategoryService.findChildrenByParent(productCategoryId);
		if (productCategory == null) {
			throw new ResourceNotFoundException();  
		}
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);
		Map<Attribute, String> attributeValue = new HashMap<Attribute, String>();
		if (productCategory != null) {
			Set<Attribute> attributes = productCategory.getAttributes();
			for (Attribute attribute : attributes) {
				String value = request.getParameter("attribute_" + attribute.getId());
				if (StringUtils.isNotEmpty(value) && attribute.getOptions().contains(value)) {
					attributeValue.put(attribute, value);
				}
			}
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("productCategory", productCategory);
		model.addAttribute("childrenpCategory", childrenpCategory);
		model.addAttribute("brand", brand);
		model.addAttribute("promotion", promotion);
		model.addAttribute("tags", tags);
		model.addAttribute("isOutOfStock", isOutOfStock);
		model.addAttribute("attributeValue", attributeValue);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", 
				productService.findPage(productCategory, brand, promotion, 
														tags, attributeValue, startPrice, endPrice,
														true, true, null, false, isOutOfStock, null, orderType, pageable));
		return "/gw/product/plist2";
	}
	
	/**
	 * 官网四级分类列表
	 */
	@RequestMapping(value = "/pplist/{productCategoryId}", method = RequestMethod.GET)
	public String pplist(@PathVariable Long productCategoryId, Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, 
														BigDecimal endPrice, OrderType orderType,Boolean isOutOfStock, Integer pageNumber, Integer pageSize, 
														HttpServletRequest request, ModelMap model) {
		System.out.println("productcontroller----pplist");
		try {
			
		
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		System.out.println("productCategory:"+productCategory.getName());
		List<ProductCategory> childrenpCategory = productCategoryService.findChildrenByParent(productCategoryId);
		if (productCategory == null) {
			throw new ResourceNotFoundException();  
		}
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);
		Map<Attribute, String> attributeValue = new HashMap<Attribute, String>();
		if (productCategory != null) {
			Set<Attribute> attributes = productCategory.getAttributes();
			for (Attribute attribute : attributes) {
				String value = request.getParameter("attribute_" + attribute.getId());
				if (StringUtils.isNotEmpty(value) && attribute.getOptions().contains(value)) {
					attributeValue.put(attribute, value);
				}
			}
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("productCategory", productCategory);
		model.addAttribute("childrenpCategory", childrenpCategory);
		model.addAttribute("brand", brand);
		model.addAttribute("promotion", promotion);
		model.addAttribute("tags", tags);
		model.addAttribute("isOutOfStock", isOutOfStock);
		model.addAttribute("attributeValue", attributeValue);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		Page<Product> page = productService.findPageGW(productCategory, brand, promotion, 
				tags, attributeValue, startPrice, endPrice,
				true, true, null, false, isOutOfStock, null, orderType, pageable);
		System.out.println("page:"+page.getContent().size());
		model.addAttribute("page", page);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		System.out.println("pplist电热水器="+productService.findPage(productCategory, brand, promotion, 
//														tags, attributeValue, startPrice, endPrice,
//														true, true, null, false, isOutOfStock, null, orderType, pageable).getTotal());
		return "/gw/product/pplist";
	}
	
	
	/**
	 * 获取参数组
	 */
	@RequestMapping(value = "/parameter_groups", method = RequestMethod.GET)
	public @ResponseBody
	Set<ParameterGroup> parameterGroups(Long id) {
		Set<ParameterGroup> set = null;
		ProductCategory productCategory = productCategoryService.find(id);
		if(productCategory.getParameterGroups()==null || productCategory.getParameterGroups().size()<=0){
			List<ProductCategory> list = productCategoryService.findParents(productCategory);
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ProductCategory p = (ProductCategory) iterator.next();
				if(p.getAttributes()!=null && p.getParameterGroups().size()>0){
					set = p.getParameterGroups();
				}
			}
		}else{
			set = productCategory.getParameterGroups();
		}
		return set;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list/{productCategoryId}", method = RequestMethod.GET)
	public String list(@PathVariable Long productCategoryId, Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, 
														BigDecimal endPrice, OrderType orderType,Boolean isOutOfStock, Integer pageNumber, Integer pageSize, 
														HttpServletRequest request, ModelMap model) {
		System.out.println("=====isOutOfStock===="+isOutOfStock);
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		
		List<ProductCategory> childrenpCategory = productCategoryService.findChildrenByParent(productCategoryId);
		if (productCategory == null) {
			throw new ResourceNotFoundException();  
		}
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);
		Map<Attribute, String> attributeValue = new HashMap<Attribute, String>();
		if (productCategory != null) {
			Set<Attribute> attributes = productCategory.getAttributes();
			for (Attribute attribute : attributes) {
				String value = request.getParameter("attribute_" + attribute.getId());
				if (StringUtils.isNotEmpty(value) && attribute.getOptions().contains(value)) {
					attributeValue.put(attribute, value);
				}
			}
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("productCategory", productCategory);
		model.addAttribute("childrenpCategory", childrenpCategory);
		model.addAttribute("brand", brand);
		model.addAttribute("promotion", promotion);
		model.addAttribute("tags", tags);
		model.addAttribute("isOutOfStock", isOutOfStock);
		model.addAttribute("attributeValue", attributeValue);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", 
				productService.findPage(productCategory, brand, promotion, 
														tags, attributeValue, startPrice, endPrice,
														true, true, null, false, isOutOfStock, null, orderType, pageable));
		return "/gw/product/list";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, 
								Integer pageNumber, Integer pageSize, HttpServletRequest request, ModelMap model) {
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("brand", brand);
		model.addAttribute("promotion", promotion);
		model.addAttribute("tags", tags);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", productService.findPage(null, brand, promotion, tags, null, startPrice, endPrice, 
																						true, true, null, false, null, null, orderType, pageable));
		
		if(null!=tags&&tags.size()==1&&(tags.get(0).getId()==405||tags.get(0).getName().equals("积分"))){
			System.out.println("检查积分静态页面");
			return "/shop/product/pointsList";
		}
		return "/shop/product/list";
	}
	
	

	/**
	 * 搜索
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(String keyword, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Integer pageNumber, 
												Integer pageSize, ModelMap model,HttpServletRequest request) {
		System.out.println("gwSearch");
		System.out.println("keyword="+keyword);
		System.out.println("keyword="+keyword);
		System.out.println("keyword="+keyword);
		
		ProductCategory productCategory = productCategoryService.findProductCategoryByKeyword(keyword);
		
		
		
		Map<Attribute, String> attributeValue = new HashMap<Attribute, String>();
		if (productCategory != null) {
			System.out.println("productCategory="+productCategory.getName());
			System.out.println("productCategory="+productCategory.getId());
			Set<Attribute> attributes = productCategory.getAttributes();
			for (Attribute attribute : attributes) {
				String value = request.getParameter("attribute_" + attribute.getId());
				if (StringUtils.isNotEmpty(value) && attribute.getOptions().contains(value)) {
					attributeValue.put(attribute, value);
				}
			}
		}
		
		if (StringUtils.isEmpty(keyword)) {
			return ERROR_VIEW;
		}
		Pageable pageable = null;
		if(pageSize != null){
			pageable = new Pageable(pageNumber, pageSize);
		}else{
			pageable = new Pageable(pageNumber, DEFAULT_PAGE_SIZE);
		}
		Page<Product> page = searchService.search(keyword, startPrice, endPrice, orderType, pageable);
		List<Product> products= page.getContent();
		System.out.println("page.zlh->"+products);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("productKeyword", keyword);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		pageable.setSearchValue(keyword);   
		pageable.setSearchProperty("name");
		model.addAttribute("page1", 
				productService.findPage(productCategory, null, null, 
						null, attributeValue, startPrice, endPrice,true, true, null, false, null, null, orderType, pageable));
		System.out.println(productService.findPage(productCategory, null, null, 
						null, attributeValue, startPrice, endPrice,true, true, null, false, null, null, orderType, pageable).getTotal());
		
		
		model.addAttribute("page", productService.findPageByEntcode(null, null, null, null, null, null, null, null, true, null, null, null, null, OrderType.dateDesc, pageable));
		System.out.println(productService.findPageByEntcode(null, null, null, null, null, null, null, null, true, null, null, null, null, OrderType.dateDesc, pageable).getTotal());
		
		return "gw/product/search"; 
		
	}

	/**
	 * 点击数
	 */
	@RequestMapping(value = "/hits/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Long hits(@PathVariable Long id) {
		return productService.viewHits(id);
	}
	
	
	@RequestMapping(value = "/service",method = RequestMethod.GET)
	public String about(HttpServletRequest request, ModelMap model) {
		return "/shop/service/service";
	}
	
	@RequestMapping(value = "/preference",method = RequestMethod.GET)
	public String preference(ModelMap model) {
		Member member = memberService.getCurrent();  
		model.addAttribute("member", member);   
		return "/gw/product/preference";
	}
	
	@RequestMapping(value = "/preferencex",method = RequestMethod.GET)
	public String preferencex(String idname, ModelMap model) {
		System.out.println("preferencex");
		Member member = memberService.getCurrent();
		model.addAttribute("idname", idname);  
		model.addAttribute("member", member);   
		return "/gw/product/preferencex";
	}
	
	@RequestMapping(value = "/preferenceDetail/{promotionId}/{productId}",method = RequestMethod.GET)
	public String preferenceProductDetail(@PathVariable Long productId,@PathVariable Long promotionId, ModelMap model) {
		Promotion promotion = promotionService.find(promotionId);
		Product product = productService.find(productId);
		model.addAttribute("product", product);
		model.addAttribute("promotion", promotion);
		return "/shop/product/preferenceDetail";
	}
	
	/**
	 * 获取当前地区
	 */
	@RequestMapping(value = "/getArea", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> edit(String areaName,ModelMap model) {
		Map<String, Object> data = new HashMap<String, Object>();
		model.addAttribute("area",areaName);
		data.put("areaName", areaName);
		return data;
	}
	
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
				map.put("instruction", product.getInstruction());
				map.put("path", product.getPath());
				data.add(map);
			}
		}  
		return data;
	}

}