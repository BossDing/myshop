<!DOCTYPE html>
<html>
	<head>
		<title>${setting.mobilesiteName}</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/mobile/css/couponList.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {
//判断当前是否存在用户
	if (!$.checkLogin()) {
		$.redirectLogin(window.location.href, "${message("shop.common.mustLogin")}");
		return false;
	}
	[@flash_message /]
	$("input[name='copyItem']").click(function(){
		var e = $(this).siblings()[0];
		e.select();
		document.execCommand("Copy");
    });
});
</script>
</head>
<body>
[#assign current = "couponList" /]
[#include "/mobile/include/header.ftl" /]
[#list page.content as couponCode]
	<div class="convert">
		<div class="convert_name">
			<div class="nam">${couponCode.code}</div>
			<div class="convert_need">
				${couponCode.coupon.name}
			</div>
		</div>
		<div class="convert_infor">
			<ul>
			   <li>使用期限</li>
			   <li>
				[#if couponCode.usedDate??]
					<span title="${couponCode.usedDate?string("yyyy-MM-dd HH:mm:ss")}">${couponCode.usedDate}</span>
				[#else]
					-
				[/#if]
			   </li>
			   <li>
				[#if couponCode.coupon.endDate??]
					<span title="${couponCode.coupon.endDate?string("yyyy-MM-dd HH:mm:ss")}">${couponCode.coupon.endDate}</span>
				[#else]
					-
				[/#if]
			   </li>
			   <li>
				<input type="hidden" name="id" value="${coupon.id}" />
				<a href="javascript:;" class="exchange">
					是否已用:${couponCode.isUsed?string(message("shop.member.true"), message("shop.member.false"))}
				</a>
			   </li>
			</ul>
		</div>
	</div>
	[/#list]
	[#if !page.content?has_content]
		<p>${message("shop.member.noResult")}</p>
	[/#if]
	<div class="nav-index1 card" style="position:relative;height:3.55em;background:none;"></div>
	[#include "/mobile/include/footer.ftl" /]
</body>
</html>