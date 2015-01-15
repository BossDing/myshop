<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.order.payment")}[#if systemShowPowered][/#if]</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<link href="${base}/resources/shop/css/order.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_check_order.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $dialogOverlay = $("#dialogOverlay");
	var $dialog = $("#dialog");
	var $other = $("#other");
	var $amountPayable = $("#amountPayable");
	var $fee = $("#fee");
	var $paymentForm = $("#paymentForm");
	var $paymentPluginId = $("#paymentPlugin :radio");
	var $paymentButton = $("#paymentButton");
	
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
	    var type= $("#orty").val();
	    if(type!=null && type=="1"){
	      var dopay = checkPay();
		  if(!dopay) {
		  return;
		  }
	    }
		$dialogOverlay.show();
		$dialog.show();
	});	
	

});
/**检查积分订单 是否积分不足*/
function checkPay(){
	    var curpt = ${order.member.point};
		var paypt = ${order.lowPoints};
		if(paypt!=null && paypt>0){
		    if(curpt==null||curpt < paypt) {
			 alert("您的积分不足以支付该订单");
			 flag = false;
             window.location.href="${base}/member/index.jhtml";
			 return false;
			 }
		}
		return true;
	}
	
/**提交完全积分兑换支付*/	
function payptMethod(){
        var dopay = checkPay();
		if(!dopay) {
		  return;
		  }
		$.ajax({
			url: "${base}/member/order/submit.jhtml",
			type: "POST",
			data: {sn: "${order.sn}"},
			dataType: "json",
			cache: false,
			success: function(data) {
				if (data.type == "success") {
					alert("支付成功！");
					location.href = "${base}/member/index.jhtml";
				}else{
				    alert("支付失败！");
				}
			},
			complete: function() {
				$("#paypt").prop("disabled", false);
			}
		});
	}
