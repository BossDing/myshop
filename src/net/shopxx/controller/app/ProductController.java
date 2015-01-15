package net.shopxx.controller.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Parameter;
import net.shopxx.entity.ParameterGroup;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Review;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.BrandService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.ReviewService;
import net.shopxx.service.SearchService;
import net.shopxx.service.TagService;
import net.shopxx.util.TwUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * @Controller用于申明该类为一个控制器，其值必须唯一
 * @RequestMapping用于配置响应请求的URL前缀
 * @RequestMapping value用于配置响应请求的URL后缀
 * @RequestMapping method用于配置s响应请求的方式(post / get)
 * String username用于接收请求参数值
 * model.addAttribute("name",  username);用于将变量传递至模板
 * return "shop/hello/view";表示使用 "/WEB-INF/template/shop/hello/view.ftl"模板输出
 */
@Controller("APPproductController")
@RequestMapping("/m/product")
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
	@Resource(name = "reviewServiceImpl")
	private ReviewService reviewService;
	
	/**
	 * 获取商品详情
	 */
	@RequestMapping(value = "/getInfo",method = RequestMethod.POST)
	public @ResponseBody ModelMap getInfo(HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			Long id = Long.parseLong((String)obj.get("id"));
			HashMap<String, Object> productMap = new HashMap<String, Object>();
			
			Product product = productService.find(id);
			productMap.put("id", product.getId());
			productMap.put("fullname", product.getFullName());
			productMap.put("image", product.getMedium());
			productMap.put("price", product.getPrice());
			productMap.put("marketprice", product.getMarketPrice());
			productMap.put("stock", product.getStock());
			
			productList.add(productMap);
			model.put("product", productList);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取热卖商品
	 */
	@RequestMapping(value = "/getHotProduct",method = RequestMethod.POST)
	public @ResponseBody ModelMap getHotProduct(Long[] tagIds,
			BigDecimal startPrice, BigDecimal endPrice, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));
