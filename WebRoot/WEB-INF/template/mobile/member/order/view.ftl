<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<link href="${base}/resources/mobile/css/dingdanxinxi.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<meta charset="utf-8">
<title>个人中心 - 订单详情</title>
<script type="text/javascript">
$().ready(function() {

	var $cancel = $("#cancel");
	var $dialogOverlay = $("#dialogOverlay");
	var $dialog = $("#dialog");
	var $close = $("#close");
	var $deliveryContent = $("#deliveryContent");
	var $deliveryQuery = $("a.deliveryQuery");
	var $payment = $("#payment");

	[@flash_message /]
	 
	// 订单取消
	$cancel.click(function() {
		if (confirm("${message("shop.member.order.cancelConfirm")}")) {
			$.ajax({
				url: "${base}/mobile/member/order/cancel.jhtml?sn=${order.sn}",
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
	
	// 物流动态
	$deliveryQuery.click(function() {
		var $this = $(this);
		$.ajax({
			url: "delivery_query.jhtml?sn=" + $this.attr("sn"),
			type: "GET",
			dataType: "json",
			cache: true,
			beforeSend: function() {
				$dialog.show();
				$dialogOverlay.show();
				$deliveryContent.html("${message("shop.member.order.loading")}");
			},
			success: function(data) {
				if (data.data != null) {
					var html = '<table>';
					$.each(data.data, function(i, item) {
						html += '<tr><th>' + item.time + '<\/th><td>' + item.context + '<\/td><\/tr>';
					});
					html += '<\/table>';
					$deliveryContent.html(html);
				} else {
					$deliveryContent.text(data.message);
				}
			}
		});
		return false;
	});
	
	// 关闭物流动态
	$close.click(function() {
		$dialog.hide();
		$dialogOverlay.hide();
	});
	
		$payment.click(function(){
			document.location.href = "${base}/mobile/member/order/payment.jhtml?sn=${order.sn}";
		});

});
</script>

</head>

<body>
	[#include "/mobile/include/header.ftl" /]
	[#assign current = "orderList" /]
    <div class="shouhuo">
    	<div class="top-big">
    		<div id="deliveryContent" class="deliveryContent"></div>
        	<table>
        	 	<tr>
	        	<td class="one-a">${message("Order.sn")}:</td>
	            <td colspan="2" class="four-b"><span>${order.sn}</span></td>
	            </tr>
            	<tr>
            		<td class="one-a" style="border-bottom:1px dashed #E4E4E4; padding-bottom:20px;">${message("shop.member.order.status")}:</td>
                    <td colspan="2" class="one-b" style="border-bottom:1px dashed #E4E4E4; padding-bottom:20px;">
                    <strong>
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
					 </strong>
                    </td>
                    <td class="one-c">
                    [#if !order.expired && order.orderStatus == "unconfirmed" && (order.paymentStatus == "unpaid" || order.paymentStatus == "partialPayment")]
                    <a href="javascript:;" id="cancel"><img src="${base}/resources/mobile/images/NO_10.png" width="10%"></a>
                    [/#if]
                    </td>
                </tr>
              
                <tr>
            	<td class="one-a">${message("shop.common.createDate")}:</td>
                <td colspan="2" class="four-b"><span>${order.createDate?string("yyyy-MM-dd HH:mm:ss")}</span></td>
		        </tr>
	            <tr>
	            <td class="one-a">${message("Order.paymentMethod")}:</td>
	            <td colspan="2" class="four-b"><span>${order.paymentMethodName}</span></td>
	            </tr>
	            <tr>
	            <td class="one-a">${message("Order.shippingMethod")}:</td>
	            <td colspan="2" class="four-b"><span>${order.shippingMethodName}</span></td>
	            </tr>
	            [#if order.point > 0]
	                <tr>
	                	<td class="one-a">${message("Order.point")}:</td>
	                    <td colspan="2" class="two-b"><span>
	                    ${order.point}
	                    </span></td>
	                </tr>
	            [/#if]
	            <tr>
	            <td class="one-a">${message("Order.price")}:</td>
	            <td colspan="2" class="four-b"><span>${currency(order.price, true)}</span></td>
	            </tr>
	            [#if order.fee > 0]
		            <tr>
			            <td class="one-a">${message("Order.fee")}:</td>
			            <td colspan="2" class="two-b"><span>
			            ${currency(order.fee, true)}
			            </span></td>
		            </tr>
	            [/#if]

                [#if order.freight > 0]
	                <tr>
	                	<td class="one-a">${message("Order.freight")}:</td>
	                    <td colspan="2" class="two-b"><span>
	                    ${currency(order.freight, true)}
	                    </span></td>
	                </tr>
                [/#if]
                [#if order.promotionDiscount > 0]
	                <tr>
		                <td class="one-a">${message("Order.promotionDiscount")}:</td>
		                <td colspan="2" class="two-b"><span>
		                ${currency(order.promotionDiscount, true)}
		                </span></td>
	                </tr>
                [/#if]
                [#if order.couponDiscount > 0]
	                <tr>
	                	<td class="one-a">${message("Order.couponDiscount")}:</td>
	                    <td colspan="2" class="two-b"><span>
	                    ${currency(order.couponDiscount, true)}
	                    </span></td>
	                </tr>
	            [/#if]  
	            <tr>
                <td class="one-a">${message("Order.amount")}:</td>
                <td colspan="2" class="two-b"><span>
                ${currency(order.amount, true)}
                </span></td>
                </tr>
            	[#if order.couponCode??]
	                <tr>
		                <td class="one-a">${message("shop.member.order.coupon")}:</td>
		                <td colspan="2" class="two-b"><span>
		                ${order.couponCode.coupon.name}
		                </span></td>
	                </tr>
                [/#if]
                [#if order.promotion??]
                		 <tr>
                <td class="one-a">${message("Order.promotion")}:</td>
                <td colspan="2" class="two-b"><span>
                ${order.promotion}
                </span></td>
                </tr>
                [/#if]
                <tr>
                	<td class="one-a" style="border-bottom:1px dashed #E4E4E4; padding-bottom:20px;">${message("Order.memo")}:</td>
                    <td colspan="2" class="two-b" style="border-bottom:1px dashed #E4E4E4; padding-bottom:20px;">
                    <span>${order.memo}</span></td>
                </tr>
                [#if order.isInvoice]
	                <tr>
		                <td class="four-a">${message("Order.invoiceTitle")}:</td>
		                <td colspan="2" class="four-b"><span>${order.invoiceTitle}</span></td>
	                </tr>
	                <tr>
	                	<td class="four-a" style="border-bottom:1px dashed #E4E4E4; padding-bottom:20px;">${message("Order.tax")}:</td>
	                    <td colspan="2" class="four-b" style="border-bottom:1px dashed #E4E4E4; padding-bottom:20px;">
	                    <span>${currency(order.tax, true)}</span></td>
                    </tr>
                [/#if]
                <tr>
                <td class="four-a">${message("Order.consignee")}:</td>
                <td colspan="2" class="four-b"><span>${order.consignee}</span></td>
                </tr>
                <tr>
                <td class="four-a">${message("Order.zipCode")}:</td>
                <td colspan="2" class="four-b"><span>${order.zipCode}</span></td>
                </tr>
                <tr>
                	<td class="four-a">${message("Order.address")}:</td>
                    <td colspan="2" class="four-b"><span>${order.areaName}${order.address}</span></td>
                </tr>
                <tr>
                <td class="five-a">${message("Order.phone")}:</td>
                <td colspan="2" class="five-b"><span>${order.phone}</span></td>
                </tr>
            </table>
			[#if order.shippings?has_content]
					<table class="info">
						[#list order.shippings as shipping]
							<tr>
								<th>
									${message("Shipping.deliveryCorp")}:
								</th>
								<td>
									[#if shipping.deliveryCorpUrl??]
										<a href="${shipping.deliveryCorpUrl}" target="_blank">${shipping.deliveryCorp}</a>
									[#else]
										${(shipping.deliveryCorp)!"-"}
									[/#if]
								</td>
								<th>
									${message("Shipping.trackingNo")}:
								</th>
								<td>
									${(shipping.trackingNo)!"-"}
									[#if setting.kuaidi100Key?has_content && shipping.deliveryCorpCode?? && shipping.trackingNo??]
										<a href="javascript:;" class="deliveryQuery" sn="${shipping.sn}">[${message("shop.member.order.deliveryQuery")}]</a>
									[/#if]
								</td>
								<th>
									${message("shop.member.order.deliveryDate")}:
								</th>
								<td>
									${shipping.createDate?string("yyyy-MM-dd HH:mm")}
								</td>
							</tr>
						[/#list]
					</table>
				[/#if]
            <div>
            [#list order.orderItems as orderItem]
                <div class="Car-big-A">
                	<a href="${base}/mobile${orderItem.product.path}" title="${orderItem.fullName}">
                    <div class="Car-big-a">
						<img src="[#if orderItem.thumbnail??]${orderItem.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" class="thumbnail" alt="${orderItem.fullName}" />
                    </div>
                    <div class="Car-big-b">
                        <ul>
                            <li class="Car-big-b-a">
							[#if orderItem.product??]
								${abbreviate(orderItem.fullName, 30)}
							[#else]
								<span title="${orderItem.fullName}">${abbreviate(orderItem.fullName, 30)}</span>
							[/#if]
							[#if orderItem.isGift]
								<span class="red">[${message("shop.member.order.gift")}]</span>
							[/#if]
                            </li>
                            <li class="Car-big-b-b">
                                <span>
								[#if !orderItem.isGift]
									${currency(orderItem.price, true)}
								[#else]
									-
								[/#if]
                                </span>
                                <span>数量：${orderItem.quantity}</span>
                                
                             </li>
                             <li class="Car-big-b-b"><span>小计：
								[#if !orderItem.isGift]
									${currency(orderItem.subtotal, true)}
								[#else]
									-
								[/#if]
                             </span></li>
                             [#if order.store ??]
		                    	<li><span>店铺：${order.store.name}</span></li>
		                    [#else]
		                     	<li><span>店铺：商城自营</span></li>
		                    [/#if]
                        </ul>               
                    </div>
                    </a>
                </div>
                [/#list]
            </div>
        </div>
        [#if !order.expired && (order.orderStatus == "unconfirmed" || order.orderStatus == "confirmed") && (order.paymentStatus == "unpaid" || order.paymentStatus == "partialPayment")]
        	[#if order.paymentMethod.method == "online"]
				<div class="foot">
					<div class="button" id="payment">
					立即支付
					<div>
				</div>
			[#else]
				
			[/#if]
		[/#if]
     </div>
</div>
</body>
</html>