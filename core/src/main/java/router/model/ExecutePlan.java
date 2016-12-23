package router.model;

import parser.model.Limit;

import java.util.List;
import java.util.Map;

/**
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

    private Limit limit;

    /**
     * 在解析limit的时候,如果limit(x,x)会产生需要覆盖的参数
     */
    private Map<Integer,Object> overrideParameters;

    public Object getExecuteType() {
    }

    public List<TargetSqlEntity> getSqlList() {

    }

    public Map<Integer,Object> getOverrideParameters() {
        return this.overrideParameters;
    }
}
