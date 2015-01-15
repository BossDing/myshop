package net.shopxx.controller.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.shopxx.Filter;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.entity.Area;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Store;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.AreaService;
import net.shopxx.service.BrandService;
import net.shopxx.service.FileService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.SearchService;
import net.shopxx.service.StoreService;
import net.shopxx.service.TagService;
import net.shopxx.util.AddressUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author guoxianlong
 *
 */
@Controller("mobileStoreController")
@RequestMapping("/mobile/store")
public class StoreController extends BaseController{
	
	@Resource(name = "storeServiceImpl")
	private StoreService storeService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String areaList(String keyword, Long cityId, Long provinceId, 
			Integer pageNumber, Integer pageSize, 
			HttpServletRequest request, ModelMap model) {
		Area city = null;
		if (null == keyword && null == cityId && null == provinceId
				&& null == pageNumber && null == pageSize) {
			try {
				String[] address = AddressUtil.getAddresses(request, "UTF-8");
				if (StringUtils.isNotEmpty(address[0]) && StringUtils.isNotEmpty(address[1])) {
					provinceId = areaService.findByAreaName(address[0].trim()).getId();
					city = areaService.findByAreaName(address[1].trim());
					cityId = city.getId();
				}
			} catch (Exception e) {
//				System.out.println("根据IP没有找到对应省份城市");
			}
		}
		if (pageNumber == null || pageNumber.equals(new Integer(0))) {
			pageNumber = new Integer(1);
		}
		if (pageSize == null || pageSize.equals(new Integer(0))) {
			pageSize = new Integer(9);
		}

		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("cityId", cityId);
		model.addAttribute("provinceId", provinceId);
		pageable.setSearchValue(keyword);
		pageable.setSearchProperty("name");

		List<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("checkStatus", Filter.Operator.eq, Store.CheckStatus.success)); // 通过审核
		filters.add(new Filter("isEnabled", Filter.Operator.eq, new Boolean(true))); // 启用中

