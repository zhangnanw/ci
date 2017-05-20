package org.yansou.ci.data.mining.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TabBiddUpdateFmd5Dao {
	private static final Logger LOG = LoggerFactory.getLogger(TabBiddUpdateFmd5Dao.class);
	private MysqlDataSource ds;
	private Connection conn;
	private QueryRunner qr = new QueryRunner();

	public TabBiddUpdateFmd5Dao() {
		initDatabase();
	}

	void initDatabase() {
		ds = new MysqlDataSource();
		ds.setURL("jdbc:mysql://biaoshuking.mysql.rds.aliyuncs.com:3306/hngp?useUnicode=true&characterEncoding=utf-8");
		ds.setUser("hngp");
		ds.setPassword("hngp123");
	}

	public void updateFmd5InUrl(JSONObject res) {
		String url = res.getString("urls");
		String fmd5 = res.getString("fmd5");
		updateFmd5InUrl(url, fmd5);
	}

	void updateFmd5InUrl(String url, String fmd5) {
		try {
			if (null == conn) {
				conn = ds.getConnection();
			}
			int rownum = qr.update(conn, "update tab_bidd set fmd5=? where url=?", fmd5, url);
			LOG.debug("update fmd5:{},{},{}", rownum, fmd5, url);
		} catch (SQLException e) {
			LOG.info(e.getMessage());
			DbUtils.closeQuietly(conn);
			conn = null;
			LOG.error(e.getMessage(), e);
		} finally {

		}

	}
}
