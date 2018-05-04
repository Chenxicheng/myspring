package com.dashwood.myspring.model.mysql.dao;

import com.alibaba.fastjson.JSONObject;
import com.dashwood.myspring.common.utils.DatabaseColumnInfo;
import com.dashwood.myspring.common.utils.DatabaseInfo;
import com.dashwood.myspring.common.utils.DatabaseTableInfo;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MysqlService {
    /**
     * 测试数据库连接
     * @param databaseInfo 数据库信息
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    void testConnection (DatabaseInfo databaseInfo) throws ClassNotFoundException, SQLException;

    /**
     * 连接数据库
     * @param databaseInfo
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    void connection (DatabaseInfo databaseInfo) throws ClassNotFoundException, SQLException;

    /**
     * 关闭数据库连接
     * @throws SQLException
     */
    void closeConnection () throws SQLException;
    /**
     * 创建数据库
     * @param dbName 数据库名称
     * @return
     */
    void createDatabase(String dbName) throws SQLException;

    /**
     * 创建数据表
     * @param tableName 数据表名称
     * @param columnInfoList 字段信息集合
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    void createTable(String tableName, List<DatabaseColumnInfo> columnInfoList) throws SQLException;

    /**
     * 删除数据表
     * @param tableName 数据表名称
     */
    void dropTable(String tableName) throws SQLException;

    /**
     * 查询数据库下的表信息集合
     * @return List<DatabaseTableInfo> 表信息集合
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    List<DatabaseTableInfo> findListOfTable () throws SQLException;

    /**
     * 通过表名称查询该表下的字段信息
     * @param tableName 表名称
     * @return List<DatabaseColumnInfo> 表字段集合
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    List<DatabaseColumnInfo> finListOfColumnByTable (String tableName) throws SQLException;

    /**
     * 通过表名称， 分页获取表中的数据
     * @param tableName 表名称
     * @param pageNo 页数
     * @param pageSize 每页的条数
     * @return Map<String, List<String>> {字段: List<该字段下数据集合>}
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    Map<String, Object> findPageByTable (String tableName, Integer pageNo, Integer pageSize) throws SQLException;

    /**
     * 通过表名称，获得该表下数据总数
     * @param tableName
     * @return
     */
    Integer countByTable (String tableName) throws SQLException;
}
