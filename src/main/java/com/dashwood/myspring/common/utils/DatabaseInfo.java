package com.dashwood.myspring.common.utils;

import java.io.Serializable;

/**
 *  连接数据库信息
 */
public class DatabaseInfo implements Serializable{

    private String jdbcUrl; // jdbcUrl
    private String username; // 用户名
    private String password; // 密码
    private String driver; // 数据库驱动
    private String databaseName; // 数据库名称

    public DatabaseInfo() {

    }

    public DatabaseInfo(String jdbcUrl, String username, String password, String driver) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    public DatabaseInfo(String jdbcUrl, String username, String password, String driver, String databaseName) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.databaseName = databaseName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
