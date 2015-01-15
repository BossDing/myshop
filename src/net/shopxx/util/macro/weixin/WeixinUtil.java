package net.shopxx.util.macro.weixin;

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
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

/**
 * 文件名称: WeixinUtil 模块名称: 公众平台通用接口工具类 创建日期: 2014-11-28
 */
public class WeixinUtil {

	/**
	 * (Tomcat 使用此方法发送请求)
	 * @param serviceUrl
	 * @param requestMode
	 * @return
	 */
	public static JSONObject requestMethod(String serviceUrl, String requestMode) {
		StringBuilder strb_ressponse = new StringBuilder();
		JSONObject jsonObject = null;
		URL request_url;
		try {
			request_url = new URL(serviceUrl);
			// 打开连接
			HttpURLConnection httpURLConnection = (HttpURLConnection) request_url
					.openConnection();
			httpURLConnection.setDoOutput(true);// 打开写入属性
			httpURLConnection.setDoInput(true);// 打开读取属性
			httpURLConnection.setRequestMethod(requestMode);// 设置提交方法
			httpURLConnection.setConnectTimeout(8000);// 连接超时时间
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.connect();
			// 读取post之后的返回值
			BufferedReader in = new BufferedReader(new InputStreamReader(
					(InputStream) httpURLConnection.getInputStream(), "UTF-8"));
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
	 * （WebLogic 使用此方法发送请求）
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
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
					.openConnection();
			httpsURLConnection.setSSLSocketFactory(ssf);
			httpsURLConnection.setDoOutput(true);
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpsURLConnection.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpsURLConnection.connect();
			}
			httpsURLConnection.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpsURLConnection
						.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpsURLConnection.getInputStream();
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
			httpsURLConnection.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 网页授权获取用户基本信息 openid 用户的唯一标识
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private static String getOpenidFromWechatService(HttpServletRequest request,
			HttpServletResponse response) {
		String openid = null;
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			/**
			 * 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
			 * 若用户禁止授权，则重定向后不会带上code参数，仅会带上state参数redirect_uri?state=STATE
			 * code说明 ：
			 * code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
			 */
			String code = request.getParameter("code");
			if (StringUtils.isNotEmpty(code)) {
				String url = MicroConf.ACCESS_TOKEN_URL + "?appid="
						+ MicroConf.APP_ID + "&secret=" + MicroConf.APP_SECRET
						+ "&code=" + code + "&grant_type=authorization_code";
//				System.out.println("url: "+url);
//				System.out.println("code: "+code);
				/**
				 * 正确时返回的JSON数据包如下：
				 *	{
				 *	   "access_token":"ACCESS_TOKEN",
				 *	   "expires_in":7200,
				 *	   "refresh_token":"REFRESH_TOKEN",
				 *	   "openid":"OPENID",
				 *	   "scope":"SCOPE"
				 *	}
				 */
//				JSONObject obj = WeixinUtil.httpRequest(url, "GET", null);
				JSONObject obj = WeixinUtil.requestMethod(url, "GET");
				openid = obj.get("openid").toString();
				String accessToken = obj.get("access_token").toString();
//				System.out.println("openid : "+openid);
//				System.out.println("token : "+accessToken);
				HttpSession session = request.getSession();
				session.setAttribute("openid", openid);
				session.setAttribute("accessToken", accessToken);
				Object expiresIn = obj.get("expires_in");
				if (expiresIn != null) {
					session.setMaxInactiveInterval(Integer.valueOf(expiresIn.toString()));
				}
//				System.out.println("JSON date--->");
//				System.out.println(obj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return openid;
	}

	/**
	 * 网页授权获取用户基本信息 openid 用户的唯一标识
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getOpenid(HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		Object openid = session.getAttribute("openid");
//		System.out.println("openid: "+openid);
//		Object accessToken = session.getAttribute("accessToken");
		if (openid == null) {
			openid = getOpenidFromWechatService(request, response);
		}
		return openid == null ? null : openid.toString();
	}
	
	/**
	 * 返回的JSON数据包：
	 * {
	 *   "openid":" OPENID",
	 *   " nickname": NICKNAME,
	 *   "sex":"1",  用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 *   "province":"PROVINCE"
	 *   "city":"CITY",
	 *   "country":"COUNTRY",
	 *   "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46", 
	 *	 "privilege":[
	 *		"PRIVILEGE1"
	 *		"PRIVILEGE2"
	 *    ]
	 *    "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
	 * }
	 * @param request
	 * @param response
	 * @return
	 */
	public static JSONObject getWeixinUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
//		System.out.println("-------------------getWeixinUserInfo---------------");
		HttpSession session = request.getSession();
		Object openid = session.getAttribute("openid");
		Object accessToken = session.getAttribute("accessToken");
		if (openid == null) {
			openid = getOpenid(request, response);
			accessToken = session.getAttribute("accessToken");
		}
		
//		System.out.println("openid: "+openid);
//		System.out.println("accessToken: "+accessToken);
		String url = MicroConf.USER_INFO_URL + "?access_token="+accessToken+"&openid="+openid;
//		System.out.println(url);
//		JSONObject jsonObj = WeixinUtil.httpRequest(url, "GET", null);
		JSONObject jsonObj = requestMethod(url, "GET");
//		System.out.println("getWeixinUserInfo Json data----");
//		System.out.println(jsonObj);
		String nickname = jsonObj.get("nickname").toString();
//		String nickname = jsonObj.getString("nickname");
//		String headImageUrl = jsonObj.getString("headimgurl");
//		System.out.println("nickname: "+nickname);
//		System.out.println("headImageUrl: "+headImageUrl);
		return jsonObj;
	}

}
