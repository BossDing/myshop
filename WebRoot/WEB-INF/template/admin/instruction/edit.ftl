<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.group.add")} </title>
<meta name="author" content="ZSGY Team" />
<meta name="copyright" content="ZSGY" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/upfile.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $browserButton = $("input.browserButton");
	
	var $productTable = $("#productTable");
	var $productSelect = $("#productSelect");
	var $deleteProduct = $("a.deleteProduct");
	var $productTitle = $("#productTitle");
	var $productImageTable = $("#productImageTable");
	var productIds = new Array();
	
	[@flash_message /]

	$browserButton.upfile();

	//商品选择
	$productSelect.autocomplete("product_select.jhtml",{
		dataType : "json",
		max : 20,
		width : 600,
		scrollHeight : 300,
		parse : function(data){
			return $.map(data, function(item){
				return {
					data: item,
					value: item.fullName
				}
			});
		},
		formatItem: function(item){
			if($.inArray(item.id, productIds) < 0){
				return '<span title="'+item.fullName+'">'+item.fullName.substring(0,50)+'<\/span>';
			}else{
				return false;
			}
		}
	}).result(function(event, item){
		[@compress single_line = true]
			var trHtml =
			'<tr class="productTr">
				<th>
					<input type="hidden" name="productIds" value="'+item.id+'" />
					&nbsp;
				<\/th>
				<td>
					<span title="'+item.fullName+'">'+item.fullName.substring(0,50)+'<\/span>
				<\/td>
				<td>
					<a href="${base}'+item.path+'" target="_blank">[${message("admin.common.view")}]<\/a>
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

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			url: "required"
		}
	});
});

</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑 产品说明书
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${instruction.id}" />
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>说明书名称:
				</th>
				<td>
					<input type="text" name="name" value="${instruction.name}" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>说明书链接:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="url" value="${instruction.url}" class="text" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button"  class="button browserButton" value="${message("admin.browser.select")}" />
					</span>
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