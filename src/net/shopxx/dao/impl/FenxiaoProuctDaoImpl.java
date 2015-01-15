package net.shopxx.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.Setting;
import net.shopxx.dao.FenxiaoProductDao;
import net.shopxx.entity.Attribute;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;
import net.shopxx.entity.Product.OrderType;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Promotion;
import net.shopxx.entity.Tag;
import net.shopxx.util.SettingUtils;
import net.shopxx.util.WebUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author guoxl
 *
 */
@Repository("fenxiaoProductDaoImpl")
public class FenxiaoProuctDaoImpl extends BaseDaoImpl<Product, Long> implements
		FenxiaoProductDao {
	
	@Override
	public Product find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product find(Long id, LockModeType lockModeType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findList(Integer first, Integer count,
			List<Filter> filters, List<Order> orders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(Filter... filters) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void persist(Product entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Product merge(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Product entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Product entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Product entity, LockModeType lockModeType) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long getIdentifier(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isManaged(Product entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void detach(Product entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lock(Product entity, LockModeType lockModeType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<Product> findPage(List<Filter> filters, List<Order> orders,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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
		/** 允许分销 **/
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
				.equal(root.get("isUseFenxiao"), true));
		
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isList"), isList));
		}
		String entcode = WebUtils.getXentcode();
		if (entcode != null && !entcode.equals("")) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("entcode"), entcode));
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
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

}
