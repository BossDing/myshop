<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<link href="${base}/resources/mobile/css/fuwu.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<title>网点查询</title>
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<script type="text/javascript">
	$(function(){
		//判断当前是否存在用户
		if (!$.checkLogin()) {
			$.redirectLogin("${base}/mobile/member/index.jhtml", "${message("shop.common.mustLogin")}");
			return false;
		}
	 });
</script>
</head>

<body>
[#include "/mobile/include/header.ftl" /]
<div class="quanbu-big">
        <div class="left-one">
            <ul>
                	[#list provinces as province]
                		<li class="left-top"><a href="${base}/mobile/member/service/content.jhtml?province=${province}"><span class="left-left">${province}</span>
                		<img id="left-seven" src="${base}/resources/mobile/images/jie_50.png"></a></li>
                	[/#list]
            </ul>
        </div>
        
        
        <div class="bottom">
        	<ul>
            	<li><a href="${base}/mobile/login/index.jhtml">登录</a></li>
                <li class="bottom-a"><a href="${base}/mobile/register/index.jhtml">注册</a></li>
                <li><a href="#">返回顶部</a></li>
            </ul>
        </div>
</div>
</body>
</html>
