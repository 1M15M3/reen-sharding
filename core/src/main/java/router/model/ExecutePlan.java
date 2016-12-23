package router.model;

import parser.model.Limit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 一个sql通过parser,得到ParserResult,其中包含了表名,计算单元...,然后通过route,得到这个ExecutePlan,其中包含了拆分好的各个sql语句及其datasource+statement
 * 路由计算后的结果,执行计划,用于描述一个sql
 * Created by aude on 2016/12/23.
 */
public class ExecutePlan {

    /**
     * 执行的类型
     */
    private ExecuteType executeType;

    /**
     * 目标sql
     */
    private List<TargetSqlEntity> sqlList;

    /**
     * 聚合函数字段
     */
    private Map<String ,MergeColumn.MergeType> mergeColumns;

    /**
     * 分组字段
     */
    private List<String> groupbyColumns;

    /**
     * 排序字段
     */
    private List<OrderbyColumn> orderbyColumns;

    private Limit limit;

    /**
     * 在解析limit的时候,如果limit(x,x)会产生需要覆盖的参数
     */
    private Map<Integer,Object> overrideParameters;


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("[ExecuteType:%s]" + System.getProperty("line.separator"), executeType.getValue()));
        for(TargetSqlEntity targetSqlEntity : sqlList){
            sb.append("/*---------------------------------*/").append(System.getProperty("line.separator"));
            sb.append(targetSqlEntity.toString());
        }
        sb.append("/*---------------------------------*/");
        return sb.toString();
    }

    public void addSql(TargetSqlEntity sql){
        if(sqlList == null){
            sqlList = new ArrayList<TargetSqlEntity>();
        }
        sqlList.add(sql);
    }

    /*------------------------------------------------------------------------------------*/

    public List<TargetSqlEntity> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<TargetSqlEntity> sqlList) {
        this.sqlList = sqlList;
    }

    public ExecuteType getExecuteType() {
        return executeType;
    }

    public void setExecuteType(ExecuteType executeType) {
        this.executeType = executeType;
    }

    public List<OrderbyColumn> getOrderbyColumns() {
        return orderbyColumns;
    }

    public void setOrderbyColumns(List<OrderbyColumn> orderbyColumns) {
        this.orderbyColumns = orderbyColumns;
    }

    public List<String> getGroupbyColumns() {
        return groupbyColumns;
    }

    public void setGroupbyColumns(List<String> groupbyColumns) {
        this.groupbyColumns = groupbyColumns;
    }

    public Map<String, MergeColumn.MergeType> getMergeColumns() {
        return mergeColumns;
    }

    public void setMergeColumns(Map<String, MergeColumn.MergeType> mergeColumns) {
        this.mergeColumns = mergeColumns;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public Map<Integer, Object> getOverrideParameters() {
        return overrideParameters;
    }

    public void setOverrideParameters(Map<Integer, Object> overrideParameters) {
        this.overrideParameters = overrideParameters;
    }
}
