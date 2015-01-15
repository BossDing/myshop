<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>欢迎光临万家乐官方商城-厂家直销、最低价格、品质保障、货到付款、配送及时、放心服务、轻松购物 </title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="author" content="SHOP++ Team" />
	<meta name="copyright" content="SHOP++" />
	<meta name="keywords" content="万家乐,万家乐集团,万家乐商城,万家乐官方商城,万家乐官网,万家乐官方网站,万家乐官方旗舰店,万家乐旗舰店,万家乐商城旗舰店,万家乐网上商城,万家乐官方网站,热水器,万家乐热水器,万家乐家电,家电网购" />
	<meta name="description" content="万家乐官方商城-中国直销商城，在线销售燃气热水器、电热水器、空气能热水器、太阳能热水器、厨电套装、燃气灶、吸油烟机、消毒柜、壁挂炉、散热器、空气净化器、生活电器等产品，为您提供愉悦的网上商城购物体验!" />
    <link href="${base}/resources/shop/css/index.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/banner.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/jquery.slides.min.js"></script>
    <script type="text/javascript">
     $().ready(function() {
     
	var $slider = $("#slider");
	var $newArticleTab = $("#newArticle .tab");
	var $promotionProductTab = $("#promotionProduct .tab");
	var $promotionProductInfo = $("#promotionProduct .info");
	var $hotProductTab = $("#hotProduct .tab");
	var $newProductTab = $("#newProduct .tab");
	var $hotProductImage = $("#hotProduct img");
	var $newProductImage = $("#newProduct img");
	var $qiye_click = $("#qiye_click");
	var $shangcheng_click = $("#shangcheng_click");
	var $jiadian_click = $("#jiadian_click");
	/**
	$slider.nivoSlider({
		effect: "random",
		animSpeed: 1000,
		pauseTime: 6000,
		controlNav: true,
		keyboardNav: false,
		captionOpacity: 0.4
	});
	
	
	$newArticleTab.tabs("#newArticle .tabContent", {
		tabs: "li",
		event: "mouseover",
		initialIndex: 1
	});
	
	$promotionProductTab.tabs("#promotionProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	$hotProductTab.tabs("#hotProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	$newProductTab.tabs("#newProduct .tabContent", {
		tabs: "li",
		event: "mouseover"
	});**/

$qiye_click.mouseover(function(){
		$qiye_click[0].className="module_join";
	});
$qiye_click.mouseout(function(){
	$qiye_click[0].className="module_join_t";
});
$shangcheng_click.mouseover(function(){
	$shangcheng_click[0].className="module_join";
});
$shangcheng_click.mouseout(function(){
	$shangcheng_click[0].className="module_join_t";
});
$jiadian_click.mouseover(function(){
	$jiadian_click[0].className="module_join";
});
$jiadian_click.mouseout(function(){
	$jiadian_click[0].className="module_join_t";
});
	
 $('#slides').slidesjs({
        width: 550,
        height: 278,
        play: {
          active: true,
          auto: true,
          interval: 2000,
          swap: true
        }
      });
	function promotionInfo() {
		$promotionProductInfo.each(function() {
			var $this = $(this);
			var beginDate = $this.attr("beginTimeStamp") != null ? new Date(parseFloat($this.attr("beginTimeStamp"))) : null;
			var endDate = $this.attr("endTimeStamp") != null ? new Date(parseFloat($this.attr("endTimeStamp"))) : null;
			
if (beginDate != null) {
				if (endDate != null) {
					var time = (endDate - new Date()) / 1000;
alert(time);
					$this.html("${message("shop.index.remain")}:<em>" + Math.floor(time / (24 * 3600)) + "<\/em> ${message("shop.index.day")} <em>" + Math.floor((time % (24 * 3600)) / 3600) + "<\/em> ${message("shop.index.hour")} <em>" + Math.floor((time % 3600) / 60) + "<\/em> ${message("shop.index.minute")}");
				} else if (endDate != null && endDate < new Date()) {
					$this.html("${message("shop.index.ended")}");
				} else {
					$this.html("${message("shop.index.going")}");
				}
			}
		});
	}
	
	promotionInfo();

	setInterval(promotionInfo, 60 * 1000);	
	$('.info').each(function(){
               var $this = $(this);
			var beginDate = $this.attr("beginTimeStamp") != null ? new Date(parseFloat($this.attr("beginTimeStamp"))) : null;
			var endDate = $this.attr("endTimeStamp") != null ? new Date(parseFloat($this.attr("endTimeStamp"))) : null;
               if (beginDate == null || beginDate <= new Date()) {
				if (endDate != null && endDate >= new Date()) {
					var time = (endDate - new Date()) / 1000;
					$this.next().next().next().next().next().append("${message("shop.index.remain")}:<em>" + Math.floor(time / (24 * 3600)) + "<\/em> ${message("shop.index.day")} <em>" + Math.floor((time % (24 * 3600)) / 3600) + "<\/em> ${message("shop.index.hour")} <em>" + Math.floor((time % 3600) / 60) + "<\/em> ${message("shop.index.minute")}");
				} else if (endDate != null && endDate < new Date()) {
					$this.next().next().next().next().next().html("${message("shop.index.ended")}");
                                        $this.parent().hide();   
				} else {
					$this.next().next().next().next().next().html("${message("shop.index.going")}");
				}
	       }else{
                    $this.parent().hide();     
               }
        });
        /**
	$hotProductImage.lazyload({
		threshold: 100,
		effect: "fadeIn",
		skip_invisible: false
	});
	
	$newProductImage.lazyload({
		threshold: 100,
		effect: "fadeIn",
		skip_invisible: false
	});**/
    $('.allsort').css('display','block');
    BindRecommend("#HomeTab", "a", "#HomeRecommend ul#countDownTimeSpan", "on", true);
    BindRecommend("#show_pro_title", "a", "#BottomProductlist ul", "show_a", true);
        var areaGroups = [];
    areaGroups[-999999] = { "status": 1, "msg": true };
    $(".rec_sku").delegate(".pic", "mouseenter", function () {
        var img = $(this).find('.buy').find('img');
        var id = $(this).attr('data-areaGroupSysNo');
        if (id) {
            $(this).removeAttr('data-areaGroupSysNo');
            if (typeof(areaGroups[id]) != 'undefined') {
                if (!areaGroups[id]) {
                    var src = img.attr('src');
                    src = src.replace('btn_buy.gif', 'btn_buy_un.gif');
                    img.attr('src', src);
                }
            } else {
                $.ajax({
                    url: '/ajax/CheckProductAreaIsCanDelivery',
                    data: { areaGroupSysNo: id },
                    type: 'POST',
                    success: function (data) {
                        if (data.status == 1) {
                            areaGroups[id] = data.msg;
                            if (!data.msg) {
                                var src = img.attr('src');
                                src = src.replace('btn_buy.gif', 'btn_buy_un.gif');
                                img.attr('src', src);
                            }
                        }
                    }
                })
            }
        }
        $(this).find("img").eq(0).stop(true, false).animate({ "margin-top": "-10px", "opacity": "1" }, 300);
        $(this).find(".buy").stop(true, false).animate({ "top": "120px" }, 200);
    });
    $(".rec_sku").delegate(".pic", "mouseleave", function () {
        $(this).find("img").eq(0).stop(true, false).animate({ "margin-top": "0px", "opacity": "1" }, 200);
        $(this).find(".buy").stop(true, false).animate({ "top": "150px" }, 300);
    }); 
     $("#shouye").addClass("on"); 
	 
      $("#HomeRecommend").delegate(".pic", "mouseenter", function () {
        var img = $(this).find('.buy').find('img');
        var id = $(this).attr('data-areaGroupSysNo');
        if (id) {
            $(this).removeAttr('data-areaGroupSysNo');
            if (typeof(areaGroups[id]) != 'undefined') {
                if (!areaGroups[id]) {
                    var src = img.attr('src');
                    src = src.replace('btn_buy.gif', 'btn_buy_un.gif');
                    img.attr('src', src);
                }
            } else {
                $.ajax({
                    url: '/ajax/CheckProductAreaIsCanDelivery',
                    data: { areaGroupSysNo: id },
                    type: 'POST',
                    success: function (data) {
                        if (data.status == 1) {
                            areaGroups[id] = data.msg;
                            if (!data.msg) {
                                var src = img.attr('src');
                                src = src.replace('btn_buy.gif', 'btn_buy_un.gif');
                                img.attr('src', src);
                            }
                        }
                    }
                })
            }
        }
        $(this).find("img").eq(0).stop(true, false).animate({ "margin-top": "-10px", "opacity": "1" }, 300);
        $(this).find(".buy").stop(true, false).animate({ "top": "120px" }, 200);
    });
    $("#HomeRecommend").delegate(".pic", "mouseleave", function () {
        $(this).find("img").eq(0).stop(true, false).animate({ "margin-top": "0px", "opacity": "1" }, 200);
        $(this).find(".buy").stop(true, false).animate({ "top": "150px" }, 300);
    });
    /**新闻公告点击事件**/
    if(null!=$("#qiye")&&"undefined"!=$("#qiye")){
       $("#qiye_click").click(function(){
           window.location.href="${base}/article/adverte/"+$("#qiye").val()+"/-1.jhtml";
       });
    }
    if(null!=$("#shangcheng")&&"undefined"!=$("#shangcheng")){
       $("#shangcheng_click").click(function(){
           window.location.href="${base}/article/adverte/"+$("#shangcheng").val()+"/-1.jhtml";
       });
    }
    if(null!=$("#jiadian")&&"undefined"!=$("#jiadian")){
       $("#jiadian_click").click(function(){
           window.location.href="${base}/article/categoryList/"+$("#jiadian").val()+".jhtml";
       });
    }
         
});
  
