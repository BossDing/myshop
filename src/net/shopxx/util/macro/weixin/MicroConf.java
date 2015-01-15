package net.shopxx.util.macro.weixin;

/**
 * description:
 * 万家乐 - 微信公众号配置
 * @author cgd 2014-11-28
 * @version 1.0
 */
public class MicroConf {

	/**
	 * 服务号凭证和密码 
	 */
//	public static final String APP_ID = "wx4ef6a2d6bc55c849";
	public static final String APP_ID = "wx6ff99fe3f88571d0"; //同望科技 微信公众号

	/**
	 * 服务号凭证和密码 
	 */
//	public static final String APP_SECRET = "82053c61e96dc1afe5d28b8ee1d248c6";
	public static final String APP_SECRET = "d5b7476cd299b5cef74f64b96d02d800"; //同望科技 微信公众号

	/**
	 * 图片下载的地址和保存路径
	 */
	public static final String SRC_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get";
	
	/**
	 * 获取access_token
	 * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	 */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/**
	 * 刷新access_token
	 * https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
	 */
	public static final String REFRESH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	/**
	 * 获取用户基本信息
	 * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	 */
	public static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
	
//	/**
//	 * 获取用户基本信息
//	 */
//	public static final String USER_INFO_URL2 = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	/**
	 * 检验授权是否有效
	 * https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
	 */
	public static final String IS_ENABLED= "https://api.weixin.qq.com/sns/auth";

}