//			System.out.println("pageNumber:"+pageNumber+"、pageSize："+pageSize);
			
			Pageable pageable = new Pageable(pageNumber, pageSize);
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			
			// 查找热卖推荐
			tagIds = new Long[5];
			tagIds[0] = Long.parseLong(Integer.toString(1));
			List<Tag> tags = tagService.findList(tagIds);
			
			for(Product p : productService.findPage(null, null,null, tags, null, startPrice, endPrice,
					true, true, null, false, null, null, null, pageable).getContent()) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("id", p.getId());
				productMap.put("name", p.getName());
				productMap.put("image", p.getThumbnail());
				productMap.put("price", p.getPrice());
				productMap.put("marketprice", p.getMarketPrice());
				productList.add(productMap);
			}
			
			model.put("hotproducts", productList);
			model.put("length", productList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取精品推荐商品
	 */
	@RequestMapping(value = "/getCommendationProducts",method = RequestMethod.POST)
	public @ResponseBody ModelMap getCommendationProducts(Long[] tagIds, 
			BigDecimal startPrice, BigDecimal endPrice, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			JSONObject obj = TwUtil.maptoJsin(map);
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));
			Pageable pageable = new Pageable(pageNumber, pageSize);
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			
			// 查找热卖推荐
			tagIds = new Long[5];
			tagIds[0] = Long.parseLong(Integer.toString(2));
			List<Tag> tags = tagService.findList(tagIds);
			
			for(Product p : productService.findPage(null, null,null, tags, null, startPrice, endPrice,
					true, true, null, false, null, null, null, pageable).getContent()) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("id", p.getId());
				productMap.put("name", p.getName());
				productMap.put("image", p.getThumbnail());
				productMap.put("price", p.getPrice());
				productMap.put("marketprice", p.getMarketPrice());
				productList.add(productMap);
			}
			
			model.put("commendationproducts", productList);
			model.put("length", productList.size());
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取商品介绍
	 */
	@RequestMapping(value = "/getInfo_Xiangxi",method = RequestMethod.POST)
	public @ResponseBody ModelMap getInfo_Xiangxi(HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			JSONObject obj = TwUtil.maptoJsin(map);
			Long id = Long.parseLong((String)obj.get("id"));
			String introduction = productService.find(id).getIntroduction();
			Matcher matcher = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(introduction);
			if(matcher.find()) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("image", matcher.group(1));
				productList.add(productMap);
			}
			
			model.put("Content-Type", "charset=utf-8");
			model.put("datas", productList);
			model.put("introduction", introduction.replace("710", "100%"));
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取商品参数
	 */
	@RequestMapping(value = "/getInfo_Canshu",method = RequestMethod.POST)
	public @ResponseBody ModelMap getInfo_Canshu(HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			ArrayList<HashMap<String, Object>> productList = new ArrayList<HashMap<String, Object>>();
			
			JSONObject obj = TwUtil.maptoJsin(map);
			Long id = Long.parseLong((String)obj.get("id"));
			Product product = productService.find(id);
			if (product == null) {
				model.put("error", "操作错误, 该商品不存在");
				model.put("success", 1);
				return model;
			}
			if(product.getProductCategory().getParameterGroups().size() == 0) {
				model.put("datas", productList);
				model.put("success", 2);
			}
			for(ParameterGroup pg : product.getProductCategory().getParameterGroups()) {
				for(Parameter p : pg.getParameters()) {
					HashMap<String, Object> productMap = new HashMap<String, Object>();
					productMap.put("name", p.getName());
					productMap.put("value", product.getParameterValue().get(p));
					productList.add(productMap);
				}
			}
			
			model.put("datas", productList);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取商品评论
	 */
	@RequestMapping(value = "/getInfo_Pinglun",method = RequestMethod.POST)
	public @ResponseBody ModelMap getInfo_Pinglun(HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			
			JSONObject obj = TwUtil.maptoJsin(map);
			Long id = Long.parseLong((String)obj.get("id"));
			
			Product product = productService.find(id);
			if (product == null) {
				model.put("error", "操作错误, 该商品不存在");
				model.put("success", 1);
				return model;
			}
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("score", product.getScore());
			map1.put("scoreCount", product.getScoreCount());
			list.add(map1);
			
			model.put("list", list);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 获取商品全部评论
	 */
	@RequestMapping(value = "/getInfo_AllPinglun",method = RequestMethod.POST)
	public @ResponseBody ModelMap getInfo_AllPinglun(HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			
			JSONObject obj = TwUtil.maptoJsin(map);
			Long id = Long.parseLong((String)obj.get("id"));
			Integer pageNumber = Integer.valueOf((String) obj.get("pagenumber"));
			Integer pageSize = Integer.valueOf((String) obj.get("pagesize"));
			Pageable pageable = new Pageable(pageNumber, pageSize);
			
			Product product = productService.find(id);
			if (product == null) {
				model.put("error", "操作错误, 该商品不存在");
				model.put("success", 1);
				return model;
			}
			Page<Review> page = reviewService.findPage(null, product, null, true, pageable);
			List<Review> reviews = page.getContent();
			if(reviews.size() == 0) {
				model.put("list", list);
				model.put("success", 2);
				return model;
			}
			for(Review r : reviews) {
				HashMap<String, Object> map2 = new HashMap<String, Object>();
				map2.put("score", r.getScore()==null?5:r.getScore());
				map2.put("content", r.getContent());
				map2.put("date", r.getModifyDate().toString());
				map2.put("memberName", r.getMember()==null?"匿名":r.getMember().getUsername());
				list.add(map2);
			}
			model.put("list", list);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 搜索处理
	 */
	@RequestMapping(value = "/search",method = RequestMethod.POST)
	public @ResponseBody ModelMap search(String keyword, Integer pageNumber, Integer pageSize, HttpServletResponse response, HttpServletRequest request) {
		ModelMap model = null;
		try {
			model = new ModelMap();
			Map<String, Object> map = TwUtil.pareObject(request.getInputStream());
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			Pageable pageable = new Pageable(pageNumber, pageSize);
			
			JSONObject obj = TwUtil.maptoJsin(map);
			keyword = ((String)obj.get("keyword"));
//			System.out.println("搜索的keyword : "+keyword);
			if (StringUtils.isEmpty(keyword)) {
				model.put("error", "搜索条件为空, 请输入正确的条件");
				model.put("success", 1);
				return model;
			}
			List<Product> lists = productService.searchForApp(keyword);
			if(lists.size() == 0) {
				model.put("length", list.size());
				model.put("list", list);
				model.put("success", 2);
				return model;
			}
			
			for(Product product: lists) {
				HashMap<String, Object> productMap = new HashMap<String, Object>();
				productMap.put("id", product.getId());
				productMap.put("fullname", product.getFullName());
				productMap.put("image", product.getThumbnail());
				productMap.put("price", product.getPrice());
				productMap.put("marketprice", product.getMarketPrice());
				list.add(productMap);
			}
			
			model.put("length", list.size());
			model.put("list", list);
			model.put("success", 2);
		} catch (Exception e) {
			model.put("success", 1);
			model.put("error", e.getMessage());
		}
		return model;
	}
	
	/**
	 * 商品列表
	 * tagIds:1 热销 ，2 新品
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Integer pageNumber, Integer pageSize, HttpServletRequest request, ModelMap model) {
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
		model.addAttribute("page", productService.findPage(null, brand, promotion, tags, null, startPrice, endPrice, true, true, null, false, null, null, orderType, pageable));
		return "mobile/product/list";
	}
	@RequestMapping(value = "/newList", method = RequestMethod.GET)
		public String newList(Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Integer pageNumber, Integer pageSize, HttpServletRequest request, ModelMap model) {
		Pageable pageable = new Pageable(pageNumber, pageSize);
		model.addAttribute("page", productService.findNewListPage(pageable));
		return "mobile/product/list";
	}
	/**
	 * 全部分类功能
	 */
	@RequestMapping(value = "/list/{productCategoryId}", method = RequestMethod.GET)
	public String list(@PathVariable Long productCategoryId, Long brandId, Long promotionId, Long[] tagIds, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Integer pageNumber, Integer pageSize, HttpServletRequest request, ModelMap model) {
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
		model.addAttribute("attributeValue", attributeValue);
		model.addAttribute("startPrice", startPrice);
		model.addAttribute("endPrice", endPrice);
		model.addAttribute("orderType", orderType);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", productService.findPage(productCategory, brand, promotion, tags, attributeValue, startPrice, endPrice, true, true, null, false, null, null, orderType, pageable));
		return "mobile/product/list";
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