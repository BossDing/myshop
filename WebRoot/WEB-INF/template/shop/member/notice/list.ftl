<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/common.css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/gonggao.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>


<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>
<title>万家乐商城</title>
<script type="text/javascript"> 
	$(function(){
		var $pageNumber = $("#pageNumber");
		var $listForm = $("#listForm");
        $("#classify_act").mouseover(function(){
            $("#classify").slideDown();
         }); 
		 
		 $("#classify").mouseleave(function(){
            $(this).slideUp();
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp();
         }); 
                  $.pageSkip = function(pageNumber) {
				$pageNumber.val(pageNumber);														
				$listForm.submit();
				return false;								
		}
	});

  var number=1;
  function onwrite(w){
    if(number%2==0){
      w.checked=false;
    }else{
       w.checked=true;  
    }
    number++;
  }
</script>
</head>
<body>
[#include "/shop/include/header.ftl" /]
<!--产品试用开始-->
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>><span><a href="/member/index.jhtml">个人中心</a></span>><span>站内公告</span></div>
  <div class="kfdlb">
  	<ul>
		<li>分享到：</li>
		<li><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
		<li><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
		<li><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
		<li><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
		<li><a href="#"><img src="/resources/shop/images/xinxi_ico.png" width="17px" ;=""></a></li>
		<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
  	</ul>
  </div>
</div>

<!--个人资料开始-->

<div class="ziliao-big">
  <div class="ziliao-left">[#include "/shop/member/include/navigation.ftl" /]</div>
  <div class="ziliao-right">
    <div class="ziliao-right-1">
      <div class="right-11">站内公告</div>
      <div class="right-13">
      <form id="listForm" action="list.jhtml" method="get">
      <input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
        <table cellpadding="0";cellspacing="0">
          <tr class="tr-1">
            <td align="left" class="ty-1">主题</td>
            <td align="center" class="ty-2">发布时间</td>
          </tr>
          [#list page.content as notice]
          <tr class="tr-2">
            <td align="left" class="ty-1"><a href="/member/notice/content/${notice.id}.jhtml"><input type="checkbox" name="write" value="male" onClick="onwrite(this)"/>&nbsp;${notice.theme}<a></td>
            <td align="center" class="ty-2">${notice.noticeDate}</td>
          </tr>
          [/#list]
          <!--
          <tr class="tr-2">
            <td align="left" class="ty-1"><input type="checkbox" name="write" value="male" onClick="onwrite(this)"/>&nbsp;2014年年终大促即将开始！会员预购积分征集中</td>
            <td align="center" class="ty-2">2014-11-28</td>
            <td align="center" class="ty-2">未读&nbsp;|&nbsp;已读</td>
          </tr>-->
        </table>
        <form>
      </div>
      <!--<div class="no"><input type="checkbox" name="write" value="male" onClick="onwrite(this)"/>&nbsp;全选&nbsp;&nbsp;&nbsp;<span>删除</span></div>-->
            [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
			[#include "/shop/include/pagination.ftl"]
		[/@pagination]
    </div>
  </div>

</div>

<!--个人资料完-->
	[#include "/shop/include/footer.ftl" /]
</body>
</html>
