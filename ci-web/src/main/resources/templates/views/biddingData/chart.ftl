<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>企业竞争情报系统-欢迎您</title> <#include "/views/styles.ftl"/>
<script src="/js/plugins/echart/charts.js"></script>
<script src="/js/plugins/echart/echarts.js"></script>
<script src="/js/jquery-2.1.1.js"></script>
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
				name:'企业名称',
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
				name:'总容量：(兆瓦)'
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
				/* var mount=[];
				var type=[];
				var remount=reJson.mount;
				var retype=reJson.type;
				for(var i=0;i<remount.length;i++){
					mount.push(remount[i]);
					type.push(retype[i]);
				} */
				var option = {
				    title : {
				        text: '中标项目单多晶占比',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{b} :{d}%"
				    },
				    legend: {
				        x : 'center',
				        y : '25',
				        data: ['单晶','多晶','单多晶未知']				        
				    },
				    series : [
				        {
				            name: '访问来源',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:reJson,			      
				            itemStyle:{
				            normal:{
				                  label:{
				                    show: true,
				                    formatter: '{d}%'
				                  },
				                  labelLine :{show:true}
				                }
				            },
				            color: ['#FCCE10', '#E87C25', '#60C0DD']
				        }
				    ]
				};
			mychart.setOption(option);
			},
			error : function() {
					alert("异常！");
				}
			});
		}
</script>

<script type="text/javascript">
	function refreshmul() {
		var mychart = echarts.init(document.getElementById('flot-line-chart-multi'));
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
			    formatter:"{a}:{c}<br/>{a1}:{c1}"
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
				name:'月份',
				type : 'category',
				axisLabel : {//显示所有横坐标
					interval : 0
				},
				data : reJson.xdate
			},
			yAxis : {
				name:'Y轴单位',
				type : 'value'
			},
			series : [
					{
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
									color :'#E87C25'																		
								}
							}
						},

						data : reJson.mount
					},{
						name : '中标金额',
						type : 'bar',
						barWidth:30,
						itemStyle : {
							normal : {
								color : '#60C0DD',
								label : {//显示值
									show : true,
									position : 'top',
									formatter : '{c}'
								},
								lineStyle : {//线条颜色
									color :'#333333'																		
								}
							}
						},

						data : reJson.count
					}
					]
		};
		mychart.setOption(option);
			},
			error : function() {
					alert("异常！");
				}
			});
		}
</script>

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
			    formatter:"{a}:{c}<br/>{a1}:{c1}"
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
				name:'月份',
				type : 'category',
				axisLabel : {//显示所有横坐标
					interval : 0
				},
				data : reJson.xdate
			},
			yAxis : {
				name:'万元',
				nameRotate:-0.1,
				type : 'value'
			},
			series : [
					{
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
									color :'#E87C25'																		
								}
							}
						},

						data : reJson.single
					},{
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
									color :'#333333'																		
								}
							}
						},

						data : reJson.Multi
					}
					]
		};
		mychart.setOption(option);
			},
			error : function() {
					alert("异常！");
				}
			});
		}
</script>

<script type="text/javascript">
	function refreshlive() {
		var mychart = echarts.init(document.getElementById('flot-line-chart-moving'));
		$.ajax({
			type : "POST",
			url : "/biddingData/showLive",
			data : {
				"datefrom" : 1,
				"dateto" : 2,
			}, 
			async : false,
			dataType : 'json',
			success : function(reJson) {	
		var option = {
				title : {//标题
					text : '区域分布',
					x : 'center',
					y : '25'
				},
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        },
			        formatter:"{a4}:{c4}<br/>{a3}:{c3}<br/>{a2}:{c2}<br/>{a1}:{c1}<br/>{a}:{c}"
			    },
			    legend: {
			        data: ['中国', '其他亚太地区','欧洲','北美','新兴市场']
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    yAxis:  {
			        type: 'value'
			    },
			    xAxis: {
			        type: 'category',
			        name:'季度',
			        data: reJson.xdate
			    },
			    series: [
			        {
			            name: '中国',
			            type: 'bar',
			            stack: '总量',
			            barWidth:40,
			            label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                }
			            },
			            data: reJson.china
			        },
			        {
			            name: '其他亚太地区',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                }
			            },
			            data: reJson.others
			        },
			        {
			            name: '欧洲',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                }
			            },
			            data: reJson.europe
			        },
			        {
			            name: '北美',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                }
			            },
			            data: reJson.northAmerica
			        },
			        {
			            name: '新兴市场',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: true,
			                    position: 'inside'
			                }
			            },
			            data: reJson.newMarket
			        }
			    ]
			};
		mychart.setOption(option);
			},
			error : function() {
					alert("异常！");
				}
			});
		}

</script>

