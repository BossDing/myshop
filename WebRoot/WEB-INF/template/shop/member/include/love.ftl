<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${base}/resources/shop/css/zzsc.css" type="text/css">
<script type="text/javascript" src="${base}/resources/shop/js/zzsc.js"></script>
<script type="text/javascript">
	$().ready(function(){
		var $historyProduct = $("#historyProduct ul");
		var $clearHistoryProduct = $("#clearHistoryProduct");
		
		// 浏览记录
		var historyProduct = getCookie("historyProduct");
		var historyProductIds = historyProduct != null ? historyProduct.split(",") : new Array();
		for (var i = 0; i < historyProductIds.length; i ++) {
			if (historyProductIds[i] == "${product.id}") {
				historyProductIds.splice(i, 1);
				break;
			}
		}
		historyProductIds.unshift("${product.id}");
		if (historyProductIds.length > 5) {
			historyProductIds.pop();
		}
		addCookie("historyProduct", historyProductIds.join(","), {path: "${base}/"});
		$.ajax({
			url: "${base}/product/history.jhtml",
			type: "GET",
			data: {ids: historyProductIds},
			dataType: "json",
			traditional: true,
			cache: false,
			success: function(data) {
				$.each(data, function (index, product) {
					var thumbnail = product.thumbnail != null ? product.thumbnail : "${setting.defaultThumbnailProductImage}";
					$historyProduct.append('<li class="ml0">'
															+'<a href="${base}' + product.path + '" target="_blank" class="imgwrap">'
															+'<img src="' + thumbnail + '" \/>'
															+'<div id="recommend_once_infor">'
															+'<p class="model">'+product.name+'<\/p>'
															+'<p class="heading">专利双引擎速热技术<\/p>'
															+'<p class="price">'+ currency(product.price, true) + '<\/p>'
															+'<\/div>'
															+'<\/a>'
															+'<p class="mt10">'
															+'<a href="javascript:addCart('+product.id+');"><input type="button" class="add_shopping_cart" value="加入购物车" \/></a>'
															+'<a href="${base}' + product.path + '"><input type="button" class="understand" value="了解产品" \/></a>'
															+'<\/p>'
															+'<\/li>');
				});
			}
		});
	});
	
	function addCart(id){
		$.ajax({
			url: "${base}/cart/add.jhtml",
			type: "POST",
			data: {id: id, quantity: 1},
			dataType: "json",
			cache: false,
			success: function(message){
				window.location.href="${base}/cart/list.jhtml";
			}
		});
	}
	
</script>

</head>
<body>

  <div class="case">
    <div class="title cf">
      <ul class="title-list fr cf ">
        <li class="on">猜你喜欢</li>
        <li>最近浏览</li>
        <li>大家都在买</li>
        <p><b></b></p>
      </ul>
    </div>
    <div class="product-wrap">
     <!--案例1-->
	      <div class="product show">
	        <ul class="cf">
	        	  [@product_list count = 4 ]
						[#list products as product]
				          <li class="ml0">
					          	<a href="${base}${product.path}" target="_blank" class="imgwrap">
					          		  <img src="${product.image}">
							          <div id="recommend_once_infor">
								          <p class="model">${abbreviate(product.name, 30)}</p>
								          <p class="heading">专利双引擎速热技术</p>
								          <p class="price">${currency(product.price, true, true)}</p>
					        		   </div>
					        	</a>
					            <p class="mt10">
							            <a href="javascript:addCart(${product.id});"><input type="button" class="add_shopping_cart" value="加入购物车" /></a>
							            <a href="${base}${product.path}"><input type="button" class="understand" value="了解产品" /></a>
					            </p>
				    		</li>
					    [/#list]
					[/@product_list]		
		    	</ul>	
	      </div>
      <!--案例2-->
      <div class="product" id="historyProduct">
	        <ul class="cf">
		         <!--
		          <li class="ml0">
			          <a href="#" target="_blank" class="imgwrap">
				          <img src="/resources/shop/images/n_3.png">
				           <div id="recommend_once_infor">
					          <p class="model">万家乐 燃气热水器 LJSQ20-11UF3豪华版</p>
					          <p class="heading">专利双引擎速热技术</p>
					          <p class="price">￥3999.00</p>
				          </div>
					  </a>
			           <p class="mt10">
				            <input type="button" class="add_shopping_cart" value="加入购物车" />
				            <input type="button" class="understand" value="了解产品" />
			            </p>
		          </li>
          -->
          
        </ul>
      </div>
      
      <!--案例3-->
      <div class="product">
        <ul class="cf">
        	[#list hotproducts as product]
	          <li class="ml0">
		          <a href="${base}${product.path}" target="_blank" class="imgwrap">
				          <img src="${product.image}">
				          <div id="recommend_once_infor">
						          <p class="model">${abbreviate(product.name, 30)}</p>
						          <p class="heading">专利双引擎速热技术</p>
						          <p class="price">${currency(product.price, true, true)}</p>
				          </div> 
		          </a>
		          <p class="mt10">
		          		  <a href="javascript:addCart(${product.id});"><input type="button" class="add_shopping_cart" value="加入购物车" /></a>
		            	  <a href="${base}${product.path}"><input type="button" class="understand" value="了解产品" /></a>
	              </p>
	          </li>
           [/#list]
          
          <!--
          <li>
          <a href="#" target="_blank" class="imgwrap">
          <img src="/resources/shop/images/n_3.png">
           <div id="recommend_once_infor">
          <p class="model">万家乐 燃气热水器 LJSQ20-11UF3豪华版</p>
          <p class="heading">专利双引擎速热技术</p>
          <p class="price">￥3999.00</p>
        </div></a>
             <p class="mt10">
            <input type="button" class="add_shopping_cart" value="加入购物车" />
            <input type="button" class="understand" value="了解产品" />
            </p>
			
          </li>
          <li>
          <a href="#" target="_blank" class="imgwrap">
          <img src="/resources/shop/images/n_3.png">
           <div id="recommend_once_infor">
          <p class="model">万家乐 燃气热水器 LJSQ20-11UF3豪华版</p>
          <p class="heading">专利双引擎速热技术</p>
          <p class="price">￥3999.00</p>
        </div></a>
             <p class="mt10">
            <input type="button" class="add_shopping_cart" value="加入购物车" />
            <input type="button" class="understand" value="了解产品" />
            </p>
			
          </li>
          <li>
          <a href="#" target="_blank" class="imgwrap">
          <img src="/resources/shop/images/n_3.png">
           <div id="recommend_once_infor">
          <p class="model">万家乐 燃气热水器 LJSQ20-11UF3豪华版</p>
          <p class="heading">专利双引擎速热技术</p>
          <p class="price">￥3999.00</p>
        </div></a>
             <p class="mt10">
            <input type="button" class="add_shopping_cart" value="加入购物车" />
            <input type="button" class="understand" value="了解产品" />
            </p>
			
          </li>
          -->
          
        </ul>
      </div>   
    </div>
  </div>

</body>
</html>