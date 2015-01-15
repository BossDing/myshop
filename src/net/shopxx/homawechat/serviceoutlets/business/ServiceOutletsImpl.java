package net.shopxx.homawechat.serviceoutlets.business;

import java.util.List;

import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;
import net.shopxx.entity.Review;
import net.shopxx.homawechat.serviceoutlets.IServiceOutlets;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderItemService;
import net.shopxx.service.OrderService;
import net.shopxx.service.ProductService;
import net.shopxx.service.ReviewService;

/**
 * ServiceOutletsImpl
 * 
 * @author shenlong
 */
public class ServiceOutletsImpl implements IServiceOutlets {

	private Member member = null;
	public static String sb = "";
	public static String stroe = "0";

	
	public String serviceOutletsBinDing(String content, String fromUserName,
			MemberService memberService) throws Exception {
		if (memberService.findByMicrono(fromUserName) != null) {
			member = memberService.findByMicrono(fromUserName);
		}

		if (content != null && content.length() > 0 && content.equals("n")) {
			String username = member.getUsername();
			if (username != null) {
				member.setMicrono(null);
				memberService.update(member);
				return "亲，您已解除绑定，请继续关注中山公用平台，谢谢!";
			} else {
				return "亲，您未与中山公用平台绑定!";
			}
		}
		if (member != null) {
			return "亲，您的账号已与中山公用平台账号进行了绑定!\r\n\n用户名:" + member.getUsername()
					+ "\n解除绑定请回复　n";
		} else {
			if (memberService.findByUsername(content) != null) {
				member = memberService.findByUsername(content);
				member.setMicrono(fromUserName);
				memberService.update(member);
				return "亲，恭喜您，绑定成功![胜利]\n用户名为:" + member.getUsername();
			} else {
				return "亲，很抱歉，您输入的账号在中山公用平台中不存在，请联系客服人员!";
			}
		}
	}

	
	public String serviceOutletsOrderSearch(String fromUserName,
			OrderService orderService, MemberService memberService,
			OrderItemService orderItemService) throws Exception {
		if (memberService.findByMicrono(fromUserName) != null) {
			member = memberService.findByMicrono(fromUserName);
		}
		if (member != null) {
			String contentSbf = "";
			List<Order> orderlist = orderService.findList(member, null, null,
					null);
			for (Order order : orderlist) {
				if (("completed").equals(order.getOrderStatus().toString())) {
					long id = order.getId();
					List<OrderItem> orderitemlist = orderItemService.findAll();
					for (OrderItem orderItem : orderitemlist) {
						if (orderItem.getOrder().getId().equals(id)) {
							contentSbf += "【" + orderItem.getSn() + "】\n商品名称："
									+ orderItem.getName() + "\n商品价格："
									+ orderItem.getPrice() + "\n购买数量："
									+ orderItem.getQuantity() + "\n";
						}
					}
					contentSbf += "请回复括号内数字进行商品评论!\n";
				}
			}
			if (contentSbf.equals("")) {
				return "亲，您还没有已付款的订单，请尽情下单付款吧![呲牙]";
			} else {
				return contentSbf;
			}
		} else {
			return "亲，请绑定微信号后做此操作![抱拳]";
		}
	}

	
	public String serviceOutletsOrderComment(String content,
			String fromUserName, MemberService memberService,
			ProductService productService, ReviewService reviewService)
			throws Exception {
		if (memberService.findByMicrono(fromUserName) != null) {
			member = memberService.findByMicrono(fromUserName);
		}
		if (member != null) {
			Product product = productService.findBySn(content);
			if (product == null) {
				product = productService.findBySn(sb);
			}
			if (product != null) {
				if (content.length() > 4
						&& (content.substring(0, 4).equals("2014") || content
								.substring(0, 4).equals("2013"))) {
					sb = content;
				}

				List<Review> reviewList = reviewService.findList(member,
						product, null, null, null, null, null);
				if (reviewList.size() > 0) {
					Review review = reviewList.get(0);
					if (review.getScore() > 0
							&& !review.getContent().equals("微信评单!")) {
						return "亲，您已评价过此商品!";
					}

					if (review.getScore() > 0
							&& !review.getContent().equals("微信评单!")) {
						return "亲，您已评价过此商品!";
					} else {
						if (review.getScore() <= 0) {
							if (content.matches("[1-5]")) {
								stroe = content;
								reviewService.delete(review.getId());
								Review review1 = new Review();
								review1.setContent("微信评单!");
								review1.setIp("微信评单!");
								review1.setIsShow(true);
								review1.setMember(member);
								review1.setProduct(product);
								review1.setScore(Integer.parseInt(content));
								reviewService.save(review1);
								stroe = "0";
								return "亲，请输入要评论的内容!";
							} else {
								return "亲，请输入1-5间的评分数!";
							}
						}
						if (review.getContent().equals("微信评单!")) {
							reviewService.delete(review.getId());
							Review review1 = new Review();
							review1.setContent(content);
							review1.setIp("微信评单!");
							review1.setIsShow(true);
							review1.setMember(member);
							review1.setProduct(product);
							review1.setScore(Integer.parseInt(stroe));
							reviewService.save(review1);
							sb = "";
							return "亲，感谢你的配合，评价完成![拥抱]";
						} else {
							sb = "";
							return "亲，感谢你的配合，评价完成![拥抱]";
						}
					}
				} else {
					stroe = "0";
					Review review = new Review();
					review.setContent("微信评单!");
					review.setIp("微信评单!");
					review.setIsShow(true);
					review.setMember(member);
					review.setProduct(product);
					review.setScore(Integer.parseInt(stroe));
					reviewService.save(review);
					return "亲，请输入评分：数字1-5![抱拳]";
				}
			} else {
				return "亲，很抱歉，找不到对应商品(原因可能是商品已下架或输入有误)[抱拳]";
			}

		} else {
			return "亲，请绑定微信号后做此操作![抱拳]";
		}
	}

	
	public String serviceOutletsBalance(String fromUserName,
			MemberService memberService) throws Exception {
		Member member = memberService.findByMicrono(fromUserName);
		if (member != null) {
			return "账户余额：" + member.getBalance().toString() + "￥[悠闲]";
		} else {
			return "亲，请绑定微信号后做此操作![抱拳]";
		}
	}

}
