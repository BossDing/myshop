package net.shopxx.controller.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Document;

public class TestParseXmlForWeixinPay {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc = null;
		String strXml = "<xml><OpenId><![CDATA[oq2Y4t9q-ADSMp5_eAyae33fxC4Q]]></OpenId><AppId><![CDATA[wx4ef6a2d6bc55c849]]></AppId><IsSubscribe>1</IsSubscribe><TimeStamp>1413507171</TimeStamp><NonceStr><![CDATA[kyhJblbXsjU8VtgH]]></NonceStr><AppSignature><![CDATA[874304deabe9e81c62a8011c2976b21b1d607c4c]]></AppSignature><SignMethod><![CDATA[sha1]]></SignMethod></xml> ";
		try {
			doc = DocumentHelper.parseText(strXml); // 将字符串转为XML
			Element rootE = doc.getRootElement(); // 获取根节点
			System.out.println(rootE.elementText("OpenId"));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(doc != null) doc.clearContent();
		}
	}

}
