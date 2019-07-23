<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html;utf-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>专辑</title>
    <%--引入样式文件--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" type="text/css">
    <%--引入js功能文件--%>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <script type="text/javascript">
        //页面完全加载
        $(function () {
            $("#userList").jqGrid({
                url:'${pageContext.request.contextPath}/user/findAllUser',
                datatype:'json',
                styleUI:'Bootstrap',
                colNames:['编号','电话','头像','用户名','密码','法名','性别','省份','城市','签名','上传日期'],
                cellEdit:true,
                colModel:[
                    {name:'id', align:'center',width:70},
                    {name:'phone',editable:true,align:'center',width:170},
                    {name:'photo',editable:true,align:'center',edittype:'file',
                        formatter:function (value,options,row) {
                            var temp="<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/image/"+row.photo+"'>";
                            return temp;
                        }
                    },
                    {name:'username',editable:true,align:'center'},
                    {name:'password',editable:true,align:'center',edittype:'select',editoptions:{value:"正常:正常;冻结:冻结"}},
                    {name:'dharma',editable:true,align:'center'},
                    {name:'sex',editable:true,align:'center',width:70},
                    {name:'province',editable:true,align:'center'},
                    {name:'city',editable:true,align:'center'},
                    {name:'sign',editable:true,align:'center'},
                    {name:'createTime',editable:false,align:'center',width:250}
                ],
                mtype:'post',
                pager:'#pager',
                rowList:[3,5,10,20,30],
                height:400,
                autowidth:true,
                autoencode:true,
                sortname:'id',
                sortorder:'desc',
                page:1,//默认显示第一页
                rowNum:3,//默认显示的信息条数
                viewrecords:true,//是否显示总的信息条数
                <%--editurl:'${pageContext.request.contextPath}/banner/editBanner',//设置单元格编辑后提交的修改路径--%>
                // multiselect:true,//多选框


            })
                // .navGrid("#pager",{del:false,edit:false,search:false,add:false});
        });

        function out() {
            window.location.href="${pageContext.request.contextPath}/user/findAll";
            alert("导入成功");
            window.location.href="${pageContext.request.contextPath}/back/main.jsp";

        }

    </script>
</head>
<body>
<div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#home"  aria-controls="home" role="tab" data-toggle="tab">所有用户</a>
        </li>
        <li role="presentation">
            <a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" onclick="out()">导出用户</a>
        </li>
    </ul>
</div>
    <table id="userList"></table>
    <div id="pager" style="height: 60px"></div>
</body>
</html>