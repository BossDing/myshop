<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.promotion.add")}</title>
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<style type="text/css">
.memberRank label, .productCategory label, .brand label, .coupon label {
	min-width: 120px;
	_width: 120px;
	display: block;
	float: left;
	padding-right: 4px;
	_white-space: nowrap;    
}    
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $productTable = $("#productTable");
	var $productSelect = $("#productSelect");
	var $deleteProduct = $("a.deleteProduct");
	var $productTitle = $("#productTitle");
    var productIds = new Array();
   
	var productImageIndex = 0;
	var $productImageTable = $("#productImageTable");
	var $addProductImage = $("#addProductImage");
	var $deleteProductImage = $("a.deleteProductImage");
	var $areaId = $("#areaId");
	
	 

	var $browserButton = $("#browserButton");
	var $browserButton2 = $("#browserButton2");
	var $browserButton3 = $("#browserButton3");

	

	
	$browserButton.browser();
	$browserButton2.browser();
	$browserButton3.browser();

    // 商品选择
	$productSelect.autocomplete("product_select.jhtml", {
		dataType: "json",
		max: 20,
		width: 600,
		scrollHeight: 300,
		parse: function(data) {
			return $.map(data, function(item) {
				return {
					data: item,
					value: item.fullName
				}
			});
		},
		formatItem: function(item) {
			if ($.inArray(item.id, productIds) < 0) {
				return '<span title="' + item.fullName + '">' + item.fullName.substring(0, 50) + '<\/span>';
			} else {   
				return false;
			}   
		}    
	}).result(function(event, item) {
		[@compress single_line = true]
			var trHtml = 
			'<tr class="productTr">
				<th>
					<input type="hidden" name="productIds" value="' + item.id + '" \/>
					&nbsp;
				<\/th>
				<td>
					<span title="' + item.fullName + '">' + item.fullName.substring(0, 50) + '<\/span>
				<\/td>
				<td>
					<a href="${base}' + item.path + '" target="_blank">[${message("admin.common.view")}]<\/a>
					<a href="javascript:;" class="deleteProduct">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$productTitle.show();
		$productTable.append(trHtml);
		productIds.push(item.id);
	});
	
	// 删除商品
	$deleteProduct.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				var id = parseInt($this.closest("tr").find("input:hidden").val());
				productIds = $.grep(productIds, function(n, i) {
					return n != id;
				});
				$this.closest("tr").remove();
				if ($productTable.find("tr.productTr").size() <= 0) {
					$productTitle.hide();
				}
			}
		});
	});	

	
	[@flash_message /]
	
	//地区选择
	$areaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	   
    // 增加展馆图片
	$addProductImage.click(function() {
		[@compress single_line = true]
			var trHtml = 
			'<tr>
				<td>
					<input type="file" name="productImages[' + productImageIndex + '].file" class="productImageFile" \/>
				<\/td>
				<td>
					<input type="text" name="productImages[' + productImageIndex + '].title" class="text" maxlength="200" \/>
				<\/td>
				<td>
					<input type="text" name="productImages[' + productImageIndex + '].order" class="text productImageOrder" maxlength="9" style="width: 50px;" \/>
				<\/td>
				<td>
					<a href="javascript:;" class="deleteProductImage">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$productImageTable.append(trHtml);
		productImageIndex ++;
	});
	
	// 删除展馆图片
	$deleteProductImage.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				$this.closest("tr").remove();
			}
		});
	});	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			areaId: "required",
			mapx: { 
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},mapy: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			}		  
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 添加体验店
	</div>
	<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.product.base")}" />
			</li>
			<li>
				<input type="button" value="体验店图片" />  
			</li>
		</ul>
		<div class="tabContent">
		<table class="input">
			<tr>
					<th>
						<span class="requiredField">*</span>体验店名称:
					</th>
					<td colspan="2">
						<input type="text" name="name" class="text" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField"></span>营业时间:
					</th>
					<td colspan="2">
						<input type="text" name="opentime" class="text" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField"></span>服务热线:
					</th>
					<td colspan="2">
						<input type="text" name="phone" class="text" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>体验店地区
					</th>
				<td>
					<span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId"/>
					</span>
				</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField"></span>详细地址
					</th>
					<td colspan="2">
						<input type="text" name="address" class="text" maxlength="200" />
					</td>
				</tr>     
				<tr>
					<th>  
						地图维度:
					</th>
					<td colspan="2">
						<input type="text"  name="mapx" class="text"  maxlength="16" />
					</td>
				</tr>
				<tr>
					<th>
						地图经度:
					</th>
					<td colspan="2">
						<input type="text"  name="mapy" class="text"  maxlength="16" />
					</td>
				</tr>
				<tr>
				<th>
					体验店图片:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="image" class="text" value="${experience.image}" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
						[#if experience.image??]
							<a href="${experience.image}" target="_blank">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
			<tr>
				<th>
					促销活动链接一
				</th>
				<td>
					<input type="text" name="promotionlinkfrist" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					促销活动图片一:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="promotionimagefrist" class="text" value="${experience.promotionimagefrist}" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton2" class="button" value="${message("admin.browser.select")}" />
						[#if experience.promotionimagefrist??]
							<a href="${experience.promotionimagefrist}" target="_blank">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
			<tr>
				<th>
					促销活动链接二
				</th>
				<td>
					<input type="text" name="promotionlinksecond" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					促销活动图片二:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="promotionimagesecond" class="text" value="${experience.promotionimagesecond}" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton3" class="button" value="${message("admin.browser.select")}" />
						[#if experience.promotionimagesecond??]
							<a href="${experience.promotionimagesecond}" target="_blank">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
				<tr>  
					<th>
						公交线路:  
					</th>
					<td colspan="2">
					    <textarea name="busline" class="text">
					   </textarea>
					   <input type ="hidden" name ="entcode" value = "qinyuan" />
					</td>
				</tr>
			<tr>  
					<th>
						体验店简介:  
					</th>
					<td colspan="2">
					    <textarea name="introduction" class="text">
					   </textarea>
					</td>
				</tr>		
			 <tr>
			      
			 </tr>
		</table>
		
		<table id="productTable" class="input">  
				<tr>
					<th>
						${message("Promotion.products")}:
					</th>
					<td colspan="2">
						<input type="text" id="productSelect" name="productSelect" class="text" maxlength="200" title="${message("admin.promotion.productSelectTitle")}" />
					</td>
				</tr>
				<tr id="productTitle" class="title hidden">
					<th>
						&nbsp;   
					</th>
					<td width="712">
						${message("Product.name")}
					</td>
					<td>
						${message("admin.common.handle")}
					</td>
				</tr>
			</table>
		</div>
		
		<table id="productImageTable" class="input tabContent">
			<tr>
				<td colspan="4">
					<a href="javascript:;" id="addProductImage" class="button">${message("admin.product.addProductImage")}</a>
				</td>
			</tr>
			<tr class="title">
				<td>
					${message("ProductImage.file")}
				</td>
				<td>
					${message("ProductImage.title")}
				</td>
				<td>
					${message("admin.common.order")}
				</td>
				<td>
					${message("admin.common.delete")}
				</td>
			</tr>
		</table>   
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>