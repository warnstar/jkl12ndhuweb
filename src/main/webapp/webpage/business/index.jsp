<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>首页</title>
</head>

    <frameset cols="17%,*"frameborder="no" border="0" framespacing="0">
        <!--主体左部分-->
        <frame src="<%=basePath%>/storeBusiness.do?index_left&mobile=${mobile}" name="left" noresize="noresize" frameborder="0" scrolling="no" marginwidth="0" marginheight="0" id="leftframe"/>
        <!--主体右部分-->
        <frame src="<%=basePath%>/storeBusiness.do?publish_not_ready_page" name="main" frameborder="0" scrolling="auto" marginwidth="0" marginheight="0" id="rightframe"/>
    </frameset>
</html>