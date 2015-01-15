<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>我的预约</title>
    <link href="${base}/resources/shop/css/myPreSellApply.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
</head>

<script type="text/javascript">
	var arrays= new Array();
			
		var index = 0;
	$().ready(function(){
		if(arrays != null && arrays.length >0){
			window.setInterval(function(){
				for(var i=0;i<arrays.length;i++){
						var ID = $("#getId").val();
						alert(ID);
						var id = "#"+String(ID);
						alert("id="+id);
						var strTime = $(id).val();
						alert(strTime);
						
						var NO = $("#getNo").val();
						alert(NO);
						var no = "#"+String(NO);
						alert("no="+no);
						var strTime1 = $(no).val();
						alert(strTime1);
						
						//var strTime = $("#"+String(${apply.id})).val();
						//var strTime1 = $("#"+String(${apply.preSellApplyNo})).val();
						//alert(strTime);
						//alert(strTime1);
						
						var TIME = $("#getTime").val();
						alert("TIME="+TIME);
						var time = "#"+String(TIME);
						alert("time="+time);
						
						if(strTime!=null&&strTime1!=null){
									var date = new Date(Date.parse(strTime.replace(/-/g,"/")));
									var date1 = new Date(Date.parse(strTime1.replace(/-/g,"/")));
									//计算剩余的毫秒数
									var ts = date - new Date();
									var ts1 = date1 - new Date();
									if(ts1<=0){
										$("#time").text("").append("<span>活动已结束！</span>");
										$("#wait_buy").val("已结束抢购");
										$("#wait_buy_link").attr("href","#");
										return false;
									}else if(ts<=0&&ts1>0){
										$("#time").text("").append("<span>活动已开始！</span>");
										$("#wait_buy").val("马上去抢购");
										var id= $('#preSellRoleId').val();
										$("#wait_buy_link").attr("href","${base}/preSellRole/presellqg/"+id+".jhtml");
										return false;
									}else if(ts>0){
										$("#wait_buy").val("等待抢购");
										var dd = parseInt(ts/1000/60/60/24,10);
										var hh = parseInt(ts/1000/60/60%24,10);
										var mm = parseInt(ts/1000/60%60,10);
										var ss = parseInt(ts/1000%60,10);
										dd = checkTime(dd);  
								        hh = checkTime(hh);  
								        mm = checkTime(mm);
										$(time).html('<i class="icon-orderTime toBegin"></i>距离开始：<br/><em>'+dd+'</em>天<em>'+hh+'</em>时<em>'+mm+'</em>分<em>'+ss+'</em>秒');
									}
								}
				}	
			}, 1000);
		}
		
			//setInterval("timer()",1000);
	});
	
	function timer(){
		var strTime = $("#starttime").val();
		var strTime1 = $("#endtime").val();
		alert(strTime);
		if(strTime!=null&&strTime1!=null){
			var date = new Date(Date.parse(strTime.replace(/-/g,"/")));
			var date1 = new Date(Date.parse(strTime1.replace(/-/g,"/")));
			//计算剩余的毫秒数
			var ts = date - new Date();
			var ts1 = date1 - new Date();
			if(ts1<=0){
				$("#time").text("").append("<span>活动已结束！</span>");
				$("#wait_buy").val("已结束抢购");
				$("#wait_buy_link").attr("href","#");
				return false;
			}else if(ts<=0&&ts1>0){
				$("#time").text("").append("<span>活动已开始！</span>");
				$("#wait_buy").val("马上去抢购");
				var id= $('#preSellRoleId').val();
				$("#wait_buy_link").attr("href","${base}/preSellRole/presellqg/"+id+".jhtml");
				return false;
			}else if(ts>0){
				$("#wait_buy").val("等待抢购");
			}
			var dd = parseInt(ts/1000/60/60/24,10);
			var hh = parseInt(ts/1000/60/60%24,10);
			var mm = parseInt(ts/1000/60%60,10);
			var ss = parseInt(ts/1000%60,10);
			dd = checkTime(dd);  
	        hh = checkTime(hh);  
	        mm = checkTime(mm);
			$('#dd').text(dd);
			$('#hh').text(hh);
			$('#mm').text(mm);
			$('#ss').text(ss);
			setInterval("timer()",1000);
		}
	}
	
	function checkTime(i){
		if(i<10){
			i = "0"+i;
		}
		return i;
	}
	
