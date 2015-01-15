package net.shopxx.plugin.wap_unionpay.util;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sun.misc.BASE64Encoder;

import net.shopxx.dao.impl.OrderDaoImpl;
import net.shopxx.entity.Order;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.PaymentStatus;
import net.shopxx.plugin.wap_unionpay.conf.UpmpConfig;
import net.shopxx.plugin.wap_unionpay.service.UpmpService;
import net.shopxx.util.SettingUtils;


public class MypayUtil {

	public static String getSn(String orderSn ,BigDecimal amount){
        // 请求要素
		Map<String, String> req = new HashMap<String, String>();
		req.put("version", UpmpConfig.VERSION);// 版本号
		req.put("charset", UpmpConfig.CHARSET);// 字符编码
		req.put("transType", "01");// 交易类型
		req.put("merId", UpmpConfig.MER_ID);// 商户代码
		req.put("backEndUrl", UpmpConfig.MER_BACK_END_URL);// 通知URL
		req.put("frontEndUrl", UpmpConfig.MER_FRONT_END_URL);// 前台通知URL(可选)
		req.put("orderDescription", "订单描述");// 订单描述(可选)
		String ordertime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		req.put("orderTime", ordertime);// 交易开始日期时间yyyyMMddHHmmss
		req.put("orderTimeout", "");// 订单超时时间yyyyMMddHHmmss(可选)
		req.put("orderNumber", orderSn);//订单号(商户根据自己需要生成订单号)
		req.put("orderAmount", amount.toString());// 订单金额
        req.put("orderCurrency", "156");// 交易币种(可选)
        req.put("reqReserved", "透传信息");// 请求方保留域(可选，用于透传商户信息)
                
        // 保留域填充方法
        Map<String, String> merReservedMap = new HashMap<String, String>();
        merReservedMap.put("ordersn", orderSn);
        req.put("merReserved", UpmpService.buildReserved(merReservedMap));// 商户保留域(可选)
		
		Map<String, String> resp = new HashMap<String, String>();
		String respString = UpmpService.tradeX(req);
		boolean validResp = UpmpService.tradeY(respString, resp);
	       
        // 商户的业务逻辑
        if (validResp){
            // 服务器应答签名验证成功
    		String result = respString.substring(respString.lastIndexOf("tn=")+3, respString.lastIndexOf("&signMethod"));
    		String url = SettingUtils.get().getSiteUrl();
    		BASE64Encoder base64=new BASE64Encoder();
    		String paydata  =  URLEncoder.encode(base64.encode( ("tn="+result+",resultURL="+URLEncoder.encode(url+"/mobile/payment/toVerify.jhtml?ordersn="+orderSn+"&ordertime="+ordertime+"&argName=")+",usetestmode=" + true).getBytes() ) );
        	return paydata;
            
        }else {
            // 服务器应答签名验证失败
            return null;
        }
	}
	
	
	public static boolean query(String ordertime, String ordersn) throws ParseException {

        // 请求要素
		Map<String, String> req = new HashMap<String, String>();
		req.put("version", UpmpConfig.VERSION);// 版本号
		req.put("charset", UpmpConfig.CHARSET);// 字符编码
		req.put("transType", "01");// 交易类型
		req.put("merId", UpmpConfig.MER_ID);// 商户代码
		req.put("orderTime", ordertime);// 交易开始日期时间yyyyMMddHHmmss或yyyyMMdd
		req.put("orderNumber", ordersn);// 订单号
		// 保留域填充方法
        Map<String, String> merReservedMap = new HashMap<String, String>();
        merReservedMap.put("test", "test");
        req.put("merReserved", UpmpService.buildReserved(merReservedMap));// 商户保留域(可选)
		
		Map<String, String> resp = new HashMap<String, String>();
		boolean validResp = UpmpService.query(req, resp);
	       
        // 商户的业务逻辑
        if (validResp){
            // 服务器应答签名验证成功
        	return true;
            
        }else {
            // 服务器应答签名验证失败
        	return false;
            
        }
	
	}
}
