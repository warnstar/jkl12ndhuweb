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
        .register_all{width: 50%;margin:0;}
        .re_input,.re_text{width:100%;border:none;}
        .number{bottom: 36px;}
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
                     
                            <li>手机号：</li>
                            <li><input type="text" class="re_input" value="${mobile}"></li>

                            <li>昵称：</li>
                            <li style="text-align:left;"><b style="float:left;">${name}</b> <a href="<%=basePath%>/storeBusiness.do?change_name_page" class="h_revise dk fl">修改</a></li> 

                            <li>店名：</li>
                            <li style="text-align:left;"><b style="float:left;">${shopname}</b> <a href="<%=basePath%>/storeBusiness.do?change_shopname_page" class="h_revise dk fl">修改</a></li>  
                            <li>QQ：</li>
                            <li style="text-align:left;"><b style="float:left;">${business_qq}</b>  <a href="<%=basePath%>/storeBusiness.do?change_qq_page" class="h_revise dk fl">修改</a></li>                           
                         
                        </ul>
                       
                        
                    </div>
                    
                </div>        
                    
            </div>



        </div><!-- h_content_mian -->
        
    </div><!-- 模板 end-->
</div><!-- l_right 右边内容 -->


 
<script src="<%=path %>js/jquery-1.11.1.min.js"></script>
<script src="<%=path %>js/menu.js"></script>

</body>
</html>