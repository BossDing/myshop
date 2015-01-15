<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;hidden;margin:0;}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=FPw9WPZ4pUAlATwfwO4wzpG9"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
<title>添加普通标注点</title>
</head>
<body>
<div id="allmap" style="overflow:hidden;zoom:1;position:relative;">	
    <div id="map" style="height:100%;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;"></div>
    <div id="showPanelBtn" style="position:absolute;font-size:14px;top:50%;margin-top:-95px;right:0px;width:20px;padding:10px 10px;color:#999;cursor:pointer;text-align:center;height:170px;background:rgba(255,255,255,0.9);-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;font-family:'微软雅黑';font-weight:bold;"><br/>&lt;</div>
    <div id="panelWrap" style="width:0px;position:absolute;top:0px;right:0px;height:100%;overflow:auto;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;">
        <div style="width:20px;height:200px;margin:-100px 0 0 -10px;color:#999;position:absolute;opacity:0.5;top:50%;left:50%;">此处用于展示结果面板</div>
        <div id="panel" style="position:absolute;"></div>
    </div>
</div>
</body>   
</html>
<script type="text/javascript">

// 百度地图API功能    
    var map = new BMap.Map('map');
    var poi = new BMap.Point(${experience.mapy},${experience.mapx});
    map.centerAndZoom(poi, 16);
    map.enableScrollWheelZoom();

    var content = '<div style="margin:0;line-height:20px;padding:2px;">' +
                    '<img src="${experience.image}" alt="" style="float:right;zoom:1;overflow:hidden;width:100px;height:100px;margin-left:3px;"/>' +
                    '地址：${experience.areaName}${experience.address}<br/>电话：${experience.phone}' +
                  '</div>';

    //创建检索信息窗口对象
    var searchInfoWindow = null;
	searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
			title  : "${experience.name}",      //标题
			width  : 290,             //宽度
			height : 105,              //高度  
			panel  : "panel",         //检索结果面板
			enableAutoPan : true,     //自动平移
			searchTypes   :[
				BMAPLIB_TAB_SEARCH,   //周边检索
				BMAPLIB_TAB_TO_HERE,  //到这里去
				BMAPLIB_TAB_FROM_HERE //从这里出发
			]
		});
    var marker = new BMap.Marker(poi); //创建marker对象
    marker.enableDragging(); //marker可拖拽
    marker.addEventListener("click", function(e){
	    searchInfoWindow.open(marker);
    })
    map.addOverlay(marker); //在地图中添加marker
    searchInfoWindow.open(marker); //在marker上打开检索信息串口
    $("close").onclick = function(){
        searchInfoWindow.close();
    }
    $("open").onclick = function(){
		var enableSendToPhone = false;
        if ($("enableSendToPhone").checked) {
            enableSendToPhone = true;
        }
		searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
			title  : "${experience.name}",      //标题
			width  : 290,             //宽度
			height : 105,              //高度
			panel  : "panel",         //检索结果面板
			enableAutoPan : true,     //自动平移
			enableSendToPhone: enableSendToPhone, //是否启用发送到手机
			searchTypes   :[
				BMAPLIB_TAB_SEARCH,   //周边检索
				BMAPLIB_TAB_TO_HERE,  //到这里去
				BMAPLIB_TAB_FROM_HERE //从这里出发
			]
		});
        if ($("enableAutoPan").checked) {
            searchInfoWindow.enableAutoPan();
        } else {
            searchInfoWindow.disableAutoPan();
        };
        searchInfoWindow.open(marker);
    }  
</script>
