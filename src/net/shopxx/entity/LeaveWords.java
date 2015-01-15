package net.shopxx.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Entity
@Table(name = "xx_leave_words")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_leave_words_sequence")
public class LeaveWords extends BaseEntity {

	private static final long serialVersionUID = 1138530417069125808L;
	
	/**用户姓名*/
	private String userName;
	
	/** 会员 */
	private Member member;
	
	/**手机号*/
	private String phone;
	
	/**地址*/
	private String address;
	
	/**QQ*/
	private String qq;
	
	/**微信号*/
	private String micro;
	
	/**备注*/
	private String  remark;
	
	/**电子邮件*/
	private String email;
		
	/**加盟区域*/
	private String joinArea;
	
	/**加盟类型*/
	private String joinType;
	
	/**留言类型*/
	
	/**1、留言 2、询问 3、投诉 4、售后 5、求购 6、在线留言*/
	private String consultationType;
	
	/**主题*/
	private String theme;
	
	/**附件*/
	private String accessory;
	
	/** 内容 */
	private String content;
	
	/** 咨询 */
	private LeaveWords forLeaveWords;

	/** 回复 */
	private Set<LeaveWords> replyLeaveWords = new HashSet<LeaveWords>();
	
	/**企业号*/
	private String entcode;
	
	/** 是否已查看 */
	private Boolean isRead;
	
	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	/**留言来源 0表示万家乐官方商城  1万家乐官网...**/
    private Integer source;

	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	public LeaveWords getForLeaveWords() {
		return forLeaveWords;
	}

	public void setForLeaveWords(LeaveWords forLeaveWords) {
		this.forLeaveWords = forLeaveWords;
	}
	
	@OneToMany(mappedBy = "forLeaveWords", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public Set<LeaveWords> getReplyLeaveWords() {
		return replyLeaveWords;
	}

	public void setReplyLeaveWords(Set<LeaveWords> replyLeaveWords) {
		this.replyLeaveWords = replyLeaveWords;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMicro() {
		return micro;
	}

	public void setMicro(String micro) {
		this.micro = micro;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJoinArea() {
		return joinArea;
	}

	public void setJoinArea(String joinArea) {
		this.joinArea = joinArea;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getConsultationType() {
		return consultationType;
	}

	public void setConsultationType(String consultationType) {
		this.consultationType = consultationType;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	/**
	 *获取企业号
	 * @return
	 */
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	public String getEntcode() {
		return entcode;
	}
	 /**
	  * 设置企业号
	  * @param entcode
	  */
	public void setEntcode(String entcode) {
		this.entcode = entcode;
	}
}
