package net.shopxx.controller.test;

import java.util.Map;

import net.shopxx.util.app.alipay.util.AlipaySingleQuery;

public class TestAlipaySingleQuery {

	public static void main(String[] args) throws Exception {
		
		//key 4czvuacut6r8adbaleapqhhfiy7p9bvw;
		String strXml = AlipaySingleQuery.getSingleQueryString("", "2014103010807-001");
		
		Map<String, String> map = AlipaySingleQuery.parseXMLStrToMap(strXml);
		System.out.println(map);
		
	}
}
