<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/zhuyishixiang.css"/>
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
  <div id="index_place" style="width:400px;float:left">您的位置：<a href="/"><span>首页</span></a>><a href="${base}/article/categoryList/615.jhtml"><span>家电常识</span></a>><a href="${base}/article/list/${article.articleCategory.id}.jhtml"><span>${article.articleCategory.name}</span></a>><span>${article.seoTitle}</span></div>
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
	[#if article?has_content]
		  <div class="changshi-left">
			    <div class="changshi-left-top">
				      <div class="changshi-left-top1">${article.seoTitle}</div>
				      <div class="changshi-left-top2">时间：<span>${article.createDate?string("yyyy-MM-dd HH:mm:ss")}</span> 来源：<span>万家乐商城</span></div>
			    </div>
			    <div class="xiangqing">
				      <div class="xiangqing-1">${abbreviate(article.seoDescription, 150)}</div>
				      <!--<div class="xiangqing-1">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大</div>-->
				      <div class="xiangqing-2">
				          [#list article.tags as tags]
				          		<div><input name="" type="button" value="${tags.name}" class="anniu9"></div>
				          [/#list]
				          <!--<div><input name="" type="button" value="空气能热水器" class="anniu9"></div>-->
				      </div>
				      
				      
				      ${article.content}
				      <!--
				      <div class="xiangqing-3">万家乐电热水器都是面对热水器，左边是热水管（出水管），右边是冷水管（进水管），包括槽下式安装方式的机器。 只有小厨宝会使用槽下式安装方式，出水管和进水管在机器的顶部，由于安装的位置比较低，将进出水管设计在顶部方便安装和维修，进出水管在上面的绝对不可以改为向下安装，因为内部结构不同，具体参考下图：</div>
				      <div class="xiangqing-4">
					        <div class="xiangqing-41"><img src="${base}/resources/shop/images/xiangqing-1.jpg"></br><span>具体参考下图</span></div>
					        <div class="xiangqing-41"><img src="${base}/resources/shop/images/xiangqing-2.jpg"></br><span>具体参考下图</span></div>
				      </div>
				      -->
				      
				      
				      <div class="xiangqing-5">
				        <div class="xiangqing-51">更多关联文章</div>
				        <div class="xiangqing-52">
				          <ul>
				          	[@article_list articleCategoryId = article.articleCategory.id  count=2 ]
				        		[#list articles as article]
				         	  		<li><a href="${base}/article/content/${article.id}.jhtml">>${abbreviate(article.title, 30)}</a></li>
				         	  	[/#list]
				          	[/@article_list]
				          <!--
				            <li><a href="#">>万家乐太阳能热水器安装注意事项</a></li>
				            <li><a href="#">>万家乐热水器售后之电热水器三包政策</a></li>
				          -->  
				          </ul>
				        </div>
			      </div>
		    </div>
		[/#if]
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
        	[@tag_list type="article"]
        		[#list tags as tag]
         	 		<li>${tag.name}</li>
         	 	[/#list]	
          	[/@tag_list]
        	
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
        	[@article_list articleCategoryId = 615 count=10 ]
        		[#list articles as article]
         	  		<li><a href="${base}/article/content/${article.id}.jhtml">>${abbreviate(article.title, 30)}</a></li>
         	  	[/#list]
          	[/@article_list]
          <!-- 循环注释
          <li><a href="#">>太阳能热水器以其节能、环保、能源可再生</a></li>
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
