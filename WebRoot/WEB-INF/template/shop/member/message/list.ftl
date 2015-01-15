<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/duanxin.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>
<title>万家乐商城</title>
<script type="text/javascript"> 
    $sendForm = $("#sendForm");
	$(function(){
        $("#classify_act").mouseover(function(){
            $("#classify").slideDown();
         }); 
		 
		 $("#classify").mouseleave(function(){
            $(this).slideUp();
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp();
         }); 
         
         $(".anniu").click(function(){
            $(".right-15").slideToggle();
         });
    $.validator.addMethod("notEqualsIgnoreCase", 
		function(value, element, param) {
			return this.optional(element) || param.toLowerCase() != value.toLowerCase()
		}
	);
	
	//更多商品
	$(".more1").click(function(){
	 var num1 = parseInt($("#num1").val())+1;
	 var size=0;
	 size = Math.ceil(${receiverPage.content?size}/3);
	if(num1 == size){
		$(".more1").hide();
	}
	$(".shou-x"+num1).show();
	$("#num1").val(num1);
	});
	
	$(".more2").click(function(){
	var num2 = parseInt($("#num2").val())+1;
	var size=0;
	size = Math.ceil(${senderPage.content?size}/3);
	if(num2 == size){
		$(".more2").hide();
	}
	$(".shou-y"+num2).show();
	$("#num2").val(num2);
	});
	
	// 表单验证
	  $sendForm.validate({
		rules: {
			username: {
				required: true,
				notEqualsIgnoreCase: "${member.username}",
				remote: {
					url: "check_username.jhtml",
					cache: false
				}
			},
			title: {
				required: true
			},
			content: {
				required: true,
				maxlength: 1000
			}
		},
		messages: {
			username: {
				notEqualsIgnoreCase: "${message("shop.member.message.notAllowSelf")}",
				remote: "${message("shop.member.message.memberNotExsit")}"
			}
		}
	});
});

/**w是当前对象,n表示第几套全选,m表示是全选按钮还是普通按钮*/
  function checkBox(w,n,m){
   var boxs =  $("input[name='box"+n+"']");
    var checkAll = true;
    // 全选
    if($(w).prop("checked")){
   
       if(m==0){
          for(var i=0;i<boxs.length;i++){
             boxs[i].checked = true;
          }
       }else{
         $.each(boxs,function(j,box){
             if(!$(box).prop("checked")){
                  checkAll=false;
                  return;
                  }
         });
         if(checkAll){
            $("#boxAll"+n).prop("checked",true);
         }else{
            $("#boxAll"+n).prop("checked",false);
         }
       }
    }else{ //反选
       if(m==0){
          for(var i=0;i<boxs.length;i++){
             boxs[i].checked = false;
          }
       }else{
         $.each(boxs,function(j,box){
             if($(box).prop("checked")){
                  checkAll=false;
                  return;
                  }
         });
         if(checkAll){
            $("#boxAll"+n).prop("checked",true);
         }else{
            $("#boxAll"+n).prop("checked",false);
         }
       }
    }
  }
  
   /**t是当前对象,d表示第几套全选*/
  function deleteBox(t,d){
  var $this = t;
  var $name = "box"+d;
  var $checkedIds = $("input[name="+$name+"]:enabled:checked");
  var lopX = "lop"+d;
  if($("#"+lopX).text()=="0条记录" || $checkedIds.size()==0){
  	return false;
  }
			if (confirm("${message("shop.dialog.deleteConfirm")}")) {
				$.ajax({
					url: "${base}/member/message/delete.jhtml",
					type: "POST",
					data: $checkedIds.serialize(),
					dataType: "json",
					cache: false,
					success: function(message) {
						$.message(message);
						if (message.type == "success") {
						if(d == 1){
							$("#lop1").text(message.objX+"条记录");
						}else if(d == 2){
							$("#lop2").text(message.objX+"条记录");
						}
							$checkedIds.closest(".shou-1").remove();
						}
						$("#boxAll"+d).prop("checked", false);
						$checkedIds.prop("checked", false);
					}
				});
		}
  }
