<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<title> 个人中心 </title>
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/memberindex.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<script type="text/javascript">
	$(function(){
    	var $headerUsername = $("#headerUsername");
		var $headerLogout = $("#headerLogout");
		
		var username = getCookie("username");
		if (username != null) {
			$headerUsername.text("${message("shop.header.welcome")}, " + username).show();
			$headerLogout.show();
		}
		
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
        	<div class="denglu">
        		<span id="headerUsername" class="headerUsername"></span>
				<span id="headerLogout" class="headerLogout">
					<a href="${base}/mobilelogout.jhtml">[${message("shop.header.logout")}]</a>
				</span>
        	</div>
        	<ul>
        		<a href="${base}/mobile/member/order/list.jhtml"><li class="left-top"><span class="left-left">订单查询</span>
                	<img class="left-seven"></li></a>
                <a href="${base}/mobile/member/order/unpaidList.jhtml"><li class="left-top"><span class="left-left">待付款订单</span>
                	<img class="left-seven"></li></a>
            	<a href="${base}/mobile/member/favorite/list.jhtml"><li class="left-top"><span class="left-left">我的收藏</span>
                	<img class="left-seven"></li></a>
            	<a href="${base}/mobile/member/service/list.jhtml"><li class="left-top"><span class="left-left">网点查询</span>
            	<img class="left-seven"></li></a>
            	<!--<a href="${base}/mobile/member/waterquality/check.jhtml"><li class="left-top"><span class="left-left">水质查询</span>
            	<img class="left-seven"></li></a>-->
            	<a href="${base}/mobile/member/invitation/index.jhtml?rulename=我的邀请链接"><li class="left-top"><span class="left-left">邀请有奖</span>
            	<img class="left-seven"></li></a>
             <!--   <li class="left-top"><span class="left-left">商品评论</span>
                	<img class="left-seven"></li>
                	-->
            </ul>
            <ul>
            	<a href="${base}/mobile/member/profile/edit.jhtml"><li class="left-top"><span class="left-left">个人资料</span>
                	<img class="left-seven"></li></a>
                <a href="${base}/mobile/member/profile/mybalance.jhtml"><li class="left-top"><span class="left-left">我的余额</span>
                <img class="left-seven"></li></a>
                <a href="${base}/mobile/member/points/list.jhtml"><li class="left-top"><span class="left-left">我的积分</span>                	
                	<img class="left-seven"></li></a>
               <a href="${base}/mobile/member/password/edit.jhtml"> <li class="left-top"><span class="left-left">修改密码</span>
                	<img class="left-seven"></li></a>
               <a href="${base}/mobile/member/receiver/list.jhtml?istocart=0"> <li class="left-top"><span class="left-left">收货地址</span>
                	<img class="left-seven"></li></a>
            </ul>
    <!--        <ul>
            	<li class="left-top"><span class="left-left">预存款充值</span>
                	<img class="left-seven"></li>
                <li class="left-top"><span class="left-left">我的预存款</span>
                	<img class="left-seven"></li>
            </ul>
            -->
        </div>
	</div>
</body>
</html>