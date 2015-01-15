<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.promotion.list")}</title>
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	[@flash_message /]
	var $statusSelect = $("#statusSelect");
	var $statusOption = $("#statusOption a");
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

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 预售申请管理<span>(${message("admin.page.total", page.total)})</span>
	</div>   
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="statusSelect" class="button">
						状态<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="statusOption" class="check">
							<li>
								<a href="javascript:;" name="reportStatus" val="approving" [#if reportStatus=='approving'] class="checked"[/#if]>抢购未开始</a>
							</li>
							<li>
								<a href="javascript:;" name="reportStatus" val="pass" [#if reportStatus=='pass'] class="checked"[/#if]>抢购进行中</a>
							</li>
							<li class="separator">
								<a href="javascript:;" name="reportStatus" val="reject" [#if reportStatus=='reject'] class="checked"[/#if]>抢购已结束</a>
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
				&nbsp;&nbsp;
				${message("Presell.apply_date")}: <input type="text" id="beginDate" name="beginDate" class="text Wdate" value="${(beginDate?string('yyyy-MM-dd'))!}" onfocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')}'});" />
			    - <input type="text" id="endDate" name="endDate" class="text Wdate" value="${(endDate?string('yyyy-MM-dd'))!}" onfocus="WdatePicker({minDate: '#F{$dp.$D(\'beginDate\')}'});" />
			<input type="submit" class="button" value="${message("admin.common.submit")}" />
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
							<a href="javascript:;"[#if page.searchProperty == "name"] class="current"[/#if] val="name">${message("Presell.preSell_apply_no")}</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "title"] class="current"[/#if] val="title">${message("PreSell.Item_name")}</a>
						</li>
							<li>
							<a href="javascript:;"[#if page.searchProperty == "title"] class="current"[/#if] val="title">${message("PreSell.person")}</a>
						</li>
							<li>
							<a href="javascript:;"[#if page.searchProperty == "title"] class="current"[/#if] val="title">${message("PreSell.phone")}</a>
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
					<a href="javascript:;" class="sort" name="apply_no">${message("Presell.preSell_apply_no")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="Item_name">${message("PreSell.Item_name")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="apply_date">${message("Presell.apply_date")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="status">${message("Presell.status")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="order">${message("Presell.username")}</a>
				</th>				
					<th>
					<a href="javascript:;" class="sort" name="order">${message("PreSell.person")}</a>
				</th>	
					<th>
					<a href="javascript:;" class="sort" name="order">${message("PreSell.phone")}</a>
				</th>	
					<th>
					<a href="javascript:;" class="sort" name="order">${message("Presell.email")}</a>
				</th>	
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as presell]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${presell.id}" />
					</td>
					<td>
						<span title="${promotion.name}">${abbreviate(promotion.name, 50, "...")}</span>
					</td>
					<td>
						<span title="${promotion.title}">${abbreviate(promotion.title, 50, "...")}</span>
					</td>
					<td>
						[#if promotion.beginDate?exists]
							<span title="${promotion.beginDate?string("yyyy-MM-dd HH:mm:ss")}">${promotion.beginDate}</span>
						[#else]
							-
						[/#if]
					</td>
					<td>
						[#if promotion.endDate?exists]
							<span title="${promotion.endDate?string("yyyy-MM-dd HH:mm:ss")}">${promotion.endDate}</span>
						[#else]  
							-
						[/#if]
					</td>
					<!--
					<td>  
						${promotion.order}
					</td>
					-->
					<td>
						<a href="edit.jhtml?id=${promotion.id}">[${message("admin.common.edit")}]</a>
						[#if promotion.isEnabled]
							<a href="${base}${promotion.path}" target="_blank">[${message("admin.common.view")}]</a>
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