
/**视频播放js介绍：统一调用 videoPlayer(u,w,h,a,id); 
 * u: 视频地址 w：宽度 h：高度 a:是否自动播放  id：写入的位置
 *  如需对各类播放器自定义设置 可直接修改相应方法 mediaplayer格式的视频，
 *  rmvb等realPlayer格式的视频 必须在本机上安装realPlayer播放器，无法做到直接插件就能网页播放功能，调用pv_r()方法
 *  --zlh
 **/


/**视频播放入口*/
function videoPlayer(u,w,h,a,id){
  if(""!=u&&"undefined"!=u&&u.length>1){
     var type = u.split(".")[u.split(".").length-1];
	 if(!checkFileFormat(type.toLowerCase())){
	    alert("文件格式不支持");
		return false;
		}
	 if(type.toLowerCase() =="flv"){
	    pv_f(u,w,h,a,id);
		}else if(type.toLowerCase() =="rmvb"||type.toLowerCase() =="rm"||type.toLowerCase() =="ram"){
 		pv_r(u,w,h,a,id);
		}else{
		  pv_m(u,w,h,a,id);
		}
  }else{
    alert("找不到视频文件，请检查文件路径是否正确");
  }
}

/**校验文件格式*/
function checkFileFormat(filetype){
   var match ="mp4,avi,3gp,wmv,mpg,mpeg,swf,asf,wm,mp3,flv,rm,rmvb,ram";
   if(match.indexOf(filetype)<0) {
      return false;
	  }else{
       return true;
   }
}
/**
*u: 视频地址 w：宽度 h：高度 a:是否自动播放  id：写入的位置
*包括.avi .mpg .mpeg .wmv .wma .asf .mid .mp3等
*/



function pv_m(u, w, h,a,id){
var pv='';var start="";if(a){start="1";}else{start="0";}
pv += '<object width="'+w+'" height="'+h+'" id="iask_v" classid="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715" standby="Loading Microsoft Windows Media Player components..." type="application/x-oleobject">';
pv += '<param name="FileName" value="'+u+'">';
pv += '<param name="AutoStart" value="'+start+'">';
pv += '<param name="AutoSize" value="1">';
pv += '<param name="ShowControls" value="1">';
pv += '<param name="ShowPositionControls" value="0">';
pv += '<param name="ShowAudioControls" value="1">';
pv += '<param name="ShowTracker" value="1">';
pv += '<param name="ShowDisplay" value="0">';
pv += '<param name="ShowStatusBar" value="1">';
pv += '<param name="ShowGotoBar" value="0">';
pv += '<param name="ShowCaptioning" value="0">'; 
pv += '<param name="PlayCount" value="1">';
pv += '<param name="AnimationAtStart" value="0">';
pv += '<param name="TransparentAtStart" value="0">';
pv += '<param name="AllowScan" value="0">';
pv += '<param name="EnableContextMenu" value="0">';
pv += '<param name="ClickToPlay" value="0">';
pv += '<param name="InvokeURLs" value="1">';
pv += '<param name="DefaultFrame" value="">';
pv += '<embed src="'+u+'" width="'+w+'" height="'+h+'" type="application/x-mplayer2" pluginspage="http://www.microsoft.com/isapi/redir.dll?prd=windows&;sbp=mediaplayer&ar=media&sba=plugin&" name="MediaPlayer" showcontrols="1" showpositioncontrols="0" showaudiocontrols="1" showtracker="1" showdisplay="0" showstatusbar="1" autosize="0" showgotobar="0" showcaptioning="0" autostart="1" autorewind="0" animationatstart="0" transparentatstart="0" allowscan="1" enablecontextmenu="1" clicktoplay="0" invokeurls="1" defaultframe=""></embed>';
pv += '</object>';
$("#"+id).html(pv);
}

/**
* 播放.rm .ram .rmvb //流媒体视频 需要本机安装realPlayer播放器，否则网页无法播放该类视频
* 下载地址：http://www.realplayer.cn/
*/
function pv_r(u, w, h,a,id){
var pv='';var start="";if(a){start="1";}else{start="0";}
pv += '<object width="'+w+'" height="'+h+'" id="iask_v" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA">';
pv += '<param name="SRC" value="'+u+'">';
pv += '<param name="AUTOSTART" value="'+start+'">';
pv += '<param name="CONTROLS" value="Imagewindow,StatusBar,ControlPanel">';
pv += '<param name="_ExtentX" value="18415">';
pv += '<param name="_ExtentY" value="9102">';
pv += '<param name="SHUFFLE" value="0">';
pv += '<param name="PREFETCH" value="0">';
pv += '<param name="NOLABELS" value="0">';
pv += '<param name="CONSOLE" value="Clip1">';
pv += '<param name="LOOP" value="0">';
pv += '<param name="NUMLOOP" value="0">';
pv += '<param name="CENTER" value="0">';
pv += '<param name="MAINTAINASPECT" value="0">';
pv += '<param name="BACKGROUNDCOLOR" value="#000000">';
pv += '<embed src="'+u+'" width="'+w+'" height="'+h+'" type="audio/x-pn-realaudio-plugin" console="Clip1" controls="Imagewindow,StatusBar,ControlPanel" autostart="true" />';
pv += '</object>';
$("#"+id).html(pv);
}

/**播放flv swf等*/
function pv_f(u, w, h,a,id){
   var start="";if(a){start="true";}else{start="false";}
   var s5 = new SWFObject("FlvPlayer.swf","playlist",w,h,"7");
   s5.addParam("allowfullscreen","true");
   s5.addVariable("autostart",start);
   s5.addVariable("image","flashM-cebbank.jpg");//可以自行设置默认图片
   s5.addVariable("file",u);
   s5.addVariable("width",w);
   s5.addVariable("height",h);
   s5.addVariable("backcolor","0x000000");
   s5.write(id);  
}

