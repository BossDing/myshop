<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/shuomingshu.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/fuwuzhongxin.css"/>
<link href="${base}/resources/admin/css/admin_common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/scoll.js"></script>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>

<title>欢迎光临万家乐官方商城-厂家直销、最低价格、品质保障、货到付款、配送及时、放心服务、轻松购物 </title>
<style>
  .fieldSet .valid{
    width:80px;
  
  }
</style>
<script type="text/javascript">
$(function(){
	   var $installBooking = $("#installBooking");
	   var $productSelect = $("#productSelect");
	   var $wxyySelect = $("#wxyySelect");
	   var $productTable = $("#productTable");
       var $repairBooking = $("#repairBooking");
       var $productTitle = $("#productTitle");
       var $protectService = $("#protectService");
       var $msgOnline = $("#msgOnline");
       var $outletForm = $("#outletForm");
	   var $areaId =$("#areaId");
	   var $areaId1 =$("#areaId1");
	   var productIds = new Array();
	   $areaId.lSelect({
		url: "${base}/common/area.jhtml"
	   });
	   $areaId1.lSelect({
		url: "${base}/common/area.jhtml"
	   });
        $("#classify_act").mouseover(function(){
            $("#classify").slideDown();
         }); 
		 
		 $("#classify").mouseleave(function(){
            $(this).slideUp();
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp();
         }); 
         
    // 安装需求 商品选择
	$productSelect.autocomplete("product_select.jhtml", {
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
				return '<span title="' + item.fullName + '">' + item.fullName.substring(0, 50) + '<\/span>';
			} else {
				return false;
			}
		}
	}).result(function(event, item) {
			$("#productIds").val(item.id);
			$("#productSelect").val(item.fullName);
	});
	
	// 维修预约 商品选择
	$wxyySelect.autocomplete("product_select.jhtml", {
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
				return '<span title="' + item.fullName + '">' + item.fullName.substring(0, 50) + '<\/span>';
			} else {
				return false;
			}
		}
	}).result(function(event, item) {
			$("#wxyySelect").val(item.fullName);
			$("#productIds1").val(item.id);
	});
    // 安装需求
	$installBooking.validate({
		rules: {
			areaId : "required",
			zipCode : "required",
			consignee : {
				required: true,
				maxlength: 25
			},
			phone : "required"	
		},
		submitHandler: function(form) {
			$.ajax({
				url: $installBooking.attr("action"),
				type: "POST",
				data: $installBooking.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
				  $("#submit1").prop("disabled", true);
				},
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						setTimeout(function() {
							 $("#submit1").prop("disabled", false);
						}, 3000);
						clearFormInstall();
					} else {
					    setTimeout(function() {
							 $("#submit1").prop("disabled", false);
					    	window.location.href="${base}/login.jhtml?redirectUrl=%2Fmember%2Findex.jhtml";
						}, 1000);
					}
				}
			});
		}
	});   
	
	// 维修预约
	$repairBooking.validate({
		rules: {
			areaId : "required",
			zipCode : "required",
			consignee : {
				required: true,
				maxlength: 25
			},
			phone : "required"	
		},
		submitHandler: function(form) {
			$.ajax({
				url: $repairBooking.attr("action"),
				type: "POST",
				data: $repairBooking.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
				  $("#submit2").prop("disabled", true);
				},
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						setTimeout(function() {
							 $("#submit2").prop("disabled", false);
						}, 3000);
						//清空表单
						clearFormRepair();
					} else {
						$("#submit2").prop("disabled", false);
					    window.location.href="${base}/login.jhtml";
					}
				}
			});
		}
	});     
	
	// 在线留言	
	$msgOnline.validate({
		rules: {
			userName : "required",
			content : {
				required: true,
				maxlength: 500
			},
			phone : "required"	
		},
		submitHandler: function(form) {
			$.ajax({
				url: $msgOnline.attr("action"),
				type: "POST",
				data: $msgOnline.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
				  $("#submit4").prop("disabled", true);
				},
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						setTimeout(function() {
							 $("#submit4").prop("disabled", false);
						}, 3000);
						//清空表单
					} else {
						$("#submit4").prop("disabled", false);
					}
				}
			});
		}
	}); 
	
	
	
	 // 表单验证 --服务网点查询
	$outletForm.validate({
		submitHandler: function(form) {
			$.ajax({
				url: $outletForm.attr("action"),
				type: "POST",
				data: $outletForm.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
				  $("#submitOutlet").prop("disabled", true);
				},
				success: function(data) {
					if (null!=data&&data.length>0) {
						$("#submitOutlet").prop("disabled", false);
						$("#outletList").html("");
						var outlets = data;
						/***展示网点*/
						var hml ="<ul>";
	                    if(null!=outlets){
	                        for(var i=0;i<outlets.length;i++){
	                            var outString = outlets[i].split(",");
	                            hml +="<li>"+outString[1]+"&nbsp;"+outString[2]+"&nbsp;"+outString[4]+"</li>";
	                        }
	                    }
	                    hml+="</ul>"
						$("#outletList").html(hml);
					} else {
						$("#submitOutlet").prop("disabled", false);
						$("#outletList").html("");
						var hml ="<p>没有查询到相关记录";
	                    hml+="</p>"
						$("#outletList").html(hml);
					}
				}
			});
		}
	}); 
	var lis = $("#focus").children("ul").children("li");
	if(null!=lis && "undefined"!=lis && lis.length>7){  
	   play(3); 
	}else{
	   var liDiv = lis.length*156;
	   $("#focus").css("width",liDiv+"px");
	   $("#pos_id").removeClass("pos");
	}   
});
var number=1;
  function onwrite(w){
    if(number%2==0){
      w.checked=false;
    }else{
       w.checked=true;  
    }
    number++;
  }

  function setTab(name,cursel,n){
  for(i=1;i<=n;i++){
  var menu=document.getElementById(name+i);
  var con=document.getElementById("con_"+name+"_"+i);
  menu.className=i==cursel?"liuyan-b-1":"";
  con.style.display=i==cursel?"block":"none";
  }
  }
  
