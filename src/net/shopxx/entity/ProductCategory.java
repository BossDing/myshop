/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 商品分类
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Entity
@Table(name = "xx_product_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_product_category_sequence")
public class ProductCategory extends OrderEntity {

	private static final long serialVersionUID = 5095521437302782717L;

	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/product/list";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";

	/** 名称 */
	private String name;

	/** 页面标题 */
	private String seoTitle;

	/** 页面关键词 */ 
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;

	/** 树路径 */
	private String treePath;

	/** 层级 */
	private Integer grade;

	/** 上级分类 */
	private ProductCategory parent;

	/** 下级分类 */
	private Set<ProductCategory> children = new HashSet<ProductCategory>();

	/** 商品 */
	private Set<Product> products = new HashSet<Product>();

	/** 筛选品牌 */
	private Set<Brand> brands = new HashSet<Brand>();

	/** 参数组 */
	private Set<ParameterGroup> parameterGroups = new HashSet<ParameterGroup>();

	/** 筛选属性 */
	private Set<Attribute> attributes = new HashSet<Attribute>();

	/** 促销 */
	private Set<Promotion> promotions = new HashSet<Promotion>();
	
	/** 服务网点 */
	private Set<Outlet> outlets = new HashSet<Outlet>();
	
	
	/** 是否直接显示产品 */
	private Boolean isShowProduct;
	
	/** 是否参与维修预约 */
	private Boolean isRepair;
	
	/** 是否参与安装预约 */
	private Boolean isInstall;
	
	/** 是否参与产品说明书 */
	private Boolean isInstruction;
	
	/** 分类图片 */
	private String image;
	
	/**企业号*/
	private String entcode;
	
	/** 店铺 **/
	private net.shopxx.entity.Store store;
	

	/**
	 * 获取是否直接显示产品
	 * 
	 * @return 是否直接显示产品
	 */
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	@NotNull
	@Column(nullable = false)
	public Boolean getIsShowProduct() {
		return isShowProduct;
	}

	/**
	 * 设置是否直接显示产品
	 * 
	 * @param isShowProduct
	 *            是否直接显示产品
	 */
	public void setIsShowProduct(Boolean isShowProduct) {
		this.isShowProduct = isShowProduct;
	}
	
	
	
//	/**
//	 * 获取是否参与维修预约
//	 * 
//	 * @return 是否参与维修预约
//	 */
//	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
//	@NotNull
//	@Column(nullable = false)
//	public Boolean getIsRepair() {
//		return isRepair;
//	}
//
//	/**
//	 * 设置是否参与维修预约
//	 * 
//	 * @param isRepair
//	 *            是否参与维修预约
//	 */
//	public void setIsRepair(Boolean isRepair) {
//		this.isRepair = isRepair;
//	}
//
//	
//	/**
//	 * 获取是否参与安装预约
//	 * 
//	 * @return 是否参与安装预约
//	 */
//	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
//	@NotNull
//	@Column(nullable = false)
//	public Boolean getIsInstall() {
//		return isInstall;
//	}
//
//	/**
//	 * 设置是否参与安装预约
//	 * 
//	 * @param isInstall
//	 *            是否参与安装预约
//	 */
//	public void setIsInstall(Boolean isInstall) {
//		this.isInstall = isInstall;
//	}

	

	public Boolean getIsRepair() {
		return isRepair;
	}

	public void setIsRepair(Boolean isRepair) {
		this.isRepair = isRepair;
	}

	public Boolean getIsInstall() {
		return isInstall;
	}

	public void setIsInstall(Boolean isInstall) {
		this.isInstall = isInstall;
	}

	
	public Boolean getIsInstruction() {
		return isInstruction;
	}

	public void setIsInstruction(Boolean isInstruction) {
		this.isInstruction = isInstruction;
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取页面标题
	 * 
	 * @return 页面标题
	 */
	@Length(max = 200)
	public String getSeoTitle() {
		return seoTitle;
	}

	/**
	 * 设置页面标题
	 * 
	 * @param seoTitle
	 *            页面标题
	 */
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	/**
	 * 获取页面关键词
	 * 
	 * @return 页面关键词
	 */
	@Length(max = 200)
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * 设置页面关键词
	 * 
	 * @param seoKeywords
	 *            页面关键词
	 */
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */
	@Length(max = 200)
	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param seoDescription
	 *            页面描述
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	@Column(nullable = false)
	public String getTreePath() {
		return treePath;
	}

	/**
	 * 设置树路径
	 * 
	 * @param treePath
	 *            树路径
	 */
	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	/**
	 * 获取层级
	 * 
	 * @return 层级
	 */
	@Column(nullable = false)
	public Integer getGrade() {
		return grade;
	}

	/**
	 * 设置层级
	 * 
	 * @param grade
	 *            层级
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	/**
	 * 获取展示图片
	 * 
	 * @return 展示图片
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.NO)
	@Length(max = 200)
	public String getImage() {
		return image;
	}

	/**
	 * 设置展示图片
	 * 
	 * @param image
	 *            展示图片
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取上级分类
	 * 
	 * @return 上级分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public ProductCategory getParent() {
		return parent;
	}

	/**
	 * 设置上级分类
	 * 
	 * @param parent
	 *            上级分类
	 */
	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}

