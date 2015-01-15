<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.afterBooking.list")} - Powered By SHOP++</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<style type="text/css">
.moreTable th {
	width: 80px;
	line-height: 25px;
	padding: 5px 10px 5px 0px;
	text-align: right;
	font-weight: normal;
	color: #333333;
	background-color: #f8fbff;
}

.moreTable td {
	line-height: 25px;
	padding: 5px;
	color: #666666;
}

.promotion {
	color: #cccccc;
}
</style>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo;安装预约列表
	</div>
	<form id="listForm" action="list2.jhtml" method="get">
		<input type="hidden" name="label" value="0" />
		<div class="bar">
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
							<a href="javascript:;"[#if page.searchProperty == "sn"] class="current"[/#if] val="sn">预约编号</a>
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
					<a href="javascript:;" class="sort" name="sn">编码</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="consignee">预约人</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="phone">预约手机号码</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="visitServiceDate">预约时间</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="address">地址</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="source">预约来源</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="type">类型</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="state">当前状态</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">创建时间</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="finishDate">完成时间</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as afterBooking]
				[#if afterBooking.type=='install']
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${afterBooking.id}" />
					</td>
					<td>
						${afterBooking.sn}
					</td>
					<td>
						<span title="${(afterBooking.consignee)!'-'}">
						${(afterBooking.consignee)!'-'}
						</span>
					</td>
					<td>
						${afterBooking.phone}
					</td>
					<td>
						${(afterBooking.visitServiceDate?string.medium)!'-'}
					</td>
					<td>
						<span title="${afterBooking.address}">
						[#if afterBooking.address??]
						${abbreviate(afterBooking.address, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						[#if afterBooking.source??]
							[#if afterBooking.source == 0]
								<span title="万家乐商城">商城</span>
							[#elseif afterBooking.source==1]
								<span title="万家乐官网">官网</span>
							[/#if]
						[/#if]
					</td>
					<td>
						[#if afterBooking.type=='install']安装[#elseif  afterBooking.type=='repair']维修[/#if]
					</td>
					<td>  
					    [#if afterBooking.state??]
							[#if afterBooking.state == 0]
								<span title="未处理或处理中">未完成</span>
							[#elseif afterBooking.state==1]
								<span title="已完成">完成</span>
							[/#if]
						[/#if]
					</td>
					<td>
						<span>${afterBooking.createDate?string.medium}</span>
					</td>
					<td>
						<span>${(afterBooking.finishDate?string.medium)!'-'}</span>
					</td>
					<td>
						<a href="edit.jhtml?id=${afterBooking.id}">[${message("admin.common.edit")}]</a>
					</td>
				</tr>
				[/#if]
			[/#list]
		</table>
		[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
			[#include "/admin/include/pagination.ftl"]
		[/@pagination]
	</form>
</body>
</html>