/**选中所选商品事件*/
function changedata(obj,type){
      var option = obj.options[obj.options.selectedIndex];
      var id ;
      if(type=='install') id = $("#selectSn").val();
      if(type=='repair') id = $("#selectSn1").val();
      var t = $(option).text();
      if(null!=id&&"undefined"!=id&&""!=id&&"-1"!=id){
        /**插入字符串集合**/
        var productIds= $("#productIds").val();
        var productIds1= $("#productIds1").val();
        
  if(type=='install'){
     /**   if(null!=productIds&&""!=productIds){
           //校验是否已经选中过
           if(productIds.indexOf(id)>=0){
             alert("该商品已经被选中过，请选择其他商品");
             return false;
           }else{
             $("#productIds").val(productIds +"-" +id); //多选
           }
        }else{
             $("#productIds").val(id);
        }
        
        //展示所有选择的商品
        var showSn = $("#showSn ul").html();
        if(null!=showSn&&""!=showSn){
          $("#showSn ul").html($("#showSn ul").html()+"<li>&nbsp;"+t+"</li>");
        }else{
          $("#showSn ul").html("<li>&nbsp;"+t+"</li>");
        }
         $("#showSn").show(); 
         */
          $("#productIds").val(id); //单选
         
       }else if(type=='repair'){
        /** if(null!=productIds1&&""!=productIds1){
           //校验是否已经选中过
           if(productIds1.indexOf(id)>=0){
             alert("该商品已经被选中过，请选择其他商品");
             return false;
             }else{
             $("#productIds1").val(productIds1 +"-" +id);//多选
             }
          }else{
             $("#productIds1").val(id);
         }
        
        //展示所有选择的商品
        var showSn1 = $("#showSn1 ul").html();
        if(null!=showSn1&&""!=showSn1){
          $("#showSn1 ul").html($("#showSn1 ul").html()+"<li>&nbsp;"+t+"</li>");
        }else{
          $("#showSn1 ul").html("<li>&nbsp;"+t+"</li>");
        }
         $("#showSn1").show(); //多选时候显示 单选不显示
         */
         $("#productIds1").val(id);
       }
      }
	}
	/**清空安装预约表单**/
