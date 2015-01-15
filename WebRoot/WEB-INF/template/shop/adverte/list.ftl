<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<head>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv_public.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
<title>${articleCategory.name}</title>
</head>
<script>
 $().ready(function(){
    if("${(article.articleCategory.id)!}"!=""){
      $("#aaa"+${article.articleCategory.id}).show().siblings("div").hide(); 
      $("#aaa"+${article.articleCategory.id}).siblings("div:first").show();
      $("#titleName").html("${article.articleCategory.name}");
    }
 });
function lookList(id,titleName){
$("#aaa"+id).show().siblings("div").hide();
$("#aaa"+id).siblings("div:first").show();
$("#titleName").html(titleName);	
}
</script>
<body>
[#include "/shop/include/header.ftl" /]
<!--页面追踪导航-->
   <div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</span>&gt;<span><a href="/article/adverte/${articleCategory.id}.jhtml">${articleCategory.name}</a></span></div>
		  <div class="kfdlb">
			  
		  			</div>   
				</div>




<div class="content">


<!--左边导航-->

   <div class="indiv_nav">
        <dl>
						<dl>
							<dt>
								${articleCategory.name}
							</dt>
							[#if articleCategory.children??&&articleCategory.children?size>0]
							[#list articleCategory.children as category]
								<dd>
									<a href="javascript:void(0)" onclick="lookList(${category.id},'${category.name}')">${category.name}</a>
								</dd>
							[/#list]
							[/#if]
						</dl>
        </div>
        
        
        <#-- 
        ??:  判断变量是否存在  (变量存在返回true， 不存在返回false )
        !:	 指定缺失变量的默认值
        
		        测试空值处理：
		${sss} 	:没有定义这个变量，会报异常！ 
		${sss!}  :没有定义这个变量，默认值是空字符串！  
		${sss!"abc"}  :没有定义这个变量，默认值是字符串abc！ 
         -->
        <div class="collect_infor">
        <div class="title1" id="titleName">${articleCategory.name}</div>
        		[#assign ii=0 /]
        		[#if articleCategory.children??&&articleCategory.children?size>0]
                    [#list articleCategory.children as category]
							[#assign ii=ii+1 /]
								 [@article_list articleCategoryId = category.id]
				<div id="aaa${category.id}" [#if ii==1]style="display:block;"[#else]style="display:none;"[/#if]>				 
  				<ul id="article_list">
									[#list articles as article]
									<li>
							            <a href="/article/info/${articleCategory.id}/${article.id}.jhtml">
							            <i>&nbsp;>&nbsp;</i>
							            <span>${article.title}</span>
							            <div class="time">发表时间：<span>${article.createDate}</span></div>
							            </a>
							         </li>
									[/#list]
				</ul>
				</div>
								[/@article_list]
								
					[/#list]
			[#else]	
			 [@article_list articleCategoryId = articleCategory.id]
			 <div>				 
  				<ul id="article_list">
					[#list articles as article]
						<li>
				            <a href="/article/info/${articleCategory.id}/${article.id}.jhtml">
				            <i>&nbsp;>&nbsp;</i>
				            <span>${article.title}</span>
				            <div class="time">发表时间：<span>${article.createDate}</span></div>
				            </a>
				         </li>
					[/#list]
				</ul>
			</div>
		    [/@article_list]
            [/#if]
            <!--<div class="more"><a href="#">显示更多<i></i></a></div>-->
          </div> 
        
       <div id="clear"></div>
       </div>
       [#include "/shop/include/footer.ftl" /]
 </body>
</html>
