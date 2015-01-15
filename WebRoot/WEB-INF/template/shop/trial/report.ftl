<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>	<title>试用报告填写</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
	<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${base}/resources/shop/editor/themes/default/default.css" rel="stylesheet">
	<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${base}/resources/shop/css/report.css" rel="stylesheet" type="text/css" />
	<link href="${base}/resources/shop/css/submit_report.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/common_report.js"></script>
	<link rel="stylesheet" href="${base}/resources/kindeditor-4.1.10/themes/default/default.css" />
	<link rel="stylesheet" href="${base}/resources/kindeditor-4.1.10/plugins/code/prettify.css" />
	<script charset="utf-8" src="${base}/resources/kindeditor-4.1.10/kindeditor.js"></script>
	<script charset="utf-8" src="${base}/resources/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${base}/resources/kindeditor-4.1.10/plugins/code/prettify.js"></script>
	<script type="text/javascript"> 
		$(function(){
		
			[#if member==null]
				window.location.href = "${base}/login.jhtml";
			[/#if]
			
			[#if apply == null]
				alert("该试用申请不存在!");
				window.location.href = "${base}/trial/trial_index.jhtml";
				return;
			[/#if]
			
			[#if member.username != apply.member.username]
				alert("该试用申请不属于您!");
				window.location.href = "${base}/trial/trial_index.jhtml";
				return;
			[/#if]
			
			
			var $areaId = $('#areaId');
			var $reportForm = $('#reportForm');
			var $trialApplyId = $('#trialApplyId');
			var $trialRate = $('#trialRate');
			var $reportName = $('#reportName');
			var $age = $('#age');
			var $job = $('#job');
			var $interest = $('#interest');
			var $trialRate = $('#trialRate');
			var $lookRate = $('#lookRate');
			var $funcationRate = $('#funcationRate');
			var $feelRate = $('#feelRate');
			var $costEffectiveRate = $('#costEffectiveRate');
			var $packageRate = $('#packageRate');
			var $logisticsRate = $('#logisticsRate');
			var $trialSuggestion = $('#trialSuggestion');
			var $reportDesc1 = $('#editor');
			var $submit = $(":submit");
			var $browserButton = $("#browserButton");
			var $itemWholeDocid = $('#itemWholeDocid');
			var $itemDetailDocid = $('#itemDetailDocid');
			var $browserDetailButton = $('#browserDetailButton');
			
			$browserButton.browser();
			$browserDetailButton.browser();
			
			// 地区选择
			$areaId.lSelect({
				url: "${base}/common/area.jhtml"
			});
			
			var $addrForm = $('#addressAreaId select:eq(0)');
			
			$addrForm.change(function(){
				if($areaId.val() =="" || $areaId.val() ==null || $areaId.val() == 0){
					$addrForm.siblings('label').css('display','none');
				}else{
					$addrForm.siblings('label').css('display','none');
				}
			});
			
			
			// 表单验证
			$reportForm.validate({
				rules: {
					reportName: "required",
					sex: "required",
					age: {
						required: true,
						min: 0,
						max: 160,
						decimal: {
							integer: 3,
							fraction: ${setting.priceScale}
						}
					},
					job: "required",
					areaId: "required",
					interest: "required",
					trialRate: "required",
					lookRate: "required",
					funcationRate: "required",
					feelRate: "required",
					costEffectiveRate: "required",
					packageRate: "required",
					logisticsRate: "required",
					trialSuggestion: "required"
				},
				submitHandler: function(form) {
					$.ajax({
						url: $reportForm.attr("action"),
						type: "POST",
						data: {
							trialApplyId: $.trim($trialApplyId.val()),
							reportName: $.trim($reportName.val()),
							sex: $('input[name="sex"]:checked').val(),
							age: $.trim($age.val()),
							job: $.trim($job.val()),
							areaId: $.trim($areaId.val()),
							interest: $.trim($interest.val()),
							trialRate: $.trim($trialRate.val()),
							lookRate: $.trim($lookRate.val()),
							funcationRate: $.trim($funcationRate.val()),
							feelRate: $.trim($feelRate.val()),
							costEffectiveRate: $.trim($costEffectiveRate.val()),
							packageRate: $.trim($packageRate.val()),
							logisticsRate: $.trim($logisticsRate.val()),
							trialSuggestion: $.trim($trialSuggestion.val()),
							reportDesc1: $reportDesc1.val(),
							itemWholeDocid: $.trim($itemWholeDocid.val()),
							itemDetailDocid: $.trim(itemDetailDocid.val())
						},
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								setTimeout(function() {
									$submit.prop("disabled", false);
									window.location.href = "${base}/trialReport/succeed.jhtml";
								}, 2000);
							} else if(message.type == "warn") {
								setTimeout(function() {
									$submit.prop("disabled", false);
									window.location.href = "${base}";
								}, 2000);
							} else{
								setTimeout(function() {
									$submit.prop("disabled", false);
									window.location.href = "${base}/login.jhtml";
								}, 2000);
							}
						}
					});
				}
			});	
		});
		
		function getScore(obj){
			var aLi = $(obj).parent().children('li');
			var score = parseInt($(obj).children('a').html());
			$(obj).parent().siblings('input').val(score);
			var scoreMsg = $(obj).parent().siblings('span');
			for(var i=0;i<aLi.length;i++){
				aLi[i].className = i < score ? "on" : "";
			}
			if(score == 1){
				scoreMsg.html("<strong>"+score+"&nbsp;分</strong>&nbsp;&nbsp;（非常不满 ）");
			}else if(score == 2){
				scoreMsg.html("<strong>"+score+"&nbsp;分</strong>&nbsp;&nbsp;（不满意）");
			}else if(score == 3){
				scoreMsg.html("<strong>"+score+"&nbsp;分</strong>&nbsp;&nbsp;（一般）");
			}else if(score == 4){
				scoreMsg.html("<strong>"+score+"&nbsp;分</strong>&nbsp;&nbsp;（满意）");
			}else if(score == 5){
				scoreMsg.html("<strong>"+score+"&nbsp;分</strong>&nbsp;&nbsp;（非常满意）");
			}
			
			$(obj).parent().siblings('label').css('display','none');
		}
	
	
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="reportDesc1"]', {
				cssPath : '${base}/resources/kindeditor-4.1.10/plugins/code/prettify.css',
				uploadJson : '${base}/resources/kindeditor-4.1.10/jsp/upload_json.jsp',
				fileManagerJson : '${base}/resources/kindeditor-4.1.10/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterBlur : function() {
					this.sync();
					K.ctrl(document, 13, function() {
						document.forms['example'].submit();
					});
					K.ctrl(this.edit.doc, 13, function() {
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
	</script>
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
				<strong class="title">
					试用报告
                </strong>
                <!--提交试用报告-->
                <div class="submit_report" ice:editable="*">
                <form action="${base}/trialReport/save.jhtml" method="post" id="reportForm">
                	<input type="hidden" value="${apply.id}" name="trialApplyId" id="trialApplyId"/>
	                <table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						    <th>试用者信息</th>
						    <td>&nbsp;</td>
						</tr>
						<tr>
						    <th><i>*</i>昵称：</th>
						    <td><input class="submit_report_onc" type="text" name="reportName" id="reportName"/></td>
						</tr>
						<tr>
						    <th><i>*</i>性别：</th>
						    <td>
						        <label>
						          <input type="radio" name="sex" value="M" id="RadioGroup1_0" checked/>
						          男</label>
						          　 
						        <label>
						          <input type="radio" name="sex" value="W" id="RadioGroup1_1" />
						          女</label></td>
						</tr>
						<tr>
							<th><i>*</i>年龄：</th>
							<td>
								<input class="submit_report_onc" type="text" name="age" id="age"/>
							</td>
						</tr>
						<tr>
						    <th><i>*</i>职业：</th>
						    <td><input class="submit_report_onc" type="text" name="job" id="job" /></td>
						</tr>
						<tr>
						    <th><i>*</i>居住城市：</th>
						    <td id="addressAreaId">
						    	<input type="hidden" id="areaId" name="areaId" />
						    </td>
						</tr>
						<tr>
						    <th><i>*</i>爱好及关注：</th>
						    <td><input class="submit_report_onc" name="interest" type="text" id="interest" /></td>
						</tr>
						<tr>
						    <th>试用评价：</th>
						    <td>
						    	<div class="star" id="star1">
						    		<ul>
						        		<li onclick="getScore(this);"><a>1</a></li>
						        		<li onclick="getScore(this);"><a>2</a></li>
						        		<li onclick="getScore(this);"><a>3</a></li>
						        		<li onclick="getScore(this);"><a>4</a></li>
						        		<li onclick="getScore(this);"><a>5</a></li>
						    		</ul>
						    		<input type="hidden" value="" name="trialRate" id="trialRate">
						    		<span></span>
								</div>
							</td>
						</tr>
						<tr>
						    <th><i>*</i>外观做工：</th>
						    <td>
						    	<div class="star" id="star2">
								    <ul>
								        <li onclick="getScore(this);"><a>1</a></li>
								        <li onclick="getScore(this);"><a>2</a></li>
								        <li onclick="getScore(this);"><a>3</a></li>
								        <li onclick="getScore(this);"><a>4</a></li>
								        <li onclick="getScore(this);"><a>5</a></li>
								    </ul>
								    <input type="hidden" value="" name="lookRate" id="lookRate">
						    		<span></span>
								</div>
							</td>
						</tr>
						<tr>
						    <th><i>*</i>功能操作：</th>
						    <td>
						    	<div class="star" id="star3">
								    <ul>
								        <li onclick="getScore(this);"><a>1</a></li>
								        <li onclick="getScore(this);"><a>2</a></li>
								        <li onclick="getScore(this);"><a>3</a></li>
								        <li onclick="getScore(this);"><a>4</a></li>
								        <li onclick="getScore(this);"><a>5</a></li>
								    </ul>
								    <input type="hidden" value="" name="funcationRate" id="funcationRate">
						    		<span></span>
								</div>
							</td>
						</tr>
						<tr>
						    <th><i>*</i>使用感受：</th>
						    <td>
						    	<div class="star" id="star4">
								    <ul>
								        <li onclick="getScore(this);"><a>1</a></li>
								        <li onclick="getScore(this);"><a>2</a></li>
								        <li onclick="getScore(this);"><a>3</a></li>
								        <li onclick="getScore(this);"><a>4</a></li>
								        <li onclick="getScore(this);"><a>5</a></li>
								    </ul>
								    <input type="hidden" value="" name="feelRate" id="feelRate">
						   	 		<span></span>
								</div>
							</td>
						</tr>
						<tr>
						    <th><i>*</i>商品性价比：</th>
						    <td>
						    	<div class="star" id="star5">
								    <ul>
								        <li onclick="getScore(this);"><a>1</a></li>
								        <li onclick="getScore(this);"><a>2</a></li>
								        <li onclick="getScore(this);"><a>3</a></li>
								        <li onclick="getScore(this);"><a>4</a></li>
								        <li onclick="getScore(this);"><a>5</a></li>
								    </ul>
								    <input type="hidden" value="" name="costEffectiveRate" id="costEffectiveRate">
						    		<span></span>
								</div>
							</td>
							<tr>
							    <th><i>*</i>商品包装：</th>
							    <td>
							    	<div class="star" id="star6">
									    <ul>
									        <li onclick="getScore(this);"><a>1</a></li>
									        <li onclick="getScore(this);"><a>2</a></li>
									        <li onclick="getScore(this);"><a>3</a></li>
									        <li onclick="getScore(this);"><a>4</a></li>
									        <li onclick="getScore(this);"><a>5</a></li>
									    </ul>
									    <input type="hidden" value="" name="packageRate" id="packageRate">
									    <span></span>
									</div>
								</td>
							</tr>
							<tr>
							    <th><i>*</i>物流服务：</th>
							    <td>
							    	<div class="star" id="star7">
									    <ul>
									        <li onclick="getScore(this);"><a>1</a></li>
									        <li onclick="getScore(this);"><a>2</a></li>
									        <li onclick="getScore(this);"><a>3</a></li>
									        <li onclick="getScore(this);"><a>4</a></li>
									        <li onclick="getScore(this);"><a>5</a></li>
									    </ul>
									    <input type="hidden" value="" name="logisticsRate" id="logisticsRate">
									    <span></span>
									</div>
								</td>
							</tr>
							<tr>
							    <th valign="top"><i>*</i>亮点推荐建议：</th>
							    <td align="right">
							    	<textarea  class="submit_report_twic" name="trialSuggestion" id="trialSuggestion"></textarea>
							        <p> 剩<span>180</span>字</p>
							    </td>
							</tr>
							<tr>
							    <th colspan="2" align="left"><div style="font-size:14px; float:left;">试用过程与体验</div></th>
							</tr>
							<tr>
								<th valign="top">商品整体图：</th>
							    <td>
							    	<div class="submit_report_pic"></div>
							        <div class="pic_format">
							            (图片大小300内)
							            <br>
							            <a href="javascript:void(0)"  onclick = "document.getElementById('light_zt').style.display='block';document.getElementById('fade').style.display='block'">图片案例</a>  
									    <!--弹出框内容-->
										<div id="light_zt" class="car_white_content">
											<div class="t_neirong">
												整体案例图片
												<div class="t_guanbi">
													<a href = "javascript:void(0)"  onclick =  "document.getElementById('light_zt').style.display='none';document.getElementById('fade').style.display='none'">
														X
													</a>
												</div>
											</div>
										</div>
										 <span class="fieldSet">
											<input type="text" name="itemWholeDocid" class="text" maxlength="200" id="itemWholeDocid"/>
											<input type="button" id="browserButton" class="button" value="${message("admin.browser.select")}" />
										</span>
							        </div>
							 	</td>
							 </tr>
						     <tr>
						   	     <th valign="top">商品细节图：</th>
						         <td>
						           <div class="submit_report_pic"></div>
						           <div class="pic_format">
						                (图片大小300k内)
						                <br>
						                <a href="javascript:void(0)"  onclick = "document.getElementById('light_bx').style.display='block';document.getElementById('fade').style.display='block'">
						                                                  图片案例
						                </a>  
										<!--弹出框内容-->
										<div id="light_bx" class="car_white_content">
											<div class="t_neirong">
												细节案例图片
												<div class="t_guanbi">
													<a href = "javascript:void(0)"  onclick =  "document.getElementById('light_bx').style.display='none';document.getElementById('fade').style.display='none'">
														X
													</a>
												</div>
											</div>
										</div>
						           		 <span class="fieldSet">  
									        <input type="text" name="itemDetailDocid" class="text" maxlength="200"  id="itemDetailDocid"/>
											<input type="button" id="browserDetailButton" class="button" value="${message("admin.browser.select")}" />
							 			</span>
						           	</div>
						        </td>
						    </tr>
							<tr>
							    <th valign="top"><i>*</i>试用过程说明：</th>
							    <td></td>
							</tr>
							<tr>
								<td colspan="2">
									<table class="input tabContent">
										<tr>
											<td>
												<textarea id="editor" name="reportDesc1" class="editor" style="width: 100%;"></textarea>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th>&nbsp;</th>
							    <td><input type="submit" class="submit_report_button" value="提交试用报告" /></td>
							</tr>
						</table>
					</form>
				</div>
			</div>	
			<div class="side">
				<strong class="title">试用商品</strong>
				<div class="pro_img">
					<a href="${base}/trial/content/${apply.trial.id}.jhtml">
						<img src="${apply.trial.image}" alt="${product.name} " />
					</a>
				</div>
				<div class="pro_data">
					<strong class="pro_name" title="沁园净水器 BCD-225SFVG-ES ">
						<a href="${base}/trial/content/${apply.trial.id}.jhtml">
							${apply.product.name}
						</a>
					</strong>
					<span class="pro_type">${apply.product.sn}</span>
		    		<span class="price">预计市场价：
		    			<em class="redText">
            				<strong>${currency(apply.trial.marketprice,true)}</strong>
            			</em>
            		</span>	
            		<span >
            			<a href="${base}/trial/content/${apply.trial.id}.jhtml">
              				<input class="saveTrialApplyLink" type="button" value="查看产品详情" style="cursor:pointer;"/>
              			</a>
              		</span>
	   			</div>
       			<div class="sybg_declaration">
         			<strong class="title">宣言</strong>
     				<div class="sybg_declaration_infor">
      					${apply.applyReason}
       				</div>
     			</div>
        	</div>	
			<div class="cl"></div>
		</div>
	</div>
    <div id="fade" class="car_black_overlay"></div>
    [#include "/shop/include/footer.ftl" /]
	</body>
</html>

