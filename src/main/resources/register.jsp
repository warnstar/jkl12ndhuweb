<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>注册</title>
	<link rel="stylesheet" href="<%=path %>css/all.css">
	<link rel="stylesheet" href="<%=path %>css/denglu.css">
	<style type="text/css">
		html,body{
			background: #fff !important;
		}
		.register_all ul li:nth-child(odd){
			width:40%;
		}
		.register_all ul li:nth-child(even){
			width:60%;
		}
	</style>
</head>
<body>


<div class="h_register_all">
	<h2>注册</h2>
	<div class="register_all">
		<ul>
			<li>手机号：</li>
			<li><input type="tel" class="re_input" id="phone"></li>			
			<li>输入验证码：</li>
			<li>
				<input type="text" class="re_input" id="code" style="width:150px;">
				<input type="button" id="btn" class="re_btn sendcode" value="发送验证码">
			</li>
			<li>密码：</li>
			<li><input type="password" class="re_input" id="pass"></li>
			<li>确认密码：</li>
			<li><input type="password" class="re_input" id="passwordagin"></li>
			<li>昵称：</li>
			<li><input type="text" class="re_input" id="namemaster"></li>
			<li>店名：</li>
			<li><input type="text" class="re_input" id="storename"></li>
			<li>QQ：</li>
			<li><input type="text" class="re_input" id="qq"></li>	
			<li style="height:80px;">验证信息：</li>
			<li style="height:80px;"><textarea class="re_text" id="content"></textarea></li>
			<li style="width:100%;height:40px;"><input type="button" class="re_btn zc" value="注册" id="registeri"></li>	
		</ul>
	</div>
</div>
<script src="<%=path %>js/jquery-1.11.1.min.js"></script>
<script src="<%=path %>js/menu.js"></script>
<script>

$("#registeri").bind('click',function(){

    var mobile=$("#phone").val();
    /* if(checkMobile){
    	alert("请输入正确的手机号码");
    	return;
    } */
    var code=$("#code").val();
    var password=$("#pass").val();
    var name=$("#namemaster").val(); 
    var shopname=$("#storename").val();  
    var bussiness_qq=$("#qq").val();  
    var information=$("#content").val(); 
	$.post(
	
			url="<%=basePath%>/storeLoginBusiness.do?register",
			data={
					code : code,
				    mobile : mobile,
		    		password : password,
		    		name : name,
		    		shopname : shopname,
		    		bussiness_qq : bussiness_qq,
		    		information : information 	
				  },	
			function(result){
				alert(result.obj);
			},'json');

 });  
 $(".sendcode").bind('click',function(){
	    var mobile=$("#phone").val();
	    $.post(
				
				url="<%=basePath%>/storeLoginBusiness.do?code",
				data={
					    mobile : mobile
			    		
					  },	
				function(result){
					alert(result.obj);
				},'json');
 });
 

/* function checkMobile() {
	var isMobile=/^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/; 
	var mobile = $("#phone").val();
	if(trim(mobile) == "" || !isMobile.test(mobile)) {
		return true;
	} else {
		return false;
	}
}	 */
$("#pass").blur(function(){
	 var password=$("#pass").val();
	 if(password==""){
	    	alert("不能为空！");
	   }
})

$("#passwordagin").blur(function(){
	 var passwordagin=$("#passwordagin").val();
	 var password=$("#pass").val();
	 if(passwordagin==password){
	   
	 }else{
	    	alert("两次输入密码不一致！");
	    }
})
</script>
 <script type="text/javascript">
var wait=60;
function time(o) {
        if (wait == 0) {
            o.removeAttribute("disabled");           
            o.value="发送验证码";
            wait = 60;
        } else {
            o.setAttribute("disabled", true);
            o.value="还剩余(" + wait + "秒)";
            wait--;
            setTimeout(function() {
                time(o)
            },
            1000)
        }
    }
document.getElementById("btn").onclick=function(){time(this);}
</script>

</body>
</html>