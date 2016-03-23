<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
  <div class="theme-poptit">
         <h3 class="fl">商品信息<span class="status fr">商品状态：<b>审核中</b></span><span class="status fr">本商品期数：<b>25</b></span></h3>
        <a href="javascript:;" title="关闭" class="close fr">×</a>
    </div>

    <!-- 弹窗内容模块 开始-->
    <div class="popbod" >
        <div class="h_shop_information">
            <span class="shop_img dk fl"><img src="images/shop.png" alt=""></span>
            <div class="shop_inf fl">
                <p>
                    <span class="inf_title fl">商品名称：</span>
                    <span class="inf_detail fl">董文华读物</span>
                </p>
                <p>
                    <span class="inf_title fl">商品单价（元）：</span>
                    <span class="inf_detail fl">90</span>
                </p>
                <p>
                    <span class="inf_title fl">参与上架的商品数量：</span>
                    <span class="inf_detail fl">9</span>
                </p>
                <p>
                    <span class="inf_title fl">类别：</span>
                    <span class="inf_detail fl">十元专区 手机平板</span>
                </p>
                <%--<p>
                    <span class="inf_title fl">简介：</span>
                    <span class="inf_detail fl kuan">
                        颜色随机 唯一的不同是处处不同 联通移动电信 三网通 正式起售（我只是想凑齐50个字，最多50字哦）
                    </span>
                </p>--%>
            </div>
        </div>
<c:if test="${coupon != null}">
        <h2 class="coupon_inf">商品优惠券信息</h2>

        <div class="h_shop_information" style="height:110px;">
            <div class="shop_coupon fl pr">
                <span class="money">¥</span>
                <span class="number">10</span>
                <span class="coupon_user">商家：<b>大师济南万阿</b></span>
            </div>
            <div class="shop_inf fl" style="height:100px;">
                <p>
                    <span class="inf_title fl">商品优惠券链接：</span>
                    <span class="inf_detail fl">www.sdjwdiwh.com</span>
                </p>
                <p>
                    <span class="inf_title fl">商品优惠券面值（元）：</span>
                    <span class="inf_detail fl">10</span>
                </p>
               
            </div>
        </div>
</c:if>
        <h2 class="coupon_inf">应付金额</h2>
        <h4 class="money_num">450</h4>
        <p style="font-size:14px;font-weight:600;">应付金额=保证金+服务费。保证金=单价*数量（期数）*40%，服务费=单价*数量（期数）*10%。当用户关于该商品的晒单数量=该商品的期数时，保证金将如数退回。</p>
       
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->