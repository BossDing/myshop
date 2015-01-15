<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
[@seo type = "productSearch"]
	<title>${productCategory.name}_万家乐官方网站</title>
	<META name=Keywords content=${productCategory.name}>
	<META name=Description content=${productCategory.seoDescription}>
[/@seo]
<link href="${base}/resources/gw/css/all_css.css" rel="stylesheet" type="text/css" />
    <SCRIPT language=javascript type=text/javascript src="${base}/resources/gw/js/all_js_index.js"></SCRIPT>
</head>
<body>

	[#include "/gw/include/header.ftl" /]
	<DIV class=ban4>
<DIV id=bn><SPAN class=tu>[@ad_position adname="官网 - ${productCategory.name}banner图"/]</SPAN></DIV></DIV>
<DIV class=cpzx>
<DIV class=cpzx_div>
<DIV class=cp_dq>目前您在：<A href="/gw/index.jhtml">首页</A> &gt; 
<H1 style="FONT-SIZE: 14px; FONT-WEIGHT: normal; DISPLAY: inline">
	[#if productCategory.id=1079]
		<a href="http://www.chinamacro.cn/reshui/">${productCategory.name}</a>
	[#elseif productCategory.id=1089]
		<a href="http://www.chinamacro.cn/gongnuan/">${productCategory.name}</a>
	[#else]
		<a href="${base}/gw/product/list/${productCategory.id}.jhtml">${productCategory.name}</a>
	[/#if]
</H1></DIV>
<DIV>
<TABLE cellSpacing=0 cellPadding=0 width=1000 border=0>
  <TBODY>
  <TR>
    <TD width=1000>
      <DIV class=cpzx_bt>[#if productCategory.name?index_of("产品")>-1]${productCategory.name}[#else]${productCategory.name}产品[/#if]</DIV>
    </TD>
  </TR>
  <TR>
    <TD>
      <TABLE class=qn cellSpacing=0 cellPadding=0 width="100%" border=0>
		<TBODY>
			<TR>
				[#list childrenpCategory?chunk(5) as row   ]
					[#assign rows = 0 /]
					[#list row as p  ]
						[#assign rows = rows+1 /]
						[#if rows<=6  ]
							[#if productCategory.id==1101]
								<td height="504" align="left" valign="bottom">
									<a href="${base}/gw/product/plist/${p.id}.jhtml"><img alt="" width="161" height="484" src="${base}${p.image}" /></a>
								</td>
							[#else]
								<TD vAlign=middle width="25%" align=center >
								    <DIV class=qn_lb>
									    <DIV class=qn_lb_t>
									    	[#if p.id=1080]
												<a href="http://www.chinamacro.cn/reshui/ranqi/"><IMG alt=${p.name} src="${base}${p.image}" width=150 height=150></a>
											[#elseif p.id=1081]
												<a href="http://www.chinamacro.cn/reshui/kongqi/"><IMG alt=${p.name} src="${base}${p.image}" width=150 height=150></a>
											[#elseif p.id=1086]
												<a href="http://www.chinamacro.cn/chufang/chouyou/"><IMG alt=${p.name} src="${base}${p.image}" width=150 height=150></a>
											[#elseif p.id=1087]
												<a href="http://www.chinamacro.cn/chufang/ranqizao/"><IMG alt=${p.name} src="${base}${p.image}" width=150 height=150></a>
											[#elseif p.id=1090]
												<a href="http://www.chinamacro.cn/gongnuan/bigualu/"><IMG alt=${p.name} src="${base}${p.image}" width=150 height=150></a>
											[#else]
											 	<a href="${base}/gw/product/plist/${p.id}.jhtml"><IMG alt=${p.name} src="${base}${p.image}" width=150 height=150></a>
											[/#if]
									    </DIV>
								    	<DIV class=qn_lb_w>${p.name}</DIV>
								    </DIV>
							    </TD>
						    [/#if]
					    [/#if]
					[/#list]
				[/#list]
			</TR>
		</TBODY>
       </TABLE>
      </TD>
   </TR>
       
       [@article_list useCache=false name="官网 - ${productCategory.name} 科学划分空间" articleCategoryId=1 tagIds=1 ]
			[#list articles as a]
			${a.content}
			[/#list ]  
		[/@article_list]
  <TR>
    <TD>
      <DIV class=cxjs>${productCategory.name}创新技术</DIV>
      </TD>
  </TR>
  <TR>
    <TD>
    	[@ad_position adname="官网 - ${productCategory.name} 创新技术"/]
     </TD>
    </TR>
    </TBODY>
    </TABLE>
    </DIV>
<DIV class=xptj>
<DIV class=rsq_bt>${productCategory.name}新品推荐</DIV>
<DIV>
[@product_list productCategoryId = productCategory.id tagIds = 6   count=6 px=true]
	[#list products as p]
	   <DIV class=cplb2>
		<DIV class=cplb_tu2>

		<A href="${base}${p.gwPath}"><IMG 
		alt=${p.name} src="${base}${p.image}" 
		width=180 height=180></A> </DIV>
		<DIV class=cplb_mc><A 
		href="${base}${p.path}">${p.name}</A></DIV>
		<DIV class=cplb_ts>${p.seoTitle}<BR>${p.seoKeywords}<BR>${p.seoDescription}</DIV></DIV>			
	 [/#list]
[/@product_list]

</DIV></DIV>

<DIV class=rxcp>
<DIV class=rsq_bt>${productCategory.name}热销产品</DIV>
[@product_list productCategoryId = productCategory.id tagIds = 8  count=7 px1=true]
	[#list products as p]
	   <DIV class=rxcp_lb>
			<TABLE cellSpacing=0 cellPadding=0 width=305 border=0>
				  <TBODY>
					  <TR>
						    <TD height=85 width=120 align=center>
						    	<A href="${base}${p.gwPath}"><IMG alt=${p.name} src="${base}${p.image}" width=57 height=57></A>
						    </TD>
						    <TD width=185>
						      	<P>${p.name}</P>
						    </TD>
					   </TR>
					</TBODY>
			</TABLE>
		</DIV>		
	 [/#list]
[/@product_list]
</DIV></DIV></DIV>

	[#include "/gw/include/footer.ftl" /]
</body>
</html>