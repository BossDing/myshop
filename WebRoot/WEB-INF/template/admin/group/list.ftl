<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.group.list")} - Powered By SHOP++</title>
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
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 团购列表 <span>(${message("admin.page.total", page.total)})</span>
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
							<a href="javascript:;"[#if page.searchProperty == "name"] class="current"[/#if] val="name">name</a>
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
					<a href="javascript:;" class="sort" name="name">${message("admin.group.name")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="type">${message("admin.group.type")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="prbegindate">${message("admin.group.grstartdata")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="prenddate">${message("admin.group.grenddate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="begindate">${message("admin.group.startdate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="enddate">${message("admin.group.enddate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="order">${message("admin.group.order")}</a>
				</th>
				<th>
					${message("admin.group.edit")}
				</th>
			</tr>
			[#list page.content as gr]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${gr.id}" />
					</td>
					<td>
						<span title="${gr.name}">${abbreviate(gr.name, 50, "...")}</span>
					</td>
					<td>
						${gr.type}
					</td>
					<td>
						<span title="${gr.prbegindate?string("yyyy-MM-dd HH:mm:ss")}">${gr.prbegindate}</span>
					</td>
					<td>
						<span title="${gr.prenddate?string("yyyy-MM-dd HH:mm:ss")}">${gr.prenddate}</span>
					</td>
					<td>
						<span title="${gr.begindate?string("yyyy-MM-dd HH:mm:ss")}">${gr.begindate}</span>
					</td>
					<td>
						<span title="${gr.enddate?string("yyyy-MM-dd HH:mm:ss")}">${gr.enddate}</span>
					</td>
					<td>
						${gr.order}
					</td>
					<td>
						<a href="edit.jhtml?id=${gr.id}">[${message("admin.common.edit")}]</a>
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