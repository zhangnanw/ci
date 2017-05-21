package org.yansou.ci.storage.ciimp;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.InetAddress;

/**
 * 
 * @author Administrator
 *
 */
public abstract class AbsStatistics {

	final protected QueryRunner qr;

	public AbsStatistics() {
		ComboPooledDataSource ds = new ComboPooledDataSource(true);
		try {
			ds.setDriverClass(com.mysql.jdbc.Driver.class.getName());
		} catch (PropertyVetoException e) {
			throw new IllegalStateException(e);
		}
		ds.setDataSourceName("hngp");
		ds.setJdbcUrl("jdbc:mysql://" + getDBHost()
				+ ":3306/hngp?useUnicode=true&rewriteBatchedStatements=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
		ds.setUser("hngp");
		ds.setPassword("hngp123");
		qr = new QueryRunner(ds);
	}

	final static String getDBHost() {
		String[] hosts = { "zhaobiao.mysql.rds.aliyuncs.com", "biaoshuking.mysql.rds.aliyuncs.com" };
		for (String host : hosts) {
			try {
				InetAddress[] addresses = InetAddress.getAllByName(host);
				for (int i = 0; i < addresses.length; i++) {
					boolean reac = addresses[i].isReachable(3000);
					if (reac) {
						System.out.println("ONLINE -> " + host);

						return host;
					} else {
						System.out.println("OFFLINE -> " + host);
					}
				}
			} catch (IOException ex) {
				System.out.println("Could not find " + host);

			}
		}
		return null;
	}

}
