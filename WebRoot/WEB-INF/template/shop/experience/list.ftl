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
	<title>体验中心</title>
[/#if]
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/tiyan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {
    var $productForm = $("#productForm");  
	var $brandId = $("#brandId");
	var $promotionId = $("#promotionId");
	var $orderType = $("#orderType");
	var $pageNumber = $("#pageNumber");  
	var $pageSize = $("#pageSize");
	var $filter = $("#filter dl");
	var $lastFilter = $("#filter dl:eq(2)");
	var $hiddenFilter = $("#filter dl:gt(2)");
	var $moreOption = $("#filter dd.moreOption");
	var $moreFilter = $("#moreFilter a");
	var $tableType = $("#tableType");
	var $listType = $("#listType");
	var $orderSelect = $("#orderSelect");
	var $brand = $("#filter a.brand");
	var $attribute = $("#filter a.attribute");
	var $previousPage = $("#previousPage");     
	var $nextPage = $("#nextPage");
	var $size = $("#layout a.size");
	var $tagIds = $("input[name='tagIds']");
	var $sort = $("#sort a");
	var $startPrice = $("#startPrice");
	var $endPrice = $("#endPrice");
	var $result = $("#result");
	var $productImage = $("#result img");
	var $areaId = $("#areaId");
	var $inputForm = $("#inputForm");
    var $submit = $("#submit");
    //地区选择
	$areaId.lSelect({
		url: "${base}/common/area.jhtml"    
	});  
      
    $size.click(function() {
		var $this = $(this);
		$pageNumber.val(1);
		$pageSize.val($this.attr("pageSize"));
		$productForm.submit();
		return false;
	});     	
	// 预约订单提交
	$submit.click(function() {
		$.ajax({
			url: "${base}/experience_apply/save.jhtml",
			type: "POST",
			data: $inputForm.serialize(),
			dataType: "json",
			cache: false,
			success: function(message) {
				if (message.type == "success") {				
					$.message(message);
					window.location.href = "${base}/experience/list.jhtml";
				} else {
					
				}
			}  
		});
	});
	  
	$previousPage.click(function() {
		$pageNumber.val(${page.pageNumber - 1});
		$productForm.submit();
		return false;
	});
	
	$nextPage.click(function() {
		$pageNumber.val(${page.pageNumber + 1});
		$productForm.submit();
		return false;
	});
	$productForm.submit(function() {
		
	});
	$.pageSkip = function(pageNumber) {
		$pageNumber.val(pageNumber);
		$productForm.submit();
		return false;
	}
    [#if provinceId??]    
		$('#select_province').attr('value','${provinceId}'); 
		$.ajax({
				url: "${base}/area/listbyparent.jhtml?areaId=${provinceId}",  
				type: "POST",
				dataType: "json",    
				cache: false,    
				success: function(data) {
					for(var i=0;i<data.length;i++){ 
						$("#select_city").append("<option value="+data[i].id+">"+data[i].name+"</option>");  
					}    
					$('#select_city').attr('value','${areaId_new}');  				
				}  
		}); 
 	[/#if]
});
    function wqdu1(){
		document.getElementById("kop1").style.display="block";
		document.getElementById("kop2").style.display="block";
	}
	function no1(){
		document.getElementById("kop1").style.display="none";
		document.getElementById("kop2").style.display="none";
	}
	function wqdu2(id,name,opentime,phone,busline,areaName,address){ 
	    $("#dianpuming").text("");  
	    $("#dianpuming").append(name);  
	     
	    $("#dizhi1").text("");
	    $("#dizhi1").append(areaName);
	    $("#dizhi1").append(address);
	          
	    $("#fuwushijian").text("");
	    $("#fuwushijian").append(opentime);
	    
	    $("#fuwudianhua").text("");      
	    $("#fuwudianhua").append(phone);
	     
	    $("#gongjiaoxianlu").text("");
	    $("#gongjiaoxianlu").append(busline);
	    
	    $("#dianpuming2").text("");
	    $("#dianpuming2").append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+name+'&nbsp;&nbsp;<span>&lt;详情&gt;</span>');  
	     
	    $("#dizhi2").text("");
	    $("#dizhi2").append(areaName);
	    $("#dizhi2").append(address);
	    
	    $("#myiframe").attr('src','/experience/map/'+id+'.jhtml');

		document.getElementById("kop1").style.display="block";
		document.getElementById("kop3").style.display="block";
	}
	function no2(){
		document.getElementById("kop1").style.display="none";
		document.getElementById("kop3").style.display="none";  
	}
	function wqdu3(id){
	  
	    $("#experienceId").attr("value",id);
		document.getElementById("kop1").style.display="block";
		document.getElementById("kop4").style.display="block";  
	}
	function no3(){
		document.getElementById("kop1").style.display="none";
		document.getElementById("kop4").style.display="none"; 
	}
	function subimtAddress1(_this){
		$("#select_city").empty();   
		$.ajax({
			url: "${base}/area/listbyparent.jhtml?areaId="+_this.value,
			type: "POST",
			dataType: "json",  
			cache: false,  
			success: function(data) {
				for(var i=0;i<data.length;i++){ 
					$("#select_city").append("<option value="+data[i].id+">"+data[i].name+"</option>");  
				}    
				$('#select_city').attr('value',data[0].id);  				
			}  
		});  
	}  
	function subimtAddress2(_this){
		$("#areaId_new").val($("#select_city").val());
		$("#provinceId").val($("#select_province").val());
		$("#productForm").submit();    
	}	
</script>  
</head>      
<body>			
	[#include "/shop/include/header.ftl" /]  
	<div class="tiyan-big">
	<div class="tiyan-top">
				<div class="shouye"><a href="#">首页</a>&nbsp;&gt;</div>
				<div class="tiyanguan"><a href="#">体验馆</a></div>   
					</div>
					<div><a href="#">[@ad_position id = 910 /]</a></div>
					<div class="wofujin">   
						<div class="fujin">我附近的体验店</div>
						<form id="productForm" action="${base}/experience/list.jhtml"}" method="get">
				        <input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
				        <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
						<div class="dangqian">  
							<div class="where"><span>您当前位置：</span>
								  <input type="hidden" id="areaId_new" name="areaId_new" />		
								  <input type="hidden" id="provinceId" name="provinceId" />					 
								<select style="border:1px #6d6d6d solid;width:90px;height:25px;" id="select_province" onchange=subimtAddress1(this);>
							<option value="">请选择省份</option>
								<option value="1">北京市</option>
								<option value="18">天津市</option>
								<option value="35">河北省</option>
								<option value="219">山西省</option>
								<option value="351">内蒙古自治区</option>
								<option value="465">辽宁省</option>
								<option value="580">吉林省</option>
								<option value="650">黑龙江省</option>
								<option value="792">上海市</option>
								<option value="810">江苏省</option>
								<option value="926">浙江省</option>
								<option value="1028">安徽省</option>   
								<option value="1150">福建省</option>
								<option value="1245">江西省</option>
								<option value="1357">山东省</option>
								<option value="1515">河南省</option>
								<option value="1692">湖北省</option>
								<option value="1809">湖南省</option>
								<option value="1946">广东省</option>
								<option value="2089">广西壮族自治区</option>
								<option value="2213">海南省</option>
								<option value="2240">重庆市</option> 
								<option value="2279">四川省</option>
								<option value="2482">贵州省</option>
								<option value="2580">云南省</option>
								<option value="2726">西藏自治区</option>
								<option value="2807">陕西省</option>
								<option value="2925">甘肃省</option>
								<option value="3026">青海省</option>
								<option value="3078">宁夏回族自治区</option>
								<option value="3106">新疆维吾尔自治区</option>
								<option value="3219">台湾省</option>
								<option value="3292">香港特别行政区</option>
								<option value="3314">澳门特别行政区</option>
						</select>&nbsp;&nbsp;
						<select style="border:1px #6d6d6d solid;width:90px;height:25px;" id="select_city" onchange=subimtAddress2(this);>
							<option value="">请选择城市</option>
						</select>&nbsp;&nbsp;
								   
				            </div>
				            <div class="jiansou"><input value="${(searchWord)!}"  name="searchWord" type="text"  class="zhanghu"/>&nbsp;<input name="" type="submit" value="检索体验店" class="ds"><a href="#"><span>${page.total}</span>家体验店</a></div>
						</div>
						<div class="hot">  
						[#list page.content?chunk(4) as row]  
						[#list row as product]
							<div class="hot-1">
								<div class="hotname"><span>${product.name}</span></div>  
								<div class="photo" id="pic">   
							       <div id="slideBox">
										<ul id="show_pic" style="left:0px">
											<li><a href="/experience/content/${product.id}.jhtml" target="_blank"><img  alt=""  title="" height="170" width="308" src="${product.image}" /></a></li>		
										</ul>
									<div id="slideText">
							            <ul id="textBall">
											<li class="active"><a href="javascript:void(0)">${product.name}</a></li>											
										</ul>  
							        </div>    
								</div>  
								<div class="call">
									<div class="where-1"><A onclick="wqdu1()" href="javascript:void(0)"><img src="/resources/shop/images/call-1.png">&nbsp;地址免费发手机</a></div>
									<div class="where-1" onclick="wqdu2(${product.id},'${product.name}','${product.opentime}','${product.phone}','${product.busline}','${product.areaName}','${product.address}')"><a href="#"><img src="/resources/shop/images/ikons.png">查看地图</a></div>
									<div class="where-1" onclick="wqdu3(${product.id})"><a href="#"><img src="/resources/shop/images/ikons2.png">&nbsp;预约服务</a></div>
								</div>
								<div class="time">营业时间：${product.opentime}<br>服务电话：${product.phone}<br>展馆地址：${product.address}</div>
							</div>
						</div>	
						[/#list]
					    [/#list]																												
					</div>   
				</div>   
				<br>        
				<br>
				<br>   
				[@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
					[#include "/shop/include/pagination.ftl"]
				[/@pagination]
			</form>
		</div>    
	</div>   
	<div style="display:none;"id="kop1"></div>                                          
<div class="two-call" style="display:none;" id="kop2">
	<div class="two-call-a">   
		<div class="call-a-1">免费发送体验馆信息到手机</div>
		<div class="call-a-2" onclick="no1()">关闭</div>
	</div>
	<div class="dgsw">
		<table>
			<tr style="height:70px">
				<td>手机号：</td>
				<td colspan="2"><input value="" type="text"  class="zhanghu12"  style="border:1px solid #999;" /></td>
			</tr>
			<tr>
				<td>验证码：</td>
				<td><input value="" type="text" style="border:1px solid #999;"  class="zhang"/></td>
				<td class="dsq">fdjl</td>
			</tr>
			<tr style="height:70px; margin-top:30px;">
				<td></td>
				<td colspan="2"><a href="#"><input name="" type="button"value="免费发送" class="dsfli"></a></td>
					
			</tr>
		</table>
	</div>
</div>

<div class="two-call" style="display:none;" id="kop3">  
	<div class="two-where-a">
		<div class="where-a-1">查看地图</div>      
		<div class="where-a-2" onclick="no2()">关闭</div>
	</div>
	<div class="where-p">  
		<div class="where-left">
			<div class="dfsk"><span id ="dianpuming">海尔佛山华勇专卖店</span></div>
			<div class="lkj">
				<div class="kdjp1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本馆地址：</div>
				<div class="fdjs1" id = "dizhi1">佛山市南海区狮山镇小塘三环路60号</div>
			</div>
			<div class="lkj">
				<div class="kdjp2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务时间：</div>
				<div class="fdjs2" id ="fuwushijian">8:00-18:00</div>
			</div>
			<div class="lkj">
				<div class="kdjp3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务热线：</div>   
				<div class="fdjs3" id = "fuwudianhua">400-672-266</div>
			</div>
			<div class="lkj">    
				<div class="kdjp4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公交路线：</div>
				<div class="fdjs4" id = "gongjiaoxianlu">小塘医院站：286A&nbsp;南高01路</div>
			</div>  
		</div>
		<div class="wep"><iframe id="myiframe" name="myiframe" width=510 height=500 src ="/experience/map/210.jhtml">  uioiuuo </iframe></div>
		<div class="wep-right">   
			<div class="right-0" id = "dianpuming2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;海尔佛山华勇专卖店&nbsp;&nbsp;<span>&lt;详情&gt;</span></div>
			<div class="wep-1">
				<div class="wep-2">本馆地址：</div>
				<div class="wep-3" id ="dizhi2">佛山市南海区狮山镇小塘三环路60号</div>
			</div>   
		</div>	
	</div>   
</div>

<div class="two-call" style="display:none;" id="kop4">   
	<div class="two-yuyue-a">
		<div class="yuyue-a-1">服务预约</div>
		<div class="yuyue-a-2" onclick="no3()">关闭</div>
	</div>  

	<form id="inputForm"  method="post">
	<input value="waiting" type="hidden" name="experienceStatus">
	<input  id="experienceId" type="hidden" name="experienceId">
	<div class="der">    
		<table>        
			<tr>    
				<td align="right">姓名：</td>   
				<td class="lpoi"><input value="" type="text" name="userName" class="zhanghu2"  style="width:165px; height:30px; border:1px solid #999;">
				</td>
				<td align="right">手机号：</td>
				<td><input value="" type="text"  name="tel" class="zhanghu2"  style=" height:30px; border:1px solid #999; width: 159px;"></td>
			</tr>
			<tr style="height:70px">
				<td align="right">体验内容：</td>
				<td><select name="experienceType" onchange="changedata(this)"  class="zhanghu2" style="width:165px; height:30px; border:1px solid #999;">
                     <option value="免费上门领取PP棉">免费上门领取PP棉</option>
                     <option value="上门检测水质">上门检测水质</option>
                     <option value="免费定制水家装方案">免费定制水家装方案</option>
                     <option value="上门体验净水产品">上门体验净水产品</option>
                     <option value="参加本期专卖店活动">参加本期专卖店活动</option>
                     <option value="其他">其他</option>
                     </select></td>  
				<td align="right" style="width:90px;">体验日期：</td>
				<td><input type="text" style="width: 150px;"; id="experienceDate" name="experienceDate" class="text" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" /></td>
			</tr>  
			      
			<tr style="height:70px; margin-top:30px;">      
				<td align="right">其他要求：</td>   
				<td colspan="4" class="jdfhs"><textarea name="note"></textarea></td>  
			</tr>
			<tr style="height:70px; margin-top:30px;">
				<td></td>
				<td colspan="4">
				<input name="" type="button" id="submit" value="提交申请" class="dsfli2" />
				</td>
			</tr>
		</table>    
	</div>   
	</form>
</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>