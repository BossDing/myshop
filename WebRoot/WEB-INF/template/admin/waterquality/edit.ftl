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
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
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
	var $areaId = $("#areaId");
	var $browserButton = $("#browserButton");
	
	[@flash_message /]
	
	$browserButton.browser();
	
		//地区选择
	$areaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	
	// 表单验证
	$inputForm.validate({
		rules: {
			areaId: "required",
			itemSpec: "required",
			
			  
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 设置机型推荐
	</div>
	<form id="inputForm" action="updateItmSpec.jhtml" method="post">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.promotion.base")}" />
			</li>
		</ul>
		<div class="tabContent">
			<table class="input" >
				<tr>
					<th style="text-align:center">
						<span class="requiredField">*</span>选择地区
					</th>
					<td colspan="4">
					<span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId"/>
					</span>
					</td>
				</tr>
				
				<tr>
					<th style="text-align:center">
						<span class="requiredField">*</span>TDS:
					</th>
					<th>
						大于
					</th>
					<td style="text-align:center">
						<input type="text" name="gt_tds" class="text" maxlength="200" />
					</td>
					<th  style="text-align:center">
						小于等于
					</th>
					<td >
						<input type="text" name="ltqt_tds" class="text" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th style="text-align:center">
						<span class="requiredField">*</span>余氯:
					</th>
					<th>
						大于
					</th>
					<td style="text-align:center">
						<input type="text" name="gt_cl" class="text" maxlength="200" />
					</td>
					<th  style="text-align:center">
						小于等于
					</th>
					<td>
						<input type="text" name="ltqt_cl" class="text" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th style="text-align:center">
						<span class="requiredField">*</span>推荐机型:
					</th>
					
					<td colspan="4">
						<input type="text" name="itemSpec" class="text" maxlength="200" />
					</td>
				</tr>
				
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					
				</td>
				<td>
					
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>