package com.dashwood.myspring.model.rdbms.entity;

import java.io.Serializable;

/**
 * 数据库字段信息
 */
public class DatabaseColumnInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String IS_Null_YES = "1";
    public static final String IS_NULL_NO = "0";

    public static final String IS_PRIMARY_KEY_YES = "1";
    public static final String IS_PRIMARY_KEY_NO = "0";
    private String columnName; // 字段名称
    private String columnType; // 字段名称
    private String columnSize; // 字段长度
    private String decimalDigits; // 字段精度
    private String isNull; // 字段是否为空 0-否 1-是
    private String isPrimaryKey; // 字段是否为主键 0-否 1-是
    private String remarks; // 字段注释

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    public String getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(String decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(String isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
