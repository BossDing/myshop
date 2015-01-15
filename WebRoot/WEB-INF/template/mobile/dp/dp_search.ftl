<!DOCTYPE html>
<html>
	<head>
		<title>${store.name} - 搜索</title>
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
        	//判断输入的是否为空
               function check_search(){
		            var search = $("#keyword").val();
		            if("" != search){
		            	return true;
		            }
		            alert("输入的内容不能为空");
		            return false;
	            } 
        </script>
	</head>
	<body>
        <!--头部导航start-->
		[#include "/mobile/include/header.ftl"]
        <!--头部导航start-->
        
		<div class="quanbu-big">
			<div class="soso">
	            <form method="get" action="dp_category_list.jhtml" onsubmit="return check_search();">
					<input name="keyword" id="keyword" placeholder="请输入关键字……" type="text" class="soso-nei">
		            <input name="storeId" type="hidden" value="${store.id}">    
		            <input type="submit" value=" " class="soso-right1">
	            <form/>
		    </div>
		</div>
	</body>
</html>
