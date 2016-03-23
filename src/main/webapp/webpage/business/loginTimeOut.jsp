<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<!DOCTYPE html>

<!-- Link JScript-->
<script type="text/javascript">
    //判断如果当前页面不为主框架，则将主框架进行跳转
    var tagert_URL = "<%=basePath%>/storeLoginBusiness.do?login_page";
    window.parent.location.href=tagert_URL;

</script>
</body>
</html>