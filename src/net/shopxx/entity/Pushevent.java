package net.shopxx.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 微信用户操作记录
 * 
 * @author shenlong
 * @version 3.0
 */
@Entity
@Table(name = "pushevent")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pushevent_sequence")
public class Pushevent extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2054657226112275250L;
	
	/** 开发者微信号 */
	private String tousername;
	/** 发送方帐号(一个OpenID) */
	private String fromusername;
	/** 消息创建时间 (整型)  */
	private String createtime;
	/** 消息类型，event  */
	private String msgtype;
	/** 事件类型，CLICK  */
	private String event;
	/** 事件KEY值，与自定义菜单接口中KEY值对应 */
	private String eventkey;
	/** 事件主键值 */
	private String objid;
	
	public String getTousername() {
		return tousername;
	}
	public void setTousername(String tousername) {
		this.tousername = tousername;
	}
	public String getFromusername() {
		return fromusername;
	}
	public void setFromusername(String fromusername) {
		this.fromusername = fromusername;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventkey() {
		return eventkey;
	}
	public void setEventkey(String eventkey) {
		this.eventkey = eventkey;
	}
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}
	
}
