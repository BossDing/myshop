<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.experienceApply.add")}</title>
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<style type="text/css">
.memberRank label, .productCategory label, .brand label, .coupon label {
	min-width: 120px;
	_width: 120px;
	display: block;
	float: left;
	padding-right: 4px;
	_white-space: nowrap;    
}    
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $productTable = $("#productTable");
	var $productSelect = $("#productSelect");
	var $deleteProduct = $("a.deleteProduct");
	var $productTitle = $("#productTitle");
	var $giftTable = $("#giftTable");
	var $giftSelect = $("#giftSelect");
	var $deleteGift = $("a.deleteGift");
	var $giftTitle = $("#giftTitle");
	var productIds = new Array();
	var giftIds = new Array();
	var giftItemIndex = 0;
	var productImageIndex = 0;
	var $productImageTable = $("#productImageTable");
	var $addProductImage = $("#addProductImage");
	var $deleteProductImage = $("a.deleteProductImage");
	
	[@flash_message /]
	
	//地区选择
	$experienceId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
	   

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			mapx: { 
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			},mapy: {
				min: 0,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			}		  
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 添加申请
	</div>
	<form id="inputForm" action="save.jhtml" method="post" enctype="multipart/form-data">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="${message("admin.product.base")}" />
			</li>
			
		</ul>
		<table class="input tabContent">
			<tr>
					<th>
						<span class="requiredField">*</span>申请人名称:
					</th>
					<td colspan="2">
						<input type="text" name="userName" class="text" maxlength="200" />
					</td>
				</tr>
				<tr>
					<th>
						<span class="requiredField"></span>申请时间:
					</th>
					<td colspan="2">	
						<input type="text" id="experienceDate" name="experienceDate" class="text" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
					</td>
				</tr>
				
				<tr>
					<th>
						<span class="requiredField"></span>电话:
					</th>
					<td colspan="2">
						<input type="text" name="tel" class="text" maxlength="200" />
					</td>
				</tr>
		
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>申请状态：
					</th>
				<td>
					<td colspan="2">
						
						<select  name="experienceStatus" >
                        <option value="waiting">待体验</option>
                        <option value="closed">已体验</option>
                        </select>
					</td>
					
				</td>
				</tr> 
				<tr>
					<th>
						<span class="requiredField"></span>申请内容：
					</th>
					<td colspan="2">
						<input type="text" name="experienceType" class="text" maxlength="200" />
					</td>
				</tr>      
				<tr>
					<th>
						其他要求:
					</th>
					<td colspan="2">
					    <textarea name="note" class="text">
					   </textarea>
					   <input type ="hidden" name ="entcode" value = "qinyuan" />
					</td>
				</tr>
		</table>
		   
		<table class="input">
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>