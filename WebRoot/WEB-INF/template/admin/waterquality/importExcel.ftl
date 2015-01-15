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

<script type="text/javascript">
$().ready(function() {
	var $browserButton = $("#browserButton");
	[@flash_message /]
	$browserButton.browser();
	
	
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; Excel批量数据导入
	</div>
	<form id="inputForm" action="uploadExcel.jhtml" method="post" enctype="multipart/form-data">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.promotion.base")}" />
			</li>
		</ul>
		<div class="tabContent">
			<table class="input" >
				<tr>
					<th>
						<span class="requiredField">*</span>选择Excel文件:
					</th>
					
					<td style="text-align:left">
						<input id="file" type="file" name="file" />
					</td>
				</tr>
				<tr>
				<th>
					下载文件模板:
				</th>
				<td>
					<a href="${base}/resources/admin/WaterQualityExcel/waterQualityDatasExemple.xls"><input type="button" value="下载" class="button" /></a>
				</td>
			</tr>
				
			<tr>
	
				<td style="text-align:center">
					<input  type="submit" class="button" value="${message("admin.common.submit")}" />
					
				</td>
				<td style="text-align:left">
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>