</script>
</head>
<body>
[#include "/shop/include/header.ftl" /]
<!--产品试用开始-->
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>><span><a href="/member/index.jhtml">个人中心</a></span>><span>站内短信</span></div>
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

<div class="ziliao-big">
  <div class="ziliao-left">[#include "/shop/member/include/navigation.ftl" /]</div>
  <div class="ziliao-right">
    <div class="ziliao-right-1">
      <div class="right-11">站内短信</div>
      <div class="right-12">
        <div class="right-12-left">
          <div class="right-12-p">
            <div class="right-left-1"><span class="span1">收件箱</span></div>
              <div class="shou">
                  [#assign x=0]
                  [#list receiverPage.content?chunk(3) as memberMessages]
                  <div class="shou-x${memberMessages_index+1}" [#if memberMessages_index!=0]style="display:none;"[/#if]>
                  [#list memberMessages as memberMessage]
                  [#assign x=x+1]
                    <div class="shou-1">
                  <input type="hidden" name="id" value="${memberMessage.id}" />
                  <div class="shou-1-left"><input type="checkbox" name="box1" value="${memberMessage.id}" onclick="checkBox(this,1,1)"/></div>
                  <div class="shou-1-right">
                   ${memberMessage.title}:${memberMessage.content}</br>${memberMessage.createDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      发件人：[#if memberMessage.sender??]${memberMessage.sender.username}
                   [#else]${message("shop.member.message.admin")}
                   [/#if]
                   </div>
                    </div>
                  [/#list]
                  </div>
                  [/#list]
              </div>
              <input type="hidden" id="num1" value="1"/>
              [#if receiverPage.content?size>3]<div class="more1">查看更多</div>[/#if]
            </div>
            <div class="right-12-bottom">
              <div class="no"><input type="checkbox" id="boxAll1" name="boxAll1" value="box1" onclick="checkBox(this,1,0)"/>&nbsp;全选&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="deleteBox(this,1)">删除</a></div>
              <div class="lop" id="lop1">${x}条记录</div>
            </div>
          </div>
          
          
        <div class="right-12-left" style="float:right">
          <div class="right-12-p">
            <div class="right-left-1"><span class="span1">发件箱</span></div>
              <div class="shou">
                  [#assign y=0]
                  [#list senderPage.content?chunk(3) as memberMessages]
                  <div class="shou-y${memberMessages_index+1}" [#if memberMessages_index!=0]style="display:none;"[/#if]>
                  [#list memberMessages as memberMessage]
                  [#assign y=y+1]
                    <div class="shou-1">
                  <input type="hidden" name="id" value="${memberMessage.id}" />
                  <div class="shou-1-left"><input type="checkbox" name="box2" value="${memberMessage.id}"  onclick="checkBox(this,2,1)" /></div>
                  <div class="shou-1-right">
                   ${memberMessage.title}:${memberMessage.content}</br>${memberMessage.createDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      收件人：[#if memberMessage.receiver??]${memberMessage.receiver.username}
                   [#else]${message("shop.member.message.admin")}
                   [/#if]
                   </div>
                    </div>
                    [/#list]
                    </div>
                  [/#list]
              </div>
              <input type="hidden" id="num2" value="1"/>
              [#if senderPage.content?size>3]<div class="more2">查看更多</div>[/#if]
            </div>
            <div class="right-12-bottom">
              <div class="no"><input type="checkbox" id="boxAll2" name="boxAll2" value="male" onclick="checkBox(this,2,0)"/>&nbsp;全选&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="deleteBox(this,2)">删除</a></div>
              <div class="lop" id="lop2">${y}条记录</div>
            </div>
          </div>
      </div>
      <div class="right-14"><input name="" type="button" value="发短信" class="anniu"></div>
      <div class="right-15">
       <form id="sendForm" action="${base}/member/message/send.jhtml" method="post">
        <table>
          <tr>
            <td align="right" class="td-6"><span>*</span>收件人:&nbsp;&nbsp;</td>
            <td align="left" class="td-0"><input type="text" value="" class="td-a" name="username" id="username"/></td>
            <td align="left" style="vertical-align: left;">（会员/管理员）</td>
          </tr>
          <tr>
            <td align="right" class="td-6"><span>*</span>主题:&nbsp;&nbsp;</td>
            <td align="left"colspan=2 class="td-9"><input type="text" value="" class="td-b" name="title" id="title"/></td>
          </tr>
          <tr>
            <td align="right" class="td-8"><span>*</span>内容:&nbsp;&nbsp;</td>
            <td align="left"colspan=2 class="td-7"><textarea name="content" id="content"></textarea></td>
          </tr>
          <tr>
            <td align="right" class="td-8"></td>
            <td align="left"colspan=2>限制140字以内</td>
          </tr>
        </table>
        <div class="right-16"><input name="" type="submit" value="发送" class="anniu1"></div>
      </div>
    </div> 
  </div>
</div>
<!--个人资料完-->
[#include "/shop/include/footer.ftl" /]
</body>
</html>