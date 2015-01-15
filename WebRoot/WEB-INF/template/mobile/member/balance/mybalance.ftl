<!DOCTYPE html>
<html>
	<head>
		<title>个人中心 - 余额查询</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="${base}/resources/mobile/css/common.css" />
		<link rel="stylesheet" type="text/css" href="${base}/resources/mobile/css/balance.css" />
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
	<div class = "mybanlance">
		<ul>
			<li>
				${message("shop.member.index.memberRank")}: ${member.memberRank.name}
			</li>
			<li>
				${message("shop.member.index.balance")}:
				<strong>${currency(member.balance, true, true)}</strong>
			</li>
			<li>
				${message("shop.member.index.amount")}:
				<strong>${currency(member.amount, true, true)}</strong>
			</li>
			<li>
				${message("shop.member.index.point")}:
				<strong>${member.point}</strong>
			</li>
		</ul>
	</div>
	</body>
</html>
  