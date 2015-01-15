<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/changshi.css"/>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>
<title>万家乐商城</title>
<script type="text/javascript"> 
	$(function(){
        $("#classify_act").mouseover(function(){
            $("#classify").slideDown();
         }); 
		 x`
		 $("#classify").mouseleave(function(){
            $(this).slideUp();
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp();
         }); 
	});
</script>
</head>
<body>
<!--头部-->
[#include "/shop/include/header.ftl" /]
<!--头部结束--> 

<!--产品试用开始-->
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<a href="/"><span>首页</span></a>><a href="${base}/article/categoryList/615.jhtml"><span>家电常识</span></a></div>
  <div class="kfdlb">
      <ul>
          <li>分享到：</li>
          <li><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
          <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
      </ul>
  </div>
</div> 

<!--家电常识开始-->
<div class="changshi">
  <div class="changshi-left">
    <div class="photo">
    <!--	
      <img src="${base}/resources/shop/images/changshi-1.jpg">
      <div class="tite"><span>推荐产品--清新小厨房厨电配套常识</span></div>
      -->
      [@ad_position adname="首页 - 家电常识 - banner图"/]
    </div>
    
    
    <div class="new">
      <div class="new-1">
        <div class="new-1a">最新文章</div>
        	[#if page??]
        	   [#list page.content as article]
		        <div class="new-1b">
		          <div class="new-left"><img src="${article.image}" style="width:130px;height:98px;"></div>
		          <div class="new-right">
		            <div class="lop">${article.title}</div>
		            <div class="xq">${abbreviate(article.seoDescription, 150)} <a href="${base}/article/content/${article.id}.jhtml">...阅读全文</a>   <span class="sp1">发表日期:${article.createDate?string("yyyy/MM/dd")}</span></div>
		          </div>
		        [/#list]  
		     [/#if]     
        </div>
      </div>
    </div>
    
    
    <div class="changshi-xq">
    
    <!--文章分类循环-->
    [@article_category_children_list articleCategoryId = 615 count=6]
       [#list articleCategories as articleCategory]
	      <div class="changshi-xq1">
	        <div class="-xq1-top"><span>${articleCategory.name}</span><a href="${base}/article/list/${articleCategory.id}.jhtml">更多》</a></div>
	        <div  class="-xq1-one">
	        <!--每个文章循环-->
	        	[@article_list  articleCategoryId = articleCategory.id  count = 2]
	        		[#list articles as article]
			          <div class="-xq1-one1">
			            <div class="-xq1-one11"><img src="${article.image}" style="width:130px;height:98px;"></div>
			            <div class="-xq1-one12">
			              <div class="-xq1-one121">${article.seoTitle}</div>
			              <div class="-xq1-one122">${abbreviate(article.seoDescription, 30)} <a href="${base}/article/content/${article.id}.jhtml">... 阅读全文</a></div>
			            </div>
			          </div>
		          [/#list]
		        [/@article_list]  
		    <!--每个文章循环 end-->
	          <!--
	          <div class="-xq1-one1">
	            <div class="-xq1-one11"><img src="${base}/resources/shop/images/new-1.jpg"></div>
	            <div class="-xq1-one12">
	              <div class="-xq1-one121">燃气热水器安装注意事项</div>
	              <div class="-xq1-one122">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍 <a href="#">... 阅读全文</a></div>
	            </div>
	          </div>
	          -->
	        </div>
	      </div>
      	[/#list]
      [/@article_category_children_list]
      <!--文章分类循环 end-->

		<!--分类循环
      <div class="changshi-xq1">
        <div class="-xq1-top"><span>厨电产品频道</span><a href="#">更多》</a></div>
        <div  class="-xq1-one">
          <div class="-xq1-one1">
            <div class="-xq1-one11"><img src="${base}/resources/shop/images/new-1.jpg"></div>
            <div class="-xq1-one12">
              <div class="-xq1-one121">燃气热水器安装注意事项</div>
              <div class="-xq1-one122">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍 <a href="#">... 阅读全文</a></div>
            </div>
          </div>
          <div class="-xq1-one1">
            <div class="-xq1-one11"><img src="${base}/resources/shop/images/new-1.jpg"></div>
            <div class="-xq1-one12">
              <div class="-xq1-one121">燃气热水器安装注意事项</div>
              <div class="-xq1-one122">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍 <a href="#">... 阅读全文</a></div>
            </div>
          </div>
        </div>
      </div>

      <div class="changshi-xq1">
        <div class="-xq1-top"><span>生活电器频道</span><a href="#">更多》</a></div>
        <div  class="-xq1-one">
          <div class="-xq1-one1">
            <div class="-xq1-one11"><img src="${base}/resources/shop/images/new-1.jpg"></div>
            <div class="-xq1-one12">
              <div class="-xq1-one121">燃气热水器安装注意事项</div>
              <div class="-xq1-one122">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍 <a href="#">... 阅读全文</a></div>
            </div>
          </div>
          <div class="-xq1-one1">
            <div class="-xq1-one11"><img src="${base}/resources/shop/images/new-1.jpg"></div>
            <div class="-xq1-one12">
              <div class="-xq1-one121">燃气热水器安装注意事项</div>
              <div class="-xq1-one122">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍 <a href="#">... 阅读全文</a></div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="changshi-xq1">
        <div class="-xq1-top"><span>供暖产品频道</span><a href="#">更多》</a></div>
        <div  class="-xq1-one">
          <div class="-xq1-one1">
            <div class="-xq1-one11"><img src="${base}/resources/shop/images/new-1.jpg"></div>
            <div class="-xq1-one12">
              <div class="-xq1-one121">燃气热水器安装注意事项</div>
              <div class="-xq1-one122">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍 <a href="#">... 阅读全文</a></div>
            </div>
          </div>
          <div class="-xq1-one1">
            <div class="-xq1-one11"><img src="${base}/resources/shop/images/new-1.jpg"></div>
            <div class="-xq1-one12">
              <div class="-xq1-one121">燃气热水器安装注意事项</div>
              <div class="-xq1-one122">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍 <a href="#">... 阅读全文</a></div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="changshi-xq1">
        <div class="-xq1-top"><span>空气净化器频道</span><a href="#">更多》</a></div>
        <div  class="-xq1-one">
          <div class="-xq1-one1">
            <div class="-xq1-one11"><img src="${base}/resources/shop/images/new-1.jpg"></div>
            <div class="-xq1-one12">
              <div class="-xq1-one121">燃气热水器安装注意事项</div>
              <div class="-xq1-one122">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍 <a href="#">... 阅读全文</a></div>
            </div>
          </div>
          <div class="-xq1-one1">
            <div class="-xq1-one11"><img src="${base}/resources/shop/images/new-1.jpg"></div>
            <div class="-xq1-one12">
              <div class="-xq1-one121">燃气热水器安装注意事项</div>
              <div class="-xq1-one122">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍 <a href="#">... 阅读全文</a></div>
            </div>
          </div>
        </div>
      </div>
      -->
    </div>
  </div>
  
  <div class="changshi-right">
    <div class="changshi-right-top">
      <div class="changshi-right-top1">
        <div class="erweima"><img src="${base}/resources/shop/images/erweima.jpg"></div>
        <div class="sao">扫描二维码</br>关注万家乐微信服务号</div>
        <div class="how">获得更多直接资讯</div>
      </div>
    </div>
    <div class="biaoqian">
      <div class="biaoqian1">最热标签</div>
      <div class="biaoqian2">
        <ul>
        	<!--最热标签循环-->
        	[@tag_list type="article"]
        		[#list tags as tag]
         	 		<li>${tag.name}</li>
         	 	[/#list]	
          	[/@tag_list]
          	<!--最热标签循环 end-->
        <!--
          <li><a href="#">热水器安装</a></li>
          <li><a href="#">空气能热水器</a></li>
          <li><a href="#">双高火灶具</a></li>
          <li><a href="#">万家乐橱柜</a></li>
          <li><a href="#">采暖炉用气量</a></li>
          <li><a href="#">净化器</a></li>
          <li><a href="#">热水器安装</a></li>
          <li><a href="#">空气能热水器</a></li>
          <li><a href="#">双高火灶具</a></li>
          <li><a href="#">万家乐橱柜</a></li>
          <li><a href="#">采暖炉用气量</a></li>
          <li><a href="#">净化器</a></li>
          -->
        </ul>
      </div>
    </div>
    <div class="yuedu">
      <div class="yuedu1">最多阅读</div>
      <div class="yuedu2">
        <ul>
        	<!--最多阅读 循环-->
        	[@article_list articleCategoryId = 615 count=10 ]
        		[#list articles as article]
         	  		<li><a href="${base}/article/content/${article.id}.jhtml">>${abbreviate(article.title, 30)}</a></li>
         	  	[/#list]
          	[/@article_list]
          	<!--最多阅读 循环 end-->
          <!--
          <li><a href="#">>等特点深受广大用户的欢迎。今天，小编就</a></li>
          <li><a href="#">>太阳能热水器以其节能、环保、能源可再生</a></li>
          <li><a href="#">>等特点深受广大用户的欢迎。今天，小编就</a></li>
          <li><a href="#">>太阳能热水器以其节能、环保、能源可再生</a></li>
          <li><a href="#">>等特点深受广大用户的欢迎。今天，小编就</a></li>
          <li><a href="#">>太阳能热水器以其节能、环保、能源可再生</a></li>
          <li><a href="#">>等特点深受广大用户的欢迎。今天，小编就</a></li>
          <li><a href="#">>太阳能热水器以其节能、环保、能源可再生</a></li>
          <li><a href="#">>等特点深受广大用户的欢迎。今天，小编就</a></li>
          -->
        </ul>
      </div>
    </div>
  </div>
</div>
<!--家电常识完-->
<!--底部开始-->
[#include "/shop/include/footer.ftl" /]
</body>
</html>
