<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加店铺</title>
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
    var $areaId = $("#areaId");
    var $browserButton = $("#browserButton");
    var $browserButton2 = $("#browserButton2");
    var $browserButton3 = $("#browserButton3");
	
	[@flash_message /]
    
     //地区选择
	$areaId.lSelect({
		url: "${base}/admin/common/area.jhtml"
	});
    
    $browserButton.browser();
    $browserButton2.browser();
    $browserButton3.browser();
    
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
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 添加店铺
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>名称:
				</th>
				<td  width="360">
					<input type="text" name="name" class="text" maxlength="200" />
				</td>
                <th>
					<span class="requiredField"></span>公司名称
				</th>
				<td >
					<input type="text" name="companyName" class="text" maxlength="200"/>
				</td>
			</tr>
            <tr>
				<th>
					<span class="requiredField">*</span>联系人:
				</th>
				<td>
					<input type="text" title="开店申请人信息" name="applyMan" class="text" />
				</td>
				<th>
					<span class="requiredField">*</span>联系电话:
				</th>
				<td >
					<input type="text" title="开店申请人信息" name="contactTelephone" class="text" />
				</td>
			</tr>
            <tr>
				<th>
					<span class="requiredField"></span>QQ:
				</th>
				<td>
					<input type="text" title="开店申请人信息" name="qq" class="text" />
				</td>
				<th>
					<span class="requiredField">*</span>电子邮箱:
				</th>
				<td >
					<input type="email" name="email" class="text" title="开店申请人信息" />
				</td>
			</tr>
            <tr>
				<th>
					<span class="requiredField"></span>营业时间:
				</th>
				<td>
					<input type="text" title="格式：小时:分钟-小时:分钟" name="openTime" class="text" />
				</td>
				<th>
					<span class="requiredField">*</span>服务热线:
				</th>
				<td >
					<input type="text" name="serviceTelephone" class="text" maxlength="200" />
				</td>
			</tr>
            <tr>
				<th>
					<span class="requiredField"></span>邮政编码:
				</th>
				<td >
					<input type="text" name="zipCode" class="text" />
				</td>
				<th>
					设置:
				</th>
				<td >
					<!--<label>
						<input type="checkbox" name="isEnabled" value="true" checked="checked" />是否启用
						<input type="hidden" name="_isEnabled" value="false" />
					</label>-->
                    <input type="hidden" name="isEnabled" value="false"/><!--添加店铺默认不启用，以可以通过审核，生成帐号-->
					<label>
						<input type="checkbox" name="isMainStore" value="true" />是否为主店铺
						<input type="hidden" name="_isMainStore" value="false" />
					</label>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>所在地区
				</th>
				<td >
					<span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId" />
					</span>
				</td>
                <th>
					<span class="requiredField"></span>详细地址
				</th>
				<td >
					<input type="text" name="address" class="text" maxlength="200"/>
				</td>
			</tr>
			<tr>
                <th>
					<span class="requiredField"></span>url:
				</th>
				<td >
					<input type="text" name="url" class="text" maxlength="200" />
				</td>
				<th>
					<span class="requiredField"></span>店铺首页url
				</th>
				<td>
					<input type="text" name="indexUrl" class="text" maxlength="200"/>
				</td>
			</tr>     
			<tr>
                <th>
					经度:
				</th>
				<td>
					<input type="text"  name="mapy" class="text" title="可通过百度地图坐标拾取获得" maxlength="16"/>
				</td>
				<th>  
					维度:
				</th>
				<td >
					<input type="text"  name="mapx" class="text" title="可通过百度地图坐标拾取获得" maxlength="16"/>
				</td>
			</tr>
			<tr>
				<th>
					店铺照片1:
				</th>
				<td >
					<span class="fieldSet">
						<input type="text" name="storeImage" class="text" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
					</span>
				</td>
				<th>
					店铺照片2:
				</th>
				<td >
					<span class="fieldSet">
						<input type="text" name="storeImage2" class="text" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton2" class="button" value="${message("admin.browser.select")}" />
					</span>
				</td>
			</tr>
            <tr>
				<th>
					店铺照片3:
				</th>
				<td colspan="3">
					<span class="fieldSet">
						<input type="text" name="storeImage3" class="text" maxlength="200" title="${message("admin.product.imageTitle")}" />
						<input type="button" id="browserButton3" class="button" value="${message("admin.browser.select")}" />
					</span>
				</td>
			</tr>
            <tr>
				<th>
					公交线路:
				</th>
				<td >
				    <textarea name="busline" class="text"></textarea>
				</td>
				<th>
					店铺介绍:  
				</th>
				<td >
				    <textarea name="introduction" class="text"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td  colspan="3">
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="history.go(-1);" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>