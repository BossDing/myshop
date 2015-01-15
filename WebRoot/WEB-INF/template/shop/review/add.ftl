<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${product.name} ${message("shop.review.title")}[#if systemShowPowered] - Powered By SHOP++[/#if]</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/judge.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/review.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.rating.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>

<script type="text/javascript">
$().ready(function() {
	var $listForm = $("#listForm");
	var $pageNumber = $("#pageNumber");        
		var $pageSize = $("#pageSize");  
	var $reviewForm = $("#reviewForm");
	var $score = $("input.score");
	var $tips = $("#tips");
	var $content = $("#content");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $submit = $(":submit");
	[#if scrollSide?? && scrollSide == "1"]
	    $("html, body").scrollTop(0).animate({scrollTop: $("#historyProduct").offset().top});
	[/#if]
	// 评分
	$score.rating({
		callback: function(value, link) {
			$tips.text(message("${message("shop.review.tips")}", value));
		}
	});
	
	// 更换验证码
	$captchaImage.click(function() {
		$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	});
	//分页
	$.pageSkip = function(pageNumber) {
		$pageNumber.val(pageNumber);														
		$listForm.submit();
		return false;								
	}
	
	$(".anniu2").click(function(){
    $(this).parent().parent().parent().next().next().slideToggle("slow");
    });
    $(".reply_but2").click(function(){
    	$(this).parent().parent().slideToggle("slow");
    });
    $(".reply_but1").click(function(){
    var $this = $(this);
    var $forReview = $this.parent().parent().children(".forReview").val();
    var $replyContent = $this.parent().parent().children(".forReview").next().children(".replyContent").val();
    var $productId = ${product.id};
    $.ajax({
				url: "${base}/review/reply.jhtml",
				type: "POST",
				data: {forReviewId:$forReview ,content:$replyContent ,id:$productId},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						window.location.reload();
					}
				}
    });
 });
	// 表单验证
	$reviewForm.validate({
		rules: {
			content: {
				required: true,
				maxlength: 200
			},
			captcha: "required"
		},
		submitHandler: function(form) {
			$.ajax({
				url: $reviewForm.attr("action"),
				type: "POST",
				data: $reviewForm.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$submit.prop("disabled", true);
				},
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						setTimeout(function() {
							$submit.prop("disabled", false);
							location.href = "${product.id}.jhtml?scrollSide=1";
						}, 3000);
					} else {
						$submit.prop("disabled", false);
						[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("review")]
							$captcha.val("");
							$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
						[/#if]
					}
				}
			});
		}
	});

});
</script>
</head>
<body>
	[#include "/shop/include/header.ftl" /]
	<div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页<a></span>&gt;<span><a href="/member/index.jhtml">我的万家乐</a></span>&gt;<span><a href="/member/order/list.jhtml">我的订单</a></span>&gt;<span>订单评价</span></div>
		  <div class="kfdlb">
			  <ul>
			      <li>分享到：</li>
		          <li><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
		          <li><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
		          <li><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
		          <li><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
		          <li><a href="#"><img src="/resources/shop/images/xinxi_ico.png" width="17px" ;=""></a></li>
		          <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
			  </ul>
 			</div>   
	</div>
	<div class="container review">
		<!--<div class="span6">
			<div class="hotProductCategory">
				[@product_category_root_list]
					[#list productCategories as category]
						<dl>
							<dt>
							    <a class="name1" href="${base}${category.path}">${category.name}</a>
								<a class="name2" href="${base}${category.path}">${category.name}</a>
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
					[@product_list count = 6 orderBy="monthSales desc"]
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
		</div>
		-->
		<div class="big_pc">
		  <img src="[#if product.thumbnail??]${product.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" alt="${product.name}" />
		  </div>
		<div class="span18 last" style="width:700px; margin:0px auto;">
			<!--<div class="path">
				<ul>
					<li>
						<a href="${base}/">${message("shop.path.home")}</a>
					</li>
					<li>
						<a href="${base}${product.path}">${abbreviate(product.name, 30)}</a>
					</li>
					<li>${message("shop.review.title")}</li>
				</ul>
			</div>-->
			<table class="info" style="margin:20px;">
				<tr>
					<th rowspan="3">
						&nbsp;
					</th>
					<td>
						<a href="${base}${product.path}">${abbreviate(product.name, 60, "...")}</a>
					</td>
				</tr>
				<tr>
					<td>
						${message("Product.price")}: <strong>${currency(product.price, true, true)}</strong>
					</td>
				</tr>
				<tr>
					<td>
						[#if product.scoreCount > 0]
							<div>${message("Product.score")}: </div>
							<div class="score${(product.score * 2)?string("0")}"></div>
							<div>${product.score?string("0.0")}</div>
						[#else]
							[#if setting.isShowMarketPrice]
								${message("Product.marketPrice")}:
								<del>${currency(product.marketPrice, true, true)}</del>
							[/#if]
						[/#if]
					</td>
				</tr>
			</table>
			<form style="margin:20px;" id="reviewForm" action="${base}/review/save.jhtml" method="post">
				<input type="hidden" name="id" value="${product.id}" />
				<input type="hidden" name="orderItem" [#if orderItem??]value="${orderItem.id}"[/#if]>
				[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("review")]
					<input type="hidden" name="captchaId" value="${captchaId}" />
				[/#if]
				<div class="add">
					<table>
						<tr>
							<th>
								${message("Review.score")}:
							</th>
							<td>
								<input type="radio" name="score" class="score" value="1" title="${message("shop.review.score1")}" />
								<input type="radio" name="score" class="score" value="2" title="${message("shop.review.score2")}" />
								<input type="radio" name="score" class="score" value="3" title="${message("shop.review.score3")}" />
								<input type="radio" name="score" class="score" value="4" title="${message("shop.review.score4")}" />
								<input type="radio" name="score" class="score" value="5" title="${message("shop.review.score5")}" checked="checked" />
								<strong id="tips" class="tips">${message("shop.review.tips", 5)}</strong>
							</td>
						</tr>
						<tr>
							<th>
								${message("Review.content")}:
							</th>
							<td>
								<textarea id="content" name="content" class="text"></textarea>
							</td>
						</tr>
						[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("review")]
							<tr>
								<th>
									${message("shop.captcha.name")}:
								</th>
								<td>
									<span class="fieldSet">
										<input type="text" id="captcha" name="captcha" class="text captcha" maxlength="4" autocomplete="off" /><img id="captchaImage" class="captchaImage" src="${base}/common/captcha.jhtml?captchaId=${captchaId}" title="${message("shop.captcha.imageTitle")}" />
									</span>
								</td>
							</tr>
						[/#if]
						<tr>
							<th>
								&nbsp;
							</th>
							<td>
								<input type="submit" class="button" value="${message("shop.review.submit")}" />
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	
	<!--猜你喜欢-->
	<div class="content">
	
	<div id="clear"></div>
	<div id="historyProduct" class="historyProduct">
				<div class="title">猜你喜欢</div>
				<ul>
					[@product_list productCategoryId = product.productCategory.id count = 2]
					[#list products as product]
					<li>
						<a href="${product.path}">
							<img src="${product.image}">${product.name}
						</a>
						<span id="h_price">￥${product.price}</span> 
						<span id="h_comment">评论<i>[#if product.scoreCount??]${product.scoreCount}[#else]0[/#if]</i>条</span> 
					</li>
					[/#list]
					[/@product_list]
				</ul>
				<a href="/product/list/${product.productCategory.id}.jhtml" id="clearHistoryProduct" class="clearHistoryProduct">查看更多</a>
			</div>
	<div class="pl-big">
	<div class="pl-top">
		<div class="pl-a">好评率：</br><span>${positiveRate}</span></div>
		<div class="pl-b">
			<table cellpadding="0";cellspacing="0" style="width:100%;">
				<tr>
					<td align="left" class="pl-td1">综合评分：</td>
					<td align="left" class="pl-td1"><div class="pl-td11"><div class="pl-td12"></div></div></td>
				</tr>
				<tr>
					<td align="left" class="pl-td1">好评<span>${positiveRate}</span>：</td>
					<td align="left" class="pl-td1"><div class="pl-td21"><div class="pl-td22" style="width:${positiveRate};"></div></div></td>
				</tr>
				<tr>
					<td align="left" class="pl-td1">中评<span>${moderateRate}</span>：</td>
					<td align="left" class="pl-td1"><div class="pl-td21"><div class="pl-td32" style="width:${moderateRate};"></div></div></td>
				</tr>
				<tr>
					<td align="left" class="pl-td1">差评<span>${negativeRate}</span>：</td>
					<td align="left" class="pl-td1"><div class="pl-td21"><div class="pl-td42" style="width:${negativeRate};"></div></div></td>
				</tr>
			</table>
		</div>
		<!--
		<div class="pl-c">
			<ul>
				<li>您可对已购商品进行评价</li>
				<li><a href="#"><input name="" type="button" value="发表评价送积分" class="anniu"></a></li>
			</ul>
		</div>-->
	</div>
	</div>
	<div class="pl-xq">
	<form id="listForm" action="${product.id}.jhtml" method="get">
		<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
		<ul>
		[#list page.content as review]
			<li class="pl-xq-li1">
				<div class="xq-left">
					<div class="xq-1">${review.content}</div>
					<div class="xq-2"><span>${review.createDate}</span>
					
					<!--
					<span>颜色：钛灰</span><span>规格：12L</span>-->
					<span>
					[#list product.specifications as specification]${specification.name} : 
						[#list specification.specificationValues as specificationValue]
						[#if product.specificationValues?seq_contains(specificationValue)]
						[#if specification.type == "text"]${specificationValue.name}[#else]${specificationValue.name}[/#if]
						[/#if]
						[/#list] 
					[/#list]
					</span>
				
					<span><input name="" type="button" value="回复" class="anniu2"></span>
					[#list review.replyReviews as replyReview]
					<span>${replyReview.createDate}             ${replyReview.member.username}   :     ${replyReview.content}          </span>
					[/#list]
					</div>
				</div>
				<div class="xq-right">
					<ul>
						<li>${review.member.name}</li>
						<li><a href="#">${review.member.memberRank.name}</a></li>

					</ul>
				</div>
				<div class="panel">
					<input type="hidden" value="${review.id}" name="forReview" class="forReview">
					<p>
					   <textarea id="reply_text" name="replyContent" class="replyContent"></textarea> 
					   </p>
					 <p>
				     <input class="reply_but1"  type="button" value="确定" />
				     <input class="reply_but2"  type="button" value="取消" />
				    </p>			
					</div>
				
			</li>
			[/#list]
			<!--
			<li class="pl-xq-li1">
				<div class="xq-left">
					<div class="xq-1">全5分，很好的裤子，170cm,60KG,物流一如既往的快!</div>
					<div class="xq-2"><span>14.08.23</span><span>颜色：钛灰</span><span>规格：12L</span><span><input name="" type="button" value="回复" class="anniu2"></span></div>
				</div>
				<div class="xq-right">
					<ul>
						<li>憧憬</li>
						<li><a href="#">普通会员</a></li>

					</ul>
				</div>
			</li>-->
		
		</ul>
		</form>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
			[#include "/shop/include/pagination.ftl"]
		[/@pagination]
	</div>
</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>