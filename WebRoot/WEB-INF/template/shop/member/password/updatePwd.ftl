<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>修改密码</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/gaimima.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
    	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv_order.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/shop/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $captcha = $("#captcha");
	var $changeimage = $("#changeimage");
	var $captchaImage = $("#captchaImage");
	
	// 更换验证码
	$changeimage.click(function() {
		$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	});
	$("#currentPassword").focus(function(){
		$("#check1").html("");
	});
	$("#currentPassword").blur(function(){
		var thisval = $(this).val();
		if(thisval==""){
			$("#check1").html("<img src='${base}/resources/shop/images/gaimima-1.png'>&nbsp;请输入旧密码 ");
		}
	});
	
	$("#password").focus(function(){
		$("#check2").html("");
	});
	$("#password").blur(function(){
		var thisval = $(this).val();
		if(thisval==""){
			$("#check2").html("<img src='${base}/resources/shop/images/gaimima-1.png'>&nbsp;请输入新密码 ");
		}
		var pwdFormat =/^[\w]{6,17}$/;
		var myReg = /^[^@\/\'\\\"#$%&\^\*]+$/;
		var strlength = thisval.length;
		if(thisval == '' || thisval.search(pwdFormat) == -1 ){
			 if(myReg.test(thisval) || strlength<6){
			 	$("#check2").html("<img src='${base}/resources/shop/images/gaimima-1.png'>&nbsp;密码格式不正确 ");
			 }else{
				
			}
		}
		pwStrength(thisval);
	});
	
	$("#confimpassword").focus(function(){
		$("#check3").html("");
	});
	$("#confimpassword").blur(function(){
		var thisval = $(this).val();
		if(thisval==""){
			$("#check3").html("<img src='${base}/resources/shop/images/gaimima-1.png'>&nbsp;请输入确认密码 ");
		}
		if(thisval!=$("#password").val()){
			$("#check3").html("<img src='${base}/resources/shop/images/gaimima-1.png'>&nbsp;两次密码输入不一致 ");
		}
	});
	
	$("#captcha").focus(function(){
		$("#check4").html("");  
	});
	$("#captcha").blur(function(){
		var thisval = $(this).val();
		if(thisval==""){
			$("#check4").html("<img src='${base}/resources/shop/images/gaimima-1.png'>&nbsp;请输入验证码 ");
		}
	});
	
	
	$('#submit_botton').click(function(){
		
		if($("#check1").text().indexOf("请输入旧密码")>0 || $("#check1").text().indexOf("原始密码不正确")>0 
		   ||$("#check2").text().indexOf("请输入新密码")>0 || $("#check2").text().indexOf("密码格式不正确")>0
		   ||$("#check3").text().indexOf("请输入确认密码")>0 || $("#check3").text().indexOf("两次密码输入不一致")>0
		   ||$("#check4").text().indexOf("请输入验证码")>0 || $("#check4").text().indexOf("验证码输入错误")>0
		   ){
			return false;
		}
   		$.ajax({  
			url: "${base}/member/password/update.jhtml",
			type: "POST",
			data:{
				  currentPassword:$('#currentPassword').val(),
				  password:$('#password').val(),
				  captchaId: "${captchaId}",
				  captcha: $captcha.val()					  
			},    
			dataType: "json",
			cache: false,
			success: function(message) {  
				if (message.type == "success") {
						alert(message.content);	
						$('#currentPassword').val("");	
						$('#password').val("");	
						$('#confimpassword').val("");	
						$('#captcha').val("");	
						document.getElementById("strength_L").style.background="#d2d2d2";  
						document.getElementById("strength_M").style.background="#d2d2d2";  
						document.getElementById("strength_H").style.background="#d2d2d2";  
				} else {
						var messagecontent = message.content;
						if(messagecontent=="密码不能为空"){
							$("#check1").html("<img src='${base}/resources/shop/images/gaimima-1.png'>&nbsp;请输入旧密码 ");
						} 
						if(messagecontent=="验证码输入错误"){
							$("#check4").html("<img src='${base}/resources/shop/images/gaimima-1.png'>&nbsp;验证码输入错误 ");
						} 
						if(messagecontent=="原始密码不正确"){
							$("#check1").html("<img src='${base}/resources/shop/images/gaimima-1.png'>&nbsp;原始密码不正确 ");
						}
						//alert(messagecontent);
						$captcha.val("");
						$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());	
				}
				//alert(message.content);
				//$.message(message);    
			}
		});
   	});
});  

			//判断输入密码的类型  
			function CharMode(iN){  
			if (iN>=48 && iN <=57) //数字  
					return 1;  
					if (iN>=65 && iN <=90) //大写  
					return 2;  
					if (iN>=97 && iN <=122) //小写  
					return 4;  
					else  
					return 8;   
			}  
			//bitTotal函数  
			//计算密码模式  
			function bitTotal(num){  
					modes=0;  
					for (i=0;i<4;i++){  
					if (num & 1) modes++;  
					num>>>=1;  
					}  
					return modes;  
			}  
			//返回强度级别  
			function checkStrong(sPW){  
					if (sPW.length<=6)  
					return 0; //密码太短  
					Modes=0;  
					for (i=0;i<sPW.length;i++){  
					//密码模式  
					Modes|=CharMode(sPW.charCodeAt(i));  
					}  
					return bitTotal(Modes);  
			} 

			//显示颜色  
			function pwStrength(pwd){  
					O_color="#eeeeee";  
					L_color="#FF0000";  
					M_color="#FF9900";  
					H_color="#33CC00";  
					if (pwd==null||pwd==''){  
					Lcolor=Mcolor=Hcolor=O_color;  
					}  
					else{  
					S_level=checkStrong(pwd);  
					switch(S_level) {  
					case 0:  
					Lcolor=Mcolor=Hcolor=O_color;  
					case 1:  
					Lcolor=L_color;  
					Mcolor=Hcolor=O_color;  
					break;  
					case 2:  
					Lcolor=Mcolor=M_color;  
					Hcolor=O_color;   
					break;  
					default:  
					Lcolor=Mcolor=Hcolor=H_color;  
					}  
					}  
					document.getElementById("strength_L").style.background=Lcolor;  
					document.getElementById("strength_M").style.background=Mcolor;  
					document.getElementById("strength_H").style.background=Hcolor;  
					return;  
			}


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
	  <div id="index_place" style="width:400px;float:left">您的位置：<a href="/"><span>首页</span></a>><a href="${base}/member/index.jhtml"><span>个人中心</span></a>><span>修改密码</span></div>
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
      <div class="right-11">修改密码</div>
      <div class="right-12">
        <table cellpadding="0";cellspacing="0">
          <tr>
            <td align="right" class="tu-1"><span>*</span> 当前密码</td>
            <td align="left" class="tu-2"><input type="password" id="currentPassword" value="" class="td-a"/></td>
            <td align="left" class="tu-3" id="check1">
            	
            </td>   
          </tr>
          <tr>
            <td class="tu-1"></td>
            <td colspan=2 class="tu-4"><span class="mao"><!--<a href="#">忘记当前密码</a>--></span></td>
          </tr>
          <tr>
            <td align="right" class="tu-1"><span>*</span> 新密码</td>
            <td align="left" class="tu-2"><input type="password" id="password"  value="" class="td-a"/></td>
        	<td align="left" class="tu-3" id="check2">
            	
            </td>  
          </tr>
          <tr>
            <td class="tu-1"></td>
            <td colspan=3 class="tu-4"><span class="mao">密码由6-16个字符组成，区分大小写（不能够9位以下的纯数字，不能包括空格）</span></td>
          </tr>
          <tr>
            <td align="right" class="tu-1"> 密码强度</td>
            <td align="left" class="tu-2" colspan=2>
              <ul>
                <li		id="strength_L"></li>
                <li		id="strength_M"></li>
                <li		id="strength_H"></li>
              </ul>
            </td>
          </tr>
          <tr>
            <td class="tu-1"></td>
            <td colspan=3 class="tu-4"><span class="mao">为了提升您的密码安全性，建议使用英文字母或符号的混合密码</span></td>
          </tr>
          <tr>
            <td align="right" class="tu-6"><span>*</span> 确认新密码</td>
            <td align="left" class="tu-5"><input type="password" id="confimpassword" value="" class="td-a"/></td>
            <td align="left" class="tu-3" id="check3" style="padding-bottom:20px;">
            	
            </td>   
          </tr>
          <tr>
            <td align="right" class="tu-1"><span>*</span> 验证码</td>
            <td align="left" class="tu-2"><input type="text" id="captcha" name="captcha" value="" class="td-a"/></td>
        	<td align="left" class="tu-3" id="check4">
            	
            </td>   
          </tr>
          <tr>
            <td class="tu-1"></td>
            <td colspan=2 class="tu-4"><span class="mao">输入下图中的字符，不区分大小写</span></td>
          </tr>
          <tr>
            <td class="tu-1"></td>
            <td colspan=2 class="tu-4">
            	<span class="mao">
            		<img id="captchaImage" class="captchaImage" src="${base}/common/captcha.jhtml?captchaId=${captchaId}" 
            			title="${message("shop.captcha.imageTitle")}" />
            	</span>
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<a id="changeimage" href="#">看不清，换一张</a></td>
          </tr>
          <tr>
            <td align="right" class="tu-6"></td>
            <td align="left" class="tu-8" colspan=2><input name="" type="button" id ="submit_botton" value="提交" class="anniu"></td>
          </tr>
        </table>
      </div>
    </div>  
  </div>	  


	[#include "/shop/include/footer.ftl" /]
</body>
</html>