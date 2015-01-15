<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "productSearch"]
	<title>商品列表</title>
	<meta name="author" content="SHOP++ Team" />
	<meta name="copyright" content="SHOP++" />
	[#if seo.keywords??]
		<meta name="keywords" content="[@seo.keywords?interpret /]" />
	[/#if]
	[#if seo.description??]
		<meta name="description" content="[@seo.description?interpret /]" />
	[/#if]
[/@seo]
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/seek.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $historyProduct = $("#historyProduct ul");
	var $productForm = $("#productForm");
	var $keyword = $("#keyword");
	var $orderType = $("#orderType");
	var $pageNumber = $("#pageNumber");
	var $pageSize = $("#pageSize");
	var $tableType = $("#tableType");
	var $listType = $("#listType");
	var $orderSelect = $("#orderSelect");
	var $previousPage = $("#previousPage");
	var $nextPage = $("#nextPage");
	var $size = $("#layout a.size");
	var $sort = $("#sort a");
	var $startPrice = $("#startPrice");
	var $endPrice = $("#endPrice");
	var $result = $("#result");
	var $productImage = $("#result img");
	var $tagIds = $("input[name='tagIds']");
	var $toPage = $("#toPage");
	
	var $isOutOfStock = $("input[name='isOutOfStock']");
	
	var layoutType = getCookie("layoutType");
	var ischecked= getCookie("isOutOfStock");
	if(ischecked ==1){
		$("#isOutOfStock")[0].checked=true;
	}else{
		$("#isOutOfStock")[0].checked=false;
	}
	if (layoutType == "listType") {
		$listType.addClass("currentList");
		$result.removeClass("table").addClass("list");
	} else {
		$tableType.addClass("currentTable");
		$result.removeClass("list").addClass("table");
	}
	
	$tableType.click(function() {
		var $this = $(this);
		if (!$this.hasClass("currentTable")) {
			$this.addClass("currentTable");
			$listType.removeClass("currentList");
			$result.removeClass("list").addClass("table");
			addCookie("layoutType", "tableType", {path: "${base}/"});
		}
	});
	
	$listType.click(function() {
		var $this = $(this);
		if (!$this.hasClass("currentList")) {
			$this.addClass("currentList");
			$tableType.removeClass("currentTable");
			$result.removeClass("table").addClass("list");
			addCookie("layoutType", "listType", {path: "${base}/"});
		}
	});
	
	
	$size.click(function() {
		var $this = $(this);
		$pageSize.val($this.attr("pageSize"));
		$pageNumber.val(1);
		$productForm.submit();
		return false;
	});
	
	$previousPage.click(function() {
		$pageNumber.val(${page.pageNumber - 1});
		$productForm.submit();
		return false;
	});
	
	$nextPage.click(function() {
		$pageNumber.val(${page.pageNumber + 1});
		$productForm.submit();
		return false;
	});
	
	//页面跳转
	$('#toPage').click(function() {
		var totalPages = $('#totalPages').val();
		var insertPage = $('#insertPage').val();
		if(insertPage>totalPages){
			$pageNumber.val(totalPages);
		}else if(insertPage<1){
			$pageNumber.val(1);
		}else{
			$pageNumber.val(insertPage);
		}
		$productForm.submit();
		return false;
	});
	
	$orderSelect.mouseover(function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.orderSelect");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height()}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	});
	
	$tagIds.click(function() {
		$pageNumber.val(1);
		$productForm.submit();
	});
	
	//排序
	$sort.click(function() {
		var $this = $(this);
		if ($this.hasClass("current")) {
			$orderType.val("");
		} else {
			$orderType.val($this.attr("orderType"));
		}
		$pageNumber.val(1);
		$productForm.submit();
		return false;
	});
	
	$startPrice.add($endPrice).focus(function() {
		$(this).siblings("button").show();
	});
	
	$startPrice.add($endPrice).keypress(function(event) {
		var $this = $(this);
		var key = event.keyCode?event.keyCode:event.which;
		if (key == 13 || (key >= 48 && key <= 57) || (key == 46 && $this.val().indexOf(".") == -1)) {
			return true;
		} else {
			return false;
		}
	});
	
	$productForm.submit(function() {
		if ($orderType.val() == "" || $orderType.val() == "topDesc") {
			$orderType.prop("disabled", true)
		}
		if ($pageNumber.val() == "" || $pageNumber.val() == "1") {
			$pageNumber.prop("disabled", true)
		}
		if ($pageSize.val() == "" || $pageSize.val() == "20") {
			$pageSize.prop("disabled", true)
		}
		if ($startPrice.val() == "" || !/^\d+(\.\d+)?$/.test($startPrice.val())) {
			$startPrice.prop("disabled", true)
		}
		if ($endPrice.val() == "" || !/^\d+(\.\d+)?$/.test($endPrice.val())) {
			$endPrice.prop("disabled", true)
		}
	});
	
	$productImage.lazyload({
		threshold: 100,
		effect: "fadeIn"
	});
	
	$.pageSkip = function(pageNumber) {
		$pageNumber.val(pageNumber);
		$productForm.submit();
		return false;
	}
	
	
});

//点击商品分类伸缩效果
	function zhankai(obj){
		if($(obj).next('div').css('display') == 'block'){
			$(obj).next('div').css('display','none');
			$(obj).attr('class','xfbt');
		}else{
			$(obj).next('div').css('display','block');
			$(obj).attr('class','xfbtt');			
		}
	}
	
	
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
	if (historyProductIds.length > 6) {
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
				$("#historyProduct").append('<li><a href="${base}' + product.path + '"><img src="' + thumbnail + '" \/></a><span>' + product.name + '<\/span><b>' + currency(product.price, true) + '<\/b><\/li>');
			});
		}
	});
	
	//底部搜索框
	function searchProduct(){
    		var keyword = $('#searchkeyword').val();
    		if(keyword==''){
				return false;
    		}else{
				window.location.href="/product/search.jhtml?keyword="+escape($('#searchkeyword').val());
    		} 
	}

	//仅显示有货
	function showGoods(){
		var radio=document.getElementById("isOutOfStock");
			if(radio.checked == true){
				alert("选中");
				addCookie("isOutOfStock",1,null);
				$isOutOfStock.val(false);
				alert("val="+$('#isOutOfStock').val());
			}else{
				alert("未选中");
				addCookie("isOutOfStock",2,null);
				$isOutOfStock.val(true);
				alert("val="+$('#isOutOfStock').val());
			}
		$('#pageNumber').val(1);
		$('#productForm').submit();
		return false;
	}
