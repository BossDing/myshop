<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/liuyan.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>
<link href="${base}/resources/admin/css/common_report.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/common_report.js"></script>
<title>万家乐商城</title>
<script type="text/javascript"> 
$().ready(function() {
		var $anniu = $(".anniu");
		var $messageForm = $("#messageForm");
		var $typeBox = $(".consultationType");
		var $submit = $(":submit");
		var $fileButton = $("#fileButton");
		$fileButton.browser();
		// 表单验证
	$messageForm.validate({
		rules: {
			content: {
				required: true,
				maxlength: 200
			},
			theme: "required"
		},
		submitHandler: function(form) {
			$.ajax({
				url: $messageForm.attr("action"),
				type: "POST",
				data: $messageForm.serialize(),
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
						$(":radio").eq(0).attr("checked",'true');
						 $("[name='theme']").val("");
						 $("[name='accessory']").val("");
						 $("[name='content']").val("");
						 
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
         $typeBox.click(function(){
         	var checkedType = $(this).val();
         	$("#consultationType").val(checkedType);
         });
});
</script>
</head>
<body>
[#include "/shop/include/header.ftl" /]
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>><span><a href="/member/index.jhtml">个人中心</a></span>><span>买家留言</span></div>
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
      <div class="right-11">买家留言</div>
      [#if product??]
        <div>
  	<table class="info" style="margin:20px;">
				<tr>
					<th rowspan="3">
						<img src="[#if product.thumbnail??]${product.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" alt="${product.name}" />
					</th>
					<td>
						<a href="${base}${product.path}">${abbreviate(product.name, 60, "...")}</a>
					</td>
				</tr>
				<tr>
					<td>
						${message("Product.price")}: <strong>${currency(product.price, true, true)}</strong>
					</td>
				</tr>
				<tr>
					<td>
						[#if product.scoreCount > 0]
							<div>${message("Product.score")}: </div>
							<div class="score${(product.score * 2)?string("0")}"></div>
							<div>${product.score?string("0.0")}</div>
						[#else]
							[#if setting.isShowMarketPrice]
								${message("Product.marketPrice")}:
								<del>${currency(product.marketPrice, true, true)}</del>
							[/#if]
						[/#if]
					</td>
				</tr>
			</table>
  </div>
  [/#if]
      <div class="liu-a">
        <div class="fdk">留言类型:</div>
        <div>
          <input  type="radio" class="consultationType" name="checkBox" value="1"  checked class="fdgkr-1" id="mydiv1"/>&nbsp;&nbsp;留言&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" class="consultationType"  name="checkBox" value="2" style="padding-left:17px;" class="fdgkr-2"  id="mydiv2"/>&nbsp;&nbsp;询问&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" class="consultationType"  name="checkBox" value="3" style="padding-left:17px;" class="fdgkr-2"  id="mydiv2"/>&nbsp;&nbsp;投诉&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" class="consultationType"  name="checkBox" value="4" style="padding-left:17px;" class="fdgkr-2"  id="mydiv2"/>&nbsp;&nbsp;售后&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" class="consultationType"  name="checkBox" value="5" style="padding-left:17px;" class="fdgkr-2"  id="mydiv2"/>&nbsp;&nbsp;求购      
        </div>
      </div>
      <form id="messageForm" action="${base}/member/leaveWords/saveX.jhtml"  method="post" >
      <div class="zu">
        <div class="zu-i">主&nbsp;&nbsp;&nbsp;&nbsp;题</div>
        <div class="zu-2"><input type="text" value="" class="td-a" name="theme" /></div>
      </div>
      <div class="shan">
        <div class="shan-a">上传附件</div>
        <div>
        <input type="text" name="accessory" id="accessoryX"/>
        <input type="button" value="选择文件" id="fileButton"/>
        </div>
        <div class="zhi">只支持JPG、GIF、PNG、JPEG格式图片</div>
      </div>
      <div class="fdpp">
        <div class="dks0">留言内容</div>
        <div class="opi"><textarea name="content"></textarea></div>
      </div>
      <input type="hidden" name="consultationType" id="consultationType" value="1"/>
      [#if product??]
      <input type="hidden" name="id" id="productId" value="${product.id}">
      [/#if]
      <div class="ds09"><input type="submit" value="提交" class="anniu"></div>
      </form>
    </div>
  </div>
</div>
[#include "/shop/include/footer.ftl" /]
</body>
</html>