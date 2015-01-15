package net.shopxx.util.app.alipay.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.shopxx.util.app.alipay.config.AlipayConfig;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 支付宝单笔交易查询结果 （非支付宝官方）
 * 
 * @author cgd 2014-10-08
 *
 */
public class AlipaySingleQuery {
	
	/** 私有化构造器 */
	private AlipaySingleQuery() {
	}

	/**
	 * 支付宝单笔订单交易查询
	 * 可根据订单号或支付宝交易号查询（建议使用支付宝交易号查询--效率高）
	 * @param trade_no
	 *            支付宝交易号
	 * @param out_trade_no
	 *            商户订单号
	 * @return XML格式的字符串 "<?xml version...><alipay>...</alipay>"
	 * @throws Exception
	 */
	public static String getSingleQueryString(String trade_no,
			String out_trade_no) throws Exception {

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "single_trade_query");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("trade_no", trade_no);//支付宝交易号
		sParaTemp.put("out_trade_no", out_trade_no);//商户网站唯一订单号

		// 建立请求
		String strResult = AlipaySubmit.buildRequest("", "", sParaTemp);
		return strResult;
	}

	/**
	 * 根据XML字符串转换成Map
	 * @param strXml 支付宝单笔订单查询 返回的XML格式字符串 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXMLStrToMap(String strXml) {
		Map<String, String> map = new HashMap<String, String>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(strXml); // 将字符串转为XML
			Element rootE = doc.getRootElement(); // 获取根节点
			Element isSuccessE = rootE.element("is_success");//请求是否成功，不代表业务处理成功
			String isSuccess = isSuccessE.getTextTrim();
			map.put("is_success", isSuccess);
			if("F".equals(isSuccess)){
				map.put("error", rootE.elementTextTrim("error"));//支付宝错误码
				return map;
			}
			map.put("sign", rootE.elementTextTrim("sign")); //签名
			map.put("sign_type", rootE.elementTextTrim("sign_type")); //签名方式
			Element tradeE = rootE.element("response").element("trade");
			List<Element> eList = tradeE.elements();
			for (Element e : eList) {
				map.put(e.getName(), e.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(doc != null) doc.clearContent();
		}
		return map;
	}

}
