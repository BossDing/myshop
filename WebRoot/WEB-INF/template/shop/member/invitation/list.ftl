<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.member.invitation.list")}[#if systemShowPowered] - Powered By HOMA[/#if]</title>
<meta name="author" content="HOMA Team" />
<meta name="copyright" content="HOMA" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/member.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/invitation.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>

<link href="${base}/resources/shop/css/top.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/footer.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.zclip.js"></script>
<script type="text/javascript">
var jiathis_config;//jiathis配置
$(document).ready(function() {

	[@flash_message /]
	
	url="http://"+location.host+"/?inviter_id="+${member.id};
	$("#invitation_link").html("<p style='font-weight:bold;'>${promotion.shareSummary}"+url+"</p>");
	
	var summary="${promotion.shareSummary}";

	jiathis_config = { 
		url: url, 
		title: " ",
		summary:summary
	};
	

	 if(window.clipboardData){
		$('#copy_button').click(function(){
			window.clipboardData.setData('text', url);  
			alert("已成功复制到剪切板！");
		});
	 }else{
		$('#copy_button').zclip({
			path:'${base}/resources/shop/js/ZeroClipboard.swf',
			copy:function(){return url;},
			afterCopy:function(){alert("已成功复制到剪切板！");}
		});
	 }

	
	
		
	
});
</script>

</head>
<body>
	[#assign current = "invitationList" /]
	[#include "/shop/member/include/header.ftl" /]
	<div class="container member">
		[#include "/shop/member/include/navigation.ftl" /]
		<div class="span18 last">
			<div class="list  yaoq">
				<div class="title">${message("shop.member.invitation.list")}</div>
				<table>
					<tr>
						<th class="promotion_title">${promotion.title}</th>
					</tr>
					<tr>
						<td id='summary' class="promotion_introdduction">${promotion.introduction}</td>
					</tr>
					<tr>
						<td class="promotion_introdduction" id='invitation_link'></td>
					</tr>
				</table>
				<div id="copy_and_share">
					<div>
						<input id="copy_button" type="button" value=""/>
					</div>
					<!-- JiaThis Button BEGIN -->
					<div class="jiathis_style" style="padding-top:8px;";>
						<span style="float:left;padding-left:30px">分享&nbsp</span>
						<a class="jiathis_button_qzone"></a>
						<a class="jiathis_button_tsina"></a>
						<a class="jiathis_button_tqq"></a>
						<a class="jiathis_button_weixin"></a>
						<a class="jiathis_button_renren"></a>
						<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
						<a class="jiathis_counter_style"></a>
					</div>
					<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>							
					<!-- JiaThis Button END -->	
				</div>
			</div>
		</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>