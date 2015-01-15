<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
		<title>购物车</title>
		<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
		<link href="${base}/resources/mobile/css/Car.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
		<script type="text/javascript">
		$().ready(function() {
		
			var $quantity = $("input[name='quantity']");
			var $increase = $("a[name='increase']");
			var $decrease = $("a[name='decrease']");
			var $delete = $("a.delete");
			var $giftItems = $("#giftItems");
			var $promotion = $("#promotion");
			var $effectivePoint = $("#effectivePoint");
			var $effectivePrice = $("#effectivePrice");
			var $clear = $("#clear");
			var $submit = $("#submit");
			var timeouts = {};
			
			// 初始数量
			$quantity.each(function() {
				var $this = $(this);
				$this.data("value", $this.val());
			});
			
			// 数量
			$quantity.keypress(function(event) {
				var key = event.keyCode ? event.keyCode : event.which;
				if ((key >= 48 && key <= 57) || key==8) {
					return true;
				} else {
					return false;
				}
			});
			
			// 增加数量
			$increase.click(function() {
				var $quantity = $(this).parent().find("input");
				var quantity = $quantity.val();
				if (/^\d*[1-9]\d*$/.test(quantity)) {
					$quantity.val(parseInt(quantity) + 1);
				} else {
					$quantity.val(1);
				}
				edit($quantity);
			});
			
			// 减少数量
			$decrease.click(function() {
				var $quantity = $(this).parent().find("input");
				var quantity = $quantity.val();
				if (/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 1) {
					$quantity.val(parseInt(quantity) - 1);
				} else {
					$quantity.val(1);
				}
				edit($quantity);
			});
			
			// 编辑数量
			$quantity.bind("input propertychange change", function(event) {
				if (event.type != "propertychange" || event.originalEvent.propertyName == "value") {
					edit($(this));
				}
			});
			
			// 编辑数量
			function edit($quantity) {
				var quantity = $quantity.val();
				if (/^\d*[1-9]\d*$/.test(quantity)) {
					var $tr = $quantity.closest("tr");
					var id = $tr.find("input[name='id']").val();
					var test = $tr.parent().find("span.subtotal");
					clearTimeout(timeouts[id]);
					timeouts[id] = setTimeout(function() {
						$.ajax({
							url: "${base}/mobile/cart/edit.jhtml",
							type: "POST",
							data: {id: id, quantity: quantity},
							dataType: "json",
							cache: false,
							beforeSend: function() {
								$submit.prop("disabled", true);
							},
							success: function(data) {
								if (data.message.type == "success") {
									$quantity.data("value", quantity);
									$tr.parent().find("span.subtotal").text(currency(data.subtotal, true));
									$effectivePoint.text(data.effectivePoint);
									$effectivePrice.text(currency(data.effectivePrice, true, true));
								}else if (data.message.type == "warn") {
									$.message(data.message);
									$quantity.val($quantity.data("value"));
								} else if (data.message.type == "error") {
									$quantity.val($quantity.data("value"));
									setTimeout(function() {
										location.reload(true);
									}, 3000);
								}
							},
							complete: function() {
								$submit.prop("disabled", false);
							}
						});
					}, 500);
				} else {
					$quantity.val($quantity.data("value"));
				}
			}
		
			// 删除
			$delete.click(function() {
				if (confirm("${message("shop.dialog.deleteConfirm")}")) {
					var $this = $(this);
					var $div = $this.closest("div");
					var id = $div.parent().find("input[name='id']").val();
					clearTimeout(timeouts[id]);
					$.ajax({
						url: "${base}/mobile/cart/delete.jhtml",
						type: "POST",
						data: {id: id},
						dataType: "json",
						cache: false,
						beforeSend: function() {
							$submit.prop("disabled", true);
						},
						success: function(data) {
							if (data.message.type == "success") {
								if (data.quantity > 0) {
									$div.parent().remove();
									$effectivePoint.text(data.effectivePoint);
									$effectivePrice.text(currency(data.effectivePrice, true, true));
								} else {
									location.reload(true);
								}
							} else {
								$.message(data.message);
								setTimeout(function() {
									location.reload(true);
								}, 3000);
							}
						},
						complete: function() {
							$submit.prop("disabled", false);
						}
					});
				}
				return false;
			});
			
			// 清空
			$clear.click(function() {
				if (confirm("${message("shop.dialog.clearConfirm")}")) {
					$.each(timeouts, function(i, timeout) {
						clearTimeout(timeout);
					});
					$.ajax({
						url: "${base}/mobile/cart/clear.jhtml",
						type: "POST",
						dataType: "json",
						cache: false,
						success: function(data) {
							location.reload(true);
						}
					});
				}
				return false;
			});
			
			// 提交
			$submit.click(function() {
				if (!$.checkLogin()) {
					$.redirectLogin(window.location.href, "${message("shop.cart.accessDenied")}");
					return false;
				}
			});
			
		});
		</script>
	</head>
	<body>
		[#include "/mobile/include/header.ftl"]
		<!--头部导航end-->
		[#if cart?? && cart.cartItems?has_content]
		<div class="Big">
			<div class="Car-big">
				[#list cart.cartItems as cartItem]
				<div class="Car-big-A">
					<div class="Car-big-a">
						<a href="${base}/mobile${cartItem.product.path}" title="${cartItem.product.fullName}">
						<img src="[#if cartItem.product.thumbnail??]${cartItem.product.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" 
						alt="${cartItem.product.name}" width="100%" /></a>
					</div>
					<div class="Car-big-b">
						<table border="0" cellspacing="0" cellpadding="0">
				           <tr class="Car-big-b-a">
				             <td style="font-size:12px;">${abbreviate(cartItem.product.fullName, 60, "...")}</td>
				             </tr>
				              <tr class="Car-big-b-b">
				             <td><span>单价：${currency(cartItem.price, true)}</span></td>
				             </tr>
				              <tr class="Car-big-b-b">
								<input type="hidden" name="id" value="${cartItem.id}" />
				            	 <td><span style="float:left;">数量：</span>
				             		<span>
						             	<a class="jia" href="javascript:void(0);" name="decrease">－</a>
						             	<input type="text" name="quantity" class="num" value="${cartItem.quantity}">
						             	<a class="jia" href="javascript:void(0);" name="increase">＋</a>
					             	</span>
				             </td>
				             </tr>
				             <tr class="Car-big-b-b">
				            	<td><span>小计：<span class="subtotal">${currency(cartItem.subtotal, true)}</span></span></td>
				             </tr>
            				 <tr class="Car-big-b-b">
            					[#if cartItem.product.store.name ??]
				            		<td><span>店铺：<span>${cartItem.product.store.name}</span></span></td>
            					[#else]
            						<td><span><span>商城自营</span></span></td>
            					[/#if]
				             </tr>
				           </table>
					</div>
					<div class="del">
						<a href="javascript:void(0);" class="delete">X</a>
					</div>
				</div>
				[/#list]
			</div>
			<div class="heji">
				<div class="heji_centent">
					合计金额：<span id="effectivePrice">${currency(cart.effectivePrice, true, true)}</span>
					<a href="${base}/mobile/member/order/info.jhtml?id=0" id="submit" class="submit"><input class="jiesuan" type="button" value="去结算"></a>
				</div>
			</div>
		</div>
		[#else]
			<center>
				<a href="${base}/mobile/index.jhtml">
					<span class="emptycar"></span></br>
					<span>您的购物车为空</span></br>
					<input type="button" class="emptycar_button" value="${message("shop.cart.empty")}" />
				</a>
			</center>	
		[/#if]
	</body>
</html>