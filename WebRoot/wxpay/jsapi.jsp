<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.wxap.RequestHandler"%>
<%@page import="java.util.TreeMap"%>
<%@ page import="com.wxap.client.TenpayHttpClient"%>
<%@page import="java.util.SortedMap"%>
<%@page import="com.wxap.util.Sha1Util"%>
<%@ include file="config.jsp"%>

<%
	//=================================
	//jsapi�ӿ�
	//=================================
	//��ʼ��

	RequestHandler reqHandler = new RequestHandler(request, response);
	TenpayHttpClient httpClient = new TenpayHttpClient();
	
	TreeMap<String, String> outParams = new TreeMap<String, String>();
	 //��ʼ�� 
	reqHandler.init();
	
	reqHandler.init(APP_ID, APP_SECRET, PARTNER_KEY,APP_KEY);
	reqHandler.setKey(PARTNER_KEY);
	
	//��ȡ�ύ�Ķ�����
	String out_trade_no = request.getParameter("out_trade_no");
	//��ȡ�ύ����Ʒ����
	String name = request.getParameter("name");
	//��ȡ�ύ�Ķ���֧�����
	String amountPayable = request.getParameter("amountPayable");
	//����package��������
	SortedMap<String, String> packageParams = new TreeMap<String, String>();
	packageParams.put("bank_type", "WX");  //֧������   
	packageParams.put("body", name); //��Ʒ����   
	packageParams.put("fee_type","1"); 	  //���б���
	packageParams.put("input_charset", "GBK"); //�ַ���    
	packageParams.put("notify_url", NOTIFY_URL); //֪ͨ��ַ  
	packageParams.put("out_trade_no", out_trade_no); //�̻�������  
	packageParams.put("attach", out_trade_no); //�̻�������  
	packageParams.put("partner", PARTNER); //�����̻���
	packageParams.put("total_fee", amountPayable); //��Ʒ�ܽ��,�Է�Ϊ��λ
	packageParams.put("spbill_create_ip",  request.getRemoteAddr()); //�������ɵĻ���IP��ָ�û��������IP
	
	//��ȡpackage��
	String packageValue = reqHandler.genPackage(packageParams);
	String noncestr = Sha1Util.getNonceStr();
	String timestamp = Sha1Util.getTimeStamp();
	//����֧������
	SortedMap<String, String> signParams = new TreeMap<String, String>();
	signParams.put("appid", APP_ID);
	signParams.put("appkey", APP_KEY);
	signParams.put("noncestr", noncestr);
	signParams.put("package", packageValue);
	signParams.put("timestamp", timestamp);
	//����֧��ǩ����Ҫ����URLENCODER��ԭʼֵ����SHA1�㷨��
	String sign = Sha1Util.createSHA1Sign(signParams);
	
	//���ӷǲ���ǩ���Ķ������
	signParams.put("paySign", sign);
	signParams.put("signType", "sha1");
	
%>

<html>
<link href="../resources/mobile/css/jsapi.css" rel="stylesheet" type="text/css" />
	<head>
		<script language="javascript">
		function callpay(){
			WeixinJSBridge.invoke('getBrandWCPayRequest',{
  		 "appId" : "<%= APP_ID %>","timeStamp" : "<%= timestamp %>", "nonceStr" : "<%= noncestr %>", "package" : "<%= packageValue %>","signType" : "SHA1", "paySign" : "<%= sign %>" 
   			},function(res){
				if(res.err_msg == "get_brand_wcpay_request:ok") {
					alert("֧���ɹ�!");
				}
				window.location.href= "http://m.emacro.cn/mobile/member/index.jhtml";
			});
		}
		</script>
	</head>
  <body>
    <div style="width:100%;"><img src="../resources/mobile/images/weixinzhifu.jpg" width="100%";></div>
	<div class="weixinzhifu"><a href="javascript:" onclick="callpay()">΢��֧��</a></div>
  </body>
</html>
