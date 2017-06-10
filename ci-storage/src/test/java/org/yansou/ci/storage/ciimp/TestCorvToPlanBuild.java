package org.yansou.ci.storage.ciimp;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yansou.ci.common.time.TimeStat;
import org.yansou.ci.common.utils.JSONArrayHandler;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.storage.CiStorageApplication;

import com.alibaba.fastjson.JSONArray;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CiStorageApplication.class)
public class TestCorvToPlanBuild {
	@Autowired
	public CorvToPlanBuild corv;

	@Test
	public void testRun() {
		try {
			corv.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testImport1() {
		Long projectNumber = 633981L;
		try {
			TimeStat ts = new TimeStat();
			String sql = "select * from tab_rcc_project where project_name project_number=?";
			JSONArray arr = corv.qr.query(sql, JSONArrayHandler.create(), projectNumber);
			ts.buriePrint("plan-build-query-time:{}", System.out::println);
			corv.filter(JSONUtils.streamJSONObject(arr)).forEachOrdered(corv::store);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
