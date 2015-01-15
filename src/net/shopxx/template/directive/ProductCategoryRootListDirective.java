/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.entity.ProductCategory;
import net.shopxx.service.ProductCategoryService;
import net.shopxx.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 顶级商品分类列表
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Component("productCategoryRootListDirective")
public class ProductCategoryRootListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "productCategories";

	/** 是否启用店铺 参数名称 */
	private static final String USE_STORE_PARAMETER_NAME = "useStore";

	/** 店铺ID参数名称 */
	private static final String STORE_ID_PARAMETER_NAME = "storeId";

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		List<ProductCategory> productCategories;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		String entcode = getEntcode(params);
		Long storeId = FreemarkerUtils.getParameter(STORE_ID_PARAMETER_NAME,
				Long.class, params);
		Boolean useStore = FreemarkerUtils.getParameter(
				USE_STORE_PARAMETER_NAME, Boolean.class, params);
		if (useCache) {
			if (useStore != null && useStore) {
				productCategories = productCategoryService.findRoots(count,
						storeId, cacheRegion);
			} else {
				productCategories = productCategoryService.findRoots(count,
						cacheRegion);
			}
		} else {
			if (entcode != null) {
				if (useStore != null && useStore) {
					productCategories = productCategoryService
							.findRootsByEntcode(count, storeId, entcode);
				} else {
					productCategories = productCategoryService
							.findRootsByEntcode(count, entcode);
				}
			} else {
				if (useStore != null && useStore) {
					productCategories = productCategoryService.findRoots(count,
							storeId);
				} else {
					productCategories = productCategoryService.findRoots(count);
				}

			}

		}
		setLocalVariable(VARIABLE_NAME, productCategories, env, body);
	}

}