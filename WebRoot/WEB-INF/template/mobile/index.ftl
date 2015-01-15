<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <title>${setting.mobilesiteName}</title>
	    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	    <meta name="format-detection" content="telephone=no">
		<meta name="keywords" content="${setting.mobilesiteName}">
	    <link rel="stylesheet" href="${base}/resources/mobile/css/css_56e9a2f.css">
        <link rel="stylesheet" href="${base}/resources/mobile/css/history_store.css">
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/scroll.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
        <script type="text/javascript" src="${base}/resources/mobile/js/scrollTest.js"></script>
		<style type="text/css">
			*{margin:0;padding:0;list-style-type:none;}
			a,img{border:0;}
			
			.scroll{width:40em;height:10em;margin:0px auto 0 auto; position:relative;overflow:hidden;}
			.mod_01{float:left;width:40em;}
			.mod_01 img{display:block;width:100%;}
			.dotModule_new{padding:0 5px;height:11px;line-height:6px;-webkit-border-radius:11px;position:absolute;bottom:5px;right:178px;z-index:11;}
			#slide_01_dot{text-align:center;margin:3px 0 0 0;}
			#slide_01_dot span{display:inline-block;margin:0 3px;width:5px;height:5px;vertical-align:middle;background:#f7f7f7;-webkit-border-radius:5px;}
			#slide_01_dot .selected{background:#dddddd;}
		</style>
	      <script type="text/javascript">
            $().ready(function() {
            	removeCookie("store", {expires: 1 * 2 * 60 * 60});
	            var $historyStore = $("#historyStore ul");
				var $clearhistoryStore = $("#clearhistoryStore");
	            // 浏览记录
				var historyStore = getCookie("historyStore");
				var historyStoreIds = historyStore != null ? historyStore.split(",") : new Array();
				for (var i = 0; i < historyStoreIds.length; i ++) {
					if (historyStoreIds[i] == "${store.id}") {
						historyStoreIds.splice(i, 1);
						break;
					}
				}
				historyStoreIds.unshift("${store.id}");
				if (historyStoreIds.length > 6) {
					historyStoreIds.pop();
				}
				addCookie("historyStore", historyStoreIds.join(","), {path: "${base}/"});
				$.ajax({
					url: "${base}/mobile/store/history.jhtml",
					type: "GET",
					data: {ids: historyStoreIds},
					dataType: "json",
					traditional: true,
					cache: false,
					success: function(data) {
                    	$.each(data.lists,function(index,store){
                    
						$historyStore.append('<li><a href="${base}' + store.url + '">'+store.name+'</a></li>');
                    });
					}
				});
				
				// 清空浏览记录
				$clearhistoryStore.click(function() {
					$historyStore.empty();
					$(this).text("没有历史记录");
					removeCookie("historyStore", {path: "${base}/"});
				});
	            
            });
        </script>
	</head>
	<body>
		<!--历史店铺start-->
		<input type="checkbox" id="sideToggle">
		<aside id="item">
			<div id="historyStore" class="historyStore">
				<ul></ul>
				<a href="javascript:;" id="clearhistoryStore" class="clearhistoryStore">清空历史店铺</a>
			</div>
		</aside>
		<!--历史店铺end--> 
        
	    <div id="apptitle" class="header">
	        <div class="left">
	        
	        </div>
	        <div class="tit"></div>
	        <div class="right">
	            <ul>
	                <li class="user">
	                    <a href="${base}/mobile/member/index.jhtml" title="个人中心">
	                        <span class="ico"></span>
	                    </a>
	                </li>
	                <li class="cart"><a href="${base}/mobile/cart/list.jhtml" title="购物车">
	                    <span class="ico"></span>
	                </a>
	                </li>
	            </ul>
	        </div>
	    </div>
	    <div id="viewport" class="viewport">
	<div class="scroll">
	        <div class="slide_01" id="slide_01" card card-nomb" style="visibility: visible;">
				[@ad_position id=610/]
	     
	        </div>
	        	<div class="dotModule_new">
			<div id="slide_01_dot"></div>
		</div>
	</div>
	        <div class="nav-index card">
	            <ul>
	                <li><a href="${base}/mobile/product_category/index.jhtml"><span class="ico ico-qb"></span><span class="t"><span>全部分类</span></span></a></li>
	                <li><a href="${base}/mobile/product/toSearch.jhtml"><span class="ico ico-ss"></span><span class="t"><span>搜索</span></span></a></li>
	             <li><a href="${base}/mobile/store/list.jhtml"><span class="ico ico-pc"></span><span class="t"><span>线下实体店</span></span></a></li>    
                <li>
                		<a href="javascript:;">
		                	<span class="ico ico-qb"></span>
		                	<span class="t">历史店铺</span>
		                	<div id="wrap">
				               <label id="sideMenuControl" for="sideToggle"></label>
			                </div>
                		</a>
                	</li>
	               
	            </ul>
	        </div>
	        <div class="card card-list card-nomb">
	        	<div class="new">精品推荐</div>
	            
				[#if newpage.content?has_content]
	        		[#list newpage.content?chunk(2) as rows]
						[#list rows as np]
				             [#if (np_index==0)] <div class="col2">[/#if]
				                <div class="row1">
				                    <a href="${base}/mobile${np.path}">
										<span class="imgurl">
											<img src="[#if np.medium??]${np.medium}[#else]${setting.defaultThumbnailProductImage}[/#if]" style="display: inline;">
											</span><span class="p"><span>${abbreviate(np.name, 60)}</span></span>
											<span class="p"><span>${currency(np.price, true)}元</span>
										</span>
									</a>
				                </div>
							[#if (np_index==1)] </div>[/#if]
						[/#list]
					[/#list]
				[/#if]
	        </div>
	        
	        <div class="card card-list card-nomb">
	
				<div class="ney">热卖推荐</div>
	
	           [#if hotpage.content?has_content]
		       			 [#list hotpage.content?chunk(2) as row]
							[#list row as product]
					             [#if (product_index==0)] <div class="col2">[/#if]
					                <div class="row1">
					                    <a href="${base}/mobile${product.path}">
											<span class="imgurl">
												<img src="[#if product.medium??]${product.medium}[#else]${setting.defaultThumbnailProductImage}[/#if]" style="display: inline;">
												</span><span class="p"><span>${abbreviate(product.name, 60)}</span></span>
												<span class="p"><span>${currency(product.price, true)}元</span>
											</span>
										</a>
					                </div>
	           		 			[#if (product_index==1)]  </div>[/#if]
							[/#list]
						[/#list]
					[/#if]
	        </div>
	    </div>
	    <div class="col1"><a href="${base}/mobile/product_category/index.jhtml"><span>查看更多商品&nbsp;&gt;</span></a></div>
	    <div class="footer">
	        <div class="links">
	       [#if member??]
	       <a href="${base}/mobile/member/index.jhtml" class="user"><span>${member.username}</span></a>
	       [#else]
	        <a href="${base}/mobile/login/index.jhtml" class="nologin"><span>登录</span></a>
	       [/#if]
	        <a href="javascript:window.scrollTo(0,0);"><span>返回顶部</span></a>
	        </div>
	    </div>
	    [#include "/mobile/include/footer.ftl" /]
	    <script type="text/javascript">
			if(document.getElementById("slide_01")){
				var slide_01 = new ScrollPic();
				slide_01.scrollContId   = "slide_01"; //内容容器ID
				slide_01.dotListId      = "slide_01_dot";//点列表ID
				slide_01.dotOnClassName = "selected";
				slide_01.arrLeftId      = "sl_left"; //左箭头ID
				slide_01.arrRightId     = "sl_right";//右箭头ID
				slide_01.frameWidth     = 480;
				slide_01.pageWidth      = 480;
				slide_01.upright        = false;
				slide_01.speed          = 10;
				slide_01.space          = 30; 
				slide_01.initialize(); //初始化
			}
		</script>
	</body>
</html>
