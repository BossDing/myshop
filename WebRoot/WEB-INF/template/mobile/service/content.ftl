<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<link href="${base}/resources/mobile/css/fuwu-didian.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<title>无标题文档</title>
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
        <!--
            <ul>
            	<li>公司：深圳三瑞兴业科技有限公司</li>
                <li>地址：深圳市福田区华强北A座</li>
                <li><div>电话：0744-1356721</div></li>
            </ul>
            <ul>
            	<li>深圳三瑞兴业科技有限公司</li>
                <li>地址：深圳市福田区华强北A座</li>
                <li>电话：0744-1356721</li>
            </ul>
            -->
            [#list siteServices as siteService]
            	<ul>
            		<li>公司：${siteService.serviceName}</li>
                	<li>地址：${siteService.serviceAddress}</li>
               		<li>电话：${siteService.serviceTel}</li>
            	</ul>
            [/#list]
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
