<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>	<title>申请试用</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />

<link href="${base}/resources/shop/css/sqtx.css" rel="stylesheet" type="text/css" />
</head>

	<body>
	[#include "/shop/include/header.ftl" /]
	<!-- main content -->	
	<div class="main-content">
	  <div class="trial_step">
						



	<dl>
		<dt>新品免费试用流程</dt>
		<dd class="step1">提交试用申请</dd>
		<dd class="step2">等待沁园审核</dd>
		<dd class="step3">获得试用商品</dd>
		<dd class="step4">提交试用报告</dd>
	</dl>


	  </div>

					<div class="np_apply_box">
						<div class="main">
							<strong class="title">确认收货信息</strong>
							<form id="atg_store_trial_apply_add" action="" class="js_mmjs_validation" method="post"><div style="display:none"><input name="_dyncharset" value="UTF-8" type="hidden"/> </div><div style="display:none"><input name="_dynSessConf" value="-612520167267714885" type="hidden"/> </div>
	<input name="/ehaier/store/trial/formhandler/NewProductTrialApplyFormHandler.newProductTrialId" value="prod150557" type="hidden"/><input name="_D:/ehaier/store/trial/formhandler/NewProductTrialApplyFormHandler.newProductTrialId" value=" " type="hidden"/>
	<input name="/ehaier/store/trial/formhandler/NewProductTrialApplyFormHandler.submitApplySuccessURL" value="trialApplySuccessful.jsp?pName=沁园冰箱 BCD-225SFVG-ES &amp;pCat=冰箱冷柜" type="hidden"/><input name="_D:/ehaier/store/trial/formhandler/NewProductTrialApplyFormHandler.submitApplySuccessURL" value=" " type="hidden"/>
	<input name="/ehaier/store/trial/formhandler/NewProductTrialApplyFormHandler.submitApplyErrorURL" value="trialApplyForm.jsp?newProductTrialId=prod150557" type="hidden"/><input name="_D:/ehaier/store/trial/formhandler/NewProductTrialApplyFormHandler.submitApplyErrorURL" value=" " type="hidden"/>
								<div class="addrBox">
										

	<div class="addrNew new_addr" id="addrNew" style="display: block">

		<div class="addrForm js_mmjs_validation_fe_wrap">
			<label>收货人：</label>
			<div class="addrFormItem">
				<input id="reveiverName" title="请输入收货人|请输入2至20个字符，且不能包含数字或特殊字符" data-jsv_preg="/^[^\u0021-\u0022\u0024-\u0040\u005b-\u0060\u007b-\u007f\￥]{2,20}$/" name="name" value="" class="jsv_required jsv_preg" type="text"/><input name="_D:name" value=" " type="hidden"/>
			</div>
			<div class="cn js_validation_marked_info"></div>
		</div>

		<div class="addrForm js_mmjs_validation_fe_wrap">
			<label>所在区域：</label>
			<div class="addrFormItem">	
	<input name="" value=" " type="hidden"></input><select id="region_province" title="请选择省份" name="/ehaier/store/trial/formhandler/NewProductTrialApplyFormHandler.editValue.state" class="jsv_required">
		<option value="">
	请选择省
	</option>	
	<option value="10">
	上海
	</option>	
	<option value="11">
	江苏
	</option>
	<option value="16">
	山东
	</option>

	</select>
		
	<input name="" value=" " type="hidden"></input><select id="region_city" title="请选择市" name="" class="jsv_required">
	<option value="">
	请选择市/区
	</option>
	</select>

	<input name="" value=" " type="hidden"></input><select id="region_county" title="请选择区/县/街道" name="" class="jsv_required">
	<option value="">
	请选择区/县/街道
	</option>
	</select>
	
	
			</div>
			<div class="cn js_validation_marked_info"></div>
		</div>

		<div class="addrForm js_mmjs_validation_fe_wrap">
			<label>详细地址：</label>
			<div class="addrFormItem">
				<p class="">
					<span id="show_province"></span> <span id="show_city"></span> <span
						id="show_county"></span>
				</p>
				<input id="detailAddress" title="请输入收货地址|请输入1至50个字符，且不能为特殊字符" data-jsv_preg="" name="" value="" class="iptSpe jsv_required jsv_preg" type="text"/><input name="" value=" " type="hidden"/>
			</div>
			<div class="cn js_validation_marked_info"></div>
		</div>

		<div class="cellphone_phone">
			<div class="addrForm js_mmjs_validation_fe_wrap">
				<label>联系方式：</label>
				<div class="addrFormItem">
					<input id="mobileNumber" title=" " name="" value="" class="jsv_required jsv_contact" type="text"/><input name="" value=" " type="hidden"/>
				</div>
				<div class="cn js_validation_marked_info"></div>
			</div>
			<div class="cl"></div>
		</div>

	</div>


								</div>

								<div class="js_text_limit" data-text_limit_num="800">
									<strong class="title">申请理由<span
										class="reason_copy_num">您还可输入<em class="js_text_limit_hint">180</em> 字
									</span></strong>
									<div class="reason_box js_mmjs_validation_fe_wrap">
										<input name="" value=" " type="hidden"></input><textarea id="applyReason" title="请填写申请理由|申请理由包含非法标签" data-jsv_preg="/^[^<>&]+$/" name="reason" onblur="" class="reason_txt jsv_required js_text_limit_fe" onfocus=""></textarea>
										<input type="hidden" id="applyReasonPrompt" value="">
									</div>
									<div class="js_mmjs_validation_fe_wrap" style="
padding: 10px 15px;">
										<input id="user_policy" class="fe_nb jsv_required"
											type="checkbox" title="请仔细阅读《用户服务协议》并确认同意所有条款"
											name="agreement"> <label for="user_policy">
											我已经仔细阅读 <a target="_blank"
											href="#">《用户服务协议》</a>
											并同意所有条款
										</label>
										
									</div>


									<div class="btns" style="
padding: 10px 15px;
">
										<a href="#" class="saveTrialApplyLink"><span>提交申请</span></a>
									</div>
									<input name="" value="" type="hidden"/><input name="" value=" " type="hidden"/>
	</div>	<div style="display:none"><input name="_DARGS" value="" type="hidden"/> </div></form>
	</div>	
	
	
		<div class="side">
			<strong class="title">试用商品</strong>
			<div class="pro_img">
				<a href="#">
					<img
						src="../images/big_pic_1.jpg"
						alt="沁园冰箱 BCD-225SFVG-ES " />
				</a>
			</div>
			<div class="pro_data">

				<strong class="pro_name" title="沁园净水器 BCD-225SFVG-ES "><a href="#">
	沁园净水器 BCD-225SFVG-ES 
	</a></strong> <span class="pro_type">BCD-225SFVG-ES </span>
				<span class="price">预计市场价：<em class="redText"><strong>							
	
	￥2599	
	</strong></em></span>	
	</div>	</div>	
	
						<div class="cl"></div>
					</div>
	</div>
	</body>
</html>

