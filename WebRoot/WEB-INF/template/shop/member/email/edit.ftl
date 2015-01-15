<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>个人中心-修改绑定邮箱</title>
    <link href="${base}/resources/shop/css/sns.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/shop/css/email.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
</head>
<script type="text/javascript">

		
       $(document).ready(function(){
       		
       		[#if member==null]
       			window.location.href="${base}/login.jhtml";
       		[/#if]
       
       		//全局标记
       		//邮箱是否已获取验证码
       		var flag = false;//是否已获取验证码
       		//var nextFlag = false;
       		var code = ""; //验证码  
 			var codeLength = 6;//验证码长度
       		
			/**修改邮箱点击获取验证码按钮 start*/
			$('#email_button').click(function(){
				code = "";
				time(this);
				//邮箱已获取验证码
				flag = true;
				$('#emailFailMsg').css('display','none');
				$('#emailSucceedMsg').css('display','block');
				 // 产生验证码  
	            for ( var i = 0; i < codeLength; i++) {  
	              code += parseInt(Math.random() * 9).toString();  
	            }  
				$.ajax({
					url: "${base}/member/email/sendCode.jhtml",
					type: "GET",
					data: {email: $('#email_value').html(),code:  code},
					dataType: "json",
					traditional: true,
					cache: false,
					success: function(data) {
					}
				});
			});
			/** end 修改邮箱点击获取验证码按钮 */
			
			var wait=60;
			function time(o) {
					if (wait == 0) {
				            o.removeAttribute("disabled");            
				            o.value="重新获取验证码";
				            wait = 60;
				        } else {
				            o.setAttribute("disabled", true);
				            o.value="重新发送(" + wait + ")";
				           // $("#button").attr("disabled", true);
				           // $("#button").val("重新发送(" + wait + ")");
				            wait--;
				            setTimeout(function() {
				                time(o)
				            },1000)
				        }
			    }
		//	document.getElementById("btn").onclick=function(){time(this);}
			
			/**修改邮箱验证码校验 start*/
			$('#emailCodeValue').blur(checkCode);
			var emailCodeIsOk = false;
			function checkCode(){
				var result = $('#emailCodeValue').val();
				if(result == ''){
						$('#emailCodeMsg').html('请输入六位数字验证码').css('display','block').css('color','red');
						emailCodeIsOk = false;
						return false;
				}else{
					$.ajax({  
			            url : "${base}/member/email/checkCode.jhtml",   
			            data : {usercode : result},   
			            type : "GET",   
			            dataType : "json",   
			            success : function(data) {  
			                //data = parseInt(data, 10);  
			                if (data == true) {  
			                    $("#emailCodeMsg").html("<font color='#339933'>√ 短信验证码正确，请继续</font>").css('display','block');
			                    emailCodeIsOk = true;
			                } else {  
			                    $("#emailCodeMsg").html("<font color='red'>× 短信验证码有误，请核实后重新填写</font>").css('display','block');
			                    emailCodeIsOk = false;
			                    return false;
			                }  
			            }  
			        });
				}
			}
			/** end 修改邮箱验证码校验 */
			
			/**点击下一步触发事件 start*/
			$('#next').click(function(){
				//nextFlag = true;
				//验证是否已获取验证码
				if(!flag){
					//还未获取验证码
					$('#emailFailMsg').html('请点击按钮获取验证码');
					$('#emailFailMsg').css('display','block');
				}else{
					//验证验证码
					checkCode();
					if(emailCodeIsOk){
						//成功
						$('#checkEmail').css('display','none');
						$('#updateEmail').css('display','block');
				 	}
				}
			});
			/** end 点击下一步触发事件*/ 
			
			<!------------------绑定新邮箱start-------------------------->
			
			/**新邮箱验证 start*/
			//邮箱地址格式的正则表达式
			var RegEmail = /^\w+((-\w+)|(\.\w+))*\@(qq|163|126|139|189|sina|sohu|tom)+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
			//验证邮箱账号
			$('#newEmailValue').blur(checkNewEmail);
			var newEmailIsOk = false;
			function checkNewEmail(){
				var newEmail = $.trim($('#newEmailValue').val());
				if( newEmail == "" || newEmail == null){
					$('#newEmailFailMsg').html("请输入您的新邮箱").css('display','block').css('color','red');
					$('#email_button2').attr('disabled','true');
					newEmailIsOk = false;
					return false;
				}else if (newEmail.search(RegEmail) == -1) {
					$("#newEmailFailMsg").html("邮箱格式不对").css('display','block').css("color","red");
					$('#email_button2').attr('disabled','true');
					newEmailIsOk = false;
					return false;
				}else{
					$.ajax({
						'url':'${base}/member/email/check_email.jhtml',
						'type':'get',
						'data': {email: newEmail},
						'async':false,
						'dataType':'json',
						'success':function(data){
							if(data == false){
								$('#newEmailFailMsg').html("该邮箱已被占用").css('display','block').css("color","red");
								newEmailIsOk =false;
								$('#email_button2').attr('disabled','true');
								return false;
							}else if(data == true){
								$("#newEmailFailMsg").html("√ 邮箱可以使用").css('display','block').css("color","green");
								newEmailIsOk =true;
								document.getElementById("email_button2").disabled=false;
					        }
						}
					});
				}	
			}
			/**end 新邮箱验证 */
			
			
			var flag2 = false;//标记是否已获取验证码
			var newcode = "";//生成六位数字验证码
       		/**绑定新邮箱，点击获取验证码按钮 start*/
			$('#email_button2').click(function(){
				newcode = "";
				time2(this);
				//邮箱已获取验证码
				flag2 = true;
				$('#newEmailFailMsg').css('display','none');
				$('#newEmailSucceedMsg').css('display','block');
				for ( var i = 0; i < codeLength; i++) {  
	              	newcode += parseInt(Math.random() * 9).toString();  
	            }
				$.ajax({
					url: "${base}/member/email/sendNewCode.jhtml",
					type: "GET",
					data: {email: $('#newEmailValue').val(),newcode:newcode},
					dataType: "json",
					traditional: true,
					cache: false,
					success: function(data) {
					}
				});
			});
			/** end 绑定新邮箱，点击获取验证码按钮*/ 
			
			var wait2=60;
			function time2(o) {
					if (wait2 == 0) {
				            o.removeAttribute("disabled");            
				            o.value="重新获取验证码";
				            wait2 = 60;
				        } else {
				            o.setAttribute("disabled", true);
				            o.value="重新发送(" + wait2 + ")";
				           // $("#button").attr("disabled", true);
				           // $("#button").val("重新发送(" + wait + ")");
				            wait2--;
				            setTimeout(function() {
				                time2(o)
				            },1000)
				        }
			    }
		//	document.getElementById("btn").onclick=function(){time(this);}
			
			/**绑定新邮箱 验证码校验 start*/
			$('#newEmailCodeValue').blur(checkNewCode);
			var newEmailCodeIsOk = false;
			function checkNewCode(){
				var result2 = $('#newEmailCodeValue').val();
				if(result2 == ''){
					$('#newEmailCodeMsg').html('请输入六位数字验证码').css('display','block').css('color','red');
					newEmailCodeIsOk = false;
					return false;
				}else{
					$.ajax({
						url : "${base}/member/email/checkNewCode.jhtml",
						data : {usercode : result2},
						type : "GET",
						dataType : "json",
						success : function(data){
							if(data == true){
								$('#newEmailCodeMsg').html('<font color="#339933">√ 短信验证码正确，请继续</font>').css('display','block');
								newEmailCodeIsOk = true;
							}else{
								$('#newEmailCodeMsg').html('<font color="red">× 短信验证码有误，请核实后重新填写</font>').css('display','block');
								newEmailCodeIsOk = false;
								return false;
							}
						}
					});
				}
			}
			/**end 绑定新邮箱 验证码校验*/ 
			<!------------------绑定新邮箱end-------------------------->
			
			/**绑定新邮箱，提交 start*/
			$('#submitButton').click(function(){
				//1、验证新邮箱
				//1)是否为空
				//2)格式是否正确
				//3)是否被占用
				//4)可用，按钮才有效
				checkNewEmail();
				if(!newEmailIsOk){
					return false;
				}
				//2、验证是否已获取验证码
				if(!flag2){
					//还未获取验证码
					$('#newEmailFailMsg').html('请点击按钮获取验证码').css('display','block').css('color','red');
				}else{
				//3、验证验证码
				//1)是否为空
				//2)是否正确
					$('#newEmailFailMsg').css('display','none');
					checkNewCode();
					if(newEmailCodeIsOk){
						//成功
						//save
						$.ajax({
							url : "${base}/member/email/saveNewEmail.jhtml",
							data : {email : $('#email_value').html(),newemail : $('#newEmailValue').val()},
							type : "GET",
							dataType : "json",
							success : function(){
							}
						});
						$('#updateEmail').css('display','none');
						$('#updateEmailSucceed').css('display','block');
					}
				}
			});
       		/**end 绑定新邮箱，提交*/ 
       		
       		/**登陆邮箱 start*/
			function toLoginEmail(){
				var email = $.trim($('#getEmail').attr('value'));
				var startIndex = email.indexOf('@') + 1;
				email = email.substring(startIndex,email.length);
				var endIndex = email.indexOf('\.');
				var keyWord = email.substring(0,endIndex);
				if("qq"==keyWord){
					window.location = "https://mail.qq.com/cgi-bin/loginpage";
				}else if("163"==keyWord){
					window.location = "http://mail.163.com/";
				}else if("126"==keyWord){
					window.location = "http://www.126.com/";
				}else if("sina"==keyWord){
					window.location = "http://mail.sina.com.cn/?logout";
				}else if("sohu"==keyWord){
					window.location = "http://mail.sohu.com/";
				}else if("139"==keyWord){
					window.location = "http://mail.10086.cn/";
				}else if("189"==keyWord){
					window.location = "http://webmail10.189.cn/webmail/";
				}else if("tom"==keyWord){
					window.location = "http://web.mail.tom.com/webmail/login/index.action";
				}	
			};
       		/** end 登陆邮箱*/
       		
		    var historyProduct = getCookie("historyProduct");
			var historyProductIds = historyProduct != null ? historyProduct.split(",") : new Array();
			for (var i = 0; i < historyProductIds.length; i ++) {
				if (historyProductIds[i] == "${product.id}") {
					historyProductIds.splice(i, 1);
					break;
				}
			}
			historyProductIds.unshift("${product.id}");
			if (historyProductIds.length > 4) {
				historyProductIds.pop();
			}
			addCookie("historyProduct", historyProductIds.join(","), {path: "${base}/"});
			$.ajax({
				url: "${base}/product/history.jhtml",
				type: "GET",
				data: {ids: historyProductIds},
				dataType: "json",
				traditional: true,
				cache: false,
				success: function(data) {
					$.each(data, function (index, product) {
						var thumbnail = product.thumbnail != null ? product.thumbnail : "${setting.defaultThumbnailProductImage}";
						$("#historyProduct").append('<li><a href="${base}' + product.path + '" title="' + product.name + '"><img src="' + thumbnail + '" \/></a><p class="link-evaluate"><span>' + currency(product.price, true) + '</span></p></li>');
					});
				}
			});
			
		});
    </script>
<body>
   [#include "/shop/member/include/header.ftl" /]
    <!--我的沁园头部 end--><!--我的沁园头部 end-->
    <!--我的沁园 内容区--><!--我的沁园 内容区-->
    <div class="snsnrq">
        <div class="snsnr">
            <!--左 导航区-->
            <div class="hong"></div>
            <div class="zcdhq">
                <div class="side-neck">
                    <i></i>
                </div>
   					 [#include "/shop/member/include/navigation.ftl" /]       
        	</div>
        <!--左 导航区 end--><!--左 导航区 end-->
        
        <!--右 内容区-相关资料及修改--><!--右 内容区-相关资料及修改-->
            <div class="wdzlq">
                <!--我的资料--><!--我的资料-->
                <div class="wdzl">
                    <div class="zlbt">我的资料</div>
                    <div class="wdzllb">
                        <ul>
                            <li>
                                <a href="#" class="xuanzhong">基本资料</a>
                            </li>
                            <li>
                                <a href="#">修改头像</a>
                            </li>
                        </ul>
                    </div>
                    <!--资料明细-->
                    <div class="zlmxq">
                        <ul>
                            <li>
                                <div class="fa xmnr">头像：</div>
                                <div class="head-pic">
                                    <a href="#">
                                        <img src="../images-sns/touxiang.jpg" alt="Alternate Text" />
                                        <p>
                                            <span></span>
                                            <em>修改头像</em>
                                        </p>
                                    </a>
                                </div>
                            </li>
                            <li class="hydj">
                                <div class="fa xmnr">会员等级：</div>
                                <i></i>
                                <b>普通会员</b>
                            </li>
                            <li class="shuru">
                                <div class="fa xmnr">用户名：</div>
                                <input type="text" name="name" value="Ironmen " />
                            </li>
                            <li class="shuru">
                                <div class="fa xmnr">姓名：</div>
                                <input type="text" name="name" value="李某某 " />
                            </li>
                            <li>
                                <div class="fa xmnr">性别：</div>
                                <input type="radio" name="name" value=" " />
                                男
                                <input type="radio" name="name" value=" " />
                                女
                                <input type="radio" name="name" value=" " />
                                保密
                            </li>
                            <li>
                                <div class="fa xmnr">邮箱：</div>
                                <b>Iron******men@126.com</b>
                                <a href="${base}/member/email.jhtml">修改</a>
                            </li>
                            <li>
                                <div class="fa xmnr">手机号：</div>
                                <b>196******55</b>
                                <a href="${base}/member/mobile.jhtml">修改</a>
                            </li>
                            <li>
                                <div class="fa xmnr">密码：</div>
                                <b>*******</b>
                                <a href="#">修改</a>
                            </li>
                            <li>
                                <div class="fa xmnr">居住地址：</div>
                                <span class="jzdz">
                                    <select>
                                        <option value="default">--请选择--</option>
                                        <option value="北京">北京</option>
                                        <option value="上海">上海</option>
                                        <option value="天津">天津</option>
                                        <option value="重庆">重庆</option>
                                        <option value="安徽">安徽</option>
                                        <option value="福建">福建</option>
                                        <option value="甘肃">甘肃</option>
                                        <option value="广东">广东</option>
                                        <option value="广西">广西</option>
                                        <option value="贵州">贵州</option>
                                        <option value="海南">海南</option>
                                        <option value="河北">河北</option>
                                        <option value="河南">河南</option>
                                        <option value="黑龙江">黑龙江</option>
                                        <option value="湖北">湖北</option>
                                        <option value="湖南">湖南</option>
                                        <option value="吉林">吉林</option>
                                    </select>
                                    <select>
                                        <option value="default">--请选择--</option>
                                    </select>
                                    <select class="last">
                                        <option value="default">--请选择--</option>
                                    </select>
                                    <select class="last">
                                        <option value="default">--请选择--</option>
                                    </select>
                                </span>
                            </li>
                            <li class="xqzdz">
                                <input type="text" name="name" value=" " />
                                <span>请填写省市区镇及详细地址！</span>
                            </li>
                            <li class="baocun">
                                <a href="#">保存</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <!--我的资料 end--><!--我的资料 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--验证邮箱 --><!--验证邮箱 -->
                <div class="mailbox" id="checkEmail">
                    <div class="zlbt">修改绑定邮箱</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定邮箱</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--绑定选项 -->
                    <div class="bdxx">
                        <!--切换到邮箱 -->
                        <div class="bdlb" >
                            <div class="zlfbt" >邮箱地址:</div>
                            <div id="email_value">${member.email}</div>
                            <div>
                                <!--
                                <a href="#" class="hqyzm" id="email_button">获取邮箱验证码</a>
                                <input style="margin-left:10px; width:120px;height:40px;border-radius:5px;background-color: #f2f2f2;color:#666;
										font-family:微软雅黑;font-size:15px;"  name="" type="button" value="获取邮箱验证码" id="email_button">
								-->
								<a href="#" class="hqyzm">
                                    <input type="button" name="" value="获取邮箱验证码 " id="email_button"/>
                                </a>
										
										
                            </div>
                            <div class="yzmcw" id="emailFailMsg">请点击按钮获取验证码</div>
                            <div class="wmgr" id="emailSucceedMsg">已发送验证码到邮箱，请登录邮箱查收!</div>
                        </div>
                        <div class="bdlb" >
                            <div class="zlfbt" >邮箱验证码:</div>
                            <div class="shur">
                                <input type="text" name="" value="" id="emailCodeValue"/>
                            </div>
                            <div class="yzmcw" id="emailCodeMsg">请输入六位数字验证码</div>
                        </div>
                        <!--切换到邮箱  end-->
                        <div class="xiayibu">
                            <a href="#" class="xybu" id="next">下一步</a>
                        </div>
                    </div>
                    <!--绑定选项 -->
                </div>
                <!--验证邮箱 end--><!--验证邮箱 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--绑定新邮箱 --><!--绑定新邮箱 -->
                <div class="mailbox mailbox-x" id="updateEmail">
                    <div class="zlbt">绑定新邮箱</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定邮箱</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--绑定选项 -->
                    <div class="bdxx">
                        <div class="bdlb gyyx">
                            <div class="zlfbt">新邮箱地址:</div>
                            <div class="yxshur">
                                <input type="text" name="name" value="" id="newEmailValue" />
                            </div>
                            <div>
                                <!--<a href="#" class="hqyzm" id="email_button2">获取邮箱验证码</a>
                                <input style="margin-left:10px; width:120px;height:40px;border-radius:5px;background-color: #f2f2f2;color:#666;
										font-family:微软雅黑;font-size:15px;"  name="" type="button" value="获取邮箱验证码" id="email_button2" disabled="true">
								-->	
								<a href="#" class="hqyzm">
                                    <input type="button" name="" value="获取邮箱验证码 " id="email_button2" disabled="true"/>
                                </a>		
                            </div>
                            <div class="yzmcw" id="newEmailFailMsg"></div>
                            <div class="wmgr" id="newEmailSucceedMsg">已发送验证码到邮箱，请登录邮箱查收!</div>
                        </div>
                        <div class="bdlb">
                            <div class="zlfbt">邮箱验证码:</div>
                            <div class="shur">
                                <input type="text" name="name" value="" id="newEmailCodeValue"/>
                            </div>
                            <div class="yzmcw" id="newEmailCodeMsg">请输入六位数字验证码</div>
                        </div>
                        <div class="xiayibu">
                            <a href="#" class="xybu" id="submitButton">提交</a>
                        </div>
                    </div>
                    <!--绑定选项 -->
                </div>
                <!--绑定新邮箱 end--><!--绑定新邮箱 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--绑定新邮箱成功 --><!--绑定新邮箱成功 -->
                <div class="mailbox mailbox-cg" id="updateEmailSucceed">
                    <div class="zlbt">绑定成功</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定邮箱</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--成功提示 -->
                    <div class="bdxx">恭喜你,修改绑定邮箱成功！</div>
                    <!--成功提示 end-->
                </div>
                <!--绑定新邮箱成功 end--><!--绑定新邮箱成功 end-->
                
            <!--------------------------------------------------------------------------------------------------------------------------------------->
			<!--------------------------------------------------------------------------------------------------------------------------------------->	
                <!--温馨提示 -->            <!--温馨提示---------------------- -->
                <div class="tips">
                    <p>
                        <strong>邮箱可正常使用收不到验证码怎么办？</strong>
                    </p>
                    <p>
                        若您的邮箱能正常使用但无法接收到验证码邮件，请仔细查找垃圾箱邮件。同时由于网络原因，可能会有延迟。如果您10分钟还没有收到邮件，请重新点击发送。
                    </p>
                    <p>
                        <strong>无法通过验证？</strong>
                    </p>
                    <p>
                        如果您的手机和邮箱已经无法正常使用，请发送账号名（手机/邮箱/用户名）、联系手机、身份证正反面照片注明原因发送至<b> 4008007867@qinyuan.com</b>，客服将在收
                        到邮件后第一时间联系您。（客服工作时间为09:00-18:00）
                    </p>
                </div>
                <!--温馨提示 end-->            <!--温馨提示 end-->
            </div>
            <!--右 内容区-相关资料及修改 end--><!--右 内容区-相关资料及修改 end-->
            
        </div>
        <div class="cenmax"></div>
    </div>
    <!--我的沁园内容区 end-->
    <div class="cenmaxbs"></div>
    [#include "/shop/include/footer.ftl" /]
</body>
</html>
