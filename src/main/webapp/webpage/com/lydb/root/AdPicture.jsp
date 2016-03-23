<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>APP广告图片添加</title>
	<link rel="stylesheet" href="<%=basePath%>/webpage/js/all.css">
	<link rel="stylesheet" href="<%=basePath%>/webpage/js/h_style.css">
	<style>
		.yang{text-align:center;font-size:26px;color: #55606e;}
		.all_ads{margin:0 auto;width: 50%;}
		/* .h_chuan,.h_pics{width: 50%;} */
		.h_donicmy_left,.h_er_right,.h_chuan,.h_pics{margin:0 auto;}
		.h_donicmy_left{margin-bottom: 20px;font-size: 20px;}
		.h_pics{margin:10px auto;}
		.h_wai{border:none;}
	</style>
</head>
<body >
<div class="l_right" style="width:100%;">

	<div class="l_moban" style="padding:0;"><!-- 模板 style -->
	         
          <div class="h_content_mian l_all" >

        	<div class="h_mian_bt  l_all yang" >APP首页广告图</div>
 		<input type="hidden" id="domain" value="http://7xp1b8.com1.z0.glb.clouddn.com/">
        <input type="hidden" id="uptoken_url" value="<%=basePath%>/qiniu.do?uptoken">
    			<div class="all_ads">
    				<div class="h_donicmy_con">
			
                 	  <div class="h_donicmy_left ">第一张图：</div>

                     <div class="h_er_right " >
                     		<div class="h_chuan">
                     			<div class="h_sc fl pr"><input type="file" class="chuan po" data="1" >上传附图</div>
                     			<span class="jian fl dk">建议上传640*240像素的图片</span>
                     		</div>

                     		<div class="h_pics" >
                     			<div class="h_wai fl pr " style="width:640px;height:240px;">
                     				<img src="${picture1}" alt="" class="show_img">
                     			</div>
                     			
                     		</div>
                        <div class="h_btns" style="padding-left:252px;">
                          <a href="#" class="tijiao fk fl submit">修改</a>
                        </div>
                     </div>
                    
            </div><!-- h_donicmy_con -->

            <div class="h_donicmy_con">

                    <div class="h_donicmy_left ">第二张图：</div>

                     <div class="h_er_right " >
                        <div class="h_chuan">
                          <div class="h_sc fl pr"><input type="file" class="chuan po" data="2" >上传附图</div>
                          <span class="jian fl dk">建议上传640*240像素的图片</span>
                        </div>

                        <div class="h_pics" >
                          <div class="h_wai fl pr " style="width:640px;height:240px;">
                            <img src="${picture2}" alt="" class="show_img">
                          </div>
                          
                        </div>
                        <div class="h_btns" style="padding-left:252px;">
                          <a href="#" class="tijiao fk fl submit">修改</a>
                        </div>
                     </div>
                    
            </div><!-- h_donicmy_con -->

            <div class="h_donicmy_con">

                    <div class="h_donicmy_left ">第三张图：</div>

                     <div class="h_er_right " >
                        <div class="h_chuan">
                          <div class="h_sc fl pr"><input type="file" class="chuan po" data="3" >上传附图</div>
                          <span class="jian fl dk">建议上传640*240像素的图片</span>
                        </div>

                        <div class="h_pics" >
                          <div class="h_wai fl pr " style="width:640px;height:240px;">
                            <img src="${picture3}" alt="" class="show_img">
                          </div>
                          
                        </div>
                        <div class="h_btns" style="padding-left:252px;">
                          <a href="#" class="tijiao fk fl submit">修改</a>
                        </div>
                     </div>
                    
            </div><!-- h_donicmy_con -->
                
    			</div>
                              		    
         </div><!-- h_content_mian -->
		
	</div><!-- 模板 end-->
</div><!-- l_right 右边内容 -->



 <script src="<%=basePath%>/webpage/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/js/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/js/plupload/i18n/zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/js/ui.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/js/qiniu.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/js/main.js"></script>
 <script>

            $(function(){
            	 var formdata = new FormData();
            	 $(".chuan").on("change", function(){
 		        	var index1 =$(this).attr('data');

 		            var files = !!this.files ? this.files : [];
 		            if (!files.length || !window.FileReader) return;
 		            for(var i=0;i<files.length;i++){
 		                if (/^image/.test( files[i].type)){
 		                    var reader = new FileReader();
 		                    reader.readAsDataURL(files[i]);
 		                    formdata.append("img[]", files[i]);
 		                   formdata.append("picture", "picture"+index1);
 		                    reader.onloadend = function(){
 		                       $('.show_img').eq(index1-1).attr('src', this.result);
 		                        
 		                    }
 		                }
 		            }
 		        });




            	    $('.submit').click(function(){
            	        $.ajax({
            	            url:"<%=basePath %>/AppClientManagement.do?doAdPicture",
            	            type:'POST',
            	            data:formdata,
            	            dataType:'json',
            	            mimeType:"multipart/form-data",
            	            processData: false,  // 告诉jQuery不要去处理发送的数据
            	            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            	            success:function(data){
            	                        if (data.success) {
            	                            alert(data.msg);
            	                        }else{
            	                          alert(data.msg);
            	                        }
            	                    },
            	            error:function(XmlHttpRequest,textStatus,errorThrown){
            	                            alert('添加失败!');
            	                            console.log(XmlHttpRequest);
            	                            console.log(textStatus);
            	                            console.log(errorThrown);
            	                    }
            	        });
            	      
            	    });
             });

      
</script>
</body>
</html>