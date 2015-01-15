<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.password.reset")}[#if systemShowPowered] - Powered By SHOP++[/#if]</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/password.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $passwordForm = $("#passwordForm");
	var $password = $("#password");
	var $submit = $(":submit");
	
	// 表单验证
	$passwordForm.validate({
		rules: {
			newPassword: {
				required: true,
				pattern: /^[^\s&\"<>]+$/,
				minlength: ${setting.passwordMinLength}
			},
			rePassword: {
				required: true,
				equalTo: "#newPassword"
			}
		},
		submitHandler: function(form) {
			$.ajax({
				url: $passwordForm.attr("action"),
				type: "POST",
				data: $passwordForm.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$submit.prop("disabled", true);
				},
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						setTimeout(function() {
							$submit.prop("disabled", false);
							location.href = "${base}/mobile/";
						}, 3000);
					} else {
						$submit.prop("disabled", false);
					}
				}
			});
		}
	});

});
</script>
</head>
<body>
	<div class="container password">
		<div class="span24">
			<div class="wrap">
				<div class="main">
					<div class="title">
						<strong>${message("shop.password.reset")}</strong>RESET PASSWORD
					</div>
					<form id="passwordForm" action="reset.jhtml" method="post">
						<input type="hidden" name="captchaId" value="${captchaId}" />
						<input type="hidden" name="username" value="${member.username}" />
						<input type="hidden" name="key" value="${key}" />
						<table>
							<tr>
								<th>
									${message("shop.password.username")}:
								</th>
								<td>
									${member.username}
								</td>
							</tr>
							<tr>
								<th>
									<span class="requiredField">*</span>${message("shop.password.newPassword")}:
								</th>
								<td>
									<input type="password" id="newPassword" name="newPassword" class="text" maxlength="${setting.passwordMaxLength}" autocomplete="off" />
								</td>
							</tr>
							<tr>
								<th>
									<span class="requiredField">*</span>${message("shop.password.rePassword")}:
								</th>
								<td>
									<input type="password" name="rePassword" class="text" maxlength="${setting.passwordMaxLength}" autocomplete="off" />
								</td>
							</tr>
							<tr>
								<th>
									&nbsp;
								</th>
								<td>
									<input type="submit" class="submit" value="${message("shop.password.submit")}" />
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>