function clearFormInstall(){
	var objs = $("#installBooking table input[type='text']");
	for(var i=0;i<objs.length;i++){
	    objs[i].value ="";
	}
	$("#installContent").html("");
	$("#showSn ul").html("");
	$("#showSn").hide();   
	$("#productIds").val("");
	$("#selectSn").val("-1");
	if($("#areaId")!=null&&$("#areaId").val()!=""){
	     $("#areaId").attr('value','');
         $("#areaId").removeAttr('treePath');
	     $("#areaId").lSelect({
		   url: "${base}/common/area.jhtml"
	      });
	
	}
}
/**清空维修预约表单*/
function clearFormRepair(){
   var objs = $("#repairBooking table input[type='text']");
	for(var i=0;i<objs.length;i++){
	    objs[i].value ="";
	}
	$("#productIds1").val("");
	$("#showSn1 ul").html("");
	$("#showSn1").hide(); 
	$("#selectSn1").val("-1");
	$("#repairDes").html("");
	if($("#areaId1")!=null&&$("#areaId1").val()!=""){
	     $("#areaId1").attr('value','');
         $("#areaId1").removeAttr('treePath');
	     $("#areaId1").lSelect({
		   url: "${base}/common/area.jhtml"
	      });
	
	}  
}
/**清空在线留言表单*/
function clearFormMsgOnline(){
	
}

/**选择默认地址*/
function chooseAddress(_this,type){
  if('${(receiver.area.id)!}'==""){
    alert("您没有登陆或者没有设置默认地址");
    window.location.href="${base}/member/index.jhtml";
  }
  if(type=="install"){
     if($("#installMark").val()=="0"){
         $("#installMark").val("1");
	     $("#areaId").attr('value','${(receiver.area.id)!}');
         $("#areaId").attr('treePath','${(receiver.area.treePath)!}');
	     $("#areaId").lSelect({
		   url: "${base}/common/area.jhtml"
	      });
	  }else{
	     $("#installMark").val("0");
	     $("#areaId").siblings("select").remove();
	     $("#areaId").attr('value','');
         $("#areaId").removeAttr('treePath');
	     $("#areaId").lSelect({
		   url: "${base}/common/area.jhtml"
	      });
	  }
  }
  if(type=="repair"){
    if($("#repairMark").val()=="0"){
         $("#repairMark").val("1");
	     $("#areaId1").attr('value','${(receiver.area.id)!}');
         $("#areaId1").attr('treePath','${(receiver.area.treePath)!}');
	     $("#areaId1").lSelect({
		   url: "${base}/common/area.jhtml"
	      });
	  }else{
	     $("#repairMark").val("0");
	     $("#areaId1").siblings("select").remove();
	     $("#areaId1").attr('value','');
         $("#areaId1").removeAttr('treePath');
	     $("#areaId1").lSelect({
		   url: "${base}/common/area.jhtml"
	      });
	  }
  }
}	
		
/**查询分类*/
function search(){
  $("#searchResult").html("");
  var keyword = $("#categoryWord").val();
  [@product_category_root_list entcode="macro" useCache="false"]
      [#list productCategories as category]
         [@product_category_children_list productCategoryId = category.id count = 40]
             [#list productCategories as productCategory]
              /*  if("productCategory.name".indexOf(keyword)>0){
                   $("#searchResult").html($("#searchResult").html()+"<li><a href='${base}/product/instructions.jhtml?id=${productCategory.id}'>${productCategory.name}</a></li>");
                }*/
                if('${productCategory.name}'==keyword) {
                   $("#searchResult").html($("#searchResult").html()+"<li><a href='${base}/product/instructions.jhtml?id=${productCategory.id}'>${productCategory.name}</a></li>");
                    window.location.href ="${base}/product/instructions.jhtml?id=${productCategory.id}";
                }
             [/#list]
         [/@product_category_children_list]
      [/#list]
    [/@product_category_root_list]
    if($("#searchResult").html().length>0){
       $("#searchResult").hide();
    }else{
    $("#searchResult").show();
       $("#searchResult").html("没有查询结果");
    }
}

/**服务网点省份提交*/
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
				$("#outletAreaId").val(data[0].id);						
			}  
		});  																
	}
/**提交城市*/  				
function subimtAddress2(_this){
		$("#outletAreaId").val($("#select_city").val());
}	

