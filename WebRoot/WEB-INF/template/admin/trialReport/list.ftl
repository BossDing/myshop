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
	var $rateSelect = $("#rateSelect");
	var $trialRateOption = $("#trialRateOption a");
	
	// 报告状态筛选
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
	
	// 试用评分筛选
	$rateSelect.mouseover(function() {
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
	$trialRateOption.click(function() {
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
		url: "${base}/admin/trial/report/audit.jhtml",
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
				window.location.href = "${base}/admin/trial/report/list.jhtml";
			}, 1000);
		}
	});
}
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 试用报告管理<span>(${message("admin.page.total", page.total)})</span>
	</div>   
	<form id="listForm" action="list.jhtml" method="get">
		<input type="hidden" id="reportStatus" name="reportStatus" value="${reportStatus}" />
		<input type="hidden" id="trialRate" name="trialRate" value="${trialRate}" />
		<div class="bar">
			<!-- <a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a> -->
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
								<a href="javascript:;" name="reportStatus" val="approving" [#if reportStatus=='approving'] class="checked"[/#if]>审核中</a>
							</li>
							<li>
								<a href="javascript:;" name="reportStatus" val="pass" [#if reportStatus=='pass'] class="checked"[/#if]>审核通过</a>
							</li>
							<li class="separator">
								<a href="javascript:;" name="reportStatus" val="reject" [#if reportStatus=='reject'] class="checked"[/#if]>未审核通过</a>
							</li>
			
						</ul>
					</div>
				</div>
				<div class="menuWrap">
					<a href="javascript:;" id="rateSelect" class="button">
						试用评分<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="trialRateOption" class="check">
							<li>
								<a href="javascript:;" name="trialRate" val="5" [#if trialRate=='5'] class="checked"[/#if]>5分</a>
							</li>
							<li>
								<a href="javascript:;" name="trialRate" val="4" [#if trialRate=='4'] class="checked"[/#if]>4分</a>
							</li>
							<li>
								<a href="javascript:;" name="trialRate" val="3" [#if trialRate=='3'] class="checked"[/#if]>3分</a>
							</li>
							<li>
								<a href="javascript:;" name="trialRate" val="2" [#if trialRate=='2'] class="checked"[/#if]>2分</a>
							</li>
							<li>
								<a href="javascript:;" name="trialRate" val="1" [#if trialRate=='1'] class="checked"[/#if]>1分</a>
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
							<a href="javascript:;"[#if page.searchProperty == "trialReportNo"] class="current"[/#if] val="trialReportNo">报告单号</a>
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
					<a href="javascript:;" class="sort" name="member">报告人</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="trialReportNo">报告单号</a>
				</th>
                <th>
					<a href="javascript:;" class="sort" name="reportName">昵称</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="sex">性别</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="age">年龄</a>
				</th>
				</th>
                <th>
					<a href="javascript:;" class="sort" name="trial">试用名称</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="trialApply">申请宣言</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="trialRate">试用评分</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="reportStatus">报告状态</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">报告时间</a>
				</th>

				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as report]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${report.id}" />
					</td>
					<td>
						<span title="报告人">${abbreviate(report.member.username, 20, "...")}</span>
					</td>
					<td>
						<span title="报告单号">${report.trialReportNo}</span>
					</td>
                    <td>
						<span title="昵称">${abbreviate(report.reportName, 20, "...")}</span>
					</td>
                    <td>
						<span title="性别">
							[#if report.sex == "M"]
								男
							[#elseif report.sex == "W"]
								女
							[#else]
								不详
							[/#if]
						</span>
					</td>   
					<td>
						<span title="年龄">${abbreviate(report.age, 20, "...")}</span>
					</td>  
					<td>
						<span title="试用名称">${abbreviate(report.trial.name, 20, "...")}</span>
					</td>
					<td>
						<span title="申请宣言">${abbreviate(report.trialApply.applyReason, 20, "...")}</span>
					</td>
					<td>
						<span title="试用评分">
							[#if report.trialRate == 5]
							 	非常满意（5分）
							[#elseif report.trialRate == 4]
								满意（4分）
							[#elseif report.trialRate == 3]
								一般（3分）
							[#elseif report.trialRate == 2]
								不满意（2分）
							[#elseif report.trialRate == 1]
								非常不满（1分）
							[/#if]
						</span>
					</td>
					
					<td>
						<span title="报告状态">
							[#if report.reportStatus == "approving" || report.reportStatus == null]
						    	审核中
					    	[#elseif report.reportStatus == "pass"]
					    		审核通过
					    	[#elseif report.reportStatus == "reject"]
					    		未通过审核
					    	[/#if]
						</span>
					</td>
					<td>
						<span title="报告时间">${abbreviate(report.createDate, 20, "...")}</span>
					</td>
					<td>
						<!--<a href="edit.jhtml?id=${experienceApply.id}">[查看]</a>
						[#if experienceApply.isEnabled]
							<a href="${base}${experienceApply.path}" target="_blank">[${message("admin.common.view")}]</a>
						[/#if]-->
						
						[#if report.reportStatus == "pass"]
						     <span style="color:green;">已审核</span>
						[#elseif report.reportStatus == "reject"]
						    <select name="status">
								<option value="pass">通过</option>
							</select>
							<input type="button" value="审核" onclick="audit(${report.id},this)"/>
						[#else]
							<select name="status">
								<option value="pass">通过</option>
								<option value="reject">未通过</option>
							</select>
							<input type="button" value="审核" onclick="audit(${report.id},this)"/>
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