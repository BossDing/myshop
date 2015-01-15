package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 预售中心
 * 
 * @author hfh
 * @version 1.0
 */
@Entity
@Table(name = "xx_pre_sell_role")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_pre_sell_role_sequence")
public class PreSellRole extends BaseEntity {

	private static final long serialVersionUID = 3536993535267962270L;

	/** 名称 */
	private String name;

	/** 标题 */
	private String title;

	/** 预约日期 */
	private Date reserveDate;

	/** 起始日期 */
	private Date beginDate;

	/** 结束日期 */
	private Date endDate;

	 /**
	 * 预售类型
	 */
//	 public enum preSellType {
//	
//	 /** 预约获得购买资格 */
//	 reserveBuyLicense,
//	
//	 /** 预约获得优惠资格 */
//	 reserveDiscountLicense
//	 }
	private String preSellType;


	/** 允许参加会员等级 */
	 private Set<MemberRank> memberRanks = new HashSet<MemberRank>();
		
	 /** 允许参与商品 */
	 private Set<Product> products = new HashSet<Product>();
	
	 /** 展示图片 */
	 private String image;
	 
	 /** 预售登记 */
	 private Set<PreSellApply> preSellApplys = new HashSet<PreSellApply>();

	/** 预售价 */
	private BigDecimal preSellPrice;

	/** 预售数量 */
	private Long qtyPreSell;

	/** 限购数量 */
	private Long qtyLimit;
	
	/**已申请人数*/
	private Long qtyApplied;
	
	/**商品ID*/
	private Long item_Id;
	
	/**预售的商品*/
//	private Product product;
	
//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}

	public Long getItem_Id() {
		return item_Id;
	}

	public void setItem_Id(Long item_Id) {
		this.item_Id = item_Id;
	}

	public Long getQtyApplied() {
		return qtyApplied;
	}

	public void setQtyApplied(Long qtyApplied) {
		this.qtyApplied = qtyApplied;
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
	 * 获取标题
	 * 
	 * @return 标题
	 */
	
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 获取预售日期
	 * 
	 * @param reserveDate
	 *            预售日期
	 */
	@Column
	public Date getReserveDate() {
		return reserveDate;
	}
	
	/**
	 * 设置预售日期
	 * 
	 * @param reserveDate
	 *            预售日期
	 */
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}

	
	/** 
	 * 获取起始日期
	 *    
	 * @return 起始日期
	 */
	
	@Column
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置起始日期
	 * 
	 * @param beginDate
	 *            起始日期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取结束日期
	 * 
	 * @return 结束日期
	 */
	
	@Column
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束日期
	 * 
	 * @param endDate
	 *            结束日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 获取允许参加会员等级
	 * 
	 * @return 允许参加会员等级
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_pre_sell_role_member_rank")
	public Set<MemberRank> getMemberRanks() {
		return memberRanks;
	}

	/**
	 * 设置允许参加会员等级
	 * 
	 * @param memberRanks
	 *            允许参加会员等级
	 */
	public void setMemberRanks(Set<MemberRank> memberRanks) {
		this.memberRanks = memberRanks;
	}
	
	/**
	 * 获取允许参与商品
	 * 
	 * @return 允许参与商品
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_pre_sell_role_product")
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 设置允许参与商品
	 * 
	 * @param products
	 *            允许参与商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
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
	 * 获取预售登记
	 * @return 预售登记
	 */
	@OneToMany(mappedBy = "preSellRole", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<PreSellApply> getPreSellApplys() {
		return preSellApplys;
	}

	/**
	 * 设置预售登记
	 * @param preSellApplys 预售登记
	 */
	public void setPreSellApplys(Set<PreSellApply> preSellApplys) {
		this.preSellApplys = preSellApplys;
	}

	/**
	 * 获取预售价
	 * 
	 * @return 预售价
	 */
	
	@Field(store = Store.YES, index = Index.NO)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getPreSellPrice() {
		return preSellPrice;
	}

	/**
	 * 设置预售价
	 * 
	 * @param preSellPrice
	 *            预售价
	 */
	public void setPreSellPrice(BigDecimal preSellPrice) {
		this.preSellPrice = preSellPrice;
	}

	/**
	 * 获取预售数量
	 * 
	 * @return 预售数量  
	 */
	  
	@Field(store = Store.YES, index = Index.NO)
	@Column(nullable = false)
	public Long getQtyPreSell() {
		return qtyPreSell;
	}

	/**
	 * 设置预售数量
	 * 
	 * @param qtyPreSell
	 *            预售数量
	 */
	public void setQtyPreSell(Long qtyPreSell) {
		this.qtyPreSell = qtyPreSell;
	}

	/**
	 * 获取限购数量
	 * 
	 * @return 限购数量  
	 */
	  
	@Field(store = Store.YES, index = Index.NO)
	@Column(nullable = false)
	public Long getQtyLimit() {
		return qtyLimit;
	}

	/**
	 * 设置限购数量
	 * 
	 * @param qtyPreSell
	 *            限购数量
	 */
	public void setQtyLimit(Long qtyLimit) {
		this.qtyLimit = qtyLimit;
	}
	
	 public String getPreSellType() {
			return preSellType;
	}

	public void setPreSellType(String preSellType) {
		this.preSellType = preSellType;
	}

}
