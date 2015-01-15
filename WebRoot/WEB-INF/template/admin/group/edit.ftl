<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.group.update")} </title>
<meta name="author" content="ZSGY Team" />
<meta name="copyright" content="ZSGY" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
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
	
	var productIds = [#if promotion.products?has_content][[#list promotion.products as product]${product.id}[#if product_has_next], [/#if][/#list]][#else]new Array()[/#if];
	
	[@flash_message /]

	$browserButton.browser();

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

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			wantcount1: "required",
			wantcount2: "required",
			wantcount3: "required",
			wantcount4: "required",
			wantcount5: "required",
			purchasePrice1: "required",
			purchasePrice2: "required",
			purchasePrice3: "required",
			purchasePrice4: "required",
			purchasePrice5: "required",
			marketPrice: "required",
			//sn: "required",
			grstartdata: "required",
			grenddate: "required",
			startdate: "required",
			enddate: "required"
			//grdesc: "required",
			//productSelect: "required"
			
		}
	});
	
});
</script>


</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑团购
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
	<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.product.base")}" />
			</li>
			<li>
				<input type="button" value="参与商品" />
			</li>
			<li>
				<input type="button" value="详情图片" />
			</li>
		</ul>
	<input type="hidden" name="id" value="${group.id}" />
		<table class="input tabContent">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" value="${group.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.group.type")}:
				</th>
				<td>
					<input type="text" name="type" value="${group.type}" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.showpicture")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="path" id="path" class="text" value="${group.path}" maxlength="200" />
						<input type="button" class="button browserButton" value="${message("admin.browser.select")}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>市场价:
				</th>
				<td>
					<input type="text" id="marketPrice" value="${group.marketPrice}" name="marketPrice" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>参团人数:
				</th>
				<td>
					<input type="text" id="wantcount1" name="wantcount1" value="${group.wantcount1}" class="text" maxlength="9" style="width:70px;"/>
					<input type="text" id="wantcount2" name="wantcount2" value="${group.wantcount2}" class="text" maxlength="9" style="width:70px;"/>
					<input type="text" id="wantcount3" name="wantcount3" value="${group.wantcount3}" class="text" maxlength="9" style="width:70px;"/>
					<input type="text" id="wantcount4" name="wantcount4" value="${group.wantcount4}" class="text" maxlength="9" style="width:70px;"/>
					<input type="text" id="wantcount5" name="wantcount5" value="${group.wantcount5}" class="text" maxlength="9" style="width:70px;"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>对应团购价:
				</th>
				<td>
					<input type="text" id="purchasePrice1" name="purchasePrice1" value="${group.purchasePrice1}" class="text" maxlength="9" style="width:70px;" />
					<input type="text" id="purchasePrice2" name="purchasePrice2" value="${group.purchasePrice2}" class="text" maxlength="9" style="width:70px;" />
					<input type="text" id="purchasePrice3" name="purchasePrice3" value="${group.purchasePrice3}" class="text" maxlength="9" style="width:70px;" />
					<input type="text" id="purchasePrice4" name="purchasePrice4" value="${group.purchasePrice4}" class="text" maxlength="9" style="width:70px;" />
					<input type="text" id="purchasePrice5" name="purchasePrice5" value="${group.purchasePrice5}" class="text" maxlength="9" style="width:70px;" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>当前团购价:
				</th>
				<td>
					<input type="text" id="purchasePrice" value="${group.purchasePrice}" name="purchasePrice" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>定金:
				</th>
				<td>
					<input type="text" id="previousPrice" value="${group.previousPrice}" name="previousPrice" class="text" maxlength="200" />
				</td>
			</tr>
			<!--
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.goods")}:
				</th>
				<td>
					<input type="text" id="sn" name="sn" class="text" value="${group.product.sn}" maxlength="20" />
				</td>
			</tr>
			-->
			<tr>
				<th>
					<span class="requiredField">*</span>目前已参团人数:
				</th>
				<td>
					<input type="text" id="buycount" name="buycount" value="${group.buycount}" class="text" maxlength="9" />
				</td>
			</tr>
	        <tr>
				<th>
					${message("admin.group.grstartdata")}:
				</th>
				<td>
					<input type="text" id="prbegindate" value="${group.prbegindate?string("yyyy-MM-dd HH:mm:ss")}" name="prbegindate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'prenddate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.group.grenddate")}:
				</th>
				<td>
					<input type="text" id="prenddate" value="${group.prenddate?string("yyyy-MM-dd HH:mm:ss")}" name="prenddate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'prbegindate\')}', maxDate: '#F{$dp.$D(\'begindate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.group.startdate")}:
				</th>
				<td>
					<input type="text" id="begindate" value="${group.begindate?string("yyyy-MM-dd HH:mm:ss")}" name="begindate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'prenddate\')}', maxDate: '#F{$dp.$D(\'enddate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.group.enddate")}:
				</th>
				<td>
					<input type="text" id="enddate" value="${group.enddate?string("yyyy-MM-dd HH:mm:ss")}" name="enddate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'begindate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.grdesc")}:
				</th>
				<td>
					<textarea id="grdesc" name="grdesc" class="text">${group.grdesc}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.order")}:
				</th>
				<td>
					<input type="text" id="order" name="order" value="${group.order}" class="text" maxlength="9" />
				</td>
			</tr>
		</table>
		
		<table  class="input tabContent" id="productTable">  
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
			[#list group.products as product]
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
		<table class="input tabContent">
			<tr>
				<td>
                    <textarea id="editor" name="images" class="editor" style="width: 100%;">${group.images}</textarea>
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