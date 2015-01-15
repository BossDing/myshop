<html>
<head>
<meta http-equiv="content-type" content="charset=utf-8" />
<title>UP</title>

<link href="${base}/resources/shop/css/heard.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$().ready(function() {
    /**导航动态样式*/
       var url = window.location.href;
       var inputs = $("#h_nav input");
       var hos= "http://"+ window.location.host;
       for(var y=0;y<inputs.length;y++){
         if(url==hos+$(inputs[y]).val()){
           $(inputs[y]).parent("li").attr("id","classify_ppp").siblings("li").removeAttr("id");
           $("#h_nav li").first().attr("id","classify_act");
           break;
         }
       }
	$("#classify_ppp").prev().css("background", "none");
	var $headerLogin = $("#headerLogin");
	var $headerRegister = $("#headerRegister");
	var $headerUsername = $("#headerUsername");
	var $headerLogout = $("#headerLogout");
        var $productSearchForm= $("#productSearchForm");
        var $keyword = $("#productSearchForm input");
        $headerLogin.hide();
        $headerRegister.hide();
        $headerUsername.hide();
        $headerLogout.hide();
        var username = getCookie("username");
	if ($.checkLogin()) {
		var path = location.href;
		if(path.lastIndexOf("login.jhtml") == -1){
		queryFavorite();
		queryShippedOrder();
		}
		$("#welcome").text("${message("shop.header.welcome")}, " + username);
		$headerUsername.text("${message("shop.header.welcome")}, " + username).show();
		$headerLogout.show();
	} else {
		$headerLogin.show();   
		$headerRegister.show();
	}
        $('#categoryList').delegate("li", "mousemove",
             function (e) {
             var height = $(this).offset().top;
             var index = $(this).index();
             if ($(this).find('div').hasClass("hover") == true) {
                return
              }
             $(this).find('div').addClass('hover').siblings().removeClass("hover");
             var list_top = index * 43 + 16;
             $(this).find(".i-mc").css("top", list_top);
             return false;
             }).delegate("li", "mouseleave",
              function (event) {
             $(this).find('div').removeClass("hover");
        })
    	$productSearchForm.submit(function() {
               	if ($.trim($keyword.val()) == "") {
			return false;
		}
	});
        $('.all').mouseover(function(){
		$('.allsort').css('display','block');
	});
	
	$('.all').mouseout(function(){	
              if(window.location.pathname !== '/'){		
               $('.allsort').css('display','none');   
              }	
	}); 
        $('.allsort').mouseover(function(){
		$('.allsort').css('display','block');
	});
	
	$('.allsort').mouseout(function(){
              if(window.location.pathname !== '/'){
	       $('.allsort').css('display','none');
              }
	});
        /**$(".nogoods").mouseover(function () {
                $.ajax({
				url: "/cart/list_forindex.jhtml",
				type: "POST",
                                data:{id:348,quantity:1},
				dataType: "json",
				cache: false,   
				success: function(data) {  
                                        //$("#cartcount").text("");
                                        //$("#cartall").append("<h1>123</h1>"); 
                                        $(".cartftb").find('dl').addClass('carthover');
                                }
	        }); 
        });
        **/ 
        $(".nogoods").mouseover(function () {
             $(".cartftb").find('dl').removeClass('carthover');
        });
        $("#cartListViews").mouseout(function () {
              $(".cartftb").find('dl').removeClass('carthover');
        }); 
        queryInfo();  
        $("#useweixin").click(function(){
            $("#twocodeParent").toggle();
        });
	    /**搜索提交**/
       $("#soso_but").click(function(){
         $("#productSearchForm").submit();
       });
       
      
       
       
});

