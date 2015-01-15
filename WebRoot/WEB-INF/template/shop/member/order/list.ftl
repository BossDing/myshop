<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shop.member.order.list")}[#if systemShowPowered] - Powered By SHOP++[/#if]</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/common.css" />
    <link href="${base}/resources/shop/css/sns.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv_order.css"/>
	<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	[@flash_message /]
	var $pageNumber = $("#pageNumber");        
	var $listForm = $("#listForm"); 
	var $orderForm = $("#orderForm"); 
	var $but = $("#but");
	$but.click(function() {														
		$listForm.submit();
	});
	 $.pageSkip = function(pageNumber) {
				$pageNumber.val(pageNumber);														
				$orderForm.submit();
				return false;								
		}				      
						
});
</script>					
</head>			
<body>																									
	[#assign current = "orderList" /]			
	[#include "/shop/include/header.ftl" /]
	<div class="content">
	<div class="container member">
		<div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>><span><a href="/member/index.jhtml">个人中心</a></span>><span>我的订单</span></div>
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
		
		[#include "/shop/member/include/navigation.ftl" /]
			<div class="indiv_content">
					   <div class="indiv_car">
					       <div id="my_order">我的订单</div>
					        <div id="car_nav">
					           <ul>
					               <li [#if status == null]id="this"[/#if]><a href="list.jhtml">所有订单<i>${allOrderCount}</i></a></li>
					               <li [#if status == "unpaid"]id="this"[/#if]><a href="[#if waitingPaymentOrderCount != 0]list.jhtml?status=unpaid[#else]javascript:;[/#if]">待付款<i>${waitingPaymentOrderCount}</i></a></li>
					               <li [#if status == "unshipped"]id="this"[/#if]><a href="[#if waitingShippingOrderCount != 0]list.jhtml?status=unshipped[#else]javascript:;[/#if]">待发货<i>${waitingShippingOrderCount}</i></a></li>			
					               <li [#if status == "unreceive"]id="this"[/#if]><a href="[#if waitinggetOrderCount != 0]list.jhtml?status=unreceive[#else]javascript:;[/#if]">待收货<i>${waitinggetOrderCount}</i></a></li>
					               <li [#if status == "unEvaluation"]id="this"[/#if]><a href="[#if waitingconmentOrderCount != 0]list.jhtml?status=unEvaluation[#else]javascript:;[/#if]">待评价<i>${waitingconmentOrderCount}</i></a></li>   
					           </ul>  
					           <div id="del">订单回收站</div>
					        </div>
					
					        <div id="car_soso">   
					        <form id="listForm" action="list.jhtml" method="get">
					           <input type="text" id="tex" name="searchValue" value ="${searchValue}"/>
					           <input type="button" id="but" value="订单查询" />
					        </form>  
					           </div>
					           <table id="order_infor" border="0" cellspacing="0" cellpadding="0">
				  
					<thead>
					  <tr id="titleone">
					    <td id="goods" style="width:365px;"><input class="choose" type="checkbox" value="" />商品</td>
					    <td id="price" style="width:90px;">单价</td>
					    <td id="quantity" style="width:115px;">数量</td>
					    <td id="state" style="width:140px;">交易状态</td>
					    <td id="pay" style="width:140px;">实付款</td>
					    <td id="operate">商品操作</td>		
					  </tr>
					  <tr>
					  <!--
					    <td colspan="5"><input class="choose"  type="checkbox" value="" />全选
					       <input class="but"  type="button" value="合并付款" />
					       <input class="but" type="button" value="批量收货" />
					       </td>			
					    <td ><div class="page">
					      <input class="page_next" type="button" value="下一页&nbsp;&#8250;" />
					         <input class="page_last" type="button" value="&#8249;"  />
					       
					         </div></td>
					     -->
					  </tr>								
					  </thead>   
					  <form id="orderForm" action="list.jhtml" method="get">
					  <input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
					  <input type="hidden" name="status" value="${status}">
					    [#list page.content as order]
					  <tbody>
					
								  <tr id="titletwo">
								    <td colspan="6" ><a href="${base}/member/order/view.jhtml?sn=${order.sn}"><input class="choose" type="checkbox" value="" />2014-08-22    订单号：${order.sn} </a ><a href="${base}/member/order/view.jhtml?sn=${order.sn}" style="margin-left:600px;">查看</a></td>
								  </tr>
								  [#assign at=0]
								  [#list order.orderItems as orderItem]
								  [#assign at=at+1]
								  				<tr>
											    <td class="goods">
											          <div class="goods_pic">
											            <!-- 商品图片位置--> 
											            <a href="${orderItem.product.path}">
											            <img src="[#if orderItem.thumbnail??]${orderItem.thumbnail}[#else]${setting.defaultThumbnailProductImage}[/#if]" class="thumbnail" alt="${orderItem.fullName}" />
											            </a>
											            </div>
											          <div class="goods_name"><a href="${orderItem.product.path}">${orderItem.fullName}</a></div>
											          <div class="goods_num">${orderItem.sn}</div>
											          <!--<div class="goods_color">颜色:妙趣白</div>	-->		
											        </td>
											    <td class="price">￥${orderItem.price}</td>
											    <td class="quantity">1</td>  
											      <td class="state">				
											            <p><A href="#">
																[#if order.expired]
																	${message("shop.member.order.hasExpired")}
																[#elseif order.orderStatus == "completed" || order.orderStatus == "cancelled"]
																	${message("Order.OrderStatus." + order.orderStatus)}
																[#elseif order.paymentStatus == "unpaid" || order.paymentStatus == "partialPayment"]
																	${message("shop.member.order.waitingPayment")}
																	[#if order.shippingStatus != "unshipped"]
																		${message("Order.ShippingStatus." + order.shippingStatus)}
																	[/#if]
																[#else]
																	${message("Order.PaymentStatus." + order.paymentStatus)}
																	[#if order.paymentStatus == "paid" && order.shippingStatus == "unshipped"]
																		${message("shop.member.order.waitingShipping")}
																	[#else]
																		${message("Order.ShippingStatus." + order.shippingStatus)}
																	[/#if]
																[/#if]											            
											            
											            </A></p>
											           <!-- <p><A href="#">订单详情</A></p> -->
											            </td>
											    
											    <td class="pay">
											        <p>￥${orderItem.price}</p>
											    <!--<p>(含运费0.00)</p>-->
											</td>
											  			
											  			[#if (!order.expired) && (order.paymentStatus=="unpaid") && (order.orderStatus!="completed") && (order.orderStatus!="cancelled")]
											  				[#if at=1]
											  				<td class="operate"><p><a href="payment.jhtml?sn=${order.sn}">立即支付</a></p>
											                        </td>
											                [#else]<td></td>
											            	[/#if]   
											            [#elseif (!order.expired) && (order.paymentStatus=="paid") && (order.orderStatus="completed") && (order.reviewStatus="unreview")]
											            <td class="operate"><p><a href="/review/add/${orderItem.product.id}.jhtml?orderItemId=${orderItem.id}">立即评价</a></p>
											                        </td>
											            [#elseif (!order.expired) && (order.paymentStatus=="paid") && (order.orderStatus="completed") && (order.reviewStatus="reviewed")]
											            <td class="operate"><p><a href="javascript:;">已评价</a></p>
											                        </td>
											  			[#else]
											            <td class="operate"><p><a href="javascript:;">违规举报</a></p>
											                        <p><a href="javascript:;">退运保险</a></p>
											                        </td>
											            [/#if]          
											            
								 				 </tr>
								 	[/#list]
					 
					  </tbody>
					   [/#list]
					   <form>
					  <tfoot>    
					  <tr>				
					    <td colspan="5">			
					       
					    <!--底部页码-->
					    <input class="choose"  type="checkbox" value="" />全选
					       <input class="but"  type="button" value="合并付款" />
					       <input class="but" type="button" value="批量收货" />
					       </td>
					    <td ><div class="page">
					    [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
						[#include "/shop/include/pagination.ftl"]
						[/@pagination]
					    	  <!--
					   		 <a  href="" id ="lastpage" ><input class="page_next"   type="button" value="上一页&nbsp;&#8250;" /></a>
					      	 <a  href="" id ="nextpage" ><input class="page_next"   type="button" value="下一页&nbsp;&#8250;" /></a>-->
					         <!--<input class="page_last" type="button" value="&#8249;"  /> -->
							
					         </div></td>  
					  </tr>
					  </tfoot> 
					</table>		
					   </div>
					   <div id="clear"></div>
					   </div>  
					  </div>
	</div>
	<div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>