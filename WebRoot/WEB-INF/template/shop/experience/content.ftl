<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${experience.name}</title>   
<link href="${base}/resources/shop/css/global.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/layer.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/style.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/css/tiyan.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/mvl.js"></script>	
<script type="text/javascript">
$().ready(function() {

	//详细页首屏轮播图
	$('#J_d_info_slide').slide();

	//详细页体验店展示
	$('#J_d_show_slide').slide({
		type         : 'slide',
		showMarkers   : false,
		showControls : true,
		hideControls : true
	});
});		
 function wqdu2(id,name,opentime,phone,busline,areaName,address){ 
	    $("#dianpuming").text("");
	    $("#dianpuming").append(name);  
	     
	    $("#dizhi1").text("");    
	    $("#dizhi1").append(areaName);
	    $("#dizhi1").append(address);
	     
	    $("#fuwushijian").text("");
	    $("#fuwushijian").append(opentime);
	    
	    $("#fuwudianhua").text("");   
	    $("#fuwudianhua").append(phone);
	    
	    $("#gongjiaoxianlu").text("");
	    $("#gongjiaoxianlu").append(busline);
	    
	    $("#dianpuming2").text("");
	    $("#dianpuming2").append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+name+'&nbsp;&nbsp;<span>&lt;详情&gt;</span>');  
	     
	    $("#dizhi2").text("");
	    $("#dizhi2").append(areaName);
	    $("#dizhi2").append(address);  
	    
	    $("#myiframe").attr('src','/experience/map/'+id+'.jhtml');

		document.getElementById("kop1").style.display="block";
		document.getElementById("kop3").style.display="block";
	}
	function no2(){
		document.getElementById("kop1").style.display="none";
		document.getElementById("kop3").style.display="none";
	}
