package net.shopxx.util;

import java.util.HashMap;
import java.util.Map;

import org.tempuri.SmsIDGroup;
import org.tempuri.holders.ArrayOfSmsIDListHolder;

import com.mobset.view.MobsetClient;

public class SendMessageUtils {

	/**
	 * 校验是否可以发送短信
	 * 
	 * @param msg
	 * @param mobile
	 * @return boolean
	 */
	public static boolean checkMessage(String msg, String mobile) {
		// 增加手机号空和位数控制 Modify By ZJC 2011-12-01 14:07
		if (mobile == null || msg == null || mobile.trim().length() != 11
				|| msg.length() == 0)
			return false;
		else
			return true;
	}

	/**
	 * 
	 * @param msg
	 *            短信内容
	 * @param mobile
	 *            接收短信手机号码
	 * @param msgid
	 *            消息id
	 * @param entcode
	 *            企业号
	 * @return 发送短信结果: true 发送成功 false 发送失败
	 * @throws Exception
	 */
	public static boolean executeSendV(String msg, String mobile, String msgid,
			String entcode) throws Exception {
		if (!checkMessage(msg, mobile))
			return false;

		// SMSBean smsBean = new SMSBean("send", msg, mobile, msgid);deleted by
		// lindezhao in 2012-11-6 15:33:29
		SMSBean smsBean = new SMSBean(entcode);
		smsBean.SMSBeanV("send", msg, mobile, msgid, entcode);
		// 构造HttpClient的实例
		String url = "";
		try {
			// String interfaceCompany = "广州首易";
			// if (interfaceCompany.equals("上海短信通")) {
			// url = "http://121.101.221.34:8888/sms.aspx?action=send"
			// + "&userid=" + smsBean.userid + "&account="
			// + smsBean.getUid() + "&password=" + smsBean.PASSWORD
			// + "&mobile=" + smsBean.getMobile() + "&content="
			// + java.net.URLEncoder.encode(msg, "UTF-8");
			// }
			// if (interfaceCompany.equals("深圳短信接口")) {
			// url = "http://sms.wap10000.com/sdk/SMS?cmd=" + smsBean.getCmd()
			// + "&uid=" + smsBean.getUid() + "&psw="
			// + smsBean.getPsw() + "&mobile=" + smsBean.getMobile()
			// + "&msg=" + smsBean.getMsg();
			// }
			// if (interfaceCompany.equals("广州首易")) {
			Map<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("cordId", smsBean.userid);
			requestMap.put("userName", smsBean.getUid());
			requestMap.put("passwd", smsBean.PASSWORD);
			requestMap.put("serverIP", "sms3.mobset.com");
			requestMap.put("mobiles", smsBean.getMobile());
			requestMap.put("content", msg);
			Map<String, Object> resultMap = MobsetClient.smsSend(requestMap);

			if (null != resultMap && !resultMap.isEmpty()) {
				if (resultMap.containsKey("errMsg")) {
					System.out.println("广州首易短信发送异常:" + resultMap.get("errMsg"));
				}
				if (resultMap.containsKey("smsMsg")) {
					System.out.println("广州首易短信发送返回信息:"
							+ resultMap.get("smsMsg"));
				}
				if (resultMap.containsKey("SmsID")) {
					ArrayOfSmsIDListHolder arrayIdList = (ArrayOfSmsIDListHolder) resultMap
							.get("SmsID");
					if (null != arrayIdList) {
						SmsIDGroup[] smsIds = arrayIdList.value;
						if (null != smsIds && smsIds.length > 0) {
							System.out.println("广州首易短信发送短信ID:"
									+ resultMap.get("smsMsg"));
							return true;
						}
					}
				}
			}
			// }
		} catch (Exception e) {
			System.out.println("获取数据异常：" + e.getMessage());
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			SendMessageUtils
					.executeSendV("测试发短信", "13516510362", null, "macro");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
