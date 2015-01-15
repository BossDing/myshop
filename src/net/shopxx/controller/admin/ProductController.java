/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.Setting;
import net.shopxx.FileInfo.FileType;
import net.shopxx.entity.Area;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Goods;
import net.shopxx.entity.MemberRank;
import net.shopxx.entity.Parameter;
import net.shopxx.entity.ParameterGroup;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.ProductImage;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Specification;
import net.shopxx.entity.SpecificationValue;
import net.shopxx.entity.Store;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.entity.Tag.Type;
import net.shopxx.service.AdminService;
import net.shopxx.service.AreaService;
import net.shopxx.service.AttributeService;
import net.shopxx.service.BrandService;
import net.shopxx.service.FileService;
import net.shopxx.service.GoodsService;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductImageService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.SpecificationService;
import net.shopxx.service.SpecificationValueService;
import net.shopxx.service.TagService;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 商品
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController extends BaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	@Resource(name = "specificationValueServiceImpl")
	private SpecificationValueService specificationValueService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "attributeServiceImpl")
	private AttributeService attributeService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	/**
	 * 检查编号是否唯一
	 */
	@RequestMapping(value = "/check_sn", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkSn(String previousSn, String sn) {
		if (StringUtils.isEmpty(sn)) {
			return false;
		}
		if (productService.snUnique(previousSn, sn)) {
			return true;
		} else {
			return false;
		}
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
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		// model.addAttribute("productCategoryTree", productCategoryService
		// .findTree());

		/**
		 * 店铺管理员，查询该店铺及商城的分类 非店铺管理员，查询商城分类
		 * 
		 * 店铺管理 只能查询标签为 热销
		 */
		model.addAttribute("productCategoryTree", productCategoryService
				.findTreeForStore());

		model.addAttribute("brands", brandService.findAll());
		Store store = WebUtils.getStore();
		model.addAttribute("store", store);
		if (store != null) {
			model.addAttribute("tags", tagService.findListByStore(store,
					Type.product));
		} else {
			model.addAttribute("tags", tagService.findList(Type.product));
		}
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		model.addAttribute("areas", areaService.findRoots());
		model.addAttribute("entcode", adminService.getCurrent().getEntcode());
		return "/admin/product/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String save(Product product, Long[] area_name,
			Long productCategoryId, Long brandId, Long[] tagIds,
			Long[] specificationIds, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		product.setAreas(new HashSet<Area>(areaService.findList(area_name)));

		for (Iterator<ProductImage> iterator = product.getProductImages()
				.iterator(); iterator.hasNext();) {
			ProductImage productImage = iterator.next();
			if (productImage == null || productImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (productImage.getFile() != null
					&& !productImage.getFile().isEmpty()) {
				if (!fileService
						.isValid(FileType.image, productImage.getFile())) {
					addFlashMessage(redirectAttributes, Message
							.error("admin.upload.invalid"));
					return "redirect:add.jhtml";
				}
			}
		}
		product.setProductCategory(productCategoryService
				.find(productCategoryId));
		product.setBrand(brandService.find(brandId));
		product.setTags(new HashSet<Tag>(tagService.findList(tagIds)));   
		if (!isValid(product)) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(product.getSn())
				&& productService.snExists(product.getSn())) {
			return ERROR_VIEW;
		}
		if (product.getMarketPrice() == null) {
			BigDecimal defaultMarketPrice = calculateDefaultMarketPrice(product
					.getPrice());
			product.setMarketPrice(defaultMarketPrice);
		}
		if (product.getPoint() == null) {
			long point = calculateDefaultPoint(product.getPrice());
			product.setPoint(point);
		}
		product.setFullName(null);
		product.setAllocatedStock(0);
		product.setScore(0F);
		product.setTotalScore(0L);
		product.setScoreCount(0L);
		product.setHits(0L);
		product.setWeekHits(0L);
		product.setMonthHits(0L);
		product.setSales(0L);
		product.setWeekSales(0L);
		product.setMonthSales(0L);
		product.setWeekHitsDate(new Date());
		product.setMonthHitsDate(new Date());
		product.setWeekSalesDate(new Date());
		product.setMonthSalesDate(new Date());
		product.setReviews(null);
		product.setConsultations(null);
		product.setFavoriteMembers(null);
		product.setPromotions(null);
		product.setCartItems(null);
		product.setOrderItems(null);
		product.setGiftItems(null);
		product.setProductNotifies(null);
		product.setEntcode(WebUtils.getXentcode());
//		product.setIsShare(false);

		Store store = WebUtils.getStore();
		product.setStore(store);// 设置所属店铺 cgd 2014-10-15

		/**
		 * 如果是店铺新增商品 默认未上架，审核状态为待审核
		 */
		if (store == null) {
			product.setCheckStatus(Product.CheckStatus.success);
		} else {
			product.setIsMarketable(false);
			product.setCheckStatus(Product.CheckStatus.waitting);
		}

		for (MemberRank memberRank : memberRankService.findAll()) {
			String price = request.getParameter("memberPrice_"
					+ memberRank.getId());
			if (StringUtils.isNotEmpty(price)
					&& new BigDecimal(price).compareTo(new BigDecimal(0)) >= 0) {
				product.getMemberPrice().put(memberRank, new BigDecimal(price));
			} else {
				product.getMemberPrice().remove(memberRank);
			}
		}
		for (ProductImage productImage : product.getProductImages()) {
			productImageService.build(productImage);
		}
		Collections.sort(product.getProductImages());
		if (product.getImage() == null && product.getThumbnail() != null) {
			product.setImage(product.getThumbnail());
		}
		StringBuffer  allAttribute = new StringBuffer();
		List<Long> all = product.getProductCategory().getTreePaths();
		all.add(productCategoryId);
		for(int i=0;i<all.size();i++){
			for (Attribute attribute : productCategoryService.find(all.get(i)).getAttributes()) {  
				allAttribute.append(attribute.getName()+",");  
			}
		}  			
		Set<ParameterGroup> parameterGroups = parameterGroups(product
				.getProductCategory().getId());
		if (parameterGroups != null) {
			for (ParameterGroup parameterGroup : parameterGroups) {
				for (Parameter parameter : parameterGroup.getParameters()) {
					String parameterValue = request.getParameter("parameter_"
							+ parameter.getId());  
					if(allAttribute.indexOf(parameter.getName())!= -1 &&!parameterValue.equals("")){	  
						for(int j=0;j<all.size();j++){
							for (Attribute attribute : productCategoryService.find(all.get(j)).getAttributes()) {
							  if(parameter.getName().equals(attribute.getName())){
								Boolean insert = true;
								for(int i = 0;i < attribute.getOptions().size(); i ++){
									if(parameterValue.equals(attribute.getOptions().get(i))){
										insert = false;
										break;
									}
								}	
								if(insert){  
									attribute.getOptions().add(parameterValue);
									List<String> options = attribute.getOptions();
									Boolean isDisgit = true;
									for (int i = 0; i < options.size(); i++){
										for(int t =0 ;t<options.get(i).length();t++){
											String s = options.get(i);
											if (!Character.isDigit(s.charAt(t))){
											    isDisgit = false;
											    break;
											}    
										}  
									}
									if(isDisgit){
										List<Integer> intoptions = new ArrayList<Integer>();
										for(int i = 0;i < options.size(); i++){
											String s = options.get(i);
											Integer n = Integer.valueOf(s);   
											intoptions.add(n);      
										}       
										Collections.sort(intoptions);
										List<String> newoptions = new ArrayList<String>();
										for(int i = 0;i < intoptions.size(); i++){  
											String s = intoptions.get(i).toString();
											newoptions.add(s);
										}   
										attribute.setOptions(newoptions);  
									}else{
										attributeService.update(attribute);
									}
								}
							}
							}
						}
					}   
					
					if (StringUtils.isNotEmpty(parameterValue)) {
						product.getParameterValue().put(parameter,
								parameterValue);
					} else {
						product.getParameterValue().remove(parameter);
					}
				}
			}
		}
		// for (Attribute attribute :
		// product.getProductCategory().getAttributes()) {
		// gxh 修改 让其子分类可以使用上级分类的商品属性 2014-09-19
		Set<Attribute> attributes = attributes(product.getProductCategory()
				.getId());
		if (attributes != null) {
			for (Attribute attribute : attributes) {
				String attributeValue = request.getParameter("attribute_"
						+ attribute.getId());
				if (StringUtils.isNotEmpty(attributeValue)) {
					product.setAttributeValue(attribute, attributeValue);
				} else {
					product.setAttributeValue(attribute, null);
				}
			}
		}

		Goods goods = new Goods();
		List<Product> products = new ArrayList<Product>();
		if (specificationIds != null && specificationIds.length > 0) {
			for (int i = 0; i < specificationIds.length; i++) {
				Specification specification = specificationService
						.find(specificationIds[i]);
				String[] specificationValueIds = request
						.getParameterValues("specification_"
								+ specification.getId());
				if (specificationValueIds != null
						&& specificationValueIds.length > 0) {
					for (int j = 0; j < specificationValueIds.length; j++) {
						if (i == 0) {
							if (j == 0) {
								product.setGoods(goods);
								product
										.setSpecifications(new HashSet<Specification>());
								product
										.setSpecificationValues(new HashSet<SpecificationValue>());
								products.add(product);
							} else {
								Product specificationProduct = new Product();
								BeanUtils.copyProperties(product,
										specificationProduct);
								specificationProduct.setId(null);
								specificationProduct.setCreateDate(null);
								specificationProduct.setModifyDate(null);
								specificationProduct.setSn(null);
								specificationProduct.setFullName(null);
								specificationProduct.setAllocatedStock(0);
								specificationProduct.setIsList(false);
								specificationProduct.setScore(0F);
								specificationProduct.setTotalScore(0L);
								specificationProduct.setScoreCount(0L);
								specificationProduct.setHits(0L);
								specificationProduct.setWeekHits(0L);
								specificationProduct.setMonthHits(0L);
								specificationProduct.setSales(0L);
								specificationProduct.setWeekSales(0L);
								specificationProduct.setMonthSales(0L);
								specificationProduct
										.setWeekHitsDate(new Date());
								specificationProduct
										.setMonthHitsDate(new Date());
								specificationProduct
										.setWeekSalesDate(new Date());
								specificationProduct
										.setMonthSalesDate(new Date());
								specificationProduct.setGoods(goods);
								specificationProduct.setReviews(null);
								specificationProduct.setConsultations(null);
								specificationProduct.setFavoriteMembers(null);
								specificationProduct
										.setSpecifications(new HashSet<Specification>());
								specificationProduct
										.setSpecificationValues(new HashSet<SpecificationValue>());
								specificationProduct.setPromotions(null);
								specificationProduct.setCartItems(null);
								specificationProduct.setOrderItems(null);
								specificationProduct.setGiftItems(null);
								specificationProduct.setProductNotifies(null);
								products.add(specificationProduct);
							}
						}
						Product specificationProduct = products.get(j);
						SpecificationValue specificationValue = specificationValueService
								.find(Long.valueOf(specificationValueIds[j]));
						specificationProduct.getSpecifications().add(
								specification);
						specificationProduct.getSpecificationValues().add(
								specificationValue);
					}
				}
			}
		} else {
			product.setGoods(goods);
			product.setSpecifications(null);
			product.setSpecificationValues(null);
			products.add(product);
		}
		goods.getProducts().clear();
		goods.getProducts().addAll(products);
		goodsService.save(goods);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		Product p = productService.find(id);
		Store store = WebUtils.getStore();
		if (store == null) { // 非店铺管理员，查询所有分类
			model.addAttribute("productCategoryTree", productCategoryService
					.findTree());
			model.addAttribute("tags", tagService.findList(Type.product));
		} else { // 店铺管理员，查询该店铺的分类及商城分类
			model.addAttribute("productCategoryTree", productCategoryService
					.findTreeForStore());
			model.addAttribute("tags", tagService.findListByStore(store,
					Type.product));
		}
		model.addAttribute("store", store);
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		model.addAttribute("attributes", attributes(p.getProductCategory()
				.getId()));
		model.addAttribute("parameterGroups", parameterGroups(p
				.getProductCategory().getId()));
		model.addAttribute("product", p);
		model.addAttribute("areas", areaService.findRoots());
		model.addAttribute("entcode", adminService.getCurrent().getEntcode());
		return "/admin/product/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Product product, Long[] area_name,
			Long productCategoryId, Long brandId, Long[] tagIds,
			Long[] specificationIds, Long[] specificationProductIds,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		product.setAreas(new HashSet<Area>(areaService.findList(area_name)));
		for (Iterator<ProductImage> iterator = product.getProductImages()
				.iterator(); iterator.hasNext();) {
			ProductImage productImage = iterator.next();
			if (productImage == null || productImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (productImage.getFile() != null
					&& !productImage.getFile().isEmpty()) {
				if (!fileService
						.isValid(FileType.image, productImage.getFile())) {
					addFlashMessage(redirectAttributes, Message
							.error("admin.upload.invalid"));
					return "redirect:edit.jhtml?id=" + product.getId();
				}
			}
		}
		product.setProductCategory(productCategoryService
				.find(productCategoryId));
		product.setBrand(brandService.find(brandId));
		product.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		product.setEntcode(WebUtils.getXentcode());

		if (!isValid(product)) {
			return ERROR_VIEW;
		}
		Product pProduct = productService.find(product.getId());
		if (pProduct == null) {
			return ERROR_VIEW;
		}
		/**
		 * 不可编辑共享商品
		 */
//		if (pProduct.getIsShare()) {
//			return ERROR_VIEW;
//		}
		product.setStore(pProduct.getStore());// 设置所属店铺 cgd 2014-10-15
		Store store = WebUtils.getStore();
		Product.CheckStatus checkStatus = pProduct.getCheckStatus();
		product.setCheckStatus(checkStatus);

		if (Product.CheckStatus.waitting.equals(checkStatus)
				|| Product.CheckStatus.failure.equals(checkStatus)) {
			product.setIsMarketable(false);
		}

		if (StringUtils.isNotEmpty(product.getSn())
				&& !productService.snUnique(pProduct.getSn(), product.getSn())) {
			return ERROR_VIEW;
		}
		if (product.getMarketPrice() == null) {
			BigDecimal defaultMarketPrice = calculateDefaultMarketPrice(product
					.getPrice());
			product.setMarketPrice(defaultMarketPrice);
		}
		if (product.getPoint() == null) {
			long point = calculateDefaultPoint(product.getPrice());
			product.setPoint(point);
		}
		for (MemberRank memberRank : memberRankService.findAll()) {
			String price = request.getParameter("memberPrice_"
					+ memberRank.getId());
			if (StringUtils.isNotEmpty(price)
					&& new BigDecimal(price).compareTo(new BigDecimal(0)) >= 0) {
				product.getMemberPrice().put(memberRank, new BigDecimal(price));
			} else {
				product.getMemberPrice().remove(memberRank);
			}
		}

		for (ProductImage productImage : product.getProductImages()) {
			productImageService.build(productImage);
		}
		Collections.sort(product.getProductImages());
		if (product.getImage() == null && product.getThumbnail() != null) {
			product.setImage(product.getThumbnail());
		}
		StringBuffer  allAttribute = new StringBuffer();
		List<Long> all = product.getProductCategory().getTreePaths();
		all.add(productCategoryId);
		for(int i=0;i<all.size();i++){
			for (Attribute attribute : productCategoryService.find(all.get(i)).getAttributes()) {  
				allAttribute.append(attribute.getName()+",");  
			}
		}   	
		// for (ParameterGroup parameterGroup :
		// product.getProductCategory().getParameterGroups()) {
		// gxh 修改 让其子分类可以使用上级分类的商品参数 2014-09-20
		Set<ParameterGroup> parameterGroups = parameterGroups(product
				.getProductCategory().getId());
		if (parameterGroups != null) {
			for (ParameterGroup parameterGroup : parameterGroups) {
				for (Parameter parameter : parameterGroup.getParameters()) {
					String parameterValue = request.getParameter("parameter_"
							+ parameter.getId());  
					if(allAttribute.indexOf(parameter.getName())!= -1 && !parameterValue.equals("")){	  
						for(int j=0;j<all.size();j++){
							for (Attribute attribute : productCategoryService.find(all.get(j)).getAttributes()) {  
								if(parameter.getName().equals(attribute.getName())){
									Boolean insert = true;
									for(int i = 0;i < attribute.getOptions().size(); i ++){
										if(parameterValue.equals(attribute.getOptions().get(i))){
											insert = false;
											break;
										}
									}	
									if(insert){  
										attribute.getOptions().add(parameterValue);
										List<String> options = attribute.getOptions();
										Boolean isDisgit = true;
										for (int i = 0; i < options.size(); i++){
											for(int t =0 ;t<options.get(i).length();t++){
												String s = options.get(i);
												if (!Character.isDigit(s.charAt(t))){
												    isDisgit = false;
												    break;
												}    
											}  
										}
										if(isDisgit){
											List<Integer> intoptions = new ArrayList<Integer>();
											for(int i = 0;i < options.size(); i++){
												String s = options.get(i);
												Integer n = Integer.valueOf(s);   
												intoptions.add(n);      
											}       
											Collections.sort(intoptions);
											List<String> newoptions = new ArrayList<String>();
											for(int i = 0;i < intoptions.size(); i++){  
												String s = intoptions.get(i).toString();
												newoptions.add(s);
											}   
											attribute.setOptions(newoptions);  
										}else{
											attributeService.update(attribute);
										}
									}  
								}
							}
						}
					} 
					if (StringUtils.isNotEmpty(parameterValue)) {
						product.getParameterValue().put(parameter,
								parameterValue);
					} else {
						product.getParameterValue().remove(parameter);
					}
				}
			}
		}

		// for (Attribute attribute :
		// product.getProductCategory().getAttributes()) {
		// gxh 修改 让其子分类可以使用上级分类的商品属性 2014-09-19
		Set<Attribute> attributes = attributes(product.getProductCategory()
				.getId());
		if (attributes != null) {
			for (Attribute attribute : attributes) {
				String attributeValue = request.getParameter("attribute_"
						+ attribute.getId());
				if (StringUtils.isNotEmpty(attributeValue)) {
					product.setAttributeValue(attribute, attributeValue);
				} else {
					product.setAttributeValue(attribute, null);
				}
			}
		}

		Goods goods = pProduct.getGoods();
		List<Product> products = new ArrayList<Product>();
		if (specificationIds != null && specificationIds.length > 0) {
			for (int i = 0; i < specificationIds.length; i++) {
				Specification specification = specificationService
						.find(specificationIds[i]);
				String[] specificationValueIds = request
						.getParameterValues("specification_"
								+ specification.getId());
				if (specificationValueIds != null
						&& specificationValueIds.length > 0) {
					for (int j = 0; j < specificationValueIds.length; j++) {
						if (i == 0) {
							if (j == 0) {
								BeanUtils.copyProperties(product, pProduct,
										new String[] { "id", "createDate",
												"modifyDate", "fullName",
												"allocatedStock", "score",
												"totalScore", "scoreCount",
												"hits", "weekHits",
												"monthHits", "sales",
												"weekSales", "monthSales",
												"weekHitsDate",
												"monthHitsDate",
												"weekSalesDate",
												"monthSalesDate", "goods",
												"reviews", "consultations",
												"favoriteMembers",
												"specifications",
												"specificationValues",
												"promotions", "cartItems",
												"orderItems", "giftItems",
												"productNotifies" });
								pProduct
										.setSpecifications(new HashSet<Specification>());
								pProduct
										.setSpecificationValues(new HashSet<SpecificationValue>());
								products.add(pProduct);
							} else {
								if (specificationProductIds != null
										&& j < specificationProductIds.length) {
									Product specificationProduct = productService
											.find(specificationProductIds[j]);
									if (specificationProduct == null
											|| (specificationProduct.getGoods() != null && !specificationProduct
													.getGoods().equals(goods))) {
										return ERROR_VIEW;
									}
									specificationProduct
											.setSpecifications(new HashSet<Specification>());
									specificationProduct
											.setSpecificationValues(new HashSet<SpecificationValue>());
									products.add(specificationProduct);
								} else {
									Product specificationProduct = new Product();
									BeanUtils.copyProperties(product,
											specificationProduct);
									specificationProduct.setId(null);
									specificationProduct.setCreateDate(null);
									specificationProduct.setModifyDate(null);
									specificationProduct.setSn(null);
									specificationProduct.setFullName(null);
									specificationProduct.setAllocatedStock(0);
									specificationProduct.setIsList(false);
									specificationProduct.setScore(0F);
									specificationProduct.setTotalScore(0L);
									specificationProduct.setScoreCount(0L);
									specificationProduct.setHits(0L);
									specificationProduct.setWeekHits(0L);
									specificationProduct.setMonthHits(0L);
									specificationProduct.setSales(0L);
									specificationProduct.setWeekSales(0L);
									specificationProduct.setMonthSales(0L);
									specificationProduct
											.setWeekHitsDate(new Date());
									specificationProduct
											.setMonthHitsDate(new Date());
									specificationProduct
											.setWeekSalesDate(new Date());
									specificationProduct
											.setMonthSalesDate(new Date());
									specificationProduct.setGoods(goods);
									specificationProduct.setReviews(null);
									specificationProduct.setConsultations(null);
									specificationProduct
											.setFavoriteMembers(null);
									specificationProduct
											.setSpecifications(new HashSet<Specification>());
									specificationProduct
											.setSpecificationValues(new HashSet<SpecificationValue>());
									specificationProduct.setPromotions(null);
									specificationProduct.setCartItems(null);
									specificationProduct.setOrderItems(null);
									specificationProduct.setGiftItems(null);
									specificationProduct
											.setProductNotifies(null);
									products.add(specificationProduct);
								}
							}
						}
						Product specificationProduct = products.get(j);
						SpecificationValue specificationValue = specificationValueService
								.find(Long.valueOf(specificationValueIds[j]));
						specificationProduct.getSpecifications().add(
								specification);
						specificationProduct.getSpecificationValues().add(
								specificationValue);
					}
				}
			}
		} else {
			product.setSpecifications(null);
			product.setSpecificationValues(null);
			BeanUtils.copyProperties(product, pProduct, new String[] { "id",
					"createDate", "modifyDate", "fullName", "allocatedStock",
					"score", "totalScore", "scoreCount", "hits", "weekHits",
					"monthHits", "sales", "weekSales", "monthSales",
					"weekHitsDate", "monthHitsDate", "weekSalesDate",
					"monthSalesDate", "goods", "reviews", "consultations",
					"favoriteMembers", "promotions", "cartItems", "orderItems",
					"giftItems", "productNotifies" });
			products.add(pProduct);
		}
		goods.getProducts().clear();
		goods.getProducts().addAll(products);
		goodsService.update(goods);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long productCategoryId, Long brandId, Long promotionId,
			Long tagId, Boolean isMarketable, Boolean isList, Boolean isTop,
			Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert,
			Pageable pageable, ModelMap model) {
			try {
				System.out.println("product====.list");
				System.out.println("id:"+productCategoryId);
				Store store = WebUtils.getStore();
				if (store != null) {
					//将店铺是否存在的信息传到页面
					model.addAttribute("isStore", true);
					model.addAttribute("tags", tagService.findListByStore(store,
							Type.product));
				} else {
					model.addAttribute("tags", tagService.findList(Type.product));
				}
				ProductCategory productCategory = productCategoryService
						.find(productCategoryId);
				System.out.println(productCategory);
				Brand brand = brandService.find(brandId);
				Promotion promotion = promotionService.find(promotionId);
				List<Tag> tags = tagService.findList(tagId);
				model.addAttribute("productCategoryTree", productCategoryService
						.findTree());
				model.addAttribute("brands", brandService.findAll());
				model.addAttribute("promotions", promotionService.findAll());
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
				List<String> productcategoryids = new ArrayList<String>();
				productcategoryids = adminService.getCurrent().getProductcategory();
				if(productcategoryids!=null && productcategoryids.size()>0){
					System.out.println("111111");
					List<ProductCategory> list = new ArrayList<ProductCategory>();
					for(int i=0 ;i<productcategoryids.size();i++){
						String id = productcategoryids.get(i);
						ProductCategory p = new ProductCategory();
						p.setId(new Long(id));
						list.add(p);
						model.addAttribute("page", productService.findPageByCategoryList(
								list, brand, promotion, tags, null, null, null,
								isMarketable, isList, isTop, isGift, isOutOfStock,
								isStockAlert, OrderType.dateDesc, pageable));
					}
				}else{
					System.out.println("222222");
					model.addAttribute("page", productService.findPageByEntcode(
						productCategory, brand, promotion, tags, null, null, null,
						isMarketable, isList, isTop, isGift, isOutOfStock,
						isStockAlert, OrderType.dateDesc, pageable));
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}
		return "/admin/product/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		productService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 计算默认市场价
	 * 
	 * @param price
	 *            价格
	 */
	private BigDecimal calculateDefaultMarketPrice(BigDecimal price) {
		Setting setting = SettingUtils.get();
		Double defaultMarketPriceScale = setting.getDefaultMarketPriceScale();
		return setting.setScale(price.multiply(new BigDecimal(
				defaultMarketPriceScale.toString())));
	}

	/**
	 * 计算默认积分
	 * 
	 * @param price
	 *            价格
	 */
	private long calculateDefaultPoint(BigDecimal price) {
		Setting setting = SettingUtils.get();
		Double defaultPointScale = setting.getDefaultPointScale();
		return price.multiply(new BigDecimal(defaultPointScale.toString()))
				.longValue();
	}

	/**
	 * 复制
	 */
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public String copyProduct(Long id, ModelMap model) {
		if (id == null) {
			throw new ResourceNotFoundException();
		}
		Product product = productService.find(id);
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		Store store = WebUtils.getStore();
		if (store == null) { // 非店铺管理员，查询所有分类
			model.addAttribute("productCategoryTree", productCategoryService
					.findTree());
			model.addAttribute("tags", tagService.findList(Type.product));
		} else { // 店铺管理员，查询该店铺的分类及商城分类
			model.addAttribute("productCategoryTree", productCategoryService
					.findTreeForStore());
			model.addAttribute("tags", tagService.findListByStore(store,
					Type.product));
		}
		model.addAttribute("store", store);
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		model.addAttribute("attributes", attributes(product
				.getProductCategory().getId()));
		model.addAttribute("parameterGroups", parameterGroups(product
				.getProductCategory().getId()));
		model.addAttribute("product", product);
		model.addAttribute("areas", areaService.findRoots());
		return "/admin/product/copy";
	}

}