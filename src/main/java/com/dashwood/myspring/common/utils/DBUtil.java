package com.dashwood.myspring.common.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.DruidWebUtils;
import com.google.common.collect.Maps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class DBUtil {
    private static Map<String, DruidDataSource> dsMap = Maps.newConcurrentMap();

    private DruidDataSource druidDataSource = null;

    private static ThreadLocal<Connection> connThreadLocal = new ThreadLocal<Connection>();

    public DBUtil () {}

    public DBUtil (DruidDataSource druidDataSource) {
        this.druidDataSource = druidDataSource;
    }

    public static DBUtil init(final DatabaseInfo databaseInfo, final String dsId) {
        DruidDataSource ds = null;
        if (dsMap.containsKey(dsId)) {
            ds = dsMap.get(dsId);
            return new DBUtil(ds);
        } else {
            ds = new DruidDataSource();
            ds.setUrl(databaseInfo.getJdbcUrl());
            ds.setUsername(databaseInfo.getUsername());// 用户名
            ds.setPassword(databaseInfo.getPassword());// 密码
            ds.setInitialSize(2);
            ds.setMaxActive(20);
            ds.setMinIdle(0);
            ds.setMaxWait(60000);
            ds.setTestOnBorrow(false);
            ds.setTestWhileIdle(true);
            ds.setPoolPreparedStatements(true);
            dsMap.put(dsId, ds);
        }
        return new DBUtil(ds);
    }

    public Connection getConnection () {
        Connection conn = connThreadLocal.get();
        try {
            if (conn == null) {

                conn = druidDataSource.getConnection();
                connThreadLocal.set(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public void closeConnection () {
        Connection conn = connThreadLocal.get();
        if (conn != null) {
            try {
                conn.close();
                connThreadLocal.remove();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void destroyDruidDataSource(final String dsId) {
        Connection conn = connThreadLocal.get();
        if (conn != null) {
            try {
                conn.close();
                connThreadLocal.remove();
                dsMap.remove(dsId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
