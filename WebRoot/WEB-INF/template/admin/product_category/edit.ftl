<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.productCategory.edit")} - Powered By SHOP++</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<style type="text/css">
.brands label {
	width: 150px;
	display: block;
	float: left;
	padding-right: 6px;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $browserButton = $("#browserButton");
	
	[@flash_message /]
	$browserButton.browser();

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			order: "digits"
		}
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.productCategory.edit")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${productCategory.id}" />
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>${message("ProductCategory.name")}:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text" value="${productCategory.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("ProductCategory.parent")}:
				</th>
				<td>
					<select name="parentId">
						[#if store == null && productCategory.store == null]<option value="">${message("admin.productCategory.root")}</option>[/#if]
						[#list productCategoryTree as category]
							[#if category != productCategory && !children?seq_contains(category)]
                        	[#--
                        		非店铺管理员编辑时：
                        		商城分类--编辑--最终其上一级分类必须是商城分类
                        		店铺分类--编辑--最终其上一级分类必须是商城的分类或对应店铺的分类
                        	--]
	                        	[#if store == null && productCategory.store != null][#--店铺分类--]
		                        	[#if category.store != null && productCategory.store.id ==  category.store.id][#--该分类属于对应店铺的分类--]
										<option value="${category.id}"[#if category == productCategory.parent] selected="selected"[/#if]>
											[#if category.grade != 0]
												[#list 1..category.grade as i]
													&nbsp;&nbsp;
												[/#list]
											[/#if]
											${category.name}
										</option>
                                    [#elseif  category.store == null][#--商城分类--]
                                    	<option value="${category.id}"[#if category == productCategory.parent] selected="selected"[/#if]>
											[#if category.grade != 0]
												[#list 1..category.grade as i]
													&nbsp;&nbsp;
												[/#list]
											[/#if]
											${category.name}
										</option>
		                            [/#if]
	                            [#elseif store == null && productCategory.store == null && category.store == null][#--商城分类--]
	                           	 	<option value="${category.id}"[#if category == productCategory.parent] selected="selected"[/#if]>
										[#if category.grade != 0]
											[#list 1..category.grade as i]
												&nbsp;&nbsp;
											[/#list]
										[/#if]
										${category.name}
									</option>
	                            [#elseif store != null] [#--店铺管理员操作时，不作处理（后台Controller已处理）--]
	                           		<option value="${category.id}"[#if category == productCategory.parent] selected="selected"[/#if]>
										[#if category.grade != 0]
											[#list 1..category.grade as i]
												&nbsp;&nbsp;
											[/#list]
										[/#if]
										${category.name}
									</option>
	                            [/#if]
							[/#if]
						[/#list]
					</select>
				</td>
			</tr>
			<tr>
				<th>
					分类图片:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="image" class="text" value="${productCategory.image}" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
						[#if productCategory.image??]
							<a href="${productCategory.image}" target="_blank">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
			<tr class="brands">
				<th>
					${message("ProductCategory.brands")}:
				</th>
				<td>
					[#list brands as brand]
						<label>
							<input type="checkbox" name="brandIds" value="${brand.id}"[#if productCategory.brands?seq_contains(brand)] checked="checked"[/#if] />${brand.name}
						</label>
					[/#list]
				</td>
			</tr>
			<tr>
				<th>
					${message("ProductCategory.seoTitle")}:
				</th>
				<td>
					<input type="text" name="seoTitle" class="text" value="${productCategory.seoTitle}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("ProductCategory.seoKeywords")}:
				</th>
				<td>
					<input type="text" name="seoKeywords" class="text" value="${productCategory.seoKeywords}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("ProductCategory.seoDescription")}:
				</th>
				<td>
					<input type="text" name="seoDescription" class="text" value="${productCategory.seoDescription}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("admin.common.order")}:
				</th>
				<td>
					<input type="text" name="order" class="text" value="${productCategory.order}" maxlength="9" />
				</td>
			</tr>
			<tr>
				<th>
					设置:
				</th>
				<td>
					<input type="checkbox" name="isShowProduct" value="true"[#if productCategory.isShowProduct] checked="checked"[/#if]  />是否显示产品
					<input type="hidden" name="_isShowProduct" value="false" />
					<input type="checkbox" name="isRepair" value="true"[#if productCategory.isRepair] checked="checked"[/#if]  />维修预约
					<input type="hidden" name="_isRepair" value="false" />
					<input type="checkbox" name="isInstall" value="true"[#if productCategory.isInstall] checked="checked"[/#if]  />安装预约
					<input type="hidden" name="_isInstall" value="false" />
					<input type="checkbox" name="isInstruction" value="true"[#if productCategory.isInstruction] checked="checked"[/#if]  />产品说明书
					<input type="hidden" name="_isInstruction" value="false" />
				</td>
			</tr>
            <tr>
	         	<th>
					所属店铺:
				</th>
				<td>
	                [#if productCategory.store??]${productCategory.store.name}[#else] - [/#if]
				</td>    
        	</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>[#--非店铺管理员不能编辑店铺的商品--]
					[#if store == null && productCategory.store == null]
                		<input type="submit" class="button" value="${message("admin.common.submit")}" />
                    [#elseif store??]
                    	<input type="submit" class="button" value="${message("admin.common.submit")}" />
                    [#else]
                    <input type="button" class="button" disabled value="${message("admin.common.submit")}" />
                    [/#if]
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>