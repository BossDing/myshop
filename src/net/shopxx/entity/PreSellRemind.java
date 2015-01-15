package net.shopxx.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity - 预售提醒
 * 
 * @author hfh   
 * @version 1.0
 */
@Entity
@Table(name = "xx_pre_sell_remind" , uniqueConstraints = {@UniqueConstraint(columnNames={"preSellRoleId", "tel"})})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_pre_sell_remind_sequence")
public class PreSellRemind extends OrderEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 15765765456345353L;
	
	/**
	 * 预售提醒单号
	 */
	private String preSellRemindNo;
	
	/**
	 * 企业号
	 */
	private String entCode;
	
	/**
	 * 预售ID
	 */
	private long preSellRoleId;
	
	/**
	 * 电话号码
	 */
	private String tel;
	
	/**
	 * 已提醒 yes ,未提醒 no
	 */
	private String isRemaided;
	
	/**
	 * 提醒时间
	 */
	private Date remaidedDate;
	
	/**
	 * 创建人
	 */
	private String createdBy;
	
	/**
	 * 修改人
	 */
	private String lastUpdateBy;

	public String getPreSellRemindNo() {
		return preSellRemindNo;
	}

	public void setPreSellRemindNo(String preSellRemindNo) {
		this.preSellRemindNo = preSellRemindNo;
	}

	public String getEntCode() {
		return entCode;
	}

	public void setEntCode(String entCode) {
		this.entCode = entCode;
	}

	public long getPreSellRoleId() {
		return preSellRoleId;
	}

	public void setPreSellRoleId(long preSellRoleId) {
		this.preSellRoleId = preSellRoleId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getIsRemaided() {
		return isRemaided;
	}

	public void setIsRemaided(String isRemaided) {
		this.isRemaided = isRemaided;
	}

	public Date getRemaidedDate() {
		return remaidedDate;
	}

	public void setRemaidedDate(Date remaidedDate) {
		this.remaidedDate = remaidedDate;
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
