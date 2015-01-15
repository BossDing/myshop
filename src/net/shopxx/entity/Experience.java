package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;   
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
  
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Entity - 试用中心
 * 
 * @author DongXinXing   
 * @version 1.0
 */
@Entity
@Table(name = "xx_experience")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_experience_sequence")
public class Experience  extends OrderEntity {        
    
	private static final long serialVersionUID = 3536993675267962670L;
	
	/** 门店名称 */
	private String name;
	
	/** 营业时间*/
	private String opentime;
	   
	/** 企业号*/
	private String entcode;
	
	/** 联系电话*/
	private String phone;
	
	/** 详细地址*/
	private String address;
	
	/** 地区名称 */
	private String areaName;
	
	/** 地址坐标x轴*/
	private BigDecimal mapx;
	
	/** 地址坐标y轴*/
	private BigDecimal mapy;
	
	/** 公交线路*/
	private String busline; 
	
	/** 体验店简介*/
	private String introduction;
		
	/** 体验店图片*/
	private String image;
	
	/** 活动图片一*/
	private String promotionimagefrist;
	
	/** 活动链接一*/
	private String promotionlinkfrist;
	
	/** 活动图片二*/
	private String promotionimagesecond;
	
	/** 活动链接二*/
	private String promotionlinksecond;
	
		
	/** 地区 */
	private Area area;
	
	/** 体验店商品 */
	private Set<Product> products = new HashSet<Product>();
	
	/** 门店图片 */
	private List<ProductImage> productImages = new ArrayList<ProductImage>();
	
		
	/**
	 * 获取门店名称
	 * 
	 * @return 门店名称      
	 */
	@JsonProperty   
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置门店名称
	 * 
	 * @param name
	 *            门店名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取营业时间
	 * 
	 * @return 营业时间  
	 */
	@JsonProperty   
	@Length(max = 200)
	public String getOpentime() {
		return opentime;
	}

	/**
	 * 设置营业时间
	 * 
	 * @param opentime
	 *            营业时间
	 */
	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}
	
	/**
	 * 获取企业号
	 * 
	 * @return 企业号   
	 */
	@JsonProperty   
	@NotEmpty
	@Length(max = 200)
	public String getEntcode() {
		return entcode;
	}

	/**
	 * 设置企业号
	 * 
	 * @param entcode
	 *            企业号
	 */
	public void setEntcode(String entcode) {
		this.entcode = entcode;
	}
	
	/**
	 * 获取联系电话
	 * 
	 * @return 联系电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置联系电话
	 * 
	 * @param areaName
	 *            联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * 获取详细地址
	 * 
	 * @return 详细地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置详细地址
	 * 
	 * @param areaName
	 *            详细地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	/**
	 * 获取地区名称
	 * 
	 * @return 地区名称
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 设置地区名称
	 * 
	 * @param areaName
	 *            地区名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * 获取坐标x轴
	 * 
	 * @return 坐标x轴
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.NO)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6)
	public BigDecimal getMapx() {
		return mapx;
	}
	/**
	 * 设置坐标x轴
	 * 
	 * @param mapx
	 *            坐标x轴
	 */
	public void setMapx(BigDecimal mapx) {
		this.mapx = mapx;
	}
	
	/**
	 * 获取坐标y轴
	 * 
	 * @return 坐标y轴
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.NO)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6)
	public BigDecimal getMapy() {
		return mapy;
	}
	/**
	 * 设置坐标x轴
	 * 
	 * @param mapx
	 *            坐标x轴
	 */
	public void setMapy(BigDecimal mapy) {
		this.mapy = mapy;
	}
	
	/**
	 * 获取公交线路
	 * 
	 * @return 公交线路
	 */
	public String getBusline() {
		return busline;
	}

	/**
	 * 设置公交线路
	 * 
	 * @param busline
	 *            公交线路
	 */
	public void setBusline(String busline) {
		this.busline = busline;
	}
	
	/**
	 * 获取体验店简介
	 * 
	 * @return 体验店简介
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 设置体验店简介
	 * 
	 * @param busline
	 *            体验店简介
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	/**
	 * 获取体验店图片
	 * 
	 * @return 体验店图片
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.NO)
	@Length(max = 200)
	public String getImage() {
		return image;
	}

	/**
	 * 设置体验店图片
	 * 
	 * @param image  
	 *           体验店图片 
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * 获取活动图一
	 * 
	 * @return 活动图一
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.NO)
	@Length(max = 200)
	public String getPromotionimagefrist() {
		return promotionimagefrist;
	}

	/**
	 * 设置活动图一
	 * 
	 * @param image  
	 *        活动图一 
	 */
	public void setPromotionimagefrist(String promotionimagefrist) {
		this.promotionimagefrist = promotionimagefrist;
	}
	
	/**   
	 * 获取活动图片二
	 * 
	 * @return 活动图片二
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.NO)
	@Length(max = 200)
	public String getPromotionimagesecond() {
		return promotionimagesecond;
	}

	/**
	 * 设置活动图片二
	 * 
	 * @param image  
	 *           活动图片二
	 */
	public void setPromotionimagesecond(String promotionimagesecond) {
		this.promotionimagesecond = promotionimagesecond;
	}
	/**   
	 * 获取活动链接二
	 *    
	 * @return 活动链接二
	 */
	public String getPromotionlinkfrist() {
		return promotionlinkfrist;    
	}
	/**
	 * 设置活动链接二
	 * 
	 * @param image  
	 *           活动链接二   
	 */
	public void setPromotionlinkfrist(String promotionlinkfrist) {
		this.promotionlinkfrist = promotionlinkfrist;
	}
	/**   
	 * 获取活动链接二
	 * 
	 * @return 活动链接二
	 */
	public String getPromotionlinksecond() {
		return promotionlinksecond;
	}
	/**
	 * 设置活动链接二
	 * 
	 * @param image  
	 *           活动链接二
	 */
	public void setPromotionlinksecond(String promotionlinksecond) {
		this.promotionlinksecond = promotionlinksecond;
	}
	
	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@NotNull
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
	 * 获取体验店商品
	 * 
	 * @return 体验店商品
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_experience_product")
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 设置体验店商品
	 * 
	 * @param products
	 *            体验店商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
	/**
	 * 获取体验商家图片
	 * 
	 * @return 体验商家图片
	 */
	@Valid
	@ElementCollection
	@CollectionTable(name = "xx_experience_experience_image")
	public List<ProductImage> getProductImages() {
		return productImages;
	}

	/**
	 * 设置体验商家图片
	 * 
	 * @param productImages               
	 *            体验商家图片
	 */
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
}
