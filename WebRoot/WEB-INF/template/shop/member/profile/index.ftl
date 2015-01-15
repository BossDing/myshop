<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>我的资料</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/gerenziliao.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
    	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv_order.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/shop/datePicker/WdatePicker.js"></script>
<script type="text/javascript">
$().ready(function() {
	var $areaId = $("#areaId");
	var $saveButton = $("#saveButton");
	var $memberForm = $("#memberForm");
	var $username = $("#username");
	
	
	// 地区选择 
	$areaId.lSelect({
		url: "${base}/common/area.jhtml"
	});
	
	$('#saveButton').click(function(){   
					$.ajax({  
						url: "${base}/member/profile/save.jhtml",
						type: "POST",
						data:{
							  username:$('#username').val()	,
							  name:$('#name').val(),
							  email:$('#email').val(),
							  areaId:$('#areaId').val(),
							  zipCode:$('#zipCode').val(),
							  mobile:$('#mobile').val(),
							  phone:$('#phone').val(),   
							  address:$('#address').val(),
							  birth:$('#birth').val(),
							  qq:$('#qq').val(),
							  wangwang:$('#wangwang').val(),
							  zhifubao:$('#zhifubao').val(),
							  yinghanzhanghao:$('#yinghanzhanghao').val(),
							  gender:$("input[name='gender']:checked").val()
						},  
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								setTimeout(function() {
									$('#saveButton').prop("disabled", false);
								}, 3000);
								window.location.href = "${base}/member/profile/edit.jhtml";
								alert("保存成功");
							} else {
								$('#saveButton').prop("disabled", false);
							}
						}
					});
	}); 
});
</script>					   
</head>			
<body>																									
	[#assign current = "orderList" /]			
	[#include "/shop/include/header.ftl" /]
	<div class="content">
	<div class="container member">
		<div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<a href="/"><span>首页</span></a>><a href="${base}/member/index.jhtml"><span>个人中心</span></a>><span>我的资料</span></div>
		  <div class="kfdlb">
			  <ul>
				  <li>分享到：</li>
				  <li><a href="#"><img src="/resources/shop/images/qq_ico.png" width="17px";></a></li>
				  <li><a href="#"><img src="/resources/shop/images/renren_ico.png" width="17px";></a></li>
				  <li><a href="#"><img src="/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
				  <li><a href="#"><img src="/resources/shop/images/weibo_ico.png" width="17px";></a></li>
			      <li><a href="#"><img src="/resources/shop/images/xinxi_ico.png" width="17px";></a></li>
			  </ul>
		  			</div>   
				</div>
		</div>					
		
	
		<div class="ziliao-big">  
[#include "/shop/member/include/navigation.ftl" /]
  <div class="ziliao-right">				
    <div class="ziliao-right-1">
      <div class="right-11">个人资料</div>
      <div class="right-12"><span>当前级别：<a href="#">${member.memberRank.name}</a></span><span>晋升金额：${member.memberRank.amount}</span><span>会员折扣：<a href="#">${member.memberRank.scale}</a></span></div>
      <div class="right-13">
        <div class="right-13a">亲爱的 ${member.username} <span></span>您好！欢迎光临万家乐商城！填写真实的资料，有助于享受更多购物乐趣！</div>
        <div class="right-13b">  
         <form id="memberForm" action="${base}/member/profile/save.jhtml" method="post">
          <table cellpadding="0";cellspacing="0"> 			
            <tr>		
              <td align="left" class="td1">用户名</td>
              <td align="left" class="td2">
              <input type="hidden"  name="id" value="${member.id}"  class="td-a"/>
              <input type="text" id= "username" name="username" value="${member.username}"  readOnly="true"  class="td-a"/></td>
              <td align="left" class="td1">E-mail</td>
              <td align="left" class="td2"><input type="text" id="email"  value="${member.email}"  class="td-a"/></td>
            </tr>
            <tr>
              <td align="left" class="td1">姓名</td>
              <td align="left" class="td2"><input type="text" id="name" value="${member.name}" class="td-a"/></td>
              <td align="left" class="td1"> 邮编</td>
              <td align="left" class="td2"><input type="text" id="zipCode" value="${member.zipCode}" class="td-a"/></td>
            </tr>
            <tr>							
              <td align="left" class="td1">联系地址</td>
              <td align="left" class="td3">			
              	[#if member.area?has_content]
					<input type="hidden" id="areaId" name="areaId" value="${(member.area.id)!}"  treePath="${(member.area.treePath)!}" />
				[#else]
					<input type="hidden" id="areaId" name="areaId" />
				[/#if]
               </td>
              <td align="left" class="td1">固定电话</td>
              <td align="left" class="td2"><input type="text" id="phone"  value="${member.phone}" class="td-a"/></td>
            </tr>
            <tr><td align="left" class="td1">详细地址</td><td align="left" class="td2"><input type="text" id="address"  value="${member.address}"   class="td-b"/></td>
            	<td align="left" class="td1">银行账户名</td>
              <td align="left" class="td2"><input type="text"  id="yinghanzhanghao" value="${member.yinghanzhanghao}" class="td-a"/></td>
            </tr>
            <tr>
              <td align="left" class="td1">移动电话</td>
              <td align="left" class="td2"><input type="text" id="mobile"  value="${member.mobile}" class="td-a"/></td>
              <td align="left" class="td1">旺旺</td>
              <td align="left" class="td2"><input type="text" id="wangwang" value="${member.wangwang}" class="td-a"/></td>
            </tr>
            <tr>
              <td align="left" class="td1">支付宝</td>
              <td align="left" class="td2"><input type="text"  id="zhifubao" value="${member.zhifubao}" class="td-a"/></td>
              <td align="left" class="td1">性别</td>
              <td align="left" class="td2">  
              <div class="td-a" style="border:none; padding:0px;">
              [#if member.gender == "male"]    
	                                <input type="radio" name="gender" value="male" checked="checked" class="fdgkr-1"/>
                                [#else]
                                	<input type="radio" name="gender" value="male" class="fdgkr-2"/>
                                [/#if]
                                男
                                [#if member.gender == "female"]
                                	<input type="radio" name="gender" value="female" checked="checked"  class="fdgkr-2"/>
                                [#else]
                                	<input type="radio" name="gender" value="female" class="fdgkr-1"/>
                            	[/#if]
                                女
                                </div>
              </td>
            </tr>
            <tr>
              <td align="left" class="td1">QQ</td>
              <td align="left" class="td2"><input type="text" id="qq" value="${member.qq}" class="td-a"/></td>
               <td align="left" class="td1">出生日期</td>  
              <td align="left" class="td2">
              <div class="td-a" style="border:none; padding:0px;">
                     <input type="text" id="birth" class="text" onfocus="WdatePicker();" value = "${member.birth}"/>
				</div>
              </td>
            </tr>
            
            <tr>
              <td colspan=4 align="right"><input name=""  id="saveButton"  type="button" value="提交" class="anniu"></td>
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