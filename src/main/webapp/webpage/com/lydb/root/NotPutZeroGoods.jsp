<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="DbZeroGoods" fitColumns="false" title="(带*进行模糊搜索)"
                    actionUrl="BusManagement.do?Zerogoodsdatagrid&status_begin=1&status_end=4&dbWebBusinessid=${id}&type=1"
                    sortName="createTime" sortOrder="desc" pageSize="50" idField="id" fit="true" queryMode="group" queryBuilder="true">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="商家主键" field="dbWebBusinessid" hidden="true"></t:dgCol>

            <t:dgCol title="商品序列号" field="zserialNum" align="center" query="true"></t:dgCol>
            <t:dgCol title="商品的封面图" image="true"  imageSize="120,90"  field="zgoodsHeadurl"></t:dgCol>
            <t:dgCol title="申请时间" field="createTime" align="center" formatter="yyyy-MM-dd hh:mm:ss"
                     width="140"></t:dgCol>
            <t:dgCol title="商品名称" field="zgoodsName" align="center" query="true"></t:dgCol>

            <t:dgCol title="商品单价（元）" field="zgoodsRmb" align="center" width="120"></t:dgCol>

            <t:dgCol title="参与夺宝的商品数量" field="zgoodsNum" align="center" width="120"></t:dgCol>

            <t:dgCol title="商品状态" field="status" align="center" replace="未审核_1,审核通过未缴费_2,审核拒绝_3,已缴费等待上架_4" query="true"
                     width="120"></t:dgCol>
            <t:dgCol title="开奖间隔时间（默认:1天）" field="time" align="center"></t:dgCol>
            <t:dgCol title="操作" field="opt" align="center" width="200"></t:dgCol>

            <t:dgFunOpt funname="LookZGoodsDeteil(status,id)" title="查看商品详情"></t:dgFunOpt>
            <c:set var="theString" value="${user_role}"/>
            <c:if test="${fn:contains(theString, 'admin')||fn:contains(theString, 'root')}">
                <t:dgDelOpt title="common.delete" url="BusManagement.do?zerogooddel&id={id}"/>
            </c:if>

        </t:datagrid>
    </div>
</div>
<script src="<%=basePath %>/webpage/js/myJacaScript.js"></script>
<script type="text/javascript">

    function LookZGoodsDeteil( status, id) {

        $.dialog.setting.zIndex = 1990;  // 修改权重
        if (status == "1") {
            $.dialog({
                content: "url:BusManagement.do?BusinessZeroGoodsByType&zGoodsID=" + id + "&Type=" + status,
                inline: true,
                lock: true,
                title: '商品' ,
                opacity: 0.3,
                button: [
                    {
                        name: "审核通过",
                        callback: function () {
                            $.dialog({
                                content: "url:ZeroGoodsManagement.do?setZeroGoodsTime&ZgoodsID=" + id,
                                inline: true,
                                lock: true,
                                title: '设置开奖的间隔时间（单位/天）',
                                opacity: 0.3,
                                ok: function () {
                                    iframe = this.iframe.contentWindow;
                                    saveObj();
                                    return false;
                                },
                                cancel: false,
                                width: 700,
                                height: 300
                            }).zindex();
                            $.post("<%=basePath%>/ZeroGoodsManagement.do?checkGoods",
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
                            $.post("<%=basePath%>/BusManagement.do?ChangeBusinessStatus",
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
                height: heigth * 0.85
            }).zindex();
        } else if (status == "2" || status == "3") {
            $.dialog({
                content: "url:BusManagement.do?BusinessZeroGoodsByType&zGoodsID=" + id + "&Type=" + status,
                inline: true,
                lock: true,
                title: '商品' ,
                opacity: 0.3,

                width: 950,
                height: heigth * 0.85
            }).zindex();
        } else if (status == "4") {

            $.dialog({
                content: "url:BusManagement.do?BusinessZeroGoodsByType&zGoodsID=" + id + "&Type=" + status,
                inline: true,
                lock: true,
                title: '商品' ,
                opacity: 0.3,
                button: [
                    {
                        name: "   上架     ",
                        callback: function () {
                            $.post("<%=basePath%>/ZeroGoodsManagement.do?putGoods",
                                    {"id": id, "status": "5"},
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
                    }
                ],

                width: 950,
                height: heigth * 0.85
            }).zindex();
        }
    }

</script>

