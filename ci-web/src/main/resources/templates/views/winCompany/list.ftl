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
					<h2>${(biddingData.projectName)!'招中标数据'} - 中标单位信息</h2>
					<ol class="breadcrumb">
						<li>
							<a href="/welcome/index">首页</a>
						</li>
						<li>
							<a>中标单位信息</a>
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
						<div class="ibox-content" style="border: 0px">
							<a class="btn btn-w-m btn-success"
							   href="/winCompany/add?biddingDataId=${biddingData.id}">新增</a>
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
											<th>中标单位</th>
											<th>中标金额（万元）</th>
											<th>中标单价（元/瓦）</th>
											<th>中标容量（兆瓦）</th>
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
	$(document).ready(function () {
		var dataTablesExample = $('.dataTables-example').DataTable({
			"dom": '<"html5buttons"B>lTfgitp',
			"searching": false,
			"lengthChange": false,
			buttons: [],
			"processing": false,
			"serverSide": true,
			"ajax": {
				"url": "/winCompany/showList",
				"type": "POST",
				"data": {
					"biddingData.id": ${biddingData.id}
				}
			},
			"order": [],
			"columnDefs": [
				{
					"targets": 0,
					"data": null,
					"defaultContent": "",
					"searchable": false,
					"orderable": false
				},
				{
					"targets": 1,
					"data": "companyName",
					"orderable": false
				},
				{
					"targets": 2,
					"data": "winAmount",
					"orderable": false
				},
				{
					"targets": 3,
					"data": "winPrice",
					"orderable": false
				},
				{
					"targets": 4,
					"data": "winCapacity",
					"orderable": false
				},
				{
					"targets": 5,
					"data": "id",
					"orderable": false,
					"render": function (data, type, full, meta) {
						return '<a href="/winCompany/edit?id=' + data + '" class="btn btn-primary btn-xs">编辑</a>';
					},
				},
				{
					"targets": 6,
					"data": "id",
					"orderable": false,
					"render": function (data, type, full, meta) {
						return '<a href="javascript:;" onclick="deleteForDataTable(this)" ' +
								'res="/winCompany/delete?ids=' + data + '" class="btn btn-primary btn-xs">删除</a>';
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

		dataTablesExample.on('order.dt search.dt', function () {
			dataTablesExample.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
				cell.innerHTML = i + 1;
			});
		}).draw();

	});


</script>

</body>
</html>























