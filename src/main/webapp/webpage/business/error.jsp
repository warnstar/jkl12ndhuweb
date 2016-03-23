<%@ page contentType="text/html; charset=UTF-8"%>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<html>
<head>
<title>失败!</title>
</head>
<body>
充值失败！！！
</body>
<script>
    //判断如果当前页面不为主框架，则将主框架进行跳转
    var tagert_URL = "<%=basePath%>/storeLoginBusiness.do?index";
    window.parent.location.href=tagert_URL;
</script>
</html>