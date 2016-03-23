<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>

 <div class="h_content_mian l_all">
           
            <div class="h_mian_bt  l_all">我的对账单</div>
            
            <div class="h_mian_nav l_all dongtai">
                
                <ul>
                    <li class="w25">时间</li>
                    <li class="w25">内容</li>
                    <li class="w25">类型（收/支）</li> 
                    <li class="w25">金额（元）</li>
                  
                </ul>
            </div><!-- h_mian_nav -->

            <div class=" h_content_all l_all dongtai pageno"  value="${pagenow}">
                    <c:forEach items="${payzero}" var="zero">
                    <ul class="h_contents l_all ">
                        <li class="w25">${zero.create_time }</li>
                        <li class="w25">${zero.zserial_num}期商品${zero.zgoods_name }交付保证金+服务费</li>
                        <li class="w25 income"> 
                        <c:choose>
							<c:when test="${zero.payment_status=='1'}">  
    		  					支出
							</c:when>
							<c:when test="${zero.payment_status=='2'}">  
    		  					支出
							</c:when>
							<c:otherwise> 
			 					未付款
							</c:otherwise>
					    </c:choose>
					    </li>
					     <c:choose>
							<c:when test="${zero.payment_status=='1'}">  
    		  					<li class="w25">${zero.deposit+zero.servicefee }</li> 
							</c:when>
							<c:otherwise> 
			 					<li class="w25">${zero.deposit}</li>
							</c:otherwise>
					    </c:choose>
                            
                    </ul>
                 
                   </c:forEach>

                   
            </div><!-- h_mian_nav -->

			 <div class="tcdPageCode" value="${pagelist}">
             </div>
        </div><!-- h_content_mian -->
		<script>
		    $(".tcdPageCode").createPage({
		        pageCount:$(".tcdPageCode").attr("value"),
		        current:$(".pageno").attr("value"),
		        backFn:function(p){
		    		var type=$(".showtype").val();
		    		if(type==null){type = 0;}
		    		var why=$(".showwhy").attr("value");
		    		if(why==null){why = 0;}
		    		var name=$(".showname").attr("value");
		    		$.post(
		    				url='<%=basePath%>/storeBusiness.do?zero_account',
		    				data={
		    						pagenow : p,
		    						type : type,
		    						order : why,
		    						name : name
		    						},
		    				function(result){
		    					
		    					$("#qwertyui").empty().html(result);
		    					
		    				},'text');
		        }
		    });
        </script>

        