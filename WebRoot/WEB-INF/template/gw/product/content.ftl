<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "productSearch"]
	<title>${product.name}_万家乐官方网站</title>
	<META name=Keywords content=${productCategory.name}>
	<META name=Description content=${productCategory.seoDescription}>
[/@seo]
<link href="${base}/resources/gw/css/style.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/gw/css/css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/gw/css/all_css.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/gw/css/ban2.css" rel="stylesheet" type="text/css">

<link rel="icon" href="http://www.chinamacro.cn/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="http://www.chinamacro.cn/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/slide_share.css?v=bc01b5e3.css">
<link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/imgshare.css?v=754091cd.css">
<link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/share_style0_16.css?v=f4b44e79.css">

<script type="text/javascript" src="${base}/resources/gw/js/common.js"></script>
<script src="${base}/resources/gw/js/jquery-1.4.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript">var $a =jQuery.noConflict();</script>
<script src="${base}/resources/gw/js/nav.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="${base}/resources/gw/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${base}/resources/gw/js/lanrenzhijia.js"></script>
<script src="${base}/resources/gw/js/share.js"></script>
</head>

<script type="text/javascript">

$().ready(function() {
	var $parameterTable = $("#parameterTable");
	// 加载参数
	function loadParameter() {
		$.ajax({
			url: "/gw/product/parameter_groups.jhtml",
			type: "GET",
			data: {id: ${product.productCategory.id}},
			dataType: "json",
			beforeSend: function() {
				$parameterTable.empty();
			},
			success: function(data) {
				var trHtml = "";
				$.each(data, function(i, parameterGroup) {
					trHtml += "";
					$.each(parameterGroup.parameters, function(i, p) {
						
							trHtml += 
							'<tr>'+
							 '<td class="cpsxz">'+p.name+'</td>'+
							  '<td class="cpsxy">'+'</td>'+
							'</tr>';
					});
				});
				$parameterTable.append(trHtml);
			}
		});
	}
	
})
function $id(element) {
  return document.getElementById(element);
}
//切屏--是按钮，_v是内容平台，_h是内容库
function reg(str){
  var bt=$id(str+"_b").getElementsByTagName("h2");
  for(var i=0;i<bt.length;i++){
    bt[i].subj=str;
    bt[i].pai=i;
    bt[i].style.cursor="pointer";
    bt[i].onclick=function(){
      $id(this.subj+"_v").innerHTML=$id(this.subj+"_h").getElementsByTagName("blockquote")[this.pai].innerHTML;
      for(var j=0;j<$id(this.subj+"_b").getElementsByTagName("h2").length;j++){
        var _bt=$id(this.subj+"_b").getElementsByTagName("h2")[j];
        var ison=j==this.pai;
        _bt.className=(ison?"":"h2bg");
      }
    }
  }
  $id(str+"_h").className="none";
  $id(str+"_v").innerHTML=$id(str+"_h").getElementsByTagName("blockquote")[0].innerHTML;
}
</script>

