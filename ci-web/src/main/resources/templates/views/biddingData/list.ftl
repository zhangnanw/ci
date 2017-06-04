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
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="projectName">项目名称</label>
								<input type="text" id="projectName" name="projectName" value=""
									   placeholder="" class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="projectProvince">项目地址（省）</label>
								<input type="text" id="projectProvince" name="projectProvince" value=""
									   placeholder=""
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="projcetOwner">采购人</label>
								<input type="text" id="projcetOwner" name="projcetOwner" value="" placeholder=""
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="parentCompany">母公司</label>
								<input type="text" id="parentCompany" name="parentCompany" value="" placeholder=""
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="winCompanyInfo">中标单位</label>
								<input type="text" id="winCompanyInfo" name="winCompanyInfo" value="" placeholder=""
									   class="form-control">
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="agency">代理机构</label>
								<input type="text" id="agency" name="agency" value="" placeholder=""
									   class="form-control">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="productType">产品类型</label>
								<select name="productType" id="productType" class="form-control">
									<option value="" selected>--请选择--</option>
									<option value="1">单晶硅</option>
									<option value="2">多晶硅</option>
								</select>
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="deploymentType">产品部署类型</label>
								<select name="deploymentType" id="deploymentType" class="form-control">
									<option value="" selected>--请选择--</option>
									<option value="1">分布式</option>
									<option value="2">集中式</option>
									<option value="3">渔光</option>
									<option value="4">农光</option>
								</select>
							</div>
						</div>
						<div class="col-sm-2">
							<div class="form-group">
								<label class="control-label" for="status">采购方式</label>
								<select name="purchasingMethod" id="purchasingMethod" class="form-control">
									<option value="" selected>--请选择--</option>
									<option value="1">邀标公告</option>
									<option value="2">询价公告</option>
									<option value="3">招标公告</option>
									<option value="4">中标公告</option>
									<option value="5">成交公告</option>
									<option value="6">更正公告</option>
									<option value="7">其他公告</option>
									<option value="8">单一来源</option>
									<option value="9">资格预审</option>
									<option value="10">废标流标</option>
									<option value="11">竞争性谈判</option>
									<option value="12">竞争性磋商</option>
								</select>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group" id="daterange_publishTime">
								<label class="control-label">发布时间</label>

								<div class="input-daterange input-group" id="datepicker">
									<input type="text" class="input-sm form-control" name="publishStartTime"
										   id="publishStartTime"/>
									<span class="input-group-addon">到</span>
									<input type="text" class="input-sm form-control" name="publishEndTime"
										   id="publishEndTime"/>
								</div>
							</div>
						</div>

					</div>

					<div class="row">
						<div class="ibox-content">
							<a class="btn btn-w-m btn-info" href="javascript:;" onclick="searchAllData(this)">搜索</a>
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
											<th>#</th>
											<th>项目名称</th>
											<th>项目规模（MW）</th>
											<th>项目总投资（万元）</th>
											<th>项目地址（省）</th>
											<th>采购人</th>
											<th>采购方式</th>
											<th>母公司</th>
											<th>产品类型</th>
											<th>产品部署类型</th>
											<th>单晶硅规格</th>
											<th>单晶硅采购容量（MW）</th>
											<th>多晶硅规格</th>
											<th>多晶硅的采购容量（MW）</th>
											<th>代理机构</th>
											<th>中标单位信息</th>
											<th>发布时间</th>
											<th>中标单位</th>
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
					"dom": '<"html5buttons"B>lT<"hidden"f>gitp',
					"lengthChange": false,
					buttons: [
						{
							extend: 'excel',
							text: '导出为Excel',
							title: '招中标数据',
							exportOptions: {
								modifier: {
									page: 'all'
								}
							}
						}
					],
					"processing": false,
					"serverSide": true,
					"ajax": {
						"url": "/biddingData/showList",
						"type": "POST",
						"data": function (d) {
							return $.extend({}, d, {
								"publishStartTime": $("#publishStartTime").val(),
								"publishEndTime": $("#publishEndTime").val()
							});
						}
					},
					"columnDefs": [
						{
							"targets": 0,
							"data": null,
							"name": "#",
							"searchable": false,
							"orderable": false,
							"class": 'details-control',
							"defaultContent": ''
						},
						{
							"targets": 1,
							"data": "projectName",
							"name": "项目名称",
							"orderable": false
						},
						{
							"targets": 2,
							"data": "projectScale",
							"name": "项目规模（MW）",
							"orderable": false
						},
						{
							"targets": 3,
							"data": "projectTotalInvestment",
							"name": "项目总投资（万元）",
							"orderable": false
						},
						{
							"targets": 4,
							"data": "projectProvince",
							"name": "项目地址（省）",
							"orderable": false
						},
						{
							"targets": 5,
							"data": "projcetOwner",
							"name": "采购人",
							"orderable": false
						},
						{
							"targets": 6,
							"data": "purchasingMethod",
							"name": "采购方式",
							"orderable": false,
							"render": function (data, type, full, meta) {
								if (data === 1) {
									return '邀标公告';
								} else if (data === 2) {
									return '询价公告';
								} else if (data === 3) {
									return '招标公告';
								} else if (data === 4) {
									return '中标公告';
								} else if (data === 5) {
									return '成交公告';
								} else if (data === 6) {
									return '更正公告';
								} else if (data === 7) {
									return '其他公告';
								} else if (data === 8) {
									return '单一来源';
								} else if (data === 9) {
									return '资格预审';
								} else if (data === 10) {
									return '废标流标';
								} else if (data === 11) {
									return '竞争性谈判';
								} else if (data === 12) {
									return '竞争性磋商';
								} else {
									return '未知';
								}
							}
						},
						{
							"targets": 7,
							"data": "parentCompany",
							"name": "母公司",
							"orderable": false
						},
						{
							"targets": 8,
							"data": "productType",
							"name": "产品类型",
							"orderable": false,
							"render": function (data, type, full, meta) {
								if (data === 1) {
									return '单晶硅';
								} else if (data === 2) {
									return '多晶硅';
								} else {
									return '未知';
								}
							}
						},
						{
							"targets": 9,
							"data": "deploymentType",
							"name": "产品部署类型",
							"orderable": false,
							"render": function (data, type, full, meta) {
								if (data === 1) {
									return '分布式';
								} else if (data === 2) {
									return '集中式';
								} else if (data === 3) {
									return '渔光';
								} else if (data === 4) {
									return '农光';
								} else {
									return '未知';
								}
							}
						},
						{
							"targets": 10,
							"data": "monocrystallineSpecification",
							"name": "单晶硅规格",
							"orderable": false
						},
						{
							"targets": 11,
							"data": "monocrystallineCapacity",
							"name": "单晶硅采购容量（MW）",
							"orderable": false
						},
						{
							"targets": 12,
							"data": "polysiliconSpecification",
							"name": "多晶硅规格",
							"orderable": false
						},
						{
							"targets": 13,
							"data": "polysiliconCapacity",
							"name": "多晶硅的采购容量（MW）",
							"orderable": false
						},
						{
							"targets": 14,
							"data": "agency",
							"name": "代理机构",
							"orderable": false
						},
						{
							"targets": 15,
							"data": "winCompanyInfo",
							"name": "中标单位信息",
							"orderable": false,
							"visible": false
						},
						{
							"targets": 16,
							"data": "publishTime",
							"name": "发布时间",
							"orderable": false
						},
						{
							"targets": 17,
							"data": "id",
							"name": "中标单位",
							"orderable": false,
							"render": function (data, type, full, meta) {
								return '<a href="/winCompany/list?biddingDataId=' + data + '" class="btn btn-primary btn-xs">查看中标单位</a>';
							}
						},
						{
							"targets": 18,
							"data": "id",
							"name": "编辑",
							"orderable": false,
							"render": function (data, type, full, meta) {
								return '<a href="/biddingData/edit?id=' + data + '" class="btn btn-primary btn-xs">编辑</a>';
							}
						},
						{
							"targets": 19,
							"data": "id",
							"name": "删除",
							"orderable": false,
							"render": function (data, type, full, meta) {
								return '<a href="javascript:;" onclick="deleteForDataTable(this)" ' +
										'res="/biddingData/delete?ids=' + data + '" class="btn btn-primary btn-xs">删除</a>';
							}
						}
					],
					"language": {
						"info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页, _TOTAL_ 条数据 )",
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

	function searchAllData() {
		var projectName = $("#projectName").val();
		var projectProvince = $("#projectProvince").val();
		var projcetOwner = $("#projcetOwner").val();
		var parentCompany = $("#parentCompany").val();
		var winCompanyInfo = $("#winCompanyInfo").val();
		var agency = $("#agency").val();
		var productType = $("#productType").val();
		var deploymentType = $("#deploymentType").val();
		var purchasingMethod = $("#purchasingMethod").val();

		var oTable = $('.dataTables-example').DataTable();

		oTable.column(1).search(projectName)
				.column(4).search(projectProvince)
				.column(5).search(projcetOwner)
				.column(7).search(parentCompany)
				.column(15).search(winCompanyInfo)
				.column(14).search(agency)
				.column(8).search(productType)
				.column(9).search(deploymentType)
				.column(6).search(purchasingMethod)
				.draw();

	}

	/* Formatting function for row details - modify as you need */
	function format(d) {
		// `d` is the original data object for the row
		var wciData = JSON.parse(d.winCompanyInfo);
		var allWciStr = '<table class="table" style="margin-bottom: 0px">'
				+ "<thead>"
				+ "<tr>"
				+ "<th>#</th>"
				+ "<th>中标单位</th>"
				+ "<th>中标容量（兆瓦）</th>"
				+ "</tr>"
				+ "</thead>";

		for (var wciIdx in wciData) {
			allWciStr = allWciStr +
					'<tr>' +
					'<td>' + (wciIdx + 1) + '</td>' +
					'<td>' + wciData[wciIdx].companyName + '</td>' +
					'<td>' + wciData[wciIdx].winCapacity + '</td>' +
					'</tr>';
		}

		allWciStr = allWciStr + '</table>';

		return allWciStr;
	}

	$(document).ready(function () {

		$('#daterange_publishTime .input-daterange').datepicker({
			format: "yyyy-mm-dd",
			todayBtn: "linked",
			language: "zh-CN"
		});

		TableManaged.init();

		// Add event listener for opening and closing details
		$('.dataTables-example tbody').on('click', 'td.details-control', function () {
			var tr = $(this).closest('tr');
			var row = $('.dataTables-example').DataTable().row(tr);
			if (row.child.isShown()) {
				// This row is already open - close it
				row.child.hide();
				tr.removeClass('shown');
			}
			else {
				// Open this row
				row.child(format(row.data())).show();
				tr.addClass('shown');
			}
		});
	});


</script>

</body>
</html>























