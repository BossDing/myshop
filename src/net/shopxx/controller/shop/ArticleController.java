/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.shop;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.ResourceNotFoundException;
import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;
import net.shopxx.entity.Product;
import net.shopxx.service.ArticleCategoryService;
import net.shopxx.service.ArticleService;
import net.shopxx.service.ProductService;
import net.shopxx.service.SearchService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 文章
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("shopArticleController")
@RequestMapping("/article")
public class ArticleController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 5;

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;

	/**
	 * 所有文章分类列表
	 * @param id
	 * @param model
	 * @returnStringhfh
	 */
	@RequestMapping(value = "/categoryList/{id}", method = RequestMethod.GET)
	public String categoryList(@PathVariable Long id, ModelMap model){
//		System.out.println(id);
		model.addAttribute("articleCategoryId", id);
		ArticleCategory articleCategory = articleCategoryService.find(id);
/*		ArticleCategory parent = articleCategoryService.find(id);
		if (parent == null) {
			throw new ResourceNotFoundException();
		}
		List<ArticleCategory> articleCategories = articleCategoryService.findChildren(parent, 6);
		model.addAttribute("articleCategories", articleCategories);
		
		for(ArticleCategory articleCategory : articleCategories){
			List<Article> articles = articleService.findList(articleCategory, null, 2, null, null);
			model.addAttribute("articles", articles);
		}*/
		//List<Article> findList(ArticleCategory articleCategory, List<Tag> tags, Integer count, List<Filter> filters, List<Order> orders);
//		System.out.println(articleCategory.getName());
		Page<Article> page = articleService.findNewArticle(articleCategory);
		model.addAttribute("page", page);
//		System.out.println(page.getContent().get(0).getTitle()+"---"+page.getContent().get(0).getId());
		return "shop/article/catogoryList";
	}
	
	/**
	 * 分类文章列表
	 */
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public String list(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		System.out.println("list="+id);
		ArticleCategory articleCategory = articleCategoryService.find(id);
		if (articleCategory == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleCategory", articleCategory);
		model.addAttribute("page", articleService.findPage(articleCategory, null, pageable));
		String name = articleCategory.getName().trim();
		if(name.equals("购物指南")||name.equals("服务政策")||name.equals("物流服务")||name.equals("支付方式")||name.equals("售后服务")||name.equals("关于我们")){
			if(null!=articleCategory.getArticles()&&articleCategory.getArticles().size()>0){
				model.addAttribute("article",articleCategory.getArticles().iterator().next());
			}
			return "/shop/article/guide";
		}
		List<Article> articles =  articleService.findList(articleCategory, null,PAGE_SIZE, null, null);
		model.addAttribute("articles", articles);
		model.addAttribute("articleCategory", articleCategory);
		return "/shop/article/list";
	}
	
	@RequestMapping(value = "/content/{id}", method = RequestMethod.GET)
	public String content(@PathVariable Long id, ModelMap model) {
			System.out.println("content="+id);
			hits(id);
			Article article = articleService.find(id);
			article.setHits(article.getHits()+1);
			articleService.update(article);
			model.addAttribute("article",articleService.find(id));
			String name = article.getArticleCategory().getName().trim();
			if(name.equals("购物指南")||name.equals("服务政策")||name.equals("物流服务")||name.equals("支付方式")||name.equals("售后服务")||name.equals("关于我们")){
				return "/shop/article/guide";
			}
		return "/shop/article/content" ;
	}

	/**
	 * 搜索
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(String keyword, Integer pageNumber, ModelMap model) {
		if (StringUtils.isEmpty(keyword)) {
			return ERROR_VIEW;
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("articleKeyword", keyword);
		model.addAttribute("page", searchService.search(keyword, pageable));
		return "shop/article/search";
	}

	/**
	 * 点击数
	 */
	@RequestMapping(value = "/hits/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Long hits(@PathVariable Long id) {
		return articleService.viewHits(id);
	}
    
	/**
	 * 文章详情
	 */
	@RequestMapping(value = "/queryArticle/{id}", method = RequestMethod.GET)
	public String queryArticle(@PathVariable Long id, ModelMap model) {
			model.addAttribute("article",articleService.find(id));
		return "/shop/article/guide" ;
	}
	
	
	/**
	 * 商城公告
	 */
	@RequestMapping(value = "/adverte/{id}/{articleId}", method = RequestMethod.GET)
	public String adverte(@PathVariable Long id,@PathVariable Long articleId, ModelMap model) {
		if(null!=id&&!id.equals("")&&id>-1){
		  model.addAttribute("articleCategory",articleCategoryService.find(id));
		  }
		if(null!=articleId&&!articleId.equals("")&&articleId>-1){
			  model.addAttribute("article",articleService.find(articleId));
			  }
		return "shop/adverte/list";
	}
	
	
	
	
	@RequestMapping(value = "/info/{id}/{articleId}", method = RequestMethod.GET)
	public String info(@PathVariable Long id,@PathVariable Long articleId, ModelMap model) {
			//System.out.println("content="+id);
			hits(articleId);
			Article article = articleService.find(articleId);
			article.setHits(article.getHits()+1);
			articleService.update(article);
			if(null!=id&&!id.equals("")&&id>-1){
				  model.addAttribute("articleCategory",articleCategoryService.find(id));
				  }
				if(null!=articleId&&!articleId.equals("")&&articleId>-1){
					  model.addAttribute("article",articleService.find(articleId));
					  }
		return "/shop/adverte/info" ;
	}
	
	/**
	 * 商品选择
	 */
	@RequestMapping(value = "/product_select", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> productSelect(String q) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<Product> products = productService.search(q, false, 20);
			for (Product product : products) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", product.getId());
				map.put("sn", product.getSn());
				map.put("fullName", product.getFullName());
				map.put("path", product.getPath());
				data.add(map);
			}
		}
		return data;
	}

}