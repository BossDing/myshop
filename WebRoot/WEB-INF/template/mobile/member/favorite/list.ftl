<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>个人中心 - 我的收藏</title>
    
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
	<meta name="keywords" content="奥马微商城">
    <link rel="stylesheet" href="${base}/resources/mobile/css/shoucanjia.css">
    <link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>

	<script type="text/javascript">
	$().ready(function() {	
	});
		// 删除
			function deleteFavorite(value) {
				if (confirm("${message("shop.dialog.deleteConfirm")}")) {
					$.ajax({
						url: "delete.jhtml",
						type: "POST",
						data: {id: value},
						dataType: "json",
						cache: false,
						success: function(message) {
							if (message.type == "success") {
            					window.location.href += "?" + new Date().getTime();
							} else if(message.type == "error"){
            					$.message("${message("shop.member.noResult")}");
            				}
						}
					});
				}
			}
	</script>

</head>
<body>
	[#assign current = "favoriteList" /]
	[#include "/mobile/include/header.ftl" /]
	<div class="container member">
		<div class="span18 last">
			<div class="list">
			<div class="one-hang">
				[#list page.content as product]
           		<div  class="one-hang-left">
                	<ul>
                    	<li class="photo"><a href="${base}/mobile${product.path}" ><img src="[#if product.thumbnail??]${product.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" width="100%"></a></li>
                        <li class="Name">${product.name}&nbsp;&nbsp;${currency(product.price, true)}<span><img class="img" onclick="deleteFavorite(${product.id})"></span></li>
                    </ul>
              </div>
				[/#list]
				</div>
				[#if !page.content?has_content]
					<p><center>亲,您还收藏东西呢,去逛逛!</center></p>
				[/#if]
			</div>
		</div>
	</div>
</body>
</html>