/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 售后预约
 * 
 * @author zlh
 * @version 3.0
 */
@Entity
@Table(name = "xx_after_booking")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_after_booking_sequence")
public class AfterBooking extends BaseEntity {

	private static final long serialVersionUID = -1312743765286909390L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 安装 */
		install,

		/** 维修 */
		repair
	}
    
	/**预约编号*/
	private String sn;
	
	/** 用户姓名 */
	private String consignee;
	
	/** 邮箱地址 */
	private String email;
	
	/** 手机号 */
	private String phone;
	
	/**区号*/
	private String areaCode;
	
	/**电话号码*/
	private String telephone;
	
	/**分机*/
	private String extension;
	
	/** 地区名称 */
	private String areaName;
	
	/** 地址 */
	private String address;
	
	
	/** 邮编 */
	private String zipCode;
	
	/** 类型 */
	private Type type;
    
	/** 配件需求 */
	private String parts;
	
    /** 企业号 **/
	private String entCode;
	
	/** 其他要求/故障描述 */
	private String content;

	/** 故障类型 0不运行 */
	private Integer faultType;
	
    /**上门安装或者维修时间*/
    private Date visitServiceDate;
    
    /**服务描述*/
    private String serviceDesc;
    
    public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	/**故障发生的日期*/
    private Date faultDate;
    
    /**购买时间*/
    private Date buyDate;

	/**0 未解决 1 已解决*/
    private Integer state;
    
    /**完成时间*/
    private Date finishDate;
    
	/**预约来源 0表示万家乐官方商城  1万家乐官网...**/
    private Integer source;
    
	private String attributeValue;
    
    private String attributeValue1;
    
    private String attributeValue2;
    
    private String attributeValue3;
    
    private String attributeValue4;
    
//	/**会员*/
	private Member member;
//	
	/** 产品 */
	private Set<Product> products = new HashSet<Product>();
	
	/** 产品类别 */
//	private Set<ProductCategory> productCategorys = new HashSet<ProductCategory>();
	private ProductCategory productCategory;
	
	/** 产品型号 */
	private Product product;
	/** 用户录入产品型号 */
	private String productName;
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	/**销售单位*/
	private String saleCompany;
	
	public String getSaleCompany() {
		return saleCompany;
	}

	public void setSaleCompany(String saleCompany) {
		this.saleCompany = saleCompany;
	}

	//	private List<Product> products = new ArrayList<Product>();
	/** 筛选品牌 */
//	private Set<Brand> brands = new HashSet<Brand>();
	//	/** 地区 */
	private Area area;
	
	public String getParts() {
		return parts;
	}

	public void setParts(String parts) {
		this.parts = parts;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@NotNull
	@Column(nullable = false)
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@Length(max = 1000)
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 获取内容
	 * 
	 * @return 用户姓名
	 */
	/**
	 * 获取收货人
	 * 
	 * @return 收货人
	 */
	@JsonProperty
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	/**
	 * 获取编号
	 * @return
	 */
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEntCode() {
		return entCode;
	}

	public void setEntCode(String entCode) {
		this.entCode = entCode;
	}

    public Date getFinishDate() {
			return finishDate;
	}

	public void setFinishDate(Date finishDate) {
			this.finishDate = finishDate;
	}
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getFaultType() {
		return faultType;
	}

	public void setFaultType(Integer faultType) {
		this.faultType = faultType;
	}

	public Date getVisitServiceDate() {
		return visitServiceDate;
	}

	public void setVisitServiceDate(Date visitServiceDate) {
		this.visitServiceDate = visitServiceDate;
	}

	public Date getFaultDate() {
		return faultDate;
	}

	public void setFaultDate(Date faultDate) {
		this.faultDate = faultDate;
	}
    
    public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeValue1() {
		return attributeValue1;
	}

	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	public String getAttributeValue2() {
		return attributeValue2;
	}

	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	public String getAttributeValue3() {
		return attributeValue3;
	}

	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	public String getAttributeValue4() {
		return attributeValue4;
	}

	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}
	
	/**获取会员*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_after_booking_product")
	public Set<Product> getProducts(){
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "xx_after_booking_category")
//	public Set<ProductCategory> getProductCategorys(){
//		return productCategorys;
//	}
//
//	public void setProductCategorys(Set<ProductCategory> productCategorys) {
//		this.productCategorys = productCategorys;
//	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id",nullable = false, updatable = false)
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
//	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}
	
	/**
	 * 获取筛选品牌
	 * 
	 * @return 筛选品牌
	 */
//	@ManyToMany(fetch = FetchType.LAZY)
//	@JoinTable(name = "xx_after_booking_brand")
//	@OrderBy("order asc")
//	public Set<Brand> getBrands() {
//		return brands;
//	}
//
//	/**
//	 * 设置筛选品牌
//	 * 
//	 * @param brands
//	 *            筛选品牌
//	 */
//	public void setBrands(Set<Brand> brands) {
//		this.brands = brands;
//	}
	
}