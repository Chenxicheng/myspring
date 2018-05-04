package com.dashwood.myspring.model.rdbms.util;

import com.dashwood.myspring.model.rdbms.util.DataBaseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 *
 */
public class DBUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DBUtils.class);


    public static boolean testConnection(final DataBaseType dataBaseType, final String jdbcUrl, final String username, final String password) {
        Connection connection = null;
        try{
            Connection conn = connect(dataBaseType, jdbcUrl, username, password);
            if (conn != null) {
                return true;
            }
        } catch (Exception e) {
            LOG.warn("test connection of [{}] failed, for {}.", jdbcUrl,
                    e.getMessage());
        } finally {
            DBUtils.closeDBResources(null, connection);
        }

        return false;
    }

    /**
     * 获取数据库连接
     * @param dataBaseType
     * @param jdbcUrl
     * @param username
     * @param password
     * @return
     */
    public static Connection getConnect(final DataBaseType dataBaseType, final String jdbcUrl, final String username, final String password) {
        return connect(dataBaseType, jdbcUrl, username, password);
    }

    /**
     * 连接数据库，
     * @param dataBaseType
     * @param jdbcUrl
     * @param username
     * @param password
     * @return
     */
    private static synchronized  Connection connect(final DataBaseType dataBaseType, final String jdbcUrl, final String username, final String password) {
        try {
            Class.forName(dataBaseType.getDriverClassName());
            DriverManager.setLoginTimeout(Constant.TIMEOUT_SECONDS);
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (Exception e) {
            throw RdbmsException.asConnException(dataBaseType, e, username, null);
        }
    }

    /**
     * 检查slave的库中的数据是否已到凌晨00:00
     * 如果slave同步的数据还未到00:00返回false
     * 否则范围true
     *
     * @author ZiChi
     * @version 1.0 2014-12-01
     */
    /*private static boolean isSlaveBehind(Connection conn) {
        try {
            ResultSet rs = query(conn, "SHOW VARIABLES LIKE 'read_only'");
            if (DBUtils.asyncResultSetNext(rs)) {
                String readOnly = rs.getString("Value");
                if ("ON".equalsIgnoreCase(readOnly)) { //备库
                    ResultSet rs1 = query(conn, "SHOW SLAVE STATUS");
                    if (DBUtils.asyncResultSetNext(rs1)) {
                        String ioRunning = rs1.getString("Slave_IO_Running");
                        String sqlRunning = rs1.getString("Slave_SQL_Running");
                        long secondsBehindMaster = rs1.getLong("Seconds_Behind_Master");
                        if ("Yes".equalsIgnoreCase(ioRunning) && "Yes".equalsIgnoreCase(sqlRunning)) {
                            ResultSet rs2 = query(conn, "SELECT TIMESTAMPDIFF(SECOND, CURDATE(), NOW())");
                            DBUtils.asyncResultSetNext(rs2);
                            long secondsOfDay = rs2.getLong(1);
                            return secondsBehindMaster > secondsOfDay;
                        } else {
                            return true;
                        }
                    } else {
                        LOG.warn("SHOW SLAVE STATUS has no result");
                    }
                }
            } else {
                LOG.warn("SHOW VARIABLES like 'read_only' has no result");
            }
        } catch (Exception e) {
            LOG.warn("checkSlave failed, errorMessage:[{}].", e.getMessage());
        }
        return false;
    }*/



    /**
     * a wrapped method to execute select-like sql statement .
     *
     * @param conn Database connection .
     * @param sql  sql statement to be executed
     * @return a {@link ResultSet}
     * @throws SQLException if occurs SQLException.
     */
    public static ResultSet query(Connection conn, String sql, int fetchSize)
            throws SQLException {
        // 默认3600 s 的query Timeout
        return query(conn, sql, fetchSize, Constant.SOCKET_TIMEOUT_INSECOND);
    }

    /**
     * a wrapped method to execute select-like sql statement .
     *
     * @param conn         Database connection .
     * @param sql          sql statement to be executed
     * @param fetchSize
     * @param queryTimeout unit:second
     * @return
     * @throws SQLException
     */
    public static ResultSet query(Connection conn, String sql, int fetchSize, int queryTimeout)
            throws SQLException {
        // make sure autocommit is off
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(fetchSize);
        stmt.setQueryTimeout(queryTimeout);
        return query(stmt, sql);
    }

    /**
     * a wrapped method to execute select-like sql statement .
     *
     * @param stmt {@link Statement}
     * @param sql  sql statement to be executed
     * @return a {@link ResultSet}
     * @throws SQLException if occurs SQLException.
     */
    public static ResultSet query(Statement stmt, String sql)
            throws SQLException {
        return stmt.executeQuery(sql);
    }



    public static ResultSet executeQuery(Connection conn, String sql)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setQueryTimeout(Constant.SOCKET_TIMEOUT_INSECOND);
        } catch (SQLException e) {
            throw  e;
        } finally {
            DBUtils.closePreparedStatement(pstmt);
        }

        return rs;
    }


