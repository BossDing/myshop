<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.cart.title")}[#if systemShowPowered] - Powered By SHOP++[/#if]</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/wjl_indiv_car.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/wjl_indiv.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $quantity = $("input[name='quantity']");
	var $increase = $("input.increase2");
	var $decrease = $("input.decrease2");
	var $delete = $("a.delete");
	var $remove = $("a.remove");
	var $giftItems = $("#giftItems");
	var $promotion = $("#promotion");
	var $effectivePoint = $("#effectivePoint");
	var $effectivePrice = $("#effectivePrice");
	var $effectivePrice2 = $("#effectivePrice2");
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
		var $quantity = $($(this).siblings("input")[1]);
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
		var $quantity =$($(this).siblings("input")[0]);
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
			clearTimeout(timeouts[id]);
			timeouts[id] = setTimeout(function() {
				$.ajax({
					url: "edit.jhtml",
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
							$tr.find(".operate").text(currency(data.saveprice*data.qty, true));
							// hfh 2014/9/19 临时价格为团购价
							//if(data.tempPrice != null && data.tempPrice > 0){
								//$tr.find("span.subtotal").text(currency(data.tempPrice*data.qty, true));
							//}else{
								$tr.find("span.subtotal").text(currency(data.productEffectivePrice*data.qty, true));
							//}
							$tr.find("span.stateProvince").text(currency((data.price - data.subtotal)*data.qty, true));
							if (data.giftItems != null && data.giftItems.length > 0) {
								$giftItems.html('<dt>${message("shop.cart.gift")}:<\/dt>');
								$.each(data.giftItems, function(i, giftItem) { 
									$giftItems.append('<dd><a href="${base}' + giftItem.gift.path + '" title="' + giftItem.gift.fullName + '" target="_blank">' + giftItem.gift.fullName.substring(0, 50) + ' * ' + giftItem.quantity + '<\/a><\/dd>');
								});
								$giftItems.show();
							} else {
								$giftItems.hide();
							}
							if (data.promotions != null && data.promotions.length > 0) {
								var promotionName = "";
								$.each(data.promotions, function(i, promotion) { 
									promotionName += promotion.name + " ";
								});
								$promotion.text(promotionName);
							} else {
								$promotion.empty();
							}
							if (!data.isLowStock) {
								$tr.find("span.lowStock").remove();
							}
							$effectivePoint.text(data.effectivePoint);
							$effectivePrice.text(currency(data.effectivePrice, true, true));
							$effectivePrice2.text(currency(data.effectivePrice, true, true));
						} else if (data.message.type == "warn") {
							$.message(data.message);
							$quantity.val($quantity.data("value"));
						} else if (data.message.type == "error") {
							$.message(data.message);
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
			var $tr = $this.closest("tr");
			var id = $tr.find("input[name='id']").val();
			clearTimeout(timeouts[id]);
			$.ajax({
				url: "delete.jhtml",
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
							$tr.remove();
							if (data.giftItems != null && data.giftItems.length > 0) {
								$giftItems.html('<dt>${message("shop.cart.gift")}:<\/dt>');
								$.each(data.giftItems, function(i, giftItem) { 
									$giftItems.append('<dd><a href="${base}' + giftItem.gift.path + '" title="' + giftItem.gift.fullName + '" target="_blank">' + giftItem.gift.fullName.substring(0, 50) + ' * ' + giftItem.quantity + '<\/a><\/dd>');
								});
								$giftItems.show();
							} else {
								$giftItems.hide();
							}
							if (data.promotions != null && data.promotions.length > 0) {
								var promotionName = "";
								$.each(data.promotions, function(i, promotion) { 
									promotionName += promotion.name + " ";
								});
								$promotion.text(promotionName);
							} else {
								$promotion.empty();
							}
							$effectivePoint.text(data.effectivePoint);
							$effectivePrice.text(currency(data.effectivePrice, true, true));
							$effectivePrice2.text(currency(data.effectivePrice, true, true));
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
				url: "clear.jhtml",
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
			$.redirectLogin("${base}/cart/list.jhtml", "${message("shop.cart.accessDenied")}");
			return false;
		}
	});
	
});
</script>
</head>
<body>
	[#include "/shop/include/header.ftl" /]
	<!--页面跟踪导航-->
	<div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<a href="/"><span>首页</span></a>><a href="${base}/member/index.jhtml"><span>个人中心</span></a>><span>购物车</span></div>
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
               <li id="this"><a href="/cart/list.jhtml">我的购物车</a></li>
               <li><a href="/member/order/info.jhtml">填写并核对订单信息</a></li>
               <li><a href="#">核对支付信息</a></li>
               <li><a href="#">支付结果信息</a></li>
           </ul>
        </div>
         <div id="my_order">我的购物车</div>
	 [#if cart?? && cart.cartItems?has_content]
           <table id="order_infor" border="0" cellspacing="0" cellpadding="0">
<thead>
  <tr id="titleone">
	    <td class="choose"><input class="choose" type="checkbox" value="" />全选</td>    <td class="goods">商品</td>
	    <td class="price">价格</td>
	    <td class="quantity">数量</td>
	    <td class="operate">优惠</td>
	    <td class="pay">小计</td>
	    <td class="state">操作</td>
  </tr>
 
  </thead>
  <tbody>
	  [#list cart.cartItems as cartItem]
		<tr>
			 <td class="choose"><input class="choose" type="checkbox" value="${cartItem.id}" name="id"/></td>
			 <td class="goods">
				  <div class="goods_pic">
				    <img src="[#if cartItem.product.thumbnail??]${cartItem.product.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" alt="${cartItem.product.name}" />
				    </div>
				  <div class="goods_name"><a href="${base}${cartItem.product.path}" title="${cartItem.product.fullName}" target="_blank">${abbreviate(cartItem.product.fullName, 60, "...")}</a></div>
				  <div class="goods_num">货品编码：${cartItem.product.sn}</div>
				[#if product.specifications?has_content]
					<div class="goods_color">
						[#list product.specificationValues as specificationValue]
							${specificationValue.name}
						[/#list]
					</div>
				[/#if]
				[#if cartItem.product.store??]
					<div class="goods_num">
						店铺: <a href="${cartItem.product.store.indexUrl}">${cartItem.product.store.name}</a>
					</div>
    			[#else]
    				<div class="goods_num">
						店铺: 商城自营
					</div>
				[/#if]
				</td>
				<td class="price">
						${currency(cartItem.price, true)}
				</td>
				 <td class="quantity">
					    <input id="reduce" class="decrease2" type="button" value="-" />
					    <input type="text" name="quantity"  id="num" value="${cartItem.quantity}" maxlength="4" onpaste="return false;" />
					    <input id="add" class="increase2" type="button" value="+" />
				  </td>
				  <td class="operate">
				    	 ${currency(cartItem.savingMoney, true)}
				  </td>
				  <td class="pay">
						<span class="subtotal">${currency(cartItem.subtotal, true)}</span>
				  </td>
				  <td class="state">
					    <p><A href="javascript:;" class="remove">移出收藏夹</A></p>
					    <p><a href="javascript:;" class="delete">${message("shop.cart.delete")}</a></p>
	              </td>
		</tr>
	  [/#list]
  </tbody>
  <tfoot>
  <tr>
    <td valign="top"><input class="choose"  type="checkbox" value="" />全选</td>
    <td  valign="top">
    <!--底部页码-->
    
       <a href="#">&nbsp;&nbsp;x&nbsp;删除选中商品</a>
       </td>
       <td colspan="3" >&nbsp;</a>
       </td>
    <td colspan="2" class="">
       <div class="amount">
           <dl>
           <dt>${message("shop.cart.effectivePrice")}：</dt>
           <dd><strong id="effectivePrice">${currency(cart.effectivePrice, true, true)}</strong></dd>
            <dt>商品优惠总额：</dt>
           <dd>￥0.00</dd>
            <dt>预计可获积分：</dt>
           <dd><em id="effectivePoint">${cart.effectivePoint}</em></dd>
            <dt class="zonge">总计(不含运费)：</dt>
           <dd class="zonge" id="effectivePrice2">${currency(cart.effectivePrice, true, true)}</dd>
           </dl>
        </div>
       </td>
  </tr>
  </tfoot>
</table>
[#else]
		<p>
			<a href="${base}/">${message("shop.cart.empty")}</a>
		</p>
	[/#if]
     <div class="page">
         <a href="${base}/member/order/info.jhtml" class="page_next">去结算</a>
         <input class="page_last" type="button" value="还要购物" onclick="location.href='/'" />
     </div>
   </div>
   <div id="clear"></div>
   </div>
  </div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>