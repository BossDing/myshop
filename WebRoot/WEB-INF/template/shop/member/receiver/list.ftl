<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/shouhuodizhi.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/swfobject_modified.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<title>万家乐商城</title>
<script type="text/javascript"> 
$().ready(function() {
	var $areaId = $("#areaId");
	var $submit = $(":submit");
	var $receiverForm = $("#receiverForm");
	var $tr2 = $(".tr-2");
		// 地区选择
	$areaId.lSelect({
		url: "${base}/common/area.jhtml"
	});
	$submit.click(function(){
		if($("#address").val() == "详细地址"){
			$("#address").val(null);
		}
		if($("#consignee").val() == "不得超过25个字"){
			$("#consignee").val(null);
		}
	});
	// 表单验证
	$receiverForm.validate({
		rules: {
			areaId : "required",
			zipCode : "required",
			address : "required",
			consignee : {
				required: true,
				maxlength: 25
			},
			phone : "required"	
		},
		submitHandler: function(form) {
			$.ajax({
				url: $receiverForm.attr("action"),
				type: "POST",
				data: $receiverForm.serialize(),
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$submit.prop("disabled", true);
				},
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
						setTimeout(function() {
							$submit.prop("disabled", false);
						}, 3000);
						var htmlX ="<tr class='tr-2'>"+
				             "<td align='center' class='shou-td1'><input type='hidden' name='id' value='${receiver.id}' />"+message.objX.consignee+"</td>"+
				              "<td align='left' class='shou-td2'>"+message.objX.areaName+"</td>"+
				              "<td align='left' class='shou-td2'>"+message.objX.address+"</td>"+
				              "<td align='center' class='shou-td1'>"+message.objX.zipCode+"</td>"+
				              "<td align='center' class='shou-td3'>"+message.objX.phone+" </td>";
				              if(message.objX.isDefault){
					              $(".defaultAdress").each(function(){
						              var $this = $(this); 
							          if($this.text() == "默认地址"){
							            	$this.text("") ;
							           }
				              	  });
				             	htmlX+="<td align='center' class='shou-td1'><a href='#' class='defaultAdress'>默认地址</a></td>";
				              }else{
				               htmlX+="<td align='center' class='shou-td1'><a href='#' class='defaultAdress'></a></td>";
				              }
				               htmlX+="<td align='center' class='shou-td1'><a href='edit.jhtml?id=${receiver.id}'>修改</a>&nbsp;|&nbsp;<a href=\"javascript:deleteRec(this,'${receiver.id}');\" class='delete' >删除</a></td></tr>";
						$(".tr-1").after(htmlX);
						emptyForm();
						$(".right-15").show();
					} else {
						$submit.prop("disabled", false);
					}
				}
			});
		}
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
          $('.anniu3').click(function(){
          $('.right-15').slideToggle();
        });
/**删除事件*/
$("#recList tr td .delete ").click(function(){
          var id = $(this).attr("id").substring(4);
          var $tr =$(this).parent().parent();
          if (confirm("${message("shop.dialog.deleteConfirm")}")) {
          $.ajax({
				url: "delete.jhtml",
				type: "POST",
				data: {id: id},
				dataType: "json",
				cache: false,
				success: function(message) {
					$.message(message);
					if (message.type == "success") {
					   if(null!=$tr&&"undefined"!=$tr) $tr.remove();
					}
				}
			});
		}
        })
        
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
  
  //清空表单
  function emptyForm(){
  	$(":text").val("");
  	$("#address").val("详细地址");
  	$("#consignee").val("不得超过25个字");
  	$("#areaCode").val("区号");
  	$("#telephone").val("电话号码");
  	$("#extension").val("分机");
    $("select option:first").prop("selected", 'selected');
	$("select").eq(2).remove();
	$("select").eq(1).remove();
	$("#areaId").val("");
	$(":radio").attr("checked",false);
  }

 function deleteRec(obj,id){
     
     }
</script>
</head>
<body>
[#include "/shop/include/header.ftl" /]
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>><span><a href="/member/index.jhtml">个人中心</span>><span>收货地址</span></div>
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
<div class="ziliao-big">
  <div class="ziliao-left">[#include "/shop/member/include/navigation.ftl" /]</div>
  <div class="ziliao-right">
    <div class="ziliao-right-1">
      <div class="right-11">收货地址</div>
      <div class="right-13">
        <div class="right-13a"><span>新增收货地址</span> 电话号码、手机号选填一项，其余均为必填项</div>
        <div class="right-13b">
        <form id="receiverForm" action="${base}/member/receiver/save.jhtml" method="post">
          <table cellpadding="0";cellspacing="0" >
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;所在地区</td>
              <td align="left" class="td3">
	                <span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId"/>
					</span>
                </td>
            </tr>
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;街道地址</td>
              <td align="left" class="td1"><input type="text" name="address" id="address" value="详细地址" onfocus="if(value=='详细地址') {value=''}" onblur="if (value=='') {value='详细地址'}" class="td-b"/></td>
            </tr>
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;邮政编码</td>
              <td align="left" class="td2"><input type="text" name="zipCode" id="zipCode" value="" class="td-a"/></td>
              
            </tr>
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;收货人姓名</td>
              <td align="left" class="td2"><input type="text" name="consignee" id="consignee" value="不得超过25个字" onfocus="if(value=='不得超过25个字') {value=''}" onblur="if (value=='') {value='不得超过25个字'}" class="td-a"/></td>
            
            </tr>
            
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;手机号</td>
              <td align="left" class="td2"><input type="text" name="phone" id="phone" value="" class="td-a"/></td>
            </tr>
            <tr>
              <td align="left" class="td1">电话号码</td>
              <td align="left" class="td3">
	              <input type="text" class="td-a" name="areaCode" id="areaCode" style="width:40px;" value="区号" onfocus="if(value=='区号') {value=''}" onblur="if (value=='') {value='区号'}">一
	              <input type="text" class="td-a" name="telephone" id="telephone" value="电话号码" onfocus="if(value=='电话号码') {value=''}" onblur="if (value=='') {value='电话号码'}">一
	              <input type="text" class="td-a" name="extension" id="extension" value="分机" onfocus="if(value=='分机') {value=''}" onblur="if (value=='') {value='分机'}">
              </td>
            </tr>
            <tr>
              <td align="left" class="td1">设为默认地址</td>
              <td align="left" class="td5"><input type="radio" name="isDefault" value="true"/>&nbsp;设置为默认收货地址</td>
            </tr>
            <tr>
              <td colspan=4 align="right"><input name="" type="submit" value="提交" class="anniu"></td>
            </tr>
          </table>
          </form>
        </div>
        <div class="right-14"><input name="" type="button" value="已保存有效地址" class="anniu3"></div>
        <div class="right-15">
          <table cellpadding="0";cellspacing="0" id="recList">
            <tr class="tr-1">
              <td align="center" class="shou-td1">收货人</td>
              <td align="center" class="shou-td2">所以地区</td>
              <td align="center" class="shou-td2">街道地址</td>
              <td align="center" class="shou-td1">邮编</td>
              <td align="center" class="shou-td3">电话号码/手机</td>
              <td align="center" class="shou-td1">状态</td>
              <td align="center" class="shou-td1">操作</td>
          </tr>
          [#list page.content as receiver]
          	<tr class="tr-2">
              <td align="center" class="shou-td1">${receiver.consignee}</td>
              <td align="left" class="shou-td2">${receiver.areaName}</td>
              <td align="left" class="shou-td2">${receiver.address}</td>
              <td align="center" class="shou-td1">${receiver.zipCode}</td>
              <td align="center" class="shou-td3">${receiver.phone} </td>
              <td align="center" class="shou-td1"><a href="#" class="defaultAdress">[#if receiver.isDefault]默认地址[/#if]</a></td>
              <td align="center" class="shou-td1"><a href="edit.jhtml?id=${receiver.id}">修改</a>&nbsp;|&nbsp;<a href="#" id="del_${receiver.id}" class="delete">删除</a></td>
          </tr>
          [/#list]
          </table>
          <div>最多保存20个有效地址</div>
        </div>
        </div>
    </div>
  </div>

</div>
[#include "/shop/include/footer.ftl" /]
</body>
</html>
