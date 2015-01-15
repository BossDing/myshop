package net.shopxx.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 试用申请
 * @author lizy_java
  * @version 1.0
 */
@Entity
@Table(name = "xx_trial_apply")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_trial_apply_sequence")
public class Trial_Apply extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4484681057087993118L;

	/** 试用申请单ID */
	private Integer trial_apply_id;
	
	/** 试用申请单编号 */
	private String trial_apply_no;
	
	/** 企业号 */
	private String ent_code;
	
	/** 会员ID */
	public Integer member_id;
	
	/** 试用设置ID */
	public Integer trial_role_id;
	
	/** 商品ID */
	public Integer item_id;
	
	/** 申请日期	 */
	public Date apply_date;
	
	/** 申请宣言	 */
	public String apply_reason;
	
	/** 收货人 */
	public String receiver;
	
	/** 省份 */
	public Integer province_id;
	
	/** 市级 */
	public Integer city_id; 
	
	/** 区域 */
	public Integer district_id;
	
	/** 详细地址	 */
	public String address;
	
	/** 申请状态：approving审核中、pass审核通过、reject未通过审核  */
	public String apply_status;

	/** 是否提交试用报告：Yes 是、No 否 */
	public String is_report;
	
	/** 创建人  */
	public String created_by;
	
	/** 创建日期 */
	public Date creation_date;
	
	/** 修改人 */
	public String last_update_by;
	
	/** 修改日期 */
	public Date last_update_date;
	
	

}
