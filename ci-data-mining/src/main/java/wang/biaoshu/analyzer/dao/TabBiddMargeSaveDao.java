package wang.biaoshu.analyzer.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * 
 * @author 张南
 *
 */
public class TabBiddMargeSaveDao implements AutoCloseable {
	private static final Logger LOG = LoggerFactory.getLogger(TabBiddMargeSaveDao.class);
	private MysqlDataSource ds;
	private TabBiddUpdateFmd5Dao biddDao = new TabBiddUpdateFmd5Dao();

	void initDatabase() {
		ds = new MysqlDataSource();
		ds.setURL("jdbc:mysql://biaoshuking.mysql.rds.aliyuncs.com:3306/hngp?useUnicode=true&characterEncoding=utf-8");
		ds.setUser("hngp");
		ds.setPassword("hngp123");
		qr = new QueryRunner(ds);
	}

	private volatile boolean closed = false;
	private final String tab_name;
	{
		if (StringUtils.isBlank(System.getProperty("marge.tabname"))) {
			tab_name = "tab_bidd_merge";
		} else {
			tab_name = System.getProperty("marge.tabname");
		}
	}
	private List<String> fields = new ArrayList<>();
	private List<String> skipField = Lists.newArrayList("updateTime", "updateTime", "insertTime", "update_time",
			"insert_time");
	private QueryRunner qr;

	public boolean syncStore(JSONObject jObject) {
		if (closed) {
			throw new IllegalStateException("appDao closed.");
		}
		_save(jObject);
		return true;
	}

	DaoCall getInsCall(JSONObject obj) {
		Objects.requireNonNull(obj);
		DaoCall daoCao = new DaoCall();
		ArrayList<Object> argList = Lists.newArrayList();
		StringBuffer sb = new StringBuffer();
		StringBuffer sbV = new StringBuffer();
		fields.stream().forEach(key -> {
			if (StringUtils.isNotBlank(obj.getString(key))) {
				sb.append(key).append(",");
				sbV.append('?').append(',');
				argList.add(removeVal(obj, key));
			}
		});
		sb.setLength(sb.length() - 1);
		sbV.setLength(sbV.length() - 1);
		daoCao.setSql("INSERT INTO " + tab_name + " (" + sb + ")VALUES(" + sbV + ")");
		daoCao.setArgs(argList.toArray());
		return daoCao;
	}

	DaoCall getUpdate(JSONObject obj, List<String> fieldNames) {
		fields.forEach(field -> {
			if (!obj.containsKey(field)) {
				obj.put(field, "");
			}
		});
		DaoCall daoCall = new DaoCall();
		ArrayList<Object> argList = Lists.newArrayList();
		StringBuffer sb = new StringBuffer();
		Object id = obj.remove("id");
		if (null == id) {
			throw new IllegalArgumentException("id is null.");
		}
		sb.append("UPDATE " + tab_name + " SET ");
		fields.stream().filter(key -> null == fieldNames || (!fieldNames.contains(key))).forEach(key -> {
			if (null != obj.getString(key)) {
				sb.append(key).append("=?,");
				argList.add(removeVal(obj, key));
			}
		});
		sb.setLength(sb.length() - 1);
		sb.append(" WHERE id=?");
		argList.add(id);
		daoCall.setSql(sb.toString());
		daoCall.setArgs(argList.toArray());
		return daoCall;
	}

	Object removeVal(JSONObject obj, String key) {
		Object val = obj.remove(key);
		return val;
	}

	private Connection queryConn;

	public TabBiddMargeSaveDao() {
		initDatabase();
		readTabField();
		Timer countTimer = new Timer("UPDATE COUNT", true);
		countTimer.scheduleAtFixedRate(new LmdTask(() -> {
			// --- 后面的参数还没写呢。看到了赶紧写完。
			System.out.printf("UPD:%d/%d INS:%d/%d", updCount.get(), allUpdCount.get(), insCount.get(),
					allInsCount.get());
			updCount.set(0);
			insCount.set(0);
			System.out.println();
		}), 1000, 1000);
	}

	private String querySql = "select id,status from " + tab_name + " where fmd5=?";
	private String queryNotUpdateFieldMapSql = "select * from " + tab_name + " where id=?";

