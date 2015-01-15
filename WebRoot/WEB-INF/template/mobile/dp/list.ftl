<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>线下实体店</title>
    <link href="${base}/resources/mobile/css/common.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${base}/resources/mobile/css/xxtyd.css">
	<script type="text/javascript" src="${base}/resources/mobile/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/resources/mobile/js/common.js"></script>
	<script type="text/javascript">
 		$().ready(function() {
 			var $province = $("#province");
 			var $select_province = $("#select_province");
    		var $select_city = $("#select_city");
    		var $submit = $("#submit");
			var $cityId = $("#cityId");
			var $provinceId = $("#provinceId");
			var $storeForm = $("#storeForm");
			var $submit = $("#submit");
    		
 			[#if provinceId??]
		    	$select_province.val(${provinceId});
				$.ajax({
						url: "${base}/area/listbyparent.jhtml?areaId=${provinceId}",  
						type: "POST",
						dataType: "json",    
						cache: false,    
						success: function(data) {
							for(var i=0;i<data.length;i++){ 
								$("#select_city").append("<option value="+data[i].id+">"+data[i].name+"</option>");  
							}
		                    $select_city.val(${cityId});
						}  
				}); 
		 	[/#if]
		 	$select_province.change(function(){
				$select_city.empty();
		        $cityId.val("");  
		        $select_city.append("<option value=''>请选择城市</option>");
				$.ajax({
					url: "${base}/area/listbyparent.jhtml?areaId="+$select_province.val(),
					type: "POST",
					dataType: "json",  
					cache: false,  
					success: function(data) {
			           	var html = "";
						for(var i=0;i<data.length;i++){ 
							html += "<option value="+data[i].id+">"+data[i].name+"</option>";  
						}
			            $select_city.append(html);
					}  
				});
			});
			
			$submit.click(function(){
				if($select_province.val() == "" || $select_city.val() == "") {
					return;
				}
				$cityId.val($select_city.val());
				$provinceId.val($select_province.val());
				$storeForm.submit();
			});
 		});
	</script>
</head>
<body>
	[#include "/mobile/include/header.ftl" /]
	<form id="storeForm" action="${base}/mobile/store/list.jhtml" method="get">
    <div class="czxxdq">
        <div class="czxxd">
            <div class="qxz">
            	<input type="hidden" id="cityId" name="cityId" value="${cityId}" />
            	<input type="hidden" id="provinceId" name="provinceId" value="${provinceId}" />
                <select class="xzlb" id="select_province">
                    <option value="">请选择省份</option>
                	[#list provinceList as province]
                		<option value="${province.id}">${province.fullName}</option>
                	[/#list]
                </select>
            </div>
            <div class="qxz">
                <select class="xzlb" id="select_city">
                    <option value="">请选择城市</option>
                </select>
            </div>
            <div class="result">
            	</span>为您找到<span class="shuliang">${page.total}</span>家实体店
            </div>
            <div class="chax">
                <a href="javascript:void(0)" id="submit">查询</a>
            </div>
        </div>
    </div>
    <!--搜索结果        搜索结果-->
    <div class="ssjg">
        <ul>
        	[#list page.content?chunk(4) as row]
	        	[#list row as store]
	        		<a href="${base}/mobile/${store.id}.jhtml">
		        		[#if store_index == 0]<li style="border-top: 0px;">[/#if]
		        		<li>
			                <p class="dianm">${store.name}</p>
			                <p class="xiz">${store.areaName}${store.address}</p>
			                <p class="xiz">联系电话：${store.serviceTelephone}</p>
			            </li>
			        </a>
	        	[/#list]
        	[/#list]
		            
        </ul>
    </div>
    <!--搜索结果        搜索结果  end-->
    </form>
    [#include "/mobile/include/footer.ftl" /]
</body>
</html>
