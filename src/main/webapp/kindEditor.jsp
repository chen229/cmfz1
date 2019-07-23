<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>kindEditor</title>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
                //相关参数配置
                width:'1000px',
                height:'500px',
                //展示图片空间
                allowFileManager:true,
                //图片空间对应的地址
                fileManagerJson:'${pageContext.request.contextPath}/kindEditor/browser',
                //上传图片额地址
                uploadJson:'${pageContext.request.contextPath}/kindEditor/upload',
                //上传图片时接收的参数
                filePostName:'picName'
            });
        });
    </script>



</head>
<body>
    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
         &lt;strong&gt;HTML内容&lt;/strong&gt;
    </textarea>
</body>
</html>