	IDEntry getID(String fmd5) {
		try {
			if (null == queryConn) {
				queryConn = ds.getConnection();
			}
			IDEntry ent = qr.query(queryConn, querySql, new BeanHandler<>(IDEntry.class), fmd5);
			if (null != ent) {
				return ent;
			}
			return null;
		} catch (SQLException e) {
			DbUtils.closeQuietly(queryConn);
			queryConn = null;
			throw new IllegalStateException(e);
		}
	}

	List<String> getNotUpdateFieldName(long id) {
		try {
			if (null == queryConn) {
				queryConn = ds.getConnection();
			}
			Map<String, Object> ent = qr.query(queryConn, queryNotUpdateFieldMapSql, new MapHandler(), id);
			ent.entrySet().stream().filter(e -> {
				return e.getValue() != null && StringUtils.isNotBlank(e.getValue().toString());
			});
			return null;
		} catch (SQLException e) {
			DbUtils.closeQuietly(queryConn);
			queryConn = null;
			throw new IllegalStateException(e);
		}
	}

	DaoCall getCall(JSONObject obj) {
		if (null == queryConn) {
			try {
				queryConn = ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	void readTabField() {
		fields.clear();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM " + tab_name + " WHERE 1=2");
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				String columnName = rsmd.getColumnLabel(i);
				String type = rsmd.getColumnTypeName(i).toUpperCase();
				if (type.contains("VARCHAR") || type.contains("FLOAT") || type.contains("TIME")) {
					fields.add(columnName);
				}
			}
			LOG.info("Fields:" + fields);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, stmt, rs);
		}
	}

	public static class IDEntry {
		private Long id;
		private Long status;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getStatus() {
			return status;
		}

		public void setStatus(Long status) {
			this.status = status;
		}

	}

	private Connection conn = null;

	public void _save(JSONObject json) {
		DaoCall daoCall = null;
		try {
			if (null == conn) {
				conn = ds.getConnection();
			}
			if (json.getBooleanValue("_updateBiddFmd5_")) {
				biddDao.updateFmd5InUrl(json);
			}
			IDEntry ent = getID(json.getString("fmd5"));
			if (null != ent && ent.getStatus() != 0) {
				// 人工改动过的记录,不修改。
				LOG.info("skip save:{}" + ent.getId());
				return;
			}
			skipField.forEach(json::remove);
			if (null == ent) {
				daoCall = getInsCall(json);
			} else {
				long id = ent.getId();
				json.put("id", id);
				List<String> fieldNames = getNotUpdateFieldName(id);
				daoCall = getUpdate(json, fieldNames);
			}
			qr.update(conn, daoCall.getSql(), daoCall.getArgs());
			if (StringUtils.startsWithIgnoreCase(daoCall.getSql(), "UPDATE")) {
				updCount.incrementAndGet();
				allUpdCount.incrementAndGet();
			} else if (StringUtils.startsWithIgnoreCase(daoCall.getSql(), "INSERT")) {
				insCount.incrementAndGet();
				allInsCount.incrementAndGet();
			} else {
				errCount.incrementAndGet();
			}
		} catch (Exception e) {
			DbUtils.closeQuietly(conn);
			conn = null;
			LOG.error(e.getMessage() + "---{} {}", daoCall, e);
		} finally {

		}
	}

	AtomicInteger allUpdCount = new AtomicInteger(0);
	AtomicInteger allInsCount = new AtomicInteger(0);
	AtomicInteger updCount = new AtomicInteger(0);
	AtomicInteger insCount = new AtomicInteger(0);
	AtomicInteger errCount = new AtomicInteger(0);

	private class DaoCall {
		private String sql;
		private Object[] args;

		public String getSql() {
			return sql;
		}

		public void setSql(String sql) {
			this.sql = sql;
		}

		public Object[] getArgs() {
			return args;
		}

		public void setArgs(Object[] args) {
			this.args = args;
		}

		@SuppressWarnings("unused")
		public boolean isOK() {
			String whs = sql.replaceAll("\\?", "");
			System.out.println(whs);
			return whs.length() == args.length;
		}

		@Override
		public String toString() {
			return super.toString();
		}
	}

	private static class LmdTask extends TimerTask {
		public LmdTask(Runnable runnable) {
			this.runable = runnable;
		}

		private final Runnable runable;

		@Override
		public void run() {
			runable.run();
		}
	}

	@Override
	public void close() {
		closed = true;
		ds = null;
	}
}
