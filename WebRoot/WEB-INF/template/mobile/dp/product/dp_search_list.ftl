<!DOCTYPE html>
<html>
	<head>
		<title>${store.name} - 搜索  </title>
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0"/>
		<meta name="apple-mobile-web-app-capable" content="yes"/>
		<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
		<meta name="format-detection" content="telephone=no"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="${base}/resources/mobile/css/productList.css"/>
		<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
	</head>
	<body>
        
        <!--头部导航start-->
       [#include "/mobile/include/header.ftl"]
        <!--头部导航end-->
        
         [#if page.content?has_content]
		<div class="dingdan-big">
		    <div class="shouhuo">
		    	[#list page.content?chunk(2) as row]
		           <div class="one-hang">
		           	[#list row as product]
		           		[#if product_index % 2 == 0]
		           		<div  class="one-hang-left">
		                	<ul>
		                    	<li class="photo">
                                    <a href="${base}/mobile${product.path}">
	                              		<img src="[#if product.productImages[0].source??]${product.productImages[0].source}[#else]${setting.defaultThumbnailProductImage}[/#if]" width="100%">
                                    </a>
                                </li>
		                        <li class="Name">${abbreviate(product.name, 35, " ...")}</li>
		                        <li class="jia">
                                    <span>${currency(product.price, true)}
									[#if setting.isShowMarketPrice]
										<del>${currency(product.marketPrice, true)}</del>
									[/#if]
                                    </span>
                                </li>
                                 <li class="Name">
                                    [#if product.store != null]
                                    	${product.store.name}
                                   	[#else]
                                    	商城自营
                                    [/#if]
                                </li>
		                    </ul>
		                </div>
		                [/#if]
		                [#if product_index % 2 == 1]
		                <div  class="one-hang-right">
		                	<ul>
		                    	<li class="photo"><a href="${base}/mobile${product.path}">
	                              <img src="[#if product.productImages[0].source??]${product.productImages[0].source}[#else]${setting.defaultThumbnailProductImage}[/#if]" width="100%"></a></li>
		                        <li class="Name">${abbreviate(product.name, 35, " ...")}</li>
		                        <li class="jia">
                                    <span>${currency(product.price, true)}
									[#if setting.isShowMarketPrice]
										<del>${currency(product.marketPrice, true)}</del>
									[/#if]
									</span>
                                </li>
                                <li class="Name">
                                    [#if product.store != null]
                                    	${product.store.name}
                                   	[#else]
                                    	商城自营
                                    [/#if]
                                </li>
		                    </ul>
		                </div>
		                [/#if]
		               [/#list]
		           </div>
		         [/#list]
		    </div>
		</div>
		[#else]
			<p>
			<center>
				${message("shop.product.noSearchResult", productKeyword)}
			</center>
			</p>
		[/#if]
	</body>
</html>
