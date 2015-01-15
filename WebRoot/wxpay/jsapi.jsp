<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.wxap.RequestHandler"%>
<%@page import="java.util.TreeMap"%>
<%@ page import="com.wxap.client.TenpayHttpClient"%>
<%@page import="java.util.SortedMap"%>
<%@page import="com.wxap.util.Sha1Util"%>
<%@ include file="config.jsp"%>

<%
	//=================================
	//jsapi接口
	//=================================
	//初始化

	RequestHandler reqHandler = new RequestHandler(request, response);
	TenpayHttpClient httpClient = new TenpayHttpClient();
	
	TreeMap<String, String> outParams = new TreeMap<String, String>();
	 //初始化 
	reqHandler.init();
	
	reqHandler.init(APP_ID, APP_SECRET, PARTNER_KEY,APP_KEY);
	reqHandler.setKey(PARTNER_KEY);
	
	//获取提交的订单号
	String out_trade_no = request.getParameter("out_trade_no");
	//获取提交的商品名称
	String name = request.getParameter("name");
	//获取提交的订单支付金额
	String amountPayable = request.getParameter("amountPayable");
	//设置package订单参数
	SortedMap<String, String> packageParams = new TreeMap<String, String>();
	packageParams.put("bank_type", "WX");  //支付类型   
	packageParams.put("body", name); //商品描述   
	packageParams.put("fee_type","1"); 	  //银行币种
	packageParams.put("input_charset", "GBK"); //字符集    
	packageParams.put("notify_url", NOTIFY_URL); //通知地址  
	packageParams.put("out_trade_no", out_trade_no); //商户订单号  
	packageParams.put("attach", out_trade_no); //商户订单号  
	packageParams.put("partner", PARTNER); //设置商户号
	packageParams.put("total_fee", amountPayable); //商品总金额,以分为单位
	packageParams.put("spbill_create_ip",  request.getRemoteAddr()); //订单生成的机器IP，指用户浏览器端IP
	
	//获取package包
	String packageValue = reqHandler.genPackage(packageParams);
	String noncestr = Sha1Util.getNonceStr();
	String timestamp = Sha1Util.getTimeStamp();
	//设置支付参数
	SortedMap<String, String> signParams = new TreeMap<String, String>();
	signParams.put("appid", APP_ID);
	signParams.put("appkey", APP_KEY);
	signParams.put("noncestr", noncestr);
	signParams.put("package", packageValue);
	signParams.put("timestamp", timestamp);
	//生成支付签名，要采用URLENCODER的原始值进行SHA1算法！
	String sign = Sha1Util.createSHA1Sign(signParams);
	
	//增加非参与签名的额外参数
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
					alert("支付成功!");
				}
				window.location.href= "http://m.emacro.cn/mobile/member/index.jhtml";
			});
		}
		</script>
	</head>
  <body>
    <div style="width:100%;"><img src="../resources/mobile/images/weixinzhifu.jpg" width="100%";></div>
	<div class="weixinzhifu"><a href="javascript:" onclick="callpay()">微信支付</a></div>
  </body>
</html>
