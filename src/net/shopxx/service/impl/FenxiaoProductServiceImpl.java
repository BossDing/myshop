package net.shopxx.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.CacheManager;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.FenxiaoProductDao;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.service.FenxiaoProductService;
import net.shopxx.service.StaticService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author guoxl
 */
@Service("fenxiaoProductServiceImpl")
public class FenxiaoProductServiceImpl extends BaseServiceImpl<Product, Long>
		implements FenxiaoProductService {

	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	@Resource(name = "fenxiaoProductDaoImpl")
	private FenxiaoProductDao fenxiaoProductDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Resource(name = "fenxiaoProductDaoImpl")
	public void setBaseDao(FenxiaoProductDao fenxiaoProductDao) {
		super.setBaseDao(fenxiaoProductDao);
	}

	@Transactional(readOnly = true)
	public Page<Product> findPageByEntcode(ProductCategory productCategory,
			Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Pageable pageable) {
		
		return fenxiaoProductDao.findPageByEntcode(productCategory, brand, promotion,
				tags, attributeValue, startPrice, endPrice, isMarketable,
				isList, isTop, isGift, isOutOfStock, isStockAlert, orderType,
				pageable);
	}

}
