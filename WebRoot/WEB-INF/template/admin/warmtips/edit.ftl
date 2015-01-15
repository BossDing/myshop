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
	var $browserButton = $("#browserButton");
	
	[@flash_message /]
	
	$browserButton.browser();
	
	// 表单验证
	$inputForm.validate({
		rules: {
			timeTopLine: "required",
			timeBottomLine: "required",
			warmTipsDesc: "required",
			areaId: "required" 
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑温馨提示
	</div>
	<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${warmtips.id}" />
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.promotion.base")}" />
			</li>
		</ul>
		<div class="tabContent">
			<table class="input">
				<tr>
					<th>
						<span class="requiredField">*</span>省份:
					</th>
					<td style="text-align:left">
						<select id="areaId" name="areaId" style="width:80px; height:25px" >
						[#if warmtips.id >0]
							<option value='${warmtips.area.id}'>${warmtips.area.name}</option>
							[#if areas?has_content]
								[#list areas as area]
									<option value='${area.id}'>${area.name}</option>
								[/#list]
							[/#if]
						[#else]
							[#if areas?has_content]
								[#list areas as area]
									<option value='${area.id}'>${area.name}</option>
								[/#list]
							[/#if]
						[/#if]
						</select>
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>时间上限:
					</th>
					<td colspan="2">
						<input type="text" id="timeTopLine" name="timeTopLine" value="${warmtips.timeTopLine?string("yyyy-MM-dd HH:mm:ss")}" 
							class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'timeBottomLine\')}'});" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>时间下限:
					</th>
					<td colspan="2">
						<input type="text" id="timeBottomLine" name="timeBottomLine" value="${warmtips.timeBottomLine?string("yyyy-MM-dd HH:mm:ss")}" 
							class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate: '#F{$dp.$D(\'timeTopLine\')}'});" />
					</td>
				</tr>
				
				<tr>
					<th>  
						<span class="requiredField">*</span>温馨用语：
					</th>
					<td colspan="2">
						<input type="text" name="warmTipsDesc" value="${warmtips.warmTipsDesc}" class="text"  maxlength="9" />
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