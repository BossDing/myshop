package net.shopxx.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "xx_trial_image")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_Trial_image_sequence")
public class TrialImage extends BaseEntity {
	
	private static final long serialVersionUID = -7582726103068441921L;

	/** 原图片 */
	private String source;
	
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
	
//	private Set<TrialReport> trialReports = new HashSet<TrialReport>();
	
//	private TrialReport trialReport;
//	
//	public TrialReport getTrialReport() {
//		return trialReport;
//	}
//
//	public void setTrialReport(TrialReport trialReport) {
//		this.trialReport = trialReport;
//	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

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
	
	

//	@OneToMany(mappedBy = "trialImage",  cascade = CascadeType.ALL)
//	public Set<TrialReport> getTrialReports() {
//		return trialReports;
//	}
//
//	public void setTrialReports(Set<TrialReport> trialReports) {
//		this.trialReports = trialReports;
//	}
}
