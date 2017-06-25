package org.yansou.ci.storage.ciimp;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.InetAddress;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Administrator
 */
public abstract class AbsStatistics {

    final protected QueryRunner qr;
    final static private DataSource dataSource;

    static {
        ComboPooledDataSource ds = new ComboPooledDataSource( true );
        try {
            ds.setDriverClass( com.mysql.jdbc.Driver.class.getName() );
        } catch (PropertyVetoException e) {
            throw new IllegalStateException( e );
        }
        ds.setDataSourceName( "hngp" );
        ds.setJdbcUrl( "jdbc:mysql://" + getDBHost()
                + ":3306/hngp?useUnicode=true&rewriteBatchedStatements=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull" );
        ds.setUser( "hngp" );
        ds.setPassword( "hngp123" );
        dataSource = ds;
    }

    public AbsStatistics() {
        qr = new QueryRunner( dataSource );
    }

    final static String getDBHost() {
        String[] hosts = {"zhaobiao.mysql.rds.aliyuncs.com", "biaoshuking.mysql.rds.aliyuncs.com"};
        for (String host : hosts) {
            try {
                InetAddress[] addresses = InetAddress.getAllByName( host );
                for (int i = 0; i < addresses.length; i++) {
                    boolean reac = addresses[i].isReachable( 3000 );
                    if (reac) {
                        System.out.println( "ONLINE -> " + host );

                        return host;
                    } else {
                        System.out.println( "OFFLINE -> " + host );
                    }
                }
            } catch (IOException ex) {
                System.out.println( "Could not find " + host );

            }
        }
        return null;
    }

}
