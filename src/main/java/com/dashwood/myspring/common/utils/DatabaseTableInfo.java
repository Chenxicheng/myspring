package com.dashwood.myspring.common.utils;

import java.io.Serializable;

/**
 * 数据库表信息
 */
public class DatabaseTableInfo implements Serializable{

    private String tableName; // 表名称
    private String tableType; // 表类型
    private String remarks; // 表注释

    public DatabaseTableInfo() {
    }

    public DatabaseTableInfo(String tableName, String tableType, String remarks) {
        this.tableName = tableName;
        this.tableType = tableType;
        this.remarks = remarks;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
