<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.password.find")}[#if systemShowPowered] - Powered By SHOP++[/#if]</title>
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
	var $username = $("#username");
	var $email = $("#email");
	var $submit = $(":submit");
	
	// 表单验证
	$passwordForm.validate({
		rules: {
			username: "required",
			email: {
				required: true,
				email: true
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
						<strong>${message("shop.password.find")}</strong>FORGOT PASSWORD
					</div>
					<form id="passwordForm" action="find.jhtml" method="post">
						<input type="hidden" name="captchaId" value="${captchaId}" />
						<table>
							<tr>
								<th>
									<span class="requiredField">*</span>${message("shop.password.username")}:
								</th>
								<td>
									<input type="text" name="username" class="text" maxlength="${setting.usernameMaxLength}" />
								</td>
							</tr>
							<tr>
								<th>
									<span class="requiredField">*</span>${message("shop.password.email")}:
								</th>
								<td>
									<input type="text" name="email" class="text" maxlength="200" />
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