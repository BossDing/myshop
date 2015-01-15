<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>增值税发票</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
    <link href="${base}/resources/shop/css/sns.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/zengzhishui.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv_order.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {
	$('#saveButton').click(function(){   
					$.ajax({  
						url: "${base}/member/profile/saveinvoice.jhtml",
						type: "POST",
						data:{
							  dangweimingchen:$('#dangweimingchen').val()	,
							  nashuishibiehao:$('#nashuishibiehao').val(),
							  zhucedizhi:$('#zhucedizhi').val(),
							  zhucedianhua:$('#zhucedianhua').val(),
							  kaihuyh:$('#kaihuyh').val(),
							  zengzhishuiyhzh:$('#zengzhishuiyhzh').val(),
						},  
						dataType: "json",
						cache: false,
						success: function(message) {
							if (message.type == "success") {
								window.location.href = "${base}/member/profile/invoice.jhtml";
							} else {
								$('#saveButton').prop("disabled", false);
							}
						}
					});   
	}); 					      
						
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
	  <div id="index_place" style="width:400px;float:left">您的位置：<span>首页</span>><span>个人中心</span>><span>增值税发票</span></div>
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
      <div class="right-11">增值税发票</div>
      <div class="right-12">已保存的增值税发票</div>
      <div class="right-13">
        <table>
          <tr class="tr-1">
            <td align="center" class="ty-1">单位名称</td>
            <td align="center" class="ty-1">纳税人识别号</td>
            <td align="center" class="ty-1">注册地址</td>
            <td align="center" class="ty-4">注册电话</td>
            <td align="center" class="ty-1">开户银行</td>
            <td align="center" class="ty-1">银行帐户</td>
            <td align="center" class="ty-7">状态</td>
          </tr>
			          <tr class="tr-2">
			            <td align="center" class="ty-1">${member.dangweimingchen}</td>
			            <td align="center" class="ty-1">${member.nashuishibiehao}</td>
			            <td align="center" class="ty-1">${member.zhucedizhi}</td>
			            <td align="center" class="ty-4">${member.zhucedianhua}</td>
			            <td align="center" class="ty-1">${member.kaihuyh}</td>
			            <td align="center" class="ty-1">${member.zengzhishuiyhzh}</td>
			            <td align="center" class="ty-7"></td>
			          </tr>
        </table>
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
      </div>
      <div class="right-14"><input name="" type="button" value="新增增值税发票" class="anniu"></div>
      <div class="right-15">
       <form id="memberForm" action="${base}/member/profile/save.jhtml" method="post">
        <table>
          <tr>
            <td align="left" class="ui-1">* 单位名称</td>
            <td align="left"><input type="text" value=""  id = "dangweimingchen"  class="td-a"/></td>
            <td align="left" class="ui-2"><span>* 纳税人识别号</span></td>
            <td align="left"><input type="text" value=""  id = "nashuishibiehao" class="td-a"/></td>
          </tr>
          <tr>
            <td align="left" class="ui-1">* 注册地址</td>
            <td align="left"><input type="text" value=""  id = "zhucedizhi"  class="td-a"/></td>
            <td align="left" class="ui-2"><span>* 注册电话</span></td>
            <td align="left"><input type="text" value="" id = "zhucedianhua" class="td-a"/></td>
          </tr>
          <tr>
            <td align="left" class="ui-1">开户银行</td>
            <td align="left"><input type="text" value=""  id = "kaihuyh" class="td-a"/></td>
            <td align="left" class="ui-2"><span>银行帐户</span></td>
            <td align="left"><input type="text" value=""  id = "zengzhishuiyhzh" class="td-a"/></td>
          </tr>
          <td colspan=4 align="right"><input name="" type="button"   id="saveButton"   value="新增" class="anniu2"></td>
           </form>
        </table>
      </div>
      <script type="text/javascript">
      //
      $('.anniu').click(function(){
        $('.right-15').slideToggle();
      })
      </script>
    </div>
  </div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>