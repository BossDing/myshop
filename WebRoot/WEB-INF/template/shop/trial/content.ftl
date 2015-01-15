<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>申请详情</title>
<link href="${base}/resources/shop/css/sysq_xiangqing.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
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


});
</script>
</head>
<body style="background-color:#f8f8f8;">
[#include "/shop/include/header.ftl" /]
	[#if trial.products?has_content]
   		[#list trial.products as product]
   			[#if product_index==0] 
  <div class="container productContent">
	<div class="apply">
			<div class="apply_list"></div>
   
   			<div class="productImage" style="margin:20px">
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
								<a[#if productImage_index == 0] class="current"[/#if] href="javascript:;" rel="{gallery: 'gallery', smallimage: '${productImage.medium}', largeimage: '${productImage.large}'}"><img src="${productImage.thumbnail}" title="${productImage.title}"  width="60" height="60"/></a>
							[/#list]
						[#else]
							<a class="current" href="javascript:;"><img src="${setting.defaultThumbnailProductImage}" /></a>
						[/#if]
					</div>
				</div>
				<a href="javascript:;" class="next"></a>
			</div>  
   <div class="apply_infor">
     <ul>
     <li id="xq_name" style="margin-top:44px;">${trial.name}</li>
     <li id="xq_brief">
	${trial.title}
</li>
     <li>市场价格：<span>￥${trial.marketprice}</span></li>
     <li>申请时间：<span>${trial.beginDate} </span>至 <span>${trial.endDate}</span></li>
     <li>免费试用数量：<span>${trial.qtylimit}</span> 台</li>
     [#if (trial.appliernum>0)]<li>已经有 <span>${trial.appliernum}</span> 人已申请</li>[#else]<li><span>快来申请吧！</span></li>[/#if]
     <li id="xq_share">
     	 <span style="width:75px; color:#333;">分享到：</span>
         <span><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="24" height="24" /></a></span>
         <span><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="24" height="24" /></a></span>
         <span><a class="jiathis_button_weixin"  title="分享到微信"><img src="${base}/resources/shop/images/xinxi_ico.png" width="24" height="24" /></a></span>
         <span><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="24" height="24" /></a></span>
         <span><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="24" height="24" /></a></span>
         <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
      </li>
      <li><input type="button" class="xq_button" value="立即申请" onclick="javascript: window.location.href='${base}/trial/toApply/${trial.id}.jhtml'"/></li>
     </ul>
     </div>
   </div>
<div class="apply_xiangqing_list">
    
    </div>
    <!--详情宣言-->
<div class="apply_xiangqing">

<!--申请宣言-->
   <div class="appl_xuanyan">
      <dl>
        <dt>申请宣言</dt>
         [#if trial.trialApplys?has_content]
         	[#list trial.trialApplys as trialApply]
         	[#if trialApply_index <6]
        <dd>
           <p>
               <span class="name_x">${trialApply.receiver?substring(0,1)} * *</span>
                 [#list areas as area]
	             [#if trialApply_index==area_index]
	             <span class="name_x">${area}</span>
	             [/#if]
	             [/#list]
               <span class="time">${trialApply.applyDate}</span>
               </p>
           <p class="xq_xy_say">[#if trialApply.applyReason?length>20]
		               	${trialApply.applyReason?substring(0,40)}...[#else]${trialApply.applyReason}。[/#if]</p>
           </dd>
           	[/#if]
               [/#list]
          [/#if]
        </dl>
      </div> 
      
      <!--申请宣言结束-->
      
   <div class="xiangqing_infor">
   	   <div class="xq_centent">
         ${product.introduction}
       </div>
   </div> 
      
   <div class="xiangqing_lit"></div>  
   </div>
   </div>
   [/#if]
   [/#list]
   [/#if] 
    <!---详情结束--->
    [#include "/shop/include/footer.ftl" /]
</body>
</html>
