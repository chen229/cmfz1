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
            $("#albumList").jqGrid({
                url :'${pageContext.request.contextPath}/album/findAllAlbum',
                datatype : "json",
                height : 400,
                colNames : [ '编号', '标题', '封面', '章节数', '评分','作者', '播音员','简介','创建时间' ],
                colModel : [
                    {name : 'id',editable:false,align:'center'},
                    {name : 'title',editable:true,align:'center'},
                    {name : 'cover',editable:true,align:'center',edittype:'file',
                        formatter:function (value,options,row) {
                            var temp="<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/image/"+row.cover+"'>";
                            return temp;
                        }

                    },
                    {name : 'count',editable:false,align:'center'},
                    {name : 'score',editable:true,align:'center'},
                    {name : 'author',editable:true,align:'center'},
                    {name : 'reader',editable:true,align:'center'},
                    {name : 'brief',editable:true,align:'center'},
                    {name : 'createTime',editable:false,width:250,align:'center'}
                ],
                rowNum : 3,
                rowList : [ 3, 6, 10,15],
                pager : '#pager',
                viewrecords : true,
                multiselect : true,
                editurl:'${pageContext.request.contextPath}/album/addAlbum',
                styleUI:"Bootstrap",
                autowidth:true,
                subGrid : true,
                caption : "Grid as Subgrid",
                subGridRowExpanded : function(subgrid_id, albumId) {
                    // var subgrid_table_id, pager_id;
                    var subgrid_table_id = subgrid_id + "_t";
                   var  pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html(
                        "<table id='" + subgrid_table_id
                        + "' class='scroll'></table><div id='"
                        + pager_id + "' class='scroll'></div>");
                    jQuery("#" + subgrid_table_id).jqGrid(
                        {
                            url :'${pageContext.request.contextPath}/chapter/findByAlbumId?albumId='+albumId,
                            datatype : "json",
                            colNames : [ '章节名称', '大小', '时长','文件名','创建时间','在线播放' ],
                            colModel : [
                                // {name : "cid",editable:false,align:'center',width:70,display:true },
                                {name : "title",editable:true,align:'center',width:70},
                                {name : "size",editable:false,align:'center',width:50},
                                {name : "duration",editable:false,align:'center',width:50},
                                {name : "name",editable:true,align:'center',width:100,edittype:'file'},
                                {name : "createTime",editable:false,align:'center',width:150},

                                {name : "aa",width:250,formatter:function (value,options,row) {
                                        return "<audio controls loop>\n" +
                                            "  <source src='${pageContext.request.contextPath}/album/music/"+row.name+"' type=\"audio/ogg\">\n" +
                                            "</audio>";
                                    }},

                            ],
                            rowNum : 3,
                            rowList : [ 3, 6, 10,15],
                            pager : pager_id,
                            height : '100%',
                            autowidth:true,
                            autoencode:true,
                            sortname:'id',
                            sortorder:'desc',
                            editurl:'${pageContext.request.contextPath}/chapter/addChapter?albumId='+albumId,
                            styleUI:"Bootstrap",

                        });
                    jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                        "#" + pager_id, {
                            edit : true,
                            add : true,
                            del : false
                        },
                        {
                            //控制子表的修改操作
                            closeAfterEdit:true,
                        },
                        {
                            //控制子表的添加操作
                            closeAfterAdd:true,
                            afterSubmit:function (response) {
                                var status=response.responseJSON.status;
                                var cid=response.responseJSON.id;
                                if(status){
                                    $.ajaxFileUpload({
                                        url:'${pageContext.request.contextPath}/chapter/upLoad',
                                        fileElementId:'name',
                                        data:{cid:cid},
                                        type:'post',
                                        success:function () {
                                            $("#" + subgrid_table_id).trigger("reloadGrid");
                                        }
                                    });
                                    return "123456";
                                }
                            }
                        });
                },
                subGridRowColapsed : function(subgrid_id, albumId) {
                    // this function is called before removing the data
                    //var subgrid_table_id;
                    //subgrid_table_id = subgrid_id+"_t";
                    //jQuery("#"+subgrid_table_id).remove();
                }
            }).navGrid("#pager",{search:false,edit:true,del:false},
                {
                    //控制修改
                    closeAfterEdit:close,
                },
                {
                    //控制添加
                    closeAfterAdd:close,
                    afterSubmit:function (response) {
                        var status=response.responseJSON.status;
                        var id=response.responseJSON.id;

                        if(status){
                            alert(status);
                            alert(id);
                            $.ajaxFileUpload({
                                url:'${pageContext.request.contextPath}/album/upLoad',
                                fileElementId:'cover',
                                data:{id:id},
                                type:"post",
                                success:function () {
                                    $("#albumList").trigger("reloadGrid");
                                }
                            });
                        }
                        return "123456";
                    }

                },
                {
                    //控制删除
                }
                );
            jQuery("#albumList").jqGrid('navGrid', '#pager', {
                add : false,
                edit : false,
                del : false
            });
        });

    </script>
</head>
<body>
    <table id="albumList"></table>
    <div id="pager" style="height: 60px"></div>
</body>
</html>