package com.dashwood.myspring.model.rdbms.entity;

import java.io.Serializable;
import java.sql.Connection;

/**
 *  连接数据库信息
 */
public class DatabaseInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jdbcUrl; // jdbcUrl
    private String username; // 用户名
    private String password; // 密码

    public DatabaseInfo() {

    }

    public DatabaseInfo(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
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

}
