<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/pindao.css"/>
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
  <div id="index_place" style="width:400px;float:left">您的位置：<a href="/"><span>首页</span></a>><a href="${base}/article/categoryList/615.jhtml"><span>家电常识</span></a>><span>${articleCategory.name}</span></div>
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
    <div class="changshi-left-top">${articleCategory.name}</div>
    <div class="pindao">
    	[#if articles ??]
    		[#list articles as article]
			      <div class="pindao1">
			        <div class="pindao1-photo"><img src="${article.image}" style="width:186px;height:140px;"></div>
			        <div class="pindao1-right">
			          <div class="pindao-1">${article.seoTitle}</div>
			          <div class="pindao-2">${abbreviate(article.seoDescription, 30)} <a href="${base}/article/content/${article.id}.jhtml">... 阅读全文</a></div>
			          <div class="pindao-3">
			            <div class="pindao-31">
			              	[#list article.tags as tag]
			              		<div><input name="" type="button" value="${tag.name}" class="anniu9"></div>
			                [/#list]
			              <!--<div><input name="" type="button" value="空气能热水器" class="anniu9"></div>-->
			            </div>
			            <div class="pindao-32">已读:<span>${article.hits}</span></div>
			          </div>
			        </div>
			      </div>
			[/#list]	      
		[/#if]
		<!--
      <div class="pindao1">
        <div class="pindao1-photo"><img src="${base}/resources/shop/images/pindao-1.jpg"></div>
        <div class="pindao1-right">
          <div class="pindao-1">燃气热水器安装注意事项</div>
          <div class="pindao-2">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大 <a href="#">... 阅读全文</a></div>
          <div class="pindao-3">
            <div class="pindao-31">
              <div><input name="" type="button" value="热水器安装" class="anniu9"></div>
              <div><input name="" type="button" value="空气能热水器" class="anniu9"></div>
            </div>
            <div class="pindao-32">已读:<span>126</span></div>
          </div>
        </div>
      </div>

      <div class="pindao1">
        <div class="pindao1-photo"><img src="${base}/resources/shop/images/pindao-1.jpg"></div>
        <div class="pindao1-right">
          <div class="pindao-1">燃气热水器安装注意事项</div>
          <div class="pindao-2">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大 <a href="#">... 阅读全文</a></div>
          <div class="pindao-3">
            <div class="pindao-31">
              <div><input name="" type="button" value="热水器安装" class="anniu9"></div>
              <div><input name="" type="button" value="空气能热水器" class="anniu9"></div>
            </div>
            <div class="pindao-32">已读:<span>126</span></div>
          </div>
        </div>
      </div>

      <div class="pindao1">
        <div class="pindao1-photo"><img src="${base}/resources/shop/images/pindao-1.jpg"></div>
        <div class="pindao1-right">
          <div class="pindao-1">燃气热水器安装注意事项</div>
          <div class="pindao-2">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大 <a href="#">... 阅读全文</a></div>
          <div class="pindao-3">
            <div class="pindao-31">
              <div><input name="" type="button" value="热水器安装" class="anniu9"></div>
              <div><input name="" type="button" value="空气能热水器" class="anniu9"></div>
            </div>
            <div class="pindao-32">已读:<span>126</span></div>
          </div>
        </div>
      </div>

      <div class="pindao1">
        <div class="pindao1-photo"><img src="${base}/resources/shop/images/pindao-1.jpg"></div>
        <div class="pindao1-right">
          <div class="pindao-1">燃气热水器安装注意事项</div>
          <div class="pindao-2">太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大家介绍太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大太阳能热水器以其节能、环保、能源可再生等特点深受广大用户的欢迎。今天，小编就向大 <a href="#">... 阅读全文</a></div>
          <div class="pindao-3">
            <div class="pindao-31">
              <div><input name="" type="button" value="热水器安装" class="anniu9"></div>
              <div><input name="" type="button" value="空气能热水器" class="anniu9"></div>
            </div>
            <div class="pindao-32">已读:<span>126</span></div>
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
        	[@tag_list type="article"]
        		[#list tags as tag]
         	 		<li>${tag.name}</li>
         	 	[/#list]	
          	[/@tag_list]
          <!--
          <li>空气能热水器</li>
          <li>双高火灶具</li>
          <li>万家乐橱柜</li>
          <li>采暖炉用气量</li>
          <li>净化器</li>
          <li>热水器安装</li>
          <li>空气能热水器</li>
          <li>双高火灶具</li>
          <li>万家乐橱柜</li>
          <li>采暖炉用气量</li>
          <li>净化器</li>
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
          <!--循环注释
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
