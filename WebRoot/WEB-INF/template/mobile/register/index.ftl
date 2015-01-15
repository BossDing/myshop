<!DOCTYPE html>
<html>
	<head>
		<title>会员注册</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
		<link href="${base}/resources/mobile/css/denglu.css" rel="stylesheet" type="text/css" />
		<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.lSelect.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.validate.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jsbn.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/prng4.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/rng.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/rsa.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/base64.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/datePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		$().ready(function() {
		
			var $registerForm = $("#registerForm");
			var $username = $("#username");
			var $password = $("#password");
			var $email = $("#email");
			var $areaId = $("#areaId");
			var $captcha = $("#captcha");
			var $captchaImage = $("#captchaImage");
			var $submit = $(":submit");
			var $agreement = $("#agreement");
			
			// 地区选择
			$areaId.lSelect({
				url: "${base}/common/area.jhtml"
			});
			
			// 更换验证码
			$captchaImage.click(function() {
				$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
			});
			
			// 注册协议
			$agreement.hover(function() {
				$(this).height(200);
			});
			
			// 表单验证
			$registerForm.validate({
				rules: {
					username: {
						required: true,
						pattern: /^[0-9a-z_A-Z\u4e00-\u9fa5]+$/,
						minlength: ${setting.usernameMinLength},
						remote: {
							url: "${base}/mobile/register/check_username.jhtml",
							cache: false
						}
					},
					password: {
						required: true,
						pattern: /^[^\s&\"<>]+$/,
						minlength: ${setting.passwordMinLength}
					},
					rePassword: {
						required: true,
						equalTo: "#password"
					},
					captcha: "required"
					[@member_attribute_list]
						[#list memberAttributes as memberAttribute]
							[#if memberAttribute.isRequired]
								,memberAttribute_${memberAttribute.id}: {
									required: true
								}
							[/#if]
						[/#list]
					[/@member_attribute_list]
				},
				messages: {
					username: {
						pattern: "${message("shop.register.usernameIllegal")}",
						remote: "${message("shop.register.disabledExist")}"
					},
					password: {
						pattern: "${message("shop.register.passwordIllegal")}"
					}
				},
				submitHandler: function(form) {
					$.ajax({
						url: "${base}/common/public_key.jhtml",
						type: "GET",
						dataType: "json",
						cache: false,
						beforeSend: function() {
							$submit.prop("disabled", true);
						},
						success: function(data) {
							var rsaKey = new RSAKey();
							rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
							var enPassword = hex2b64(rsaKey.encrypt($password.val()));
							$.ajax({
								url: $registerForm.attr("action"),
								type: "POST",
								data: {
									username: $username.val(),
									enPassword: enPassword,
									email: $email.val()
									[@member_attribute_list]
										[#list memberAttributes as memberAttribute]
											,memberAttribute_${memberAttribute.id}: $(":input[name='memberAttribute_${memberAttribute.id}']").val()
										[/#list]
									[/@member_attribute_list]
								},
								dataType: "json",
								cache: false,
								success: function(message) {
									$.message(message);
									if (message.type == "success") {
										window.setTimeout(function() {
											$submit.prop("disabled", false);
											location.href = "${base}/mobile/index.jhtml";
										}, 1000);
									} else {
										$submit.prop("disabled", false);
									}
								}
							});
						}
					});
				}
			});
		
		});
		</script>
	</head>
	<body>	
		[#include "/mobile/include/header.ftl" /]
			<div class="Big">
			    <div class="Call-border"></div>
			  <div class="KK">
			  <form id="registerForm" action="${base}/mobile/register/submit.jhtml" method="post">
			        <div class="zhang"><span>*</span><input id="username" name="username" placeholder="${message("shop.register.username")}" maxlength="${setting.usernameMaxLength}" type="text"  class="zhanghu"/></div>
			        <div class="zhang"><span>*</span><input type="password" id="password" name="password"  maxlength="${setting.passwordMaxLength}" placeholder="密&nbsp;码" class="zhanghu"/></div>
			        <div class="zhang"><span>*</span><input type="password" name="rePassword" placeholder="确认密码" type="text" class="zhanghu"/></div>
			        [@member_attribute_list]
						[#list memberAttributes as memberAttribute]
									[#if memberAttribute.type == "name"]
										<div class="zhang"><input placeholder="${memberAttribute.name}" name="memberAttribute_${memberAttribute.id}" type="text"  class="zhanghu"/></div>
									[#elseif memberAttribute.type == "gender"]
										<div class="zhang">
												&nbsp;性别：
              									&nbsp;&nbsp;
											[#list genders as gender]
													<input type="radio" name="memberAttribute_${memberAttribute.id}" value="${gender}" />${message("Member.Gender." + gender)}
											[/#list]
										</div>
									[#elseif memberAttribute.type == "birth"]
									<div class="zhang"><input placeholder="出生日期" type="text"  name="memberAttribute_${memberAttribute.id}" class="zhanghu" onfocus="WdatePicker();"/></div>
									[#elseif memberAttribute.type == "area"]
										<div class="zhang">
											<input type="hidden" id="areaId" name="memberAttribute_${memberAttribute.id}" />
										</div>
									[#elseif memberAttribute.type == "address"]
									 	<div class="zhang"><input placeholder="地址" name="memberAttribute_${memberAttribute.id}" type="text"  class="zhanghu"/></div>
									[#elseif memberAttribute.type == "mobile"]
										<div class="zhang"><span>*</span><input placeholder="手机" type="text" name="memberAttribute_${memberAttribute.id}" class="zhanghu" maxlength="200" /></div>
									[/#if]
						[/#list]
					[/@member_attribute_list]
			        <div class="zhang"><span></span><input id="email" name="email" placeholder="邮箱" type="text" class="zhanghu"/></div>
			     
			   <!-- <div id="yz"><input placeholder="${message("shop.captcha.name")}" type="text" id="captcha" name="captcha"  maxlength="4" autocomplete="off"   class="yanzheng_input"/>
			        <img id="captchaImage" class="yanzhengma" src="${base}/common/captcha.jhtml?captchaId=${captchaId}" title="${message("shop.captcha.imageTitle")}" />
			     </div>
			     -->
			    <input type="submit" class="Big-denglu" value="注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册"/></div>			   
			     </form>
			    </div>
			    
			</div>
</body>
</html>