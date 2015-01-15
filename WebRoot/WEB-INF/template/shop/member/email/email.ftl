<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>个人中心-我的资料</title>
    <link href="${base}/resources/shop/css/sns.css" rel="stylesheet" type="text/css" />
    <link href="${base}/resources/shop/css/email.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/pic_switching.js"></script>
</head>
<script type="text/javascript">

		function Year_Month() {
		    var now = new Date();
		    var yy = now.getYear();
		    var mm = now.getMonth();
		    var mmm = new Array();
		    mmm[0] = "1";
		    mmm[1] = "2";
		    mmm[2] = "3";
		    mmm[3] = "4";
		    mmm[4] = "5";
		    mmm[5] = "6";
		    mmm[6] = "7";
		    mmm[7] = "8";
		    mmm[8] = "9";
		    mmm[9] = "10";
		    mmm[10] = "11";
		    mmm[11] = "12";
		    mm = mmm[mm];
		    return (mm);
		}
		
		function Date_of_Today() {
		    var now = new Date();
		    return (now.getDate());
		}
		 
		function Date_of_Week() {
		    var now = new Date();
		    var week = now.getDay();
		    var week_day
		    if (week == 1) {
		        week_day = "星期一"
		    };
		    if (week == 2) {
		        week_day = "星期二"
		    };
		    if (week == 3) {
		        week_day = "星期三"
		    };
		    if (week == 4) {
		        week_day = "星期四"
		    };
		    if (week == 5) {
		        week_day = "星期五"
		    };
		    if (week == 6) {
		        week_day = "星期六"
		    };
		    if (week == 0) {
		        week_day = "星期日"
		    };
		    return (week_day);
		}
		
       $(document).ready(function(){
       
       		$("#shop-info-day")[0].innerHTML= Date_of_Today();
       		$("#shop-info-mouth")[0].innerHTML= Year_Month() + "月";
       		$("#shop-info-week")[0].innerHTML= Date_of_Week();
       		
       		
    var historyProduct = getCookie("historyProduct");
	var historyProductIds = historyProduct != null ? historyProduct.split(",") : new Array();
	for (var i = 0; i < historyProductIds.length; i ++) {
		if (historyProductIds[i] == "${product.id}") {
			historyProductIds.splice(i, 1);
			break;
		}
	}
	historyProductIds.unshift("${product.id}");
	if (historyProductIds.length > 4) {
		historyProductIds.pop();
	}
	addCookie("historyProduct", historyProductIds.join(","), {path: "${base}/"});
	$.ajax({
		url: "${base}/product/history.jhtml",
		type: "GET",
		data: {ids: historyProductIds},
		dataType: "json",
		traditional: true,
		cache: false,
		success: function(data) {
			$.each(data, function (index, product) {
				var thumbnail = product.thumbnail != null ? product.thumbnail : "${setting.defaultThumbnailProductImage}";
				$("#historyProduct").append('<li><a href="${base}' + product.path + '" title="' + product.name + '"><img src="' + thumbnail + '" \/></a><p class="link-evaluate"><span>' + currency(product.price, true) + '</span></p></li>');
			});
		}
	});
		});
    </script>
