/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * Entity - 会员邀请项
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Entity
@Table(name = "xx_rulepoints")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_rulepoints_sequence")
public class MemberPoints extends BaseEntity {

	/**
	 * @description 
	 * @author jishaocong
	 * @since Jul 8, 2014 5:37:53 PM
	 */
	private static final long serialVersionUID = -329876374652904611L;
	/** 规则名称 */
	private String rulename;
	/** 有效天数 */
	private Integer ruledays;
	/** 积分点数*/
	private Integer points;
	/** 是否启用*/
	private boolean isstart;
	/** 备注 */
	private String note;

	@Column(nullable = false)
	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	@Column(nullable = false)
	public Integer getRuledays() {
		return ruledays;
	}

	public void setRuledays(Integer ruledays) {
		this.ruledays = ruledays;
	}

	@Column(nullable = false)
	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	@Column(nullable = false)
	public boolean getIsstart() {
		return isstart;
	}

	public void setIsstart(boolean isstart) {
		this.isstart = isstart;
	}

	@Column(length = 255)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}