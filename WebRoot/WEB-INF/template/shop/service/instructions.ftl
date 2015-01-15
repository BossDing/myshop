<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/heard.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/shuomingshu.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/bottom.css"/>
<script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>
<title>万家乐商城</title>
<script type="text/javascript"> 
	$(function(){
        $("#classify_act").mouseover(function(){
            $("#classify").slideDown();
         }); 
		 
		 $("#classify").mouseleave(function(){
            $(this).slideUp();
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp();
         });
        $("#queryIns").click(function(){
            var  proSn = $("#proSn").val();
            $.ajax({
               url:"${base}/product/searchPros.jhtml",
               type: "GET",
               data:{proSn:proSn,entCode:'macro'},
               datatype:"json",
               traditional: true,
		       cache: false,
               success:function(data){
                 if(null!=data){
                 var products = data;
                 var str ="";
                 for(var i=0;i<products.length;i++){
                       str += "<li><a href='"+products[i].instruction+"' >"+"&nbsp;"+products[i].name +"</a></li>";
                  }
                  $("#searchList").html(str);
                  $("#searchListDiv").show();
                }
               }
            });
        }); 
        $("#queryIns1").click(function(){
            var  proSn = $("#proSn").val();
            $.ajax({
               url:"${base}/product/searchPros.jhtml",
               type: "GET",
               data:{proSn:proSn,entCode:'macro'},
               datatype:"json",
               traditional: true,
		       cache: false,
               success:function(data){
                 if(null!=data){
                  var products = data;
                  var str ="";
                  for(var i=0;i<products.length;i++){
                     str += "<li><a href='"+products[i].instruction+"' >"+"&nbsp;"+products[i].name +"</a></li>";
                  }
                   $("#searchList").html(str);
                   $("#searchListDiv").show();
                }
             }
            });
        }); 
	});
	/**选中所选说明书事件*/
	function changedata(){
      var id = $("#instruction").val();
      if(null!=id&&"undefined"!=id&&""!=id){
        $("#downloadIns").attr("href",id);
      }
	}
</script>
</head>
<body>
[#include "/shop/include/header.ftl" /]
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="${base}/">首页</a></span>><span>服务中心</span>><span>说明书下载</span></div>
  <div class="kfdlb">
      <ul>
          <li>分享到：</li>
          <li><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
          <li><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
          <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
      </ul>
  </div>
</div>

<!--说明书下载开始-->
<div class="shuoming-big">
  <div class="big-a">请选择产品类别，获得常见问题、服务政策、说明书下载等服务支持。</div>
  <div class="big-b">
    <div class="big-left">
      <div class="big-left-big">
        <div class="photo"><a href="#"><img src="${base}/resources/shop/images/shuomingshu-1.jpg"></a></div>
        <div class="big-right">
          <div class="big-right-1">请选择具体产品下载说明书</div>
          <!--<div class="big-right-2">产品型号<select id="instruction" name="CustType" onchange="changedata(this.options.selectedIndex);"  class="hao">  -->
           <div class="big-right-2">产品型号<select id="instruction" name="CustType"   class="hao" onchange="changedata()" >  
             <option value="-1">请选择</option>
           [#list products as product]
                <option value="${product.instruction}" >${product.sn}&nbsp;${product.name}</option>
           [/#list]
                </select>
          </div>
          <div class="big-right-3"><a href="#" id="downloadIns">下载</a></div>
          <div class="big-right-4">按产品型号或名称查找</div>
          <div class="big-right-5"><input type="text" id="proSn" class="soso_text" value="输入产品型号" onFocus="this.value=''" />
          <input type="button"  class="soso_but" id="queryIns" /></div>
          <div class="big-right-5" style="display:none;" id="searchListDiv">
              <ul id="searchList"></ul>
          </div>
          <div class="big-right-6"><input name="" type="button" value="查询" id="queryIns1"></div>
        </div>
      </div>
    </div>
    <div class="big-right-w">
      <div class="right-lei">
        <div class="lei-1"><a href="${base}/article/queryArticle/437.jhtml"><span>购物指南</span></br>提供注册登录，密码相关，购物流程等相关的购特物指引</a></div>
        <div class="lei-2"><a href="${base}/article/queryArticle/447.jhtml"><span>服务政策</span></br>提供安装、售后服务的相关指导，安装维修服务收费指引等</a></div>
        <div class="lei-3"><a href="${base}/article/queryArticle/445.jhtml"><span>配送服务</span></br>提供配送服务查询，配送及安装服务收费指引等</a></div>
        <div class="lei-4"><a href="${base}/article/list/609.jhtml"><span>支付方式</span></br>各类关于支付方式的详细说明都能在此找到</a></div>
      </div>
      <div class="dian"><a href="${base}/article/list/609.jhtml">请点击此处》</a></div>
    </div>
  </div>
  <div class="poi">
    <div class="poi-a">常见问题</div>
  <!--  <div class="poi-b"><a href="#">更多故障信息></a></div>-->
  </div>
  <div class="hot">
     [#list articles as article]
        ${article.content}
     [/#list]
  </div>
</div>

<!--说明书下载完-->
[#include "/shop/include/footer.ftl" /]
</body>
</html>
