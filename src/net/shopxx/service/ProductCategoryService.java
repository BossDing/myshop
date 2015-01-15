/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.entity.Store;

/**
 * Service - 商品分类
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface ProductCategoryService extends
		BaseService<ProductCategory, Long> {

	/**
	 * 查找顶级商品分类
	 * 
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRoots();

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRoots(Integer count);

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRootsByEntcode(Integer count, String entcode);

	/**
	 * 查找顶级商品分类(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级商品分类(缓存)
	 */
	List<ProductCategory> findRoots(Integer count, String cacheRegion);

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 上级商品分类
	 */
	List<ProductCategory> findParents(ProductCategory productCategory);

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<ProductCategory> findParents(ProductCategory productCategory,
			Integer count);

	/**
	 * 查找上级商品分类(缓存)
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 上级商品分类(缓存)
	 */
	List<ProductCategory> findParents(ProductCategory productCategory,
			Integer count, String cacheRegion);

	/**
	 * 查找商品分类树
	 * 
	 * @return 商品分类树
	 */
	List<ProductCategory> findTree();

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 下级商品分类
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory);

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory,
			Integer count);

	/**
	 * 查找下级商品分类(缓存)
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 下级商品分类(缓存)
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory,
			Integer count, String cacheRegion);

	/**
	 * @Description 根据父类id查询子类
	 * @param parentId
	 * @author Guoxianlong
	 * @create_date Sep 3, 2014 8:59:39 AM
	 */
	public List<ProductCategory> findChildrenByParent(Long parentId);

	/**
	 * 根据店铺id查找顶级商品分类(缓存)，如果没有店铺id，那么查找storeId is null的数据
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级商品分类(缓存)
	 */
	List<ProductCategory> findRoots(Integer count, Long storeId,
			String cacheRegion);

	/**
	 * 根据店铺id和企业号查找顶级商品分类(缓存)，如果没有店铺id，那么查找storeId is null的数据
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRootsByEntcode(Integer count, Long storeId,
			String entcode);

	/**
	 * 根据店铺id查找顶级商品分类，如果没有店铺id，那么查找storeId is null的数据
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级商品分类(缓存)
	 */
	List<ProductCategory> findRoots(Integer count, Long storeId);

	ProductCategory findProductCategoryByKeyword(String keyword);

	/**
	 * 查找商品分类树 （如果有店铺，查找分类属于店铺的及分类不属于任何店铺的--store==账号所属店铺 or store is
	 * null；如果没有店铺属性，查找store is null的数据---查找条件有企业号、店铺id）
	 * 
	 * @return 商品分类树
	 */
	List<ProductCategory> findTreeForStore();
	
	/**
	 * 查找商品分类树 前台调用
	 * @author guoxl
	 * @param productCategory
	 * @param count
	 * @param store
	 * @return 商品分类树
	 */
	public List<ProductCategory> findChildrenForStoreForFront (ProductCategory productCategory, Integer count, Store store);
	
	/**
	 * 根据店铺查找分类
	 * @param store
	 * 		店铺
	 * @return 
	 * 		商分类集合
	 * @author wmd
	 * @date 2014/11/25
	 */
	public List<ProductCategory> findListForDeleteStore(Store store);
	
}