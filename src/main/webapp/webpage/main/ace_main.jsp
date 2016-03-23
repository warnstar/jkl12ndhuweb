<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title><t:mutiLang langKey="jeect.platform"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="plug-in/ace/css/bootstrap.css"/>
    <link rel="stylesheet" href="plug-in/ace/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
    <!-- text fonts -->
    <link rel="stylesheet" href="plug-in/ace/css/ace-fonts.css"/>

    <link rel="stylesheet" href="plug-in/ace/css/jquery-ui.css"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="plug-in/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>
    <link rel="stylesheet" href="plug-in/ace/css/ace-skins.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-ie.css"/>
    <![endif]-->
    <!-- ace settings handler -->
    <script src="plug-in/ace/js/ace-extra.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="plug-in/ace/js/html5shiv.js"></script>
    <script src="plug-in/ace/js/respond.js"></script>
    <![endif]-->


</head>

<body class="skin">
<div class="lu_mask"></div>
<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="navbar-container" id="navbar-container">
        <!-- #section:basics/sidebar.mobile.toggle -->
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>
        </button>

        <!-- /section:basics/sidebar.mobile.toggle -->
        <div class="navbar-header pull-left">
            <!-- #section:basics/navbar.layout.brand -->
            <a href="#" class="navbar-brand">
                <small>
                    <!-- <i class="fa fa-leaf"></i> -->
                    一圆梦后台系统
                </small>
            </a>
            <!-- /section:basics/navbar.layout.brand -->

            <!-- #section:basics/navbar.toggle -->

            <!-- /section:basics/navbar.toggle -->
        </div>

        <!-- #section:basics/navbar.dropdown -->
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">

                <!-- #section:basics/navbar.user_menu -->
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="plug-in/ace/avatars/user.jpg"/>
								<span class="user-info">
									<small>Welcome,${userName }</small>
									<span style="color: #CC33FF">
                    <span style="color: red"><t:mutiLang langKey="common.role"/>:</span>
                    <span style="color: #000">${roleName }</span>
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="javascript:add('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword','',550,200)">
                                <i class="ace-icon fa fa-cog"></i>
                                <t:mutiLang langKey="common.change.password"/>
                            </a>
                        </li>

                        <li>
                            <a href="javascript:openwindow('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo')">
                                <i class="ace-icon fa fa-user"></i>
                                <t:mutiLang langKey="common.profile"/>
                            </a>
                        </li>
                        <%--	<li>
                                <a href="javascript:add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle','',550,200)">
                                    <i class="ace-icon fa fa-user"></i>
                                     <t:mutiLang langKey="common.my.style"/>
                                </a>
                            </li>--%>

                        <li>
                            <a href="javascript:clearLocalstorage()">
                                <i class="ace-icon fa fa-warning"></i>
                                <t:mutiLang langKey="common.clear.localstorage"/>
                            </a>
                        </li>

                        <li class="divider"></li>

                        <li>
                            <a href="javascript:logout()">
                                <i class="ace-icon fa fa-power-off"></i>
                                <t:mutiLang langKey="common.logout"/>
                            </a>
                        </li>
                    </ul>
                </li>

                <!-- /section:basics/navbar.user_menu -->
            </ul>
        </div>

        <!-- /section:basics/navbar.dropdown -->
    </div><!-- /.navbar-container -->