</script>


</head>
<body>
	[#include "/shop/include/header.ftl" /]
    <!--分类 热卖 面包屑 搜索内容区  -->
    <div class="boxzq">
        <div class="crumbs">
            <a href="${base}/"><span>${message("shop.path.mallhome")}</span></a>
            [#if productCategory??]
				[@product_category_parent_list productCategoryId = productCategory.id]
					[#list productCategories as productCategory]
		            <b>></b>
		            <a href="${base}${productCategory.path}"><span>${productCategory.name}</span></a>
            		[/#list]
				[/@product_category_parent_list]
	            	<b>></b>
	            	<a href="${base}${productCategory.path}"><span class="wdwz" >${productCategory.name}</span></a>
            [/#if]
        </div>
        
        <!--分类 热卖 搜索内容区 start --><!--分类 热卖 搜索内容区  start-->
        <div class="Findw">
            <!--左侧全部搜索分类 start--><!--左侧全部搜索分类 start-->
            <div class="qbssfl fa">
                <div class="zlmbt">
                    <a href="#">全部搜索分类</a>
                </div>
                <ul>
                    	[@product_category_root_list]
							[#list productCategories as category]
                    <li>
		                        <div class="xfbt" onclick="zhankai(this);"><A href="${base}${category.path}" >${category.name}</A></div>
		                        <div class="xfmx" >
			                        [@product_category_children_list productCategoryId = category.id count = 4]
										[#list productCategories as productCategory]
	                                          <span><a title="${productCategory.name}" href="${base}${productCategory.path}" >${productCategory.name}</a></span>
			                            [/#list]
									[/@product_category_children_list]
		                        </div>
                    </li>
                       		 [/#list]
						[/@product_category_root_list]
                </ul>
            </div>
            <!--左侧全部搜索分类 end--><!--左侧全部搜索分类 end-->
            
            
            <!--右侧搜索结果 start --><!--右侧搜索结果 start -->
        <form id="productForm" action="${base}${(productCategory.path)!"/product/list.jhtml"}" method="get">
            <input type="hidden" id="keyword" name="keyword" value="${productKeyword}" />
			<input type="hidden" id="orderType" name="orderType" value="${orderType}" />
			<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
			<input type="hidden" id="tagIds" name="tagIds" value="405" />
            <div class="serp  fb">
                <div class="ssbt">
                    <div>
                        <b>${message(productKeyword)}</b>
                        搜索到
                        <b>${message("shop.product.searchResultCount",page.total)}</b>
                        件相关商品
                    </div>
                </div>
                <!--搜索结果排序筛选选项 start--><!--搜索结果排序筛选选项 start-->
                <div  class="sxfsq" id="sort">
                    <ul>
                        <li style="width: 45px;">排序：</li>
                        <li class="sxx">
                            <a href="javascript:;"[#if orderType == "priceAsc"] class="currentAsc current" title="${message("shop.product.cancel")}"[#else] class="asc"[/#if] orderType="priceAsc">${message("shop.product.priceAsc")}</a>
                        </li>
                        <li>
                            <a href="javascript:;"[#if orderType == "salesDesc"] class="currentDesc current" title="${message("shop.product.cancel")}"[#else] class="desc"[/#if] orderType="salesDesc">${message("shop.product.salesDesc")}</a>
                        </li>
                        <li>
                            <a href="javascript:;"[#if orderType == "scoreDesc"] class="currentDesc current" title="${message("shop.product.cancel")}"[#else] class="desc"[/#if] orderType="scoreDesc">${message("shop.product.scoreDesc")}</a>
                        </li>
                        
                    </ul>
                    <div class="sfyh">
                    <!-- 
                        <label>${message("shop.product.tag")}:</label>
						[#assign tagList = tags /]
						[@tag_list type = "product"]
							[#list tags as tag]
								<label>
									<input type="checkbox" name="tagIds" value="${tag.id}"[#if tagList?seq_contains(tag)] checked="checked"[/#if] />${tag.name}
								</label>
							[/#list]
						[/@tag_list]
					-->
					
					
					
						<label><input type="checkbox" name="isOutOfStock" id="isOutOfStock" 
									value="${isOutOfStock}" onclick="showGoods()"/>
						&nbsp;&nbsp;仅显示有货
						</label>
						
						
						
						
						
                    </div>
                    <div class="dingwei fb">
                        <b>浙江 杭州</b>
                        <span></span>
                    </div>
                </div>
                <!--搜索结果排序筛选选项 end--><!--搜索结果排序筛选选项 end-->
                
                
                <!--商品搜索结果列表 start --><!--商品搜索结果列表 start -->
                <div class="result" style="height:1300px;">
                	[#if page.content?has_content]
                    <ul>
                    	[#list page.content?chunk(4) as row]
	                    	[#list row as product]
	                        <li[#if !row_has_next] class="last"[/#if]>
	                            <a href="${base}${product.pointPath}">
	                                <img src="${product.image}" alt="" />
	                            </a>
	                            <b title="${product.name}">${abbreviate(product.name, 60)}</b>
	                            <span>全网最低  正品保证</span>
	                            <div class="jiage">
	                               兑换积分：${product.lowPoints}
	                               
	                            	[#if product.lowPrice >0 ]
												<span style="display:inline-block;color:#666"> (加价:${product.lowPrice}元) </span>
									[/#if]
							        
									<b></b>
								</div>
	                            <div class="handle">
	                                <a href="${base}${product.pointPath}">查看详情</a>
	                                <a href="${base}${product.pointPath}" class="hyx">立即购买</a>
	                            </div>
	                        </li>
	                        [/#list]
						[/#list]
                    </ul>
                   [#else]
						${message("shop.product.noSearchResult", productKeyword)}
					[/#if] 
                </div>
                <!--商品搜索结果列表 end --><!--商品搜索结果列表 end -->
                
                
                <!--搜索结果分页 start --><!--搜索结果分页 start -->
                [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
					[#if totalPages > 0]
				<input type="hidden" id="totalPages" name="totalPages" value="${page.totalPages}" />
                <div class="fenye fb">
                	
                	[#if hasPrevious]
						<a class="syysd" href="[@pattern?replace("{pageNumber}", "${previousPageNumber}")?interpret /]">
							<b></b>上一页
						</a>
					[#else]
						<span class="syys">
							<b></b>上一页
						</span>
					[/#if]
                    
                    
                    [#list segment as segmentPageNumber]
						[#if segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1]
							<a class="shenglue">...</a>
						[/#if]
						[#if segmentPageNumber != pageNumber]
							<a href="[@pattern?replace("{pageNumber}", "${segmentPageNumber}")?interpret /]">${segmentPageNumber}</a>
						[#else]
							<a class="dyy">${segmentPageNumber}</a>
						[/#if]
						[#if !segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1]
							<a class="shenglue">...</a>
						[/#if]
					[/#list]
                    
                    
                   [#if hasNext]
						<a class="xyys" href="[@pattern?replace("{pageNumber}", "${nextPageNumber}")?interpret /]">
							<b></b>下一页
						</a>
					[#else]
						<span class="xyysd">
							<b></b>下一页
						</span>
					[/#if]
                    
                    <!--跳转-->
                    <div class="tiaozhuan">
                        向第
                        <input type="text"  name="" value="" id="insertPage"/>
                        页
                    	<input type="button" name="name" value="跳转 " class="tzts" id="toPage"/>
                    </div>
                </div>
                <!--搜索结果分页  end--><!--搜索结果分页  end-->
                
                [/#if]
                [/@pagination]
            </div>
            <!--搜索结果 end--><!--搜索结果 end-->
            </form>
            
            <!--商城热卖 start --><!--商城热卖 start -->
            <div class="hot fa">
                <div class="zlmbt">
                    <a href="#">${message("shop.product.hotProduct")}</a>
                </div>
                <ul>
              	  [@product_list count = 5 orderBy="monthSales desc"]
						[#list products as product]
                    <li[#if !product_has_next] class="last"[/#if]>
                        <a href="${base}${product.path}">
                            <img src="${product.image}" alt="Alternate Text" />
                        </a>
                        <span title="${product.name}">${abbreviate(product.name, 30)}</span>
                        <b>
							${currency(product.price, true, true)}
						</b>
                    </li>
					[/#list]
				[/@product_list]
                </ul>
            </div>
            <!--商城热卖 end--><!--商城热卖 end-->
            
            <!--您刚刚浏览过 start--><!--您刚刚浏览过 start-->
            <div class="record fa">
                <div class="zlmbt">
                    <a href="#">您刚刚浏览过</a>
                </div>
                <ul id="historyProduct">


                </ul>
            </div>
            <!--您刚刚浏览过 end--><!--您刚刚浏览过 end-->
        </div>
        <!--分类 热卖 搜索内容区   end--><!--分类 热卖 搜索内容区   end-->
        
        
        <div class="clear"></div>
        <!--搜索及意见反馈
        <div class="ssfk">
            <div class="ssqy">
                <input type="text" name="keyword" value="" id="searchkeyword" onkeydown="if(event.keyCode==13){searchProduct();};"/>
                <a href="#" onclick="searchProduct();"></a>
            </div>
            <div class="yjfk">
                在此反馈你的意见吧，
                <a href="#">立即反馈</a>
            </div>
            <div class="fktbb"></div>
        </div>
        搜索及意见反馈 end-->
    </div>
    <!--分类 热卖 搜索内容区  end-->
    <!--分类 热卖 面包屑 搜索内容区   end-->
    <div class="cenmax"></div>



    <div class="cenx"></div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>