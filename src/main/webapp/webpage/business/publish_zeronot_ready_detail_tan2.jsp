<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
  <div class="theme-poptit">
     
         <h3 class="fl">商品信息<span class="status fr">商品状态：<b> <c:choose>
							<c:when test="${detail.status=='1'}">  
    		  					审核中
							</c:when>
							<c:when test="${detail.status=='2'}">  
    		  					审核通过未交费
							</c:when>
							<c:when test="${detail.status=='3'}">  
    		  					审核拒绝
							</c:when>
							<c:otherwise> 
			 					已缴费未上架
							</c:otherwise>
				      	 </c:choose> </b></span></h3>
        <a href="javascript:;" title="关闭" class="close fr">×</a>
    </div>

    <!-- 弹窗内容模块 开始-->
    <div class="popbod" >
    
        <div class="h_shop_information">
            <span class="shop_img dk fl"><img src="${detail.zgoodsHeadurl }" alt=""></span>
            <div class="shop_inf fl">
                <p>
                    <span class="inf_title fl">商品名称：</span>
                    <span class="inf_detail fl">${detail.zgoodsName}</span>
                </p>
                <p>
                    <span class="inf_title fl">商品单价（元）：</span>
                    <span class="inf_detail fl">${detail.zgoodsRmb}</span>
                </p>
                <p>
                    <span class="inf_title fl">参与上架的商品数量：</span>
                    <span class="inf_detail fl">${detail.zgoodsNum}</span>
                </p>
               
               <%-- <p>
                    <span class="inf_title fl">简介：</span>
                    <span class="inf_detail fl kuan">
                   ${detail.zgoodsContent}
                    </span>
                </p>--%>
            </div>
        </div>

<c:if test="${coupon != null}">
        <h2 class="coupon_inf">商品优惠券信息</h2>

        <div class="h_shop_information" style="height:150px;">
            <div class="shop_coupon fl pr">
                <span class="money">¥</span>
                <span class="number">${coupon.couponValue }</span>
                <span class="coupon_user">商家：<b>${coupon.businessName}</b></span>
            </div>
            <div class="shop_inf fl" style="height:150px;">
                <p>
                    <span class="inf_title fl">商品优惠券链接：</span>
                    <span class="inf_detail fl">${coupon.couponUrl}</span>
                </p>
                <p>
                    <span class="inf_title fl">商品优惠券面值（元）：</span>
                    <span class="inf_detail fl">${coupon.couponValue}</span>
                </p>

            </div>
        </div>
    </c:if>
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
