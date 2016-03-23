<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String path = request.getContextPath()+"/webpage/business/";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>商家管理-已售出商品详情页面</title>
    <link rel="stylesheet" href="<%=path %>css/all.css">
    
    <link rel="stylesheet" href="<%=path %>css/h_style.css">
    <style>
      .h_content_all{
        border:none;
      }
      .h_mian_bt{margin-bottom: 30px;}
       @media (max-width: 1200px) and (min-width:900px){
        .h_usered ul li{width:30%;}
       }
    </style>
</head>
<body>



<div class="l_right ">

    <div class="l_moban"  style="padding:0;"><!-- 模板 style -->
       
        <div class="h_content_mian l_all">
           
            <div class="h_mian_bt  l_all">商品信息<span class="status">商品状态：<b>已发货</b></span>
                <a href="javascript:;" title="关闭" class="close fr">×</a>
            </div>

            

            <div class=" h_content_all l_all dongtai" style="width:90%;">
                       
                        <div class="h_shop_information">
                          
                            <span class="shop_img dk fl"><img src="${detail.zgoodsHeadurl }" alt=""></span>
                           <div class="shop_inf fl">
					               <p>
					                   <span class="inf_title fl">商品名称：</span>
					                   <span class="inf_detail fl"> ${detail.zgoodsName} </span>
					               </p>
					               <p>
					                   <span class="inf_title fl">商品单价（元）：</span>
					                   <span class="inf_detail fl">${detailzgoodsRmb}</span>
					               </p>
					               
					               <p>
					                   <span class="inf_title fl">参与上架的商品数量：</span>
					                   <span class="inf_detail fl">${detail.zgoodsNum}</span>
					               </p>
					               <%--<p>
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

                        <h2 class="coupon_inf" style="margin:0 0 10px 0;">往期购买用户</h2>
                        <div class="h_usered">
                            <ul>
                            	<c:forEach items="${userlist}" var="listwiner">
                                <li class="tan1 showwiners" value="${listwiner.id }" style="width:30%;">
                                    <p class="bold">第${listwiner.goods_current_num }期夺宝号：<b>${listwiner.lucky_id }</b></p>
                                    <p>对应用户名：<b>${listwiner.mobile}</b></p>
                                    <p>用户晒单：<b>
                                        <c:choose>
                                            <c:when test="${fn:contains(listwiner.share , '1')}">
                                                已晒单
                                            </c:when>
                                            <c:otherwise>
                                                未晒单
                                            </c:otherwise>
                                        </c:choose></b></p>
                                </li >
                               </c:forEach>
                            </ul>


                        </div> 

                        <h2 class="coupon_inf" style="margin:0 0 10px 0;">保证金状态</h2>                     
                        <div class="luky_number">
                            <b> <c:choose>
                                <c:when test="${detail.zshareNum}==${detail.zgoodsNum}">
                                     已完成
                                </c:when>
                                <c:otherwise>
                                     未返还
                                </c:otherwise>
                            </c:choose> </b>
                            	  <b>已晒单数量：${detail.zshareNum}/${detail.zgoodsNum}</b><!-- 或者已返还 -->
                            
                        </div>
                        <p style="font-size:14px;font-weight:600;">注：保证金=单价*数量（期数）*40%。当该商品的晒单数量=该商品的期数时，保证金将如数退回。</p>
                        <p>

                        <h2 class="coupon_inf">可得保证金（元）</h2>
                        <h4 class="money_num">${moeny}</h4>
                        <p style="font-size:14px;font-weight:600;">保证金=单价*数量（期数）*40%。当用户关于该商品的晒单数量=该商品的期数时，保证金将如数退回。</p>
            </div><!-- h_mian_nav -->

        </div><!-- h_content_mian -->
        
    </div><!-- 模板 end-->
</div><!-- l_right 右边内容 -->


<div class="theme-popover-mask"></div><!-- 所有弹窗共用这一个黑屏，不需要复制 -->

<!-- 删除弹框 调用的类是 tan1-->
<div class="theme-popover theme1"><!-- 点击按钮，调用theme1事件， -->
    <div class="theme-poptit">
        <h3 class="fl">用户信息</h3>
        <a href="javascript:;" title="关闭" class="close fr">×</a>
    </div>
    <!-- 弹窗内容模块 开始-->
    <div class="popbod" id="showwiner">
        
     
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
    

</div><!-- theme-popover -->

 <script src="<%=path %>js/jquery-1.11.1.min.js"></script>
 <script src="<%=path %>js/menu.js"></script>
  <script src="<%=path %>js/tan.js"></script>
 <script >
 close();
 $('.tan1').click(function(event) {
    pop_show1();
 });
 $('.showwiners').bind("click",function() {
	    var id=$(this).attr("value");
		$.post(
				url='<%=basePath%>/zeroSingleBusiness.do?publish_zeroshow_winers',
				data={
						id : id 
						},
				function(result){
					$("#showwiner").empty().html(result);
					
				},'text');
 });
 
/* 调用弹框*/

 </script>
 
</body>
</html>