<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>店铺管理</title>
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]

});
/** 审核*/
function check(_this, storeId){
    var value = $(_this).prev().val();
    $.ajax({
    	url: "check.jhtml",
    	type: "post",
    	data: {id: storeId, status: value},
    	dataType: "json",
    	cache: false,
    	beforeSend: function() {
			$(_this).prop("disabled", true);
		},
		success: function(data) {
    		$.message(data);
    		setTimeout(function() {
    			window.location.reload();
				//window.location.href = "${base}/admin/store/list.jhtml";
			}, 3000);
    	}
    });
}
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 店铺列表 <span>(${message("admin.page.total", page.total)})</span>
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<div class="buttonWrap">
                [#if hasStore == false]
					<a href="javascript:;" id="deleteButton" class="iconButton disabled">
						<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
					</a>
                [/#if]
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
							<a href="javascript:;"[#if page.searchProperty == "name"] class="current"[/#if] val="name">名称</a>
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
					<a href="javascript:;" class="sort" name="name">名称</a>
				</th>
				<th>
					<span>url</span>
				</th>
                <th>
					<span>店铺首页链接</span>
				</th>
				<th>
					<a href="javascript:;" class="sort">是否为主店铺</a>
				</th>
                <th>
					<span>${message("admin.store.checkStatus")}</span>
				</th>
				<th>
					<span>启用状态</span>
				</th>
				<th>
					<span>操作</span>
				</th>
			</tr>
			[#list page.content as store]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${store.id}" />
					</td>
					<td>
						${store.name}
					</td>
					<td>
						${store.url}
					</td>
                    <td>
						${store.indexUrl}
					</td>
					<td>
						[#if store.isMainStore]
							<span class="green">是</span>
						[/#if]
					</td>
                    <td>
						[#if store.checkStatus == "wait"]
							<span class="green">${message("admin.store.waitCheck")}</span>
                        [#elseif store.checkStatus == "failure"]
                        	<span class="red">${message("admin.store.failure")}</span>
                        [#elseif store.checkStatus == "success"]
                        	<span class="green">${message("admin.store.success")}</span>
						[/#if]
					</td>
					<td>
						[#if !store.isEnabled]
							<span class="red">${message("admin.admin.disabled")}</span>
						[#else]
							<span class="green">${message("admin.admin.normal")}</span>
						[/#if]
					</td>
					<td>
						<a href="edit.jhtml?id=${store.id}">[${message("admin.common.edit")}]</a>
                        [#if store.checkStatus == "wait"]
							<select>
                                <option value="T">${message("admin.store.pass")}</option>
                                <option value="F">${message("admin.store.refuse")}</option>
                        	</select>&nbsp;
                            <input type="button" value="${message("admin.store.check")}" onclick="check(this, ${store.id});"/>
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