<!DOCTYPE html>
<html>
	<head>
		<title>${setting.mobilesiteName}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0"/>
		<meta name="apple-mobile-web-app-capable" content="yes"/>
		<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
		<meta name="format-detection" content="telephone=no"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link href="${base}/resources/mobile/css/product_search.css" rel="stylesheet" type="text/css" />
		<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
		<script type="text/javascript">
			function search(){
			var value = $("#keyword").val();
				if("" != value){
					window.location.href = "${base}/mobile/product/search.jhtml?keyword="+document.getElementById("keyword").value;
				}else{
					alert("输入的内容不能为空");
				}
				
			}
		</script>
	</head>
	<body>
	[#include "/mobile/include/header.ftl" /]
	<div class="quanbu-big">
		<div class="soso">
			<input id="keyword" placeholder="请输入关键字……" type="text" class="soso-nei">
	    	<a href="javascript:search();"><span class="soso-right"></span></a>
	    </div>
	</div>
	</body>
</html>