<body>
   [#include "/shop/member/include/header.ftl" /]
    <!--我的沁园头部 end-->
    <!--我的沁园 内容区-->
    <div class="snsnrq">
        <div class="snsnr">
            <!--左 导航区-->
            <div class="hong"></div>
            <div class="zcdhq">
                <div class="side-neck">
                    <i></i>
                </div>
    [#include "/shop/member/include/left.ftl" /]       
            
            </div>
            <!--左 导航区 end--><!--左 导航区 end-->
                        <!--右 内容区-相关资料及修改-->
            <div class="wdzlq">
                <!--我的资料--><!--我的资料-->
                <div class="wdzl">
                    <div class="zlbt">我的资料</div>
                    <div class="wdzllb">
                        <ul>
                            <li>
                                <a href="#" class="xuanzhong">基本资料</a>
                            </li>
                            <li>
                                <a href="#">修改头像</a>
                            </li>
                        </ul>
                    </div>
                    <!--资料明细-->
                    <div class="zlmxq">
                        <ul>
                            <li>
                                <div class="fa xmnr">头像：</div>
                                <div class="head-pic">
                                    <a href="#">
                                        <img src="../images-sns/touxiang.jpg" alt="Alternate Text" />
                                        <p>
                                            <span></span>
                                            <em>修改头像</em>
                                        </p>
                                    </a>
                                </div>
                            </li>
                            <li class="hydj">
                                <div class="fa xmnr">会员等级：</div>
                                <i></i>
                                <b>普通会员</b>
                            </li>
                            <li class="shuru">
                                <div class="fa xmnr">用户名：</div>
                                <input type="text" name="name" value="Ironmen " />
                            </li>
                            <li class="shuru">
                                <div class="fa xmnr">姓名：</div>
                                <input type="text" name="name" value="李某某 " />
                            </li>
                            <li>
                                <div class="fa xmnr">性别：</div>
                                <input type="radio" name="name" value=" " />
                                男
                                <input type="radio" name="name" value=" " />
                                女
                                <input type="radio" name="name" value=" " />
                                保密
                            </li>
                            <li>
                                <div class="fa xmnr">邮箱：</div>
                                <b>Iron******men@126.com</b>
                                <a href="#">修改</a>
                            </li>
                            <li>
                                <div class="fa xmnr">手机号：</div>
                                <b>196******55</b>
                                <a href="#">修改</a>
                            </li>
                            <li>
                                <div class="fa xmnr">密码：</div>
                                <b>*******</b>
                                <a href="#">修改</a>
                            </li>
                            <li>
                                <div class="fa xmnr">居住地址：</div>
                                <span class="jzdz">
                                    <select>
                                        <option value="default">--请选择--</option>
                                        <option value="北京">北京</option>
                                        <option value="上海">上海</option>
                                        <option value="天津">天津</option>
                                        <option value="重庆">重庆</option>
                                        <option value="安徽">安徽</option>
                                        <option value="福建">福建</option>
                                        <option value="甘肃">甘肃</option>
                                        <option value="广东">广东</option>
                                        <option value="广西">广西</option>
                                        <option value="贵州">贵州</option>
                                        <option value="海南">海南</option>
                                        <option value="河北">河北</option>
                                        <option value="河南">河南</option>
                                        <option value="黑龙江">黑龙江</option>
                                        <option value="湖北">湖北</option>
                                        <option value="湖南">湖南</option>
                                        <option value="吉林">吉林</option>
                                    </select>
                                    <select>
                                        <option value="default">--请选择--</option>
                                    </select>
                                    <select class="last">
                                        <option value="default">--请选择--</option>
                                    </select>
                                    <select class="last">
                                        <option value="default">--请选择--</option>
                                    </select>
                                </span>
                            </li>
                            <li class="xqzdz">
                                <input type="text" name="name" value=" " />
                                <span>请填写省市区镇及详细地址！</span>
                            </li>
                            <li class="baocun">
                                <a href="#">保存</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <!--我的资料 end--><!--我的资料 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--修改邮箱 --><!--修改邮箱 -->
                <div class="mailbox">
                    <div class="zlbt">修改绑定邮箱</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定邮箱</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--绑定选项 -->
                    <div class="bdxx">
                        <div class="bdlb">
                            <div class="zlfbt">验证方式:</div>
                            <div>
                                <select>
                                    <option value="验证邮箱">验证邮箱</option>
                                    <option value="验证手机">验证手机</option>
                                </select>
                            </div>
                            <div>下拉菜单选择其他验证方式</div>
                        </div>
                        <div class="bdlb">
                            <div class="zlfbt">邮箱地址:</div>
                            <div>wih*********ht@126.com</div>
                            <div>
                                <a href="#" class="hqyzm">获取邮箱验证码</a>
                            </div>
                            <div class="yzmcw">请输入您的新邮箱</div>
                            <div class="wmgr">已发送验证码到邮箱，请<a href="#">登录邮箱</a>查收!您还有<b>1</b>次获取机会邮箱验证码</div>
                        </div>
                        <div class="bdlb">
                            <div class="zlfbt">邮箱验证码:</div>
                            <div class="shur">
                                <input type="text" name="name" value=" " />
                            </div>
                            <div class="yzmcw">请输入六位数字验证码</div>
                        </div>
                        <div class="xiayibu">
                            <a href="#" class="xybu">下一步</a>
                        </div>
                    </div>
                    <!--绑定选项 -->
                </div>
                <!--修改邮箱 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--绑定新邮箱 -->
                <!--绑定新邮箱 -->
                <div class="mailbox mailbox-x">
                    <div class="zlbt">绑定新邮箱</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定邮箱</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--绑定选项 -->
                    <div class="bdxx">
                        <div class="bdlb gyyx">
                            <div class="zlfbt">新邮箱地址:</div>
                            <div class="yxshur">
                                <input type="text" name="name" value=" " />
                            </div>
                            <div>
                                <a href="#" class="hqyzm">获取邮箱验证码</a>
                            </div>
                            <div class="yzmcw">请输入您的新邮箱</div>
                            <div class="wmgr">已发送验证码到邮箱，请<a href="#">登录邮箱</a>查收!您还有<b>1</b>次获取机会邮箱验证码</div>
                        </div>
                        <div class="bdlb">
                            <div class="zlfbt">邮箱验证码:</div>
                            <div class="shur">
                                <input type="text" name="name" value=" " />
                            </div>
                            <div class="yzmcw">请输入六位数字验证码</div>
                        </div>
                        <div class="xiayibu">
                            <a href="#" class="xybu">提交</a>
                        </div>
                    </div>
                    <!--绑定选项 -->
                </div>
                <!--绑定新邮箱 end-->
                <!--绑定新邮箱 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--绑定新邮箱成功 -->
                <!--绑定新邮箱成功 -->
                <div class="mailbox mailbox-cg">
                    <div class="zlbt">绑定成功</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定邮箱</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--成功提示 -->
                    <div class="bdxx">恭喜你,修改绑定邮箱成功！</div>
                    <!--成功提示 end-->
                </div>
                <!--绑定新邮箱成功 end-->
                <!--绑定新邮箱成功 end-->

                <!--修改手机号码 -->
                <!--修改手机号码 -->
                <div class="phone">
                    <div class="zlbt">修改绑定手机</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定手机</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--绑定选项 -->
                    <div class="bdxx">
                        <div class="bdlb">
                            <div class="zlfbt">验证方式:</div>
                            <div>
                                <select>
                                    <option value="验证邮箱">验证手机</option>
                                    <option value="验证手机">验证邮箱</option>
                                </select>
                            </div>
                            <div>下拉菜单选择其他验证方式</div>
                        </div>
                        <div class="bdlb">
                            <div class="zlfbt">邮箱地址:</div>
                            <div>wih*********ht@126.com</div>
                            <div>
                                <a href="#" class="hqyzm">获取短信验证码</a>
                            </div>
                            <div class="yzmcw">请获取验证码</div>
                            <div class="wmgr">短信验证码已发送成功，请注意查收!您还有<b>2</b>次发送机会</div>
                        </div>
                        <div class="bdlb">
                            <div class="zlfbt">短信验证码:</div>
                            <div class="shur">
                                <input type="text" name="name" value=" " />
                            </div>
                            <div class="yzmcw">请输入六位数字验证码</div>
                        </div>
                        <div class="xiayibu">
                            <a href="#" class="xybu">下一步</a>
                        </div>
                    </div>
                    <!--绑定选项 -->
                </div>
                <!--修改手机号码 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--绑定新手机号码 -->
                <!--绑定新手机号码 -->
                <div class="phone-x">
                    <div class="zlbt">绑定新手机</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定手机</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--绑定选项 -->
                    <div class="bdxx">
                        <div class="bdlb gyyx">
                            <div class="zlfbt">新手机:</div>
                            <div class="yxshur">
                                <input type="text" name="name" value=" " />
                            </div>
                            <div>
                                <a href="#" class="hqyzm">获取短信验证码</a>
                            </div>
                            <div class="yzmcw">请获取验证码</div>
                            <div class="wmgr">短信验证码已发送成功，请注意查收!您还有<b>2</b>次发送机会</div>
                        </div>
                        <div class="bdlb">
                            <div class="zlfbt">短信验证码:</div>
                            <div class="shur">
                                <input type="text" name="name" value=" " />
                            </div>
                            <div class="yzmcw lwyzm">请输入六位数字验证码</div>
                        </div>
                        <div class="xiayibu">
                            <a href="#" class="xybu">提交</a>
                        </div>
                    </div>
                    <!--绑定选项 -->
                </div>
                <!--绑定新手机号码 end--><!--绑定新手机号码 end-->
                <!--------------------------------------------------------------------------------------------------------------------------------------->
                <!--绑定新手机号成功 --><!--绑定新手机号成功 -->
                <div class="phone-cg">
                    <div class="zlbt">绑定成功</div>
                    <div class="wzdwz">
                        <ul>
                            <li class="w1">验证身份</li>
                            <li class="w2">修改绑定邮箱</li>
                            <li class="w3">修改成功</li>
                        </ul>
                    </div>
                    <!--成功提示 -->
                    <div class="bdxx">恭喜你,修改绑定手机成功！</div>
                    <!--成功提示 end-->
                </div>
                <!--绑定新手机号成功 end-->
                <!--绑定新手机号成功 end-->

                <!--温馨提示 -->            <!--温馨提示---------------------- -->
                <div class="tips">
                    <p>
                        <strong>邮箱可正常使用收不到验证码怎么办？</strong>
                    </p>
                    <p>
                        若您的邮箱能正常使用但无法接收到验证码邮件，请仔细查找垃圾箱邮件。同时由于网络原因，可能会有延迟。如果您10分钟还没有收到邮件，请重新点击发送。
                    </p>
                    <p>
                        <strong>无法通过验证？</strong>
                    </p>
                    <p>
                        如果您的手机和邮箱已经无法正常使用，请发送账号名（手机/邮箱/用户名）、联系手机、身份证正反面照片注明原因发送至<b> 4008007867@qinyuan.com</b>，客服将在收
                        到邮件后第一时间联系您。（客服工作时间为09:00-18:00）
                    </p>
                </div>
                <!--温馨提示 end-->            <!--温馨提示 end-->
            </div>
            <!--右 内容区-相关资料及修改 end-->
            
        </div>
        <div class="cenmax"></div>
    </div>
    <!--我的沁园内容区 end-->
    <div class="cenmaxbs"></div>
    [#include "/shop/include/footer.ftl" /]
</body>
</html>
