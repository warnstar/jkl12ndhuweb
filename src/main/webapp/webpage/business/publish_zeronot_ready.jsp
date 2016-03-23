<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
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
     
         .h_mx{margin: 20px 0 0 80px;}
         @media (max-width: 1200px) and (min-width:900px){
            .h_mx {margin: 20px 0 0 40px;width:65px !important;}
            .h_ce {margin: 20px 0 0 5px !important;font-size: 13px;width:70px !important;}
            .h_service{padding: 20px 0;}
            .all_fw{width:80px;}
            .tan1{width:50px;}
            .h_shai{margin-right: 5px;}
            .h_search{margin-left: 0 !important;width:180px;}
            .h_sort{margin-left: 5px;}
        }
         @media (max-width: 1550px) and (min-width: 1201px){
            .h_mx {margin: 20px 0 0 86px;width:90px !important;}
            .h_ce {margin: 20px 0 0 8px !important;font-size: 13px;width:85px !important;}
            .h_service{padding: 20px 0;}
            .all_fw{width:80px;}
            .h_shai{margin-right: 5px;}
            .h_search{margin-left: 0 !important;width:180px;}
            .h_sort{margin-left: 15px;}
        }
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
                <select name="all_fw " id="all_fw" class="all_fw fl typeofc">
                    <option value="1">审核中</option>
                    <option value="3">已拒绝</option>
                    <option value="2">未缴费</option>
                    <option value="4">未上架</option>
                </select>
                                            
               
                <div class="h_search fl" style="margin-left:50px;">
                    <input type="text" id="search" class="search fl" placeholder="输入商品名称">                  
                </div>
                <a href="#" class="h_shai dk fl searchname">搜索</a>
                <div class="h_sort fl findwhy" value="1">按商品单价排序</div>
                <div class="h_sort fl findwhy" value="2">按商品录入时间排序</div>
                <div class="h_sort fl findwhy" value="3">按参与上架的数量排序</div>
            </div>
        </div>
        
        <div id="qwertyui">
        </div>
    </div><!-- 模板 end-->
</div><!-- l_right 右边内容 -->




<!-- 已拒绝或审核中的商品详情弹框 调用的类是 tan2-->
<div class="theme-popover theme2" id="tan2detail"><!-- 点击按钮，调用theme2事件， -->
 
        
</div><!-- theme-popover -->

<!-- 未缴费商品详情弹框 调用的类是 tan3-->
<div class="theme-popover theme3" id="tan3detail"><!-- 点击按钮，调用theme2事件， -->
  
</div><!-- theme-popover -->


<!-- 等待上架弹框 调用的类是 tan4-->
<div class="theme-popover theme4" id="tan4detail"><!-- 点击按钮，调用theme2事件， -->
  
</div><!-- theme-popover -->


 <script src="<%=path %>js/jquery-1.11.1.min.js"></script>
 <script src="<%=path %>js/menu.js"></script>
  <script src="<%=path %>js/tan.js"></script>
   <script src="<%=path %>js/jquery.page.js"></script>
 <script>
 close();
   $(document).ready(function(){
		var name="";
	   $.post(
				url='<%=basePath%>/zeroBusiness.do?publish_zeronot_ready_list',
				data={pagenow : 1,
						typel : 0,
						whyl : 0,
						name : name
				},
				function(result){
					$("#qwertyui").empty().html(result);
					
				},'text');
	});
   $(".searchname").bind('click',function(){
	    var name=$("#search").val();
	    $(".findwhy").removeClass("showwhy");
	    $(".typeofc").removeClass("showtype");
	    $(this).addClass("showname"); 
		 $.post(
				 url="<%=basePath%>/zeroBusiness.do?publish_zeronot_ready_list",
				 data={
						 pagenow : 1,
						 whyl : 0,
						 typel : 0,
						 name : name
				 },
				 function(result){
					 $("#qwertyui").empty().html(result);
						
					},'text');
		
})
   $(".findwhy").bind('click',function(){
	    var whyl=$(this).attr("value");
	    $(".findwhy").removeClass("showwhy");
	    $(".typeofc").removeClass("showtype");
	    $(".searchname").removeClass("showname");
	    $(this).addClass("showwhy"); 
		 $.post(
				 url="<%=basePath%>/zeroBusiness.do?publish_zeronot_ready_list",
				 data={
						 pagenow : 1,
						 whyl : whyl,
						 typel : 0
				 },
				 function(result){
					 $("#qwertyui").empty().html(result);
						
					},'text');
		
})
$(".typeofc").bind('click',function(){
	    var typel=$(this).val();
	    $(this).addClass("showtype");
	    $(".findwhy").removeClass("showwhy");
	    $(".searchname").removeClass("showname");
		 $.post(
				 url="<%=basePath%>/zeroBusiness.do?publish_zeronot_ready_list",
				 data={
						 pagenow : 1,
						 typel : typel,
						 whyl : 0
				 },
				 function(result){
					 $("#qwertyui").empty().html(result);
						
					},'text');
		
})
   
</script>
 

</body>
</html>