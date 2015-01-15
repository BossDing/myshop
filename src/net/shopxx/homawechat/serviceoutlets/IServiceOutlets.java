package net.shopxx.homawechat.serviceoutlets;

import net.shopxx.service.MemberService;
import net.shopxx.service.OrderItemService;
import net.shopxx.service.OrderService;
import net.shopxx.service.ProductService;
import net.shopxx.service.ReviewService;

/** IServiceOutlets概要说明：管理接口
 * @author shenlong
 */

public interface IServiceOutlets {
	/**
	 * 功能：账号绑定
	 * @param content文本内容
	 * @param fromUserName收信人
	 * @param memberService会员
	 * @return String 
	 * @throws Exception 
	 */
	public String serviceOutletsBinDing(String content,String fromUserName,MemberService memberService) throws Exception;
	
	/**
	 * 功能：订单查询
	 * @param content文本内容
	 * @param fromUserName收信人
	 * @param memberService会员
	 * @param memberService订单
	 * @return String 
	 * @throws Exception 
	 */
	public String serviceOutletsOrderSearch(String fromUserName,OrderService orderService,MemberService memberService,OrderItemService orderItemService) throws Exception;
	
	/**
	 * 功能：订单评价(商品评价)
	 * @param content文本内容
	 * @param fromUserName收信人
	 * @param memberService会员
	 * @param productService商品
	 * @param reviewService评论
	 * @param content文本内容
	 * @return String 
	 * @throws Exception 
	 */
	public String serviceOutletsOrderComment(String content,String fromUserName,MemberService memberService,ProductService productService,ReviewService reviewService) throws Exception;

	/**
	 * 功能：会员余额
	 * @param content文本内容
	 * @param fromUserName收信人
	 * @param memberService会员
	 * @return String
	 * @throws Exception
	 */
	public String serviceOutletsBalance(String fromUserName,MemberService memberService) throws Exception;
}
