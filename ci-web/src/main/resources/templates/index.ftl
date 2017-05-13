<#assign base = request.contextPath />
<!DOCTYPE html>
<html>

<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>企业竞争情报系统</title>

	<link href="${base}/css/bootstrap.min.css" rel="stylesheet">
	<link href="${base}/font-awesome/css/font-awesome.css" rel="stylesheet">

	<link href="${base}/css/animate.css" rel="stylesheet">
	<link href="${base}/css/style.css" rel="stylesheet">

</head>

<body>
<div id="wrapper">
	<nav class="navbar-default navbar-static-side" role="navigation">
		<div class="sidebar-collapse">
			<!-- BEGIN SIDEBAR MENU -->
			<#include "/left.ftl"/>
			<!-- END SIDEBAR MENU -->

		</div>
	</nav>

	<div id="page-wrapper" class="gray-bg">
		<div class="row border-bottom">
			<nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
				<div class="navbar-header">
					&nbsp;
				</div>
				<ul class="nav navbar-top-links navbar-right">
					<li>
						<span class="m-r-sm text-muted welcome-message">欢迎您访问企业竞争情报系统</span>
					</li>

					<li>
						<a href="login.html">
							<i class="fa fa-sign-out"></i> 退出登录
						</a>
					</li>
					<li>
						&nbsp;
					</li>
				</ul>

			</nav>
		</div>

		<!-- BEGIN PAGE CONTAINER-->
		<div id="page-container">
		<#include "/right.ftl"/>
		</div>
		<!-- END PAGE CONTAINER-->

		<div class="footer">
			<div class="pull-right">
				&nbsp;
			</div>
			<div>
				<strong>Copyright</strong> Example Company &copy; 2014-2015
			</div>
		</div>
	</div>

</div>

<!-- Mainly scripts -->
<script src="${base}/js/jquery-2.1.1.js"></script>
<script src="${base}/js/bootstrap.min.js"></script>
<script src="${base}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${base}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- Flot -->
<script src="${base}/js/plugins/flot/jquery.flot.js"></script>
<script src="${base}/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="${base}/js/plugins/flot/jquery.flot.spline.js"></script>
<script src="${base}/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="${base}/js/plugins/flot/jquery.flot.pie.js"></script>
<script src="${base}/js/plugins/flot/jquery.flot.symbol.js"></script>
<script src="${base}/js/plugins/flot/jquery.flot.time.js"></script>

<!-- Custom and plugin javascript -->
<script src="${base}/js/inspinia.js"></script>
<script src="${base}/js/plugins/pace/pace.min.js"></script>

<!-- Sparkline -->
<script src="${base}/js/plugins/sparkline/jquery.sparkline.min.js"></script>

<script>
	$(document).ready(function() {

		var sparklineCharts = function(){
			$("#sparkline1").sparkline([34, 43, 43, 35, 44, 32, 44, 52], {
				type: 'line',
				width: '100%',
				height: '50',
				lineColor: '#1ab394',
				fillColor: "transparent"
			});

			$("#sparkline2").sparkline([32, 11, 25, 37, 41, 32, 34, 42], {
				type: 'line',
				width: '100%',
				height: '50',
				lineColor: '#1ab394',
				fillColor: "transparent"
			});

			$("#sparkline3").sparkline([34, 22, 24, 41, 10, 18, 16,8], {
				type: 'line',
				width: '100%',
				height: '50',
				lineColor: '#1C84C6',
				fillColor: "transparent"
			});
		};

		var sparkResize;

		$(window).resize(function(e) {
			clearTimeout(sparkResize);
			sparkResize = setTimeout(sparklineCharts, 500);
		});

		sparklineCharts();




		var data1 = [
			[0,4],[1,8],[2,5],[3,10],[4,4],[5,16],[6,5],[7,11],[8,6],[9,11],[10,20],[11,10],[12,13],[13,4],[14,7],[15,8],[16,12]
		];
		var data2 = [
			[0,0],[1,2],[2,7],[3,4],[4,11],[5,4],[6,2],[7,5],[8,11],[9,5],[10,4],[11,1],[12,5],[13,2],[14,5],[15,2],[16,0]
		];
		$("#flot-dashboard5-chart").length && $.plot($("#flot-dashboard5-chart"), [
					data1,  data2
				],
				{
					series: {
						lines: {
							show: false,
							fill: true
						},
						splines: {
							show: true,
							tension: 0.4,
							lineWidth: 1,
							fill: 0.4
						},
						points: {
							radius: 0,
							show: true
						},
						shadowSize: 2
					},
					grid: {
						hoverable: true,
						clickable: true,

						borderWidth: 2,
						color: 'transparent'
					},
					colors: ["#1ab394", "#1C84C6"],
					xaxis:{
					},
					yaxis: {
					},
					tooltip: false
				}
		);

	});
</script>
</body>
</html>
