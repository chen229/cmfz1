<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="assets/js/jquery-2.2.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <script src="assets/js/scripts.js"></script>
    <script src="assets/js/jquery.validate.min.js"></script>
    <script>
        //页面完全加载
        $(function () {
            //注册时表单验证
            $("#user1").blur(function () {
                var username=$(this).val();
                $.ajax({
                    url:'${pageContext.request.contextPath}/admin/findByUsername',
                    data:'username='+username,
                    dataType:'text',
                    type:'get',
                    success:function (data) {
                        if(data=="Y"){
                            $("#userMsg").html("<b><font style='color: green'>用户名可用</font></b>")
                        }else {
                            $("#userMsg").html("<b><font style='color: red'>用户名已注册</font></b>")
                        }
                    }
                })
            })


          /*  //表单验证
            var usm=false;
            var pwd=false;
            var code=false;
            //验证用户名
            $("#user1").blur(function () {
                //获取当前输入框的值
                var username=$(this).val();
                if(username==""){
                    $("#userMsg").html("<b><font style='color: red'>用户名由数字和</font></b>")
                }else {
                    $("#userMsg").html("<b><font style='color: green'>验证通过</font></b>")
                    usm=true;
                }
            });

            //验证密码
            $("#pwd1").blur(function () {
                //获取当前输入框的值
                var password=$(this).val();
                if (password==""){
                    $("#pwdMsg").html("<b><font style='color: red'>密码必填</font></b>")
                }else {
                    if(password.length<6){
                        $("#pwdMsg").html("<b><font style='color: red'>密码长度不能少于6位数</font></b>")
                    }else {
                        $("#pwdMsg").html("<b><font style='color: green'>验证通过</font></b>")
                        pwd=true;
                    }
                }

            });
            //验证码验证
            $("#form-code").blur(function () {
                //获取当前输入框的值
                var code=$(this).val();
                if (code==""){
                    $("#codeMsg").html("<b><font style='color: red'>验证码必填</font></b>")
                } else {
                    if(code.length<4){
                        $("#codeMsg").html("<b><font style='color: red'>验证码长度不能少于4位数</font></b>")
                    }else {
                        $("#codeMsg").html("<b><font style='color: green'>验证通过</font></b>")
                        code=true;
                    }
                }
            });
*/
            // //表单总的验证
            // $("form").submit(function () {
            //     //把所有的input框的事件都执行一遍
            //     $("input").trigger("blur");
            //     if (usm && pwd && code) {
            //         $("form").submit();
            //     }else {
            //         return false;
            //     }
            // });







            //验证码
            <%--$("#captchaImage").click(function () {--%>
                <%--$("#captchaImage").prop("href", "${pageContext.request.contextPath}/admin/sendCode");--%>
            <%--});--%>

            //登录
           $("#captchaImage").click(function () {
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/sendCode",
                    type: "GET",
                    // data: $("#loginForm").serialize(),
                    dataType: "text",
                    success: function () {
                        location.href ="${pageContext.request.contextPath}/login/register.jsp";
                    }
                })
            })
        });


    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Register Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Welcome to register</h3>
                            <p>Please written your message</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="${pageContext.request.contextPath}/admin/registerAdmin" method="post"
                              class="login-form" id="loginForm" >
                            <span style="color: red" id="msg"></span>
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" id="user1" name="username" placeholder="请输入用户名..."
                                       class="form-username form-control" id="form-username" required>
                                <p id="userMsg"></p>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" id="pwd1" name="password" placeholder="请输入密码..."
                                       minlength="2" class="form-password form-control" id="form-password" required>
                                <p id="pwdMsg"></p>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="text" id="nickName1" name="nickname" placeholder="请输入昵称"
                                       minlength="2" class="form-password form-control" id="form-nickname" required>
                                <p id="nickMsg"></p>
                            </div>
                            <div class="form-group">
                              <%--  <img id="captchaImage" style="height: 48px" class="captchaImage"--%>
                                     <%--src="${pageContext.request.contextPath}/captcha/creatCaptcha" >--%>
                                  <p>请输入验证码:</p>
                                <input style="width: 287px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       type="test" name="enCode" id="form-code" required >
                                  <a href="#" id="captchaImage">获得验证码</a>
                                <p id="codeMsg"></p>
                            </div>
                            <a href=""><input type="submit" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                   id="registerButtonId" value="注册"></a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


</body>

</html>
