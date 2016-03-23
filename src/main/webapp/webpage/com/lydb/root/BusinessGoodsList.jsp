<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
	.tabs {
		list-style-type: none;
		height: 26px;
		margin: 0px;
		padding: 0px 0px 0px 4px;
		font-size: 12px;
		width:100% !important;
		border-style: solid;
		border-width: 0px 0px 1px;
	}
</style>
<t:tabs id="tt" iframe="true" tabPosition="top"  plain="true">
	<c:if test="${id != '' }">
	<t:tab href="BusManagement.do?NotPutGoods&id=${id}"  title="商家未上架商品" id="NotPutGoods"></t:tab>
	</c:if>
	<t:tab href="BusManagement.do?NotPrizeGoods&id=${id}" title="商家未开奖商品" id="NotPrizeGoods"></t:tab>
	<t:tab href="BusManagement.do?IsPrizeGoods&id=${id}" title="商家已开奖商品列表" id="IsPrizeGoods"></t:tab>
	<t:tab href="BusManagement.do?ReturnRMBGoods&id=${id}" title="需返还保证金的商品列表" id="ReturnRMBGoods"></t:tab>
</t:tabs>

