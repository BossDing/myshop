<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.experienceApply.list")}</title>
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]
	
	var $listForm = $('#listForm');	
	var $statusSelect = $("#statusSelect");
	var $statusOption = $("#statusOption a");
	
	// 试用状态筛选
	$statusSelect.mouseover(function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.menuWrap");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	}); 
	
	// 筛选选项
	$statusOption.click(function() {
		var $this = $(this);
		var $dest = $("#" + $this.attr("name"));
		if ($this.hasClass("checked")) {
			$dest.val("");
		} else {
			$dest.val($this.attr("val"));
		}
		$listForm.submit();
		return false;
	});

});
//审核
function audit(num,obj){
	var status = $(obj).siblings('select').val();
	$.ajax({
		url: "${base}/admin/trial/apply/audit.jhtml",
		type: "POST",
		data: {
			id: num,
			status: status
		},
		dataType: "json",
		cache: false,
		success: function(message) {
			$.message(message);
			setTimeout(function() {
				window.location.href = "${base}/admin/trial/apply/list.jhtml";
			}, 1000);
		}
	});
}
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 试用申请管理<span>(${message("admin.page.total", page.total)})</span>
	</div>   
	<form id="listForm" action="list.jhtml" method="get">
		<input type="hidden" id="applyStatus" name="applyStatus" value="${applyStatus}" />
		<div class="bar">
			<!--
			<a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>-->
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="statusSelect" class="button">
						申请状态<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="statusOption" class="check">
							<li>
								<a href="javascript:;" name="applyStatus" val="approving" [#if applyStatus=='approving'] class="checked"[/#if]>审核中</a>
							</li>
							<li>
								<a href="javascript:;" name="applyStatus" val="pass" [#if applyStatus=='pass'] class="checked"[/#if]>审核通过</a>
							</li>
							<li class="separator">
								<a href="javascript:;" name="applyStatus" val="reject" [#if applyStatus=='reject'] class="checked"[/#if]>未审核通过</a>
							</li>
			
						</ul>
					</div>
				</div>
				<div class="menuWrap">
					<a href="javascript:;" id="pageSizeSelect" class="button">
						${message("admin.page.pageSize")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="pageSizeOption">
							<li>
								<a href="javascript:;"[#if page.pageSize == 10] class="current"[/#if] val="10">10</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 20] class="current"[/#if] val="20">20</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 50] class="current"[/#if] val="50">50</a>
							</li>
							<li>
								<a href="javascript:;"[#if page.pageSize == 100] class="current"[/#if] val="100">100</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="menuWrap">
				<div class="search">
					<span id="searchPropertySelect" class="arrow">&nbsp;</span>
					<input type="text" id="searchValue" name="searchValue" value="${page.searchValue}" maxlength="200" />
					<button type="submit">&nbsp;</button>
				</div>
				<div class="popupMenu">
					<ul id="searchPropertyOption">
						<li>
							<a href="javascript:;"[#if page.searchProperty == "trialApplyNo"] class="current"[/#if] val="trialApplyNo">申请单号</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>   
				<th>
					<a href="javascript:;" class="sort" name="member">申请人</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="trialApplyNo">申请单号</a>
				</th>
                <th>
					<a href="javascript:;" class="sort" name="trial">试用名称</a>
				</th>
                <th>
					<a href="javascript:;" class="sort" name="applyReason">申请宣言</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="applyDate">申请时间</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="receiver">收货人</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="address">详细地址</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="telephone">联系电话</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="applyStatus">申请状态</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="isReport">是否提交试用报告</a>
				</th>

				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as apply]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${apply.id}" />
					</td>
					<td>
						<span title="申请人">${abbreviate(apply.member.username, 20, "...")}</span>
					</td>
					<td>
						<span title="申请单号">${apply.trialApplyNo}</span>
					</td>
                    <td>
						<span title="试用名称">${abbreviate(apply.trial.name, 20, "...")}</span>
					</td>
                    <td>
						<span title="申请宣言">${abbreviate(apply.applyReason, 20, "...")}</span>
					</td>     
					<td>
						<span title="申请时间">${abbreviate(apply.applyDate, 20, "...")}</span>
					</td>
					<td>
						<span title="收货人">${abbreviate(apply.receiver, 20, "...")}</span>
					</td>
					<td>
						<span title="详细地址">${abbreviate(apply.address, 20, "...")}</span>
					</td>
					<td>
						<span title="联系电话">${abbreviate(apply.telephone, 20, "...")}</span>
					</td>
					<td>
						<span title="申请状态">
							[#if apply.applyStatus == "approving" || apply.applyStatus == null]
						    	审核中
					    	[#elseif apply.applyStatus == "pass"]
					    		审核通过
					    	[#elseif apply.applyStatus == "reject"]
					    		未通过审核
					    	[/#if]
						</span>
					</td>
					<td>
						<span title="是否提交试用报告">
							[#if apply.isReport == null || apply.isReport == "No"]
								否
							[#elseif apply.isReport == "Yes"]
								是
							[/#if]
						</span>
					</td>
					<td>
						<!--<a href="edit.jhtml?id=${experienceApply.id}">[${message("admin.common.edit")}]</a>
						[#if experienceApply.isEnabled]
							<a href="${base}${experienceApply.path}" target="_blank">[${message("admin.common.view")}]</a>
						[/#if]-->
						[#if apply.applyStatus == "pass"]
						     <span style="color:green;">已审核</span>
						[#elseif apply.applyStatus == "reject"]
						    <select name="status">
								<option value="pass">通过</option>
							</select>
							<input type="button" value="审核" onclick="audit(${apply.id},this)"/>
						[#else]
							<select name="status">
								<option value="pass">通过</option>
								<option value="reject">未通过</option>
							</select>
							<input type="button" value="审核" onclick="audit(${apply.id},this)"/>
					    [/#if]
					</td>
				</tr>
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>