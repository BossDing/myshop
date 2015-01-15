<!DOCTYPE html>
<html>
<head>
<title>新增收货地址</title> 
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/add_addr.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $areaId = $("#areaId");
	
	[@flash_message /]
	
	// 地区选择
	$areaId.lSelect({
		url: "${base}/common/area.jhtml"
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			consignee: "required",
			areaId: "required",
			address: "required",
			zipCode: "required",
			phone: "required"
		}
	});
});
function insert_addr(){
	$.ajax({
    	url: "${base}/mobile/member/receiver/save.jhtml",
    	type: "post",
    	data: $("#inputForm").serialize(),
    	dataType: "json",
    	success: function(message){
    		$.message(message);
			if (message.type == "success") {
    			window.location.href = "${base}/mobile/member/receiver/list.jhtml";
			}
    	}
    });
}
</script>
</head>
<body>
	[#assign current = "receiverList" /]
	[#include "/mobile/include/header.ftl"]
	<div class="Big">
	    <div class="shouhuo">
	    <div class="input">
          <form id="inputForm" >
					<table class="input">
						<tr>
							<th>
								${message("Receiver.consignee")}: 
							</th>
							<td>
								<input type="text" name="consignee" class="text" maxlength="200" />
							</td>
						</tr>
						<tr>
							<th>
								${message("Receiver.area")}: 
							</th>
							<td>
								<span class="fieldSet">
									<input type="hidden" id="areaId" name="areaId" />
								</span>
							</td>
						</tr>
						<tr>
							<th>
								${message("Receiver.address")}: 
							</th>
							<td>
								<input type="text" name="address" class="text" maxlength="200" />
							</td>
						</tr>
						<tr>
							<th>
								${message("Receiver.zipCode")}: 
							</th>
							<td>
								<input type="text" name="zipCode" class="text" maxlength="200" />
							</td>
						</tr>
						<tr>
							<th>
								${message("Receiver.phone")}: 
							</th>
							<td>
								<input type="text" name="phone" class="text" maxlength="200" />
							</td>
						</tr>
						<tr>
							<th>
								${message("Receiver.isDefault")}:
							</th>
							<td>
								<input type="checkbox" name="isDefault" value="true" />
								<input type="hidden" name="_isDefault" value="false" />
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;
							</th>
							<td>
								<div class="Big-denglu" onclick="insert_addr();">${message("shop.member.submit")}</div>
							</td>
						</tr>
					</table>
				</form>
				</div>
	     </div>
	</div>
</body>
</html>