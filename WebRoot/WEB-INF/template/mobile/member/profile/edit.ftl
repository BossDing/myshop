<!DOCTYPE html>
<html>
	<head>
		<title>修改个人信息</title> 
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="${base}/resources/mobile/css/person_detail.css" />
		<link rel="stylesheet" type="text/css" href="${base}/resources/mobile/css/common.css" />
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.lSelect.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/jquery.validate.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
		<script type="text/javascript" src="${base}/resources/mobile/js/datePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$().ready(function(){
				//判断当前是否存在用户
				if (!$.checkLogin()) {
					$.redirectLogin(window.location.href, "${message("shop.common.mustLogin")}");
					return false;
				}
				var $inputForm = $("#inputForm");
				var $areaId = $("#areaId");
				var $submit = $("#submit");
				var $inputForm = $("#inputForm");
				
				[@flash_message /]
				// 地区选择
				$areaId.lSelect({url: "${base}/common/area.jhtml"});
				
				$submit.click(function() {
					$.ajax({
						url: "${base}/mobile/member/profile/updateInfo.jhtml",
						type: "POST",
						data: $inputForm.serialize(),
                		dataType: "json",
						success: function(data) {
                			$.message(data);
                			var taskId = window.setTimeout(function(){
                				window.location.href = "${base}/mobile/member/index.jhtml";
                			},1000);
						}
					});
				});
			});
			/**删除输入框中的数据*/
			function toDelete (id){//对应输入框的id
				var $obj = $("#"+id);
				$obj.val('');//置空
			}
			/**显示清除按钮*/
			function isVisiblle(id, flag){
				var $objSpan = $("#"+id+ " + span");//层次，选择器下一个兄弟
				var $obj = $("#"+id);//层次，选择器下一个兄弟
				var regEx = /^\s*$/;
				if(flag && !regEx.test($obj.val())) //输入框不为空
					$objSpan.show();
				else 
					$objSpan.hide();
			}
		</script>
	</head>
	<body>
		[#include "/mobile/include/header.ftl" /]
	
		[#assign current = "profileEdit" /]
		<div class="Big">
		    <div class="Call">个人资料</div>
		    <div class="Call-border"></div>
		    <div class="KK">
		    	<form id="inputForm">
		    	<table>
		        	<tr>
		            	<td class="left-1">${message("Member.email")}</td>
		                <td class="right-2" onmouseover="isVisiblle('email',true);" onmouseout="isVisiblle('email',false);">
		                	<input type="text" value="${member.email}" name="email" id="email" class="table_input" />
		                	<span class="no" onclick="toDelete('email');">
			                		<img src="${base}/resources/mobile/images/w_10.png">
			                </span>
		                </td>
		        	</tr>
		        	[#list memberAttributes as memberAttribute]
			            <tr>
			            	<td class="left-1">${memberAttribute.name}</td>
			                <td class="right-2">
			                	[#if memberAttribute.type == "name"]
			                	<span class="input_sp" onmouseover="isVisiblle('name',true);" onmouseout="isVisiblle('name',false);">
			                	<input type="text" value="${member.name}" id="name" name="memberAttribute_${memberAttribute.id}" class="table_input"/>
			                	<span class="no" onclick="toDelete('name');">
			                		<img src="${base}/resources/mobile/images/w_10.png">
			                	</span>
			                	</span>
			                	[#elseif memberAttribute.type == "gender"]
			                		<span class="fieldSet">
										[#list genders as gender]
											<label>
												<input type="radio" name="memberAttribute_${memberAttribute.id}" value="${gender}"[#if gender == member.gender] checked="checked"[/#if] />${message("Member.Gender." + gender)}
											</label>
										[/#list]
									</span>
								[#elseif memberAttribute.type == "birth"]
									<input type="text" name="memberAttribute_${memberAttribute.id}" value="${member.birth}" onfocus="WdatePicker();" class="table_input"/>
								[#elseif memberAttribute.type == "area"]
									<span class="fieldSet" id="area_select">
										<input type="hidden" id="areaId" name="memberAttribute_${memberAttribute.id}" value="${(member.area.id)!}" treePath="${(member.area.treePath)!}" class="table_input"/>
									</span>
								[#elseif memberAttribute.type == "address"]
								<span class="input_sp" onmouseover="isVisiblle('address',true);" onmouseout="isVisiblle('address',false);">
									<input type="text" id="address" name="memberAttribute_${memberAttribute.id}" value="${member.address}"  class="table_input"/>
									<span class="no" onclick="toDelete('address');">
			                			<img src="${base}/resources/mobile/images/w_10.png">
			               			</span>
			               		</span>
								[#elseif memberAttribute.type == "zipCode"]
								<span class="input_sp" onmouseover="isVisiblle('zipCode',true);" onmouseout="isVisiblle('zipCode',false);">
									<input type="text" id="zipCode" name="memberAttribute_${memberAttribute.id}" value="${member.zipCode}" class="table_input"/>
									<span class="no" onclick="toDelete('zipCode');">
			                			<img src="${base}/resources/mobile/images/w_10.png">
			               			</span>
			               		</span>
								[#elseif memberAttribute.type == "phone"]
								<span class="input_sp" onmouseover="isVisiblle('phone',true);" onmouseout="isVisiblle('phone',false);">
									<input type="text" id="phone" name="memberAttribute_${memberAttribute.id}" value="${member.phone}" class="table_input"/>
									<span class="no" onclick="toDelete('phone');">
			                			<img src="${base}/resources/mobile/images/w_10.png">
			               			</span>
			               		</span>
								[#elseif memberAttribute.type == "mobile"]
								<span class="input_sp" onmouseover="isVisiblle('mobile',true);" onmouseout="isVisiblle('mobile',false);">
									<input type="text" id="mobile" name="memberAttribute_${memberAttribute.id}" value="${member.mobile}" class="table_input"/>
									<span class="no" onclick="toDelete('mobile');">
			                			<img src="${base}/resources/mobile/images/w_10.png">
			               			</span>
			               		</span>
								[#elseif memberAttribute.type == "text"]
								<span class="input_sp" onmouseover="isVisiblle('text',true);" onmouseout="isVisiblle('text',false);" >
									<input type="text" id="text" name="memberAttribute_${memberAttribute.id}" value="${member.getAttributeValue(memberAttribute)}" class="table_input" />
									<span class="no" onclick="toDelete('text');">
			                			<img src="${base}/resources/mobile/images/w_10.png">
			               			</span>
			               		</span>
								[#elseif memberAttribute.type == "select"]
									<select name="memberAttribute_${memberAttribute.id}">
										<option value="">${message("shop.common.choose")}</option>
										[#list memberAttribute.options as option]
											<option value="${option}"[#if option == member.getAttributeValue(memberAttribute)] selected="selected"[/#if]>
												${option}
											</option>
										[/#list]
									</select>
								[#elseif memberAttribute.type == "checkbox"]
									<span class="fieldSet">
										[#list memberAttribute.options as option]
											<label>
												<input type="checkbox" name="memberAttribute_${memberAttribute.id}" value="${option}"[#if (member.getAttributeValue(memberAttribute)?seq_contains(option))!] checked="checked"[/#if] class="table_input" />${option}
											</label>
										[/#list]
									</span>
			                	[/#if]
			                </td>
			        	</tr>
		        	[/#list]
		        </table>
		        </form>
		    </div>
		    <!--
		    <span class="err_msg1" id="err_msg1"></span>
		    <span class="err_msg2" id="err_msg2"></span>
		    -->
		    <a href="javascript:void(0);" id="submit"><div class="Big-denglu">确认修改</div></a>
		</div>
	</body>
</html>
  