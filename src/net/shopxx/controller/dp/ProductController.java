package net.shopxx.controller.dp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.shopxx.Pageable;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Store;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.BrandService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.SearchService;
import net.shopxx.service.StoreService;
import net.shopxx.service.TagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 店铺
 * 
 * @author Cgd
 * @version 1.0
 */
@Controller("dpProductController")
@RequestMapping("/dp/product")
public class ProductController extends BaseController {

	@Resource(name = "storeServiceImpl")
	private StoreService storeService;

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
	 * 展示所有产品
	 * 
	 * @param productCategoryId
	 *            分类id
	 * @param brandId
	 *            品牌id
	 * @param promotionId
	 *            促销id
	 * @param tagIds
	 *            标签id
	 * @param startPrice
	 *            最低价
	 * @param endPrice
	 *            最高价
	 * @param orderType
	 *            排序类型
	 * @param isOutOfStock
	 *            是否缺货
	 * @param storeId
	 *            店铺id
	 * @param pageNumber
	 *            第几页
	 * @param pageSize
	 *            页容量
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(String keyword, Long productCategoryId, Long brandId,
			Long promotionId, Long[] tagIds, BigDecimal startPrice,
			BigDecimal endPrice, Product.OrderType orderType,
			Boolean isOutOfStock, Long storeId, Integer pageNumber,
			Integer pageSize, HttpServletRequest request, ModelMap model) {
		
		if (storeId == null) {
			return ERROR_VIEW;
		}
		Store store = storeService.find(storeId); // 根据店铺id，获取店铺信息
		if(store == null || !store.getIsEnabled()) {
			return "redirect:store/list.jhtml";
		}
		request.getSession().setAttribute("store", store); // 把当前店铺放入session中，方便在dao查询数据库时获取
		ProductCategory productCategory = null;
		if (productCategoryId != null) {
			productCategory = productCategoryService.find(productCategoryId); // 根据分类id，获取分类信息
		}
		Brand brand = brandService.find(brandId);// 根据品牌id，获取品牌信息
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagIds);// 根据标签id，获取标签信息
		Map<Attribute, String> attributeValue = new HashMap<Attribute, String>();
		/**
		 * 根据分类属性,获取属性值
		 */
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

		Pageable pageable = new Pageable(pageNumber, pageSize);// 初始化分页信息
		pageable.setSearchProperty("name");
		pageable.setSearchValue(keyword);
		model.addAttribute("keyword", keyword); //关键字
		model.addAttribute("orderTypes", OrderType.values());// 所有排序类型
		model.addAttribute("productCategory", productCategory);// 当前分类
		model.addAttribute("productCategories", productCategoryService
				.findTreeForStore());// 商城及该店铺的分类
		model.addAttribute("brand", brand); // 品牌
		model.addAttribute("promotion", promotion); // 促销
		model.addAttribute("tags", tags); // 标签
		model.addAttribute("isOutOfStock", isOutOfStock); // 是否缺货
		model.addAttribute("attributeValue", attributeValue); // 属性值
		model.addAttribute("startPrice", startPrice); // 起始价
		model.addAttribute("endPrice", endPrice); // 末尾价
		model.addAttribute("orderType", orderType); // 排序类型
		model.addAttribute("pageNumber", pageNumber); // 第几页
		model.addAttribute("pageSize", pageSize); // 页容量
		model.addAttribute("page", productService.findPageByEntcode(
				productCategory, brand, promotion, tags, attributeValue,
				startPrice, endPrice, true, true, null, false, isOutOfStock,
				null, orderType, pageable)); // 根据条件获取商品(分页)
		model.addAttribute("store", store);// 店铺信息
		model.addAttribute("hotProducts", productService.findHotProductListByCreateDate(4));// 获取热销商品（至多8条）

		request.getSession().removeAttribute("store"); // 从session中移除store
		return "/dp/product/list";
	}

}
