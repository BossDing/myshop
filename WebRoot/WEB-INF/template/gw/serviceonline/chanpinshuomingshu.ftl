<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0038)http://www.chinamacro.cn/servicing.php -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Generator" content="ECSHOP v2.7.3">
<!--<base href="http://www.chinamacro.cn/">-->
<base href=".">
<style>
.gy_nr {
	width: 762px;
	margin-right: auto;
	margin-left: auto;
	font-size: 14px;
	line-height: 24px;
	background-color: #FFF;
	min-height: 680px;
	padding-top: 12px;
	padding-right: 18px;
	padding-bottom: 18px;
	padding-left: 18px;
	overflow: hidden;
}
.cj_soso{width:650px; height:170px; margin:20px auto; background:url(/resources/gw/images/gwcj_so_bg.jpg) bottom repeat-x; border:1px solid #CCC;font-weight: bold;}
.cj_soso table{ width:70%; margin:0px auto;}
.cj_soso td { position:relative; padding-top:20px;}
.cj_soso .s_text .selected{
	width:100%;
	height:30px;
	border:none;
	font-size:22px;
}
.s_text {
	width: 350px;
	height: 30px;			
	border: 1px solid #CFCFCF;
	background: #fff;
}
.cj_soso .text_1 {
	width: 100%;
	float: right;
	border: none;
	height: 30px;
	line-height: 24px;
}
.cj_soso .but_1 {
position: absolute;
left: 0px;
height: 28px;
width: 30px;
margin: 1px 2px;
border: none;
background: url(/resources/gw/images/fuwusoso.png) 5px 5px no-repeat;
background-color: #f2f2f2;
}

.cj_soso .but_2 {
width: 110px;
height: 40px;
padding-bottom: 10px;
line-height: 24px;
font-family: "微软雅黑";
font-size: 16px;
background-image: url(/resources/gw/images/fuwusoso-2.png);
background-repeat: no-repeat;
background-color: rgba(255,255,255,0);
float: right;
border: none;
color: #fff;
}
.cj_soso .but_2:hover{background-image: url(/resources/gw/images/fuwusoso-2hover.png);}
.cj_test{ color:#F00; font-size:14px;}
  </style>
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>产品说明书</title>
<link rel="icon" href="http://www.chinamacro.cn/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="http://www.chinamacro.cn/favicon.ico" type="image/x-icon">
<link href="${base}/resources/gw/css/all_css.css" rel="stylesheet" type="text/css">
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/fuwuzhongxin.css"/>
<link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/imgshare.css?v=754091cd.css">
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.4.min.js"></script>
<script type="text/javascript">var $a =jQuery.noConflict();</script>
<script src="${base}/resources/gw/js/nav.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="${base}/resources/gw/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/gw/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/scoll.js"></script>
<script type="text/javascript">
	
	$().ready(function(){
	
			var $productSelect = $("#productSelect");
			var $productSelect2 = $("#productSelect2");
			
			var productIds = new Array();
		/*
			$productSelect2.autocomplete("/gw/product/product_select.jhtml", {
					dataType: "json",
					max: 20,
					width: 600,
					scrollHeight: 300,
					parse: function(data) {
						return $.map(data, function(item) {
							return {
								data: item,
								value: item.fullName
							}
						});
					},
					formatItem: function(item) {
						if ($.inArray(item.id, productIds) < 0) {
							return '<span title="' + item.fullName + '的使用说明书">' + item.fullName.substring(0, 50) + '的使用说明书<\/span>';
						} else {
							return false;
						}
					}
			}).result(function(event, item) {
					//$("#upload").src(item.instruction);
					$("#upload").attr('href',item.instruction);
					//$("#productSelect2").val(item.fullName+'的使用说明书');
					$("#productSelect2").val(item.fullName);
					$("#productSn").val(item.sn);
			});
		*/
		
			$productSelect2.autocomplete("/gw/serviceOnline/instruction_select.jhtml", {
					dataType: "json",
					max: 20,
					width: 600,
					scrollHeight: 300,
					parse: function(data) {
						return $.map(data, function(item) {
							return {
								data: item,
								value: item.name
							}
						});
					},
					formatItem: function(item) {
						if ($.inArray(item.id, productIds) < 0) {
							return '<span title="' + item.name + '的使用说明书">' + item.name + '的使用说明书<\/span>';
						} else {
							return false;
						}
					}
			}).result(function(event, item) {
					//$("#upload").src(item.instruction);
					//$("#upload").attr('href',item.url);
					//$("#productSelect2").val(item.fullName+'的使用说明书');
					$("#productSelect2").val(item.name);
					//$("#productSn").val(item.sn);
			});
			
			
			var $productCategoryId = $("#productCategoryId").val();
			var $productSn = $("#productSn");
			
			$("#productCategoryId").change(function(){
				//alert($(this).val()=='');
				if($(this).val()!=''){
					$.ajax({
							url: "${base}/gw/serviceOnline/productsQuery.jhtml",
							type: "POST",
							//data: $repair.serialize(),
							data: {
								productCategoryId: $("#productCategoryId").val()
							},
							dataType: "json",
							cache: false,
							//beforeSend: function() {
							  //$("#submit1").prop("disabled", true);
							//},
							success: function(data) {
					/*
								$.message(data.message);
								if (data.message.type == "success") {
					*/
									var str='<select id="productSn" class="selected">';
									if(data.productList!=null){
										for(var i=0;i<data.productList.length;i++){
											str+= '<option value="'+data.productList[i].productSn+'">'+data.productList[i].productName+'</option>';
										}
									}else{
										str+='<option value="">--请选择--</option>';
									}
									str+="</select>";
									//$("#sn").html(str);
					/*
									setTimeout(function() {
										 //$("#submit1").prop("disabled", false);
										  window.location.href = "/gw/serviceOnline/problemQuery.jhtml";
									}, 3000);
					*/
					/*
								} else if (data.message.type == "error"){
									//$("#submit1").prop("disabled", false);
									setTimeout(function() {
											//$submit.prop("disabled", false);
											if(data.message.content == "用户还没有登录"){
												window.location.href = "${base}/login.jhtml";
											}
										}, 2000);
									return false;
								}else if (data.message.type == "warn"){
									return false;
								}
					*/
							}
					});
				}else{
					//$("#sn").html('<select  id="productSn" class="selected"><option value="">--请选择--</option></select>');
				}
			});
			
			//点击搜索
			$("#searchButton").click(function(){
				//alert($("#productCategoryId").val());
				//验证条件框是否有值
	/*
				var productVal ="";
				if($("#productSn").val()==""){
					productVal=$("#productSelect2").val();
				}else{
					productVal=$("#productSn").val();
				}
	*/
				$.ajax({
					//url: "${base}/gw/serviceOnline/instructionQuery.jhtml",
					url: "${base}/gw/serviceOnline/instructionQuery2.jhtml",
					type: "POST",
					//data: $repair.serialize(),
					data: {
						name: $("#productSelect2").val()
					},
					dataType: "json",
					cache: false,
					//beforeSend: function() {
					  //$("#submit1").prop("disabled", true);
					//},
					success: function(data) {
						$.message(data.message);
						if (data.message.type == "success") {
							$("#instruction").html(data.instruction[0].name+"的说明书");
							$("#instruction").attr("href",data.instruction[0].url);
			/*
							var str="";
							for(var i=0;i<data.articleList.length;i++){
								str+=data.articleList[i].title;
								str+=data.articleList[i].content;
							}
							$("#article_detail").html(str);
							setTimeout(function() {
								 //$("#submit1").prop("disabled", false);
								  window.location.href = "/gw/serviceOnline/problemQuery.jhtml";
							}, 3000);
			*/
						} else if (data.message.type == "error"){
							//$("#submit1").prop("disabled", false);
							setTimeout(function() {
									//$submit.prop("disabled", false);
									if(data.message.content == "用户还没有登录"){
										window.location.href = "${base}/login.jhtml";
									}
								}, 2000);
							return false;
						}else if (data.message.type == "warn"){
							return false;
						}
					}
				});
			});
	});    
</script>
</head>
<body>
[#include "/gw/include/header.ftl" /]
<div class="ban4">
  <div id="bn">
  	<span class="tu">
	<!--
  	  <a href="/gw/serviceOnline/repair.jhtml" style="width: 1349px;">
  		 <img border="0" alt="" width="1680" height="257" src="${base}/resources/gw/images/zcfw.jpg" data-bd-imgshare-binded="1">
  	  </a>
  	-->
  		[@ad_position adname="官网 - 服务支持banner图"/]
  	</span>
  </div>
</div>
<div class="cpzx">
  <div class="cpzx_div zlm_bg">
    <div class="gywjl_dq">目前您在：<a href="/gw/index.jhtml">首页</a> &gt; <a href="/gw/article/glist/838.jhtml">服务支持</a> &gt; 产品说明书</div>
    <div class="jrwjl_z">
      	[@article_list useCache=false name="服务支持 - 左菜单栏" articleCategoryId=1 tagIds=1 ]
			[#list articles as a]
			${a.content}
			[/#list ]
		[/@article_list]
    </div>
    <div class="gywjl_y">
    <div class="gy_nrbt">产品说明书</div>
     	<div class="gy_nr">
       你可以在下面的搜索框内输入你所购产品的类型，并选择产品说明书下载
       </b>
    <div class="cj_soso">
         <table width="100%" cellpadding="0" cellspacing="0" >
          <tr>
            <td width="99">产品类别</td>
            <td width="219">
            <div class="s_text">
                <select id="productCategoryId"  class="selected">
            	<!--
                	<option value="">--请选择--</option>
                	<option value="1080">燃气热水器</option>
                	<option value="1082">电热水器</option>
                	<option value="1086">吸油烟机</option>
                	<option value="1087">燃气灶具</option>
                	<option value="1088">消毒碗柜</option>
            	-->
                	
                	<option selected="selected"  value="-1">请选择</option>
                    [#list categorys as category]
                    	[#if category.isInstruction == true]
	                  		<option value="${category.id}">&nbsp;${category.name}</option>
	                   [/#if]
	                [/#list]
	                </select>
            	   <input type="hidden" name="productCategory" id="product_category"/>
                </select>
                <!--
                <input class="but_1" type="button" />
                <input class="text_1" id="productSelect" name="productSelect" type="text" />
                <input  id="productCategory" name="productCategory" type="hidden" />	
                <input  id="instruction" name="instruction" type="hidden" />
                -->		
            </div>
            </td>
          </tr>
          <tr>
            <td>产品型号</td>
            <td>
            <div class="s_text" id="sn">
                <!--
                <input class="but_1" type="button" />
                <select id="productSn" class="selected">
                	<option value="">--请选择--</option>
                </select>
            	-->
                <input class="text_1"  id="productSelect2" name="productSelect2"   type="text" placeholder="请输入产品型号" />
            	<input  id="productSn" name="productSn" type="hidden" />
            </div>      
             </td>
          </tr>		
          <tr>
            <td>&nbsp;</td>
            <td>
            	<!--<a href="/gw/serviceOnline/chanpinshuomingshu.jhtml" id="upload">-->
            		<input  class="but_2" type="button" value="搜索" id="searchButton" />
            	<!--</a>-->
            </td>
          </tr>
        </table>
         </div>   
  	<div class="cj_test">
     <!--查询内容显示-->
     		<a id="instruction" target="_blank">
     			
     		</a>
     </div>       
    </div>
  </div>
</div>
[#include "/gw/include/footer.ftl" /]

</body>
</html>