function outletChange(_this){
    $("#outletCategoryName").val($("#outlet_category").val());
}
</script>
</head>
<body>
<!--头部-->
[#include "/shop/include/header.ftl" /]
<!--头部结束--> 

<!--服务中心开始-->
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="${base}/">首页</a></span>><span>服务中心</span></div>
  <div class="kfdlb">
      <ul>
          <li>分享到：</li>
           <li><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
          <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
      </ul>
  </div>
</div>

<!--个人资料开始-->
<div class="fuwuzhongxin">
  <div class="fuwu-a">选择产品类别，获得常见问题、服务政策、说明书下载等服务支持。</div>
  <div class="fuwu-b" style="position: relative;" id="focusHead">
    <div class="fuwu-b1" id="focus">
      <ul style="position:absolute;height:200px;">
   [@product_category_root_list entcode="macro" useCache="false"]
      [#list productCategories as category]
         [@product_category_children_list productCategoryId = category.id count = 40]
             [#list productCategories as productCategory]
               <li class="fuwu-b11">
                 <a href="${base}/product/instructions.jhtml?id=${productCategory.id}">[#if productCategory.image??]<img src="${base}${productCategory.image}"/>[/#if]</a>
                 <div class="fuwu-b11b"> ${productCategory.name}</div>
              </li>
             [/#list]
         [/@product_category_children_list]
      [/#list]
    [/@product_category_root_list]
       </div>
    </div>
    <div class="pos" id="pos_id">
      <div class="pos-left" id="pos-left"></div>
      <div class="pos-right" id="pos-right"></div>
    </div>
  </div>
  <div class="new-big">
      <div class="new"><input type="text" id="categoryWord" class="soso_text2" value="智能浴热水器" onFocus="this.value=''" />
      <input type="button" class="soso_but" /><span class="new1">按产品分类名称查询</span><input onclick="search()" type="button" value="查询" class="anniu1"></div>
      <div id="searchResult"></div>
  </div>
  <div class="fenlei">
    <div class="fenlei-1">
      <div class="fenlei-11"><a href="${base}/article/queryArticle/437.jhtml"><img src="${base}/resources/shop/images/fenlei-1.png"></a></div>
      <div class="fenlei-12">购物指南</div>
      <div class="fenlei-13">提供注册登录，密码相关，购物流程等相关的购特物指引</div>
    </div>
    <div class="fenlei-1">
      <div class="fenlei-11"><a href="${base}/article/queryArticle/447.jhtml"><img src="${base}/resources/shop/images/fenlei-2.png"></a></div>
      <div class="fenlei-12">服务政策</div>
      <div class="fenlei-13">提供安装、售后服务的相关指导，安装维修服务收费指引等</div>
    </div>
    <div class="fenlei-1">
      <div class="fenlei-11"><a href="${base}/article/list/610.jhtml"><img src="${base}/resources/shop/images/fenlei-3.png"></a></div>
      <div class="fenlei-12">配送服务</div>
      <div class="fenlei-13">提供配送服务查询，配送及安装服务收费指引等</div>
    </div>
    <div class="fenlei-1" style="border:none;">
      <div class="fenlei-11"><a href="${base}/article/list/609.jhtml"><img src="${base}/resources/shop/images/fenlei-4.png"></a></div>
      <div class="fenlei-12">支付方式</div>
      <div class="fenlei-13">各类关于支付方式的详细说明都能在此找到，<a href="${base}/article/list/609.jhtml">请点此处》</a></div>
    </div>
  </div>
  <div class="liuyan">
    <div class="liuyan-a">在线服务及留言</div>
    <div  class="liuyan-b">
      <ul>
        <li class="liuyan-b-1" id="menu1" onclick="setTab('menu',1,3)">安装预约</li>
        <li class="liuyan-b-2" id="menu2" onclick="setTab('menu',2,3)">维修预约</li>
        <!--<li class="liuyan-b-2" id="menu3" onclick="setTab('menu',3,4)">延保服务</li>-->
        <li class="liuyan-b-2" id="menu3" onclick="setTab('menu',3,3)">在线留言</li>
      </ul>
    </div>
    <div class="xiangqing">
     
    <form action="${base}/afterBooking/save.jhtml" method="post" id="installBooking"> 
      <div class="xiangqing-a" id="con_menu_1">
        <div class="xiangqing-a-left">
        <input type="hidden" name="state" value="0"/>
        <input type="hidden" name="productIds" id="productIds" />
        <input type="hidden" name="type"  value="install" />
        <input type="hidden" name="source"  value="0" />
          <table cellpadding="0";cellspacing="0">
            <tr>
              <td align="right"  class="left-ty">安装需求</td>
              <td align="left" class="left-fd"></td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;安装机型</td>
              <td align="left" class="left-fd">
              <input type="text" id="productSelect" name="productSelect" class="text" maxlength="200" title="${message("admin.promotion.productSelectTitle")}" />
              </td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;安装时间</td>
              <td align="left" class="left-fd">
                  <input type="text" id="visitServiceDate" name="visitServiceDate" class="text Wdate" 
                   onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'visitServiceDate\')}'});" />
               </td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;配件需求</td>
              <td align="left" class="left-fd"><input type="text" name="parts" class="xiangqing-td1" value=""/></td>
            </tr>
            <tr>
              <td align="right" class="topi"><span>*</span>&nbsp;其它要求</td>
              <td align="left" class="left-fd"><textarea class="textarea1" name="content" id="installContent"></textarea></td>
            </tr> 
          </table>
        </div>
        <div class="xiangqing-a-right">
          <table>
            <tr>
              <td align="right"><span>*</span>&nbsp;所在地区</td>
              <td align="left" class="left-fd">
                 <span class="fieldSet">
					<input type="hidden" id="areaId" name="areaId"/>
				 </span>
			  </td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;街道地址</td>
              <td align="left" class="left-fd"><input type="text" name="address" class="xiangqing-td1" value="" />  </td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;邮政编码</td>
              <td align="left" class="left-fd"><input type="text" name="zipCode" class="xiangqing-td1" value=""/></td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;用户姓名</td>
              <td align="left" class="left-fd"><input type="text" name="consignee" class="xiangqing-td1" value="" /></td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;手机号码</td>
              <td align="left" class="left-fd"><input type="text" name="phone" class="xiangqing-td1" value=""/></td>
            </tr>
            <tr>
              <td align="right">电话号码</td>
              <td align="left" class="left-fd"><input type="text" name="areaCode" class="xiangqing-td3" value=""/>一
              <input type="text" class="xiangqing-td4" name="telephone" value=""/>一
              <input type="text" class="xiangqing-td5" name="extension" value=""/></td>
            </tr>
            <tr>
              <td align="right">使用默认地址</td>
              <td align="left" class="left-fd"><input type="checkbox" name="write" value="male" onClick="chooseAddress(this,'install')"/>&nbsp;设置默认收货地址为安装地址</td>
            </tr>
            <tr>
              <td align="right"></td>
              <td align="left" class="left-fd"><input id="submit1" type="submit" value="提交" class="anniu2"></td>
            </tr>
          </table>
        </div>
      </div>
      </form>
      <!--标识是否使用默认地址-->
       <input type="hidden" id="installMark" value="0"/>
       <input type="hidden" id="repairMark" value="0"/>
      
      <form action="${base}/afterBooking/save.jhtml" method="post" id="repairBooking"> 
      <div class="xiangqing-b" style="display:none;" id="con_menu_2">
          <div class="xiangqing-b-left">
        <input type="hidden" name="state" value="0"/>
        <input type="hidden" name="productIds" id="productIds1" />
        <input type="hidden" name="type"  value="repair" />
        <input type="hidden" name="source"  value="0" />
          <table cellpadding="0";cellspacing="0">
            <tr>
              <td align="right"  class="left-ty">维修预约</td>
              <td align="left" class="left-fd"></td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;故障机型</td>
              <td align="left" class="left-fd">
              <input type="text" id="wxyySelect" name="wxyySelect" class="text" maxlength="200" title="${message("admin.promotion.productSelectTitle")}" />
                </td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;故障时间</td>
              <td align="left" class="left-fd">
                  <input type="text" id="faultDate" name="faultDate" class="text Wdate"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'faultDate\')}'});"  />
               </td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;购买时间</td>
              <td align="left" class="left-fd">
                  <input type="text" id="buyDate" name="buyDate" class="text Wdate" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'buyDate\')}'});" />
               </td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;上门时间</td>
              <td align="left" class="left-fd">
                  <input type="text" id="visitServiceDate1" name="visitServiceDate" class="text Wdate"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'visitServiceDate1\')}'});"  />
               </td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;故障类型</td>
              <td align="left" class="left-fd">
              <select name="faultType" style="width:150px;">
                <option value="0">不能运行</option>
                <option value="1">未知原因</option>
              </select>
              </td>
            </tr>
            <tr>
              <td align="right" class="topi"><span>*</span>&nbsp;故障描述</td>
              <td align="left" class="left-fd"><textarea class="textarea1" name="content" id="repairDes"></textarea></td>
            </tr>
          </table>
        </div>
        <div class="xiangqing-b-right">
          <table>
            <tr>
              <td align="right"><span>*</span>&nbsp;所在地区</td>
              <td align="left" class="left-fd">
                 <span class="fieldSet">
					<input type="hidden" id="areaId1" name="areaId"/>
				 </span>
			  </td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;街道地址</td>
              <td align="left" class="left-fd"><input type="text" name="address" class="xiangqing-td1" value=""/></td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;邮政编码</td>
              <td align="left" class="left-fd"><input type="text" name="zipCode" class="xiangqing-td1" value=""/></td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;用户姓名</td>
              <td align="left" class="left-fd"><input type="text" name="consignee" class="xiangqing-td1" value="" /></td>
            </tr>
            <tr>
              <td align="right"><span>*</span>&nbsp;手机号码</td>
              <td align="left" class="left-fd"><input type="text" name="phone" class="xiangqing-td1" value=""/></td>
            </tr>
            <tr>
              <td align="right">电话号码</td>
              <td align="left" class="left-fd"><input type="text" name="areaCode" class="xiangqing-td3" value=""/>一
              <input type="text" class="xiangqing-td4" name="telephone" value=""/>一
              <input type="text" class="xiangqing-td5" name="extension" value=""/></td>
            </tr>
            <tr>
              <td align="right">使用默认地址</td>
              <td align="left" class="left-fd"><input type="checkbox" name="write" value="male" onClick="chooseAddress(this,'repair')"/>&nbsp;设置默认收货地址为安装地址</td>
            </tr>
            <tr>
              <td align="right"></td>
              <td align="left" class="left-fd"><input id="submit2" type="submit" value="提交" class="anniu2"></td>
            </tr>
          </table>
        </div>
      </div>
      </form>
      
     <!-- <form action="" method="post" id="protectService"> 
      <div class="xiangqing-c" style="display:none;" id="con_menu_3">
        <div class="yanbao-1">延保服务</div>
        <div class="yanbao-2">
          <div  class="yanbao-21">
            <div  class="yanbao-22"><span>*</span>&nbsp;延保机型</div>
            <div><select name="CustType" onchange="changedata(this)"  class="yanbao-td1">  
                <option value="-1"> 机型1</option>
                  <option value="0">机型2</option>
                </select></div>
          </div>
          <div  class="yanbao-21">
            <div  class="yanbao-22"><span>*</span>&nbsp;购买时间</div>
            <div><select name="CustType" onchange="changedata(this)"  class="yanbao-td1">  
                <option value="-1"> 机型1</option>
                  <option value="0">机型2</option>
                </select></div>
          </div>
        </div>
        <div class="yanbao-3">
          <div class="yanbao-31">
            <div><img src="${base}/resources/shop/images/yanbao-1.png"></div>
            <div class="yanbao-311">* 适用全新机型</br>价格：<span>128</span>元</div>
          </div>
          <div class="yanbao-31">
            <div><img src="${base}/resources/shop/images/yanbao-2.png"></div>
            <div class="yanbao-311">* * 适用在用机型</br>价格：<span>158</span>元</div>
          </div>
          <div class="yanbao-31">
            <div><img src="${base}/resources/shop/images/yanbao-3.png"></div>
            <div class="yanbao-311">* 适用全新机型</br>价格：<span>228</span>元</div>
          </div>
        </div>
        <div class="tijao"><input name="" type="button" value="提交" class="anniu2"></div>
      </div>
     </form>
   -->
      <form action="${base}/member/leaveWords/saveXX.jhtml" method="post" id="msgOnline"> 
      <input type="hidden" value="6" name="consultationType"/>
      <div class="xiangqing-d" id="con_menu_3" style="display:none;">
        <div class="xiangqing-d-left">
          <table cellpadding="0";cellspacing="0">
            <tr>
              <td align="right"  class="left-ty">在线留言</td>
              <td align="left" class="left-fd"></td>
            </tr>
            <tr>
              <td align="right">用户姓名</td>
              <td align="left" class="left-fd"><input type="text" name="userName" class="xiangqing-td1" value=""/></td>
            </tr>
            <tr>
              <td align="right">邮箱地址</td>
              <td align="left" class="left-fd"><input type="text" name="email" class="xiangqing-td1" value=""/></td>
            </tr>
            <tr>
              <td align="right">手机号码</td>
              <td align="left" class="left-fd"><input type="text" name="phone" class="xiangqing-td1" value=""/></td>
            </tr>
            
          </table>
        </div>
        <div class="xiangqing-d-right">
          <table>
            <tr>
              <td align="right" class="topi">故障描述</td>
              <td align="left" class="left-fd"><textarea class="textarea1" name="content"></textarea></td>
            </tr>
            <tr>
              <td align="right"></td>
              <td align="left" class="left-fd"><input  type="submit" id="submit4" value="提交" class="anniu2"></td>
            </tr>
          </table>
        </div>
      </div>
      </form>
      
    </div>
  </div>
