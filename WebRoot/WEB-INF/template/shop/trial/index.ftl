<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${setting.siteName} - 试用中心</title>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/shiyong.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
 <script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
 <script>
	$(function(){

		var $promotionProductInfo = $("#promotionProduct .info2");
		
		 
		 function promotionInfo() {
			$promotionProductInfo.each(function() {
				var $this = $(this);
				var beginDate = $this.attr("beginTimeStamp") != null ? new Date(parseFloat($this.attr("beginTimeStamp"))) : null;
				var endDate = $this.attr("endTimeStamp") != null ? new Date(parseFloat($this.attr("endTimeStamp"))) : null;
				if (beginDate == null || beginDate <= new Date()) {
					if (endDate != null && endDate >= new Date()) {
						var time = (endDate - new Date()) / 1000;
						$this.html("<em>" + Math.floor(time / (24 * 3600)) + "<\/em> ${message("shop.index.day")} <em>"
						+ Math.floor((time % (24 * 3600)) / 3600) + "<\/em> ${message("shop.index.hour")} <em>"
						+ Math.floor((time % 3600) / 60) + "<\/em> ${message("shop.index.minute")} "
						+ (Math.floor(time) - Math.floor((time/(24 * 3600)))*24*3600 - Math.floor((time % (24 * 3600)) / 3600)*3600 - Math.floor((time % 3600) / 60)*60)+ "<\/em> 秒");
					} else if (endDate != null && endDate < new Date()) {
						$this.html("${message("shop.index.ended")}");
					} else {
						$this.html("${message("shop.index.going")}");
					}
				}
			});
		}
	
		promotionInfo();
		setInterval(promotionInfo, 1000);

		
	});
 </script>
</head>
 
