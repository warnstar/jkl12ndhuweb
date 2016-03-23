<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
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
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="true" tabPosition="top" plain="true">
	<c:if test="${id != '' }">
	<t:tab href="BusManagement.do?NotPutZeroGoods&id=${id}"  title="未上架零元商品" id="NotPutZeroGoods"></t:tab>
	</c:if>
	<t:tab href="BusManagement.do?NotPrizeZeroGoods&id=${id}" title="未开奖零元商品" id="NotPrizeZeroGoods"></t:tab>
	<t:tab href="BusManagement.do?IsPrizeZeroGoods&id=${id}" title="已开奖零元商品列表" id="IsPrizeZeroGoods"></t:tab>
	<t:tab href="BusManagement.do?ReturnRMBZeroGoods&id=${id}"  title="需返还保证金的零元商品列表" id="ReturnRMBZeroGoods"></t:tab>
</t:tabs>
