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
<link href="${base}/resources/mobile/css/exchange.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {
//判断当前是否存在用户
	if (!$.checkLogin()) {
		$.redirectLogin(window.location.href, "${message("shop.common.mustLogin")}");
		return false;
	}
	var $exchange = $("div.convert_infor");
	[@flash_message /]
	// 兑换
	$exchange.click(function() {
		if (confirm("${message("shop.member.couponCode.exchangeConfirm")}")) {
			var $close = $(this).closest("div");
			var id = $close.find("input[name='id']").val();
			$.ajax({
				url: "exchange.jhtml",
				type: "POST",
				data: {id: id},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						setTimeout(function() {
							//location.href = "list.jhtml";
						}, 3000);
					}
				}
			});
		}
		return false;
	});
});
</script>
</head>
<body>
	[#assign current = "couponCodeExchange" /]
	[#include "/mobile/include/header.ftl" /]
	
	[#list page.content as coupon]
	<div class="convert">
		<div class="convert_name">
			<div class="nam"><strong>${coupon.name}</strong></div>
			<div class="convert_need">
				${message("Coupon.point")}：${coupon.point}
			</div>
		</div>
		<div class="convert_infor">
			<ul>
			   <li>使用期限</li>
			   <li>
				[#if coupon.beginDate??]
					<span title="${coupon.beginDate?string("yyyy-MM-dd HH:mm:ss")}">
						${coupon.beginDate}
					</span>
				[#else]
					-
				[/#if]
			   </li>
			   <li>
				[#if coupon.endDate??]
					<span title="${coupon.endDate?string("yyyy-MM-dd HH:mm:ss")}">
						${coupon.endDate}
					</span>
				[#else]
					-
				[/#if]
			   </li>
			   <li>
				<input type="hidden" name="id" value="${coupon.id}" />
				<a href="javascript:;" class="exchange">
					[${message("shop.member.handle.exchange")}]
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