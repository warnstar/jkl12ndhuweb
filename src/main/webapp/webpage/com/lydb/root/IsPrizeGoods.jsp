<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="IsPrizeGoods" fitColumns="false" title="已开奖商品列表"
                    actionUrl="BusManagement.do?goodsdatagrid&dbWebBusinessid=${id}&type=6" pageSize="50" idField="id"
                    fit="true" queryMode="group">
            <!--当前的id属性为商品中每一个开奖商品的id db_goods_single的id -->
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="商品序列号" field="serial_num" align="center" query="true"></t:dgCol>
            <t:dgCol title="商品的封面图" image="true"  imageSize="120,90" field="goods_headurl"></t:dgCol>
            <t:dgCol title="商品上架时间" field="star_time" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
            <t:dgCol title="商品开奖时间" field="open_time" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>

            <t:dgCol title="当前期数" field="goods_current_num" align="center"></t:dgCol>
            <t:dgCol title="总期数" field="goods_all_num" align="center"></t:dgCol>

            <t:dgCol title="商品名称" field="goods_name" align="center" query="true" width="200"></t:dgCol>
            <t:dgCol title="商品单价（元）" field="goods_rmb" align="center"></t:dgCol>
            <t:dgCol title="幸运号" field="lucky_id" align="center"></t:dgCol>
            <t:dgCol title="中奖用户" field="mobile" align="center"></t:dgCol>
            <t:dgCol title="是否已晒单" field="share" replace="未晒单_0,已晒单_1" align="center"></t:dgCol>
            <t:dgCol title="商品状态" field="singlestatus" replace="已开奖未发货_2,已开奖已发货_3,已开奖已收货_4" align="center"
                     query="true"></t:dgCol>

            <t:dgCol title="所属商店名称" field="shopname" align="center"></t:dgCol>


            <t:dgCol title="操作" field="opt" align="center" width="200"></t:dgCol>

            <t:dgFunOpt funname="LookGoodsDeteil(id)" title="查看商品详情"></t:dgFunOpt>
            <t:dgFunOpt funname="LookJoinDeteil(id)" title="查看参与用户详情"></t:dgFunOpt>


        </t:datagrid>
    </div>
</div>
<script src="<%=basePath %>/webpage/js/myJacaScript.js"></script>
<script type="text/javascript">

    function LookGoodsDeteil(id) {
        $.dialog({
            content: "url:BusManagement.do?BusinessGoodsByType&GoodsID=" + id + "&Type=6",
            inline: true,
            lock: true,
            title: '商品' ,
            opacity: 0.3,

            width: 950,
            height:  heigth * 0.8
        }).zindex();
    }
    function LookJoinDeteil(id) {
        $.dialog({
            content: "url:GoodsManagement.do?checkJoinClient&id=" + id,
            inline: true,
            lock: true,
            title: '商品',
            opacity: 0.3,

            width: width * 0.8,
            height:  heigth * 0.8
        }).zindex();

    }

</script>

