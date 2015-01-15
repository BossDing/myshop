<!DOCTYPE html>
<html>
	<head>
		<title>个人中心 - 收货地址</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
		<link href="${base}/resources/mobile/css/address_list.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
		<script type="text/javascript">
        	$(function(){
        		//判断当前是否存在用户
				if (!$.checkLogin()) {
					$.redirectLogin(window.location.href, "${message("shop.common.mustLogin")}");
					return false;
				}
            	[@flash_message /] //显示瞬时消息
            	
        		$divhref = $(".shouhuo-b");
        		$divhref.click(function(){
        			var receiverId = $(this).find("input[name='receiverId']").val();
        			var istocart = getParam("istocart");
        			if(istocart == 1){
	            		//回购物车,将收货地址id传过去
	            		document.location.href = "${base}/mobile/member/order/info.jhtml?id="+receiverId;
	            	} 
        		});
            });
			// 删除
			function deleteAddr(value) {
				if (confirm("${message("shop.dialog.deleteConfirm")}")) {
					$.ajax({
						url: "${base}/mobile/member/receiver/delete.jhtml",
						type: "POST",
						data: {id: value},
						dataType: "json",
						cache: false,
						success: function(message) {
							if (message.type == "success") {
            					window.location.reload();
							} else if(message.type == "error"){
            					$.message("${message("shop.member.noResult")}");
            				}
						}
					});
				}
			}
			// 获取url后参数的值
			function getParam(names) {
				var sstr = document.location.search.substring(1,document.location.search.length).split("&");
				var len = sstr.length;
				for (var i = 0; i < len; i++) {
					var sobj = sstr[i].split("=");
					if (names == sobj[0])
						return sobj[1];
				}
			}
		</script>
	</head>
	<body>
		[#assign current = "receiverList" /]
		[#include "/mobile/include/header.ftl" /]
	    <div class="dingdan-big">
		    <div class="shouhuo">
		    	<div>
					[#list page.content as receiver]
		                <div class="shouhuo-b">
		                	<input type="hidden" value="${receiver.id}" name="receiverId"/>
		                    <ul>
			                    <li class="shouhuo-b-a">${receiver.areaName}${abbreviate(receiver.address, 30, "...")}</li>
			                    <li>&nbsp;${receiver.consignee}
                                    <span onclick="window.location.href='edit.jhtml?id=${receiver.id}';" class="shouhuo-b-b" title="[${message("shop.member.handle.edit")}]"></span>
                                </li>
                                <li>${receiver.phone}</li>
		                    </ul>
		                </div>
					<div class="delimg">
                        <img src="${base}/resources/mobile/images/NO_10.png" onclick="deleteAddr(${receiver.id});" 
                        	class="imge" title="[${message("shop.member.handle.delete")}]"/>
                    </div><hr/>
                    [/#list]
                    [#if !page.content?has_content]
                    <div class="no-result">
						<p>${message("shop.member.noResult")}</p>
                    </div>
					[/#if]
		        </div>
			 <div class="Big-denglu" onclick="document.location.href='add.jhtml'">${message("shop.member.receiver.add")}</div>
		</div>
	</body>
</html>