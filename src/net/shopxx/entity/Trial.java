package net.shopxx.entity;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashSet;

import java.util.Set;


import javax.persistence.Column;

import javax.persistence.CascadeType;
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
 * Entity - 试用中心
 * 
 * @author DongXinXing   
 * @version 1.0
 */
@Entity
@Table(name = "xx_trial_role")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_trial_role_sequence")
public class Trial  extends OrderEntity {

	private static final long serialVersionUID = 3536993535267962270L;
	
	/** 名称 */
	private String name;
	
	/** 标题 */
	private String title;   
	
	/** 起始日期 */   
	private Date beginDate;  

	/** 结束日期 */
	private Date endDate;
	
	/** 允许参加会员等级 */
	private Set<MemberRank> memberRanks = new HashSet<MemberRank>();
	
	/** 允许参与商品 */
	private Set<Product> products = new HashSet<Product>();
	
	/** 试用申请 */
	private Set<TrialApply> trialApplys = new HashSet<TrialApply>();
	
	/** 试用报告 */
	private Set<TrialReport> trialReports = new HashSet<TrialReport>();
	
	/** 展示图片 */
	private String image;
	
	/** 介绍 */
	private String introduction;
	
	/** 市场价*/
	private BigDecimal marketprice;
	
	/** 试用数量*/
	private Long qtylimit;
	  
	/** 申请人数*/   
	private Long appliernum;

		  
	/**
	 * 获取名称
	 * 
	 * @return 名称   
	 */
	@JsonProperty   
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
	@JsonProperty
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
	 * 获取起始日期
	 *    
	 * @return 起始日期
	 */
	@JsonProperty
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
	@JsonProperty
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
	@JoinTable(name = "xx_trial_role_member_rank")
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
	@JoinTable(name = "xx_trial_role_product")
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
	 * 获取试用申请
	 * @return 试用申请
	 */
	@OneToMany(mappedBy = "trial", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<TrialApply> getTrialApplys() {
		return trialApplys;
	}

	/**
	 * 设置试用申请
	 * @param trialApplys 试用申请
	 */
	public void setTrialApplys(Set<TrialApply> trialApplys) {
		this.trialApplys = trialApplys;
	}
	
	/**
	 *  获取试用报告
	 * @return 试用报告
	 */
	@OneToMany(mappedBy = "trial", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	public Set<TrialReport> getTrialReports() {
		return trialReports;
	}
            
	/**
	 * 设置试用报告
	 * @param trialReports 试用报告
	 */
	public void setTrialReports(Set<TrialReport> trialReports) {
		this.trialReports = trialReports;
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
	 * 获取市场价
	 * 
	 * @return 市场价
	 */
	@JsonProperty
	@Field(store = Store.YES, index = Index.NO)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getMarketprice() {
		return marketprice;
	}
	/**
	 * 设置市场价
	 * 
	 * @param marketPrice
	 *            市场价
	 */
	public void setMarketprice(BigDecimal marketprice) {
		this.marketprice = marketprice;
	}
	/**
	 * 获取试用数量
	 * 
	 * @return 试用数量  
	 */
	@JsonProperty  
	@Field(store = Store.YES, index = Index.NO)
	@Column(nullable = false)
	public Long getQtylimit() {
		return qtylimit;
	}
	/**
	 * 设置试用数量
	 * 
	 * @param name
	 *            试用数量
	 */
	public void setQtylimit(Long qtylimit) {
		this.qtylimit = qtylimit;
	}
	
	/**
	 * 获取申请人数
	 * 
	 * @return 申请人数   
	 */
	@JsonProperty  
	@Field(store = Store.YES, index = Index.NO)
	@Column(nullable = false)
	public Long getAppliernum() {
		return appliernum;
	}
	/**
	 * 设置申请人数
	 * 
	 * @param name
	 *            申请人数
	 */
	public void setAppliernum(Long appliernum) {
		this.appliernum = appliernum;
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
	 * 获取介绍
	 * 
	 * @return 介绍  
	 */
	@Lob         
	public String getIntroduction() {
		return introduction;
	}   

	/**
	 * 设置介绍
	 * 
	 * @param introduction
	 *            介绍
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	

	
}
