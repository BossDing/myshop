/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.entity.ActionType;
import net.shopxx.entity.AfterBooking.Type;
import net.shopxx.entity.Article;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Receiver;
import net.shopxx.entity.Tag;
import net.shopxx.service.ActionTypeService;
import net.shopxx.service.ArticleService;
import net.shopxx.service.BrandService;
import net.shopxx.service.MemberService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.ReceiverService;
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
@Controller("shopProductController")
@RequestMapping("/product")
public class ProductController extends BaseController {

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
	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;

	/**
	 * 浏览记录
	 */
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public @ResponseBody
	List<Product> history(Long[] ids) {
		List<Product> products =productService.findList(ids);
		for(Product product : products){
			String sum ;
			if(null==product.getReviews()){
				sum = "0";
			}else{
				sum = String.valueOf(product.getReviews().size());
			}
			product.setSynopsis(sum);
		}
		return products;
		
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list/{productCategoryId}", method = RequestMethod.GET)
	public String list(@PathVariable Long productCategoryId, Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, 
														BigDecimal endPrice, OrderType orderType,Boolean isOutOfStock, Integer pageNumber, Integer pageSize, 
														HttpServletRequest request, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
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
		return "/shop/product/list";
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
			return "/shop/product/pointsList";
		}
		return "/shop/product/list";
	}

	/**
	 * 搜索
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(String keyword, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Integer pageNumber, 
												Integer pageSize, ModelMap model) {
		if (StringUtils.isEmpty(keyword)) {
			return ERROR_VIEW;
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		Page<Product> page = searchService.search(keyword, startPrice, endPrice, orderType, pageable);
//		List<Product> products= page.getTotal();
		System.out.println("page.zlh->"+page.getTotal());
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("productKeyword", keyword);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		pageable.setSearchValue(keyword);   
		pageable.setSearchProperty("name");     
		model.addAttribute("page", productService.findPageByEntcode(null, null, null, null, null, null, null, null, true, null, null, null, null, OrderType.dateDesc, pageable));
		return "shop/product/search"; 
	}
		
	/**         
	 * 点击数
	 */
	@RequestMapping(value = "/hits/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Long hits(@PathVariable Long id) {
		return productService.viewHits(id);
	}
	
	/**服务中心*/
	@RequestMapping(value = "/service",method = RequestMethod.GET)
	public String ser(HttpServletRequest request, ModelMap model) {
		List<Product> products = productService.findAll();
		Set<Receiver> receivers = null;
		Receiver receiver = new Receiver();
		Member member = memberService.getCurrent();
	    if(null!=member){
	    	receivers = member.getReceivers();
	    	for(Receiver rec :receivers){	
				if(rec.getIsDefault()){
					receiver = rec;
					break;
				}
			}
	    }
		model.addAttribute("receiver", receiver);
		model.addAttribute("types", Type.values());
		model.addAttribute("products", products);
		return "/shop/service/index";
	}
	
	  
	@RequestMapping(value = "/instructions",method = RequestMethod.GET)
	public String instructions(Long id,HttpServletRequest request, ModelMap model) {
		List<Product> products = new ArrayList<Product>();
		ProductCategory productCategory = productCategoryService.find(id);
		if(null!=productCategory){
			products = productService.findList(productCategory, null, null,null, null);
		}else{
			products = productService.findAll();
		 }
		List<Article> ars = articleService.findAll();
		List<Article> articles= new ArrayList<Article>();
		if(null!=ars&&ars.size()>0){
		  for(Article article :ars){
			if(article.getTitle().trim().equals("常见问题")){
				articles.add(article);
			}
		  }
		}
		model.addAttribute("articles", articles);
		model.addAttribute("products", products);				
		return "/shop/service/instructions";
	}
	
	@RequestMapping(value = "/searchPros", method = RequestMethod.GET)
	public @ResponseBody List<Product> searchPros(String proSn,String entCode) {
		if (StringUtils.isEmpty(proSn)) {
			return null;
		}
		/**if(null==entCode||"".equals(entCode.trim())||entCode.length()<=0){
			entCode ="macro";
		}*/
		List<Product> products = productService.search(proSn.toUpperCase(),null, null);
		List<Product> pros = new ArrayList<Product>();
		for(Product product :products){
			if(null!=product.getEntcode()&&product.getEntcode().equalsIgnoreCase(entCode)){
				pros.add(product);
			}
		}
		return pros;   
	}
	   			
	
	@RequestMapping(value = "/preference",method = RequestMethod.GET)
	public String preferenceProduct(String areaName,ModelMap model) {
		Member member = memberService.getCurrent();
		ActionType actionType = actionTypeService.findByActionName("限时抢购");
		model.addAttribute("actionType", actionType);
		model.addAttribute("member", member);
		model.addAttribute("areaName",areaName);
		return "/shop/product/preference";
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
	
//	/**
//	 * 通过类别筛选商品
//	 */
//	@RequestMapping(value = "/product_selectByCategory", method = RequestMethod.GET)
//	public @ResponseBody
//	List<Map<String, Object>> productSelectByCategory(String q) {
//		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//		if (StringUtils.isNotEmpty(q)) {
////			List<Product> products = productService.search(q, false, 20);?
//			List<Product> products = productService.findList(productCategory, null, null, null, null);
//			for (Product product : products) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("id", product.getId());
//				map.put("sn", product.getSn());
//				map.put("fullName", product.getFullName());
//				map.put("instruction", product.getInstruction());
//				map.put("path", product.getPath());
//				data.add(map);
//			}
//		}  
//		return data;
//	}
	
}