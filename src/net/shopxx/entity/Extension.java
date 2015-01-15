package net.shopxx.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="xx_extension")
@SequenceGenerator(name="sequenceGenerator", sequenceName = "xx_extension_sequences")
public class Extension extends BaseEntity {


	private static final long serialVersionUID = 1L;
	
	/**编号*/
	private String sn;
	
	/**服务卡号*/
	private String cardNo;
	
	/**密码*/
	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**用户名*/
	private String userName; 
	
	/**条形码*/
	private String barCode;
	
	/**保修期*/
	private Date endDate;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
