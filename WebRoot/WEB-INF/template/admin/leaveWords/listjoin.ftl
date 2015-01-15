<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>加盟留言</title>
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
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.consultation.list")} <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="listjion.jhtml" method="get">
		<input type="hidden" id="type" name="type" value="${type}" />
		<input type="hidden"  name="label" value="2" />
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
							<a href="javascript:;"[#if page.searchProperty == "content"] class="current"[/#if] val="content">${message("Consultation.content")}</a>
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
					<a href="javascript:;" class="sort" name="userName">用户姓名</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="phone">电话</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="address">地址</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="QQ">qq</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="micro">微信</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="remark">备注</a>
				</th>
					<th>
					<a href="javascript:;" class="sort" name="email">邮箱</a>
				</th>
				</th>
					<th>
					<a href="javascript:;" class="sort" name="joinArea">加盟区域</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="joinType">加盟类型</a>
				</th>
				<th> 
					<a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="isRead">是否已查看</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list page.content as leaveWords]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${leaveWords.id}" />
					</td>
					
					<td>
						<span title="${leaveWords.userName}">
						[#if leaveWords.userName??]
						${abbreviate(leaveWords.userName, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						<span title="${leaveWords.phone}">
						[#if leaveWords.phone??]
						${abbreviate(leaveWords.phone, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						<span title="${leaveWords.address}">
						[#if leaveWords.address??]
						${abbreviate(leaveWords.address, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						<span title="${leaveWords.qq}">
						[#if leaveWords.qq??]
						${abbreviate(leaveWords.qq, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						<span title="${leaveWords.micro}">
						[#if leaveWords.micro??]
						${abbreviate(leaveWords.micro, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						<span title="${leaveWords.remark}">
						[#if leaveWords.remark??]
						${abbreviate(leaveWords.remark, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						<span title="${leaveWords.email}">
						[#if leaveWords.email??]
						${abbreviate(leaveWords.email, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						<span title="${leaveWords.joinArea}">
						[#if leaveWords.joinArea??]
						${abbreviate(leaveWords.joinArea, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						<span title="${leaveWords.joinType}">
						[#if leaveWords.joinType??]
						${abbreviate(leaveWords.joinType, 50, "...")}
						[#else]—
						[/#if]
						</span>
					</td>
					<td>
						<span title="${leaveWords.createDate?string("yyyy-MM-dd HH:mm:ss")}">${leaveWords.createDate}</span>
					</td>
					<td>
						<span class="${leaveWords.isRead?has_content?string("true", "false")}Icon">&nbsp;</span>
					</td>
					<td>				
						<a href="viewjoin.jhtml?id=${leaveWords.id}">[${message("admin.common.view")}]</a>
						<!--<a href="${base}${consultation.path}" target="_blank">[${message("admin.common.view")}]</a>-->
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