

<!--微商城店铺首页开始-->
<!-- 头部广告 -->
<div class="banner-big">
	<div class="index-2_banner imgurl">
		[@ad ad_position_name="微商城广告位" storeid="${store.id}"]
			[#if ads?has_content]
				[#list ads as ad]
					[#if ad.path == null && ad.type == "image"]
						<span><img src="/resources/mobile/images/weishangcheng.jpg" /></span>
					[#else]
						<span><img src="${ad.path}" /></span>
					[/#if]
                    [#if ad.type == "text"]
                    	${ad.content}
                    [/#if]
				[/#list]
			[/#if]
		[/@ad]
	</div>
</div>

