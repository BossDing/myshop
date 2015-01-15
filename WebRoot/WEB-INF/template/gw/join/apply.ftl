<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "productSearch"]
	<title>万家乐官方网站</title>
	<META name=Keywords content=${productCategory.name}>
	<META name=Description content=${productCategory.seoDescription}>
[/@seo]    
<link href="${base}/resources/gw/css/css.css" rel="stylesheet" type="text/css">
<script src="${base}/resources/gw/js/jquery-1.4.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript">var $a =jQuery.noConflict();</script>
<script src="${base}/resources/gw/js/nav.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="${base}/resources/gw/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${base}/resources/gw/js/lanrenzhijia.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<link href="${base}/resources/gw/css/ban2.css" rel="stylesheet" type="text/css">
<link href="${base}/resources/gw/css/all_css.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/gw/js/share.js"></script>
<link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/slide_share.css?v=bc01b5e3.css"><link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/imgshare.css?v=754091cd.css"><link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/share_style0_16.css?v=f4b44e79.css"></head>

<script type="text/javascript">
$().ready(function() {
	var $messageFrom = $("#messageFrom");	
	var $userName = $("#userName");	
	var $phone = $("#phone");	
	var $email = $("#email");	
	var $joinArea = $("#joinArea");				
	var $joinType = $("#joinType");	
	var $remark = $("#remark");	
    var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");

    	// 更换验证码
	$captchaImage.click(function() {
		$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	}); 
	
	$('#saveButton').click(function(){   
				$.ajax({  
						url: "/gw/join/saveapply.jhtml",
						type: "POST",
						data: {
						 userName: $userName.val()
						,phone: $phone.val()
						,email: $email.val()
						,joinArea: $joinArea.val()
						,joinType: $joinType.val()
						,remark: $remark.val()
						,captchaId: "${captchaId}"
						,captcha: $captcha.val() }, 
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								alert("保存成功");
								window.location.href = "/gw/join/apply.jhtml";
							} else {	
								alert(message.content);
								$captcha.val("");
								$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());							
							}
						}
					});				
	});   
});
</script>				
</head>
<body>

[#include "/gw/include/header.ftl" /]
	  
 <div class="ban4">
    <div id="bn">
      <span class="tu">
      <a href="#" style="width: 1264px; display: none;"><img alt="" width="1680" height="257" src="${base}/resources/gw/images/zcfw.jpg" data-bd-imgshare-binded="1"></a>
      <a href="#" style="width: 1264px; display: inline;"><img border="0" alt="" width="1680" height="257" src="${base}/resources/gw/images/zcfw.jpg" data-bd-imgshare-binded="1"></a><a href="##" style="width: 1264px; display: none;"><img border="0" alt="" width="1680" height="257" src="${base}/resources/gw/images/zcfw.jpg" data-bd-imgshare-binded="1"></a></span></div></div>
<div class="cpzx">
  <div class="cpzx_div zlm_bg">
	<div class="gywjl_dq">目前您在：<a href="#">首页</a> &gt; <a href="#">招商加盟</a> &gt; <h1 style="font-size:14px;font-weight:normal;display:inline"></h1></div>
  		<div class="jrwjl_z"><div class="gywjl_lmbt">招商加盟</div>
<div class="gy_zlm "><a href="/gw/join/intruduction.jhtml">加盟介绍</a></div>
<div class="gy_zlm"><a href="/gw/join/apply.jhtml">加盟申请</a></div>
</div>
  		<div class="gywjl_y">
  		  <div class="gy_nrbt">加盟申请</div>
  		  
          <div class="gy_nr">
           <form id="messageFrom" method="post">
        <table width="720" border="0" align="center" cellpadding="0" cellspacing="0">
          <tbody><tr>
            <td height="50"><strong>产品加盟请填写加盟信息,我们工作人员将会尽快与您联系.</strong></td>
          </tr>
          <tr>
            <td>
            <form id="messageFrom" method="post">
          <table width="720" border="0" cellspacing="0" cellpadding="0">
           <tbody><tr style="display:none;">
                <td align="right" colspan="3">用户名</td>
                <td colspan="3">
                               匿名用户                                </td>
            </tr>
              <tr style="display:none;">
                <td align="right" colspan="3">留言类型</td>
                <td colspan="3"><input name="msg_type" type="radio" value="0">
                  留言                  <input type="radio" name="msg_type" value="1">
                  应聘                  <input type="radio" name="msg_type" value="2">
                                    <input type="radio" name="msg_type" value="3">
                  维修                  <input type="radio" name="msg_type" value="4" checked="checked">
                  安装</td>
              </tr>
   </tbody></table>    
    <table width="720" border="0" cellspacing="0" cellpadding="0">
  <tbody><tr>
    <td height="36" align="right"><span class="hong">*</span> 姓名：</td>
    <td height="36"><input id="userName" type="text" class="weixiu" size="25"></td>
  </tr>
  <tr>
    <td height="36" align="right"><span class="hong">*</span> 联系电话：</td>
    <td height="36"><input id="phone" type="text" class="weixiu" size="25"></td>
  </tr>
  <tr>
    <td height="36" align="right"><span class="hong">*</span> 电子邮件：</td>
    <td height="36"><input id="email" type="text" class="weixiu" size="25" value=""></td>
  </tr>
  <tr>
    <td height="36" align="right"><span class="hong">*</span> 加盟区域：</td>
    <td height="36"><input id="joinArea" type="text" class="weixiu" size="40"></td>
  </tr>
    <tr>
    <td height="36" align="right"><span class="hong">* </span>加盟品类：</td>
    <td height="36"><label for="cp_lx"></label>
      <select id ="joinType" name="cp_lx" id="cp_lx" style="margin-left:15px;">
        <option selected="selected">请选择</option>
		<option>壁挂炉产品</option>
		<option>橱柜产品</option>
		<option>空气能产品</option>					
		<option>生活电器</option>			
	  </select></td>
  </tr>
  <tr>
    <td height="24" align="right" valign="top"><span class="hong">* </span>备注：</td>
    <td valign="top"><textarea id= "remark" name="msg_content" cols="50" rows="6" wrap="virtual" style="border:1px solid #ABAFB2; margin-left:15px;"></textarea></td>
  </tr>
</tbody></table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
                 <tbody><tr>
                <td width="140" align="left">验证码:</td>
                <td width="580" align="left"><input type="text" size="8" id="captcha"  name="captcha" class="inputBg">
				                <img id="captchaImage" class="captchaImage" src="${base}/common/captcha.jhtml?captchaId=${captchaId}" title="${message("shop.captcha.imageTitle")}" />		
                  </td>
            </tr>
                         <tr>
                <td width="140" height="50" align="center" valign="middle"><input type="hidden" name="act" value="act_add_message"></td>
                <td align="left" valign="middle"><input name="" type="button" id="saveButton" value="提交申请"/></td>
            </tr>
             <tr>
               <td height="100" colspan="2">&nbsp;</td>
               </tr>
</tbody></table> 
    </form></td>
          </tr>
        </tbody></table>			
        </div>
          
            
          
  		</div>
  </div>
</div></div></div>
	[#include "/gw/include/footer.ftl" /]
</body>
</html>