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
	//΢��֧��֧��֪ͨ����̨֪ͨ��ʾ�����̻����մ��ĵ����п�������
	//---------------------------------------------------------

	//����֧��Ӧ�����

	ResponseHandler resHandler = new ResponseHandler(request, response);
	String PARTNER_KEY = WeixinPayConfig.PARTNER_KEY;
	String APP_KEY = WeixinPayConfig.APP_KEY;
	resHandler.setKey(PARTNER_KEY);
	resHandler.setKey(APP_KEY);
	//�����������
	RequestHandler queryReq = new RequestHandler(null, null);
	queryReq.init();

	 //��ȡHTTP�����������
	InputStream is = request.getInputStream();
	//��HTTP��������������һ��BufferedReader����
	BufferedReader br = new BufferedReader(new InputStreamReader(is,"GBK"));
	
	//��ȡHTTP��������
	String buffer = null;
	StringBuffer sb = new StringBuffer();
	
	while ((buffer = br.readLine()) != null) {
	//��ҳ������ʾ��ȡ�����������
		sb.append(buffer);
	}
	String result = sb.toString();
	String out_trade_no = resHandler.getParameter("out_trade_no");	//�̻�������
	String transaction_id = resHandler.getParameter("transaction_id");	//�Ƹ�ͨ������
	String total_fee = resHandler.getParameter("total_fee");	//���,�Է�Ϊ��λ
	String trade_state = resHandler.getParameter("trade_state");	//֧�����
	
	//System.out.println("------transaction_id: "+transaction_id+"��total_fee: "+total_fee+"��trade_state: "+trade_state);
	//�ж�ǩ�������
	if("0".equals(trade_state)) {
		request.setAttribute("result", result);
		request.setAttribute("out_trade_no", out_trade_no);
		request.setAttribute("transaction_id", transaction_id);
		request.setAttribute("total_fee", total_fee);
		request.getRequestDispatcher("../mobile/payment/weixinVerify.jhtml").forward(request, response);
	}
%>