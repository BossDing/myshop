<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.promotion.edit")} - Powered By SHOP++</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
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
	var $giftTable = $("#giftTable");
	var $giftSelect = $("#giftSelect");
	var $deleteGift = $("a.deleteGift");
	var $giftTitle = $("#giftTitle");
	var productIds = [#if promotion.products?has_content][[#list promotion.products as product]${product.id}[#if product_has_next], [/#if][/#list]][#else]new Array()[/#if];
    var $browserButton = $("#browserButton");
    
    
    
	$browserButton.browser();
	[@flash_message /]
	
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
			
	$.validator.addClassRules({
		giftItemQuantity: {
			required: true,
			integer: true,
			min: 1
		}
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			title: "required",
			qtylimit: {
				required:true,   
				digits:true,
				min:0,
				decimal:{
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			appliernum: {
				digits:true,
				min:0,
				decimal:{
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},
			marketprice: {
				required:true,   
				digits:true,
				min:0,
				decimal:{
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
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑试用
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${trial.id}" />
		<ul id="tab" class="tab">   
			<li>
				<input type="button" value="${message("admin.promotion.base")}" />
			</li>
			<!--
			<li>
				<input type="button" value="${message("Promotion.introduction")}" />
			</li>
			-->
		</ul>
		<div class="tabContent">
			<table class="input">
				<tr>
					<th>
						<span class="requiredField">*</span>${message("Promotion.name")}:
					</th>
					<td colspan="2">
						<input type="text" name="name" class="text" value="${trial.name}" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>${message("Promotion.title")}:
					</th>
					<td colspan="2">
						<input type="text" name="title" class="text" value="${trial.title}" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th>
						${message("Promotion.beginDate")}:
					</th>
					<td colspan="2">
						<input type="text" id="beginDate" name="beginDate" class="text Wdate" value="[#if trial.beginDate??]${trial.beginDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'endDate\')}'});" />
					</td>
				</tr>
				<tr>
					<th>
						${message("Promotion.endDate")}:
					</th>
					<td colspan="2">
						<input type="text" id="endDate" name="endDate" class="text Wdate" value="[#if trial.endDate??]${trial.endDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'beginDate\')}'});" />
					</td>
				</tr>			
				<tr class="memberRank">
					<th>
						${message("Promotion.memberRanks")}
					</th>
					<td colspan="2">
						[#list memberRanks as memberRank]
							<label>
								<input type="checkbox" name="memberRankIds" value="${memberRank.id}"[#if trial.memberRanks?seq_contains(memberRank)] checked="checked"[/#if] />${memberRank.name}
							</label>
						[/#list]
					</td>
				</tr>
				<tr>
					<th>
						预计市场价
					</th>
					<td colspan="2">
						<input type="text" name="marketprice" class="text"  value="${trial.marketprice}" maxlength="16" />
					</td>
				</tr>
				<tr>
					<th>
						试用数量
					</th>
					<td colspan="2">
						<input type="text" name="qtylimit" class="text"  value="${trial.qtylimit}"   maxlength="9" />
					</td>
				</tr>
				<tr>
					<th>
						申请人数
					</th>
					<td colspan="2">
						<input type="text" name="appliernum" class="text"  value="${trial.appliernum}"   maxlength="9"  readOnly="true"/>
					</td>
				</tr>
				<tr>
				<th>
					${message("Product.image")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="image" class="text" value="${trial.image}" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
						[#if product.image??]
							<a href="${product.image}" target="_blank">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
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
				<tr id="productTitle" class="title[#if !promotion.products?has_content] hidden[/#if]">
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
				[#list trial.products as product]
					<tr class="productTr">   
						<th>
							<input type="hidden" name="productIds" value="${product.id}" />
							&nbsp;
						</th>
						<td>
							<span title="${product.fullName}">${abbreviate(product.fullName, 50)}</span>
						</td>
						<td>
							<a href="${base}${product.path}" target="_blank">[${message("admin.common.view")}]</a>
							<a href="javascript:;" class="deleteProduct">[${message("admin.common.delete")}]</a>
						</td>
					</tr>
				[/#list]
			</table>
		</div>
		<div class="tabContent">
			<table class="input">
				<tr>
					<td>
						<textarea id="editor" name="introduction" class="editor" style="width: 100%;">${promotion.introduction?html}</textarea>
					</td>
				</tr>
			</table>
		</div>
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