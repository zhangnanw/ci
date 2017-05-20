package org.yansou.ci.data.mining.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TabBiddQueryInIDForEachOrderBy {
	private static final Logger LOG = LoggerFactory.getLogger(TabBiddQueryInIDForEachOrderBy.class);
	private MysqlDataSource ds;
	private static final String SQL_ASC = "select id,url,title,anchorText,context,remark,time,fmd5 from tab_bidd where id>? order by id asc limit 500";
	private static final String SQL_DESC = "select id,url,title,anchorText,context,remark,time,fmd5 from tab_bidd where id<? order by id desc limit 500";
	private static final String SQL_QUERY_MAX_ID = "SELECT max(id) from tab_bidd";
	private String sql = SQL_ASC;
	private QueryRunner qr = new QueryRunner();
	private AtomicLong atomicSeq = new AtomicLong(0);
	private Connection conn;

	public AtomicLong getAtomicSeq() {
		return atomicSeq;
	}

	public long getMaxId() {
		try (Connection conn = ds.getConnection()) {
			Map<String, Object> mh = qr.query(conn, SQL_QUERY_MAX_ID, new MapHandler());
			return mh.values().stream().mapToLong(v -> ((Number) v).longValue()).findAny().getAsLong();
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public void setMaxId() {
		setAtomicSeq(getMaxId());
	}

	public void setAtomicSeq(AtomicLong atomicSeq) {
		this.atomicSeq = atomicSeq;
	}

	public void setDesc(boolean desc) {
		if (desc) {
			sql = SQL_DESC;
		} else {
			sql = SQL_ASC;
		}
	}

	public void setAtomicSeq(long id) {
		this.atomicSeq.set(id);
	}

	public TabBiddQueryInIDForEachOrderBy(AtomicLong atomicSeq) {
		this();
		this.atomicSeq = atomicSeq;
	}

	public TabBiddQueryInIDForEachOrderBy() {
		initDatabase();
	}

	void initDatabase() {
		ds = new MysqlDataSource();
		ds.setURL("jdbc:mysql://biaoshuking.mysql.rds.aliyuncs.com:3306/hngp?useUnicode=true&characterEncoding=utf-8");
		ds.setUser("hngp");
		ds.setPassword("hngp123");
	}

	private boolean isEnd = false;

	public boolean isEnd() {
		return isEnd;
	}

	public void forEach(Consumer<Map<String, Object>> action) {
		for (;;) {
			AtomicReference<Object> obj = new AtomicReference<Object>();
			try {
				if (conn == null)
					conn = ds.getConnection();
				List<Map<String, Object>> list = qr.query(conn, sql, new MapListHandler(), atomicSeq.get());
				if (list.isEmpty()) {
					isEnd = true;
					DbUtils.closeQuietly(conn);
					LOG.info("所有数据已经读取完毕...");
					return;
				}

				list.forEach(map -> {
					obj.set(map);
					action.accept(map);
					atomicSeq.set(Long.valueOf(map.get("id").toString()));
				});
			} catch (Exception e) {
				LOG.error("obj:{}\nerr:{}", obj.get(), e.getMessage());
				DbUtils.closeQuietly(conn);
				conn = null;
			}
		}
	}
}
