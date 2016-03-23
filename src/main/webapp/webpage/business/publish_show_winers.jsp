<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<div class="h_shop_information">

           <c:forEach items="${winer}" var="winer">
           <span class="shop_img dk fl" style="width:150px;height:150px;"><img src="${winer.head_img}" alt=""></span>
            <div class="shop_inf fl" style="width:400px;">
                <p>
                    <span class="inf_title fl">昵称：</span>
                    <span class="inf_detail fl">${winer.client_name}</span>
                </p>
                <p>
                    <span class="inf_title fl">手机号：</span>
                    <span class="inf_detail fl">${winer.mobile}</span>
                </p>

                <p>
                    <span class="inf_title fl">QQ：</span>
                    <span class="inf_detail fl">${winer.client_qq}</span>
                </p>
                <p>
                    <span class="inf_title fl">推广ID：</span>
                    <span class="inf_detail fl">${winer.popularize_id}</span>
                </p>
                <p>
                    <span class="inf_title fl">收货地址:</span>
                    <span class="inf_detail fl kuan" style="margin-left:10px;">
                            ${winer.ship_address}
                    </span>
                </p>

            </div>
           </c:forEach>
</div>