<!doctydde html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=0">
<title>水质报告</title>
<link href="${base}/resources/mobile/css/water_report.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
<script type="text/javascript">
$(function(){
		//判断当前是否存在用户
		if (!$.checkLogin()) {
			$.redirectLogin("${base}/mobile/member/index.jhtml", "${message("shop.common.mustLogin")}");
			return false;
		}
	 });
</script>
</head>

<body>
  <div class="top">
    <div class="return">
    	<a href="${base}/mobile/member/waterquality/check.jhtml"><div class="return"></div></a>
    </div>
  </div>
<div class="water_report">
<dl>
	[#if data.tds??]
    <dt>水质报告</dt>
    <dd>尊敬的用户：</dd>
    <dd >经过查询您所在小区水质</dd>
    <dd ><strong>TDS值为：<i>${data.tds}</i></strong></dd>
    <dd ><strong>余氯含量为：<i>${data.cl}</i>mg/l</strong></dd>
    <dd >TDS是指水中溶解性总固体含量。未经终端净化设备净化的自来水，TDS值越大，说明水中矿物质和杂质含量越多，值越低水质越纯净。
     余氯指水经过氯，单位mg／l。余氯浓度过高，易与水中有机酸反应，产生三氯甲烷等致癌物。(余氯值高的危害)
     </dd>
     [#else]
     	 <dd >
     	 	  <p id="error">
                       对不起！系统暂时没有您小区的水质数据！<br>
                	请致电<span style="color: #32beff;">400-800-1234</span>，我们将为您免费送测水笔。
              </p>
     	</dd>
     [/#if]
</dl>
 <dl>
     <dt>国际健康水的七大标准</dt>
    <dd>无污染，不含任何对人体有毒、有害及有异味的物质。</dd>
    <dd>水的硬度始终，一般为100mg/l左右(碳酸钙计)。</dd>
    <dd>含有人体健康所必须的适量矿物质。</dd>
    <dd>PH值呈弱碱性。</dd>
</dl>
</div>
</body>
</html>
