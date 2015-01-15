package net.shopxx.util;

import java.io.UnsupportedEncodingException;


public class SMSBean { 
	private String uid;
	private String psw;
	/** 手机号码*/
	private String mobile;
	/** 短信内容*/
	private String msg;
	/** 接口类型*/
	private String cmd;
	/** 消息编号*/ 
	private String msgid;
	/** 定时发送日期*/  
	private String tdate;
	/** 定时发送时间*/
	private String time;
	/** 常量用户名 */
	 public String UID = "zstcl";
	/** 常量密码 */
	 public String PASSWORD = "ktxxglb";
	 /***客户ID***/
	 public String userid;
	 /****源消息内容****/
	 public String message;
  

	public void SMSBeanV(String cmd, String msg, String mobile, String msgid,String entcode)
				throws UnsupportedEncodingException {
		
	
			this.UID="abc";
			this.PASSWORD="201076";
			this.userid = "300034";
		
			this.uid = this.UID;
			this.cmd = cmd;
			this.mobile = mobile;
			this.msg = java.net.URLEncoder.encode(msg, "GB2312");
			this.msgid = msgid;
    }

	
	public SMSBean(String cmd) {
		this.uid = this.UID;
		this.cmd = cmd;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

}

