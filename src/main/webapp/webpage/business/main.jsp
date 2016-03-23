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
</head>
<body>




<div class="l_right">
	<div class="l_right_heard pr">
		<div class="l_top_left fl">
			<ul>
				<li class="top_i">
				    <a href="" ciass="dk"><i></i><!-- <b class="top_b po dk">11</b>--></a> 
				</li>
				<li class="top_i2">
					<a href="" ciass="dk"><i></i></a>
				</li>
				<li class="top_i3">
					<a href="" ciass="dk"><i></i></a>
				</li>
				<!-- <li class="top_li top_i4">
					<i></i>
					<input type="submit" id="su" class="l_sousuo fl">
					<input type="text" class="top_input fl" placeholder="请输入手机号搜索">
				</li> -->

			</ul>
		</div>

		<div class="l_top_right fr">
				
			<h2 class="fl dk ot">张三炮</h2>
			<span class="fl dk"style="background:#e6e8ea url(<%=path %>images/icon.jpg) no-repeat center center;  background-size:contain;"></span>
				
			<a href="" class="dk"><i></i></a><!-- 退出 -->
		</div>

	</div><!-- l_right_heard -->


	<div class="l_moban"><!-- 模板 style -->


		<!-- <div class="shouye">
			<div class="shouye_top shouye_top_i1">				
				<dl>
					<dt></dt>
					<dd>
						<span>123</span>
						<br>今日预约数
					</dd>
				</dl>
			</div>

			<div class="shouye_top shouye_top_i2">
				<dl>
					<dt></dt>
					<dd>
						<span>6123</span>
						<br>今日下单数
					</dd>
				</dl>
			</div>

			<div class="shouye_top shouye_top_i3">
				<dl>
					<dt></dt>
					<dd>
						<span>46123</span>
						<br>总点攒数
					</dd>
				</dl>
			</div>

		</div>shouye -->

		<div class="l_all">
			<a href="" class="l_all_a">
				<div class="shouye_con fl ">

					<div href="" class="shouye_con_span shouye_con_span_i fl pr">
						<i></i>
						<img src="<%=path %>images/shouye-1.jpg" alt="">
					</div>
					<div class="shouye_con_dl shouye_con_dl_bg fr">
							<b><i></i></b><br>到店消费
					</div>
				</div>				
			</a>
			<a href="">
				<div class="shouye_con shouye_con2 shouye_con_right fl">

				    <div href="" class="shouye_con_span dk shouye_con_span_i2 fl pr">
						<i></i>
						<img src="<%=path %>images/shouye-2.jpg" alt="">
					</div>
					<div class="shouye_con_dl shouye_con_dl_bg2 fr">
							<b><i></i></b><br>预约消费
					</div>					
				</div>				
			</a>
			<a href="">
				<div class="shouye_con shouye_con3 fl">

				    <div href="" class="shouye_con_span shouye_con_span_i3 fr pr">
						<i></i>
						<img src="<%=path %>images/shouye-3.jpg" alt="">
					</div>
					<div class="shouye_con_dl shouye_con_dl_bg3 fl">
							<b><i></i></b><br>会员卡充值
					</div>				
				</div>				
			</a>
			<a href="">
				<div class="shouye_con shouye_con3 shouye_con_right fl">

				    <div href="" class="shouye_con_span shouye_con_span_i4 fr pr">
						<i></i>
						<img src="<%=path %>images/shouye-4.jpg" alt="">
					</div>
					<div class="shouye_con_dl shouye_con_dl_bg4 fl">
							<b><i></i></b><br>撤销消费
					</div>				
				</div>				
			</a>			
		</div><!-- shouye_con -->
	</div><!-- 模板 end-->	
</div><!-- l_right 右边内容 -->



 <script src="<%=path %>js/jquery-1.11.1.min.js"></script>
 <script src="<%=path %>js/menu.js"></script>

</body>
</html>
