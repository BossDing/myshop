// JavaScript Document
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
                            $("#ImageList ul li").eq(nowshowpic).animate({ "opacity": 0 }, 1000);
                            nowshowpic = $(this).index();
                            $("#ImageList ul li").eq(nowshowpic).animate({ "opacity": 1 }, 1000);
                            shezhixiaoyuandian(nowshowpic);
                        }
                    }
                );

                function huxixianshitupian(a) {
                    $("#ImageList ul li").eq(a - 1).animate({ "opacity": 0 }, 1000);
                    $("#ImageList ul li").eq(a).animate({ "opacity": 1 }, 1000);

                }
                function huxixianshitupian2(a) {
                    if (a == $("#ImageList ul li").length - 1) {
                        $("#ImageList ul li").eq(0).animate({ "opacity": 0 }, 1000);
                        $("#ImageList ul li").eq(a).animate({ "opacity": 1 }, 1000);
                    } else {
                        $("#ImageList ul li").eq(a + 1).animate({ "opacity": 0 }, 1000);
                        $("#ImageList ul li").eq(a).animate({ "opacity": 1 }, 1000);
                    }
                }
                function shezhixiaoyuandian(a) {
                    $("#xyddul li").eq(a).addClass("cur").siblings().removeClass("cur");
                }

                function shezhilianjie(a) {
                    $("#zhezhao a").attr("href", $("#ImageList ul li a").eq(a).attr("href"));
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

                $('#banner').mouseenter(function () {
                    window.clearInterval(timer);
                });

                $('#banner').mouseleave(function () {
                    timer = window.setInterval(method, 2000);
                });

            }

        );