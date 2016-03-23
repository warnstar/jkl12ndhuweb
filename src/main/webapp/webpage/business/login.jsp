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
	<title>商家平台登陆</title>
	<link rel="stylesheet" href="<%=path %>css/all.css">
	<link rel="stylesheet" href="<%=path %>css/denglu.css">
	<style type="<%=path %>text/css">
		html,body{
			background: #fff !important;
		}
	</style>
</head>
<body>

<div class="l_denglu_all l_denglu_conter pr">
	
	<div class="l_denglu_left fl">
		<span class="left_title dk">用户名：</span>
		<span class="left_title dk">密码：</span>
	</div>
	<div class="l_denglu_right fl" >
		<h2 class="l_denglu_right_h2">登录界面</h2>
		<div class="l_zhanghao pr l_zhanghao_i">
		    <i></i>
			<input type="text" class="l_zhanghao_input"placeholder="请输入账号" id="username">
		</div>
		<div class="l_zhanghao pr l_zhanghao_top l_zhanghao_i2" >
			<i></i>
			<input type="password" class="l_zhanghao_input" placeholder="请输入密码" id="password">
		</div>
		
			<input type="submit" id="register" value="注册" class="l_zhanghao_bg login"></a>
			<input type="submit" id="loginin" value="登陆" class="l_zhanghao_bg">
		    <a href="<%=basePath%>/storeLoginBusiness.do?resetpassword_page" class="l_zhanghao_a dk">忘记密码</a>
		
	</div><!-- l_denglu_right -->

</div><!-- l_denglu_conter -->
<script src="<%=path %>js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
/* 跳转到注册页面 */
 $("#register").bind('click',function(){
	 window.location.href="<%=basePath%>/storeLoginBusiness.do?register_page";
 })

/*  登录验证 */
 $(document).keyup(function(event){
	
	  if(event.keyCode ==13){
	 	login();
	  } 
	});

	 $("#loginin").bind('click',function() {
		login();
	 });
	 
	 function login(){
		 var user=$("#username").val();
		 var pass=$("#password").val();
	
		 var tagert_URL = "<%=basePath%>/storeLoginBusiness.do?index";
	     if(user!=""&&pass!=""){
	    	 
	        $.post(
	            url="<%=basePath%>/storeLoginBusiness.do?login",
	            data={
	                name : user ,
	                pwd : pass
	            },
	            function(result) {
	            
	            if(result.obj=="flasecheck"){ 
	            		  alert("用户名或密码错误");
	            	 }else if(result.obj=="truecheck"){
	            		 window.location.href = tagert_URL;
	            		  $.post(
	            		            url="<%=basePath%>/storeLoginBusiness.do?index",
	            		            data={
	            		            		mobile : user
	            		            },
	            		            function(result) {
	            		            	$(document).find('html').html(result);
	            		            });  
	            	}     
	            },'json');        
	     }else{
	        alert("用户名或密码未输入");
	       }
		 
	 }
</script>





</body>
</html>