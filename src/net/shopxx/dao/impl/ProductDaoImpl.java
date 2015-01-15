/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.dao.GoodsDao;
import net.shopxx.dao.ProductDao;
import net.shopxx.dao.SnDao;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Goods;
import net.shopxx.entity.Member;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Payment;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.SpecificationValue;
import net.shopxx.entity.Store;
import net.shopxx.entity.Tag;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.PaymentStatus;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.entity.Sn.Type;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 商品
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements
		ProductDao {

	private static final Pattern pattern = Pattern.compile("\\d*");
	private static final String patternStr = "^\\d+$";

	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	public boolean snExists(String sn) {
		if (sn == null) {
			return false;
		}
		String jpql = "select count(*) from Product product where lower(product.sn) = lower(:sn)";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class)
				.setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn);
		try {
			return query.getSingleResult() > 0;
		} catch (Exception e) {
			e.printStackTrace();   
			return false;
		}
	}

	public Product findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select product from Product product where lower(product.sn) = lower(:sn)";
		Store store = WebUtils.getStore();
		if (store != null) {
			jpql += " and product.store=:store";
		}
		try {
			TypedQuery<Product> query = entityManager.createQuery(jpql,
					Product.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("sn", sn);
			if (store != null) {
				query.setParameter("store", store);
			}
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Product> search(String keyword, Boolean isGift, Integer count) {
		if (StringUtils.isEmpty(keyword)) {
			return Collections.<Product> emptyList();
		}
		keyword = keyword.toLowerCase();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
//		if (pattern.matcher(keyword).matches()) {
		if (Pattern.matches(patternStr, keyword)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("id"), Long.valueOf(keyword)), 
							criteriaBuilder.like(criteriaBuilder.lower(root.<String> get("sn")), "%" + keyword + "%"),
							criteriaBuilder.like(criteriaBuilder.lower(root.<String> get("fullName")),"%" + keyword + "%")));
		} else {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.like(criteriaBuilder.lower(root.<String> get("sn")), "%"+ keyword + "%"),
							criteriaBuilder.like(criteriaBuilder.lower(root.<String> get("fullName")), "%" + keyword + "%")));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}

		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}
		String entcode = WebUtils.getXentcode();//添加企业号作文条件
