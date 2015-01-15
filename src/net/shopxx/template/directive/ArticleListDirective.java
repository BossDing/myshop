/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.Filter;
import net.shopxx.Order;
import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;
import net.shopxx.entity.Tag;
import net.shopxx.service.ArticleCategoryService;
import net.shopxx.service.ArticleService;
import net.shopxx.service.TagService;
import net.shopxx.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 文章列表
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Component("articleListDirective")
public class ArticleListDirective extends BaseDirective {

	/** "文章分类ID"参数名称 */
	private static final String ARTICLE_CATEGORY_ID_PARAMETER_NAME = "articleCategoryId";

	/** "标签ID"参数名称 */
	private static final String TAG_IDS_PARAMETER_NAME = "tagIds";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "articles";
	
	/** "是否根据排序字段排序"参数名称 */
	private static final String PX_PARAMETER_NAME = "px";

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long articleCategoryId = FreemarkerUtils.getParameter(ARTICLE_CATEGORY_ID_PARAMETER_NAME, Long.class, params);
		Long[] tagIds = FreemarkerUtils.getParameter(TAG_IDS_PARAMETER_NAME, Long[].class, params);
		String name = getName(params);
		Boolean px = FreemarkerUtils.getParameter(PX_PARAMETER_NAME, Boolean.class, params);
		List<Article> articles;
		if(name!=null && !name.equals("")){
			articles = new ArrayList<Article>();
			Article a = articleService.findByName(name);
			if(a!=null){
				articles.add(a);
			}
		}else{
			ArticleCategory articleCategory = articleCategoryService.find(articleCategoryId);
			List<Tag> tags = tagService.findList(tagIds);
	
			
			if ((articleCategoryId != null && articleCategory == null) || (tagIds != null && tags.isEmpty())) {
				articles = new ArrayList<Article>();
			} else {
				boolean useCache = useCache(env, params);
				String cacheRegion = getCacheRegion(env, params);
				Integer count = getCount(params);
				
				List<Filter> filters = getFilters(params, Article.class);
				List<Order> orders = getOrders(params);
				try {
					if(orders.size() <= 0){
						orders = new ArrayList<Order>();
						orders.add(Order.desc("hits"));
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				if (useCache) {
//					if((px!=null && px)){
//						articles = articleService.findList(articleCategory, tags, count, filters, orders, cacheRegion, px);
//					}else{
						articles = articleService.findList(articleCategory, tags, count, filters, orders, cacheRegion);
//					}
				} else {
//					if((px!=null && px)){
//						articles = articleService.findList(articleCategory, tags, count, filters, orders, px);
//					}else{
						articles = articleService.findList(articleCategory, tags, count, filters, orders);
//					}
				}
				
			}
		}
		setLocalVariable(VARIABLE_NAME, articles, env, body);
	}

}