<body>
[#include "/shop/include/header.ftl" /]

<!--产品试用开始-->
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span>首页</span>><span>产品试用</span></div>
  <div class="kfdlb">
      <ul>
          <li>分享到：</li>
          <li><a href="#"><img src="/resources/shop/images/qq_ico.png" width="17px";></a></li>
          <li><a href="#"><img src="/resources/shop/images/renren_ico.png" width="17px";></a></li>
          <li><a href="#"><img src="/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
          <li><a href="#"><img src="/resources/shop/images/weibo_ico.png" width="17px";></a></li>
      </ul>
  </div>
</div>
<div class="shiyong">
    <div class="left-1">
      <div class="left-2"><img src="/resources/shop/images/le-1.png"><span>用户申请宣言</span></div>
      <div class="left-3">
	 [#if trials?has_content]
         	[#list trials as trial] 
         		[#if trial.trialApplys?has_content]
		         	[#list trial.trialApplys as trialApply]
		         	[#if trialApply_index<4]
		           <div class="left-4">
			     <a href="#"><span>${trialApply.receiver?substring(0,1)} * *:</span></br>
			     [#if trialApply.applyReason?length>20]
				${trialApply.applyReason?substring(0,20)}....
			     [#else]
				${trialApply.applyReason}
			     [/#if]
				</a>
			    </div>
		            
		            [/#if]
	            	[/#list]
            	[/#if]
             [/#list]
             [#else]
             	[#if trialReports?has_content]
	             [#list trialReports as trial]
	             	[#if trial_index==1] 
	         		 [#if trial.trialApplys?has_content]
			         	[#list trial.trialApplys as trialApply]
			         	[#if trialApply_index<4]
				    <div class="left-4">
			             <a href="#"><span>${trialApply.receiver?substring(0,1)} * *:</span></br>
				     [#if trialApply.applyReason?length>20]
			               	${trialApply.applyReason?substring(0,20)}....
				     [#else]
					${trialApply.applyReason}
				     [/#if]
					</a>
			            </div>
			            [/#if]
		            	[/#list]
		            	[#else]
		            	<div class="left-4">暂无试用申请！</div>
	            	[/#if]
	               [/#if]
	             [/#list]
	            [#else]
		           <div class="left-4">暂无试用申请！</div>
	            [/#if] 
	  
          [/#if]
          <!--
	  <div class="left-4"><a href="#"><span>喜欢红色**闪电:</span></br>我是一个喜欢体验和喜欢尝试的人，会用客观公正的眼光来评判商品的真正价值和性价比</a></div>
           <div class="left-4"><a href="#"><span>喜欢红色**闪电:</span></br>我是一个喜欢体验和喜欢尝试的人，会用客观公正的眼光来评判商品的真正价值和性价比</a></div>
            <div class="left-4"><a href="#"><span>喜欢红色**闪电:</span></br>我是一个喜欢体验和喜欢尝试的人，会用客观公正的眼光来评判商品的真正价值和性价比</a></div>
	    -->
      </div>
    </div>
    <div class="right-1" id="promotionProduct" >

	 [#list trials as trial]
		<div class="right-2"><img src="${trial.image}"></div>
		<div class="right-3">
		    <div class="right-4">
			<div class="right-5">限量<span>${trial.qtylimit}</span>台已有<span>[#if trial.appliernum??]${trial.appliernum}[#else]0[/#if]</span>人提交申请</div>
			<div class="right-6">剩余&nbsp;<span class="info2"[#if trial.beginDate??] beginTimeStamp="${trial.beginDate?long}"[/#if][#if trial.endDate??] endTimeStamp="${trial.endDate?long}"[/#if]>
													[#if trial.beginDate??]
														<em>${trial.beginDate?string("yyyy-MM-dd HH:mm")}</em>
													[/#if]
												</span></div>
		    </div>
		    <div class="tehui-xq1-c">
			<div class="tehui-xq1-c1"><a href="${base}/trial/toApply/${trial.id}.jhtml">申请试用</a></div>
			<div class="tehui-xq1-c2"><a href="#">了解产品</a></div>
		    </div>
		</div>
	[/#list]
        
    </div>
</div>
<div class="maip">
      <div class="maip-1"><span>免费试用|</span>分享创造快乐</div>
      <div class="maip-2">免费试用流程:注册会员-提交试用申请-审核-获得试用商品-提交试用报告<span><a href="#">加入试用>></a></span></div>
  </div>
<div class="shi-big">
      <div class="shi-1">
	[#if trialReports?has_content]
       	[#list trialReports as trial ]
	       	<div class="shi-2">
              <div class="shi-3">
                    <div  class="shi-31"><a href="${base}/trialReport/reportList/${trial.id}.jhtml"><img src="${trial.image}"></a></div>
                    <div class="shi-32">${trial.name}</div>
              </div>
              <div class="shi-4">
                  <div class="shi-41">试用报告：<a href="${base}/trialReport/reportList/${trial.id}.jhtml"><span>${trial.trialReports?size}</span>篇&nbsp;>></a></div>
                  [#if trial.trialReports?has_content]
	                  [#list trial.trialReports as trialReport]
	                  [#if report_index == 0]
	                  <div class="shi-42"><span>${trialReport.member.username}:</span></br>${${base}/trial/toApply/${trial.id}.jhtml}</br><span>查看详情》》</span></div>
	                  <div class="shi-5"><img src="/resources/shop/images/shi-6.jpg"><img src="/resources/shop/images/shi-7.jpg"></div>
	                  [/#if]
	                  [/#list]
	                  [#else]暂无试用报告!
                  [/#if]
              </div> 
          </div>
       [/#list]
        [/#if]
		<!--
          <div class="shi-2">
              <div class="shi-3">
                    <div  class="shi-31"><a href="#"><img src="/resources/shop/images/shi-3.jpg"></a></div>
                    <div class="shi-32">专利双高匀火灶</div>
              </div>
              <div class="shi-4">
                  <div class="shi-41">试用报告：<a href="#"><span>2</span>篇&nbsp;>></a></div>
                  <div class="shi-42"><span>喜欢红色闪电:</span></br>我是一个喜欢体验和喜欢尝试的人，会用客观公正的眼光来评判商品的真正价值和性价比</br><span>查看详情》》</span></div>
                  <div class="shi-5"><img src="/resources/shop/images/shi-6.jpg"><img src="/resources/shop/images/shi-7.jpg"></div>
              </div> 
          </div>
          <div class="shi-2">
              <div class="shi-3">
                    <div  class="shi-31"><a href="#"><img src="/resources/shop/images/shi-4.jpg"></a></div>
                    <div class="shi-32">专利双温分控</div>
              </div>
              <div class="shi-4">
                  <div class="shi-41">试用报告：<a href="#"><span>1</span>篇&nbsp;>></a></div>
                  <div class="shi-42"><span>喜欢红色闪电:</span></br>我是一个喜欢体验和喜欢尝试的人，会用客观公正的眼光来评判商品的真正价值和性价比</br><span>查看详情》》</span></div>
                  <div class="shi-5"><img src="/resources/shop/images/shi-6.jpg"><img src="/resources/shop/images/shi-7.jpg"></div>
              </div> 
          </div>-->
      </div>
      <div class="shi-r6">
	[#if nextTrials?has_content]
	     [#list nextTrials as nextTrial]
		<div class="shi-r61"><img src="/resources/shop/images/r6-1.png"><span>下期预告</span></div>
		  <div class="jfd"><a href="#"><img src="${nextTrial.image}" width="235px" height="220px"></a></div>
		  <div class="tehui-xq1-c">
			<div class="tehui-xq1-c1"><a href="#">申请试用</a></div>
			<div class="tehui-xq1-c2"><a href="#">了解产品</a></div>
		    </div>
	      [/#list]
	 [#else]
		<div class="yg_shop_pic">
		  <span style="line-height:120px; text-align:center; font-weight:900;">暂无预告试用！</span>
	       </div>
	 [/#if]
         
      </div>
</div>

[#include "/shop/include/footer.ftl" /]
</body>
</html>
