<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="dbAppClientList" fitColumns="false" title="(带*进行模糊搜索)" pageSize="100"
                    actionUrl="AppClientManagement.do?datagrid" idField="id" fit="true" queryMode="group"
                    queryBuilder="true">
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>

            <t:dgCol title="用户头像" image="true"  imageSize="120,90" field="headImg"></t:dgCol>
            <t:dgCol title="手机号" field="mobile" align="center" query="true" width="120"></t:dgCol>
            <t:dgCol title="用户昵称" field="clientName" align="center" query="true" width="120"></t:dgCol>
            <t:dgCol title="推广ID" field="popularizeId" align="center" query="true" width="120"></t:dgCol>
            <t:dgCol title="已邀请的人数" field="inviteNum" align="center" width="120"></t:dgCol>


            <t:dgCol title="QQ" field="clientQq" align="center" query="true" width="120"></t:dgCol>
            <t:dgCol title="夺宝次数" field="joinNum" align="center"></t:dgCol>
            <t:dgCol title="中奖次数" field="winNum" align="center"></t:dgCol>


            <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" align="center"
                     width="140"></t:dgCol>
            <t:dgCol title="上次登录时间" field="loginTime" formatter="yyyy-MM-dd hh:mm:ss" align="center"
                     width="140"></t:dgCol>
            <t:dgCol title="操作" field="opt" align="center" width="250"></t:dgCol>

            <t:dgFunOpt funname="checkclientdetail(id)" title="查看详情"></t:dgFunOpt>

            <t:dgFunOpt funname="appClientJpush(id)" title="推送消息"></t:dgFunOpt>
            <c:set var="theString" value="${user_role}"/>
            <c:if test="${fn:contains(theString, 'admin')||fn:contains(theString, 'root')}">
                <t:dgFunOpt funname="giveClientRMB(id)" title="赠送抢币"></t:dgFunOpt>
                <t:dgFunOpt funname="delclient(id)" title="删除"/>


            </c:if>

        </t:datagrid>
    </div>
</div>
<script src="<%=basePath %>/webpage/js/myJacaScript.js"></script>
<script type="text/javascript">


    function checkclientdetail(id) {
        openwindow('用户详情', "AppClientManagement.do?ClientDetail&id=" + id, "", 1300, heigth * 0.85);
    }
    function giveClientRMB(id) {
        $.dialog({
            content: "url:AppClientManagement.do?gogiveClientRMB&id=" + id,
            inline: true,
            lock: true,
            title: '赠送抢币！',
            opacity: 0.3,
            ok: function () {
                iframe = this.iframe.contentWindow;
                saveObj();
                return false;
            },
            cancel: true,
            width: width * 0.5,
            height: heigth * 0.5
        }).zindex();
    }
    function appClientJpush(id) {
        $.dialog({
            content: "url:AppClientManagement.do?appClientJpush&id=" + id,
            inline: true,
            lock: true,
            title: '推送消息！',
            opacity: 0.3,
            ok: function () {
                iframe = this.iframe.contentWindow;
                saveObj();
                return false;
            },
            cancel: true,
            width: width * 0.8,
            height: heigth * 0.8
        }).zindex();
    }
    function delclient(id) {
        $.dialog.confirm("注意该操作会删除跟用户有关的所有信息，请谨慎操作！", function () {
            $.ajax({
                async: false,
                cache: false,
                type: 'POST',
                data: {},
                url: "AppClientManagement.do?deleteClient&userid=" + id,// 请求的action路径
                error: function () {
                },
                success: function (data) {

                    var d = $.parseJSON(data);
                    if (d.success) {
                        var msg = d.msg;
                        tip(msg);
                    }
                    reloadTable();
                }
            });

        }, function () {
        }).zindex();

    }
</script>