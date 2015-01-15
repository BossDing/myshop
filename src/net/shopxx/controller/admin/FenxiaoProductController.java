/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Goods;
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
import net.shopxx.service.BrandService;
import net.shopxx.service.FenxiaoProductService;
import net.shopxx.service.GoodsService;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.service.ProductService;
import net.shopxx.service.PromotionService;
import net.shopxx.service.TagService;
import net.shopxx.util.WebUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 分销商品
 * 
 * @author guoxl
 * 
 */
@Controller("adminFenxiaoProductController")
@RequestMapping("/admin/fenxiao_product")
public class FenxiaoProductController extends BaseController {

	@Resource(name = "fenxiaoProductServiceImpl")
	private FenxiaoProductService fenxiaoProductService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long productCategoryId, Long brandId, Long promotionId,
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
			model.addAttribute("page", fenxiaoProductService.findPageByEntcode(
					productCategory, brand, promotion, tags, null, null, null,
					isMarketable, isList, isTop, isGift, isOutOfStock,
					isStockAlert, OrderType.dateDesc, pageable));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/fenxiao_product/list";
	}

	/**
	 * 同步
	 */
	@RequestMapping(value = "/tongbu", method = RequestMethod.POST)
	public @ResponseBody
	Message tongbu(Long[] ids) {
		if (ids.length == 0) {
			return Message.error("数据异常，请重新操作");
		}
		Store store = WebUtils.getStore();
		if (store == null)
			return Message.error("操作失败，只有店铺管理员才能同步");
		List<Product> productList = productService.findList(ids);
		Tag tag = tagService.find(1L); // 热销标签
		BigDecimal zero = new BigDecimal(0);
		Class<Product> classType = Product.class;
		Field[] fields = classType.getDeclaredFields();
		for (Product oldProduct : productList) {
			Goods newGoods = new Goods();
			Set<Product> newProducts = newGoods.getProducts();
			Goods oldGoods = oldProduct.getGoods();
			Set<Product> productSet = oldGoods.getProducts();
			for (Product p : productSet) {
				Product newProduct = new Product();
				copyProperties(p, newProduct, newGoods, fields, store, tag,
						zero);
				newProducts.add(newProduct);
			}
			goodsService.save(newGoods);
		}
		return SUCCESS_MESSAGE;
	}

	private void copyProperties(Product srcProduct, Product targetProduct,
			Goods newGoods, Field[] fields, Store store, Tag tag,
			BigDecimal zero) {
		for (Field field : fields) {
			String fieldTypeName = field.getType().getName();
			/**
			 * 设置过滤属性
			 */
			int modify = field.getModifiers();
			if (Modifier.isFinal(modify) || Modifier.isStatic(modify)) {
				continue;
			}
			if (fieldTypeName.contains("List") || fieldTypeName.contains("Set")
					|| fieldTypeName.contains("Map")
					|| fieldTypeName.contains("Goods")) {
				continue;
			}
			// 设置成员变量访问权
			field.setAccessible(true);
			try {
				field.set(targetProduct, field.get(srcProduct));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		targetProduct.setCost(zero);// 成本价
		targetProduct.setStock(0);// 库存
		targetProduct.setAllocatedStock(0);// 已分配库存
		targetProduct.setStockMemo(null);// 库存备注
		targetProduct.setIsMarketable(true);// 默认上架
		targetProduct.setScore(0f);// 平分
		targetProduct.setTotalScore(0L);// 总评分
		targetProduct.setScoreCount(0L);// 评分数
		targetProduct.setHits(0L);// 点击数
		targetProduct.setWeekHits(0L);// 周点击数
		targetProduct.setMonthHits(0L);// 周点击数
		targetProduct.setSales(0L);// 销量
		targetProduct.setWeekSales(0L);// 周点销量
		targetProduct.setMonthSales(0L);// 月点销量
		targetProduct.setGoods(newGoods);// 货品
		targetProduct.setCreateDate(srcProduct.getCreateDate());// 创建日期
		targetProduct.setModifyDate(new Date());// 修改日期
		targetProduct.setIsUseFenxiao(false);// 默认非分销
		targetProduct.setSn(srcProduct.getSn() + "-FX-" + store.getId());
		targetProduct.setStore(store);// 设置店铺
		targetProduct.setSourceId(srcProduct.getId());// 设置来源id

		List<ProductImage> productImages = srcProduct.getProductImages();
		targetProduct.getProductImages().addAll(productImages); // 商品图片
		Set<Specification> spcs = srcProduct.getSpecifications();
		for (Specification specification : spcs) {
			specification.setProducts(null);
		}
		targetProduct.getSpecifications().addAll(spcs);// 规格
		Set<SpecificationValue> spvs = srcProduct.getSpecificationValues();
		for (SpecificationValue specificationValue : spvs) {
			specificationValue.setProducts(null);
		}
		targetProduct.getSpecificationValues().addAll(spvs);// 规格值
		targetProduct.getParameterValue()
				.putAll(srcProduct.getParameterValue());// 参数值
		/*Set<Tag> tags = srcProduct.getTags();
		for (Tag t : tags) {
			t.setProducts(null);
		}
		if (!tags.contains(tag)) {
			tags.add(tag);
		}
		targetProduct.getTags().addAll(tags);// 标签
*/		targetProduct.getTags().add(tag);// 标签
	}
}