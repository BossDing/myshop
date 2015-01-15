package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 店铺
 * 
 * @description: 
 * @author: Guoxianlong
 * @date: Oct 11, 2014  10:34:47 AM
 */
@Entity
@Table(name = "xx_store")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_store_sequence")
public class Store extends BaseEntity {

	private static final long serialVersionUID = -4422107659283633951L;
	
	public enum CheckStatus {
		wait, /**待审核*/
		failure, /**审核未通过*/
		success  /**审核通过*/
	}
	
	/** 名称 **/
	private String name;
	
	/** url **/
	private String url;
	
	/** 是否启用 */
	private Boolean isEnabled;
	
	/** 是否为主店铺 **/
	private Boolean isMainStore;
	
	/** 审核状态*/
	private CheckStatus checkStatus;
	
	/** 店面预览（位于店铺列表页面，展示店铺面貌）*/
	private String storeImage;
	
	/** 店面预览2（位于店铺列表页面，展示店铺面貌）*/
	private String storeImage2;
	
	/** 店面预览3（位于店铺列表页面，展示店铺面貌）*/
	private String storeImage3;
	
	/** 服务电话*/
	private String serviceTelephone;
	
	/** 店铺地址*/
	private String address;
	
	/** 营业时间*/
	private String openTime;

	/** 地区名称 */
	private String areaName;
	
	/** 地址坐标x轴（维度）*/
	private BigDecimal mapx;
	
	/** 地址坐标y轴（经度）*/
	private BigDecimal mapy;
	
	/** 公交线路*/
	private String busline; 
		
	/** 地区 */
	private Area area;
	
	/** 邮编*/
	private String zipCode;
	
	/** 店铺简介*/
	private String introduction;
	
	/*******************
	 *     申请人信息	   *
	 ******************/
	
	/** 申请人*/
	private String applyMan;
	
	/** 申请开店的邮箱*/
	private String email;
	
	/** 申请人QQ*/
	private String qq;
	
	/** 申请人联系电话*/
	private String contactTelephone;
	
	/** 店铺首页链接*/
	private String indexUrl;
	
	/** 公司名*/
	private String companyName;
	
	/**
	 * 移动端店铺首页链接
	 * @author wmd
	 * @date 2014/11/28
	 */
	private String indexMobileUrl;
	
	/**
	 * 正品保障（图片）
	 * @author wmd
	 * @date 2014/11/24
	 */
	private String isAGImage;
	
	/**
	 * 假一赔三（图片）
	 * @author wmd
	 * @date 2014/11/24
	 */
	private String isCSImage;
	
	/**
	 * 商城指派的商品
	 */
	private Set<Product> assignProducts = new HashSet<Product>();
	
	

	@Column(nullable = false)
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@NotNull
	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsMainStore() {
		return isMainStore;
	}

	public void setIsMainStore(Boolean isMainStore) {
		this.isMainStore = isMainStore;
	}
	
	public CheckStatus getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(CheckStatus checkStatus) {
		this.checkStatus = checkStatus;
	}

	@Email
	@NotEmpty
	@Length(max = 200)
	@NotNull
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStoreImage() {
		return storeImage;
	}

	public void setStoreImage(String storeImage) {
		this.storeImage = storeImage;
	}
	
	/**
	 * 获取店铺服务电话
	 * @return
	 */
	@NotEmpty
	@Length(max = 20)
	@NotNull
	public String getServiceTelephone() {
		return serviceTelephone;
	}

	/**
	 * 设置店铺服务电话
	 * @param serviceTelephone
	 */
	public void setServiceTelephone(String serviceTelephone) {
		this.serviceTelephone = serviceTelephone;
	}
	
	@Pattern(regexp = "^(([01]?\\d{1}|2[0-3]):[0-5]?\\d{1})-(([01]?\\d{1}|2[0-3]):[0-5]?\\d{1})$")
	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
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
	@Field(store = org.hibernate.search.annotations.Store.YES, index = Index.NO)
	@Min(0)
	//整数部分：12  fraction：分数部分
	@Digits(integer = 12, fraction = 6)
	//precision： 精度  scale: 规模
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
	@Field(store = org.hibernate.search.annotations.Store.YES, index = Index.NO)
	@Min(0)
	@Digits(integer = 12, fraction = 6)
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
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@NotNull
	@NotEmpty
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
	 * 获取申请人姓名
	 * @return
	 */
	@NotNull
	@NotEmpty
	public String getApplyMan() {
		return applyMan;
	}

	/**
	 * 设置申请人姓名
	 * @param applyMan
	 */
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStoreImage2() {
		return storeImage2;
	}

	public void setStoreImage2(String storeImage2) {
		this.storeImage2 = storeImage2;
	}

	public String getStoreImage3() {
		return storeImage3;
	}

	public void setStoreImage3(String storeImage3) {
		this.storeImage3 = storeImage3;
	}

	/**
	 * 获取申请人QQ
	 * @return
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * 设置申请人QQ
	 * @param qq
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * 获取申请人的联系电话
	 * @return
	 */
	@NotNull
	public String getContactTelephone() {
		return contactTelephone;
	}

	/**
	 * 设置申请人的联系电话
	 * @param contactTelephone
	 */
	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	/**
	 * 获取店铺首页URL
	 * @return
	 */
	public String getIndexUrl() {
		return indexUrl;
	}

	/**
	 * 设置店铺首页URL
	 * @param indexUrl
	 */
	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	/**
	 * 获取公司名
	 * @return
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 设置公司名
	 * @param company
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column
	public String getIsAGImage() {
		return isAGImage;
	}


	public void setIsAGImage(String isAGImage) {
		this.isAGImage = isAGImage;
	}

	@Column
	public String getIsCSImage() {
		return isCSImage;
	}


	public void setIsCSImage(String isCSImage) {
		this.isCSImage = isCSImage;
	}

	@Column
	public String getIndexMobileUrl() {
		return indexMobileUrl;
	}

	public void setIndexMobileUrl(String indexMobileUrl) {
		this.indexMobileUrl = indexMobileUrl;
	}

	/**
	 * 获取拥有的指派商品
	 * @return
	 */
	@ManyToMany(mappedBy = "assignedStores", fetch = FetchType.LAZY)
	public Set<Product> getAssignProducts() {
		return assignProducts;
	}

	/**
	 * 设置指派商品
	 * @param assignProducts
	 */
	public void setAssignProducts(Set<Product> assignProducts) {
		this.assignProducts = assignProducts;
	}
}
