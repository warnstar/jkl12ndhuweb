<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="NotPrizeGoods" fitColumns="false" title="未开奖零元商品列表"
                    actionUrl="BusManagement.do?Zerogoodsdatagrid&dbWebBusinessid=${id}&type=5" sortName="star_time"
                    sortOrder="desc" pageSize="50" idField="id" fit="true" queryMode="group">
            <%--当前的id属性为商品中每一个开奖商品的id db_goods_single的id--%>
            <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="商品序列号" field="zserial_num" align="center" query="true"></t:dgCol>
            <t:dgCol title="商品的封面图" image="true"  imageSize="120,90" field="zgoods_headurl"></t:dgCol>
            <t:dgCol title="商品上架时间" field="star_time" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="150"></t:dgCol>

            <t:dgCol title="当前开奖的期数" field="goods_current_num" align="center"></t:dgCol>
            <t:dgCol title="商品的总期数" field="goods_all_num" align="center"></t:dgCol>
            <t:dgCol title="商品名称" field="zgoods_name" align="center" query="true"></t:dgCol>
            <t:dgCol title="商品单价（元）" field="zgoods_rmb" align="center"></t:dgCol>
            <t:dgCol title="当前抢夺人数" field="current_join_num" align="center"></t:dgCol>

            <t:dgCol title="所属商店名称" field="shopname" align="center"></t:dgCol>

            <t:dgCol title="当前设置的中奖号" field="lucky_id" align="center"></t:dgCol>
            <t:dgCol title="开奖时间" field="open_time" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="150"></t:dgCol>
            <t:dgCol title="开奖时间间隔" field="time" align="center"></t:dgCol>
            <t:dgCol title="操作" field="opt" align="center" width="160"></t:dgCol>

            <t:dgFunOpt funname="LookGoodsDeteil(id)" title="查看商品详情"></t:dgFunOpt>
            <c:set var="theString" value="${user_role}"/>
            <c:if test="${fn:contains(theString, 'admin')||fn:contains(theString, 'root')}">
                <t:dgFunOpt funname="ChangeZeroLuckyId(id)" title="修改中奖号"></t:dgFunOpt>
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
    function LookGoodsDeteil( id) {
        $.dialog({
            content: "url:BusManagement.do?BusinessZeroGoodsByType&zGoodsID=" + id + "&Type=5",
            inline: true,
            lock: true,
            title: '商品',
            opacity: 0.3,

            width: 950,
            height: heigth * 0.8
        }).zindex();
    }

    function ChangeZeroLuckyId(id) {
        $.dialog({
            content: "url:ZeroGoodsManagement.do?goChangeZeroLuckId&id=" + id,
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
            width: width * 0.5,
            height: heigth * 0.5
        }).zindex();
    }


</script>

