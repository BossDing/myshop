<!DOCTYPE html>
<html>
<head>
<title>编辑收货地址</title> 
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/edit_addr.css" rel="stylesheet" type="text/css" />
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
	$.validator.addMethod("requiredOne", 
		function(value, element, param) {
			return $.trim(value) != "" || $.trim($(param).val()) != "";
		},
		"${message("shop.member.receiver.requiredOne")}"
	);
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
function update_addr(){
	$.ajax({
    	url: "update.jhtml",
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
            <form id="inputForm">
                <input type="hidden" name="id" value="${receiver.id}" />
		    	<div>
		            <div class="shouhuo-A">${message("Receiver.consignee")}</div>
		            <div><input id="re_name" name="consignee" value="${receiver.consignee}" type="text"  class="new-nane"/></div>
		        </div>
		        <div style="width:100%;">
		        	<div class="shouhuo-A">${message("Receiver.area")}:</div>
		            <div class="where">
		            	<span class="fieldSet">
							<input type="hidden" id="areaId" name="areaId" value="${(receiver.area.id)!}" treePath="${(receiver.area.treePath)!}"/>
						</span>
		            </div>
		            <div id="areavalue">
		             </div>
		             <div class="di">
                        <div class="shouhuo-A">${message("Receiver.address")}</div>
		             	<textarea id="re_address" name="address">${receiver.address}</textarea>
		             </div>
		        </div>
		        <div>
		            <div class="shouhuo-A">${message("Receiver.zipCode")}</div>
		            <div><input value="${receiver.zipCode}" type="text" name="zipCode" class="new-nane" id="re_zip"/></div>
		        </div>
		        <div>
		            <div class="shouhuo-A">${message("Receiver.phone")}</div>
		            <div><input value="${receiver.phone}" type="text" name="phone" class="new-nane" id="re_phone"/></div>
		        </div>
                <div class="moren">
		            <span class="shouhuo-A">${message("Receiver.isDefault")}</span>
		            <input type="checkbox" name="isDefault" type="checkbox" name="isDefault" 
                        	value="true"[#if receiver.isDefault] checked="checked"[/#if] />
						<input type="hidden" name="_isDefault" value="false" />  		         
		        </div>
		        <div class="Big-denglu" onclick="update_addr();">${message("shop.member.submit")}</div>
            </form>
	     </div>
	</div>
</body>
</html>