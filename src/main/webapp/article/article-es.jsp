<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<div class="row">
    <div class="col-lg-1">
    </div>
    <div class="col-lg-6">
        <div class="input-group">
            <input type="text" class="form-control" id="input-value" placeholder="请输入内容...">
                <span class="input-group-btn">
                    <button class="btn btn-info" id="search1" type="button">搜索文章</button>
                </span>
        </div><!-- /input-group -->
    </div><!-- /.col-lg-6 -->
</div><!-- /.row -->
<script type="text/javascript">
    $("#search1").click(function () {
        var content= $("#input-value").val();
        $.ajax({
            url:'${pageContext.request.contextPath}/es/esArticle',
            data:'content='+content,
            dataType:'json',
            type:'post',
            success:function (data) {

            }
        })
    });






</script>