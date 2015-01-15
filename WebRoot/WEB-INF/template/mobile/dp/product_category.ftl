<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>${store.name} - 分类</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
		<link href="${base}/resources/mobile/css/quanbushangpin.css" rel="stylesheet" type="text/css" />
		<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
		<script type="text/javascript">
			function toggle(text){
				$("#"+text+" ul").children("li").each(function(index){
					 if(0!=index){
					   $(this).toggle();
					 }else{
						 var imgsrc="${base}/resources/mobile/images/shou_36.png";
						 var imgsrc2="${base}/resources/mobile/images/shouhuo_50.png";
						 if(imgsrc == $("#left-one"+text).attr("src"))  $("#left-one"+text).attr("src",imgsrc2); else  $("#left-one"+text).attr("src",imgsrc);
					 }
				});
			}
		</script>
	</head>
	<body>
        <!--头部导航start-->
       [#include "/mobile/include/header.ftl"]
        <!--头部导航end-->
		<div class="quanbu-big">
	         [#list productCategories as rootProductCategory]
             [#if rootProductCategory.grade == 0]
		        <div class="left-[#if rootProductCategory_index==0]1[/#if]" id="_${rootProductCategory.id}">
		            <ul>
		            	<li class="left-top">
		            		<span><a href="${base}/mobile/store/dp_category_list.jhtml?productCategoryId=${rootProductCategory.id}&storeId=${store.id}">${rootProductCategory.name}</a></span>
		            		<a href="javascript:toggle('_${rootProductCategory.id}');"><img id="left-one_${rootProductCategory.id}" src="${base}/resources/mobile/images/shouhuo_50.png"/></a>
		            	</li>
		            	[#list rootProductCategory.children as productCategory]
		            		[#if productCategory.store == null]
		                		<li  style="display:none;"><a href="${base}/mobile/store/dp_category_list.jhtml?productCategoryId=${productCategory.id}&storeId=${store.id}"><span>${productCategory.name}</span></a></li>
		                	[/#if]
                            [#if productCategory.store?? && productCategory.store.id == store.id]
                            	<li  style="display:none;"><a href="${base}/mobile/store/dp_category_list.jhtml?productCategoryId=${productCategory.id}&storeId=${store.id}"><span>${productCategory.name}</span></a></li>
                            [/#if]
		                [/#list]
		            </ul>
		        </div>
               [/#if]
			[/#list]
	    </div>
	</body>
</html>