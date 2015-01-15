<!doctype html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>沁园水质查询</title>
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/top_waterquality.css">
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/footer_waterquality.css">
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/RO_waterquality.css">
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/raphael.2.1.0.min.js"></script>
    <script type="text/javascript" src="${base}/resources/shop/js/justgage.1.0.1.js"></script>
	<script type="text/javascript">
	$().ready(function() {
		var $scrollable = $("#scrollable");
			// 商品缩略图滚动
		$scrollable.scrollable({
			items:"#items",
			loop:true,//设置是否自动跳转（根据间隔时间）
			interval: 1000,//设置间歇时间间隔
			speed:1000
		}); 
		
        var obj = $scrollable.scrollable();
        $("#next").click(function(){
        	obj.next() ;
        
        });
        $("#prev").click(function(){
        	obj.prev() ;
        	
        });
		var $areaId = $("#areaId");
		//地区选择
		$areaId.lSelect({
			url: "${base}/common/area.jhtml"
		});
		
		var $addrForm = $('#addrForm select:eq(0)');
		
		$addrForm.change(function(){
			if($areaId.val() =="" || $areaId.val() ==null || $areaId.val() == 0){
				$addrForm.siblings('label').css('display','block');
			}else{
				$addrForm.siblings('label').css('display','none');
			}
		});
		
		var $text_search = $("#text_search");
		var $test = $("#test");
		var $tips = $("#tips");
		
		$test.click(function(){
			$("#span_tds").text("");
			$("#span_cl").text("");
			g1.refresh(0);
			$("#init-result").css("display","block");
			$("#result").css("display","none");
			$("#picarea").css("display","block");
			$("#recommend").css("display","none");
			$("#error").css("display","none");
			$("#items").text("");
			if($areaId.val() == null){
				$tips.css("display","block");
				return;
			}
			
			$.ajax({
				url: "testWater.jhtml",
				type: "POST",
				data: {
					areaId: $.trim($areaId.val()),
					districtName: $.trim($text_search.val())
				},
				dataType: "json",
				cache: false,
				success: function(data) {
					
					if(data.tds!=null){
						$("#span_tds").text(data.tds);
						$("#span_cl").text(data.cl);
						$("#init-result").css("display","none");
						$("#result").css("display","block");
						
						if(data.products!=null){
							for(var i=0; i<data.products.length; i++){
								if(i<5){
									var objHtml =
										'<li>'
		                                    +'<div class="tjxx">'
		                                        +'<h3>特别推荐<\/h3>'
		                                        +'<div class="s-name">'
		                                           +' <a href="${base}/product/content/201407/'+data.products[i].id+'.html">'+ data.products[i].name +'<\/a>'
		                                        +'<\/div>'
		                                        +'<div class="s-ext">'
		                                            +'<b>五级过滤<\/b>'
		                                        +'<\/div>'
		                                    +'<\/div>'
		                                    +'<a href="${base}'+data.products[i].path+'">'
		                                        +'<img product src="' + data.products[i].image + '" alt="'+data.products[i].fullName+'" \/>'
		                                    +'<\/a>'
			                            +'<\/li>';
									$("#items").append(objHtml);
								}
							}
							$("#picarea").css("display","none");
							$("#recommend").css("display","block");
						}
						
						
					}else{
						$("#error").css("display","block");
						$("#init-result").css("display","none");
					}
					g1.refresh($("#span_tds").text());
					
				}		
			});	
			
		});
		var g1 = new JustGage({
			id: "yibiao", 
			value: 0, 
			min: 0,
			max: 1000,
			title: "测试TDS值",
			label: "mg/l",
			levelColors: [
			              "#339933",
			              "#99FF00",
			              "#CC6633",
			              "#993300",
			              "#FF0000"
			              ],
			refreshAnimationType : "bounce",
			startAnimationType:"bounce",
			gaugeColor :"#DDDDDD",
			valueFontColor : "#842b00",
			titleFontColor :"#842b00",
			refreshAnimationTime :2000
	  	});
	});
	</script>
