<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>活动提醒</title>
<link href="${base}/resources/shop/css/sysq_xiangqing.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.jqzoom.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
	$().ready(timer());
	
	function timer(){  
		var strTime=$("#starttime").text(); //字符串日期格式           
		var date= new Date(Date.parse(strTime.replace(/-/g,"/"))); 
                var ts = date - new Date();//计算剩余的毫秒数
                if(ts<=0){
                	$("#time").text("").append("<span>活动已开始！</span>");
                	return;
                }  
                var dd = parseInt(ts / 1000 / 60 / 60 / 24, 10);  
                var hh = parseInt(ts / 1000 / 60 / 60 % 24, 10);
                var mm = parseInt(ts / 1000 / 60 % 60, 10);
                var ss = parseInt(ts / 1000 % 60, 10);
                dd = checkTime(dd);  
                hh = checkTime(hh);  
                mm = checkTime(mm);  
                $('#dd').text(dd);
                $('#hh').text(hh);
                $('#mm').text(mm);
                $('#ss').text(ss);   
                setInterval("timer()",1000);  
            }  
    function checkTime(i)    
    {    
       if (i < 10) {    
           i = "0" + i;    
        }    
       return i;    
    }
  	$().ready(function(){
  		$("#remind").click(function(){
	  		var tel = $("#tel").val();
	  		var isTel = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/.test(tel);
	  		if(tel.length==0){
	  			alert("请填写手机号码！");
	  			return;
	  		}
	  		if(!isTel){
	  			alert("请填写正确的手机号码！");
	  			return;
	  		}
  			$('#remindForm').submit();
  		});
  	});
  	
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
<body>
[#include "/shop/include/header.ftl" /]
[#if preSellRole.products?has_content]
   		[#list preSellRole.products as product] 
<div class="container productContent">   		
	<div class="apply">
	 <div class="apply_list"></div>
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
     <li id="xq_name" style="margin-top:40px;">${preSellRole.name}</li>
     <li id="xq_brief">
     	${preSellRole.title}
     </li>
     <li>预售价格：<span>￥${preSellRole.preSellPrice}</span></li>
     <li>申请时间：<span id="starttime"> ${preSellRole.beginDate} </span>至 <span>${preSellRole.endDate}</span></li>
     <li>免费试用数量：<span>${preSellRole.qtyLimit}</span> 台</li>
     <li id="time">距离开始时间:<span id="dd"></span>天<span id="hh"></span>小时<span id="mm"></span>分<span id="ss"></span>秒 </span></li>
     <li id="xq_share">
     <span style="width:75px; color:#333;">分享到：</span>
         <span><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="24" height="24" /></a></span>
         <span><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="24" height="24" /></a></span>
         <span><a class="jiathis_button_weixin" title="分享到微信"><img src="${base}/resources/shop/images/xinxi_ico.png" width="24" height="24" /></a></span>
         <span><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="24" height="24" /></a></span>
         <span><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="24" height="24" /></a></span>
             <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
         </li>
      <li><input  onclick = "document.getElementById('light_bx').style.display='block';document.getElementById('fade').style.display='block'" 
      			type="button" class="xq_button" value="活动提醒" /></li>
     </ul>
     </div>
   </div>

<div class="apply_xiangqing_list">
    
</div>
    <!--详情宣言-->
<div class="apply_xiangqing">

<!--申请宣言
   <div class="appl_xuanyan">
      <dl>
        <dt>申请宣言</dt>
         [#if trial.trialApplys?has_content]
         	[#list trial.trialApplys as trialApply]
        <dd>
           <p>
               <span class="name">${trialApply.receiver}</span>
               <span class="name">${trialApply.address}</span>
               <span class="time">${trialApply.applyDate}</span>
             </p>
           <p class="xq_xy_say">${trialApply.applyReason}</p>
           </dd>
               [/#list]
           [#else]
     		<dd>
           <p>
           <p class="xq_xy_say">暂无申请！</p>
           </dd>
        [/#if]
        </dl>
      </div> 
      
    <!--  申请宣言结束-->
  
   <div class="xiangqing_infor">
   			<div class="xq_centent">
      				 ${product.introduction}</div>
      		</div> 
      
      		<div class="xiangqing_lit"></div>  
    </div>
    <!--详情结束-->
 
    
    
    
    <!--提醒弹出开始-->
    
    
    <div class="t_baoxiu">   

<div id="light_bx" class="car_white_content">
	<div class="t_biaoti">活动提醒
		<div class="t_guanbi">
			<a href = "javascript:void(0)"  onclick =  "document.getElementById('light_bx').style.display='none';document.getElementById('fade').style.display='none'">
				X
			</a>
		</div>
	</div>
<div class="t_neirong">

<form id="remindForm" action='${base}/preSellRole/saveRemind.jhtml' method="post">
	<table width="570" border="0" cellspacing="10" cellpadding="0">
	  <tr>
	    <td width="500" align="left" colspan="2">   <strong>     尊敬的客户，请留下您的电话号码，我们将会在第一时间把活动开始信息以短信的方式发送给您。</strong>
	       </td>
	  </tr>
	  <tr>
	    <td align="right">手机号码:</td>
	    <td><input id="tel" name="tel" class="t_phone" type="text" /><strong id="testTel"></strong></td>
	  </tr>
	  <tr style="display:none">
	    <td> 
	    <input name="preSellRoleId" class="t_phone" type="text" value="${preSellRole.id}"/> 
	    <input name="isRemaided" class="t_phone" type="text" value="no"/>
	    <input name="remaidedDate" class="t_phone" type="text" value="${preSellRole.beginDate}"/>
	     </td>
	  </tr>
	  <tr>
	    <td align="right">&nbsp;</td>
	    <td><input id="remind" class="t_baoxiu_button" type="button" value="提交" /></td>
	  </tr>
	</table>
	<div class="xq_succeed_submit" style="display:none;" id="success">
		 <strong id="msg">已提交成功 ！</strong>
	</div>
</form>
</div>

</div>
</div>
<div id="fade" class="car_black_overlay"></div> 
</div>
   [/#list]
[/#if]
<!--提醒弹出结束-->
 [#include "/shop/include/footer.ftl" /]
</body>
</html>
