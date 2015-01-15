$(document).ready(function(){
	var show=false;
	var li_id;
	
	$("#itemMun").hide();
	$("#nav_ul li").click(function(){
		if(show==false)
		{
			$("#itemMun").slideDown("50");
			show=true;
			li_id=this.id; 
			  var index_li=this.id.substring(3);
				   searchItem(index_li);
	     }else if(show==true)
			{
				if(li_id==this.id)
				{
				$("#itemMun").slideUp("80");
				show=false;
				}else
				{  
				    li_id=this.id;
				   var index_li=this.id.substring(3);
				   searchItem(index_li);
				}
			}
		$("#nav_ul li a").css({"color":"#000"});
		$(this).children("a").css({"color":"#000"});
		});
		$("#head_div").hover(function(){}, function(){
			show=false;
				$("#itemMun").slideUp("80");
			});
			function searchItem(index_li)
			{
				  var width; 
				     if(index_li==1)
					 {
						 width="2%";
				     }
					   if(index_li==2)
					 {
						 width="-100%";
				     }  if(index_li==3)
					 {
						 width="-200%";
				     }  if(index_li==4)
					 {
						 width="-300%";
				     }  if(index_li==5)
					 {
						 width="-400%";
				     }
					 $("#box").animate({left:width},"slow");
				}
	
	});