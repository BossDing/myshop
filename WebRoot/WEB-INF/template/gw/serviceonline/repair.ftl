<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0038)http://www.chinamacro.cn/servicing.php -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="Generator" content="ECSHOP v2.7.3">
<!--<base href="http://www.chinamacro.cn/">-->
<base href=".">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>维修预约_广东万家乐燃气具有限公司</title>
<link rel="icon" href="http://www.chinamacro.cn/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="http://www.chinamacro.cn/favicon.ico" type="image/x-icon">
<link href="${base}/resources/gw/css/all_css.css" rel="stylesheet" type="text/css">
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/imgshare.css?v=754091cd.css">

<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.4.min.js"></script>
    <script type="text/javascript">var $a =jQuery.noConflict();</script>
	<script src="${base}/resources/gw/js/nav.js" type="text/javascript" language="javascript"></script>
	<script type="text/javascript" src="${base}/resources/gw/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/gw/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
    <script type="text/javascript">
	
	$().ready(function(){
	
			$("#productCategory").change(function(){
				var productCategory = $(this).val();
				if(productCategory != -1){
					$("#product_category").val(productCategory);
				}else{
					$("#product_category").val('');
				}
			});
	
			var CodeIsOk = false;
			var $captchaImage = $("#captchaImage");
   			// 更换验证码
			$captchaImage.click(function() {
				$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
			});
			var CodeIsOk = false;
			//校验验证码
			$("#captcha").focus(function(){
				$("#codeMsg").text("");
			});
			$("#captcha").blur(function(){
				var thisval = $(this).val();
				if(thisval==""){
					$("#codeMsg").text("请输入验证码").css('color','#666');
					CodeIsOk = false;
					return false;
				}else{
					$.ajax({
   						url : "${base}/gw/serviceOnline/checkCode.jhtml",
   						type : "GET",
   						data : {captchaId: "${captchaId}",captcha : thisval},
   						dataType : "json",
   						traditional : true,
   						cache : false,
   						success : function(data){
   							if(!data){
   								$('#codeMsg').text("验证码不正确").css('color','red');
   								CodeIsOk = false;
   								return false;
   							}else{
   								$('#codeMsg').text("验证码正确").css('color','green');
   								CodeIsOk = true;
   							}
   						}
   					});
				}
			});
			
			var $repair = $("#repair");
			var $type = $("#type");
			var $productCategory = $("#productCategory");
			var $productSelect = $("#productSelect");
			var $buyDate = $("#buyDate");
			var $saleCompany= $("#saleCompany");
			var $visitServiceDate = $("#visitServiceDate");
			var $serviceDesc = $("#serviceDesc");
			var $consignee = $("#consignee");
			var $phone = $("#phone");
			var $email = $("#email");
			var $address = $("#address");
			
			var productIds = new Array();
			
			$productSelect.autocomplete("/gw/product/product_select.jhtml", {
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
					//$("#upload").src(item.instruction);
					//$("#upload").attr('href',item.instruction); 
					//$("#productSelect2").val(item.fullName+'的使用说明书');
					$("#productSelect").val(item.fullName);
					//$("#productSn").val(item.sn);
			});
			
			// 表单验证
		$repair.validate({
			rules: {
				productCategory: "required",
				productName: "required",
				buyDate : "required",
				saleCompany : "required",
				visitServiceDate : "required",
				serviceDesc : "required",
				//captcha : "required",
				address : "required",
				consignee : {
					required: true,
					maxlength: 25
				},
				email : {
					required: true,
					email: true
				},
				phone : "required",
				captcha : "required"
			},
			submitHandler: function(form) {
				if(CodeIsOk){
					$.ajax({
						url: $repair.attr("action"),
						type: "POST",
						//data: $repair.serialize(),
						data: {
							type: $type.val(),
							productCategoryId: $productCategory.val(),
							productName: $productSelect.val(),
							buyDate: $buyDate.val(),
							saleCompany: $saleCompany.val(),
							visitServiceDate: $visitServiceDate.val(),
							serviceDesc: $serviceDesc.val(),
							consignee: $consignee.val(),
							email: $email.val(),
							phone: $phone.val(),
							address: $address.val()
						},
						dataType: "json",
						cache: false,
						//beforeSend: function() {
						  //$("#submit1").prop("disabled", true);
						//},
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								setTimeout(function() {
									 //$("#submit1").prop("disabled", false);
									 window.location.href = "/gw/serviceOnline/repair.jhtml";
								}, 3000);
								//alert($productCategory.val());
								//alert(message);
								//清空表单
							} else if (message.type == "error"){
								//$("#submit1").prop("disabled", false);
								setTimeout(function() {
										//$submit.prop("disabled", false);
										window.location.href = "${base}/login.jhtml";
									}, 2000);
							}
						}
					});
				}
			}
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
    <div class="gywjl_dq">目前您在：<a href="/gw/index.jhtml">首页</a> &gt; <a href="/gw/article/glist/838.jhtml">服务支持</a> &gt; 维修预约</div>
    <div class="jrwjl_z">
    <!--
      	<div class="gywjl_lmbt">服务支持</div>
      	<div class="gy_zlm hong"><a href="${base}/gw/serviceOnline/repair.jhtml">在线服务</a></div>
		<div class="gy_zlm"><a href="${base}/gw/serviceOnline/servicePolicy.jhtml">服务政策</a></div>
		<div class="gy_zlm"><a href="http://www.chinamacro.cn/fuwu/a18.html">常见问题</a></div>
		-->
		
		[@article_list useCache=false name="服务支持 - 左菜单栏" articleCategoryId=1 tagIds=1 ]
			[#list articles as a]
			${a.content}
			[/#list ]
		[/@article_list]
		
		
    </div>
    <div class="gywjl_y">
      <div class="gy_nrbt">维修预约</div>
      <div class="gy_nr">
        <table width="720" border="0" align="center" cellpadding="0" cellspacing="0">
          <tbody>
            <tr>
              <td height="166" align="left" valign="middle" style=" border-bottom:solid 1px #E6E6E6;"><table width="740" border="0" cellspacing="0" cellpadding="0">
                  <tbody>
                    <tr>
                      <td height="106" valign="top" style="border-right:solid 1px #E6E6E6; padding-right:10px;"><p>维修预约</p>
                        <p class="zt12">在线提交维修申请，节省话费
                          快速响应</p>
                        <p><a href="${base}/gw/serviceOnline/repair.jhtml"><img src="${base}/resources/gw/images/sqwx.jpg" width="93" height="23" data-bd-imgshare-binded="1"></a></p></td>
                      <td valign="top" style="border-right:solid 1px #E6E6E6; padding-left:10px; padding-right:10px;"><p>安装预约</p>
                        <p class="zt12">在线提交维修申请，我们会立即联系您上门进行产品安装</p>
                        <p><a href="${base}/gw/serviceOnline/install.jhtml"><img src="${base}/resources/gw/images/sqaz.jpg" width="93" height="23" data-bd-imgshare-binded="1"></a></p></td>
                      <td valign="top" style="border-right:solid 1px #E6E6E6;padding-left:10px; padding-right:10px;"><p>延保服务</p>
                        <p class="zt12">在线查询延保服务信息，让您省心、安心、舒心</p>
                        <p><a href="${base}/gw/serviceOnline/extension.jhtml"><img src="${base}/resources/gw/images/ybfw.jpg" width="93" height="23" data-bd-imgshare-binded="1"></a></p>
                        <p></p></td>
                      <td valign="top" style="padding-left:10px; padding-right:10px;"><p>在线留言</p>
                        <p class="zt12">在线提交您的留言信息，我们将尽快与您取得联系</p>
                        <p><a href="${base}/gw/serviceOnline/leaveMsg.jhtml"><img src="${base}/resources/gw/images/sqly.jpg" width="93" height="23" data-bd-imgshare-binded="1"></a></p>
                        <p></p></td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
            <tr>
              <td height="100"><div class="faxq_nav">
                  <div class="fa_lj2"><a href="${base}/gw/serviceOnline/repair.jhtml">维修预约</a></div>
			        <div class="fa_lj"><a href="${base}/gw/serviceOnline/install.jhtml">安装预约</a></div>
			        <div class="fa_lj"><a href="${base}/gw/serviceOnline/extension.jhtml">延保服务</a></div>
			        <div class="fa_lj"><a href="${base}/gw/serviceOnline/leaveMsg.jhtml">在线留言</a></div>
                </div></td>
            </tr>
            <tr>
              <td>
              <form action="${base}/gw/serviceOnline/save.jhtml" method="post" name="formMsg"
              	 id="repair">
              	 <input type="hidden" name="entCode" value="gwmacro" />
              	 <input type="hidden" name="type" id="type" value="repair" />
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                      <tr style="display:none;">
                        <td align="right" colspan="3">用户名</td>
                        <td colspan="3"> 匿名用户 </td>
                      </tr>
                      <tr style="display:none;">
                        <td align="right" colspan="3">留言类型</td>
                        <td colspan="3"><input name="msg_type" type="radio" value="0">
                          留言
                          <input type="radio" name="msg_type" value="1">
                          应聘
                          <input type="radio" name="msg_type" value="2" checked="checked">
                          <input type="radio" name="msg_type" value="3">
                          维修</td>
                      </tr>
                    </tbody>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                      <tr>
                        <td width="150" height="40" style=" border-bottom:solid 1px #E6E6E6;"><strong>产品信息</strong></td>
                        <td width="570" style=" border-bottom:solid 1px #E6E6E6;">&nbsp;</td>
                      </tr>
                      <tr>
                        <td height="36" align="right"><span class="hong">* </span>产品类别：</td>
                        <td height="36"><label for="cp_lx"></label>
	                          <select id="productCategory" style="margin-left:15px;">
	                            <option selected="selected"  value="-1">请选择</option>
	                            [#list categorys as category]
	                            	[#if category.isRepair == true]
				                  		<option value="${category.id}">&nbsp;${category.name}</option>
				                   [/#if]
				                [/#list]
			                 </select>
                        	   <input type="hidden" name="productCategory" id="product_category"/>
                        </td>
			            
			                
                            <!--
	                            <option value="${category.id}">燃气热水器</option>
	                            <option value="${category.id}">电热水器</option>
	                            <option value="${category.id}">吸油烟机</option>
	                            <option value="${category.id}">燃气灶具</option>
	                            <option value="${category.id}">消毒柜</option>
	                            <option>空气能热水器</option>
	                            <option>燃气壁挂炉</option>
	                            <option>生活电器</option>
	                            <option>空气净化器</option>
	                            <option>净水器</option>
	                            
	                            <option>太阳能热水器</option>
	                            <option>集成厨柜</option>
	                            <option>供暖系统</option>
	                            <option>散热器</option>
	                            <option>净水系统</option>
	                            <option>水槽</option>
	                            <option>电压力锅</option>
	                            <option>电磁炉</option>
	                            <option>电热水壶</option>
	                            <option>电饼铛</option>
	                            <option>电饭锅</option>
	                            <option>电风扇</option>
	                            <option>电暖器</option>
                            -->
	                         
                      </tr>
                      <tr>
                      		<td height="36" align="right"><span class="hong">* </span>产品型号：</td>
                        	<td height="36"><input name="productName" id="productSelect" type="weixiu text" class="weixiu" size="25" placeholder="请输入产品型号" /></td>
                      </tr>
                      
                      <tr>
                        <td height="36" align="right"><span class="hong">*</span> 购买日期：</td>
                        <td height="36">
                        	<!--<input name="buyDate" type="text" class="weixiu" size="25">-->
                        	<input type="text" id="buyDate" name="buyDate" class="weixiu text Wdate" 
                   				onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'visitServiceDate\')}'});" />
                        </td>
                      </tr>
                      <tr>
                        <td height="36" align="right"><span class="hong">*</span> 销售单位：</td>
                        <td height="36">
                        	<!--<input name="buyDate" type="text" class="weixiu text" size="25">-->
                        	<input type="text" id="saleCompany" name="saleCompany" class="weixiu text"  />
                        </td>
                      </tr>
                      <tr>
                        <td height="36" align="right"><span class="hong">* </span>要求服务日期：</td>
                        <td height="36">
                        	<!--<input name="visitServiceDate" type="text" class="weixiu" size="25">-->
                        	<input type="text" id="visitServiceDate" name="visitServiceDate" class="weixiu text Wdate" 
                   				onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" />
                        </td>
                      </tr>
                      <tr>
                        <td height="24" align="right" valign="top"><span class="hong">* </span>故障描述：</td>
                        <td height="150" valign="top">
                        	<textarea name="serviceDesc" id="serviceDesc" cols="50" rows="6" wrap="virtual" style="border:1px solid #ABAFB2; margin-left:15px;">
                        	</textarea>
                        </td>
                      </tr>
                      <tr>
                        <td height="40" style=" border-bottom:solid 1px #E6E6E6;"><strong>个人信息</strong></td>
                        <td style=" border-bottom:solid 1px #E6E6E6;">&nbsp;</td>
                      </tr>
                      <tr>
                        <td height="36" align="right"><span class="hong">*</span> 用户姓名：</td>
                        <td height="36"><input name="consignee" id="consignee" type="text" class="weixiu" size="25"></td>
                      </tr>
                      <tr>
                        <td height="36" align="right">邮箱地址：</td>
                        <td height="36"><input name="email" id="email" type="text" class="weixiu" size="25" value=""></td>
                      </tr>
                      <tr>
                        <td height="36" align="right"><span class="hong">*</span> 手机号码：</td>
                        <td height="36"><input name="phone" id="phone" type="text" class="weixiu" size="25"></td>
                      </tr>
                      <tr>
                        <td height="36" align="right"><span class="hong">*</span> 详细地址：</td>
                        <td height="36"><input name="address" id="address" type="text" class="weixiu" size="40"></td>
                      </tr>
                    </tbody>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                      <tr>
                        <td width="150" align="right"><span class="hong">*</span> 验证码：</td>
                        <td height="36" align="left" valign="middle">
	                          <input type="text" size="8" name="captcha" id="captcha" class="weixiu">
	                          <!--
	                          	<img src="${base}/resources/gw/images/captcha.php" alt="captcha" style="vertical-align: middle;cursor: pointer;"
	                          	 onclick="this.src=&#39;captcha.php?&#39;+Math.random()" data-bd-imgshare-binded="1">
	                          	 -->
	                          <img id="captchaImage" class="captchaImage" src="${base}/common/captcha.jhtml?captchaId=${captchaId}"
	                          		 title="${message("shop.captcha.imageTitle")}" style="vertical-align: middle;cursor: pointer;" />
	                          <span id="codeMsg" style="color:#666;font-size:10px;"></span>		 
	                    </td>
                      </tr>
                      <tr>
                        <td height="50" align="center" valign="middle"><input type="hidden" name="act" value="act_add_message"></td>
                        <td align="left" valign="middle"><input type="image" src="${base}/resources/gw/images/tjsq.jpg" value="维修预约"></td>
                      </tr>
                      <tr>
                        <td height="100" colspan="2">&nbsp;</td>
                      </tr>
                    </tbody>
                  </table>
                </form>
        <script type="text/javascript">
	        var msg_empty_email = "请输入您的电子邮件地址";
	        var msg_error_email = "电子邮件地址格式不正确";
	        var msg_title_empty = "留言标题为空";
	        var msg_content_empty = "留言内容为空";
	        var msg_captcha_empty = "验证码为空";
	        var msg_title_limit = "留言标题不能超过200个字";
	                
	        /**
	         * 提交留言信息
	        */
	        /**
	        function submitMsgBoard(frm){
	            var msg = new Object;
	             msg.user_email  = frm.elements['email'].value;
	             msg.msg_title   = frm.elements['consignee'].value;
	             msg.zp_lxfs = frm.elements['phone'].value;
	             //msg.zp_gw = frm.elements['zp_gw'].value;
	             msg.msg_content = frm.elements['serviceDesc'].value;
	             msg.captcha = frm.elements['captcha'] ? frm.elements['captcha'].value : '';
	            var msg_err = '';
	
	            if (msg.user_email.length > 0){
	               if (!(Utils.isEmail(msg.user_email))){
	                  msg_err += msg_error_email + '\n';
	                }
	             }else{
	                  msg_err += msg_empty_email + '\n';
	             }
	            if (msg.msg_title.length == 0){
	                msg_err += '姓名不能为空\n';
	            }
	            if (msg.zp_lxfs.length == 0){
	                msg_err += '联系方式不能为空\n';
	            }
	            if (frm.elements['captcha'] && msg.captcha.length==0){
	                msg_err += msg_captcha_empty + '\n'
	            }
	            if (msg.msg_content.length == 0){
	                msg_err += msg_content_empty + '\n'
	            }
	            if (msg.msg_title.length > 200){
	                msg_err += msg_title_limit + '\n';
	            }
	
	            if (msg_err.length > 0){
	                alert(msg_err);
	                return false;
	            }else{
	                return true;
	            }
	        }
	        */
        </script>
        		</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
[#include "/gw/include/footer.ftl" /]

</body>
</html>