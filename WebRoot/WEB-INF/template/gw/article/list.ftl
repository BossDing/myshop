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
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/all_js.js"></SCRIPT>
<script type="text/javascript">
$().ready(function() {

	

});
</script>
</head>
<body>
	[#include "/gw/include/header.ftl" /]

	<DIV class=ban4>
<DIV id=bn><SPAN class=tu>
[#if articleCategory.id = 809]
	[@ad_position adname="官网 - 解决方案banner图 - 家居热水"/]
[#elseif articleCategory.id = 810]
	[@ad_position adname="官网 - 解决方案banner图 - 家居供暖"/]
[#elseif articleCategory.id = 811]
	[@ad_position adname="官网 - 解决方案banner图 - 商业热水"/]
[#elseif articleCategory.id = 812]
	[@ad_position adname="官网 - 解决方案banner图 - 做中国菜厨电"/]
[#elseif articleCategory.id = 813]
	[@ad_position adname="官网 - 解决方案banner图 - 集成厨柜"/]
[#elseif articleCategory.id = 814]
	[@ad_position adname="官网 - 解决方案banner图 - 健康空气"/]
[/#if]	


<!--<A href="http://www.chinamacro.cn/#"><IMG border=0 
alt=家居热水解决方案banner图 src="智慧热水_家居热水解决方案_解决方案_万家乐官方网站_files/jiejuefanan_02.jpg" 
width=1680 height=270></A>&nbsp; <A href="http://www.chinamacro.cn/#"><IMG 
alt=家居热水解决方案banner图 src="智慧热水_家居热水解决方案_解决方案_万家乐官方网站_files/jiejuefanan_01.jpg" 
width=1680 height=257></A>--> 
</SPAN></DIV></DIV>

<DIV class=jjfa_navbg>
	<DIV class=jjfa_navbg2>
		<DIV class=jjfa_nav>
		[@article_category_children_list articleCategoryId = articleCategory.parent.id count=6]
			[#list articleCategories as a]
					[#if a.id=articleCategory.id]
					<DIV class=jjfa_nav1><A href="${base}/gw/article/list/${a.id}.jhtml">${a.name}</A></DIV>
					[#else]
					<DIV class=jjfa_nav2><A href="${base}/gw/article/list/${a.id}.jhtml">${a.name}</A></DIV>
					[/#if]
			[/#list]
		[/@article_category_children_list]
		</DIV>
	</DIV>

	<DIV class=jjfa_nav3>
	[#list page.content as a]
		 [#if a.isTop==true]
		  	<A href="${base}/gw/article/plist/${a.id}.jhtml">[#if a.id ==article.id]<span class="bai">${a.title}</span>[#else]${a.title}[/#if]</A> | 
		  [/#if]
	[/#list]
	</DIV>

</DIV>
<DIV class=cpzx>
<DIV class=cpzx_div>
<DIV class=gywjl_dq>目前您在：<A href="${base}/gw/index.jhtml">首页</A> &gt;
[@article_category_parent_list articleCategoryId = articleCategory.id]
	[#list articleCategories as a]
	        <A href="#">${a.name}</A> &gt;	
	[/#list]
[/@article_category_parent_list]
<H1 style="FONT-SIZE: 14px; FONT-WEIGHT: normal; DISPLAY: inline">${article.title}</H1>

</DIV>
<DIV>
${article.content}
</DIV></DIV></DIV>

	
	[#include "/gw/include/footer.ftl" /]
</body>
</html>