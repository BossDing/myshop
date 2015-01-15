<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.group.update")} </title>
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
	[@flash_message /]
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.ad.add")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
	<input type="hidden" name="id" value="${remind.id}" />
		<table class="input">
			<tr>
				<th>
					${message("admin.remind.person")}:
				</th>
				<td>
					<input type="text" name="contectperson" class="text" value="${remind.contectperson}" readonly="true" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.remind.phone")}:
				</th>
				<td>
					<input type="text" name="mobile" value="${remind.mobile}"  class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.remind.status")}:
				</th>
				<td>
					<select name="isremind">
							<option value="1"[#if 1 == remind.isremind] selected="selected"[/#if]>${message("admin.remind.1")}</option>
							<option value="2"[#if 2 == remind.isremind] selected="selected"[/#if]>${message("admin.remind.2")}</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.remind.time")}:
				</th>
				<td>
					<input type="text" id="purchaseprice" value="${remind.remindtime}" name="remindtime" readonly="true" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.group.name")}:
				</th>
				<td>
					<select name="grid">
						[#list group as group]
							<option value="${group.id}"[#if group.id == remind.groupPurchase] selected="selected"[/#if]>${group.name}</option>
						[/#list]
					</select>
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