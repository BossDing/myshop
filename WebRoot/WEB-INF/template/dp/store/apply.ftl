<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[#if productCategory??]
	[@seo type = "productList"]
		<title>[#if productCategory.seoTitle??]${productCategory.seoTitle}[#elseif seo.title??][@seo.title?interpret /][/#if]]</title>
		[#if productCategory.seoKeywords??]
			<meta name="keywords" content="${productCategory.seoKeywords}" />
		[#elseif seo.keywords??]
			<meta name="keywords" content="[@seo.keywords?interpret /]" />
		[/#if]
		[#if productCategory.seoDescription??]
			<meta name="description" content="${productCategory.seoDescription}" />
		[#elseif seo.description??]
			<meta name="description" content="[@seo.description?interpret /]" />
		[/#if]
	[/@seo]
[#else]
	<title>我要开店</title>
[/#if]
<link href="${base}/resources/dp/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/dp/css/storeApply.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/dp/js/uploadImage.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=zxYrDDGlL4mpRA88MkizGsQP"></script>
<script type="text/javascript">
    var full_address;
 $().ready(function() {
    [@flash_message /]
    
	var $areaId = $("#areaId");
	var $inputForm = $("#inputForm");
    var $submit = $("#submit");
    var $browserButton = $("#browserButton");
    var $browserButton2 = $("#browserButton2");
    var $browserButton3 = $("#browserButton3");
    var $openTime = $("#openTime");
    //地区选择
	$areaId.lSelect({
		url: "${base}/common/area.jhtml"    
	});
    
    $browserButton.browser();
    $browserButton2.browser();
    $browserButton3.browser();
    
    
    // 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
    		applyMan: "required",
    		serviceTelephone: "required",
    		contactTelephone: "required",
			areaId: "required",
    		openTime: {
    			pattern: /^(([01]?\d{1}|2[0-3]):[0-5]?\d{1})-(([01]?\d{1}|2[0-3]):[0-5]?\d{1})$/
    		},
    		email: {
    			email: true,
    			required: true
    		},
            qq: {
            	pattern: /^\d{4,15}$/
            }
		}
	});
      
});

//设置经纬度
	function get_latitude_longitude(all_address){
		
		// 创建地址解析器实例
		var myGeo = new BMap.Geocoder();
		// 将地址解析结果显示在地图上,并调整地图视野
		myGeo.getPoint(all_address, function(point){
			if (point) {
				var orderlng = point.lng;
				var orderlat = point.lat;
	          //将经纬度设置到隐藏域上
	         $("#orderlng").val(orderlng);
	         $("#orderlat").val(orderlat);
			}
		}, "");
	}
	
	//查询出店铺地址
	function find_adress(){
		$.post(
			"/area/searchArea.jhtml",
			{"areaId":$("#areaId").val()},
			function(data){
				if(data != null){
					//查询到地址
					full_address = data.fullName;
					//获取街道地址
					var address = $("#address").val();
					
					var all_address = full_address+address;
					
					get_latitude_longitude(all_address);
				}
			},
			"json"
		);
	}
	
</script>
</head>      
<body>			
	[#include "/shop/include/header.ftl" /]
<div class="xinxi-big">
	<div class="xinxi-a">客户开店申请表</div>
    <form id="inputForm" action="submit.jhtml" method="post">
		<div class="xinxi-b">
			<div class="op">申请人信息</div>
			<table cellpadding="0";cellspacing="0" class="table1">
				<tr>
					<td align="right" class="td1"><span class="requiredField">*</span>联系人：</td>
					<td align="left" class="td2"><input type="text" id="applyMan" name="applyMan" class="td-11" /></td>
					<td align="right" class="td1"><span class="requiredField">*</span>联系电话：</td>
					<td align="left" class="td2"><input type="text" id="contactTelephone" name="contactTelephone" class="td-11"/></td>
				</tr>
				<tr>
					<td align="right" class="td1"><span class="requiredField">*</span>邮件地址：</td>
					<td align="left" class="td2"><input type="email" id="email" name="email" class="td-11"/></td>
                    <td align="right" class="td1">QQ：</td>
					<td align="left" class="td2"><input type="text" id="qq" name="qq" class="td-11"/></td>
				</tr>
			</table>
		</div>
		<div class="xinxi-b">
			<div class="op">申请店铺信息</div>
			<div>
			<table cellpadding="0" cellspacing="0" class="table1">
				<tr>
					<td align="right" class="td1"><span class="requiredField">*</span>店铺名称：</td>
					<td align="left" class="td2"><input type="text" id="name" name="name" class="td-11"/></td>
					<td align="right" class="td1"><span class="requiredField">*</span>服务电话：</td>
					<td align="left" class="td2"><input type="text" id="serviceTelephone" name="serviceTelephone" class="td-11"/></td>
				</tr>
				<tr>
                    <td align="right" class="td1">邮编：&nbsp;</td>
					<td align="left" class="td2"><input type="text" id="zipCode" name="zipCode" class="td-11"/></td>
					<td align="right" class="td1">营业时间：</td>
					<td colspan="3" class="td2">
                        <input type="text" title="格式：小时:分钟-小时:分钟" placeholder="HH:ss-HH:ss" id="openTime" name="openTime" class="td-11"/>
                    </td>
				</tr>
                <tr>
					<td align="right" class="td1">
						<span class="requiredField">*</span>所在地区：
					</td>
					<td colspan="3">
						<span class="fieldSet">
							<input type="hidden" id="areaId" name="areaId" value="${(store.area.id)!}"  treePath="${(store.area.treePath)!}" />
							<input type="hidden" id="orderlng" name="mapy"  />
							<input type="hidden" id="orderlat" name="mapx"  />
						</span>
					</td>
				</tr>
				<tr>
					<td align="right" class="td1">镇/街道办：&nbsp;</td>
					<td colspan="3" class="td3"><textarea title="详细地址" id="address" name="address" onblur="find_adress();"></textarea></td>
				</tr>
				<tr>
					<td align="right" class="td1" style="vertical-align: top;">店铺简介：</td>
					<td colspan=3 class="td4"><textarea title="店铺介绍" id="introduction" name="introduction"></textarea></td>
				</tr>
				<tr>
					<td align="right" class="td1" style="vertical-align: top; padding-top:20px;">店铺照片：</td>
					<td colspan="3" class="td4">
                        <input type="hidden" name="fileType" value="image"/><!--上传文件类型-->
                        <ul>
							<li>
                                <input type="hidden" name="storeImage" maxlength="200" />
                                <span id="browserButton">
                                    <img src="/resources/shop/images/weixiu-2.png">
                                </span>
                            </li>
							<li>
                                <input type="hidden" name="storeImage2" maxlength="200" />
                                <span id="browserButton2">
                                    <img src="/resources/shop/images/weixiu-2.png">
                                </span>
                            </li>
							<li>
                                <input type="hidden" name="storeImage3" maxlength="200" />
                                <span id="browserButton3">
                                    <img src="/resources/shop/images/weixiu-2.png">
                                </span>
                            </li>
						</ul>
                    </td>
				</tr>
				<tr>
					<td align="left" class="td1" style="vertical-align: top;"></td>
					<td colspan="3" class="td4"><input type="submit" id="submit" class="tijiao2" value="提交申请" /></td>
				</tr>
			</table>
			<div class="new-b"></div>
		</div>
    </form>
</div>
</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>