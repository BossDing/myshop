/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import net.shopxx.dao.ProductCategoryDao;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Store;
import net.shopxx.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 商品分类
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("productCategoryDaoImpl")
public class ProductCategoryDaoImpl extends BaseDaoImpl<ProductCategory, Long>
		implements ProductCategoryDao {

	public List<ProductCategory> findRootsByEntcode(Integer count,
			String entcode) {
		Store store = WebUtils.getStore();
		String jpql = "select productCategory from ProductCategory productCategory where productCategory.parent is null";
		if(store == null) {
			jpql += " and productCategory.store is null ";
		}
		jpql += " and productCategory.entcode=:entcode order by productCategory.order asc";
		
		TypedQuery<ProductCategory> query = entityManager.createQuery(jpql, ProductCategory.class)
				.setFlushMode(FlushModeType.COMMIT).setParameter("entcode",
						entcode);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ProductCategory> findRoots(Integer count) {
		String jpql = "select productCategory from ProductCategory productCategory where productCategory.parent is null order by productCategory.order asc";
		TypedQuery<ProductCategory> query = entityManager.createQuery(jpql, ProductCategory.class)
					.setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ProductCategory> findParents(ProductCategory productCategory,
			Integer count) {
		if (productCategory == null || productCategory.getParent() == null) {
			return Collections.<ProductCategory> emptyList();
		}
		String jpql;
		TypedQuery<ProductCategory> query;
		jpql = "select productCategory from ProductCategory productCategory where productCategory.id in (:ids) order by productCategory.grade asc";
		query = entityManager.createQuery(jpql, ProductCategory.class)
				.setFlushMode(FlushModeType.COMMIT).setParameter("ids",
						productCategory.getTreePaths());
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ProductCategory> findChildren(ProductCategory productCategory,
			Integer count) {
		String entcode = WebUtils.getXentcode();
		Store store = WebUtils.getStore();
		TypedQuery<ProductCategory> query;
		String jpql;
		if (productCategory != null) {
			jpql = "select productCategory"
					+ "  from ProductCategory productCategory"
					+ " where productCategory.treePath like :treePath";
			if (entcode != null && !entcode.equals("")) {
				jpql = "select productCategory"
						+ "  from ProductCategory productCategory"
						+ " where productCategory.treePath like :treePath"
						+ "   and productCategory.entcode = :entcode";
				if (store != null) {
					jpql += " and productCategory.store=:store";
				}else {
					jpql += " and productCategory.store is null";
				}
				jpql += " order by productCategory.order asc";
				query = entityManager.createQuery(jpql, ProductCategory.class)
						.setFlushMode(FlushModeType.COMMIT).setParameter(
								"treePath",
								"%" + ProductCategory.TREE_PATH_SEPARATOR
										+ productCategory.getId()
										+ ProductCategory.TREE_PATH_SEPARATOR
										+ "%").setParameter("entcode", entcode);
			} else {
				if (store != null) {
					jpql += " and productCategory.store=:store";
				}else {
					jpql += " and productCategory.store is null";
				}
				jpql += " order by productCategory.order asc";
				query = entityManager.createQuery(jpql, ProductCategory.class)
						.setFlushMode(FlushModeType.COMMIT).setParameter(
								"treePath",
								"%" + ProductCategory.TREE_PATH_SEPARATOR
										+ productCategory.getId()
										+ ProductCategory.TREE_PATH_SEPARATOR
										+ "%");
			}

		} else {
			if (entcode != null && !entcode.equals("")) {
				jpql = "select productCategory"
						+ "  from ProductCategory productCategory"
						+ " where productCategory.entcode = :entcode";
				if (store != null) {
					jpql += " and productCategory.store=:store";
				} else {
					jpql += " and productCategory.store is null";
				}
				jpql += " order by productCategory.order asc";
				query = entityManager.createQuery(jpql, ProductCategory.class)
						.setFlushMode(FlushModeType.COMMIT).setParameter(
								"entcode", entcode);
			} else {
				jpql = "select productCategory"
						+ "  from ProductCategory productCategory";
				if (store != null) {
					jpql += " where productCategory.store=:store";
				}else {
					jpql += " where productCategory.store is null";
				}
				jpql += " order by productCategory.order asc";
				query = entityManager.createQuery(jpql, ProductCategory.class)
						.setFlushMode(FlushModeType.COMMIT);
			}
		}
		if (store != null) {
			query.setParameter("store", store);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		if (store == null) {
			return sort(query.getResultList(), productCategory);
		} else {
			return sortWithStore(query.getResultList(), productCategory);
		}
	}

	/**
	 * 设置treePath、grade并保存
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	@Override
	public void persist(ProductCategory productCategory) {
		Assert.notNull(productCategory);
		setValue(productCategory);
		super.persist(productCategory);
	}

	/**
	 * 设置treePath、grade并更新
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 商品分类
	 */
	@Override
	public ProductCategory merge(ProductCategory productCategory) {
		Assert.notNull(productCategory);
		setValue(productCategory);
		for (ProductCategory category : findChildren(productCategory, null)) {
			setValue(category);
		}
		return super.merge(productCategory);
	}

	/**
	 * 清除商品属性值并删除
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	@Override
	public void remove(ProductCategory productCategory) {
		if (productCategory != null) {
			StringBuffer jpql = new StringBuffer("update Product product set ");
			for (int i = 0; i < Product.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ i;
				if (i == 0) {
					jpql.append("product." + propertyName + " = null");
				} else {
					jpql.append(", product." + propertyName + " = null");
				}
			}
			jpql.append(" where product.productCategory = :productCategory");
			entityManager.createQuery(jpql.toString()).setFlushMode(
					FlushModeType.COMMIT).setParameter("productCategory",
					productCategory).executeUpdate();
			super.remove(productCategory);
		}
	}

	/**
	 * 排序商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 * @param parent
	 *            上级商品分类
	 * @return 商品分类
	 */
	private List<ProductCategory> sort(List<ProductCategory> productCategories,
			ProductCategory parent) {
		List<ProductCategory> result = new ArrayList<ProductCategory>();
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				if ((productCategory.getParent() != null && productCategory
						.getParent().equals(parent))
						|| (productCategory.getParent() == null && parent == null)) {
					result.add(productCategory);
					result.addAll(sort(productCategories, productCategory));
				}
			}
		}
		return result;
	}

	/**
	 * 设置值
	 * 
	 * @param productCategory
	 *            商品分类
	 */
	private void setValue(ProductCategory productCategory) {
		if (productCategory == null) {
			return;
		}
		ProductCategory parent = productCategory.getParent();
		if (parent != null) {
			productCategory.setTreePath(parent.getTreePath() + parent.getId()
					+ ProductCategory.TREE_PATH_SEPARATOR);
		} else {
			productCategory.setTreePath(ProductCategory.TREE_PATH_SEPARATOR);
		}
		productCategory.setGrade(productCategory.getTreePaths().size());
	}

	/**
	 * @Description 根据父类id查询子类
	 * @param parentId
	 * @author Guoxianlong
	 * @create_date Sep 3, 2014 8:59:39 AM
	 */
	public List<ProductCategory> findChildrenByParent(Long parentId) {
		if (parentId == 0) {
			return null;
		}
		ProductCategory parent = find(parentId);
		Store store = WebUtils.getStore();
		if (store == null) {
			String jpql = "select pc from ProductCategory pc where pc.parent = :parent order by pc.order asc";
			return entityManager.createQuery(jpql, ProductCategory.class)
					.setFlushMode(FlushModeType.COMMIT).setParameter("parent",
							parent).getResultList();
		} else {
			String jpql = "select pc from ProductCategory pc where pc.store=:store and pc.parent = :parent order by pc.order asc";
			return entityManager.createQuery(jpql, ProductCategory.class)
					.setFlushMode(FlushModeType.COMMIT).setParameter("parent",
							parent).setParameter("store", store)
					.getResultList();
		}
	}

	public List<ProductCategory> findRoots(Integer count, Long storeId) {
		String jpql;
		TypedQuery<ProductCategory> query;
		if (storeId == null) {
			jpql = "select productCategory from ProductCategory productCategory where productCategory.store is null and productCategory.parent is null order by productCategory.order asc";
			query = entityManager.createQuery(jpql, ProductCategory.class)
					.setFlushMode(FlushModeType.COMMIT);
		} else {
			jpql = "select productCategory from ProductCategory productCategory where productCategory.store=:store and productCategory.parent is null order by productCategory.order asc";
			query = entityManager.createQuery(jpql, ProductCategory.class)
					.setFlushMode(FlushModeType.COMMIT);
			query.setParameter("store", storeId);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public List<ProductCategory> findRootsByEntcode(Integer count,
			Long storeId, String entcode) {
		String jpql;
		TypedQuery<ProductCategory> query;
		if (storeId == null) {
			jpql = "select productCategory from ProductCategory productCategory " +
					"where productCategory.store is null and productCategory.parent is null " +
					"and productCategory.entcode=:entcode order by productCategory.order asc";
			query = entityManager.createQuery(jpql, ProductCategory.class)
					.setFlushMode(FlushModeType.COMMIT).setParameter("entcode",
							entcode);
		} else {
			jpql = "select productCategory from ProductCategory productCategory " +
					"where productCategory.store=:store and productCategory.parent is null " +
					"and productCategory.entcode=:entcode order by productCategory.order asc";
			query = entityManager.createQuery(jpql, ProductCategory.class)
					.setFlushMode(FlushModeType.COMMIT).setParameter("entcode",
							entcode);
//			query.setParameter("store", storeId);
			query.setParameter("store", find(storeId)); 

		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public ProductCategory findProductCategoryByKeyword(String keyword) {
		if (keyword == null) {
			return null;
		}
		String jpql = "select productCategory from ProductCategory productCategory where productCategory.name = :name";
		String entcode = WebUtils.getXentcode();
		if (entcode != null && !entcode.equals("")) {
			// restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
			// .equal(root.get("entcode"), entcode));
			jpql += " and productCategory.entcode = :entcode";
		}
		try {
			return entityManager.createQuery(jpql, ProductCategory.class)
					.setFlushMode(FlushModeType.COMMIT).setParameter("name",
							keyword).setParameter("entcode", entcode)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ProductCategory> findChildrenForStore(
			ProductCategory productCategory, Integer count) {
		String entcode = WebUtils.getXentcode();
		Store store = WebUtils.getStore();
		TypedQuery<ProductCategory> query = null;
		StringBuilder sb = new StringBuilder(
				"select productCategory from ProductCategory productCategory where 1=1");

		if (productCategory != null) {
			sb.append(" and productCategory.treePath like :treePath");
		}
		if (StringUtils.isNotEmpty(entcode)) {
			sb.append(" and productCategory.entcode = :entcode");
		}
		if (store != null) {
			sb.append(" and (productCategory.store = :store or productCategory.store is null)");
		} else {
			sb.append(" and productCategory.store is null");
		}

		sb.append(" order by productCategory.order asc");
		query = entityManager.createQuery(sb.toString(), ProductCategory.class)
				.setFlushMode(FlushModeType.COMMIT);

		if (productCategory != null) {
			query.setParameter("treePath", "%"
					+ ProductCategory.TREE_PATH_SEPARATOR
					+ productCategory.getId()
					+ ProductCategory.TREE_PATH_SEPARATOR + "%");
		}
		if (StringUtils.isNotEmpty(entcode)) {
			query.setParameter("entcode", entcode);
		}
		if (store != null) {
			query.setParameter("store", store);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), productCategory);
	}
	
	public List<ProductCategory> findChildrenForStoreForFront (ProductCategory productCategory, Integer count, Store store) {
		String entcode = WebUtils.getXentcode();
		TypedQuery<ProductCategory> query = null;
		StringBuilder sb = new StringBuilder(
				"select productCategory from ProductCategory productCategory where 1=1");

		if (productCategory != null) {
			sb.append(" and productCategory.treePath like :treePath");
		}
		if (StringUtils.isNotEmpty(entcode)) {
			sb.append(" and productCategory.entcode = :entcode");
		}
		if (store != null) {
			sb.append(" and (productCategory.store = :store or productCategory.store is null)");
		} else {
			sb.append(" and productCategory.store is null");
		}

		sb.append(" order by productCategory.order asc");
		query = entityManager.createQuery(sb.toString(), ProductCategory.class)
				.setFlushMode(FlushModeType.COMMIT);

		if (productCategory != null) {
			query.setParameter("treePath", "%"
					+ ProductCategory.TREE_PATH_SEPARATOR
					+ productCategory.getId()
					+ ProductCategory.TREE_PATH_SEPARATOR + "%");
		}
		if (StringUtils.isNotEmpty(entcode)) {
			query.setParameter("entcode", entcode);
		}
		if (store != null) {
			query.setParameter("store", store);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return sort(query.getResultList(), productCategory);
	}

	/**
	 * 排序商品分类 带店铺属性
	 * 
	 * @param productCategories
	 *            商品分类
	 * @param parent
	 *            上级商品分类
	 * @return 商品分类
	 */
	private List<ProductCategory> sortWithStore(
			List<ProductCategory> productCategories, ProductCategory parent) {
		List<ProductCategory> result = new ArrayList<ProductCategory>();
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				if ((productCategory.getParent() != null && productCategory
						.getParent().equals(parent))
						|| (productCategory.getParent() == null && parent == null)
						|| (productCategory.getParent() != null
								&& productCategory.getParent().getStore() == null && parent == null)) {
					result.add(productCategory);
					result.addAll(sort(productCategories, productCategory));
				}
			}
		}
		return result;
	}

	public List<ProductCategory> findListForDeleteStore(Store store){
		String jpql = "from ProductCategory pc where pc.store = :store";
		return entityManager.createQuery(jpql, ProductCategory.class).
			setParameter("store", store).setFlushMode(FlushModeType.COMMIT).getResultList();
	}
	
}