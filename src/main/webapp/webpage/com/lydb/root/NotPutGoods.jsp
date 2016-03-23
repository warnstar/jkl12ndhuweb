<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="NotPutGoods" fitColumns="false" title="(带*进行模糊搜索)"
                    actionUrl="BusManagement.do?goodsdatagrid&status_begin=1&status_end=4&dbWebBusinessid=${id}&type=1"
                    sortName="createTime" sortOrder="desc" pageSize="30" idField="id" fit="true" queryMode="group"
                    queryBuilder="true">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="商家主键" field="dbWebBusinessid" hidden="true"></t:dgCol>
            <t:dgCol title="商品序列号" field="serialNum" align="center" query="true"></t:dgCol>
            <t:dgCol title="商品的封面图" image="true"  imageSize="120,90" field="goodsHeadurl"></t:dgCol>
            <t:dgCol title="申请时间" field="createTime" align="center" formatter="yyyy-MM-dd hh:mm:ss"
                     width="140"></t:dgCol>
            <t:dgCol title="商品名称" field="goodsName" align="center" query="true" width="200"></t:dgCol>

            <t:dgCol title="商品单价（元）" field="goodsRmb" align="center" ></t:dgCol>

            <t:dgCol title="参与夺宝的商品数量" field="goodsNum" align="center" ></t:dgCol>

            <t:dgCol title="商品类别" field="goodsType" align="center" query="true" ></t:dgCol>
            <t:dgCol title="是否十元专区" field="goods10" align="center" replace="是_1,否_0" query="true"></t:dgCol>

            <t:dgCol title="商品状态" field="status" align="center" replace="未审核_1,审核通过未缴费_2,审核拒绝_3,已缴费等待上架_4" query="true"
                    ></t:dgCol>

            <t:dgCol title="操作" field="opt" align="center" width="130"></t:dgCol>

            <t:dgFunOpt funname="LookGoodsDeteil(status,id)" title="查看商品详情"></t:dgFunOpt>
            <c:set var="theString" value="${user_role}"/>
            <c:if test="${fn:contains(theString, 'admin')||fn:contains(theString, 'root')}">
                <t:dgDelOpt title="common.delete" url="BusManagement.do?gooddel&id={id}"/>
            </c:if>
        </t:datagrid>
    </div>
</div>
<script src="<%=basePath %>/webpage/js/myJacaScript.js"></script>
<script type="text/javascript">
    function LookGoodsDeteil(status, id) {
        $.dialog.setting.zIndex = 1990;  // 修改权重
        if (status == "1") {
            $.dialog({
                content: "url:BusManagement.do?BusinessGoodsByType&GoodsID=" + id + "&Type=" + status,
                inline: true,
                lock: true,
                title: '商品' ,
                opacity: 0.3,
                button: [
                    {
                        name: "审核通过",
                        callback: function () {
                            $.post("<%=basePath%>/GoodsManagement.do?checkGoods",
                                    {"id": id, "status": "2"},
                                    function (result) {
                                        if (result.success) {
                                            tip("审核操作成功!");
                                            reloadTable();
                                        }
                                        else {
                                            tip("审核操作失败!");
                                        }
                                    },
                                    "json");

                        },
                        focus: true
                    },
                    {
                        name: "审核拒绝",
                        callback: function () {
                            $.post("<%=basePath%>/GoodsManagement.do?checkGoods",
                                    {"id": id, "status": "3"},
                                    function (result) {
                                        if (result.success) {
                                            tip("审核操作成功!");
                                            reloadTable();
                                        }
                                        else {
                                            tip("审核操作失败!");
                                        }
                                    },
                                    "json");
                        }
                    }
                ],
                width: 950,
                height: heigth * 0.8
            }).zindex();
        } else if (status == "2" || status == "3") {
            $.dialog({
                content: "url:BusManagement.do?BusinessGoodsByType&GoodsID=" + id + "&Type=" + status,
                inline: true,
                lock: true,
                title: '商品' ,
                opacity: 0.3,

                width: 950,
                height: heigth * 0.8
            }).zindex();
        } else if (status == "4") {

            $.dialog({
                content: "url:BusManagement.do?BusinessGoodsByType&GoodsID=" + id + "&Type=" + status,
                inline: true,
                lock: true,
                title: '商品' ,
                opacity: 0.3,
                button: [
                    {
                        name: "   上架     ",
                        callback: function () {
                            $.post("<%=basePath%>/GoodsManagement.do?putGoods",
                                    {"id": id, "status": "5"},
                                    function (result) {
                                        if (result.success) {
                                            tip("上架操作成功!");
                                            reloadTable();
                                        }
                                        else {
                                            tip("上架操作失败!");
                                        }
                                    },
                                    "json");

                        },
                        focus: true
                    }
                ],

                width: 950,
                height: heigth * 0.8
            }).zindex();
        }
    }

</script>

