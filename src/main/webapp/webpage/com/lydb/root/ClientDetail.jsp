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
    <title>用户详情</title>
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
<div class="theme-popover theme1" style="top:0;width:100%;margin-bottom:0px;display:block;">
    <div class="theme-poptit">
        <h3 class="fl">用户信息</h3>
    </div>
    <!-- 弹窗内容模块 开始-->
    <div class="popbod">
       <div class="h_shop_information">
            <span class="shop_img dk fl" style="width:150px;height:150px;"><img src="${appclient.headImg }" alt=""></span>
            <div class="shop_inf fl" style="width:400px;">

                <p>
                    <span class="inf_title fl">昵称：</span>
                    <span class="inf_detail fl">${appclient.clientName }</span>
                </p>
                <p>
                    <span class="inf_title fl">手机号：</span>
                    <span class="inf_detail fl">${appclient.mobile }</span>
                </p>
                <p>
                    <span class="inf_title fl">QQ：</span>
                    <span class="inf_detail fl">${appclient.clientQq }</span>
                </p>
                <p>
                    <span class="inf_title fl">推广ID：</span>
                    <span class="inf_detail fl">${appclient.popularizeId }</span>
                </p>
                <p>
                    <span class="inf_title fl">收货地址:</span>
                    <span class="inf_detail fl kuan" style="margin-left:10px;">
                      ${address.cityId}${address.shipAddress }
                    </span>
                </p>
            </div>

            <div class="user_eassy fl">
                    <h2 class="coupon_inf" style="margin:0 0 10px 0;">用户动态</h2>
                    <div class="luky_number">
                         <ul>
                             <li >抢币余额：<b>${rmb.rmb }</b></li>
                             <li class="left">积分余额：<b>${rmb.integrate }</b></li>
                             <li >购买次数：<b>${appclient.joinNum}</b></li>
                             <li class="left">中奖次数：<b>${appclient.winNum }</b></li>
                             <li >邀请人数：<b>${appclient.inviteNum }</b></li>
                
                         </ul>
                    </div>
            </div>
        </div>

        <h2 class="coupon_inf">晒单详情</h2>
        <div class="share_orders">
            <ul>
                 <c:forEach items="${list}" var="date">
                <li>
                    <div class="share_box">
                        <h2>${date.get("share_title")}</h2>
                        <h3>${date.get("share_time")}</h3>
                        <div class="luky_if">
                            <span>获奖产品：<b >（第${date.get("goods_current_num")}期）${date.get("goods_name")} </b></span>
                            <span>本期参与：<b>${date.get("all_join_num")}</b>人次</span>
                            <span>幸运号码：<b>${date.get("lucky_id")}</b></span>
                            <span>揭晓时间：<em>${date.get("open_time")}</em></span>
                        </div>
                        <p>${date.get("share_content")}</p>
                        <c:forEach items="${date.get(\"allImgUrl\")}" var="imgurls">
                       		 <img src="${imgurls}" alt="">    
                        </c:forEach>
                    </div>   
                </li>
                </c:forEach>
            </ul>
        </div>
       

      
    </div><!-- popbod-->
    <!-- 弹窗内容模块 结束-->
    

</div><!-- theme-popover -->
</body>
</html>
