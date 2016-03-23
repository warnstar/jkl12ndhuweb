function close(){
	$('body').on('click','.close,.del',function(){
		   $('.theme-popover-mask').hide();
		   $('.theme-popover').slideUp(200);
	   })
}
function pop_show1(){
		$('.theme-popover-mask').show();
		$('.theme-popover-mask').height($(document).height());
		$('.theme1').slideDown(200);
	}
	$('.close,.del').click(function(){
		$('.theme-popover-mask').hide();
		$('.theme1').slideUp(200);
	})

function pop_show2(){
		$('.theme-popover-mask').show();
		$('.theme-popover-mask').height($(document).height());
		$('.theme2').slideDown(200);
	}
	$('.close,.del').click(function(){
		$('.theme-popover-mask').hide();
		$('.theme2').slideUp(200);
	})

function pop_show3(){
		$('.theme-popover-mask').show();
		$('.theme-popover-mask').height($(document).height());
		$('.theme3').slideDown(200);
	}
	$('.close,.del').click(function(){
		$('.theme-popover-mask').hide();
		$('.theme3').slideUp(200);
	});
function pop_show4(){
		$('.theme-popover-mask').show();
		$('.theme-popover-mask').height($(document).height());
		$('.theme4').slideDown(200);
	}
	$('.close,.del').click(function(){
		$('.theme-popover-mask').hide();
		$('.theme4').slideUp(200);
	});
   
	$(".close").click(function(){

	})

	/*点击排序按钮变色*/
	
	$(".h_sort").click(function(event) {
		 
		if(!$(this).hasClass('change'))
		{
			$(this).css({"background":"#4da801"});
			$(this).addClass('change');
		}else{
			$(this).css({"background":"#78cd51"});
			$(this).removeClass('change');
		}
		
	});

/*-----------------进度条begin------------*/
// 获取#in_schedule的宽度


//获取总需
var all = $("#all_num").text();
all = parseInt(all);
//获取剩余
var remainder = $("#remainder").text();
remainder = parseInt(remainder);
// 进度条宽度 = 总需-剩余
kuan = (parseInt(all-remainder)/all)*100;
// 设置宽度
$(".in_schedule").css({"width":kuan+"%"});



/*-----------------进度条end------------*/
	