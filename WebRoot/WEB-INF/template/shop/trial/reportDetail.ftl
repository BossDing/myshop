<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>	
	<title>试用报告详情</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="description" content="" />
	<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${base}/resources/shop/css/reportDetail.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
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
                     [ <span>${report.trialReportNo}</span>]
                     <span>使用报告</span>
                      -<span>${report.area.fullName}</span>
                      <span>${report.member.username}</span>
                      <span>${report.createDate}</span>
                      </strong>
                      <!--报告详情内容-->
                      <div class="sybg_xiangqing">
                        <p>【昵称】:<span>${report.reportName}</span></p>
        				<p>【性别】:
        					<span>
        						[#if report.sex=="M"]
        							男
        						[#elseif report.sex=="W"]
        							女
        						[#else]
        							不详
        						[/#if]
        					</span>
        				</p>
				        <p>【年龄】:<span>${report.age}</span></p>
				        <p>【职位】:<span>${report.job}</span></p>
				        <p>【城市】:<span>${report.area}</span></p>
				        <p>【爱好关注】:<span>${report.interest}</span></p>
					        <!--试用报告内容-->
					        <div class="sybg_grade">
					          <table width="100%%" border="0" cellspacing="0"                            cellpadding="0">
					            <tr>
					              <th>试用评价：</th>
					              <td><div class="star" id="star2"> <span></span>
					                  <ul>
					                  	[#list 0..4 as i]
					                  		[#if report.trialRate>i]
					                    		<li class="on"><a>${i}</a></li>
					                    	[#else]
					                    		<li><a>${i}</a></li>
					                    	[/#if]
					                    [/#list]
					                  </ul>
					                 [#if report.trialRate == 1]
					                 	<span><strong>${report.trialRate}&nbsp;分</strong>&nbsp;&nbsp;（非常不满）</span>
					                 [#elseif report.trialRate == 2]
					                 	<span><strong>${report.trialRate}&nbsp;分</strong>&nbsp;&nbsp;（不满意）</span>
					                 [#elseif report.trialRate == 3]
					                 	<span><strong>${report.trialRate}&nbsp;分</strong>&nbsp;&nbsp;（一般）</span>
					                 [#elseif report.trialRate == 4]
					                 	<span><strong>${report.trialRate}&nbsp;分</strong>&nbsp;&nbsp;（满意）</span>
					                 [#elseif report.trialRate == 5]
					                 	<span><strong>${report.trialRate}&nbsp;分</strong>&nbsp;&nbsp;（非常满意）</span>
					                 [#else]
					                 	<span><strong>（没有评分）</span>
					                 [/#if]
					                </div></td>
					            </tr>
					            <tr>
					              <th>外观做工：</th>
					              <td><div class="star" id="star2"> <span></span>
					                  <ul>
					                    [#list 0..4 as i]
					                  		[#if report.lookRate>i]
					                    		<li class="on"><a>${i}</a></li>
					                    	[#else]
					                    		<li><a>${i}</a></li>
					                    	[/#if]
					                    [/#list]
					                  </ul>
					                  [#if report.lookRate == 1]
					                 	<span><strong>${report.lookRate}&nbsp;分</strong>&nbsp;&nbsp;（非常不满）</span>
					                  [#elseif report.lookRate == 2]
					                 	<span><strong>${report.lookRate}&nbsp;分</strong>&nbsp;&nbsp;（不满意）</span>
					                  [#elseif report.lookRate == 3]
					                 	<span><strong>${report.trialRate}&nbsp;分</strong>&nbsp;&nbsp;（一般）</span>
					                  [#elseif report.lookRate == 4]
					                 	<span><strong>${report.lookRate}&nbsp;分</strong>&nbsp;&nbsp;（满意）</span>
					                  [#elseif report.lookRate == 5]
					                  	<span><strong>${report.lookRate}&nbsp;分</strong>&nbsp;&nbsp;（非常满意）</span>
					                  [#else]
					                 	<span><strong>（没有评分）</span>
					                  [/#if]
					                </div></td>
					            </tr>
					            <tr>
					              <th>功能操作：</th>
					              <td><div class="star" id="star2"> <span></span>
					                  <ul>
					                    [#list 0..4 as i]
					                  		[#if report.funcationRate>i]
					                    		<li class="on"><a>${i}</a></li>
					                    	[#else]
					                    		<li><a>${i}</a></li>
					                    	[/#if]
					                    [/#list]
					                  </ul>
					                  [#if report.funcationRate == 1]
					                 	<span><strong>${report.funcationRate}&nbsp;分</strong>&nbsp;&nbsp;（非常不满）</span>
					                  [#elseif report.funcationRate == 2]
					                 	<span><strong>${report.funcationRate}&nbsp;分</strong>&nbsp;&nbsp;（不满意）</span>
					                  [#elseif report.funcationRate == 3]
					                 	<span><strong>${report.funcationRate}&nbsp;分</strong>&nbsp;&nbsp;（一般）</span>
					                  [#elseif report.funcationRate == 4]
					                 	<span><strong>${report.funcationRate}&nbsp;分</strong>&nbsp;&nbsp;（满意）</span>
					                  [#elseif report.funcationRate == 5]
					                  	<span><strong>${report.funcationRate}&nbsp;分</strong>&nbsp;&nbsp;（非常满意）</span>
					                  [#else]
					                 	<span><strong>（没有评分）</span>
					                  [/#if]
					                </div></td>
					            </tr>
					            <tr>
					              <th>使用感受：</th>
					              <td><div class="star" id="star2"> <span></span>
					                  <ul>
					                    [#list 0..4 as i]
					                  		[#if report.feelRate>i]
					                    		<li class="on"><a>${i}</a></li>
					                    	[#else]
					                    		<li><a>${i}</a></li>
					                    	[/#if]
					                    [/#list]
					                  </ul>
					                  [#if report.feelRate == 1]
					                 	<span><strong>${report.feelRate}&nbsp;分</strong>&nbsp;&nbsp;（非常不满）</span>
					                  [#elseif report.feelRate == 2]
					                 	<span><strong>${report.feelRate}&nbsp;分</strong>&nbsp;&nbsp;（不满意）</span>
					                  [#elseif report.feelRate == 3]
					                 	<span><strong>${report.feelRate}&nbsp;分</strong>&nbsp;&nbsp;（一般）</span>
					                  [#elseif report.feelRate == 4]
					                 	<span><strong>${report.feelRate}&nbsp;分</strong>&nbsp;&nbsp;（满意）</span>
					                  [#elseif report.feelRate == 5]
					                  	<span><strong>${report.feelRate}&nbsp;分</strong>&nbsp;&nbsp;（非常满意）</span>
					                  [#else]
					                 	<span><strong>（没有评分）</span>
					                  [/#if]
					                </div></td>
					            </tr>
					            <tr>
					              <th>商品性价比：</th>
					              <td><div class="star" id="star2"> <span></span>
					                  <ul>
					                    [#list 0..4 as i]
					                  		[#if report.costEffectiveRate>i]
					                    		<li class="on"><a>${i}</a></li>
					                    	[#else]
					                    		<li><a>${i}</a></li>
					                    	[/#if]
					                    [/#list]
					                  </ul>
					                  [#if report.costEffectiveRate == 1]
					                 	<span><strong>${report.costEffectiveRate}&nbsp;分</strong>&nbsp;&nbsp;（非常不满）</span>
					                  [#elseif report.costEffectiveRate == 2]
					                 	<span><strong>${report.costEffectiveRate}&nbsp;分</strong>&nbsp;&nbsp;（不满意）</span>
					                  [#elseif report.costEffectiveRate == 3]
					                 	<span><strong>${report.costEffectiveRate}&nbsp;分</strong>&nbsp;&nbsp;（一般）</span>
					                  [#elseif report.costEffectiveRate == 4]
					                 	<span><strong>${report.costEffectiveRate}&nbsp;分</strong>&nbsp;&nbsp;（满意）</span>
					                  [#elseif report.costEffectiveRate == 5]
					                  	<span><strong>${report.costEffectiveRate}&nbsp;分</strong>&nbsp;&nbsp;（非常满意）</span>
					                  [#else]
					                 	<span><strong>（没有评分）</span>
					                  [/#if]
					                </div></td>
					            </tr>
					            <tr>
					              <th>商品包装：</th>
					              <td><div class="star" id="star2"> <span></span>
					                  <ul>
					                    [#list 0..4 as i]
					                  		[#if report.packageRate>i]
					                    		<li class="on"><a>${i}</a></li>
					                    	[#else]
					                    		<li><a>${i}</a></li>
					                    	[/#if]
					                    [/#list]
					                  </ul>
					                  [#if report.packageRate == 1]
					                 	<span><strong>${report.packageRate}&nbsp;分</strong>&nbsp;&nbsp;（非常不满）</span>
					                  [#elseif report.packageRate == 2]
					                 	<span><strong>${report.packageRate}&nbsp;分</strong>&nbsp;&nbsp;（不满意）</span>
					                  [#elseif report.packageRate == 3]
					                 	<span><strong>${report.packageRate}&nbsp;分</strong>&nbsp;&nbsp;（一般）</span>
					                  [#elseif report.packageRate == 4]
					                 	<span><strong>${report.packageRate}&nbsp;分</strong>&nbsp;&nbsp;（满意）</span>
					                  [#elseif report.packageRate == 5]
					                  	<span><strong>${report.packageRate}&nbsp;分</strong>&nbsp;&nbsp;（非常满意）</span>
					                  [#else]
					                 	<span><strong>（没有评分）</span>
					                  [/#if]
					                </div></td>
					            </tr>
					            <tr>
					              <th>物流服务：</th>
					              <td><div class="star" id="star2"> <span></span>
					                  <ul>
					                    [#list 0..4 as i]
					                  		[#if report.logisticsRate>i]
					                    		<li class="on"><a>${i}</a></li>
					                    	[#else]
					                    		<li><a>${i}</a></li>
					                    	[/#if]
					                    [/#list]
					                  </ul>
					                  [#if report.logisticsRate == 1]
					                 	<span><strong>${report.logisticsRate}&nbsp;分</strong>&nbsp;&nbsp;（非常不满）</span>
					                  [#elseif report.logisticsRate == 2]
					                 	<span><strong>${report.logisticsRate}&nbsp;分</strong>&nbsp;&nbsp;（不满意）</span>
					                  [#elseif report.logisticsRate == 3]
					                 	<span><strong>${report.logisticsRate}&nbsp;分</strong>&nbsp;&nbsp;（一般）</span>
					                  [#elseif report.logisticsRate == 4]
					                 	<span><strong>${report.logisticsRate}&nbsp;分</strong>&nbsp;&nbsp;（满意）</span>
					                  [#elseif report.logisticsRate == 5]
					                  	<span><strong>${report.logisticsRate}&nbsp;分</strong>&nbsp;&nbsp;（非常满意）</span>
					                  [#else]
					                 	<span><strong>（没有评分）</span>
					                  [/#if]
					                </div></td>
					            </tr>
					          </table>
					        </div>
					        [#if report.itemWholeDocid==null || report.itemWholeDocid==""]
					        
					        [#else]
					        	<div>【商品整体图】：</div>
					        	<div class="sybg_picture">
					        		
					        		<img src="${report.itemWholeDocid}" width="330" height="330"/>
					        	</div>
					        [/#if]
					        [#if report.itemWholeDocid==null || report.itemWholeDocid==""]
					        
					        [#else]
					        	<div>【商品细节图】：</div>
					        	<div class="sybg_picture">
					        		<img src="${report.itemDetailDocid}" width="330" height="330"/>
					        	</div>
					        [/#if]
					        <div class="sybg_experience">
					        	【使用说明】：
					        	${report.reportDesc1}
					        	${report.reportDesc2}
					        	${report.reportDesc3}
					        	${report.reportDesc4}
					        	${report.reportDesc5}
					        	${report.reportDesc6}
					        	${report.reportDesc7}
					        	${report.reportDesc8}
					        	${report.reportDesc9}
					        	${report.reportDesc10}
					        </div>
					        <div class="sybg_idea"> 【建议】：${report.trialSuggestion} </div>
					        </div>
					        
                         <!--使用报告内容结束-->
                        </div> 
                      <!--报告详情内容-->
                      <div class="side">
			<strong class="title">试用商品</strong>
			<div class="pro_img">
				<a href="${base}/trial/content/${report.trial.id}.jhtml">
					<img src="${report.trial.image}" alt="${product.name} " />
				</a>
			</div>
			<div class="pro_data">

			<strong class="pro_name" title="${report.product.name}">
				<a href="${base}/trial/content/${report.trial.id}.jhtml">
		 			${report.product.name}
				</a>
			</strong> 
            <span class="pro_type">${report.product.sn}</span>
		    <span class="price">预计市场价：<em class="redText">
            <strong>${currency(report.trial.marketprice,true)}</strong>
            </em>
            </span>	
            <span>
             	<a href="${base}/trial/content/${report.trial.id}.jhtml">
              		<input class="saveTrialApplyLink" style="cursor:pointer;" type="button" value="查看产品详情"/>
              	</a>
            </span>
	   </div>
                      </div>	
		
      </div>	
			<div class="cl"></div>
		</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
	</body>
</html>