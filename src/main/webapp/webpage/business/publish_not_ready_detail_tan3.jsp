<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
            <span class="shop_img dk fl"><img src="${detail.goodsHeadurl}" alt=""></span>
            <div class="shop_inf fl">
                <p>
                    <span class="inf_title fl">商品名称：</span>
                    <span class="inf_detail fl">${detail.goodsName}</span>
                </p>
                <p>
                    <span class="inf_title fl">商品单价（元）：</span>
                    <span class="inf_detail fl">${detail.goodsRmb}</span>
                </p>
                <p>
                    <span class="inf_title fl">参与上架的商品数量：</span>
                    <span class="inf_detail fl">${detail.goodsNum}</span>
                </p>
                <p>
                    <span class="inf_title fl">类别：</span>
                    <span class="inf_detail fl">${detail.goodsType}</span>
                </p>
               <%-- <p>
                    <span class="inf_title fl">简介：</span>
                    <span class="inf_detail fl kuan">
                   ${detail.goodsContent}
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
        <h2 class="coupon_inf">应付金额（元）</h2>
        <h4 class="money_num">${money}</h4>
        <p style="font-size:14px;font-weight:600;">应付金额=保证金+服务费。保证金=单价*数量（期数）*40%，服务费=单价*数量（期数）*10%。当用户关于该商品的晒单数量=该商品的期数时，保证金将如数退回。</p>
        <p>
        <div style="width:230px;margin:0 auto;height: 52px;">
            <button  class="go_pay" id="payfor" style="float:left;margin-right:20px;">去付款</button>
            <button id="payfinish" class="go_pay" style="float:left;">支付完成</button>
        </div>
        </p>
    </div><!-- popbod-->

    <!-- 弹窗内容模块 结束-->
<div class="theme-popover-mask"  ></div><!-- 所有弹窗共用这一个黑屏，不需要复制 -->
<!-- 未售出商品详情弹框 调用的类是 tan2-->
<div class="theme-popover theme3" id="notsend123" ><!-- 点击按钮，调用theme3事件， -->

</div><!-- theme-popover -->
<script src="<%=path %>js/tan.js"></script>
    <script>
        $("#payfor").on('click',function(){
            var id="${detail.id}";
            $.post(
                    url="<%=basePath%>/rest/app_lydb/alipayBusiness/deposit",
                    data={id : id},
                    function(result){
                        $("#notsend123").empty().html(result);
                    }
            )
        })
        $("#payfinish").bind("click", function() {
            location.reload();
        })
    </script>