package net.shopxx.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;



@Entity
@Table(name = "xx_group_remind") 
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_group_remind_sequence")
public class GroupRemind  extends BaseEntity {

	private static final long serialVersionUID = -7849848867030199578L;
	
	 
	/**手机号码*/
	private String mobile; 
	

	/**邮箱*/
	private String Email;
	
	/**提醒时间*/
	private Date remindtime;
	
	/**联系人*/
	private String contectperson;
	
	/**
	 * 提醒状态1否2是
	 */
	private int isremind;
	
	/**团购*/
	private GroupPurchase groupPurchase;
	
	/**
	 *  获取团购
	 * @return
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public GroupPurchase getGroupPurchase() {
		return groupPurchase;
	}

	public void setGroupPurchase(GroupPurchase groupPurchase) {
		this.groupPurchase = groupPurchase;
	}

	

    /**
     * 获取email
     * @return
     */
	@Length(max=25)
	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
    /**
     * 获取提醒时间
     * @return
     */
	@Column(nullable=false)
	public Date getRemindtime() {
		return remindtime;
	}

	public void setRemindtime(Date remindtime) {
		this.remindtime = remindtime;
	}
	
    /**
     * 获取联系人
     * @return
     */
	@Length(max=25)
	@Column(nullable=false)
	public String getContectperson() {
		return contectperson;
	}

	public void setContectperson(String contectperson) {
		this.contectperson = contectperson;
	}
   /**
   * 获取提醒状态
   * @return
   */
	@Length(max=2)
	@Column(nullable=false)
	public int getIsremind() {
		return isremind;
	}
 
	public void setIsremind(int isremind) {
		this.isremind = isremind;
	}
	/**
	 * 获取手机号码
	 * @return
	 */
	@NotNull
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}