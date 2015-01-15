package net.shopxx.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 活动分类
 * @author lzy
 *
 */
@Entity
@Table(name = "xx_action_type")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_action_type_sequence")
public class ActionType extends BaseEntity {

	private static final long serialVersionUID = 6201941271258028361L;
	
	/** 活动分类名称 */
	private String actionName;
	
	/** 活动备注 */
	private String memo;
	
	/** 创建人 */
	private String createBy;
	
	/** 修改人 */
	private String modifyBy;
	
	/** 活动 */
	private Set<Promotion> promotions = new HashSet<Promotion>();

	/**
	 * 获取活动分类名称
	 * @return
	 */
	@NotNull
	@Column(name="action_name")
	public String getActionName() {
		return actionName;
	}

	/**
	 * 设置活动分类名称
	 * @param actionName
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * 获取活动备注
	 * @return
	 */
	@Column(name="memo")
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置活动备注
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取创建人
	 * @return
	 */
	@Column(name="create_by")
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * 设置创建人
	 * @param createBy
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 获取修改人
	 * @return
	 */
	@Column(name="modify_by")
	public String getModifyBy() {
		return modifyBy;
	}

	/**
	 * 设置修改人
	 * @param modifyBy
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	/**
	 * 获取活动
	 * @return
	 */
	@OneToMany(mappedBy = "actionType", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Promotion> getPromotions() {
		return promotions;
	}

	/**
	 * 设置活动信息
	 * @param promotions
	 */
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}
	
}
