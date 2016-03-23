<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>一圆梦版本检测</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript">
        //编写自定义JS代码
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="dbversionController.do?doUpdate"
             tiptype="1">
    <input id="id" name="id" type="hidden" value="${dbversionPage.id }">
    <table style="width: 695px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    版本号:
                </label>
            </td>
            <td class="value">
                <input id="version" name="version" type="text" style="width: 300px" class="inputxt"

                       value='${dbversionPage.version}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">版本号</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    下载路径:
                </label>
            </td>
            <td class="value">
                <input id="url" name="url" type="text" style="width: 600px" class="inputxt"

                       value='${dbversionPage.url}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">下载路径</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    版本说明:
                </label>
            </td>
            <td class="value">
                <textarea id="content" name="content" style="width:400px;height:260px;" datatype="*"
                          errormsg="内容不能为空！">${dbversionPage.content}</textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">版本说明</label>
            </td>
        </tr>

        <input id="createTime" name="createTime"   class="inputxt" type="hidden"
                       value='${dbversionPage.createTime}'>

        </tr>
    </table>
</t:formvalid>
</body>