<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="ruleTableList" fitColumns="false" title="规则编辑表" actionUrl="ruleTableController.do?datagrid"
                    idField="id" fit="true" queryMode="group" pagination="false">
            <t:dgCol title="主键" field="id" hidden="true" width="120"></t:dgCol>
            <t:dgCol title="规则名" field="ruleName" width="120"></t:dgCol>
            <t:dgCol title="规则内容(简介)" field="ruleContent" width="500"></t:dgCol>
            <t:dgCol title="最后修改时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" width="140"></t:dgCol>
            <t:dgCol title="最后修改者名称" field="changeName"></t:dgCol>

            <t:dgCol title="操作" field="opt" align="center"  width="140"></t:dgCol>
            <c:set var="theString" value="${user_role}"/>
            <c:if test="${fn:contains(theString, 'admin')||fn:contains(theString, 'root')}">
                <t:dgFunOpt funname="LookDetail(id)" title="修改规则"></t:dgFunOpt>
            </c:if>

        </t:datagrid>
    </div>
</div>
<script src="webpage/com/lydb/rule_table/ruleTableList.js"></script>
<script src="<%=basePath %>/webpage/js/myJacaScript.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //给时间控件加上样式
    });

    function LookDetail(id) {

        createwindow("修改规则", "ruleTableController.do?goUpdate&id=" + id, 900, heigth * 0.72);
    }
</script>