//		System.out.println("ENTCODE:" + entcode);
		if (StringUtils.isNotEmpty(entcode)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("entcode"), entcode));
		}

		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, null, count, null, null);
	}

	public List<Product> findList(ProductCategory productCategory, Brand brand,
			Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Integer count,
			List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory), criteriaBuilder.like(root.get(
							"productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);

			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry
								.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	/**
	 * @author Guoxianlong
	 * @date Oct 21, 2014 3:53:13 PM
	 */
	public List<Product> findList(ProductCategory productCategory, Brand brand,
			Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Integer count,
			List<Filter> filters, List<Order> orders, Store store) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory), criteriaBuilder.like(root.get(
							"productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		} else {
			restrictions = criteriaBuilder.and(restrictions, root.get("store")
					.isNull());
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);

			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry
								.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<Product> findList(ProductCategory productCategory,
			Date beginDate, Date endDate, Integer first, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(
				root.get("isMarketable"), true));
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory), criteriaBuilder.like(root.get(
							"productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.greaterThanOrEqualTo(root.<Date> get("createDate"),
							beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
      
		Store store = WebUtils.getStore();

		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}
		String entcode = WebUtils.getXentcode();
		if (entcode != null && !entcode.equals("")) {
			if(productCategory.getId()==1080 || productCategory.getId()==1082 || productCategory.getId()==1086
					|| productCategory.getId()==1088|| productCategory.getId()==241){
				entcode = "macrogw";
			}
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("entcode"), entcode));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, first, count, null, null);
	}

	public List<Product> findList(Goods goods, Set<Product> excludes) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (goods != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("goods"), goods));
		}
		if (excludes != null && !excludes.isEmpty()) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.not(root.in(excludes)));
		}

		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}

		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(
				FlushModeType.COMMIT).getResultList();
	}

	public List<Object[]> findSalesList(Date beginDate, Date endDate,
			Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder
				.createQuery(Object[].class);
		Root<Product> product = criteriaQuery.from(Product.class);
		Join<Product, OrderItem> orderItems = product.join("orderItems");
		Join<Product, net.shopxx.entity.Order> order = orderItems.join("order");
		criteriaQuery.multiselect(product.get("id"), product.get("sn"), product
				.get("name"), product.get("fullName"), product.get("price"),
				criteriaBuilder.sum(orderItems.<Integer> get("quantity")),
				criteriaBuilder.sum(criteriaBuilder.prod(orderItems
						.<Integer> get("quantity"), orderItems
						.<BigDecimal> get("price"))));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.greaterThanOrEqualTo(order.<Date> get("createDate"),
							beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder
					.and(restrictions, criteriaBuilder.lessThanOrEqualTo(order
							.<Date> get("createDate"), endDate));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(product.get("store"), store));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(
				order.get("orderStatus"), OrderStatus.completed),
				criteriaBuilder.equal(order.get("paymentStatus"),
						PaymentStatus.paid));
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(product.get("id"), product.get("sn"), product
				.get("name"), product.get("fullName"), product.get("price"));
		criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder
				.sum(criteriaBuilder.prod(orderItems.<Integer> get("quantity"),
						orderItems.<BigDecimal> get("price")))));
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery)
				.setFlushMode(FlushModeType.COMMIT);
		if (count != null && count >= 0) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public Page<Product> findPage(ProductCategory productCategory, Brand brand,
			Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory), criteriaBuilder.like(root.get(
							"productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}

		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			//for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				// String propertyName =
				// Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX +
				// entry.getKey().getPropertyIndex();
				// restrictions = criteriaBuilder.and(restrictions,
				// criteriaBuilder.equal(root.get(propertyName),
				// entry.getValue()));
			//}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		} else {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.isNull(root.get("store")));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesAsc) {
			orders.add(Order.asc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreAsc) {
			orders.add(Order.asc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateAsc) {
			orders.add(Order.asc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}
	public Page<Product> findPageByCategoryList(List<ProductCategory> productCategorys,
			Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Pageable pageable) {
    
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategorys != null && productCategorys.size()>0) {
			List<Predicate> predicatesList = new ArrayList<Predicate>();
			for(int i=0;i<productCategorys.size();i++){
				Predicate predicate1 = criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategorys.get(i)));
				Predicate predicate2 = criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%"
						+ ProductCategory.TREE_PATH_SEPARATOR
						+ productCategorys.get(i).getId()
						+ ProductCategory.TREE_PATH_SEPARATOR + "%");
				predicatesList.add(predicate1);
				predicatesList.add(predicate2);
			}
			Predicate p = criteriaBuilder.or(predicatesList.toArray(new Predicate[predicatesList.size()]));
			restrictions = criteriaBuilder.and(restrictions,p);
			}
		if (brand != null) {  
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}

		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);					
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry
								.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		String entcode = WebUtils.getXentcode();
		if (entcode != null && !entcode.equals("")) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("entcode"), entcode));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesAsc) {
			orders.add(Order.asc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreAsc) {
			orders.add(Order.asc("score"));
			orders.add(Order.desc("createDate"));
		}else if (orderType == OrderType.dateAsc) {
			orders.add(Order.asc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	
		
	}
	public Page<Product> findPageByEntcode(ProductCategory productCategory,
			Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory),criteriaBuilder.equal(root.get("productCategory"),
									productCategory)
							,criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")
							));
		}
		if (brand != null) {  
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}

		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);					
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry
								.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		String entcode = WebUtils.getXentcode();
		if (entcode != null && !entcode.equals("")) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("entcode"), entcode));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesAsc) {
			orders.add(Order.asc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreAsc) {
			orders.add(Order.asc("score"));
			orders.add(Order.desc("createDate"));
		}else if (orderType == OrderType.dateAsc) {
			orders.add(Order.asc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Product> findGWPage(ProductCategory productCategory,
			Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory), criteriaBuilder.like(root.get(
							"productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}

		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry
								.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}

		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesAsc) {
			orders.add(Order.asc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreAsc) {
			orders.add(Order.asc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Product> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return new Page<Product>(Collections.<Product> emptyList(), 0,
					pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);

		Store store = WebUtils.getStore();
		if (store == null)
			criteriaQuery.where(criteriaBuilder.equal(root
					.join("favoriteMembers"), member));
		else
			criteriaQuery.where(criteriaBuilder.equal(root
					.join("favoriteMembers"), member), criteriaBuilder.equal(
					root.get("store"), store));

		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Member favoriteMember, Boolean isMarketable,
			Boolean isList, Boolean isTop, Boolean isGift,
			Boolean isOutOfStock, Boolean isStockAlert) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (favoriteMember != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.join("favoriteMembers"), favoriteMember));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}

		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}

		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public boolean isPurchased(Member member, Product product) {
		if (member == null || product == null) {
			return false;
		}
		String jqpl = "select count(*) from OrderItem orderItem where orderItem.product = :product and orderItem.order.member = :member and orderItem.order.orderStatus = :orderStatus";
		try {
			Long count = entityManager.createQuery(jqpl, Long.class).setFlushMode(
					FlushModeType.COMMIT).setParameter("product", product)
					.setParameter("member", member).setParameter("orderStatus",
							OrderStatus.completed).getSingleResult();
			return count > 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 设置值并保存
	 * 
	 * @param product
	 *            商品
	 */
	@Override
	public void persist(Product product) {
		Assert.notNull(product);
		setValue(product);
		super.persist(product);
	}

	/**
	 * 设置值并更新
	 * 
	 * @param product
	 *            商品
	 * @return 商品
	 */
	@Override
	public Product merge(Product product) {
		Assert.notNull(product);

		if (!product.getIsGift()) {
			String jpql = "delete from GiftItem giftItem where giftItem.gift = :product";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT)
					.setParameter("product", product).executeUpdate();
		}
		if (!product.getIsMarketable() || product.getIsGift()) {
			String jpql = "delete from CartItem cartItem where cartItem.product = :product";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT)
					.setParameter("product", product).executeUpdate();
		}

		setValue(product);
		return super.merge(product);
	}

	@Override
	public void remove(Product product) {
		if (product != null) {
			Goods goods = product.getGoods();
			if (goods != null && goods.getProducts() != null) {
				goods.getProducts().remove(product);
				if (goods.getProducts().isEmpty()) {
					goodsDao.remove(goods);
				}
			}
		}

		super.remove(product);
	}

	/**
	 * 设置值
	 * 
	 * @param product
	 *            商品
	 */
	private void setValue(Product product) {
		if (product == null) {
			return;
		}
		if (StringUtils.isEmpty(product.getSn())) {
			String sn;
			do {
				sn = snDao.generate(Type.product);
			} while (snExists(sn));
			product.setSn(sn);
		}
		StringBuffer fullName = new StringBuffer(product.getName());
		if (product.getSpecificationValues() != null
				&& !product.getSpecificationValues().isEmpty()) {
			List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>(
					product.getSpecificationValues());
			Collections.sort(specificationValues,
					new Comparator<SpecificationValue>() {
						public int compare(SpecificationValue a1,
								SpecificationValue a2) {
							return new CompareToBuilder().append(
									a1.getSpecification(),
									a2.getSpecification()).toComparison();
						}
					});
			fullName.append(Product.FULL_NAME_SPECIFICATION_PREFIX);
			int i = 0;
			for (Iterator<SpecificationValue> iterator = specificationValues
					.iterator(); iterator.hasNext(); i++) {
				if (i != 0) {
					fullName.append(Product.FULL_NAME_SPECIFICATION_SEPARATOR);
				}
				fullName.append(iterator.next().getName());
			}
			fullName.append(Product.FULL_NAME_SPECIFICATION_SUFFIX);
		}
		product.setFullName(fullName.toString());
	}

	public Page<Product> findNewListPage(Pageable pageable) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);

		Store store = WebUtils.getStore();
		if (store == null)
			criteriaQuery.where(criteriaBuilder.equal(root
					.get("productCategory"), 80));
		else
			criteriaQuery.where(criteriaBuilder.equal(root
					.get("productCategory"), 80), criteriaBuilder.equal(root
					.get("store"), store));

		return super.findPage(criteriaQuery, pageable);
	}

	public List<Product> findList(ProductCategory productCategory, Brand brand,
			Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Integer count,
			List<Filter> filters, List<Order> orders, Boolean isPromotion) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory), criteriaBuilder.like(root.get(
							"productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry
								.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		if (isPromotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isPromotion"), isPromotion));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<Product> findListForApp(Long tag) {
		if (tag.longValue() == 0L) {
			return null;
		}
		String jpql = " select product.id,product.sn,product.name,product.price,product.image from Product product";
		Store store = WebUtils.getStore();
		if (store != null) {
			jpql += " where product.store=:store";
		}
		TypedQuery<Product> query = entityManager.createQuery(jpql,
				Product.class).setFlushMode(FlushModeType.COMMIT);
		if (store != null)
			query.setParameter("store", store);
		return query.getResultList();
	}

	public Page<Product> findPage(ProductCategory productCategory, Brand brand,
			Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Pageable pageable,
			Boolean isPromotion) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory), criteriaBuilder.like(root.get(
							"productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry
								.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		if (isPromotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isPromotion"), isPromotion));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public List<Product> findByItemSpec(String item) {
		List<Product> list = new ArrayList<Product>();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (item != null) {
			restrictions = criteriaBuilder.like(root
					.<String> get("attributeValue0"), item);
		}

		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}

		criteriaQuery.where(restrictions);
		list = super.findList(criteriaQuery, null, null, null, null);
		return list;
	}

	public List<Product> findHotProductList(Integer count) {
		String jpql = "select product from Product product";

		Store store = WebUtils.getStore();
		if (store != null) {
			jpql += " where product.store=:store";
		}

		jpql += " order by product.sales desc";
		TypedQuery<Product> query = entityManager.createQuery(jpql,
				Product.class).setFlushMode(FlushModeType.COMMIT);

		if (store != null)
			query.setParameter("store", store);

		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}
	
	public List<Product> findHotProductListByCreateDate(Integer count) {
		String jpql = "select product from Product product where product.isMarketable = true and product.isList = true";

		Store store = WebUtils.getStore();
		if (store != null) {
			jpql += " and product.store=:store";
		}

		jpql += " order by product.createDate desc";
		TypedQuery<Product> query = entityManager.createQuery(jpql,
				Product.class).setFlushMode(FlushModeType.COMMIT);

		if (store != null)
			query.setParameter("store", store);

		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	/**
	 * @Description 根据名称查询商品
	 * @author Guoxianlong
	 */
	public List<Product> searchForApp(String name) {
		String jpql = "select product from Product product where lower(product.fullName) like lower(:name) and product.isMarketable = true and product.isList = true";

		Store store = WebUtils.getStore();
		if (store != null) {
			jpql += " and product.store=:store";
		}
		TypedQuery<Product> query = entityManager.createQuery(jpql,
				Product.class).setFlushMode(FlushModeType.COMMIT).setParameter(
				"name", "%" + name + "%");

		if (store != null)
			query.setParameter("store", store);

		return query.getResultList();
	}

	public Page<Product> findPageByEntcodeForCheck(
			ProductCategory productCategory, Brand brand, Promotion promotion,
			List<Tag> tags, Map<Attribute, String> attributeValue,
			BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
			Boolean isList, Boolean isTop, Boolean isGift,
			Boolean isOutOfStock, Boolean isStockAlert, OrderType orderType,
			Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),productCategory)
							,criteriaBuilder.like(root.get("productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")
							));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}

		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry
								.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		String entcode = WebUtils.getXentcode();
		if (entcode != null && !entcode.equals("")) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("entcode"), entcode));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
				.equal(root.get("checkStatus"), Product.CheckStatus.waitting));
		
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesAsc) {
			orders.add(Order.asc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreAsc) {
			orders.add(Order.asc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}
	
	public Long selectMaxId() {
		try {
			String jpql = "select max(id)+1 from Product ";
			TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT);
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Product findByProductName(String name) {
		if (name == null) {
//			System.out.println("null");
			return null;
		}
		String jpql = "select product from Product product where lower(product.name) = lower(:name)";
		Store store = WebUtils.getStore();
		if (store != null) {
			jpql += " and product.store=:store";
		}
		try {
			TypedQuery<Product> query = entityManager.createQuery(jpql,
					Product.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("name", name);
			if (store != null) {
				query.setParameter("store", store);
			}
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Page<Product> findPageGW(ProductCategory productCategory,
			Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Pageable pageable) {
		// TODO Auto-generated method stub

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory), criteriaBuilder.like(root.get(
							"productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}

		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			//for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				// String propertyName =
				// Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX +
				// entry.getKey().getPropertyIndex();
				// restrictions = criteriaBuilder.and(restrictions,
				// criteriaBuilder.equal(root.get(propertyName),
				// entry.getValue()));
			//}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Store store = WebUtils.getStore();
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
//		pageable.setOrderDirection(Direction.asc);
//		pageable.setOrderProperty("order");
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
//		System.out.println("dao-order");
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesAsc) {
			orders.add(Order.asc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreAsc) {
			orders.add(Order.asc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateAsc) {
			orders.add(Order.asc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<Product> findListGW(ProductCategory productCategory,
			Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, OrderType orderType, Integer count,
			List<Filter> filters, List<Order> orders, Store store, Boolean px, Boolean px1) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();

		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.equal(root.get("productCategory"),
							productCategory), criteriaBuilder.like(root.get(
							"productCategory").<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("brand"), brand));
		}
		if (store != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("store"), store));
		} else {
			restrictions = criteriaBuilder.and(restrictions, root.get("store")
					.isNull());
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);

			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"),
							promotion));
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory")
							.join("promotions"), promotion));
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join(
							"promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.exists(subquery1), criteriaBuilder
							.exists(subquery2), criteriaBuilder
							.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root),
					subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry
								.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isGift"), isGift));
		}
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock),
								criteriaBuilder.greaterThan(stock,
										allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.isNotNull(stock), criteriaBuilder
								.lessThanOrEqualTo(stock, criteriaBuilder.sum(
										allocatedStock, setting
												.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder
						.and(restrictions, criteriaBuilder.or(criteriaBuilder
								.isNull(stock), criteriaBuilder.greaterThan(
								stock, criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		if(px){
//			System.out.println("进入dao，px："+px);
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("orderNew")));
		}
		if(px1){
//			System.out.println("进入dao，px1："+px1);
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("orderHot")));
		}
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}
	
	public List<Product> findListForDeleteStore(Store store){
		String jpql = "from Product pd where pd.store = :store";
		return entityManager.createQuery(jpql, Product.class).setParameter("store", store).
			setFlushMode(FlushModeType.COMMIT).getResultList();
	}
}