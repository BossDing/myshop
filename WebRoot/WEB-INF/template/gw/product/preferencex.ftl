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
    <link href="${base}/resources/gw/css/jingpinxiangqing.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/all_js_index.js"></SCRIPT>
    <style>
    	#guide_nav dl dd{display:none;}
    </style>
    
</head>
<script type="text/javascript">
<!--$(function()-->
$(document).ready(function(){
		
	// 滚动固定
	window.onscroll = htmlScroll;
	var elFix = document.getElementById('nav_layout');
	htmlPosition(elFix);
	
	
	function htmlScroll()
	{
		var tolH = document.body.scrollHeight;
		if(isIE = navigator.userAgent.indexOf("MSIE")!=-1) { 
        	var delH = document.documentElement.scrollTop;
   		}else{
   			var delH = document.body.scrollTop;
   		}
		var useH = window.screen.availHeight;
		
		var top = document.body.scrollTop ||  document.documentElement.scrollTop;
		if(elFix.data_top < top)
		{
			elFix.style.position = 'fixed';
			elFix.style.top = 0;
			elFix.style.right = '50%';
            elFix.style.margin = '0 -590px 0 0';
			
		} else { elFix.style.position = 'absolute';}
		
		if(tolH - delH < useH+200){
			document.getElementById('nav_layout').style.display='none';
		}else{
			document.getElementById('nav_layout').style.display='block';
		}
	}
	
	function htmlPosition(obj)
	{
		var o = obj;
		var t = o.offsetTop;
		var l = o.offsetLeft;
		while(o = o.offsetParent)
		{
			t += o.offsetTop;
			l += o.offsetLeft;
		}
		obj.data_top = t;
		obj.data_left = l;
	}
	
	var oldHtmlWidth = document.documentElement.offsetWidth;
	window.onresize = function(){
		var newHtmlWidth = document.documentElement.offsetWidth;
		if(oldHtmlWidth == newHtmlWidth)
		{
			return;
		}
		oldHtmlWidth = newHtmlWidth;
		elFix.style.position = 'absolute';
		elFix.style.right = '0';
        elFix.style.margin = '0';
		htmlPosition(elFix);
		htmlScroll();
	}

//返回顶部代码
	$("#back-to-top").hide();
	//当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
	$(function () {
		$(window).scroll(function(){
		if ($(window).scrollTop()>100){
		$("#back-to-top").fadeIn(500);
		}
		else
		{
		$("#back-to-top").fadeOut(500);
		}
		});
		//当点击跳转链接后，回到页面顶部位置
		$("#back-to-top").click(function(){
		$('body,html').animate({scrollTop:0},100);
		return false;
		});
		});
	
});
</script>
<body>

    [#include "/gw/include/header.ftl" /]
    <input type="hidden" value="${idname}" id="idname" />
<!--头部结束--> 
<!--精品开始-->
<div class="content_jp">
  <div class="jp_xq">
  		<a name="p5"><img src="${base}/resources/gw/images/jp/${idname}5.jpg" width="1020"/></a>
  		<a name="p0"><img src="${base}/resources/gw/images/jp/${idname}0.jpg" width="1020"/></a>
      	<a name="p1"><img src="${base}/resources/gw/images/jp/${idname}1.jpg" width="1020"/></a>
        <a name="p2"><img src="${base}/resources/gw/images/jp/${idname}2.jpg" width="1020"/></a>
        <a name="p3"><img src="${base}/resources/gw/images/jp/${idname}3.jpg" width="1020"/></a>
        <a name="p4"><img src="${base}/resources/gw/images/jp/${idname}4.jpg" width="1020"/></a>
    </div>
  <div class="jp_nav_x" id="nav_layout">
      <div id="guide_nav">
       <dl><dt><a href="${base}/gw/product/preference.jhtml">精品推荐</a></dt></dl>
       <dl>
            <dt id="y_"><a href="${base}/gw/product/preferencex.jhtml?idname=y_">>燃气热水器</a></dt>
                  <dd><a href="#p5">概览</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p0">产品参数</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p1">产品介绍</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p2">产品细节</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p3">产品展示</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p4">核心技术</a><div class="left_dot dot_bg"></div></dd>
       </dl>
        <dl>
            <dt id="d_"><a href="${base}/gw/product/preferencex.jhtml?idname=d_">>电热水器</a></dt>
                  <dd><a href="#p5">概览</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p0">产品参数</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p1">产品结构</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p2">产品展示</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p3">核心技术</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p4">速热功能</a><div class="left_dot dot_bg"></div></dd>
       </dl>
       <dl>
            <dt id="yy_0"><a href="${base}/gw/product/preferencex.jhtml?idname=yy_0">>吸油烟机</a></dt>
            	 <dd><a href="#p5">概览</a><div class="left_dot dot_bg"></div></dd>
                 <dd><a href="#p0">产品参数</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p1">核心技术</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p2">产品细节</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p3">产品展示</a><div class="left_dot dot_bg"></div></dd>
       </dl>
      
       <dl>
            <dt id="rr_0"><a href="${base}/gw/product/preferencex.jhtml?idname=rr_0">>燃气灶具</a></dt>
                  <dd><a href="#p5">概览</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p0">产品参数</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p1">产品介绍</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p2">产品细节</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p3">产品展示</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p4">核心技术</a><div class="left_dot dot_bg"></div></dd>
       </dl>
       
       <dl>
            <dt id="x_0"><a href="${base}/gw/product/preferencex.jhtml?idname=x_0">>消毒柜</a></dt>
                  <dd><a href="#p5">概览</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p0">产品参数</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p1">产品介绍</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p2">产品细节</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p3">产品展示</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p4">核心技术</a><div class="left_dot dot_bg"></div></dd>
       </dl>
       <dl>
            <dt id="p_"><a href="${base}/gw/product/preferencex.jhtml?idname=p_">>壁挂炉</a></dt>
                  <dd><a href="#p5">概览</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p0">产品参数</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p1">产品介绍</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p2">产品细节</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p3">产品展示</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p4">核心技术</a><div class="left_dot dot_bg"></div></dd>
       </dl>
   <!--
        <dl>
            <dt id="k_"><a href="${base}/gw/product/preferencex.jhtml?idname=k_">>空气能热水器</a></dt>
                 <dd><a href="#p0">概览</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p1">核心技术</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p2">精良部件</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p3">产品展示</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p4">技术规格</a><div class="left_dot dot_bg"></div></dd>
       </dl>
   -->
       <dl>
            <dt id="Q3-"><a href="${base}/gw/product/preferencex.jhtml?idname=Q3-">>空气净化器</a></dt>
                <dd><a href="#p0">概览</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p1">核心技术</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p2">产品展示</a><div class="left_dot dot_bg"></div></dd>
                  <dd><a href="#p3">精良部件</a><div class="left_dot dot_bg"></div></dd>
				  <dd><a href="#p4">产品参数</a><div class="left_dot dot_bg"></div></dd>
       </dl>
      
     </div>
  </div>
  <div id="clear"></div>
</div>


<!--
<div class="jp_tittle">万家乐》精品推荐</div>
<div class="jp_pinlei_nav">
      <ul>
          <li>
              <dl>
              <dt><a href="${base}/gw/product/preferencex.jhtml?idname=y_">燃气热水器</a></dt>
              <dd><a href="#">概览</a></dd>
              <dd><a href="#">核心技术</a></dd>
              <dd><a href="#">精良部件</a></dd>
              <dd><a href="#">产品细节</a></dd>
              <dd><a href="#">产品展示</a></dd>
              <dd><a href="#">技术规格</a></dd>
              </dl>
          </li>
          <li>
              <dl>
              <dt><a href="${base}/gw/product/preferencex.jhtml?idname=d_">电热水器</a></dt>
              <dd><a href="#">概览</a></dd>
              <dd><a href="#">核心技术</a></dd>
              <dd><a href="#">精良部件</a></dd>
              <dd><a href="#">产品细节</a></dd>
              <dd><a href="#">产品展示</a></dd>
              <dd><a href="#">技术规格</a></dd>
              </dl>
          </li>
          <li>
              <dl>
              <dt><a href="#">燃气灶具</a></dt>
              <dd><a href="#">概览</a></dd>
              <dd><a href="#">核心技术</a></dd>
              <dd><a href="#">精良部件</a></dd>
              <dd><a href="#">产品细节</a></dd>
              <dd><a href="#">产品展示</a></dd>
              <dd><a href="#">技术规格</a></dd>
              </dl>
          </li>
          <li>
              <dl>
              <dt><a href="#">吸油烟机</a></dt>
              <dd><a href="#">概览</a></dd>
              <dd><a href="#">核心技术</a></dd>
              <dd><a href="#">精良部件</a></dd>
              <dd><a href="#">产品细节</a></dd>
              <dd><a href="#">产品展示</a></dd>
              <dd><a href="#">技术规格</a></dd>
              </dl>
          </li>
          <li>
              <dl>
              <dt><a href="#">消毒柜</a></dt>
              <dd><a href="#">概览</a></dd>
              <dd><a href="#">核心技术</a></dd>
              <dd><a href="#">精良部件</a></dd>
              <dd><a href="#">产品细节</a></dd>
              <dd><a href="#">产品展示</a></dd>
              <dd><a href="#">技术规格</a></dd>
              </dl>
          </li>
          <li>
              <dl>
              <dt><a href="${base}/gw/product/preferencex.jhtml?idnameQ3-">空气能热水器</a></dt>
              <dd><a href="#">概览</a></dd>
              <dd><a href="#">核心技术</a></dd>
              <dd><a href="#">精良部件</a></dd>
              <dd><a href="#">产品细节</a></dd>
              <dd><a href="#">产品展示</a></dd>
              <dd><a href="#">技术规格</a></dd>
              </dl>
          </li>
          <li>
              <dl>
              <dt><a href="${base}/gw/product/preferencex.jhtml?idname=p_">壁挂炉</a></dt>
              <dd><a href="#">概览</a></dd>
              <dd><a href="#">核心技术</a></dd>
              <dd><a href="#">精良部件</a></dd>
              <dd><a href="#">产品细节</a></dd>
              <dd><a href="#">产品展示</a></dd>
              <dd><a href="#">技术规格</a></dd>
              </dl>
          </li>
          </ul>
      </div>
      -->
      
<!--精品结束-->
<!--底部开始-->
<p id="back-to-top" style="display: block;"><a href="#top"><span></span>回到顶部</a></p>
[#include "/gw/include/footer.ftl" /]
<script>
	$().ready(function(){
	
		//$("#guide_nav dl").hover(function(){
			//alert(0);
		//	$(this).find("dd").css('display','block');
		//}).mouseout(function(){
			//alert(1);
		//	$(this).find("dd").css('display','none');
		//});
		// 边栏的展开和收起
		$('#guide_nav dl dt').click(function(){
			$(this).parent().nextAll().find("dd").slideUp();
			$(this).parent().prevAll().find("dd").slideUp();
			if($(this).parent().find("dd").css("display")=="none"){
				$(this).parent().find('dd').slideDown();
			}else{
				$(this).parent().find('dd').slideUp();
			}
		})
		document.getElementById("${idname}").click();
		$("#${idname}").addClass("this");
	});
</script>
</body>
</html>