function BindRecommend(targetId, child, showId, cssClass, isShowImg) {
    $(targetId).delegate(child, "mouseover",
    function () {
        var obj = this;
        IndexRecommend = $(obj).index();
        target = targetId;
        setTimeout(function () {
            if (IndexRecommend == $(obj).index() && target == targetId && IndexRecommend >= 0) {
                var showIdList = showId.split("#");
                if (showIdList.length == 2) {
                    $(showId).hide()
                } else {
                    for (var a in showIdList) {
                        if (showIdList[a] == "") {
                            continue
                        }
                        $("#" + showIdList[a]).hide()
                    }
                }
                var name = $(obj).attr("name");
                var nameList = name.split("#");
                if (nameList.length == 2) {
                    if (isShowImg && $(obj).attr("data-img") == "0") {
                        $(name).fadeIn(1200)
                    } else {
                        $(name).show()
                    }
                } else {
                    for (var a in nameList) {
                        if (nameList[a] == "") {
                            continue
                        }
                        $("#" + nameList[a]).show()
                    }
                }
                if (isShowImg) {
                    var data = $(obj).attr("data-img");
                    if (data) {
                        if (data == "0") {
                            $(obj).attr("data-img", "1");
                            lazyLoad.Run(nameList[1])
                        }
                    }
                }
                $(obj).addClass(cssClass).siblings().removeClass(cssClass)
            }
        },
        400)
    }).delegate(child, "mouseout",
    function () {
        IndexRecommend = -1
    })
}

