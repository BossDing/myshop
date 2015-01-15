<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/common.css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/shenqingshiyong.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>
<title>万家乐商城</title>
<script type="text/javascript"> 
	$(function(){
	var $pageNumber = $("#pageNumber");        
	var $pageSize = $("#pageSize"); 
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
</script>
</head>
<body>
[#include "/shop/include/header.ftl" /]
<!--产品试用开始-->
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span>首页</span>><span>个人中心</span>><span>申请试用</span></div>
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

<!--个人资料开始-->

<div class="ziliao-big">
  <div class="ziliao-left">[#include "/shop/member/include/navigation.ftl" /]</div>
  <div class="ziliao-right">
    <div class="ziliao-right-1">
      <div class="right-11">申请试用</div>
      <div class="zhifen">
      <form id="listForm" action="applyList.jhtml" method="get">
      <input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
      <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
        <table cellpadding="0";cellspacing="0">
            <tr class="dks1  bei">
              <td align="center" class="td-b">申请商品</td>
              <td align="center" class="td-c">申请宣言</td>
              <td align="center" class="td-a">申请日期</td>
              <td align="center" class="td-a">地址</td>
              <td align="center" class="td-a">申请状态</td>
            </tr>
            
          </table>
          <table cellpadding="0";cellspacing="0">
            <!--
            <tr class="dks2">
              <td align="left" class="td-b">
                <div class="td-b-1">
                      <div class="td-b-1-left"></div>
                      <div  class="td-b-1-right">
                        <ul>
                          <li class="li-1">燃气热水器 MZGB-2390EVTZ</li>
                          <li class="li-2">预计市场价： <span>￥2199</span></li>
                          <li class="li-2">试用品数量：<span>1 </span>已有 <span>223</span> 人申请</li>
                          <li class="li-2">活动时间： <span>2014/08/19 -  2014/09/02</span></li>
                        </ul>
                      </div>
                </div>
              </td>
              <td align="left" class="td-c">刚好家里的热水器坏了，喜欢万家乐这个品牌，机会难得，特此申请!</td>
              <td align="center" class="td-a"><span>2014/08/19</span></td>
              <td align="center" class="td-a">广东广州番禺区大石镇洛涛南区3-6-503</td>
              <td align="center" class="td-a"><span>审核中</span></td>
            </tr>
            -->
         	[#list page.content as apply]        
         	<tr class="dks2">
              <td align="left" class="td-b">
                <div class="td-b-1">
                      <div class="td-b-1-left"><img src="${apply.trial.image}"/></div>
                      <div  class="td-b-1-right">
                        <ul>
                          <li class="li-1">${apply.product.name}</li>
                          <li class="li-2">预计市场价： <span>￥${apply.product.marketPrice}</span></li>
                          <li class="li-2">试用品数量：<span>1 </span>已有 <span>[#if apply.trial.appliernum == null]0
						            	[#else]
						            		${apply.trial.appliernum}
						            	[/#if]</span> 人申请</li>
                          <li class="li-2">活动时间： <span>${apply.trial.beginDate} -  ${apply.trial.endDate}</span></li>
                        </ul>
                      </div>
                </div>
              </td>
              <td align="left" class="td-c">${apply.applyReason}</td>
              <td align="center" class="td-a"><span>${apply.createDate}</span></td>
              <td align="center" class="td-a">${apply.area.fullName}${apply.address}</td>
              <td align="center" class="td-a"><ul>
              [#if apply.applyStatus == "approving" || apply.applyStatus == null]
						    		<li>审核中</li>
						    	[#elseif apply.applyStatus == "pass"]
						    		<li>审核通过</li>
						    		<li><a href="reportList.jhtml?applyId=${apply.id}">去填写试用报告</a></li>
						    	[#elseif apply.applyStatus == "reject"]
						    		<li>未通过审核</li>
						    	[/#if]
              </ul></td>
            </tr>
         	[/#list]
          </table>
          <!--
          <div class="yema">
            <ul>
                <li class="yema-1">1/2</li>
                <li class="yema-2"><a href="#"><<上一页</a></li>
                <li class="yema-1"><a href="#">1</a></li>
                <li class="yema-1"><a href="#">2</a></li>
                <li class="yema-3"><a href="#">下一页>></a></li>
            </ul>
          </div>
          -->
          [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
			[#include "/shop/include/pagination.ftl"]
		[/@pagination]
		<form>
      </div>
    </div>
    <div class="qita-big">
      <div  class="qita-big-a">其他试用产品</div>
      <div class="qita-big-b">
      <!--
        <div class="qita-big-b1">
          <div class="qita-big-b11"><a href="#"><img src="${base}/resources/shop/images/qita1.jpg"></a></div>
          <div class="qita-big-b12"><a href="#">万家乐 燃气热水器 智能燃气浴热水JSQ16-QH3  新品 智能控温 恒温机 </a></div>
          <div class="qita-big-b13"><a href="#">免费试用数量：<span>1</span> 台</br>剩余：<span>138</span> 小时<span> 22</span> 分 <span>35</span> 秒</br>已有 <span>223</span>人提交申请
          </a></div>
        </div>
        -->
        [#list trialNews as trial]
        [#list trial.products as product]
        	<div class="qita-big-b1">
          <div class="qita-big-b11"><a href="#"><img src="${trial.image}"></a></div>
          <div class="qita-big-b12"><a href="#">${product.name} </a></div>
          <div class="qita-big-b13"><a href="#">免费试用数量：<span>${trial.qtylimit}</span> 台</br>剩余：<span>138</span> 小时<span> 22</span> 分 <span>35</span> 秒</br>
          已有 <span>
          [#if trial.appliernum == null]0
			[#else]${trial.appliernum}
		  [/#if]
          </span>人提交申请
          </a></div>
        </div>
        [/#list]
        [/#list]
      </div>
    </div>
  </div>

</div>

<!--个人资料完-->
[#include "/shop/include/footer.ftl" /]
</body>
</html>
