package net.shopxx.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * 预约
 * @author hfh
  * @version 1.0
 */
@Entity
@Table(name = "xx_pre_sell_apply")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_pre_sell_apply_sequence")
public class PreSellApply extends BaseEntity{
	
	private static final long serialVersionUID = 4484681057087993118L;
	
	
	/** 预售申请单编号 */
	private String preSellApplyNo;
	
	/** 企业号 */
	private String entCode;
	
	/** 会员 */
	private Member member;
	
	/** 预售策略 */
	private PreSellRole preSellRole;
	
	/** 商品 */
	private Product product;
	
	/** 预约日期	 */
	private Date applyDate;
	
	/** 申请人姓名	 */
	private String applierName;
	
	/** 申请人email */
	private String applierEmail;
	
	/** 申请人电话 */
	private String applierMobile;
	
	/** 备注信息	 */
	private String remarkInformation;
	

	/** 地区 */
	private Area area;
	

	/** 详细地址	 */
	private String address;
	
	/** 创建人 */
	private String createdBy;
	
	/** 修改人 */
	private String lastUpdateBy;

	   
	/**
	 * 备注信息
	 * @return
	 */
	@NotEmpty
	@Length(max = 180)
	@Column(name="remarkInformation",nullable = false)
	public String getRemarkInformation() {
		return remarkInformation;
	}

	public void setRemarkInformation(String remarkInformation) {
		this.remarkInformation = remarkInformation;
	}
	
	
	@NotEmpty
	@Column(nullable = false)
	public String getPreSellApplyNo() {
		return preSellApplyNo;
	}

	public void setPreSellApplyNo(String preSellApplyNo) {
		this.preSellApplyNo = preSellApplyNo;
	}

	
	/**
	 * 预售策略id
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="preSellRole_id", nullable = false, updatable = false)
	public PreSellRole getPreSellRole() {
		return preSellRole;
	}

	public void setPreSellRole(PreSellRole preSellRole) {
		this.preSellRole = preSellRole;
	}

	
	
	public String getEntCode() {
		return entCode;
	}

	public void setEntCode(String entCode) {
		this.entCode = entCode;
	}

	
	/**
	 * 会员id
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id", nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	
	/**
	 * 商品id
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id",nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	   
	
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	
	
	public String getApplierName() {
		return applierName;
	}

	public void setApplierName(String applierName) {
		this.applierName = applierName;
	}

	/**
	 * 地区
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="area_id",nullable = false, updatable = false)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 详细地址
	 * @return
	 */
	@Length(max = 200)
	@Column(nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getApplierEmail() {
		return applierEmail;
	}

	public void setApplierEmail(String applierEmail) {
		this.applierEmail = applierEmail;
	}

	
	
	public String getApplierMobile() {
		return applierMobile;
	}

	public void setApplierMobile(String applierMobile) {
		this.applierMobile = applierMobile;
	}
	
	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

}
