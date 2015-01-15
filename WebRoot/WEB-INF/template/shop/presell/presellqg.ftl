<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>立即抢购</title>
    <link href="${base}/resources/shop/css/presellyy.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/shop/css/top.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/shop/css/footer.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.jqzoom.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
	<script type="text/javascript">		
	$().ready(function() {
	var $zoom = $("#zoom"); 
	var $thumbnail = $("#scrollable a");
	var $scrollable = $("#scrollable");
	var $historyProduct = $("#historyProduct ul");
	var $xq_button = $(".xq_button");
	        // 直接购买
	$xq_button.click(function(){
		var quantity = 1;
		if (/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 0) {
			$.ajax({
				url: "${base}/cart/add.jhtml",
				type: "POST",
				data: {id: ${product.id}, quantity: quantity , preSellRoleId: ${preSellRole.id}},
				dataType: "json",
				cache: false,
				success: function(message) {
					if(!(message.type=='warn')){
                                        	   document.location.href="${base}/cart/list.jhtml";
                                         }else{
                                            $.message(message);
                                         }
				}
			});
		} else {
			$.message("warn", "${message("shop.product.quantityPositive")}");
		}
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
		var $li = $("#scrollable li"); 
		if ($this.parent().hasClass("current")) {
			return false;
		} else {
			$li.removeClass("current");
			$this.parent().addClass("current");
			$this.click();
		}
		});
		
});
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
				$("#historyProduct").append('<li><a href="${base}' + product.path + '"><img src="' + thumbnail + '" \/></a><span>' + product.name + '<\/span><b>' + currency(product.price, true) + '<\/b><\/li>');
			});
		}
	});
	</script>
</head>
<body>
	[#include "/shop/include/header.ftl" /]
	[#assign preSellRole = preSellRole /]
	[#assign product = product /]
    <div class="nrqy">
        <div class="crumbs">
            <span><a href="${base}/">${message("shop.path.home")}</a></span>
            <b>></b>            
            <span>
            	[@product_category_parent_list productCategoryId = product.productCategory.id]
					[#list productCategories as productCategory]						
						<a href="${base}${productCategory.path}">${productCategory.name}</a>						
					[/#list]
				[/@product_category_parent_list]
            </span>
            <b>></b>
            <span class="wdwz"><a href="${base}${product.productCategory.path}">${product.productCategory.name}</a></span>      		
        </div>
      
        <div class="apply">
            <div class="apply_list"></div>
            <div class="apply_picture">
                <div class="big_pic">
               	[#if product.productImages?has_content]
					<a id="zoom" href="${product.productImages[0].large}"  rel="gallery">
						<img class="medium" src="${product.productImages[0].medium}"/>
					</a>
				[#else]
					<a id="zoom" href="${setting.defaultLargeProductImage}" rel="gallery">
						<img  src="${setting.defaultMediumProductImage}" width="300" height="300" />
					</a>
				[/#if]
                </div>
                <a href="javascript:;" class="prev"></a>
                <div id="scrollable" class="yulan">               	
                   <ul>		
					[#if product.productImages?has_content]
						[#list product.productImages as productImage]
							<li [#if productImage_index == 0]class="current"[/#if]><a href="javascript:;" rel="{gallery: 'gallery', smallimage: '${productImage.medium}', largeimage: '${productImage.large}'}"><img src="${productImage.thumbnail}" width="60" height="60"/></a></li>
						[/#list]
					[#else]
						<li class="current"><a href="javascript:;"><img src="${setting.defaultThumbnailProductImage}" /></a></li>
					[/#if]
				</ul>               
                </div>
                <a href="javascript:;" class="next"></a>
            </div>
            <div class="apply_infor">
                <ul>
                    <li id="xq_name">${product.name}[#if product.isGift] [${message("shop.product.gifts")}][/#if]</li>
                    <li id="xq_brief">
                        <p>①RO膜+超滤膜，过滤精度0.0001微米+0.01微米，放心到一点一滴；</p>
                        <p>②去除水碱重金属，出水可直饮！</p>
                        <p>③智能记忆芯，滤芯更换提醒功能！</p>
                    </li>
                    <li>新品尝鲜价：<span>¥${preSellRole.preSellPrice}</span></li>
                    <li>预售时间：<span> ${preSellRole.beginDate} </span>至 <span>${preSellRole.endDate}</span></li>
                    <li>已经有 <span>${preSellRole.qtyApplied}</span> 人已申请</li>
                 
                    <li id="xq_share">
                        <span style="width: 75px; color: #333;">分享到：</span>
                        <span><a href="#">
                            <img src="${base}/resources/shop/images/qq_ico.png" width="24" height="24" /></a></span>
                        <span><a href="#">
                            <img src="${base}/resources/shop/images/tx_weibo_ico.png" width="24" height="24" /></a></span>
                        <span><a href="#">
                            <img src="${base}/resources/shop/images/xinxi_ico.png" width="24" height="24" /></a></span>
                        <span><a href="#">
                            <img src="${base}/resources/shop/images/weibo_ico.png" width="24" height="24" /></a></span>
                        <span><a href="#">
                            <img src="${base}/resources/shop/images/renren_ico.png" width="24" height="24" /></a></span>
                    </li>
                    <li>
                        <input type="button" class="xq_button" value="立即抢购" /></li>
                </ul>
            </div>
        </div>
        <div class="apply_xiangqing_list">
        </div>
        <!--详情宣言-->
        <div class="apply_xiangqing">             
             <!--商城热卖 start --><!--商城热卖 start -->
            <div class="hot fa">
                <div class="zlmbt">
                    <a href="#">${message("shop.product.hotProduct")}</a>
                </div>
                <ul>
              	  [@product_list count = 5 orderBy="monthSales desc"]
						[#list products as product]
                    <li>
                        <a href="${base}${product.path}">
                            <img src="${product.image}" alt="Alternate Text" width="120" height="120"/>
                        </a>
                        <span title="${product.name}">${product.name}</span>
                        <b>
							¥${product.price}
						</b>
                    </li>
					[/#list]
				[/@product_list]
                </ul>
            </div>
            <!--商城热卖 end--><!--商城热卖 end-->

            <div class="xiangqing_infor fb">
            	
                <div class="xq_centent">
                    [#list products as product]
                	[#if product.introduction?has_content]
                	<!--
                	<img src="${product.introduction}" width="790" height="3183" />	-->
                	<div>
						${product.introduction}
					</div>
                	[/#if]
					[/#list]
                </div>
              
                <!--
                 [#list products as product]
                [#if product.introduction?has_content]
				<div id="introduction" name="introduction" class="introduction">
					<div class="title">
						<strong>${message("shop.product.introduction")}</strong>
					</div>
					<div>
						${product.introduction}
					</div>
				</div>
				[/#if]
				[/#list]
				-->
				</div>
            	
           	<!--您刚刚浏览过 start--><!--您刚刚浏览过 start-->
            <div class="record fa">
                <div class="zlmbt">
                    <a href="#">您刚刚浏览过</a>
                </div>
                <ul id="historyProduct">


                </ul>
            </div>
            <!--您刚刚浏览过 end--><!--您刚刚浏览过 end-->
        </div>
        <!---详情结束--->
    </div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>
