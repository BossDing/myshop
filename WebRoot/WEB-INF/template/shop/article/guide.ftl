<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/guide.css"/>

<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<title>万家乐官方商城</title>
<script type="text/javascript">
$().ready(function(){
    $dd = $("#guide_nav dl dd");
    $id = $("#artId");
    $title = $("#artTitle");
    $content =$("#artContent");
 
});
</script>


</head>

<body>
	[#include "/shop/include/header.ftl" /]
<div class="content">
  <!--<div id="guide_t">购物指南</div>-->
  <!-- 您所在的位置 -->
  <div class="bar_top">
    <span class="tit_w">您所在的位置：</span>
    <ul>
      <li><a href="/">首页</a></li>
      <li>><a href="${base}/product/service.jhtml">服务中心</a></li>
      <li>><strong>${article.title}</strong></li>
    </ul>
  </div>
  <!--banner imag-->
  <div class="banner_img_below">
    [@ad_position adname="购物指南banner" ]
    [/@ad_position]  
  </div>
  <input type="hidden" id="artId" value="${article.id}"/>
  
  
  <div id="lef">
   <div class="guide_tittle" id="artTitle">${article.title}</div>
   <div id="artContent">${article.content}</div>
   </div>
   
   
  <div id="guide_nav">
   [@article_category_root_list]
   [#list articleCategories as category]
      [#if category.name=="购物指南"||category.name=="特色服务"||category.name="物流服务"||category.name=="支付方式"||category.name=="售后服务"||category.name=="关于我们"]
       <dl>
            <dt>>${category.name}</dt>
            [@article_list articleCategoryId = category.id ]
               [#list articles as ar]
                  <dd [#if article.id==ar.id ]  class="selected"[/#if] ><a href="${base}/article/queryArticle/${ar.id}.jhtml">${ar.title}</a><div class="left_dot dot_bg"></div></dd>
			   [/#list]
			[/@article_list]
       </dl>
      [/#if]
   [/#list]
  [/@article_category_root_list]
     </div>
     <div id="clear"></div>
  </div>
</body>
[#include "/shop/include/footer.ftl" /]
</html>
