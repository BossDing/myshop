<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.afterBooking.edit")} - Powered By SHOP++</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
<style type="text/css">
	.specificationSelect {
		height: 100px;
		padding: 5px;
		overflow-y: scroll;
		border: 1px solid #cccccc;
	}
	
	.specificationSelect li {
		float: left;
		min-width: 150px;
		_width: 200px;
	}
</style>
<script type="text/javascript">
   $(function(){
        if('${afterBooking.state}'=='1'){
        	$("#finishDate1").show();	
        }
        var $areaId =$("#areaId");
	    $areaId.lSelect({
			url: "${base}/common/area.jhtml"
	    });
   });
   function changeState(_this,id){
      var state = $(_this).val();
      if(state=="1"){
       $("#"+id).show();
     }else{
       $("#"+id+" input").val("");
       $("#"+id).show();
     }
   }
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 修改预约
	</div>
	<form id="inputForm" action="update.jhtml" method="post">
		<input type="hidden" name="id" value="${afterBooking.id}" />
		<input type="hidden" name="type" value="${(afterBooking.type)!'没有类型'}" />
		<input type="hidden" name="sn" value="${afterBooking.sn}" />
		<input type="hidden" name="source" value="${afterBooking.source}">
		<input type="hidden" name="entCode" value="${afterBooking.entCode}">
		<table class="input">
			<tr>
				<th>
					预约类型:
				</th>
				<td>
					[#if afterBooking.type=='install']安装预约[#elseif  afterBooking.type=='repair']维修预约[/#if]
				</td>
			</tr>
			<tr>
				<th>
					预约来源:
				</th>
				<td>
					[#if afterBooking.source??]
							[#if afterBooking.source == 0]
								<span title="万家乐商城">商城</span>
							[#elseif afterBooking.source==1]
								<span title="万家乐官网">官网</span>
							[/#if]
				   [/#if]
				</td>
			</tr>
			<tr>
				<th>
					编号:
				</th>
				<td>
				    ${afterBooking.sn}
				</td>
			</tr>
			<tr>
				<th>
					预约人：
				</th>
				<td>
				   <input type="text" name="consignee" value="${afterBooking.consignee}">
				</td>
			</tr>
			<tr>
				<th>
					产品类别：
				</th>
				<td>
				   ${afterBooking.productCategory.name}
				</td>
			</tr>
			<tr class="input">
				<th>
					[#if afterBooking.type=='install']安装产品型号[#elseif  afterBooking.type=='repair']维修产品型号[/#if]:
				</th>
				<td>
				    <div>
				    <ul>
						<!--<li>&nbsp;${afterBooking.product.sn}&nbsp;${afterBooking.product.name}</li>-->
						<li>${afterBooking.productName}</li>
					</ul>
					</div>
				</td>
			</tr>
			<tr class="ser">
				<th>
					手机号码：
				</th>
				<td>
				  <input type="text"  name="phone" class="text" value="${afterBooking.phone}"/>
				</td>
			</tr>
			<tr>
				<th>
					邮箱:
				</th>
				<td>
					<input type="text" name="email" class="text" value="${afterBooking.email}"  />
				</td>
			</tr>
	<!--
			<tr>
				<th>
					所在地区:
				</th>
				<td>
					 <span class="fieldSet">
						<input type="hidden" id="areaId" name="areaId" value="${(afterBooking.area.id)!}"  treePath="${(afterBooking.area.treePath)!}" />
					</span>
				</td>
			</tr>
	-->
			<tr>
				<th>
					详细地址:
				</th>
				<td>
					<input type="text" name="address" class="text" value="${afterBooking.address}"  />
				</td>
			</tr>
			<tr>
				<th>
					购买时间：
				</th>
				<td>
				  <input type="text" id="buyDate" name="buyDate" class="text Wdate" value="${(afterBooking.buyDate?string.medium)!}"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'buyDate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					销售单位：
				</th>
				<td>
				   ${afterBooking.saleCompany}
				</td>
			</tr>
			<tr class="ser">
				<th>
					要求服务时间：
				</th>
				<td>
				  <input type="text" id="visitServiceDate" name="visitServiceDate" class="text Wdate" value="${(afterBooking.visitServiceDate?string.medium)!}"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'visitServiceDate\')}'});" />
				</td>
			</tr>
			[#if afterBooking.type=='install']
			<tr>
				<th>
					要求服务描述:
				</th>
				<td>
					<textarea style="width:200px;height:100px;max-width:200px;max-height:100px;" name="serviceDesc">${afterBooking.serviceDesc}</textarea>
				</td>
			</tr>
	<!--
			<tr class="ser">
				<th>
					配件需求:
				</th>
				<td>
					<input type="text" name="parts" class="text" value="${afterBooking.parts}"  />
				</td>
			</tr>
			<tr class="ser">
				<th>
					其他需求:
				</th>
				<td>
					<textarea style="width:200px;height:100px;max-width:200px;max-height:100px;" name="content">${afterBooking.content}</textarea>
				</td>
			</tr>
	-->		
			[#elseif afterBooking.type=='repair']
	<!--
			<tr>
				<th>
					故障时间：
				</th>
				<td>
				  <input type="text" id="faultDate" name="faultDate" class="text Wdate" value="${(afterBooking.faultDate?string.medium)!}"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'faultDate\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					故障类型:
				</th>
				<td>
					<input type="text" name="faultType" class="text" value="${afterBooking.faultType}"  />
				</td>
			</tr>
	-->
			<tr>
				<th>
					故障描述:
				</th>
				<td>
					<textarea style="width:200px;height:100px;max-width:200px;max-height:100px;" name="serviceDesc">${afterBooking.serviceDesc}</textarea>
				</td>
			</tr>
			[/#if]
			<tr>
				<th>
					当前状态：
				</th>
				<td>
					<select name="state" onchange="changeState(this,'finishDate1')" >
					   <option value="0" [#if afterBooking.state=="0"]selected="selected"[/#if]>未完成</option>
					   <option value="1" [#if afterBooking.state=="1"]selected="selected"[/#if]>完成</option>
					</select>
				</td>
			</tr>
			<tr style="display:none;" id="finishDate1">
				<th>
					完成时间：
				</th>
				<td>
				  <input type="text"  id="finishDateCur1" name="finishDate" class="text Wdate" value="${(afterBooking.finishDate?string.medium)!}"  onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate: '#F{$dp.$D(\'finishDateCur1\')}'});" />
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					[#if afterBooking.type=='repair']
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml?label=1'" />
					[#elseif afterBooking.type=='install']
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list2.jhtml?label=0'" />
					[/#if]
				</td>
			</tr>
		</table>
	</form>
	
</body>
</html>