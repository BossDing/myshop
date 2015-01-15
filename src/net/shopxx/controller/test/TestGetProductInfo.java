package net.shopxx.controller.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipInputStream;

/**
 * @description: 测试查询详细
 * @author: Guoxianlong
 * @date: Sep 2, 2014 9:52:08 AM
 */
public class TestGetProductInfo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int nRet = 0;
		byte[] bs;
		String strUrl;
		String strTmp;
		String sessionId = "";
		String tmpLine;
		String lines;
		String strCode = "UTF-8";

		BufferedReader reader;
		ZipInputStream zipStream;
		HttpURLConnection http = null;

		try {
			// 120.132.154.7:82
			// strUrl =
			// http://www.itwowin.com
			// "http://task.etwowin.com:82/jsp/loginman.jsp?classid=mbuser&action=login";
			strUrl = "http://m.emacro.cn/m/product/getInfo.jhtml";
			
			System.out.println(strUrl);
			URL url = new URL(strUrl);

			// 连接
			http = (HttpURLConnection) url.openConnection();
			// 连接服务器失败{0}！
			if (http == null) throw new Exception(strUrl);

			// 设置头
			http.setDoInput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Cache-Control", "no-cache");
			http.setRequestProperty("User-Agent",
					"Mozilla/5.0 (compatible; mobile; ios;android; macro;)");
			http.setRequestProperty("Content-Type", "text/html");
			http.setDoOutput(true);
			http.setRequestProperty("Content-Type", "text/xml;charset=utf-8");

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			strTmp = "{'id':'1052'}";
			out.write(strTmp.getBytes());

			out.close();

			// 禁止跳转
			http.setInstanceFollowRedirects(false);
			// 提交
			http.connect();

			OutputStream httpOut = http.getOutputStream();
			httpOut.write(out.toByteArray());
			httpOut.flush();
			httpOut.close();

			// 获取返回编号
			nRet = http.getResponseCode();

			if (HttpURLConnection.HTTP_OK == nRet) {
				System.out.println("请求成功!");
				// 返回成功状态
				InputStream is = http.getInputStream();

				byte[] buf = new byte[is.available()];
				int len = 0;

				while ((len = is.read(buf)) != -1) {
					System.out.println(new String(buf, "utf-8"));
				}
			}
			else {
				throw new Exception("HTTP Code: " + nRet);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			http = null;
		}
	}
}