</script>		
</head>
	[#include "/shop/include/header.ftl" /]
	<body id="_tiyan_">

	<div id="wrap">
	  <div class="w">
				<div class="crumbs">
					<ul class="clear-fix">
						<li class="home"><i class="icons"></i><a href="#">首页</a></li>
						<li><i class="icons"></i><a href="#">体验店</a></li>
						<li><i class="icons"></i><a href="#">${experience.name}</a></li>
					</ul>
				</div><!--#面包屑导航-->

				<div class="grid-330 clear-fix">
					<div class="col-wrap">
						<div class="col-main">
							<div class="slide-wrap" id="J_d_info_slide">
								<div class="slide">
								   <!--banner  -->
    <div id="banner">
        <div id="ImageList">
            <ul>
                   <li class="tp1"><a href="${experience.promotionlinkfrist}"> <img  src="${experience.promotionimagefrist}"> </a></li>
                <li class="tp2"><a href="${experience.promotionlinksecond}"><img  src="${experience.promotionimagesecond}"></a></li>
            </ul>
        </div>
        <div id="xydd">
            <ul id="xyddul">
                <li class="cur">1</li>
                <li>2</li>
            </ul>
        </div>
    </div>
    <!--banner end-->
								</div>
							
                            </div>
						</div>
					</div>
					<div class="col-330">
						<div class="info">
							<div class="info-hd">
								<h3>体验店信息</h3>
							</div>
							<div class="info-bd" id="784_nav_item">
								<h4>${experience.name}</h4>
								<p>服务热线：${experience.phone}<br>
								营业时间：${experience.opentime}<br>
								<span>本店地址：${experience.areaName}${experience.address}</span></p>
								<a href = "javascript:void(0)" onclick = "document.getElementById('light_bx').style.display='block';document.getElementById('fade').style.display='block'" class="btn">地址免费发手机<i class="icons"></i></a>
							</div>
						</div>
					</div>
				</div>
                <!--#体验店信息-->
 
 
 
            

				<div class="layout">
					<div class="layout-hd">
						<h2>体验店简介</h2>
					</div>
					<div class="layout-bd">
						<div class="intro">
							<div class="grid-295 clear-fix">
								<div class="col-wrap">
									<div class="col-main">
										<div class="state">
											<ul>
												<li>
													<h3>体验店简介</h3>
													<p>${experience.introduction}</p>
												</li>
												<li>
													<h3>交通线路</h3>
													<p>${experience.busline}</p>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<div class="col-295">
									<div class="view">
										<iframe width="293" height="233" border="0" frameborder="0" src="/experience/map/${experience.id}.jhtml">
                                        
                                        </iframe>
										<a href="#" onclick="wqdu2(${experience.id},'${experience.name}','${experience.opentime}','${experience.phone}','${experience.busline}','${experience.areaName}','${experience.address}')"  class="btn">查看大图<i class="icons"></i></a>
										
									</div>  
								</div>
							</div>
						</div>
					</div>
				</div><!--#体验店简介-->
					<!--查看地图内容-->  
					<div style="display:none;"id="kop1"></div>
					<div class="two-call" style="display:none;" id="kop3">  
					<div class="two-where-a">
						<div class="where-a-1">查看地图</div>
						<div class="where-a-2" onclick="no2()">关闭</div>
					</div>
					<div class="where-p">
						<div class="where-left">
							<div class="dfsk"><span id ="dianpuming">海尔佛山华勇专卖店</span></div>
							<div class="lkj">
								<div class="kdjp1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本馆地址：</div>
								<div class="fdjs1" id = "dizhi1">佛山市南海区狮山镇小塘三环路60号</div>
							</div>
							<div class="lkj">
								<div class="kdjp2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务时间：</div>
								<div class="fdjs2" id ="fuwushijian">8:00-18:00</div>
							</div>
							<div class="lkj">
								<div class="kdjp3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务热线：</div>   
								<div class="fdjs3" id = "fuwudianhua">400-672-266</div>
							</div>
							<div class="lkj">    
								<div class="kdjp4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公交路线：</div>
								<div class="fdjs4" id = "gongjiaoxianlu">小塘医院站：286A&nbsp;南高01路</div>
							</div>  
						</div>
						<div class="wep"><iframe id="myiframe" name="myiframe" width=510 height=500 src ="/experience/map/210.jhtml">  uioiuuo </iframe></div>
						<div class="wep-right">   
							<div class="right-0" id = "dianpuming2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;海尔佛山华勇专卖店&nbsp;&nbsp;<span>&lt;详情&gt;</span></div>
							<div class="wep-1">
								<div class="wep-2">本馆地址：</div>
								<div class="wep-3" id ="dizhi2">佛山市南海区狮山镇小塘三环路60号</div>
							</div>   
						</div>	
					</div>   
				</div>
				<div class="detail-mod" style="margin:25px 0 0;">
					<div class="detail-hd icons">
						<h2>体验店展示</h2>
					</div>
					<div class="detail-bd">
						<div class="slide-wrap" id="J_d_show_slide">
							<div class="slide">
								<ul class="slide-element" style="display: block;">
										<li style="display: list-item;">
											<ul>
												<li><a href="1.jpg"><img src="${experience.productImages[0].source}" style="width:320px;height:210px;"></a></li>
												<li style="margin:0px 25px;"><a href="2.jpg"><img src="${experience.productImages[1].source}" style="width:320px;height:210px;"></a></li>
												<li><a href="3.jpg"><img src="${experience.productImages[2].source}" style="width:320px;height:210px;"></a></li>
											</ul>
										</li>
								</ul>  
							</div>
						</div>
					</div>
				</div><!--#体验店展示-->

				<!--#体验店平面图-->

			<!--#体验店特卖-->
<div class="layout">
					<div class="layout-hd">
						<h2>本店展品推荐</h2>
						<span>到店体验再购买，放心又省心！</span>
					</div>
					<div class="layout-bd">
						<div class="show clear-fix">
						  [#list experience.products as product]
							<div class="show-mod">
								<div class="pic">
								<a href="${product.path}" target="_blank"><img src="${product.image}" width="250" height="250" /></a>
								</div>
							        <div class="txt"><a href="${product.path}" target="_blank">${product.name}</a></div>
							        <div class="price">
									<span class="now">¥<del>${product.price}</del></span>&nbsp;&nbsp
									<span class="del">¥<del>${product.marketPrice}</del></span>
								</div>
							</div>	
							[/#list]
						</div>
					</div>
		  </div><!--#本馆展品推荐--><!--#本馆特色服务-->
		</div>			
		</div><!--#wrap-->
<!--地址发送弹出框开始-->

  <div id="light_bx" class="car_white_content">
    <div class="t_biaoti">免费发送体验店信息到手机
      <div class="t_guanbi"><a href = "javascript:void(0)"  onclick =  "document.getElementById('light_bx').style.display='none';document.getElementById('fade').style.display='none'"></a></div>
    </div>
    <div class="t_neirong">
      <table width="360" border="0" cellspacing="10" cellpadding="0">
        <tr>
          <td align="right">手机号码</td>
          <td><input class="t_phone" type="text" /></td>
        </tr>
        <tr>
          <td align="right">验证码</td>
          <td><input class="t_phone" type="text" style=" width:80px;" />
            <div class="yzm_pic">
              验证码
              </div>
            </td>
        </tr>
        <tr>
          <td align="right">&nbsp;</td>
          <td><input class="t_baoxiu_button" type="button" value="提交" /></td>
        </tr>
      </table>
    </div>
  </div>
<div id="fade" class="car_black_overlay"></div>
[#include "/shop/include/footer.ftl" /]
<!--弹出框结束-->
</body></html>