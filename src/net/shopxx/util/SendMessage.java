package net.shopxx.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.tempuri.SmsIDGroup;
import org.tempuri.holders.ArrayOfSmsIDListHolder;

import com.mobset.view.MobsetClient;

public class SendMessage {

	/**
	 * 校验是否可以发送短信
	 * @param msg
	 * @param mobile
	 * @return boolean
	 */
	  public boolean checkMessage(String msg, String mobile){
		//增加手机号空和位数控制 Modify By ZJC 2011-12-01 14:07
		 if(mobile == null || msg == null || mobile.trim().length() != 11 || msg.length() == 0)
			return false;
		 else
			return true;
	}
	
	/**
	 * @throws UnsupportedEncodingException added by lindezhao in 2012-11-6 16:18:35
	 *             发送信息，下行短信MT接口
	 * */
	@SuppressWarnings("deprecation")
	public void executeSendV(String msg, String mobile, String msgid,String entcode)
			throws UnsupportedEncodingException {   
		if(!checkMessage(msg, mobile))
			return;
		
		//SMSBean smsBean = new SMSBean("send", msg, mobile, msgid);deleted by lindezhao in 2012-11-6 15:33:29
		SMSBean smsBean = new SMSBean(entcode);
		smsBean.SMSBeanV("send", msg, mobile, msgid, entcode);
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
//		CPCDao cpcDao = new CPCDao(entcode);
		String url = "";
		try {
			StringBuffer sbfStr = new StringBuffer("select cpcs.confvalue " +
					"  from cpcsysconf cpcs " + 
					" where cpcs.confname = 'SMSServerCompay' and (cpcs.entcode is null or cpcs.entcode ='"
					+entcode+"') ");
//			String interfaceCompany = cpcDao.strSelect(sbfStr.toString());
			String interfaceCompany = "";
			if(interfaceCompany.equals("上海短信通")){
				url = "http://121.101.221.34:8888/sms.aspx?action=send"
						+ "&userid="+smsBean.userid+"&account=" + smsBean.getUid() + "&password=" + smsBean.PASSWORD
						+ "&mobile=" + smsBean.getMobile() + "&content=" + java.net.URLEncoder.encode(msg, "UTF-8");
			}
			if(interfaceCompany.equals("深圳短信接口")){
				url = "http://sms.wap10000.com/sdk/SMS?cmd=" + smsBean.getCmd()
						+ "&uid=" + smsBean.getUid() + "&psw=" + smsBean.getPsw()
						+ "&mobile=" + smsBean.getMobile() + "&msg="+smsBean.getMsg();
			}
			if(interfaceCompany.equals("广州首易")){
				Map<String,String> requestMap = new HashMap<String,String>();
				requestMap.put("cordId",smsBean.userid);
				requestMap.put("userName",smsBean.getUid());
				requestMap.put("passwd",smsBean.PASSWORD);
				requestMap.put("serverIP","sms3.mobset.com");
				requestMap.put("mobiles",smsBean.getMobile());
				requestMap.put("content",msg);
				Map<String,Object> resultMap = MobsetClient.smsSend(requestMap);
				
				if(null != resultMap 
						&& !resultMap.isEmpty()){
					if(resultMap.containsKey("errMsg")){
						System.out.println("广州首易短信发送异常:"+resultMap.get("errMsg"));
					}
					if(resultMap.containsKey("smsMsg")){
						System.out.println("广州首易短信发送返回信息:"+resultMap.get("smsMsg"));
					}
					if(resultMap.containsKey("SmsID")){
						ArrayOfSmsIDListHolder arrayIdList = (ArrayOfSmsIDListHolder) resultMap.get("SmsID");
						if(null != arrayIdList){
							SmsIDGroup[] smsIds = arrayIdList.value;
							if(null != smsIds && smsIds.length > 0){
							 for(int index=0;index<smsIds.length;index++){
								 System.out.println("广州首易短信发送短信ID:"+resultMap.get("smsMsg")); 
							 }
							}
						}
					}
				}
				return;
			}
			System.out.println("短信连接："+url);
		} catch (Exception e) {
			System.out.println("获取数据异常："+e.getMessage());
		} finally {
//			cpcDao.freeConnection();
		}
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		try {
			// 执行postMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("客户端发送失败！");
			}
			// 读取内容
			String responseBody = new String(getMethod.getResponseBody());
			if (responseBody.equals("100")) {
			} else if (responseBody.equals("101")) {
				System.out.println(mobile + "短信发送失败！");
			} else if (responseBody.equals("102")) {
				System.out.println(mobile + "短信用户验证失败！");
			} else if (responseBody.equals("103")) {
				System.out.println(mobile + "短信号码有错！");
			} else if (responseBody.equals("104")) {
				System.out.println(mobile + "短信内容有错！");
			} else if (responseBody.equals("105")) {
				System.out.println(mobile + "短信操作频率过快！");
			} else if (responseBody.equals("106")) {
				System.out.println(mobile + "短信限制发送！");
			} else if (responseBody.equals("107")) {
				System.out.println("参数不全！");  
			}
			System.out.print("返回信息" + responseBody);
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("客户端发送失败！");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}

	}

	
}
