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
<link href="${base}/resources/gw/css/all_css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/gw/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/gw/css/css.css" rel="stylesheet" type="text/css" />
    <!--<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />-->
<LINK rel=stylesheet type=text/css href="${base}/resources/gw/js/ban2.css"></HEAD>
    
    
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/common.js"></SCRIPT>
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/jquery-1.4.min.js"></SCRIPT>
    <SCRIPT type=text/javascript>var $a =jQuery.noConflict();</SCRIPT>
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/nav.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/resources/gw/js/jquery-1.7.1.min.js"></SCRIPT>
<script type="text/javascript">
$().ready(function() {
	var articleId = $("#article_id").val();
	if(articleId == 838){
		$("#service_policy_children").show();
	}else if(articleId == 721){
		$("#service_fee_children").show();
	}
});
</script>
</head>
<body>
	[#include "/gw/include/header.ftl" /]
	<input type="hidden" value="${articleId}" id="article_id" />
	<DIV class=ban4>
<DIV id=bn><SPAN class=tu>
[@ad_position adname="官网 - ${articleCategoryroot.name}banner图"/]
</SPAN></DIV></DIV>

<DIV class=cpzx>
	<DIV class="cpzx_div zlm_bg2">
		<DIV class=gywjl_dq>目前您在：<A href="/gw/index.jhtml">首页</A> &gt; <A href="#">${articleCategoryroot.name}</A> &gt;   
			<H1 style="FONT-SIZE: 14px; FONT-WEIGHT: normal; DISPLAY: inline">${article.title}</H1>
		</DIV>
		<DIV class=jrwjl_z>
		[@article_list useCache=false name="${articleCategoryroot.name} - 左菜单栏" articleCategoryId=1 tagIds=1 ]
			[#list articles as a]
				${a.content}
			[/#list ]
		[/@article_list]
		<!-- <DIV class=gywjl_lmbt>关于万家乐</DIV>
		<DIV class="gy_zlm hong"><A 
		href="http://www.chinamacro.cn/guanyu/jianjie/a20.html">企业简介</A></DIV>
		<DIV class="gy_zlm "><A 
		href="http://www.chinamacro.cn/guanyu/jianjie/a27.html">关联企业</A></DIV>
		<DIV class="gy_zlm "><A 
		href="http://www.chinamacro.cn/guanyu/jianjie/a21.html">企业文化</A></DIV>
		<DIV class="gy_zlm "><A 
		href="http://www.chinamacro.cn/guanyu/jianjie/a22.html">品牌轨迹</A></DIV>
		<DIV class="gy_zlm "><A 
		href="http://www.chinamacro.cn/guanyu/jianjie/a23.html">光荣之路</A></DIV>
		<DIV class="gy_zlm "><A 
		href="http://www.chinamacro.cn/guanyu/lichen/">光辉历程</A></DIV>
		<DIV class="gy_zlm "><A 
		href="http://www.chinamacro.cn/guanyu/jianjie/a25.html">创意鉴赏</A></DIV>
		<DIV class="gy_zlm "><A 
		href="http://www.chinamacro.cn/guanyu/bao/">万家乐报</A></DIV>-->
		
		</DIV>
		<DIV class=gywjl_y>
			<DIV class=gy_nrbt>${article.title}</DIV>
			[#if article.id!=452 && article.id!=763 && article.id!=461 && article.id!=459 && article.id!=457 && article.id!=455 && article.id!=453]
			<DIV class=news2>
				<DIV class=neri_bt3>${article.subTitle}</DIV>
				<DIV class=neri_bt>${article.title}</DIV>
				<DIV class=neri_bt3>${article.subTitle2}</DIV>
			<DIV class=neri_bt2></DIV>
			<DIV class=news_sx>${article.createDate?string("yyyy-MM-dd")} 来源：万家乐官方网站</DIV>
			<DIV class=wz_nr>
				${article.content}
			</DIV>
			[#else]
			<DIV class=gy_nr>
				${article.content}
			</DIV>
			[/#if]
		</DIV>
	</DIV>
</DIV>
	
	[#include "/gw/include/footer.ftl" /]
</body>
</html>