<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.member.order.list")}[#if systemShowPowered] - Powered By SHOP++[/#if]</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
    <link href="${base}/resources/shop/css/sns.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/indiv_order_details.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	var $cancelOrder = $("#cancelOrder");
	$cancelOrder.click(function(){
	if (confirm("${message("shop.member.order.cancelConfirm")}")) {
			$.ajax({
				url: "cancel.jhtml?sn=${order.sn}",
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(message) {
					if (message.type == "success") {
						location.reload(true);
					} else {
						$.message(message);
					}
				}
			});
		}
		return false;
	});      						
});
</script>					
</head>			
<body>																									
	[#assign current = "orderList" /]			
	[#include "/shop/include/header.ftl" /]
	<div class="content">
	<div class="container member">
		<div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>><span><a href="/member/index.jhtml">个人中心</a></span>><span><a href="/member/order/list.jhtml">我的订单</a></span></div>
		  <div class="kfdlb">
			  <ul>
				  <li>分享到：</li>
				  <li><a href="#"><img src="/resources/shop/images/qq_ico.png" width="17px";></a></li>
				  <li><a href="#"><img src="/resources/shop/images/renren_ico.png" width="17px";></a></li>
				  <li><a href="#"><img src="/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
				  <li><a href="#"><img src="/resources/shop/images/weibo_ico.png" width="17px";></a></li>
			      <li><a href="#"><img src="/resources/shop/images/xinxi_ico.png" width="17px";></a></li>
			  </ul>
		  			</div>   
				</div>
		</div>
		
		[#include "/shop/member/include/navigation.ftl" /]
			<div class="indiv_content">
					   <div class="indiv_car">
							        <div id="car_nav">
							           <ul>
							               <li id="this"><a href="#">订单详情</a></li>
							                <li ><a href="#">订单号：${order.sn}</a></li>
							           </ul>
							           [#if !order.expired && order.orderStatus == "unconfirmed" && order.paymentStatus == "unpaid"]
							           <div id="del"><a href="javascript:;" id="cancelOrder">订单回收站</a></div>
							           [/#if]
							        </div>
							        <div id="car_soso">
							        尊敬的用户<span>${order.member.username}</span>您已于期<span>${order.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>成功于万家乐商城购买商品如下：
							      <span class="stus">状态：</span>
							       <i>
							       [#if order.expired]
										${message("shop.member.order.hasExpired")}
									[#elseif order.orderStatus == "completed" || order.orderStatus == "cancelled"]
										${message("Order.OrderStatus." + order.orderStatus)}
									[#elseif order.paymentStatus == "unpaid" || order.paymentStatus == "partialPayment"]
										${message("shop.member.order.waitingPayment")}
										[#if order.shippingStatus != "unshipped"]
											${message("Order.ShippingStatus." + order.shippingStatus)}
										[/#if]
									[#else]
										${message("Order.PaymentStatus." + order.paymentStatus)}
										[#if order.paymentStatus == "paid" && order.shippingStatus == "unshipped"]
											${message("shop.member.order.waitingShipping")}
										[#else]
											${message("Order.ShippingStatus." + order.shippingStatus)}
										[/#if]
									[/#if]
							       </i>
							           </div>
							           <table id="order_infor" border="0" cellspacing="0" cellpadding="0" width="945" >
							<thead>
							  <tr id="titleone">
							  <td class="goods"><input class="choose" type="checkbox" value="" />商品编码</td>
							    <td class="goods">商品</td>
							     <td class="price">数量</td>
							    <td  class="price">商品价格</td>
							    <td class="quantity">商品折扣</td>
							    <td class="operate">促销活动</td>
							  </tr>
							 
							  </thead>  
							  <tbody>						
							[#list order.orderItems as orderItem]
							  <tr>
							    <td><div class="goods">${orderItem.sn}</div></td>
							    <td><div class="goods">
										          <div class="goods_name">${abbreviate(orderItem.fullName, 30)}</div>
										          <!--<div class="goods_num">货品编码：579019610078</div>-->
										          <!--<div class="goods_color">颜色:妙趣白</div>-->
									</div>
							    </td>
							    <td><div class="price"> ${orderItem.quantity}</div></td>
							    <td><div class="quantity">￥${orderItem.price}</div></td>
							    <td><div class="operate">[#if orderItem.promotion_discount??]￥${orderItem.promotion_discount}[/#if]</div></td>
							    <td><div class="pay"> ${order.promotion.name}</div></td>
							  </tr>
							  [/#list]   
							  </tbody>				
							  <tfoot>
							  <tr>
							  <td colspan="6" align="right">
							      </td>
							   <td>
							      合计金额：
							      </td>
							      <td><div class="total"><a href="#">${currency(order.amount, true)}</a></div></td>
							   </tr>
							   <tr >
							   <td colspan="8">
							   <div class="addres"><span>${order.consignee}</span>
							     <span>${order.areaName}</span>
							     <span>${order.address}</span>     
							     <span>${order.zipCode}</span>
							     <span>${order.phone}</span>
							     </div>
							 
							    <!--<div id="new_addres"><a href="#">使用新地址</a></div>-->
							       </td>
							       </tr>
							        <tr>
							    
							    <td colspan="8" >
							        [#if !order.expired && (order.orderStatus == "unconfirmed" || order.orderStatus == "confirmed") && (order.paymentStatus == "unpaid" || order.paymentStatus == "partialPayment")]
							          			<a href="payment.jhtml?sn=${order.sn}" class="button"><input class="page_next" type="button" value="订单支付" /></a>
							          [/#if]
							         </td>
							  </tr>
							  </tfoot>
							</table>
							   </div>
							   <div id="clear"></div>
							   </div>
	<div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>