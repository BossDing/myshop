<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${setting.siteName} - 新品上市</title>
    <link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/xinpin.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/type.css"/>
    <script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
    <script src="${base}/resources/shop/js/swfobject_modified.js" type="text/javascript"></script>
    <script type="text/javascript">
       $(function(){
        $("#classify_act").mouseover(function(){
            $("#classify").slideDown();//商品分类 滑动方式隐藏
         }); 
		 
		 $("#classify").mouseleave(function(){
            $(this).slideUp();//滑动方法显示	
         }); 
		 
		 $("#classify_act").mouseleave(function(){
            $("#classify").slideUp();//商品分类 滑动方式显示	
         }); 
	});
	
	$(document).ready(
            function () {
                $("p").eq(-1).css('color', 'red');
                var nowshowpic = 0; //信号量，信号量的作用指示当前显示的图片序号。
                $("#ImageList ul li").css('opacity', '0');
                $("#ImageList ul li").eq(0).css('opacity', '1');
                $("#rightan").click(
                    function () {
                        if (!$("#ImageList ul li").is(":animated")) {
                            if (nowshowpic == $("#ImageList ul li").length - 1) {
                                nowshowpic = 0;
                            } else {
                                nowshowpic = nowshowpic + 1;
                            }
                            huxixianshitupian(nowshowpic);
                            shezhixiaoyuandian(nowshowpic);
                            shezhilianjie(nowshowpic);
                        }
                    }
                );

                $("#leftan").click(
                    function () {
                        if (!$("#zhezhao").is(":animated")) {
                            if (nowshowpic == 0) {
                                nowshowpic = $("#ImageList ul li").length - 1;
                            } else {
                                nowshowpic = nowshowpic - 1;
                            }
                            huxixianshitupian2(nowshowpic);
                            shezhixiaoyuandian(nowshowpic);
                            shezhilianjie(nowshowpic);
                        }
                    }
                );
                $("#xyddul li").click(
                    function () {
                        if (!$("#zhezhao").is(":animated")) {
                            $("#ImageList ul li").eq(nowshowpic).animate({ "opacity": 0 }, 2000);
                            nowshowpic = $(this).index();
                            $("#ImageList ul li").eq(nowshowpic).animate({ "opacity": 1 }, 2000);
                            shezhixiaoyuandian(nowshowpic);
                        }
                    }
                );

                function huxixianshitupian(a) {
                    $("#ImageList ul li").eq(a - 1).animate({ "opacity": 0 }, 2000);
                    $("#ImageList ul li").eq(a).animate({ "opacity": 1 }, 2000);

                }
                function huxixianshitupian2(a) {
                    if (a == $("#ImageList ul li").length - 1) {
                        $("#ImageList ul li").eq(0).animate({ "opacity": 0 }, 2000);
                        $("#ImageList ul li").eq(a).animate({ "opacity": 1 }, 2000);
                    } else {
                        $("#ImageList ul li").eq(a + 1).animate({ "opacity": 0 }, 2000);
                        $("#ImageList ul li").eq(a).animate({ "opacity": 1 }, 2000);
                    }
                }
                function shezhixiaoyuandian(a) {
                    $("#xyddul li").eq(a).addClass("cur").siblings().removeClass("cur");
                    add_url(a);
                }

                function shezhilianjie(a) {
                	add_url(a);
                }

                var timer = window.setInterval(method, 2000);                               /*设置自动轮播*/
                var nowshowpic = 0;
 
                function method() {
                    if (!$("#ImageList ul li").is(":animated")) {
                        if (nowshowpic == $("#ImageList ul li").length - 1) {
                            nowshowpic = 0;
                        } else {
                            nowshowpic = nowshowpic + 1;
                        }
                        huxixianshitupian(nowshowpic);
                        shezhixiaoyuandian(nowshowpic);
                        shezhilianjie(nowshowpic);
                    }
                }

                $('#banner').mouseover(function () {
                    window.clearInterval(timer);
                });

                $('#banner').mouseout(function () {
                    timer = window.setInterval(method, 2000);
                });
            }

        );
        
        function add_url(a){
        	if(0 == a){
				$("#rll").val($("#banner_1").val());
			}else if(1 == a){
				$("#rll").val($("#banner_2").val());
			}else if(2 == a){
				$("#rll").val($("#banner_3").val());
			}else{
				$("#rll").val($("#banner_1").val());
			}
        }
        
function tiaozhuan(){
	if($("#rll").val().length>0){
		//window.open($("#rll").val());
	}
}

// 添加商品收藏
function addFavorite(id){
		$.ajax({
			url: "${base}/member/favorite/add.jhtml?id="+id,
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(message) {
				$.message(message);
				//if(message.content=="该商品已收藏"){
				//   window.location.href="${base}/member/favorite/list.jhtml";
				//}
			}
		});
	}
    </script>
</head>
<body>
    <!--页眉-->
    [#include "/shop/include/header.ftl" /]
    <!--新品上市开始-->

<div id="index_place">您的位置：<span><a href="${base}/">首页</a></span>><span>新品上市</span></div>

<div class="tehui-big">
  <div class="fdjh">
    <div id="banner">
      <div id="ImageList">
        <ul>
	      [@ad_position adname="新品上市 - banner图"/]
        </ul>
      </div>
    </div>
    <div id="xydd">
        <ul id="xyddul">
            <li class="cur"></li>
            <li></li>
            <li></li>
        </ul>
    </div>
  </div>
  <div class="xiobenner">
      <div class="xiobenner-1">[@ad_position adname="新品上市 - 2号广告位图1"/]</div>
      <div class="xiobenner-2">[@ad_position adname="新品上市 - 2号广告位图2(视频)"/]</div>
      <div class="xiobenner-3">[@ad_position adname="新品上市 - 2号广告位图3"/]</div>
      <div class="xiobenner-4">[@ad_position adname="新品上市 - 2号广告位图4"/]</div>
  </div>
  <script type="text/javascript">
swfobject.registerObject("FlashID");
</script>
<div class="like_lit" style="width:1148px; margin-top:20px;font-size:22px;">
  <div>热销商品</div>
  </div>
<div class="mybe_like">
    <ul>
   [@product_list tagIds =2  count = 10]
   [#list products as product]		
    <li>
		<div class="like_pic">
    		<a href="${base}${product.path}">
    			<img src="${product.image}" />
			</a>
		</div>
		<a href="${base}${product.path}">
		    <div class="like_name" title="${product.name}">${abbreviate(product.name, 58, "...")}</div>
		    <div class="like_tt">
				<div class="like_price">${currency(product.price, true)}</div>
				<div class="like_com">评论<i>[#if product.scoreCount > 0]${product.scoreCount}[#else]0[/#if]</i>条</div>
			</div>
		</a>
        [#if product.store != null]  
		    <a href="${product.store.indexUrl}" target="_blank">${product.store.name}</a>
	    [#else]
	    	 商城自营
	    [/#if]	                            
	 </li>
	[/#list]
	[/@product_list]
    </ul>
</div>
</div>

    [#include "/shop/include/footer.ftl" /]
</body>
</html>
