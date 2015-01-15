<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "index"]
	<title>[#if seo.title??]精品推荐[#else]精品推荐[/#if][#if systemShowPowered] [/#if]</title>
	<meta name="author" content="SHOP++ Team" />
	<meta name="copyright" content="SHOP++" />
	[#if seo.keywords??]
		<meta name="keywords" content="[@seo.keywords?interpret /]" />
	[/#if]
	[#if seo.description??]
		<meta name="description" content="[@seo.description?interpret /]" />
	[/#if]
[/@seo]
    <link href="${base}/resources/gw/css/all_css.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/gw/css/jipin.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/all_js_index.js"></SCRIPT>
</head>
<body>
    [#include "/gw/include/header.ftl" /]
<!--头部结束--> 
<!--精品开始-->
<div class="jipin-big">
  <ul>
      <li class="jipin-1"><a href="${base}/gw/product/preferencex.jhtml?idname=y_"></a></li>
      <li class="jipin-2"><a href="${base}/gw/product/preferencex.jhtml?idname=y_"></a></li>
      <li class="jipin-3"><a href="${base}/gw/product/preferencex.jhtml?idname=d_"></a></li>
      <li class="jipin-4"><a href="${base}/gw/product/preferencex.jhtml?idname=k_"></a></li>
      <li class="jipin-5"><a href="${base}/gw/product/preferencex.jhtml?idname=p_"></a></li>
      <li class="jipin-6"><a href="${base}/gw/product/preferencex.jhtml?idname=rr_0"></a></li>
      <li class="jipin-7"><a href="${base}/gw/product/preferencex.jhtml?idname=yy_0"></a></li>
      <li class="jipin-8"><a href="${base}/gw/product/preferencex.jhtml?idname=x_0"></a></li>
      <li class="jipin-9"><a href="${base}/gw/product/preferencex.jhtml?idname=Q3-"></a></li>
  </ul>
</div>
<!--精品结束-->
<!--底部开始-->

[#include "/gw/include/footer.ftl" /]
</body>
</html>
