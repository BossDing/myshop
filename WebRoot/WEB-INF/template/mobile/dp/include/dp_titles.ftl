<!-- 动态显示页面主题 -->
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript">
		// 获取url后search中指定name的值
		function getParam(names) {
			var sstr = document.location.search.substring(1,
					document.location.search.length).split("&");
			var len = sstr.length;
			for (var i = 0; i < len; i++) {
				var sobj = sstr[i].split("=");
				if (names == sobj[0])
					return sobj[1];
			}
		}
		var uri = window.location.href;
		var regEx1 = /\d+\.jhtml/; 
		var domHtml = document.getElementById("title");
                
		if(uri.indexOf("/product/toSearch.jhtml") > 0){
			domHtml.innerHTML = "搜索";
		} else if (uri.indexOf("/mobile/store/dp_category_list.jhtml?keyword") > 0){
			domHtml.innerHTML = "${keyword}";
		} else if(uri.indexOf("/mobile/store/product_list.jhtml") > 0) {
			domHtml.innerHTML = "所有商品";
		} else if(uri.indexOf("/mobile/store/toSearch.jhtml") > 0) {
			domHtml.innerHTML =  "搜索";
		}else if(uri.indexOf("/mobile/store/product_category.jhtml") > 0) {
			domHtml.innerHTML = "全部分类";
		}  else if(uri.indexOf("mobile/store/dp_category_list.jhtml") > 0) {
            var value = getParam("productCategoryId");
            //向后台发请求
            $.post(
		        "/mobile/store/find_category.jhtml", 
		        {"id":value}, 
		        function(data){ 
                	domHtml.innerHTML = data.name;
		        }
		    ); 
		} 
</script>