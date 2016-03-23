<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
 <div class="theme-poptit">
 	 <c:forEach items="${detail}" var="listdetail1">
        <h3 class="fl">商品信息<span class="status fr">商品状态：<b>未售出</b></span><span class="status fr">商品期数：<b>${listdetail1.zserial_num}-${listdetail1.goods_current_num}</b></span></h3>
        <a href="javascript:;" title="关闭" class="close fr">×</a>
        </c:forEach>
    </div>

    <!-- 弹窗内容模块 开始-->
    <div class="popbod" >
        <div class="h_shop_information">
        <c:forEach items="${detail}" var="listdetail">
            <span class="shop_img dk fl"><img src="${listdetail.zgoods_headurl }" alt=""></span>
            <div class="shop_inf fl">
                <p>
                    <span class="inf_title fl">商品名称：</span>
                    <span class="inf_detail fl">${listdetail.zgoods_name }</span>
                </p>
                <p>
                    <span class="inf_title fl">商品单价（元）：</span>
                    <span class="inf_detail fl">${listdetail.zgoods_rmb }</span>
                </p>
          
                <p>
                    <span class="inf_title fl">参与上架的商品数量：</span>
                    <span class="inf_detail fl">${listdetail.zgoods_num }</span>
                </p>
                <p>
                    <span class="inf_title fl">当前参与人次：</span>
                    <span class="inf_detail fl kuan">
                    ${listdetail.current_join_num} /次
                    </span>
                </p>
            </div>
            </c:forEach>
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
       <%-- <h2 class="coupon_inf" style="margin:0 0 10px 0;">商品夺宝进度</h2>
        <div class="h_schedule">
            <div class="schedule">
                <div class="in_schedule"></div>
            </div>
            <div class="schedule_nums">
                 <c:forEach items="${detail}" var="detaillist">
                <span class="fl">总需<b id="all_num">${detaillist.zgoods_rmb }</b>人次</span>
                <span class="fr">剩余<b id="remainder">${detaillist.zgoods_rmb-detaillist.goods_current_num }</b></span>
                </c:forEach>
            </div>
        </div>--%>
        
        <h2 class="coupon_inf">应付金额</h2>
        <h4 class="money_num">${moeny}</h4>
        <p style="font-size:14px;font-weight:600;">应付金额=保证金+服务费。保证金=单价*数量（期数）*40%，服务费=单价*数量（期数）*10%。当用户关于该商品的晒单数量=该商品的期数时，保证金将如数退回。</p>
       
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->