</script>
</head>
<body>
	<div id="dialogOverlay" class="dialogOverlay"></div>
	[#include "/shop/include/header.ftl" /]
	<!--页面跟踪导航-->
	<div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<span>首页</span>><span>个人中心</span>><span>购物车</span></div>
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
    <!--页面跟踪导航结束-->

	<div class="content">


<!--左边导航-->

   [#include "/shop/member/include/navigation.ftl" /]
     <form id="orderForm" action="create.jhtml" method="post">
					<input type="hidden" id="receiverId" name="receiverId"[#if defaultReceiver??] value="${defaultReceiver.id}"[/#if] />
					<input type="hidden" name="cartToken" value="${cartToken}" />
   <div class="indiv_content">
   <div class="indiv_car">
      
        <div id="car_nav">
           <ul>
               <li><a href="/cart/list.jhtml">我的购物车</a></li>
               <li><a href="#">填写并核对订单信息</a></li>
               <li  id="this" ><a href="#">核对支付信息</a></li>
               <li><a href="#">支付结果信息</a></li>
           </ul>
        </div>


	<div class="container order">
		<div id="dialog" class="dialog">
			<dl>
				${message("shop.order.paymentDialog")}
			</dl>
			<div>
				<a href="view.jhtml?sn=${order.sn}">${message("shop.order.paid")}</a>
				<a href="${base}/">${message("shop.order.trouble")}</a>
			</div>
			<a href="javascript:;" id="other">${message("shop.order.otherPaymentMethod")}</a>
		</div>
		<div class="span24">
			
			<div class="result">
				[#if order.paymentStatus == "paid"]
					<div class="title">${message("shop.order.waitingShipment")}</div>
				[#else]
					[#if order.paymentMethod.method == "online"]
						<div class="title">${message("shop.order.waitingPayment")}</div>
					[#else]
						<div class="title">${message("shop.order.waitingProcess")}</div>
					[/#if]
				[/#if]
				<table>
					<tr>
						<th colspan="4">${message("shop.order.info")}:</th>
					</tr>
					<tr>
						<td width="100">${message("shop.order.sn")}:</td>
						<td width="340">
							<strong>${order.sn}</strong>
							<a href="view.jhtml?sn=${order.sn}">[${message("shop.order.view")}]</a>
						</td>
						[#if order.paymentStatus == "unpaid" || order.paymentStatus == "partialPayment"]
							<td width="100">${message("shop.order.amountPayable")}:</td>
							<td>
							<!--积分-->
							[#if order.type?? && order.type=="1"]
							  [#if order.lowPrice?? && order.lowPrice>0 ]
								[#if amount??]
									<strong>${currency(order.amount, true, true)}</strong>  &nbsp;  <strong>${order.lowPoints}</strong>积分
								[#else]
									<strong id="amountPayable">${currency(order.amountPayable, true, true)}</strong>&nbsp;  <strong>${order.lowPoints}</strong> 积分
								[/#if]
							  [#else]
							        <strong>${order.lowPoints}</strong> 积分
							  [/#if]
								[#if fee??]
									<span[#if fee == 0] class="hidden"[/#if]>(${message("shop.order.fee")}: <span id="fee">${currency(fee, true)}</span>)</span>
								[#else]
									<span[#if order.fee == 0] class="hidden"[/#if]>(${message("shop.order.fee")}: <span id="fee">${currency(order.fee, true)}</span>)</span>
								[/#if]
							 [#else]
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
							[/#if]	
							</td>
						[#else]
							<td width="100">${message("shop.order.amount")}:</td>
							<td>
							   [#if order.type?? && order.type=="1"]
							   [#if order.lowPrice?? && order.lowPrice>0]
							       <strong>${currency(order.amount, true, true)}</strong>  &nbsp;  <strong>${order.lowPoints}</strong>积分
								  [#else]
								   <strong>${order.lowPoints}</strong>
							   [/#if]
							   [#else]
								<strong>${currency(order.amount, true, true)}</strong>
								[#if order.fee > 0]
									(${message("shop.order.fee")}: ${currency(order.fee, true)})
								[/#if]
							 [/#if]
							</td>
						[/#if]
					</tr>
					<tr>
						<td>${message("shop.order.shippingMethod")}:</td>
						<td>${order.shippingMethodName}</td>
						<td>${message("shop.order.paymentMethod")}:</td>
						<td>${order.paymentMethodName}</td>
					</tr>
					[#if order.expire??]
						<tr>
							<td colspan="4">${message("shop.order.expireTips", order.expire?string("yyyy-MM-dd HH:mm"))}</td>
						</tr>
					[/#if]
				</table>
				 <input type="hidden" id="orty" value="${order.type}" />
				[#if order.paymentStatus != "paid"]
				 [#if order.type?? &&order.type=="1" && (!(order.lowPrice??)||order.lowPrice==0) ]
				     <form target="_blank">
					   <input type="hidden" name="sn" value="${order.sn}" />
					   <input type="hidden" name="type" value="paymentpt" />
					   <span style="display:block;">你的可用积分:<strong> ${order.member.point} <strong></span>   <br>
				       <input type="button" onclick="payptMethod()" class="paymentButton" id="paypt" value="立即支付"/>
					 </form>
				  [#else]
					[#if order.paymentMethod.method == "online"]
						[#if paymentPlugins??]
							<form id="paymentForm" action="${base}/payment/submit.jhtml" method="post" target="_blank">
								<input type="hidden" name="type" value="payment" />
								<input type="hidden" name="sn" value="${order.sn}" />
								<table id="paymentPlugin" class="paymentPlugin">
									<tr>
										<th colspan="4">${message("shop.order.paymentMethod")}:</th>
									</tr>
									[#list paymentPlugins?chunk(4, "") as row]
										<tr>
											[#list row as paymentPlugin]
												[#if paymentPlugin?has_content]
													<td>
														<input type="radio" id="${paymentPlugin.id}" name="paymentPluginId" value="${paymentPlugin.id}"[#if paymentPlugin == defaultPaymentPlugin] checked="checked"[/#if] />
														<label for="${paymentPlugin.id}">
															[#if paymentPlugin.logo??]
																<em title="${paymentPlugin.paymentName}" style="background-image: url(${paymentPlugin.logo});">&nbsp;</em>
															[#else]
																<em>${paymentPlugin.paymentName}</em>
															[/#if]
														</label>
													</td>
												[#else]
													<td>
														&nbsp;
													</td>
												[/#if]
											[/#list]
										</tr>
									[/#list]
								</table>
								<input type="submit" id="paymentButton" class="paymentButton" value="${message("shop.order.payNow")}" />
							</form>
						[/#if]
					[#else]
						${order.paymentMethod.content}
					[/#if]
				[/#if]
			 [/#if]
			</div>
		</div>
	</div>
	</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>