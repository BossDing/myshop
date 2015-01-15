<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.consultation.reply")} - Powered By SHOP++</title>
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
					${message("Consultation.member")}:
				</th>
				<td colspan="2">
					[#if consultation.member??]
						<a href="../member/view.jhtml?id=${consultation.member.id}">${consultation.member.username}</a>
					[#else]
						${message("admin.consultation.anonymous")}
					[/#if]
				</td>
			</tr>
			<tr>
				<th>
					${message("LeaveWords.content")}:
				</th>
				<td colspan="2">
					${leaveWords.content}
				</td>
			</tr>
			[#if leaveWords.replyLeaveWords?has_content]
				<tr class="title">
					<td colspan="3">
						&nbsp;
					</td>
				</tr>
				[#list leaveWords.replyLeaveWords as replyLeaveWord]
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							${replyLeaveWord.content}
						</td>
						<td width="80">
							<span title="${replyLeaveWord.createDate?string("yyyy-MM-dd HH:mm:ss")}">${replyLeaveWord.createDate}</span>
						</td>
					</tr>
				[/#list]
			[/#if]
		</table>
	</form>
</body>
</html>