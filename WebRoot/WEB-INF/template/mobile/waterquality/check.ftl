<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<title>水质查询</title>
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<link href="${base}/resources/mobile/css/szcx.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
	$().ready(function() {
		
		var $areaId = $("#areaId");
		var $sel = $(".sel");
		var $return = $(".return");
		var $sel1 = $sel.eq(0);
		var $next;
		var $this1;
		$areaId.val($sel1.find("option:selected").text());
		var $so_but = $(".so_but");
		var $so_tex = $(".so_tex");
		
		//判断当前是否存在用户
		if (!$.checkLogin()) {
			$.redirectLogin("${base}/mobile/member/index.jhtml", "${message("shop.common.mustLogin")}");
			return false;
		}
		
		//水质查询
		$so_but.click(function way1(){
		var boo = !( $areaId.val()=="请选择省份"||$areaId.val()=="请选择城市"||$areaId.val()=="请选择城区"||$areaId.val()=="请选择小区" );
			if(boo  ){
				var a = $areaId.val();
				var b = $so_tex.val();
				var thisIndex = $sel.index($this1);
				var path = "${base}/mobile/member/waterquality/report.jhtml?areaId="+a;
				if(b != ""&b != null){
					path=path+"&so_tex="+b;
				}
				path = path+"&thisIndex="+thisIndex;
				window.location.href=path;
				
			}
		});
		
		//地区选择
			 $sel.change(function way2(){
			 $this1 = $(this);
			 $next = $this1.next();
			 var $nextIndex = $sel.index($next);
			 var $thisIndex = $sel.index($this1);
			 var $seledOption = $this1.find("option:selected");
			 $areaId.val($seledOption.text());
	
			 	
			 	var $nextAll = $this1.nextAll(".sel");
			 	for(i=$nextAll.length-1;i>=0;i--){
			 	
					$nextAll.eq(i).children("option").eq(0).nextAll().remove();		
			 	}
			 
			 
			
			if($thisIndex!=3&!($areaId.val()=="请选择省份"||$areaId.val()=="请选择城市"||$areaId.val()=="请选择城区"||$areaId.val()=="请选择小区")){
				
					$.ajax({
	    				url: "${base}/mobile/member/waterquality/areaList.jhtml",
	    				type: "get",
	    				data: {areaId:$areaId.val(),nextIndex:$nextIndex},
	    				dataType: "text",
	    				cache: false,
	    				success: function(kHtml){
	    				if(kHtml.length!=0||kHtml!=""||kHtml!=null){
	    				var xHtml = kHtml;	    				
	    				$next.append(kHtml);     				
						}
    					}
    				});
				}
			 });		
	});

	</script>
</head>

<body>
<div class="top">
  	<div >
    	<a href="${base}/mobile/index.jhtml"><div class="return"></div></a>
    </div>
  </div>
<div class="sz_diqu">
    <div class="tittle">健康饮水，从了解水开始！水质还能免费检测！</div>
      <div class="address"  id="addrForm">
      		[#assign n=1 /]
      	   <input type="hidden" id="areaId" name="areaId"/>
          <select name="areaId_select" class="sel" id="sel1">
             <option value="1">请选择省份</option>
             [#list roots as root]
             [#assign n=n+1 /]
				<option value="${n}">${root}</option>
			[/#list]
          </select>
          <select name="areaId_select" class="sel" id="sel2">
             <option value="1">请选择城市</option>
             
          </select>
           <select name="areaId_select" class="sel" id="sel3">
                 <option value="1">请选择城区</option>
                
              </select>
              <select name="areaId_select" class="sel" id="sel4">
                 <option value="1">请选择小区</option>
                 
              </select>
             
          </div>
      
      <div class="tittle">检索您居住的小区具体水质TDS值和余氯含量：</div>
      
      <div class="soso">
            <input type="text" class="so_tex" name="so_tex"/>
            <input type="button" class="so_but" value="搜索" />
      </div>
</div>       
        <div class="pic">
        
        </div>
</body>
</html>
