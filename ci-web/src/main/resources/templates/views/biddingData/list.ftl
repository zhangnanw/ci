<#assign base = request.contextPath />
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
					<h2>招中标数据</h2>
					<ol class="breadcrumb">
						<li>
							<a href="/welcome/index">首页</a>
						</li>
						<li>
							<a>招中标数据</a>
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
								<input type="text" id="product_name" name="product_name" value=""
									   placeholder="Product Name"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="price">项目地址</label>
								<input type="text" id="price" name="price" value="" placeholder="Price"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="quantity">采购人</label>
								<input type="text" id="quantity" name="quantity" value="" placeholder="Quantity"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label" for="status">采购方式</label>
								<select name="status" id="status" class="form-control">
									<option value="1" selected>Enabled</option>
									<option value="0">Disabled</option>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label" for="product_name">Product Name</label>
								<input type="text" id="product_name" name="product_name" value=""
									   placeholder="Product Name"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="price">Price</label>
								<input type="text" id="price" name="price" value="" placeholder="Price"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="quantity">Quantity</label>
								<input type="text" id="quantity" name="quantity" value="" placeholder="Quantity"
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label" for="status">Status</label>
								<select name="status" id="status" class="form-control">
									<option value="1" selected>Enabled</option>
									<option value="0">Disabled</option>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="ibox-content">
							<a class="btn btn-w-m btn-info" href="javascript:;">搜索</a>
							<a class="btn btn-w-m btn-success" href="/biddingData/add">新增</a>
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
											<th>项目名称</th>
											<th>项目规模（MW）</th>
											<th>项目总投资（元）</th>
											<th>项目地址（省）</th>
											<th>采购人</th>
											<th>母公司</th>
											<th>单晶硅规格</th>
											<th>单晶硅采购容量（MW）</th>
											<th>多晶硅规格</th>
											<th>多晶硅的采购容量（MW）</th>
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
		$('.dataTables-example').DataTable({
			"dom": '<"html5buttons"B>lTfgitp',
			"searching": false,
			"lengthChange": false,
			buttons: [
				{extend: 'csv'},
				{extend: 'excel', title: 'ExampleFile'}
			],
			"processing": false,
			"serverSide": true,
			"ajax": {
				"url": "/biddingData/showList",
				"type": "POST"
			},

			"columnDefs": [
				{
					"targets": 0,
					"data": "projectName",
					"orderable": false
				},
				{
					"targets": 1,
					"data": "projectScale",
					"orderable": false
				},
				{
					"targets": 2,
					"data": "projectTotalInvestment",
					"orderable": false
				},
				{
					"targets": 3,
					"data": "projectProvince",
					"orderable": false
				},
				{
					"targets": 4,
					"data": "projcetOwner",
					"orderable": false
				},
				{
					"targets": 5,
					"data": "parentCompany",
					"orderable": false
				},
				{
					"targets": 6,
					"data": "monocrystallineSpecification",
					"orderable": false
				},
				{
					"targets": 7,
					"data": "monocrystallineCapacity",
					"orderable": false
				},
				{
					"targets": 8,
					"data": "polysiliconSpecification",
					"orderable": false
				},
				{
					"targets": 9,
					"data": "polysiliconCapacity",
					"orderable": false
				},
				{
					"targets": 10,
					"data": "id",
					"orderable": false,
					"render": function (data, type, full, meta) {
						return '<a href="/biddingData/edit?id=' + data + '" class="btn btn-primary btn-xs">编辑</a>';
					},
				},
				{
					"targets": 11,
					"data": "id",
					"orderable": false,
					"render": function (data, type, full, meta) {
						return '<a href="javascript:;" onclick="deleteForDataTable(this)" ' +
								'res="/biddingData/delete?ids=' + data + '" class="btn btn-primary btn-xs">删除</a>';
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

	});


</script>

</body>
</html>
