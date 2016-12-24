package router;

import jdbc.model.ParameterCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.SqlParser;
import parser.SqlParserFactory;
import parser.model.CalculateUnit;
import parser.model.ParseResult;
import parser.model.SqlType;
import parser.utils.SqlTypeUtil;
import router.model.ExecutePlan;
import router.model.ExecuteType;
import router.model.TargetSqlEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by aude on 2016/12/21.
 */
public class RouteService implements IRouteService {
    private final Logger logger = LoggerFactory.getLogger(RouteService.class);

    /**
     * 路由入口
     *
     * @param sql              原始的sql语句
     * @param parameterCommand 业务层通过setString(1,"")等设置的参数
     * @return
     */
    @Override
    public ExecutePlan doRoute(String sql, Map<Integer, ParameterCommand> parameterCommand) {
        SqlType sqlType = SqlTypeUtil.getSqlType(sql);
        SqlParser parser = SqlParserFactory.getParser(sqlType);

        if (parser == null) {

        }

        ParseResult result = new ParseResult();

        parser.init(sql, buildParameters(parameterCommand));

        parser.parse(result);

        ExecutePlan plan = route(result.getTables(), result.getCalculateUnits(), sql, sqlType);

        parser.changeSql(result, plan);

        return plan;
    }

    /**
     * 根据CalculateUnit进行路由计算
     *
     * @param tables         sql中提取到的所有表名
     * @param calculateUnits sql中提取到的计算单元
     * @param sql            原始sql
     * @param sqlType        sql类型
     * @return
     */
    private ExecutePlan route(List<String> tables, List<CalculateUnit> calculateUnits, String sql, SqlType sqlType) {
        //先打个log
        if (logger.isDebugEnabled()) {
            logger.debug("计算单元:", +PrintUtil.printCalculates(calculateUnits));
        }

        // 判断是否解析出了表名
        if (tables == null || tables.size() == 0) {
            return buildExecutePlanTypeNo(sql, null, sqlType);
        }



    }

    /**
     * 如果一个表不是分区表,则创建无路由执行计划
     *
     * @param sql
     * @param o
     * @param sqlType
     * @return
     */
    private ExecutePlan buildExecutePlanTypeNo(String sql, String tableName, SqlType sqlType) {
        //不是分区表,那么久不需要路由
        ExecutePlan plan = new ExecutePlan();
        plan.setExecuteType(ExecuteType.NO);
        TargetSqlEntity actionSql = new TargetSqlEntity();
        actionSql.setSqlType(sqlType);
        actionSql.setPartition(null);
        actionSql.setLogicTableName(tableName);
        actionSql.setOriginalSql(sql);
        actionSql.setTargetSql(sql);
        actionSql.setTargetTableName(tableName);
        plan.addSql(actionSql);
        return plan;
    }

    private List<Object> buildParameters(Map<Integer, ParameterCommand> parameterCommand) {
    }
}
