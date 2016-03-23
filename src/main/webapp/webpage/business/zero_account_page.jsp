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
    <title>未上架商品列表</title>
    <link rel="stylesheet" href="<%=path %>css/all.css">
    
    <link rel="stylesheet" href="<%=path %>css/h_style.css">
    <style>
        .income{color: green !important;}
        *{ margin:0; padding:0; list-style:none;}
		a{ text-decoration:none;}
		a:hover{ text-decoration:none;}
		.tcdPageCode{padding: 15px 20px;text-align: left;color: #ccc;}
		.tcdPageCode a{display: inline-block;color: #428bca;display: inline-block;height: 25px;	line-height: 25px;	padding: 0 10px;border: 1px solid #ddd;	margin: 0 2px;border-radius: 4px;vertical-align: middle;}
		.tcdPageCode a:hover{text-decoration: none;border: 1px solid #428bca;}
		.tcdPageCode span.current{display: inline-block;height: 25px;line-height: 25px;padding: 0 10px;margin: 0 2px;color: #fff;background-color: #428bca;	border: 1px solid #428bca;border-radius: 4px;vertical-align: middle;}
		.tcdPageCode span.disabled{	display: inline-block;height: 25px;line-height: 25px;padding: 0 10px;margin: 0 2px;	color: #bfbfbf;background: #f2f2f2;border: 1px solid #bfbfbf;border-radius: 4px;vertical-align: middle;}
	</style>
</head>
<body>
<div class="l_right ">

    <div class="l_moban"><!-- 模板 style -->
        <div class="h_mian_condition  l_all">
            <h2>筛选条件</h2>
            <div class="h_service">
                <b class="h_mc fl">状态：</b>
                <select name="all_fw" id="all_fw" class="all_fw fl typeofc">
                    <option value="2">收入</option>
                    <option value="1">支出</option>
                   
                </select>
                                            
               
                <div class="h_search fl" style="margin-left:50px;">
                    <input type="text" id="search" class="search fl " placeholder="输入内容关键字">                  
                </div>
                <a href="#" class="h_shai dk fl searchname">搜索</a>
                <div class="h_sort fl findwhy" value ="1">按时间排序</div>
                <div class="h_sort fl findwhy" value ="2">按金额排序</div>
               
            </div>
        </div>
        <div id="qwertyui">
       
        </div> 
    </div><!-- 模板 end-->
</div><!-- l_right 右边内容 -->




 <script src="<%=path %>js/jquery-1.11.1.min.js"></script>
 <script src="<%=path %>js/menu.js"></script>
 <script src="<%=path %>js/jquery.page.js"></script>
<script>
   $(document).ready(function(){
	   $.post(
				url='<%=basePath%>/storeBusiness.do?zero_account',
				data={
						pagenow : 1,
						type : 0,
						order : 0
				},
				function(result){
					$("#qwertyui").empty().html(result);
					
				},'text');
   })
   $(".searchname").bind('click',function(){ 
	   var name=$("#search").val();
	    $(".findwhy").removeClass("showwhy");
	    $(".typeofc").removeClass("showtype");
	    $(this).addClass("showname"); 
		 $.post(
				 url="<%=basePath%>/storeBusiness.do?zero_account",
				 data={
						 pagenow : 1,
						 order : 0,
						 type : 0,
						 name : name
				 },
				 function(result){
					 $("#qwertyui").empty().html(result);
						
					},'text');
})
   $(".findwhy").bind('click',function(){
	    var why=$(this).attr("value");
	    $(".findwhy").removeClass("showwhy");
	    $(".typeofc").removeClass("showtype");
	    $(".searchname").removeClass("showname");
	    $(this).addClass("showwhy"); 
		 $.post(
				 url="<%=basePath%>/storeBusiness.do?zero_account",
				 data={
						 pagenow : 1,
						 order : why,
						 type : 0		
				 },
				 function(result){
					 $("#qwertyui").empty().html(result);
						
					},'text');
})
$(".typeofc").bind('click',function(){
	    var type=$(this).val();
	    $(this).addClass("showtype");
	    $(".findwhy").removeClass("showwhy"); 
	    $(".searchname").removeClass("showname");
		 $.post(
				 url="<%=basePath%>/storeBusiness.do?zero_account",
				 data={
						 pagenow : 1,
						 type : type,
						 order : 0
				 },
				 function(result){
					 $("#qwertyui").empty().html(result);
						
					},'text');
});
   
</script>
</body>
</html>