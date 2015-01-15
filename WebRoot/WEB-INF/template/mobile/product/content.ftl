<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0"/>
		<meta name="apple-mobile-web-app-capable" content="yes"/>
		<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
		<meta name="format-detection" content="telephone=no"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>${product.fullName}</title>
		<link href="${base}/resources/mobile/css/shangpinxingqing.css" rel="stylesheet" type="text/css" />
		<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/scroll.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.tools.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.jqzoom.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.validate.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
    	<style type="text/css">
		*{margin:0;padding:0;list-style-type:none;}
		a,img{border:0;}
		.scroll{width:480px;margin:20px auto 0 auto; position:relative;overflow:hidden;}
		.mod_01{float:left;width:100%；}
		.mod_01 img{display:block;width:100%;}
		.dotModule_new{padding:0 5px;height:11px;line-height:6px;-webkit-border-radius:11px;background:rgba(45,45,45,0.5);position:absolute;bottom:5px;right:281px;z-index:11;}
		#slide_01_dot{text-align:center;margin:3px 0 0 0;}
		#slide_01_dot span{display:inline-block;margin:0 3px;width:5px;height:5px;vertical-align:middle;background:#f7f7f7;-webkit-border-radius:5px;}
		#slide_01_dot .selected{background:#66ff33;}
		</style>
		<script type="text/javascript">
			$().ready(function() {
            	var productMap = {};
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
                //查询此商品是否已收藏（据此改变样式）
                $.ajax({
            		url: "${base}/mobile/member/favorite/isExist.jhtml?id=${product.id}",
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(message) {
            			if(message.type == "error"){
							$.message(message);
            			} else if(message.type == "success"){
            				
            			} else if(message.type == "warn"){
            				$('#addFavorite').removeClass("addFavorite").addClass("addFavoriteh");
            			}
					}
                }); 
				var $addCart = $("#addCart");
				// 加入购物车
				$addCart.click(function() {
					var status = true;
                	//判断购物车中是否有店铺商品
					$.ajax({
						url: "${base}/mobile/cart/queryCartIsExistStore.jhtml",
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
                			var quantity = 1;
							if (/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 0) {
								$.ajax({
									url: "${base}/mobile/cart/add.jhtml",
									type: "POST",
									data: {id: ${product.id}, quantity: quantity},
									dataType: "json",
									cache: false,
									success: function(message) {
										$.message(message);
									}
								});
							} else {
								$.message("warn", "${message("shop.product.quantityPositive")}");
							}
               			}
               		});
				});
				// 直接购买
				var $directPurchase = $("#directPurchase");
				$directPurchase.click(function(){
					var status = true;
                	//判断购物车中是否有店铺商品
					$.ajax({
						url: "${base}/mobile/cart/queryCartIsExistStore.jhtml",
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
                			var quantity = 1;
							if (/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 0) {
								$.ajax({
									url: "${base}/mobile/cart/add.jhtml",
									type: "POST",
									data: {id: ${product.id}, quantity: quantity},
									dataType: "json",
									cache: false,
									success: function(message) {
										document.location.href="${base}/mobile/cart/list.jhtml";
									}
								});
							} else {
								$.message("warn", "${message("shop.product.quantityPositive")}");
							}
               			}
               		});
				});
				
			    // 添加商品收藏
                var $addFavorite = $("#addFavorite");
				$addFavorite.click(function() {
					//判断当前是否存在用户
					if (!$.checkLogin()) {
						$.redirectLogin(window.location.href, "${message("shop.common.mustLogin")}");
						return false;
					}
					$.ajax({
						url: "${base}/mobile/member/favorite/add.jhtml?id=${product.id}",
						type: "POST",
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							$('#addFavorite').removeClass("addFavorite").addClass("addFavoriteh");
						}
					});
					return false;
				});
                [#if setting.isReviewEnabled && setting.reviewAuthority != "anyone"]
					// 发表商品评论
                	var $addReview = $("#addReview");
					$addReview.click(function() {
						if ($.checkLogin()) {
							return true;
						} else {
							$.redirectLogin("${base}/mobile/review/add/${product.id}.jhtml", "${message("shop.product.addReviewNotAllowed")}");
							return false;
						}
					});
				[/#if]
                // 点击数
				$.ajax({
					url: "${base}/mobile/product/hits/${product.id}.jhtml",
					type: "GET"
				});
			});
		 	function tabs(index) {
				var boxId = document.getElementById("big-box-" + index);
				if ("none" == boxId.style.display) {
					boxId.style.display = "inline";
					for (var i = 1; i < 5; i++) {
						if (i != index) {
							document.getElementById("big-box-" + i).style.display = "none";
						}
					}
				}
			}
			function toglic(n) {
				switch (n) {
					case 1 :
						document.getElementById("div1").setAttribute("class", "aaa");
						document.getElementById("div2").setAttribute("class", "bbb");
						document.getElementById("div3").setAttribute("class", "bbb");
						document.getElementById("div4").setAttribute("class", "bbb");
			
						break;
					case 2 :
						document.getElementById("div1").setAttribute("class", "bbb");
						document.getElementById("div2").setAttribute("class", "aaa");
						document.getElementById("div3").setAttribute("class", "bbb");
						document.getElementById("div4").setAttribute("class", "bbb");
			
						break;
					case 3 :
						document.getElementById("div1").setAttribute("class", "bbb");
						document.getElementById("div2").setAttribute("class", "bbb");
						document.getElementById("div3").setAttribute("class", "aaa");
						document.getElementById("div4").setAttribute("class", "bbb");
						break;
					case 4 :
						document.getElementById("div1").setAttribute("class", "bbb");
						document.getElementById("div2").setAttribute("class", "bbb");
						document.getElementById("div3").setAttribute("class", "bbb");
						document.getElementById("div4").setAttribute("class", "aaa");
						break;
				}
			
			}
			
			var ADAPT_CONFIG = {
            path: 'css/',
            dynamic: true,
            range: [
                '0px    to 760px  = mobile.min.css',
                '760px  to 980px  = 720.min.css',
                '980px            = 960.min.css'
            ]
        };
		
	$(function () {
		var h= $(document.body).width();
		 $(".scroll").css({"width":h,"height":"auto"});
         $("#sliderA").excoloSlider();
        });
		</script>
	</head>
	<body  style="background-color:#f8f8f8;">
		[#assign productCategory = product.productCategory /]
        
		<!--头部导航start-->
	    <div style="display:none;">
		<script type="text/javascript" src="http://pw.cnzz.com/c.php?id=1250590449&amp;l=2" charset="gb2312"></script>
		</div>
		<div class="dingdan-top">
			<table>
		    	<tr>
                    [#if product.store != null]
			        	<td>
			            	<a href="${product.store.indexMobileUrl}">
			            		<span class="dingdan-top-left"><img src="${base}/resources/mobile/images/home.png" style="width:90%"></span>
			            	</a>
			        	</td>
                    [#else]
	                    <td>
			            	<a href="${base}/mobile/index.jhtml">
			            		<span class="dingdan-top-left"><img src="${base}/resources/mobile/images/home.png" style="width:90%"></span>
			            	</a>
			        	</td>
                    [/#if]
		            <td class="dingdan-top-center" id="title"><!-- 标题栏 --></td>
		            <td>
		            	<a href="${base}/mobile/cart/list.jhtml">
		            		<span class="dingdan-top-right"><img src="${base}/resources/mobile/images/gwc.png" style="width:100%"></span>
		            	</a>
		            </td>
		        </tr>
			</table>
		</div>
		[#include "/mobile/include/titles.ftl"]
        <!--头部导航start-->
        
		<div class="quanbu-big">
		
			<div class="xingqing-big">
				<div class="scroll">
					<div class="slide_01" id="slide_01">
						[#if product.productImages?has_content]
							[#list product.productImages as productImage]
								<div class="mod_01"><a href="#"><img src="${productImage.thumbnail}"></a></div>
							[/#list]
						[#else]
								<div class="mod_01"><a href="#"><img src="${setting.defaultThumbnailProductImage}" /></a></div>
						[/#if]
					</div>
					<div class="dotModule_new">
						<div id="slide_01_dot"></div>
					</div>
				</div>
				<div class="nane-left-big">
					<div class="nane-left">
						${product.name}
						[#if product.isGift] [${message("shop.product.gifts")}][/#if]<!-- 产品名称 -->
	                    [#if product.store??]
							<span><a href="${base}/mobile/${product.store.id}.jhtml">
								店铺：${product.store.name}
							</a></span>
						[#else]
							<span>店铺：商城自营</span>
						[/#if]
						<p class="jiage">${currency(product.price, true)}元&nbsp;
							[#if setting.isShowMarketPrice]
								<del>${currency(product.marketPrice, true)}</del>
							[/#if]
						</p> <!-- 产品价格 -->
					</div>
				</div>
				<!-- 加入购物车s --> 
				[#if !product.isGift]
						[#if product.isOutOfStock]
							<div class="jiacar" id="addProductNotify">
								<input type="button" class="addProductNotify" value="${message("shop.product.addProductNotify")}" />
							</div>	
						[#else]
							<div class="jiacar" id="addCart">
								<span class="addCart">${message("shop.product.addCart")}</span>
							</div>
							<div class="goumai" id="directPurchase">
								<span class="addCart">立即购买</span>
							</div>
						[/#if]
				[/#if]
				<div id="addFavorite" class="addFavorite"> </div>
				<!-- 加入购物车e -->
			</div>
			
			<div class="shuju-big">
				<table>
					<tr>
						<td class="aaa" id="mydiv1">
							<a href="javascript:void(0)" onClick="tabs('1');"><span
								id="div1" onClick="toglic(1)">概述</span>
							</a>
						</td>
						<td class="bbb" id="mydiv2">
							<a href="javascript:void(0)" onClick="tabs('2');"><span
								id="div2" onClick="toglic(2)">详细</span>
							</a>
						</td>
						<td class="bbb" id="mydiv3">
							<a href="javascript:void(0)" onClick="tabs('3');"><span
								id="div3" onClick="toglic(3)">评论</span>
							</a>
						</td>
						<td class="bbb" id="mydiv4">
							<a href="javascript:void(0)" onClick="tabs('4');"><span
								id="div4" onClick="toglic(4)">参数</span>
							</a>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="xingqing-big" id="big-box-1">
				<div class="photo">
					<!-- 图片 -->
					[#if product.productImages?has_content]
						<img src="${product.productImages[0].medium}" />
					[#else]
						<img src="${setting.defaultMediumProductImage}" width="100%" height="100%"/>
					[/#if]
					<!-- 图片 -->
				</div>
			</div>
			
			<!-- 商品信息 -->
			<div id="big-box-2" style="display: none;">
				${product.introduction}
			</div>
			
			<!-- 评论 -->
			<div id="big-box-3" style="display: none;">
				[#if setting.isReviewEnabled]
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
					</div>
					<div class="handle">
						<a href="${base}/mobile/review/add/${product.id}.jhtml" id="addReview">${message("shop.product.addReview")}</a>
					</div>
					[/#if]
					<div class="big-box-2-big-box">
					[@review_list productId = product.id count = 5]
						[#if reviews?has_content]
							[#list reviews as review]
							<ul>
								<li class="big-box-2-A">
									<div  class="score${(review.score * 2)?string("0")}"></div>
								</li>
								<li class="big-box-2-B">${review.content}</li>
								<li class="big-box-2-C">
									[#if review.member??]
										${review.member.username}
									[#else]
										${message("shop.product.anonymous")}
									[/#if]
									${review.createDate?string("yyyy-MM-dd")}
								</li>
							</ul>
							[/#list]
						[#else]
							<p>
								${message("shop.product.noReview")}
							</p>
						[/#if]
					[/@review_list]
					</div>
				[/#if]
			</div>
			
			<!-- 参数 -->
			<div id="big-box-4" style="display: none;">
				[#if product.parameterValue?has_content]
					[#list productCategory.parameterGroups as parameterGroups]
						[#list parameterGroups.parameters as parameter]
							[#if product.parameterValue.get(parameter)??]
								<div class="big-box-3-big">
									<div class="big-box-3-big-A">${parameter.name}</div>
									<div class="big-box-3-big-B">${product.parameterValue.get(parameter)}</div>
								</div>
							[/#if]
						[/#list]
					[/#list]
				[/#if]
			</div>
		</div>
		<script type="text/javascript">
		if(document.getElementById("slide_01")){
			var slide_01 = new ScrollPic();
			slide_01.scrollContId   = "slide_01"; //内容容器ID
			slide_01.dotListId      = "slide_01_dot";//点列表ID
			slide_01.dotOnClassName = "selected";
			slide_01.arrLeftId      = "sl_left"; //左箭头ID
			slide_01.arrRightId     = "sl_right";//右箭头ID
			slide_01.frameWidth     = 480;
			slide_01.pageWidth      = 480;
			slide_01.upright        = false;
			slide_01.speed          = 10;
			slide_01.space          = 30; 
			slide_01.initialize(); //初始化
		}
		</script>
	</body>
</html>