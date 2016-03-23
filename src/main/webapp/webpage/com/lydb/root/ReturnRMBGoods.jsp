<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="RetrunRMBGoods" fitColumns="false" title="需返还保证金商品列表"
                    actionUrl="BusManagement.do?goodsdatagrid&dbWebBusinessid=${id}&type=7" pageSize="50" idField="id"
                    fit="true" queryMode="group">
            <!--当前的id属性为商品id db_goods -->
            <t:dgCol title="商品主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="商品序列号" field="serial_num" align="center" query="true"></t:dgCol>
            <t:dgCol title="商品的封面图" image="true"  imageSize="120,90" field="goods_headurl"></t:dgCol>
            <t:dgCol title="商品录入时间" field="create_time" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>


            <t:dgCol title="商品数量" field="goods_num" align="center"></t:dgCol>

            <t:dgCol title="商品名称" field="goods_name" align="center" query="true" width="200"></t:dgCol>
            <t:dgCol title="商品单价（元）" field="goods_rmb" align="center"></t:dgCol>


            <t:dgCol title="所属商店名称" field="shopname" align="center"></t:dgCol>
            <t:dgCol title="商店注册手机号" field="mobile" align="center"></t:dgCol>

            <t:dgCol title="应返还保证金额" field="deposit" align="center"></t:dgCol>


            <t:dgCol title="操作" field="opt" align="center" width="120"></t:dgCol>

            <c:set var="theString" value="${user_role}"/>
            <c:if test="${fn:contains(theString, 'admin')||fn:contains(theString, 'root')}">
                <t:dgFunOpt funname="queryReturn(id)" title="确认已退款"></t:dgFunOpt>
            </c:if>

        </t:datagrid>
    </div>
</div>

<script type="text/javascript">

    function queryReturn(id) {

        $.post(
                url = "<%=basePath%>/GoodsManagement.do?confirmReturnRMB",
                data = {
                    'id': id
                },
                function (result) {
                    alertTip(result.msg);
                    reloadTable();
                }, 'json');

    }


</script>

