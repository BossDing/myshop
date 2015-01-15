<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<title>绑定账号</title>
<link href="${base}/resources/mobile/css/denglu.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/jsbn.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/prng4.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/rng.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/rsa.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/base64.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $loginForm = $("#loginForm");
	var $username = $("#username");
	var $password = $("#password");
	var $openid = $("#openid");
	var $isRememberUsername = $("#isRememberUsername");
	var $submit = $(":submit");
	
	// 表单验证、记住用户名
	$loginForm.validate({
		rules: {
			username: "required",
			password: "required"
			[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("memberLogin")]
				,captcha: "required"
			[/#if]
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
						url: $loginForm.attr("action"),
						type: "POST",
						data: {
							username: $username.val(),
							enPassword: enPassword,
							openid : $openid.val()
						},
						dataType: "json",
						cache: false,
						success: function(message) {
							$submit.prop("disabled", false);
							if (message.type == "success") {
								alert("亲!您的微信号已与您的账号绑定成功");
								location.href = "${base}/mobile/index.jhtml";
							} else {
								$.message(message);
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
	<form id="loginForm" action="${base}/mobile/bind/submit.jhtml" method="post">
	    <div class="Call-border"></div>
	    <div class="KK">
	        <div class="zhang"><input type="text" id="username" name="username" class="zhanghu" placeholder="用户名" maxlength="${setting.usernameMaxLength}"/></div>
	        <div class="zhang"><input type="password" id="password" name="password" class="zhanghu" placeholder="密  码" maxlength="${setting.passwordMaxLength}" autocomplete="off" /></div>
	        <!--<div class="zhang"><input type="text" id="captcha" name="captcha" class="yanzheng_input"placeholder="验证码"/><img id="captchaImage" class="yanzhengma" src="${base}/common/captcha.jhtml?captchaId=${captchaId}" title="${message("shop.captcha.imageTitle")}"/></div>-->
	    	<input type="submit" class="Big-denglu" value="绑定"/>
	    </div>
    </form>
</div>
</body>
</html>
