package com.dashwood.myspring.model.mysql.dao.impl;

import com.dashwood.myspring.common.utils.DatabaseColumnInfo;
import com.dashwood.myspring.common.utils.DatabaseInfo;
import com.dashwood.myspring.common.utils.DatabaseTableInfo;
import com.dashwood.myspring.common.utils.JDBCUtils;
import com.dashwood.myspring.model.mysql.dao.MysqlService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Map;

@Service
public class MysqlServiceImpl implements MysqlService {
    private final String[] tableTypes = { "TABLE", "VIEW" };
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private DatabaseMetaData metaData = null;

    @Override
    public void testConnection(DatabaseInfo databaseInfo) throws ClassNotFoundException, SQLException {
        try {
            conn = JDBCUtils.getInitJDBCUtil().setDatabaseInfo(databaseInfo).testConnection();
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtils.getInitJDBCUtil().closeConnection(conn, rs, pstmt);
        }
    }
    @Override
    public void connection(DatabaseInfo databaseInfo) throws ClassNotFoundException, SQLException {
        try {
            conn = JDBCUtils.getInitJDBCUtil().setDatabaseInfo(databaseInfo).testConnection();
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        JDBCUtils.getInitJDBCUtil().closeConnection(conn,rs, pstmt);
    }

    @Override
    public void createDatabase(String dbName) throws SQLException {
        StringBuffer sql = new StringBuffer();
        try {
            conn.setAutoCommit(false);
            sql.append("CREATE DATABASE "+ dbName);
            pstmt = (PreparedStatement)conn.prepareStatement(sql.toString());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void createTable(String tableName, List<DatabaseColumnInfo> columnInfoList) throws SQLException {
        StringBuffer sql = new StringBuffer();
        List<String> columnList = Lists.newArrayList();
        List<String> primaryKeyList = Lists.newArrayList();
        try {
            conn.setAutoCommit(false);
            // 遍历字段信息集合，拼接成新建表字段信息sql
            for (DatabaseColumnInfo columnInfo: columnInfoList) {
                StringBuffer columnSb = new StringBuffer();
                String columnType = columnInfo.getColumnType(); // 字段类型
                String columnName = columnInfo.getColumnName(); // 字段名称
                String columnSize = columnInfo.getColumnSize(); // 字段长度
                String columnScale = columnInfo.getDecimalDigits(); // 字段精度
                String remarks = columnInfo.getRemarks(); // 字段注释
                columnSb.append("`"+columnName +"` "+ columnType);
                if (checkDataType(columnType)) {
                    if(columnSize != null && !columnSize.trim().isEmpty()) { // 判断长度
                        columnSb.append("("+ columnSize);
                        if (columnScale != null && !columnScale.trim().isEmpty()) { // 判断精度
                            if (checkDouble(columnType)) {
                                columnSb.append(", "+ columnScale);
                            }
                        }
                        columnSb.append(") ");
                    }
                }
                if(DatabaseColumnInfo.IS_NULL_NO.equals(columnInfo.getIsNull())) {//不为空
                    columnSb.append(" NOT NULL ");
                }
//					if(StringUtils.isNotBlank(sf.getString("DV"))) {//默认值
//						sql += "DEFAULT '"+ sf.getString("DV") +"' ";
//					}
                if (remarks != null && !remarks.trim().isEmpty()) {
                    columnSb.append(" COMMENT '"+ remarks +"' ");
                }

                if (DatabaseColumnInfo.IS_PRIMARY_KEY_YES.equals(columnInfo.getIsPrimaryKey())) {//主键
                    primaryKeyList.add(columnName);
                }
                columnList.add(columnSb.toString());
            }
            sql.append("CREATE TABLE `"+ tableName +"` ( ")
                    .append(Joiner.on(",").skipNulls().join(columnList));
            if (primaryKeyList.size() > 0) {
                sql.append(", PRIMARY KEY (")
                        .append(Joiner.on(",").skipNulls().join(primaryKeyList))
                        .append(")");
            }
            sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='"+ tableName +"';");
            System.out.println(sql.toString());
            pstmt = (PreparedStatement)conn.prepareStatement(sql.toString());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void dropTable(String tableName) throws SQLException {
        StringBuffer sql = new StringBuffer();
        try {
            conn.setAutoCommit(false);
            sql.append("DROP TABLE "+ tableName);
            pstmt = (PreparedStatement)conn.prepareStatement(sql.toString());
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<DatabaseTableInfo> findListOfTable() throws SQLException {
        List<DatabaseTableInfo> tableInfoList = Lists.newArrayList();
        try {
            metaData = conn.getMetaData();
            rs = metaData.getTables(conn.getCatalog(), null, null, tableTypes);
            while (rs.next()) {
                DatabaseTableInfo databaseTableInfo = new DatabaseTableInfo(rs.getString("TABLE_NAME"), rs.getString("TABLE_TYPE"), rs.getString("REMARKS"));
                tableInfoList.add(databaseTableInfo);
            }
        }  catch (SQLException e) {
            throw e;
        }
        return tableInfoList;
    }

    @Override
    public List<DatabaseColumnInfo> finListOfColumnByTable(String tableName) throws SQLException {
        List<DatabaseColumnInfo> columnInfoList = Lists.newArrayList();
        Map<String, String> primaryKeyMap = Maps.newHashMap();
        try {
            metaData = conn.getMetaData();
            // 获取指定的数据库的表的主键，第二个参数也是模式名称的模式,使用null了
            rs = metaData.getPrimaryKeys(null, null, tableName);
            while (rs.next()) {
                primaryKeyMap.put(rs.getString("COLUMN_NAME"), DatabaseColumnInfo.IS_PRIMARY_KEY_YES);
            }
            rs = metaData.getColumns(null, null, tableName, "%");
            while (rs.next()) {
                DatabaseColumnInfo columnInfo = new DatabaseColumnInfo();
                String columnName = rs.getString("COLUMN_NAME");
                String remarks = rs.getString("REMARKS");
                columnInfo.setColumnName(columnName);
                columnInfo.setColumnType(rs.getString("TYPE_NAME").toUpperCase());
                columnInfo.setColumnSize(rs.getString("COLUMN_SIZE"));
                columnInfo.setDecimalDigits(rs.getString("DECIMAL_DIGITS"));
                columnInfo.setRemarks(remarks == null? "": remarks);
                columnInfo.setIsNull("yes".equals(rs.getString("IS_NULLABLE").toLowerCase()) ? DatabaseColumnInfo.IS_Null_YES: DatabaseColumnInfo.IS_NULL_NO);
                if (primaryKeyMap.containsKey(columnName)) {
                    columnInfo.setIsPrimaryKey(primaryKeyMap.get(columnName));
                } else {
                    columnInfo.setIsPrimaryKey(DatabaseColumnInfo.IS_PRIMARY_KEY_NO);
                }
                columnInfoList.add(columnInfo);
            }
        } catch (SQLException e) {
            throw e;
        }
        return columnInfoList;
    }

    @Override
    public Map<String, Object> findPageByTable(String tableName, Integer pageNo, Integer pageSize) throws SQLException {
        Map<String, Object> resultMap = Maps.newHashMap();
        Map<String, List<String>> selectResultMap = Maps.newHashMap();
        Integer count = null;
        List<String> columnNameList = Lists.newArrayList();
        StringBuffer sql = new StringBuffer();
        try {
            metaData = conn.getMetaData();
            rs = metaData.getColumns(null, null, tableName, "%");
            while (rs.next()) {
                columnNameList.add(rs.getString("COLUMN_NAME"));
            }
            resultMap.put("columnList", columnNameList);

//            String countSql = "SELECT COUNT(-1) FROM "+ tableName +";";
//            pstmt = (PreparedStatement)conn.prepareStatement(countSql);
//            rs = pstmt.executeQuery();
//            while(rs.next()) {
//                count = rs.getInt(1);
//            }
//            resultMap.put("count", count);

            sql.append("SELECT ")
                    .append(Joiner.on(",").skipNulls().join(columnNameList))
                    .append(" FROM ")
                    .append(tableName)
                    .append(" LIMIT ")
                    .append(pageNo == 1 ? (pageNo-1): pageNo)
                    .append(",")
                    .append(pageSize+";");
            pstmt = (PreparedStatement)conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();
            while(rs.next()) {
                for(String cn: columnNameList) {//根据每个字段名查询
                    if (!selectResultMap.containsKey(cn)) {
                        List<String> list = Lists.newArrayList();
                        list.add(rs.getString(cn));
                        selectResultMap.put(cn, list);
                    } else {
                        List<String> list = selectResultMap.get(cn);
                        list.add(rs.getString(cn));
                        selectResultMap.put(cn, list);
                    }
                }
            }
            resultMap.put("selectResultMap", selectResultMap);
        } catch (SQLException e) {
            throw e;
        }

        return resultMap;
    }

    @Override
    public Integer countByTable( String tableName) throws SQLException {
        StringBuffer sql = new StringBuffer();
        Integer count = null;
        try {

            sql.append("SELECT COUNT(-1) FROM "+ tableName);
            pstmt = (PreparedStatement)conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();
            while(rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw e;
        }
        return count;
    }


    /**
     * 判断字段类型
     * @param type 字段类型
     * @return
     */
    public static boolean checkDataType(String type) {
        boolean flag = true;
        if (type.startsWith("LONG")) {
            flag = false;
        } else if (type.startsWith("DATE")) {
            flag = false;
        } else if (type.startsWith("TIME")) {
            flag = false;
        } else if ("BIGINT".equals(type)) {
            flag = false;
        } else if (type.endsWith("TEXT")) {
            flag = false;
        } else if ("BOOL".equals(type)) {
            flag = false;
        } else if ("BIT".equals(type)) {
            flag = false;
        } else if ("YEAR".equals(type)) {
            flag = false;
        } else if ("VARBINARY".equals(type)) {
            flag = false;
        } else if (type.endsWith("BLOB")) {
            flag = false;
        }

        return flag;
    }

    /**
     * 判断字段类型为浮点型
     * @param type 字段类型
     * @return
     */
    public static boolean checkDouble(String type) {
        boolean flag = false;
        if ("DOUBLE".equals(type)) {
            flag = true;
        } else if ("FLOAT".equals(type)) {
            flag = true;
        } else if ("DECIMAL".equals(type)) {
            flag = true;
        }
        return flag;
    }


}
