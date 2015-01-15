<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "articleList"]
	<title>社会招聘</title>
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
[@ad_position adname="官网 - 社会招聘banner图"/]
</SPAN></DIV></DIV>

	<DIV class=cpzx>
<DIV class="cpzx_div zlm_bg2">
<DIV class=gywjl_dq>目前您在: <A href="">首页</A> 
<CODE>&gt;</CODE> <A href="">社会招聘</A> 
<CODE>&gt;</CODE> 
<H1 style="FONT-SIZE: 14px; FONT-WEIGHT: normal; DISPLAY: inline"><A 
href="">加入万家乐</A></H1></DIV>

<DIV class=jrwjl_z>
[@article_list useCache=false name="加入万家乐 - 左菜单栏" articleCategoryId=1 tagIds=1]
[#list articles as a]
${a.content}
[/#list ]
[/@article_list]			

</DIV>
<DIV class=gywjl_y>
<DIV class=gy_nrbt>加入万家乐</DIV>		
<DIV class=news>
<form id="productForm" action="#" method="get">
<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
<table width="660" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="270" height="30" align="left" valign="middle" style="padding-left:29px;"><strong>职位名称</strong></td>
    <td width="130" height="30" align="center" valign="middle"><strong>工作地点</strong></td>
    <td width="130" height="30" align="center" valign="middle"><strong>招聘人数</strong></td>
    <td width="130" height="30" align="center" valign="middle"><strong>更新时间</strong></td>
  </tr>
[#list page.content as a]
[#if page.content??]
  <tr>
    <td class="zp1"><a href="${base}/gw/article/zpzlist/${a.id}.jhtml" title="">${a.title}</a></td>
    <td class="zp2">${a.jobname}</td>
    <td class="zp2">${a.jobnumber}</td>
    <td class="zp2">${a.modifyDate}</td>  
  </tr>	
[/#if]
[/#list ]
</table>
</div>	
[@pagination pageNumber = page.pageNumber totalPages = page.totalPages totals = page.total pattern = "javascript: $.pageSkip({pageNumber});"]
		[#include "/gw/include/pagination.ftl"]
	[/@pagination]
</from>
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