<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>0元商品列表</title>
    <link rel="stylesheet" href="webpage/js/all.css">
    
    <link rel="stylesheet" href="webpage/js/h_style.css">
    <style>
        .h_mx{margin: 20px 0 0 50px;}
         @media (max-width: 1200px) and (min-width:900px){
            .h_mx {margin: 20px 0 0 5px;font-size: 13px;width:65px !important;}
            .h_ce {margin: 20px 0 0 5px !important;font-size: 13px;width:85px !important;}
            .h_service{padding: 20px 0;}
            .all_fw{width:80px;}
            .tan1{width:50px !important;}
            .h_shai{margin-right: 5px;}
            .h_search{margin-left: 0 !important;width:180px;}
            .h_sort{margin-left: 5px;}
        }
         @media (max-width: 1550px) and (min-width: 1201px){
            .h_mx {margin: 20px 0 0 10px;width:90px !important;}
            .h_ce {margin: 20px 0 0 8px !important;font-size: 13px;width:85px !important;}
            .h_service{padding: 20px 0;}
            .all_fw{width:80px;}
            .h_shai{margin-right: 5px;}
            .h_search{margin-left: 0 !important;width:180px;}
            .h_sort{margin-left: 15px;}
        }
    </style>
    <script type=text/javascript src="webpage/js/jquery-1.11.1.min.js"></script>
</head>
<body>

<script>
    var nWidth, nHeight
    function test(img){
        if (img.naturalWidth) { // 现代浏览器
            nWidth = img.naturalWidth
            nHeight = img.naturalHeight
        } else { // IE6/7/8
            var imgae = new Image()
            image.src = img.src
            nWidth= image.width;
            nHeight =image.height;
        }


        $("#test").html(nWidth+" x "+nHeight )
    }
    function test2(img){
        if (img.naturalWidth) { // 现代浏览器
            nWidth = img.naturalWidth
            nHeight = img.naturalHeight
        } else { // IE6/7/8
            var imgae = new Image()
            image.src = img.src
            nWidth= image.width;
            nHeight =image.height;
        }


        $("#test2").html(nWidth+" x "+nHeight )
    }
</script>

<!-- 已拒绝或审核中的商品详情弹框 调用的类是 tan2-->
<div class="theme-popover theme2" style="display:block;"><!-- 点击按钮，调用theme2事件， -->
     <div class="theme-poptit">
        <h1 class="fl" style="font-size:32px;">0元商品信息<span class="status fr">商品状态：<b>
        <c:choose>
		    <c:when test="${fn:contains(goods.status , '1')}">
		     	  未审核
		    </c:when>
		    <c:when test="${fn:contains(goods.status , '3')}">
		    	 已拒绝
		    </c:when>
		    <c:otherwise>
		      
		    </c:otherwise>
	</c:choose>
        </b></span></h1>
    </div>

    <!-- 弹窗内容模块 开始-->
    <div class="popbod" >
        <div class="h_shop_information" style="height:460px;">
            <span class="shop_img dk fl"><a href="${goods.zgoodsHeadurl}" target="_blank"> <img src="${goods.zgoodsHeadurl}" alt="" onload="test(this)"></a></span>
            <div class="shop_inf fl">
                <p>
                    <span class="inf_title fl">商品名称：</span>
                    <span class="inf_detail fl">${goods.zgoodsName}</span>
                </p>
                <p>
                    <span class="inf_title fl">商品单价（元）：</span>
                    <span class="inf_detail fl">${goods.zgoodsRmb}</span>
                </p>
               
                <p>
                    <span class="inf_title fl">参与夺宝的商品数量：</span>
                    <span class="inf_detail fl">${goods.zgoodsNum}</span>
                </p>
                <%--<p>
                    <span class="inf_title fl">简介：</span>
                    <span class="inf_detail fl kuan">
                  ${goods.zgoodsContent}
                    </span>
                </p>--%>
            </div>
            <span  style="margin:20px auto;"><a href="${imgurl}" target="_blank"><img src="${imgurl}" alt="" width="640" height="240" onload="test2(this)"/></a> </span>
            <br>
            <span  style="margin:20px auto;">封面图尺寸：<span id="test"></span></span>
            <br>
            <span  style="margin:20px auto;">详情图尺寸：<span id="test2"></span></span>
        </div>

        <h2 class="coupon_inf">商品优惠券信息</h2>

        <div class="h_shop_information" style="height:150px;">
            <div class="shop_coupon fl pr">
                <span class="money">¥</span>
                <span class="number">${coupon.couponValue}</span>
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

        <h2 class="coupon_inf" style="margin:0 0 10px 0;">商家联系方式</h2>
        <div class="luky_number">
            <span class="contanct fl">商家用户名：<b>${business.mobile}</b></span>
            <span class="contanct fr" style="margin-right:50px;">QQ：<b>${business.businessQq}</b></span>
            <br>
            <span class="contanct fl">商家昵称：<b>${business.shopname}</b></span>
            <span class="contanct fr" style="margin-right:50px;">店名：<b>${business.name}</b></span>
        </div>
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
</div><!-- theme-popover -->


 
</body>
</html>