<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/shop/css/wjl_indiv_public.css"/>
<script type="text/javascript" src="${base}/resources/shop/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/js/common.js"></script>

<title>公告详情</title>
<script type="text/javascript">

function lookList(id,titleName){
$("#aaa"+id).show().siblings("div").hide();
$("#aaa"+id).siblings("div:first").show();
$("#titleName").html(titleName);
$("#infor_1").show();
$("#infor_2").hide();
}
</script>
</head>

<body>
[#include "/shop/include/header.ftl" /]
<!--页面追踪导航-->
   <div class="where-big">
	  <div id="index_place" style="width:400px;float:left">您的位置：<span><a href="/">首页</a></span>&gt;<span><a href="/member/index.jhtml">个人中心</a></span><span><a href="/member/notice/list.jhtml">商城公告</a></span></div>
		  <div class="kfdlb">
			  
		  			</div>   
				</div>
<div class="content">
<!--左边导航-->
   <div class="indiv_nav">
        <dl>
							<dt>
								站内公告
							</dt>
							[#list page.content as notice]
								<dd>
									<a href="${notice.id}.jhtml" >${notice.theme}</a>
								</dd>
						    [/#list]
						</dl>
        </div>
        <div class="collect_infor" id="infor_2">
            <div class="title1">站内公告</div>
            <div class="title2">${notice.theme} </div> 
            <div class="time_ff">
               <div class="ti1">发表时间：${notice.noticeDate}</div>
               
            </div>
            <div class="max_infor">
               
               ${notice.content}
               
               </div>
            </div>
         </div>     
        
       <div id="clear"></div>
       </div>
        [#include "/shop/include/footer.ftl" /]
 </body>
</html>
