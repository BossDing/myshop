package net.shopxx.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "xx_sign_in_point_rule")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_sign_in_point_rule_sequence")
public class SignInPointRule extends BaseEntity{
	private static final long serialVersionUID = -329876374652904611L;
	/** 连续签到次数*/
	private Integer serialSignInTimes;
	/** 奖励积分*/
	private Integer point;
	/**
	 * 获得连续签到次数
	 * @return
	 */
	public Integer getSerialSignInTimes() {
		return serialSignInTimes;
	}
	
	/**
	 * 设置连续签到次数
	 * @return
	 */
	public void setSerialSignInTimes(Integer serialSignInTimes) {
		this.serialSignInTimes = serialSignInTimes;
	}

	/**
	 * 获得奖励积分
	 * @return
	 */
	public Integer getPoint() {
		return point;
	}
	/**
	 * 设置奖励积分
	 * @return
	 */
	public void setPoint(Integer point) {
		this.point = point;
	}
	
}
