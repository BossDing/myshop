<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "productSearch"]
	<title>万家乐官方网站</title>
	<META name=Keywords content=${productCategory.name}>
	<META name=Description content=${productCategory.seoDescription}>
[/@seo]    
<link href="${base}/resources/gw/css/css.css" rel="stylesheet" type="text/css">
<script src="${base}/resources/gw/js/jquery-1.4.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript">var $a =jQuery.noConflict();</script>
<script src="${base}/resources/gw/js/nav.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="${base}/resources/gw/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${base}/resources/gw/js/lanrenzhijia.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
<link href="${base}/resources/gw/css/ban2.css" rel="stylesheet" type="text/css">
<script src="${base}/resources/gw/js/share.js"></script>
<link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/slide_share.css?v=bc01b5e3.css"><link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/imgshare.css?v=754091cd.css"><link rel="stylesheet" href="http://bdimg.share.baidu.com/static/api/css/share_style0_16.css?v=f4b44e79.css"></head>
<script type="text/javascript">
$().ready(function() {
	var $messageFrom = $("#messageFrom");	
	var $userName = $("#userName");	
	var $phone = $("#phone");	
	var $address = $("#address");	
	var $qq = $("#qq");	
	var $micro = $("#micro");	
	var $remark = $("#remark");	
    var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");		

    	// 更换验证码
	$captchaImage.click(function() {
		$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
	}); 
	
	$('#saveButton').click(function(){   
				$.ajax({  
						url: "/gw/join/savemessage.jhtml",
						type: "POST",
						data: {
						 userName: $userName.val()
						,phone: $phone.val()
						,address: $address.val()
						,qq: $qq.val()
						,micro: $micro.val()
						,remark: $remark.val()
						,captchaId: "${captchaId}"
						,captcha: $captcha.val() },
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								alert("保存成功");
								window.location.href = "/gw/join/intruduction.jhtml";
							} else {	
								alert(message.content);
								$captcha.val("");
								$captchaImage.attr("src", "${base}/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());							
							}
						}
					});				
	});   
});
</script>					
</head>
<body>
 
