<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>${store.name}</title>
	<link rel="stylesheet" type="text/css" href="${base}/resources/dp/css/dpProductList.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/common.css"/>
   	<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
	<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
    <script type="text/javascript">
       $(function(){
			var $productForm = $("#productForm");
			var $pageNumber = $("#pageNumber");  
			var $pageSize = $("#pageSize");
			var $searchForm = $("#searchForm");
			var $keyword = $("#priceLeftKey");
			var $startPrice = $("#priceLeftMin");
			var $endPrice = $("#priceLeftMsx");
			var $cat_open = $(".cat_open");//分类展开、关闭按钮
		    
			/** 分页*/
			$.pageSkip = function(pageNumber) {
				$pageNumber.val(pageNumber);
				$productForm.submit();
				return false;
			}
			
			$searchForm.validate({
				rules: {
					keyword:"requiered",
					startPrice: {pattern: /^\d{0,8}$/},
					endPrice: {pattern: /^\d{0,8}$/}
				}
			});
		        
			/**
			 * icon click
			 */
			$('.inshop_catNav h4>sup').click(function(nextUl){
				var nextUl = $(this).parent().next('ul');
				if(!nextUl.is(":visible")){
					nextUl.show();
					$(this).addClass('cat_open').parent('h4').addClass('catNav_cur').siblings('h4').removeClass('catNav_cur');
					liclick(nextUl);
				}else if(nextUl.is(':visible')){
					nextUl.hide();
					$(this).removeClass('cat_open');
				}
			});
			/**
			 * title click
			 */
			$('.inshop_catNav h4>a').click(function(){
				var nextUl = $(this).parent().next('ul');
				nextUl.parent().find('li').removeClass('cat_cur');
				$(this).parent().addClass('catNav_cur').siblings('h4').removeClass();
		
				if(nextUl.length == 0 ){
					$('.inshop_catNav>ul').children('li').removeClass('cat_cur');
				}
				if(!nextUl.is(":visible")){
					nextUl.show();
					$(this).prev('sup').addClass('cat_open');
		
				}else if(nextUl.is(':visible')){
					nextUl.siblings('h4').removeClass();
					$(this).parent('h4').addClass('catNav_cur');
					nextUl.children('li').removeClass('cat_cur');
					liclick(nextUl);
				}
			});
			/**
			 * 子菜单点击
			 * 
			 * @param nextUl
			 */
			function liclick(nextUl){
				nextUl.children('li').click(function(){
					$(this).addClass('cat_cur').siblings().removeClass('cat_cur').parent(nextUl).siblings('ul').children('li').removeClass('cat_cur');
					$(this).parent(nextUl).siblings('h4').removeClass();
				});
			}
		        	
		});
	</script>
</head>

