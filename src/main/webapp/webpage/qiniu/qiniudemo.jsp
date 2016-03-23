<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%
String path = request.getContextPath()+"/webpage/com/motu/qiniu";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
</head>
<script type="text/javascript" src="<%=path %>/static/qiniu/js/jquery-1.9.1.min.js"></script>  
<body >
	<div class="l_lianjie">
            <span class="bd_span dk fl mr10"style="margin-top:10px;margin-left:22px;">用户头像：</span>

            <div class="l_guang"style="width:600px;">
             <span class="l_dt dk"><input type="file"  id="file" name="file"/>
				</span>大图片建议：<b style="color:red;">（ 图片300像素X300像素 ）</b>
                                            
              </div>

              <div class="guang_tu"id="preview"style="margin-left:90px;">
                <img id="show_img"  src=""  width="20%" style="border:0;" />
				</div>
              </div><!-- l_lianjie -->
              <a href="javascript:;" class="dk l_btn fl l_btnbg submit"style="margin:7px 0 50px 100px; padding:0 30px;">提交</a> 
</body>
</html>
<script>
$(function(){
    var formdata = new FormData();
    $("#file").on("change", function(){
        var files = !!this.files ? this.files : [];
        if (!files.length || !window.FileReader) return;
        if (/^image/.test( files[0].type)){
            var reader = new FileReader();
            reader.readAsDataURL(files[0]);
            formdata.append("user_imgurl", this.files[0]);
            formdata.append("test","test");
            formdata.append("test1","test1");
            reader.onloadend = function(){
                $('#show_img').attr('src', this.result);
            }
        }
    });



    $('.submit').click(function(){
        $.ajax({
            url:"<%=basePath %>/qiniu.do?updateQiniu",
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