[#include "/gw/include/header.ftl" /]

 <div class="ban4">
    <div id="bn">
      <span class="tu">
      <a href="#" style="width: 1264px; display: none;"><img alt="" width="1680" height="257" src="${base}/resources/gw/images/zcfw.jpg" data-bd-imgshare-binded="1"></a>
      <a href="#" style="width: 1264px; display: inline;"><img border="0" alt="" width="1680" height="257" src="${base}/resources/gw/images/zcfw.jpg" data-bd-imgshare-binded="1"></a><a href="##" style="width: 1264px; display: none;"><img border="0" alt="" width="1680" height="257" src="${base}/resources/gw/images/zcfw.jpg" data-bd-imgshare-binded="1"></a></span></div></div>
<div class="cpzx">
  <div class="cpzx_div zlm_bg">
	<div class="gywjl_dq">目前您在：<a href="#">首页</a> &gt; <a href="#">招商加盟</a> &gt; <h1 style="font-size:14px;font-weight:normal;display:inline"></h1></div>
  		<div class="jrwjl_z"><div class="gywjl_lmbt">招商加盟</div>
<div class="gy_zlm "><a href="/gw/join/intruduction.jhtml">加盟介绍</a></div>
<div class="gy_zlm"><a href="/gw/join/apply.jhtml">加盟申请</a></div>
</div>
  		<div class="gywjl_y">  
  		  <div class="gy_nrbt">空气能产品</div>
  		  <div class="gy_nr"><table width="720" border="0" align="center" cellpadding="0" cellspacing="0">
  <tbody>
        <tr>
            <td align="left"><table width="720" border="0" cellspacing="0" cellpadding="0">
        <tbody><tr>
            <td width="370" align="left"><img alt="" width="350" height="250" src="${base}/resources/gw/images/00020.jpg" data-bd-imgshare-binded="1"></td>
            <td width="407">
            	<p class="zt12">
            	<!--
            		二十世纪八十年代，万家乐将热水器带进千千万万的中国家庭，改变了国人传承千年的洗浴生活。<br/>
            		一直以来，万家乐专注于改善中国家庭的热水和厨房生活。通过对清洁能源的高效利用，以领先科技和卓越服务，提供包括家庭和商业。热水、家庭厨房的专业解决方案。<br/>
            		万家乐将持续帮助每个家庭降低能源消耗，减少碳排放，助力建设环保、智慧型社会，创造高品质的热水和厨房生活。<br/>
            		万家乐，中国热水与中国菜厨电专家。<br/>
            		万家乐，乐万家。<br/>
        		-->
            		二十世纪八十年代，万家乐将热水器带进千千万万的中国家庭，改变了国人传承千年的洗浴生活。<br/>
					一直以来，万家乐专注于改善中国家庭的热水和厨房生活。通过对清洁能源的高效利用，以领先科技和卓越服务，提供包括家庭和商业热水、家庭厨房的专业解决方案。<br/>
					万家乐将持续帮助每个家庭降低能源消耗，减少碳排放，助力建设环保、智慧型社会，创造更加舒适、健康的高品质热水和厨房生活，为每个家庭带来快乐。<br/>
					万家乐，中国热水与中国菜厨电专家。<br/>
					万家乐，乐万家。<br/>
					万家乐股票代码：000533  深圳证券交易所主板  1994年
            	</p>
            </td>
        </tr>
            </tbody></table></td>
            </tr>
        <tr>
            <td height="100">
            <div class="faxq_nav">
            <div class="fa_lj2"><a href="/gw/join/intruduction.jhtml">空气能产品</a></div>
            <div class="fa_lj"><a href="/gw/join/fireplace.jhtml">壁挂炉产品</a></div>
            <div class="fa_lj"><a href="/gw/join/hec.jhtml">生活电器</a></div>
            <div class="fa_lj"><a href="/gw/join/kitchen.jhtml">橱柜产品</a></div>
            </div>
            </td>
        </tr>
        <tr>
            <td height="50" align="left" valign="middle" class="xb2"><strong>关于万家乐空气能科技有限公司</strong></td>
    </tr>
        <tr>
          <td align="left" valign="middle" class="zt13 xb2"><br>            &nbsp;&nbsp;&nbsp;&nbsp;广东万家乐空气能科技有限公司是广东万家乐股份有限公司旗下子公司，是万家乐在原有燃气热水器生产能力基础上，在新能源热水器产业上的布局。<br>
&nbsp;&nbsp;&nbsp;&nbsp;广东万家乐空气能科技有限公司占地30000多平方米，华南最大生产空气能、生产太阳能的技术型企业之一，拥有国际太阳能行业的专家和国内中高端技术的人才，拥有国内先进的"多孔位数控冲床"、"直缝自动焊机"等设备。<br>
&nbsp;&nbsp;&nbsp;&nbsp;空气能公司拥有完善的组织架构、精湛的服务、专业的国际空气能行业顾问和国内高端的技术人才，集研究、开发、生产制造和销售服务为一体，秉承"专业制造、贴心服务"的企业精神，坚持以科技创新、尊重人才的经营理念，致力于为消费者提供安全节能、品质上乘的空气能、太阳能系列产品。<br>
&nbsp;&nbsp;&nbsp;&nbsp;我们秉承“创造高品质生活”的企业使命，让安全、节能、舒适的空气能产品走进千家万户，为广大消费者创造更加舒适的生活！<br><br></td>
    </tr>
        <tr>
          <td align="left" valign="middle" class="zt13 xb2"><table width="720" border="0" cellspacing="0" cellpadding="0">
            <tbody><tr>
              <td> <br>
                · 空气能、太阳能华南地区配套最完善的生产基地之一；<br>
· 26年的热水器生产、制造、研发经验；<br>
· 热水器行业首个博士后工作站落户万家乐；<br>
· 国家高薪技术企业；<br>
· 国家认定企业技术中心；<br><br></td>
              <td>· 拥有国家级生产许可证；<br>
· 全方位的终端建设、市场推广支持；<br>
· 丰富的空气能、太阳能商用、家用产品线；<br>
· 24x7小时的服务电话 4007003888（转3）； <br>
· 合作共赢，全方位的技术、资金支持；</td>
            </tr>
          </tbody></table></td>
    </tr>
        <tr>
          <td height="50" align="left" valign="middle" class="xb2"><strong>用户留言</strong> <span class="zt12">我们工作人员将会尽快与您联系。</span></td>
    </tr>
        <tr>
          <td align="left" valign="middle" class="xb2"><table width="720" border="0" cellspacing="0" cellpadding="0">
            <tbody><tr>
              <td width="360" class="zt12"><div class="box">
     <div class="box_1">
      <div class="boxCenterList">
          <form id="messageFrom" method="post">
            <table width="360" border="0" cellpadding="3">
              <tbody><tr style="display:none;">
                <td align="right">留言类型</td>
                <td colspan="3"><input name="msg_type" type="radio" value="0">
                  留言                  <input type="radio" name="msg_type" value="1">
                  应聘                  <input type="radio" name="msg_type" value="2">
                                    <input type="radio" name="msg_type" value="3">
                  维修                  <input type="radio" name="msg_type" value="4">
                  安装                  <input type="radio" name="msg_type" value="5" checked="checked">
                  加盟</td>
              </tr>
              <tr>
                <td align="right">姓名：</td>
                <td><input id="userName" type="text" class="inputBg" size="30"></td>
              </tr>
              <tr>
                <td align="right">手机号：</td>
                <td><input id="phone" type="text" class="inputBg" size="30"></td>
              </tr>
              <tr>
                <td align="right">地址：</td>
                <td><input id="address" type="text" class="inputBg" size="30"></td>
              </tr>
              <tr>
                <td align="right">QQ：</td>
                <td><input id="qq" type="text" class="inputBg" size="30"></td>
              </tr>
              <tr>
                <td align="right">微信号：</td>
                <td><input id="micro" type="text" class="inputBg" size="30"></td>
              </tr>
              <tr>
                <td align="right" valign="top">备注：</td>
                <td><textarea id="remark" cols="30" rows="4" wrap="virtual" style="border:1px solid #ccc;"></textarea></td>
              </tr>
                          <tr>
                <td align="right">验证码：</td>
                <td><input type="text" size="8" id="captcha"  name="captcha" class="inputBg">
                <img id="captchaImage" class="captchaImage" src="${base}/common/captcha.jhtml?captchaId=${captchaId}" title="${message("shop.captcha.imageTitle")}" />		
                </td>
              </tr>
                          <tr>
                <td>&nbsp;</td>
                <td height="50" valign="middle"><input type="hidden" name="act" value="act_add_message">
                <input name="" type="button" id="saveButton" value="提交留言"/>
                </td>
              </tr>
            </tbody></table>
          </form>         
      </div>
     </div>
    </div></td>
              <td><table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
                <tbody><tr>
                  <td height="50">万家乐客服微信：wjlqkn</td>
                </tr>
                <tr>
                  <td><img src="${base}/resources/gw/images/kqn711_r1_c6.jpg" width="198" height="198" data-bd-imgshare-binded="1"></td>
                </tr>
                <tr>
                  <td height="50">关注我们，获取更多信息！</td>
                </tr>
              </tbody></table></td>
            </tr>
          </tbody></table></td>
    </tr>
        <tr>
          <td height="60" align="left" valign="middle" class="xb2"><table width="200" border="0" cellspacing="0" cellpadding="0">
            <tbody><tr>
              <td width="60" align="center"><img src="${base}/resources/gw/images/kqn711_r5_c2.jpg" width="34" height="34" data-bd-imgshare-binded="1"></td>
              <td><strong>联系人</strong></td>
            </tr>
          </tbody></table></td>
    </tr>
        <tr>
          <td align="left" valign="middle" class="xb2"><p><br>
            财富热线： <strong>叶先生  ( 手机：18126632088   QQ：249011453 ）</strong><br>
            财富热线： <strong>王先生  ( 手机：18126632097   QQ：86008711 ）</strong><br>
            <br>
          </p>
          <p> </p></td>
    </tr>
        <tr>
          <td height="50" align="left" valign="middle"><strong>终端效果图</strong></td>
    </tr>
        <tr>
          <td align="left" valign="middle" class="xb2"><img src="${base}/resources/gw/images/kqn711_r7_c1.jpg" width="720" height="314" data-bd-imgshare-binded="1"></td>
    </tr>
  </tbody>
</table></div>
  		</div>
  </div>
</div> 

[#include "/gw/include/footer.ftl" /]
</body>
</html>