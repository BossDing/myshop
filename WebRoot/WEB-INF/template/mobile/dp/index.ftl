<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <title>${store.name} - 专卖店</title> 
	    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
	    <link rel="stylesheet" href="${base}/resources/mobile/css/css_56e9a2f.css">
        <link rel="stylesheet" href="${base}/resources/mobile/css/history_store.css">
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/scroll.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
        <script type="text/javascript" src="${base}/resources/mobile/js/scrollTest.js"></script>
        <script type="text/javascript">
            $().ready(function() {
            	addCookie("store", ${store.id}, {expires: 1 * 2 * 60 * 60});
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
	    	<a href="${base}/mobile/index.jhtml">
	        	<div class="left"></div>
	        </a>
	        <div class="tit">
            	<span align="center">${store.name}</span>    
            </div>
	        <div class="right">
	            <ul>
	                <li class="user">
	                    <a href="${base}/mobile/member/index.jhtml" title="个人中心">
	                        <span class="ico"></span>
	                    </a>
	                </li>
	                <li class="cart">
                        <a href="${base}/mobile/cart/list.jhtml" title="购物车">
	                    	<span class="ico"></span>
	                	</a>
	                </li>
	            </ul>
	        </div>
	    </div>
	    <div id="viewport" class="viewport">
			<div class="scroll">
	        <div class="slide_01" id="slide_01" card card-nomb" style="visibility: visible;"></div>
	        <div class="dotModule_new">
				<div id="slide_01_dot"></div>
			</div>
		</div>
                
        <!--微商城广告位start-->
        [#include "/mobile/dp/include/header.ftl" /]
        <!--微商城广告位end-->
            
        <div class="nav-index card">
            <ul>
                <li><a href="${base}/mobile/store/product_category.jhtml?storeId=${store.id}"><span class="ico ico-qb"></span><span class="t"><span>全部分类</span></span></a></li>
                <li><a href="${base}/mobile/store/toSearch.jhtml?storeId=${store.id}"><span class="ico ico-ss"></span><span class="t"><span>搜索</span></span></a></li>
                <li><a href="${base}/mobile/store/product_list.jhtml?storeId=${store.id}"><span class="ico ico-pc"></span><span class="t"><span>所有商品</span></span></a></li>
                <li><a href="javascript:;">
                	<span class="ico ico-qb"></span>
                	<span class="t">历史店铺
                	</span>
                <div id="wrap">
		               <label id="sideMenuControl" for="sideToggle">
		               </label>
	                </div>
                </a>
                </li>
            </ul>
        </div>
                
	    <!--热销推荐start--> 
        <div class="card card-list card-nomb">
		   <div class="ney">热销推荐</div>
           		[@product_list tagIds = 1 count = 10 storeid = "${store.id}"]
	       			 [#list products?chunk(2) as row]
						[#list row as product]
				             [#if (product_index==0)] <div class="col2">[/#if]
				                <div class="row1">
				                    <a href="${base}/mobile${product.path}">
										<span class="imgurl">
											<img src="[#if product.image??]${product.image}[#else]${setting.defaultThumbnailProductImage}[/#if]" style="display: inline;">
											</span><span class="p"><span>${abbreviate(product.name, 60)}</span></span>
											<span class="p"><span>${currency(product.price, true)}元</span>
										</span>
									</a>
				                </div>
           		 			[#if (product_index==1)]  </div>[/#if]
						[/#list]
					[/#list]
				[/@product_list]
        	</div>
    	</div>
       <!--热销推荐end-->
       
	    [#include "/mobile/include/footer.ftl" /]
	</body>
</html>
