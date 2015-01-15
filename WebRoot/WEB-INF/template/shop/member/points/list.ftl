<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/common.css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/zhifen.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>
<title>万家乐商城</title>
<script type="text/javascript"> 
	$(function(){
		var $pointForm = $("#pointForm"); 
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
				$pointForm.submit();
				return false;								
		}
	});
</script>
</head>
<body>
<!--头部-->
[#include "/shop/member/include/header.ftl" /]

<!--头部结束--> 

<!--产品试用开始-->
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>><span><a href="/member/index.jhtml">个人中心</a></span>><span>积分查询</span></div>
  <div class="kfdlb">
      <ul>
          <li>分享到：</li>
          <li><a href="#"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
          <li><a href="#"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
          <li><a href="#"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
          <li><a href="#"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
      </ul>
  </div>
</div>

<!--个人资料开始-->

<div class="ziliao-big">
  <div class="ziliao-left">[#include "/shop/member/include/navigation.ftl" /]</div>
  <div class="ziliao-right">
    <div class="ziliao-right-1">
      <div class="right-11">积分查询</div>
      <div class="right-12"><span>当前有效积分<a href="#">${member.point}</a></span></div>
      <div class="zhifen">
      <form id="pointForm" action="list.jhtml" method="get">
      	<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
          <table cellpadding="0";cellspacing="0">
            <tr class="dks">
              <td align="center" class="td-a">日期</td>
              <td align="center" class="td-a">获取积分</td>
              <td align="center" class="td-a">消耗积分</td>
              <td align="center" class="td-b">摘要</td>
            </tr>
            [#list page.content as pointsWater]
            
	            [#if pointsWater.order!=null]
	            	[#list pointsWater.order.orderItems as orderItem]
	            	<tr class="dks">
		              <td align="center" class="td-a">${pointsWater.createDate}</td>
		              <td align="center" class="td-a">+${orderItem.quantity*orderItem.product.point}</td>
		              <td align="center" class="td-a">0</td>
		              <td align="center" class="td-b">购买${orderItem.quantity}个${orderItem.name}</td>
	            	</tr>
	            	[/#list]           	
	            [/#if]
	            [#if pointsWater.order == null]
	            	  <td align="center" class="td-a">${pointsWater.createDate}</td>
		              <td align="center" class="td-a">
			              [#if pointsWater.points_stat ==1]
			              	+${pointsWater.points}
			              [#elseif pointsWater.points_stat ==2]
			              	0
			              [/#if]
		              </td>
		              <td align="center" class="td-a">
			              [#if pointsWater.points_stat ==1]
			              	0 
			              [#elseif pointsWater.points_stat ==2]
			              	-${pointsWater.points}
			              [/#if]
		              </td>
		              <td align="center" class="td-b">${pointsWater.rulename}</td>
	            	</tr>
            	[/#if]
            [/#list]
          </table>
          </form>
      </div>
      <div class="yema">
      <!--
        <ul>
            <li class="yema-1">1/2</li>
            <li class="yema-2"><a href="#"><<上一页</a></li>
            <li class="yema-1"><a href="#">1</a></li>
            <li class="yema-1"><a href="#">2</a></li>
            <li class="yema-3"><a href="#">下一页>></a></li>
        </ul>
        -->
        
         [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
			[#include "/shop/include/pagination.ftl"]
		[/@pagination]
        
    </div>
      <div class="djso"><a href="#">了解积分使用规则></a></div>
    </div>
  </div>

</div>

<!--个人资料完-->
<!--底部开始-->
[#include "/shop/include/footer.ftl" /]
</body>
</html>
