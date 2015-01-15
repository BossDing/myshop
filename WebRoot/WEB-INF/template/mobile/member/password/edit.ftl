<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>个人中心 - 修改密码</title> 
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${base}/resources/mobile/css/member.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	
	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		rules: {
			currentPassword: {
				required: true,
				remote: {
					url: "check_current_password.jhtml",
					cache: false
				}
			},
			password: {
				required: true,
				pattern: /^[^\s&\"<>]+$/,
				minlength: ${setting.passwordMinLength},
				maxlength: ${setting.passwordMaxLength}
			},
			rePassword: {
				required: true,
				equalTo: "#password"
			}
		},
		messages: {
			password: {
				pattern: "${message("shop.validate.illegal")}"
			}
		},
		submitHandler: function(form) {
			$.ajax({
	    		//url: "${base}/mobile/member/password/update.jhtml",
	    		url: $inputForm.attr("action"),
	    		type: "POST",
	    		//data: $inputForm.serialize(),
	    		data: {
						currentPassword: $("#currentPassword").val(),
						password: $("#password").val(),
						rePassword: $("#rePassword").val()
					},
	    		dataType: "json",
	    		success: function(msg){
	    			$.message(msg);
	    			if(msg.type=="success"){
		    			setTimeout(function(){
		    				window.location.href = "${base}/mobile/member/index.jhtml";
		    			}, 3000);
	    			}
	    		}
	    	});
		}
	});
    
    /*
    $("#submit").click(function(){
    	
    });
	*/
});
</script>
</head>
<body>
	[#assign current = "passwordEdit" /]
	<div class="container member">
		<div class="span18 last">
			<div class="input">
				<div class="title">${message("shop.member.password.edit")}</div>
				<form id="inputForm" action="${base}/mobile/member/password/update.jhtml" method="post">
					<table class="input">
						<tr>
							<th>
								${message("shop.member.password.currentPassword")}: 
							</th>
							<td>
								<input type="password" name="currentPassword" id="currentPassword" class="text" maxlength="20" />
							</td>
						</tr>
						<tr>
							<th>
								${message("shop.member.password.newPassword")}: 
							</th>
							<td>
								<input type="password" id="password" name="password" class="text" maxlength="20" />
							</td>
						</tr>
						<tr>
							<th>
								${message("shop.member.password.rePassword")}: 
							</th>
							<td>
								<input type="password" name="rePassword" id="rePassword" class="text" maxlength="20" />
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;
							</th>
							<td>
								<input type="submit" class="button" value="${message("shop.member.submit")}" id="submit" />
								<input type="button" class="button" value="${message("shop.member.back")}" onclick="location.href='${base}/mobile/member/index.jhtml'" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>