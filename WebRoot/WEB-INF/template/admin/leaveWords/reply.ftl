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
	var $mailTestStatus = $("#mailTestStatus");

	[@flash_message /]
	
	// 表单验证
	$inputForm.validate({
		rules: {
			content: {
				required: true,
				maxlength: 200
			}
		},
		submitHandler: function(form){
			$.ajax({
				url : $inputForm.attr("action"),
				type: "POST",
				data : {id : $("#leaveWordId").val(), content: $("#replyContent").val()},
				datyType : "json",
				cache : false,
				beforeSend: function() {
				  $("#submit").prop("disabled", true);
				  $mailTestStatus.html('<span class="loadingIcon">&nbsp;<\/span>正在发送邮件中，请耐心等候...');
				},
				success : function(message){
					$mailTestStatus.empty();
					$.message(message);
					if(message.type == "success"){
						setTimeout(function(){
							window.location.href = "reply.jhtml?id="+$("#leaveWordId").val();
						}, 3000);
					}
				}
			});
		}
	});
	
	
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.consultation.reply")}
	</div>
	<form id="inputForm" action="reply.jhtml" method="post">
		<input type="hidden" name="id" id="leaveWordId" value="${leaveWords.id}" />
		<table class="input">
			<tr>
				<th>
					类型:
				</th>
				<td colspan="2">
					[#if leaveWords.consultationType == "1"]留言
					[#elseif leaveWords.consultationType == "2"]询问
					[#elseif leaveWords.consultationType == "3"]投诉
					[#elseif leaveWords.consultationType == "4"]售后
					[#elseif leaveWords.consultationType == "5"]求购
					[#elseif leaveWords.consultationType == "6"]在线留言
					[/#if]
				</td>
			</tr>
			<tr>
				<th>
					${message("LeaveWords.member")}:
				</th>
				<td colspan="2">
					[#if leaveWords.member??]
						<a href="../member/view.jhtml?id=${leaveWords.member.id}">${leaveWords.member.username}</a>
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
				[#list leaveWords.replyLeaveWords as replyLeaveWords]
					<input type="hidden" id="replyLeaveWordId_${replyLeaveWords.id}" value="${replyLeaveWords.id}" />
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							${replyLeaveWords.content}
						</td>
						<td width="120">
							<span title="${replyLeaveWords.createDate?string("yyyy-MM-dd HH:mm:ss")}">${replyLeaveWords.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
						</td>
						<!--
						<td width="80">
							<a href="javascript:;" id="deleteButton_${replyLeaveWords.id}">[删除]</a>
						</td>
						-->
					</tr>
					<script>
						var $deleteButton = $("#deleteButton_${replyLeaveWords.id}");
							// 删除
						$deleteButton.click( function() {
							var $this = $(this);
						/*
							if ($this.hasClass("disabled")) {
								return false;
							}
							var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
						*/
							$.dialog({
								type: "warn",
								content: message("admin.dialog.deleteConfirm"),
								ok: message("admin.dialog.ok"),
								cancel: message("admin.dialog.cancel"),
								onOk: function() {
									$.ajax({
										url: "deleteReply.jhtml",
										type: "POST",
										//data: $checkedIds.serialize(),
										data: {id: $("#replyLeaveWordId_${replyLeaveWords.id}").val()},
										dataType: "json",
										cache: false,
										success: function(message) {
											$.message(message);
											if(message.type == "success"){
												setTimeout(function(){
													window.location.href = "reply.jhtml?id="+$("#leaveWordId").val();
												}, 1000);
											}
											//$deleteButton.addClass("disabled");
											//$selectAll.prop("checked", false);
											//$checkedIds.prop("checked", false);
										}
									});
								}
							});
						});
					</script>
				[/#list]
			[/#if]
			<tr>
				<th>
					邮箱:
				</th>
				<td colspan="2">
					${leaveWords.email}
				</td>
			</tr>
			<tr>
				<th>
					回复内容:
				</th>
				<td colspan="2">
					<textarea name="content" id="replyContent" class="text">尊敬的用户：
&nbsp;&nbsp;&nbsp;&nbsp;您好！感谢您的留言！
					</textarea>
					<span id="mailTestStatus">&nbsp;</span>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td colspan="2">
					<input type="submit" id="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml?label=1'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>