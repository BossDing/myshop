<html>
<head>
<meta http-equiv="content-type" content="charset=utf-8" />
<link href="${base}/resources/shop/css/sns.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/member_top.css" rel="stylesheet" type="text/css" />
<title>UP</title>
</head>

<script type="text/javascript">
$().ready(function() {

    var $productSearchForm= $("#productSearchForm");
    var $keyword = $("#keyword");
    var username = getCookie("username");
    
	if (username != null) {
		$("#header_login").hide();
		$("#header_register").hide();
		$("#fenge").hide();
		$("#header_exit").css('display','block');
		$("#header_login").text(username);
		$("#header_login").show();
		$("#member_center").show();
		$("#username").html(username);
	}

	$productSearchForm.submit(function() {
	    if ($.trim($keyword.val()) == "") {
			return false;
		}
	});
       
});
</script>
<body>
 <!--页眉-->
    <div class="header">
        <div class="cttq">
            <!--欢迎词及温馨提示-->
            <div class="hyjwx fa">Hi！欢迎来到沁园官方商城</div>
            <!--配送地址-->
         <!--   <div class="psdz fa">
                <span商品配送至：</span>
                <span class="tub"></span>
                <span>浙江</span>
                <span class="genghuan">[更换]</span>
            </div>  -->
            <!--部分功能快速通道-->
            <div class="kstd fb">
                <ul>
                    <li>
                        <span id="header_login"><a href="${base}/login.jhtml">登录</a></span>
                        <b id="fenge">/</b>
                        <span id ="header_register"><a href="${base}/register.jhtml">注册</a></span>
                    </li>
                    <li class="fgx"></li>
                    <li><span style="display:none;" id ="member_center"><a href="${base}/member/index.jhtml">个人中心</a></span></li>
                    <li class="fgx"></li>
                    <li class="sjqy"><a href="#">手机商城</a></li>
                    <li class="fgx"></li>
                    <li><a href="#">服务中心</a></li>
                    <li class="fgx"></li>
                    <li class="zhyxl"><a href="http://www.qinyuanwater.com/" target="_black">沁园官网</a></li>
                    <li class="fgx"></li>
                    <li><span style="display:none;" id ="header_exit"><a href="${base}/logout.jhtml">退出</a></span></li>
                </ul>
            </div>
        </div>
    </div>
    <!--页眉 end-->
    <!--我的沁园头部-->
    <div class="ms-header">
        <!--导航与搜索-->
        <div class="headers">
            <div class="fqnav">
                <div class="header-menu">
                    <div class="ms-logo">
                        <a class="ms-head-logo" href="${base}/member/index.jhtml"></a>
                    </div>
                    <div class="ms-nav">
                        <ul>
                            <li ><a href="/">商城首页</a></li>
                            <li class="selected"><a href="${base}/member/index.jhtml">我的沁园</a><i class="nav-arrow"></i></li>
                            <li class="nav-manage">
                                <a href="#">账户管理<em></em></a><i class="nav-arrow"></i>
                                <div class="list-nav-manage hide">
                                    <p class="nav-mge-hover">账户管理<em></em></p>
                                    <p><a href="#">个人信息</a></p>
                                    <p><a href="#">安全设置</a></p>
                                    <p><a href="#">门店会员卡</a></p>
                                    <p><a href="#">关联互联号</a></p>
                                    <p><a href="#">我的易付宝</a></p>
                                    <p><a href="#">地址管理</a></p>
                                    <p><a href="#">校园代理</a></p>
                                </div>
                            </li>
                            <li class="ms-nav-msg"><a href="#">消息<span>0</span></a><i class="nav-arrow"></i></li>
                        </ul>
                     <!--   <div class="ms-search">
                            <form id="msiSearchForm" onsubmit="return SFE.base.onSubmitSearch(this)" method="get">
                                <span>搜索</span>
                                <input id="searchKeywords" type="hidden" value="">
                                <input id="searchKeywordFixed" type="text" value="">
                                <a href="#"></a>
                            </form>
                        </div>  -->
                    </div>
                </div>
            </div>
        </div>
        <!--导航与搜索 end-->
        <!--个人资料-->
        <div class="headerx">
            <div class="grzl">
                <!--头像-->
                <div class="touxiangq fa">
                    <div class="tuq">
                        <img src="../images-sns/touxiang.jpg" alt="Alternate Text" />
                        <a href="${base}/member/profile/edit.jhtml">修改</a>
                    </div>
                    <a href="${base}/member/profile/edit.jhtml" class="nicheng" id="username"></a>
                </div>
                <!--头像 end-->
                <!--用户名称及其他-->
                <div class="ms-name-info">
                    <div class="link-myinfo fa">
                        <a href="#">我的资料</a>
                    </div>
                    <div class="info-member">
                        <!--会员等级对应类名：普通-铂金（member-1/4）-->
                        <span class="name-member member-1 fa">
                            <i></i><a href="#">${member.memberRank.name}</a></span>
                        <div class="rate-member">
                            <div class="text-rate"><span>244</span>/<span>8000</span></div>
                            <div class="bg-rate" style="width: 3.05%"></div>
                        </div>
                    </div>
                    <div class="info-safety fa">
                        <!--安全等级文字颜色状态对应类名：低(lv-1)/中(lv-2)/高(lv-3)-->
                        <span class="safety-lv lv-3">安全等级：<span>中</span></span>
                        <!--未/已绑定状态：(unbind)bind-phone/email-->
                        <a class="bind-phone" name="Myyigou_index_none_daohang036" href="${base}/member/mobile/edit.jhtml">
                            <i></i>修改手机</a>
                        <a class="bind-email" name="Myyigou_index_none_daohang037"  href="${base}/member/email/edit.jhtml">
                            <i></i>修改邮箱</a>
                    </div>
                </div>
            </div>
        </div>
        <!--个人资料 end-->
    </div>
</body>
</html>