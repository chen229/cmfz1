<%@ page contentType="text/html; utf-8" isELIgnored="false" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ECharts</title>
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/china.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">



    $.ajax({
        url:'${pageContext.request.contextPath}/user/findByRegister',
        dataType:'json',
        type:'get',
        success:function (response) {
            myChart.setOption({
                series: [{
                    name: '男性',
                    type: 'line',
                    data: response.mList,
                },
                    {
                        name: '女性',
                        type: 'line',
                        data: response.fList,
                    }

                ]
            })
        }
    });

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '持明法洲用户注册2019年上半年折线图'
        },
        tooltip: {},
        legend: {
             data:['男性','女性'],
            left:'right',
        },
        xAxis: {
            data: ["一月份","二月份","三月份","四月份","五月份","六月份"]
        },
        yAxis: {},
        series: [{
            name: '男性',
            type: 'line',
            data: [5, 20, 36, 10, 10, 20]
        },
            {
                name: '女性',
                type: 'line',
                data: [5, 40, 36, 10, 35, 20]
            }

        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

</script>
</body>
</html>