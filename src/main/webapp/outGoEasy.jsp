<%@ page pageEncoding="UTF-8" %>
<script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
<script>
    var goEasy = new GoEasy({
        //发布的appkey
        appkey: "BC-872e282a2a424daf8e504d04f16b0a24"
    });
    goEasy.publish({
        //当前的channel名称
        channel: "TEST",
        //发送（发布）的内容
        message: "Hello, LALALALA!"
    });
</script>