// 加入购物车
function addCart(id) {
	var status = true;
	$.ajax({
		url: "${base}/cart/queryCartIsExistStore.jhtml",
		type: "POST",
		data: {id: id},
		dataType: "json",
		cache: false,
		success: function(data) {
			if(data.message.type == "success") {
				
			} else if(data.message.type == "error") {
				$.message(data.message);
				status = false;
			}
			if(!status) {
				return;
			}
			
			$.ajax({
				url: "${base}/cart/add.jhtml",
				type: "POST",
				data: {id: id, quantity: 1},
				dataType: "json",
				cache: false,
				success: function(message) {
					window.location.href="${base}/cart/list.jhtml";
				}
			});
		}
	});
	
}
function queryInfo(){
       	 $.ajax({
			url: "/cart/queryCartCount.jhtml",
			type: "POST",
			dataType: "json",
			cache: false,				
			success: function(data) {
				if (data.message.type == "success") {
                                      //document.getElementById("cart_buy").innerHTML=data.quantity;
                                      $("#cartofcount").text("");
                                      $("#cartofcount").append(data.quantity);
                                      $("#headcartcount").text("");
                                      $("#headcartcount").append(data.quantity);
				}
			},
			complete: function() {				
			}
		});
}
function queryFavorite(){
       	 $.ajax({
			url: "/member/favorite/queryFavoriteCount.jhtml",
			type: "POST",
			dataType: "json",
			cache: false,				
			success: function(data) {
				if (data.message.type == "success") {
                                      $("#favoriteCount").text("");
                                      $("#favoriteCount").append(data.favoriteCount);
				}
			},
			complete: function() {				
			}
		});
}
function queryShippedOrder(){
       	 $.ajax({
			url: "/member/order/queryShippedCount.jhtml",
			type: "POST",
			dataType: "json",
			cache: false,				
			success: function(data) {
				if (data.message.type == "success") {
                                      $("#shippedCount").text("");
                                      $("#shippedCount").append(data.shippedCount);
				}
			},
			complete: function() {				
			}
		});
}
$(function(){

	// 线程 IDs
	//var mouseover_tid = [];
	//var mouseout_tid = [];
	
	//$('#classify_act').hover(
	//	function(){
	//		// 停止卷起事件
	//		clearTimeout(mouseout_tid[0]);
	//		// 当鼠标进入超过 0.2 秒, 展开菜单, 并记录到线程 ID 中
	//		mouseover_tid[0] = setTimeout(function() {
	//			$(this).children("#classify").slideDown(200);
	//		}, 0);
	//	},
	//	function(){
	//		 // 停止展开事件
	//		clearTimeout(mouseover_tid[0]);
	//		// 当鼠标离开超过 0.2 秒, 卷起菜单, 并记录到线程 ID 中
	//		mouseout_tid[0] = setTimeout(function() {
	//			$(this).children("#classify").slideUp(200);
	//		}, 0);
	//	}
	//);
         
         
        $("#classify_act").mouseover(function(){
            $("#classify").slideDown(200);
         }); 
		 
		 $("#classify").mouseleave(function(){
            $(this).slideUp(200);
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp(200);
         }); 
		 $("#ImageList ul li").click(function(){
            window.open($("#rll").val());
          });
	});
