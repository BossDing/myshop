<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>个人中 - 待付款订单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
	<meta name="keywords" content="奥马微商城">
	<link href="${base}/resources/mobile/css/daifukuan.css" rel="stylesheet" type="text/css" />
	<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>

	<script type="text/javascript">
		$().ready(function() {
			//判断当前是否存在用户
			if (!$.checkLogin()) {
				$.redirectLogin(window.location.href, "${message("shop.common.mustLogin")}");
				return false;
			}
		});
	</script>
</head>
<body>
	[#include "/mobile/include/header.ftl" /]
	[#assign current = "orderList" /]
	<div class="dingdan-big">
    <div class="shouhuo">
          [#list page.content as order]
          [#if !order.expired]
          	<a href="${base}/mobile/member/order/view.jhtml?sn=${order.sn}">
            <div class="shouhuo-b" [#if !order_has_next] class="last"[/#if]>
            	<div class="photo">
				[#list order.orderItems as orderItem]
					<img src="[#if orderItem.thumbnail??]${orderItem.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" class="thumbnail" alt="${orderItem.fullName}" />
					[#if orderItem_index == 0]
						[#break /]
					[/#if]
				[/#list]
				</div>
                <ul class="dfjsdh"> 
                    <li class="shouhuo-b-a">
                    [#list order.orderItems as orderItem]
						${abbreviate(orderItem.fullName, 15)}
						[#if orderItem_index == 0]
							[#break /]
						[/#if]
					[/#list]
					</li>
                    <li>${message("Order.sn")}：${order.sn}</li>
                    <li><span>
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
                    </span></li>
                </ul>
                <div class="right"><img src="${base}/resources/mobile/images/jie_50.png" width="100%" height="100%"></div>
            </div>
          </a>
          [/#if]
          [/#list]
        </div>
        [#if !page.content?has_content]
			<p><center>亲,您还未下过订单,去首页逛逛吧!</center></p>
		[/#if]
	</div>
</body>
</html>