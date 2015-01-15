<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.wxap.RequestHandler"%>
<%@ page import="com.wxap.ResponseHandler"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.io.BufferedReader"%>
<%@page import="net.shopxx.weixin.WeixinPayConfig"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	//---------------------------------------------------------
	//微信支付支付通知（后台通知）示例，商户按照此文档进行开发即可
	//---------------------------------------------------------

	//创建支付应答对象

	ResponseHandler resHandler = new ResponseHandler(request, response);
	String PARTNER_KEY = WeixinPayConfig.PARTNER_KEY;
	String APP_KEY = WeixinPayConfig.APP_KEY;
	resHandler.setKey(PARTNER_KEY);
	resHandler.setKey(APP_KEY);
	//创建请求对象
	RequestHandler queryReq = new RequestHandler(null, null);
	queryReq.init();

	 //获取HTTP请求的输入流
	InputStream is = request.getInputStream();
	//已HTTP请求输入流建立一个BufferedReader对象
	BufferedReader br = new BufferedReader(new InputStreamReader(is,"GBK"));
	
	//读取HTTP请求内容
	String buffer = null;
	StringBuffer sb = new StringBuffer();
	
	while ((buffer = br.readLine()) != null) {
	//在页面中显示读取到的请求参数
		sb.append(buffer);
	}
	String result = sb.toString();
	String out_trade_no = resHandler.getParameter("out_trade_no");	//商户订单号
	String transaction_id = resHandler.getParameter("transaction_id");	//财付通订单号
	String total_fee = resHandler.getParameter("total_fee");	//金额,以分为单位
	String trade_state = resHandler.getParameter("trade_state");	//支付结果
	
	//System.out.println("------transaction_id: "+transaction_id+"、total_fee: "+total_fee+"、trade_state: "+trade_state);
	//判断签名及结果
	if("0".equals(trade_state)) {
		request.setAttribute("result", result);
		request.setAttribute("out_trade_no", out_trade_no);
		request.setAttribute("transaction_id", transaction_id);
		request.setAttribute("total_fee", total_fee);
		request.getRequestDispatcher("../mobile/payment/weixinVerify.jhtml").forward(request, response);
	}
%>