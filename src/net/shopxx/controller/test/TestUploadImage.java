package net.shopxx.controller.test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class TestUploadImage {
	
	public static void main(String[] args) throws Exception {
		System.out.println("begin...");
		String path = "D:\\test_upload_image.png";
		File file = new File(path);

		if (null == file || file.length() < 1) {
			throw new Exception("not found file or file zero");
		}

		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--";
		String LINE_END = "\r\n";
//		String strTmp = "{'num':*,'stdize':'600'}";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		Long time = System.currentTimeMillis();

		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append("http://test.etwowin.com:9008/m/file/upload.jhtml");
//		sbUrl.append("image/upload?num=*");
//		sbUrl.append("&stdsize=600");
//		sbUrl.append(time);

		//请求
		try {
			URL url = new URL(sbUrl.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(200000);
			conn.setConnectTimeout(200000);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", "utf-8"); // 设置编码
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; mobile; ios;android; macro;)");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE
					+ ";boundary="
					+ BOUNDARY);

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			StringBuffer sb = new StringBuffer();
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINE_END);

			//name 是文件的存储位置需保持跟服务端一致
			sb.append("Content-Disposition: form-data; name=\"iconfile\"; filename=\""
					+ file.getName()
					+ "\""
					+ LINE_END);
			sb.append("Content-Type: application/octet-stream; charset="
					+ "utf-8"
					+ LINE_END);
			sb.append(LINE_END);
//			sb.append(strTmp);
			dos.write(sb.toString().getBytes());
			InputStream fin = new FileInputStream(file);
			byte[] buf = new byte[1024];
			int nCount = 1;
			int nTotal = 0;

			while (nCount > 0) {

				nCount = fin.read(buf);

				if (nCount > 0) {

					dos.write(buf, 0, nCount);
					dos.flush();
					nTotal += nCount;

				}
			}

			fin.close();

			dos.write(LINE_END.getBytes());
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
			dos.write(end_data);
			dos.flush();
			dos.close();
			dos = null;

			int nCode = conn.getResponseCode();
			if (HttpURLConnection.HTTP_OK == nCode) {
				System.out.println("请求成功!");
				// 返回成功状态
				InputStream is = conn.getInputStream();

				byte[] mResponseData = new byte[is.available()];
				int len = 0;

				while ((len = is.read(mResponseData)) != -1) {
					System.out.println(new String(mResponseData, "utf-8"));
				}
			}
			else {
				throw new Exception("HTTP Code: " + nCode);
			}
//			if (checkError(nCode, conn)) {
//
//				this.mResponseData = null;
//				throw new Exception(this.Result, this.Message);
//			}

		}
		catch (Exception e) {
			
		}

//		if (null != this.mResponseData && Exception.OK == this.Result) {
//
//			// TWXmlParser parser = new TWXmlParser();
//			TWJsonParser parser = new TWJsonParser();
//			parser.parse(mResponseData);
//
//			if (parser.isError()) {
//
//				this.Result = Exception.SERVER;
//				this.Message = parser.getError();
//				parser = null;
//				throw new Exception(this.Result, this.Message);
//			}
//
//			TWDataInfo info = parser.getData();
//			parser = null;
//			if (info.getString("sucess").equals("1")) {
//
//				info.clear();
//				throw new Exception(Exception.FILE, info.getString("error"));
//
//			}
//			else {
//
//				return info.getString("iconurl");
//			}
//
//		}
//		else {
//
//			throw new Exception("Data is null");
//		}

	}
}
