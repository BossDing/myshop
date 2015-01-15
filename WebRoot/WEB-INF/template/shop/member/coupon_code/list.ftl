<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>优惠券</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
    <link href="${base}/resources/shop/css/sns.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/youhuiquan.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv_order.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {
	
							      
						 
});   
  $('.anniu').click(function(){
    $('.right-15').slideToggle();  
  })
  $(function(){
        $("#classify_act").mouseover(function(){
            $("#classify").slideDown();
         }); 
		    
		 $("#classify").mouseleave(function(){
            $(this).slideUp();
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp();
         }); 
	});
   
</script>					
</head>			
<body>																									
	[#assign current = "orderList" /]			
	[#include "/shop/include/header.ftl" /]
	<div class="content">
	<div class="container member">				
		<div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<a href="/"><span>首页</span></a>><a href="${base}/member/index.jhtml"><span>个人中心</span></a>><span>优惠券</span></div>
		  <div class="kfdlb">
			  <ul>
				  <li>分享到：</li>
				  <li><a href="#"><img src="/resources/shop/images/qq_ico.png" width="17px";></a></li>
				  <li><a href="#"><img src="/resources/shop/images/renren_ico.png" width="17px";></a></li>
				  <li><a href="#"><img src="/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
				  <li><a href="#"><img src="/resources/shop/images/weibo_ico.png" width="17px";></a></li>
			      <li><a href="#"><img src="/resources/shop/images/xinxi_ico.png" width="17px";></a></li>
			  </ul>
		  			</div>   
				</div>
		</div>
			<div class="ziliao-big">  
[#include "/shop/member/include/navigation.ftl" /]					
  <div class="ziliao-right">
    <div class="ziliao-right-1">
      <div class="right-11">优惠券</div>
      <div class="right-12">
        <ul>
          <li class="you-1">全部<span>${all}</span></li>
          <li>未使用<span>${unused}</span></li>
          <li>已使用<span>${used}</span></li>   
          <li>已过期<span>${expired}</span></li>					
        </ul>
      </div>
      <div class="zhifen">
          <table cellpadding="0";cellspacing="0">
            <tr class="dks  bei">
              <td align="center" class="td-a">优惠券名称</td>				
              <td align="center" class="td-b">编码</td>
              <td align="center" class="td-a">使用条件</td>
              <td align="center" class="td-a">有效时间</td>
              <td align="center" class="td-a">状态</td>
            </tr>
            [#list page.content as couponCode]
            <tr class="dks">
              <td align="center" class="td-a">${couponCode.coupon.name}</td>
              <td align="center" class="td-b">${couponCode.code}</td>
              <td align="center" class="td-a">${couponCode.coupon.prefix}</td>
              <td align="center" class="td-a">${couponCode.coupon.endDate}</td>
              <td align="center" class="td-a">          
              [#if couponCode.isUsed]已使用[#else]  未使用&nbsp;|&nbsp;<a href="/cart/list.jhtml">马上使用</a></td> [/#if] 
            </tr>
            [/#list]
          </table>
      </div>
    </div>
  </div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>