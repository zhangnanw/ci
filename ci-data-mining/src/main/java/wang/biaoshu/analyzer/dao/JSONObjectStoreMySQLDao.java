package wang.biaoshu.analyzer.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 *
 * @author 张南
 *
 */
public final class JSONObjectStoreMySQLDao implements AutoCloseable {

	private final Logger LOG = LoggerFactory.getLogger(JSONObjectStoreMySQLDao.class);
	private DataSource dataSource;
	final private String tab_name;
	final private String rowkey;
	final private Map<String, String> fields = new LinkedHashMap<>();
	private QueryRunner qr;
	private volatile boolean closed = false;

	private Connection queryConn;
	private String statusInfo;
	private Connection conn = null;

	private String querySql;
	private String queryNotUpdateFieldMapSql;

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
		fields.forEach((key, type) -> {
			if (StringUtils.isNotBlank(obj.getString(key))) {
				sb.append(key).append(",");
				sbV.append('?').append(',');
				argList.add(obj.remove(key));
			}
		});
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		sbV.setLength(sbV.length() - 1);
		daoCao.setSql("INSERT INTO " + tab_name + " (" + sb + ")VALUES(" + sbV + ")");
		daoCao.setArgs(argList.toArray());
		return daoCao;
	}

	DaoCall getUpdate(JSONObject obj, List<String> fieldNames) {
		fields.forEach((field, type) -> {
			if (!obj.containsKey(field)) {
				obj.put(field, "");
			}
		});
		DaoCall daoCall = new DaoCall();
		ArrayList<Object> argList = Lists.newArrayList();
		StringBuilder sb = new StringBuilder();
		Object id = obj.remove("id");
		if (null == id) {
			throw new IllegalArgumentException("id is null.");
		}
		sb.append("UPDATE ").append(tab_name).append(" SET ");
		fields.forEach((field, type) -> {
			if (Objects.isNull(fieldNames) || (!fieldNames.contains(field))) {
				if (null != obj.getString(field)) {
					sb.append(field).append("=?,");
					Object val = obj.remove(field);
					switch (type) {
					case "STRING":
						val = Optional.ofNullable(val).filter(Objects::nonNull).map(v -> v.toString()).orElse("");
						break;
					case "FLOAT":
						val = Optional.ofNullable(val).filter(Objects::nonNull).map(v -> v.toString())
								.filter(StringUtils::isNotBlank).map(v -> Double.valueOf(v.toString())).orElse(0d);
						break;
					case "INT":
						val = Optional.ofNullable(val).filter(Objects::nonNull).map(v -> v.toString())
								.filter(StringUtils::isNotBlank).map(v -> Long.valueOf(v.toString())).orElse(0l);
						break;
					case "TIME":
						if (val instanceof Date) {
							val = new Timestamp(((Date) val).getTime());
						} else {
							val = null;
						}
						break;
					default:
						break;
					}
					argList.add(val);
				}
			}

		});
		sb.setLength(sb.length() - 1);
		sb.append(" WHERE id=?");
		argList.add(id);
		daoCall.setSql(sb.toString());
		daoCall.setArgs(argList.toArray());
		return daoCall;
	}

	/**
	 * 创建一个数据库保存对象
	 *
	 * @param dataSource
	 *            数据源
	 * @param tab_name
	 *            表名
	 * @param rowkey
	 *            标识唯一性的字段名
	 */
	public JSONObjectStoreMySQLDao(DataSource dataSource, String tab_name, String rowkey) {
		this.dataSource = dataSource;
		this.tab_name = tab_name;
		this.rowkey = rowkey;
		initSql();
		qr = new QueryRunner(dataSource);
		readTabField();
		Timer countTimer = new Timer("UPDATE COUNT", true);
		countTimer.scheduleAtFixedRate(new LmdTask(() -> {
			// --- 后面的参数还没写呢。看到了赶紧写完。
			setStatusInfo(String.format("UPD:%d/%d INS:%d/%d\n", updCount.get(), allUpdCount.get(), insCount.get(),
					allInsCount.get()));
			updCount.set(0);
			insCount.set(0);
			System.out.print(getStatusInfo());
		}), 1000, 1000);
	}

	public JSONObjectStoreMySQLDao(String tab_name, String rowkey) {
		this(defaultDataSource(), tab_name, rowkey);
	}

	private static DataSource defaultDataSource() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL(
				"jdbc:mysql://biaoshuking.mysql.rds.aliyuncs.com:3306/hngp?useUnicode=true&rewriteBatchedStatements=true&characterEncoding=utf-8");
		ds.setUser("hngp");
		ds.setPassword("hngp123");
		return ds;
	}

	protected void initSql() {
		querySql = "select id from " + tab_name + " where " + rowkey + "=?";
		queryNotUpdateFieldMapSql = "select * from " + tab_name + " where id=?";
	}

	public Long getIdInRowkey(String rowkey) {
		IDEntry ent = getId(rowkey);
		if (null != ent) {
			return ent.getId();
		}
		return null;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	@Override
	public void close() {
		closed = true;
		dataSource = null;
	}

	IDEntry getId(String rowkey) {
		try {
			queryConn = checkConn(queryConn);
			if (null == queryConn) {
				queryConn = dataSource.getConnection();
			}
			IDEntry ent = qr.query(queryConn, querySql, new BeanHandler<>(IDEntry.class), rowkey);
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
		queryConn = checkConn(queryConn);
		try {
			if (null == queryConn) {
				queryConn = dataSource.getConnection();
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
		queryConn = checkConn(queryConn);
		if (null == queryConn) {
			try {
				queryConn = dataSource.getConnection();
			} catch (SQLException e) {
				LOG.info(e.getMessage(), e);
			}
		}
		return null;
	}

	void readTabField() {
		fields.clear();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM " + tab_name + " WHERE 1=2");
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				String columnName = rsmd.getColumnLabel(i);
				String type = rsmd.getColumnTypeName(i).toUpperCase();
				if (type.contains("CHAR") || type.contains("TEXT")) {
					fields.put(columnName, "STRING");
				} else if (type.contains("FLOAT")) {
					fields.put(columnName, "FLOAT");
				} else if (type.contains("INT")) {
					fields.put(columnName, "INT");
				} else if (type.contains("TIME") || type.contains("DATE")) {
					fields.put(columnName, "TIME");
				}
			}
			LOG.info("Fields:" + fields);
		} catch (SQLException e) {
			LOG.info(e.getMessage(), e);
		} finally {
			DbUtils.closeQuietly(con, stmt, rs);
		}
	}

	public static class IDEntry {

		private Long id;
		private long status;

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

	Object[] argsConversion(Object[] args) {
		Object[] res = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof JSON) {
				res[i] = args[i].toString();
			} else {
				res[i] = args[i];
			}
		}
		return res;
	}

	void _save(JSONObject json) {
		DaoCall daoCall = null;
		try {
			conn = checkConn(conn);
			if (null == conn) {
				conn = dataSource.getConnection();
			}
			IDEntry ent = getId(json.getString(rowkey));
			if (null != ent && ent.getStatus() != 0) {
				// 人工改动过的记录,不修改。
				LOG.info("skip save:{}" + ent.getId());
				return;
			}
			if (null == ent) {
				daoCall = getInsCall(json);
			} else {
				long id = ent.getId();
				json.put("id", id);
				List<String> fieldNames = getNotUpdateFieldName(id);
				daoCall = getUpdate(json, fieldNames);
			}
			qr.update(conn, daoCall.getSql(), argsConversion(daoCall.getArgs()));
			if (StringUtils.startsWithIgnoreCase(daoCall.getSql(), "UPDATE")) {
				updCount.incrementAndGet();
				allUpdCount.incrementAndGet();
			} else if (StringUtils.startsWithIgnoreCase(daoCall.getSql(), "INSERT")) {
				insCount.incrementAndGet();
				allInsCount.incrementAndGet();
			} else {
				errCount.incrementAndGet();
			}
		} catch (SQLException e) {
			DbUtils.closeQuietly(conn);
			conn = null;
			LOG.error(e.getMessage() + "-{} {}", daoCall, e);
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

	private IdentityHashMap<Connection, Long> timeOutMap = new IdentityHashMap<>();

	private Connection checkConn(Connection conn) {
		if (Objects.isNull(conn)) {
			return null;
		}
		Long timeOut = timeOutMap.get(conn);
		if (Objects.isNull(timeOut)) {
			timeOut = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(300);
			timeOutMap.put(conn, timeOut);
		} else if (timeOut < System.currentTimeMillis()) {
			timeOutMap.remove(conn);
			DbUtils.closeQuietly(conn);
			return null;
		}
		return conn;
	}

}
