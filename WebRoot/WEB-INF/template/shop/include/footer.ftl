<html>
<head>
<meta http-equiv="content-type" content="charset=utf-8" />
<title>UP</title>
<link href="${base}/resources/shop/css/bottom.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="service">
  <div id="service_left">
    <Dl>
      <dt><strong>售后服务和保修政策</strong></dt>
      <dd>
        <ul>
          <li>燃气热水器</li>
          <li>电热水器</li>
          <li>空气能热水器</li>
          <li>吸油烟机</li>
          <li>燃气灶具</li>
          <li>消毒柜</li>
          <li>厨房电器</li>
          <li>燃气灶具</li>
          <li>生活小家电</li>
        </ul>
      </dd>
    </Dl>
  </div>
  <div id="service_right">
    <input type="button" value="在线客服"  class="cuser" />
  </div>
</div>
<div id="bottom_infor">
  <ul>
    [@article_category_root_list count = 5]
	[#list articleCategories as articleCategory]
		<li>
		      <dl>
			<dt>${articleCategory.name}</dt>
			[@article_list articleCategoryId = articleCategory.id count = 5]

					[#list articles as article]
						<dd>
							<a href="${base}/article/content/${article.id}.jhtml" title="${article.title}" target="_blank">${abbreviate(article.title, 30)}</a>
						</dd>
					[/#list]

			[/@article_list]
			</dl>
		</li>
	[/#list]
  [/@article_category_root_list]
    <!--<li>
      <dl>
        <dt>购物指南</dt>
        <dd><a href="#">注册登录</a></dd>
        <dd><a href="#">购物流程</a></dd>
        <dd><a href="#">联系客服</a></dd>
        <dd><a href="#">上门安装</a></dd>
      </dl>
    </li>
    <li>
      <dl>
        <dt>支付方式</dt>
        <dd><A href="#">在线支付</A></dd>
        <dd><a href="#">货到付款</a></dd>
        <dd><a href="#">发票制度</a></dd>
      </dl>
    </li>
    <li>
      <dl>
        <dt>物流发配</dt>
        <dd><a href="#">验货与签收</a></dd>
        <dd><a href="#">配送时间</a></dd>
        <dd><a href="#">配送范围</a></dd>
        <dd><a href="#">注意事项</a></dd>
      </dl>
    </li>
    <li>
      <dl>
        <dt>售后服务</dt>
        <dd><a href="#">服务政策</a></dd>
        <dd><a href="#">退换货说明</a></dd>
        <dd><a href="#">咨询投诉</a></dd>
      </dl>
    </li>
    <li>
      <dl>
        <dt>关于我们</dt>
        <dd><a href="#">官网微博</a></dd>
        <dd><a href="#">官方微信</a></dd>
      </dl>
    </li>-->
  </ul>
</div>
<div id="scope" > <img src="${base}/resources/shop/images/bottom_serve.png" width="1190" height="90" /> </div>
<div id="copyright">
[@navigation_list position = "bottom"]
				[#list navigations as navigation]
					
						<a href="${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a>
						[#if navigation_has_next]|[/#if]
					
				[/#list]
			[/@navigation_list]
<br />
  ${message("shop.footer.copyright", setting.siteName)}</div>
  [#include "/shop/include/statistics.ftl" /]
</body>
</html>