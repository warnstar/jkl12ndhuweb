<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

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

<div class="theme-popover theme2"style="display:block;width:100%;" ><!-- 点击按钮，调用theme2事件， -->
    <div class="theme-poptit">
        <h3 class="fl">0元商品信息<span class="status fr">商品状态：<b>
        <c:choose>
            <c:when test="${fn:contains(single.status , '2')}">
                  已开奖，未发货
            </c:when>
            <c:when test="${fn:contains(single.status , '3')}">
                 已开奖，已发货
            </c:when>
            <c:otherwise>
              
            </c:otherwise>
    </c:choose></b></span><span class="status fr">0元商品序列号：<b>${goods.zserialNum}</b></span></h3>
    </div>

    <!-- 弹窗内容模块 开始-->
    <div class="popbod" >
        
                        <div class="h_shop_information">
                            <span class="shop_img dk fl"><a href="${goods.zgoodsHeadurl}" target="_blank"> <img src="${goods.zgoodsHeadurl}" alt=""></a></span>
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
                                         ${goods.zgoodsContent}
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

                        <h2 class="coupon_inf" style="margin:0 0 10px 0;">往期中奖用户</h2>
                        <div class="h_usered">
                            <ul>
                                <c:forEach items="${dateMaps}" var="date">
                                <li class="tan1" onclick='clicktest(this)'>
                                    <input type="hidden"  class="test123" value='${date.get("db_app_clientid")}' />
                                    <p class="bold">第${date.get("goods_current_num")}期夺宝号：<b>${date.get("lucky_id")}</b></p>
                                    <p>对应用户名：<b>${date.get("mobile")}</b></p>
                                    <p>用户晒单：<b>
                                        <c:choose>
                                            <c:when test="${fn:contains(date.get('share') , '1')}">
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
                      
                        <h2 class="coupon_inf">应返还的保证金（元）</h2>
                   <div class="luky_number"style="height: 60px;">
                        <span class="contanct"style="font-weight:bold;font-size:18px;">
                      <b>保证金：${payment.deposit}（元）           服务费：${payment.servicefee}（元）</b>
                         </span>
                         <span style="margin-right:100px;width:100%;height:30px;display:block;font-weight:bold;color:#3f3f3f;"><b>保证金=单价*数量（期数）*40%。当用户关于该商品的晒单数量=该商品的期数时，保证金将如数退回。</b></span>
                    </div>
                     <h2 class="coupon_inf">保证金状态：</h2>
                   <div class="luky_number"style="height: 60px;">
                        <span class="contanct"style="font-weight:bold;font-size:18px;"><b>
                             <c:choose>
                                <c:when test="${fn:contains(payment.paymentStatus , '2')}">
                                    已返还
                                 </c:when>
                              <c:otherwise>
                                     未返还
                             </c:otherwise>
                             </c:choose>
                      
                        </b>（已晒单数量：${goods.zshareNum}/${goods.zgoodsNum}）</span>
                        <span style="margin-right:100px;width:100%;height:30px;display:block;font-weight:bold;color:#3f3f3f;"><b>注：保证金=单价*数量（期数）*40%。当该商品的晒单数量=该商品的期数时，保证金将如数退回。</b></span>
                    </div>
                   
      
       
       
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
</div><!-- theme-popover -->
<script>
    function clicktest(obj){
        var id = $(obj).find("input").attr("value");
        $.dialog.setting.zIndex = 2080;
        openwindow('用户详情',"AppClientManagement.do?ClientDetail&id="+id,"",1300,900);
    }

</script>

</body>
</html>
