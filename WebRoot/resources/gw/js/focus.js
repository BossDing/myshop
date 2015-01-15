pHPSetupFunction=function(){

	jQuery("#p-top-banner .p-home-banner").css("cursor","pointer");

	jQuery("#p-top-banner .p-home-banner").click(function(M){
		window.location=jQuery(this).find("a").attr("href")
	});
	
	jQuery("#p-top-banner .p-bannerqh").css("width","20000em");
	
	jQuery.easing.custom=function(N,O,M,Q,P){
		if(O==0){return M}
		if(O==P){return M+Q}
		if((O/=P/2)<1){return Q/2*Math.pow(2,8*(O-1))+M}
		return Q/2*(-Math.pow(2,-10*--O)+2)+M
	};
	
	jQuery("#p-top-banner .p-top-qh-pane,#p-top-banner .p-top-qh-pane .p-home-banner").width(jQuery("#p-top-banner").width());

	var L=navigator.platform.match(/iPad/i)!=null;

	if(L){
		var K=jQuery("#p-top-banner .p-top-qh-pane").scrollable({easing:"custom",speed:2000,circular:true}).navigator().handleSwipes()
	}else{
		var K=jQuery("#p-top-banner .p-top-qh-pane").scrollable({easing:"custom",speed:2000,circular:true}).navigator().autoscroll({interval:6000})
	}
	
	window.api=K.data("scrollable");

	jQuery(".p-top-qh-nav a").append('<div class="p-top-qh-view"><div class="p-banner-small-alt"></div></div>');
	
	var E=jQuery("#p-top-main #p-banner-1 img:first").attr("alt");
	var H=jQuery("#p-banner-1 p em:first").clone();
	
	jQuery("span",H).remove();
	
	jQuery(".p-top-qh-nav a:nth-child(1) .p-top-qh-view").addClass("p-banner-small-1").children("div.p-banner-small-alt").prepend('<span class="p-banner-small-1"></span><h2>'+E+"</h2><p>"+H.text()+"</p>");
	
	var C=jQuery("#p-top-main #p-banner-2 img:first").attr("alt");
	var G=jQuery("#p-banner-2 p em:first").clone();
	
	jQuery("span",G).remove();
	jQuery(".p-top-qh-nav a:nth-child(2) .p-top-qh-view").addClass("p-banner-small-2").children("div.p-banner-small-alt").prepend('<span class="p-banner-small-2"></span><h2>'+C+"</h2><p>"+G.text()+"</p>");
	
	var B=jQuery("#p-top-main #p-banner-3 img:first").attr("alt");
	var D=jQuery("#p-banner-3 p em:first").clone();

	jQuery("span",D).remove();
	jQuery(".p-top-qh-nav a:nth-child(3) .p-top-qh-view").addClass("p-banner-small-3").children("div.p-banner-small-alt").prepend('<span class="p-banner-small-3"></span><h2>'+B+"</h2><p>"+D.text()+"</p>");
	
	var Ea=jQuery("#p-top-main #p-banner-4 img:first").attr("alt");
	var Ha=jQuery("#p-banner-4 p em:first").clone();
	
	jQuery("span",Ha).remove();
	
	jQuery(".p-top-qh-nav a:nth-child(4) .p-top-qh-view").addClass("p-banner-small-4").children("div.p-banner-small-alt").prepend('<span class="p-banner-small-4"></span><h2>'+Ea+"</h2><p>"+Ha.text()+"</p>");
	
	var Ca=jQuery("#p-top-main #p-banner-5 img:first").attr("alt");
	var Ga=jQuery("#p-banner-5 p em:first").clone();
	
	jQuery("span",Ga).remove();
	jQuery(".p-top-qh-nav a:nth-child(5) .p-top-qh-view").addClass("p-banner-small-5").children("div.p-banner-small-alt").prepend('<span class="p-banner-small-5"></span><h2>'+Ca+"</h2><p>"+Ga.text()+"</p>");
	
	var Ba=jQuery("#p-top-main #p-banner-6 img:first").attr("alt");
	var Da=jQuery("#p-banner-6 p em:first").clone();

	jQuery("span",Da).remove();
	jQuery(".p-top-qh-nav a:nth-child(6) .p-top-qh-view").addClass("p-banner-small-6").children("div.p-banner-small-alt").prepend('<span class="p-banner-small-6"></span><h2>'+Ba+"</h2><p>"+Da.text()+"</p>");
	
	var Baa=jQuery("#p-top-main #p-banner-7 img:first").attr("alt");
	var Daa=jQuery("#p-banner-7 p em:first").clone();

	jQuery("span",Daa).remove();
	jQuery(".p-top-qh-nav a:nth-child(7) .p-top-qh-view").addClass("p-banner-small-7").children("div.p-banner-small-alt").prepend('<span class="p-banner-small-7"></span><h2>'+Baa+"</h2><p>"+Daa.text()+"</p>");
	
	var Bab=jQuery("#p-top-main #p-banner-8 img:first").attr("alt");
	var Dab=jQuery("#p-banner-8 p em:first").clone();

	jQuery("span",Dab).remove();
	jQuery(".p-top-qh-nav a:nth-child(8) .p-top-qh-view").addClass("p-banner-small-8").children("div.p-banner-small-alt").prepend('<span class="p-banner-small-8"></span><h2>'+Bab+"</h2><p>"+Dab.text()+"</p>");
	
	var Bab=jQuery("#p-top-main #p-banner-9 img:first").attr("alt");
	var Dab=jQuery("#p-banner-9 p em:first").clone();

	jQuery("span",Dab).remove();
	jQuery(".p-top-qh-nav a:nth-child(9) .p-top-qh-view").addClass("p-banner-small-9").children("div.p-banner-small-alt").prepend('<span class="p-banner-small-9"></span><h2>'+Bab+"</h2><p>"+Dab.text()+"</p>");
	
	if(jQuery.browser.msie){
		
		jQuery(".p-top-qh-nav a:nth-child(1)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","block")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","none")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(2)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","block")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","none")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(3)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","block")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","none")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(4)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","block")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","none")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(5)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","block")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","none")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(6)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","block")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","none")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(7)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","block")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","none")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(8)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","block")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","none")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(9)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","block")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").css("display","none")
		})
		
	}else{
		
		jQuery(".p-top-qh-nav a:nth-child(1)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeIn("fast")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeOut("fast")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(2)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeIn("fast")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeOut("fast")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(3)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeIn("fast")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeOut("fast")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(4)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeIn("fast")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeOut("fast")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(5)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeIn("fast")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeOut("fast")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(6)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeIn("fast")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeOut("fast")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(7)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeIn("fast")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeOut("fast")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(8)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeIn("fast")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeOut("fast")
		});
		
		jQuery(".p-top-qh-nav a:nth-child(9)").mouseenter(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeIn("fast")
		}).mouseleave(function(){
			jQuery(this).children().children("div.p-banner-small-alt").stop(true,true).fadeOut("fast")
		})
	}
		   
	var I;
	jQuery(window).resize(function(){

		if(!L){
			if(I){
				clearTimeout(I);
				I=null
			}
		}
		
		if(jQuery("#p-top-banner .p-bannerqh").is(":not(animated)")){
			if(!L){
				api.stop()
			}
			var aa=-jQuery("#p-top-banner").width();
			var bb=-jQuery("#p-top-banner").width();
			var cc=-jQuery("#p-top-banner").width();
			var dd=-jQuery("#p-top-banner").width();
			var ee=-jQuery("#p-top-banner").width();
			var ff=-jQuery("#p-top-banner").width();
			var gg=-jQuery("#p-top-banner").width();
			var hh=-jQuery("#p-top-banner").width();
			var ii=-jQuery("#p-top-banner").width();
			
			if(jQuery("#p-top-banner #p-banner-1").hasClass("p-selected-view")){
				jQuery("#p-top-banner .p-bannerqh").css("left",aa)
			}if(jQuery("#p-top-banner #p-banner-2").hasClass("p-selected-view")){
				jQuery("#p-top-banner .p-bannerqh").css("left",bb)
			}if(jQuery("#p-top-banner #p-banner-3").hasClass("p-selected-view")){
				jQuery("#p-top-banner .p-bannerqh").css("left",cc)
			}if(jQuery("#p-top-banner #p-banner-4").hasClass("p-selected-view")){
				jQuery("#p-top-banner .p-bannerqh").css("left",dd)
			}if(jQuery("#p-top-banner #p-banner-5").hasClass("p-selected-view")){
				jQuery("#p-top-banner .p-bannerqh").css("left",ee)
			}if(jQuery("#p-top-banner #p-banner-6").hasClass("p-selected-view")){
				jQuery("#p-top-banner .p-bannerqh").css("left",ff)
			}if(jQuery("#p-top-banner #p-banner-7").hasClass("p-selected-view")){
				jQuery("#p-top-banner .p-bannerqh").css("left",gg)
			}if(jQuery("#p-top-banner #p-banner-8").hasClass("p-selected-view")){
				jQuery("#p-top-banner .p-bannerqh").css("left",hh)
			}if(jQuery("#p-top-banner #p-banner-9").hasClass("p-selected-view")){
				jQuery("#p-top-banner .p-bannerqh").css("left",ii)
			}
			
			jQuery("#p-top-banner .p-top-qh-pane,#p-top-banner .p-top-qh-pane .p-home-banner").width(jQuery("#p-top-banner").width());
			
			if(!L){
				I=setTimeout
				(function(){api.play()},1000)
			}
		}
		
	});

};

jQuery(pHPSetupFunction);