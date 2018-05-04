
package com.dashwood.myspring.common.utils;

import java.sql.*;

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

	public static JDBCUtils getInitJDBCUtil() {
		if (jdbcUtils == null) {
			// 给类加锁 防止线程并发
			synchronized (JDBCUtils.class) {
				if (jdbcUtils == null) {
					jdbcUtils = new JDBCUtils();
				}
			}
		}
		return jdbcUtils;
	}

	public JDBCUtils setDatabaseInfo(DatabaseInfo databaseInfo) {
		driver = databaseInfo.getDriver();
		url = databaseInfo.getJdbcUrl();
		username = databaseInfo.getUsername();
		password = databaseInfo.getPassword();
		return jdbcUtils;
	}

	private JDBCUtils() {
	}

	// 获得连接
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        throw e;
	    } catch (SQLException e) {
	        throw e;
	    }
		return conn;
	}
	
	// 获得连接
	public Connection testConnection() throws ClassNotFoundException, SQLException {
		try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        throw e;
	    } catch (SQLException e) {
	        throw e;
	    }
		return conn;
	}
	
	// 关闭连接
	public void closeConnection(Connection con, ResultSet rs, Statement statement) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
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