</head>
<body>

	[#include "/gw/include/header.ftl" /]
	
	<div class="ban5">
	<p>
	[#assign rows = 0 /]
	[@product_category_parent_list productCategoryId = product.productCategory.id]
		[#list productCategories as productCategory]
		[#assign rows = rows+1 /]
		    [#if rows==2]
		     [@ad_position adname="官网 - ${productCategory.name}banner图"/]
		    [/#if]
		[/#list]
	[/@product_category_parent_list]
	</p></div>

<div class="cpxq">
  <div class="cpxq_div">
    <div class="cp_dq">目前您在: <a href="${base}/gw/index.jhtml">首页</a>
			[#assign rows = 0 /]
			[@product_category_parent_list productCategoryId = product.productCategory.id]
				[#list productCategories as productCategory]
					[#assign rows = rows+1 /]
				    &gt;
				    [#if rows==1]
				    	[#if productCategory.id=1079]
							<a href="http://www.chinamacro.cn/reshui/">${productCategory.name}</a>
						[#elseif productCategory.id=1089]
							<a href="http://www.chinamacro.cn/gongnuan/">${productCategory.name}</a>
						[#else]
							<a href="${base}/gw/product/list/${productCategory.id}.jhtml">${productCategory.name}</a>
						[/#if]
				    [#else]
				    	[#if productCategory.isShowProduct]
							<a href="${base}/gw/product/pplist/${productCategory.id}.jhtml">${productCategory.name}</a>
				    	[#else]
				    		[#if productCategory.id=1080]
								<a href="http://www.chinamacro.cn/reshui/ranqi/">${productCategory.name}</a>
							[#elseif productCategory.id=1081]
								<a href="http://www.chinamacro.cn/reshui/kongqi/">${productCategory.name}</a>
							[#elseif productCategory.id=1086]
								<a href="http://www.chinamacro.cn/chufang/chouyou/">${productCategory.name}</a>
							[#elseif productCategory.id=1087]
								<a href="http://www.chinamacro.cn/chufang/ranqizao/">${productCategory.name}</a>
							[#elseif productCategory.id=1090]
								<a href="http://www.chinamacro.cn/gongnuan/bigualu/">${productCategory.name}</a>
							[#else]
							 	<a href="${base}/gw/product/plist/${productCategory.id}.jhtml">${productCategory.name}</a>
							[/#if]
				    	[/#if]
				    [/#if]
				[/#list]
			[/@product_category_parent_list]
	            	&gt;
	            	<h1 style="font-size:14px;font-weight:normal;display:inline">
	            		<a href="${base}/gw/product/pplist/${product.productCategory.id}.jhtml">${product.productCategory.name}</a>
	            	</h1>
				    &gt;
				    ${product.name}
    </div>
    <div class="cpxq_bt">
	      <div class="cpbt_z">${product.name}</div>
	      <div class="rhgm"></div>
    </div>
    <div class="nTab">
<script type="text/javascript">
		function nTabs(thisObj,Num){
			if(thisObj.className == "active")return;
			var tabObj = thisObj.parentNode.id;
			var tabList = document.getElementById(tabObj).getElementsByTagName("li");
			for(i=0; i <tabList.length; i++){
				  if (i == Num){
				   	  thisObj.className = "active"; 
				      document.getElementById(tabObj+"_Content"+i).style.display = "block";
				  }else{
				   	  tabList[i].className = "normal"; 
				      document.getElementById(tabObj+"_Content"+i).style.display = "none";
				  }
			} 
		}
</script>
  <div class="TabTitle">
    <ul id="myTab0">
      <li class="active" onclick="nTabs(this,0);">产品综述</li>
      <li class="normal" onclick="nTabs(this,1);">产品特征</li>
      <li class="normal" onclick="nTabs(this,2);">产品展示</li>
      <li class="normal" onclick="nTabs(this,3);">核心部件</li>
      <li class="normal" onclick="nTabs(this,4);">用户体验</li>
      <li class="normal" onclick="nTabs(this,5);">规格参数</li>
    </ul>
  </div>
  
  <div class="TabContent">
    <div id="myTab0_Content0">
      ${product.introduction}
    </div>
    <div id="myTab0_Content1" class="none">${product.features}</div>
    <div id="myTab0_Content2" class="none">${product.showimg}</div>
    <div id="myTab0_Content3" class="none">${product.core}</div>
    <div id="myTab0_Content4" class="none">${product.ue}</div>
    <div id="myTab0_Content5" class="none">
    <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#dddddd" id="parameterTable">
                                 <tbody>
				 [#list parameterGroups as parameterGroup]
					[#list parameterGroup.parameters as parameter]
					
					    [#if product.parameterValue.get(parameter)!='fenge' ]
					        	[#if product.parameterValue.get(parameter)??]
					        		<tr>
									  <td class="cpsxz">${abbreviate(parameter.name, 20)}</td>
									  <td class="cpsxy">${abbreviate(product.parameterValue.get(parameter), 30, "...")}</td>
									</tr>
								[/#if]
						[#else]
								[#if product.parameterValue.get(parameter)??]
								<tr>
								 	<td class="cpsx_fg" colspan="2">${abbreviate(parameter.name, 20)}</td>
								</tr>
								[/#if]
						[/#if]
					 [/#list]
				 [/#list]
				
                             </tbody></table>
      <div style="margin-left:16px; margin-top:16px;">说明：● 代表有</div>
    </div>
  </div>
  <p class="f_r">
                  <a href="http://www.chinamacro.cn/reshui/ranqi/zhineng/Q6/g775.html">下一篇&gt;</a>
          </p>

</div>

[#include "/gw/product/pright2.ftl" /]</div></div>
	[#include "/gw/include/footer.ftl" /]
</body>
</html>