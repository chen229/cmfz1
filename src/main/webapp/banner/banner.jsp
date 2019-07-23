<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html;utf-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>轮播图</title>
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
        //alert("2222");
        //页面完全加载
        $(function () {
            $("#bannerList").jqGrid({
                url:'${pageContext.request.contextPath}/banner/findBy',
                datatype:'json',
                styleUI:'Bootstrap',
                colNames:['编号','名称','图片','描述','状态','上传日期'],
                cellEdit:true,
                colModel:[
                    {name:'id', align:'center'},
                    {name:'name',editable:true,align:'center'},
                    {name:'cover',editable:true,align:'center',edittype:'file',
                        formatter:function (value,options,row) {
                            var temp="<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/image/"+row.cover+"'>";
                            return temp;
                        }
                    },
                    {name:'description',editable:true,align:'center'},
                    {name:'status',editable:true,align:'center',edittype:'select',editoptions:{value:"正常:正常;冻结:冻结"}},
                    {name:'createtime',editable:false,align:'center'}
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
                editurl:'${pageContext.request.contextPath}/banner/editBanner',//设置单元格编辑后提交的修改路径
                multiselect:true,//多选框
            }).navGrid('#pager',{search:false,},
                {
                    //修改控制
                    closeAfterEdit:close,
                },
                {
                    //添加控制

                    closeAfterAdd:true,
                    afterSubmit:function (response) {
                        var status = response.responseJSON.status;
                        var id = response.responseJSON.id;
                        if(status){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/banner/upLoad",
                                fileElementId:"cover",
                                data:{id:id},
                                type:"post",
                                success:function () {
                                    //alert("11111")
                                    $("#bannerList").trigger("reloadGrid");
                                }
                            });
                        }
                        return "123456";
                    }

                },
                {
                    //删除控制

                });
        });
    </script>

</head>
<body>
<div class="panel">
    <div class="panel-body">
        <table id="bannerList"></table>
    </div>
</div>
<div id="pager" style="height: 60px"></div>
</body>
</html>