<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.member.favorite.list")}[#if systemShowPowered] - Powered By SHOP++[/#if]</title>
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
	var $deleteChecked = $("a.deleteChecked");
	var $delete2 = $(".delete2");
	var $giftItems = $("#giftItems");
	var $promotion = $("#promotion");
	var $effectivePoint = $("#effectivePoint");
	var $effectivePrice = $("#effectivePrice");
	var $effectivePrice2 = $("#effectivePrice2");
	var $clear = $("#clear");
	var $submit = $("#submit");
	var timeouts = {};
	
	var $tr = $quantity.closest("tr");
	
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
		$(this).parent().next().next().children(".subtotal").text(currency($("#price").val()*$quantity.val(), true));
		//$tr.find("span.subtotal").text(currency($("#price").val()*$quantity.val(), true));
		//edit($quantity);
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
		$(this).parent().next().next().children(".subtotal").text(currency($("#price").val()*$quantity.val(), true));
		//$tr.find("span.subtotal").text(currency($("#price").val()*$quantity.val(), true));
		//edit($quantity);
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
							// hfh 2014/9/19 临时价格为团购价
							if(data.tempPrice != null && data.tempPrice > 0){
								$tr.find("span.subtotal").text(currency(data.tempPrice*data.qty, true));
							}else{
								$tr.find("span.subtotal").text(currency(data.subtotal*data.qty, true));
							}
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
	
	
		//全选
		var ids = document.getElementsByName("id");
		$(".checkAll").toggle(function(){
			$(this).checked = true;
			for(i=0;i<ids.length;i++){
				ids[i].checked = true;
			}
		},function(){
				//var ids = document.getElementsByName("id");
				$(this).checked = false;
				for(i=0;i<ids.length;i++){
					ids[i].checked = false;
				}
		});


	// 删除选中的记录
	$deleteChecked.click(function() {
			var $tr = $delete2.closest("tr");
			//var id = $tr.find("input[name='id']").val();
			/*获取多条记录*/
			var ids = document.getElementsByName("id");
			var productIds = "";
			for(var i=0;i<ids.length;i++){
				if(ids[i].checked == true){
					if(i==ids.length - 1){
						productIds += $(ids[i]).val();
					}else{
						productIds += $(ids[i]).val() + ",";
					}
				}
			}
			if(productIds.length>0){
				if (confirm("${message("shop.dialog.deleteConfirm")}")) {
					$.ajax({
						url: "delete.jhtml",
						type: "POST",
						data: {ids: productIds},
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								var $siblings = $tr.siblings();
								if ($siblings.size() <= 1) {
									//暂无信息
									$("#order_infor").after('<p>${message("shop.member.noResult")}<\/p>');
									//隐藏购买按钮
									$(".amount").hide();
								} else {
									$siblings.last().addClass("last");
								}
								$tr.remove();
								window.location = "${base}/member/favorite/list.jhtml";
							}
						}
					});
	   			}
			}else{
				$.ajax({
					url: "delete.jhtml",
					type: "POST",
					data: {ids: productIds},
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						if (message.type == "success") {
							var $siblings = $tr.siblings();
							if ($siblings.size() <= 1) {
								//暂无信息
								$("#order_infor").after('<p>${message("shop.member.noResult")}<\/p>');
								//隐藏购买按钮
								$(".amount").hide();
							} else {
								$siblings.last().addClass("last");
							}
							$tr.remove();
							//window.location = "${base}/member/favorite/list.jhtml";
						}
					}
				});
			}
		return false;
	});
	
	
	//删除当前记录
	$delete.click(function() {
		if (confirm("${message("shop.dialog.deleteConfirm")}")) {
			var $tr = $(this).closest("tr");
			var id = $tr.find("input[name='id']").val();
			var index_value = $tr.find("input[name='index_value']").val();
			$.ajax({
				url: "delete.jhtml",
				type: "POST",
				data: {id: id},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						var $siblings = $tr.siblings();
						if ($siblings.size() <= 1) {
							$("#order_infor").after('<p>${message("shop.member.noResult")}<\/p>');
						} else {
							$siblings.last().addClass("last");
						}
						
						window.location = "${base}/member/favorite/list.jhtml";
					}
				}
			});
		}
		return false;
	});

	//移出收藏夹
	$remove.click(function() {
		if (confirm("${message("确定要移出收藏夹")}")) {
			var $tr = $(this).closest("tr");
			var id = $tr.find("input[name='id']").val();
			$.ajax({
				url: "delete.jhtml",
				type: "POST",
				data: {id: id},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						var $siblings = $tr.siblings();
						if ($siblings.size() <= 1) {
							$("#order_infor").after('<p>${message("shop.member.noResult")}<\/p>');
						} else {
							$siblings.last().addClass("last");
						}
						$tr.remove();
						window.location = "${base}/member/favorite/list.jhtml";
					}
				}
			});
		}
		return false;
	});
});


	//加入购物车(支持同时多个商品加入购物车)
	function addToCart(){
		var ids = document.getElementsByName("id");
		var productIds = "";
		var qtys = document.getElementsByName("quantity");
		var quantitys = "";
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked == true){
				if(i==ids.length - 1){
					productIds += $(ids[i]).val();
					quantitys += $(qtys[i]).val();
				}else{
					productIds += $(ids[i]).val() + ",";
					quantitys += $(qtys[i]).val() + ",";
				}
			}
		}
		$.ajax({
			url: "${base}/cart/add.jhtml",
			type: "POST",
			data: {ids: productIds, quantitys: quantitys},
			dataType: "json",
			cache: false,
			success: function(message){
				$.message(message);
			}
		});
	}
	
	//立即购买
	function buyToNow(){
		var ids = document.getElementsByName("id");
		var productIds = "";
		var qtys = document.getElementsByName("quantity");
		var quantitys = "";
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked == true){
				if(i==ids.length - 1){
					productIds += $(ids[i]).val();
					quantitys += $(qtys[i]).val();
				}else{
					productIds += $(ids[i]).val() + ",";
					quantitys += $(qtys[i]).val() + ",";
				}
			}
		}
		$.ajax({
			url: "${base}/cart/add.jhtml",
			type: "POST",
			data: {ids: productIds, quantitys: quantitys},
			dataType: "json",
			cache: false,
			success: function(message){
				if(productIds == ""){
					$.message(message);
				}else{
					window.location = "${base}/cart/list.jhtml";
				}
			}
		});
	}
	
