<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/indiv_after_sales.css"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>售后列表</title>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
	<script type="text/javascript" src="${base}/resources/admin/datePicker/WdatePicker.js"></script>
	</head>

	<body>
	 [#include "/shop/include/header.ftl" /]
	 <div class="where-big">
		  <div id="index_place" style="width:400px;float:left">您的位置：<a href="/"><span>首页</span></a>&gt;<a href="${base}/member/index.jhtml"><span>个人中心</span></a>&gt;<span>售后列表</span></div>
			  <div class="kfdlb">
				  <ul>
					  <li>分享到：</li>
			          <li><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
			          <li><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
			          <li><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
			          <li><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
			          <!--<li><a href="#"><img src="/resources/shop/images/xinxi_ico.png" width="17px" ;=""></a></li>-->
			          <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
				  </ul>
	  			</div>   
			</div>
	
	<div class="content">
		<!--左边导航-->
		  [#include "/shop/member/include/navigation.ftl" /]
		<!--我的订单-->
		<form id=listForm action="${base}/member/afterSale/list.jhtml" method="get">
		   <div class="indiv_content">
			   <div class="indiv_car">
			         <div id="my_order">售后列表</div>
			         <div class="means">
				           <p>
					           订单号:<input class="t1" type="text" id="order_no" name="orderNo" [#if orderNo??]value="${orderNo}"[/#if] />
					           <span id="return_exchange">[#if afterSaleType == 1]退款单号[#else]退货单号[/#if]</span>:
					           		<input class="t1" type="text" id="return_exchange_no" name="returnOrExchangeNo" [#if returnOrExchangeNo??]value="${returnOrExchangeNo}"[/#if]  />
					           售后状态:
					            <select class="t3" id="afterSale_status" name="afterSaleStatus">
					            	[#if afterSaleType == 1]
					            		<option value="0" [#if afterSaleStatus == 0]selected="selected"[/#if]>全部</option>
							            <option value="1" [#if afterSaleStatus == 1]selected="selected"[/#if]>退款中</option>
							            <option value="2" [#if afterSaleStatus == 2]selected="selected"[/#if]>退款成功</option>
							            <option value="3" [#if afterSaleStatus == 3]selected="selected"[/#if]>退款取消</option>
							         [#else]
							         	<option value="0" [#if afterSaleStatus == 0]selected="selected"[/#if]>全部</option>
							            <option value="1" [#if afterSaleStatus == 1]selected="selected"[/#if]>退货中</option>
							            <option value="2" [#if afterSaleStatus == 2]selected="selected"[/#if]>退货成功</option>
							            <option value="3" [#if afterSaleStatus == 3]selected="selected"[/#if]>退货取消</option>
							         [/#if]   
							         	
					            </select>
					           售后类型:
					            <select class="t3" id="afterSale_type" name="afterSaleType">
						            <option value="1" [#if afterSaleType == 1]selected="selected"[/#if]>退款</option> 
					            	<option value="2" [#if afterSaleType == 2]selected="selected"[/#if]>退货</option>
					            </select>
				           </p>
				           <p>
					           物流订单:<input class="t1" type="text" id="logistics_no" name="logisticsNo" [#if logisticsNo??]value="${logisticsNo}"[/#if] />
					           申请时间:
					           从
					           <input type="text" id="begindate" name="begindate" class="t2" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" 
					           		[#if begindate??]value="${begindate}"[/#if] />
					           到
					           <input type="text" id="enddate" name="enddate" class="t2" onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});" 
					           		[#if enddate??]value="${enddate}"[/#if]  />
					           <input class="enquiries" value="查询" type="submit" />
				           </p>
			           </div>
					  <table id="order_infor" border="0" cellspacing="0" cellpadding="0">
							<thead>
								  <tr id="titleone">
									    <td class="choose"></td>  
									    <td class="goods">订单号</td>
									    <td class="price">[#if afterSaleType == 1]退款单号[#else]退货单号[/#if]</td>
									    <td class="quantity">申请时间</td>
									    <td class="operate">数量</td>
									    <td class="pay">物流单号</td>
									    <td class="state">处理状态</td>
								  </tr>
							  </thead>
							  <tbody>
							  	[#if page.content?has_content]
								  	[#list page.content as returns]
									  <tr>
										    <td class="choose"><input class="choose" type="checkbox" value="${returns.id}" name="ids" /></td>
										    <td class="goods">${returns.order.sn}</td>
										    <td class="price">${returns.sn}</td>
										    <td class="quantity">${returns.createDate}</td>
										    <td class="operate">1</td>
										    <td class="pay">${returns.trackingNo}</td>
										    <td class="state">
										            <span>
										            	[#if afterSaleType == 1]
															[#if returns.refundsStatus == "unconfirmed" || returns.refundsStatus == "confirmed"]
																退款中
															[#elseif returns.refundsStatus == "completed"]
																退款成功 
															[#elseif returns.refundsStatus == "cancelled"]
																退款取消
															[/#if]
														[#else]
															[#if returns.returnsStatus == "unconfirmed" || returns.returnsStatus == "confirmed"]
																退货中
															[#elseif returns.returnsStatus == "completed"]
																退货成功 
															[#elseif returns.returnsStatus == "cancelled"]
																退货取消
															[/#if]
														[/#if]	
													</span>
										    </td>
									  </tr>
								  	[/#list]
								  [/#if]	
								<!--
								  <tr>
									    <td class="choose"><input class="choose" type="checkbox" value="" /></td>
									    <td class="goods">20140929105687A</td>
									    <td class="price">20140929105687A</td>
									    <td class="quantity">20140929105687A</td>
									    <td class="operate">1</td>
									    <td class="pay">20140929105687A</td>
									    <td class="state">
									            <span style="display:none">退货中</span>
									            <span style="display:none">退货成功</span>
									            <span style="display:none">退款中</span>
									    </td>
								  </tr>
								-->
							  </tbody>
							  <tfoot>
								  <tr>
								    	<td colspan="2" valign="top"><input class="choose"  type="checkbox" value="" />全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								       		<a href="javascript:;" id="deleteButton">&nbsp;&nbsp;x&nbsp;删除选中订单</a>
								        </td>
								        <td colspan="3" >&nbsp;</td>
								        <td colspan="2" class=""></td>
								  </tr>
							  </tfoot>
					</table>
				    <div class="page">
				    </div>
			   </div>
			   <div id="clear"></div>
		   </div>
		 </form>  
	  </div>
	   
	   [#include "/shop/include/footer.ftl" /]
	   
	   <script type="text/javascript">
	   	
	   		//$(".enquiries").on("click",function(){
	   			//$("#listForm").submit();
	   		//});
	   		
	   		$().ready(function(){
		   		var $deleteButton = $("#deleteButton");
	   			
	   			//切换退款和退货
	   			$('#afterSale_type').change(function(){
		   			var $this = $(this);
		   			if($this.val() == 1){
		   				$("#return_exchange").text("退款单号");
		   				$("#afterSale_status").html('').append('<option value="0">全部</option><option value="1">退款中</option><option value="2">退款成功</option><option value="3">退款取消</option>');
		   			}else{
		   				$("#return_exchange").text("退货单号");
		   				$("#afterSale_status").html('').append('<option value="0">全部</option><option value="1">退货中</option><option value="2">退货成功</option><option value="3">退货取消</option>');
		   			}
		   		});
		   		
		   		/*删除退款或退货记录*/
		   		$deleteButton.click(function(){
		   			var checkedIds = $("#order_infor input[name='ids']");
		   			var orderIds = "";
		   			for(var i=0;i<checkedIds.length;i++){
		   				//alert($(checkedIds[i]).val());
		   				if(checkedIds[i].checked == true){
			   				if(i == checkedIds.length - 1){
			   					orderIds += $(checkedIds[i]).val();
			   				}else{
			   					orderIds += $(checkedIds[i]).val() + ",";
			   				}
		   				}
		   			}
		   			if (confirm("${message("shop.dialog.deleteConfirm")}")) {
		   				$.ajax({
		   						url: "${base}/member/afterSale/delete.jhtml",
		   						type: "POST",
		   						data: {ids: orderIds, afterSaleType:$("#afterSale_type").val() },
		   						dataType: "json",
		   						cache: false,
		   						success: function(message){
		   							$.message(message);
		   						}
		   					});
		   			}
		   			
		   		});
	   		});
	   		
	   		
	   		
	   		
	   		
	   		
	   		
	   </script>
	</body>
</html>
