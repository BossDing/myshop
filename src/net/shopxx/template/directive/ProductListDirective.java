/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.template.directive;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.entity.Article;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;
import net.shopxx.entity.Store;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Tag;
import net.shopxx.service.AttributeService;
import net.shopxx.service.BrandService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.StoreService;
import net.shopxx.service.TagService;
import net.shopxx.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 商品列表
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Component("productListDirective")
public class ProductListDirective extends BaseDirective {
	
	/** "商品分类ID"参数名称 */
	private static final String PRODUCT_CATEGORY_ID_PARAMETER_NAME = "productCategoryId";

	/** "品牌ID"参数名称 */
	private static final String BRAND_ID_PARAMETER_NAME = "brandId";

	/** "促销ID"参数名称 */
	private static final String PROMOTION_ID_PARAMETER_NAME = "promotionId";

	/** "标签ID"参数名称 */
	private static final String TAG_IDS_PARAMETER_NAME = "tagIds";

	/** "属性值"参数名称 */
	private static final String ATTRIBUTE_VALUE_PARAMETER_NAME = "attributeValue";

	/** "最低价格"参数名称 */
	private static final String START_PRICE_PARAMETER_NAME = "startPrice";

	/** "最高价格"参数名称 */
	private static final String END_PRICE_PARAMETER_NAME = "endPrice";

	/** "排序类型"参数名称 */
	private static final String ORDER_TYPE_PARAMETER_NAME = "orderType";
	
	/** "是否根据排序字段排序"参数名称 */
	private static final String PX_PARAMETER_NAME = "px";
	
	/** "是否根据排序字段排序"参数名称 */
	private static final String PX1_PARAMETER_NAME = "px1";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "products";
	
	/** ID参数配比 */
	private static final Pattern ID_PATTERN = Pattern.compile("\\d+");

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "attributeServiceImpl")
	private AttributeService attributeService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "storeServiceImpl")
	private StoreService storeService;
	
	/**
	 * 
	 * @param env：系统环境变量，通常用它来输出相关内容，如Writer out = env.getOut();
	 * @param params：自定义标签传过来的对象，其key=自定义标签的参数名，value值是TemplateModel类型，
	 * 而TemplateModel是一个接口类型，通常我们都使用TemplateScalarModel接口来替代它获取一个String 值，
	 * 如TemplateScalarModel.getAsString();当然还有其它常用的替代接口，如TemplateNumberModel获取number，TemplateHashModel等
	 * @param loopVars  循环替代变量
	 * @param body 用于处理自定义标签中的内容，如<@myDirective>将要被处理的内容</@myDirective>；当标签是<@myDirective />格式时，body=null
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Store store = null;
		Long productCategoryId = FreemarkerUtils.getParameter(PRODUCT_CATEGORY_ID_PARAMETER_NAME, Long.class, params);
		Long brandId = FreemarkerUtils.getParameter(BRAND_ID_PARAMETER_NAME, Long.class, params);
		Long promotionId = FreemarkerUtils.getParameter(PROMOTION_ID_PARAMETER_NAME, Long.class, params);
		Long[] tagIds = FreemarkerUtils.getParameter(TAG_IDS_PARAMETER_NAME, Long[].class, params);
		Map<String, String> attributeValue = FreemarkerUtils.getParameter(ATTRIBUTE_VALUE_PARAMETER_NAME, Map.class, params);
		BigDecimal startPrice = FreemarkerUtils.getParameter(START_PRICE_PARAMETER_NAME, BigDecimal.class, params);
		BigDecimal endPrice = FreemarkerUtils.getParameter(END_PRICE_PARAMETER_NAME, BigDecimal.class, params);
		OrderType orderType = FreemarkerUtils.getParameter(ORDER_TYPE_PARAMETER_NAME, OrderType.class, params);
		String storeid = getStoreId(params);
		Boolean px = FreemarkerUtils.getParameter(PX_PARAMETER_NAME, Boolean.class, params);
		Boolean px1 = FreemarkerUtils.getParameter(PX1_PARAMETER_NAME, Boolean.class, params);
		
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);
		Map<Attribute, String> attributeValueMap = new HashMap<Attribute, String>();
		if (attributeValue != null) {
			for (Entry<String, String> entry : attributeValue.entrySet()) {
				if (ID_PATTERN.matcher(entry.getKey()).matches()) {
					Long attributeId = Long.valueOf(entry.getKey());
					Attribute attribute = attributeService.find(attributeId);
					if (attribute != null) {
						attributeValueMap.put(attribute, entry.getValue());
					}
				}
			}
		}

		List<Product> products;
		if ((productCategoryId != null && productCategory == null) || (brandId != null && brand == null) || (promotionId != null && promotion == null) || (tagIds != null && tags.isEmpty()) || (attributeValue != null && attributeValueMap.isEmpty())) {
			products = new ArrayList<Product>();
		} else {
			boolean useCache = useCache(env, params);
			String cacheRegion = getCacheRegion(env, params);
			Integer count = getCount(params);
			List<Filter> filters = getFilters(params, Article.class);
			List<Order> orders = getOrders(params);
			if(storeid != null && !"".equals(storeid)) {
				Long id = Long.parseLong(storeid);
				store = storeService.find(id);
			}
			if (useCache) {
//				System.out.println("useCache");
				if((px!=null && px)){
//					System.out.println("进入px");
					products = productService.findListGW(productCategory, brand, promotion, tags, attributeValueMap, startPrice, endPrice, true, true, null, false, null, null, orderType, count, filters, orders, cacheRegion, store, px ,false);
				}else if((px1!=null && px1)){
//					System.out.println("进入px1");
					products = productService.findListGW(productCategory, brand, promotion, tags, attributeValueMap, startPrice, endPrice, true, true, null, false, null, null, orderType, count, filters, orders, cacheRegion, store,false, px1);
				}else{
//					System.out.println("进入else");
					products = productService.findList(productCategory, brand, promotion, tags, attributeValueMap, startPrice, endPrice, true, true, null, false, null, null, orderType, count, filters, orders, cacheRegion, store);
				}
			} else {
				if(px!=null && px){
//					System.out.println("进入px");
					products = productService.findListGW(productCategory, brand, promotion, tags, attributeValueMap, startPrice, endPrice, true, true, null, false, null, null, orderType, count, filters, orders, store, px, false);					
				}else if((px1!=null && px1)){
//					System.out.println("进入px1");
					products = productService.findListGW(productCategory, brand, promotion, tags, attributeValueMap, startPrice, endPrice, true, true, null, false, null, null, orderType, count, filters, orders, store, false, px1);
				}else{
//					System.out.println("进入else");
					products = productService.findList(productCategory, brand, promotion, tags, attributeValueMap, startPrice, endPrice, true, true, null, false, null, null, orderType, count, filters, orders, store);
				}
			}
		}
		setLocalVariable(VARIABLE_NAME, products, env, body);
	}

}