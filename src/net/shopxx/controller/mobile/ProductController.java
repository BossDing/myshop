package net.shopxx.controller.mobile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.BrandService;
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
 * 创建日期:2014-04-29
 * @author chengandou
 * @version 3.0
 *
 */
/*
 * @Controller用于申明该类为一个控制器，其值必须唯一
 * @RequestMapping用于配置响应请求的URL前缀
 * @RequestMapping value用于配置响应请求的URL后缀
 * @RequestMapping method用于配置响应请求的方式(post / get)
 * String username用于接收请求参数值
 * model.addAttribute("name",  username);用于将变量传递至模板
 * return "shop/hello/view";表示使用 "/WEB-INF/template/shop/hello/view.ftl"模板输出
 * 
 */
@Controller("productController")
@RequestMapping("/mobile/product")
public class ProductController extends BaseController{
	
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
	
	/**
	 * 跳转到搜索页面
	 */
	@RequestMapping(value = "/toSearch", method = RequestMethod.GET)
	public String toSearch(ModelMap model) {
		return "mobile/product/productSearch";//转向对应的search.ftl文件
	}
	
	/**
	 * 旧搜索功能
	 */
	@RequestMapping(value = "/search1", method = RequestMethod.GET)
	public String search1(String keyword, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Integer pageNumber, Integer pageSize, ModelMap model) {
		if (StringUtils.isEmpty(keyword)) {
			return ERROR_VIEW;
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("productKeyword", keyword);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		model.addAttribute("page", searchService.search(keyword, startPrice, endPrice, orderType, pageable));
		return "mobile/product/list";//转向对应的productList.ftl文件
	}
	
	/**
	 * 新的商品搜索
	 * @author wmd
	 *  2014/2/12/3
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(String keyword, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Integer pageNumber, 
												Integer pageSize, ModelMap model) {
		if (StringUtils.isEmpty(keyword)) {
			return ERROR_VIEW;
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("orderTypes", OrderType.values());
		model.addAttribute("productKeyword", keyword);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		pageable.setSearchValue(keyword);   
		pageable.setSearchProperty("name");     
		model.addAttribute("page", productService.findPageByEntcode(null, null, null, null, null, null, null, null, true, null, null, null, null, OrderType.dateDesc, pageable));
		return "mobile/product/list"; 
	}
	
	/**
	 * 商品列表
	 * tagIds:1 热销 ，2 新品
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long brandId, Long promotionId, Long[] tagIds, 
			BigDecimal startPrice, BigDecimal endPrice, 
			OrderType orderType, Integer pageNumber, 
			Integer pageSize, HttpServletRequest request, ModelMap model) {
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
		model.addAttribute("page", productService.findPage(null, brand, 
				promotion, tags, null, startPrice, endPrice, true, true, 
				null, false, null, null, orderType, pageable));
		return "mobile/product/list";
	}
	@RequestMapping(value = "/newList", method = RequestMethod.GET)
		public String newList(Long brandId, Long promotionId, 
				Long[] tagIds, BigDecimal startPrice, 
				BigDecimal endPrice, OrderType orderType, 
				Integer pageNumber, Integer pageSize, 
				HttpServletRequest request, ModelMap model) {
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("page", productService.findNewListPage(pageable));
		return "mobile/product/list";
	}
	
	/**
	 * 全部分类功能
	 */
	@RequestMapping(value = "/list/{productCategoryId}", method = RequestMethod.GET)
	public String list(@PathVariable Long productCategoryId, Integer pageNumber, Integer pageSize, HttpServletRequest request, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		if (productCategory == null) {
			throw new ResourceNotFoundException();
		}
		
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("productCategoryId", productCategoryId);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("page", productService.findPage(productCategory, null, null, null, null, null, null,
				true, true, null, false, null, null, OrderType.priceAsc, pageable));
		return "mobile/product/list";
	}
	
	/**
	 * @author guoxianlong
	 */
	@RequestMapping(value = "/listForScroll", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> 
	listForScroll(Long productCategoryId, Integer pageNumber, Integer pageSize, HttpServletRequest request, ModelMap model) {
		Map<String, Object> data = new HashMap<String, Object>();
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		if (productCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, pageSize);
		List<Product> list = productService.findPage(productCategory, null, null, null, null, null, null,
				true, true, null, false, null, null, OrderType.priceAsc, pageable).getContent();
		
		data.put("message", SUCCESS_MESSAGE);
		data.put("results", list);
		return data;
	}
	
	
	/**
	 * 点击数
	 */
	@RequestMapping(value = "/hits/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Long hits(@PathVariable Long id) {
		return productService.viewHits(id);
	}
}