	/**
	 * 获取下级分类
	 * 
	 * @return 下级分类
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("order asc")
	public Set<ProductCategory> getChildren() {
		return children;
	}

	/**
	 * 设置下级分类
	 * 
	 * @param children
	 *            下级分类
	 */
	public void setChildren(Set<ProductCategory> children) {
		this.children = children;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 设置商品
	 * 
	 * @param products
	 *            商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	/**
	 * 获取筛选品牌
	 * 
	 * @return 筛选品牌
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_product_category_brand")
	@OrderBy("order asc")
	public Set<Brand> getBrands() {
		return brands;
	}

	/**
	 * 设置筛选品牌
	 * 
	 * @param brands
	 *            筛选品牌
	 */
	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}
	
	/**
	 * 获取服务网点
	 * @return
	 */
	@ManyToMany(mappedBy = "productCategorys", fetch = FetchType.LAZY)
	public Set<Outlet> getOutlets() {
		return outlets;
	}

	public void setOutlets(Set<Outlet> outlets) {
		this.outlets = outlets;
	}
	/**
	 * 获取参数组
	 * 
	 * @return 参数组
	 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<ParameterGroup> getParameterGroups() {
		return parameterGroups;
	}

	/**
	 * 设置参数组
	 * 
	 * @param parameterGroups
	 *            参数组
	 */
	public void setParameterGroups(Set<ParameterGroup> parameterGroups) {
		this.parameterGroups = parameterGroups;
	}

	/**
	 * 获取筛选属性
	 * 
	 * @return 筛选属性
	 */
	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("order asc")
	public Set<Attribute> getAttributes() { 
		
		return attributes;
	}

	/**
	 * 设置筛选属性
	 * 
	 * @param attributes
	 *            筛选属性
	 */
	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * 获取促销
	 * 
	 * @return 促销
	 */
	@ManyToMany(mappedBy = "productCategories", fetch = FetchType.LAZY)
	public Set<Promotion> getPromotions() {
		return promotions;
	}

	/**
	 * 设置促销
	 * 
	 * @param promotions
	 *            促销
	 */
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	@Transient
	public List<Long> getTreePaths() {
		List<Long> treePaths = new ArrayList<Long>();
		String[] ids = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		if (ids != null) {
			for (String id : ids) {
				treePaths.add(Long.valueOf(id));
			}
		}
		return treePaths;
	}

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		if (getId() != null) {
			return PATH_PREFIX + "/" + getId() + PATH_SUFFIX;
		}
		return null;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Promotion> promotions = getPromotions();
		if (promotions != null) {
			for (Promotion promotion : promotions) {
				promotion.getProductCategories().remove(this);
			}
		}
	}
	 
	/**
	 *获取企业号
	 * @return
	 */
	public String getEntcode() {
		return entcode;
	}
	 /**
	  * 设置企业号
	  * @param entcode
	  */
	public void setEntcode(String entcode) {
		this.entcode = entcode;
	}
	
	/**
	 * 获取第二级分类名称
	 * 
	 * @return 第二级分类名称
	 */
	@Transient
	public String getParent2() {
		int i = 0;
		String pid = null; 
		ProductCategory pc = this;
		String[] ids = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		if (ids != null && ids.length>=2) {
			pid = ids[1];
			
			while(i==0){
				if(pc!=null && pc.parent!=null){
					if(pc.getId().toString().equals(pid)){
						break;
					}
					pc = pc.parent;
				}else{
					i++;
				}
			}
		}
		
		return pc.getName();
	}
	
	/**
	 * 获取上级的商品属性
	 * 
	 * @return 上级的商品属性
	 */
	@Transient
	public Set<Attribute> getGWAttributes() { 
		int i = 0;
		ProductCategory pc = this;
		while(i==0){
			if(pc.attributes==null || pc.attributes.size()<=0){
				if(pc.parent!=null){
					pc = pc.parent;
				}else{
					i++;
				}
			}else{
				i++;
			}
		}
		return pc.attributes;
	}

	/**
	 * 获取店铺
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stores")
	public net.shopxx.entity.Store getStore() {
		return store;
	}

	/**
	 * 设置店铺
	 * @param store
	 */
	public void setStore(net.shopxx.entity.Store store) {
		this.store = store;
	}

}