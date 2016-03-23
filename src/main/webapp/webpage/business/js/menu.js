$(document).ready(function(){
	//左边侧边栏
	$("#firstpane .menu_body:eq(0)").show();
		$("#firstpane h3.menu_head").click(function(){
			$(this).addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
			$(this).siblings().removeClass("current");
		});
		
		$("#secondpane .menu_body:eq(0)").show();
		$("#secondpane h3.menu_head").mouseover(function(){
			$(this).addClass("current").next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");
			$(this).siblings().removeClass("current");
		});
	// 左边导航最低高度
	var daohang_r = $(document.body).outerHeight(); //所有内容的高
	var	daohang_l = $(".l_left").outerHeight(); //div的高
	if(daohang_r > daohang_l){
		$(".l_left").css("height",daohang_r);	 //当右边所有内容的高高于左边导航的高的时候就调用这哥， 使左边导航的高为右边所有内容的高。
	}
	
});

