<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<title>邀请有奖</title>
<link href="${base}/resources/mobile/css/invitation.css" rel="stylesheet" type="text/css">
<link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.zclip.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>

<script type="text/javascript">	
	var jiathis_config;//jiathis配置
	$(document).ready(function() {
		
		//判断当前是否存在用户
		if (!$.checkLogin()) {
			$.redirectLogin("${base}/mobile/member/index.jhtml", "${message("shop.common.mustLogin")}");
			return false;
		}
				
		[@flash_message /]
		var url="http://"+location.host+"/?inviter_id="+${member.id};
		var summary="${promotion.shareSummary}";

	jiathis_config = { 
		url: url, 
		title: " ",
		summary:summary
	};
	

	 if(window.clipboardData){
		$('.reward_but').click(function(){
			window.clipboardData.setData('text', url);  
			alert("已成功复制到剪切板！");
		});
	 }else{
		$('.reward_but').zclip({
			path:'${base}/resources/shop/js/ZeroClipboard.swf',
			copy:function(){return url;},
			afterCopy:function(){alert("已成功复制到剪切板！");}
		});
	 }
	});
</script>
</head>
<body>
	[#include "/mobile/include/header.ftl" /]
    <div class="heard"></div>
    <div class="reward—pic">
      <img src="${base}/resources/mobile/images/js_pic.jpg" > 
    </div>
    <div class="reward_little">${promotion.title}</div>
<div class="reward_infor">
          <div class="reward_infor_centent">
			   <span>${promotion.introduction}</span></br>
			   <span>${promotion.shareSummary}</span>
              <p><a href="#">http://192.168.102.200:9003/?inviter_id=${member.id}</a></p>
          </div> 
            
</div>
        <div class="reward_bottom">
           <input type="button" class="reward_but" value="复制"/>
         </div> 
    
        	<!-- JiaThis Button BEGIN -->
					<div class="jiathis_style" >
						<span style="float:left;padding-left:69px">分享&nbsp</span>
						<a class="jiathis_button_qzone"></a>
						<a class="jiathis_button_tsina"></a>
						<a class="jiathis_button_tqq"></a>
						<a class="jiathis_button_weixin"></a>
						<a class="jiathis_button_renren"></a>
						<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
						<a class="jiathis_counter_style"></a>
					</div>
					<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>							
					<!-- JiaThis Button END -->	
			
</body>
</html>
