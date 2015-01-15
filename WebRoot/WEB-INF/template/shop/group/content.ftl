<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/tuangouxiangqing.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/common.css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/swfobject_modified.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.spinner.js"></script>

<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.jqzoom.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect_X.js"></script>
<title>欢迎光临万家乐官方商城-厂家直销、最低价格、品质保障、货到付款、配送及时、放心服务、轻松购物 </title>
<script type="text/javascript"> 
		
		$().ready(function(){
			var $areaId = $("#areaId");
				// 地区选择
			$areaId.lSelect({
				url: "${base}/common/area.jhtml"
			});
		
	        $("#classify_act").mouseover(function(){
	            $("#classify").slideDown();
	         }); 
			 
			 $("#classify").mouseleave(function(){
	            $(this).slideUp();
	         }); 
			 
			 $("#classify_act").mouseleave(function(){
	            $("#classify").slideUp();
	         });
	         
	         function promotionInfo(){
	         	$('#haveTime').each(function(){
	         		var $this = $(this);
	         		var beginDate = $this.attr("beginTimeStamp") != null ? new Date(parseFloat($this.attr("beginTimeStamp"))) : null;
	         		var endDate = $this.attr("endTimeStamp") != null ? new Date(parseFloat($this.attr("endTimeStamp"))) : null;
	         		if(beginDate == null || beginDate <= new Date()){
	         			if(endDate != null && endDate >= new Date()){
	         				var time = (endDate - new Date()) / 1000;
	         				$this.html("<span>"+Math.floor(time / (24 * 3600)) + "</span>天<span>" + Math.floor((time % (24 * 3600)) / 3600) + "</span>时"
	         						+"<span>"+ Math.floor((time % 3600) / 60) +"</span>分<span>"+Math.floor((time % 60))+"</span>秒");
	         			}else if(endDate != null && endDate < new Date()){
	         				//活动已结束
	         				$this.html("<span>${message("shop.index.ended")}</span>");
	         				$("#buyNow").removeAttr('href');
	         				$("#addCart").removeAttr('href');
	         			}
	         		}
	         	});
	         }
	         promotionInfo();
	         setInterval(promotionInfo, 1000);
		});
		
		// 加入购物车
		function addMyCart(id,purchasePrice){
		/*
			if( $('#is_inv').val()<1){
		        alert("请选择配送区域");
				return;
			}
		*/	
			var quantity = $('#quantity').val();
			if(/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 0){
				$.ajax({
					url: "${base}/cart/add.jhtml",
					type: "POST",
					data: {id: id, quantity: quantity, purchasePrice: purchasePrice},
					dataType: "json",
					cache: false,
					success: function(message){
						$.message(message);
					}
				});
			}else{
				$.message("warn","购买数量必须为正整数");
			}
		}
		
		// 立即购买
		function buyNow(id,purchasePrice){
		/*
			if( $('#is_inv').val()<1){
		        alert("请选择配送区域");
				return;
			}
		*/	
			var quantity = $('#quantity').val();
			if(/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 0){
				$.ajax({
					url: "${base}/cart/add.jhtml",
					type: "POST",
					data: {id: id, quantity: quantity,purchasePrice: purchasePrice },
					dataType: "json",
					cache: false,
					success: function(message){
						$.message(message);
						if(message.type=="success"){
							window.location.href="${base}/cart/list.jhtml";
						}
					}
				});
			}else{
				$.message("warn","购买数量必须为正整数");
			}
		}
		
		//收藏商品
		function addFavorite(product_id){
			[#if member==null]
				window.location.href = "${base}/login.jhtml";
				return false;
			[/#if]
			$.ajax({
				url: "${base}/member/favorite/add.jhtml",
				type: "POST",
				data: {id: product_id},
				dateType: "json",
				cache: false,
				success: function(message){
					$.message(message);
				}
			});
			$('#collection_pic').attr('src','${base}/resources/shop/images/fen-2.png');
			$('#collection_dec').text('已收藏该商品');
			return false;
		}
</script>

</head>
<body>
<!--头部-->
[#include "/shop/include/header.ftl" /]

<!--头部结束--> 

<!--产品试用开始-->
<div class="where-big">
  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="${base}/">首页</a></span>><span><a href="${base}/group/list.jhtml">团购中心</a></span>><span>产品详情</span></div>
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
<!--试用详情开始-->
	[#if groupPurchase?has_content]
	[#list groupPurchase as group]
    <div class="shiyongxiangqing">
	      <div class="xiangqing-1">${groupPurchase.grdesc}订金时间：${groupPurchase.prbegindate?string("yyyy-MM-dd HH:mm:ss")} 至 ${groupPurchase.prenddate?string("yyyy-MM-dd HH:mm:ss")}，数量有限，预购从速！</div>
	      <div class="xiangqing-2">
	        <div class="xiangqing-21">
		          <div class="xq-211">
			            <div class="xq-2111">团购价￥</div>
			            <div class="xq-2112">${group.purchasePrice}</div>
		          </div>
	        <div class="xiangqing-22">
		          <div class="xq-221">
			            <ul>
				              <li id="marketPrice">原价</br>${currency(product.marketPrice, true)}</li>
				              <li>节扣</br>8.2折</li>
				              <li id="savePrice">节省</br>${currency(product.marketPrice-group.purchasePrice, true)}</li>
				              
				              <!--
				              <script type="text/javascript">
				              			$('#marketPrice').html('原价</br>￥'+Math.round(${product.marketPrice}));
				              			var result = Math.round(parseFloat(${product.marketPrice})-parseFloat(${group.purchasePrice}));
				              			$('#savePrice').html('节省</br>￥'+result);
				              </script>-->
			            </ul>
		          </div>
		          <div class="xq-222">
			            <div class="xq-2221">
			            		${group.wantcount1}人&nbsp;&nbsp;&nbsp;${group.wantcount2}人&nbsp;&nbsp;&nbsp;
				          		${group.wantcount3}人&nbsp;&nbsp;&nbsp;${group.wantcount4}人&nbsp;&nbsp;&nbsp;
				          		${group.wantcount5}人&nbsp;&nbsp;&nbsp;人数
			            </div>
			            <div><img src="${base}/resources/shop/images/tuan-13.png" width="227px"></div>
			            <div class="xq-2221">
			            		${group.purchasePrice1}&nbsp;&nbsp;&nbsp;${group.purchasePrice2}&nbsp;&nbsp;
				          		${group.purchasePrice3}&nbsp;&nbsp;&nbsp;${group.purchasePrice4}&nbsp;&nbsp;
				          		${group.purchasePrice5}&nbsp;&nbsp;价格
			            </div>
		          </div>
	        </div>
	        <div class="xiangqing-23">
		          <div class="xq-231">${group.buycount}人已成功购买，数量有限，下单要快哟</div>
		          <!--<div class="xq-232"><img src="${base}/resources/shop/images/xq-231.png"></div>-->
	        </div>
	        <div class="xiangqing-24">
		          <div class="xq-24-left">
	            	<!--
			            <div class="xq-241">配送至：
			            		<select name="CustType" onchange="changedata(this)"  class="where-y">  
					                  <option value="-1">佛山</option>
					                  <option value="0">广州</option>
					            </select>
					            &nbsp;&nbsp;(请选择配送区域)
					        <span class="fieldSet" style=" padding-left: 10px; ">
									<input type="hidden" id="areaId" name="areaId" />
									<input type="hidden" id="is_inv" name="is_inv" />
							</span>
			            </div>
			        -->
			            <div class="xq-242">
				              <div class="xq-2421">我要买：</div>
				              <div><input type="text" class="spinnerExample" id="quantity" name="quantity" /></div>
			            </div>
			            <script type="text/javascript">
			            		$('.spinnerExample').spinner({});
			            </script>
			            <div class="tehui-xq1-c">
			                <div class="tehui-xq1-c1"><a href="javascript:buyNow(${product.id},${group.purchasePrice});" id="buyNow">立即购买</a></div>
			                <div class="tehui-xq1-c2"><a href="javascript:addMyCart(${product.id},${group.purchasePrice});" id="addCart">加入购物车</a></div>
			            </div>
		          </div>
		          <div class="xq-25-right">
			            <div class="xq-251"><img src="${base}/resources/shop/images/time.png" width="20px";>&nbsp;剩余时间</div>
			            <!--<div class="xq-252"><span>38</span>天<span>15</span>小时<span>23</span>分<span>50</span>秒</div>-->
			            <div class="xq-252" id="haveTime" [#if group.begindate??] beginTimeStamp="${group.begindate?long}"[/#if][#if group.enddate??] endTimeStamp="${group.enddate?long}"[/#if]>
			            		
			            </div>
		          </div>
	        </div>
	        <div class="fen-1">
		          <ul>
			            <li>分享到：</li>
			            <li><a class="jiathis_button_qzone" title="分享到QQ空间"><img src="${base}/resources/shop/images/qq_ico.png" width="17px";></a></li>
				          <li><a class="jiathis_button_renren" title="分享到人人网"><img src="${base}/resources/shop/images/renren_ico.png" width="17px";></a></li>
				          <li><a class="jiathis_button_tqq" title="分享到腾讯微博"><img src="${base}/resources/shop/images/tx_weibo_ico.png" width="17px";></a></li>
				          <li><a class="jiathis_button_tsina" title="分享到新浪微博"><img src="${base}/resources/shop/images/weibo_ico.png" width="17px";></a></li>
				          <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
		          </ul>
	          	  <div class="fen-11">
	          	  		<a href="javascript:addFavorite(${product.id});">
	          	  			[#if hasFavoriteProduct]
	          	  				<img src="${base}/resources/shop/images/fen-2.png" id="collection_pic"><span id="collection_dec">已收藏该商品</span>
	          	  			[#else]
	          	  				<img src="${base}/resources/shop/images/fen-22.png" id="collection_pic"><span id="collection_dec">收藏此商品</span>
	          	  			[/#if]	
	          	  		</a>	
	          	  </div>
	        </div>
	      </div>
	        <div class="tuan-12">
							      		<img src="${group.path}"  />
							      		<div class="jia">
										<div class="name1">${product.name}</div>
										<div class="name2">${(product.seoKeywords)!""}</div>
							      				<span class="jia-1">活动价：</span><span class="jia-2">${group.purchasePrice}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							      				<span class="jia-1">定金：</span><span class="jia-2">${group.previousPrice}</span></br>
							      				已参团人数：<span class="jia-3">${group.buycount}</span>
							      		</div>
							      </div>
							      </div>
  </div>
  <div class="xq-photo">${(group.images)!}</div>
  [/#list]
  [/#if]
<!--产品试用完-->

<!--底部开始-->
[#include "/shop/include/footer.ftl" /]
</body>
</html>
