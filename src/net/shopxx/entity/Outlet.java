package net.shopxx.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 服务网点
 * @author Mr.Zhang
 *
 */
@Entity
@Table(name = "xx_outlet")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_outlet_sequence")
public class Outlet extends BaseEntity {

	private static final long serialVersionUID = -1245743652819390L;
	
	/**网点编号*/
	private String sn;
	
	/** 网点名称 */
	private String name;
	
	/** 总机 */
	private String phone;
	
	/** 报装电话 */
	private String installBookPhone;
	
	/** 报修电话 */
	private String repairBookPhone;
	
	/** 安装查询电话 */
	private String installQueryPhone;
	
	/** 维修查询电话 */
	private String repairQueryPhone;
	
	/** 投诉电话 */
	private String complaintPhone;
	
	/** 传真 */
	private String fax;
	
	/** 负责人 */
	private String manager;
	
    /** 负责人职工编号 */
	private String managerSn;
	
	/** 负责人电话 */
	private String managerPhone;
	
	/** 服务区域 */
	private String serviceArea;
	
	/** 网点地址 */
	private String address;
	
	/**单位类别*/
	private String unitType;
	
	/**单位级别*/
	private String unitlevel;
	
	/**中心编号*/
	private String centerSn;
	
	/** 邮编 */
	private String zipCode;
	
	/** 工作时间 */
	private String workDate;
	
	/** 网点介绍 */
	private String introduction;
	
	/** 状态 */
	private String state;
	
	/** 备注 */
    private String remark;
    
    /** 企业号 */
    private String entCode;
    
    /**地区全称*/
    private String areaName;
    
	private String categoryNames;
    

	/** 所在地 */
    private Area area;
    
    /** 经营类别 **/
	private Set<ProductCategory> productCategorys = new HashSet<ProductCategory>();

	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInstallBookPhone() {
		return installBookPhone;
	}

	public void setInstallBookPhone(String installBookPhone) {
		this.installBookPhone = installBookPhone;
	}

	public String getRepairBookPhone() {
		return repairBookPhone;
	}

	public void setRepairBookPhone(String repairBookPhone) {
		this.repairBookPhone = repairBookPhone;
	}

	public String getInstallQueryPhone() {
		return installQueryPhone;
	}

	public void setInstallQueryPhone(String installQueryPhone) {
		this.installQueryPhone = installQueryPhone;
	}

	public String getRepairQueryPhone() {
		return repairQueryPhone;
	}

	public void setRepairQueryPhone(String repairQueryPhone) {
		this.repairQueryPhone = repairQueryPhone;
	}

	public String getComplaintPhone() {
		return complaintPhone;
	}

	public void setComplaintPhone(String complaintPhone) {
		this.complaintPhone = complaintPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManagerSn() {
		return managerSn;
	}

	public void setManagerSn(String managerSn) {
		this.managerSn = managerSn;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitlevel() {
		return unitlevel;
	}

	public void setUnitlevel(String unitlevel) {
		this.unitlevel = unitlevel;
	}

	public String getCenterSn() {
		return centerSn;
	}

	public void setCenterSn(String centerSn) {
		this.centerSn = centerSn;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEntCode() {
		return entCode;
	}

	public void setEntCode(String entCode) {
		this.entCode = entCode;
	}
	
	public String getAreaName() {
			return areaName;
	}

	public void setAreaName(String areaName) {
			this.areaName = areaName;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_outlet_product_category")
	public Set<ProductCategory> getProductCategorys() {
		return productCategorys;
	}

	public void setProductCategorys(Set<ProductCategory> productCategorys) {
		this.productCategorys = productCategorys;
	}
	
	
}
