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
    <title>商品列表</title>
    <link rel="stylesheet" href="webpage/js/all.css">
    
    <link rel="stylesheet" href="webpage/js/h_style.css">
    <style>
        .h_mx{margin: 20px 0 0 100px;}
        .status{margin-left: 100px;}
         @media (max-width: 1200px) and (min-width:900px){
            .h_mx {margin: 20px 0 0 40px;}
            
            .h_service{padding: 20px 0;}
            .all_fw{width:80px;}
           
            .h_shai{margin-right: 5px;}
            .h_search{margin-left: 0 !important;width:180px;}
            .h_sort{margin-left: 5px;font-size: 12px;}
        }
         @media (max-width: 1550px) and (min-width: 1201px){
            .h_mx {margin: 20px 0 0 50px;width:90px !important;}
            .h_ce {margin: 20px 0 0 8px !important;font-size: 13px;width:85px !important;}
            .h_service{padding: 20px 0;}
            .all_fw{width:80px;}
            .h_shai{margin-right: 5px;}
            .h_search{margin-left: 0 !important;width:180px;}
            .h_sort{margin-left: 15px;}
        }
    </style>
</head>
<body>

<div class="theme-popover theme2"style="display:block;" ><!-- 点击按钮，调用theme2事件， -->
    <div class="theme-poptit">
        <h3 class="fl">商品信息<span class="status fr">商品状态：<b>未开奖</b></span><span class="status fr">商品序列号：<b>${goods.serialNum}</b></span></h3>
    </div>

    <!-- 弹窗内容模块 开始-->
    <div class="popbod" >
        <div class="h_shop_information">
            <span class="shop_img dk fl"><a href="${goods.goodsHeadurl}" target="_blank"><img src="${goods.goodsHeadurl}" alt=""></a> </span>
            <div class="shop_inf fl">
                <p>
                    <span class="inf_title fl">商品名称：</span>
                    <span class="inf_detail fl">${goods.goodsName}</span>
                </p>
                <p>
                    <span class="inf_title fl">商品单价（元）：</span>
                    <span class="inf_detail fl">${goods.goodsRmb}</span>
                </p>
                <p>
                    <span class="inf_title fl">类别：</span>
                    <span class="inf_detail fl"><c:if test="${fn:contains(goods.goods10 , '1')}">十元专区</c:if>  ${goods.goodsType}</span>
                </p>
                <p>
                    <span class="inf_title fl">商品的总期数：</span>
                    <span class="inf_detail fl">${single.goodsAllNum}</span>
                </p>
                 <p>
                    <span class="inf_title fl">当前商品的期数：</span>
                    <span class="inf_detail fl">${single.goodsCurrentNum}</span>
                </p>
               <%-- <p>
                    <span class="inf_title fl">简介：</span>
                    <span class="inf_detail fl kuan">
                        ${goods.goodsContent}
                    </span>
                </p>--%>
            </div>
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

         <h2 class="coupon_inf" style="margin:0 0 10px 0;">预设幸运号</h2>
        <div class="luky_number">
        	
             已设幸运号码：<b>${single.luckyId }</b>
        </div>

       


        <h2 class="coupon_inf" style="margin:0 0 10px 0;">商品夺宝进度</h2>
        <div class="h_schedule">
            <div class="schedule">
                <div class="in_schedule"></div>
            </div>
            <div class="schedule_nums">
                <span class="fl">总需<b id="all_num">${single.allJoinNum}</b>人次</span>
                <span class="fr">剩余<b id="remainder">${single.allJoinNum - single.currentJoinNum}</b></span>
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

        <h2 class="coupon_inf">商家已付金额</h2>
       <div class="luky_number">
           <span class="contanct"style="font-weight:bold;font-size:18px;">
            <b>保证金：${payment.deposit}（元）           服务费：${payment.servicefee}（元）</b>
           </span>
            <span style="margin-right:100px;width:100%;height:30px;display:block;font-weight:bold;color:#3f3f3f;"><b>应付金额=保证金+服务费。保证金=单价*数量（期数）*40%，服务费=单价*数量（期数）*10%。当用户关于该商品的晒单数量=该商品的期数时，保证金将如数退回。</b></span>
        </div>
       
       
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
</div><!-- theme-popover -->
 <script src="webpage/js/jquery-1.11.1.min.js"></script>
  <script src="webpage/js/tan.js"></script>
 



 </script>
</body>
</html>