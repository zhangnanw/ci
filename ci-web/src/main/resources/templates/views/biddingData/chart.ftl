<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>企业竞争情报系统-欢迎您</title> <#include "/views/styles.ftl"/>

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


			</div>
			<!-- END PAGE CONTAINER-->

			<#include "/views/footer.ftl"/>
		</div>

	</div>

	<#include "/views/plugins.ftl"/>
	<!-- Page-Level Scripts -->
	<!-- <script>
		var TableManaged = function() {
			return {
				// main function to initiate the module
				init : function() {

					var dataTablesExample = $('.dataTables-example')
							.DataTable(
									{
										"dom" : '<"html5buttons"B>lTfgitp',
										"searching" : false,
										"lengthChange" : false,
										buttons : [ {
											extend : 'csv'
										}, {
											extend : 'excel',
											title : 'ExampleFile'
										} ],
										"processing" : false,
										"serverSide" : true,
										"ajax" : {
											"url" : "/biddingData/showList",
											"type" : "POST"
										},
										"columnDefs" : [
												{
													"targets" : 0,
													"data" : "id",
													"name" : "#",
													"searchable" : false,
													"orderable" : false,
													"render" : function(data,
															type, full, meta) {
														return '<input type="checkbox" class="i-checks" name="ids" value="' + data + '">';
													}
												},
												{
													"targets" : 1,
													"data" : "projectName",
													"name" : "项目名称",
													"orderable" : false
												},
												{
													"targets" : 2,
													"data" : "projectScale",
													"name" : "项目规模（MW）",
													"orderable" : false
												},
												{
													"targets" : 3,
													"data" : "projectTotalInvestment",
													"name" : "项目总投资（万元）",
													"orderable" : false
												},
												{
													"targets" : 4,
													"data" : "projectProvince",
													"name" : "项目地址（省）",
													"orderable" : false
												},
												{
													"targets" : 5,
													"data" : "projcetOwner",
													"name" : "采购人",
													"orderable" : false
												},
												{
													"targets" : 6,
													"data" : "parentCompany",
													"name" : "母公司",
													"orderable" : false
												},
												{
													"targets" : 7,
													"data" : "monocrystallineSpecification",
													"name" : "单晶硅规格",
													"orderable" : false
												},
												{
													"targets" : 8,
													"data" : "monocrystallineCapacity",
													"name" : "单晶硅采购容量（MW）",
													"orderable" : false
												},
												{
													"targets" : 9,
													"data" : "polysiliconSpecification",
													"name" : "多晶硅规格",
													"orderable" : false
												},
												{
													"targets" : 10,
													"data" : "polysiliconCapacity",
													"name" : "多晶硅的采购容量（MW）",
													"orderable" : false
												},
												{
													"targets" : 11,
													"data" : "id",
													"name" : "中标单位",
													"orderable" : false,
													"render" : function(data,
															type, full, meta) {
														return '<a href="/winCompany/list?biddingDataId='
																+ data
																+ '" class="btn btn-primary btn-xs">查看中标单位</a>';
													}
												},
												{
													"targets" : 12,
													"data" : "id",
													"name" : "编辑",
													"orderable" : false,
													"render" : function(data,
															type, full, meta) {
														return '<a href="/biddingData/edit?id='
																+ data
																+ '" class="btn btn-primary btn-xs">编辑</a>';
													}
												},
												{
													"targets" : 13,
													"data" : "id",
													"name" : "删除",
													"orderable" : false,
													"render" : function(data,
															type, full, meta) {
														return '<a href="javascript:;" onclick="deleteForDataTable(this)" '
																+ 'res="/biddingData/delete?ids='
																+ data
																+ '" class="btn btn-primary btn-xs">删除</a>';
													}
												} ],
										"language" : {
											"info" : "第 _PAGE_ 页 ( 总共 _PAGES_ 页, _TOTAL_ 条数据 )",
											"infoEmpty" : "没有数据",
											"emptyTable" : "没有数据",
											"paginate" : {
												"previous" : "上一页",
												"next" : "下一页"
											}
										}

									});

				}

			};

		}();

		$(document).ready(function() {
			TableManaged.init();
		});
	</script>
 -->
</body>
</html>























