<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.jqzoom.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/sy_content.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link href="${base}/resources/shop/css/wjl_details.css" rel="stylesheet" type="text/css"/>
<title>试用申请</title>
<script type="text/javascript">
$().ready(function() {
	var $zoom = $("#zoom");
	var $scrollable = $("#scrollable");
	var $thumbnail = $("#scrollable a");
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

<body>
[#include "/shop/include/header.ftl" /]
[#assign product = report.product ]
<div class="content">
<div class="jindu">
  <ul>
     <li>新品免费试用流程</li>
     <li><img src="${base}/resources/shop/images/sy_bg_tx.png"/>提交试用申请</li>
     <li><img src="${base}/resources/shop/images/jiantou_ico.png" /></li>
     <li><img src="${base}/resources/shop/images/sy_bg_dd.png" />等待审核</li>
     <li><img src="${base}/resources/shop/images/jiantou_ico.png" /></li>
     <li><img src="${base}/resources/shop/images/sy_bg_ps.png"/>获得试用商品</li>
     <li><img src="${base}/resources/shop/images/jiantou_ico.png" /></li>
     <li><img src="${base}/resources/shop/images/sy_bg_tj.png" />提交试用报告</li>
     </ul>
  </div>


   <!--左边试用宣言，试用报告-->
 <div class="le">
  <div class="about_goods">
    <div class="goods_pic">
    <!--
    <img src="imgaes/bgxq_goods_pic.jpg" />-->
    [#if product.productImages?has_content]
					<a id="zoom" href="${product.productImages[0].large}" rel="gallery">
						<img class="medium" src="${product.productImages[0].medium}" />
					</a>
				[#else]
					<a id="zoom" href="${setting.defaultLargeProductImage}" rel="gallery">
						<img class="medium" src="${setting.defaultMediumProductImage}" />
					</a>
				[/#if]
    </div> 
    <!--
    <ul>
        <li class="con"><img src="${base}/resources/shop/images/bgxq_goods_pic1.jpg" /></li>
        <li><img src="${base}/resources/shop/images/bgxq_goods_pic2.jpg" /></li>
        <li><img src="${base}/resources/shop/images/bgxq_goods_pic3.jpg" /></li>
        <li><img src="${base}/resources/shop/images/bgxq_goods_pic4.jpg" /></li>
        </ul>  -->
        <a href="javascript:;" class="prev"></a>
					<ul id="scrollable" class="scrollable">
						[#if product.productImages?has_content]
							[#list product.productImages as productImage]
								<li><a[#if productImage_index == 0] class="current"[/#if] href="javascript:;" rel="{gallery: 'gallery', smallimage: '${productImage.medium}', largeimage: '${productImage.large}'}"><img src="${productImage.thumbnail}" title="${productImage.title}" /></a></li>
							[/#list]
						[#else]
							<li><a class="current" href="javascript:;"><img src="${setting.defaultThumbnailProductImage}" /></a></li>
						[/#if]
					</ul>
				<a href="javascript:;" class="next"></a>
        
     <div class="nam"> 
	<p>${product.name}</p> 
    <!--<p>恒温精控 多模快速热水器 </p>-->
     </div> 
     <div class="price">
      预计市场价：<i>￥${product.marketPrice}</i>
      </div>  
    <input  class="about_goods_details" type="button" />
    </div>
    <!--试用申请，end-->
    
    
    <!--试用报告-->
   <div class="report">
    <dl>
    <dt>试用报告</dt>
    <dd class="t1">${report.trialApply.product.name}</dd>
 	[#list reports as TrialReport]
    <dd>
      <div class="d_name">${TrialReport.trialApply.receiver?substring(0,1)}* * 
      [#if TrialReport.area.parent.parent??]
      ${TrialReport.area.parent.parent?substring(0,2)} 
      [#else]${TrialReport.area.parent?substring(0,2)} 
      [/#if]
      ${TrialReport.createDate}</div>
      <div class="d_infor">${TrialReport.trialApply.applyReason}</div>
      <div class="r_more"><A href="reportContent.jhtml?reportId=${TrialReport.id}">查看详情》》</A></div>
      <div class="r_pic">
      	 [#if TrialReport.image1??]
         <img src="${TrialReport.image1}" />
         [/#if]
         [#if TrialReport.image2??]
         <img src="${TrialReport.image2}"  />
         [/#if]
          </div>
      </dd>
      [/#list]
      <!--
      <dd>
      <div class="d_name">陈**重庆 2014/08/27</div>
      <div class="d_infor">作为万家乐老用户，总希望能尝试万家乐的新产品。外观看来这款热水器非常高档，马上半斤新家，希望可以用上这高大上的产品。</div>
      <div class="r_more"><A href="#">查看详情》》</A></div>
      <div class="r_pic">
         <img src="${base}/resources/shop/images/bg_pc1.jpg" />
         <img src="${base}/resources/shop/images/bg_pc2.jpg"  />
          </div>
      </dd>
      -->
    </dl>
    </div> 
   </div>
   <!--试用报告，end-->
   
   <!--产品特点-->
  <div class="details">
   <div class="tittle">${report.trialApply.product.name}  试用报告  <i>${report.createdBy}</i> <span>${report.createDate}</span></div>
   <div class="report_infor" >
       <dl>
          <dt>
            <p>【试用用户】：<span>${report.createdBy}</span></p>
            <p>【用户区域】：<span>${report.area.fullName}</span></p>
            <p>【试用商品】：<span><a href="${base}/trial/toApply/${report.trial.id}.jhtml">${siteUrl}/trial/toApply/${report.trial.id}.jhtml</a></span></p>
            <p>【商品链接】：<span><a href="${report.product.path}">${siteUrl}${report.product.path}</a></span></p>
          </dt>
          <dd>
            <div class="report_tt">
            【物流状况】${report.logistics}
            </div>
             <div class="report_pic">
               <img src="imgaes/baogao_pc1.jpg"  />
               <img src="imgaes/baogao_pc2.jpg"  />
               </div>
             </dd>
             <dd>
            <div class="report_tt">
            【产品外观】${report.appearance}
            </div>
             <div class="report_pic">
               <img src="${base}/resources/shop/images/baogao_pc1.jpg"  />
               <img src="${base}/resources/shop/images/baogao_pc2.jpg"  />
               </div>
             </dd>
             <dd>
            <div class="report_tt">
            【主要功能】${report.majorFunction}
            </div>
             <div class="report_pic">
               <img src="${base}/resources/shop/images/baogao_pc1.jpg"  />
               <img src="${base}/resources/shop/images/baogao_pc2.jpg"  />
               </div>
             </dd>
             <dd>
            <div class="report_tt">
           【使用心得】${report.useingExperience}
            </div>
             <div class="report_pic">
               <img src="${base}/resources/shop/images/baogao_pc1.jpg"  />
               <img src="${base}/resources/shop/images/baogao_pc2.jpg"  />
               </div>
             </dd>
          </dl>
       </div>
    </div>
     
</div>
[#include "/shop/include/footer.ftl" /]
</body>
</html>
