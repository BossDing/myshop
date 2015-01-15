<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv_public.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>

<title>公告详情</title>
<script type="text/javascript">

function lookList(id,titleName){
$("#aaa"+id).show().siblings("div").hide();
$("#aaa"+id).siblings("div:first").show();
$("#titleName").html(titleName);
$("#infor_1").show();
$("#infor_2").hide();
}
</script>
</head>

<body>
[#include "/shop/include/header.ftl" /]
<!--页面追踪导航-->
   <div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>&gt;<span><a href="/article/adverte.jhtml">商城公告</a></span></div>
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
        
          <div class="collect_infor" id="infor_1" style="display:none;">
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
          
          
          
        
        <div class="collect_infor" id="infor_2">
            <div class="title1">${article.articleCategory.name}</div>
            <div class="title2">${article.title} </div> 
            <div class="time_ff">
               <div class="ti1">发表时间：${article.createDate}</div>
               
            </div>
            <div class="max_infor">
               
               ${article.content}
               
               </div>
            </div>
      <!--      <div class="last">上一篇：<a href="#">5月“唯爱”狂欢盛典活动中奖公示</a></div>
            <div class="next">下一篇：<a href="#">福建部分地区因暴雨延时送达公告</a></div>   -->
         </div>     
        
       <div id="clear"></div>
       </div>
        [#include "/shop/include/footer.ftl" /]
 </body>
</html>
