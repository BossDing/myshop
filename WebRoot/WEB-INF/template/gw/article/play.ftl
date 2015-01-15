<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "articleList"]
	<title>[#if articleCategory.seoTitle??]${articleCategory.seoTitle}[#elseif seo.title??][@seo.title?interpret /][/#if][#if systemShowPowered] - Powered By SHOP++[/#if]</title>
	<meta name="author" content="SHOP++ Team" />
	<meta name="copyright" content="SHOP++" />
	[#if articleCategory.seoKeywords??]
		<meta name="keywords" content="${articleCategory.seoKeywords}" />
	[#elseif seo.keywords??]
		<meta name="keywords" content="[@seo.keywords?interpret /]" />
	[/#if]
	[#if articleCategory.seoDescription??]
		<meta name="description" content="${articleCategory.seoDescription}" />
	[#elseif seo.description??]
		<meta name="description" content="[@seo.description?interpret /]" />
	[/#if]
[/@seo]
<title>123</title>

<script type="text/javascript" src="${base}/resources/gw/js/jquery-1.7.1.min.js"></script>
<script  type="text/javascript" src="${base}/resources/gw/js/jquery-ui-1.10.4.custom.min.js"></script>
<script  type="text/javascript" src="${base}/resources/gw/js/swfObject.js"></script>
<script  type="text/javascript" src="${base}/resources/gw/js/playVideo.js"></script>
<script type="text/javascript">
   $(document).ready(function(){
   		var video = $("#video").val();
		videoPlayer("${base}/resources/gw/data/video/"+video+".flv",1024,576,true,"d2");
   });
</script>
<style>
	#d2{
		margin:0 auto;
		width:1030px;
	}
</style>
</head>
<body>
<input type="hidden" value="${name}" id="video" />
<div id="d1">
</div>

<div id="d2">
</div>


<!--<div id="d2" style="padding-top:20px;width:auto;text-align:center;">
</div> -->

</body>
</html>
