<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
		<title>订单信息</title>
		<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
		<link href="${base}/resources/mobile/css/order.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.lSelect.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.validate.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
		<script type="text/javascript">
			$().ready(function() {
				//判断当前是否存在用户
				if (!$.checkLogin()) {
					$.redirectLogin(window.location.href, "${message("shop.common.mustLogin")}");
					return false;
				}
				var $orderForm = $("#orderForm");
				var $submit = $("#submit");
				var $paymentMethod = $("select[name=paymentMethod]");
				var $shippingMethod = $("select[name=shippingMethod]");
				var $useBalance = $("#useBalance");
				var $isInvoice = $("#isInvoice");
				var $invoiceTitleTr = $("#invoiceTitleTr");
				var $freight = $("#freight");
				var $promotionDiscount = $("#promotionDiscount");
				var $couponDiscount = $("#couponDiscount");
				var $tax = $("#tax");
				var $amountPayable = $("#amountPayable");
				var $paymentMethodId = $("#paymentMethodId");
		
				//支付配送匹配
				$paymentMethodId.change(function(){
					var tt= $paymentMethodId.find("option:selected").text();
					var kk = document.getElementById("shippingMethodId").options;
					if (tt=="货到付款") {
						for(var i=0; i< kk.length;i++){
							if(kk[i].text=="顺丰速递"){
								kk[i].selected=true;
								break;  
							}
						}
					}
					if (tt=="网上支付") {
						for(var j=0; j< kk.length; j++){
							if(kk[j].text=="普通快递"){
								kk[j].selected=true;
								break;  
							}
						}
					}
				});
				
				
				// 订单提交
				$submit.click(function() {
					console.info($orderForm.serialize());
					$.ajax({
						url: "${base}/mobile/member/order/create.jhtml",
						type: "POST",
						data: $orderForm.serialize(),
						dataType: "json",
						cache: false,
						beforeSend: function() {
							$submit.prop("disabled", true);
						},
						success: function(message) {
							if (message.type == "success") {
								location.href = "${base}/mobile/member/order/payment.jhtml?sn=" + message.content;
							} else {
								$.message(message);
								setTimeout(function() {
									location.reload(true);
								}, 3000);
							}
						},
						complete: function() {
							$submit.prop("disabled", false);
						}
					});
				});
				
				// 开据发票
				$isInvoice.click(function() {
					$invoiceTitleTr.toggle();
					calculate();
				});
			
				// 使用余额
				$useBalance.click(function() {
					calculate();
				});
				
				// 计算
			function calculate() {
				$.ajax({
					url: "${base}/mobile/member/order/calculate.jhtml",
					type: "POST",
					data: $orderForm.serialize(),
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.message.type == "success") {
							$freight.text(currency(data.freight, true));
							$amountPayable.text(currency(data.amountPayable, true, true));
						} else {
							$.message(data.message);
							setTimeout(function() {
								location.reload(true);
							}, 3000);
						}
					}
				});
			}
				
			});
			
			function showHidden(){
				var dis=document.getElementById('NNNN');
				if(dis.style.display=='none'){
					document.getElementById('div-gsfp').innerHTML="发票抬头";
					document.getElementById("div-gsfp").setAttribute("class","AAAA");
					dis.style.display="";
				}else{
					document.getElementById('div-gsfp').innerHTML="发票抬头";
					document.getElementById("div-gsfp").setAttribute("class","BBBB");
					dis.style.display="none";
				}
			}
			
		</script>
	</head>
	<body>
		[#include "/mobile/include/header.ftl"]
        <!--头部导航end -->
		
		<form id="orderForm" method="post">
		<input type="hidden" name="cartToken" value="${cartToken}" />
		<div class="Big">
		<div class="dingdan-big">
			<div class="shouhuo">
				<div>
					<div class="shouhuo-a">收货信息</div>
					<a href="${base}/mobile/member/receiver/list.jhtml?istocart=1">
						<div class="shouhuo-b">
						[#list receivers as receiver]
							<ul>
								<input type="hidden" id="receiverId" name="receiverId" value="${receiver.id}" />
								<li class="shouhuo-b-a">${receiver.areaName}${receiver.address}</li>
								<li>${receiver.consignee} 收</li>
								<li>${receiver.phone}</li>
							</ul>
						[/#list]
						</div>
					</a>
				</div>
				<div class="zhifu">
					<div class="zhifu-a">支付方式</div>
					<div>
						<select name="paymentMethodId" class="zhifu-b" id="paymentMethodId">
							[#list paymentMethods as paymentMethod]
								<option value="${paymentMethod.id}">${paymentMethod.name}</option>
							[/#list]
						</select>
					</div>
				</div>
				<div class="peisong">
					<div class="zhifu-a">配送方式</div>
					<select name="shippingMethodId" class="zhifu-b" id="shippingMethodId">
							[#list shippingMethods as shippingMethod]
								<option value="${shippingMethod.id}">${shippingMethod.name}</option>
							[/#list]
					</select>
				</div>
				<!--
				[#if setting.isInvoiceEnabled]
					<div>
						<div class="shouhuo-a">${message("shop.order.invoiceInfo")}</div>
							<div class="shouhuo-bb">
								<div class="zhifu-bb">
									${message("shop.order.isInvoice")}:
									<input type="checkbox" id="isInvoice" name="isInvoice" value="true" />
									[#if setting.isTaxPriceEnabled](${message("shop.order.invoiceTax")} :  ${setting.taxRate * 100}%)[/#if]
									<div id="invoiceTitleTr" style="display:none;margin-top:10px;font-size:16px;">
										${message("shop.order.invoiceTitle")} : <input type="text" id="invoiceTitle" name="invoiceTitle" class="text"[#if defaultReceiver??] value="${defaultReceiver.consignee}"[/#if] maxlength="200" />
									</div>
								</div>
							</div>
						</a>
					</div>
				[/#if]
				-->
				<div>
					<div class="shouhuo-a">${message("shop.order.amountPayable")}</div>
						<div class="shouhuo-bb">
							<div class="zhifu-bb">
								<div class="span12 last">
									<ul class="statistic">
										<li>
											<span>
												${message("shop.order.freight")}: <em id="freight">${currency(order.freight, true)}</em>
											</span>
											[#if setting.isInvoiceEnabled && setting.isTaxPriceEnabled]
												<span class="hidden">
													${message("shop.order.tax")}: <em id="tax">${currency(order.tax, true)}</em>
												</span>
											[/#if]
											<span>
												${message("shop.order.point")}: <em>${order.point}</em>
											</span>
										</li>
										<li>
											<span[#if order.promotionDiscount == 0] class="hidden"[/#if]>
												${message("shop.order.promotionDiscount")}: <em id="promotionDiscount">${currency(order.promotionDiscount, true)}</em>
											</span>
											<span[#if order.couponDiscount == 0] class="hidden"[/#if]>
												${message("shop.order.couponDiscount")}: <em id="couponDiscount">${currency(order.couponDiscount, true)}</em>
											</span>
										</li>
										<li>
											<span>
												${message("shop.order.amountPayable")}: <strong id="amountPayable">${currency(order.amountPayable, true, true)}</strong>
											</span>
										</li>
										[#if member.balance > 0]
											<li>
												<input type="checkbox" id="useBalance" name="useBalance" value="true" />
												<label for="useBalance">
													${message("shop.order.useBalance")} (${message("shop.order.balance")}: <em>${currency(member.balance, true)}</em>)
												</label>
											</li>
										[/#if]
									</ul>
								</div>
							</div>
						</div>
					</a>
				</div>
				<div class="Big-denglu">
					<div class="denglu-center" id="submit">提交订单</div>
				</div>
			</div>
		</div>
		</form>
	</body>
</html>