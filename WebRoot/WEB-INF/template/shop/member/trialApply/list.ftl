<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>我的试用</title>
<meta name="author" content="SHOP++ Team" />
<meta name="copyright" content="SHOP++" />
<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/member.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/my_apply.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript">
	$().ready(function() {
		[#if member == null]
			window.location.href="${base}/login.jhtml";
		[/#if]
	});

	function setTab(name,cursel,n){
		for(i=1;i<=n;i++){
			var menu=document.getElementById(name+i);
			var con=document.getElementById("con_"+name+"_"+i);
			menu.className=i==cursel?"hover":"";
			con.style.display=i==cursel?"block":"none";
		}
	}
</script>
</head>
<body>
	[#include "/shop/member/include/header.ftl" /]
	<div class="container member">
		[#include "/shop/member/include/navigation.ftl" /]
			<div class="span18 last">
				<div id="my_apply_tab">
				  <div class="Menubox">
				    <ul>
				      <li id="menu1" onclick="setTab('menu',1,2)" class="hover">我的试用申请</li>
				      <li id="menu2" onclick="setTab('menu',2,2)" class="" >我的试用报告</li>
				    </ul>
				  </div>
				  <div class="Contentbox"> 
				    <div id="con_menu_1" class="hover">
				    
				    <!--我的试用申请-->
				      <div class="my_apply_centent">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				    <tr>
				     <th width="40%">申请商品</th>
				     <th width="16%">申请宣言</th>
				    <th width="12%">申请日期</th>
				    <th width="14%">申请地址</th>
				    <th width="9%">申请状态</th>
				    <th width="9%">操作</th>
				  </tr>
				  [#if applys?has_content]
				  	[#list applys as apply]
						  <tr>
						    <td>
						       <div class="my_apply_pic"><img src="${apply.trial.image}"/></div>
						       <div class="my_apply_goods">
						         <dl>
						            <dt>${apply.product.name}</dt>
						            <dd>预计市场价格:<span>${currency(apply.trial.marketprice,true)}</span></dd>
						            <dd>试用品数量:<span>${apply.trial.qtylimit}</span>已有
						            	<span>
						            	[#if apply.trial.appliernum == null]
						            	0
						            	[#else]
						            		${apply.trial.appliernum}
						            	[/#if]
						            	</span>人申请
						            </dd>
						            <dd>活动时间:<span>${apply.trial.beginDate}</span>至<span>${apply.trial.endDate}</span></dd>
						            </dl>
						         </div>
						       </td>
						    <td><div style="width:130px;">${apply.applyReason}</div></td>
						    <td><div style="width:100px;">${apply.createDate}</div></td>
						    <td><div style="width:120px;">${apply.area.fullName}${apply.address}</div></td>
						    <td><div style="width:60px;">
						    	[#if apply.applyStatus == "approving" || apply.applyStatus == null]
						    		审核中
						    	[#elseif apply.applyStatus == "pass"]
						    		审核通过
						    	[#elseif apply.applyStatus == "reject"]
						    		未通过审核
						    	[/#if]
						    </div></td>
						    <td>
						    	<div style="width:50px;">
						    		<div><a href="#">查看</a></div>
						    		<div><a href="#">提交报告</a></div>
						    	</div>
						    </td>
						  </tr>
					[/#list]
				[/#if]
				</table>
				</div>
				 <!--我的试用申请结束-->
				    </div>
				    <div id="con_menu_2" style="display:none">
				    
				     <!--我的试用报告-->
				     <div class="my_apply_centent">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				    <tr>
				     <th width="30%">申请商品</th>
				     <th width="10%">报告日期</th>
				    <th width="40%">试用报告</th>
				    <th width="10%">报告状况</th>
				    <th width="10%">操作</th>
				  </tr>
				 [#if reports?has_content]
				  	[#list reports as report]
					  <tr>
					    <td>
					       <div class="my_apply_pic"><img src="${report.trial.image}"/></div>
					       <div class="my_apply_goods" style="width:150px; margin-top:10px;">
					         <dl>
					            <dt>${report.product.name}</dt>
					            <dd>预计市场价格:<span>${currency(report.trial.marketprice,true)}</span></dd>
					            </dl>
					         </div>
					       </td>
					    <td><div style="width:80px;">${report.createDate}</div></td>
					    <td><dl  class="report_infor">
					            <dt>
					            	【昵称】${report.reportName}
					            	【性别】
					            	[#if report.sex == "M"]
					            	男
					            	[#elseif report.sex == "W"]
					            	女
					            	[#else]
					            	不详
					            	[/#if]
					            	【年龄】${report.age} 
					            	<A href="${base}/trialReport/detail/${report.id}.jhtml" target="_blank">更多>></A>
								</dt>
								[#if report.itemWholeDocid != null]
					            <dd><img src="${report.itemWholeDocid}"/></dd>
					            [/#if]
					            [#if report.itemDetailDocid != null]
					            <dd><img src="${report.itemDetailDocid}"/></dd>
					            [/#if]
					        </dl>
					        </td>
					    <td><div style="width:60px;">
					    		[#if report.reportStatus == "approving" || report.reportStatus == null]
					    		审核中
					    		[#elseif report.reportStatus == "pass"]
					    		审核通过
					    		[#elseif report.reportStatus == "reject"]
					    		未通过审核
					    		[/#if]
					    	</div>
					    </td>
					    <td>
					    	<div style="width:60px;">
					    		<div><a href="#">查看</a></div>
					    		<div><a href="#">编辑</a></div>
					    	</div>
					    </td>
					  </tr>
					[/#list]
				[/#if] 
				</table>
				</div>
				     <!--我的试用报告结束-->
				
				    </div>
				  </div>
				</div>
				<div class="hot_shop">
				    <div  style="border-bottom: 1px solid #f8f8f8;"><strong>热门试用商品</strong></div>
				       	<ul>
				       		[#if trials?has_content]
				  				[#list trials as trial]
						       		<li>   
						       			<div class="sy_hot_pic">
						       				<a href="${base}/trial/content/${trial.id}.jhtml" target="_blank">
						         				<img src="${trial.image}" /> 
						         			</a>
						         		</div>
					       				<div class="sy_hot_name">
					       					<a href="${base}/trial/content/${trial.id}.jhtml" target="_blank">
					        					${trial.name}
					        				</a>
					         			</div>
						            </li>
					            [/#list]
							[/#if] 
				       </ul>
				      </div>
				</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
</body>
</html>