</div>

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <!-- #section:basics/sidebar -->
    <div id="sidebar" class="sidebar      responsive">
        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'fixed')
            } catch (e) {
            }
        </script>

        <div class="sidebar-shortcuts" id="sidebar-shortcuts">
            <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                <button class="btn btn-success">
                    <i class="ace-icon fa fa-signal"></i>
                </button>

                <button class="btn btn-info">
                    <i class="ace-icon fa fa-pencil"></i>
                </button>

                <!-- #section:basics/sidebar.layout.shortcuts -->
                <button class="btn btn-warning">
                    <i class="ace-icon fa fa-users"></i>
                </button>

                <button class="btn btn-danger">
                    <i class="ace-icon fa fa-cogs"></i>
                </button>

                <!-- /section:basics/sidebar.layout.shortcuts -->
            </div>

            <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                <span class="btn btn-success"></span>

                <span class="btn btn-info"></span>

                <span class="btn btn-warning"></span>

                <span class="btn btn-danger"></span>
            </div>
        </div><!-- /.sidebar-shortcuts -->

        <ul class="nav nav-list">
            <li class="">
                <a href="javascript:loadModule('首页','loginController.do?home')">
                    <i class="menu-icon fa fa-tachometer"></i>
                    <span class="menu-text"> 首页 </span>
                </a>

                <b class="arrow"></b>
            </li>
            <t:menu style="ace" menuFun="${menuMap}"></t:menu>
        </ul><!-- /.nav-list -->

        <!-- #section:basics/sidebar.layout.minimize -->
        <%--<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>--%>

        <!-- /section:basics/sidebar.layout.minimize -->
        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'collapsed')
            } catch (e) {
            }
        </script>
    </div>
    <div class="main-content">
        <!-- /section:basics/sidebar -->
        <!-- #section:basics/content.breadcrumbs -->
        <div class="breadcrumbs" id="breadcrumbs" style="display:none">
            <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Home</a>
                </li>
            </ul><!-- /.breadcrumb -->

            <!-- #section:basics/content.searchbox -->
            <div class="nav-search" id="nav-search">
                <form class="form-search">
							<span class="input-icon">
								<input type="text" placeholder="Search ..." class="nav-search-input"
                                       id="nav-search-input" autocomplete="off"/>
								<i class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
                </form>
            </div><!-- /.nav-search -->

            <!-- /section:basics/content.searchbox -->
        </div>

        <!-- /section:basics/content.breadcrumbs -->
        <div class="page-content" style="padding:0px">
            <!-- #section:settings.box -->
            <div class="ace-settings-container" id="ace-settings-container" style="display: none">
                <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                    <i class="ace-icon fa fa-cog bigger-130"></i>
                </div>

                <div class="ace-settings-box clearfix" id="ace-settings-box">
                    <div class="pull-left width-50">
                        <!-- #section:settings.skins -->
                        <div class="ace-settings-item">
                            <div class="pull-left">
                                <select id="skin-colorpicker" class="hide">
                                    <option data-skin="no-skin" value="#438EB9">#438EB9</option>
                                    <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                    <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                    <option data-skin="skin-3" selected="selected" value="#D0D0D0">#D0D0D0</option>
                                </select>
                            </div>
                            <span>&nbsp; Choose Skin</span>
                        </div>

                        <!-- /section:settings.skins -->

                        <!-- #section:settings.navbar -->
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar"/>
                            <label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
                        </div>

                        <!-- /section:settings.navbar -->

                        <!-- #section:settings.sidebar -->
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar"/>
                            <label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
                        </div>

                        <!-- /section:settings.sidebar -->

                        <!-- #section:settings.breadcrumbs -->
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs"/>
                            <label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
                        </div>

                        <!-- /section:settings.breadcrumbs -->

                        <!-- #section:settings.rtl -->
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl"/>
                            <label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
                        </div>

                        <!-- /section:settings.rtl -->

                        <!-- #section:settings.container -->
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container"/>
                            <label class="lbl" for="ace-settings-add-container">
                                Inside
                                <b>.container</b>
                            </label>
                        </div>

                        <!-- /section:settings.container -->
                    </div><!-- /.pull-left -->

                    <div class="pull-left width-50">
                        <!-- #section:basics/sidebar.options -->
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover"/>
                            <label class="lbl" for="ace-settings-hover"> Submenu on Hover</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact"/>
                            <label class="lbl" for="ace-settings-compact"> Compact Sidebar</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight"/>
                            <label class="lbl" for="ace-settings-highlight"> Alt. Active Item</label>
                        </div>

                        <!-- /section:basics/sidebar.options -->
                    </div><!-- /.pull-left -->
                </div><!-- /.ace-settings-box -->
            </div><!-- /.ace-settings-container -->

            <!-- /section:settings.box -->
            <div class="page-content-area" data-ajax-content="false">
                <div id="tabs">
                    <ul style="height:0px">
                        <li>
                            <a href="#tabs-1" id="mainTitle">首页</a>
                        </li>
                    </ul>
                    <div id="tabs-1" style="padding:0px">
                        <iframe style="width:100%;height:925px;margin:0px;padding:0px" scrolling="auto" frameborder="0"
                                id="center" src="loginController.do?home"></iframe>
                        <script>
                            window.onload = function () {
                               /* var gao = $(document).height();*/
                                var gao = window.innerHeight;
                                gao=gao-91;
                                $("#center").css({"height":gao+"px"});
                            }
                            window.onresize = function () {
                                var gao = window.innerHeight;
                                gao=gao-91;
                                $("#center").css({"height":gao+"px"});
                            }
                        </script>
                    </div>
                </div>
            </div><!-- /.page-content-area -->
        </div><!-- /.page-content -->
    </div><!-- /.main-content -->


    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->
