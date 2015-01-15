<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<% 

//调试模式
 boolean DEBUG_ = true;
 String PARTNER		= "1223874501" ;	//财付通商户号
 String PARTNER_KEY	= "6285fb26dbcb68accc587e8c564cd7d5";	//财付通密钥
 String APP_ID		= "wx5558a43696b0e865";	//appid
 String APP_SECRET	= "747ced90345ad04f3df9c8c649fca869";	//appsecret
 String APP_KEY		= "";	//paysignkey 128位字符串(非appkey)
 String NOTIFY_URL	= "http://m.emacro.cn/wxpay/payNotifyUrl.jsp";  //支付完成后的回调处理页面,*替换成notify_url.asp所在路径
 String LOGING_DIR	= "";  //日志保存路径
%>
