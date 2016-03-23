<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
  <div class="theme-poptit">
        <c:forEach items="${detail}" var="listdetail1">
        <h3 class="fl">商品信息<span class="status fr">商品状态：<b>未发货</b></span><span class="status fr">商品期数：<b>${listdetail1.serial_num}-${number}</b></span></h3>
        <a href="javascript:;" title="关闭" class="close fr">×</a>
        </c:forEach>
    </div>

    <!-- 弹窗内容模块 开始-->
     <div class="popbod" >
        <div class="h_shop_information">
        <c:forEach items="${detail}" var="listdetail">
            <span class="shop_img dk fl"><img src=${listdetail.goods_headurl} alt=""></span>
            <div class="shop_inf fl">
                <p>
                    <span class="inf_title fl">商品名称：</span>
                    <span class="inf_detail fl">${listdetail.goods_name }</span>
                </p>
                <p>
                    <span class="inf_title fl">商品单价（元）：</span>
                    <span class="inf_detail fl">${listdetail.goods_rmb }</span>
                </p>
                <p>
                    <span class="inf_title fl">类别：</span>
                    <span class="inf_detail fl">${listdetail.goods_type }</span>
                </p>
                <p>
                    <span class="inf_title fl">参与上架的商品数量：</span>
                    <span class="inf_detail fl">${listdetail.goods_num }</span>
                </p>
              <%--  <p>
                    <span class="inf_title fl">简介：</span>
                    <span class="inf_detail fl kuan">
                    ${listdetail.goods_content}
                    </span>
                </p>--%>
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
        <h2 class="coupon_inf">已付金额</h2>
        <h4 class="money_num">${moeny}</h4>
        <p style="font-size:14px;font-weight:600;">应付金额=保证金+服务费。保证金=单价*数量（期数）*40%，服务费=单价*数量（期数）*10%。当用户关于该商品的晒单数量=该商品的期数时，保证金将如数退回。</p>

        <h2 class="coupon_inf">购买用户资料</h2>

        <div class="h_shop_information">
           <c:forEach items="${userlist}" var="userlist">
            <span class="shop_img dk fl" style="width:150px;height:150px;"><img src="${userlist.head_img }" alt=""></span>
           
            <div class="shop_inf fl" style="width:400px;">
                <p>
                    <span class="inf_title fl">昵称：</span>
                    <span class="inf_detail fl">${userlist.client_name}</span>
                </p>
                <p>
                    <span class="inf_title fl">手机号：</span>
                    <span class="inf_detail fl">${userlist.mobile }</span>
                </p>
                <p>
                    <span class="inf_title fl">QQ：</span>
                    <span class="inf_detail fl">${userlist.client_qq }</span>
                </p>
                <p>
                    <span class="inf_title fl">购买单号：</span>
                    <span class="inf_detail fl">${userlist.lucky_id }</span>
                </p>
                <p>
                    <span class="inf_title fl">收货地址:</span>
                    <span class="inf_detail fl kuan" style="margin-left:10px;">
                       ${userlist.ship_address }收货人电话${userlist.ship_phone }
                    </span>
                </p>
              
            </div>
            </c:forEach>
        </div>

    </div><!-- popbod-->
<!-- 弹窗内容模块 结束-->
    <div class="popbod_bottom"><!-- 此处放按钮 -->
            
            <div class="buttom_del buttom_del_bg2 del" style="margin-left:350px;width:200px;" id="suresend" value="${id}">确认发货</div><!-- 调用del这个类，公用的 -->
              
    </div><!-- popbod_bottom -->
    <script>
     $("#suresend").bind('click',function() {
	 var id=$("#suresend").attr("value");
	 $.post(
				url='<%=basePath%>/storeSingleBusiness.do?make_sure_send',
				data={
						id : id 
						},
				function(result){
				 	/* alert(result.obj); */
				 	$.post(
				 			url='<%=basePath%>/storeSingleBusiness.do?publish_goods_notsend&pagenow=1&search=0',
							data={},
							function(result){
				 				$('body').empty().html(result);
				 			},'text');
				},'json');
	 })
    </script>