<!-- basic scripts -->

<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='plug-in/ace/js/jquery.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->
<div id="changestylePanel" style="display:none">
    <form id="formobj" action="userController.do?savestyle" name="formobj" method="post">
        <table style="width: 550px" cellpadding="0" cellspacing="1" class="formtable">
            <tr>
                <td>风格</td>
            </tr>
            <tr>
                <td class="value"><input type="radio" value="default" name="indexStyle"/> <span>经典风格</span></td>
            </tr>
            <!--
            <tr>
                <td class="value"><input type="radio" value="bootstrap" name="indexStyle" /> <span>BootStrap风格</span></td>
            </tr>
            -->
            <!-- update-start--Author:gaofeng  Date:2014-01-10 for:新增首页风格  -->
            <tr>
                <td class="value"><input type="radio" value="shortcut" name="indexStyle"/> <span>ShortCut风格</span></td>
            </tr>
            <!-- update-start--Author:gaofeng  Date:2014-01-24 for:新增首页风格  -->
            <tr>
                <td class="value"><input type="radio" value="sliding" name="indexStyle"/><span>Sliding云桌面</span></td>
            </tr>
            <!-- update-end--Author:longjb  Date:2013-03-15 for:新增首页风格  -->
            <tr>
                <td class="value"><input type="radio" value="ace" name="indexStyle"/><span>ACE平面风格</span></td>
            </tr>
        </table>
    </form>
</div>
<div id="changepassword" style="display:none">

    <input id="id" type="hidden" value="${user.id }">
    <table style="width: 550px" cellpadding="0" cellspacing="1" class="formtable">
        <tbody>
        <tr>
            <td align="right" width="20%"><span class="filedzt">原密码:</span></td>
            <td class="value"><input id="password" type="password" value="" name="password" class="inputxt" datatype="*"
                                     errormsg="请输入原密码"/> <span class="Validform_checktip"> 请输入原密码 </span></td>
        </tr>
        <tr>
            <td align="right"><span class="filedzt">新密码:</span></td>
            <td class="value"><input type="password" value="" name="newpassword" class="inputxt"
                                     plugin="passwordStrength" datatype="*6-18" errormsg="密码至少6个字符,最多18个字符！"/> <span
                    class="Validform_checktip"> 密码至少6个字符,最多18个字符！ </span> <span class="passwordStrength"
                                                                                style="display: none;"> <b>密码强度：</b> <span>弱</span><span>中</span><span
                    class="last">强</span> </span></td>
        </tr>
        <tr>
            <td align="right"><span class="filedzt">重复密码:</span></td>
            <td class="value"><input id="newpassword" type="password" recheck="newpassword" datatype="*6-18"
                                     errormsg="两次输入的密码不一致！"> <span class="Validform_checktip"></span></td>
        </tr>
        </tbody>
    </table>
