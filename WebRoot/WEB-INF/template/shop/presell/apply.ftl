<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>预售登记</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="description" content="" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${base}/resources/shop/css/presellApply.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
	<script type="text/javascript">
		
		
		$().ready(function() {
		
			[#if member==null]
				window.location.href = "${base}/login.jhtml";
			[/#if]
			var $areaId = $("#areaId");
			var $address = $('#detailAddress');
			var $applierName = $('#applierName');
			var $applierMobile = $('#applierMobile');
			
			var $submit = $(":submit");
			var $remarkInformation = $('#remarkInformation');
			var $saveTrialApplyLink = $('#saveTrialApplyLink');
			var $atg_store_trial_apply_add = $('#atg_store_trial_apply_add');
			var $product = $('#product');
			var $preSellRole = $('#preSellRole');
			var $user_policy = $('#user_policy');
			var $limit = $('#limit');
			
			var str = "";
			// 地区选择
			$areaId.lSelect({
				url: "${base}/common/area.jhtml"
			});
			
			var $addrForm = $('#addrForm select:eq(0)');
			
			$addrForm.change(function(){
				if($areaId.val() =="" || $areaId.val() ==null || $areaId.val() == 0){
					$addrForm.siblings('label').css('display','block');
				}else{
					$addrForm.siblings('label').css('display','none');
				}
			});
			
			
		$remarkInformation.keyup(function(){
			var text = String($remarkInformation.val());
			var lastLen = 180 - text.length;
			if(lastLen <= 0){
				text = text.substring(0,180);
				$remarkInformation.val(text);
				lastLen = 0;
			}
			$limit.html(lastLen);
		});
		
		$remarkInformation.keydown(function(){
			var text = String($remarkInformation.val());
			var lastLen = 180 - text.length;
			if(lastLen <= 0){
				text = text.substring(0,180);
				$remarkInformation.val(text);
				lastLen = 0;
			}
			$limit.html(lastLen);
		});
			
			
		
		// 表单验证
		$atg_store_trial_apply_add.validate({
			rules: {
				applierName: "required",
				areaId: "required",
				address: "required",
				applierMobile: "required",
				remarkInformation: {
					required:true,   
					maxlength:180
				}
			},
			submitHandler: function(form) {
				if($user_policy.attr('checked')=="checked"){
					$.ajax({
						url: $atg_store_trial_apply_add.attr("action"),
						type: "POST",
						data: {
							applierName: $.trim($applierName.val()),
							areaId:$.trim($areaId.val()),
							productId:$.trim($product.val()),
							preSellRoleId:$.trim($preSellRole.val()),
							remarkInformation: $.trim($remarkInformation.val()),
							applierMobile: $.trim($applierMobile.val()),
							address: $.trim($address.val()),
						},
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								setTimeout(function() {
									$submit.prop("disabled", false);
									window.location.href = "${base}/preSellApply/succeed.jhtml";
								}, 2000);
							}else if (message.type == "warn") {
								setTimeout(function() {
									$submit.prop("disabled", false);
									window.location.href = "${base}/trial/trial_index.jhtml";
								}, 2000);
							} else {
								setTimeout(function() {
									$submit.prop("disabled", false);
									window.location.href = "${base}/login.jhtml";
								}, 2000);
							}
						}
					});
				}else{
					return;
				}
			}
		});	
		
		
		
		});
	</script>
</head>

