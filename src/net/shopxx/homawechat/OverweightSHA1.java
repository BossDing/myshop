/**
 *******************************************************************************
 * 
 * (c) Copyright 2011 
 *
 * 文 件  名：AddSHA1.java
 * 系统名称：etoway.eshop
 * 模块名称：(请更改成该模块名称)
 * 创 建  人：huanghy
 * 创建日期：2013-11-18 上午10:07:47
 * 修 改 人：(修改了该文件，请填上修改人的名字)
 * 修改日期：(请填上修改该文件时的日期)  
 * 版     本： V1.0.0
 *******************************************************************************  
 */
package net.shopxx.homawechat;
import java.security.*;

/** AddSHA1概要说明：SHA1加密
 * @author Administrator
 */
public class OverweightSHA1 {
	 private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
         "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	public static String sha1(String data){
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("SHA-1"); // 同样可以使用SHA1
			// 计算md5函数
			md.update(data.getBytes());
			// digest()最后确定返回sha1 hash值，返回值为8为字符串。因为sha1 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			// 参数也可不只用16可改动,当然结果也不一样了
			byte [] dataAry = md.digest();
			return byteToString(dataAry);
		} catch (Exception e) {
			throw new RuntimeException("fail to encrypt",e);
		}
	}
	
	 // 返回形式为数字跟字符串
	 private static String byteToArrayString(byte bByte) {
	     int iRet = bByte;
	     // System.out.println("iRet="+iRet);
	     if (iRet < 0) {
	         iRet += 256;
	     }
	     int iD1 = iRet / 16;
	     int iD2 = iRet % 16;
	     return strDigits[iD1] + strDigits[iD2];
	 }
	 
	 // 转换字节数组为16进制字串
	 private static String byteToString(byte[] bByte) {
	     StringBuffer sBuffer = new StringBuffer();
	     for (int i = 0; i < bByte.length; i++) {
	         sBuffer.append(byteToArrayString(bByte[i]));
	     }
	     return sBuffer.toString();
	 }

}
