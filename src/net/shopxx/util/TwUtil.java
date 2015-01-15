package net.shopxx.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopxx.entity.Order;

public class TwUtil {

	private static String hexString = "0123456789ABCDEF";
	private static int READ_BUFF_SIZE = 1024;
	public static int MOBILE_IMAGE_STDSIZE = 400;

	public static String decodeHex(String bytes) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	public static byte[] decodeHextoByte(String bytes) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return baos.toByteArray();
	}

	public static String encodeHex(byte[] str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str;
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {

			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 将null转成空字串
	 * 
	 * @param str
	 * @return
	 */
	public static String nvlString(String str) {

		if (str == null) {
			str = "";

		}

		return str;

	}

	public static void copyFile(String sourcePath, String tragetPath)
			throws Exception {

		File sFile = new File(sourcePath);

		if (sFile.exists()) {

			InputStream input = new FileInputStream(sourcePath);
			FileOutputStream output = new FileOutputStream(tragetPath);

			byte[] buffer = new byte[1024];
			int len;

			while ((len = input.read(buffer)) != -1) {

				output.write(buffer, 0, len);
				output.flush();
			}

			input.close();
			output.flush();
			output.close();

			output = null;
		}

	}

	/**
	 * 生成缩略图。
	 */
	public static void genThumbnail(String filePath, String traget, int stdSize)
			throws Exception {

		int Width = 0;
		int Height = 0;
		String suffix = getFileSuffix(filePath).toLowerCase();

		BufferedImage bi = null;
		bi = ImageIO.read(new File(filePath));

		if (null != bi) {

			if (0 == Width) Width = bi.getWidth();
			if (0 == Height) Height = bi.getHeight();

			if (bi.getWidth() > stdSize && bi.getHeight() > stdSize) {

				// 按比例缩小
				int nWidth = bi.getWidth();
				int nHeight = bi.getHeight();
				double dbScaleX = 1.0 * stdSize / nWidth;
				double dbScaleY = 1.0 * stdSize / nHeight;
				int targetW, targetH;
				if (dbScaleX < dbScaleY) {

					// 按宽度比例缩放
					targetW = (int) (nWidth * dbScaleX);
					targetH = (int) (nHeight * dbScaleX);
				}
				else {

					// 按高度比例缩放
					targetW = (int) (nWidth * dbScaleY);
					targetH = (int) (nHeight * dbScaleY);
				}

				BufferedImage target = new BufferedImage(targetW, targetH,
						BufferedImage.TYPE_INT_BGR);
				Graphics g = target.getGraphics();
				g.drawImage(bi,
						0,
						0,
						targetW,
						targetH,
						0,
						0,
						nWidth,
						nHeight,
						null);

				g.dispose();

				// 统一采用jpg格式，保存到临时目录
				File toFile = new File(traget);
				ImageIO.write(target, suffix, toFile);

				if (target != null) {
					target.flush();
				}

			}
			else {
				//将文件复制到新的目录
				copyFile(filePath, traget);

			}
		}

	}

	public static String getFileSuffix(String filePath) {

		if (filePath == null) {
			return null;

		}
		else if (filePath.lastIndexOf(".") == -1) {
			return null;

		}

		int len = filePath.lastIndexOf(".");

		return filePath.substring(len + 1);

	}

	public static int nvlInt(Object obj) {

		String str;

		if (obj == null) {
			str = "0";
		}
		else {

			str = obj.toString();

			if (str.length() == 0) {
				str = "0";

			}
		}

		return Integer.parseInt(str);

	}

	public static byte[] negByte(byte[] values) {

		int size = 0;

		if (values != null) {
			size = values.length;

		}

		for (int i = 0; i < size; i++) {

			int b = 0;
			for (int j = 0; j < 8; j++) {
				int bit = (values[i] >> j & 1) == 0 ? 1 : 0;
				b += (1 << j) * bit;
			}
			values[i] = (byte) b;
		}

		return values;

	}

	public static byte[] deByte(byte[] values) {

		int size = 0;

		if (values != null) {
			size = values.length;

		}

		for (int i = 0; i < size; i++) {

			int temp = values[i] & 0xFF;

			values[i] = (byte) ((temp >>> 2) | (temp << 6));
		}

		return values;
	}

	public static String genCheckCode(String str) throws Exception {

		str = MD5Util.md5(str);

		str = encodeHex(negByte(str.getBytes()));

		return str;

	}

	public static String decryptStr(String str) throws Exception {

		String decry = "";


		try {
			decry = new String(negByte(decodeHextoByte(str)));
			//转码后的

			BASE64Decoder decoder = new BASE64Decoder();
			decry = new String(decoder.decodeBuffer(decry));

		}
		catch (Exception iOException) {

			throw new Exception("类型转换异常");
		}

		return decry;

	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encryptStr(String str) throws Exception {

		String encryStr = "";
		String negStr;

		// 先base64加密
		BASE64Encoder encode = new BASE64Encoder();

		try {
			encryStr = encode.encode(str.getBytes("utf-8"));
			encryStr = encodeHex(negByte(encryStr.getBytes()));

		}
		catch (UnsupportedEncodingException e) {
			throw new Exception(e.getMessage());
		}

		return encryStr;
	}

	/**
	 * 获取汉字拼音首字母
	 * 
	 * @param chines
	 * @return
	 */
	/*
	 * public static String converterToFirstSpell(String chines) {
	 * 
	 * String pinyinName = ""; char[] nameChar = chines.toCharArray();
	 * HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	 * defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	 * defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	 * 
	 * for (int i = 0; i < nameChar.length; i++) {
	 * 
	 * if (nameChar[i] > 128) {
	 * 
	 * try { pinyinName += PinyinHelper.toHanyuPinyinStringArray( nameChar[i],
	 * defaultFormat)[0].charAt(0);
	 * 
	 * } catch (BadHanyuPinyinOutputFormatCombination e) { e.printStackTrace();
	 * }
	 * 
	 * } else { pinyinName += nameChar[i];
	 * 
	 * } }
	 * 
	 * return pinyinName; }
	 */

	public static Map<String, Object> pareObject(String str) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String, Object>> tmpList;

		JSONObject jObj = JSONObject.fromObject(str);

		Iterator<String> it = jObj.keys();
		String key;
		Object obj;

		while (it.hasNext()) {

			key = it.next();

			obj = jObj.get(key);

			if (obj instanceof JSONArray) {

				tmpList = parsArray((JSONArray) obj);

				if (tmpList != null && tmpList.size() != 0) {

					map.put(key.toLowerCase(), tmpList);
				}

			}
			else {
				map.put(key.toLowerCase(), jObj.get(key).toString());

			}

		}

		return map;

	}

	public static Map<String, Object> pareObject(InputStream input)
			throws Exception {

		// 生成流
		ByteArrayOutputStream outPut = new ByteArrayOutputStream();
		byte[] data = new byte[READ_BUFF_SIZE];
		int count;
		while ((count = input.read(data, 0, READ_BUFF_SIZE)) != -1) {
			outPut.write(data, 0, count);
		}

		data = null;
		return pareObject(new String(outPut.toByteArray(), "utf-8"));

	}

	public static String mapStrtoJson(Map<String, ?> hmObj, String key,
			String strJson) {

		JSONObject obj = JSONObject.fromObject(hmObj);
		obj.element(key, strJson);

		return obj.toString();
	}

	public static String mapToJsion(Map<String, ?> hmObj) {

		JSONObject obj = JSONObject.fromObject(hmObj);

		return obj.toString();
	}

	public static JSONObject maptoJsin(Map<String, ?> hmObj) {

		JSONObject obj = JSONObject.fromObject(hmObj);
		return obj;

	}

	public static List<HashMap<String, Object>> parsArray(JSONArray arry) {

		ArrayList<HashMap<String, Object>> listCont = null;
		HashMap<String, Object> hm;

		JSONObject jobj;

		int size = 0;

		if (arry != null) {

			size = arry.size();
		}

		if (size != 0) {

			listCont = new ArrayList<HashMap<String, Object>>();

			for (int i = 0; i < size; i++) {

				jobj = arry.getJSONObject(i);

				if (jobj != null && jobj.size() != 0) {

					hm = new HashMap<String, Object>();

					hm.putAll(pareObject(jobj.toString()));

					listCont.add(hm);
				}

			}
		}

		return listCont;

	}
	
	
	/**
	 *  订单显示的状态
	 * @param orderStatus 	订单状态		未确认  unconfirmed,   已确认  confirmed,   已完成  completed,   已取消   cancelled 
	 * @param paymentStatus 	支付状态		未支付 unpaid,  部分支付 partialPayment,  已支付 paid,  部分退款 partialRefunds,  已退款 refunded
	 * @param shippingStatus		配送状态		未发货 unshipped,  部分发货 partialShipment,  已发货 shipped,  部分退货 partialReturns,  已退货 returned
	 * @return
	 */
	public static int getOrderStatus(Order order, String orderStatus, String paymentStatus, String shippingStatus) {
		String str = "" ;
		int i = 0;
		if(order.isExpired()) {
			str = "已过期";
			i = 0;
		} else if(orderStatus == "completed") {
			str = "已完成";
			i = 1;
		} else if(orderStatus == "cancelled") {
			str = "已取消";
			i = 2;
		} else if(orderStatus == "confirmed" && paymentStatus == "paid" && shippingStatus == "unshipped") {
			str = "已支付-等待发货";
			i = 3;
		} else if(orderStatus == "confirmed" && paymentStatus == "paid" && shippingStatus == "shipped") {
			str = "已支付-已发货-等待收货";
			i = 4;
		} else if(paymentStatus == "unpaid") {
			str = "等待付款";
			i = 5;
		}
		return i;
	}
	
	/**
	 *  订单显示的状态
	 * @param orderStatus 	订单状态		未确认  unconfirmed,   已确认  confirmed,   已完成  completed,   已取消   cancelled 
	 * @param paymentStatus 	支付状态		未支付 unpaid,  部分支付 partialPayment,  已支付 paid,  部分退款 partialRefunds,  已退款 refunded
	 * @param shippingStatus		配送状态		未发货 unshipped,  部分发货 partialShipment,  已发货 shipped,  部分退货 partialReturns,  已退货 returned
	 * @return
	 */
	public static String getStringOrderStatus(Order order, String orderStatus, String paymentStatus, String shippingStatus) {
		String str = "" ;
		if(order.isExpired()) {
			str = "已过期";
		} else if(orderStatus == "completed") {
			str = "已完成";
		} else if(orderStatus == "cancelled") {
			str = "已取消";
		} else if(orderStatus == "confirmed" && paymentStatus == "paid" && shippingStatus == "unshipped") {
			str = "等待发货";
		} else if(orderStatus == "confirmed" && paymentStatus == "paid" && shippingStatus == "shipped") {
			str = "等待收货";
		} else if(paymentStatus == "unpaid") {
			str = "等待付款";
		}
		return str;
	}
	
	/**
	 * 预存款类型的状态
	 * @Description 
	 * @author Guoxianlong
	 * @create_date Sep 17, 20146:24:00 PM
	 */
	public static int getIntDepositType(String type) {
		String str = "";
		int i = 6;
		if(type == "memberRecharge") {
			i = 0;
			str = "会员充值";
		} else if(type == "memberPayment") {
			i = 1;
			str = "会员支付";
		} else if(type == "adminRecharge") {
			i = 2;
			str = "后台充值";
		} else if(type == "adminChargeback") {
			i = 3;
			str = "后台扣费";
		} else if(type == "adminPayment") {
			i = 4;
			str = "后台支付";
		} else if(type == "adminRefunds") {
			i = 5;
			str = "后台退款";
		}
		return i;
	}
}