</div>
<div class="fu-big">
  <div class="fu-a">
    <div class="fu-a1">售后服务政策</div>
    <div class="fu-a2">
      <ul>
        <li>万家乐秉承"真诚服务 乐送万家"的宗旨，诚心诚意为顾客提供专业、优质的服务</li>
        <li>
          <div class="div-1"><img src="${base}/resources/shop/images/fu-1.jpg"></div>
          <div class="div-2"><span>服务理念</span></br>真诚服务 乐送万家</div></li>
        <li>
          <div class="div-1"><img src="${base}/resources/shop/images/fu-2.jpg"></div>
          <div class="div-2"><span>服务理念</span></br>真诚服务 乐送万家</div></li>
        <li>
          <div class="div-3"><img src="${base}/resources/shop/images/call.jpg" width="47px;"></div>
          <div class="div-4"><span class="span-a">全国统一服务电话</span></br><span class="span-b">400 700 3888</span></br>说明：仅支付市话费且支持手机拨打，香港、澳门及台湾地区除外）</div></li>
      </ul>
    </div>
  </div>
  <div class="fu-a">
    <div class="fu-a1">服务网点查询</div>
    <div class="fu-a3">
      <form action="${base}/outlet/list.jhtml" method="post" id="outletForm"> 
      <input type="hidden" value="macro" name="entCode"/>
      <input type="hidden" value="" name="areaId" id="outletAreaId"/>
       <input type="hidden" value="" name="productCategoryName" id="outletCategoryName"/>
      <table>
        <tr>
          <td align="left">产品品类</td>
          <td align="left" class="fu-fd">
          <select id="outlet_category" onchange="outletChange(this)"  class="fu-td1">  
          <option value="-1"> 请选择</option>
          [@product_category_root_list entcode="macro" useCache="false"]
          [#list productCategories as category]
                <option value="${category.name}">${category.name}</option>
          [/#list]
          [/@product_category_root_list] 
          </select>
          </td>
        </tr>
        <tr>
          <td align="left">省级区域</td>
          <td align="left" class="fu-fd">
          	<select id="select_province" onchange="subimtAddress1(this);"  class="fu-td1">
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
						</select>
          </td>
        </tr>
        <tr>
          <td align="left">市县区域</td>
          <td align="left" class="fu-fd">
          <select id="select_city" onchange=subimtAddress2(this); class="fu-td1">  
           </select>
          </td>
        </tr>
        <tr>
          <td align="left"></td>
          <td align="left" class="fu-fd"><input name="" type="submit" value="查询" class="anniu2"></td>
        </tr>
      </table>
      </form>
      <div class="lopk">
        <div class="lopk-1">服务网点</div>
        <div class="lopk-2" id="outletList"></div>
      </div>
    </div>
  </div>
  <div class="fu-b">
    <div class="fu-b1">售后服务政策</div>
    <div class="fu-b2">
      <ul>
        <li>1、服务网点所在城市及近郊实行24小时内上门安装服务，24小时内上门维修服务，远郊实行48小时内上门维修服务。</li>
        <li>2、提供24小时热线电话咨询服务。</li>
        <li>3、产品售出10天内出现质量问题可退、换、修；产品售出20天内出现质量问题可换、修。</li>
        <li>4、产品包修期内上门费、维修费、更换配件费全免（小家电产品及浴霸产品不提供免费上门服务）。</li>
        <li>5、百分之百上门服务、百分之百终身保养，包修期外提供有偿服务。</li>
        <li style="font-size: 12px;"><a href="#"><!--更多服务详情》》--></a></li>
      </ul>
    </div>
  </div>
</div>
<!--底部开始-->
[#include "/shop/include/footer.ftl" /]
</body>
</html>
