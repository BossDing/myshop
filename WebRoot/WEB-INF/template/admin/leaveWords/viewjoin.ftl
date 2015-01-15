<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>加盟留言</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");		

	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		rules: {
			content: {
				required: true,
				maxlength: 200
			}
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 查看
	</div>
	<form id="inputForm" action="reply.jhtml" method="post">
		<input type="hidden" name="id" value="${consultation.id}" />
		<table class="input">
			<tr>
				<th>
					用户姓名
				</th>
				<td colspan="2">
					${leaveWords.userName}   
				</td>
			</tr>	
			<tr>
				<th>
					手机号
				</th>
				<td colspan="2">
					${leaveWords.phone}   
				</td>
			</tr>	
			<tr>
				<th>
					地址
				</th>
				<td colspan="2">
					${leaveWords.address}   
				</td>
			</tr>	
			<tr>
				<th>
					QQ
				</th>
				<td colspan="2">
					${leaveWords.qq}   
				</td>
			</tr>	
			<tr>
				<th>
					微信号
				</th>
				<td colspan="2">
					${leaveWords.micro}   
				</td>
			</tr>	
			<tr>
				<th>
					备注
				</th>
				<td colspan="2">
					${leaveWords.remark}   
				</td>
			</tr>	
			<tr>
				<th>
					电子邮件
				</th>
				<td colspan="2">
					${leaveWords.email}   
				</td>
			</tr>	
			<tr>
				<th>
					加盟区域
				</th>
				<td colspan="2">
					${leaveWords.joinArea}   
				</td>
			</tr>	
			<tr>
				<th>
					加盟类型
				</th>
				<td colspan="2">
					${leaveWords.joinType}   
				</td>
			</tr>	
			<tr>
				<th>
					&nbsp;
				</th>
				<td colspan="2">
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='listjion.jhtml?label=2'" />
				</td>
			</tr>		
		</table>   
	</form>
</body>
</html>