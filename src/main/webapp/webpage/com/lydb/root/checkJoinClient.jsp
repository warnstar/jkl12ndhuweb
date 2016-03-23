<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="checkJoinClient" fitColumns="ture" title="参与用户列表" actionUrl="GoodsManagement.do?checkJoindatagrid&id=${id}" pageSize="100" idField="id" fit="true" queryMode="group" >
   <!--当前的id属性为商品id   db_goods  -->
   <t:dgCol title="用户主键"  field="id"  hidden="true"  ></t:dgCol>
   	<t:dgCol title="用户手机号"  field="mobile"  align="center" width="120" query="true"></t:dgCol>
    <t:dgCol title="用户昵称" field="client_name" align="center" width="180"></t:dgCol>
   <t:dgCol title="夺宝时间"  field="create_time"    align="center"  width="200" ></t:dgCol>
  
  <t:dgCol title="本次参与次数"  field="joinnum"    align="center"  width="140" ></t:dgCol>
  
    <t:dgCol title="夺宝码" hidden="true" field="allcode"  align="center"   width="400"  query="true"></t:dgCol>
    
    <t:dgCol title="用户的总中奖次数"  field="win_num"  align="center"   width="120" ></t:dgCol>
    <t:dgCol title="用户的总参与次数"  field="join_num"  align="center"   width="120" ></t:dgCol>
   
    
    <t:dgCol title="用户的ip信息"  field="ip_address"  align="center"  width="120" ></t:dgCol>
    <t:dgCol title="用户的地理信息"  field="address_info"  align="center"  width="120" ></t:dgCol>
    
    
   <t:dgCol title="操作" field="opt" align="center" width="200"></t:dgCol>
   <t:dgFunOpt  funname="checkclientdetail(id)" title="查看用户详情"></t:dgFunOpt>
   <t:dgFunOpt  funname="checkcode(allcode)" title="查看夺宝码"></t:dgFunOpt>
    <t:dgDelOpt title="删除" url="" />
  </t:datagrid>
  </div>
 </div>
<script src="<%=basePath %>/webpage/js/myJacaScript.js"></script>
 <script type="text/javascript">

 
 function checkcode(allcode){
	 var msg=[];
	 msg = allcode.split(',');
	 var s = '';
	 for(var i=1;i<=msg.length;i++){
         if(i==1){
             s="<div style='width: 620px;height: 500px;overflow-y: auto'>"
         }
		 s=s+"  "+msg[i-1];
		 if(!(i%10))s=s+' </br> ';

         if(i==msg.length){
             s=s+"</div>"
         }
	 }
	 
	 
	 $.dialog.setting.zIndex = 2080;
		$.dialog({
				title:"夺宝码",
				content: s,
                top:'30%',
                width:650
			}).zindex();
	}
 function checkclientdetail(id){
	 $.dialog.setting.zIndex = 2080;
	 openwindow('用户详情',"AppClientManagement.do?ClientDetail&id="+id,"",1300,900);
}
		
 </script>

