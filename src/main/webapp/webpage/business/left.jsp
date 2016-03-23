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
	<title>首页</title>
	<link rel="stylesheet" href="<%=path %>css/all.css">
	<link rel="stylesheet" href="<%=path %>css/shouye.css">
	<style>
		.test{
			background-color: rgba(27, 30, 36, 0.44) !important;
			color:#ebf0f5 !important;
			font-weight: bold !important;
		}
	</style>
</head>
<body>
<div class="l_left fl">
	<!-- <div class="l_left_heard"></div> -->
	<dl class="l_dianming ov">
		<dt class="fl dk"style="background:#e6e8ea url(<%=path %>images/icon.jpg) no-repeat center center;  background-size:contain;"></dt>
		<dd class="ot fl">
		</dd>
	</dl><!-- l_dianming -->

	<div id="firstpane" class="menu_list">
		
		<h3 class="menu_head current menu_i1" value="${mobile}"><i></i>普通商品管理</h3>
		<div  class="menu_body">

			<a href="<%=basePath%>/storeBusiness.do?publish_normal_page"  target="main">发布商品</a>
			<a href="<%=basePath%>/storeBusiness.do?publish_not_ready_page"  target="main" >未上架商品列表</a>
			<a href="<%=basePath%>/storeSingleBusiness.do?publish_goods_notopen&pagenow=1"  target="main" >未售出商品列表</a>
			<a href="<%=basePath%>/storeSingleBusiness.do?publish_goods_notsend&pagenow=1&search=0"  target="main" >未发货商品列表</a>
			<a href="<%=basePath%>/storeSingleBusiness.do?publish_goods_send&pagenow=1"  target="main" >已发货商品列表</a>
			<a href="<%=basePath%>/storeSingleBusiness.do?publish_goods_Ready&pagenow=1"  target="main" >未返还保证金列表</a>
			<a href="<%=basePath%>/storeSingleBusiness.do?publish_goods_finish&pagenow=1"  target="main" >已完成商品列表</a>
		</div>
		<h3 class="menu_head menu_i2"><i></i>精品商品管理 </h3>
		<div  class="menu_body">
			<a href="<%=basePath%>/storeBusiness.do?publish_zero_page" target="main">发布精品商品</a>
			<a href="<%=basePath%>/zeroBusiness.do?publish_zeronot_ready_page" target="main">未上架精品商品列表</a>
			<a href="<%=basePath%>/zeroSingleBusiness.do?publish_zerogoods_notopen&pagenow=1" target="main">未售出精品商品列表</a>
			<a href="<%=basePath%>/zeroSingleBusiness.do?publish_zerogoods_notsend&pagenow=1" target="main">未发货精品商品列表</a>
			<a href="<%=basePath%>/zeroSingleBusiness.do?publish_zerogoods_send&pagenow=1" target="main">已发货精品商品列表</a>
			<a href="<%=basePath%>/zeroSingleBusiness.do?publish_zerogoods_Ready&pagenow=1" target="main">未返还保证金列表</a>
			<a href="<%=basePath%>/zeroSingleBusiness.do?publish_zerogoods_finish&pagenow=1" target="main">已完成精品商品列表</a>
		</div>
		
		<h3 class="menu_head menu_i3"><i></i>个人中心 </h3>
		<div  class="menu_body">
			<a href="<%=basePath%>/storeBusiness.do?information" target="main" >个人资料</a>
			<a href="<%=basePath%>/storeBusiness.do?change_pwd_page" target="main">修改密码</a>
			<a href="<%=basePath%>/storeBusiness.do?account_page" target="main" >普通商品对账单</a>
			<a href="<%=basePath%>/storeBusiness.do?zero_account_page" target="main" >精品商品对账单</a>

		</div>
		
		<h3 class="menu_head menu_i4"><i></i>系统管理 </h3>
		<div  class="menu_body">
			<a href="javascript:;" class="loginout">退出登陆</a>

		</div>
		<h3 class="menu_head menu_i5"><i></i>帮助 </h3>
		<div  class="menu_body">
			<a href="<%=basePath%>/storeBusiness.do?help" target="main">系统帮助</a>
		</div>
	</div>



</div><!-- l_left 左边侧栏 -->

 <script src="<%=path %>js/jquery-1.11.1.min.js"></script>
 <script src="<%=path %>js/menu.js"></script> 
 <script>

	$(".menu_body a").bind("click",function(){
		$(this).addClass("test");
		$(this).siblings().removeClass("test");
	});


 $(".loginout").bind('click',function(){
 	$.post(
 		url="<%=basePath%>/storeLoginBusiness.do?loginout",
	 	data={},
	 	function(result){
 			parent.location.reload();
	 	},'text');
 })
</script>
</body>
</html>
