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
  <input type="hidden" id="artId" value="${article.id}"/>
  <div id="guide_t">
  ${article.articleCategory.name}
  </div>
  <div id="lef">
   <div class="guide_tittle" id="artTitle">${article.title}</div>
   <div id="artContent">${article.content}</div>
   </div>
  <div id="guide_nav">
  [@article_category_root_list ]
   [#list articleCategories as category]
      [#if category.name=="购物指南"||category.name=="特色服务"||category.name="物流服务"||category.name=="支付方式"]
       <dl>
            <dt>>${category.name}</dt>
            [@article_list articleCategoryId = category.id ]
               [#list articles as ar]
                  <dd [#if article.id==ar.id ]id="this"[/#if]><a href="${base}/article/queryArticle/${ar.id}.jhtml">${ar.title}</a></dd>
			   [/#list]
			[/@article_list]
       </dl>
      [/#if]
   [/#list]
  [/@article_category_root_list]
     </div>
  </div>
</body>
[#include "/shop/include/footer.ftl" /]
</html>
