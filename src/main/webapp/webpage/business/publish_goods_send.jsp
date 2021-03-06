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
    <title>未出售商品列表</title>
    <link rel="stylesheet" href="<%=path %>css/all.css">
    
    <link rel="stylesheet" href="<%=path %>css/h_style.css">
    <style>
        .h_mx{margin: 20px 0 0 100px;}
        .status{margin-left: 100px;}
         @media (max-width: 1200px) and (min-width:900px){
            .h_mx {margin: 20px 0 0 40px;}
            
            .h_service{padding: 20px 0;}
            .all_fw{width:80px;}
           
            .h_shai{margin-right: 5px;}
            .h_search{margin-left: 0 !important;width:180px;}
            .h_sort{margin-left: 5px;font-size: 12px;}
        }
         @media (max-width: 1550px) and (min-width: 1201px){
            .h_mx {margin: 20px 0 0 50px;width:90px !important;}
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
         <div class="h_mian_condition  l_all sear" value="${se}">
            <h2>筛选条件</h2>
            <div class="h_service searchforname" value="${name}"> 
                <div class="h_search fl" style="margin-left:50px;">
                    <input type="text" id="search" class="search fl" placeholder="输入商品名称">                  
                </div>
                <a href="#" class="h_shai dk fl searchname">搜索</a>            
                <div class="h_sort fl searchwhy" value="1">创建时间排序</div>
                <div class="h_sort fl searchwhy" value="2">商品价格排序</div>
                <div class="h_sort fl searchwhy" value="3">商品数量排序</div>
            </div>
        </div> 
        
        <div id="qwertyui">
        <div class="h_content_mian l_all" style="margin-top:20px;">
           
            <div class="h_mian_bt  l_all">已发货商品列表</div>

            
               <div class="h_mian_nav l_all dongtai">
                     
                    
                    <ul>
                        <li class="w20">商品录入时间</li>                      
                        <li class="w20">商品名称</li> 
                        <li class="w15">商品单价（元）</li>
                        <li class="w10">已出售数量</li>
                        <li class="w10">已晒单数量</li>                                          
                        <li class="w25">操作</li>
                    </ul>
                   
                </div><!-- h_mian_nav -->
				
                <div class=" h_content_all l_all dongtai pagen" value="${pagenow}" >
                        
                   <c:forEach items="${sendList}" var="sendlist">  

                          <ul class="h_contents l_all ">
                            <li class="w20">${sendlist.create_time}</li>                           
                            <li class="w20">${sendlist.goods_name}</li> 
                            <li class="w15">${sendlist.goods_rmb }</li>
                            <li class="w10">${sendlist.goods_current_num }/${sendlist.goods_num }</li>                           
                            <li class="w10">${sendlist.share_num }/${sendlist.goods_num }</li>
                            <li class="w25 ">
                                 <a href="<%=basePath%>/storeSingleBusiness.do?publish_ready_detailsend&id=${sendlist.id}" class="h_mx dk fl bianji " style="color:#fff !important;width:74px;">查看详情</a>
                            </li>
                        </ul>
                  </c:forEach>  
                </div><!-- h_mian_nav -->
                 <div class="tcdPageCode" value="${pagelist }">
                 </div>
        </div><!-- h_content_mian -->
        </div>
    </div><!-- 模板 end-->
</div><!-- l_right 右边内容 -->


<div class="theme-popover-mask"  ></div><!-- 所有弹窗共用这一个黑屏，不需要复制 -->
<!-- 等待保证金返还的商品详情弹框 调用的类是 tan4-->
<div class="theme-popover theme3" style="position:fixed;" id="notopen"><!-- 点击按钮，调用theme2事件， -->
    
</div><!-- theme-popover -->
 <script src="<%=path %>js/jquery-1.11.1.min.js"></script>
 <script src="<%=path %>js/menu.js"></script>
  <script src="<%=path %>js/tan.js"></script>
  <script src="<%=path %>js/jquery.page.js"></script>
 <!--  <script >
/* 调用弹框*/
 $('.tan2').click(function(event) {
    pop_show2();
 });
 
$('.tan3').click(function(event) {
    pop_show3();
 });

/* 调用弹框*/
 </script> -->
 <script type="text/javascript">
 close();
 $(".searchtype").bind('click',function(){
	    $(".searchwhy").removeClass("showtype");
	    $(this).addClass("showtype");
		var search=$(this).attr("value");
		$.post(
				url='<%=basePath%>/storeSingleBusiness.do?publish_goods_send',
				data={
						search : search,
						pagenow : 1
						},
				function(result){
					$('body').empty().html(result);
					
				},'text');
	});	
 $(".searchname").bind('click',function(){
		var name=$("#search").val();
		$.post(
				url='<%=basePath%>/storeSingleBusiness.do?publish_goods_send',
				data={
						pagenow : 1,
						name : name
						},
				function(result){
					$('body').empty().html(result);
					
				},'text');
	});
 </script>
  <script type="text/javascript">
  $(".tcdPageCode").createPage({
      pageCount:$(".tcdPageCode").attr("value"),
      current:$(".pagen").attr("value"),
      backFn:function(p){
          var search=$(".sear").attr("value");
          var name=$(".searchforname").val();
  		$.post(
  				url='<%=basePath%>/storeSingleBusiness.do?publish_goods_send',
  				data={
  						pagenow : p,
  					    search : search ,
  						name : name
  						},
  				function(result){
  					$('body').empty().html(result);
  					
  				},'text');
      }
  });
         </script>
		<script>
		 $('body').on('click','.tan2',function(event) {
			 var id=$(this).attr("value");
				$.post(
						url='<%=basePath%>/storeSingleBusiness.do?publish_ready_detailnotopen',
						data={
							
								id : id 
								},
						function(result){
							$("#notopen").empty().html(result);
							
						},'text');
			    pop_show2();
			 });
		
		
		</script>
 

</body>
</html>