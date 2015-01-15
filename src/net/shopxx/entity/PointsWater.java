/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 积分流水
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Entity
@Table(name = "xx_pointswater")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_pointswater_sequence")
public class PointsWater extends BaseEntity {

	/**
	 * @description
	 * @author jishaocong
	 * @since
	 */
	private static final long serialVersionUID = -329876374652904611L;
	/** 被赠送用户的id */
	private Long member_id;
	/** 积分状态，1.收入，2.支出，3.其他 */
	private Integer points_stat;
	/** 积分 */
	private Integer points;
	/** 赠送用户的id */
	private Long b_member_id;
	/** 备注 */
	private String note;
	/** 积分规则名称 */
	private String rulename;
	/** 通过邀请链接注册的ip地址*/
	private String ip;
	/** 关联订单*/
	private Order order;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(nullable = false)
	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	@Column(nullable = false)
	public Long getMember_id() {
		return member_id;
	}

	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}

	@Column(nullable = false)
	public Integer getPoints_stat() {
		return points_stat;
	}

	public void setPoints_stat(Integer points_stat) {
		this.points_stat = points_stat;
	}

	@Column(nullable = false)
	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Long getB_member_id() {
		return b_member_id;
	}

	public void setB_member_id(Long b_member_id) {
		this.b_member_id = b_member_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	/** 
	 * @return order 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="orders")
	public Order getOrder() {
		return order;
	}

	/** 
	 * @param order 要设置的 order 
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

}