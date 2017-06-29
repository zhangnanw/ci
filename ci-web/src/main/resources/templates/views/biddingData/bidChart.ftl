<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>企业竞争情报系统-欢迎您</title> <#include "/views/styles.ftl"/> <#include
"/views/plugins.ftl"/>
<script src="/js/plugins/echart/echarts.min.js"></script>
<script src="/js/jquery-2.1.1.js"></script>
<!-- 点击搜索 -->
<script type="text/javascript">
	function searchAllData() {
		alert("begin");
		showcapacity();
		refreshbar();
		refreshpie();
		refreshline();
		refreshmul();
		showd();
		showa();
		showb();
		showc()
		alert("over");
	}
</script>
<!-- 中标企业容量前20 -->
<script type="text/javascript">
	function showcapacity() {
		var mychart = echarts.init(document
				.getElementById('flot-capacitybar-chart'));
		var type = $('#timeType option:selected').val();
		alert(type);
		$.ajax({
			type : "POST",
			url : "/biddingData/showBar",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {//标题
						text : '中标企业分布',
						x : 'center'
					},
					tooltip : {//鼠标悬浮
						trigger : 'axis'
					},
					toolbox : {//工具栏
						show : true,
						orient : 'horizontal',
						feature : {//图例
							calculable : false,
							dataView : {//数据视图
								show : true,
								title : '数据视图',
								readOnly : true
							},
							magicType : {//所有图例菜单
								show : true,
								title : {
									bar : '柱形图',
									line : '折线图'
								},
								type : [ 'bar', 'line' ]
							},
							restore : {
								show : true,
								title : '还原',
								color : 'black'
							}
						}
					},
					xAxis : {
						type : 'category',
						name : '企业名称',
						axisLabel : {//显示所有横坐标
							interval : 0
						},
						data : reJson.campany,
						//设置字体倾斜  
						axisLabel : {
							interval : 0,
							rotate : 45,//倾斜度 -90 至 90 默认为0  
							margin : 12,
							textStyle : {
								/*  fontWeight:"bolder",   */
								color : "#000000",
								fontSize : 10
							}
						},
					},
					yAxis : {
						type : 'value',
						name : '总容量：(兆瓦)'
					},
					series : [ {
						name : '值',
						type : 'bar',
						itemStyle : {
							normal : {
								color : '#60C0DD',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#2932E1'
								}
							}
						},

						data : reJson.mount
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
<!-- 中标企业数量前20 -->
<script type="text/javascript">
	function refreshbar() {
		var mychart = echarts.init(document.getElementById('flot-bar-chart'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showBar",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {//标题
						text : '中标企业分布',
						x : 'center'
					},
					tooltip : {//鼠标悬浮
						trigger : 'axis'
					},
					toolbox : {//工具栏
						show : true,
						orient : 'horizontal',
						feature : {//图例
							calculable : false,
							dataView : {//数据视图
								show : true,
								title : '数据视图',
								readOnly : true
							},
							magicType : {//所有图例菜单
								show : true,
								title : {
									bar : '柱形图',
									line : '折线图'
								},
								type : [ 'bar', 'line' ]
							},
							restore : {
								show : true,
								title : '还原',
								color : 'black'
							}
						}
					},
					xAxis : {
						type : 'category',
						name : '企业名称',
						axisLabel : {//显示所有横坐标
							interval : 0
						},
						data : reJson.campany,
						//设置字体倾斜  
						axisLabel : {
							interval : 0,
							rotate : 45,//倾斜度 -90 至 90 默认为0  
							margin : 12,
							textStyle : {
								/*  fontWeight:"bolder",   */
								color : "#000000"
							}
						},
					},
					yAxis : {
						type : 'value',
						name : '数量：(兆瓦)'
					},
					series : [ {
						name : '值',
						type : 'bar',
						itemStyle : {
							normal : {
								color : '#60C0DD',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#2932E1'
								}
							}
						},

						data : reJson.mount
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
<!-- 中标产品分类 -->
<script type="text/javascript">
	function refreshpie() {
		var mychart = echarts.init(document.getElementById('flot-pie-chart'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showPie",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {
						text : '中标项目单多晶占比',
						x : 'center'
					},
					tooltip : {
						trigger : 'item',
						formatter : "{b} :{d}%"
					},
					legend : {
						x : 'center',
						y : '25',
						data : [ '单晶硅', '多晶硅', '单晶硅、多晶硅', '未知' ]
					},
					series : [ {
						name : '访问来源',
						type : 'pie',
						radius : '55%',
						center : [ '50%', '60%' ],
						data : reJson,
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter : '{d}%'
								},
								labelLine : {
									show : true
								}
							}
						},
						color : [ '#FCCE10', '#E87C25', '#60C0DD', '#C1CC24' ]
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
<!-- 晶澳投标价格 -->
<script type="text/javascript">
	function refreshline() {
		var mychart = echarts.init(document.getElementById('flot-line-chart'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showLine",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {//标题
						text : '晶澳投标价格',
						x : 'center'
					},
					tooltip : {//鼠标悬浮
						trigger : 'axis',
						formatter : "{a}:{c}<br/>{a1}:{c1}"
					},
					legend : {//标题
						data : [ '单晶', '多晶' ],
						x : 'left'
					},
					toolbox : {//工具栏
						show : true,
						orient : 'horizontal',
						feature : {//图例
							calculable : false,
							dataView : {//数据视图
								show : true,
								title : '数据视图',
								readOnly : true
							},
							magicType : {//所有图例菜单
								show : true,
								title : {
									line : '折线图',
									bar : '柱形图',
									stack : '堆积图',
									tiled : '平铺图'
								},
								type : [ 'line', 'bar', 'stack', 'tiled' ]
							},
							restore : {
								show : true,
								title : '还原',
								color : 'black'
							}
						}
					},
					xAxis : {
						name : '月份',
						type : 'category',
						axisLabel : {//显示所有横坐标
							interval : 0
						},
						data : reJson.xdate
					},
					yAxis : {
						name : '万元',
						nameRotate : -0.1,
						type : 'value'
					},
					series : [ {
						name : '单晶',
						type : 'line',
						itemStyle : {
							normal : {
								color : '#333333',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#E87C25'
								}
							}
						},

						data : reJson.single
					}, {
						name : '多晶',
						type : 'line',
						itemStyle : {
							normal : {
								color : '#60C0DD',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#333333'
								}
							}
						},

						data : reJson.Multi
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
<!-- 2016全国LED招标总览 -->
<script type="text/javascript">
	function refreshmul() {
		var mychart = echarts.init(document
				.getElementById('flot-line-chart-multi'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showMul",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {//标题
						text : '2016年1-10月份全国LED招标总览图',
						x : 'center'
					},
					tooltip : {//鼠标悬浮
						trigger : 'axis',
						formatter : "{a}:{c}<br/>{a1}:{c1}"
					},
					legend : {//标题
						data : [ '中标数量', '中标金额' ],
						x : 'left'
					},
					toolbox : {//工具栏
						show : true,
						orient : 'horizontal',
						feature : {//图例
							calculable : false,
							dataView : {//数据视图
								show : true,
								title : '数据视图',
								readOnly : true
							},
							magicType : {//所有图例菜单
								show : true,
								title : {
									line : '折线图',
									bar : '柱形图',
									stack : '堆积图',
									tiled : '平铺图'
								},
								type : [ 'line', 'bar', 'stack', 'tiled' ]
							},
							restore : {
								show : true,
								title : '还原',
								color : 'black'
							}
						}
					},
					xAxis : {
						name : '月份',
						type : 'category',
						axisLabel : {//显示所有横坐标
							interval : 0
						},
						data : reJson.xdate
					},
					yAxis : {
						name : 'Y轴单位',
						type : 'value'
					},
					series : [ {
						name : '中标数量',
						type : 'line',
						itemStyle : {
							normal : {
								color : '#333333',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#E87C25'
								}
							}
						},

						data : reJson.mount
					}, {
						name : '中标金额',
						type : 'bar',
						barWidth : 30,
						itemStyle : {
							normal : {
								color : '#60C0DD',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#333333'
								}
							}
						},

						data : reJson.count
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
<!-- 中标前20名企业中2016全国LED招标总览 -->
<script type="text/javascript">
	function showd() {
		var mychart = echarts.init(document
				.getElementById('flot-line-chartd-multi'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showMul",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {//标题
						text : '2016年1-10月份全国LED招标总览图',
						x : 'center'
					},
					tooltip : {//鼠标悬浮
						trigger : 'axis',
						formatter : "{a}:{c}<br/>{a1}:{c1}"
					},
					legend : {//标题
						data : [ '中标数量', '中标金额' ],
						x : 'left'
					},
					toolbox : {//工具栏
						show : true,
						orient : 'horizontal',
						feature : {//图例
							calculable : false,
							dataView : {//数据视图
								show : true,
								title : '数据视图',
								readOnly : true
							},
							magicType : {//所有图例菜单
								show : true,
								title : {
									line : '折线图',
									bar : '柱形图',
									stack : '堆积图',
									tiled : '平铺图'
								},
								type : [ 'line', 'bar', 'stack', 'tiled' ]
							},
							restore : {
								show : true,
								title : '还原',
								color : 'black'
							}
						}
					},
					xAxis : {
						name : '月份',
						type : 'category',
						axisLabel : {//显示所有横坐标
							interval : 0
						},
						data : reJson.xdate
					},
					yAxis : {
						name : 'Y轴单位',
						type : 'value'
					},
					series : [ {
						name : '中标数量',
						type : 'line',
						itemStyle : {
							normal : {
								color : '#333333',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#E87C25'
								}
							}
						},

						data : reJson.mount
					}, {
						name : '中标金额',
						type : 'bar',
						barWidth : 30,
						itemStyle : {
							normal : {
								color : '#60C0DD',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#333333'
								}
							}
						},

						data : reJson.count
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
<!-- 晶澳国内中标情况 -->
<script type="text/javascript">
	function showa() {
		var mychart = echarts.init(document.getElementById('flot-bara-chart'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showBar",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {//标题
						text : '晶澳国内中标情况',
						x : 'center'
					},
					tooltip : {//鼠标悬浮
						trigger : 'axis'
					},
					toolbox : {//工具栏
						show : true,
						orient : 'horizontal',
						feature : {//图例
							calculable : false,
							dataView : {//数据视图
								show : true,
								title : '数据视图',
								readOnly : true
							},
							magicType : {//所有图例菜单
								show : true,
								title : {
									bar : '柱形图',
									line : '折线图'
								},
								type : [ 'bar', 'line' ]
							},
							restore : {
								show : true,
								title : '还原',
								color : 'black'
							}
						}
					},
					xAxis : {
						type : 'category',
						name : '企业名称',
						axisLabel : {//显示所有横坐标
							interval : 0
						},
						data : reJson.campany,
						//设置字体倾斜  
						axisLabel : {
							interval : 0,
							rotate : 45,//倾斜度 -90 至 90 默认为0  
							margin : 12,
							textStyle : {
								/*  fontWeight:"bolder",   */
								color : "#000000"
							}
						},
					},
					yAxis : {
						type : 'value',
						name : '总容量：(兆瓦)'
					},
					series : [ {
						name : '值',
						type : 'bar',
						itemStyle : {
							normal : {
								color : '#60C0DD',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#2932E1'
								}
							}
						},

						data : reJson.mount
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
<!-- 前20名企业中标产品分类 -->
<script type="text/javascript">
	function showc() {
		var mychart = echarts.init(document.getElementById('flot-piec-chart'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showPie",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {
						text : '中标项目单多晶占比',
						x : 'center'
					},
					tooltip : {
						trigger : 'item',
						formatter : "{b} :{d}%"
					},
					legend : {
						x : 'center',
						y : '25',
						data : [ '单晶硅', '多晶硅', '单晶硅、多晶硅', '未知' ]
					},
					series : [ {
						name : '访问来源',
						type : 'pie',
						radius : '55%',
						center : [ '50%', '60%' ],
						data : reJson,
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter : '{d}%'
								},
								labelLine : {
									show : true
								}
							}
						},
						color : [ '#FCCE10', '#E87C25', '#60C0DD', '#C1CC24' ]
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
<!-- 晶澳中标客户类型-->
<script type="text/javascript">
	function showb() {
		var mychart = echarts.init(document.getElementById('flot-pieb-chart'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showPie",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {
						text : '晶澳中标客户类型',
						x : 'center'
					},
					tooltip : {
						trigger : 'item',
						formatter : "{b} :{d}%"
					},
					legend : {
						x : 'center',
						y : '25',
						data : [ '核心用户', '重点客户', '区域客户', '其他客户' ]
					},
					series : [ {
						name : '访问来源',
						type : 'pie',
						radius : '55%',
						center : [ '50%', '60%' ],
						data : reJson,
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter : '{d}%'
								},
								labelLine : {
									show : true
								}
							}
						},
						color : [ '#FCCE10', '#E87C25', '#60C0DD', '#C1CC24' ]
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
<!-- 中标区域分布 -->
<script type="text/javascript">
	function refreshpro() {
		var mychart = echarts.init(document
				.getElementById('flot-bar-chart-pro'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showBar",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			},
			async : false,
			dataType : 'json',
			success : function(reJson) {
				var option = {
					title : {//标题
						text : '中标企业分布',
						x : 'center'
					},
					tooltip : {//鼠标悬浮
						trigger : 'axis'
					},
					toolbox : {//工具栏
						show : true,
						orient : 'horizontal',
						feature : {//图例
							calculable : false,
							dataView : {//数据视图
								show : true,
								title : '数据视图',
								readOnly : true
							},
							magicType : {//所有图例菜单
								show : true,
								title : {
									bar : '柱形图',
									line : '折线图'
								},
								type : [ 'bar', 'line' ]
							},
							restore : {
								show : true,
								title : '还原',
								color : 'black'
							}
						}
					},
					xAxis : {
						type : 'category',
						name : '企业名称',
						axisLabel : {//显示所有横坐标
							interval : 0
						},
						data : reJson.campany,
						//设置字体倾斜  
						axisLabel : {
							interval : 0,
							rotate : 45,//倾斜度 -90 至 90 默认为0  
							margin : 12,
							textStyle : {
								/*  fontWeight:"bolder",   */
								color : "#000000"
							}
						},
					},
					yAxis : {
						type : 'value',
						name : '总容量：(兆瓦)'
					},
					series : [ {
						name : '值',
						type : 'bar',
						itemStyle : {
							normal : {
								color : '#60C0DD',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color : '#2932E1'
								}
							}
						},

						data : reJson.mount
					} ]
				};
				mychart.setOption(option);
			},
			error : function() {
				alert("异常！");
			}
		});
	}
</script>
</head>

<body>
	<div id="wrapper">
		<#include "/views/left.ftl"/>

		<div id="page-wrapper" class="gray-bg">
			<#include "/views/header.ftl"/>

			<!-- BEGIN PAGE CONTAINER-->
			<div id="page-container">
				<div class="row wrapper border-bottom white-bg page-heading">
					<div class="col-lg-10" style="margin-bottom: 20px;">
						<h2>中标数据</h2>
						<ol class="breadcrumb">
							<li><a href="/welcome/index">首页</a></li>
							<li><a>中标数据</a></li>
							<li class="active"><strong>数据报表</strong></li>
						</ol>
					</div>
					
					<div class="ibox-content m-b-sm border-bottom">

						<div class="row">
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="productType" style="margin-top: 10px;">统计区间</label> <select
										name="timeType" id="timeType" class="form-control"
										style="margin-top: -33px; margin-left: 60px;">
										<option value="1" selected>月度统计</option>
										<option value="2">季度统计</option>
										<option value="3">半年统计</option>
										<option value="4">年度统计</option>
									</select>
								</div>
							</div>

							<div class="col-sm-4" style="height: 20px;">
								<div class="form-group" id="daterange_publishTime">
									<label class="control-label" style="margin-left: 80px;margin-top: 10px;">发布时间</label>

									<div class="input-daterange input-group" id="datepicker"
										style="margin-top: -30px; margin-left: 140px;width: 250px">
										<input type="text" class="input-sm form-control"
											name="publishStartTime" id="publishStartTime" /> <span
											class="input-group-addon">到</span> <input type="text"
											class="input-sm form-control" name="publishEndTime"
											id="publishEndTime" />
									</div>

									
										<a class="btn btn-w-m btn-info" href="javascript:void(0);" style="margin-top: -50px; margin-left: 420px;"
											onclick="searchAllData()">查询</a>
			
								</div>
							</div>

						</div>

					</div>
				</div>
				
				<div class="wrapper wrapper-content animated fadeInRight">
					<div class="row">
						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>
										中标企业分布 <small>容量前20名</small>
									</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">按中标容量</a></li>
											<li><a href="graph_flot.html#">按中标数量</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-capacitybar-chart"></div>
										<script type="text/javascript">
											showcapacity();
										</script>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>
										中标企业分布 <small>数量前20名</small>
									</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">按中标容量</a></li>
											<li><a href="graph_flot.html#">按中标数量</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-bar-chart"></div>
										<script type="text/javascript">
											refreshbar();
										</script>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>中标产品分类</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">Config option 1</a></li>
											<li><a href="graph_flot.html#">Config option 2</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-pie-chart"></div>
										<script type="text/javascript">
											refreshpie();
										</script>
									</div>
								</div>
							</div>
						</div>

						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>晶澳投标价格</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">Config option 1</a></li>
											<li><a href="graph_flot.html#">Config option 2</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">

									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-line-chart"></div>
										<script type="text/javascript">
											refreshline();
										</script>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>
										晶澳国内中标情况 <small></small>
									</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">按中标容量</a></li>
											<li><a href="graph_flot.html#">按中标数量</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-bara-chart"></div>
										<script type="text/javascript">
											showa();
										</script>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>晶澳中标客户类型</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">Config option 1</a></li>
											<li><a href="graph_flot.html#">Config option 2</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-pieb-chart"></div>
										<script type="text/javascript">
											showb();
										</script>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>中标区域分布</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">Config option 1</a></li>
											<li><a href="graph_flot.html#">Config option 2</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-bar-chart-pro"></div>
										<script type="text/javascript">
											refreshpro();
										</script>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>中标前20名企业中标产品分类</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">Config option 1</a></li>
											<li><a href="graph_flot.html#">Config option 2</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-piec-chart"></div>
										<script type="text/javascript">
											showc();
										</script>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>2016年全国LED招标总览图</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">Config option 1</a></li>
											<li><a href="graph_flot.html#">Config option 2</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-line-chart-multi"></div>
										<script type="text/javascript">
											refreshmul();
										</script>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-12">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>中标前20名企业2016年全国LED招标总览图</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
										</a> <a class="dropdown-toggle" data-toggle="dropdown"
											href="graph_flot.html#"> <i class="fa fa-wrench"></i>
										</a>
										<ul class="dropdown-menu dropdown-user">
											<li><a href="graph_flot.html#">Config option 1</a></li>
											<li><a href="graph_flot.html#">Config option 2</a></li>
										</ul>
										<a class="close-link"> <i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="ibox-content">
									<div class="flot-chart">
										<div class="flot-chart-content" id="flot-line-chartd-multi"></div>
										<script type="text/javascript">
											showd();
										</script>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!-- END PAGE CONTAINER-->

				<#include "/views/footer.ftl"/>
			</div>

		</div>
</body>
</html>
