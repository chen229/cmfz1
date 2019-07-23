<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html;utf-8" %>
<html lang="zh-CN">
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
    <%--引入kindEditor的配置文件--%>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>


    <script type="text/javascript">
        //页面完全加载
        $(function () {
            $("#articleList").jqGrid({
                url:'${pageContext.request.contextPath}/article/findAllArticle',
                datatype:'json',
                styleUI:'Bootstrap',
                colNames:['编号','标题','内容','作者','上传日期','操作'],
                cellEdit:true,
                colModel:[
                    {name:'id', align:'center',width:70},
                    {name:'title',editable:true,align:'center',width:170},
                    {name:'content',editable:true,align:'center'},
                    {name:'author',editable:true,align:'center'},
                    {name:'createTime',editable:false,align:'center',width:250},
                    {name:'options',align:'center',width:100,
                        formatter:function (value,options,row) {
                            return "<a class='btn btn-warning' onclick=\"openModal('edit','"+row.id+"')\">修改</a>";
                        }
                    }
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
                editurl:'${pageContext.request.contextPath}/article/editArticle',//设置单元格编辑后提交的修改路径


            }).navGrid("#pager",{del:true,edit:false,search:false,add:false});
        });
        //打开修改的模态框
        function openModal(oper,id) {
            /*引入kindEditor插件*/
            KindEditor.html("#editor_id","");//添加内容时,清除上一次添加的内容

            //修改操作
           var article= $("#articleList").jqGrid("getRowData",id);
           //给表单设置默认值
            $("#article-id").val(article.id);
            $("#article-author").val(article.author);
            $("#article-title").val(article.title);
            KindEditor.html("#editor_id",article.content);//获得内容

            $("#article-oper").val(oper);//获得操作的类型是什么,然后传到后台进行相应的操作


            //显示模态框
            $("#article-modal").modal("show");
        }

        //初始化kindEditor
        KindEditor.create("#editor_id",{
            //显示图片空间
            allowFileManager:true,
            //上传图片的路径
            uploadJson:'${pageContext.request.contextPath}/article/upload',
            //图片空间对应的地址
            fileManagerJson:'${pageContext.request.contextPath}/article/browser',
            //上传图片时接受的参数
            filePostName:'picName',
            //设置只能改变高度,不能改变宽度
            resizeType:1,

            //kindEditor集成项目时需要添加此方法,把值同步到textarea文本框
            afterBlur:function () {
                this.sync();
            }
        });
        //文件添加
        function save() {
            $.ajax({
                url:'${pageContext.request.contextPath}/article/editArticle',
                data:$("#article-form").serialize(),
                dataType:'json',
                type:'post',
                success:function () {
                    //关闭模态框
                    $("#article-modal").modal("hide");
                    //关闭模态框后刷新页面
                    $("#articleList").trigger("reloadGrid");
                }
            });
        }

    </script>
</head>
<body>
<%--添加文章和修改文章--%>
<div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#home"  aria-controls="home" role="tab" data-toggle="tab">所有文章</a>
        </li>
        <li role="presentation">
            <a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" onclick="openModal('add')">添加文章</a>
        </li>
    </ul>
</div>
<table id="articleList"></table>
<div id="pager" style="height: 60px"></div>

<%--添加文章的模态框--%>
<div class="modal fade" tabindex="-1" role="dialog" id="article-modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 702px">
            <%--模态框的头--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加文章</h4>
            </div>
                <%--模态框的主体--%>
            <div class="modal-body">
                <%--添加文章的标题和作者,可以采用水平表单的样式--%>
                    <form class="form-horizontal" id="article-form">
                        <%--用到的id和操作类型--%>
                            <input type="hidden" id="article-id" name="id">
                            <input type="hidden" id="article-oper" name="oper">

                        
                        
                        <div class="form-group">
                            <label for="article-title" class="col-sm-2 control-label">文章标题</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="title" id="article-title" placeholder="文章标题">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="article-author" class="col-sm-2 control-label">文章作者</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="author" id="article-author" placeholder="文章作者">
                            </div>
                        </div>

                        <%--输入文章的内容--%>
                        <%--利用kindEditor--%>
                        <div class="form-group">
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                        </div>


                    </form>



                <%--用于存放引入kindEditor--%>




            </div>
                <%----%>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="save()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->






</body>
</html>