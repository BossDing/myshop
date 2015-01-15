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
    <link href="${base}/resources/gw/css/css.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="${base}/resources/gw/js/ban2.css"></HEAD>
    
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/common.js"></SCRIPT>
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/jquery-1.4.min.js"></SCRIPT>
    <SCRIPT type=text/javascript>var $a =jQuery.noConflict();</SCRIPT>
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/nav.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/resources/gw/js/jquery-1.7.1.min.js"></SCRIPT>
<SCRIPT type=text/javascript src="${base}/resources/gw/js/lanrenzhijia.js"></SCRIPT>
<script type="text/javascript">
	$().ready(function() {

    	[@flash_message /]  
    	
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
		<DIV id=bn>
			<SPAN class=tu>
			[@ad_position adname="官网 - ${articleCategoryroot.name}banner图"/]
			</SPAN>
		</DIV>
	</DIV>

	<DIV class=cpzx>
		<DIV class="cpzx_div zlm_bg2">
			<DIV class=gywjl_dq>目前您在：<A href="/gw/index.jhtml">首页</A> &gt; <A href="#">${articleCategoryroot.name}</A> &gt;   
				<H1 style="FONT-SIZE: 14px; FONT-WEIGHT: normal; DISPLAY: inline">${articleCategory.name}</H1>
			</DIV>
			<DIV class=jrwjl_z>
				[@article_list useCache=false name="${articleCategoryroot.name} - 左菜单栏" articleCategoryId=1 tagIds=1 ]
					[#list articles as a]
						${a.content}
					[/#list ]
				[/@article_list]
			</DIV>
			<DIV class=gywjl_y>
				<DIV class=gy_nrbt>${articleCategory.name}</DIV>
				<table cellpadding="5" style=" background-color:#fff;">
				<tr>
					  <!--
					  <td valign="top">
					  		<img src="/upload/image/201410/cjwtjt.jpg">
					  </td>
					  -->
					  <td>
							<DIV class=news style=" margin:20px 20px;padding: 0px;width: 750px;">
									<p style="font-size:16px;">
										<strong>${articleCategory.seoTitle}</strong> 
									</p>
									<p style="margin: 20px 0px;font-size: 14px;">
										${articleCategory.seoDescription}
									<p>
									<form id="productForm" action="#" method="get">
										<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
										<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
										[#if page.content??]
											[#list page.content as a]
												<DIV class=news_lb>
												<DIV class=news_bt>
                                                    <A title="${a.title}" href="javascript:;" onclick="showContent('${a.id}')">
                                                        	${a.title}
                                                        <input type="hidden" id="${a.id}_input"/>
                                                    </A>
                                                </DIV>
                                                <div style="display:none;" id="${a.id}">
                                                    ${a.content}
                                                </div>
                                                <!--<script type="text/javascript">
                                                	var $link = $("div #${a.id} a");
                                                    if ($link != undefined && $link != null) {
                                                    	$("div #${a.id}").prev().children("a").attr("href", $link.attr("href"));
                                                    }
                                                </script>-->
												<DIV class=news_sj>${a.createDate?string("yyyy-MM-dd")}</DIV></DIV>
											[/#list]
                                               <script type="text/javascript">
                                               		function showContent(id) {
                                                   		document.location.href = $('#' + id +' a').attr("href");
                                                   	}
                                               </script>
											<br>
											[@pagination pageNumber = page.pageNumber totalPages = page.totalPages totals = page.total pattern = "javascript: $.pageSkip({pageNumber});"]
												[#include "/gw/include/pagination.ftl"]
											[/@pagination]
										[/#if]
									</from>
									<div  style="margin-top:30px;" >
											    <!--文章内容-->
											    <DIV class=neri_bt id="title"></DIV>
											    <DIV class=wz_nr id="content">
														
												</DIV>
									</div>
							 </DIV>
				 		</td>
				 	</tr>
				 </table>
			   </DIV>
		</DIV>
	</DIV>		
		
		
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
		
		        
	
	[#include "/gw/include/footer.ftl" /]
	<SCRIPT language=JavaScript type=Text/Javascript>
		<!--
		function selectPage(sel)
		{
		  sel.form.submit();
		}
		//-->

	function showArticle(articleId){
		//alert(1);
		$.ajax({
					url: "${base}/gw/article/articleDetail.jhtml",
					type: "POST",
					//data: $repair.serialize(),
					data: {
						articleId: articleId
					},
					dataType: "json",
					cache: false,
					//beforeSend: function() {
					  //$("#submit1").prop("disabled", true);
					//},
					success: function(data) {
						$("#title").html(data.article[0].title);
						$("#content").html(data.article[0].content);
						/*
						if (data.message.type == "success") {
							var str="";
							for(var i=0;i<data.articleList.length;i++){
								str+=data.articleList[i].title;
								str+=data.articleList[i].content;
							}
							$("#article_detail").html(str);
						
							setTimeout(function() {
								 //$("#submit1").prop("disabled", false);
								  window.location.href = "/gw/serviceOnline/problemQuery.jhtml";
							}, 3000);
							
							//alert($productCategory.val());
							//alert(message);
							//清空表单
							//$("#username").text(data.member);
							//$("#barCode").text(data.barCode);
							//$("#endDate").text(data.endDate);
							//alert(data.barCode);
							//alert(data.endDate);
						} else if (data.message.type == "error"){
							//$("#submit1").prop("disabled", false);
							setTimeout(function() {
									//$submit.prop("disabled", false);
									if(data.message.content == "用户还没有登录"){
										window.location.href = "${base}/login.jhtml";
									}
								}, 2000);
							return false;
						}else if (data.message.type == "warn"){
							return false;
						}
						*/
						
					}
				});
			}
		</SCRIPT>
</body>
</html>