		model.addAttribute("provinceList", areaService.findRoots());
		if (city != null) {
			filters.add(new Filter("areaName", Filter.Operator.like, "%" + city.getName().trim() + "%"));
		} else if (cityId != null) {
			filters.add(new Filter("areaName", Filter.Operator.like, "%" + areaService.find(cityId).getName().trim() + "%"));
		}
		model.addAttribute("page", storeService.findPage(filters, null, pageable));
		return "/mobile/dp/list";
	}
	
	/** 跳转到搜索页面*/
	@RequestMapping(value = "/toSearch", method = RequestMethod.GET)
	public String toSearch(ModelMap model,Long storeId) {
		Store store = storeService.find(storeId);
		model.addAttribute("store", store);
		return "mobile/dp/dp_search";//转向对应的search.ftl文件
	}

	

	/**
	 * 移动端店铺的所有商品
	 * @author wmd
	 * @date 2014/11/28
	 */
	@RequestMapping(value = "/product_list", method = RequestMethod.GET)
	public String productList(String keyword, Long productCategoryId, Long brandId,
			Long promotionId, Long[] tagIds, BigDecimal startPrice,
			BigDecimal endPrice, Product.OrderType orderType,
			Boolean isOutOfStock, Long storeId, Integer pageNumber,
			Integer pageSize, HttpServletRequest request, ModelMap model) {
		
		if (storeId == null) {
			return "redirect:product_category.jhtml";
		}
		
		/** 根据店铺id，获取店铺信息 */
		Store store = storeService.find(storeId);
		if(store == null || !store.getIsEnabled()) {
			return "redirect:list.jhtml";
		}
		
		/** 把当前店铺放入session中，方便在dao查询数据库时获取*/
		request.getSession().setAttribute("store", store);
		
		ProductCategory productCategory = null;
		if (productCategoryId != null) {
			/** 根据分类id，获取分类信息*/
			productCategory = productCategoryService.find(productCategoryId); 
		}
		
		/** 根据品牌id，获取品牌信息*/
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		
		/** 根据标签id，获取标签信息*/
		List<Tag> tags = tagService.findList(tagIds);
		Map<Attribute, String> attributeValue = new HashMap<Attribute, String>();
		
		/** 根据分类属性,获取属性值 */
		if (productCategory != null) {
			Set<Attribute> attributes = productCategory.getAttributes();
			for (Attribute attribute : attributes) {
				String value = request.getParameter("attribute_"
						+ attribute.getId());
				if (StringUtils.isNotEmpty(value)
						&& attribute.getOptions().contains(value)) {
					attributeValue.put(attribute, value);
				}
			}
		}

		Pageable pageable = new Pageable(pageNumber, pageSize);	//初始化分页信息
//		pageable.setSearchProperty("name");
//		pageable.setSearchValue(keyword);
//		model.addAttribute("keyword", keyword); 				//关键字
		model.addAttribute("orderTypes", OrderType.values());	//所有排序类型
		model.addAttribute("productCategory", productCategory);	//当前分类
		model.addAttribute("productCategories", productCategoryService
				.findTreeForStore());							//商城及该店铺的分类
		model.addAttribute("brand", brand); 					//品牌
		model.addAttribute("promotion", promotion); 			//促销
		model.addAttribute("tags", tags); 						//标签
		model.addAttribute("isOutOfStock", isOutOfStock); 		//是否缺货
		model.addAttribute("attributeValue", attributeValue); 	//末尾价
		model.addAttribute("orderType", orderType); 			//排属性值
		model.addAttribute("startPrice", startPrice); 			//起始价
		model.addAttribute("endPrice", endPrice); 				//序类型
		model.addAttribute("pageNumber", pageNumber); 			//第页
		model.addAttribute("pageSize", pageSize); 				//页容几量
		model.addAttribute("page", productService.findPageByEntcode(
				productCategory, brand, promotion, tags, attributeValue,
				startPrice, endPrice, true, true, null, false, isOutOfStock,
				null, orderType, pageable)); 					//根据条件获取商品(分页)
		model.addAttribute("store", store);						//店铺信息
		request.getSession().removeAttribute("store"); 			//从session中移除store
		return "/mobile/dp/product/list";
	}
	
	/**
	 * 店铺分类
	 * @param storeId
	 * @param request
	 * @param model
	 * @return 商城分类及店铺自己的分类
	 * @author wmd
	 * @date 2014/12/3
	 */
	@RequestMapping(value = "/product_category",method = RequestMethod.GET)
	public String list(Long storeId,HttpServletRequest request, ModelMap model) {
		if (storeId == null) {
			return ERROR_VIEW;
		}
		/** 根据店铺storeId，获取店铺信息*/
		Store store = storeService.find(storeId);
		if(store == null || !store.getIsEnabled()) {
			return "redirect:store/list.jhtml";
		}
		/** 把当前店铺放入session中，方便在dao查询数据库时获取*/
		request.getSession().setAttribute("store", store); // 

		/** 商城及该店铺的分类*/
		model.addAttribute("productCategories", productCategoryService
				.findTreeForStore());
		model.addAttribute("store", store);

		/** 从session中移除store*/
		request.getSession().removeAttribute("store");
		return "/mobile/dp/product_category";
	}
	
	/**
	 * 移动端店铺分类
	 * @author wmd
	 * @date 2014/12/8
	 */
	@RequestMapping(value = "/dp_category_list", method = RequestMethod.GET)
	public String dpCategory(String keyword, Long productCategoryId, Long brandId,
			Long promotionId, Long[] tagIds, BigDecimal startPrice,
			BigDecimal endPrice, Product.OrderType orderType,
			Boolean isOutOfStock, Long storeId, Integer pageNumber,
			Integer pageSize, HttpServletRequest request, ModelMap model) {
		
		if (storeId == null) {
			return "redirect:product_category.jhtml";
		}
		
		/** 根据店铺id，获取店铺信息 */
		Store store = storeService.find(storeId);
		if(store == null || !store.getIsEnabled()) {
			return "redirect:list.jhtml";
		}
		
		/** 把当前店铺放入session中，方便在dao查询数据库时获取*/
		request.getSession().setAttribute("store", store);
		
		ProductCategory productCategory = null;
		if (productCategoryId != null) {
			/** 根据分类id，获取分类信息*/
			productCategory = productCategoryService.find(productCategoryId); 
		}
		
		/** 根据品牌id，获取品牌信息*/
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		
		/** 根据标签id，获取标签信息*/
		List<Tag> tags = tagService.findList(tagIds);
		Map<Attribute, String> attributeValue = new HashMap<Attribute, String>();
		
		/** 根据分类属性,获取属性值 */
		if (productCategory != null) {
			Set<Attribute> attributes = productCategory.getAttributes();
			for (Attribute attribute : attributes) {
				String value = request.getParameter("attribute_"
						+ attribute.getId());
				if (StringUtils.isNotEmpty(value)
						&& attribute.getOptions().contains(value)) {
					attributeValue.put(attribute, value);
				}
			}
		}

		Pageable pageable = new Pageable(pageNumber, pageSize);	//初始化分页信息
		pageable.setSearchProperty("name");
		pageable.setSearchValue(keyword);
		model.addAttribute("keyword", keyword); 				//关键字
		model.addAttribute("orderTypes", OrderType.values());	//所有排序类型
		model.addAttribute("productCategory", productCategory);	//当前分类
		model.addAttribute("productCategories", productCategoryService
				.findTreeForStore());							//商城及该店铺的分类
		model.addAttribute("brand", brand); 					//品牌
		model.addAttribute("promotion", promotion); 			//促销
		model.addAttribute("tags", tags); 						//标签
		model.addAttribute("isOutOfStock", isOutOfStock); 		//是否缺货
		model.addAttribute("attributeValue", attributeValue); 	//末尾价
		model.addAttribute("orderType", orderType); 			//排属性值
		model.addAttribute("startPrice", startPrice); 			//起始价
		model.addAttribute("endPrice", endPrice); 				//序类型
		model.addAttribute("pageNumber", pageNumber); 			//第页
		model.addAttribute("pageSize", pageSize); 				//页容几量
		model.addAttribute("page", productService.findPageByEntcode(
				productCategory, brand, promotion, tags, attributeValue,
				startPrice, endPrice, true, true, null, false, isOutOfStock,
				null, orderType, pageable)); 					//根据条件获取商品(分页)
		model.addAttribute("store", store);						//店铺信息
		request.getSession().removeAttribute("store"); 			//从session中移除store
		if(keyword != null){
			return "/mobile/dp/product/dp_search_list";
		}else{
			return "/mobile/dp/product/dp_category_list";
		}
	}
	
	/**
	 * 根据分类Id查询分类
	 * @author wmd
	 * @date 2014/12/8
	 */
	@RequestMapping(value = "/find_category",method = RequestMethod.POST)
	@ResponseBody //异步请求处理商品分类
	public Map<String, Object> findProductCategory(Long id) {
		Map<String, Object> data = new HashMap<String, Object>();
		ProductCategory productCategory = productCategoryService.find(id); 
		data.put("name", productCategory.getName());
		return  data;
	}
	
	/**
	 * 店铺浏览记录
	 * @author wmd
	 * @date 2014/12/11
	 */
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> history(Long[] ids) {
		//创建一个List用于存放Map
		ArrayList<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		//创建一个Map用于存放Store信息
		Map<String, Object> data = new HashMap<String, Object>();
		//根据Id集合查处对应的Store集合
		List<Store> stores =storeService.findList(ids);
		if(stores.size() > 0 ) {
			//遍历Store集合，将单个店铺信息放到Map
			for(Store store: stores) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", store.getName());
				map.put("url", store.getIndexMobileUrl());
				//将Map放到List中
				lists.add(map);
			}
		}
		data.put("lists", lists);
		return data;
		
	}
}
