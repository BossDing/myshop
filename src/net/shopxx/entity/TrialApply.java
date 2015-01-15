package net.shopxx.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;



/**
 * 试用申请
 * @author lizy_java
  * @version 1.0
 */
@Entity
@Table(name = "xx_trial_application")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_trial_application_sequence")
public class TrialApply extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2213752194632597752L;

	
	/** 试用申请单编号 */
	private String trialApplyNo;
	
	/** 企业号 */
	private String entCode;
	
	/** 会员 */
	private Member member;
	
	/** 试用 */
	private Trial trial;
	
	/** 商品 */
	private Product product;
	
	/** 申请日期	 */
	private Date applyDate;
	
	/** 申请宣言	 */
	private String applyReason;
	
	/** 收货人 */
	private String receiver;
	
	/** 地区 */
	private Area area;
	
	/** 详细地址	 */
	private String address;
	
	/** 联系电话 */
	private String telephone;
	
	/**区号*/
	private String areaCode;
	
	/**电话号码*/
	private String phoneNum;
	
	/**分机*/
	private String extension;
	
	/** 申请状态*/
	private String applyStatus;

	/** 是否提交试用报告 */
	private String isReport;
	
	/** 创建人 */
	private String createdBy;
	
	/** 修改人 */
	private String modifyBy;
	
	/**邮编*/
	private String zipCode;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * 申请编码 
	 * @return
	 */
	@NotEmpty
	@Column(name="trial_apply_no", nullable = false)
	public String getTrialApplyNo() {
		return trialApplyNo;
	}

	public void setTrialApplyNo(String trialApplyNo) {
		this.trialApplyNo = trialApplyNo;
	}
	
	/**
	 * 企业号
	 * @return
	 */
	@Column(name="ent_code")
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
	 * 试用策略id
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="trial_id", nullable = false, updatable = false)
	public Trial getTrial() {
		return trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}

	/**
	 * 商品id
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id", nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 申请时间
	 * @return
	 */
	@Column(name="apply_date")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * 申请宣言
	 * @return
	 */
	@NotEmpty
	@Length(max = 180)
	@Column(name="applr_reason",nullable = false)
	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	/**
	 * 收货人
	 * @return
	 */
	@NotEmpty
	@Length(max = 20)
	@Column(nullable = false)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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

	/**
	 * 申请状态
	 * @return
	 */
	@JoinColumn(name="apply_status")
	public String getApplyStatus() {
		return applyStatus;
	}

	
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	/**
	 * 是否提交试用报告 
	 * @return
	 */
	@JoinColumn(name="is_report")
	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}
	
	/**
	 * 创建人
	 * @return
	 */
	public String getCreatedBy(){
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}
	/**
	 * 更新人
	 * @return
	 */
	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	/**
	 * 联系电话
	 * @return
	 */
	@Column(nullable = false)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