<script type="text/javascript">
function refreshpro() {
	var mychart = echarts.init(document.getElementById('flot-bar-chart-pro'));
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
				name:'企业名称',
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
				name:'总容量：(兆瓦)'
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
					<div class="col-lg-10">
						<h2>招中标数据</h2>
						<ol class="breadcrumb">
							<li><a href="/welcome/index">首页</a></li>
							<li><a>招中标数据</a></li>
							<li class="active"><strong>数据报表</strong></li>
						</ol>
					</div>
					<div class="col-lg-2"></div>
				</div>
				<div class="wrapper wrapper-content animated fadeInRight ecommerce"
					style="padding-bottom: 0px">


					<div class="ibox-content m-b-sm border-bottom">
						<div class="row">
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="projectName">项目名称</label> <input
										type="text" id="projectName" name="projectName" value=""
										placeholder="" class="form-control">
								</div>
							</div>
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="projectProvince">项目地址（省）</label>
									<select name="projectProvince" id="projectProvince"
										class="form-control">
										<option value="" selected>--请选择--</option>
										<option value="1">北京市</option>
										<option value="2">天津市</option>
										<option value="3">上海市</option>
										<option value="4">重庆市</option>
										<option value="5">安徽省</option>
										<option value="6">福建省</option>
										<option value="7">甘肃省</option>
										<option value="8">广东省</option>
										<option value="9">贵州省</option>
										<option value="10">海南省</option>
										<option value="11">河北省</option>
										<option value="12">河南省</option>
										<option value="13">湖北省</option>
										<option value="14">湖南省</option>
										<option value="15">吉林省</option>
										<option value="16">江苏省</option>
										<option value="17">江西省</option>
										<option value="18">辽宁省</option>
										<option value="19">青海省</option>
										<option value="20">山东省</option>
										<option value="21">山西省</option>
										<option value="22">陕西省</option>
										<option value="23">四川省</option>
										<option value="24">云南省</option>
										<option value="25">浙江省</option>
										<option value="26">台湾省</option>
										<option value="27">黑龙江省</option>
										<option value="28">西藏自治区</option>
										<option value="29">内蒙古自治区</option>
										<option value="30">宁夏回族自治区</option>
										<option value="31">广西壮族自治区</option>
										<option value="32">新疆维吾尔自治区</option>
										<option value="33">香港特别行政区</option>
										<option value="34">澳门特别行政区</option>
									</select>
								</div>
							</div>
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="projcetOwner">采购人</label> <input
										type="text" id="projcetOwner" name="projcetOwner" value=""
										placeholder="" class="form-control">
								</div>
							</div>
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="parentCompany">母公司</label> <input
										type="text" id="parentCompany" name="parentCompany" value=""
										placeholder="" class="form-control">
								</div>
							</div>
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="winCompanyInfo">中标单位</label>
									<input type="text" id="winCompanyInfo" name="winCompanyInfo"
										value="" placeholder="" class="form-control">
								</div>
							</div>
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="agency">代理机构</label> <input
										type="text" id="agency" name="agency" value="" placeholder=""
										class="form-control">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="productType">产品类型</label> <select
										name="productType" id="productType" class="form-control">
										<option value="" selected>--请选择--</option>
										<option value="1">单晶硅</option>
										<option value="2">多晶硅</option>
										<option value="3">单晶硅、多晶硅</option>
										<option value="4">未知</option>
									</select>
								</div>
							</div>
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="deploymentType">产品部署类型</label>
									<select name="deploymentType" id="deploymentType"
										class="form-control">
										<option value="" selected>--请选择--</option>
										<option value="1">分布式</option>
										<option value="2">地面电站</option>
										<option value="3">未知</option>
									</select>
								</div>
							</div>
							<div class="col-sm-2">
								<div class="form-group">
									<label class="control-label" for="status">采购方式</label> <select
										name="purchasingMethod" id="purchasingMethod"
										class="form-control">
										<option value="" selected>--请选择--</option>
										<option value="1">公开招标</option>
										<option value="2">竞争性谈判</option>
										<option value="3">单一来源</option>
										<option value="4">市场询价</option>
										<option value="5">邀请招标</option>
										<option value="6">其他</option>
									</select>
								</div>
							</div>
							<div class="col-sm-4">
								<div class="form-group" id="daterange_publishTime">
									<label class="control-label">发布时间</label>

									<div class="input-daterange input-group" id="datepicker">
										<input type="text" class="input-sm form-control"
											name="publishStartTime" id="publishStartTime" /> <span
											class="input-group-addon">到</span> <input type="text"
											class="input-sm form-control" name="publishEndTime"
											id="publishEndTime" />
									</div>
								</div>
							</div>

						</div>

						<div class="row">
							<div class="ibox-content">
								<a class="btn btn-w-m btn-info" href="javascript:;"
									onclick="searchAllData(this)">搜索</a> <a
									class="btn btn-w-m btn-success" href="/biddingData/add">新增</a>
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
										中标企业分布 <small>前20名</small>
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
						<div class="col-lg-6">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>Line Cahrt Example</h5>
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
									<h5>Pie Chart Example</h5>
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
									<h5>Live Chart Example</h5>
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
										<div class="flot-chart-content" id="flot-line-chart-moving"></div>
										<script type="text/javascript">
											refreshlive();
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
									<h5>Multiple Axes Line Chart Example</h5>
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
					</div>
					
					<div class="row">
						<div class="col-lg-12">
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
					</div>
				</div>

			</div>
			<!-- END PAGE CONTAINER-->

			<#include "/views/footer.ftl"/>
		</div>

	</div>

	<#include "/views/plugins.ftl"/>
</body>
</html>
