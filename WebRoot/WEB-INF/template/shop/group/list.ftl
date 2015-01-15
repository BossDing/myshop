<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/common.css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/tuangou.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<title>欢迎光临万家乐官方商城-厂家直销、最低价格、品质保障、货到付款、配送及时、放心服务、轻松购物 </title>
<script type="text/javascript"> 
	$(function(){
		var $productForm = $("#productForm"); 
		var $pageNumber = $("#pageNumber");        
		var $pageSize = $("#pageSize");  
		
        $("#classify_act").mouseover(function(){
            $("#classify").slideDown();
         }); 
		 
		 $("#classify").mouseleave(function(){
            $(this).slideUp();
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp();
         }); 
         
         $.pageSkip = function(pageNumber) {
				$pageNumber.val(pageNumber);														
				$productForm.submit();
				return false;								
		}
		
		
		
	});
</script>

</head>
<body>
<!--头部-->
[#include "/shop/include/header.ftl" /]

<!--头部结束--> 

<!--产品试用开始-->
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="${base}/">首页</a></span>><span>团购中心</span></div>
  <div class="kfdlb">
      <ul>
          <li>分享到：</li>
          <li><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
          <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
      </ul>
  </div>
</div>

[@ad_position adname="团购中心 - banner图" ]
	[#if adPosition.ads?size > 0]
	<div class="tg_banner">
	      <!--<img src="${base}/resources/shop/images/tgbanner.jpg";>-->
	      [@ad_position adname="团购中心 - banner图" ]
	      [/@ad_position]
	</div>
	[/#if]
[/@ad_position]
<!--团购中心开始-->

<div class="tuangou-big">
	  <form id="productForm" action="${base}/group/list.jhtml" method="get">
        <input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
	  	  <div class="tuan-top">
		  	[#if page.content?has_content]
			    [#list page.content as group]
			    	[#if group.products?has_content]
			    		[#list group.products as product]
						    <div class="tuan-1">
							      <div class="tuan-12">
							      		<img src="${group.path}"  />
							      		<div class="jia">
										<div class="name1">${product.name}</div>
										<div class="name2">${(product.seoKeywords)!""}</div>
							      				<span class="jia-1">活动价：</span><span class="jia-2">${group.purchasePrice}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							      				<span class="jia-1">定金：</span><span class="jia-2">${group.previousPrice}</span></br>
							      				已参团人数：<span class="jia-3">${group.buycount}</span>
							      		</div>
							      </div>
							      <!--
							      <div class="tuan-11" style="height:76px;"></br>
								      订金时间：<span style="font-weight:bold;">${group.prbegindate?string("yyyy-MM-dd HH:mm:ss")}</span>
								       到 <span style="font-weight:bold;">${group.prenddate?string("yyyy-MM-dd HH:mm:ss")}</span>。 数量有限，预购从速！
							       </div>
							       -->
							       
							       <div class="xq-251"><img src="${base}/resources/shop/images/time.png" width="20px";>&nbsp;剩余时间:</div>
			            		   <!--<div class="xq-252"><span>38</span>天<span>15</span>小时<span>23</span>分<span>50</span>秒</div>-->
			            		   <div class="xq-252" id="haveTime${group.id}" [#if group.begindate??] beginTimeStamp="${group.begindate?long}"[/#if][#if group.enddate??] endTimeStamp="${group.enddate?long}"[/#if]>
			            		   </div>
			            		   <script type="text/javascript">
			            		   		promotionInfo();
	         							setInterval(promotionInfo, 1000);
			            		   		function promotionInfo(){
								         	$('#haveTime${group.id}').each(function(){
								         		var $this = $(this);
								         		var beginDate = $this.attr("beginTimeStamp") != null ? new Date(parseFloat($this.attr("beginTimeStamp"))) : null;
								         		var endDate = $this.attr("endTimeStamp") != null ? new Date(parseFloat($this.attr("endTimeStamp"))) : null;
								         		if(beginDate == null || beginDate <= new Date()){
								         			if(endDate != null && endDate >= new Date()){
								         				var time = (endDate - new Date()) / 1000;
								         				$this.html("<span>"+Math.floor(time / (24 * 3600)) + "</span>天<span>" + Math.floor((time % (24 * 3600)) / 3600) + "</span>时"
								         						+"<span>"+ Math.floor((time % 3600) / 60) +"</span>分<span>"+Math.floor((time % 60))+"</span>秒");
								         			}else if(endDate != null && endDate < new Date()){
								         				//活动已结束
								         				$this.html("<span>${message("shop.index.ended")}</span>");
								         				//$("#buyNow").removeAttr('href');
								         				//$("#addCart").removeAttr('href');
								         			}
								         		}
								         	});
								         }
			            		   </script>
							      <div class="tuan-13">
								        <div class="tuan-13-left">
									          <div class="tuan-131">
									          		<!--
									          		${group.wantcount1}人&nbsp;&nbsp;&nbsp;&nbsp;${group.wantcount2}人&nbsp;&nbsp;&nbsp;&nbsp;
									          		${group.wantcount3}人&nbsp;&nbsp;&nbsp;&nbsp;${group.wantcount4}人&nbsp;&nbsp;&nbsp;&nbsp;
									          		${group.wantcount5}人&nbsp;&nbsp;&nbsp;人数
									          		-->
									          		<div>人数</div>
										            <div>${group.wantcount1}</div>
										            <div>${group.wantcount2}</div>
										            <div>${group.wantcount3}</div>
										            <div>${group.wantcount4}</div>
										            <div>${group.wantcount5}</div>
									          </div>
									          <div><img src="${base}/resources/shop/images/tuan-13.png" width="227px"></div>
									          <div class="tuan-131">
									          		<!--
									          		${group.purchasePrice1}&nbsp;&nbsp;&nbsp;${group.purchasePrice2}&nbsp;&nbsp;&nbsp;
									          		${group.purchasePrice3}&nbsp;&nbsp;&nbsp;${group.purchasePrice4}&nbsp;&nbsp;&nbsp;
									          		${group.purchasePrice5}&nbsp;&nbsp;&nbsp;价格
									          		-->
									          		<div>价格</div>
										            <div>${group.purchasePrice1}</div>
										            <div>${group.purchasePrice2}</div>
										            <div>${group.purchasePrice3}</div>
										            <div>${group.purchasePrice4}</div>
										            <div>${group.purchasePrice5}</div>
									          </div>
								        </div>
								        <div class="tuan-13-right">
									          <div class="tuan-13-q"><input name="" type="button" value="参 团" class="anniu1 detail_button${group.id}_${product.id}"></div>
									          <div><input name="" type="button" value="了解产品" class="anniu2 detail_button${group.id}_${product.id}"></div>
								        </div>
							      </div>
						    </div>
						    <script type="text/javascript">
									$('.detail_button${group.id}_${product.id}').click(function(){
										//alert('detail_button'+${group.id});
										window.location.href = "content/${group.id}/${product.id}.jhtml";
									});
							</script>
						[/#list]
					[#else]	
					[/#if]	
			    [/#list]
		    [/#if]
	  </div>
	  <!--
	  <div class="yema">
	        <ul>
	            <li class="yema-1">1/2</li>
	            <li class="yema-2"><a href="#"><<上一页</a></li>
	            <li class="yema-1"><a href="#">1</a></li>
	            <li class="yema-1"><a href="#">2</a></li>
	            <li class="yema-3"><a href="#">下一页>></a></li>
	        </ul>
	  </div>
	  -->
	  [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
			[#include "/shop/include/pagination.ftl"]
		[/@pagination]
	</form>	
</div>

<!--团购中心完-->
<!--底部开始-->
[#include "/shop/include/footer.ftl" /]



</body>
</html>
