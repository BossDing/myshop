package net.shopxx.homawechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shopxx.homawechat.tool.TextMessage;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * MicroLetterManager概要说明：微信公众平台管理类
 * 
 * @author Administrator
 */
public class MicroLetterManager extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5389064031475344532L;

	/***************************************************************************
	 * 用户最大请求次数
	 */
	public static int postCount = 0;
	public static final String token = "xwswechat";
	private WebApplicationContext ctx;

	/***************************************************************************
	 * 处理GET请求
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			// if(postCount <= 2){
			// postCount = postCount + 1;
			// 验证是否微信公众平台请求
			String signature = null == request.getParameter("signature") ? ""
					: request.getParameter("signature");
			String timestamp = null == request.getParameter("timestamp") ? ""
					: request.getParameter("timestamp");
			String nonce = null == request.getParameter("nonce") ? "" : request
					.getParameter("nonce");
			String echostr = null == request.getParameter("echostr") ? "" : request.getParameter("echostr");
			String[] tempAarray = { token, timestamp, nonce };
			Arrays.sort(tempAarray);
			String tempStr = OverweightSHA1.sha1(tempAarray[0] + tempAarray[1]
					+ tempAarray[2]);
			System.out.println(tempStr.equals(signature));
			if (tempStr.equals(signature)) {
//				System.out.println("echostr:" + echostr);
				out.write(echostr);
				out.flush();
			} else {
				out.write("奥马商城公用平台微信管理异常:鉴权错误不允许接入!");
				out.flush();
			}
			// } else {
			// out.write("中山公用平台微信管理异常:请求达到最大限制、请稍后再试!");
			// out.flush();
			// }
		} finally {
			out.close();
		}
		//doPost(request, response);
	}

	/***************************************************************************
	 * 处理post请求
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this
				.getServletContext());
		try {
			// if(postCount <= 2){
			// postCount = postCount + 1;
			TextMessage sendMessage = MicroLetterService.processManager(
					request, ctx);

			StringBuffer Connet = new StringBuffer();
			// 文字回复
			if (sendMessage.getArticleCount() == 0) {
				Connet.append("<xml>");
				Connet.append("<ToUserName><![CDATA["
						+ sendMessage.getToUserName() + "]]></ToUserName>");
				Connet.append("<FromUserName><![CDATA["
						+ sendMessage.getFromUserName() + "]]></FromUserName>");
				Connet.append("<CreateTime>" + sendMessage.getCreateTime()
						+ "</CreateTime>");
				Connet.append("<MsgType><![CDATA[" + sendMessage.getMsgType()
						+ "]]></MsgType>");
				Connet.append("<Content><![CDATA[" + sendMessage.getContent()
						+ "]]></Content>");
				Connet.append("</xml>");
			} else {
				// 图文回复
				Connet.append("<xml>");
				Connet.append("<ToUserName><![CDATA["
						+ sendMessage.getToUserName() + "]]></ToUserName>");
				Connet.append("<FromUserName><![CDATA["
						+ sendMessage.getFromUserName() + "]]></FromUserName>");
				Connet.append("<CreateTime>" + sendMessage.getCreateTime()
						+ "</CreateTime>");
				Connet.append("<MsgType><![CDATA[" + sendMessage.getMsgType()
						+ "]]></MsgType>");
				Connet.append("<ArticleCount><![CDATA["
						+ sendMessage.getArticles().size()
						+ "]]></ArticleCount>");
				Connet.append("<Articles>");
				for (int i = 0; i < sendMessage.getArticles().size(); i++) {
					net.shopxx.homawechat.tool.Article a = sendMessage
							.getArticles().get(i);
					Connet.append("<item>");
					Connet.append("<Title><![CDATA[" + a.getTitle()
							+ "]]></Title>");
					Connet.append("<Description><![CDATA[" + a.getDescription()
							+ "]]></Description>");
					Connet.append("<PicUrl><![CDATA[" + a.getPicUrl()
							+ "]]></PicUrl>");
					Connet.append("<Url><![CDATA[" + a.getUrl() + "]]></Url>");
					Connet.append("</item>");
				}
				Connet.append("</Articles>");
				Connet.append("</xml>");
			}
			out.write(Connet.toString());
			// } else {
			// System.out.print("-----dfsaf1dasfasd4as5d6-------");
			// out.write("中山公用微信管理异常:请求达到最大限制、请稍后再试!");
			// out.flush();
			// }

		} finally {
			out.close();

		}
	}

	/***************************************************************************
	 * clearStringBuffer方法慨述:清除StringBuffer
	 * 
	 * @param tempSbf
	 *            void
	 */
	private void clearStringBuffer(StringBuffer tempSbf) {
		if (null != tempSbf && tempSbf.length() > 0) {
			tempSbf.delete(0, tempSbf.length());
		}
	}

	/***************************************************************************
	 * 撤销
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		if (postCount != 0) {
			postCount = 0;
		}
	}

	/***************************************************************************
	 * 初始化
	 * 
	 * @throws SinocpcException
	 */
	// @Override
	// public void init() throws ServletException {
	// super.init();
	//
	// //获取鉴权信息
	// ClientApiUtil.receiveAccessToken(ctx);
	// //请求次数
	// Timer timer = new Timer(false);
	// timer.schedule(new TimerTask(){
	// public void run(){
	// if(postCount > 0){
	// postCount = postCount - 1;
	// } else {
	// postCount = 0;
	// }
	// }
	// },1000, 1000);
	// //定时获取鉴权信息
	// Timer accessTokenTimer = new Timer(false);
	// accessTokenTimer.schedule(new TimerTask(){
	// public void run(){
	// ClientApiUtil.receiveAccessToken(ctx);
	// }
	// },1000, 3*60*60*1000/2);
	// }
}