</head>
<body>
[#include "/shop/include/header.ftl" /]

    <div class="water-quality">
        <div class="js-query">
            <div class="js-container fn-clear">
                <div class="col-main">
                    <div class="query-area">
                        <div class="search-city fn-clear">
                            <div class="addrForm js_mmjs_validation_fe_wrap">
								<span>
									${message("shop.apply.area")}
								</span>
								<div class="addrFormItem" id="addrForm" >
									<input type="hidden" id="areaId" name="areaId"/>
								</div>
								<div class="cn js_validation_marked_info"></div>
							</div>
                        </div>
                        <div class="search-opt">
                            <!--<input type="text" placeholder="请输入小区名称搜索" />-->
                            <input id="text_search" type="text" placeholder="输入小区名称搜索" style="width: 542px; height: 25px; color: rgb(169, 169, 169);">
                            <a id="test" href="javascript:;" title="我要测试">我要测试</a><strong id="tips" style="display: none">
									请选择地区！
								</strong>
                        </div>
                        <div id="div_district_options" style="display: none;">
                            <div id="district_options"></div>
                        </div>
                    </div>
                    <!--#query-area-->
                    <div class="query-fade" id="picarea">
                        <div class="slide">
                            <ul class="slide-element" style="display: block;">
                                <li style="display: list-item;">
                                    <img src="${base}/resources/shop/images/ad_610_360_01.png" alt="">
                                </li>
                            </ul>
                            <div class="wntj">
                            </div>
                        </div>
                    </div>
                    <!--#query-fade-->
                    <!--recommend -->
                    <div class="wntj" id="recommend">
                        <div class="tjbt">
                            <img src="${base}/resources/shop/images/tjbt.png" alt="Alternate Text" />
                        </div>
                        <div class="tjlb" id="scrollable" style="
                            position: relative;">
                            <ul id="items"></ul>
                        </div>
                        <div class="anniu">
                            <span class="anniuz fa">
                                <a href="javascript:;" id="prev"></a>
                            </span>
                            <span class="anniuy fb" >
                                <a href="javascript:;" id="next"></a>
                            </span>
                        </div>
                    </div>
                    <!--recommend end-->
                    <div class="query-tds">
                        <img src="${base}/resources/shop/images/tds.png" alt="">
                    </div>
                    <!--#query-fade-->
                </div>

                <div class="col-side">
                    <div class="query-show">
                    <div id="yibiao" style="width:400px !important;height:160px !important;"></div>
                        <!--#query_flash-->
                        <div class="query-result">
                            <ul style="display: none" id="result">
                                <li>您所在小区水质TDS值为：<strong><span id="span_tds"></span></strong></li>
                                <li>余氯含量为：<strong><span id="span_cl"></span></strong></li>
                                <li>TDS如果在300以上，推荐使用反渗透净水机（纯水机）</li>
                            </ul>
                            <p id="error" style="display: none">
                                对不起！系统暂时没有您小区的水质数据！<br>
                                <!--请致电<span style="color: #32beff;">400-800-1234</span>，我们将为您免费送测水笔。-->
                            </p>
                            <!--<div id="error" style="display:none;width:100%;height:100%">对不起，系统暂时没有您小区的水质数据，<br>请致电 <strong>400-999-9999</strong>，我们将免费上门为您测水质。</div>-->
                            <p id="init-result">
                                全国小区水质数据可查询啦！
                            </p>
                        </div>
                        <div class="tips">
                        </div>
                        <!--#query_result-->
                        <div class="query-whats">
                            <h3>什么是TDS值和余氯</h3>
                            <p>TDS是水质中的捣乱分子，通俗来讲TDS值越大，说明水中的有机物和无机物等含量越多，其中可能包含对人体有益的矿物质，也可能包含对人体有害的其他物质。话说，在中国TDS值高好不好？呵呵，你应该懂得！你家的水质TDS你造吗？</p>
                        </div>
                        <div class="query-value">
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td class="c1" width="110">TDS数值</td>
                                        <td class="c1">选择净水机</td>
                                    </tr>
                                    <tr>
                                        <td class="c2">0-90</td>
                                        <td>微滤机(0.1微米)</td>
                                    </tr>
                                    <tr>
                                        <td class="c3">91-300</td>
                                        <td>超滤机(0.01微米)</td>
                                    </tr>
                                    <tr>
                                        <td class="c4">300以上</td>
                                        <td>RO反渗透机(0.0001微米)</td>
                                    </tr>
                                </tbody>
                            </table>
                            <p>全球部分国家生活饮水水质TDS最高限值标准（超出即不合格）</p>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td width="110">中国</td>
                                        <td>不高于1000</td>
                                    </tr>
                                    <tr>
                                        <td>美国</td>
                                        <td>不高于500</td>
                                    </tr>
                                    <tr>
                                        <td>日本</td>
                                        <td>不高于500</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <!--#query_value-->
                        <div class="query-share">
                            <div class="bdsharebuttonbox bdshare-button-style1-16">
                                <span class="fn-left label">分享到：</span>
                                <a class="jiathis_button_qzone" title="分享到QQ空间"></a>
                                <a class="jiathis_button_tsina" title="分享到新浪微博"></a>
                                <a class="jiathis_button_tqq" title="分享到腾讯微博"></a>
                                <a class="jiathis_button_renren" title="分享到人人网"></a>
                                <a class="jiathis_button_weixin" data-cmd="weixin" title="分享到微信"></a>
                                <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1" charset="utf-8"></script>
                            </div>
                        </div>
                        <!--#query_share-->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--#js_query-->
    <div class="js-query-standard">
    </div>
    <!--#js_query_standard-->
    <!--js_query_tip s-->
    <div class="js-query-tip">
        <strong style="color: #0c7ac9;">TDS值</strong>是指水中溶解性总固体含量，单位：mg/L。TDS值越大说明水中的矿物质和杂质含量越多，反之水质越纯净。
    <strong style="color: #0c7ac9;">余氯</strong>指水经加氯消毒，接触一定时间后，余留在水中的氯，单位：mg/L。超过一定量的氯，带有难闻的味道，俗称漂白粉味。
    </div>
    <!--js_query_tip e-->
 [#include "/shop/include/footer.ftl" /]
</body>
</html>
