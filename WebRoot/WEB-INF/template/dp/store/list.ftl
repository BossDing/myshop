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
	<title>线下实体店</title>
[/#if]
<link href="${base}/resources/dp/css/common.css" rel="stylesheet" type="text/css" />
<!--link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" /-->
<link href="${base}/resources/dp/css/storeList.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript">
function wqdu1(id){
	document.getElementById("kop1").style.display="block";
	document.getElementById("kop2").style.display="block";
    $("#smsStoreId").val(id);
}
function no1(){
	document.getElementById("kop1").style.display="none";
	document.getElementById("kop2").style.display="none";
}
function wqdu2(id,name,opentime,serviceTelephone,busline,areaName,address){ 
    $("#dianpuming").text("");  
    $("#dianpuming").append(name);  
    $("#dizhi1").text("");
    $("#dizhi1").append(areaName);
    $("#dizhi1").append(address);
    $("#fuwushijian").text("");
    $("#fuwushijian").append(opentime);
    $("#fuwudianhua").text("");      
    $("#fuwudianhua").append(serviceTelephone);
    $("#gongjiaoxianlu").text("");
    $("#gongjiaoxianlu").append(busline);
    /*$("#dianpuming2").text("");
    $("#dianpuming2").append(name+'&nbsp;<a href="/'+id+'.jhtml">&lt;详情&gt;</a>');*/  
    $("#dizhi2").text("");
    $("#dizhi2").append(areaName);
    $("#dizhi2").append(address);
    $("#myiframe").attr('src','/store/map/'+id+'.jhtml');
	document.getElementById("kop1").style.display="block";
	document.getElementById("kop3").style.display="block";
}
function no2(){
	document.getElementById("kop1").style.display="none";
	document.getElementById("kop3").style.display="none";  
}
function no3(){
	document.getElementById("kop1").style.display="none";
	document.getElementById("kop4").style.display="none"; 
}
</script>
<script type="text/javascript">
 $().ready(function() {
 /**************************banner轮播javascript 开始*********************************/    
    $("p").eq(-1).css('color', 'red');
    var nowshowpic = 0; //信号量，信号量的作用指示当前显示的图片序号。
    $("#ImageList ul li").css('opacity', '0');
    $("#ImageList ul li").eq(0).css('opacity', '1');
    $("#xyddul li").click(
        function () {
            if (!$("#zhezhao").is(":animated")) {
                $("#ImageList ul li").eq(nowshowpic).animate({ "opacity": 0 }, 1000);
                nowshowpic = $(this).index();
                $("#ImageList ul li").eq(nowshowpic).animate({ "opacity": 1 }, 1000);
                shezhixiaoyuandian(nowshowpic);
            }
        }
    );
	function huxixianshitupian(a) {
        $("#ImageList ul li").eq(a - 1).animate({ "opacity": 0 }, 1000);
        $("#ImageList ul li").eq(a).animate({ "opacity": 1 }, 1000);
    }
    function huxixianshitupian2(a) {
        if (a == $("#ImageList ul li").length - 1) {
            $("#ImageList ul li").eq(0).animate({ "opacity": 0 }, 1000);
            $("#ImageList ul li").eq(a).animate({ "opacity": 1 }, 1000);
        } else {
            $("#ImageList ul li").eq(a + 1).animate({ "opacity": 0 }, 1000);
            $("#ImageList ul li").eq(a).animate({ "opacity": 1 }, 1000);
        }
    }
    function shezhixiaoyuandian(a) {
        $("#xyddul li").eq(a).addClass("cur").siblings().removeClass("cur");
    }
    function shezhilianjie(a) {
        $("#zhezhao a").attr("href", $("#ImageList ul li a").eq(a).attr("href"));
    }
    var timer = window.setInterval(method, 2000);  /*设置自动轮播*/
    var nowshowpic2 = 0;
    function method() {
        if (!$("#ImageList ul li").is(":animated")) {
            if (nowshowpic2 == $("#ImageList ul li").length - 1) {
                nowshowpic2 = 0;
            } else {
                nowshowpic2 = nowshowpic2 + 1;
            }
            huxixianshitupian(nowshowpic2);
            shezhixiaoyuandian(nowshowpic2);
            shezhilianjie(nowshowpic2);
        }
    }
    $('#banner').mouseenter(function () {
        window.clearInterval(timer);
    });
    $('#banner').mouseleave(function () {
        timer = window.setInterval(method, 2000);
    });
/**************************banner轮播javascript 结束*********************************/    
    
    var $storeForm = $("#storeForm");  
	var $pageNumber = $("#pageNumber");  
	var $pageSize = $("#pageSize");
	var $previousPage = $("#previousPage");     
	var $nextPage = $("#nextPage");
    var $size = $("#layout a.size");
	var $serviceForm = $("#serviceForm");
    var $submit = $("#submit");
    var $cityId = $("#cityId");
    var $provinceId = $("#provinceId");
    var $select_province = $("#select_province");
    var $select_city = $("#select_city");
    var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
    var $sendSMSForm = $("#sendSMSForm");
    var $sendSMS = $("#sendSMS");
      
    $size.click(function() {
		var $this = $(this);
		$pageNumber.val(1);
		$pageSize.val($this.attr("pageSize"));
		$storeForm.submit();
		return false;
	});     	
	  
	$previousPage.click(function() {
		$pageNumber.val(${page.pageNumber - 1});
		$storeForm.submit();
		return false;
	});
	
	$nextPage.click(function() {
		$pageNumber.val(${page.pageNumber + 1});
		$storeForm.submit();
		return false;
	});
	$.pageSkip = function(pageNumber) {
		$pageNumber.val(pageNumber);
		$storeForm.submit();
		return false;
	}
    [#if provinceId??]
    	$select_province.val(${provinceId});
		$.ajax({
				url: "${base}/area/listbyparent.jhtml?areaId=${provinceId}",  
				type: "POST",
				dataType: "json",    
				cache: false,    
				success: function(data) {
					for(var i=0;i<data.length;i++){ 
						$("#select_city").append("<option value="+data[i].id+">"+data[i].name+"</option>");  
					}
                    $select_city.val(${cityId});
				}  
		}); 
 	[/#if]
    $select_province.change(function(){
		$select_city.empty();
        $cityId.val("");  
        $select_city.append("<option value=''>请选择城市</option>");
		$.ajax({
			url: "${base}/area/listbyparent.jhtml?areaId="+$select_province.val(),
			type: "POST",
			dataType: "json",  
			cache: false,  
			success: function(data) {
	           	var html = "";
				for(var i=0;i<data.length;i++){ 
					html += "<option value="+data[i].id+">"+data[i].name+"</option>";  
				}
	            $select_city.append(html);    
			}  
		});  
	});  
	$select_city.change(function(){
		$cityId.val($select_city.val());
		$provinceId.val($select_province.val());
		$storeForm.submit();    
	});	
    // 更换验证码
	$captchaImage.click(function() {
		$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	});
     /*******表单验证 begin********/
    $sendSMSForm.validate({
		rules: {
			telephone: {
				required: true,
				pattern: /^1\d{10}$/,
				minlength: 11,
                maxlength:14
			}
		},
		messages: {
			telephone: {
				pattern: "格式有误"
			},
			captcha: {
                remote: "验证码错误"
			}
		},
		submitHandler: function(form) {
			$.ajax({
				url: "sendSMS.jhtml",
				type: "Post",
                data: $sendSMSForm.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$sendSMS.prop("disabled", true);
				},
				success: function(data) {
                    no1();
                    $sendSMS.prop("disabled", false);
                    $.message(data);
				}
			});
		}
	});
    /*******表单验证 end********/
});
</script>
</head>      
<body>			
	[#include "/shop/include/header.ftl" /]  
	<div class="tiyan-big">
        <!--banner  -->
        <div id="banner">
           [@ad_position adname="线下实体店banner" ]
    	   [/@ad_position]  
        </div>
        <!--banner end-->
        <!--我附近的实体店 end-->
        <div class="std">
            <div class="wofujin">
                <form id="storeForm" action="${base}/store/list.jhtml" method="get">
	                <div class="fujin">您附近的万家乐实体店
						<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
						<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
		                <div class="wdwz">在
							<span class="dangqian">  
							    <input type="hidden" id="cityId" name="cityId" value="${cityId}" />		
							    <input type="hidden" id="provinceId" name="provinceId" value="${provinceId}"/>					 
								<select style="width:80px;height:25px;background:none;border:none;color:#0088cc;" id="select_province">
	                                <option value="">请选择省份</option>
									[#list provinceList as province]
	                                <option value="${province.id}">${province.fullName}</option>
	                                [/#list]
								</select>&nbsp;&nbsp;
								<select style="width:80px;height:25px;background:none;border:none;color:#0088cc;" id="select_city">
									<option value="">请选择城市</option>
								</select>&nbsp;&nbsp;
					        </span>
							</span>为你找到<span class="shuliang">${page.total}</span>家实体店
	                    </div>
	                </div>
	                <div class="hot">
	                    [#list page.content?chunk(4) as row]  
							[#list row as store]
			                    <div class="hot-1">
			                        <div class="hotname"><a href="${store.indexUrl}" target="_blank"><span>${store.name}</span></a></div>
			                        <div class="photo" id="pic">
			                            <div id="slideBox">
			                                <a href="${store.indexUrl}" target="_blank">
			                                    <img alt="" title="" height="212" width="344" src="${store.storeImage}" />
			                                </a>
			                            </div>
			                        </div>
			                        <div class="call">
										<div class="where-1"><span onclick="wqdu1(${store.id})">
	                                        <img src="/resources/shop/images/call-1.png">&nbsp;地址免费发手机</span>
	                                    </div>
										<div class="where-1" 
	                                        onclick="wqdu2(${store.id},'${store.name}','${store.openTime}','${store.serviceTelephone}','${store.busline}','${store.areaName}','${store.address}')">
	                                        <span><img src="/resources/shop/images/ikons.png">查看地图</span>
	                                    </div>
									</div>
			                        <div class="time">
			                            <b>营业时间：</b>${store.openTime}<br>
			                            <b>服务电话：</b>${store.serviceTelephone}<br>
			                            <b>店铺地址：</b>${store.areaName}${store.address}
			                        </div>
			                    </div>
	                    	[/#list]
						[/#list]	
	                </div>
                    [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
						[#include "/shop/include/pagination.ftl"]
					[/@pagination]
	                <div class="ckgd"></div>
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
	            <form id="sendSMSForm" mothod="post" action="sendSMS.jhtml">
		            <input type="hidden" name="storeId" id="smsStoreId">
		            <input type="hidden" name="captchaId" id="captchaId" value="${captchaId}"> 
					<table>
						<tr style="height:70px">
							<td>手机号：</td>
							<td colspan="2"><input name="telephone" type="text"  class="zhanghu12"  style="border:1px solid #999;" /></td>
						</tr>
						<tr>
							<td>验证码：</td>
							<td><input name="captcha" type="text" style="border:1px solid #999;"  class="zhang"/></td>
							<td><img id="captchaImage" src="${base}/common/captcha.jhtml?captchaId=${captchaId}"/></td>
						</tr>
						<tr style="height:70px; margin-top:30px;">
							<td></td>
							<td colspan="2"><a href="#"><input id="sendSMS" type="submit"value="免费发送" class="dsfli"></a></td>
						</tr>
					</table>
	            </form>
			</div>
		</div>	
		
		<div class="two-call" style="display:none;" id="kop3">  
			<div class="two-where-a">
				<div class="where-a-1">查看地图</div>      
				<div class="where-a-2" onclick="no2()">关闭</div>
			</div>
			<div class="where-p">  
				<div class="where-left">
					<div class="dfsk"><span id ="dianpuming"></span></div>
					<div class="lkj">
						<div class="kdjp1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地址：</div>
						<div class="fdjs1 div-display" id = "dizhi1"></div>
					</div>
					<div class="lkj">
						<div class="kdjp2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务时间：</div>
						<div class="fdjs2 div-display" id ="fuwushijian"></div>
					</div>
					<div class="lkj">
						<div class="kdjp3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务热线：</div>   
						<div class="fdjs3 div-display" id = "fuwudianhua"></div>
					</div>
					<div class="lkj">    
						<div class="kdjp4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公交路线：</div>
						<div class="fdjs4 div-display" id = "gongjiaoxianlu"></div>
					</div>  
				</div>
	            <!--map.ftl begin-->
				<div class="wep"><iframe id="myiframe" name="myiframe" width="685" height="500" src =""></iframe></div>
	            <!--map.ftl end-->
				<!--<div class="wep-right">   
					<div class="right-0" id = "dianpuming2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>&lt;详情&gt;</span></div>
					<div class="wep-1">
						<div class="wep-2 div-display">地址：</div>
						<div class="wep-3 div-display" id ="dizhi2"></div>
					</div>
				</div>-->	
			</div>   
		</div>
		
        <!-- 全面服务  全面服务 -->
        <div class="qmfw">
            <div class="lmbt">
                <div class="dbt">更贴心的服务体验</div>
                <div class="xbt">为您提供更好的服务体验是我们永不枯竭的工作动力！</div>
            </div>
            <ul class="content_main cfl">
                <li class="main_li main_le" style="height: 275px;">
                    <h4>最全面的咨询服务</h4>
                    <span>在万家乐实体店，您可以获得关于万家乐官方产品最全面的产品信息。无论是产品规格还是操作指导，甚至包括更深入的玩机技巧，万家乐员工都乐意为您耐心地一一解答。</span>
                    <img src="/resources/shop/images/zuo.jpg" />
                </li>
                <li class="main_li" style="height: 275px;">
                    <h4>最快速、省心的售后服务</h4>
                    <span>当您的产品出现问题时，请不要着急。万家乐售后为您提供快速、省心、贴心的售后服务。您只需要提供凭证并告诉我们您的需求，剩下的，请交给我们为您解决。</span>
                    <img src="/resources/shop/images/zuo.jpg" />
                </li>
                <li style="height: 275px;">
                    <h4>在线售后预约服务</h4>
                    <span>快捷的在线售后预约服务会让您避免长时间排队的问题，专门的预约窗口带给您VIP的服务体验。预约服务还会让我们提前获知您的售后服务需求，提前为您准备详尽的处理方案，尽可能的让您到店后马上解决您的问题。<a href="http://service.order.mi.com/mihome/index">立即预约入口&gt;&gt;</a></span>
                    <img src="/resources/shop/images/zuo.jpg" />
                </li>
            </ul>
        </div>
 	</div>
    <!--主体部分结束-->
	[#include "/shop/include/footer.ftl" /]
</body>
</html>