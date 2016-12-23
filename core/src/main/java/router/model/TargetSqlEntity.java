package router.model;

import parser.model.SqlType;

/**
 * 路由后用于执行的SQL
 * 在ExecutePlan中要配合ExecuteType(对应partition字段)
 * <p>
 * 举例:select * from order where (user_id = 1 or user_id = 2) and pname = "一铜金1期"
 * <p>
 * 这句拆分成:
 * 1.select * from order where user_id = 1 and pname = "xxx"
 * 2.select * from order where user_id = 2 and panem = "xxx"
 * <p>
 * 或者:select * from order where user_id > 10000 limit 1000 , 500
 * <p>
 * 这句怎么拆呢...写完route模块就知道了
 * <p>
 * Created by aude on 2016/12/23.
 */
public class TargetSqlEntity {
    /**
     * 如果partition == null 表示在默认分区上执行
     * <p>
     * 取值DataSourceGroup.identity
     */
    private String partition;

    /**
     * 逻辑表名
     */
    private String logicTableName;

    /**
     * 目标表名
     */
    private String targetTableName;

    /**
     * 原始的sql语句
     */
    private String originalSql;

    /**
     * 改写后的sql(表名,limit,聚合函数等)
     */
    private String targetSql;

    /**
     * sql的类型
     */
    private SqlType sqlType;

    /**
     * 需要改写的limit值
     */
    private int limitStart;

    @Override
    public String toString() {
        return "TargetSqlEntity{" +
                "partition='" + partition + '\'' +
                ", logicTableName='" + logicTableName + '\'' +
                ", targetTableName='" + targetTableName + '\'' +
                ", originalSql='" + originalSql + '\'' +
                ", targetSql='" + targetSql + '\'' +
                ", sqlType=" + sqlType +
                ", limitStart=" + limitStart +
                ", limitSize=" + limitSize +
                '}';
    }

    /**
     * 需要改写的limit值
     */
    private int limitSize;

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getLogicTableName() {
        return logicTableName;
    }

    public void setLogicTableName(String logicTableName) {
        this.logicTableName = logicTableName;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getOriginalSql() {
        return originalSql;
    }

    public void setOriginalSql(String originalSql) {
        this.originalSql = originalSql;
    }

    public String getTargetSql() {
        return targetSql;
    }

    public void setTargetSql(String targetSql) {
        this.targetSql = targetSql;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(int limitSize) {
        this.limitSize = limitSize;
    }

    public String getPartition() {
        return partition;
    }
}
