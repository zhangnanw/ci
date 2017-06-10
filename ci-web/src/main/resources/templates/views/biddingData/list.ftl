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
								<select name="projectProvince" id="projectProvince" class="form-control">
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
									<option value="3">单晶硅、多晶硅</option>
									<option value="4">未知</option>
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
											<th>发布时间</th>
											<th>公告类型</th>
											<th>项目名称</th>
											<th>项目规模（MW）</th>
											<th>招标预算（万元）</th>
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
							"searchable": false,
							"orderable": false,
							"class": 'details-control',
							"defaultContent": ''
						},
						{
							"targets": 1,
							"data": "publishTime",
							"orderable": true
						},
						{
							"targets": 2,
							"data": "dataType",
							"orderable": false,
							"render": function (data, type, full, meta) {
								if (data === 1) {
									return '招标公告';
								} else if (data === 2) {
									return '中标公告';
								} else if (data === 3) {
									return '更正公告';
								} else if (data === 4) {
									return '废标公告';
								} else if (data === 5) {
									return '流标公告';
								} else {
									return '其他';
								}
							}
						},
						{
							"targets": 3,
							"data": "projectName",
							"orderable": false
						},
						{
							"targets": 4,
							"data": "projectScale",
							"orderable": false
						},
						{
							"targets": 5,
							"data": "biddingBudget",
							"orderable": false
						},
						{
							"targets": 6,
							"data": "projectProvince",
							"orderable": false,
							"render": function (data, type, full, meta) {
								if (data === 1) {
									return '北京市';
								} else if (data === 2) {
									return '天津市';
								} else if (data === 3) {
									return '上海市';
								} else if (data === 4) {
									return '重庆市';
								} else if (data === 5) {
									return '安徽省';
								} else if (data === 6) {
									return '福建省';
								} else if (data === 7) {
									return '甘肃省';
								} else if (data === 8) {
									return '广东省';
								} else if (data === 9) {
									return '贵州省';
								} else if (data === 10) {
									return '海南省';
								} else if (data === 11) {
									return '河北省';
								} else if (data === 12) {
									return '河南省';
								} else if (data === 13) {
									return '湖北省';
								} else if (data === 14) {
									return '湖南省';
								} else if (data === 15) {
									return '吉林省';
								} else if (data === 16) {
									return '江苏省';
								} else if (data === 17) {
									return '江西省';
								} else if (data === 18) {
									return '辽宁省';
								} else if (data === 19) {
									return '青海省';
								} else if (data === 20) {
									return '山东省';
								} else if (data === 21) {
									return '山西省';
								} else if (data === 22) {
									return '陕西省';
								} else if (data === 23) {
									return '四川省';
								} else if (data === 24) {
									return '云南省';
								} else if (data === 25) {
									return '浙江省';
								} else if (data === 26) {
									return '台湾省';
								} else if (data === 27) {
									return '黑龙江省';
								} else if (data === 28) {
									return '西藏自治区';
								} else if (data === 29) {
									return '内蒙古自治区';
								} else if (data === 30) {
									return '宁夏回族自治区';
								} else if (data === 31) {
									return '广西壮族自治区';
								} else if (data === 32) {
									return '新疆维吾尔自治区';
								} else if (data === 33) {
									return '香港特别行政区';
								} else if (data === 34) {
									return '澳门特别行政区';
								} else {
									return '其他';
								}
							}
						},
						{
							"targets": 7,
							"data": "projcetOwner",
							"orderable": false
						},
						{
							"targets": 8,
							"data": "purchasingMethod",
							"orderable": false,
							"render": function (data, type, full, meta) {
								if (data === 1) {
									return '公开招标';
								} else if (data === 2) {
									return '竞争性谈判';
								} else if (data === 3) {
									return '单一来源';
								} else if (data === 4) {
									return '市场询价';
								} else if (data === 5) {
									return '邀请招标';
								} else {
									return '其他';
								}
							}
						},
						{
							"targets": 9,
							"data": "parentCompany",
							"orderable": false
						},
						{
							"targets": 10,
							"data": "productType",
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
							"targets": 11,
							"data": "deploymentType",
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
							"targets": 12,
							"data": "monocrystallineSpecification",
							"orderable": false
						},
						{
							"targets": 13,
							"data": "monocrystallineCapacity",
							"orderable": false
						},
						{
							"targets": 14,
							"data": "polysiliconSpecification",
							"orderable": false
						},
						{
							"targets": 15,
							"data": "polysiliconCapacity",
							"orderable": false
						},
						{
							"targets": 16,
							"data": "agency",
							"orderable": false
						},
						{
							"targets": 17,
							"data": "winCompanyInfo",
							"orderable": false,
							"visible": false
						},
						{
							"targets": 18,
							"data": "id",
							"orderable": false,
							"render": function (data, type, full, meta) {
								return '<a href="/winCompany/list?biddingDataId=' + data + '" class="btn btn-primary btn-xs">查看中标单位</a>';
							}
						},
						{
							"targets": 19,
							"data": "id",
							"orderable": false,
							"render": function (data, type, full, meta) {
								return '<a href="/biddingData/edit?id=' + data + '" class="btn btn-primary btn-xs">编辑</a>';
							}
						},
						{
							"targets": 20,
							"data": "id",
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

		oTable.column(3).search(projectName)
				.column(6).search(projectProvince)
				.column(7).search(projcetOwner)
				.column(9).search(parentCompany)
				.column(17).search(winCompanyInfo)
				.column(16).search(agency)
				.column(10).search(productType)
				.column(11).search(deploymentType)
				.column(8).search(purchasingMethod)
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
					'<td>' + (parseInt(wciIdx) + 1) + '</td>' +
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























