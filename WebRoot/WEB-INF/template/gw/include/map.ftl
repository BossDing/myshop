<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ww2D6765Sit7sImQ6sLbIhyU"></script>
<title>up</title>
</head>

<body>
<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">

// 百度地图API功能
var map = new BMap.Map("allmap");
var point = new BMap.Point();
map.centerAndZoom(point,12);
// 创建地址解析器实例
var myGeo = new BMap.Geocoder();
// 将地址解析结果显示在地图上,并调整地图视野
[#if page.content?has_content]
[#list page.content?chunk(4) as row]
[#list row as service]
myGeo.getPoint("${service.serviceAddress}", function(point){
  if (point) {
    map.centerAndZoom(point, 13);
	var marker = new BMap.Marker(point);
    map.addOverlay(marker);
	marker.addEventListener('mouseover', function(){
					var content = "<p style='width:280px;margin:0;line-height:30px;font-size:12px'>"
									+"网点联系人：${service.serviceMan}<br/>"
									+"网点名称：${service.serviceName}<br/>"
									+"网点地址：${service.serviceAddress}<br/>"
									+"网点手机:${service.servicePhone}<br/>";
						var infoWindow1 = new BMap.InfoWindow(content);	
						this.openInfoWindow(infoWindow1);
					});
  }
}, "佛山市");
[/#list]
[/#list]
[/#if]

</script>