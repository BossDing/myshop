<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="css/wangdianchaxun.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服务网点</title>
<link href="${base}/resources/shop/css/wangdianchaxun.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/pcasunzip.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/cookie.js"></script>
</head>

<script type="text/javascript">

$().ready(function() {
$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js',function(){
	    $("#aaacity")[0].innerHTML= remote_ip_info.city;
	   	});
});




function queryarea(){
	   	$("#productForm").submit();
}
</script>
<body>
[#include "/shop/include/header.ftl" /]
<div class="wangdian-big">
	<div class="wan-left">
		<div class="lefttop">
			<div class="left-1">当前城市：<span id="aaacity"></span></div>
			 <form id="productForm" action="${base}/service/list.jhtml" method="get">
			<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
			<div class="left-2">
				<div>
										<select name="province" id="province"></select>
										&nbsp;&nbsp
										<select name="city" id="city"></select>
										
							<script type="text/javascript">
					             var province ="";
								 var city = "";
								 var area="";
								 var c_str = $.cookie('city');
					           if(c_str!=null){
						          	var c_strs = c_str.split("|");
						          	if(c_strs[0])province =  c_strs[0];
						          	if(c_strs[1])city =  c_strs[1];
						          	if(c_strs[2])area =  c_strs[2];
					           }
					            new PCAS("province","city","area",province,city,area);
					          </script>
				</div>
			</div>
			
			<div class="left-4"><input value="" type="text"  class="zhanghu"/><span><a href="javascript:void(0)" onclick="queryarea()">检索网点</a></span></div>
		</div>
		</form>
		<div class="wep-q">[#include "/shop/include/map.ftl" /]</div>
	</div>
	<div class="wan-right">
		<div class="righttop">
			<div class="righttop-a">选定服务网点信息</div>
			<div class="righttop-b"></div>
		</div>
		<div class="rightbottom">
			<div class="rightbottom-c"><span class="shan"><a href="#"></a></span>服务网点信息列表<span class="xia"><a href="#"></a></span></div>
			<div class="rightbottom-d">
			[#if page.content?has_content]
			[#list page.content?chunk(4) as row]
            [#list row as service]
				<div class="eweq">
					<div class="lpo"><img src="${base}/resources/shop/images/hot-1.png" width="60px";height="29px"></div>
					<div class="lou">
						<div class="fjhw">${service.serviceName}</div>
						<div class="dsq"></div>
						<div class="dsq">${service.serviceAddress}</div>
						<div class="dsq">分享：<img src="${base}/resources/shop/images/tx_weibo_ico.png" width="15px";height="15px"><img src="${base}/resources/shop/images/renren_ico.png" width="15px";height="15px"><img src="${base}/resources/shop/images/weibo_ico.png" width="15px";height="15px"><img src="${base}/resources/shop/images/qq_ico.png" width="15px";height="15px"><img src="${base}/resources/shop/images/xinxi_ico.png" width="15px";height="15px"></div>
					</div>
				</div>
			[/#list]
			[/#list]
			[/#if]
			</div>
		</div>
	</div>
</div>
[#include "/shop/include/footer.ftl" /]
</body>
</html>
