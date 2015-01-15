<!-- 动态显示页面主题 -->
<script type="text/javascript">
		var store = getCookie("store");
		if(store > 0) {
			$("#shouye").attr("href","${base}/mobile/"+store+".jhtml");
		}
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
		} else if (uri.indexOf("/product/search.jhtml") > 0){
			domHtml.innerHTML = "${productKeyword}";
		} else if (uri.indexOf("/product/list.jhtml") > 0){
			var value = getParam("tagIds");
			domHtml.innerHTML =  value == "1" ? "热卖推荐" : "最新推荐";
		} else if (uri.indexOf("/cart/list.jhtml") > 0){
			domHtml.innerHTML =  "购物车";
		} else if(uri.indexOf("/product_category/index.jhtml") > 0){
			domHtml.innerHTML =  "商品分类";
		} else if(regEx1.test(uri)){
			domHtml.innerHTML =  "商品分类";
        } else if(uri.indexOf("/member/order/info.jhtml") > 0) {
			domHtml.innerHTML =  "订单信息";
		} else if(uri.indexOf("/mobile/member/order/unpaidList.jhtml") > 0) {
			domHtml.innerHTML =  "待付款订单";
		} else if(uri.indexOf("/mobile/member/order/payment.jhtml") > 0) {
			domHtml.innerHTML =  "订单支付";
		} else if(uri.indexOf("/mobile/member/order/list.jhtml") > 0) {
			domHtml.innerHTML =  "订单列表";
		} else if(uri.indexOf("/mobile/member/order/view.jhtml") > 0) {
			domHtml.innerHTML =  "订单详情";
		} else if(uri.indexOf("/member/index.jhtml") > 0) {
			domHtml.innerHTML =  "个人中心";
		}else if(uri.indexOf("/mobile/member/profile/mybalance.jhtml") > 0) {
			domHtml.innerHTML =  "我的余额";
		} else if(uri.indexOf("/product/content/") > 0) {
			domHtml.innerHTML =  "商品详情";
		} else if(uri.indexOf("/member/receiver/add.jhtml") > 0) {
			domHtml.innerHTML =  "新增收货地址";
		} else if(uri.indexOf("/member/receiver/edit.jhtml") > 0) {
			domHtml.innerHTML =  "编辑收货地址";
		} else if(uri.indexOf("/member/receiver/list.jhtml") > 0) {
			domHtml.innerHTML =  "收货地址列表";
		} else if(uri.indexOf("/mobile/register.jhtml") > 0) {
			domHtml.innerHTML =  "注册";
		} else if(uri.indexOf("/mobile/member/profile/edit.jhtml") > 0) {
			domHtml.innerHTML =  "修改个人信息";
		} else if(uri.indexOf("/mobile/member/password/edit.jhtml") > 0) {
			domHtml.innerHTML =  "修改密码";
		} else if(uri.indexOf("/mobile/member/favorite/list.jhtml") > 0) {
			domHtml.innerHTML =  "商品收藏";
		} else if(uri.indexOf("/mobile/member/profile/edit.jhtml") > 0) {
			domHtml.innerHTML =  "个人资料修改";
		} else if(uri.indexOf("/mobile/login/index.jhtml") > 0) {
			domHtml.innerHTML =  "用户登录";
		} else if(uri.indexOf("/mobile/register/index.jhtml") > 0) {
			domHtml.innerHTML =  "会员注册";
		} else if(uri.indexOf("/mobile/bind/index.jhtml") > 0) {
			domHtml.innerHTML =  "账号绑定";
		} else if(uri.indexOf("/mobile/unbind/index.jhtml") > 0) {
			domHtml.innerHTML =  "账号解绑";
		} else if(uri.indexOf("/mobile/product/newList.jhtml") > 0) {
			domHtml.innerHTML =  "新品推荐";
		} else if(uri.indexOf("/mobile/member/points/list.jhtml") > 0) {
			domHtml.innerHTML =  "我的积分";
		} else if(uri.indexOf("/mobile/store/toSearch.jhtml") > 0) {
			domHtml.innerHTML =  "搜索";
		} else if(uri.indexOf("/mobile/product/list/{0-1000}.jhtml") > 0) {
			var value = getParam("name");
			domHtml.innerHTML = value;
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