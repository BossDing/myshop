package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;   
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.crypto.Data;
  
import net.shopxx.entity.Order.OrderStatus;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import sun.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * Entity - 试用预约中心
 * 
 * @author  LiuFeng
 * @version 1.0
 */
@Entity
@Table(name = "xx_experience_apply")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_experience_apply_sequence")
public class ExperienceApply  extends OrderEntity {        
    
	private static final long serialVersionUID = 3536993675267962666L;
	
	/** 体验预约登记单号 */
	private String experienceApplyId;
	
	/** 企业号*/
	private String entcode; 
	
	/** 体验状态*/
	public enum ExperienceStatus {

		/** 待体验 */
		waiting,

		/** 已体验 */
		closed,
		
	}
	
	/** 订单状态 */
	private ExperienceStatus experienceStatus;
	
	/** 体验店 */
	private Experience experiences;
	
	/** 体验店号 */
	private String experienceId;
	
	/** 体验店名称 */
	private String name;
	
	/** 体验店地址 */
	private String address;
	
	/** 申请人姓名*/
	private String userName;
	
	/** 联系电话*/
	private String tel;
	
	/** 申请内容*/
	private String experienceType;
	
	/** 申请体验日 */
	private Date experienceDate;
	
	/** 其他要求*/
	private String note;

	/**
	 * 获取登记单号
	 * 
	 * @return 登记单号
	 */
	public String getExperienceApplyId() {
		return experienceApplyId;
	}

	/**
	 * 设置订单编号
	 * 
	 * @param experienceApplyId
	 *            登记单号
	 */
	public void setExperienceApplyId(String experienceApplyId) {
		this.experienceApplyId = experienceApplyId;
	}

	/**
	 * 获取企业号
	 * 
	 * @return 企业号
	 */
	public String getEntcode() {
		return entcode;
	}

	/**
	 * 设置企业号
	 * 
	 * @param experienceApplyId
	 *            企业号
	 */
	public void setEntcode(String entcode) {
		this.entcode = entcode;
	}

	/**
	 * 获取申请人姓名
	 * 
	 * @return 申请人姓名
	 */
	@JsonProperty   
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置申请人姓名
	 * 
	 * @param userName
	 *            申请人姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取联系电话
	 * 
	 * @return 联系电话
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 设置联系电话
	 * 
	 * @param tel
	 *            联系电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 获取申请内容
	 * 
	 * @return 申请内容
	 */
	public String getExperienceType() {
		return experienceType;
	}

	/**
	 * 设置申请内容
	 * 
	 * @param experienceType
	 *            申请内容
	 */
	public void setExperienceType(String experienceType) {
		this.experienceType = experienceType;
	}

	/**
	 * 获取申请日期
	 * 
	 * @return 申请日期
	 */
	public Date getExperienceDate() {
		return experienceDate;
	}

	/**
	 * 设置申请日期
	 * 
	 * @param experienceData
	 *            申请日期
	 */
	public void setExperienceDate(Date experienceDate) {
		this.experienceDate = experienceDate;
	}


	/**
	 * 获取其他要求
	 * 
	 * @return 其他要求
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置其他要求
	 * 
	 * @param note
	 *            其他要求
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取体验状态
	 * 
	 * @return 体验状态
	 */
	
	public ExperienceStatus getExperienceStatus() {
		return experienceStatus;
	}

	/**
	 * 设置体验状态
	 * 
	 * @param experienceStatus
	 *            体验状态
	 */
	public void setExperienceStatus(ExperienceStatus experienceStatus) {
		this.experienceStatus = experienceStatus;
	}
	
	/**
	 * 获取体验店
	 * @return 
	 * 
	 * @return 体验店
	 */
	@OneToOne(fetch = FetchType.LAZY)
	public  Experience getExperiences() {
		return experiences;
	}

	/**
	 * 设置体验店
	 * 
	 * @param experiences
	 *     体验店    体验店
	 */
	public void setExperiences(Experience experiences) {
		this.experiences = experiences;
	}

	/**
	 * 获取体验店号
	 * 
	 * @return 体验店号
	 */
	public String getExperienceId() {
		return experienceId;
	}

	/**
	 * 设置体验店号
	 * 
	 * @param experienceId
	 *     体验店号
	 */
	public void setExperienceId(String experienceId) {
		this.experienceId = experienceId;
	}

	/**
	 * 获取体验店名称
	 * 
	 * @return 体验店名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置体验店名称
	 * 
	 * @param name
	 *     体验店名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取体验店地址
	 * 
	 * @return 体验店地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置体验店地址
	 * 
	 * @param address
	 *     体验店地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}



	
}