<body>
[#include "/shop/include/header.ftl" /]
    <!--面包屑  -->
    <div class="crumbs">
        <span>商城首页</span>
        <b>></b>
        <span>新品预售</span>
        <b>></b>
        <span class="wdwz">预售申请</span>
    </div>
    <!--面包屑  end-->
    
    <!-- main content -->
    <div class="main-content">
        <div class="trial_step">
            <dl>
                <dt>新品抢先售流程</dt>
                <dd class="step1">参与预约</dd>
                <dd class="step2">参与抢购</dd>
                <dd class="step3">抢购成功</dd>
                <dd class="step4">下单配送</dd>
            </dl>
        </div>

		[#if preSellRole.products?has_content]
			[#list preSellRole.products as product]
		        <div class="np_apply_box">
		            <div class="main">
		                <strong class="title">个人信息填写</strong>
		                <form id="atg_store_trial_apply_add" name="atg_store_trial_apply_add" action="${base}/preSellApply/save.jhtml" class="js_mmjs_validation" method="post">
							<input id="product" name="productId" value="${product.id}" type="hidden"/>
							<input id="preSellRole" name="preSellRoleId" value="${preSellRole.id}" type="hidden"/>
		                    <div class="addrBox">
		                        <div class="addrNew new_addr" id="addrNew" style="display: block">
		                            <div class="addrForm js_mmjs_validation_fe_wrap">
		                                <label>
		                                	申请人：
		                                </label>
		                                <div class="addrFormItem">
		                                    <input id="applierName" type="text" name="applierName" value="" />
		                                </div>
		                                <div class="cn js_validation_marked_info"></div>
		                            </div>
		                            <div class="addrForm js_mmjs_validation_fe_wrap">
		                                <label>
											所在区域：
										</label>
		                                <div class="addrFormItem" id="addrForm">
		                                    <input id="areaId" name="areaId" value="" type="hidden"></input>
		                                </div>
		                                <div class="cn js_validation_marked_info"></div>
		                            </div>
		
		                            <div class="addrForm js_mmjs_validation_fe_wrap">
		                                <label>
		                                	详细地址：
		                                </label>
		                                <div class="addrFormItem">
		                                    <input id="detailAddress" type="text" name="address" value="" class="iptSpe jsv_required jsv_preg"
		                                    title="请输入收货地址|请输入1至50个字符，且不能为特殊字符" 
		                                    data-jsv_preg=""   />
		                                </div>
		                                <div class="cn js_validation_marked_info"></div>
		                            </div>
		
		                            <div class="cellphone_phone">
		                                <div class="addrForm js_mmjs_validation_fe_wrap">
		                                    <label>
		                                    	联系方式：
		                                    </label>
		                                    <div class="addrFormItem">
		                                        <input id="applierMobile"  name="applierMobile" value="" class="jsv_required jsv_contact" type="text" />
		                                    </div>
		                                    <div class="cn js_validation_marked_info"></div>
		                                </div>
		                                <div class="cl"></div>
		                            </div>
		                        </div>
		                    </div>
		                    <div class="js_text_limit" data-text_limit_num="800">
		                        <strong class="title">备注信息
			                        <span class="reason_copy_num">
			                        	您还可输入<em class="js_text_limit_hint" id="limit">180</em> 字
									</span>
								</strong>
		                        <div class="reason_box js_mmjs_validation_fe_wrap">
		                        	<textarea id="remarkInformation" class="reason_txt jsv_required js_text_limit_fe" onfocus="" name="remarkInformation"></textarea>
		                        </div>
		                        <div class="js_mmjs_validation_fe_wrap" style="padding: 10px 15px;">
		                            <input id="user_policy" class="fe_nb jsv_required"
		                                type="checkbox" title="请仔细阅读《用户服务协议》并确认同意所有条款"
		                                name="agreement">
		                            <label for="user_policy">
		                                我已经仔细阅读 <a target="_blank"
		                                    href="#">《用户服务协议》</a>
		                                并同意所有条款
		                            </label>
		                        </div>
		                        <div class="btns" style="padding: 10px 15px;">
		                            <input type="submit" style="cursor:pointer;" id="saveTrialApplyLink" value="提交申请" />
		                        </div>
		                    </div>
		                </form>
		            </div>
		
		
		            <div class="side">
		                <strong class="title">预约商品</strong>
		                <div class="pro_img">
		                    <a href="${base}${product.path}">
		                        <img src="${product.image}" alt="${product.name} " />
		                    </a>
		                </div>
		                <div class="pro_data">
		                    <strong class="pro_name" title="${product.name} ">
		                    	<a href="${base}${product.path}">${product.name}</a>
							</strong>
							<span class="pro_type">${product.sn}</span>
		                    <span class="price">预计市场价：
		                    	<em class="redText">
		                    		<strong>	￥
				                      	  ${product.marketPrice}
				                    </strong>
		                    	</em>
		                    </span>
		                </div>
		            </div>
		            <div class="cl"></div>
		        </div>
        	[/#list]
		[/#if]
    </div>
    [#include "/shop/include/footer.ftl" /]
</body>
</html>
