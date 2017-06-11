package org.yansou.ci.crawler.utils;

import org.apache.log4j.Logger;

import java.sql.*;

public class DButil {
	private static Logger logger = Logger.getLogger(DButil.class);
	
	private static String driverName="com.mysql.jdbc.Driver";
	private static String url="jdbc:mysql://192.168.85.88:3306/paper?useUnicode=true&characterEncoding=utf-8";
	private static String username="root";
	private static String password = "123456";
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(driverName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * 关闭数据库
	 * @param rs 查询结果集
	 * @param stmt 
	 * @param conn 数据库连接
	 */
	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
					logger.error(e.getMessage(),e);
			}
		}
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
					logger.error(e.getMessage(),e);
			}
		}
		
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
					logger.error(e.getMessage(),e);
			}
		}

	}
}
