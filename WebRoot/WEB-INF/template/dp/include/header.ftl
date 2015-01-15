<!--页眉-->
[#include "/shop/include/header.ftl" /]

<!--店铺首页开始-->
<!-- 头部广告 -->
<div class="banner-big">
	<div class="index-2_banner">
		[@ad ad_position_name="头部广告1号位" storeid="${store.id}"]
			[#if ads?has_content]
				[#list ads as ad]
					[#if ad.path == null && ad.type == "image"]
						<img src="/resources/dp/images/dpbanner1.jpg" />
					[#else]
						<img src="${ad.path}" />
					[/#if]
                    [#if ad.type == "text"]
                    	${ad.content}
                    [/#if]
				[/#list]
			[/#if]
		[/@ad]
	</div>
</div>

<div class="index-2">
  <div class="index2-dao">
    <ul>
    	[@navigation_list storeid="${store.id}"]
    		[#list navigations as navigation]
					<li class="dao-2"><a href="${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a></li>
			[/#list]
      	[/@navigation_list]
    </ul>
  </div>
</div>
<div class="banner-1">
	[@ad ad_position_name="头部广告2号位" storeid="${store.id}"]
			[#if ads?has_content]
				[#list ads as ad]
					[#if ad.path == null && ad.type == "image"]
						<img src="/resources/dp/images/dpbanner2.jpg" />
					[#else]
						<img src="${ad.path}" />
					[/#if]
                    [#if ad.type == "text"]
                    	${ad.content}
                    [/#if]
				[/#list]
			[/#if]
	[/@ad]
</div>