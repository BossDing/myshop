<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.group.add")} </title>
<meta name="author" content="ZSGY Team" />
<meta name="copyright" content="ZSGY" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $browserButton = $("input.browserButton");
	
	[@flash_message /]

	$browserButton.browser();

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			purchaseprice: "required",
			marketprice: "required",
			sn: "required",
			grstartdata: "required",
			grenddate: "required",
			startdate: "required",
			enddate: "required",
			grdesc: "required"
			
			
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.ad.add")}
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.name")}:
				</th>
				<td>
					<input type="text" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.group.type")}:
				</th>
				<td>
					<input type="text" name="type" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.showpicture")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="path" id="path" class="text" value="" maxlength="200" />
						<input type="button" class="button browserButton" value="${message("admin.browser.select")}" />
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.price")}:
				</th>
				<td>
					<input type="text" id="purchaseprice" name="purchaseprice" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.commonprice")}:
				</th>
				<td>
					<input type="text" id="marketprice" name="marketprice" class="text" maxlength="9" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.goods")}:
				</th>
				<td>
					<input type="text" id="sn" name="sn" class="text" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.counts")}:
				</th>
				<td>
					<input type="text" id="buycount" name="buycount" class="text" maxlength="9" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.remindcounts")}:
				</th>
				<td>
					<input type="text" id="wantcount" name="wantcount" class="text" maxlength="9" />
				</td>
			</tr>
		        <tr>
				<th>
					${message("admin.group.grstartdata")}:
				</th>
				<td>
					<input type="text" id="prbegindate" name="prbegindate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'prenddate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.group.grenddate")}:
				</th>
				<td>
					<input type="text" id="prenddate" name="prenddate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'prbegindate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.group.startdate")}:
				</th>
				<td>
					<input type="text" id="begindate" name="begindate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'prenddate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.group.enddate")}:
				</th>
				<td>
					<input type="text" id="enddate" name="enddate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'begindate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.grdesc")}:
				</th>
				<td>
					<textarea id="grdesc" name="grdesc" class="text"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("admin.group.order")}:
				</th>
				<td>
					<input type="text" id="order" name="order" class="text" maxlength="9" />
				</td>
			</tr>
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