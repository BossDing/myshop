<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "articleList"]
	<title>[#if articleCategory.seoTitle??]${articleCategory.seoTitle}[#elseif seo.title??][@seo.title?interpret /][/#if][#if systemShowPowered] - Powered By SHOP++[/#if]</title>
	<meta name="author" content="SHOP++ Team" />
	<meta name="copyright" content="SHOP++" />
	[#if articleCategory.seoKeywords??]
		<meta name="keywords" content="${articleCategory.seoKeywords}" />
	[#elseif seo.keywords??]
		<meta name="keywords" content="[@seo.keywords?interpret /]" />
	[/#if]
	[#if articleCategory.seoDescription??]
		<meta name="description" content="${articleCategory.seoDescription}" />
	[#elseif seo.description??]
		<meta name="description" content="[@seo.description?interpret /]" />
	[/#if]
[/@seo]
<link href="${base}/resources/gw/css/all_css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/gw/css/style.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/common.js"></SCRIPT>
    <link href="${base}/resources/gw/css/css.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/jquery-1.4.min.js"></SCRIPT>
    <SCRIPT type=text/javascript>var $a =jQuery.noConflict();</SCRIPT>
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/nav.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/resources/gw/js/jquery-1.7.1.min.js"></SCRIPT>

<SCRIPT type=text/javascript src="${base}/resources/gw/js/lanrenzhijia.js"></SCRIPT>
<LINK rel=stylesheet type=text/css href="${base}/resources/gw/js/ban2.css"></HEAD>
<script type="text/javascript">
$().ready(function() {

	var $pageNumber = $("#pageNumber");
	var $productForm = $("#productForm");
	 var $previousPage = $("#previousPage");
	var $nextPage = $("#nextPage");
	
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
	
		/**表单提交*/
	$productForm.submit(function() {
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
	

});
</script>
</head>
<body>
	[#include "/gw/include/header.ftl" /]

	<DIV class=ban4>
		<DIV id=bn>
			<SPAN class=tu>
			[@ad_position adname="官网 - 搜索页面banner图"/]
			</SPAN>
		</DIV>
	</DIV>

<DIV class=cpzx>
	<DIV class="cpzx_div zlm_bg2">
		<DIV class=gywjl_dq>目前您在：<A href="/gw/index.jhtml">首页</A> &gt; 
			<H1 style="FONT-SIZE: 14px; FONT-WEIGHT: normal; DISPLAY: inline">搜索</H1>
		</DIV>
		<DIV class=jrwjl_z>
			[@article_list useCache=false name="关于万家乐 - 左菜单栏" articleCategoryId=1 tagIds=1 ]
			[#list articles as a]
			${a.content}
			[/#list ]
			[/@article_list]
		</DIV>
		<DIV class=gywjl_y>
			<DIV class=gy_nrbt>搜索</DIV>
			<DIV class=gy_nr>


<div class="AreaR">
    <div class="box">
     	<div class="box_1">
      		<h3>
                 <span>搜索结果</span>
                 <form action="${base}/gw/product/search.jhtml" method="get" class="sort" name="listform" id="productForm">
                 	 <input type="hidden" id="keyword" name="keyword" value="${productKeyword}" />
	                 <input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
					 <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
			          显示方式：
			          <a href="javascript:;" onClick="javascript:display_mode('list')"><img src="${base}/resources/gw/images/display_mode_list.gif" alt="列表显示"></a>
			          <a href="javascript:;" onClick="javascript:display_mode('grid')"><img src="${base}/resources/gw/images/display_mode_grid_act.gif" alt="图片显示"></a>
			          <a href="javascript:;" onClick="javascript:display_mode('text')"><img src="${base}/resources/gw/images/display_mode_text.gif" alt="文字显示"></a>&nbsp;&nbsp;
			              <select name="sort">
				              <option value="goods_id">按推荐排序</option>
				              <option value="shop_price" selected>按热门度排序</option>
				              <option value="last_update">按更新时间排序</option>
						  </select>
			              <select name="order">
			              		<option value="DESC" selected>倒序</option>
			              		<option value="ASC">正序</option>
			              </select>
				            <input type="image" name="imageField" src="${base}/resources/gw/images/bnt_go.gif" alt="go"/>
				            <input type="hidden" name="page" value="1" />
				            <input type="hidden" name="display" value="grid" id="display" />
			                <input type="hidden" name="keywords" value="压力锅" />
			                <input type="hidden" name="category" value="0" />
			                <input type="hidden" name="brand" value="0" />
			                <input type="hidden" name="min_price" value="0" />
			                <input type="hidden" name="max_price" value="0" />
			                <input type="hidden" name="action" value="" />
			                <input type="hidden" name="intro" value="" />
			                <input type="hidden" name="goods_type" value="0" />
			                <input type="hidden" name="sc_ds" value="0" />
			                <input type="hidden" name="outstock" value="0" />
                      </form>
         		</h3>
        
		          <form action="compare.php" method="post" name="compareForm" id="compareForm" onsubmit="return compareGoods(this);">
		               <div class="centerPadd">
		                  <div class="clearfix goodsBox" style="border:none;">
								[#if page.content?has_content]
									[#list page.content?chunk(3) as row]
										[#list row as product]
											<div class="cplb">
											    <div class="cplb_tu"><a href="${base}${product.gwPath}"><img src="${base}${product.image}" alt="${product.name}" class="goodsimg"  width="220px" height="220px"/></a></div>
											    <div class="cplb_mc"><a href="${base}${product.gwPath}" title="${product.name}">${product.name}</a></div>
											</div>
										[/#list]
									[/#list]
								[/#if]
			              </div>
		               </div>
		          </form>
		          
		          <script type="text/javascript">
		                var button_compare = "比较选定产品";
		                var exist = "您已经选择了%s";
		                var count_limit = "最多只能选择4个产品进行对比";
		                var goods_type_different = "\"%s\"和已选择产品类型不同无法进行对比";
		        		var button_compare = '';
		                var exist = "您已经选择了%s";
		                var count_limit = "最多只能选择4个产品进行对比";
		                var goods_type_different = "\"%s\"和已选择产品类型不同无法进行对比";
		        		var compare_no_goods = "您没有选定任何需要比较的产品或者比较的产品数少于 2 个。";
				        window.onload = function(){
				          Compare.init();
				          fixpng();
				        }
		        		var btn_buy = "购买";
		        		var is_cancel = "取消";
		       			var select_spe = "请选择产品属性";
		        </script>
           </div>
      </div>
      <div class="blank"></div>
      
		<form name="selectPageForm" action="/search.php" method="get">
		
			<!--
			<div id="pager" class="pagebar">
  					  <span class="f_l f6" style="margin-right:10px;">总计 <b>10</b>  个记录</span>
                      <span class="page_now">1</span>
                      <a href="search.php?keywords=%D1%B9%C1%A6%B9%F8&category=0&brand=0&sort=shop_price&order=DESC&min_price=0&max_price=0&action=&intro=&goods_type=0&sc_ds=0&outstock=0&page=2">[2]</a>
					  <a class="next" href="search.php?keywords=%D1%B9%C1%A6%B9%F8&category=0&brand=0&sort=shop_price&order=DESC&min_price=0&max_price=0&action=&intro=&goods_type=0&sc_ds=0&outstock=0&page=2">下一页</a>
  			</div>
			-->
			 <div id="pager" class="pagebar">
			 	[@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
			 		[#if totalPages > 0]
					  		<span class="f_l f6" style="margin-right:10px;">总计 <b>${page.total}</b>  个记录</span>
					  		
					  		[#if !isFirst]
								<a class="firstPage" href="[@pattern?replace("{pageNumber}", "${firstPageNumber}")?interpret /]">首页</a>
							[/#if]
					  		
					  		[#if hasPrevious]
			 				<a class="pre" href="[@pattern?replace("{pageNumber}", "${previousPageNumber}")?interpret /]" id="previousPage">上一页</a>
			 				[/#if]
			 				
			 				[#list segment as segmentPageNumber]
				 				[#if segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1]
									<a class="shenglue">...</a>
								[/#if]
				 				[#if segmentPageNumber != pageNumber]
						        <a href="[@pattern?replace("{pageNumber}", "${segmentPageNumber}")?interpret /]">[${segmentPageNumber}]</a>
						        [#else]
						        <span class="page_now">${segmentPageNumber}</span>
						        [/#if]
							    [#if !segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1]
									<a class="shenglue">...</a>
								[/#if]
						    [/#list]
					        
					        [#if hasNext]
					  		<a class="next" href="[@pattern?replace("{pageNumber}", "${nextPageNumber}")?interpret /]" id="nextPage">下一页</a>
					  		[/#if]
					  		
					  		[#if !isLast]
								<a class="lastPage" href="[@pattern?replace("{pageNumber}", "${lastPageNumber}")?interpret /]">尾页</a>
							[/#if]
					  		
					[/#if]
                [/@pagination]	  		
			 </div>
			 <!--
			 <div class="yema-big">
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
						<li class="yema-3" ><a>下一页>></a></li>
					[/#if]
					</ul>
                </div>
                [/#if]
                [/@pagination]
    </div>-->
		</form>


		<script type="Text/Javascript" language="JavaScript">
		<!--
				function selectPage(sel)
				{
				  sel.form.submit();
				}
		//-->
		</script>
   
   
   
  </div>

</DIV></DIV></DIV></DIV>
	
	[#include "/gw/include/footer.ftl" /]
</body>
</html>