</script>
</head>
<body>
<!--头部-->
<div id="head" >
  <div id="site_nav">
    <div class="content">
      <div class="hi_login"> <span>嗨，欢迎来到万家乐官方商城</span> <span id ="headerLogin"><a href="${base}/login.jhtml">[登录]</a></span><span id ="headerRegister"><a href="${base}/register.jhtml">[注册]</a></span><span id="headerUsername"></span><span id="headerLogout"><a class="login" href="${base}/logout.jhtml">[退出]</a></span> </div>
      <div class="head_my_infor">
        <ul id="main_nav">
         <li id="shop_car"><a href="${base}/cart/list.jhtml">购物车</a>
		   <dl>
			 <dd><a href="javascript:;" id="welcome"></a></dd>
			 <dd>
				<p>订单最新状况:</p>
				<p>收藏:<span id="favoriteCount">0</span>购物车:<span id="headcartcount">0</span>已发货:<span id="shippedCount">0</span></p>
				</dd>
			 <dd class="last-child"><a href="${base}/member/order/list.jhtml">查看所有订单</a></dd>
		   </dl>
          </li>
	 [@navigation_list position = "top" storeid=""]
		[#list navigations as navigation]
		      [#if navigation.name="微商城"]
		      <li class="sjqy">
                    	<a style="cursor:pointer;" id="dilog">微商城</a>
                    	<!--二维码弹出框  -->
                        <div class="tanchu">
                            <div class="ewm"></div>
                            
                            <b></b>
                        </div>
                        <!--二维码弹出框  end-->
                    </li>
              [#else]      
				<li><a href="${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a></li>
		      [/#if]
		[/#list]
	[/@navigation_list]
        </ul>
      </div>
    </div>
  </div>
  <div class="content">
    <a href="/"><div id="logo"></div></a>
    <div id="soso">
     <form id="productSearchForm" action="${base}/product/search.jhtml" method="get">
      <input type="text" id="soso_text" value=""   name="keyword"/>
      <input type="button"  id="soso_but" />

      <div id="soso_hot">
        <ul>
		[#if setting.hotSearches?has_content]
					<li>${message("shop.header.hotSearch")}:</li>
					[#list setting.hotSearches as hotSearch]
						<li><a href="${base}/product/search.jhtml?keyword=${hotSearch?url}">${hotSearch}</a></li>
					[/#list]
		[/#if]
         
        </ul>
      </div>
      </form>
    </div>
    <DIV id="clear"></DIV>
  </div>
</div>

<div id="h_nav">
  <div class="content">
    <ul>
      <li id="classify_act">商品分类

      
                <div id="classify" style="display:none;">

		[@product_category_root_list entcode="macro" useStore="true" useCache="false"]
		<ul>
			[#list productCategories as category]
				<li>
				<dl[#if !category_has_next] class="last"[/#if]>
					<dt>
						<a href="${base}${category.path}">${category.name}</a>
					</dt>
					[@product_category_children_list productCategoryId = category.id count = 4]
						[#list productCategories as productCategory]
							<dd>
								<a href="${base}${productCategory.path}">${productCategory.name}</a>
							</dd>
						[/#list]
					[/@product_category_children_list]
				</dl>
			[/#list]
		</ul>
		[/@product_category_root_list]
          <!--<ul>
            <li>
              <dl>
                <dt>热水系统</dt>
                <dd><a href="#">燃气热水器</a></dd>
                <dd><a href="#">空气能热水器</a></dd>
                <dd><a href="#">电热水器</a></dd>
                <dd><a href="#">太阳能热水器</a></dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>厨房电器产品</dt>
                <dd><a href="#">厨电套装</a></dd>
                <dd><a href="#">吸油烟机</a></dd>
                <dd><a href="#">燃气灶具</a></dd>
                <dd><a href="#">消毒柜</a></dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>供暖系统</dt>
                <dd><a href="#">燃气壁挂炉</a></dd>
                <dd><a href="#">散热器</a></dd>
                <dd><a href="#">模块锅炉</a></dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>生活电器</dt>
                <dd><a href="#">电压力锅</a></dd>
                <dd><a href="#">电池炉</a></dd>
                <dd><a href="#">养生壶</dd>
                <dd><a href="#">电饼铛</a></dd>
                <dd><a href="#">电饭锅</a></dd>
                <dd><a href="#">电风扇</a></dd>
                <dd><a href="#">电暖器</a></dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>空气净化器</dt>
                <dd><a href="#">母婴专用</a></dd>
                <dd><a href="#">办公专用</a></dd>
                <dd><a href="#">pro-air商铺</a></dd>
                <dd><a href="#">个人专用</a></dd>
              </dl>
            </li>
          </ul>-->
        </div>
      </li>
      [@navigation_list position = "middle"]
		[#list navigations as navigation]
			<li>
				<a class=word href="${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a>
				<input type="hidden" value="${navigation.url}" />
			</li>
		[/#list]
	[/@navigation_list]
    </ul>
    <DIV id="clear"></DIV>
  </div>
</div>

<!-- QQ客服start -->
    <SCRIPT type=text/javascript src="${base}/resources/shop/js/kefu.js"></SCRIPT>
    <LINK rel=stylesheet type=text/css href="${base}/resources/shop/css/qq.css"> 
    <!-- end -->

<!-- QQ客服start -->
		<DIV id=floatTools class=float0831> 
			<DIV class=floatL>
				<A  id=aFloatTools_Show class=btnOpen title=查看在线客服
					onclick="javascript:$('#divFloatToolsView').animate({width: 'show', opacity: 'show'}, 'normal',function(){ $('#divFloatToolsView').show();kf_setCookie('RightFloatShown', 0, '', '/', 'www.istudy.com.cn'); });$('#aFloatTools_Show').attr('style','display:none');$('#aFloatTools_Hide').attr('style','display:block');"
					href="javascript:void(0);">展开</A>
				<A style="DISPLAY: none" id=aFloatTools_Hide class=btnCtn title=关闭在线客服
					onclick="javascript:$('#divFloatToolsView').animate({width: 'hide', opacity: 'hide'}, 'normal',function(){ $('#divFloatToolsView').hide();kf_setCookie('RightFloatShown', 1, '', '/', 'www.istudy.com.cn'); });$('#aFloatTools_Show').attr('style','display:block');$('#aFloatTools_Hide').attr('style','display:none');"
					href="javascript:void(0);">收缩</A>
			</DIV>
			<DIV style="display:none;" id=divFloatToolsView class=floatR>
				<DIV class=tp></DIV>
				<DIV class=cn>
					<UL>
						<LI class=top>
							<H3 class=titZx>QQ咨询</H3> 
						</LI>
						<LI>
							<SPAN class=icoZx>在线咨询</SPAN>
						</LI> 
						
						
						        <LI>
									<A target="_blank" class=icoTc href="http://wpa.qq.com/msgrd?v=3&uin=3033958019&site=qq&menu=yes">
									
										客服一
									
									</A>
								</LI>
   <LI>
									<A target="_blank" class=icoTc href="http://wpa.qq.com/msgrd?v=3&uin=3033958019&site=qq&menu=yes">
									
										客服二
									
									</A>
								</LI>

						
						        
						

					</UL>
					<UL>
						<LI>
							<H3 class=titDh>
								电话咨询
							</H3>
						</LI>
						<LI>
							<SPAN class=icoTl>0757-66620898</SPAN>
						</LI>
					</UL>
				</DIV>
			</DIV>
		</DIV>
        <!-- end -->
	<script>
		$('#divFloatToolsView').animate({width: 'show', opacity: 'show'}, 'normal',function(){ $('#divFloatToolsView').show();kf_setCookie('RightFloatShown', 0, '', '/', 'www.istudy.com.cn'); });$('#aFloatTools_Show').attr('style','display:none');$('#aFloatTools_Hide').attr('style','display:block');
	</script>

<!--头部结束--> 
</body>
</html>