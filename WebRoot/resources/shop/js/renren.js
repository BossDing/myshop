/* 注册送积分活动  */
$(document).ready(function() {
	var inviter_id=getUrlParam('inviter_id');
	if(inviter_id!=null){
		$.get("http://"+location.host+"/inviter/set_inviter_id.jhtml?inviter_id="+inviter_id); 
	}
})
function getUrlParam(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象	
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数	
	if (r!=null){
		return unescape(r[2]); 
	}
	return null;
}