</div>
<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='plug-in/ace/js/jquery1x.js'>" + "<" + "/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='plug-in/ace/js/jquery.mobile.custom.js'>" + "<" + "/script>");

    function loadModule(title, url, target) {
        //TODO addTab(title,url);
        $("#mainTitle").text(title);
        $("#center").attr("src", url);
    }


    function logout() {
        bootbox.confirm("Are you sure to logout?", function (result) {
            if (result)
                location.href = "loginController.do?logout";
        });
    }
    function opendialog(title, url, target) {
        //$("#dialog").attr("src",url);
        bootbox.dialog({
            message: $("#changestylePanel").html(),
            title: title,
            buttons: {
                OK: {
                    label: "OK",
                    callback: function () {
                        var indexStyle = $('input[name="indexStyle"]:checked').val();
                        if (indexStyle == undefined || indexStyle == "") {
                            indexStyle = "ace";
                        }
                        var cssTheme = $('input[name="cssTheme"]:checked').val();
                        if (cssTheme == undefined) {
                            cssTheme = "";
                        }
                        var form = $("#formobj");//取iframe里的form
                        $.ajax({
                            url: form.attr('action'),
                            type: form.attr('method'),
                            data: "indexStyle=" + indexStyle,//+"&cssTheme="+cssTheme,
                            success: function (data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    bootbox.alert(msg);
                                } else {
                                    bootbox.alert(d.msg);
                                }
                            },
                            error: function (e) {
                                bootbox.alert("出错了哦");
                            }
                        });
                    }
                }, Cancel: {
                    label: "CLOSE",
                    callback: function () {
                        //alert('close');//$("#dialog").dialog("close");
                    }
                }
            }
        });

    }
    function changepass(title, url, target) {
        //$("#dialog").attr("src",url);
        bootbox.dialog({
            message: '<form id="formobj2"  action="userController.do?savenewpwd" name="formobj2" method="post">'
            + $("#changepassword").html() + '</form>',
            title: title,
            buttons: {
                OK: {
                    label: "OK",
                    callback: function () {
                        //alert('not implement');
                        $.ajax({
                            url: "userController.do?savenewpwd",
                            type: "post",
                            data: $('#formobj2').serialize(),// 要提交的表单 ,
                            success: function (data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    bootbox.alert(msg);
                                } else {
                                    bootbox.alert(d.msg);
                                }
                            },
                            error: function (e) {
                                bootbox.alert("出错了哦");
                            }
                        });
                    }
                }, Cancel: {
                    label: "CLOSE",
                    callback: function () {
                        alert('close');//$("#dialog").dialog("close");
                    }
                }
            }
        });

    }
    function profile(title, url, target) {
        //$("#dialog").attr("src",url);
        bootbox.dialog({
            message: '<iframe width="100%" height="300px" src="' + url + '" style="border:1px #fff solid; background:#CCC;"></iframe>',
            title: title,
            buttons: {
                OK: {
                    label: "OK"
                }, Cancel: {
                    label: "CLOSE"
                }
            }
        });

    }
    function clearLocalstorage() {
        var storage = $.localStorage;
        if (!storage)
            storage = $.cookieStorage;
        storage.removeAll();
        //bootbox.alert( "浏览器缓存清除成功!");
        alertTipTop("浏览器缓存清除成功!", "10%");
    }
</script>
<script src="plug-in/ace/js/bootstrap.js"></script>
<script src="plug-in/ace/js/bootbox.js"></script>

