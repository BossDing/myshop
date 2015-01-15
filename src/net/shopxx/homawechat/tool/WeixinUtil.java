package net.shopxx.homawechat.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * 
 * 文件名称: WeixinUtil
 * 系统名称: etoway.eshop
 * 模块名称: 公众平台通用接口工具类
 * 创建人: Guoxianlong
 * 创建日期: Jun 10, 2014
 */
public class WeixinUtil {
	
	private static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	private static final String USERINFO = "https://api.weixin.qq.com/sns/userinfo";
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public static JSONObject requestMethod(String serviceUrl,String requestMode) {
			requestMode="GET";
			StringBuilder strb_ressponse = new StringBuilder();
			JSONObject jsonObject = null;
				URL request_url;
				try {
					request_url = new URL(serviceUrl);
				//打开连接
				HttpURLConnection httpURLConnection = (HttpURLConnection) request_url.openConnection();
				httpURLConnection.setDoOutput(true);// 打开写入属性
				httpURLConnection.setDoInput(true);// 打开读取属性
				httpURLConnection.setRequestMethod(requestMode);// 设置提交方法
				httpURLConnection.setConnectTimeout(100000);// 连接超时时间
				httpURLConnection.setReadTimeout(100000);
				httpURLConnection.connect();
				// 读取post之后的返回值
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
								(InputStream) httpURLConnection.getInputStream(),"UTF-8"));
				String line = null;
				while ((line = in.readLine()) != null) {
					strb_ressponse.append(line);
				}
				in.close();
				httpURLConnection.disconnect();// 断开连接
				jsonObject = JSONObject.fromObject(strb_ressponse.toString());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			return jsonObject;
	}
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod                                                                                   
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * 网页授权获取用户基本信息  openid 用户的唯一标识
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getOpenid(HttpServletRequest request, HttpServletResponse response) {
		String openid = "";
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
	        if(request.getParameter("code")!=null && request.getParameter("code").toString().length()>0) {
	        	// 1、 获取code 
		        String code=request.getParameter("code");
		        String url = GET_ACCESS_TOKEN_URL+"?appid="+MicroConf.APPID+"&secret="+MicroConf.APPSECRET+"&code="+code+"&grant_type=authorization_code";
		        
		        System.out.println("code : "+code);
		        System.out.println("url : "+url);
		        
		        // 2、通过code换取网页授权access_token
		        JSONObject obj = WeixinUtil.httpRequest(url, "GET", null);
		        openid = obj.get("openid").toString();
		        String access_token = obj.get("access_token").toString();
		        
		        //3、拉取用户信息
		        url = USERINFO+"?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		        obj = WeixinUtil.httpRequest(url, "GET", null);
		        
//				System.out.println("姓名: "+obj.get("nickname")+"<br>"); 这些个信息这里未获取到
//				System.out.println("city: "+obj.get("city")+"<br>");
//				System.out.println("province: "+obj.get("province")+"<br>");
//				System.out.println("country: "+obj.get("country")+"<br>");
//				System.out.println("headimgurl: "+obj.get("headimgurl")+"<br>");
		        
		        System.out.println("openid : "+openid);
//		        System.out.println("access_token : "+access_token);
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return openid;
	}
	
	
}