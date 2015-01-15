<html>
<head>
<meta http-equiv="content-type" content="charset=utf-8" />
<title>UP</title>

<style>
#site_nav{ width:100%; background-color:#e60020; color:#FFF; height:40px;  line-height:40px; font-size: 12px;}
.content{width:1190px; margin:0px auto;}
#site_nav a{ margin:0px 1px; color:#fff;}
.hi_login{ float:left;}
.head_my_infor{ float:right;}
.head_my_infor li{ float:right; margin:0px 3px;}
.jp_nav{ overflow:hidden; margin:0px auto;width:845px;}
.jp_nav li{ float:left; width:65px; margin:25px 20px;}
.jp_nav li img{ width:100%;}
.jp_nav li p{ text-align:center; font-size: 12px;}
table{ margin:0px auto!important;}
table.nav_zcd p {
line-height: 30px;
text-align: center;
}
#h_nav li:first-child{width:190px;}
#h_nav li:first-child dd{width:90px;}
</style>
<script type="text/javascript">
	$().ready(function(){
	/*
		$("#service_policy").toggle(function(){
			//window.location.href = "/gw/article/glist/838.jhtml";
			$("#service_policy_children").show();
		},function(){
			$("#service_policy_children").hide();
		});
		
		$("#service_fee").toggle(function(){
			$("#service_fee_children").show();
			//window.location.href = "/gw/article/glist/838.jhtml";
		},function(){
			$("#service_fee_children").hide();
		});
	*/
		$("#service_policy").click(function(){
			if($("#service_policy_children").css('display')=="none"){
				$("#service_policy_children").show();
			}else{
				$("#service_policy_children").hide();	
			}
			$(this).attr("href","${base}/gw/article/glist/838.jhtml");
		});
		$("#service_fee").click(function(){
			if($("#service_fee_children").css('display')=="none"){
				$("#service_fee_children").show();
			}else{
				$("#service_fee_children").hide();	
			}
			$(this).attr("href","${base}/gw/article/glist/721.jhtml");
		});
		
		
		
	});
</script>

