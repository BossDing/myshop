package net.shopxx.plugin.enete_test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.EasyLink.OpenVendorV34.NetTran;

import net.shopxx.entity.Payment;
import net.shopxx.entity.PluginConfig;
import net.shopxx.entity.Store;
import net.shopxx.plugin.PaymentPlugin;

@Component("enetePluginTest")
public class EnetePlugin_test extends PaymentPlugin {

	@Override
	public String getName() {
		return "广州银联(测试)";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "jsc";
	}

	@Override
	public String getSiteUrl() {
		return "";
	}

	@Override
	public String getInstallUrl() {
		return "enete_test/install.jhtml";
	}

	@Override
	public String getUninstallUrl() {
		return "enete_test/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl() {
		return "enete_test/setting.jhtml";
	}

	@Override
	public String getRequestUrl() {
		return "http://test.gnete.com:8888/Bin/Scripts/OpenVendor/Gnete/V34/GetOvOrder.asp";
	}

	@Override
	public RequestMethod getRequestMethod() {
		return RequestMethod.post;
	}

	@Override
	public String getRequestCharset() {
		return "UTF-8";
	}

	@Override
	public Map<String, Object> getParameterMap(String sn, String description,
			HttpServletRequest request) {
		PluginConfig pluginConfig = getPluginConfig();
		Payment payment = getPayment(sn);
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		// 获取证书文件路径
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		try {
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 定义变量
		String SendCertFile = path + File.separator + "MERCHANT.pfx";
		;// 发送方证书路径(商户证书)
		String RcvCertFile = path + File.separator + "GNETEWEB-TEST.cer";
		;// 接收方证书路径(银联证书)
		String MerId = "193";// 商户ID参数
		String OrderNo = sn;// 商户订单号(商户订单号不超过20位)
		String OrderAmount = "0.01";// 订单金额，格式：元.角分
		String CurrCode = "CNY";// 货币代码，值为：CNY
		String CallBackUrl = getNotifyUrl(sn, NotifyMethod.sync);// 支付结果接收URL
		String ResultMode = "0"; // 支付结果返回方式(0-成功和失败支付结果均返回；1-仅返回成功支付结果)
		String Reserved01 = ""; // 保留域1
		String Reserved02 = ""; // 保留域2
		String SourceText = "MerId=" + MerId + "&" + "OrderNo=" + OrderNo + "&"
				+ "OrderAmount=" + OrderAmount + "&" + "CurrCode=" + CurrCode
				+ "&" + "CallBackUrl=" + CallBackUrl + "&" + "ResultMode="
				+ ResultMode + "&" + "Reserved01=" + Reserved01 + "&"
				+ "Reserved02=" + Reserved02;// 加密的最终文本。
		String EncryptedMsg = "";
		String SignedMsg = "";
		boolean ret;// 加密是否成功

		NetTran obj = new NetTran();
		// 使用银联公钥，对原始信息进行加密
		ret = obj.EncryptMsg(SourceText, RcvCertFile);
		if (ret == true) {
			EncryptedMsg = obj.getLastResult();
		}else {
			System.out.println("广州银联加密失败-"+obj.getLastResult());
		}
		// 对原始信息进行签名
		ret = obj.SignMsg(SourceText, SendCertFile, "12345678");
		if (ret == true) {
			SignedMsg = obj.getLastResult();
		}else{
			System.out.println("广州银联签名失败-"+obj.getLastResult());
		}

		parameterMap.put("EncodeMsg", EncryptedMsg);
		parameterMap.put("SignMsg", SignedMsg);
		return parameterMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyNotify(String sn, NotifyMethod notifyMethod,
			HttpServletRequest request) {
		// 获取证书文件路径
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		try {
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 接收页面变量
		String EncodeMsg = request.getParameter("EncodeMsg");
		String SignMsg = request.getParameter("SignMsg");
		// 定义变量
		String DecryptedMsg = "";
		String SignedMsg = "";
		String SendCertFile = path + File.separator + "MERCHANT.pfx";
		;// 发送方证书路径(商户证书)
		String RcvCertFile = path + File.separator + "GNETEWEB-TEST.cer";
		;// 接收方证书路径(银联证书)
		boolean ret;// 解密是否成功

		NetTran obj = new NetTran();
		// 解密数据
		ret = obj.DecryptMsg(EncodeMsg, SendCertFile, "12345678");
		if (ret == false) {
			// 解密失败
			System.out.println("广州银联解密失败-"+obj.getLastErrMsg());
			return false;
		} else {
			DecryptedMsg = obj.getLastResult();
			// 验签数据
			ret = obj.VerifyMsg(SignMsg, DecryptedMsg, RcvCertFile);
			if (ret == false) {
				// 验签失败
				System.out.println("广州银联验签失败-"+obj.getLastErrMsg());
				return false;
			} else {// 取出明文中各数据域
				// 商户订单号
				String OrderNo = getContent(DecryptedMsg, "OrderNo");
				// 支付单号
				String PayNo = getContent(DecryptedMsg, "PayNo");
				// 支付金额
				String PayAmount = getContent(DecryptedMsg, "PayAmount");
				// 货币代码
				String CurrCode = getContent(DecryptedMsg, "CurrCode");
				// 系统参考号
				String SystemSSN = getContent(DecryptedMsg, "SystemSSN");
				// 响应码
				String RespCode = getContent(DecryptedMsg, "RespCode");
				// 清算日期
				String SettDate = getContent(DecryptedMsg, "SettDate");
				// 保留域1
				String Reserved01 = getContent(DecryptedMsg, "Reserved01");
				// 保留域2
				String Reserved02 = getContent(DecryptedMsg, "Reserved02");
				// 响应码为"00"表示交易成功，具体的响应码对照表请查阅《开放商户支付接口V34.doc》
				if (RespCode.equals("00")) {// 支付成功
					return true;
				} else {// 支付不成功
					return false;
				}
			}
		}
	}

	/**
	 * 获取参数函数
	 * 
	 * @param input
	 * @param para
	 * @return
	 */
	public String getContent(String input, String para) {
		if (input.equals("") || para.equals("")) {
			return "";
		}
		String vv = "";
		StringTokenizer st = new StringTokenizer(input, "&");
		while (st.hasMoreElements()) {
			vv = st.nextToken();
			if (vv.indexOf(para) != -1
					&& vv.substring(0, vv.indexOf("=")).equals(para)) {
				vv = vv.substring(vv.indexOf("=") + 1);
				return vv;
			}
		}
		return "";
	}

	@Override
	public String getNotifyMessage(String sn, NotifyMethod notifyMethod,
			HttpServletRequest request) {
		if (notifyMethod == NotifyMethod.async) {
			return "success";
		}
		return null;
	}

	@Override
	public Integer getTimeout() {
		return 21600;
	}

	@Override
	public Map<String, Object> getParameterMap(String sn, String description,
			HttpServletRequest request, Store store) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyNotify(String sn, NotifyMethod notifyMethod,
			HttpServletRequest request, Store store) {
		// TODO Auto-generated method stub
		return false;
	}

}
