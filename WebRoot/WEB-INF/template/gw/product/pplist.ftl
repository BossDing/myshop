<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "productSearch"]
	<title>${productCategory.name}_万家乐官方网站</title>
	<META name=Keywords content=${productCategory.name}>
	<META name=Description content=${productCategory.seoDescription}>
[/@seo]
<link href="${base}/resources/gw/css/all_css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />

    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/all_js_index.js"></SCRIPT>
    <script>
    $().ready(function() {
	var $productForm = $("#productForm");
	var $pageNumber = $("#pageNumber");
	var $attribute = $("#filter a.attribute");
	$attribute.click(function() {
			var $this = $(this);
			if ($this.hasClass("current")) { 
				$this.closest("td").find("input").prop("disabled", true);
			} else {
				$this.closest("td").find("input").prop("disabled", false).val($this.text());
			}
			$pageNumber.val(1);
			$productForm.submit();
			return false;
		});
	});
    </script>
</head>
<body>

	[#include "/gw/include/header.ftl" /]
	
	<div class="ban5"><p>[#assign rows = 0 /]
	[@product_category_parent_list productCategoryId = productCategory.id]
		[#list productCategories as productCategory]
		[#assign rows = rows+1 /]
		    [#if rows==2]
		     [@ad_position adname="官网 - ${productCategory.name}banner图"/]
		 [/#if]
		[/#list]
	[/@product_category_parent_list]
	[#if rows==1]
		[@ad_position adname="官网 - ${productCategory.name}banner图"/]
	[/#if]
	</p></div>
<div class="cpzx">
  <div class="cpzx_div">
    <div class="cp_dq">目前您在: <a href="${base}/">首页</a> 
    [#if productCategory??]
		[#assign rows = 0 /]
		[@product_category_parent_list productCategoryId = productCategory.id]
			[#list productCategories as productCategory]
			[#assign rows = rows+1 /]
			    &gt;
			    [#if rows==1]
			    	[#if productCategory.id=1079]
						<a href="http://www.chinamacro.cn/reshui/">${productCategory.name}</a>
					[#elseif productCategory.id=1089]
						<a href="http://www.chinamacro.cn/gongnuan/">${productCategory.name}</a>
					[#else]
						<a href="${base}/gw/product/list/${productCategory.id}.jhtml">${productCategory.name}</a>
					[/#if]
			    [#else]
						[#if productCategory.isShowProduct]
							<a href="${base}/gw/product/pplist/${productCategory.id}.jhtml">${productCategory.name}</a>
				    	[#else]
				    		[#if productCategory.id=1080]
								<a href="http://www.chinamacro.cn/reshui/ranqi/">${productCategory.name}</a>
							[#elseif productCategory.id=1081]
								<a href="http://www.chinamacro.cn/reshui/kongqi/">${productCategory.name}</a>
							[#elseif productCategory.id=1086]
								<a href="http://www.chinamacro.cn/chufang/chouyou/">${productCategory.name}</a>
							[#elseif productCategory.id=1087]
								<a href="http://www.chinamacro.cn/chufang/ranqizao/">${productCategory.name}</a>
							[#elseif productCategory.id=1090]
								<a href="http://www.chinamacro.cn/gongnuan/bigualu/">${productCategory.name}</a>
							[#else]
							 	<a href="${base}/gw/product/plist/${productCategory.id}.jhtml">${productCategory.name}</a>
							[/#if]
				    	[/#if]
			    [/#if]
			[/#list]
		[/@product_category_parent_list]
            	&gt;
		[#if rows==1]
			<h1 style="font-size:14px;font-weight:normal;display:inline">
				[#if productCategory.id=1080]
					<a href="http://www.chinamacro.cn/reshui/ranqi/">${productCategory.name}</a>
				[#elseif productCategory.id=1081]
					<a href="http://www.chinamacro.cn/reshui/kongqi/">${productCategory.name}</a>
				[#elseif productCategory.id=1086]
					<a href="http://www.chinamacro.cn/chufang/chouyou/">${productCategory.name}</a>
				[#elseif productCategory.id=1087]
					<a href="http://www.chinamacro.cn/chufang/ranqizao/">${productCategory.name}</a>
				[#elseif productCategory.id=1090]
					<a href="http://www.chinamacro.cn/gongnuan/bigualu/">${productCategory.name}</a>
				[#else]
				 	<a href="${base}/gw/product/plist/${productCategory.id}.jhtml">${productCategory.name}</a>
				[/#if]
			</h1>
		[#else]
			<h1 style="font-size:14px;font-weight:normal;display:inline">
				<a href="${base}/gw/product/pplist/${productCategory.id}.jhtml">${productCategory.name}</a>
			</h1>
		[/#if]
    [/#if]
    </div>
    <div class="cpzx_bt"><h1>万家乐${productCategory.name}</h1></div>
    <table width="1000" border="0" cellspacing="0" cellpadding="0">
      <tbody><tr>
        <td width="756" align="left" valign="top"><div class="cpzx_lb">
          <div class="nTab2"> 
            
            <div class="TabTitle2">
              <ul id="myTab0">
                <li class="active2" onclick="nTabs2(this,0);">如何选择${productCategory.name}</li>
              </ul>
              <div class="rhgm2"></div>
            </div>
            
            <div class="TabContent2">
              <div id="myTab0_Content0"> 
                <div class="box">
<form id="productForm" action="${base}/gw/product/pplist/${productCategory.id}.jhtml" method="get">
				<input type="hidden" id="brandId" name="brandId" value="${(brand.id)!}" />
				<input type="hidden" id="promotionId" name="promotionId" value="${(promotion.id)!}" />
				<input type="hidden" id="orderType" name="orderType" value="${orderType}" />
				<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
				<input type="hidden" id="pageSize" name="pageSize" value="500" />
<div class="box_1" id="filter" >

[#list productCategory.attributes as attribute]
	<div class="screeBox">
<table width="340" cellspacing="0" cellpadding="0" border="0">
    <tbody>
        <tr>
            <td width="80" valign="top" height="50" align="left"><strong>${abbreviate(attribute.name, 15)} :</strong></td>
            <td valign="top" height="50" align="left">
	    <input type="hidden" name="attribute_${attribute.id}"[#if attributeValue?keys?seq_contains(attribute)] value="${attributeValue.get(attribute)}"[#else] disabled="disabled"[/#if] />
		[#assign rows = 0 /]
		[#list attribute.options as option]
			[#if attributeValue.get(attribute) == option]
				[#assign rows = 1 /]
				<a href="javascript:;" class="attribute">不限</a>&nbsp;	
			[/#if]
		[/#list]
		[#if rows==0 ]
			<span>不限</span>&nbsp;
		[/#if]
		[#list attribute.options as option]
			[#assign isshowa = 0 /]
			[@attribute_list categoryId=productCategory.id attributeName=attribute.name ]
				[#if attributeValues??]
					[#assign isshowa = 1 /]
				[/#if]
			[/@attribute_list]
			[#if isshowa=1]
				[#if attributeValue.get(attribute) == option]
				<span>${option}</span>
				[#else]
				<a href="javascript:;" class="attribute" >${option}</a>&nbsp;	
				[/#if]
			[/#if]
			
		[/#list]
	    </td>
        </tr>
    </tbody>
</table>
</div>
[/#list]
</div>
</form>
</div>                </div>
            </div>
          </div>
          <div>
            <div class="box">
              <div class="box_1">
                <div class="lbpx">
                  <h3>
                    <form method="GET" class="sort" name="listform">
                      排序： <a href="http://www.chinamacro.cn/#"><img src="${base}/resources/gw/images/display_mode_list.gif" alt="列表显示" data-bd-imgshare-binded="1"></a> <a href="http://www.chinamacro.cn/#"><img src="${base}/resources/gw/images/display_mode_grid.gif" alt="图片显示" data-bd-imgshare-binded="1"></a> <a href="http://www.chinamacro.cn/#"><img src="${base}/resources/gw/images/display_mode_text.gif" alt="文字显示" data-bd-imgshare-binded="1"></a>&nbsp;&nbsp; <a href="http://www.chinamacro.cn/#"><img src="${base}/resources/gw/images/goods_id_default.gif" alt="按推荐排序" data-bd-imgshare-binded="1"></a> <a href="http://www.chinamacro.cn/#"><img src="${base}/resources/gw/images/shop_price_default.gif" alt="热门产品" data-bd-imgshare-binded="1"></a> <a href="http://www.chinamacro.cn/#"><img src="${base}/resources/gw/images/last_update_default.gif" alt="按更新时间排序" data-bd-imgshare-binded="1"></a>
                      <input type="hidden" name="category" value="">
                      <input type="hidden" name="display" value="" id="display">
                      <input type="hidden" name="brand" value="">
                      <input type="hidden" name="price_min" value="">
                      <input type="hidden" name="price_max" value="">
                      <input type="hidden" name="filter_attr" value="">
                      <input type="hidden" name="page" value="">
                      <input type="hidden" name="sort" value="">
                      <input type="hidden" name="order" value="">
                    </form>
                  </h3>
                </div>
                <style type="text/css">
/*菜单模块 */
.tab2{
 list-style:none;
 text-align:right;
margin-top:20px;
 }
.tab2 li{ 
display:inline; /* 【重点】让li横向排列。*/
font-size:12px;
 }
 .tab2 li.no a{ 
color:#000; 
font-weight:bold
}
</style>
                <script type="text/javascript">
function tab(a,b,c) 
{ 
for(i=1;i<=b;i++){ 
if(c==i)
{ 
// 判断选择模块
document.getElementById(a+"_mo_"+i).style.display = "block";  // 显示模块内容
document.getElementById(a+"_to_"+i).className = "no";   // 改变菜单为选中样式
} 
else{ 
// 没有选择的模块
document.getElementById(a+"_mo_"+i).style.display = "none"; // 隐藏没有选择的模块
document.getElementById(a+"_to_"+i).className = "q";  // 清空没有选择的菜单样式
} 
} 
}
</script>
                <div class="page">
                  <div class="tab_mo">
                    <div id="tab_mo_1">
		    [#if page.content?has_content]
			<ul class="tab_mo_li">
			[#list page.content?chunk(4) as row]
				[#list row as p]
				[#assign pro = 0 /]
				[#assign pr = 0 /]
				
					[#list productCategory.attributes as attribute]
						[#list attribute.options as option]
							[#if attributeValue.get(attribute) == option]
								[#assign pr = pr+1 /]
								[#list p.parameterValue?keys as itemKey]
								 [#if attribute.name==itemKey.name &&  p.parameterValue.get(itemKey)?index_of(option?trim)>-1]
									[#assign pro = pro+1 /]
									[#break]
								  [/#if]
								[/#list]
							[/#if]
							
						[/#list]
						
					[/#list]
					
				
				[#if pr==pro]
					    <li>
			       <a href="${base}${p.gwPath}"><img  height="220" src="${base}${p.image}" alt="${p.name}" data-bd-imgshare-binded="1"></a>
			       <p class="lb_title">${p.name}</p>
			       <p>${p.seoTitle}</p>
			       <p>${p.seoKeywords}</p>
			       <p>${p.seoDescription}</p>
			       </li>
			       [/#if]
			[/#list]
			[/#list]
			
			</ul>
		    
		[/#if]
		</div>
</div>
                </div>
              </div>
              <div class="blank5"></div>
              <script type="Text/Javascript" language="JavaScript">
<!--
function selectPage(sel)
{
  sel.form.submit();
}
//-->
</script> 
            
</div>
</div></div>
</td>
<td width="244" align="left" valign="top">
	[#include "/gw/product/pright.ftl" /]
</td>
      </tr>
    </tbody></table>
  </div>
</div>

	[#include "/gw/include/footer.ftl" /]
</body>
</html>