</head>
<body>
<div id="site_nav">
    <div class="content">
      <div class="hi_login"> <span>嗨，欢迎来到万家乐官方网站</span> </div>
      <div class="head_my_infor">
        <ul>
        <!--
	 [@navigation_list position = "top"]
		[#list navigations as navigation]
				<li><a href="${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a></li>
		[/#list]
	[/@navigation_list]
		-->
			<li><a href="http://shop.chinamacro.cn/" target="_blank">官方商城</a></li>
			<li><a href="${base}/gw/article/glist/838.jhtml">服务支持</a></li>
        </ul>
      </div> 
    </div>
  </div>
  </div>
<!-- 头部开始 -->
<DIV class=top>
<H1 class=logo>
	<A href="/">
		<IMG alt=万家乐官网提供燃气热水器、电热水器、空气能热水器等热水系统产品的查询、介绍、购买等服务 src="${base}/resources/gw/images/logo.jpg" width=92 height=31></A></H1>
<DIV class=lwj><IMG alt="万家乐 乐万家" 
src="${base}/resources/gw/images/wjl.jpg" width=111 
height=17></DIV></DIV>
<DIV class=nav_bg>
<DIV id=head_div>
<DIV id=wrap>
<DIV id=nav>
<UL id=nav_ul>
  <LI id=li_5 class=st><a href="http://shop.chinamacro.cn/" target="_blank">万家乐商城</a></LI>
  <LI id=li_1 class=st><a href="#">产品中心</a></LI>
  <LI id=li_4 class=st><a href="${base}/gw/product/preference.jhtml">精品推荐</a></LI>
  <LI id=li_2 class=st><a href="#">解决方案</a></LI>
  <LI id=li_3 class=st><a href="${base}/gw/article/glist/838.jhtml">服务支持</a></LI>
  </UL></DIV>
<DIV class=sou>
<SCRIPT type=text/javascript>
    
    function checkSearchForm(){
        if(document.getElementById('keyword').value)        {
            return true;
        }else{
            alert("请输入搜索关键词！");
            return false;
        }
    }
    
    </SCRIPT>

<FORM id=searchForm class=f_r onsubmit="return checkSearchForm()" 
style="TOP: 5px; _position: relative" method=get name=searchForm 
action=/gw/product/search.jhtml>
<DIV class=sou_lx><SELECT id=category class=B_input name=category><OPTION 
  selected value=0>所有分类</OPTION><OPTION value=1>热水系统</OPTION><OPTION 
  value=2>&nbsp;&nbsp;&nbsp;&nbsp;燃气热水器</OPTION><OPTION 
  value=5>&nbsp;&nbsp;&nbsp;&nbsp;太阳能热水器</OPTION><OPTION 
  value=4>&nbsp;&nbsp;&nbsp;&nbsp;电热水器</OPTION><OPTION 
  value=3>&nbsp;&nbsp;&nbsp;&nbsp;空气能热水器</OPTION><OPTION 
  value=6>集成厨柜</OPTION><OPTION 
  value=56>&nbsp;&nbsp;&nbsp;&nbsp;双饰面面板厨柜</OPTION><OPTION 
  value=57>&nbsp;&nbsp;&nbsp;&nbsp;金属面板厨柜</OPTION><OPTION 
  value=58>&nbsp;&nbsp;&nbsp;&nbsp;UV面板厨柜</OPTION><OPTION 
  value=59>&nbsp;&nbsp;&nbsp;&nbsp;烤漆面板厨柜</OPTION><OPTION 
  value=101>&nbsp;&nbsp;&nbsp;&nbsp;膜压面板厨柜</OPTION><OPTION 
  value=102>&nbsp;&nbsp;&nbsp;&nbsp;实木面板厨柜</OPTION><OPTION 
  value=7>供暖系统</OPTION><OPTION 
  value=9>&nbsp;&nbsp;&nbsp;&nbsp;散热器</OPTION><OPTION 
  value=8>&nbsp;&nbsp;&nbsp;&nbsp;壁挂炉</OPTION><OPTION 
  value=152>&nbsp;&nbsp;&nbsp;&nbsp;模块锅炉</OPTION><OPTION 
  value=10>净水系统</OPTION><OPTION value=11>厨房电器</OPTION><OPTION 
  value=12>&nbsp;&nbsp;&nbsp;&nbsp;吸油烟机</OPTION><OPTION 
  value=13>&nbsp;&nbsp;&nbsp;&nbsp;消毒柜</OPTION><OPTION 
  value=15>&nbsp;&nbsp;&nbsp;&nbsp;厨电套装</OPTION><OPTION 
  value=14>&nbsp;&nbsp;&nbsp;&nbsp;燃气灶</OPTION><OPTION 
  value=17>生活电器</OPTION><OPTION 
  value=18>&nbsp;&nbsp;&nbsp;&nbsp;电压力锅</OPTION><OPTION 
  value=19>&nbsp;&nbsp;&nbsp;&nbsp;电磁炉</OPTION><OPTION 
  value=20>&nbsp;&nbsp;&nbsp;&nbsp;电热水壶</OPTION><OPTION 
  value=21>&nbsp;&nbsp;&nbsp;&nbsp;电饼铛</OPTION><OPTION 
  value=22>&nbsp;&nbsp;&nbsp;&nbsp;电饭锅</OPTION><OPTION 
  value=54>&nbsp;&nbsp;&nbsp;&nbsp;电风扇</OPTION><OPTION 
  value=55>&nbsp;&nbsp;&nbsp;&nbsp;电暖器</OPTION><OPTION 
  value=170>空气净化器</OPTION><OPTION 
  value=171>&nbsp;&nbsp;&nbsp;&nbsp;专业新居除甲醛</OPTION><OPTION 
  value=172>&nbsp;&nbsp;&nbsp;&nbsp;专业除PM2.5/异味</OPTION><OPTION 
  value=173>&nbsp;&nbsp;&nbsp;&nbsp;母婴专用</OPTION><OPTION 
  value=174>&nbsp;&nbsp;&nbsp;&nbsp;办公专用</OPTION><OPTION 
  value=175>&nbsp;&nbsp;&nbsp;&nbsp;Pro-air商铺</OPTION><OPTION 
  value=176>&nbsp;&nbsp;&nbsp;&nbsp;个人专用</OPTION></SELECT></DIV>
		<DIV class=sou_an>
			<INPUT height=22 src="${base}/resources/gw/images/sou.jpg" type=image width=25 value="" name=imageField>
		</DIV>
		<DIV class=sou_srk>
			<INPUT id=keyword style="WIDTH: 110px" name=keyword>
		</DIV>
</FORM></DIV></DIV>
<DIV id=itemMun>
<DIV id=navbox>

<SPAN id="h_nav">
	<ul>
		[@product_category_root_list entcode="macrogw" useCache=false count=5]
			[#list productCategories as category  ]
				<li[#if !category_has_next] style="width:210px;background:none!important;"[/#if]>
					<dl[#if !category_has_next] class="last"[/#if]>
						<dt>
							[#if category.id=1079]
								<a href="http://www.chinamacro.cn/reshui/">${category.name}</a>
							[#elseif category.id=1089]
								<a href="http://www.chinamacro.cn/gongnuan/">${category.name}</a>
							[#else]
								<a href="${base}/gw/product/list/${category.id}.jhtml">${category.name}</a>
							[/#if]
						</dt>
						[@product_category_children_list productCategoryId = category.id dg=true ]
							[#list productCategories as productCategory]
								<dd>
									[#if productCategory.id=1080]
										<a href="http://www.chinamacro.cn/reshui/ranqi/">${productCategory.name}</a>
										<!--<a href="${base}/gw/product/plist/${productCategory.id}.jhtml">${productCategory.name}</a>-->
									[#elseif productCategory.id=1081]
										<a href="http://www.chinamacro.cn/reshui/kongqi/">${productCategory.name}</a>
									[#elseif productCategory.id=1086]
										<a href="http://www.chinamacro.cn/chufang/chouyou/">${productCategory.name}</a>
									[#elseif productCategory.id=1087]
										<a href="http://www.chinamacro.cn/chufang/ranqizao/">${productCategory.name}</a>
									[#elseif productCategory.id=1090]
										<a href="http://www.chinamacro.cn/gongnuan/bigualu/">${productCategory.name}</a>
									[#else]
										<a href="${base}/gw/product/plist/${productCategory.id}.jhtml">${productCategory.name}</a>	
									[/#if]
								</dd>
							[/#list]
						[/@product_category_children_list]
					</dl>
				</li>
			[/#list]
		[/@product_category_root_list]
	</ul>
 </SPAN>
 
 
 <SPAN>
	[@article_category_children_list articleCategoryId = 808 count=6]
		<table class=nav_zcd cellSpacing=0 cellPadding=0 width=1000 align=center border=0>
		  <tbody>
			  <tr>
				[#list articleCategories as a]
				         <TD vAlign=top width=145 align=left>
						      <TABLE class=nav_zcd cellSpacing=0 cellPadding=0 width="100%" border=0>
							        <TBODY>
								        <TR>
								          <TD style="FONT-WEIGHT: bold" height=30 align=left>
								          		[#if a.id=809]
								          			<!--<A title=${a.name} href="${base}/gw/article/list/${a.id}.jhtml">${a.name}</A>-->
								          			<A title=${a.name} href="http://www.chinamacro.cn/jiejue/reshui/">${a.name}</A>
								          		[#else]
								          			<A title=${a.name} href="${base}/gw/article/list/${a.id}.jhtml">${a.name}</A>	
								          		[/#if]
								          </TD>
								         </TR>
								         [@article_list articleCategoryId = a.id orderBy="order asc,createDate desc," ]
										    [#list articles as article]
										    	[#if article.isTop==true]
										<TR>
								          <TD align=left>
								          	<A title=${article.title} href="${base}/gw/article/plist/${article.id}.jhtml"">${article.title}</A>
								          </TD>
								         </TR>
								         		[/#if]
										    [/#list]
										   [/@article_list]
							       </TBODY>
						       </TABLE>
			       			</TD>
				[/#list]
			       			<!--
					    [#if !a_has_next]
					    <TD vAlign=top width=40 align=center>
					    	<IMG alt="" src="${base}/resources/gw/images/xt2.jpg" width=1 height=103>
					    </TD>
					    [/#if]
					    -->
				</tr>
			</tbody>
		</table>
	[/@article_category_children_list]
</SPAN>

  
  <SPAN>
	<TABLE class=nav_zcd cellSpacing=0 cellPadding=0 width=890 align=center border=0>
	  <TBODY>
	  <TR>
	     <TD width=370 align=right>
	    	<A title=万家乐在线服务 href="${base}/gw/serviceOnline/repair.jhtml">
		    	<IMG alt=万家乐在线服务 src="${base}/resources/gw/images/nav_01.jpg"width=110 height=80>
				<p>在线服务</p>
			</A>
		 </TD>
		  
	    	<!--<A title=服务政策 href="${base}/gw/serviceOnline/servicePolicy.jhtml">-->
	    <TD height=30 width=150 align=center>
	    	<A title=服务政策 href="${base}/gw/article/glist/838.jhtml">
	      	<IMG alt=服务政策 src="${base}/resources/gw/images/nav_02.jpg" width=110 height=80>
			<p>服务政策</p>
	      	</A>
	    </TD>
		
	    <TD width=370 align=left><A title=服务收费 
	      href="${base}/gw/article/glist/721.jhtml"><IMG alt=服务收费 
	      src="${base}/resources/gw/images/nav_03.jpg" 
	      width=110 height=80>
		  <p>服务收费</p>
		  </A></TD>
		  
		  <TD width=370 align=left><A title=产品说明书 
	      href="${base}/gw/serviceOnline/chanpinshuomingshu.jhtml"><IMG alt=产品说明书 
	      src="${base}/resources/gw/images/nav_04.jpg" 
	      width=110 height=80>
		  <p>产品说明书</p>
		  </A></TD>
		  
		  <TD width=370 align=left><A title=安检清洗服务标准 
	      href="${base}/gw/article/llist/181.jhtml"><IMG alt=安检清洗服务标准 
	      src="${base}/resources/gw/images/nav_05.jpg" 
	      width=110 height=80>
		  <p>安检清洗服务标准</p>
		  </A></TD>
		  
	      <!--href="${base}/gw/serviceOnline/commonProblemList.jhtml.jhtml"><IMG alt=常见问题-->
		  <TD width=370 align=left><A title=常见问题 
	      href="${base}/gw/article/llist/1109.jhtml"><IMG alt=常见问题  
	      src="${base}/resources/gw/images/nav_06.jpg" 
	      width=110 height=80>
		  <p>常见问题</p>
		  </A></TD>
		  
		  <TD width=370 align=left><A title=延保服务 
	      href="${base}/gw/article/glist/695.jhtml"><IMG alt=延保服务 
	      src="${base}/resources/gw/images/nav_07.jpg" 
	      width=110 height=80>
		  <p>延保服务</p>
		  </A></TD>
      </TR></TBODY></TABLE>
</SPAN>
	  

	 <span>
			<div class="nav_zcd jp_nav">
		<ul>
			 <li>
			<a href="${base}/gw/product/preferencex.jhtml?idname=y_">
		        <img src="/resources/gw/images/jptj_nav_04.png" />
		        <p>燃气热水器</p>
			</a>
		    </li>
		<li>
			<a href="${base}/gw/product/preferencex.jhtml?idname=d_">
		        <img src="/resources/gw/images/jptj_nav_02.png" />
		        <p>电热水器</p>
			</a>
		    </li>
		    <li>
			   <a href="${base}/gw/product/preferencex.jhtml?idname=yy_0">
		        <img src="/resources/gw/images/jptj_nav_07.png" />
		        <p>吸油烟机</p>
				</a>
		    </li>
		   <li>
			<a href="${base}/gw/product/preferencex.jhtml?idname=rr_0">
		        <img src="/resources/gw/images/jptj_nav_05.png" />
		        <p>燃气灶具</p>
			</a>	
		    </li>
		    <li>
			<a href="${base}/gw/product/preferencex.jhtml?idname=x_0">
		        <img src="/resources/gw/images/jptj_nav_06.png" />
		        <p>消毒柜</p>
			</a>	
		    </li>
		    <li>
			<a href="${base}/gw/product/preferencex.jhtml?idname=p_">
		        <img src="/resources/gw/images/jptj_nav_01.png" />
		        <p>壁挂炉</p>
			</a>
		    </li>
			   <li>
			   <a href="${base}/gw/product/preferencex.jhtml?idname=Q3-">
		        <img src="/resources/gw/images/jptj_nav_08.png" />
		        <p>空气净化器</p>
				</a>
		    </li>
		<!--
		    <li>
			<a href="${base}/gw/product/preferencex.jhtml?idname=k_">
		        <img src="/resources/gw/images/jptj_nav_03.png" />
		        <p>空气能热水器</p>
			</a>
		    </li>
		-->
		    <!--
		    [@product_category_root_list entcode="gwmacro" useCache="false"]
		      [#list productCategories as category]
		         [@product_category_children_list productCategoryId = category.id count = 7]
		             [#list productCategories as productCategory]
		               <li>
		                 <a href="${base}/gw/product/preferencex.jhtml?id=${productCategory.id}">
		                 	[#if productCategory.image??]<img src="${base}${productCategory.image}"/>[/#if]
		                 	<p>${productCategory.name}</p>
		                 </a>
		              </li>
		             [/#list]
		         [/@product_category_children_list]
		      [/#list]
		    [/@product_category_root_list]
		    -->
		    </ul>
		</div>
	</span>
	  
<SPAN>
<TABLE cellSpacing=0 cellPadding=0 align=center border=0>
  <TBODY>
  <TR>
    <TD height=30 width=150 align=center><A title=万家乐官方商城 
      href="http://shop.chinamacro.cn/" rel=nofollow target=_blank><IMG 
      alt=万家乐官方商城 
      src="${base}/resources/gw/images/nav_19.png" 
      width=146 height=146></A></TD>
    <TD width=150 align=center><A title=万家乐热水器天猫旗舰店 
      href="http://wanjiale.tmall.com/" rel=nofollow target=_blank><IMG 
      alt=万家乐热水器天猫旗舰店 
      src="${base}/resources/gw/images/nav_12.png" 
      width=117 height=117></A></TD>
    <TD width=150 align=center><A title=万家乐厨房电器天猫旗舰店 
      href="http://macrocfdq.tmall.com/" rel=nofollow target=_blank><IMG 
      alt=万家乐厨房电器天猫旗舰店 
      src="${base}/resources/gw/images/nav_12a.png" 
      width=117 height=117></A></TD>
    <TD width=150 align=center><A 
      href="http://macrogd.tmall.com/shop/view_shop.htm?spm=a220o.1000855.w5001-2914146632.2.hnnKRt&amp;&amp;scene=taobao_shop" 
      target=_blank><IMG alt=空气能热水器天猫旗舰店 
      src="${base}/resources/gw/images/20140611102535e5rcfa.png" 
      width=117 height=117></A></TD>
    <TD height=30 width=150 align=center><A title=万家乐生活电器天猫旗舰店 
      href="http://macrofs.tmall.com/shop/view_shop.htm?spm=0.0.0.0.3XjXk9" 
      rel=nofollow target=_blank><IMG alt=万家乐生活电器天猫旗舰店 
      src="${base}/resources/gw/images/nav_12_b.png" 
      width=117 height=117></A></TD>
    <TD height=150 width=150 align=center><A title=空气净化器天猫旗舰店 
      href="http://macrohh.tmall.com/" rel=nofollow target=_blank><IMG 
      alt=空气净化器天猫旗舰店 
      src="${base}/resources/gw/images/nav_13_c.png" 
      width=117 height=117></A></TD>
    <TD width=150 align=left><A title=万家乐网购声明 
      href="${base}/gw/article/glist/505.jhtml"><IMG alt=万家乐网购声明 
      src="${base}/resources/gw/images/nav_11.png" 
      width=117 
height=117></A></TD></TR></TBODY></TABLE></SPAN>

   

    </DIV></DIV></DIV></DIV>

<!-- 头部结束 --> 
</body>
</html>