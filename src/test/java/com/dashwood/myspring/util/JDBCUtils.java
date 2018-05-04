
package com.dashwood.myspring.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * 
 * @author 罗盛力 JDBC辅助类 用于构建数据库连接（采用单例模式）
 */
public final class JDBCUtils {
	
	static Connection conn = null;
	private static JDBCUtils jdbcUtils = null;
	private static String driver = "";
	private static String url = "";
	private static String username = null;
	private static String password = null;

	public static JDBCUtils getInitJDBCUtil(DatabaseInfo databaseInfo) {
		if (jdbcUtils == null) {
			// 给类加锁 防止线程并发
			synchronized (JDBCUtils.class) {
				if (jdbcUtils == null) {
					jdbcUtils = new JDBCUtils();
				}
			}
		}
		driver = databaseInfo.getDriver();
		url = databaseInfo.getJdbcUrl();
		username = databaseInfo.getUsername();
		password = databaseInfo.getPassword();
		
		return jdbcUtils;
	}

	private JDBCUtils() {
	}

	// 获得连接
	public Connection getConnection() {
		try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return conn;
	}
	
	// 获得连接
	public Connection testConnection() {
		try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return conn;
	}
	
	// 关闭连接
	public void closeConnection(ResultSet rs, Statement statement, Connection con) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void rollbackConnection(Connection conn){
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}