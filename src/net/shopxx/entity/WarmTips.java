package net.shopxx.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Entity - 温馨提示
 * 
 * @author hfh
 * @version 1.0
 */
@Entity
@Table(name = "xx_Warm_Tips")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_Warm_Tips_sequence")
public class WarmTips extends BaseEntity {

	private static final long serialVersionUID = 3536993535267962270L;


	/** 地区 */
	private Area area;
	
	/** 时段上限 */
	private Number timeTopLine;

	/** 时段下限 */
	private Number timeBottomLine;

	 /**
	 * 温馨用语
	 */
	private String warmTipsDesc;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="area", nullable = false)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	
	/** 
	 * 获取时段上限
	 *    
	 * @return 时段上限
	 */
	
	@Column
	public Number getTimeTopLine() {
		return timeTopLine;
	}

	/**
	 * 设置时段上限
	 * 
	 * @param timeTopLine
	 *            时段上限
	 */
	public void setTimeTopLine(Number timeTopLine) {
		this.timeTopLine = timeTopLine;
	}

	/**
	 * 获取时段下限
	 * 
	 * @return 时段下限
	 */
	
	@Column
	public Number getTimeBottomLine() {
		return timeBottomLine;
	}

	/**
	 * 设置时段下限
	 * 
	 * @param timeBottomLine
	 *            时段下限
	 */
	public void setTimeBottomLine(Number timeBottomLine) {
		this.timeBottomLine = timeBottomLine;
	}
	
	
	 public String getWarmTipsDesc() {
			return warmTipsDesc;
	}

	public void setWarmTipsDesc(String warmTipsDesc) {
		this.warmTipsDesc = warmTipsDesc;
	}

}