function AddProductToCart(sysNo,count,name,image,price) {
                var quantity = count;
		var product_id = sysNo ;

		if (/^\d*[1-9]\d*$/.test(quantity) && parseInt(quantity) > 0) {			
			$.ajax({
				url: "/cart/add.jhtml",
				type: "POST",
				data: {id: product_id, quantity: quantity},
				dataType: "json",
				cache: false,
				success: function(message) { 
                                    if(!(message.type == 'warn')){
                                        $.message(message);
                                        $("#cartpic").text("");
                                        $("#cartname").text("");
                                        $("#cartprice").text("");
                                        $("#cartpic").append("<img src='"+image+"'/>");
                                        $("#cartname").append(name);
                                        $("#cartprice").append("￥"+price);
                                        var cartcount = parseInt($("#cartofcount").text())+1;
                                        $("#cartofcount").text("");
                                        $("#cartofcount").append(cartcount.toString());
                                        $("#headcartcount").text("");
                                        $("#headcartcount").append(cartcount.toString());
                                        $("#is_cartin").text("");
                                        $("#is_cartin").append("x1&nbsp;&nbsp;加入成功");
                                        $(".pop_cart").css("right", "-248px");
                                        $("#right_cart").toggleClass("cart_on", true);
                                        $(".pop_cart").show().stop(true, true).animate({ "right": "52px" }, 1000);
                                        setTimeout(function () {
                                            $("#right_cart").removeClass("cart_on", true);
                                            $(".pop_cart").hide();
                                        }, 3000);
                                        }
                                    else{
                                        $("#cartpic").text("");
                                        $("#cartname").text("");
                                        $("#cartprice").text("");
                                        $("#is_cartin").text("");
                                        $("#is_cartin").append("库存不足，加入失败");
                                        $(".pop_cart").css("right", "-248px");
                                        $("#right_cart").toggleClass("cart_on", true);
                                        $(".pop_cart").show().stop(true, true).animate({ "right": "52px" }, 1000);
                                        setTimeout(function () {
                                            $("#right_cart").removeClass("cart_on", true);
                                            $(".pop_cart").hide();
                                        }, 3000);                                 }  
                                    }
                                 
			});
		} else {
			$.message("warn", "${message("shop.product.quantityPositive")}");
		}   
}
 function openFlv(){
   // var filePath = document.getElementById("filePath").value;
	var s5 = new SWFObject("FlvPlayer.swf","playlist","1024","576","7");
	s5.addParam("allowfullscreen","true");
	s5.addVariable("autostart","true");
	s5.addVariable("image","flashM-cebbank.jpg");
	s5.addVariable("file","..");
	s5.addVariable("width","1024");
	s5.addVariable("height","576");
	s5.write("d2");
}
    </script>
    <style>
    [@ad_position adname="首页banner" ]
	    [#assign length = 0 /]
	    [#list adPosition.ads as ad]
			[#if ad.hasBegun() && !ad.hasEnded() && ad.type == "image"]
	            [#assign length = length + 1 /]
    			#banner #ImageList ul li.tp_${ads.id} a {
		  			background: url(${ads.path}) no-repeat top center;
    			}
	    	[/#if]
		[/#list]
    	#xydd ul {
    		width:${100*length}px !important;
    	}
	[/@ad_position]
    </style>
</head>
<body>

    [#include "/shop/include/header.ftl" /]
  
     <!--banner开始-->
<div id="banner">
  <div id="ImageList">
    <ul>
    [@ad_position adname="首页banner" ]
    [/@ad_position]
    </ul>
  </div>
</div>
<div id="xydd">
  <ul id="xyddul">
	[@ad_position adname="首页banner" ]
    [#assign index = 0/]
    [#list adPosition.ads as ad]
		[#if ad.hasBegun() && !ad.hasEnded() && ad.type == "image"]
      		<li [#if index == 0]class="cur"[/#if]></li>
            [#assign index = index + 1 /]
    	[/#if]
	[/#list]
	[/@ad_position]
  </ul>
</div>
<!--banner end-->

<div id="poster">
  <div class="content">
    <div id="p_once">
	  <!--banner2开始-->


<div style="width:550px; height:270px;">
  <div class="container" >
    <div id="slides">
    	[@ad_position adname="首页轮播小banner" ]
		[/@ad_position]
    </div>
  </div>
  </div>
<!--banner2 end-->
	</div>
    <div id="p_twice">[@ad_position adname="首页 - 2号位"/]</div>
    <div id="p_thre">[@ad_position adname="首页 - 3号位"/]</div>
  </div>
</div>

<div class="content" style="width:1197px;"> 
  
  <!--精品推荐-->
  <div class="title"><strong>精品推荐</strong></div>
  <div class="recommend">
    <dl>
     <!-- 首页精品推荐左侧大图 -->
     <dt>
     [@product_list tagIds =2  count = 1]
		[#list products as product]
			        <div id="recommend_once">
			           <a href="${(product.path)!''}"><img src="${(product.bigimage)!''}" width="590" height="485" /></a>
			        </div>
			        <div id="recommend_once_text">
			        <div id="text_one">
			        <strong>商品评论:</strong><br>
			        [@review_list productId = product.id count = 3]
						[#if reviews?has_content]
						   [#list reviews as review]
				          		<div class="text_one_w"><div class="score${(review.score * 2)?string("0")}"></div> &nbsp; &nbsp; &nbsp;${abbreviate(review.content, 140)}[#if (review.content?length)?? && review.content?length>70]...[/#if] &nbsp; &nbsp; &nbsp; <span style="padding:0px 20px; font-weight:bold;">${review.member.username} &nbsp; &nbsp; &nbsp;${review.createDate?string("yyyy-MM-dd")}</span></div>
				          [/#list]
				        [/#if]
					[/@review_list] 
					</ul>
					</div> 
			        </div>
			        <div id="recommend_once_infor">   
				          <p class="model">${product.name}</p>
				          <p class="heading">${product.seoKeywords}</p>
				          <p class="price">￥${product.price}</p>
				          <div id="recommend_button">
				            	<a href="${product.path}"><input type="button" id="buy" /></a>
				            	<a href="${product.path}"><input type="button" id="know_more" /></a>
				          </div>
			        </div>      
	     [/#list]
     [/@product_list]     
      </dt>
     <!-- 精品推荐 -->
     [@product_list tagIds =2  count = 4]
		[#list products as product]
		    <dd>
		      <a title="${product.name}"  href="${product.path}"><img alt="${product.name}" src="${product.image}" />
				  <div class="recommend_twice">
					  <p class="model">${product.name}</p>
					  <p class="heading">${product.seoKeywords}</p>
					  <p class="price">￥${product.price}</p>
				  </div>
			  </a>
		    </dd>
		[/#list]
     [/@product_list]
    </dl>
  </div>
  <!--精品推荐结束--> 
  
  <!--新品上市-->
  <div class="title"><strong>新品上市</strong></div>
  <div class="new">
    <ul>
      <li id="n_one">
        [@ad_position adname="首页 - 新品上市 - 1号位"/]
      </li>
      <li id="n_two">
        <div>[@ad_position adname="首页 - 新品上市 - 2号位"/]</div>
      </li>
      <li id="n_three">
        [@ad_position adname="首页 - 新品上市 - 3号位"/]
      </li>
      <li id="n_four">
        [@ad_position adname="首页 - 新品上市 - 4号位"/]
      </li>
    </ul>
  </div>
  <!--新品结束--> 
  
  <!--新闻，公告，常识-->
  <div class="module">           
    <ul>
      <li>
        <div class="title"><strong>企业新闻</strong>
          <div class="module_join_t"  id="qiye_click" style="cursor:pointer;"></div>
        </div>
        <div id="news">
          <div class="module_infor">
		  <ul>
		 [#assign categoryId1 =-1]
             [@article_category_root_list]
               [#list articleCategories as category]
                 [#if category.name=="企业新闻"]
                   [@article_list articleCategoryId =category.id count=6]
                   <input type="hidden" id="qiye" value="${category.id}"/>
                     [#list articles as article]
					  <li>
						<a href="${base}/article/adverte/${category.id}/${article.id}.jhtml" title="${article.title}" target="_blank">${article.title}</a>
					  </li>
					  [#assign categoryId1 =category.id ]
			         [/#list]
			       [/@article_list]
			     [/#if]
			   [/#list]	
             [/@article_category_root_list] 
		</ul>
          </div>
        </div>
      </li>
      <li>
        <div class="title"><strong>商城公告</strong>
          <div class="module_join_t" id="shangcheng_click" style="cursor:pointer;"></div>
        </div>
        <div id="notice">
          <div class="module_infor">
             <ul>
              [@article_category_root_list]
               [#list articleCategories as category]
                 [#if category.name=="商城公告"]
                   [@article_list articleCategoryId =category.id count=6]
                   <input type="hidden" id="shangcheng" value="${category.id}"/>
                     [#list articles as article]
					  <li>
						<a href="${base}/article/adverte/${category.id}/${article.id}.jhtml" title="${article.title}" target="_blank">${article.title}</a>
					  </li>
			         [/#list]
			       [/@article_list]
			     [/#if]
			   [/#list]	
             [/@article_category_root_list] 
		</ul>
          </div>
        </div>
      </li>
      <li style="margin-right:0px;">
        <div class="title"><strong>家电常识</strong>
          <div class="module_join_t"  id="jiadian_click" style="cursor:pointer;"></div>
        </div>
        <div id="know">
          <div class="module_infor">
		<ul>
               [@article_category_root_list]
               [#list articleCategories as category]
                 [#if category.name=="家电常识"]
                   [@article_list articleCategoryId =category.id  count=6]
                   <input type="hidden" id="jiadian" value="${category.id}"/>
                     [#list articles as article]
					  <li>
						<a href="${base}/article/categoryList/${article.articleCategory.id}.jhtml" title="${article.title}" target="_blank">${article.title}</a>
					  </li>
			         [/#list]
			       [/@article_list]
			     [/#if]
			   [/#list]	
             [/@article_category_root_list] 
		</ul>
          </div>
        </div>
      </li>
    </ul>
  </div>
  <!--新闻，公告，常识，end--> 
  
</div>					
    [#include "/shop/include/footer.ftl" /]
</body>
</html>