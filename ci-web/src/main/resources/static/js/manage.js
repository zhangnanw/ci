/**
 * 从左侧菜单进入右侧主界面，更新左侧菜单的状态以及右侧主界面的内容
 *
 * @param menuObj
 */
function gotoPageContent(menuObj) {
    var path = $(menuObj).attr("res");
    var menuId = $(menuObj).attr("menu_id");
    var data = $(menuObj).attr("data");

    if (!isEmpty(menuId)) {
        $("#side-menu .active").removeClass("active");

        $("#" + menuId).addClass("active");
        $(menuObj).parent("li").addClass("active");
    }

    $("#page-container").empty();
    if (isEmpty(data)) {
        $("#page-container").load(path);
    } else {
        try {
            $("#page-container").load(path, JSON.parse(data));
        } catch (e) {
            console.log(e);
        }
    }
}
/**
 * 非空验证
 * @param obj
 * @returns
 */
function isEmpty(obj) {
    return obj == undefined || obj == null || $.trim(obj) == "" || $.trim(obj) == "null" || $.trim(obj) == "-";
}
/**
 * 去掉所有的html标记
 * @param str
 * @returns
 */
function delHtmlTag(str) {
    return str.replace(/<[^>]+>/g, "");//去掉所有的html标记
}
/**
 * 点击右侧主界面的button、链接等更新右侧主界面的内容
 *
 * @param clickObj
 */
function updatePageContent(clickObj) {
    var path = $(clickObj).attr("res");

    $("#page-content").empty();
    $("#page-content").load(path);
}


function exchangeGoods(clickObj) {
    var path = $(clickObj).attr("res");


    $.alertable.confirm('您确定要兑换商品吗?').then(function () {
        debugger;
        $("#page-content").empty();
        $("#page-content").load(path);
    }, function () {
        debugger;
        // Confirmation canceled
    });


}


/**
 * 根据不同的请求更新右侧主界面的内容
 *
 * @param path
 */
function updatePageContentForPath(path) {
    $("#page-content").empty();
    $("#page-content").load(path);
}

/**
 * 从dataTable表格中删除数据
 *
 * @param clickObj
 */
function deleteForDataTable(clickObj) {
    $.alertable.confirm('您确定要删除这一条数据吗?').then(function () {//Confirmation submitted
        var path = $(clickObj).attr("res");
        var tableId = $(".table").attr("id");

        $.ajax({
            "url": path,
            "success": function (data) {
                if (data.status === 200 && data.result.count > 0) {
                    var nRow = $(clickObj).parents('tr')[0];
                    var oTable = $('#' + tableId).dataTable();

                    var start = oTable.fnSettings()._iDisplayStart;
                    var total = oTable.fnSettings().fnRecordsDisplay();

                    if (total - start === 1) {// 删除当前页的最后一条数据
                        if (start > 0) {
                            oTable.fnPageChange('previous', true);
                        } else {
                            oTable.fnDeleteRow(nRow);
                        }
                    } else {
                        oTable.fnDeleteRow(nRow);
                    }
                } else if (data.status === 500) {
                    $.alertable.alert(data.errors);
                } else {
                    $.alertable.alert("数据无法删除");
                }
            },
            "error": function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText);
            },
            "dataType": "json"
        });
    }, function () {
        // Confirmation canceled
    });

}

/**
 * 批量删除
 * @param clickObj
 */
function delAllCheck(clickObj) {
    var checkIds = getCheckIds();


    if (checkIds == undefined || checkIds.length <= 0) {
        $.alertable.alert('请选择需要删除的数据！');
    } else {
        $.alertable.confirm('您确定要删除所有选中的数据吗?').then(function () {//Confirmation submitted
            var path = $(clickObj).attr("res");
            var tableId = $(".table").attr("id");

            path = path + "?ids=" + checkIds[0];
            for (var i = 1; i < checkIds.length; i++) {
                path = path + "&ids=" + checkIds[i];
            }

            $.ajax({
                "url": path,
                "success": function (data) {
                    if (data.status === 200 && data.result.count > 0) {
                        var oTable = $('#' + tableId).dataTable();

                        var start = oTable.fnSettings()._iDisplayStart;
                        var total = oTable.fnSettings().fnRecordsDisplay();
                        var count = data.result.count;

                        if (total - start > count) {
                            $("input:checkbox[name=ids]:checked").each(function () {
                                var nRow = $(this).parents('tr')[0];

                                oTable.fnDeleteRow(nRow);
                            });
                        } else {// 当前页的数据删除完毕
                            if (start > 0) {
                                oTable.fnPageChange('previous', true);

                                jQuery('#' + tableId + ' .group-checkable').attr("checked", false);
                            } else {
                                $("input:checkbox[name=ids]:checked").each(function () {
                                    var nRow = $(this).parents('tr')[0];

                                    oTable.fnDeleteRow(nRow);
                                });
                            }
                        }
                    } else if (data.status === 500) {
                        $.alertable.alert(data.errors);
                    } else {
                        $.alertable.alert("数据无法删除");
                    }
                },
                "error": function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                },
                "dataType": "json"
            });
        }, function () {
            // Confirmation canceled
        });
    }
}

/**
 * 获得所有选中的id
 */
function getCheckIds() {
    var elements = $("input:checkbox[name=ids]:checked");

    if (elements != undefined) {
        var length = elements.length;

        if (length != undefined) {
            var checkIds = new Array(length);

            for (var i = 0; i < length; i++) {
                checkIds[i] = elements[i].value;
            }

            return checkIds;
        }

    }

    return null;
}

/**
 * 获取没有选中的id
 * @returns
 */
function getNotCheckIds() {
    var notCheckIds = new Array();
    var idx = 0;

    $("input:checkbox[name=ids]").each(function () {
        if ($(this).attr("checked")) {

        } else {
            notCheckIds[idx] = $(this).val();
            idx++;
        }
    });

    return notCheckIds;
}