<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "productContent"]
	<title>[#if product.seoTitle??]${product.seoTitle}[#elseif seo.title??][@seo.title?interpret /][/#if][#if systemShowPowered] - Powered By SHOP++[/#if]</title>
	<meta name="author" content="SHOP++ Team" />
	<meta name="copyright" content="SHOP++" />
	[#if product.seoKeywords??]
		<meta name="keywords" content="${product.seoKeywords}" />
	[#elseif seo.keywords??]
		<meta name="keywords" content="[@seo.keywords?interpret /]" />
	[/#if]
	[#if product.seoDescription??]
		<meta name="description" content="${product.seoDescription}" />
	[#elseif seo.description??]
		<meta name="description" content="[@seo.description?interpret /]" />
	[/#if]
[/@seo]
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/wjl_details.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.jqzoom.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect_X.js"></script>
<link href="${base}/resources/shop/css/heard.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready(function() {

	var $areaId = $("#areaId");
	var $historyProduct = $("#historyProduct ul");
	var $clearHistoryProduct = $("#clearHistoryProduct");
	var $zoom = $("#zoom");
	var $scrollable = $("#scrollable");
	var $thumbnail = $("#scrollable a");
	var $specification = $("#specification dl");
	var $specificationTitle = $("#specification div");
	var $specificationValue = $("#specification a");
	var $productNotifyForm = $("#productNotifyForm");
	var $productNotify = $("#productNotify");
	var $productNotifyEmail = $("#productNotify input");
	var $addProductNotify = $("#addProductNotify");
	var $quantity = $("#quantity");
	var $increase = $("#increase");
	var $decrease = $("#decrease");
	var $addCart = $("#addCart");
	var $addFavorite = $("#addFavorite");
	var $window = $(window);
	var $bar = $("#bar ul");
	var $introductionTab = $("#introductionTab");
	var $parameterTab = $("#parameterTab");
	var $reviewTab = $("#reviewTab");
	var $consultationTab = $("#consultationTab");
	var $introduction = $("#introduction");
	var $parameter = $("#parameter");
	var $review = $("#review");
	var $addReview = $("#addReview");
	var $consultation = $("#consultation");
	var $addConsultation = $("#addConsultation");
	var barTop = $bar.offset().top;
	var productMap = {};
	
	
	//详情介绍 图片放大100%
	var imgs = $(".productDetailDivImg img");
	if(null!=imgs&&"undefined"!=imgs&&imgs.length>0){
	   for(var i=0;i<imgs.length;i++){
	      $(imgs[i]).attr("width","930px");
	   }
	}	
	
	//首先将#back-to-top隐藏
	$("#back-to-top").hide();
	//当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
	$(function () {
		$(window).scroll(function(){
		if ($(window).scrollTop()>100){
		$("#back-to-top").fadeIn(500);
		}
		else
		{
		$("#back-to-top").fadeOut(500);
		}
		});
		//当点击跳转链接后，回到页面顶部位置
		$("#back-to-top").click(function(){
		$('body,html').animate({scrollTop:0},100);
		return false;
		});
		});
	
	[@compress single_line = true]
		productMap[${product.id}] = {
			path: null,
			specificationValues: [
				[#list product.specificationValues as specificationValue]
					"${specificationValue.id}"[#if specificationValue_has_next],[/#if]
				[/#list]
			]
		};
		[#list product.siblings as product]
			productMap[${product.id}] = {
				path: "${product.path}",
				specificationValues: [
					[#list product.specificationValues as specificationValue]
						"${specificationValue.id}"[#if specificationValue_has_next],[/#if]
					[/#list]
				]
			};
		[/#list]
	[/@compress]
	
	// 锁定规格值
	lockSpecificationValue();
	
	$areaId.lSelect({
		url: "/common/area.jhtml"
	});
	
	// 商品图片放大镜
	$zoom.jqzoom({
		zoomWidth: 368,
		zoomHeight: 368,
		title: false,
		showPreload: false,
		preloadImages: false
	});
	
	// 商品缩略图滚动
	$scrollable.scrollable();
	
	$thumbnail.hover(function() {
		var $this = $(this);
		if ($this.hasClass("current")) {
			return false;
		} else {
			$thumbnail.removeClass("current");
			$this.addClass("current").click();
		}
	});
	
	// 规格值选择
	$specificationValue.click(function() {
		var $this = $(this);
		if ($this.hasClass("locked")) {
			return false;
		}
		$this.toggleClass("selected").parent().siblings().children("a").removeClass("selected");
		var selectedIds = new Array();
		$specificationValue.filter(".selected").each(function(i) {
			selectedIds[i] = $(this).attr("val");
		});
		var locked = true;
		$.each(productMap, function(i, product) {
			if (product.specificationValues.length == selectedIds.length && contains(product.specificationValues, selectedIds)) {
				if (product.path != null) {
					location.href = "${base}" + product.path;
					locked = false;
				}
				return false;
			}
		});
		if (locked) {
			lockSpecificationValue();
		}
		$specificationTitle.hide();
		return false;
	});
	
	// 锁定规格值
	function lockSpecificationValue() {
		var selectedIds = new Array();
		$specificationValue.filter(".selected").each(function(i) {
			selectedIds[i] = $(this).attr("val");
		});
		$specification.each(function() {
			var $this = $(this);
			var selectedId = $this.find("a.selected").attr("val");
			var otherIds = $.grep(selectedIds, function(n, i) {
				return n != selectedId;
			});
			$this.find("a").each(function() {
				var $this = $(this);
				otherIds.push($this.attr("val"));
				var locked = true;
				$.each(productMap, function(i, product) {
					if (contains(product.specificationValues, otherIds)) {
						locked = false;
						return false;
					}
				});
				if (locked) {
					$this.addClass("locked");
				} else {
					$this.removeClass("locked");
				}
				otherIds.pop();
			});
		});
	}
	
	// 判断是否包含
	function contains(array, values) {
		var contains = true;
		for(i in values) {
			if ($.inArray(values[i], array) < 0) {
				contains = false;
				break;
			}
		}
		return contains;
	}
	
	// 到货通知
	$addProductNotify.click(function() {
		[#if product.specifications?has_content]
			var specificationValueIds = new Array();
			$specificationValue.filter(".selected").each(function(i) {
				specificationValueIds[i] = $(this).attr("val");
			});
			if (specificationValueIds.length != ${product.specificationValues?size}) {
				$specificationTitle.show();
				return false;
			}
		[/#if]
		if ($addProductNotify.val() == "${message("shop.product.addProductNotify")}") {
			$addProductNotify.val("${message("shop.product.productNotifySubmit")}");
			$productNotify.show();
			$productNotifyEmail.focus();
			if ($.trim($productNotifyEmail.val()) == "") {
				$.ajax({
					url: "${base}/product_notify/email.jhtml",
					type: "GET",
					dataType: "json",
					cache: false,
					success: function(data) {
						$productNotifyEmail.val(data.email);
					}
				});
			}
		} else {
			$productNotifyForm.submit();
		}
		return false;
	});
	
	// 到货通知表单验证
	$productNotifyForm.validate({
		rules: {
			email: {
				required: true,
				email: true
			}
		},
		submitHandler: function(form) {
			$.ajax({
				url: "${base}/product_notify/save.jhtml",
				type: "POST",
				data: {productId: ${product.id}, email: $productNotifyEmail.val()},
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$addProductNotify.prop("disabled", true);
				},
				success: function(data) {
					if (data.message.type == "success") {
						$addProductNotify.val("${message("shop.product.addProductNotify")}");
						$productNotify.hide();
					}
					$.message(data.message);
				},
				complete: function() {
					$addProductNotify.prop("disabled", false);
				}
			});
		}
	});
	
	
	//修改数量判断库存
        function isinventory(){
		$("#addCart").removeAttr("disabled");
		$("#addToCart").removeAttr("disabled");
		var $position=$(this);
		var selectName = $(this).attr("name") + "_select";
		var num=$("select[name='"+selectName+"'] option:selected");
		if($('#is_inv').val()>0){
          	   var sid=$("select[name='areaId_select'] option:selected")[0].text;
					var cid=$("select[name='areaId_select'] option:selected")[1].text;
					var qid="";
					if($("select[name='areaId_select'] option:selected").length==3){
					 qid=$("select[name='areaId_select'] option:selected")[2].text;
					}
					if("上海市"==sid||"北京市"==sid||"天津市"==sid||"重庆市"==sid){
					    qid=cid;
						cid=sid;
						sid=sid.substr(0, 2);
					}
					var qty=$("#quantity").val();
					var item_code= $('#item_code').val();
					var message="";
					$.ajax({
							url: "../../../area_inventory/areainv.jhtml",
							type: "POST",
							data: {sid:sid,cid:cid,qid:qid,qty:qty,item_code:item_code},
							//dataType: "json",
							//cache: false,
							async: false,
							success: function(data) {
								if(data){
										  
								    $("#is_kuc").html('有库存');
									  
								}else{
									$("#is_kuc").html('无库存');
									  
									//$("#addCart").attr("disabled","disabled");
									//$("#addToCart").attr("disabled","disabled");
								}
								
							},　error: function (xhr, errorInfo, ex) { 
							show.append('error invoke!errorInfo:' + errorInfo+'<br/>'); 
							
							}
					});
						//$position.remove(message);
			//return $(message).insertAfter($position);
		}
	};
	
	// 购买数量
	$quantity.keypress(function(event) {
		var key = event.keyCode ? event.keyCode : event.which;
		if ((key >= 48 && key <= 57) || key==8) {
			return true;
		} else {
			return false;
		}
	});
	
	// 增加购买数量
	$increase.click(function() {
		isinventory();
		var quantity = $quantity.val();
		if (/^\d*[1-9]\d*$/.test(quantity)) {
			$quantity.val(parseInt(quantity) + 1);
		} else {
			$quantity.val(1);
		}
	});
	
	// 减少购买数量
	$decrease.click(function() {
		isinventory();
		var quantity = $quantity.val();
		if (/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 1) {
			$quantity.val(parseInt(quantity) - 1);
		} else {
			$quantity.val(1);
		}
	});
	
	$quantity.change(function(event) {
	        isinventory();
	});
	
	// 加入购物车
	$addCart.click(function() {
		var status = true;
		if( $('#is_inv').val()<1){
        //alert("请选择配送区域");
	//	return false;
		}
		
		[#if product.specifications?has_content]
			var specificationValueIds = new Array();
			$specificationValue.filter(".selected").each(function(i) {
				specificationValueIds[i] = $(this).attr("val");
			});
			if (specificationValueIds.length != ${product.specificationValues?size}) {
				$specificationTitle.show();
				return false;
			}
		[/#if]
		
		//判断购物车中是否有店铺商品
		$.ajax({
			url: "${base}/cart/queryCartIsExistStore.jhtml",
			type: "POST",
			data: {id: ${product.id}},
			dataType: "json",
			cache: false,
			success: function(data) {
				if(data.message.type == "success") {
					
				} else if(data.message.type == "error") {
					$.message(data.message);
					status = false;
				}
				if(!status) {
					return;
				}
				
				var quantity = $quantity.val();
				if (/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 0) {
					$.ajax({
						url: "${base}/cart/add.jhtml",
						type: "POST",
						data: {id: ${product.id}, quantity: quantity},
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							queryInfo();
						}
					});
				} else {
					$.message("warn", "${message("shop.product.quantityPositive")}");
				}
			}
		});
		
	});
	
	// 添加商品收藏
	$addFavorite.click(function() {
		$.ajax({
			url: "${base}/member/favorite/add.jhtml?id=${product.id}",
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(message) {
				$.message(message);
			}
		});
		return false;
	});
	
	$window.scroll(function() {
		var scrollTop = $(this).scrollTop();
		if (scrollTop > barTop) {
			if (window.XMLHttpRequest) {
				$bar.css({position: "fixed", top: 0});
			} else {
				$bar.css({top: scrollTop});
			}
			var introductionTop = $introduction.size() > 0 ? $introduction.offset().top - 36 : null;
			var parameterTop = $parameter.size() > 0 ? $parameter.offset().top - 36 : null;
			var reviewTop = $review.size() > 0 ? $review.offset().top - 36 : null;
			var consultationTop = $consultation.size() > 0 ? $consultation.offset().top - 36 : null;
			if (consultationTop != null && scrollTop >= consultationTop) {
				$bar.find("li").removeClass("current");
				$consultationTab.addClass("current");
			} else if (reviewTop != null && scrollTop >= reviewTop) {
				$bar.find("li").removeClass("current");
				$reviewTab.addClass("current");
			} else if (parameterTop != null && scrollTop >= parameterTop) {
				$bar.find("li").removeClass("current");
				$parameterTab.addClass("current");
			} else if (introductionTop != null && scrollTop >= introductionTop) {
				$bar.find("li").removeClass("current");
				$introductionTab.addClass("current");
			}
		} else {
			$bar.find("li").removeClass("current");
			$bar.css({position: "absolute", top: "1164px"});
		}
	});
	
	[#if setting.isReviewEnabled && setting.reviewAuthority != "anyone"]
		// 发表商品评论
		$addReview.click(function() {
			if ($.checkLogin()) {
				return true;
			} else {
				$.redirectLogin("${base}/review/add/${product.id}.jhtml", "${message("shop.product.addReviewNotAllowed")}");
				return false;
			}
		});
	[/#if]
	
	[#if setting.isConsultationEnabled && setting.consultationAuthority != "anyone"]
		// 发表商品咨询
		$addConsultation.click(function() {
			if ($.checkLogin()) {
				return true;
			} else {
				$.redirectLogin("${base}/consultation/add/${product.id}.jhtml", "${message("shop.product.addConsultationNotAllowed")}");
				return false;
			}
		});
	[/#if]
	
	// 浏览记录
	var historyProduct = getCookie("historyProduct");
	var historyProductIds = historyProduct != null ? historyProduct.split(",") : new Array();
	for (var i = 0; i < historyProductIds.length; i ++) {
		if (historyProductIds[i] == "${product.id}") {
			historyProductIds.splice(i, 1);
			break;
		}
	}
	historyProductIds.unshift("${product.id}");
	if (historyProductIds.length > 6) {
		historyProductIds.pop();
	}
	addCookie("historyProduct", historyProductIds.join(","), {path: "${base}/"});
	$.ajax({
		url: "${base}/product/history.jhtml",
		type: "GET",
		data: {ids: historyProductIds},
		dataType: "json",
		traditional: true,
		cache: false,
		success: function(data) {
			$.each(data, function (index, product) {
				var thumbnail = product.thumbnail != null ? product.thumbnail : "${setting.defaultThumbnailProductImage}";
				$historyProduct.append('<li><a href="${base}' + product.path + '"><img src="' + thumbnail + '" \/>' + product.name + '<\/a><span id="h_price">' + currency(product.price, true) + '<\/span> <span id="h_comment">评论<i>'+product.synopsis+'</i>条</span> <\/li>');
			});
		}
	});
	
	// 清空浏览记录
	$clearHistoryProduct.click(function() {
		$historyProduct.empty();
		$(this).text("${message("shop.product.noHistoryProduct")}");
		removeCookie("historyProduct", {path: "${base}/"});
	});
	
	// 点击数
	$.ajax({
		url: "${base}/product/hits/${product.id}.jhtml",
		type: "GET"
	});
	
});
</script>
</head>
<body >
	[#include "/shop/include/header.ftl" /]
	[#assign productCategory = product.productCategory /]
	<div id="index_place">您的位置：<span><a href="/">首页</a></span>
		[#if productCategory??]
				[@product_category_parent_list productCategoryId = productCategory.id]
					[#list productCategories as productCategory]
		            &gt;
		            <a href="${base}${productCategory.path}"><span>${productCategory.name}</span></a>
            		[/#list]
				[/@product_category_parent_list]
	            	&gt;
	            	<a href="${base}${productCategory.path}"><span class="wdwz" >${productCategory.name}</span></a>
            [/#if]
	</div>
	
	<!--<div class="path">
				<ul>
					<li>
						<a href="${base}/">${message("shop.path.home")}</a>
					</li>
					[@product_category_parent_list productCategoryId = productCategory.id]
						[#list productCategories as productCategory]
							<li>
								<a href="${base}${productCategory.path}">${productCategory.name}</a>
							</li>
						[/#list]
					[/@product_category_parent_list]
					<li>
						<a href="${base}${productCategory.path}">${productCategory.name}</a>
					</li>
				</ul>
			</div>-->
			
	<div class="container productContent" id="productContent_c">
	<div class="span18 last" style="width:1190px; margin-top:0px; height:530px; margin-bottom:20px;">
			
			
			
			<div class="productImage">
				[#if product.productImages?has_content]
					<a id="zoom" href="${product.productImages[0].large}" rel="gallery">
						<img class="medium" src="${product.productImages[0].medium}" />
					</a>
				[#else]
					<a id="zoom" href="${setting.defaultLargeProductImage}" rel="gallery">
						<img class="medium" src="${setting.defaultMediumProductImage}" />
					</a>
				[/#if]
				<a href="javascript:;" class="prev"></a>
				<div id="scrollable" class="scrollable">
					<div class="items">
						[#if product.productImages?has_content]
							[#list product.productImages as productImage]
								<a[#if productImage_index == 0] class="current"[/#if] href="javascript:;" rel="{gallery: 'gallery', smallimage: '${productImage.medium}', largeimage: '${productImage.large}'}"><img src="${productImage.thumbnail}" title="${productImage.title}" /></a>
							[/#list]
						[#else]
							<a class="current" href="javascript:;"><img src="${setting.defaultThumbnailProductImage}" /></a>
						[/#if]
					</div>
				</div>
				<a href="javascript:;" class="next"></a>
			</div>
			
		<!--商品名称，价格，型号，那一坨-->
			
		<div id="shop_infor">
			<div class="name">${product.name}[#if product.isGift] [${message("shop.product.gifts")}][/#if]</div>
			<div class="name2">${product.seoDescription}[#if product.isGift] [${message("shop.product.gifts")}][/#if]</div>
		
		<!--	  
		 <div class="sn">
				<div>${message("Product.sn")}: ${product.sn}</div>
				[#if product.scoreCount > 0]
					<div>${message("Product.score")}:</div>
					<div class="score${(product.score * 2)?string("0")}"></div>
					<div>${product.score?string("0.0")} (${message("Product.scoreCount")}: ${product.scoreCount})</div>
				[/#if]
			</div>
			
			<div style=" width: 598px; float: right; padding-top: 10px; font-size: 14px; padding-right:20px;">
				<dl>
						<dt style=" float:left;">送货至:</dt>
						<dd>
							<span class="fieldSet" style=" padding-left: 10px; ">
									<input type="hidden" id="areaId" name="areaId" />
									<input type="hidden" id="is_inv" name="is_inv" />
							</span>
						</dd>
					</dl>
			</div>
	     -->
		 
		 
		 <!--价格信息-->
		     <div id="about_goods">
	             <DL>
		                 <dt>市场价格：${currency(product.marketPrice, true)}</dt>
		                 <dd>月初计售出：${product.monthSales}件</dd>
		                 <dt>商城价：<i>￥${product.price}</i></dt>
		                 <dd>库存：${product.stock}</dd>
		                 	<dt>店铺 : 
		                 	[#if product.store != null]
			                 	<a href="${product.store.indexUrl}" style="color:red;" target="_blank">
									${product.store.name}
								</a>
			                [#else]
			                 	<span style="color:red;">商城自营</span>
		                 	[/#if]
							</dt>
						 [#if product.scoreCount > 0]
							<dt>
								<div id="xq_star">
									<div>${message("Product.score")}:</div>
									<div class="score${(product.score * 2)?string("0")}"></div>
									<div>${product.score?string("0.0")} </div>
									<!--<ul>
										   <li></li>
										   <li></li>
										   <li></li>
										   <li></li>
										   <li></li> 
										   </ul> -->
								<div>
							</dt>
				            <dd><i>${product.scoreCount}</i>人已去评论</dd>
						[/#if]
	              </DL>
             </div>
		 
		 
		<!--旧，价格部分	<div class="info">
				<dl>
					<dt>${message("Product.price")}:</dt>
					<dd>
						<strong>￥${product.price}</strong>
						[#if setting.isShowMarketPrice]
							${message("Product.marketPrice")}:
							<del>${currency(product.marketPrice, true)}</del>
						[/#if]
					</dd>
				</dl>
				<dl>
					<dt>商城价：</dt>
					<dd>
						<strong>￥${product.price}</strong>
						[#if setting.isShowMarketPrice]
							${message("Product.marketPrice")}:
							<del>${currency(product.marketPrice, true)}</del>
						[/#if]
					</dd>
				</dl>
				<dl>
					<dt>评分：</dt>
					<dd>
						<strong>￥${product.price}</strong>
						[#if setting.isShowMarketPrice]
							${message("Product.marketPrice")}:
							<del>${currency(product.marketPrice, true)}</del>
						[/#if]
					</dd>
				</dl>
				[#if product.memberPrice?has_content]
					<dl>
						<dt>${message("Product.memberPrice")}:</dt>
						<dd>
							[#list product.memberPrice.keySet() as memberRank]
								${memberRank.name}: <span>${currency(product.memberPrice.get(memberRank), true)}</span>
							[/#list]
						</dd>
					</dl>
				[/#if]
				[#if product.validPromotions?has_content]
					<dl>
						<dt>${message("Product.promotions")}:</dt>
						<dd>
							[#list product.validPromotions as promotion]
								<a style="color:red;" target="_blank" title="${promotion.title}[#if promotion.beginDate?? || promotion.endDate??] (${promotion.beginDate} ~ ${promotion.endDate})[/#if]">${promotion.name}</a>
							[/#list]
						</dd>
					</dl>
				[/#if]
				[#if product.point > 0]
					<dl>
						<dt>${message("Product.point")}:</dt>
						<dd>
							<span>${product.point}</span>
						</dd>
					</dl>
				[/#if]
			</div>-->
			
			<!--颜色选择-->
			 <div id="choose">
		            <div id="sell">
						    [#if product.validPromotions?has_content]
							<dl>
								<dt>${message("Product.promotions")}:</dt>
								<dd>
									[#list product.validPromotions as promotion]
										&nbsp;<a style="color:red;" target="_blank" title="${promotion.title}[#if promotion.beginDate?? || promotion.endDate??] (${promotion.beginDate} ~ ${promotion.endDate})[/#if]">${promotion.name}</a>
									[/#list]
								</dd>
							</dl>
						    [/#if]
					</div>
					
		           <!-- <div id="choose_color">
		            <dl>
		                <dt>色彩：银色，铁灰</dt>
		                <dd>
		                    <div id="pc"></div>
		                    <div id="goods_color">银色</div>
		                    </dd>
		                  <dd>
		                    <div id="pc"><img src="${productImage.thumbnail}" title="${productImage.title}" /></div>
		                    <div id="goods_color">灰色</div>
		                    </dd>   
		                </dl>
		             </div>
		           <div id="quantity" >
		                 <dl>
			                  <dt>规格：</dt>
		                      <dd>
			                      <a href="#">8L</a>
			                      <a href="#">10L</a>
			                      <a href="#">11L</a>
			                      <a href="#">12L</a>
			                      <a href="#">16L</a>
			                   </dd>   
		                  </dl>
		              </div>-->
            </div>
			
			
			
			
					[#if !product.isGift]
						<div class="action">
							[#if product.specifications?has_content]
								<div id="specification" class="specification clearfix">
									<div class="title">${message("shop.product.specificationTitle")}</div>
									[#assign specificationValues = product.goods.specificationValues /]
									[#list product.specifications as specification]
										<dl>
											<dt>
												<span title="${specification.name}">${abbreviate(specification.name, 8)}:</span>
											</dt>
											[#list specification.specificationValues as specificationValue]
												[#if specificationValues?seq_contains(specificationValue)]
													<dd>
														<a href="javascript:;" class="${specification.type}[#if product.specificationValues?seq_contains(specificationValue)] selected[/#if]" val="${specificationValue.id}">[#if specification.type == "text"]${specificationValue.name}[#else]<img src="${specificationValue.image}" alt="${specificationValue.name}" title="${specificationValue.name}" />[/#if]<span title="${message("shop.product.selected")}">&nbsp;</span></a>
													</dd>
												[/#if]
											[/#list]
										</dl>
									[/#list]
								</div>
							[/#if]
							[#if product.isOutOfStock]
								<form id="productNotifyForm" action="${base}/product_notify/save.jhtml" method="post">
									<dl id="productNotify" class="productNotify">
										<dt>${message("shop.product.productNotifyEmail")}:</dt>
										<dd>
											<input type="text" name="email" maxlength="200" />
										</dd>
									</dl>
								</form>
							[#else]
								<dl class="quantity">
									<dt>${message("shop.product.quantity")}:</dt>
									<dd>
										<input type="text" id="quantity" name="quantity" value="1" maxlength="4" onpaste="return false;" />
										<div>
											<span id="increase" class="increase">&nbsp;</span>
											<span id="decrease" class="decrease">&nbsp;</span>
										</div>
									</dd>
									<dd>
										${product.unit!message("shop.product.defaultUnit")}
									</dd>
								</dl>
							[/#if]
							<div class="buy">
								[#if product.isOutOfStock]
									<input type="button" id="addProductNotify" class="addProductNotify" value="${message("shop.product.addProductNotify")}" />
								[#else]
									<input type="button" id="buyNow" class="addCart" value="立即购买" onclick="addCart(${product.id});" />
									<input type="button" id="addCart" class="addCart" value="加入购物车" />
								[/#if]
								<a href="javascript:;" id="addFavorite">${message("shop.product.addFavorite")}</a>
							</div>
						</div>
					[/#if]
					
			</div>
			
				<!--服务，分享-->	
				<div id="shop_infor_bottom">
				  <div id="assurance"><img src="/resources/shop/images/zpbz_ico.png">正品保证<img src="/resources/shop/images/jgbg_ico.png">七天调价补差额</div>
				  <div id="collect"> </div>
				</div>
		
		
		</div>
		
				<div class="mybe_like">
						 <div class="like_lit">猜你喜欢</div>
							 <ul>
							 		[@product_list productCategoryId = productCategory.id count = 5 ]
										[#list products as product]
											<li>
												<a href="${base}${product.path}" title="${product.name}">
													<div class="like_pic">
													    <img src="${product.image}">
													</div>
													<div class="like_name">${abbreviate(product.name, 30)}</div>
												</a>	
												<div class="like_tt">
													<div class="like_price">${currency(product.price, true, true)}</div>
													<div class="like_com">评论<i>${(product.reviews?size)!0}</i>条</div>
												</div>
						                 		[#if product.store??]
								                 	<dt>店铺 : 
									                 	<a href="${product.store.indexUrl}" target="_blank">
															${product.store.name}
														</a>
													</dt>
								                [/#if]
							                 	[#if product.store == null]
						                 			商城自营
							                 	[/#if]
												<!--
												<a href="${base}${product.path}" title="${product.name}">${abbreviate(product.name, 30)}</a>
												[#if product.scoreCount > 0]
													<div>
														<div>${message("Product.score")}: </div>
														<div class="score${(product.score * 2)?string("0")}"></div>
														<div>${product.score?string("0.0")}</div>
													</div>
												[/#if]
												<div>${message("Product.price")}: <strong>${currency(product.price, true, true)}</strong></div>
												<div>${message("Product.monthSales")}: <em>${product.monthSales}</em></div>-->
											</li>
										[/#list]
									[/@product_list]
							 
							 <!--
									<li>
										<div class="like_pic">
										    <img src="/resources/shop/images/mybe_like.jpg">
										</div>
										<div class="like_name">万家乐 燃气热水器 智能燃气浴热水JSQ16-QH3  新品 智能控温 恒温机 </div>
										<div class="like_tt">
											<div class="like_price">￥1998</div>
											<div class="like_com">评论<i>514</i>条</div>
										</div>
									</li>
									
									<li>
										<div class="like_pic">
											<img src="/resources/shop/images/mybe_like.jpg">
										</div>
										<div class="like_name">万家乐 燃气热水器 智能燃气浴热水JSQ16-QH3  新品 智能控温 恒温机 </div>
										<div class="like_tt">
											<div class="like_price">￥1998</div>
											<div class="like_com">评论<i>514</i>条</div>
										</div>
									</li>
										<li>
											<div class="like_pic">
											  	<img src="/resources/shop/images/mybe_like.jpg">
											</div>
											<div class="like_name">万家乐 燃气热水器 智能燃气浴热水JSQ16-QH3  新品 智能控温 恒温机 </div>
											<div class="like_tt">
												<div class="like_price">￥1998</div>
												<div class="like_com">评论<i>514</i>条</div>
											</div>
										</li>
										<li>
											<div class="like_pic">
												<img src="/resources/shop/images/mybe_like.jpg">
											</div>
											<div class="like_name">万家乐 燃气热水器 智能燃气浴热水JSQ16-QH3  新品 智能控温 恒温机 </div>
											<div class="like_tt">
												<div class="like_price">￥1998</div>
												<div class="like_com">评论<i>514</i>条</div>
											</div>
										</li>
										<li>
											<div class="like_pic">
												<img src="/resources/shop/images/mybe_like.jpg">
											</div>
											<div class="like_name">万家乐 燃气热水器 智能燃气浴热水JSQ16-QH3  新品 智能控温 恒温机 </div>
											<div class="like_tt">
												<div class="like_price">￥1998</div>
												<div class="like_com">评论<i>514</i>条</div>
											</div>
										</li>
									-->	
								</ul>
			 </div>
	 <!--左边推荐栏-->
	<div class="span6" style="margin:0px;width:235px;">
	
	<!--	
		<div class="hotProductCategory">
				[@product_category_root_list]
					[#list productCategories as category]
						<dl>
							<dt>
								<a href="${base}${category.path}">${category.name}</a>
							</dt>
							[@product_category_children_list productCategoryId = category.id count = 4]
								[#list productCategories as productCategory]
									<dd>
										<a href="${base}${productCategory.path}">${productCategory.name}</a>
									</dd>
								[/#list]
							[/@product_category_children_list]
						</dl>
					[/#list]
				[/@product_category_root_list]
			</div> 
			<div class="hotProduct">
				<div class="title">${message("shop.product.hotProduct")}</div>
				<ul>
					[@product_list productCategoryId = productCategory.id count = 6 orderBy="monthSales desc"]
						[#list products as product]
							<li>
								<a href="${base}${product.path}" title="${product.name}">${abbreviate(product.name, 30)}</a>
								[#if product.scoreCount > 0]
									<div>
										<div>${message("Product.score")}: </div>
										<div class="score${(product.score * 2)?string("0")}"></div>
										<div>${product.score?string("0.0")}</div>
									</div>
								[/#if]
								<div>${message("Product.price")}: <strong>${currency(product.price, true, true)}</strong></div>
								<div>${message("Product.monthSales")}: <em>${product.monthSales}</em></div>
							</li>
						[/#list]
					[/@product_list]
				</ul>
			</div>
			-->
	
		<!--最近浏览过的-->	
		<div id="historyProduct" class="historyProduct">
				<div class="title">${message("shop.product.historyProduct")}</div>
				<ul></ul>
				<a href="javascript:;" id="clearHistoryProduct" class="clearHistoryProduct">${message("shop.product.clearHistoryProduct")}</a>
			</div>
		</div>
		
		
		<!--商品详情那一坨-->
		
		<div class="span18 last" style="width:940px;">
			<div id="bar" class="bar">
				<ul>
						<li id="introductionTab" class="current">
							<a href="#introduction">商品介绍</a>
						</li>
						<li id="parameterTab">
							<a href="#parameter">商品参数</a>
						</li>
						<li id="reviewTab">
							<a href="#review">配送与服务</a>
						</li>
						<li id="consultationTab">
							<a href="#consultation">评价与晒单</a>
						</li>
				</ul>
			</div>
			
	   <div id="about_serve">
       <ul>
         <li>特色：全场免运费,送货上门，送装同步</li>
         <li>配送：万家乐物流及快递</li>
         <li>区域：覆盖2576个地区（至乡镇村，不含港澳台）</li>
         <li>售后：全国联保，七天退换</li>
         </ul>
       </div>
		<div id="interval"></div>
			
			<!--商品介绍部分-->
			[#if product.introduction?has_content]
				<div id="introduction" name="introduction" class="introduction">
					
					<div class="productDetailDivImg">
						${product.introduction}
					</div>
				</div>
			[/#if]
			[#if product.parameterValue?has_content]
				<div id="parameter" name="parameter" class="parameter">
					<div class="title">
						<strong>${message("shop.product.parameter")}</strong>
					</div>
					<table>
						[#list productCategory.parameterGroups as parameterGroups]
							<tr>
								<th class="group" colspan="2">${parameterGroups.name}</th>
							</tr>
							[#list parameterGroups.parameters as parameter]
								[#if product.parameterValue.get(parameter)??]
									<tr>
										<th>${parameter.name}</th>
										<td>${product.parameterValue.get(parameter)}</td>
									</tr>
								[/#if]
							[/#list]
						[/#list]
					</table>
				</div>
			[/#if]
			[#if setting.isReviewEnabled]
				<div id="review" name="review" class="review">
					<div class="title">${message("shop.product.review")}</div>
					<div class="content clearfix" style="width:940px;">
						[#if product.scoreCount > 0]
							<div class="score">
								<strong>${product.score?string("0.0")}</strong>
								<div>
									<div class="score${(product.score * 2)?string("0")}"></div>
									<div>${message("Product.scoreCount")}: ${product.scoreCount}</div>
								</div>
							</div>
							<div class="graph">
								<span style="width: ${(product.score * 20)?string("0.0")}%">
									<em>${product.score?string("0.0")}</em>
								</span>
								<div>&nbsp;</div>
								<ul>
									<li>${message("shop.product.graph1")}</li>
									<li>${message("shop.product.graph2")}</li>
									<li>${message("shop.product.graph3")}</li>
									<li>${message("shop.product.graph4")}</li>
									<li>${message("shop.product.graph5")}</li>
								</ul>
							</div>
							<div class="handle">
								<a href="${base}/review/add/${product.id}.jhtml" id="addReview">${message("shop.product.addReview")}</a>
							</div>
							[@review_list productId = product.id count = 5]
								[#if reviews?has_content]
									<table>
										[#list reviews as review]
											<tr>
												<th>
													${review.content}
													<div class="score${(review.score * 2)?string("0")}"></div>
												</th>
												<td>
													[#if review.member??]
														${review.member.username}
													[#else]
														${message("shop.product.anonymous")}
													[/#if]
													<span title="${review.createDate?string("yyyy-MM-dd HH:mm:ss")}">${review.createDate?string("yyyy-MM-dd")}</span>
												</td>
											</tr>
										[/#list]
									</table>
									<p>
										<a href="${base}/review/add/${product.id}.jhtml?scrollSide=1">[${message("shop.product.viewReview")}]</a>
									</p>
								[/#if]
							[/@review_list]
						[#else]
							<p>
								${message("shop.product.noReview")} <a href="${base}/review/add/${product.id}.jhtml" id="addReview">[${message("shop.product.addReview")}]</a>
							</p>
						[/#if]
					</div>
				</div>
			[/#if]
			[#if setting.isConsultationEnabled]
				<div id="consultation" name="consultation" class="consultation">
					<div class="title">${message("shop.product.consultation")}</div>
					<div class="content">
						[@consultation_list productId = product.id count = 5]
							[#if consultations?has_content]
								<ul>
									[#list consultations as consultation]
										<li>
											${consultation.content}
											<span>
												[#if consultation.member??]
													${consultation.member.username}
												[#else]
													${message("shop.consultation.anonymous")}
												[/#if]
												<span title="${consultation.createDate?string("yyyy-MM-dd HH:mm:ss")}">${consultation.createDate?string("yyyy-MM-dd")}</span>
											</span>
											[#if consultation.replyConsultations?has_content]
												<div class="arrow"></div>
												<ul>
													[#list consultation.replyConsultations as replyConsultation]
														<li>
															${replyConsultation.content}
															<span title="${replyConsultation.createDate?string("yyyy-MM-dd HH:mm:ss")}">${replyConsultation.createDate?string("yyyy-MM-dd")}</span>
														</li>
													[/#list]
												</ul>
											[/#if]
										</li>
									[/#list]
								</ul>
								<p>
									<a href="${base}/consultation/addMesaage/${product.id}.jhtml" id="addConsultation">[${message("shop.product.addConsultation")}]</a>
									<a href="${base}/consultation/content/${product.id}.jhtml">[${message("shop.product.viewConsultation")}]</a>
								</p>
							[#else]
								<p>
									${message("shop.product.noConsultation")} <a href="${base}/consultation/add/${product.id}.jhtml" id="addConsultation">[${message("shop.product.addConsultation")}]</a>
								</p>
							[/#if]
						[/@consultation_list]
					</div>
				</div>
			[/#if]
		</div>
	</div>
	<p id="back-to-top" style="display: block;"><a href="#top"><span></span>回到顶部</a></p>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>