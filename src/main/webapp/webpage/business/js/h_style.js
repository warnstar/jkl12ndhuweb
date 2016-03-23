
	$(document).ready(function() {
		
		/*返回按钮边框颜色变化*/
			$(".h_back_btn").hover(function() {

				$(this).css({"border":"1px solid #babcbe"});

			}, function() {
				
				$(this).css({"border":"1px solid #f2f4f6"});
			});
		/*返回按钮边框颜色变化 end*/

	


         /*消费内页--服务详情-技师列表添加按钮*/
        $(".h_js").hover(function() {
        	
        	$(this).children(":eq(1)").children(':eq(4)').fadeIn("fast");

        }, function() {
        	
        	$(this).children(":eq(1)").children(':eq(4)').fadeOut("fast");
        });


      

      /*点击上下线出现确定或取消选择悬浮框*/ 
        $(".xian").click(function(event) {
           if(!$(this).hasClass('haha'))
           {
                $(this).children().show();
                $(this).addClass('haha');
           }
           else 
           {
                $(this).children().hide();
                $(this).removeClass('haha');
           }

        });

       /* 动态信息管理-编辑动态*/

       /*鼠标悬停出现叉叉*/
       $(".h_wai").hover(function() {
           
           $(this).children(':eq(1)').fadeIn(100);

       }, function() {
        
         $(this).children(':eq(1)').fadeOut(100);

       });
     
   
        
     /*排班信息管理绿色三角hover效果*/
     $(".h_week").hover(function() {
       
        $(this).addClass("h_add_ban");

     }, function() {
       
        $(this).removeClass("h_add_ban");

     });

        /*编辑技师鼠标悬停出 现叉叉*/
       $(".h_wai2").hover(function() {
           
           $(this).children(':eq(1)').fadeIn(100);

       }, function() {
        
         $(this).children(':eq(1)').fadeOut(100);

       });

	});