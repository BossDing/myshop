<html>
<head>
<meta http-equiv="content-type" content="charset=utf-8" />
<title>UP</title>
</head>
<body>
	<div class="cpzx_ylm"><script language="javascript">
var menuTimer =null;
function showmenu(obj1,obj2,state){ 
    var btn=document.getElementById(obj1);
    var obj=document.getElementById(obj2);
    obj.onmouseover =function(){
        showmenu(obj1,obj2,'show');
    }
    obj.onmouseout =function(){
        showmenu(obj1,obj2,'hide');
    }
    if(state =="hide"){
        menuTimer =setTimeout("hiddenmenu('"+ obj2 +"')", 50);
    }else{
        clearTimeout(menuTimer);
        obj.style.display ="block";
    }
}
function hiddenmenu(psObjId){
    document.getElementById(psObjId).style.display ="none";
}
</script>


<div class="cpzx_ylm_1"><a target="_blank" href="${base}/gw/product/list/1079.jhtml">产品中心</a></div>

[@product_category_root_list entcode="macrogw" useCache="false"]
	[#list productCategories as category]
		<div class="cpzx_ylm_2">
			<!--<a target="_blank" href="${base}/gw/product/list/${category.id}.jhtml">${category.name}</a>-->
			[#if category.id=1079]
				<a target="_blank" href="http://www.chinamacro.cn/reshui/">${category.name}</a>
			[#elseif category.id=1089]
				<a target="_blank" href="http://www.chinamacro.cn/gongnuan/">${category.name}</a>
			[#else]
				<a target="_blank" href="${base}/gw/product/list/${category.id}.jhtml">${category.name}</a>
			[/#if]
		</div>
		[@product_category_children_list productCategoryId = category.id dg=true]
			[#list productCategories as productCategory]
				<div class="cpzx_ylm_3">
					<!--
					<a id="m${productCategory.id}" target="_blank" href="${base}/gw/product/plist/${productCategory.id}.jhtml" onmouseover="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;show&#39;,&#39;bottom&#39;)"
							 onmouseout="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;hide&#39;,&#39;bottom&#39;)">
							  		${productCategory.name}
					</a>
					-->
					[#if productCategory.id=1080]
						<a id="m${productCategory.id}" target="_blank" href="http://www.chinamacro.cn/reshui/ranqi/" onmouseover="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;show&#39;,&#39;bottom&#39;)"
							 onmouseout="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;hide&#39;,&#39;bottom&#39;)">
							  		${productCategory.name}
						</a>
					[#elseif productCategory.id=1081]
						<a id="m${productCategory.id}" target="_blank" href="http://www.chinamacro.cn/reshui/kongqi/" onmouseover="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;show&#39;,&#39;bottom&#39;)"
							 onmouseout="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;hide&#39;,&#39;bottom&#39;)">
							  		${productCategory.name}
						</a>
					[#elseif productCategory.id=1086]
						<a id="m${productCategory.id}" target="_blank" href="http://www.chinamacro.cn/chufang/chouyou/" onmouseover="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;show&#39;,&#39;bottom&#39;)"
							 onmouseout="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;hide&#39;,&#39;bottom&#39;)">
							  		${productCategory.name}
						</a>
					[#elseif productCategory.id=1087]
						<a id="m${productCategory.id}" target="_blank" href="http://www.chinamacro.cn/chufang/ranqizao/" onmouseover="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;show&#39;,&#39;bottom&#39;)"
							 onmouseout="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;hide&#39;,&#39;bottom&#39;)">
							  		${productCategory.name}
						</a>
					[#elseif productCategory.id=1090]
						<a id="m${productCategory.id}" target="_blank" href="http://www.chinamacro.cn/gongnuan/bigualu/" onmouseover="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;show&#39;,&#39;bottom&#39;)"
							 onmouseout="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;hide&#39;,&#39;bottom&#39;)">
							  		${productCategory.name}
						</a>
					[#else]
						<a id="m${productCategory.id}" target="_blank" href="${base}/gw/product/plist/${productCategory.id}.jhtml" onmouseover="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;show&#39;,&#39;bottom&#39;)"
							 onmouseout="showmenu(&#39;m${productCategory.id}&#39;,&#39;mdiv${productCategory.id}&#39;,&#39;hide&#39;,&#39;bottom&#39;)">
							  		${productCategory.name}
						</a>
					[/#if]
				</div>
				<div id="mdiv${productCategory.id}" style="display: none">
				[@product_category_children_list productCategoryId = productCategory.id  dg=true]
					[#list productCategories as p]
					        [#assign rows = 1 /]
						[@product_category_children_list productCategoryId = p.id  dg=true]
							[#list productCategories as p2]
							[#assign rows = rows+1 /]
							[/#list]
						[/@product_category_children_list]
						[#if rows==1 || p.isShowProduct]
							<div class="cpzx_ylm_4"><a target="_blank" href="${base}/gw/product/pplist/${p.id}.jhtml">${p.name}</a></div>
							[#if p.isShowProduct]
								[@product_category_children_list productCategoryId = p.id  dg=true]
									[#list productCategories as p2]
										<div class="cpzx_ylm_5"><a target="_blank" href="${base}/gw/product/pplist/${p2.id}.jhtml">${p2.name}</a></div>
									[/#list]
								[/@product_category_children_list]
							[/#if]
						[#else]
							<div class="cpzx_ylm_4">
								<a target="_blank" href="${base}/gw/product/plist/${p.id}.jhtml">${p.name}</a>
							</div>
						[/#if]

					[/#list]
				[/@product_category_children_list]
				</div>
			[/#list]
		[/@product_category_children_list]
	[/#list]
[/@product_category_root_list]

<!--<div class="cpzx_ylm_2"><a target="_blank" href="http://www.chinamacro.cn/reshui/">热水系统</a></div>
<div class="cpzx_ylm_3"><a id="m1" target="_blank" onmouseover="showmenu(&#39;m1&#39;,&#39;mdiv&#39;,&#39;show&#39;,&#39;bottom&#39;)" onmouseout="showmenu(&#39;m1&#39;,&#39;mdiv&#39;,&#39;hide&#39;,&#39;bottom&#39;)" href="./燃气热水器_产品中心_万家乐官方网站_files/燃气热水器_产品中心_万家乐官方网站.htm">燃气热水器</a></div>
<div id="mdiv" style="display: none">
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/ranqi/zhineng/">智能浴系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/ranqi/zidong/">自动恒温系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/ranqi/shoudong/">手动调温系列</a></div>
</div>
<div class="cpzx_ylm_3"><a id="m2" target="_blank" onmouseover="showmenu(&#39;m2&#39;,&#39;mdiv2&#39;,&#39;show&#39;,&#39;bottom&#39;)" onmouseout="showmenu(&#39;m2&#39;,&#39;mdiv&#39;,&#39;hide&#39;,&#39;bottom&#39;)" href="http://www.chinamacro.cn/reshui/dian/">电热水器</a></div>
<div id="mdiv2" style="display: none">
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/dian/sure/">智能速热系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/dian/bianrong/">智能变容系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/dian/bianjie/">实惠便捷系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/dian/nengyuan/">复合能源系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/dian/chuxing/">小厨星系列</a></div>
</div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/reshui/kongqi/">空气能热水器</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/kongqi/">家用机</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/reshui/kongqi/shangyong/">商用机</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/chanpin/a994.html">太阳能热水器</a></div>
<div class="cpzx_ylm_2"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/">供暖系统</a></div>
<div class="cpzx_ylm_3"><a id="m3" target="_blank" onmouseover="showmenu(&#39;m3&#39;,&#39;mdiv3&#39;,&#39;show&#39;,&#39;bottom&#39;)" onmouseout="showmenu(&#39;m3&#39;,&#39;mdiv&#39;,&#39;hide&#39;,&#39;bottom&#39;)" href="http://www.chinamacro.cn/gongnuan/bigualu/">壁挂炉</a></div>
<div id="mdiv3" style="display: none">
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/bigualu/ABC/">ABC系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/bigualu/D/">D系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/bigualu/E/">E系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/bigualu/F/">F系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/bigualu/K/">K系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/bigualu/P/">P系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/bigualu/U/">U系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/bigualu/V/">V系列</a></div>
</div>
<div class="cpzx_ylm_3"><a id="m4" target="_blank" onmouseover="showmenu(&#39;m4&#39;,&#39;mdiv4&#39;,&#39;show&#39;,&#39;bottom&#39;)" onmouseout="showmenu(&#39;m4&#39;,&#39;mdiv&#39;,&#39;hide&#39;,&#39;bottom&#39;)" href="http://www.chinamacro.cn/chanpin/a1049.html">散热器</a></div>
<div id="mdiv4" style="display: none">
<div class="cpzx_ylm_4"><a href="http://www.chinamacro.cn/gongnuan/sanre/weimei/">维美特系列</a></div>
<div class="cpzx_ylm_5"><a href="http://www.chinamacro.cn/gongnuan/sanre/weimei/weiyu/">钢制卫浴型</a></div>
<div class="cpzx_ylm_5"><a href="http://www.chinamacro.cn/gongnuan/sanre/weimei/gongcheng/">工程型钢制单柱型</a></div>
<div class="cpzx_ylm_5"><a href="http://www.chinamacro.cn/gongnuan/sanre/weimei/bianguan/">钢制扁管单排型</a></div>
<div class="cpzx_ylm_5"><a href="http://www.chinamacro.cn/gongnuan/sanre/weimei/zhuxing/">钢制柱型</a></div>
<div class="cpzx_ylm_4"><a href="http://www.chinamacro.cn/gongnuan/sanre/bigualu/">壁挂炉专用系列</a></div>
<div class="cpzx_ylm_5"><a href="http://www.chinamacro.cn/gongnuan/sanre/bigualu/yazhu/">压铸铝型</a></div>
<div class="cpzx_ylm_5"><a href="http://www.chinamacro.cn/gongnuan/sanre/bigualu/gangzhi/">钢制板型</a></div>
<div class="cpzx_ylm_4"><a href="http://www.chinamacro.cn/gongnuan/sanre/make/">马克鲁尼系列</a></div>
<div class="cpzx_ylm_5"><a href="http://www.chinamacro.cn/gongnuan/sanre/make/weiyu/">钢制卫浴型</a></div>
<div class="cpzx_ylm_5"><a href="http://www.chinamacro.cn/gongnuan/sanre/make/zhuyi/">铜铝复合柱翼型</a></div>
<div class="cpzx_ylm_5"><a href="http://www.chinamacro.cn/gongnuan/sanre/make/zhuxing/">钢制柱型</a></div>
</div>
<div class="cpzx_ylm_3"><a id="m5" target="_blank" onmouseover="showmenu(&#39;m5&#39;,&#39;mdiv5&#39;,&#39;show&#39;,&#39;bottom&#39;)" onmouseout="showmenu(&#39;m5&#39;,&#39;mdiv&#39;,&#39;hide&#39;,&#39;bottom&#39;)" href="http://www.chinamacro.cn/gongnuan/mokuai/">模块锅炉</a></div>
<div id="mdiv5" style="display: none">
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/mokuai/ZT/">ZT系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/gongnuan/mokuai/ZTS/">ZTS系列</a></div>
</div>
<div class="cpzx_ylm_2"><a target="_blank" href="http://www.chinamacro.cn/chufang/">厨房电器</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/chufang/chudian/">厨电套装</a></div>
<div class="cpzx_ylm_3"><a id="m6" target="_blank" onmouseover="showmenu(&#39;m6&#39;,&#39;mdiv6&#39;,&#39;show&#39;,&#39;bottom&#39;)" onmouseout="showmenu(&#39;m6&#39;,&#39;mdiv&#39;,&#39;hide&#39;,&#39;bottom&#39;)" href="http://www.chinamacro.cn/chufang/chouyou/">吸油烟机</a></div>
<div id="mdiv6" style="display: none">
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/chufang/chouyou/zhikong/">六位智控系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/chufang/chouyou/jinxi/">劲吸系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/chufang/chouyou/pinban/">平板系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/chufang/chouyou/MINI/">MINI系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/chufang/chouyou/baochao/">爆炒风系列</a></div>
</div>
<div class="cpzx_ylm_3"><a id="m7" target="_blank" onmouseover="showmenu(&#39;m7&#39;,&#39;mdiv7&#39;,&#39;show&#39;,&#39;bottom&#39;)" onmouseout="showmenu(&#39;m7,&#39;mdiv&#39;,&#39;hide&#39;,&#39;bottom&#39;)" href="http://www.chinamacro.cn/chufang/ranqizao/">燃气灶</a></div>
<div id="mdiv7" style="display: none">
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/chufang/ranqizao/menghuo/">双高猛火系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/chufang/ranqizao/yunhuo/">双高匀火系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/chufang/ranqizao/linglong/">双高玲珑火系列</a></div>
</div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/chufang/xiaodu/">消毒柜</a></div>
<div class="cpzx_ylm_2"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/">生活电器</a></div>
<div class="cpzx_ylm_3"><a id="m8" target="_blank" onmouseover="showmenu(&#39;m8&#39;,&#39;mdiv8&#39;,&#39;show&#39;,&#39;bottom&#39;)" onmouseout="showmenu(&#39;m8&#39;,&#39;mdiv&#39;,&#39;hide&#39;,&#39;bottom&#39;)" href="http://www.chinamacro.cn/shenghuo/yali/">电压力锅</a></div>
<div id="mdiv8" style="display: none">
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/yali/danji/">单机系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/yali/libao/">大礼包机系列</a></div>
</div>
<div class="cpzx_ylm_3"><a id="m9" target="_blank" onmouseover="showmenu(&#39;m9&#39;,&#39;mdiv9&#39;,&#39;show&#39;,&#39;bottom&#39;)" onmouseout="showmenu(&#39;m9&#39;,&#39;mdiv&#39;,&#39;hide&#39;,&#39;bottom&#39;)" href="http://www.chinamacro.cn/shenghuo/diancilu/">电磁炉</a></div>
<div id="mdiv9" style="display: none">
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/diancilu/gongneng/">多功能系列</a></div>
<div class="cpzx_ylm_4"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/diancilu/yijian/">一键通系列</a></div>
</div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/reshui/">电热水壶</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/dianbingdang/">电饼铛</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/dianfanguo/">电饭锅</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/fengshan/">电风扇</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/shenghuo/dianluanqi/">电暖器</a></div>
<div class="cpzx_ylm_2"><a target="_blank" href="http://www.chinamacro.cn/kongqi/">空气净化器</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/kongqi/jiaquan/">专业新居除甲醛</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/kongqi/yiwei/">专业除PM2.5/异味</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/kongqi/muyin/">母婴专用</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/kongqi/bangong/">办公专用</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/kongqi/PRO/">Pro-air商铺</a></div>
<div class="cpzx_ylm_3"><a target="_blank" href="http://www.chinamacro.cn/kongqi/Personal/">个人专用</a></div></div>-->

</body>
</html>