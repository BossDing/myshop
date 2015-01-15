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
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/common.js"></SCRIPT>
    <link href="${base}/resources/gw/css/css.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/jquery-1.4.min.js"></SCRIPT>
    <SCRIPT type=text/javascript>var $a =jQuery.noConflict();</SCRIPT>
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/nav.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/resources/gw/js/jquery-1.7.1.min.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/resources/gw/js/lanrenzhijia.js"></SCRIPT>
<LINK rel=stylesheet type=text/css href="${base}/resources/gw/js/ban2.css"></HEAD>
<script type="text/javascript">
$().ready(function() {
	var $productForm = $("#productForm");
	var $pageNumber = $("#pageNumber");

	$.pageSkip = function(pageNumber) {
		$pageNumber.val(pageNumber);
		$productForm.submit();
		return false;
	}

});
</script>
</head>
<body>
	[#include "/gw/include/header.ftl" /]
<DIV class=ban4>
<DIV id=bn><SPAN class=tu>
[@ad_position adname="官网 - 新闻中心banner图"/]
</SPAN></DIV></DIV>

	<DIV class=cpzx>
<DIV class="cpzx_div zlm_bg2">
<DIV class=gywjl_dq>目前您在: <a href="/gw/index.jhtml">首页</A> 
<CODE>&gt;</CODE> <A href="/gw/article/xwlist/21.jhtml">新闻中心</A> 
<CODE>&gt;</CODE> 
<H1 style="FONT-SIZE: 14px; FONT-WEIGHT: normal; DISPLAY: inline">企业新闻</H1></DIV>

<DIV class=jrwjl_z>
[@article_list useCache=false name="新闻中心 - 左菜单栏" articleCategoryId=1 tagIds=1]
[#list articles as a]
${a.content}
[/#list ]
[/@article_list]

</DIV>
<DIV class=gywjl_y>
<DIV class=gy_nrbt>新闻中心</DIV>
<DIV class=news>
<form id="productForm" action="#" method="get">
<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
[#if page.content??]
	[#list page.content as a]
		<DIV class=news_lb>
		<DIV class=news_bt><A title="" 
		href="${base}/gw/article/xwxlist/${a.id}.jhtml">${a.title}</A></DIV>
		<DIV class=news_sj>${a.createDate?string("yyyy-MM-dd")}</DIV></DIV>
	[/#list ]
	<br>
	[@pagination pageNumber = page.pageNumber totalPages = page.totalPages totals = page.total pattern = "javascript: $.pageSkip({pageNumber});"]
		[#include "/gw/include/pagination.ftl"]
	[/@pagination]
[/#if]
</from>

<!--<DIV class=news_fy>
<FORM method=get name=selectPageForm action=/article_cat.php>
<DIV id=pager class=pagebar><SPAN class="f_l f6" style="MARGIN-RIGHT: 10px">总计 
<B>307</B> 个记录</SPAN> <SPAN class=page_now>1</SPAN> <A 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_2.html">[2]</A> <A 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_3.html">[3]</A> <A 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_4.html">[4]</A> <A 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_5.html">[5]</A> <A 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_6.html">[6]</A> <A 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_7.html">[7]</A> <A 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_8.html">[8]</A> <A 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_9.html">[9]</A> <A 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_10.html">[10]</A> <A class=next 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_2.html">下一页</A> <A class=last 
href="http://www.chinamacro.cn/xinwen/qiye/ca13_26.html">...最末页</A> <INPUT 
type=hidden value=13 name=category> <INPUT type=hidden name=keywords> <INPUT 
type=hidden name=sort> <INPUT type=hidden name=order> <INPUT type=hidden 
value=13 name=cat> <INPUT type=hidden value=0 name=brand> <INPUT type=hidden 
value=0 name=price_min> <INPUT type=hidden value=0 name=price_max> <INPUT 
type=hidden name=filter_attr> <INPUT type=hidden value=list name=display> 
&nbsp;&nbsp;&nbsp;&nbsp; </DIV></FORM></DIV> -->


<SCRIPT language=JavaScript type=Text/Javascript>
<!--
function selectPage(sel)
{
  sel.form.submit();
}
//-->
</SCRIPT>
</DIV></DIV></DIV>



	
	[#include "/gw/include/footer.ftl" /]
</body>
</html>