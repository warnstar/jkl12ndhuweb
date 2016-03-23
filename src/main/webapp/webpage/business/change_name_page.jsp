<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<%@include file="/context/conext.jsp"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>0元夺宝商品管理-发布商品</title>
    <link rel="stylesheet" href="<%=path %>css/all.css">
    <link rel="stylesheet" href="<%=path %>css/denglu.css">
    <link rel="stylesheet" href="<%=path %>css/h_style.css">
    <style>
        .h_register_all{width:60%;}
        .register_all{width: 100%;}
        .re_input,.re_text{width:100%;}
        .number{bottom: 36px;}
        .register_all ul .title{padding-left: 400px;}
        .h_submit{margin-left: 494px;}
    </style>
</head>
<body>



<div class="l_right ">
   
    <div class="l_moban"><!-- 模板 style -->
        <div class="h_content_mian l_all">
           
           <!--  <div class="h_mian_bt  l_all">发布商品</div>  -->      

            <div class=" h_content_all l_all dongtai" style="border:none;">
                <div class="h_register_all" style="margin-top:40px;">
                    <div class="register_all">
                        <ul>
                            <li class="title">修改呢称</li>
                            <li></li>
                            <li><input type="text" id="parname"class="re_input" placeholder="请输入新昵称:${qq }" maxlength="12"></li>
                            <li class="btns">
                                <input type="submit" value="保存" id="saveParameter" class="h_submit fl">
                                <input type="submit" value="取消" class="h_cancle fl returnto">
                            </li>
                        
                       </ul>
                    </div>
                </div>        
                    
            </div>



        </div><!-- h_content_mian -->
        
    </div><!-- 模板 end-->
</div><!-- l_right 右边内容 -->
<div class="theme-popover-mask"  ></div><!-- 所有弹窗共用这一个黑屏，不需要复制 -->
<!-- 修改成功 调用的类是 tan1-->
<div class="theme-popover theme1"><!-- 点击按钮，调用theme1事件， -->
    <div class="theme-poptit">
       <!--  <h3 class="fl" >修改提醒</h3> -->
        <a href="javascript:;" title="关闭" class="close fr">×</a>
    </div>
    <!-- 弹窗内容模块 开始-->
    <div class="popbod">
        <div class="h_sure_cx">
            <p class="sure_del" style="text-align:center;">修改成功！</p>
        </div>
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
    <div class="popbod_bottom"><!-- 此处放按钮 -->
            
            <div class="buttom_del buttom_del_bg2 del returnto" id="sure_dele" style="float:right;">确认</div><!-- 关闭，调用del这个类，公用的 -->
    </div><!-- popbod_bottom -->

</div><!-- theme-popover -->
<!-- 修改失败 调用的类是 tan2-->
<div class="theme-popover theme2" ><!-- 点击按钮，调用theme1事件， -->
    <div class="theme-poptit">
        <h3 class="fl" >修改提醒</h3>
        <a href="javascript:;" title="关闭" class="close fr">×</a>
    </div>
    <!-- 弹窗内容模块 开始-->
    <div class="popbod">
        <div class="h_sure_cx">
            <p class="sure_del" style="font-weight:600;color:#666;">提交失败！</p>
        </div>
        <div class="h_sure_cx" style="margin-top:30px;width:100%;">
            <p class="sure_del" style="text-align:center;">您提交的参数有问题请重申请</p>
        </div>
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
    <div class="popbod_bottom"><!-- 此处放按钮 -->
            
            <div class="buttom_del buttom_del_bg2 del" id="sure_dele" style="float:right;">确认</div><!-- 关闭，调用del这个类，公用的 -->
    </div><!-- popbod_bottom -->

</div><!-- theme-popover -->

<script src="<%=path %>js/jquery-1.11.1.min.js"></script>
<script src="<%=path %>js/menu.js"></script>
<script src="<%=path %>js/tan.js"></script>

<script>
$(".returnto").bind('click',function(){
	$.post(
			url="<%=basePath%>/storeBusiness.do?information",
			 data={ },
			 function(result){
				 $('body').empty().html(result);
			 },'text')
})

$("#saveParameter").bind('click',function(){
	var name=$("#parname").val();
	if(name==""){alert("不能为空！");return;}
	var business_qq="";
	var shopname="";
	$.post(
			 url="<%=basePath%>/storeBusiness.do?change_Parameter",
			 data={
				business_qq : business_qq,	 
				name : name,
				shopname : shopname
			 },
			 function(result){
				 if(result.obj==1){
					 pop_show1();
				 }else{
					 pop_show2();
				 }
			 },'json')	
})
</script>

</body>
</html>