
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<div class="h_content_mian l_all">
           
            <div class="h_mian_bt  l_all">未上架商品列表</div>
            
            <div class="h_mian_nav l_all dongtai">
                
                <ul>
                    <li class="w20">商品录入时间</li>
                    <li class="w20">商品名称</li>
                    <li class="w10">商品单价（元）</li> 
                    <li class="w15">数量</li>
                    <li class="w10">状态</li>
                    <li class="w25">操作</li>
                </ul>
            </div><!-- h_mian_nav -->

            <div class=" h_content_all l_all dongtai pagen"  value="${pagenow}">
                    <c:forEach items="${goodslist}" var="list">
                    <ul class="h_contents l_all ">
                        <li class="w20">${list.create_time}</li>
                        <li class="w20">${list.zgoods_name}</li>
                        <li class="w10">${list.zgoods_rmb}</li> 
                        <li class="w15">${list.zgoods_num}</li>
                         <c:choose>
							<c:when test="${list.status=='1'}">  
    		  					<li class="w10">审核中</li>
							</c:when>
							<c:when test="${list.status=='2'}">  
    		  					<li class="w10">审核通过未交费</li>
							</c:when>
							<c:when test="${list.status=='3'}">  
    		  					<li class="w10">审核拒绝</li>
							</c:when>
							<c:otherwise> 
			 					<li class="w10">已缴费未上架</li>
							</c:otherwise>
				      	 </c:choose>
              
                        <li class="w25 ">
                        	<c:choose>
								<c:when test="${list.status=='1'}"> 
									<a href="javascript:;" class="h_mx dk fl bianji tan2 publish_not_ready_detail1" style="color:#fff !important;width:100px;" value="${list.id}">查看详情</a> 
								</c:when>
								<c:when test="${list.status=='2'}">  
	    		  					<a href="javascript:;" class="h_mx dk fl bianji tan3 publish_not_ready_detail2" style="color:#fff !important;width:100px;" value="${list.id}">查看详情</a>
								</c:when>
								<c:when test="${list.status=='3'}">  
	    		  					<a href="javascript:;" class="h_mx dk fl bianji tan2 publish_not_ready_detail1" style="color:#fff !important;width:100px;" value="${list.id}">查看详情</a>
								</c:when>
								<c:otherwise> 
				 					<a href="javascript:;" class="h_mx dk fl bianji tan4 publish_not_ready_detail3" style="color:#fff !important;width:100px;" value="${list.id}">查看详情</a>
								</c:otherwise>
				      	   </c:choose>
                               
                        </li>
                    </ul>
                     </c:forEach>
                  
            </div><!-- h_mian_nav -->

			<div class="tcdPageCode" value="${pagelist }">
             </div>
           <%--  <div class="h_pages l_all">
                
                    <a href="#" class="h_previous dk fl">Previous</a>
                   <c:forEach items="${pagelist}" var="listn">
                    	<a href="#" class="h_yema dk fl pagenowa" value="${listn.page}">${listn.page}</a>
                   
                    </c:forEach>
                    <a href="#" class="h_next dk fl">Next</a>

            </div> --%>

        </div><!-- h_content_mian -->
 <script>
 $(".tcdPageCode").createPage({
     pageCount:$(".tcdPageCode").attr("value"),
     current:$(".pagen").attr("value"),
     backFn:function(p){
    	 var typel=$(".showtype").val();
 		if(typel==null){typel = 0;}
 		var whyl=$(".showwhy").attr("value");
 		if(whyl==null){whyl = 0;}
 		var name=$(".searchname").val();		
 		$.post(
 				url='<%=basePath%>/zeroBusiness.do?publish_zeronot_ready_list',
 				data={
 						pagenow : pagenow,
 						typel : typel,
 						whyl : whyl,
 						name : name
 						},
 				function(result){
 					$("#qwertyui").empty().html(result);
 					
 				},'text');
     }
 });

 
 $(".publish_not_ready_detail3").bind('click',function(){
	 var id=$(this).attr("value");
	 $.post(
				url='<%=basePath%>/zeroBusiness.do?publish_zeronot_ready_detail',
				data={id: id},
				function(result){
					$("#tan4detail").empty().html(result);
					
				},'text');
	});	

 $(".publish_not_ready_detail2").bind('click',function(){
	 var id=$(this).attr("value");
	 $.post(
				url='<%=basePath%>/zeroBusiness.do?publish_zeronot_ready_detail',
				data={id: id},
				function(result){
					$("#tan3detail").empty().html(result);
					
				},'text');
	});	

 $(".publish_not_ready_detail1").bind('click',function(){
	 var id=$(this).attr("value");
	 $.post(
				url='<%=basePath%>/zeroBusiness.do?publish_zeronot_ready_detail',
				data={id: id},
				function(result){
					$("#tan2detail").empty().html(result);
					
				},'text');
	});	
 </script> 
 
     
 <script >
/* 调用弹框*/
 $('.tan1').click(function(event) {
    pop_show1();
 });
 $('.tan2').click(function(event) {
    pop_show2();
 });
 $('.tan3').click(function(event) {
    pop_show3();
 });
$('.tan4').click(function(event) {
    pop_show4();
 });
 

/* 调用弹框*/

 </script>