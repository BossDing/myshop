<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<title>订单支付</title>
<link href="${base}/resources/mobile/css/payment.css" rel="stylesheet" type="text/css" />
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
		var $dialogOverlay = $("#dialogOverlay");
		var $dialog = $("#dialog");
		var $other = $("#other");
		var $amountPayable = $("#amountPayable");
		var $fee = $("#fee");
		var $paymentForm = $("#paymentForm");
		var $paymentPluginId = $("#paymentPlugin :radio");
		var $paymentButton = $("#paymentButton");
		var $payment = $("#payment");
		
		[#if order.paymentMethod.method == "online" && (order.paymentStatus == "unpaid" || order.paymentStatus == "partialPayment")]
			// 订单锁定
			setInterval(function() {
				$.ajax({
					url: "lock.jhtml",
					type: "POST",
					data: {sn: "${order.sn}"},
					dataType: "json",
					cache: false,
					success: function(data) {
						if (!data) {
							location.href = "view.jhtml?sn=${order.sn}";
						}
					}
				});
			}, 10000);
	
			// 检查支付
			setInterval(function() {
				$.ajax({
					url: "check_payment.jhtml",
					type: "POST",
					data: {sn: "${order.sn}"},
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data) {
							location.href = "view.jhtml?sn=${order.sn}";
						}
					}
				});
			}, 10000);
		[/#if]
		
		// 选择其它支付方式
		$other.click(function() {
			$dialogOverlay.hide();
			$dialog.hide();
		});
		
		// 支付插件
		$paymentPluginId.click(function() {
			$.ajax({
				url: "calculate_amount.jhtml",
				type: "POST",
				data: {paymentPluginId: $(this).val(), sn: "${order.sn}"},
				dataType: "json",
				cache: false,
				success: function(data) {
					if (data.message.type == "success") {
						$amountPayable.text(currency(data.amount, true, true));
						if (data.fee > 0) {
							$fee.text(currency(data.fee, true)).parent().show();
						} else {
							$fee.parent().hide();
						}
					} else {
						$.message(data.message);
						setTimeout(function() {
							location.reload(true);
						}, 3000);
					}
				}
			});
		});
		
		// 支付
		$paymentForm.submit(function() {
			$dialogOverlay.show();
			$dialog.show();
		});
		
		$payment.click(function(){
			document.location.href = "${base}/mobile/payment/toSubmit.jhtml?sn=${order.sn}";
		});
	
	});
</script>
</head>
<body>
	[#include "/mobile/include/header.ftl" /]
		<div class="big_box">
			<div class="information">
				<dl>
					<dt>
						[#if order.paymentStatus == "paid"]
							<div class="title">${message("shop.order.waitingShipment")}</div>
						[#else]
							[#if order.paymentMethod.method == "online"]
								<div class="title">${message("shop.order.waitingPayment")}</div>
							[#else]
								<div class="title">${message("shop.order.waitingProcess")}</div>
							[/#if]
						[/#if]
					</dt>
					<dd>
						<span>订单编号：</span> <span>${order.sn}&nbsp;&nbsp;<a href="view.jhtml?sn=${order.sn}">[查看订单]</a></span>
					</dd>
					<dd>
						<span>收货人称：</span> <span>${order.consignee}</span>
					</dd>
					<dd>
						<span>手机号码：</span> <span>${order.phone}</span>
					</dd>
					<dd>
						<span>收货地址：</span> <span>${order.areaName}${order.address}</span>
					</dd>
					<dd>
						<span>配送方式：</span> <span>${order.shippingMethodName}</span>
					</dd>
					<dd>
						<span>支付方式：</span> <span>${order.paymentMethodName}</span>
					</dd>
					<dd>
						<span class="zonge">
						[#if order.paymentStatus == "unpaid" || order.paymentStatus == "partialPayment"]
							${message("shop.order.amountPayable")}：
								[#if amount??]
									<strong id="amountPayable">${currency(amount, true, true)}</strong>
								[#else]
									<strong id="amountPayable">${currency(order.amountPayable, true, true)}</strong>
								[/#if]
								[#if fee??]
									<span[#if fee == 0] class="hidden"[/#if]>(${message("shop.order.fee")}: <span id="fee">${currency(fee, true)}</span>)</span>
								[#else]
									<span[#if order.fee == 0] class="hidden"[/#if]>(${message("shop.order.fee")}: <span id="fee">${currency(order.fee, true)}</span>)</span>
								[/#if]
						[#else]
							${message("shop.order.amount")}:
								<strong id="amountPayable">${currency(order.amount, true, true)}</strong>
								[#if order.fee > 0]
									(${message("shop.order.fee")}: <strong id="amountPayable">${currency(order.fee, true)})</span>
								[/#if]
						[/#if]
						</span>
					</dd>
					<dd>
						[#if order.expire??]
							<tr>
								<td colspan="4">${message("shop.order.expireTips", order.expire?string("yyyy-MM-dd HH:mm"))}</td>
							</tr>
						[/#if]
					</dd>
					<dd></dd>
				</dl>
			</div>
			[#if order.paymentStatus != "paid"]
				[#if order.paymentMethod.method == "online"]
					<div class="foot">
						<select class="xuanze">
							<option>支付宝支付</option>
						</select> <input class="button" type="button" id="payment" value="立即支付">
					</div>
				[#else]
					
				[/#if]
			[/#if]
		</div>
</body>
</html>