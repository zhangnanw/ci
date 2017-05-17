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
					<input type="text" id="product_name" name="product_name" value="" placeholder="Product Name"
						   class="form-control">
				</div>
			</div>
			<div class="col-sm-2">
				<div class="form-group">
					<label class="control-label" for="price">项目地址</label>
					<input type="text" id="price" name="price" value="" placeholder="Price" class="form-control">
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
					<input type="text" id="product_name" name="product_name" value="" placeholder="Product Name"
						   class="form-control">
				</div>
			</div>
			<div class="col-sm-2">
				<div class="form-group">
					<label class="control-label" for="price">Price</label>
					<input type="text" id="price" name="price" value="" placeholder="Price" class="form-control">
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
				<button type="button" class="btn btn-w-m btn-primary">搜索</button>
				<button type="button" class="btn btn-w-m btn-success">导出CSV</button>
				<button type="button" class="btn btn-w-m btn-info">导出Excel</button>
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
								<th>操作</th>
							</tr>
							</thead>

						</table>
					</div>

				</div>
			</div>
		</div>
	</div>

</div>

<!-- Page-Level Scripts -->
<script>
	$(document).ready(function () {
		$('.dataTables-example').DataTable({
			"dom": '<"html5buttons"B>lTfgitp',
			"info": false,
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
					"data": "projectName"
				},
				{
					"targets": 1,
					"data": "projectScale"
				},
				{
					"targets": 2,
					"data": "projectTotalInvestment"
				},
				{
					"targets": 3,
					"data": "projectProvince"
				},
				{
					"targets": 4,
					"data": "projcetOwner"
				},
				{
					"targets": 5,
					"data": "parentCompany"
				},
				{
					"targets": 6,
					"data": "monocrystallineSpecification"
				},
				{
					"targets": 7,
					"data": "monocrystallineCapacity"
				},
				{
					"targets": 8,
					"data": "polysiliconSpecification"
				},
				{
					"targets": 9,
					"data": "polysiliconCapacity"
				},
				{
					"targets": 10,
					"data": "id",
					"render": function (data, type, full, meta) {
						return '<div class="btn-group"><button class="btn-white btn btn-xs">View</button><button class="btn-white btn btn-xs">Edit</button></div>';
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

		var oTable = $('#editable').DataTable();


	});


</script>
