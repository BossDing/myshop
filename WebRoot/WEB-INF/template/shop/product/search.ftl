<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "productSearch"]
	<title>商品搜索页</title>
	<meta name="author" content="SHOP++ Team" />
	<meta name="copyright" content="SHOP++" />
	[#if seo.keywords??]
		<meta name="keywords" content="[@seo.keywords?interpret /]" />
	[/#if]
	[#if seo.description??]
		<meta name="description" content="[@seo.description?interpret /]" />
	[/#if]
[/@seo]
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/soso.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>


<script type="text/javascript">
$().ready(function() {

	var $historyProduct = $("#historyProduct");
	var $clearHistoryProduct = $("#clearHistoryProduct");
	var $keyword = $("#keyword");
    var $previousPage = $("#previousPage");
	var $nextPage = $("#nextPage");
	var $sort =  $("#sort input");
	var $pageNumber = $("#pageNumber");
	var $productForm = $("#productForm");
	var $orderType = $("#orderType");
	var $topLast = $("#topLast");
	var $topNext = $("#topNext");
	var $searchAgainForm = $("#searchAgainForm");
	var $keyword1 = $("#keyword1");
	
	/**排序样式*/
	var ot =$orderType.val();
	if(null!=ot&&""!=ot){
	   for(var i=0;i<$sort.length;i++){
	      var obj =$sort[i];
	      if(ot==$(obj).attr("orderType")){
	          $(obj).addClass("sequence_but");
	         if(ot.lastIndexOf("Desc")>0){
	         $(obj).css("background-image","url('${base}/resources/shop/images/soso-a.png')");
	         }else{
	         $(obj).css("background-image","url('${base}/resources/shop/images/soso-b.png')");
	         }
	      }
	   }
	}
	/**排序**/
	$sort.click(function() {
		var $this = $(this);
		var orderType = $this.attr("orderType");
		var newType = "";
		var descIndex = orderType.lastIndexOf("Desc");
		var ascIndex = orderType.lastIndexOf("Asc");
		if(descIndex>0){
			newType = orderType.substring(0,descIndex)+"Asc";
		}
		if(ascIndex>0){
			newType = orderType.substring(0,ascIndex)+"Desc";
		}
		if(newType!="dateAsc"){
		  $orderType.val(newType);
		}else{
		  $orderType.val(null);
		}
		$pageNumber.val(1);
		$productForm.submit();
		return false;
	});
	
	/**上一页 下一页 选中页**/
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
	
	$.pageSkip = function(pageNumber) {
		$pageNumber.val(pageNumber);
		$productForm.submit();
		return false;
	}
	
	$topNext.click(function(){
	   if(${page.pageNumber}==${page.totalPages}) return false;
	   $pageNumber.val(${page.pageNumber + 1});
	   $productForm.submit();
	   return false;
	});
	
	$topLast.click(function(){
	   if(${page.pageNumber}==1) return false;
	   $pageNumber.val(${page.pageNumber - 1});
	   $productForm.submit();
	   return false;
	});
	
	
	/**表单提交*/
	$productForm.submit(function() {
		if ($orderType.val() == "" || $orderType.val() == "topDesc") {
			$orderType.prop("disabled", true)
		}
		if ($pageNumber.val() == "" || $pageNumber.val() == "1") {
			$pageNumber.prop("disabled", true)
		}
//		if ($pageSize.val() == "" || $pageSize.val() == "20") {
//			$pageSize.prop("disabled", true)
//		}
//		if ($startPrice.val() == "" || !/^\d+(\.\d+)?$/.test($startPrice.val())) {
//			$startPrice.prop("disabled", true)
//		}
//		if ($endPrice.val() == "" || !/^\d+(\.\d+)?$/.test($endPrice.val())) {
//			$endPrice.prop("disabled", true)
//		}
	});
	
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
	if (historyProductIds.length > 4) {
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
			if(data.length>0){
				$.each(data, function (index, product) {
				    var sum = 0;
				    if(product.synopsis!=null&&product.synopsis!="undefined") sum = product.synopsis;
					var thumbnail = product.thumbnail != null ? product.thumbnail : "${setting.defaultThumbnailProductImage}";
					var str = "<div class='two-left-b1'><div class='left-b11'>";
					   str += "<a href='${base}"+ product.path + "'><img style='width:104px;height:104px;' src='" +thumbnail+"'\/><\/a></\div>";
					   str += "<div class='left-b12'><a href='"+product.path+"'>"+product.name+ "<\/br><\/br><\/br><span class='span1'>￥<\/span>";
					   str += "<span class='span2'> "+ product.price+ "<\/span>评论<span class='span1'>"+sum+"<\/span>条<\/a><\/div><\/div>";
					$("#historyProduct").append(str);  
				});
				
				
				
			//	<li class="yema-1">1/2</li>
             //   <li class="yema-2"><a href="#"><<上一页</a></li>
            //    <li class="yema-1"><a href="#">1</a></li>
            //    <li class="yema-1"><a href="#">2</a></li>
            //    <li class="yema-3"><a href="#">下一页>></a></li>
				
				
				
			}else{
				$("#clearHistoryProduct").text("${message("shop.product.noHistoryProduct")}");
			}
		}
	});
	
	// 清空浏览记录
	 $("#clearHistoryProduct").click(function() {
		$historyProduct.empty();
		$(this).text("${message("shop.product.noHistoryProduct")}");
		removeCookie("historyProduct", {path: "${base}/"});
	});
	
	 $("#classify_act").mouseover(function(){
            $("#classify").slideDown();
         }); 
		 
		 $("#classify").mouseleave(function(){
            $(this).slideUp();
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp();
         }); 
         
     /**提交搜索前判断是否为空*/    
	$searchAgainForm.submit(function(){
	    if ($.trim($keyword1.val()) == "") {
			return false;
		}
	});
	
	/**重新搜索提交*/
	$("#subAgain").click(function(){
	   $searchAgainForm.submit();
	});
	
	
});

</script>
</head>
<body>
	[#include "/shop/include/header.ftl" /]
	<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span>首页</span>><span>站内搜索</span></div>
</div>
<!--xx-->
<div class="content">  
<form id="productForm" action="${base}/product/search.jhtml" method="get">
            <input type="hidden" id="keyword" name="keyword" value="${productKeyword}" />
			<input type="hidden" id="orderType" name="orderType" value="${orderType}" />
			<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />  
  <div class="like_lit">
   <div class="sequence" id="sort">
       <span >排序方式</span>
       <input  type="button" value="销量"  class="sequence_but2"  [#if orderType == "salesAsc"] orderType="salesAsc" [#else] orderType="salesDesc" [/#if]  />
       <input  type="button" value="价格"  class="sequence_but2" [#if orderType == "priceAsc"] orderType="priceAsc" [#else] orderType="priceDesc" [/#if] />
       <input  type="button" value="评价"  class="sequence_but2" [#if orderType == "scoreAsc"] orderType="scoreAsc" [#else] orderType="scoreDesc" [/#if] />
    </div>
   <div class="page">
       总共找到<i>${page.total}</i>个商品
       &nbsp;&nbsp;
       <span>${page.pageNumber}</span>/<span>${page.totalPages}</span>
       &nbsp;&nbsp;
        <input class="last" type="button" value="&#8249;" id="topLast" />
         <input class="next" type="button" id="topNext" value="下一页&nbsp;&#8250;" />
    </div>
  </div>
  <div class="mybe_like">
     <ul>
      [#if page.content?has_content]
          [#list page.content?chunk(10) as row]
              [#list row as product]
                 <li>
                    <div class="like_pic"><a href="${base}${product.path}"><img src="${base}${product.image}"/></a></div>
                    <div class="like_name">${product.name}</div>
                    <div class="like_tt">
                        <div class="like_price">￥${product.price}</div>
                        <div class="like_com">评论<i>${(product.reviews?size)!0}</i>条</div>
                    </div>
                    <div class="like_left">
	                	[#if product.store??]
                    		<a href="${product.store.indexUrl}">
                    			${product.store.name}
                    		</a>
                    	[#else]
                    		<a href="javascript:void(0)">
                    		商城自营
                    		</a>
	                	[/#if]
                    </div>
               </li>
              [/#list]
          [/#list]
      [/#if]                  
       </ul>
    </div>
    <div class="yema-big">
      
                   <!--搜索结果分页 start --><!--搜索结果分页 start -->
                [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
					[#if totalPages > 0]
                    <div class="yema" style="width:350px;">
                    <ul>
                    <li class="yema-1">${page.pageNumber}/${page.totalPages}</li>
                	[#if hasPrevious]
                	<li class="yema-2" id="previousPage"><a href="[@pattern?replace("{pageNumber}", "${previousPageNumber}")?interpret /]"><<上一页</a></li>
					[#else]
						<li class="yema-2"><<上一页</li>
					[/#if]
                    [#list segment as segmentPageNumber]
						[#if segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1]
							<a class="shenglue">...</a>
						[/#if]
						[#if segmentPageNumber != pageNumber]
						 <li class="yema-1"><a href="[@pattern?replace("{pageNumber}", "${segmentPageNumber}")?interpret /]">${segmentPageNumber}</a></li>
						[#else]
							<li class="yema-1"><a>${segmentPageNumber}</a></li>
						[/#if]
						[#if !segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1]
							<li><a class="shenglue">...</a></li>
						[/#if]
					[/#list]
                    
                    
                   [#if hasNext]   
                        <li class="yema-3" id="nextPage"><a href="[@pattern?replace("{pageNumber}", "${nextPageNumber}")?interpret /]">下一页>></a></li>
					[#else]
						<li class="yema-3" >下一页>></li>
					[/#if]
					</ul>
                </div>
                [/#if]
                [/@pagination]
                <!--搜索结果分页  end--><!--搜索结果分页  end-->
    </div>
   </form> 
    
    
    <div class="new-big">
      <form id="searchAgainForm" action="${base}/product/search.jhtml" method="get">
      <div class="new"><input id="keyword1" type="text" class="soso_text2"  name="keyword" maxLength="50"/>
      <input type="button" class="soso_but" id="subAgain"/><span class="new1" >重新搜索</span></div>
      </form>
    </div>
    <div class="two">
      <div class="two-left">
        <div class="two-left-a">最近浏览</div>
        <div class="two-left-b" id="historyProduct">
          <!-- <div class="two-left-b1">
          <input type="hidden" id="clearHistoryProduct">
           <div class="left-b11"><a href="#"><img src="../images/two-1.jpg"></a></div>
            <div class="left-b12"><a href="#">万家乐 燃气热水器 智能燃气浴热水JSQ16-QH3  新品智能控温 恒温机 
             </br></br></br><span class="span1">￥</span><span class="span2">1680</span>评论<span class="span1">516</span>条</a></div>
          </div> -->
        </div>
        <div class="yema2" >
            <ul id="historyPage">
                <!--浏览历史页码-->
            </ul>
        </div>
      </div>
      <div class="two-right">
        <div class="two-right-a">猜你喜欢</div>
        <div class="mybe_like2">
           [@product_list tagIds =2  count = 6]
           <ul>
		   [#list products as product]
		     <li>
		         <div class="like_pic1"><a href="${base}${product.path}"><img src="${base}${product.image}"/></a></div>
                <div class="like_name1">${product.name}</div>
                <div class="like_tt1">
                    <div class="like_price1">￥${product.price}</div>
                    <div class="like_com1">评论<i>${(product.reviews?size)!0}</i>条</div>
                 </div>
               </li>
		    [/#list]
		    </ul>
           [/@product_list]
            
        </div>
        <!--
        <div class="yema-big3">
          <div class="yema3">
              <ul>
                  <li class="yema-1">1/1</li>
                  <li class="yema-2"><a href=""><<上一页</a></li>
                  <li class="yema-1"><a href="">1</a></li>
                  <li class="yema-3"><a href="">下一页>></a></li>
              </ul>
          </div>
        </div>
        -->
      </div>
    </div>
  </div>
      
</div>

<!--个人资料完-->
    [#include "/shop/include/footer.ftl" /]
</body>
</html>