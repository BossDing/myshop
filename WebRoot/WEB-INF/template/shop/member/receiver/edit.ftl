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
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/swfobject_modified.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<title>万家乐商城</title>
<script type="text/javascript"> 
$().ready(function() {
	var $areaId = $("#areaId");
	var $submit = $(":submit");
	var $receiverForm = $("#receiverForm");
		// 地区选择
	$areaId.lSelect({
		url: "${base}/common/area.jhtml"
	});
	
	// 表单验证
	$receiverForm.validate({
		rules: {
			areaId : "required",
			zipCode : "required",
			consignee : {
				required: true,
				maxlength: 25
			},
			phone : "required"	
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
	$areaId.val("");
	$(":radio").attr("checked",false);
  }


</script>
</head>
<body>
[#include "/shop/include/header.ftl" /]
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span>首页</span>><span>个人中心</span>><span>收货地址</span></div>
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
        <form id="receiverForm" action="${base}/member/receiver/update.jhtml" method="post">
        <input type="hidden" name="id" value="${receiver.id}" />
          <table cellpadding="0";cellspacing="0" >
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;所在地区</td>
              <td align="left" class="td3">
	                <span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId" value="${(receiver.area.id)!}" treePath="${(receiver.area.treePath)!}" />
					</span>
                </td>
            </tr>
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;街道地址</td>
              <td align="left" class="td1"><input type="text" name="address" id="address" value="${receiver.address}" onfocus="if(value=='详细地址') {value=''}" onblur="if (value=='') {value='详细地址'}" class="td-b"/></td>
            </tr>
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;邮政编码</td>
              <td align="left" class="td2"><input type="text" name="zipCode" id="zipCode" value="${receiver.zipCode}" class="td-a"/></td>
              
            </tr>
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;收货人姓名</td>
              <td align="left" class="td2"><input type="text" name="consignee" id="consignee" value="${receiver.consignee}" onfocus="if(value=='不得超过25个字') {value=''}" onblur="if (value=='') {value='不得超过25个字'}" class="td-a"/></td>
            
            </tr>
            
            <tr>
              <td align="left" class="td1"><span>*</span>&nbsp;手机号</td>
              <td align="left" class="td2"><input type="text" name="phone" id="phone" value="${receiver.phone}" class="td-a"/></td>
            </tr>
            <tr>
              <td align="left" class="td1">电话号码</td>
              <td align="left" class="td3">
	              <input type="text" class="td-a" name="areaCode" id="areaCode" style="width:40px;" value="${receiver.areaCode}" onfocus="if(value=='区号') {value=''}" onblur="if (value=='') {value='区号'}">一
	              <input type="text" class="td-a" name="telephone" id="telephone" value="${receiver.telephone}" onfocus="if(value=='电话号码') {value=''}" onblur="if (value=='') {value='电话号码'}">一
	              <input type="text" class="td-a" name="extension" id="extension" value="${receiver.extension}" onfocus="if(value=='分机') {value=''}" onblur="if (value=='') {value='分机'}">
              </td>
            </tr>
            <tr>
              <td align="left" class="td1">设为默认地址</td>
              <td align="left" class="td5">
                 <input type="radio" name="isDefault" [#if receiver.isDefault] checked="checked" value="true"[/#if]/>&nbsp;设置为默认收货地址
                 <input type="radio" name="isDefault" [#if !receiver.isDefault] checked="checked" [/#if] value="false"/>&nbsp;取消默认收货地址
              </td>
            </tr>
            <tr>
              <td colspan=4 align="right"><input name="" type="submit" value="确定" class="anniu"></td>
            </tr>
          </table>
          </form>
        </div>
    </div>
  </div>

</div>
[#include "/shop/include/footer.ftl" /]
</body>
</html>
