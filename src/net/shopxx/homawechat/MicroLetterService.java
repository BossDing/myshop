package net.shopxx.homawechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import net.shopxx.controller.shop.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.entity.Pushevent;
import net.shopxx.homawechat.serviceoutlets.IServiceOutlets;
import net.shopxx.homawechat.serviceoutlets.business.ServiceOutletsImpl;
import net.shopxx.homawechat.tool.MicroletterUtil;
import net.shopxx.homawechat.tool.TextMessage;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderItemService;
import net.shopxx.service.OrderService;
import net.shopxx.service.ProductService;
import net.shopxx.service.ReviewService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author shenlong
 * 类说明       
 */

public class MicroLetterService  extends BaseController{
	private static Member member;
	private static Pushevent pushevent;
	/**会员*/
	private static MemberService memberService;
	/**微信事件*/
//	private static PusheventService pusheventService;
	/**订单*/
	private static OrderService orderService;
	/**订单项*/
	private static OrderItemService orderItemService;
	/**评论*/
	private static ReviewService reviewService;
	/**商品*/
	private static ProductService productService;
	
	
	// 事件入口
	public static net.shopxx.homawechat.tool.TextMessage processManager(HttpServletRequest request,WebApplicationContext ctx)
			throws ServletException, IOException {
		memberService = (MemberService)ctx.getBean("memberServiceImpl");
//		pusheventService = (PusheventService)ctx.getBean("pusheventServiceImpl");
		orderService = (OrderService)ctx.getBean("orderServiceImpl");
		orderItemService = (OrderItemService)ctx.getBean("orderItemServiceImpl");
		reviewService = (ReviewService)ctx.getBean("reviewServiceImpl");
		productService = (ProductService)ctx.getBean("productServiceImpl");
		// 获取XML
		StringBuffer requestXml = new StringBuffer(100);
		BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) request.getInputStream(), "utf-8"));
		String line = null;
		while ((line = in.readLine()) != null) {
			requestXml.append(line);
		}
		in.close();

		TextMessage textMessage = new TextMessage();
		try {
			// 解析推送文件
			Document document = DocumentHelper.parseText(requestXml.toString());
			Element resEle = document.getRootElement();
			String FromUserName = resEle.elementText("FromUserName");
			String ToUserName = resEle.elementText("ToUserName");
			String MsgType = resEle.elementText("MsgType");// 事件类型
			String CreateTime = resEle.elementText("CreateTime");
//			 String Content = resEle.elementText("Content");//文本内容

			textMessage.setToUserName(FromUserName);
			textMessage.setFromUserName(ToUserName);
			textMessage.setCreateTime((new Date()).getTime());
			textMessage.setMsgType("text");

			String send_content = "奥马微商城,欢迎您^v^\n";
			// 如果是文本回复
			if (MsgType.equalsIgnoreCase("text")) {
				send_content = processText(FromUserName, resEle,textMessage);
			}
			// 如果是地理位置信息
			if (MsgType.equalsIgnoreCase("location")) {
//				 send_content = processLocation(FromUserName,resEle);
			}
			// 如果是事件触发
			if (MsgType.equalsIgnoreCase("event")) {
				String event = resEle.elementText("Event");
				if(event.trim().equalsIgnoreCase("click")){
					String eventKey = resEle.elementText("EventKey");
					//删除事件记录
//					if(pusheventService.findByFromusername(FromUserName) != null){
//						pusheventService.delete(pusheventService.findByFromusername(FromUserName).getId());
//					}
					//插入事件记录
					pushevent = new Pushevent();
					pushevent.setTousername(ToUserName);
					pushevent.setFromusername(FromUserName);
					pushevent.setCreatetime(CreateTime);
					pushevent.setMsgtype(MsgType);
					pushevent.setEvent(event);
					pushevent.setEventkey(eventKey);
//					pusheventService.save(pushevent);
					
				}
				send_content = processEvent(FromUserName, resEle,textMessage);
			}
			// 如果图片消息
			if (MsgType.equalsIgnoreCase("image")) {
				send_content = processImage(FromUserName, resEle,textMessage,request);
			}
			textMessage.setContent(send_content);
		} catch (Exception e) {
		} finally {
		}
		return textMessage;
	}
	
	/**
	 * 图片文本信息
	 * @param FromUserName
	 * @param resEle
	 * @param textMessage
	 * @return
	 * @throws Exception
	 */
	private static String processImage(String FromUserName, Element resEle,TextMessage textMessage,HttpServletRequest request)
			throws Exception { return "";}
	
	/**
	 * 处理文本信息
	 * @param FromUserName
	 * @param resEle
	 * @param textMessage
	 * @return
	 * @throws Exception
	 */
	private static String processText(String FromUserName, Element resEle,TextMessage textMessage) throws Exception {
		IServiceOutlets serviceOutlets = new ServiceOutletsImpl();
		String Content = resEle.elementText("Content");
		StringBuffer contentSbf = new StringBuffer("文本信息处理失败");
//		pushevent = pusheventService.findByFromusername(FromUserName);
		String eventKey = pushevent.getEventkey();
		if(Content == null || Content.length() == 0){
			MicroletterUtil.clearStringBuffer(contentSbf);
			contentSbf.append("文本信息为空");
		}else{
			if(eventKey.trim().equalsIgnoreCase("BranBinDing")){
				MicroletterUtil.clearStringBuffer(contentSbf);
				contentSbf.append(serviceOutlets.serviceOutletsBinDing(Content, textMessage.getToUserName(),memberService));
			}
			if(eventKey.trim().equalsIgnoreCase("order")){
				MicroletterUtil.clearStringBuffer(contentSbf);
				contentSbf.append(serviceOutlets.serviceOutletsOrderComment(Content,textMessage.getToUserName(),memberService,productService,reviewService));
			}
		}
		return contentSbf.toString();
	}
	
	/**
	 * 处理事件
	 * @param FromUserName
	 * @param resEle
	 * @param textMessage
	 * @return
	 * @throws Exception
	 */
	private static String processEvent(String fromUserName, Element resEle,TextMessage textMessage) throws Exception {
		IServiceOutlets serviceOutlets = new ServiceOutletsImpl();
		String eventKey = resEle.elementText("EventKey");
		StringBuffer contentSbf = new StringBuffer();
		
		if(resEle.elementText("Event").equalsIgnoreCase("subscribe")){ 
			contentSbf.append("[微笑]亲，您好！欢迎关注奥马商城服务！\r\n");
		}else if (resEle.elementText("Event").equalsIgnoreCase("CLICK")){
			
			//账号绑定
			if(eventKey.trim().equalsIgnoreCase("BranBinDing") && memberService.findByMicrono(fromUserName) != null){
				member = memberService.findByMicrono(fromUserName);
				contentSbf.append("亲，您的微信账号已与平台账号进行了绑定!\r\n\n用户名:"+member.getUsername()+"\n解除绑定请回复　n");
			}else{
				contentSbf.append("亲，请输入您的账号即可绑定，多谢合作[抱拳]");
			}
			if(eventKey.trim().equalsIgnoreCase("BranBinDing")){
				return contentSbf.toString();
			}
			
			//订单搜索
			if(eventKey.trim().equalsIgnoreCase("order")){
				MicroletterUtil.clearStringBuffer(contentSbf);
				contentSbf.append(serviceOutlets.serviceOutletsOrderSearch(textMessage.getToUserName(),orderService,memberService,orderItemService));
				return contentSbf.toString();
			}
			
			//我的余额
			if(eventKey.trim().equalsIgnoreCase("balance")){
				MicroletterUtil.clearStringBuffer(contentSbf);
				contentSbf.append(serviceOutlets.serviceOutletsBalance(textMessage.getToUserName(),memberService));
			}
			
		}
		return contentSbf.toString();
	}

	//处理地理信息
	private static String processLocation(String FromUserName,Element resEle) throws Exception{
		return "";
	}
	
	

	 
}
