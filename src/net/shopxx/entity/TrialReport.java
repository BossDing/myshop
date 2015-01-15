package net.shopxx.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;


/**
 * 试用报告
 * @author lzy
  * @version 1.0
 */
@Entity
@Table(name = "xx_trial_reportor")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_trial_reportor_sequence")
public class TrialReport extends BaseEntity{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5603861979125115970L;

//	/**
//	 * 申请状态
//	 */
//	public enum ReportStatus {
//
//		/** 审核中 */
//		approving,
//
//		/** 审核通过 */
//		pass,
//
//		/** 未通过审核 */
//		reject
//	}
//	
//	/** 
//	 * 是否提交试用报告
//	 */
//
//	public enum Sex{
//		/** 男 */
//		M,
//		
//		/** 女 */
//		W
//	}
	
	/** 试用报告单号 */
	private String trialReportNo;
	
	/** 试用申请 */
	private TrialApply trialApply;		

	/** 企业号 */
	private String entCode;
	
	/** 会员ID */
	private Member member;
	
	/** 试用策略ID */
	private Trial trial;
	
	/** 产品ID */
	private Product product;
	
	/** 报告状态 */
	private String reportStatus;		
	
	/** 报告人昵称 */
	private String reportName;
	
	/** 地区 */
	private Area area;
	
	/** 性别 */
	private String sex;	
	
	/** 年龄 */
	private Integer age;
	
	/** 职业 */
	private String job;	
	
	/** 兴趣爱好 */
	private String interest;
	
	/** 试用建议 */
	private String trialSuggestion;		
	
	/** 商品整体图ID */
	private String itemWholeDocid;
	
	/** 商品详细图ID */
	private String itemDetailDocid;	
	
		
	/** 创建人 */
	private String createdBy;		
	
	/** 修改人 */
	private String modifyBy;
	
	/**物流状况评论*/
	private String logistics;
	
	/**外观评论*/
	private String appearance;
	
	/**主要功能评论*/
	private String majorFunction;
	
	/**使用心得*/
	private String useingExperience;

	/**改进意见*/
	private String suggestions;
	
	/**图片附件*/
	//private TrialImage trialImage;
	
	/** 原图片1 */
	private String image1;
	/** 原图片2 */
	private String image2;
	/** 原图片3 */
	private String image3;
	/** 原图片4 */
	private String image4;
	/** 原图片5 */
	private String image5;

	
	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	public String getImage5() {
		return image5;
	}

	public void setImage5(String image5) {
		this.image5 = image5;
	}

	/**
	 * 获取商品图片
	 * 
	 * @return 商品图片
	 */

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(nullable = false, updatable = false)
//	public TrialImage getTrialImage() {
//		return trialImage;
//	}
//
//	public void setTrialImage(TrialImage trialImage) {
//		this.trialImage = trialImage;
//	}
	
//	public List<TrialImage> getTrialImages() {
//		return trialImages;
//	}
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="trial_image_id", nullable = true, updatable = false)
//	public TrialImage getTrialImage() {
//		return trialImage;
//	}
//
//	public void setTrialImage(TrialImage trialImage) {
//		this.trialImage = trialImage;
//	}

	/**
	 * 设置商品图片
	 * 
	 * @param productImages
	 *            商品图片
	 */
//	public void setTrialImages(List<TrialImage> trialImages) {
//		this.trialImages = trialImages;
//	}
	
	

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	public String getAppearance() {
		return appearance;
	}

	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}

	public String getMajorFunction() {
		return majorFunction;
	}

	public void setMajorFunction(String majorFunction) {
		this.majorFunction = majorFunction;
	}

	public String getUseingExperience() {
		return useingExperience;
	}

	public void setUseingExperience(String useingExperience) {
		this.useingExperience = useingExperience;
	}

	public String getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}

	@Column(name="trial_report_no")
	public String getTrialReportNo() {
		return trialReportNo;
	}

	public void setTrialReportNo(String trialReportNo) {
		this.trialReportNo = trialReportNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="trial_apply_id", nullable = false, updatable = false)
	public TrialApply getTrialApply() {
		return trialApply;
	}

	public void setTrialApply(TrialApply trialApply) {
		this.trialApply = trialApply;
	}

	@Column(name="ent_code")
	public String getEntCode() {
		return entCode;
	}

	public void setEntCode(String entCode) {
		this.entCode = entCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id", nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="trial_role_id", nullable = false, updatable = false)
	public Trial getTrial() {
		return trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id", nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name="report_status")
	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	@Column(name="report_name", nullable = true)
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="area", nullable = false, updatable = false)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	@Column(name="trial_suggestion")
	public String getTrialSuggestion() {
		return trialSuggestion;
	}

	public void setTrialSuggestion(String trialSuggestion) {
		this.trialSuggestion = trialSuggestion;
	}

	@Column(name="item_whole_docid")
	public String getItemWholeDocid() {
		return itemWholeDocid;
	}

	public void setItemWholeDocid(String itemWholeDocid) {
		this.itemWholeDocid = itemWholeDocid;
	}

	@Column(name="item_detail_docid")
	public String getItemDetailDocid() {
		return itemDetailDocid;
	}

	public void setItemDetailDocid(String itemDetailDocid) {
		this.itemDetailDocid = itemDetailDocid;
	}

	@Column(name="create_by")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="modify_by")
	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	@Transient
	public List<String> getImages(){
		List<String> Images = new ArrayList<String>();
		if(image1 != null){
			Images.add(image1);
		}
		if(image2 != null){
			Images.add(image2);
		}
		if(image3 != null){
			Images.add(image3);
		}
		if(image4 != null){
			Images.add(image4);
		}
		if(image5 != null){
			Images.add(image5);
		}
		return Images;
	}
}
