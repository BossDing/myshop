<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
	    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>所有商品</title>
	    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/common.css">
		<link rel="stylesheet" type="text/css" href="${base}/resources/dp/css/dpProductList.css">
	    <link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/allProductSort.css">
            
	   	<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
		<script type="text/javascript" src="${base}/resources/shop/js/jquery.validate.js"></script>
		<script type="text/javascript" src="${base}/resources/shop/js/jquery.lazyload.js"></script>
		<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
		<script type="text/javascript" src="${base}/resources/shop/js/jquery.lSelect.js"></script>
		<script type="text/javascript">
		$().ready(function() {
			var $productForm = $("#productForm");
			var $pageNumber = $("#pageNumber");  
			var $pageSize = $("#pageSize");
            
            var $previousPage = $("#previousPage");     
			var $nextPage = $("#nextPage");
            
			var $searchForm = $("#searchForm");
			var $keyword = $("#priceLeftKey");
			var $startPrice = $("#priceLeftMin");
			var $endPrice = $("#priceLeftMsx");
			var $cat_open = $(".cat_open");//分类展开、关闭按钮
		    /** 排序   */
			var $sort =  $("#sort input");
			var $orderType = $("#orderType");
		    
            $previousPage.click(function() {
				$pageNumber.val(${page.pageNumber - 1});
				$("#productForm").submit();
				return false;
			});
		
			$nextPage.click(function() {
				$pageNumber.val(${page.pageNumber + 1});
				$("#productForm").submit();
				return false;
			});
            
			/** 分页  */
			$.pageSkip = function(pageNumber) {
				$pageNumber.val(pageNumber);
				$("#productForm").submit();
				return false;
			};
			
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
		     
		    /**排序样式*/
			var ot =$orderType.val();
			if(null!=ot&&""!=ot){
			   for(var i=0;i<$sort.length;i++){
			      var obj =$sort[i];
			      if(ot==$(obj).attr("orderType")){
			      		if(ot=="dateDesc"||ot=="dateAsc"){
			      			$(obj).addClass("sequence_but1");
			      		}else{
				          $(obj).addClass("sequence_but");
			      		}
			         if(ot.lastIndexOf("Desc")>0){
			         	$(obj).css("background-image","url('${base}/resources/shop/images/soso-a.png')");
			         }else{
			         	$(obj).css("background-image","url('${base}/resources/shop/images/soso-b.png')");
			         }
			      }
			   }
			}
		    /**排序*/     
		    $sort.click(function(){
				var $this = $(this);
				var orderType = $this.attr("orderType");
				var newType = "";
				var descIndex = orderType.lastIndexOf("Desc");
				var ascIndex = orderType.lastIndexOf("Asc");
				if(descIndex>0){
					newType = orderType.substring(0,descIndex)+"Asc";
				}
				if(ascIndex>0){
					newType = orderType.substring(0,ascIndex)+"Desc";
				}
				$orderType.val(newType);
				$pageNumber.val(1);
				$productForm.submit();
				return false;
			});
		       	
		});
		</script>
	</head>

	<body id="comParamId" data-param="{&quot;globalPageCode&quot;:&quot;-1&quot;,&quot;currPageId&quot;:&quot;0&quot;}">
	    [#include "/dp/include/header2.ftl" /]
	<div class="inshop_wrap">
	    <div class="inshop_content">
	        <div class="inshop_layout clearfix">
	    		<div class="inshop_layout_colleft" data-tpa="STORE_CATEGORY_C1" id="C1" ref="mohe">
	                <!--左侧栏DSR-->
					<div id="DSRModuleId" class="inshop_DSR inshop_moudle inshop_sideBar mt10" modulename="DSR" data-tpc="2_744315"> 
	                    <!--title start-->
					    <div class="inshop_title">
					        <h2>店铺信息</h2>
					    </div>
					    <!--title end-->
					    <div class="inshop_DSRs clearfix">
					        <div class="certifyList_two certifyList clearfix">
                                [#if store.isAGImage ??]
                                	<a href="javascript:;" target="_blank" class="listOne"><span><img src="${store.isAGImage}"/></span></a>
                                    [#else]
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
	                            <a href="${store.indexUrl}">
						            <h1 class="clearfix">
		                            	<span>${store.name}</span>
		                            </h1>
		                        </a>
			            		<div class="inshop_hp clearfix">
				                	<span style="height: 22px; width: 56px;"></span>
				                	<span id="dsr_live800">
										<div>
											<span onclick="" id="onlineChatSpan" class="im_online_chat  webim_UUID12640265716626456" positionid="1" merchantid="2264" mcsite="3" ordercode="0">
												<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${store.qq}&site=qq&menu=yes"  class="onlines online_icon_mid" title="联系商家在线客服"></a>
											</span>
										</div>
									</span>
			            		</div>
				            	<div class="inshopDes_other inshopDescription">
					                <ul class="clearfix">
					                    <li class="clearfix">
				                    		<span class="inshopName">公司名称：</span><span class="inshopInformation">${store.companyName}</span>
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
	                    <form id="searchForm" action="list.jhtml" method="get">
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
	                
			     	<!--left产品列表(热卖榜单)start-->
				 	<div class="inshop_prolistLeft inshop_moudle inshop_sideBar mt10" modulename="热销产品" data-tpc="2_197295">
					    <!--title start-->
					    <div class="inshop_title">
							<h2>
								热销产品
							</h2>
						</div>
				    	<!--title end-->
					    <div class="inshop_prolistLefts inshop_con">
					        <!--列表循环-->
					        [#list hotProducts as product]
					    	<dl class="clearfix">
					        	<dd class="pro_pic">
					           		<a target="_blank" href="${product.path}">
					          			<img src="[#if product.productImages[0].source??]${product.productImages[0].source}[#else]${setting.defaultThumbnailProductImage}[/#if]"
                                               width="60" height="60" alt="${product.name}">
					           		</a>
					            </dd>
					            <dt>
					                <a target="_blank" href="${product.path}">${product.name}</a>
					            </dt>
					            <dd class="pro_price">
					                <strong id="productPrice_${product.id}" doneprice="true">${currency(product.price, true)}</strong>
					            </dd>
					    	</dl>
					        [/#list]
					    </div>
					</div>
					<!--left产品列表(热卖榜单)end-->
	                
					<!--商品分类left start-->
					<div class="inshop_catNav inshop_moudle inshop_sideBar mt10" id="storeModuleProductCategory" modulename="商品分类" data-tpc="2_166464">
					    <!--title start-->
						<div class="inshop_title">
					        <h2>商品分类</h2>
					    </div>
					    <!--title end-->
					    <div class="inshop_catNavs inshop_con">
					        <h4>
					            <sup class="cat_open"></sup>
					            <a href="list.jhtml?storeId=${store.id}">
					            	查看所有商品&gt;&gt;
					            </a>
					        </h4>
	                        
					        <!--主列表循环-->
					        [#list productCategories as productCategory]
	                        	[#if productCategory.grade == 0]
									<h4>
							            <sup class="cat_open"></sup> 
										<a href="list.jhtml?storeId=${store.id}&productCategoryId=${productCategory.id}">
									   		${productCategory.name}
										</a>
									</h4>
	                            	[#if productCategory.children??]
										<ul>
			                                [#list productCategory.children as children]
				                                [#if children.store?? && children.store.id == store.id]
										            <!--子 列表循环-->
													<li>
														<s></s>
														<a href="list.jhtml?storeId=${store.id}&productCategoryId=${children.id}">
													    	${children.name}
													 	</a>
											  		</li>
				                                [#elseif children.store == null]
				                                	<li>
														<s></s>
														<a href="list.jhtml?storeId=${store.id}&productCategoryId=${children.id}">
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
	                </div>
					<!--商品分类left end-->
				</div>
	            
	            <!-- 分类导航start-->
	            <div class="inshop_all_goods">
	                <h3>
	                    <span>
	                        <a href="list.jhtml?storeId=${store.id}">所有商品</a>
	                    </span>
	                    <span>
	                        [#if productCategory.id != null]
	                        	>>${productCategory.name}
	                        [/#if]
	                    </span>
	                     <span>
	                        [#if productCategory.children !=null]
	                        	[#if productCategory.children??]
									[#if children.store?? && children.store.id == store.id]
										>>${productCategory.children.name}
									[/#if]
								[/#if]
	                        [/#if]
	                    </span>
	                </h3>
	                [#if productCategory.id == null]
		                <ul class="clearfix">
				            [#list productCategories as productCategory]
				            	[#if productCategory.grade == 0]
		                    		<li>
										<a href="list.jhtml?storeId=${store.id}&productCategoryId=${productCategory.id}">
									   		${productCategory.name}
										</a>
		                            </li>
				            	[/#if]
					 		[/#list]
		                </ul>
	                [/#if]
	                [#if productCategory.id != null]
	                	[#if productCategory.children??]
							<ul>
								[#list productCategory.children as children]
									[#if children.store?? && children.store.id == store.id]
									<!--子 列表循环-->
									<li>
										<a href="list.jhtml?storeId=${store.id}&productCategoryId=${children.id}">
											${children.name}
										</a>
									</li>
									[#elseif children.store == null]
									<li>
										<a href="list.jhtml?storeId=${store.id}&productCategoryId=${children.id}">
											${children.name}
										</a>
									</li>
									[/#if]
								[/#list]
							</ul>
						[/#if]
	                [/#if]
	            </div>
	            <!-- 分类导航end-->
	            
	            <!--商品排序start-->
	            <div class="like_lit">
				 <div class="sequence" id="sort">
				     <span >排序方式</span>
				     <input class="sequence_but2" type="button" value="销量" [#if orderType == "salesAsc"] orderType="salesAsc" [#else] orderType="salesDesc" [/#if] />
				     <input class="sequence_but2" type="button" value="价格" [#if orderType == "priceAsc"] orderType="priceAsc" [#else] orderType="priceDesc" [/#if] />
				     <input class="sequence_but2" type="button" value="评价" [#if orderType == "scoreAsc"] orderType="scoreAsc" [#else] orderType="scoreDesc" [/#if] />
				     <input class="sequence_but2" type="button" value="上架时间" [#if orderType == "dateAsc"] orderType="dateAsc" [#else] orderType="dateDesc" [/#if]/>
				 </div>
				</div>
	            <!--商品排序end-->
	            
	            <!--商品列表start-->
	            <form id="productForm" action="${base}/dp/product/list.jhtml" method="get">
	                <input type="hidden" id="brandId" name="brandId" value="${(brand.id)!}" />
	                <input type="hidden" id="promotionId" name="promotionId" value="${(promotion.id)!}" />
	                <input type="hidden" id="orderType" name="orderType" value="${orderType}" />
		            <input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
					<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
	                <input type="hidden" id="storeId" name="storeId" value="${store.id}">
					<div class="mybe_like inshop_layout_colmain" data-tpa="STORE_CATEGORY_C31" id="C31" ref="mohe">
					    [#if page.content?has_content ]
					        <ul>
					        	[#list page.content?chunk(3) as row]
					            	[#list row as product]
					                <li>
										<a href="${product.path}">
					                        <div class="like_pic"><img src="[#if product.productImages[0].source??]${product.productImages[0].source}[#else]${setting.defaultThumbnailProductImage}[/#if]" width="100%" /></div>
					                    	<div class="like_name" title="${product.name}">${abbreviate(product.name, 58, "...")}</div>
					                     	<div class="like_tt">
										</a>
										<div class="like_price">${currency(product.price, true)}</div>
										<div class="like_com">评论<i>[#if product.scoreCount > 0]${product.scoreCount}[#else]0[/#if]</i>条</div>
										</div>
					                </li>
					                [/#list]
								[/#list]
					        </ul>
						[#else]
							${message("shop.product.noListResult")}
						[/#if] 
					</div>
		            [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
						[#include "/dp/include/pagination.ftl" /]
					[/@pagination]
	            </form>
	            <!--商品列表end-->
				<div class="inshop_layout_colmain" data-tpa="STORE_CATEGORY_C32" id="C32" ref="mohe">
				    <column id="C32"></column>
				</div>
			</div>
		</div>
	</div>
	[#include "/shop/include/footer.ftl" /]
	</body>
</html>