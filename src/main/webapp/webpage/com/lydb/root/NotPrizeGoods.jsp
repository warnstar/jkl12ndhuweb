<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="NotPrizeGoods" fitColumns="false" title="未开奖商品列表"
                    actionUrl="BusManagement.do?goodsdatagrid&dbWebBusinessid=${id}&type=5" sortName="star_time"
                    sortOrder="desc" pageSize="100" idField="id" fit="true" queryMode="group">
            <!--当前的id属性为商品中每一个开奖商品的id db_goods_single的id -->
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="商品序列号" field="serial_num" align="center" query="true"></t:dgCol>
            <t:dgCol title="商品的封面图" image="true" imageSize="120,90" field="goods_headurl"></t:dgCol>
            <t:dgCol title="商品上架时间" field="star_time" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>

            <t:dgCol title="当前开奖的期数" field="goods_current_num" align="center"></t:dgCol>
            <t:dgCol title="商品的总期数" field="goods_all_num" align="center"></t:dgCol>
            <t:dgCol title="商品名称" field="goods_name" align="center" query="true" width="200"></t:dgCol>
            <t:dgCol title="商品单价（元）" field="goods_rmb" align="center"></t:dgCol>
            <t:dgCol title="当前抢夺人数" field="current_join_num" align="center"></t:dgCol>

            <t:dgCol title="所属商店名称" field="shopname" align="center"></t:dgCol>

            <t:dgCol title="开奖状态" field="singlestatus" replace="正在参与中_0,计算中_1" align="center" query="true" width="70"></t:dgCol>
            <t:dgCol title="剩余时间/s" field="opentime" align="center"></t:dgCol>
            <t:dgCol title="操作" field="opt" align="center" width="270"></t:dgCol>

            <t:dgFunOpt funname="LookGoodsDeteil(id)" title="查看商品详情"></t:dgFunOpt>
            <t:dgFunOpt funname="LookJoinDeteil(id)" title="查看参与用户详情"></t:dgFunOpt>
            <c:set var="theString" value="${user_role}"/>
            <c:if test="${fn:contains(theString, 'admin')||fn:contains(theString, 'root')}">
                <t:dgFunOpt funname="ChangeLuckyId(id,singlestatus)" title="修改中奖号"></t:dgFunOpt>
            </c:if>
        </t:datagrid>
    </div>
</div>
<script src="<%=basePath %>/webpage/js/myJacaScript.js"></script>
<script type="text/javascript">
    var i = 1;
    $(function(){
        window.setInterval(showalert, 1000);
        document.onmousewheel=function(event) {
            i=1;
        };
        document.onmousedown=function(event){
            i=1;
        }
        document.onkeydown=function(event){
            i=1;
        }
    });
    function showalert()
    {
        if(i==13){
            $(".pagination-load").trigger('click');
            i=1;
        }else{
            i=i+1;
        }
    };

    function LookGoodsDeteil(id) {
        $.dialog({
            content: "url:BusManagement.do?BusinessGoodsByType&GoodsID=" + id + "&Type=5",
            inline: true,
            lock: true,
            title: '商品' ,
            opacity: 0.3,

            width: 950,
            height: heigth * 0.8
        }).zindex();
    }

    function ChangeLuckyId(id, singlestatus) {
        if (singlestatus == '1') {
            $.dialog({
                content: "url:GoodsManagement.do?goChangeLuckId&id=" + id,
                inline: true,
                lock: true,
                title: '修改中奖号码！',
                opacity: 0.3,
                ok: function () {
                    iframe = this.iframe.contentWindow;
                    saveObj();
                    return false;
                },
                cancel: true,
                width: width * 0.6,
                height: heigth * 0.5
            }).zindex();
        } else {
            alertTipTop("由于正在参与中，不能修改中奖者！", '10%');
        }

    }
    function LookJoinDeteil(id) {

        openwindow('商品', "GoodsManagement.do?checkJoinClient&id=" + id, "", width * 0.8, heigth * 0.8);
    }
</script>

