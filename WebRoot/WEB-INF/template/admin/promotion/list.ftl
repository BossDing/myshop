<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.promotion.list")} - Powered By SHOP++</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.promotion.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>
			<div class="buttonWrap">
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
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
							<a href="javascript:;"[#if page.searchProperty == "name"] class="current"[/#if] val="name">${message("Promotion.name")}</a>
						</li>
						<li>
							<a href="javascript:;"[#if page.searchProperty == "title"] class="current"[/#if] val="title">${message("Promotion.title")}</a>
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
					<a href="javascript:;" class="sort" name="name">${message("Promotion.name")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="title">${message("Promotion.title")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="title">活动类型</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="type">类型</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="beginDate">${message("Promotion.beginDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="endDate">${message("Promotion.endDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="order">${message("admin.common.order")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as promotion]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${promotion.id}" />
					</td>
					<td>
						<span title="${promotion.name}">${abbreviate(promotion.name, 50, "...")}</span>
					</td>
					<td>
						<span title="${promotion.title}">${abbreviate(promotion.title, 50, "...")}</span>
					</td>
					<td>
						<span title="${promotion.actionType.actionName}">${abbreviate(promotion.actionType.actionName, 50, "...")}</span>
					</td>
					<td>
						<span title="[#if promotion.type?? && promotion.type==1]特惠促销商品[#else]活动商品[/#if]">[#if promotion.type?? && promotion.type==1]特惠促销商品[#else]活动商品[/#if]</span>
					</td>
					<td>
						[#if promotion.beginDate??]
							<span title="${promotion.beginDate?string("yyyy-MM-dd HH:mm:ss")}">${promotion.beginDate}</span>
						[#else]
							-
						[/#if]
					</td>
					<td>
						[#if promotion.endDate??]
							<span title="${promotion.endDate?string("yyyy-MM-dd HH:mm:ss")}">${promotion.endDate}</span>
						[#else]
							-
						[/#if]
					</td>
					<td>
						${promotion.order}
					</td>
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