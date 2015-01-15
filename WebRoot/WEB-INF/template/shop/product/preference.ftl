<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${setting.siteName} - 特惠促销</title>
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/tehui.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/seek.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/swfobject_modified.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.spinner.js"></script>

<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.jqzoom.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect_X.js"></script>


	<script type="text/javascript">
		
	$(function(){

		var $promotionProductInfo = $("#promotionProduct .info2");
		$("#classify_act").mouseover(function(){
		    $("#classify").slideDown();
		 }); 
			 
			 $("#classify").mouseleave(function(){
		    $(this).slideUp();
		 }); 
			 
			 $("#classify_act").mouseleave(function(){
		    $("#classify").slideUp();
		 });
		 
		 function promotionInfo() {
			$promotionProductInfo.each(function() {
				var $this = $(this);
				var beginDate = $this.attr("beginTimeStamp") != null ? new Date(parseFloat($this.attr("beginTimeStamp"))) : null;
				var endDate = $this.attr("endTimeStamp") != null ? new Date(parseFloat($this.attr("endTimeStamp"))) : null;
				if (beginDate == null || beginDate <= new Date()) {
					if (endDate != null && endDate >= new Date()) {
						var time = (endDate - new Date()) / 1000;
						$this.html("<em>" + Math.floor(time / (24 * 3600)) + "<\/em> ${message("shop.index.day")} <em>"
						+ Math.floor((time % (24 * 3600)) / 3600) + "<\/em> ${message("shop.index.hour")} <em>"
						+ Math.floor((time % 3600) / 60) + "<\/em> ${message("shop.index.minute")} "
						+ (Math.floor(time) - Math.floor((time/(24 * 3600)))*24*3600 - Math.floor((time % (24 * 3600)) / 3600)*3600 - Math.floor((time % 3600) / 60)*60)+ "<\/em> 秒");
					} else if (endDate != null && endDate < new Date()) {
						$this.html("${message("shop.index.ended")}");
					} else {
						$this.html("${message("shop.index.going")}");
					}
				}
			});
		}
	
		promotionInfo();
		setInterval(promotionInfo, 1000);

		
	});
	
	//收藏商品
		function addFavorite(product_id){
			
			[#if !member??]
				window.location.href = "${base}/login.jhtml";
				return false;
			[/#if]
			$.ajax({
				url: "${base}/member/favorite/add.jhtml",
				type: "POST",
				data: {id: product_id},
				dateType: "json",
				cache: false,
				success: function(message){
					$.message(message);
				}
			});
			
		}
	</script>
	<style>
	.tehui-xq{ margin-bottom:10px; position: relative!important;}
	.tehui-xq1-c .tehui-xq1-c1{
		width: 104px;
		height: 34px;
		background: url(${base}/resources/shop/images/shop.png) no-repeat;
		font-size: 15px;
		font-weight: bold;
		line-height: 34px;
		text-align: center;
		
	}
	.tehui-xq1-c .tehui-xq1-c1 a{
		color: #fff;
		width: 100%;
		height: 100%;
		display: block;
	}
	.tehui-xq1-c .tehui-xq1-c1 a:hover{
		background: url(${base}/resources/shop/images/shop-hover.png) no-repeat;
	}
	.tehui-xq1-c .tehui-xq1-c2{
		width: 104px;
		height: 34px;
		background: url(${base}/resources/shop/images/shou.png) no-repeat;
		font-size: 15px;
		font-weight: bold;
		line-height: 34px;
		text-align: center;
		margin-left: 20px;
	}
	.tehui-xq1-c .tehui-xq1-c2 a{
		color: #fff;
		width: 100%;
		height: 100%;
		display: block;
	}
	.tehui-xq1-c .tehui-xq1-c2 a:hover{
		background: url(${base}/resources/shop/images/shou-hover.png) no-repeat;
	}
	.tehui-xq1-c{
		height: 34px;
		margin-top: 20px;
	}
	.tehui-xq1-c div{
		float: left;
	}
	[@promotion_list hasEnded = false count = 6]
		[#list promotions as promotion]
			[@product_list promotionId = promotion.id count = 6]
				[#list products as product]
					.tehui-xq .tehui-xq${product.id}{
						height: 408px;
						background: url(${promotion.imgpath}) no-repeat;
						margin-bottom: 20px;
					}
				[/#list]
			[/@product_list]
		[/#list]
	[/@promotion_list]
	
	</style>
</head>

    
<body>
  [#include "/shop/include/header.ftl" /]
  <!--特惠促销开始-->
<div id="index_place">您的位置：<span><a href="${base}/">首页</a></span>><span>特惠促销</span></div>

<div class="tehui-big">
    <div class="tehui-xq1" id="promotionProduct" >
        [#assign xy=0 /]
		[@promotion_list hasEnded = false count = 6]
			[#list promotions as promotion]
			  [#if promotion.type?? && promotion.type==1]
				[@product_list promotionId = promotion.id count = 6]
					[#list products as product]
					[#assign xy=xy+1 /]
						<div class="tehui-xq" style="background: url(${promotion.imgpath}) no-repeat;">
						<!--<div class="tehui-xq" [#if xy%2==0]style="background: url(/resources/shop/images/th_bj_p1.jpg);"[#else]style="background: url(/resources/shop/images/th_bj_p2.jpg);"[/#if]>-->
						<!--<img src="${product.image}">-->
						  <div class="tehui-xq1-right">
						  	<div>${product.name}</div>
						    <div class="tehui-xq1-a"><div class="redline"><span>${currency(product.marketPrice, true)}</span></div>${currency(promotion.promotionPrice, true)}</div>
						    <div class="tehui-xq1-b">已售：&nbsp;<span>${product.sales}台</span></div>
						    <div class="tehui-xq1-b">剩余：&nbsp;<span class="info2"[#if promotion.beginDate??] beginTimeStamp="${promotion.beginDate?long}"[/#if][#if promotion.endDate??] endTimeStamp="${promotion.endDate?long}"[/#if]>
													[#if promotion.beginDate??]
														<em>${promotion.beginDate?string("yyyy-MM-dd HH:mm")}</em>
													[/#if]
												</span></div>
						    <div class="tehui-xq1-c">
							<div class="tehui-xq1-c1"><a href="javascript:addCart(${product.id})">购买</a></div>
							<div class="tehui-xq1-c2"><a href="javascript:addFavorite(${product.id})">加入收藏</a></div>
						    </div>
						  </div>
						</div>	
					[/#list]
				[/@product_list]
				[/#if]
			[/#list]
		[/@promotion_list]

       
    <div >
        <div class="serp  fb">
    <!--搜索结果分页 start --><!--搜索结果分页 start -->
                [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
					[#if totalPages > 0]
				<input type="hidden" id="totalPages" name="totalPages" value="${page.totalPages}" />
                <div class="fenye fb">
                	
                	[#if hasPrevious]
						<a class="syysd" href="[@pattern?replace("{pageNumber}", "${previousPageNumber}")?interpret /]">
							<b></b>上一页
						</a>
					[#else]
						<span class="syys">
							<b></b>上一页
						</span>
					[/#if]
                    
                    
                    [#list segment as segmentPageNumber]
						[#if segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1]
							<a class="shenglue">...</a>
						[/#if]
						[#if segmentPageNumber != pageNumber]
							<a href="[@pattern?replace("{pageNumber}", "${segmentPageNumber}")?interpret /]">${segmentPageNumber}</a>
						[#else]
							<a class="dyy">${segmentPageNumber}</a>
						[/#if]
						[#if !segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1]
							<a class="shenglue">...</a>
						[/#if]
					[/#list]
                    
                    
                   [#if hasNext]
						<a class="xyys" href="[@pattern?replace("{pageNumber}", "${nextPageNumber}")?interpret /]">
							<b></b>下一页
						</a>
					[#else]
						<span class="xyysd">
							<b></b>下一页
						</span>
					[/#if]
                    
                    <!--跳转-->
                    <div class="tiaozhuan">
                        向第
                        <input type="text"  name="" value="" id="insertPage"/>
                        页
                    	<input type="button" name="name" value="跳转 " class="tzts" id="toPage"/>
                    </div>
                </div>
                <!--搜索结果分页  end--><!--搜索结果分页  end-->
		[/#if]
                [/@pagination]
		</div>
    </div>
</div>

<!--特惠促销完--> 
  [#include "/shop/include/footer.ftl" /]
</body>
</html>
