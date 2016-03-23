<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
    <title>商品管理-帮助</title>
    <link rel="stylesheet" href="css/all.css">
    <link rel="stylesheet" href="css/denglu.css">
    <link rel="stylesheet" href="css/h_style.css">
    <style>
        .h_register_all{width:60%;margin-left: 50px;}
        .h_register_all h2{text-align: left !important;padding-left: 0;margin-bottom: 20px; font-size:18px;}
        .h_register_all p{font-size: 16px;margin-bottom: 5px;}
        .re_input,.re_text{width:100%;border:none;}
        .number{bottom: 36px;}
    </style>
</head>
<body>



<div class="l_right ">



    <div class="l_moban"><!-- 模板 style -->
        <div class="h_content_mian l_all">

            <!--  <div class="h_mian_bt  l_all">发布商品</div>  -->

            <div class=" h_content_all l_all dongtai" style="border:none;">
                <div class="h_register_all" style="margin-top:40px;">
                    <h2> ①发布普通商品或精品商品</h2>

                    <p>填写帮助：</p>

                    <p>→填写商品名称（如：帅气百搭英伦马丁靴，35码/36码/37码）</p>

                    <p>→填写商品单价（大于20元，且必须是整数）</p>

                    <p>→填写参与上架的商品数量（若数量为5，意味着本商品可以出售5次）</p>

                    <!-- <p>→填写简介（纯文字，建议不要太长）</p> -->

                    <p>→商品封面（商品的封面照片，请选择正方形的图片。选择完请点击图片下方的“确认上传”按钮）</p>

                    <p>→商品详情页面图片（商品详情页面显示的图片，一张。选择完请点击图片下方的“确认上传”按钮）</p>

                    <p>→选择类别</p>

                    <p>→选择是否设为10元专区商品（精品商品没有该选项）</p>

                    <p>→优惠券链接（请填写您的淘宝或京东店铺的优惠券链接）</p>

                    <p>→填写优惠券面值（请填写该优惠券链接里的优惠券的面值）</p>

                    <p>→点击“发布”按钮。</p>



                    <h2 style="margin-top:20px;">②等待后台审核商品</h2>

                    <p>若长时间未审核通过，请联系我们。</p>



                    <h2 style="margin-top:20px;">③按要求缴纳一定的保证金和服务费</h2>

                    <p>保证金=单价*数量（期数）*40%，服务费=单价*数量（期数）*10%。当该商品的晒单数量=该商品的期数时，保证金将如数退回。</p>

                    <p>Q1：为什么要收取保证金？</p>

                    <p>A：避免商家没有如期发货。</p>



                    <h2 style="margin-top:20px;">④等待后台将商品上架</h2>

                    <p>若后台长时间未将您的商品上架，请联系我们。</p>


                    <h2 style="margin-top:20px;">⑤收到商品售出的短信后，根据购买者的收货地址发货，并点击“确定发货”按钮</h2>


                    <p>Q2：如何查看购买者的收货地址？</p>

                    <p>A：点开“普通商品管理（或精品商品管理）”，打开“未发货商品列表（或未发货精品商品列表）”，找到您要发货的商品，并点击“查看详情”按钮，就可以看到购买者的收货地址。</p>


                    <h2 style="margin-top:20px;">⑥等待或提醒购买者晒单</h2>

                    <p>若购买者长时间未晒单，请联系购买者。</p>

                    <p>Q3：如何查看购买者的联系方式？</p>

                    <p>A：点开“普通商品管理（或精品商品管理）”，打开“未发货商品列表（或未发货精品商品列表）”，找到您要发货的商品，并点击“查看详情”按钮，就可以看到购买者的联系方式。</p>



                    <h2 style="margin-top:20px;">⑦所有购买者晒单完成时，后台将线下返还保证金</h2>

                    <p>若后台长时间未返还保证金，请联系我们。</p>
                    <h2 style="margin-top:20px;">⑧收到保证金，本商品出售结束</h2>
                    <br/>
                    <h2 style="margin-top:20px;">联系我们:</h2>
                    <p style="margin-left:6px;">Q Q：2926342287</p>
                    <p>电话：0316-2555213</p>
                    <p>微信：zaixian91</p>
                    <br/>
                </div>
            </div>
        </div><!-- h_content_mian -->

    </div><!-- 模板 end-->
</div><!-- l_right 右边内容 -->



<script src="<%=path%>js/jquery-1.11.1.min.js"></script>
<script src="<%=path%>js/menu.js"></script>

</body>
</html>