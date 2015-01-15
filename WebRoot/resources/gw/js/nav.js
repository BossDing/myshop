$a(document).ready(function(){
	var navshow=false;
	var li_id;
	
	$a("#itemMun").hide();
	$a("#nav_ul li").mouseover(function(){
		if(navshow==false)
		{
			$a("#itemMun").slideDown("50");
			navshow=true;
			li_id=this.id; 
			  var index_li=this.id.substring(3);
				   searchItem(index_li);
	     }else if(navshow==true)
			{
				if(li_id==this.id)
				{
				$a("#itemMun").slideUp("80");
				navshow=false;
				}else
				{  
				    li_id=this.id;
				   var index_li=this.id.substring(3);
				   searchItem(index_li);
				}
			}
		$a("#nav_ul li a").css({"color":"#000"});
		$a(this).children("a").css({"color":"#f00"});
		});
		$a("#head_div").hover(function(){}, function(){
			navshow=false;
				$a("#itemMun").slideUp("80");
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
				     }	if(index_li==6)
					 {
						 width="-500%";
				     }
					 $a("#navbox").animate({left:width},"slow");
				}
	
	});