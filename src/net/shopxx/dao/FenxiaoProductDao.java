package net.shopxx.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;

/**
 * 
 * @author guoxl
 *
 */
public interface FenxiaoProductDao extends BaseDao<Product, Long> {
	
	/**
	 * 查找商品分页(分销商品列表页)
	 * @param productCategory
	 * @param brand
	 * @param promotion
	 * @param tags
	 * @param attributeValue
	 * @param startPrice
	 * @param endPrice
	 * @param isMarketable
	 * @param isList
	 * @param isTop
	 * @param isGift
	 * @param isOutOfStock
	 * @param isStockAlert
	 * @param orderType
	 * @param pageable
	 * @return
	 */
	Page<Product> findPageByEntcode(ProductCategory productCategory,
			Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Pageable pageable);

}
