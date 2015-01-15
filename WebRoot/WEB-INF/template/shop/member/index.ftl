<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>个人中心</title>
    <link href="${base}/resources/shop/css/sns.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/indiv_my.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
    <script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
</head>
<script type="text/javascript">

		
    </script>
<body>
   [#include "/shop/include/header.ftl" /]
    <!--我的头部 end-->
    <!--内容区-->
    <!--页面追踪导航-->
   <div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>&gt;<span>我的账号</span></div>
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

                
    <!--主要内容-->
	<div class="content">        
		<!--左 导航区 -->
    		[#include "/shop/member/include/navigation.ftl" /]       
        <!--左 导航区 end-->
            <!--右 内容区-->
           		<!--我的订单-->
				   <div class="indiv_content">
				   <!--个人账户-->
				   <div class="my_account">
				     <dl>
				        <dt>个人账户</dt>
				        <dd><i>${member.username}</i>您好！欢迎光临万家乐网站！</dd>
				        <dd>收支明细</dd>
				        <dd>所有订单<a href="${base}/member/order/list.jhtml"><i>[${allOrderCount}]</i></a>我的积分<i>[${member.point}]</i></dd>
				     </dl>
				     </div>
				     <!--快捷通道-->
				     <div class="nimble">
				        <dl>
				        <dt>快捷通道</dt>
				        <dd>
				            <ul>
				            <li><a href="${base}/cart/list.jhtml">
				                <img src="${base}/resources/shop/images/my_shop_car.png" />
				                <p>我的购物车</p>
				                </a>
				                </li>
				             <li><a href="${base}/member/points/list.jhtml">
				                <img src="${base}/resources/shop/images/balance.png" />
				                <p>积分查询</p>
				                </a>
				                </li>
				             <li><a href="${base}/member/profile/edit.jhtml">
				                <img src="${base}/resources/shop/images/myself.png" />
				                <p>我的资料</p>
				                </a>
				                </li>
				              <li><a href="${base}/member/order/info.jhtml">
				                <img src="${base}/resources/shop/images/settle_accounts.png" />
				                <p>去结算</p>
				                </a>
				                </li>     
				            </ul>
				            </dd>
				        </dl>
				     </div>
				      <!--站内公告-->
				     <div class="ann">
				        <dl>
				        <dt>站内公告</dt>
				        [#list notices.content as notice]
				        <dd><b></b><span><a href="/member/notice/content/${notice.id}.jhtml">${notice.theme}</a></span>
				            <div class="time">${notice.noticeDate}</div>
				            </dd>
				        [/#list]
				        </dl>
				     </div>
				     <!--站内短信-->
				     <div class="message">
				        <dl>
				        <dt>站内短信</dt>
				        [#list messages.content as message]
				        <dd><b></b>
				            <span><a href="/member/message/list.jhtml">${message.title}</a></span>
				            <div class="time"> ${message.createDate}</div>
				            </dd>
				        [/#list]
				        </dl>
				     </div>
				  </div>
            <!--右 内容区end-->
        </div>
    [#include "/shop/include/footer.ftl" /]
</body>
</html>
