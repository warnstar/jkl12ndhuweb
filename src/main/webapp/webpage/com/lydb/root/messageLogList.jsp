<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="messageLogList" fitColumns="false" title="(带*进行模糊搜索)"
                    actionUrl="messageLogController.do?datagrid" sortName="createTime" sortOrder="desc" idField="id"
                    fit="true" pageSize="100" queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="推送个人的id" field="appClientid" hidden="true"></t:dgCol>
            <t:dgCol title="推送的类型" field="type" hidden="true"></t:dgCol>

            <t:dgCol title="消息标题" field="messageTitle" align="center" query="true" width="180"></t:dgCol>
            <t:dgCol title="消息简介" field="messageBrief" align="center" width="150"></t:dgCol>
            <t:dgCol title="消息内容" field="messageContent" align="center" width="500"></t:dgCol>
            <t:dgCol title="发送时间" field="createTime" align="center" formatter="yyyy-MM-dd hh:mm:ss" width="140"></t:dgCol>
            <t:dgCol title="发送者名称" field="createName" align="center" query="true" ></t:dgCol>
            <t:dgCol title="是否推送所有人" field="pullAll" align="center" replace="否_0,是_1" query="true"></t:dgCol>


            <t:dgCol title="操作" field="opt" align="center"  width="100"></t:dgCol>
            <c:set var="theString" value="${user_role}"/>
            <c:if test="${fn:contains(theString, 'admin')||fn:contains(theString, 'root')}">
                <t:dgDelOpt title="删除" url="messageLogController.do?doDel&id={id}"/>
            </c:if>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        //给时间控件加上样式
    });

</script>