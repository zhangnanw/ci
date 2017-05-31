<!DOCTYPE html>
<html>

<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>企业竞争情报系统-欢迎您</title>

<#include "/views/styles.ftl"/>

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
					<h2>拟在建数据</h2>
					<ol class="breadcrumb">
						<li>
							<a href="/welcome/index">首页</a>
						</li>
						<li>
							<a>拟在建数据</a>
						</li>
						<li class="active">
							<strong>数据预览</strong>
						</li>
					</ol>
				</div>
				<div class="col-lg-2">

				</div>
			</div>

			<div class="wrapper wrapper-content animated fadeInRight ecommerce">


				<div class="ibox-content m-b-sm border-bottom">
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label" for="product_name">项目名称</label>
								<input type="text" id="projectName" name="projectName" value="" placeholder="项目名称"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="price">项目地址（省）</label>
								<input type="text" id="projectProvince" name="projectProvince" value=""
									   placeholder="项目地址（省）"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="quantity">采购人</label>
								<input type="text" id="projcetOwner" name="projcetOwner" value="" placeholder="采购人"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label" for="status">母公司</label>
								<input type="text" id="parentCompany" name="parentCompany" value="" placeholder="母公司"
									   class="form-control">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label" for="product_name">开始时间</label>
								<input type="text" id="startTime" name="startTime" value="" placeholder="开始时间"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="price">结束时间</label>
								<input type="text" id="endTime" name="endTime" value=""
									   placeholder="结束时间" class="form-control">
							</div>
						</div>

					</div>

					<div class="row">
						<div class="ibox-content">
							<a class="btn btn-w-m btn-info" href="javascript:;">搜索</a>
							<a class="btn btn-w-m btn-success" href="/planBuildData/add">新增</a>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-lg-12">
						<div class="ibox float-e-margins">

							<div class="ibox-content">

								<div class="table-responsive">
									<table class="table table-striped table-bordered table-hover dataTables-example">
										<thead>
										<tr>
											<th>#</th>
											<th>发布时间</th>
											<th>项目名称</th>
											<th>项目规模（MW）</th>
											<th>项目总投资（万元）</th>
											<th>项目地址（省）</th>
											<th>采购人</th>
											<th>母公司</th>
											<th>拟在建项目阶段</th>
											<th>编辑</th>
											<th>删除</th>
										</tr>
										</thead>

									</table>
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
<!-- Page-Level Scripts -->
<script>

	var TableManaged = function () {
		return {
			// main function to initiate the module
			init: function () {

				var dataTablesExample = $('.dataTables-example').DataTable({
					"dom": '<"html5buttons"B>lTfgitp',
					"searching": false,
					"lengthChange": false,
					buttons: [
						{extend: '导出为excel', title: '拟在建数据'}
					],
					"processing": false,
					"serverSide": true,
					"ajax": {
						"url": "/planBuildData/showList",
						"type": "POST"
					},
					"columnDefs": [
						{
							"targets": 0,
							"data": "id",
							"name": "#",
							"searchable": false,
							"orderable": false,
							"render": function (data, type, full, meta) {
								return '<input type="checkbox" class="i-checks" name="ids" value="' + data + '">';
							}
						},
						{
							"targets": 1,
							"data": "publishTime",
							"orderable": false
						},
						{
							"targets": 2,
							"data": "projectName",
							"orderable": false
						},
						{
							"targets": 3,
							"data": "projectScale",
							"orderable": false
						},
						{
							"targets": 4,
							"data": "projectTotalInvestment",
							"orderable": false
						},
						{
							"targets": 5,
							"data": "projectProvince",
							"orderable": false
						},
						{
							"targets": 6,
							"data": "projcetOwner",
							"orderable": false
						},
						{
							"targets": 7,
							"data": "parentCompany",
							"orderable": false
						},
						{
							"targets": 8,
							"data": "planBuildStatus",
							"orderable": false
						},
						{
							"targets": 9,
							"data": "id",
							"orderable": false,
							"render": function (data, type, full, meta) {
								return '<a href="/planBuildData/edit?id=' + data + '" class="btn btn-primary btn-xs">编辑</a>';
							},
						},
						{
							"targets": 10,
							"data": "id",
							"orderable": false,
							"render": function (data, type, full, meta) {
								return '<a href="javascript:;" onclick="deleteForDataTable(this)" ' +
										'res="/planBuildData/delete?ids=' + data + '" class="btn btn-primary btn-xs">删除</a>';
							},
						}
					],
					"language": {
						"info": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
						"infoEmpty": "没有数据",
						"emptyTable": "没有数据",
						"paginate": {
							"previous": "上一页",
							"next": "下一页"
						}
					}

				});

			}

		};

	}();

	$(document).ready(function () {
		TableManaged.init();
	});


</script>

</body>
</html>