</script>
</head>
	<body>
	[#include "/shop/include/header.ftl" /]
		<!--页面跟踪导航-->
		<div class="where-big">
		     <div id="index_place" style="width:400px;float:left">您的位置：<a href="/"><span>首页</span></a>><a href="${base}/member/index.jhtml"><span>个人中心</span></a>><span>我的收藏</span></div>
				  <div class="kfdlb">
					  <ul>
						   <li>分享到：</li>
				          <li><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
				          <li><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
				          <li><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
				          <li><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
				          <!--<li><a href="#"><img src="/resources/shop/images/xinxi_ico.png" width="17px" ;=""></a></li>-->
				          <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
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
			   <!--
			        <div id="car_nav">
			           <ul>
			               <li id="this"><a href="/cart/list.jhtml">我的购物车</a></li>
			               <li><a href="/member/order/info.jhtml">填写并核对订单信息</a></li>
			               <li><a href="#">核对支付信息</a></li>
			               <li><a href="#">支付结果信息</a></li>
			           </ul>
			        </div>
			    -->    
			         <div id="my_order">我的收藏</div>
				 [#if page?? && page.content?has_content]
			           <table id="order_infor" border="0" cellspacing="0" cellpadding="0">
							<thead>
								  <tr id="titleone">
									    <td class="choose"><input class="choose checkAll" type="checkbox" value="" />全选</td>    <td class="goods">商品</td>
									    <td class="price">价格</td>
									    <td class="quantity">数量</td>
									    <td class="operate">优惠</td>
									    <td class="pay">小计</td>
									    <td class="state">操作</td>
								  </tr>
							  </thead>
							  <tbody>
							  	  [#assign xy=-1 /]
								  [#list page.content as product]
								  [#assign xy=xy+1 /]
									<tr>
									<input type="hidden" name="index_value" value="${xy}"/>
										 <td class="choose"><input class="choose" type="checkbox" value="${product.id}" name="id" id="product_id" /></td>
										 <td class="goods">
											  <div class="goods_pic">
											    <img src="[#if product.thumbnail??]${product.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" alt="${product.name}" />
											    </div>
											  <div class="goods_name"><a href="${base}${product.path}" title="${product.fullName}" target="_blank">${abbreviate(product.fullName, 60, "...")}</a></div>
											  <div class="goods_num">货品编码：${product.sn}</div>
												[#if product.specifications?has_content]
													<div class="goods_color">
														[#list product.specificationValues as specificationValue]
															${specificationValue.name}
														[/#list]
													</div>
												[/#if]
										</td>
										<td class="price">
												${currency(product.price, true)}
										</td>
										<input type="hidden" id="price" value="${product.price}" />
										 <td class="quantity">
											    <input id="reduce" class="decrease2" type="button" value="-" />
											    <input type="text" name="quantity"  id="num" value="1" maxlength="4" onpaste="return false;" />
											    <input id="add" class="increase2" type="button" value="+" />
										  </td>
										  <td class="operate">
										    	 -￥00.00
										  </td>
										  <td class="pay">
												<span class="subtotal">${currency(product.price, true)}</span>
										  </td>
										  <td class="state">
											    <p><A href="javascript:;" class="remove">移出收藏夹</A></p>
											    <p><a href="javascript:;" class="delete delete2">${message("shop.cart.delete")}</a></p>
							              </td>
									</tr>
								  [/#list]
							  </tbody>
							  <tfoot>
								  <tr>
									    <td valign="top"><input class="choose checkAll"  type="checkbox" value="" />全选</td>
									    <td  valign="top">
									    	<!--底部页码-->
									       <a href="javascript:;" class="deleteChecked">&nbsp;&nbsp;x&nbsp;删除选中商品</a>
								        </td>
									    <td colspan="3" >&nbsp;</a>
									    </td>
									    <td colspan="2" class="">
										       <div class="amount">
										           <dl>
										           <dt>${message("shop.cart.effectivePrice")}：</dt>
										           <dd><strong id="effectivePrice">${currency(product.price, true, true)}</strong></dd>
										            <dt>商品优惠总额：</dt>
										           <dd>￥0.00</dd>
										            <dt>预计可获积分：</dt>
										           <dd><em id="effectivePoint">${product.price}</em></dd>
										            <dt class="zonge">总计(不含运费)：</dt>
										           <dd class="zonge" id="effectivePrice2">${currency(product.price, true, true)}</dd>
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
			     	<!--
			         <a href="${base}/member/order/info.jhtml"><input class="page_next" type="button" value="去结算" /></a>
			         <input class="page_last" type="button" value="还要购物"  />
			         -->
			         <input class="page_next amount" type="button" value="立即购买" onclick="buyToNow();"/>
         			 <input class="page_last amount" type="button" value="加入购物车" onclick="addToCart();" />
			     </div>
		   </div>
		   <div id="clear"></div>
		 </div>
	  </div>
		[#include "/shop/include/footer.ftl" /]
	</body>
</html>