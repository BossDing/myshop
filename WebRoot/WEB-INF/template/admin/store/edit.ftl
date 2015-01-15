<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>编辑店铺</title>
		<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
		<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
		<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
		<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
		<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
		<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
		<script type="text/javascript">
			$().ready(function() {
			
				var $inputForm = $("#inputForm");
			    var $browserButton = $("#browserButton");
			    var $browserButton2 = $("#browserButton2");
			    var $browserButton3 = $("#browserButton3");
	            var $browserButton4 = $("#browserButton4");
	            var $browserButton5 = $("#browserButton5");
			    var $areaId = $("#areaId");
				
				[@flash_message /]
				
			    //地区选择
				$areaId.lSelect({
					url: "${base}/admin/common/area.jhtml"
				});
			    
			    $browserButton.browser();
			    $browserButton2.browser();
			    $browserButton3.browser();
                $browserButton4.browser();
                $browserButton5.browser();
			    
				// 表单验证
				$inputForm.validate({
					rules: {
						name: "required",
						order: "digits",
			    		applyMan: "required",
			    		contactTelephone: "required",
			    		serviceTelephone: "required",
						areaId: "required",
			    		openTime: {
			    			pattern: /^(([01]?\d{1}|2[0-3]):[0-5]?\d{1})-(([01]?\d{1}|2[0-3]):[0-5]?\d{1})$/
			    		},
			    		email: {
			    			email: true,
			    			required: true
			    		},
						mapx: { 
							min: 0,
							decimal: {
								integer: 12,
								fraction: 6
							}
						},
			    		mapy: {
							min: 0,
							decimal: {
								integer: 12,
								fraction: 6
							}
						},
			    		busline: {
			    			pattern: /^.*$/
			    		}		
					}
				});
				
			});
		</script>
	</head>
	<body>
		<div class="path">
			<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 编辑店铺
		</div>
		<form id="inputForm" action="update.jhtml" method="post">
	        <input type="hidden" name="id" value="${store.id}">
			<table class="input">
				<tr>
					<th>
						<span class="requiredField">*</span>名称:
					</th>
					<td width="360">
						<input type="text" name="name" class="text" maxlength="200" value="${store.name}" readonly="readonly"/>
					</td>
	                <th>
						<span class="requiredField"></span>公司名称
					</th>
					<td >
						<input type="text" name="companyName" class="text" maxlength="200" value="${store.companyName}"/>
					</td>
				</tr>
	            <tr>
					<th>
						<span class="requiredField">*</span>联系人:
					</th>
					<td>
						<input type="text" title="开店申请人信息" name="applyMan" class="text" value="${store.applyMan}" />
					</td>
					<th>
						<span class="requiredField">*</span>联系电话:
					</th>
					<td >
						<input type="text" title="开店申请人信息" name="contactTelephone" class="text" value="${store.contactTelephone}" />
					</td>
				</tr>
	            <tr>
					<th>
						<span class="requiredField"></span>QQ:
					</th>
					<td>
						<input type="text" title="开店申请人信息" name="qq" class="text" value="${store.qq}" />
					</td>
					<th>
						<span class="requiredField">*</span>电子邮箱:
					</th>
					<td >
						<input type="email" name="email" class="text" title="开店申请人信息" value="${store.email}" />
					</td>
				</tr>
	            <tr>
					<th>
						<span class="requiredField"></span>营业时间:
					</th>
					<td>
						<input type="text" title="格式：小时:分钟-小时:分钟" name="openTime" class="text" value="${store.openTime}" />
					</td>
					<th>
						<span class="requiredField">*</span>服务热线:
					</th>
					<td >
						<input type="text" name="serviceTelephone" class="text" maxlength="200" value="${store.serviceTelephone}" />
					</td>
				</tr>
	            <tr>
					<th>
						<span class="requiredField"></span>邮政编码:
					</th>
					<td>
						<input type="text" name="zipCode" class="text" value="${store.zipCode}" />
					</td>
                    [#if isStore == null]
						<th>
							设置:
						</th>
						<td >
		                    [#if store.checkStatus != "wait" && store.checkStatus != "failure"]
							<label>
								<input type="checkbox" name="isEnabled" value="true" [#if store.isEnabled == true]checked="checked"[/#if] />是否启用
								<input type="hidden" name="_isEnabled" value="false" />
							</label>
		                    [/#if]
							<label>
								<input type="checkbox" name="isMainStore" value="true" [#if store.isMainStore == true]checked="checked"[/#if]/>是否为主店铺
								<input type="hidden" name="_isMainStore" value="false" />
							</label>
						</td>
                    [#else]
	                    [#if store.checkStatus != "wait" && store.checkStatus != "failure"]
						<input type="hidden" name="isEnabled" value="${store.isEnabled}" />
	                    [/#if]
						<input type="hidden" name="isMainStore" value="${store.isMainStore}"/>
                    [/#if]
				</tr>
				<tr>
					<th>
						<span class="requiredField">*</span>所在地区
					</th>
					<td>
						<span class="fieldSet">
							<input type="hidden" id="areaId" name="areaId" value="${(store.area.id)!}"  treePath="${(store.area.treePath)!}" />
						</span>
					</td>
	                <th>
						<span class="requiredField"></span>详细地址
					</th>
					<td >
						<input type="text" name="address" class="text" maxlength="200" value="${store.address}"/>
					</td>
				</tr>
				<tr>
					<!--<th>
						<span class="requiredField"></span>url:
					</th>
					<td >
						<input type="text" name="url" class="text" maxlength="200" value="${store.url}" />
					</td>-->
	                <th>
						<span class="requiredField"></span>店铺首页url
					</th>
					<td>
						<input type="text" name="indexUrl" class="text" maxlength="200" value="${store.indexUrl}" readonly="readonly"/>
					</td>
                    <th>
						<span class="requiredField"></span>移动店铺首页url
					</th>
					<td>
						<input type="text" name="indexMobileUrl" class="text" maxlength="200" value="${store.indexMobileUrl}" readonly="readonly"/>
					</td>
				</tr>     
				<tr>
	                <th>
						经度:
					</th>
					<td>
						<input type="text" name="mapy" class="text" title="可通过百度地图坐标拾取获得" maxlength="16" value="${store.mapy}"/>
					</td>
					<th>  
						纬度:
					</th>
					<td >
						<input type="text" name="mapx" class="text" title="可通过百度地图坐标拾取获得" maxlength="16" value="${store.mapx}"/>
					</td>
				</tr>
				<tr>
					<th>
						店铺照片1:
					</th>
					<td>
						<span class="fieldSet">
							<input type="text" name="storeImage" class="text" value="${store.storeImage}" maxlength="200" title="${message("admin.product.imageTitle")}" />
							<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
							[#if store.storeImage??]
								<a href="${store.storeImage}" target="_blank">${message("admin.common.view")}</a>
							[/#if]
						</span>
					</td>
					<th>
						店铺照片2:
					</th>
					<td colspan="5">
						<span class="fieldSet">
							<input type="text" name="storeImage2" class="text" value="${store.storeImage2}" maxlength="200"/>
							<input type="button" id="browserButton2" class="button" value="${message("admin.browser.select")}" />
							[#if store.storeImage2??]
								<a href="${store.storeImage2}" target="_blank">${message("admin.common.view")}</a>
							[/#if]
						</span>
					</td>
				</tr>
	            <tr>
					<th>
						店铺照片3:
					</th>
					<td colspan="3">
						<span class="fieldSet">
							<input type="text" name="storeImage3" class="text" value="${store.storeImage3}" maxlength="200" />
							<input type="button" id="browserButton3" class="button" value="${message("admin.browser.select")}" />
							[#if store.storeImage3??]
								<a href="${store.storeImage3}" target="_blank">${message("admin.common.view")}</a>
							[/#if]
						</span>
					</td>
				</tr>
                
                <tr>
					<th>
						正品保障:
					</th>
					<td>
						<span class="fieldSet">
							<input type="text" name="isAGImage" class="text" value="${store.isAGImage}" maxlength="200" title="店铺信息下图片1 75 x 35" />
							<input type="button" id="browserButton4" class="button" value="${message("admin.browser.select")}" />
							[#if store.isAGImage??]
								<a href="${store.isAGImage}" target="_blank">${message("admin.common.view")}</a>
							[/#if]
						</span>
					</td>
					<th>
						假一赔三:
					</th>
					<td colspan="5">
						<span class="fieldSet">
							<input type="text" name="isCSImage" class="text" value="${store.isCSImage}" maxlength="200" title="店铺信息下图片2 75 x 35" />
							<input type="button" id="browserButton5" class="button" value="${message("admin.browser.select")}" />
							[#if store.isCSImage??]
								<a href="${store.isCSImage}" target="_blank">${message("admin.common.view")}</a>
							[/#if]
						</span>
					</td>
				</tr>
                
	            <tr>
					<th>
						公交线路:
					</th>
					<td >
					    <textarea name="busline" class="text" title="注意：只能依次输入，不可换行">${store.busline}</textarea>
					</td>
					<th>
						店铺介绍:  
					</th>
					<td >
					    <textarea name="introduction" class="text">${store.introduction}</textarea>
					</td>
				</tr>
				
				<tr>
					<th>
						&nbsp;
					</th>
					<td colspan="3">
	                    [#--[#if store??] --][#--非店铺管理员不能编辑店铺的商品--]
						<input type="submit" class="button" value="${message("admin.common.submit")}" />
	                    [#--
	                    [#else]
	                    <input type="button" class="button" disabled value="${message("admin.common.submit")}" />
	                    [/#if]
	                    --]
						<input type="button" class="button" value="${message("admin.common.back")}" onclick="history.go(-1);" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>