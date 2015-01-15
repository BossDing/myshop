<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>可同步促销列表 - Powered By SHOP++</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	[@flash_message /]
	
	var $tongbuButton = $("#tongbuButton");
	var $selectAll = $("#selectAll");
	var $ids = $("#listTable input[name='ids']");
		//同步到本店铺
	$tongbuButton.click( function() {
		var $this = $(this);
		if ($this.hasClass("disabled")) {
			return false;
		}
		var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
		$.dialog({
			type: "warn",
			content: "您确认要同步选择商品到您的店铺吗?",
			ok: message("admin.dialog.ok"),
			cancel: message("admin.dialog.cancel"),
			onOk: function() {
				$.ajax({
					url: "tongbu.jhtml",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
					}
				});
			}
		});
	});

	// 全选
	$selectAll.click( function() {
		var $this = $(this);
		var $enabledIds = $("#listTable input[name='ids']:enabled");
		if ($this.prop("checked")) {
			$enabledIds.prop("checked", true);
			if ($enabledIds.filter(":checked").size() > 0) {
				$tongbuButton.removeClass("disabled");
				$contentRow.addClass("selected");
			} else {
				$tongbuButton.addClass("disabled");
			}
		} else {
			$enabledIds.prop("checked", false);
			$tongbuButton.addClass("disabled");
			$contentRow.removeClass("selected");
		}
	});
	// 选择
	$ids.click( function() {
		var $this = $(this);
		if ($this.prop("checked")) {
			$this.closest("tr").addClass("selected");
			$tongbuButton.removeClass("disabled");
		} else {
			$this.closest("tr").removeClass("selected");
			if ($("#listTable input[name='ids']:enabled:checked").size() > 0) {
				$tongbuButton.removeClass("disabled");
			} else {
				$tongbuButton.addClass("disabled");
			}
		}
	});
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 可同步促销列表
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			<div class="buttonWrap">
				<a href="javascript:;" id="tongbuButton" class="iconButton disabled">
					<span class="addIcon">&nbsp;</span>同步到本店铺
				</a>
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<div class="popupMenu">
						<ul id="filterOption" class="check">
							<li>
								<a href="javascript:;" name="isMarketable" val="true"[#if isMarketable?? && isMarketable] class="checked"[/#if]>${message("admin.product.isMarketable")}</a>
							</li>
						</ul>
					</div>
				</div>
				<a href="javascript:;" id="moreButton" class="button">${message("admin.product.moreOption")}</a>
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
						<a href="${base}${promotion.path}" target="_blank">[${message("admin.common.view")}]</a>
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