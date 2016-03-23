<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>系统公告</title>
    <t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
    <style>
        #steps {
            width: 99% !important;
            overflow: hidden;
        }

        #wrapper {
            -moz-box-shadow: 0px 0px 3px #aaa;
            -webkit-box-shadow: 0px 0px 3px #aaa;
            box-shadow: 0px 0px 3px #aaa;
            -moz-border-radius: 10px;
            -webkit-border-radius: 10px;
            border-radius: 10px;
            border: 2px solid #fff;
            background-color: #f9f9f9;
            width: 99% !important;
            margin: 0 auto;
            padding-bottom: 20px;
            overflow: hidden;
        }
    </style>
    <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript">
        //编写自定义JS代码
    </script>

</head>
<body>
<t:formvalid formid="formobj" dialog="true" layout="div" action="ruleTableController.do?doUpdate">
    <input id="id" name="id" type="hidden" value="${ruleTablePage.id }">
    <input id="ruleName" name="ruleName" type="hidden" value="${ruleTablePage.ruleName }">
    <h1 style="font-size:32px;text-align:center;">${ruleTablePage.ruleName}</h1>
    <c:if test="${ruleTablePage.id==5}">
        <div class="form">
            <label class="Validform_label"> 公告标题： </label> <input class="inputxt" name="title" id="title"
                                                                  datatype="*0-15" errormsg="公告标题！" value="${ruleTablePage.title}">
            <span class="Validform_checktip"></span>
        </div>
        <div class="form">
            <label class="Validform_label"> 公告简介： </label> <input class="inputxt" name="brief" id="brief"
                                                                  datatype="*0-20" errormsg="公告简介！"
                                                                  style="width: 600px" value="${ruleTablePage.brief }">
            <span class="Validform_checktip"></span>
        </div>
    </c:if>
        <div id="suib" class="form" style="width: 99%">
            <t:ckeditor name="ruleContent" isfinder="true" value="${ruleTablePage.ruleContent}" type="height:400">
            </t:ckeditor>
        </div>

    </fieldset>
</t:formvalid>
</body>
<script>
    window.onload = function () {
        /* var gao = $(document).height();*/
        var wigth = window.innerWidth;
        var height = window.innerHeight - 34;
        $("#wrapper").css({"height": height + "px "});
        $("#steps").css({"height": height + "px "});
        $("#formobj").css({"height": height + "px "});


    }
    window.onresize = function () {
        var wigth = window.innerWidth;
        var height = window.innerHeight - 34;
        $("#wrapper").css({"height": height + "px"});
        $("#steps").css({"height": height + "px"});
        $("#formobj").css({"height": height + "px "});


    }


</script>
