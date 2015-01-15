<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>个人中心-修改绑定手机</title>
    <link href="${base}/resources/shop/css/sns.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/shop/css/mobile.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/pic_switching.js"></script>
</head>
<script type="text/javascript">

       $(document).ready(function(){
       
       		//全局标记
       		//手机是否已获取验证码
       		var flag = false;
       		var nextFlag = false;
       		
			//手机点击获取验证码按钮
			$('#mobile_button').click(function(){
				time(this);
				flag = true;
				$('#mobileFailMsg').css('display','none');
				$('#mobileSucceedMsg').css('display','block');
				$.ajax({
					url: "${base}/member/mobile/sendCheckCode.jhtml",
					type: "GET",
					data: {mobile: $('#mobile_value').html()},
					dataType: "json",
					traditional: true,
					cache: false,
					success: function(data) {
					}
				});
			});
			
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
			
			
			
			//点击下一步触发事件
			$('#next').click(function(){
				nextFlag = true;
				//验证是否已获取验证码
				//手机
				if(!flag){
					//还未获取验证码
					$('#mobileFailMsg').html('请点击按钮获取验证码');
					$('#mobileFailMsg').css('display','block');
				}else{
					//验证验证码是否为空
					if($('#mobileCodeValue').val() == ''){
						$('#mobileCodeMsg').html('请输入六位数字验证码').css('color','red');
						$('#mobileCodeMsg').css('display','block');
					}
					//验证验证码输入是否正确
					
					//直接成功
					$('#checkMobile').css('display','none');
					$('#updateMobile').css('display','block');
				}
			});
			
	<!------------------绑定新手机-------------------------->
	//11位手机电话号码格式的正则表达式
	var RegPhone = /^1[3|4|5|8][0-9]\d{8}$/;
	$('#newMobileValue').blur(checkNewMobile);
	var newMobileIsOk = false;
	function checkNewMobile(){
		var newMobile = $.trim($('#newMobileValue').val());
		if(newMobile == ""){
			$('#newMobileFailMsg').html('请输入您的新手机').css('display','block').css('color','red');
			$('#mobile_button2').attr('disabled','true');
			newMobileIsOk = false;
			return false;
		}else if(newMobile.search(RegPhone) == -1){
			$('#newMobileFailMsg').html('手机号码格式不对').css('display','block').css('color','red');
			$('#mobile_button2').attr('disabled','true');
			newMobileIsOk = false;
			return false;
		}else{
			$.ajax({
						'url':'${base}/member/mobile/check_mobile.jhtml',
						'type':'get',
						'data': {mobile: newMobile},
						'async':false,
						'dataType':'json',
						'success':function(data){
							if(data == false){
								$('#newMobileFailMsg').html("该手机号码已被占用").css('display','block').css("color","red");
								newMobileIsOk =false;
								$('#mobile_button2').attr('disabled','true');
								return false;
							}else if(data == true){
								$("#newMobileFailMsg").html("√ 手机号码可以使用").css('display','block').css("color","green");
								newMobileIsOk =true;
								document.getElementById("mobile_button2").disabled=false;
					        }
						}
					});
		}
	}
	
	var flag2 = false;
	//点击获取验证码按钮
	$('#mobile_button2').click(function(){
		time2(this);
		flag2 = true;
		$('#newMobileFailMsg').css('display','none');
		$('#newMobileSucceedMsg').css('display','block');
		$.ajax({
					url: "${base}/member/mobile/sendCheckCode.jhtml",
					type: "GET",
					data: {mobile: $('#newMobileValue').val()},
					dataType: "json",
					traditional: true,
					cache: false,
					success: function(data) {
					}
				});
	});
	
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
	
	$('#submitButton').click(function(){
		checkNewMobile();
		if(!newMobileIsOk){
			return false;
		}
		if(!flag2){
			//没获取验证码
			$('#newMobileFailMsg').html('请点击按钮获取验证码').css('color','red');
		}else{
			$('#newMobileFailMsg').css('display','none');
			if($('#newMobileCodeValue').val() == ''){
				$('#newMobileCodeMsg').html('请输入六位数字验证码').css('display','block').css('color','red');
				return false;
			}
			
			//直接成功
			$('#updateMobile').css('display','none');
			$('#updateMobileSucceed').css('display','block');
		}
	});
	
       		
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
    [#include "/shop/member/include/left.ftl" /]       
            
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
<!--------------------------------------------------------------------------------------------------------------------------------------->
<!--------------------------------------------------------------------------------------------------------------------------------------->	

                <!--验证手机号码 --><!--验证手机号码 -->
                <div class="phone" id="checkMobile">
                    <div class="zlbt">修改绑定手机</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定手机</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--绑定选项 -->
                    <div class="bdxx">
                        
                        <!--切换到手机 -->
                        <div class="bdlb" >
                            <div class="zlfbt" >手机号码:</div>
                            <div id="mobile_value">${member.mobile}</div>
                            <div>
                                <!--<a href="#" class="hqyzm" id="mobile_button">获取手机验证码</a>-->
                                <input style="margin-left:10px; width:120px;height:40px;border-radius:5px;background-color: #f2f2f2;color:#666;
										font-family:微软雅黑;font-size:15px;"  name="" type="button" value="获取手机验证码" id="mobile_button">
                            </div>
                            <div class="yzmcw" id="mobileFailMsg">请点击按钮获取验证码</div>
                            <div class="wmgr" id="mobileSucceedMsg">已发送验证码到手机，请注意查收!</div>
                        </div>
                        <div class="bdlb" >
                            <div class="zlfbt" >手机验证码:</div>
                            <div class="shur">
                                <input type="text" name="name" value="" id="mobileCodeValue"/>
                            </div>
                            <div class="yzmcw" id="mobileCodeMsg">请输入六位数字验证码</div>
                        </div>
                        <!--切换到手机  end-->
                        
                        <div class="xiayibu">
                            <a href="#" class="xybu"  id="next">下一步</a>
                        </div>
                    </div>
                    <!--绑定选项 -->
                </div>
                <!--验证手机号码 end--><!--验证手机号码 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--绑定新手机号码 --><!--绑定新手机号码 -->
                <div class="phone-x" id="updateMobile">
                    <div class="zlbt">绑定新手机</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定手机</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--绑定选项 -->
                    <div class="bdxx">
                        <div class="bdlb gyyx">
                            <div class="zlfbt">新手机:</div>
                            <div class="yxshur">
                                <input type="text" name="name" value="" id="newMobileValue"/>
                            </div>
                            <div>
                                <!--<a href="#" class="hqyzm" id="mobile_button2">获取手机验证码</a>-->
                                <input style="margin-left:10px; width:120px;height:40px;border-radius:5px;background-color: #f2f2f2;color:#666;
										font-family:微软雅黑;font-size:15px;"  name="" type="button" value="获取手机验证码" id="mobile_button2" disabled="true">
                            </div>
                            <div class="yzmcw" id="newMobileFailMsg"></div>
                            <div class="wmgr" id="newMobileSucceedMsg">短信验证码已发送成功，请注意查收!</div>
                        </div>
                        <div class="bdlb">
                            <div class="zlfbt">短信验证码:</div>
                            <div class="shur">
                                <input type="text" name="name" value="" id="newMobileCodeValue"/>
                            </div>
                            <div class="yzmcw lwyzm" id="newMobileCodeMsg">请输入六位数字验证码</div>
                        </div>
                        <div class="xiayibu">
                            <a href="#" class="xybu" id="submitButton">提交</a>
                        </div>
                    </div>
                    <!--绑定选项 -->
                </div>
                <!--绑定新手机号码 end--><!--绑定新手机号码 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--绑定新手机号成功 --><!--绑定新手机号成功 -->
                <div class="phone-cg" id="updateMobileSucceed">
                    <div class="zlbt">绑定成功</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定邮箱</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--成功提示 -->
                    <div class="bdxx">恭喜你,修改绑定手机成功！</div>
                    <!--成功提示 end-->
                </div>
                <!--绑定新手机号成功 end--><!--绑定新手机号成功 end-->
<!--------------------------------------------------------------------------------------------------------------------------------------->
<!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--温馨提示 --><!--温馨提示---------------------- -->
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
