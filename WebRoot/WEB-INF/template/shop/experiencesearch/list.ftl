<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>体验店查询</title>
<link href="${base}/resources/shop/css/tiyanchaxun.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/pcasunzip.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/cookie.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ww2D6765Sit7sImQ6sLbIhyU"></script>
</head>
<script type="text/javascript">
  $().ready(function() {

	var $search = $("#search");
	var $inputForm1=$("#inputForm1");
	$search.click(function(){
		$("#inputForm").submit();
	});
	
	 var $submit = $("#submit");
		$submit.click(function() {
		
				$.ajax({
					url: "${base}/experience_apply/save.jhtml",
					type: "POST",
					data: $inputForm1.serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						if (message.type == "success") {
							$.message(message);
							alert("预约成功！");
							no3();
						} else {
							
						}
					},
				});
			});
	var $scrollable = $("#scrollable");
	// 商品缩略图滚动
	$scrollable.scrollable({
	items:"#items",
	loop:true,//设置是否自动跳转（根据间隔时间）
	interval: 1000,//设置间歇时间间隔
	speed:1000,
	}); 
	
    var obj = $scrollable.scrollable();
    $("#next").click(function(){
    	obj.next() ;
    
    });
    $("#prev").click(function(){
    	obj.prev() ;
    	
    });
  	

	$.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js',function(){
	    
	    if(getCookie("cook_city")==null){
	    	$("#aaacity")[0].innerHTML= remote_ip_info.city;
	    	$("#local").val(remote_ip_info.city);
	    }else{
	    	$("#aaacity")[0].innerHTML= getCookie("cook_city");
	    	$("#local").val(getCookie("cook_city"));
	    	
	    }
		
	});

	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point();
	map.centerAndZoom(point,12);
	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();
	[#if experiences?has_content]
	[#list experiences as experience]
	myGeo.getPoint("${experience.areaName}${experience.address}", function(point){
	  if (point) {
	    map.centerAndZoom(point, 13);
		var marker = new BMap.Marker(point);
		//标注提示文本
	   var label = new BMap.Label("${experience.name}",{"offset":new BMap.Size(20,-20)});       
	   marker.setLabel(label); //添加提示文本  
	    map.addOverlay(marker);
		marker.addEventListener('click', function(){
			$("#result").hide();
			var content = "<p style='width:280px;margin:0;line-height:30px;font-size:12px'>"
							+"网点地址：${experience.address}<br/>"
							+"网点手机:${experience.phone}<br/>";
				var infoWindow1 = new BMap.InfoWindow(content);	
				this.openInfoWindow(infoWindow1);
				
				var cnm="<div class=\"eweq\">" +
						"            <div class=\"lpo\" style=\"width: 70px;float: left; margin-left:8px;\"><a href=\"${base}/experience/content/${experience.id}.jhtml\"><img src=\"${experience.image}\" width=\"60px\";height=\"85px\"></a></div>" + 
						"            <div class=\"lou\" style=\"width:145px;float:right;line-height:20px;\">" + 
						"              <div class=\"fjhw\">${experience.name}</div>" + 
						"              <div class=\"dsq\">联系电话：<span class=\"fjd\">${experience.phone}</span></div>" + 
						"              <div class=\"dsq\">${experience.address}</div>" + 
						"              <div class=\"dsq\"><input name=\"\" onclick=\"wqdu3('${experience.id}')\" type=\"button\"value=\"预约\" class=\"ds12\"></div>" + 
						"            </div>" + 
						"          </div>";
				$("#result").show("slow");
				$("#result").html("");
				$("#result").html(cnm);
			});
	  }
	}, "${experience.areaName}");
	[/#list]
	[/#if]
		
});

	
function wqdu3(id){
	  
	    $("#experienceId").attr("value",id);
		document.getElementById("kop1").style.display="block";
		document.getElementById("kop4").style.display="block";  
	}

function no3(){
		document.getElementById("kop1").style.display="none";
		document.getElementById("kop4").style.display="none";
	}
	
function sub_area(){
	
	addCookie("cook_city",$('#city').val(),null);
	$("#aaacity")[0].innerHTML= $('#city').val();
	$("#inputForm").submit();
}

function sub_province(){
	document.getElementById("city").options.add(new Option('---请选择---','---请选择---'));
}

</script>

<body>
[#include "/shop/include/header.ftl" /]
<div class="wangdian-big">
	<div class="wan-left">
		<div class="lefttop">
			<div class="left-1" style="overflow:hidden;"><span style="display:block;float:left;font-size: 14px;">当前城市：</span><span id="aaacity" style="font-size: 14px;width:100px;display:block;float:left;height:35px;" ></span></div>
			 <form id="inputForm" action="${base}/experienceSearch/list.jhtml" method="get">
			<div class="left-2">
				<div>
					<input name="local" id="local" style="display:none;"/>
					<select name="province" id="province" onchange="sub_province()" class="where-y"></select>
					&nbsp;&nbsp
					<select name="city" id="city"  onchange="sub_area()" value="" class="where-y"></select>
								
					<script type="text/javascript">
			             var province ="";
						 var city = "";
						 var area="";
						 var c_str = $.cookie('city');
			           if(c_str!=null){
				          	var c_strs = c_str.split("|");
				          	if(c_strs[0])province =  c_strs[0];
				          	if(c_strs[1])city =  c_strs[1];
				          	if(c_strs[2])area =  c_strs[2];
			           }
			            new PCAS("province","city","area",province,city,area);
			          </script>
				</div>
			</div>
			<div class="left-3">[#if experiences.size()!=0]本地共有<span>${experiences.size()}</span>家体验店[#else]周边还没有体验店！[/#if]</div>
			<div class="left-4"><input name="searchWord" type="text"  class="zhanghu"/>&nbsp;&nbsp;<span><a id="search" href="javascript:;">检索体验店</a></span></div>
		</div>
		</form>
		<div class="wep-q" id="allmap" style="height:560px">
			
		</div>
		
		
	</div>
	<div class="wan-right">
		<div class="righttop">
			<div class="righttop-a">选定体验店信息</div>
			<div class="righttop-b">
				<div style="display:none;" id="result" style="overflow:hidden;">
					<div class="fjhw"><span class="fjd">公司名称:</span><span id="com"></span></div>
					<div class="dsq"><span class="fjd">联系电话:</span><span id="tel"></span></div>
					<div class="dsq"><span class="fjd">地址:</span><span id="adr"></span></div>
				</div>
			</div>
		</div>
		<div class="rightbottom">
			<div class="rightbottom-c"><span class="shan" id="prev"><a href="javascript:;"></a></span>体验店信息列表<span class="xia" id="next"><a href="javascript:;"></a></span></div>
			<div class="rightbottom-d" id="scrollable">
				<div id="items" class="rightbottom-e">
			    [#if experiences?has_content]
				[#list experiences as experience]
				[#if experience_index%3==0]<div class="item">[/#if]
					<div class="eweq">
						<div class="lpo"><a href="${base}/experience/content/${experience.id}.jhtml"><img src="${experience.image}" width="60px";height="85px"></a></div>
						<div class="lou">
							<div class="fjhw">${experience.name}</div>
							<div class="dsq">联系电话：<span class="fjd">${experience.phone}</span></div>
							<div class="dsq">${experience.address}</div>
							<div class="dsq"><input name="" onclick="wqdu3('${experience.id}')" type="button"value="预约" class="ds12"></div>
						</div>
					</div>
				[#if (experience_index+1)%3==0]</div>[/#if]
				[/#list]
				[#if experiences.size()%3!=0]</div>[/#if]
			    [/#if]
			   </div>
			</div>
		</div>
	</div>
</div>

<div style="display:none;"id="kop1"></div>                                          
<div class="two-call" style="display:none;" id="kop4">   
	<div class="two-yuyue-a">
		<div class="yuyue-a-1">服务预约</div>
		<div class="yuyue-a-2" onclick="no3()">关闭</div>
	</div>  

	<form id="inputForm1"  method="post">
	<input value="waiting" type="hidden" name="experienceStatus">
	<input  id="experienceId" type="hidden" name="experienceId">
	<div class="der">
		<table>      
			<tr>
				<td align="right">姓名：</td>   
				<td colspan="2"><input value="" type="text" name="userName" class="zhanghu2"  style="width:165px; height:30px; border:1px solid #999;">
				</td>
				<td align="right">手机号：</td>
				<td colspan="2"><input value="" type="text"  name="tel" class="zhanghu2"  style=" height:30px; border:1px solid #999;"></td>
			</tr>
			<tr style="height:70px">
				<td align="right" style="width:165px;">体验内容：</td>
				<td colspan="2"><select name="experienceType"   class="zhanghu2" style="width:165px; height:30px; border:1px solid #999;">
                     <option value="免费上门领取PP棉">免费上门领取PP棉</option>
                     <option value="上门检测水质">上门检测水质</option>
                     <option value="免费定制水家装方案">免费定制水家装方案</option>
                     <option value="上门体验净水产品">上门体验净水产品</option>
                     <option value="参加本期专卖店活动">参加本期专卖店活动</option>
                     <option value="其他">其他</option>
                     </select></td>
				<td align="right" style="width:115px;">体验日期：</td>
				<td colspan="2"><input type="text" style="border:1px;border: 1px solid #A8A8A8;height: 30px;line-height: 30px;" id="experienceDate" name="experienceDate" class="text" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" /></td>
			</tr>  
			      
			<tr style="height:70px; margin-top:30px;">
				<td align="right">其他要求：</td>
				<td colspan="4" class="jdfhs" style="width:300px; height:80px; border:1px solid #999;"><textarea name="note"></textarea></td>  
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