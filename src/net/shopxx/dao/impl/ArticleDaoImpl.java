/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.ArticleDao;
import net.shopxx.entity.AdPosition;
import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;
import net.shopxx.entity.Product;
import net.shopxx.entity.Tag;

import org.springframework.stereotype.Repository;

/**
 * Dao - 文章
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("articleDaoImpl")
public class ArticleDaoImpl extends BaseDaoImpl<Article, Long> implements ArticleDao { 

	public List<Article> findList(ArticleCategory articleCategory, List<Tag> tags, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		Root<Article> root = criteriaQuery.from(Article.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), true));
		if (articleCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("articleCategory"), articleCategory), criteriaBuilder.like(root.get("articleCategory").<String> get("treePath"), "%" + ArticleCategory.TREE_PATH_SEPARATOR + articleCategory.getId() + ArticleCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Article> subquery = criteriaQuery.subquery(Article.class);
			Root<Article> subqueryRoot = subquery.from(Article.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
//		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<Article> findList(ArticleCategory articleCategory, Date beginDate, Date endDate, Integer first, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		Root<Article> root = criteriaQuery.from(Article.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), true));
		if (articleCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("articleCategory"), articleCategory), criteriaBuilder.like(root.get("articleCategory").<String> get("treePath"), "%" + ArticleCategory.TREE_PATH_SEPARATOR + articleCategory.getId() + ArticleCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, first, count, null, null);
	}

	public Page<Article> findPage(ArticleCategory articleCategory, List<Tag> tags, Pageable pageable) {
//		System.out.println("进入articledao");
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		Root<Article> root = criteriaQuery.from(Article.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), true));
		if (articleCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("articleCategory"), articleCategory), criteriaBuilder.like(root.get("articleCategory").<String> get("treePath"), "%" + ArticleCategory.TREE_PATH_SEPARATOR + articleCategory.getId() + ArticleCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Article> subquery = criteriaQuery.subquery(Article.class);
			Root<Article> subqueryRoot = subquery.from(Article.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		criteriaQuery.where(restrictions);
//		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("createDate"));
		pageable.setOrders(orders);
//		if(pageable.getOrderProperty() == null){
//			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
//		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Article findByName(String name) {
		if (name == null) {
			return null;
		}
		String jpql = "select article from Article article where lower(article.title) = lower(:title)";
		try {
			return entityManager.createQuery(jpql, Article.class).setFlushMode(FlushModeType.COMMIT).setParameter("title", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Page<Article> findNewArticle(ArticleCategory articleCategory) {
		/*String jpql = "select article from (select article from Article article order by article.createDate desc) where rownum = 1 ";
		try {
			return entityManager.createQuery(jpql, Article.class).setFlushMode(FlushModeType.COMMIT).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}*/
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		Root<Article> root = criteriaQuery.from(Article.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), true));
		if (articleCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("articleCategory"), articleCategory), criteriaBuilder.like(root.get("articleCategory").<String> get("treePath"), "%" + ArticleCategory.TREE_PATH_SEPARATOR + articleCategory.getId() + ArticleCategory.TREE_PATH_SEPARATOR + "%")));
		}
		criteriaQuery.where(restrictions);
		Pageable pageable = new Pageable(1, 1);
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("createDate"));
		pageable.setOrders(orders);
		return super.findPage(criteriaQuery, pageable);
	}

	public List<Article> findByContent(String problemType, ArticleCategory articleCategory) {
//		System.out.println("dao");
//		System.out.println(articleCategory.getName());
		if (problemType == null) {
			return null;
		}
		String jpql = "select article from Article article where lower(article.content) like lower(:content)";
		if (articleCategory != null) {
			jpql += " and article.articleCategory=:articleCategory";
		}
		try {
			TypedQuery<Article> query = entityManager.createQuery(jpql, Article.class).setFlushMode(FlushModeType.COMMIT).setParameter("content", "%"+problemType+"%");
			if (articleCategory != null)
			query.setParameter("articleCategory", articleCategory);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Article> findByTitle(String name, ArticleCategory articleCategory) {
		System.out.println("dao");
		System.out.println(name);
		System.out.println(articleCategory);
		if (name == null) {
			return null;
		}
		String jpql = "select article from Article article where lower(article.title) like lower(:title)";
		if (articleCategory != null) {
			jpql += " and article.articleCategory=:articleCategory";
		}
		try {
			TypedQuery<Article> query = entityManager.createQuery(jpql, Article.class).setFlushMode(FlushModeType.COMMIT).setParameter("title", "%"+name+"%");
			if (articleCategory != null)
				query.setParameter("articleCategory", articleCategory);
				return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Page<Article> findPageOrderBySetting(
			ArticleCategory articleCategory, List<Tag> tags, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		Root<Article> root = criteriaQuery.from(Article.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPublication"), true));
		if (articleCategory != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("articleCategory"), articleCategory), criteriaBuilder.like(root.get("articleCategory").<String> get("treePath"), "%" + ArticleCategory.TREE_PATH_SEPARATOR + articleCategory.getId() + ArticleCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Article> subquery = criteriaQuery.subquery(Article.class);
			Root<Article> subqueryRoot = subquery.from(Article.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		criteriaQuery.where(restrictions);
//		if (pageable.getOrderProperty()==null) {
//			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
//		}
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("createDate"));
		pageable.setOrders(orders);
		return super.findPage(criteriaQuery, pageable);
	}

}