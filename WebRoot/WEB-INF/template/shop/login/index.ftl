<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.login.title")}[#if systemShowPowered] - Powered By SHOP++[/#if]</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/wjl_login.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/qq.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jsbn.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/prng4.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/rng.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/rsa.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/base64.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/kefu.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $loginForm = $("#loginForm");
	var $username = $("#username");
	var $password = $("#password");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $isRememberUsername = $("#isRememberUsername");
	var $submit = $(":submit");
	
	// 记住用户名
	if (getCookie("memberUsername") != null) {
		$isRememberUsername.prop("checked", true);
		$username.val(getCookie("memberUsername"));
		$password.focus();
	} else {
		$isRememberUsername.prop("checked", false);
		$username.focus();
	}
	
	// 更换验证码
	$captchaImage.click(function() {
		$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	});
	
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
							enPassword: enPassword
							[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("memberLogin")]
								,captchaId: "${captchaId}",
								captcha: $captcha.val()
							[/#if]
						},
						dataType: "json",
						cache: false,
						success: function(message) {
							if ($isRememberUsername.prop("checked")) {
								addCookie("memberUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
							} else {
								removeCookie("memberUsername");
							}
							$submit.prop("disabled", false);
							if (message.type == "success") {
								[#if redirectUrl??]
									location.href = "${redirectUrl}";
								[#else]
									location.href = "${base}/";
								[/#if]
							} else {
								$.message(message);
								[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("memberLogin")]
									$captcha.val("");
									$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
								[/#if]
							}
						}
					});
				}
			});
		}
	});
	
	
	var $headerLogin = $("#headerLogin");
	var $headerRegister = $("#headerRegister");
	var $headerUsername = $("#headerUsername");
	var $headerLogout = $("#headerLogout");
	var $productSearchForm = $("#productSearchForm");
	var $keyword = $("#productSearchForm input");
	var defaultKeyword = "商品搜索";
	
	var username = getCookie("username");
	if (username != null) {
		$headerUsername.text("您好, " + username).show();
		$headerLogout.show();
	} else {
		$headerLogin.show();
		$headerRegister.show();
	}
	
	$keyword.focus(function() {
		if ($keyword.val() == defaultKeyword) {
			$keyword.val("");
		}
	});
	
	$keyword.blur(function() {
		if ($keyword.val() == "") {
			$keyword.val(defaultKeyword);
		}
	});
	
	$productSearchForm.submit(function() {
		if ($.trim($keyword.val()) == "" || $keyword.val() == defaultKeyword) {
			return false;
		}
	});
});
</script>
</head>
<body>
	[#include "/shop/include/header.ftl" /]
<!--	<div id="floatTools" class="float0831"> 
			<div class="floatL">
				<a id="aFloatTools_Show" class="btnOpen" title="shop.header.chakanzaixiankefu" onclick="javascript:$(&#39;#divFloatToolsView&#39;).animate({width: &#39;show&#39;, opacity: &#39;show&#39;}, &#39;normal&#39;,function(){ $(&#39;#divFloatToolsView&#39;).show();kf_setCookie(&#39;RightFloatShown&#39;, 0, &#39;&#39;, &#39;/&#39;, &#39;www.istudy.com.cn&#39;); });$(&#39;#aFloatTools_Show&#39;).attr(&#39;style&#39;,&#39;display:none&#39;);$(&#39;#aFloatTools_Hide&#39;).attr(&#39;style&#39;,&#39;display:block&#39;);" href="javascript:void(0);" style="display:none">展开</a>
				<a style="display:block" id="aFloatTools_Hide" class="btnCtn" title="shop.header.guangbizaixiankefu" onclick="javascript:$(&#39;#divFloatToolsView&#39;).animate({width: &#39;hide&#39;, opacity: &#39;hide&#39;}, &#39;normal&#39;,function(){ $(&#39;#divFloatToolsView&#39;).hide();kf_setCookie(&#39;RightFloatShown&#39;, 1, &#39;&#39;, &#39;/&#39;, &#39;www.istudy.com.cn&#39;); });$(&#39;#aFloatTools_Show&#39;).attr(&#39;style&#39;,&#39;display:block&#39;);$(&#39;#aFloatTools_Hide&#39;).attr(&#39;style&#39;,&#39;display:none&#39;);" href="javascript:void(0);">收缩</a>
			</div>
			<div style="" id="divFloatToolsView" class="floatR">
				<div class="tp"></div>
				<div class="cn">
					<ul>
						<li class="top">
							<h3 class="titZx">QQ咨询</h3> 
						</li>
						<li>
							<span class="icoZx">在线咨询</span>
						</li> 				
						<li>
							<a target="_blank" class="icoTc" href="#">客服一</a>
						</li>
						<li>
							<a target="_blank" class="icoTc" href="#">客服二</a>
						</li>
						<li>
							<a target="_blank" class="icoTc" href="#">客服三</a>
						</li>
						<li class="bot">
							<a target="_blank" class="icoTc" href="#">客服四</a>
						</li>
						
					</ul>
					<ul>
						<li>
							<h3 class="titDh">
								电话咨询
							</h3>
						</li>
						<li>
							<span class="icoTl">123456789</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="erweima">
        <!--二维码-->
</div>-->
		
<script>
	$('#divFloatToolsView').animate({width: 'show', opacity: 'show'}, 'normal',function(){ $('#divFloatToolsView').show();kf_setCookie('RightFloatShown', 0, '', '/', 'www.istudy.com.cn'); });$('#aFloatTools_Show').attr('style','display:none');$('#aFloatTools_Hide').attr('style','display:block');
</script>

    <div id="index_place">您现在的位置： <span>首页</span> > <span>用户登录</span></div>
	<strong style="width:1190px;margin:0px auto; display: block;font-size: 18px;line-height: 40px;">用户登录</strong>
	<div class="container login" >
		<!--<div class="span12">
			[@ad_position id = 9 /]
		</div>-->
		<div class="span12 last" >
			<div class="wrap">
				<div class="main">
					<!--<div class="title">
						<strong>${message("shop.login.title")}</strong>USER LOGIN
					</div>-->
					<form id="loginForm" action="${base}/login/submit.jhtml" method="post">
						<table>
							<tr>
								<th>
								    <div>
									[#if setting.isEmailLogin]
										${message("shop.login.usernameOrEmail")}:
									[#else]
										${message("shop.login.username")}:
									[/#if]
									</div>
									<input type="text" id="username" name="username" class="text" maxlength="${setting.usernameMaxLength}" />
								</th>
							</tr>
							<tr>
								<th>
									<div>${message("shop.login.password")}:</div>
									<input type="password" id="password" name="password" class="text" maxlength="${setting.passwordMaxLength}" autocomplete="off" />
								</th>
							</tr>
							
							[#if setting.captchaTypes?? && setting.captchaTypes?seq_contains("memberLogin")]
							<tr>
									<th>
										${message("shop.captcha.name")}:
										<span class="fieldSet">
											<input type="text" id="captcha" name="captcha" class="text captcha" maxlength="4" autocomplete="off" />
										</span>
									</th>
							</tr>
							<tr>
									<th><div style="font-size:10px; color:#adadad;">输入下图中的字符，不区分大小写</div>
									    <img id="captchaImage" class="captchaImage" src="${base}/common/captcha.jhtml?captchaId=${captchaId}" title="${message("shop.captcha.imageTitle")}" />
									</th>
							</tr>		
									
							[/#if]
							
							<tr>	
								<th>
									<label>
										<input type="checkbox" id="isRememberUsername" name="isRememberUsername" value="true" />${message("shop.login.isRememberUsername")}
									</label>
									<label>
										&nbsp;&nbsp;<a href="${base}/password/find.jhtml">${message("shop.login.findPassword")}</a>
									</label>
								</th>
							</tr>
							<tr>	
								<td>
									<input type="submit" class="submit" value="${message("shop.login.submit")}" />
								</td>
							</tr>
							<tr>	
								<td>
									<dl>
										<dt>${message("shop.login.noAccount")}</dt>
										<dd>
											${message("shop.login.tips")}
											<a href="${base}/register.jhtml">${message("shop.login.register")}</a>
										</dd>
									</dl>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="content"><span style="display: none;">
	  <script type="text/javascript" src="../js/c.php" charset="gb2312"></script><script src="../js/core.php" charset="utf-8" type="text/javascript"></script><a href="http://quanjing.cnzz.com/" target="_blank" title="全景统计"><img border="0" hspace="0" vspace="0" src="../js/2.gif"></a>
	</span>
</div>


	[#include "/shop/include/footer.ftl" /]
</body>
</html>