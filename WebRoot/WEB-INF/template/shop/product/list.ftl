<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		[@seo type = "productSearch"]
			<title>商品列表</title>
			<meta name="author" content="SHOP++ Team" />
			<meta name="copyright" content="SHOP++" />
			[#if seo.keywords??]
				<meta name="keywords" content="[@seo.keywords?interpret /]" />
			[/#if]
			[#if seo.description??]
				<meta name="description" content="[@seo.description?interpret /]" />
			[/#if]
		[/@seo]
		<link href="${base}/resources/shop/css/common.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/type.css"/>
		<link href="${base}/resources/shop/css/product.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resources/shop/js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="${base}/resources/shop/js/jquery.lazyload.js"></script>
		<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>
		<script type="text/javascript">
			$().ready(function() {
				var $pageNumber = $("#pageNumber");
				var $productForm = $("#productForm");
				var $sort =  $("#sort input");
				var $orderType = $("#orderType");
				
				$.pageSkip = function(pageNumber) {
					$pageNumber.val(pageNumber);
					$productForm.submit();
					return false;
				}
				
				/**排序样式*/
				var ot =$orderType.val();
				if(null!=ot&&""!=ot){
				   for(var i=0;i<$sort.length;i++){
				      var obj =$sort[i];
				      if(ot==$(obj).attr("orderType")){
				      	//alert(ot);
				      		
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
					//alert(orderType);
					var newType = "";
					var descIndex = orderType.lastIndexOf("Desc");
					//alert(descIndex);
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
	<body>
	
		[#include "/shop/include/header.ftl" /]
	
		[#assign productnum = 0 /]
		[@product_list  productCategoryId=productCategory.id]
		[#if products?size > 5]
			[#assign productnum = 1 /]
		[/#if]
		[/@product_list]
	
	<DIV class=ban4>
	<DIV id=bn><SPAN class=tu>
	[@ad_position adname="商城 - ${productCategory.name}banner图"/]
	</SPAN></DIV>
	</DIV>
	
		<div class="where-big">
		  <div id="index_place" style="width:400px;float:left">您的位置：<span>首页</span>&gt;<span>商品分类</span>
		  [#if productCategory??]
					[@product_category_parent_list productCategoryId = productCategory.id]
						[#list productCategories as productCategory]
			            &gt;
			            <a href="${base}${productCategory.path}"><span>${productCategory.name}</span></a>
	            		[/#list]
					[/@product_category_parent_list]
		            	&gt;
		            	<a href="${base}${productCategory.path}"><span class="wdwz" >${productCategory.name}</span></a>
	            [/#if]
		  </div>
			 <div class="kfdlb">
				  <ul>
					  <li>分享到：</li>
					  <li><a href="#"><img src="/resources/shop/images/qq_ico.png" width="17px" ;=""></a></li>
					  <li><a href="#"><img src="/resources/shop/images/renren_ico.png" width="17px" ;=""></a></li>
					  <li><a href="#"><img src="/resources/shop/images/tx_weibo_ico.png" width="17px" ;=""></a></li>
					  <li><a href="#"><img src="/resources/shop/images/weibo_ico.png" width="17px" ;=""></a></li>
				      <li><a href="#"><img src="/resources/shop/images/xinxi_ico.png" width="17px" ;=""></a></li>
				  </ul>
			  			</div> 
					</div>
	    
	    
	<div class="content"> 
	   [#if productnum==1]
	   [@product_list tagIds =2  count = 5 productCategoryId=productCategory.id]
	      
	  <!--精品推荐-->
	  <div class="recommend">
	    <dl>
	      <dt>
	      		<!-- 首页精品推荐左侧大图 -->
	  
			[#list products as product]
			[#if product_index==0]
				        <div id="recommend_once">
				           <a href="${product.path}"><img src="${product.bigimage}" width="590" height="485" /></a>
				        </div>
				        <div id="recommend_once_text" style="padding-top:18px;padding-bottom:42px;">
				        <div id="text_one" style="height:auto;border-bottom:none;font:14px/1.5 Arial,'Microsoft YaHei';">
				        <strong>商品评论:</strong><br>
				        [@review_list productId = product.id count = 3]
							[#if reviews?has_content]
							   [#list reviews as review]
					          		<div class="text_one_w" style="font:12px/1.5 Arial,'Microsoft YaHei';border-bottom:solid 1px #ccc;padding:6px 0px;"><div class="score${(review.score * 2)?string("0")}"></div>
	&nbsp; &nbsp; &nbsp; &nbsp;${abbreviate(review.content, 140)}[#if (review.content?length)?? && review.content?length>70]...[/#if]<span style="padding:0px 20px; font-weight:bold;">${(review.member.username)!""} &nbsp; &nbsp; &nbsp;${review.createDate?string("yyyy-MM-dd")}</span></div>
					          [/#list]
					        [/#if]
						[/@review_list]
						</div>  
				        </div>
				        <div id="recommend_once_infor">   
					          <p class="model">${product.name}</p>
					          <p class="heading">${product.seoKeywords}</p>
					          <p class="price">￥${product.price}</p>
					          <div id="recommend_button">
					            	<a href="${product.path}"><input type="button" id="buy" /></a>
					            	<a href="${product.path}"><input type="button" id="know_more" /></a>
					          </div>
				        </div>      
					[/#if]
		     [/#list]
	     					
	      </dt>
	      
			[#list products as product]
			[#if product_index>0]
			    <dd>
			      <a title="${product.name}"  href="${product.path}"><img alt="${product.name}" src="${product.image}" />
					  <div class="recommend_twice">
						  <p class="model">${product.name}</p>
						  <p class="heading">${product.seoKeywords}</p>
						  <p class="price">￥${product.price}</p>
					  </div>
				  </a>
			    </dd>
			    [/#if]
			[/#list]
	    </dl>
	    </div>
	     [/@product_list]
	     
	    [/#if]
	     
	  
	  <!--精品推荐结束--> 
	  
	  
	  <!--<div class="title"><strong>智能浴系列</strong></div>
	
	     <div class="sift">
	        <ul>
	          <li>
	          <dl>
	          <dt>舒适:</dt>
	          <dd>
	              <span><a href="#">不限</a></span>
	              <span><a href="#">智能浴</a></span>
	              <span><a href="#">自动恒温</a></span>
	              <span><a href="#">手动调温</a></span>
	              </dd>
	              </dl>
	          <dl>
	          <dt>满足用水点:</dt>
	          <dd>
	              <span><a href="#">不限</a></span>
	              <span><a href="#">一卫</a></span>
	              <span><a href="#">两卫</a></span>
	              <span><a href="#">三卫</a></span>
	              </dd>
	          </dl>				
	          </li>
	          <li>
	          <dl>
	          <dt>出热水量kg/min:</dt>
	          <dd>
	              <span><a href="#">不限</a></span>
	              <span><a href="#">8</a></span>
	              <span><a href="#">10</a></span>
	              <span><a href="#">11</a></span>
	              <span><a href="#">12</a></span>  
	              <span><a href="#">13</a></span>
	              <span><a href="#">16</a></span>			
	              <span><a href="#">20</a></span>
	              </dd>
	              </dl>
	          <dl>
	          <dt>燃起种类:</dt>
	          <dd>
	              <span><a href="#">不限</a></span>
	              <span><a href="#">天然气</a></span>
	              <span><a href="#">液化气</a></span>
	              </dd>
	          </dl>
	          </li>
	          <li>
	          <dl>
	          <dt>安装位置:</dt>
	          <dd>
	              <span><a href="#">不限</a></span>
	              <span><a href="#">厨房</a></span>
	              <span><a href="#">阳台</a></span>
	              </dd>
	              </dl>
	              <dl>
	          <dt>能效等级:</dt>
	          <dd>
	              <span><a href="#">不限</a></span>
	              <span><a href="#">1级</a></span>
	              <span><a href="#">2级</a></span>
	              </dd>
	          </dl>
	          </li>
	          </ul>
	        </div>-->
	        
	        
	<div class="like_lit">
	 <div class="sequence" id="sort">
	     <span >排序方式</span>
	     <input class="sequence_but2" type="button" value="销量"   [#if orderType == "salesAsc"] orderType="salesAsc" [#else] orderType="salesDesc" [/#if] />
	     <input class="sequence_but2" type="button" value="价格" [#if orderType == "priceAsc"] orderType="priceAsc" [#else] orderType="priceDesc" [/#if] />
	     <input class="sequence_but2" type="button" value="评价" [#if orderType == "scoreAsc"] orderType="scoreAsc" [#else] orderType="scoreDesc" [/#if] />
	     <input class="sequence_but2" type="button" value="上架时间" [#if orderType == "dateAsc"] orderType="dateAsc" [#else] orderType="dateDesc" [/#if]/>
	     </div>
	      [@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "javascript: $.pageSkip({pageNumber});"]
		 <div class="page">
		     总共找到<i>${page.total}</i>个商品
		     &nbsp;&nbsp;
		     <span>${page.pageNumber}</span>/<span>${page.totalPages}</span>
		     &nbsp;&nbsp;
			[#if hasPrevious]
				<input class="last" type="button" value="&#8249;"  onclick="[@pattern?replace("{pageNumber}", "${previousPageNumber}")?interpret /]"/>
			[#else]
				<input class="last" type="button" value="&#8249;"  />
			[/#if]
		      
			[#if hasNext]
				<input class="next" type="button" value="下一页&nbsp;&#8250;" onclick="[@pattern?replace("{pageNumber}", "${nextPageNumber}")?interpret /]" />
			[#else]
				<input class="next" type="button" value="下一页&nbsp;&#8250;"  />
			[/#if]
		  </div>
	      [/@pagination]
	  </div>
	  
	  
	<div class="mybe_like">
		 <form id="productForm" action="${base}${(productCategory.path)!"/product/list.jhtml"}" method="get">
		    <input type="hidden" id="brandId" name="brandId" value="${(brand.id)!}" />
			<input type="hidden" id="promotionId" name="promotionId" value="${(promotion.id)!}" />
			<input type="hidden" id="orderType" name="orderType" value="${orderType}" />
			<input type="hidden" id="pageNumber" name="pageNumber" value="${page.pageNumber}" />
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
			[#if page.content?has_content ]
		        <ul>
		        	[#list page.content?chunk(5) as row   ]
		            	[#list row as product  ]
			                <li>
								<a href="${base}${product.path}">
		                            <div class="like_pic"><img src="${product.image}"/></div>
				                    <div class="like_name" title="${product.name}">${abbreviate(product.name, 58, "...")}</div>
				                    <div class="like_tt">
										<div class="like_price">${currency(product.price, true)}</div>
										<div class="like_com">评论<i>[#if product.scoreCount > 0]${product.scoreCount}[#else]0[/#if]</i>条</div>
									</div>
                                    ${product.store.name}
								</a>
		                         [#if product.store != null]  
		                         	<a href="${product.store.indexUrl}" target="_blank">${product.store.name}</a>
		                         [#else]
		                         	商城自营
		                         [/#if]
			                </li>
		                [/#list]
					[/#list]
		        </ul>
	       	[#else]
				${message("shop.product.noListResult")}
			[/#if] 
		</form>
	    </div>
	      <div class="title"><strong>燃气热水器创新技术</strong></div>
		  <div class="new">
		    <ul>
		      <li>
		        <div><img src="/resources/shop/images/type_js_p1.jpg"  /></div>
		        <div class="n_one">智能二代定温气控仪</div>
		        <div class="n_two">精控水温，大幅减少燃气花费</div>
		      </li>
		      <li>
		        <div><img src="/resources/shop/images/type_js_p2.jpg"  /></div>
		        <div class="n_one">智能自动分段燃烧－多级节能</div>
		        <div class="n_two">精确节省每分燃气</div>
		      </li>
		      <li>
		        <div><img src="/resources/shop/images/type_js_p3.jpg"  /></div>
		        <div class="n_one">防电墙技术</div>
		        <div class="n_two"></div>
		      </li>
		      <li>
		        <div><img src="/resources/shop/images/type_js_p4.jpg"  /></div>
		        <div class="n_one">三层潜压舱内胆</div>
		        <div class="n_two">潜艇级承压内胆，三重防护</div>
		      </li>
		    </ul>
		    
		  </div>
	
		[#include "/shop/include/footer.ftl" /]
	</body>
</html>