<script src="plug-in/ace/js/jquery-ui.js"></script>
<script src="plug-in/ace/js/jquery.ui.touch-punch.js"></script>
<!-- ace scripts -->
<script src="plug-in/ace/js/ace/elements.scroller.js"></script>
<script src="plug-in/ace/js/ace/elements.colorpicker.js"></script>
<script src="plug-in/ace/js/ace/elements.fileinput.js"></script>
<script src="plug-in/ace/js/ace/elements.typeahead.js"></script>
<script src="plug-in/ace/js/ace/elements.wysiwyg.js"></script>
<script src="plug-in/ace/js/ace/elements.spinner.js"></script>
<script src="plug-in/ace/js/ace/elements.treeview.js"></script>
<script src="plug-in/ace/js/ace/elements.wizard.js"></script>
<script src="plug-in/ace/js/ace/elements.aside.js"></script>
<script src="plug-in/ace/js/ace/ace.js"></script>
<script src="plug-in/ace/js/ace/ace.ajax-content.js"></script>
<script src="plug-in/ace/js/ace/ace.touch-drag.js"></script>
<script src="plug-in/ace/js/ace/ace.sidebar.js"></script>
<script src="plug-in/ace/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="plug-in/ace/js/ace/ace.submenu-hover.js"></script>
<script src="plug-in/ace/js/ace/ace.widget-box.js"></script>
<script src="plug-in/ace/js/ace/ace.settings.js"></script>
<script src="plug-in/ace/js/ace/ace.settings-rtl.js"></script>
<script src="plug-in/ace/js/ace/ace.settings-skin.js"></script>
<script src="plug-in/ace/js/ace/ace.widget-on-reload.js"></script>
<script src="plug-in/ace/js/ace/ace.searchbox-autocomplete.js"></script>
<t:base type="tools"></t:base>
<script src="plug-in/jquery-plugs/storage/jquery.storageapi.min.js"></script>
<script>

    jQuery(function ($) {
                $("#tabs").tabs();
                var skin_class = $('#skin-colorpicker').find('option:selected').data('skin');
                applyChanges(skin_class);
                /*$('#sidebar li').addClass('hover')
                 .filter('.open').removeClass('open').find('> .submenu').css('display', 'none');*/

                $('.lu_mask').hide();

            }
    );
    function applyChanges(skin_class) {
        //skin cookie tip
        var body = $(document.body);
        body.removeClass('no-skin skin-1 skin-2 skin-3');
        //if(skin_class != 'skin-0') {
        body.addClass(skin_class);
        ace.data.set('skin', skin_class);
        //save the selected skin to cookies
        //which can later be used by your server side app to set the skin
        //for example: <body class="<?php echo $_COOKIE['ace_skin']; ?>"
        //} else ace.data.remove('skin');

        var skin3_colors = ['red', 'blue', 'green', ''];


        //undo skin-1
        $('.ace-nav > li.grey').removeClass('dark');

        //undo skin-2
        $('.ace-nav > li').removeClass('no-border margin-1');
        $('.ace-nav > li:not(:last-child)').removeClass('light-pink').find('> a > ' + ace.vars['.icon']).removeClass('pink').end().eq(0).find('.badge').removeClass('badge-warning');
        $('.sidebar-shortcuts .btn')
                .removeClass('btn-pink btn-white')
                .find(ace.vars['.icon']).removeClass('white');

        //undo skin-3
        $('.ace-nav > li.grey').removeClass('red').find('.badge').removeClass('badge-yellow');
        $('.sidebar-shortcuts .btn').removeClass('btn-primary btn-white')
        var i = 0;
        $('.sidebar-shortcuts .btn').each(function () {
            $(this).find(ace.vars['.icon']).removeClass(skin3_colors[i++]);
        })


        var skin0_buttons = ['btn-success', 'btn-info', 'btn-warning', 'btn-danger'];
        if (skin_class == 'no-skin') {
            var i = 0;
            $('.sidebar-shortcuts .btn').each(function () {
                $(this).attr('class', 'btn ' + skin0_buttons[i++ % 4]);
            })
            alert('哈哈');
            $('.sidebar[data-sidebar-scroll=true]').ace_sidebar_scroll('updateStyle', '');
            $('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('updateStyle', 'no-track scroll-thin');
        }

        else if (skin_class == 'skin-1') {
            $('.ace-nav > li.grey').addClass('dark');
            var i = 0;
            $('.sidebar-shortcuts')
                    .find('.btn').each(function () {
                $(this).attr('class', 'btn ' + skin0_buttons[i++ % 4]);
            })

            $('.sidebar[data-sidebar-scroll=true]').ace_sidebar_scroll('updateStyle', 'scroll-white no-track');
            $('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('updateStyle', 'no-track scroll-thin scroll-white');
        }

        else if (skin_class == 'skin-2') {
            $('.ace-nav > li').addClass('no-border margin-1');
            $('.ace-nav > li:not(:last-child)').addClass('light-pink').find('> a > ' + ace.vars['.icon']).addClass('pink').end().eq(0).find('.badge').addClass('badge-warning');

            $('.sidebar-shortcuts .btn').attr('class', 'btn btn-white btn-pink')
                    .find(ace.vars['.icon']).addClass('white');

            $('.sidebar[data-sidebar-scroll=true]').ace_sidebar_scroll('updateStyle', 'scroll-white no-track');
            $('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('updateStyle', 'no-track scroll-thin scroll-white');
        }

        //skin-3
        //change shortcut buttons classes, this should be hard-coded if you want to choose this skin
        else if (skin_class == 'skin-3') {
            body.addClass('no-skin');//because skin-3 has many parts of no-skin as well

            $('.ace-nav > li.grey').addClass('red').find('.badge').addClass('badge-yellow');

            var i = 0;
            $('.sidebar-shortcuts .btn').each(function () {
                $(this).attr('class', 'btn btn-primary btn-white');
                $(this).find(ace.vars['.icon']).addClass(skin3_colors[i++]);
            })

            $('.sidebar[data-sidebar-scroll=true]').ace_sidebar_scroll('updateStyle', 'scroll-dark no-track');
            $('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('updateStyle', 'no-track scroll-thin');
        }

        //some sizing differences may be there in skins, so reset scrollbar size
        $('.sidebar[data-sidebar-scroll=true]').ace_sidebar_scroll('reset')
        //$('.sidebar[data-sidebar-hover=true]').ace_sidebar_hover('reset')

        if (ace.vars['old_ie']) ace.helper.redraw(document.body, true);
    }
</script>
</body>
</html>