<body id="comParamId" data-param="{&quot;globalPageCode&quot;:&quot;-1&quot;,&quot;currPageId&quot;:&quot;0&quot;}">
	[#include "/dp/include/header.ftl" /]	
<div class="inshop_wrap">
    <div class="inshop_content">
        <div class="inshop_layout clearfix">
    		<div class="inshop_layout_colleft" data-tpa="STORE_CATEGORY_C1" id="C1" ref="mohe"><!--左侧栏DSR-->
				<div id="DSRModuleId" class="inshop_DSR inshop_moudle inshop_sideBar mt10" modulename="DSR" data-tpc="2_744315"> <!--title start-->
				     <div class="inshop_title">
					        <h2>店铺信息</h2>
					    </div>
				    <!--title end-->
				    <div class="inshop_DSRs clearfix">
				        <div class="certifyList_two certifyList clearfix">
				          	[#if store.isAGImage != null]
	                        	<a href="javascript:;" target="_blank" class="listOne"><span><img src="${store.isAGImage}"/></span></a>
	                        [/#if]
	                        [#if store.isAGImage == null]
					            <a href="javascript:void(0)" target="_blank" class="listOne"><sub></sub><span>正品保障</span></a>
	                        [/#if]
	                        [#if store.isCSImage != null]
	                        	<a href="javascript:void(0)" target="_blank" class="listThree"><span><img src="${store.isCSImage}"/></span></a>
	                        [/#if]
	                        [#if store.isCSImage == null]
					            <a href="javascript:void(0)" target="_blank" class="listThree"><sub></sub><span>假一赔三</span></a>
	                        [/#if]
				        </div>
                        <img src="http://qr.liantu.com/api.php?&w=120&logo=${setting.siteUrl}/images/logo.jpg&text=${store.indexMobileUrl}"/>
        				<div class="inshopInf">
				            <h1 class="clearfix">
                            	<span>${store.name}</span>
                            </h1>
		            		<div class="inshop_hp clearfix">
			                	<span style="height: 22px; width: 56px;"></span>
			                	<span id="dsr_live800">
									<div>
										<span onclick="" id="onlineChatSpan" class="im_online_chat  webim_UUID12640265716626456" positionid="1" merchantid="2264" mcsite="3" ordercode="0">
											<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${store.qq}&site=qq&menu=yes" class="onlines online_icon_mid" title="联系商家在线客服"></a>
										</span>
									</div>
								</span>
		            		</div>
			            	<div class="inshopDes_other inshopDescription">
				                <ul class="clearfix">
				                    <li class="clearfix">
				                    		<span class="inshopName">公司名称：</span>
                                            <span class="inshopInformation">${store.companyName}</span>
				                    </li>
				                    <li class="clearfix">
			                            <span class="inshopName">所在地：</span>
			                            <span class="inshopInformation" title="">${store.areaName}</span>
			                        </li>
				                   	<li class="clearfix">
			                        	<span class="inshopName">服务热线：</span>
			                            <span class="inshopInformation">${store.serviceTelephone}</span>
			                        </li>
				                </ul>
			            	</div>
        				</div>
    				</div>
  				</div>
				<!--热门搜索 start-->
				<div class="inshop_search inshop_moudle inshop_sideBar mt10" modulename="搜索商品" data-tpc="2_147092">
			    	<!--title start-->
			        <div class="inshop_title">
			            <h2>搜索商品</h2>
			        </div>
			        <!--title end-->
                    <form id="searchForm" action="${base}/dp/product/list.jhtml" method="get">
	            		<input type="hidden" id="pageNumber" name="pageNumber" value="1" />
						<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
                		<input type="hidden" id="storeId" name="storeId" value="${store.id}">
				        <div class="inshop_searchs inshop_con">
				            <p class="search_p clearfix">
				                <label for="">关键字：</label>
				                <input type="text" id="priceLeftKey" name="keyword" value="${keyword}" class="search_txtBox txt">
				            </p>
				            <p class="search_p clearfix">
				                <label for="">价格：</label>
				                <input id="priceLeftMin" maxlength="8" name="startPrice" value="${startPrice}"
                                    type="text" class="search_priBox txt">
				                <span class="pri_txt">到</span>
				                <input id="priceLeftMax" maxlength="8" name="endPrice" value="${endPrice}"
                                    type="text" class="search_priBox txt">
				            </p>
				            <p class="search_p">
				                <input class="btnFir inshopbtn" id="searchBtn" type="submit" value="搜索">
				            </p>
				        </div>
                	</form>
			    </div>
                <!--热门搜索 end-->
		    
				<!--商品分类left start-->
				<div class="inshop_catNav inshop_moudle inshop_sideBar mt10" id="storeModuleProductCategory" modulename="商品分类" data-tpc="2_166464"><!--商品分类left start-->
				    <!--title start-->
					<div class="inshop_title">
				        <h2>商品分类</h2>
				    </div>
				    <!--title end-->
				    <div class="inshop_catNavs inshop_con">
				        <h4>
				            <sup class="cat_open"></sup>
				            <a href="/dp/product/list.jhtml?storeId=${store.id}">
				            	查看所有商品&gt;&gt;
				            </a>
				        </h4>
				        <!--列表循环-->
				         [#list productCategories as productCategory]
                        	[#if productCategory.grade == 0]
								<h4>
						            <sup class="cat_open"></sup> 
									<a href="/dp/product/list.jhtml?storeId=${store.id}&productCategoryId=${productCategory.id}">
								   		${productCategory.name}
									</a>
								</h4>
                            	[#if productCategory.children??]
								<ul>
                                [#list productCategory.children as children]
                                [#if children.store?? && children.store.id == store.id]
						            <!--列表循环-->
									<li>
										<s></s>
										<a href="/dp/product/list.jhtml?storeId=${store.id}&productCategoryId=${children.id}">
									    	${children.name}
									 	</a>
							  		</li>
                                [#elseif children.store == null]
                                	<li>
										<s></s>
										<a href="/dp/product/list.jhtml?storeId=${store.id}&productCategoryId=${children.id}">
									    	${children.name}
									 	</a>
							  		</li>
                                [/#if]
                                [/#list]
								</ul>
                               	[/#if]
                        	[/#if]
				 		[/#list]
				    </div>
				
				<!--商品分类left end-->
                </div>
				<!--商品分类left end-->
			</div>
            <form id="productForm" action="${base}/store/product/list.jhtml" method="get">
	            <input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
                <input type="hidden" id="storeId" name="storeId" value="${store.id}">
               
				<div class="mybe_like inshop_layout_colmain" data-tpa="STORE_CATEGORY_C31" id="C31" ref="mohe">
					<div class="inshop_title">
				        <h2>店长推荐</h2>
				    </div>
                    [@product_list tagIds = 1 count = 10 storeid = "${store.id}"]
				        <ul>
				        	[#list products?chunk(4) as row]
                            [#list row as product]
				                <li>
									<a href="${product.path}">
				                        <div class="like_pic"><img src="${product.image}"  /></div>
				                    	<div class="like_name" title="${product.name}">${abbreviate(product.name, 58, "...")}</div>
									</a>
									<div class="like_price">${currency(product.price, true)}</div>
									<div class="like_com">评论<i>[#if product.scoreCount > 0]${product.scoreCount}[#else]0[/#if]</i>条</div>
				                </li>
				                [/#list]
							[/#list]
				        </ul>
                    [/@product_list]
		       </div>
               
	            [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
					[#include "/shop/include/pagination.ftl"]
				[/@pagination]
            </form>
			<div class="inshop_layout_colmain" data-tpa="STORE_CATEGORY_C32" id="C32" ref="mohe">
			    <column id="C32"></column>
			</div>
		</div>
	</div>
</div>
[#include "/shop/include/footer.ftl" /]
</body>
</html>