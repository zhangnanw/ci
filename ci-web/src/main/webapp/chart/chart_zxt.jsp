<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 1.引入jquery库 -->
<script type="text/javascript" src="../script/jquery-1.4.2.min.js"></script>
<!-- 2.引入highcharts的核心文件 -->
<script type="text/javascript" src="../script/highcharts.js"></script>
<!-- 3.引入导出需要的js库文件 -->
<script type="text/javascript" src="../script/exporting.js"></script>
<!-- 时间控件 -->
<script  type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
</head>
<script type="text/javascript" language="javascript">
function btn(){
	var $btn=$("input.btn");
	    $btn.bind("click",function(){
		var jsonXData = [];
		var jsonyD1 = [];
        var jsonyD2 = [];
        var jsonyD3=[];
        var jsonyD4=[];
		$.ajax({
			type:"post",
			url:"../chartData/list",
			data:{ctBeginTime:$("input[name=ctBeginTime]").val(),
				ctEndTime:$("input[name=ctEndTime]").val()},
			dataType:"json",
			cache: false,
            async: false,
			success:function(data){
				if(data.RowCount==0){
					alert("没有可显示的数据！")
				}
                if (data.RowCount > 0) {
                    for (var i = 0; i < data.RowCount; i++) {
                        var rows = data.Rows[i];
                        var Time = rows.ctTime;
                        var bidd = rows.biddCount;
                        var biddInc=rows.biddIncrement;
                        var biddM = rows.biddMgCount;
                        var biddMInc=rows.biddMgIncrement;
                        jsonXData.push(Time); //赋值
                        jsonyD1.push(parseInt(bidd));
                        jsonyD2.push(parseInt(biddInc));
                        jsonyD3.push(parseInt(biddM));
                        jsonyD4.push(parseInt(biddMInc));
                    } 
                    var chart;
                    chart = new Highcharts.Chart({
                        chart: {
                            renderTo: 'container1',
                            type: 'line',    //图表类别，可取值有：line、spline、area、areaspline、bar、column等
                            marginRight: 220,
                            marginBottom: 25
                        },
                        title: {
                            text: 'bidd总量',  //设置一级标题
                            x: -20                //center
                        },
                        xAxis: {     //X轴数据
                            categories: jsonXData
                        },
                        yAxis: {     //Y轴显示文字
                            lineWidth: 2,
                            title: {
                                text: '数量/条'
                            }
                        },
                        tooltip: {    //鼠标放在数据点的显示信息，但是当设置显示了每个节点的数据项的值时就不会再有这个显示信息
                            formatter: function() {
                                return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 0);
                            }
                        },
                        plotOptions: {
                        	column: {
                                dataLabels: {
                                    enabled: true    //显示每条曲线每个节点的数据项的值
                                },
                                enableMouseTracking: true  //是否显示title
                            }
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'top',
                            x: -20,
                            y: 200,
                            borderWidth: 0
                        },
                        exporting:{
                        	enabled:true,
                        	url:"../image/"   //导出图片的URL，默认导出是需要连到官方网站去的哦
                        },
                        series: [{
                            name: 'biddCount',
                            data: jsonyD1 
                        }]
                    });
                    
                    var chart2;
                    chart2 = new Highcharts.Chart({
                        chart: {
                            renderTo: 'container2',
                            type: 'line',    //图表类别，可取值有：line、spline、area、areaspline、bar、column等
                            marginRight: 220,
                            marginBottom: 25
                        },
                        title: {
                            text: 'bidd增量',  //设置一级标题
                            x: -20                //center
                        },
                        subtitle:{
                        	text:'',  //设置二级标题
                        	x:-20
                        },
                        xAxis: {     //X轴数据
                            categories: jsonXData
                        },
                        yAxis: {     //Y轴显示文字
                            lineWidth: 2,
                            title: {
                                text: '数量/条'
                            }
                        },
                        tooltip: {    //鼠标放在数据点的显示信息，但是当设置显示了每个节点的数据项的值时就不会再有这个显示信息
                            formatter: function() {
                                return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 0);
                            }
                        },
                        plotOptions: {
                        	column: {
                                dataLabels: {
                                    enabled: true    //显示每条曲线每个节点的数据项的值
                                },
                                enableMouseTracking: true  //是否显示title
                            }
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'top',
                            x: -20,
                            y: 200,
                            borderWidth: 0
                        },
                        exporting:{
                        	enabled:true,
                        	url:"../image/"   //导出图片的URL，默认导出是需要连到官方网站去的哦
                        },
                        series: [{
                            name: 'biddInc',
                            data: jsonyD2 
                        }]
                    });
                    
                    var chart3;
                    chart3 = new Highcharts.Chart({
                        chart: {
                            renderTo: 'container3',
                            type: 'line',    //图表类别，可取值有：line、spline、area、areaspline、bar、column等
                            marginRight: 220,
                            marginBottom: 25
                        },
                        title: {
                            text: 'bidd_merge总量',  //设置一级标题
                            x: -20                //center
                        },
                        subtitle:{
                        	text:'',  //设置二级标题
                        	x:-20
                        },
                        xAxis: {     //X轴数据
                            categories: jsonXData
                        },
                        yAxis: {     //Y轴显示文字
                            lineWidth: 2,
                            title: {
                                text: '数量/条'
                            }
                        },
                        tooltip: {    //鼠标放在数据点的显示信息，但是当设置显示了每个节点的数据项的值时就不会再有这个显示信息
                            formatter: function() {
                                return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 0);
                            }
                        },
                        plotOptions: {
                        	column: {
                                dataLabels: {
                                    enabled: true    //显示每条曲线每个节点的数据项的值
                                },
                                enableMouseTracking: true  //是否显示title
                            }
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'top',
                            x: -20,
                            y: 200,
                            borderWidth: 0
                        },
                        exporting:{
                        	enabled:true,
                        	url:"../image/"   //导出图片的URL，默认导出是需要连到官方网站去的哦
                        },
                        series: [{
                            name: 'biddMergeCount',
                            data: jsonyD3 
                        }]
                    });
                    
                    var chart4;
                    chart4 = new Highcharts.Chart({
                        chart: {
                            renderTo: 'container4',
                            type: 'line',    //图表类别，可取值有：line、spline、area、areaspline、bar、column等
                            marginRight: 220,
                            marginBottom: 25
                        },
                        title: {
                            text: 'bidd_merge增量',  //设置一级标题
                            x: -20                //center
                        },
                        subtitle:{
                        	text:'',  //设置二级标题
                        	x:-20
                        },
                        xAxis: {     //X轴数据
                            categories: jsonXData
                        },
                        yAxis: {     //Y轴显示文字
                            lineWidth: 2,
                            title: {
                                text: '数量/条'
                            }
                        },
                        tooltip: {    //鼠标放在数据点的显示信息，但是当设置显示了每个节点的数据项的值时就不会再有这个显示信息
                            formatter: function() {
                                return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 0);
                            }
                        },
                        plotOptions: {
                        	column: {
                                dataLabels: {
                                    enabled: true    //显示每条曲线每个节点的数据项的值
                                },
                                enableMouseTracking: true  //是否显示title
                            }
                        },
                        legend: {
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'top',
                            x: -20,
                            y: 200,
                            borderWidth: 0
                        },
                        exporting:{
                        	enabled:true,
                        	url:"../image/"   //导出图片的URL，默认导出是需要连到官方网站去的哦
                        },
                        series: [{
                            name: 'biddMergeInc',
                            data: jsonyD4 
                        }]
                    });
                    
                }
			}
		});
	});
}
$(document).ready(function(){
	btn();
});
</script>
<body>
	<form name="Form" id="form1" method='post' action='../chartData/list'>
	<table border="1" align="center">
			<tr>
				<td  width="10%" class="tableleft">开始时间：</td>
				<td ><input class="Wdate" type="text" name="ctBeginTime" onClick="WdatePicker()" /></td>

				<td  width="10%" class="tableleft">结束时间：</td>
				<td ><input class="Wdate" type="text" name="ctEndTime" onClick="WdatePicker()" /></td>
			<tr>
				<td colspan="4"><center>
						<input type="button" class="btn" value="统计"/>
				</center></td>
			</tr>
		</table>
	</form>
	<!-- 导入容器显示图表 -->
	<div style="margin: 0 2em">
		<div id="container1" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
	</div>
	<br/>
	<br/>
	<div style="margin: 0 2em">
		<div id="container2" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
	</div>
	<br/>
	<br/>
	<div style="margin: 0 2em">
		<div id="container3" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
	</div>
	<br/>
	<br/>
	<div style="margin: 0 2em">
		<div id="container4" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
	</div>
</body>
</html>