//    public static ResultSet executeQuery(Connection conn, String sql, int fetchSize, int queryTimeout)
//            throws SQLException {
//        PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
//        pstmt.setQueryTimeout(queryTimeout);
//        pstmt.setFetchSize(fetchSize);
//        return pstmt.executeQuery(sql);
//    }


    public static void executeUpdate(Connection conn, String sql)
            throws SQLException {
        conn.setAutoCommit(false);
        PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
        pstmt.executeUpdate();
        DBUtils.closePreparedStatement(pstmt);
    }

    /**
     * 事务提交
     * @param conn
     * @throws SQLException
     */
    public static void commit (Connection conn) {
        try{
            if (conn != null) {
                conn.commit();
            }
        } catch (SQLException e) {
            throw  DataXException.asDataXException(DBUtilErrorCode.COMMIT_ERROR, "事务提交失败，请检查数据库." ,e);
        }

    }

    /**
     * 事务回滚
     * @param conn
     */
    public static void rollback (Connection conn) {
        try{
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            throw  DataXException.asDataXException(DBUtilErrorCode.COMMIT_ERROR, "事务回滚失败，请检查数据库." ,e);
        }

    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (null != rs) {
                Statement stmt = rs.getStatement();
                if (null != stmt) {
                    stmt.close();
                    stmt = null;
                }
                rs.close();
            }
            rs = null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    public static void closePreparedStatement(PreparedStatement pstmt) {
        if (null != pstmt) {
            try {
                pstmt.close();
                pstmt = null;
            } catch (SQLException unused) {
            }
        }
    }

    public static void closeDBResources(ResultSet rs, Statement stmt,
                                        Connection conn) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException unused) {
            }
        }

        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException unused) {
            }
        }

        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException unused) {
            }
        }
    }

    public static void closeDBResources(Statement stmt, Connection conn) {
        closeDBResources(null, stmt, conn);
    }

    public static void closeDBResources(ResultSet rs, PreparedStatement pstmt,
                                        Connection conn) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException unused) {
            }
        }

        if (null != pstmt) {
            try {
                pstmt.close();
            } catch (SQLException unused) {
            }
        }

        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException unused) {
            }
        }
    }

    public static void closeDBResources(PreparedStatement pstmt, Connection conn) {
        closeDBResources(null, pstmt, conn);
    }

    public static DatabaseMetaData getDatabaseMetaData (Connection conn) {
        try {
            if (conn != null) {
                return conn.getMetaData();
            } else {
                throw DataXException.asDataXException(DBUtilErrorCode.CONN_NULL, "数据库Connection为空，请检查数据库连接情况");
            }

        } catch (SQLException e) {
            throw DataXException.asDataXException(DBUtilErrorCode.METEDATA_ERROR, e);
        }

    }

    public static void main(String[] args) {
        Connection conn = DBUtils.getConnect(DataBaseType.MySql, "jdbc:mysql://192.168.30.10:3306/uum", "root", "password");

        String sql = "show tables;";


    }
}
