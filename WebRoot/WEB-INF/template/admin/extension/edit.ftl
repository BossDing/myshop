<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.extension.update")} </title>
<meta name="author" content="ZSGY Team" />
<meta name="copyright" content="ZSGY" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $browserButton = $("input.browserButton");
	
	var $productTable = $("#productTable");
	var $productSelect = $("#productSelect");
	var $deleteProduct = $("a.deleteProduct");
	var $productTitle = $("#productTitle");
	
	var productIds = [#if promotion.products?has_content][[#list promotion.products as product]${product.id}[#if product_has_next], [/#if][/#list]][#else]new Array()[/#if];
	
	[@flash_message /]

	$browserButton.browser();

	var $password = $("#password");
	var $rePassword = $("#rePassword");
	// 表单验证
	$inputForm.validate({
		rules: {
			cardNo: "required",
			password: "required",
			rePassword: "required",
			userName: "required",
			barCode: "required",
			endDate: "required"
		}
	});
	
	$("#submit_button").click(function(){
		var no = $("#no").val();
		var cardNo = $("#cardNo").val();
		if(no!=cardNo){
			$.ajax({
				url: "/admin/extension/checkCardNo.jhtml",
				type: "POST",
				data: {cardNo: $("#cardNo").val()}, 
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						//alert("保存成功");
						//window.location.href = "/gw/join/apply.jhtml";
						/*$.ajax({
		   					url : $inputForm.attr("action"),
		   					type : "POST",
		   					//data : {password : pwdValue,username : $('#username').text()},
		   					data: $inputForm.serialize(),
		   					dataType : "json",
		   					success : function(){
							}
		   				});*/
		   				if($password.val() != $rePassword.val()){
							alert("两次密码不一致，请重新填写");
							return false;
						}
		   				$inputForm.submit();
					} else {	
						//alert(message.content);
						return false;						
					}
				}
			});
		}else{
			if($password.val() != $rePassword.val()){
				alert("两次密码不一致，请重新填写");
				return false;
			}
			$inputForm.submit();	
		}
	});
	
});
</script>


</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.ad.add")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${extension.id}" />
		<input type="hidden" name="sn" value="${extension.sn}" />
		<ul id="tab" class="tab">
				<li>
					<input type="button" value="${message("admin.promotion.base")}" />
				</li>
				<!--
				<li>
					<input type="button" value="${message("Promotion.introduction")}" />
				</li>
				-->
			</ul>
			<div class="tabContent">
				<table class="input">
					<tr>
						<th>
							<span class="requiredField">*</span>服务卡号:
						</th>
						<td>
							<input type="text" name="cardNo" id="cardNo" class="text" value="${extension.cardNo}" maxlength="200" />
							<input type="hidden" value="${extension.cardNo}" id="no" />
						</td>
					</tr>
					<tr>
					<th>
						<span class="requiredField">*</span>新密码:
					</th>
					<td colspan="2">
						<input type="text" id="password" name="password" value="${extension.password}" class="text" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>确认密码:
					</th>
					<td colspan="2">
						<input type="text" id="rePassword" name="rePassword" value="${extension.password}" class="text" maxlength="200" />
					</td>
				</tr>
					<tr>
						<th>
							<span class="requiredField">*</span>用户名:
						</th>
						<td>
							<input type="text" name="userName" value="${extension.userName}" class="text" maxlength="200" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="requiredField">*</span>条形码:
						</th>
						<td>
							<input type="text" name="barCode" id="barCode" class="text" value="${extension.barCode}" maxlength="200" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="requiredField">*</span>保修期:
						</th>
						<td>
							<input type="text" id="endDate" name="endDate" value="${extension.endDate?string("yyyy-MM-dd HH:mm:ss")}" class="text Wdate" maxlength="200" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
						</td>
					</tr>
				</table>
			</div>
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="button" id="submit_button" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>