</script>

<body>
	[#include "/shop/member/include/header.ftl" /]
    <!--我的沁园 内容区-->
    <div class="snsnrq">
        <div class="snsnr">
            <!--左 导航区-->
            <div class="hong"></div>
            <div class="zcdhq">
                <div class="side-neck">
                    <i></i>
                </div>
           		[#include "/shop/member/include/navigation.ftl" /]
            </div>
            <!--左 导航区 end-->
            <!--右 内容区-->
            <div class="ynrq" style="display:none;">
                <!--签到和快捷通道区域 [[-->
                <div class="ms-app ms-shopping-info">
                    <div class="fa shop-info-left">
                        <table border="0">
                            <tbody>
                                <tr>
                                    <td rowspan="2" align="right" class="shop-info-day">23</td>
                                    <td align="right" valign="bottom" class="shop-info-mouth">7月</td>
                                </tr>
                                <tr>
                                    <td align="right" valign="top" class="shop-info-week">星期三</td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="shop-info-sign">
                            <p><em>已领取</em><span>4</span>积分</p>
                            <a href="#">签到领取</a>
                        </div>
                        <div class="shop-info-sign sign-in" style="display: none">
                            <p><i class="bg-sign-left"></i><i class="bg-sign-right"></i><em>已领取</em><span>4</span>积分</p>
                            <a>明日可领8积分</a>
                        </div>
                    </div>
                    <div class="l shop-info-right">
                        <div class="shop-info-option potion-pay">
                            <a href="#">
                                <i></i>
                                <p>待付款<span>(0)</span></p>
                            </a>
                        </div>
                        <div class="shop-info-option potion-take">
                            <a href="#">
                                <i></i>
                                <p>待收货<span>(0)</span></p>
                            </a>
                        </div>
                        <div class="shop-info-option potion-evaluate">
                            <a href="#">
                                <i></i>
                                <p>待评价<span>(1)</span></p>
                            </a>
                        </div>
                        <div class="shop-info-option potion-userworld">
                            <a target="_blank" name="Myyigou_index_none_qiandao005" href="#">
                                <i></i>
                                <p>邀请有奖</p>
                            </a>
                        </div>
                        <div class="shop-info-option potion-manage">
                            <a name="Myyigou_index_none_qiandao006" href="javascript:void(0);">
                                <i></i>
                                <p>水质查询</p>
                            </a>
                        </div>
                        <div class="shop-info-option potion-wdsc">
                            <a name="Myyigou_index_none_qiandao006" href="javascript:void(0);">
                                <i></i>
                                <p>我的收藏</p>
                            </a>
                        </div>
                    </div>
                </div>
                <!--签到和快捷通道区域 ]]-->
                <!--我的资产模块 [[-->
                <div class="ms-app ms-money-myset not-activate" id="memberBalanceAccount">
                    <h3 class="app-title"><a target="_blank" href="#">我的资产</a></h3>
                    <div class="l">
                        <a href="javascript:void(0);" name="Myyigou_index_none_zichan001" class="text-seemoney">查看我的资产/优惠</a>
                        <div class="ms-money-show" id="ms-money-show"></div>
                    </div>
                    <div class="fb list-myset">
                        <ul>
                            <li>
                                <a href="#">礼品卡</a>：
                                <span>
                                    <a href="#">0.00</a>
                                </span>元
                            </li>
                            <li>
                                <a name="Myyigou_index_none_zichan010" href="#">优惠券</a>：
                                <span><a href="#">1</a></span>张
                            </li>
                            <li>
                                <a href="#">积&nbsp;分</a>：
                                <span><a target="_blank" href="#">244.00</a></span>
                            </li>
                        </ul>
                    </div>
                </div>
                <!--我的资产模块 end[[-->
                <!--我的订单区域 [[-->
                <div class="ms-app ms-myorder">
                    <h3 class="app-title">
                        <a target="_blank" href="#" style="display: inline-block;">我的订单</a>
                    </h3>
                    <div class="article-order">
                        <span class="order-number">订单：<a target="_blank" href="#" style="text-decoration: none;">
                            <em>2007734291</em>
                        </a>
                        </span>
                        <div class="list-order-pic">
                            <div class="list-order-costpic l">
                                <a target="_blank" name="Myyigou_index_order1_pic001" href="#" title="苏泊尔电压力锅CYSB50YC6B-100金属色">
                                    <img src="../images-sns/cp.jpg">
                                </a>
                            </div>
                            <div class="text-order">
                                <div class="text-order-title">
                                    <a target="_blank" href="#" name="Myyigou_index_order1_name" class="text-order-titlelink" title="沁园(qinyuan)A450EI323VB 立式净水器">沁园(qinyuan)A450EI323VB 立式净水器</a>
                                </div>
                                <p>
                                    <em>¥</em>299.00
                                </p>
                            </div>
                        </div>
                        <div class="pay-order pay-order-text" id="2007734291">
                            <span class="stats-order-ok">订单已完成</span>
                            <a name="Myyigou_index_order1_button005" href="#" class="stats-btn-order">再次购买</a>
                        </div>
                    </div>
                    <div class="article-order article-order-line">
                        <span class="order-number">订单：
                            <a target="_blank" href="#" style="text-decoration: none;">
                                <em>2006270761</em>
                            </a>
                        </span>
                        <div class="list-order-pic">
                            <div class="list-order-costpic l">
                                <a target="_blank" name="Myyigou_index_order2_pic001" href="#" title="沁园(qinyuan)A450EI323VB 立式净水器(A450EI323VB 五级过滤 黑色)">
                                    <img src="../images-sns/cp.jpg">
                                </a>
                            </div>
                            <div class="text-order">
                                <div class="text-order-title">
                                    <a target="_blank" href="#" name="Myyigou_index_order2_name" class="text-order-titlelink" title="沁园(qinyuan)A450EI323VB 立式净水器(A450EI323VB 五级过滤 黑色)">沁园(qinyuan)A450EI323VB 立式净水器 五级过滤 黑色</a>
                                </div>
                                <p>
                                    <em>¥</em>4199.00
                                </p>
                            </div>
                        </div>
                        <div class="pay-order pay-order-text" id="2006270761">
                            <span class="stats-order-ok">订单已完成</span>
                            <a name="Myyigou_index_order2_button002" href="#" class="stats-btn-order">评价</a>
                        </div>
                    </div>
                    <div class="article-order article-order-line article-order-last">
                        <div class="list-order-pic">
                            <img src="../images-sns/empty-order-right.jpg">
                        </div>
                    </div>
                </div>
                <!--我的订单区域 end]]-->
                <!--待评价 ]]-->
                <div class="ms-app app-card" id="app_module6" data-url="evaluationControl">
                    <h3 class="app-title">
                        <a target="_blank" href="#">待评价商品</a>
                    </h3>
                    <div class="article-order">
                        <div class="list-order-pic list-order-picmore pt15 fa">
                            <div class="dpj">
                                <a class="hover-totals fa" title="沁园立式净水器" name="Myyigou_index_none_review001" target="_blank" href="#">
                                    <img src="../images-sns/cp.jpg">
                                    <span class="hover-be-show text-total">80积分</span>
                                </a>
                                <p class="wypj fa">
                                    <a target="_blank" name="Myyigou_index_none_review002" href="#">我要评价</a>
                                </p>
                            </div>
                            <div class="text-empty-orderpic">评价送积分勤俭好持家</div>
                        </div>
                        <div class="list-order-info">
                            <a target="_blank" href="#">未评价的商品
                <span>(1)</span>
                            </a>
                        </div>
                    </div>
                </div>
                <!--待评价 end]]-->
                <!--为你推荐 -->
                <div class="ms-app app-card" id="Div1" data-url="evaluationControl">
                    <h3 class="app-title">
                        <a target="_blank" href="#">为你推荐</a>
                    </h3>
                    <div class="rend">
                        <ul>
                            <li>
                                <a href="#" title="沁园(qinyuan)A450EI323VB 立式净水器">
                                    <img src="../images-sns/cp.jpg" alt="Alternate Text" />
                                </a>
                                <p class="link-evaluate">
                                    <span><em>¥</em>1499.00</span>
                                </p>
                            </li>
                            <li>
                                <a href="#" title="沁园(qinyuan)A450EI323VB 立式净水器">
                                    <img src="../images-sns/cp.jpg" alt="Alternate Text" />
                                </a>
                                <p class="link-evaluate">
                                    <span><em>¥</em>1499.00</span>
                                </p>
                            </li>
                            <li>
                                <a href="#" title="沁园(qinyuan)A450EI323VB 立式净水器">
                                    <img src="../images-sns/cp.jpg" alt="Alternate Text" />
                                </a>
                                <p class="link-evaluate">
                                    <span><em>¥</em>1499.00</span>
                                </p>
                            </li>
                        </ul>
                        <div class="anniu">
                            <a href="#" class="zanniu"></a>
                            <a href="#" class="yanniu"></a>
                        </div>
                    </div>
                </div>
                <!--为你推荐 end]]-->
                <!--最近浏览 -->
                <div class="ms-app app-card" id="Div2" data-url="evaluationControl">
                    <h3 class="app-title">
                        <a target="_blank" href="#">最近浏览</a>
                    </h3>
                    <div class="browse">
                        <ul>
                            <li>
                                <a href="#" title="沁园(qinyuan)A450EI323VB 立式净水器">
                                    <img src="../images-sns/cp.jpg" alt="Alternate Text" />
                                </a>
                                <p class="link-evaluate">
                                    <span><em>¥</em>1499.00</span>
                                </p>
                            </li>
                            <li>
                                <a href="#" title="沁园(qinyuan)A450EI323VB 立式净水器">
                                    <img src="../images-sns/cp.jpg" alt="Alternate Text" />
                                </a>
                                <p class="link-evaluate">
                                    <span><em>¥</em>1499.00</span>
                                </p>
                            </li>
                            <li>
                                <a href="#" title="沁园(qinyuan)A450EI323VB 立式净水器">
                                    <img src="../images-sns/cp.jpg" alt="Alternate Text" />
                                </a>
                                <p class="link-evaluate">
                                    <span><em>¥</em>1499.00</span>
                                </p>
                            </li>
                            <li>
                                <a href="#" title="沁园(qinyuan)A450EI323VB 立式净水器">
                                    <img src="../images-sns/cp.jpg" alt="Alternate Text" />
                                </a>
                                <p class="link-evaluate">
                                    <span><em>¥</em>1499.00</span>
                                </p>
                            </li>
                        </ul>
                    </div>
                </div>
                <!--最近浏览 end]]-->
            </div>
            <!--右 内容区-->
            <div class="wdyyq" style="display:block;">
                <div class="wdyy fb">
                    <div class="yybt">我的预约</div>
                    <div class="yyjs">
                        温馨提示：商品预约成功后，您可在“我的预约”页面，查看您的预约商品记录；
                        待商品开始售卖时，可点击“立即抢购”按钮到商品详情页或者购物车页面购买商品。
                    </div>
                    <div class="wdyylb">
                        <ul>
                            <li>
                                <a href="#" class="xuanzhong">商品预约</a>
                            </li>
                            <!--
                            <li>
                                <a href="#">门店预约</a>
                            </li>
                            -->
                        </ul>
                    </div>
                    <div class="xxxxq">
                        <table cellspacing="0"cellpadding="0">
                            <tbody>   
                                <tr>
                                    <th width="100">商家</th>
                                    <th>商品信息</th>
                                    <th width="200">状态</th>
                                    <th width="120">操作</th>
                                    <th width="130">预留手机号</th>
                                </tr>
                                
                                [#if applys?has_content]
                                	[#list applys as apply]
		                                <tr class="danhao">
		                                    <td colspan="5">
		                                        <span>预约编号：${apply.preSellApplyNo}</span>
		                                        <span>预约时间：${apply.applyDate}</span>
		                                        <input type="hidden" value="${apply.id}" id="getId" />
		                                        <input type="hidden" value="${apply.preSellApplyNo}" id="getNo" />
		                                       <input type="text" id="${apply.id}" value="${apply.preSellRole.beginDate?string("yyyy-MM-dd HH:mm:ss")}"/>
		                                       <input type="text" id="${apply.preSellApplyNo}" value="${apply.preSellRole.endDate?string("yyyy-MM-dd HH:mm:ss")}"/>
		                                    </td>
		                                </tr>
		                                <tr>
		                                    <td height="50" class="svTop">
		                                        <div class="orderNum">
		                                            <a href="#">
		                                                <img src=" ${base}/resources/shop/images-sns/online.gif" alt="Alternate Text" />
		                                            </a>
		                                            <p>沁园商场</p>
		                                        </div>
		                                    </td>
		                                    <td class="uiTable">
		                                        <dl class="goodOrder">
		                                            <dt>
		                                                <a href="${base}${apply.product.path}" title="沁园PSK1273S立式净水器">
		                                                    <img src="${apply.product.image}" alt="" />
		                                                </a>
		                                            </dt>
		                                            <dd class="gName">
		                                                <a href="${base}${apply.product.path}" title="沁园PSK1273S立式净水器">${apply.product.name}</a>
		                                            </dd>
		                                        </dl>
		                                    </td>
		                                    <td class="ksys">
		                                        <div class="action">
		                                        	<input type="hidden" value="${apply.id}${apply.preSellApplyNo}" id="getTime" />
		                                            <p id="${apply.id}${apply.preSellApplyNo}">
		                                                <i class="icon-orderTime toBegin"></i>
		                                                距离开始：<br/>
		                                                <em id="dd"></em>
		                                                天
		                                                <em id="hh"></em>
		                                                时
		                                                <em id="mm"></em>
		                                                分
		                                                <em id="ss"></em>
		                                                秒
		                                            </p>
		                                            <script type="text/javascript">
											           		arrays[index] = String(${apply.id})+String(${apply.preSellApplyNo});
											           		index = index +1;
										           </script>
		                                        </div>
		                                    </td>
		                                    <td class="ksys">
		                                        <div class="action">
		                                            <p class="Btn-orderBuy">
		                                                <a href="javascript:;" id="wait_buy_link">
		                                                	<input type="hidden" value="${apply.preSellRole.id}" id="preSellRoleId">
		                                                	<input type="button" name="" value="" id="wait_buy" disabled="true"/>
		                                                </a>
		                                            </p>
		                                        </div>
		                                    </td>
		                                    <td class="ksys ham">
		                                        <div class="action">
		                                            <p>
		                                                ${apply.applierMobile}
		                                            </p>
		                                        </div>
		                                    </td>
		                                </tr>
                                [/#list]
                                [/#if]
                                
                            </tbody>
                        </table>
                        <!--分页-->
                        <div class="snPages">
                            <span class="prev">
                                <b></b>
                                 上一页
                            </span>
                            <a href="#">1</a>
                            <span class="next">
                                <b></b>
                                下一页
                            </span>
                            <div>
                                跳转至
                                <input type="text" name="name" value=" " />
                                页
                                <input type="button" class="pagesubmit" name="name" value="确定 " />
                            </div>
                        </div>
                        <!--分页 end-->
                    </div>
                </div>
            </div>

            
        </div>
        <div class="cenmax"></div>
    </div>
    <!--我的沁园内容区 end-->
    <div class="cenmaxbs"></div>
	[#include "/shop/include/footer.ftl"]
</body>
</html>
