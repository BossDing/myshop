<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title> - Powered By HOMA</title>
<meta name="author" content="HOMA Team" />
<meta name="copyright" content="HOMA" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {
	[@flash_message /]
	
	
	var $inputSerialSignInTimes=$("input[name='serialSignInTimes']");
	var $inputPoint=$("input[name='point']");
	

	var serialSignInTimesValid=undefined;//连续签到次数是否验证通过。
	var pointValid=undefined;//积分是否验证通过
	
	//校验连续签到次数，验证通过后然后校验积分。
	function checkSerialSignInTimes(){
		serialSignInTimesValid=undefined;
		var reg=/^\d+$/;
		if(reg.test($inputSerialSignInTimes.val())){
			$.get("check_serialSignInTimes.jhtml?serialSignInTimes="+$inputSerialSignInTimes.val(),function(data){
					if(data==true){//校验通过
					$inputSerialSignInTimes.removeClass('fieldError')
					$inputSerialSignInTimes.addClass('valid');
					$inputSerialSignInTimes.next('label').html('');
					$inputSerialSignInTimes.next('label').attr('display','none');
					serialSignInTimesValid=true;
					checkPoint();
				}else{//校验不通过
					$inputSerialSignInTimes.removeClass('valid')
					$inputSerialSignInTimes.addClass('fieldError');
					$inputSerialSignInTimes.next('label').html('连续签到次数已存在');						
					$inputSerialSignInTimes.next('label').attr('display','inline');
					serialSignInTimesValid=false;
				}
			});
		}else{//不符合规则
			$inputSerialSignInTimes.removeClass('valid')
			$inputSerialSignInTimes.addClass('fieldError');
			$inputSerialSignInTimes.next('label').html('不允许小于0')
			$inputSerialSignInTimes.next('label').attr('display','inline');
			serialSignInTimesValid=false;
		}
	};
	//校验积分
	function checkPoint(){
		pointValid=undefined;
		var reg=/^\d+$/;
		if(reg.test($inputPoint.val())){
			if($inputSerialSignInTimes.hasClass('valid')){
				$.get("check_point.jhtml?serialSignInTimes="+$inputSerialSignInTimes.val()+"&point="+$inputPoint.val(),
					function(data){
						var dataArr=data.split(',');
						var min=dataArr[0];//min=-1表示积分没有最小限制
						var max=dataArr[1];//max=-1表示积分没有最大限制
						var point = parseInt($inputPoint.val());//转换成数字。
						if(min==-1&&max==-1||point>min&&(max!=-1?point<max:true)){
							$inputPoint.removeClass('fieldError');
							$inputPoint.addClass('valid');
							$inputPoint.next('label').html('')
							$inputPoint.next('label').attr('display','none');
							pointValid=true;
						}else{
							$inputPoint.removeClass('valid');
							$inputPoint.addClass('fieldError');
							$inputPoint.next('label').html((min!=-1?'不能小于'+min:'')+(max!=-1?'不能大于'+max:''));
							$inputPoint.next('label').attr('display','inline');
							pointValid=false;
						}
				});
			}else{
				$inputPoint.removeClass('fieldError');
				$inputPoint.addClass('valid');
				$inputPoint.next('label').html('')
				$inputPoint.next('label').attr('display','none');
				pointValid=false;
			}
		}else{//不符合规则。
			$inputPoint.removeClass('valid');
			$inputPoint.addClass('fieldError');
			$inputPoint.next('label').html('不允许小于0')
			$inputPoint.next('label').attr('display','inline');
			pointValid=false;
		}
	}
	
	
	$inputSerialSignInTimes.blur(function(){
		checkSerialSignInTimes();
	});
	
	$inputPoint.blur(function(){
		checkPoint();
	});
	
	
	$('#submit_button').click(function(){
		var timer = setInterval(function(){//每0.5秒验证一次，直到验证得到验证结果，如果验证正确立刻提交，用户只需点击一次。
    		var allValid=true;
    		if(serialSignInTimesValid == undefined){
    			checkSerialSignInTimes();
    			allValid=false;
    		}
    		if(pointValid==undefined){
    			checkPoint();
    			allValid=false;
    		}
    		if(allValid){
    			//无论是否可提交最后都要关掉定时器。
    			clearInterval(timer);
    			//如果所有都项目都验证了则判断是否可提交。
    			if(serialSignInTimesValid&pointValid){
        			$('#inputForm').submit();
    			}
    		}
    	},500);
	});
	
});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; 新增签到积分规则
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>连续签到次数:
				</th>
				<td>
					<input type="text" name="serialSignInTimes" value="" class="text" maxlength="100" />
					<label for="serialSignInTimes" class="fieldError"></label>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>奖励积分:
				</th>
				<td>
					<input type="text" name="point" value="" class="text" maxlength="100" />
					<label for="point" class="fieldError"></label>
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input id='submit_button' type="button" class="button" value="${message("admin.common.submit")}" />
					<input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml?orderProperty=serialSignInTimes&orderDirection=asc'" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>