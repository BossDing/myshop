<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.order.info")}[#if systemShowPowered][/#if]</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<link href="${base}/resources/shop/css/order.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_check_order.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $dialogOverlay = $("#dialogOverlay");
	var $receiverForm = $("#receiverForm");
	var $receiver = $("#receiver ul");
	var $otherReceiverButton = $("#otherReceiverButton");
	var $newReceiverButton = $("#newReceiverButton");
	var $newReceiver = $("#newReceiver");
	var $areaId = $("#areaId");
	var $newReceiverSubmit = $("#newReceiverSubmit");
	var $newReceiverCancelButton = $("#newReceiverCancelButton");
	var $orderForm = $("#orderForm");
	var $receiverId = $("#receiverId");
	var $paymentMethodId = $("#paymentMethod :radio");
	var $shippingMethodId = $("#shippingMethod :radio");
	var $isInvoice = $("#isInvoice");
	var $invoiceTitleTr = $("#invoiceTitleTr");
	var $invoiceTitle = $("#invoiceTitle");
	var $code = $("#code");
	var $couponCode = $("#couponCode");
	var $couponName = $("#couponName");
	var $couponButton = $("#couponButton");
	var $useBalance = $("#useBalance");
	var $freight = $("#freight");
	var $promotionDiscount = $("#promotionDiscount");
	var $couponDiscount = $("#couponDiscount");
	var $tax = $("#tax");
	var $amountPayable = $("#amountPayable");
	var $submit = $("#submit");
	var shippingMethodIds = {};
	
	[@compress single_line = true]
		[#list paymentMethods as paymentMethod]
			shippingMethodIds["${paymentMethod.id}"] = [
				[#list paymentMethod.shippingMethods as shippingMethod]
					"${shippingMethod.id}"[#if shippingMethod_has_next],[/#if]
				[/#list]
			];
		[/#list]
	[/@compress]
	
	[#if !member.receivers?has_content]
		$dialogOverlay.show();
	[/#if]
	
	// 地区选择
	$areaId.lSelect({
		url: "${base}/common/area.jhtml"
	});
	
	// 收货地址
	$("#receiver li").live("click", function() {
		var $this = $(this);
		$receiverId.val($this.attr("receiverId"));
		$("#receiver li").removeClass("selected");
		$this.addClass("selected");
		[#if setting.isInvoiceEnabled]
			if ($.trim($invoiceTitle.val()) == "") {
				$invoiceTitle.val($this.find("strong").text());
			}
		[/#if]
	});
	
	// 其它收货地址
	$otherReceiverButton.click(function() {
		$otherReceiverButton.hide();
		$newReceiverButton.show();
		$("#receiver li").show();
	});
	
	// 新收货地址
	$newReceiverButton.click(function() {
		$dialogOverlay.show();
		$newReceiver.show();
	});
	
	// 新收货地址取消
	$newReceiverCancelButton.click(function() {
		if ($receiverId.val() == "") {
			$.message("warn", "${message("shop.order.receiverRequired")}");
			return false;
		}
		$dialogOverlay.hide();
		$newReceiver.hide();
	});
	
	// 计算
	function calculate() {
		$.ajax({
			url: "calculate.jhtml",
			type: "POST",
			data: $orderForm.serialize(),
			dataType: "json",
			cache: false,
			success: function(data) {
				if (data.message.type == "success") {
					$freight.text(currency(data.freight, true));
					if (data.promotionDiscount > 0) {
						$promotionDiscount.text(currency(data.promotionDiscount, true));
						$promotionDiscount.parent().show();
					} else {
						$promotionDiscount.parent().hide();
					}
					if (data.couponDiscount > 0) {
						$couponDiscount.text(currency(data.couponDiscount, true));
						$couponDiscount.parent().show();
					} else {
						$couponDiscount.parent().hide();
					}
					if (data.tax > 0) {
						$tax.text(currency(data.tax, true));
						$tax.parent().show();
					} else {
						$tax.parent().hide();
					}
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
	
	// 支付方式
	$paymentMethodId.click(function() {
		var $this = $(this);
		if ($this.prop("disabled")) {
			return false;
		}
		$this.closest("dd").addClass("selected").siblings().removeClass("selected");
		var paymentMethodId = $this.val();
		$shippingMethodId.each(function() {
			var $this = $(this);
			if ($.inArray($this.val(), shippingMethodIds[paymentMethodId]) >= 0) {
				$this.prop("disabled", false);
			} else {
				$this.prop("disabled", true).prop("checked", false).closest("dd").removeClass("selected");
			}
		});
		calculate();
	});
	
	// 配送方式
	$shippingMethodId.click(function() {
		var $this = $(this);
		if ($this.prop("disabled")) {
			return false;
		}
		$this.closest("dd").addClass("selected").siblings().removeClass("selected");
		calculate();
	});
	
	// 开据发票
	$isInvoice.click(function() {
		$invoiceTitleTr.toggle();
		calculate();
	});
	
	// 优惠券
	$couponButton.click(function() {
		if ($code.val() == "") {
			if ($.trim($couponCode.val()) == "") {
				return false;
			}
			$.ajax({
				url: "coupon_info.jhtml",
				type: "POST",
				data: {code : $couponCode.val()},
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$couponButton.prop("disabled", true);
				},
				success: function(data) {
					if (data.message.type == "success") {
						$code.val($couponCode.val());
						$couponCode.hide();
						$couponName.text(data.couponName).show();
						$couponButton.text("${message("shop.order.codeCancel")}");
						calculate();
					} else {
						$.message(data.message);
					}
				},
				complete: function() {
					$couponButton.prop("disabled", false);
				}
			});
		} else {
			$code.val("");
			$couponCode.show();
			$couponName.hide();
			$couponButton.text("${message("shop.order.codeConfirm")}");
			calculate();
		}
	});
	
	// 使用余额
	$useBalance.click(function() {
		calculate();
	});
	
	// 订单提交
	$submit.click(function() {
		var $checkedPaymentMethodId = $paymentMethodId.filter(":checked");
		var $checkedShippingMethodId = $shippingMethodId.filter(":checked");
		if ($checkedPaymentMethodId.size() == 0) {
			$.message("warn", "${message("shop.order.paymentMethodRequired")}");
			return false;
		}
		if ($checkedShippingMethodId.size() == 0) {
			$.message("warn", "${message("shop.order.shippingMethodRequired")}");
			return false;
		}
		[#if setting.isInvoiceEnabled]
			if ($isInvoice.prop("checked") && $.trim($invoiceTitle.val()) == "") {
				$.message("warn", "${message("shop.order.invoiceTileRequired")}");
				return false;
			}
		[/#if]
		$.ajax({
			url: "create.jhtml",
			type: "POST",
			data: $orderForm.serialize(),
			dataType: "json",
			cache: false,
			beforeSend: function() {
				$submit.prop("disabled", true);
			},
			success: function(message) {
				if (message.type == "success") {
					location.href = "payment.jhtml?sn=" + message.content;
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
	
	// 表单验证
	$receiverForm.validate({
		rules: {
			consignee: "required",
			areaId: "required",
			address: "required",
			zipCode: "required",
			phone: "required"
		},
		submitHandler: function(form) {
			$.ajax({
				url: "${base}/member/order/save_receiver.jhtml",
				type: "POST",
				data: $receiverForm.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$newReceiverSubmit.prop("disabled", true);
				},
				success: function(data) {
					if (data.message.type == "success") {
						$receiverId.val(data.receiver.id);
						$("#receiver li").removeClass("selected");
						$receiver.append('<li class="selected" receiverId="' + data.receiver.id + '"><div><strong>' + data.receiver.consignee + '<\/strong> ${message("shop.order.receive")}<\/div><div><span>' + data.receiver.areaName + data.receiver.address + '<\/span><\/div><div>' + data.receiver.phone + '<\/div><\/li>');
						$dialogOverlay.hide();
						$newReceiver.hide();
						[#if setting.isInvoiceEnabled]
							if ($.trim($invoiceTitle.val()) == "") {
								$invoiceTitle.val(data.receiver.consignee);
							}
						[/#if]
					} else {
						$.message(data.message);
					}
				},
				complete: function() {
					$newReceiverSubmit.prop("disabled", false);
				}
			});
		}
	});
	
});
</script>
</head>
<body>
	<div id="dialogOverlay" class="dialogOverlay"></div>
	[#include "/shop/include/header.ftl" /]
	<!--页面跟踪导航-->
	<div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>><span><a href="/member/index.jhtml">个人中心</a></span>><span><a href="/cart/list.jhtml">购物车</a></span></div>
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
        
        
<!--我的订单-->
    
   
   <div class="indiv_content">
   <div class="indiv_car">
      
        <div id="car_nav">
           <ul>
               <li><a href="/cart/list.jhtml">我的购物车</a></li>
               <li id="this"><a href="#">填写并核对订单信息</a></li>
               <li><a href="#">核对支付信息</a></li>
               <li><a href="#">支付结果信息</a></li>
           </ul>
        </div>
         <div id="my_order">填写并核对订单信息</div>
           <table id="order_infor" border="0" cellspacing="0" cellpadding="0">
<thead>
<div class="order">
	<div class="info">
				<form id="receiverForm" action="save_receiver.jhtml" method="post">
					<div id="receiver" class="receiver clearfix">
						<div class="title">${message("shop.order.receiver")}</div>
						<ul>
							[#list member.receivers as receiver]
								<li[#if receiver_index == 0][#assign defaultReceiver = receiver /] class="selected"[#elseif receiver_index > 3] class="hidden"[/#if] receiverId="${receiver.id}">
									<div>
										<strong>${receiver.consignee}</strong> ${message("shop.order.receive")}
									</div>
									<div>
										<span>${receiver.areaName}${receiver.address}</span>
									</div>
									<div>
										${receiver.phone}
									</div>
								</li>
							[/#list]
						</ul>
						<p>
							[#if member.receivers?size > 4]
								<a href="javascript:;" id="otherReceiverButton" class="button">${message("shop.order.otherReceiver")}</a>
							[/#if]
							<a href="javascript:;" id="newReceiverButton" class="button"[#if member.receivers?size > 4] style="display: none;"[/#if]>${message("shop.order.newReceiver")}</a>
						</p>
					</div>
					<table id="newReceiver" class="newReceiver[#if member.receivers?has_content] hidden[/#if]">
						<tr>
							<th width="100">
								<span class="requiredField">*</span>${message("shop.order.consignee")}:
							</th>
							<td>
								<input type="text" id="consignee" name="consignee" class="text" maxlength="200" />
							</td>
						</tr>
						<tr>
							<th>
								<span class="requiredField">*</span>${message("shop.order.area")}:
							</th>
							<td>
								<span class="fieldSet">
									<input type="hidden" id="areaId" name="areaId" />
								</span>
							</td>
						</tr>
						<tr>
							<th>
								<span class="requiredField">*</span>${message("shop.order.address")}:
							</th>
							<td>
								<input type="text" id="address" name="address" class="text" maxlength="200" />
							</td>
						</tr>
						<tr>
							<th>
								<span class="requiredField">*</span>${message("shop.order.zipCode")}:
							</th>
							<td>
								<input type="text" id="zipCode" name="zipCode" class="text" maxlength="200" />
							</td>
						</tr>
						<tr>
							<th>
								<span class="requiredField">*</span>${message("shop.order.phone")}:
							</th>
							<td>
								<input type="text" id="phone" name="phone" class="text" maxlength="200" />
							</td>
						</tr>
						<tr>
							<th>
								${message("shop.order.isDefault")}:
							</th>
							<td>
								<input type="checkbox" name="isDefault" value="true" />
								<input type="hidden" name="_isDefault" value="false" />
							</td>
						</tr>
						<tr>
							<th>&nbsp;
								
							</th>
							<td>
								<input type="submit" id="newReceiverSubmit" class="button" value="${message("shop.order.ok")}" />
								<input type="button" id="newReceiverCancelButton" class="button" value="${message("shop.order.cancel")}" />
							</td>
						</tr>
					</table>
				</form>
				</div>
 <form id="orderForm" action="create.jhtml" method="post">
					<input type="hidden" id="receiverId" name="receiverId"[#if defaultReceiver??] value="${defaultReceiver.id}"[/#if] />
					<input type="hidden" name="cartToken" value="${cartToken}" />
					 <div class="container order"> 
					 <div class="span24"><div class="info">
					 <dl id="paymentMethod" class="select">
						<dt>${message("shop.order.paymentMethod")}</dt>
						[#list paymentMethods as paymentMethod]
							<dd>
								<label for="paymentMethod_${paymentMethod.id}">
									<input type="radio" id="paymentMethod_${paymentMethod.id}" name="paymentMethodId" value="${paymentMethod.id}" />
									<span>
										[#if paymentMethod.icon??]
											<em style="border-right: none; background: url(${paymentMethod.icon}) center no-repeat;">&nbsp;</em>
										[/#if]
										<strong>${paymentMethod.name}</strong>
									</span>
									<span>${abbreviate(paymentMethod.description, 80, "...")}</span>
								</label>
							</dd>
						[/#list]
					</dl>
					<dl id="shippingMethod" class="select">
						<dt>${message("shop.order.shippingMethod")}</dt>
						[#list shippingMethods as shippingMethod]
							<dd>
								<label for="shippingMethod_${shippingMethod.id}">
									<input type="radio" id="shippingMethod_${shippingMethod.id}" name="shippingMethodId" value="${shippingMethod.id}" />
									<span>
										[#if shippingMethod.icon??]
											<em style="border-right: none; background: url(${shippingMethod.icon}) center no-repeat;">&nbsp;</em>
										[/#if]
										<strong>${shippingMethod.name}</strong>
									</span>
									<span>${abbreviate(shippingMethod.description, 80, "...")}</span>
								</label>
							</dd>
						[/#list]
					</dl>
					[#if setting.isInvoiceEnabled]
						<table>
							<tr>
								<th colspan="2">${message("shop.order.invoiceInfo")}</th>
							</tr>
							<tr>
								<td width="100">    
									${message("shop.order.isInvoice")}:
								</td>
								<td>
									<label for="isInvoice">
										<input type="checkbox" id="isInvoice" name="isInvoice" value="true" />
										[#if setting.isTaxPriceEnabled](${message("shop.order.invoiceTax")}: ${setting.taxRate * 100}%)[/#if]
									</label>
								</td>
							</tr>
							<tr id="invoiceTitleTr" class="hidden">
								<td width="100">
									${message("shop.order.invoiceTitle")}:
								</td>
								<td>
									<input type="text" id="invoiceTitle" name="invoiceTitle" class="text"[#if defaultReceiver??] value="${defaultReceiver.consignee}"[/#if] maxlength="200" />
								</td>
							</tr>
						</table>
					[/#if]
					</div></div></div>
 <table id="order_infor" border="0" cellspacing="0" cellpadding="0">
 <thead>
  <tr id="titleone">
    <td class="goods">商品</td>
    <td class="price">价格</td>
    <td class="quantity">数量</td>
    <td class="operate">优惠</td>
    <td class="pay">小计</td>
   
  </tr>
 
  </thead>
  <tbody>

  [#list order.orderItems as orderItem]
	<tr>
		<td class="goods">
			 <div class="goods_pic"><img src="[#if orderItem.product.thumbnail??]${orderItem.product.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" alt="${orderItem.product.name}" /></div>
			  <div class="goods_name">${abbreviate(orderItem.product.fullName, 50, "...")}[#if orderItem.isGift]
				<span class="red">[${message("shop.order.gift")}]</span>
			[/#if]</div>
				  <div class="goods_num">
					货品编码：${orderItem.product.sn}</div>
				 	[#if orderItem.product.store??]
					 	<div class="goods_num">
							店铺：<a href="${orderItem.product.store.indexUrl}">${orderItem.product.store.name}</a>
						</div>
                     [#else]
	                    <div class="goods_num">
							店铺：商城自营
						</div>
					[/#if]
				  <div class="goods_color">
			</div>
		</td>
		<td class="price">
			[#if !orderItem.isGift]
				${currency(orderItem.price, true)}
			[#else]
				-
			[/#if]
		</td>
		<td>
			${orderItem.quantity}
		</td>
		 <td class="operate">
		     -￥00.00
		    </td>
		<td class="pay">
			[#if !orderItem.isGift]
				${currency(orderItem.subtotal, true)}
			[#else]
				-
			[/#if]
		</td>
	</tr>
[/#list]
  </tbody>
  <tfoot>
  <tr>
    
       <td colspan="3" >&nbsp;</a>
       </td>
    <td colspan="2" class="">
       <div class="amount">
           <dl>
           <dt>商品总价：</dt>
           <dd> <strong id="amountPayable">${currency(order.amountPayable, true, true)}</strong></dd>
            <dt>商品优惠总额：</dt>
           <dd>[#if order.promotionDiscount == 0] 0 [#else]<span>
									${message("shop.order.promotionDiscount")}: <em id="promotionDiscount">${currency(order.promotionDiscount, true)}</em>
								</span>[/#if] </dd>
            <dt>预计可获积分：</dt>
           <dd><em>${order.point}</em></dd>
            <dt class="zonge">总计(不含运费)：</dt>
           <dd class="zonge"> <strong id="amountPayable2">${currency(order.amountPayable, true, true)}</strong></dd>
           </dl>
        </div>
       </td>
  </tr>
  </tfoot>
</table>
     <div class="page">
     <div id="remarks">
         <div class="ll">订单备注:</div>
         <textarea class="text" >选填，可告诉卖家您的特殊要求</textarea>
         </div>
         <a href="javascript:;" id="submit" class="submit"><input class="page_next" type="button" value="提交订单" /></a>
         <a href="/cart/list.jhtml"><input class="page_last" type="button" value="返回购物车"  /></a>
     </div>
   </div>
   <div id="clear"></div>
   </div>
  </div>
  </form>
   [#include "/shop/member/include/love.ftl" /]
	[#include "/shop/include/footer.ftl" /]
</body>
</html>