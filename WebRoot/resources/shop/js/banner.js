// JavaScript Document
function bannerroll(banner,imagelist,xyddul,zhezhao) {
    //$("p").eq(-1).css('color', 'red');
    var nowshowpic = 0; //信号量，信号量的作用指示当前显示的图片序号。
    $('#'+ imagelist + ' ul li').css('opacity', '0');
    $('#'+ imagelist + ' ul li').eq(0).css('opacity', '1');
//                $("#rightan").click(
//                    function () {
//                        if (!$("#ImageList ul li").is(":animated")) {
//                            if (nowshowpic == $("#ImageList ul li").length - 1) {
//                                nowshowpic = 0;
//                            } else {
//                                nowshowpic = nowshowpic + 1;
//                            }
//                            huxixianshitupian(nowshowpic);
//                            shezhixiaoyuandian(nowshowpic);
//                            shezhilianjie(nowshowpic);
//                        }
//                    }
//                );

    //$("#leftan").click(
//                    function () {
//                        if (!$("#zhezhao").is(":animated")) {
//                            if (nowshowpic == 0) {
//                                nowshowpic = $("#ImageList ul li").length - 1;
//                            } else {
//                                nowshowpic = nowshowpic - 1;
//                            }
//                            huxixianshitupian2(nowshowpic);
//                            shezhixiaoyuandian(nowshowpic);
//                            shezhilianjie(nowshowpic);
//                        }
//                    }
//                );
    $('#'+ xyddul + ' li').click(
        function () {
            if (!$('#'+ zhezhao).is(":animated")) {
                $('#'+ imagelist + ' ul li').eq(nowshowpic).animate({ "opacity": 0 }, 1000);
                nowshowpic = $(this).index();
                $('#'+ imagelist + ' ul li').eq(nowshowpic).animate({ "opacity": 1 }, 1000);
                shezhixiaoyuandian(nowshowpic);
            }
        }
    );

    function huxixianshitupian(a) {
        $('#'+ imagelist + ' ul li').eq(a - 1).animate({ "opacity": 0 }, 1000);
        $('#'+ imagelist + ' ul li').eq(a).animate({ "opacity": 1 }, 1000);

    }
    function huxixianshitupian2(a) {
        if (a == $('#'+ imagelist + ' ul li').length - 1) {
            $('#'+ imagelist + ' ul li').eq(0).animate({ "opacity": 0 }, 1000);
            $('#'+ imagelist + ' ul li').eq(a).animate({ "opacity": 1 }, 1000);
        } else {
            $('#'+ imagelist + ' ul li').eq(a + 1).animate({ "opacity": 0 }, 1000);
            $('#'+ imagelist + ' ul li').eq(a).animate({ "opacity": 1 }, 1000);
        }
    }
    function shezhixiaoyuandian(a) {
        $("#xyddul li").eq(a).addClass("cur").siblings().removeClass("cur");
//        shezhilianjie(a);
        add_url(a);
    }

//    function shezhilianjie(a) {
//        add_url(a);
//    }

    var timer = window.setInterval(method, 2000);                               /*设置自动轮播*/
    var nowshowpic = 0;

    var method = function() {
        if (!$('#'+ imagelist + ' ul li').is(":animated")) {
            if (nowshowpic == $('#'+ imagelist + ' ul li').length - 1) {
                nowshowpic = 0;
            } else {
                nowshowpic = nowshowpic + 1;
            }
            console.info(nowshowpic);
            huxixianshitupian(nowshowpic);
            shezhixiaoyuandian(nowshowpic);
//            shezhilianjie(nowshowpic);
            add_url(nowshowpic);
        }
    };

    $('#'+banner).mouseenter(function () {
        window.clearInterval(timer);
    });

    $('#'+banner).mouseleave(function () {
        timer = window.setInterval(method, 2000);
    });
    
    function add_url(a){
//    	if(0 == a){
//    		$("#rll").val($("#banner_1").val());
//    	}else if(1 == a){
//    		$("#rll").val($("#banner_2").val());
//    	}else if(2 == a){
//    		$("#rll").val($("#banner_3").val());
//    	}else{
//    		$("#rll").val($("#banner_1").val());
//    	}
    	$("#rll").val($("#banner_" + a).val());
    }

}

$(document).ready(function(){
	bannerroll('banner','ImageList','xyddul','zhezhao2');
	bannerroll('banner2','ImageList2','xyddul2','zhezhao3');
});

function tiaozhuan(){
	if ($("#rll").val().length > 0) {
		window.open($("#rll").val());
	} else {
		return false;
	}
}