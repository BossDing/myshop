<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.promotion.list")}</title>
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list1.js"></script>
<script type="text/javascript">


$().ready(function() {
	[@flash_message /]
	
	var $exportSelect = $("#exportSelect");
	var $exportOption = $("#exportOption li");
	$exportSelect.mouseover( function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.menuWrap");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	});
	
	$exportOption.click( function() {
		var $this = $(this);
		if($this.text().trim()=="选择导出"){
			var $checkedIds = $("#listTable input[name='ids']:enabled:checked");
			if($checkedIds.serialize()==""){
				alert("请选择导出的数据！");
				return false;
			}else{
				window.location.href="download1/waterQualityDatasDowan.xls.jhtml?"+$checkedIds.serialize();			
			}
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo;水质资料设置<span>(${message("admin.page.total", page.total)})</span>
	</div>   
	<form id="listForm" action="list.jhtml" method="get">
		<div class="bar">
			
			<div class="buttonWrap">
				<a href="add.jhtml" class="iconButton">
					<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
				</a>
				
				<a href="javascript:;" id="deleteButton" class="iconButton disabled">
					<span class="deleteIcon">&nbsp;</span>${message("admin.common.delete")}
				</a>
				<a href="setItemSpec.jhtml"  id="suggestButton" class="iconButton">
					<span class="button">&nbsp;</span>机型推荐
				</a>
				<a href="importExcel.jhtml" id="importButton" class="iconButton">
					<span class="excelIcon">&nbsp;</span>Excel导入
				</a>
			
				<div class="menuWrap">
					<a href="javascript:;" id="exportSelect" class="button">
						数据导出<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="exportOption">
							<li>
								<a href="download/waterQualityDatas.xls.jhtml">全部导出</a>
							</li>
							<li>
								<a href="javascript:;">选择导出</a>
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
		</div>
		<table id="listTable" class="list">
			<tr>
				<th class="check">
					<input type="checkbox" id="selectAll" />
				</th>   
				<th>
					<a href="javascript:;" class="sort" name="provinceId">省</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="cityId">市</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="districtId">区</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="communityName">小区名称</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="tds">TDS</a>
				</th>
				
				<th>
					<a href="javascript:;" class="sort" name="chlorine">余氯</a>
				
				</th>
				<th>
					<a href="javascript:;" class="sort" name="itemSpec">推荐机型</a>
				</th>
			</tr>
			[#list page.content as promotion]
				<tr>
					<td>
						<input type="checkbox" name="ids" value="${promotion.id}" />
					</td>
					<td>
						<span title="${promotion.provinceName}">${abbreviate(promotion.provinceName, 50, "...")}</span>
					</td>
					<td>
						<span title="${promotion.cityName}">${abbreviate(promotion.cityName, 50, "...")}</span>
					</td>
					<td>
						<span title="${promotion.districtName}">${abbreviate(promotion.districtName, 50, "...")}</span>
					</td>
					<td>
						<span title="${promotion.communityName}">${abbreviate(promotion.communityName, 50, "...")}</span>
					</td>
					<td>
						<span title="${promotion.tds}">${abbreviate(promotion.tds, 50, "...")}  mg/l </span>
					</td>
					<td>
						<span title="${promotion.chlorine}">${abbreviate(promotion.chlorine, 50, "...")}  mg/l </span>
					</td>
					<td>
						<span title="${promotion.itemSpec}">${abbreviate(promotion.itemSpec, 50, "...")}</span>
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