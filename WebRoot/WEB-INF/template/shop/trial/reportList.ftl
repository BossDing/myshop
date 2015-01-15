<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>	<title>试用报告列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
	<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${base}/resources/shop/css/reportList.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
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
                    试用报告列表
                      </strong>
                      <div class="sybg_xiangqing">
                     [#if trial.trialReports?has_content]
   						[#list trial.trialReports as report]
   							[#if report.reportStatus == "pass"]
		                        <!--试用报告内容-->
		                       	<div class="baogao_liebiao">
		                       		<strong>
		                           		[<span>${report.trialReportNo}</span>]
		                           		<span>试用报告</span>－
		                           		<span>${report.area.fullName}</span>
		                         		<span class="name">${report.createBy}</span>
		                         		<span class="baogao_liebiao_time">
		                          			${report.createDate}
		                         		</span>
		                         </strong>
		                        <div class="baogao_liebiao_infor">
							                 	[昵称]:<span>${report.reportName}</span>&nbsp;
							                 	[性别]:<span>
								                 [#if report.sex=="M"]
								                 	男
								                 [#elseif report.sex=="W"]   
								                 	女
								                 [#else]
								                 	不详
								                 [/#if]    
							                 </span>&nbsp;
							                 	[年龄]:<span>${report.age}</span>&nbsp;
										                 [职业]:<span>${report.job}</span>&nbsp;
										                 [兴趣爱好]: <span>${report.interest}</span>&nbsp;
		                        <span> <a  href="${base}/trialReport/detail/${report.id}.jhtml">查看详情</a></span>
		                        </div>                        
		                        <div class="bg_pic">
		                        <ul>
		                        	[#if report.itemWholeDocid==null || report.itemWholeDocid==""]
							        	
							        [#else]
							        	<li><img src="${report.itemWholeDocid}" width="80" height="80" /></li>
							        [/#if]
							        [#if report.itemWholeDocid==null || report.itemWholeDocid==""]
							        	
							        [#else]
							        	<li><img src="${report.itemDetailDocid}" width="80" height="80" /></li>
							        [/#if]
		                        </ul>
		                        </div>
		                          	</div>
		                          <!--使用报告内容结束-->
		                   		[/#if]
                          [/#list]
                      [/#if]
                      </div> 
					  </div>
		[#if trial.products?has_content]
   			[#list trial.products as product]
		<div class="side">
			<strong class="title">试用商品</strong>
			<div class="pro_img">
				<a href="${base}/trial/content/${trial.id}.jhtml">
					<img src="${trial.image}" alt="${product.name}" />
				</a>
			</div>
			<div class="pro_data">
				<strong class="pro_name" title="${product.name}">
					<a href="${base}/trial/content/${trial.id}.jhtml">
						${product.name}
					</a>
				</strong> 
            	<span class="pro_type">${product.sn}</span>
		    	<span class="price">预计市场价：
		    		<em class="redText">
            			<strong>${currency(trial.marketprice,true)}</strong>
            		</em>
            	</span>	
            	<span >
              		<a href="${base}/trial/content/${trial.id}.jhtml">
              			<input class="saveTrialApplyLink" type="button" value="查看产品详情" style="cursor:pointer;" />
              		</a>
              	</span>
	   		</div>
      	</div>
      	[/#list]
      	[/#if]
			<div class="cl"></div>
		</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
	</body>
</html>

