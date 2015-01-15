package net.shopxx.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 水质数据
 * 
 * @author WeiHuaLin   
 * @version 1.0
 */
@Entity
@Table(name = "xx_water_quality_data")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_water_quality_data_sequence")
public class WaterQualityData extends OrderEntity{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 13424252343243242L;
	
	/**
	 * 	企业号
	 */
	private String entCode;		
	
	/**
	 * 	省ID
	 */
	private long provinceId;	
	
	/**
	 * 	省名称
	 */
	private String provinceName;
	
	/**
	 * 	市ID
	 */
	private long cityId;		
	
	/**
	 * 	市名称
	 */
	private String cityName;
	
	/**
	 * 	区ID
	 */
	private long districtId;
	
	/**
	 * 	区名称
	 */
	private String districtName;
	
	/**
	 * 	小区
	 */
	private String communityName;
	
	/**
	 * 	Rds  Rds(≤ 1000 mg/l)
	 */
	private int tds;	
	
	/**
	 * 	余氯	 余氯(≤ 0.5 mg/l)	
	 */
	private double chlorine;
	
	/**
	 * 	ph值	 ph值(6.5--8.5)
	 */
	private double ph;			
	
	/**
	 * 	浊度  浊度(≤ 1 NTU)
	 */
	private double turbidity;		
	
	/**
	 * 	硬度 硬度(≤ 450 mg/l)
	 */
	private int solidity;		
	
	/**
	 * 	COD COD(≤ 3 mg/l)
	 */
	private double COD;			
	
	/**
	 * 	推荐型号
	 */
	private String itemSpec;		
	
	/**
	 * 	创建人
	 */
	private String createdBy;	
	
	/**
	 * 	修改人
	 */
	private String lastUpdateBy;
	
	
	public String getEntCode() {
		return entCode;
	}
	public void setEntCode(String entCode) {
		this.entCode = entCode;
	}
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public int getTds() {
		return tds;
	}
	public void setTds(int tds) {
		this.tds = tds;
	}
	public double getChlorine() {
		return chlorine;
	}
	public void setChlorine(double chlorine) {
		this.chlorine = chlorine;
	}
	public double getPh() {
		return ph;
	}
	public void setPh(double ph) {
		this.ph = ph;
	}
	public double getTurbidity() {
		return turbidity;
	}
	public void setTurbidity(double turbidity) {
		this.turbidity = turbidity;
	}
	public int getSolidity() {
		return solidity;
	}
	public void setSolidity(int solidity) {
		this.solidity = solidity;
	}
	public double getCOD() {
		return COD;
	}
	public void setCOD(double cod) {
		COD = cod;
	}
	public String getItemSpec() {
		return itemSpec;
	}
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
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
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	
}
